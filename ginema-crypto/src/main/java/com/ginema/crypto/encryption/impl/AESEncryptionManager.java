/*******************************************************************************
 * Copyright Mirko Calvaresi mccalv@gmail.com 2016
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
package com.ginema.crypto.encryption.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.ginema.crypto.encryption.AESEncryption;
import com.ginema.crypto.encryption.EncryptionManager;
import com.ginema.crypto.exception.GinemaCryptoException;
import com.ginema.crypto.exception.GinemaCryptoException.Type;

public class AESEncryptionManager implements EncryptionManager {

  @Override
  public byte[] encryptWithKey(byte[] iS, InputStream iSkey, char[] password) {
    ByteArrayOutputStream oS = new ByteArrayOutputStream();
    try {
      new AESEncryption(new String(password).getBytes()).encrypt(new ByteArrayInputStream(iS), oS);
      return oS.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
      throw new GinemaCryptoException(e, Type.ENCRYPTION_ERROR);
    }
  }

  @Override
  public byte[] decryptWithKey(byte[] iS, InputStream key, char[] password) {
    ByteArrayOutputStream oS = new ByteArrayOutputStream();
    try {
      new AESEncryption(new String(password).getBytes()).decrypt(new ByteArrayInputStream(iS), oS);
      return oS.toByteArray();
    } catch (Exception e) {
      throw new GinemaCryptoException(e, Type.DECRYPTION_ERROR);
    }
  }

}
