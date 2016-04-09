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
package com.ginema.crypt.encryption;

import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

public class AESEncryption {

	PaddedBufferedBlockCipher encryptCipher;
	PaddedBufferedBlockCipher decryptCipher;

	// Buffer used to transport the bytes from one stream to another
	byte[] buf = new byte[16]; // input buffer
	byte[] obuf = new byte[512]; // output buffer

	byte[] key = null;

	

	public AESEncryption(byte[] keyBytes) {
		key = new byte[keyBytes.length];
		System.arraycopy(keyBytes, 0, key, 0, keyBytes.length);
		InitCiphers();
	}

	private void InitCiphers() {
		encryptCipher = new PaddedBufferedBlockCipher(new AESEngine());
		encryptCipher.init(true, new KeyParameter(key));
		decryptCipher = new PaddedBufferedBlockCipher(new AESEngine());
		decryptCipher.init(false, new KeyParameter(key));
	}

	public void ResetCiphers() {
		if (encryptCipher != null)
			encryptCipher.reset();
		if (decryptCipher != null)
			decryptCipher.reset();
	}

	public void encrypt(InputStream in, OutputStream out) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException,
			DataLengthException, IllegalStateException, InvalidCipherTextException {
		try {
			// Bytes written to out will be encrypted
			// Read in the cleartext bytes from in InputStream and
			// write them encrypted to out OutputStream

			int noBytesRead = 0; // number of bytes read from input
			int noBytesProcessed = 0; // number of bytes processed

			while ((noBytesRead = in.read(buf)) >= 0) {
				// System.out.println(noBytesRead +" bytes read");

				noBytesProcessed = encryptCipher.processBytes(buf, 0, noBytesRead, obuf, 0);
				// System.out.println(noBytesProcessed +" bytes processed");
				out.write(obuf, 0, noBytesProcessed);
			}

			// System.out.println(noBytesRead +" bytes read");
			noBytesProcessed = encryptCipher.doFinal(obuf, 0);

			// System.out.println(noBytesProcessed +" bytes processed");
			out.write(obuf, 0, noBytesProcessed);

			out.flush();
		} catch (java.io.IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void decrypt(InputStream in, OutputStream out) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException,
			DataLengthException, IllegalStateException, InvalidCipherTextException {
		try {
			// Bytes read from in will be decrypted
			// Read in the decrypted bytes from in InputStream and and
			// write them in cleartext to out OutputStream

			int noBytesRead = 0; // number of bytes read from input
			int noBytesProcessed = 0; // number of bytes processed

			while ((noBytesRead = in.read(buf)) >= 0) {
				// System.out.println(noBytesRead +" bytes read");
				noBytesProcessed = decryptCipher.processBytes(buf, 0, noBytesRead, obuf, 0);
				// System.out.println(noBytesProcessed +" bytes processed");
				out.write(obuf, 0, noBytesProcessed);
			}
			// System.out.println(noBytesRead +" bytes read");
			noBytesProcessed = decryptCipher.doFinal(obuf, 0);
			// System.out.println(noBytesProcessed +" bytes processed");
			out.write(obuf, 0, noBytesProcessed);

			out.flush();
		} catch (java.io.IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
