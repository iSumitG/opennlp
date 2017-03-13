/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package opennlp.tools.langdetect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import opennlp.tools.formats.ResourceAsStreamFactory;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;


public class LanguageDetectorMETest {

  private LanguageDetectorModel model;

  @Before
  public void train() throws Exception {

    ResourceAsStreamFactory streamFactory = new ResourceAsStreamFactory(
        LanguageDetectorMETest.class, "/opennlp/tools/doccat/DoccatSample.txt");

    PlainTextByLineStream lineStream = new PlainTextByLineStream(streamFactory, "UTF-8");

    LanguageDetectorSampleStream sampleStream = new LanguageDetectorSampleStream(lineStream);

    TrainingParameters params = new TrainingParameters();
    params.put(TrainingParameters.ITERATIONS_PARAM, "100");
    params.put(TrainingParameters.CUTOFF_PARAM, "0");

    this.model = LanguageDetectorME.train(sampleStream, params, new LanguageDetectorFactory());
  }

  @Test
  public void testPredictLanguages() {
    LanguageDetector ld = new LanguageDetectorME(this.model);
    Language[] languages = ld.predictLanguages("estava em uma marcenaria na Rua Bruno");

    Assert.assertEquals(4, languages.length);
    Assert.assertEquals("pob", languages[0].getLang());
    Assert.assertEquals("ita", languages[1].getLang());
    Assert.assertEquals("spa", languages[2].getLang());
    Assert.assertEquals("fra", languages[3].getLang());
  }

  @Test
  public void testPredictLanguage() {
    LanguageDetector ld = new LanguageDetectorME(this.model);
    Language language = ld.predictLanguage("se lever mais il n ' a pas insisté");

    Assert.assertEquals("fra", language.getLang());
  }

  @Test
  public void testSupportedLanguages() {

    LanguageDetector ld = new LanguageDetectorME(this.model);
    String[] supportedLanguages = ld.getSupportedLanguages();

    Assert.assertEquals(4, supportedLanguages.length);
  }

  @Test
  public void testLoadFromFile() throws IOException {
    File tempFile = serializeModel(model);

    Assert.assertTrue(tempFile.exists());

    LanguageDetectorModel myModel = new LanguageDetectorModel(tempFile);

    Assert.assertNotNull(myModel);

  }

  @Test
  public void testLoadFromURL() throws IOException {
    File tempFile = serializeModel(model);

    LanguageDetectorModel myModel = new LanguageDetectorModel(tempFile.toURI().toURL());

    Assert.assertNotNull(myModel);

  }

  @Test
  public void testLoadFromStream() throws IOException {
    File tempFile = serializeModel(model);

    LanguageDetectorModel myModel = new LanguageDetectorModel(new FileInputStream(tempFile));

    Assert.assertNotNull(myModel);

  }

  @Test
  public void testCorrectFactory() throws IOException {
    File tempFile = serializeModel(model);

    LanguageDetectorModel myModel = new LanguageDetectorModel(tempFile);

    Assert.assertTrue(myModel.getFactory() instanceof LanguageDetectorFactory);

  }

  protected static File serializeModel(LanguageDetectorModel model) throws IOException {
    File tempFile = File.createTempFile("langdetect", "model");

    FileOutputStream fos = new FileOutputStream(tempFile);

    model.serialize(fos);

    return tempFile;
  }
}
