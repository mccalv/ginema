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
package com.ganemi.core.idgenerator.impl;

import org.apache.commons.lang.RandomStringUtils;

import com.ganemi.core.idgenerator.IDGenerator;
import com.ganemi.core.storage.SensitiveDataID;

/**
 * Implementation of {@link IDGenerator} based on {@link RandomStringUtils}. Its
 * produce a 16 chars string from a case sensitive set of English alphabet
 * letters and numbers. I.E :
 * 
 * <pre>
 * 2dLfjV78kOJAfI1h
 * 
 * @author mccalv
 *
 */
public class RandomIDGenerator implements IDGenerator {

	private static final char CHARS[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'J', 'L', 'M', 'N', 'O',
			'P', 'R', 'S', 'T', 'U', 'V', 'X', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'k', 'j', 'l', 'm',
			'n', 'o', 'p', 'r', 's', 't', 'u', 'v', 'x', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	private static final int DEFAULT_LENGTH = 16;

	@Override
	public SensitiveDataID generate() {
		return new SensitiveDataID(RandomStringUtils.random(DEFAULT_LENGTH, CHARS));
	}

	@Override
	public SensitiveDataID fromString(String s) {
		if (s == null || s.length() != DEFAULT_LENGTH || !s.matches("^[a-zA-Z0-9]+$"))
			throw new IllegalArgumentException("String is not a valid pattern");
		return new SensitiveDataID(s);
	}

}
