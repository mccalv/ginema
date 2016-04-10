/*******************************************************************************
 * Copyright Mirko Calvaresi mccalv@gmail.com 2015, 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *******************************************************************************/
package org.ginema.crypto.encryption;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

import com.ginema.crypto.encryption.EncryptionManager;

/**
 * An abstract template class for testing different encryption mechanisms
 * 
 * @author mccalv
 *
 */
public abstract class AbstractEncryptionTest {



  private final EncryptionManager encryptionManager = getEncryptionManager();
  private final InputStream privateKey = getPrivateKey();
  private final InputStream publicKey = getPublicKey();
  protected char[] password = getPassword();


  protected abstract EncryptionManager getEncryptionManager();

  protected abstract InputStream getPrivateKey();

  protected abstract InputStream getPublicKey();

  protected abstract char[] getPassword();


  private static final String TEXT[] =
      {"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...",
          "わたしは えいご", "الحصان العربي "};

  @Test
  public void testEncryptionAndDecryption() {

    Arrays.stream(TEXT).forEach(this::testStringEncryption);
  }

  private void testStringEncryption(String t) {
    verifyAndDecrypt(t);
  }


  private void verifyAndDecrypt(String t) {
    byte[] en;
    System.out.println(t);
    try {
      en = encryptionManager.encryptWithKey(t.getBytes("utf-8"), getPublicKey(), password);
      byte[] dec = encryptionManager.decryptWithKey(en, getPrivateKey(), password);

      assertEquals(t, new String(dec));
      if (publicKey != null)
        publicKey.close();
      if (privateKey != null)

        privateKey.close();
    } catch (Exception e) {

      e.printStackTrace();
      throw new RuntimeException();


    }

  };
}
