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

import java.io.InputStream;

import com.ginema.crypto.encryption.EncryptionManager;
import com.ginema.crypto.encryption.PGPEncryption;
import com.ginema.crypto.encryption.impl.PGPEncryptionManager;

public class PGPTestEncryptionTest extends AbstractEncryptionTest {
  @Override
  protected EncryptionManager getEncryptionManager() {
    return new PGPEncryptionManager();
  }

  @Override
  protected InputStream getPrivateKey() {
    return PGPEncryption.class.getResourceAsStream("/keys/pgp/secret.asc");

  }

  @Override
  protected InputStream getPublicKey() {
    return PGPEncryption.class.getResourceAsStream("/keys/pgp/pub.asc");

  }

  @Override
  protected char[] getPassword() {
    return "password".toCharArray();
  }



}
