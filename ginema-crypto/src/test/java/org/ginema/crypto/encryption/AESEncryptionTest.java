/*******************************************************************************
 * Copyright Mirko Calvaresi mccalv@gmail.com 2015, 2016 
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
package org.ginema.crypto.encryption;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.ginema.api.idgenerator.impl.RandomIDGenerator;
import com.ginema.crypto.encryption.AESEncryption;

public class AESEncryptionTest extends AbstractEncryptionTest {

  protected void verifyAndDecrypt(String text) throws Exception {

    ByteArrayOutputStream bs = new ByteArrayOutputStream();
    AESEncryption aes = new AESEncryption(new RandomIDGenerator().generate().toString().getBytes());
    aes.encrypt(new ByteArrayInputStream(text.getBytes()), bs);;

    byte enc[] = bs.toByteArray();

    ByteArrayOutputStream bsD = new ByteArrayOutputStream();

    aes.decrypt(new ByteArrayInputStream(enc), bsD);
    assertEquals(text, new String(bsD.toByteArray()));

  }
}
