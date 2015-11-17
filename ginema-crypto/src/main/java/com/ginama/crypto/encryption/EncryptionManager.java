/**
 * 
 */
package com.ginama.crypto.encryption;

import java.io.InputStream;

/**
 * @author mccalv
 *
 */
public interface EncryptionManager {


  byte[] encryptWithKey(byte[] iS, InputStream iSkey, char[] password);

  byte[] decryptWithKey(byte[] iS, InputStream key, char[] password);

}
