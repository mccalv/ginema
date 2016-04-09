/*******************************************************************************
 * Copyright Mirko Calvaresi mccalv@gmail.com 2015
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
package org.ginema.crypt.encryption;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import com.ginema.crypt.encryption.PGPEncryption;

public class PGPTestEncryptionTest extends AbstractEncryptionTest {

  protected void verifyAndDecrypt(String text) throws Exception {

    InputStream privateKey = PGPEncryption.class.getResourceAsStream("/keys/pgp/secret.asc");
    InputStream pubKey = PGPEncryption.class.getResourceAsStream("/keys/pgp/pub.asc");
    byte enc[] = PGPEncryption.encrypt(text.getBytes(), pubKey);
    byte dec[] = PGPEncryption.decrypt(enc, privateKey, "password".toCharArray());
    System.out.println(text);
    assertEquals(text, new String(dec));
  }
}
