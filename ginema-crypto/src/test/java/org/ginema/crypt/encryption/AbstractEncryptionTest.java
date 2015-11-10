/*******************************************************************************
 * Copyright Mirko Calvaresi mccalv@gmail.com 2015 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *******************************************************************************/
package org.ginema.crypt.encryption;

import java.util.Arrays;

import org.junit.Test;
/**
 * An abstract template class for testing different encryption mechanisms
 * @author mccalv
 *
 */
public abstract class AbstractEncryptionTest {

  private static final String TEXT[] =
      {"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...",
          "わたしは えいご", "الحصان العربي "};

  @Test
  public void testEncryptionAndDecryption() throws Exception {
    Arrays.stream(TEXT).forEach(this::testStringEncryption);
  }

  private void testStringEncryption(String t) {
    try {
      verifyAndDecrypt(t);
    } catch (Exception e) {
      try {
        throw e;
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }
  }

  protected abstract void verifyAndDecrypt(String t) throws Exception;
}
