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
/**
 * 
 */
package com.ganemi.core.idgenerator.impl;

import java.util.UUID;

import com.ganemi.core.idgenerator.IDGenerator;
import com.ganemi.core.storage.SensitiveDataID;

/**
 * Implementation based on java UUID generator
 * 
 * @author mccalv
 *
 */
public class UUIDGenerator implements IDGenerator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ganemi.core.id.IDGenerator#generate()
	 */
	@Override
	public SensitiveDataID generate() {
		return new SensitiveDataID(UUID.randomUUID().toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ganemi.core.id.IDGenerator#fromString(java.lang.String)
	 */
	@Override
	public SensitiveDataID fromString(String s) {
		// TODO Auto-generated method stub
		return new SensitiveDataID(UUID.fromString(s).toString());
	}

}
