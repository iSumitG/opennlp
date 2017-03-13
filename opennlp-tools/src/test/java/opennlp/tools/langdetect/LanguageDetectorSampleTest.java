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


import org.junit.Assert;
import org.junit.Test;


public class LanguageDetectorSampleTest {

  @Test
  public void testConstructor() {
    Language lang = new Language("aLang");
    CharSequence context = "aContext";

    LanguageDetectorSample sample = new LanguageDetectorSample(lang, context);

    Assert.assertEquals(lang, sample.getLanguage());
    Assert.assertEquals(context, sample.getContext());
  }

  @Test(expected = NullPointerException.class)
  public void testNullLang() throws Exception {
    CharSequence context = "aContext";

    new LanguageDetectorSample(null, context);
  }

  @Test(expected = NullPointerException.class)
  public void testNullContext() {
    Language lang = new Language("aLang");

    new LanguageDetectorSample(lang, null);
  }

  @Test
  public void testToString() {
    Language lang = new Language("aLang");
    CharSequence context = "aContext";

    LanguageDetectorSample sample = new LanguageDetectorSample(lang, context);

    Assert.assertEquals(lang.getLang() + "\t" + context, sample.toString());
  }

  @Test
  public void testHash() {

    int hashA = new LanguageDetectorSample(new Language("aLang"), "aContext").hashCode();
    int hashB = new LanguageDetectorSample(new Language("bLang"), "aContext").hashCode();
    int hashC = new LanguageDetectorSample(new Language("aLang"), "bContext").hashCode();

    Assert.assertNotEquals(hashA, hashB);
    Assert.assertNotEquals(hashA, hashC);
    Assert.assertNotEquals(hashB, hashC);
  }

  @Test
  public void testEquals() throws Exception {

    LanguageDetectorSample sampleA = new LanguageDetectorSample(new Language("aLang"), "aContext");
    LanguageDetectorSample sampleA1 = new LanguageDetectorSample(new Language("aLang"), "aContext");
    LanguageDetectorSample sampleB = new LanguageDetectorSample(new Language("bLang"), "aContext");
    LanguageDetectorSample sampleC = new LanguageDetectorSample(new Language("aLang"), "bContext");

    Assert.assertEquals(sampleA, sampleA);
    Assert.assertEquals(sampleA, sampleA1);
    Assert.assertNotEquals(sampleA, sampleB);
    Assert.assertNotEquals(sampleA, sampleC);
    Assert.assertNotEquals(sampleB, sampleC);
    Assert.assertFalse(sampleA.equals("something else"));
  }
}
