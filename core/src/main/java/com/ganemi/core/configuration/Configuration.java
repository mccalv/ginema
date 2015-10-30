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
package com.ganemi.core.configuration;

import com.ganemi.core.idgenerator.IDGenerator;
import com.ganemi.core.idgenerator.impl.UUIDGenerator;

/**
 * Configuration file. From this file the basic generation strategy is defined.
 * 
 * @author mccalv
 *
 */
public class Configuration {
	private static final String ENV_PROPERTY_GENERATOR = "com.ganemi.idgenerator";
	private static IDGenerator idGenerator;

	public static IDGenerator getIDGenerator() {

		if (idGenerator == null) {
			synchronized (Configuration.class) {
				String className = System.getProperty(ENV_PROPERTY_GENERATOR);
				if (className != null) {
					try {
						idGenerator = (IDGenerator) Class.forName(className).newInstance();
					} catch (Exception e) {
						throw new IllegalArgumentException(
								"Impossible to instanziate idGenerator from classname " + className);
					}
				} else {
					idGenerator = new UUIDGenerator();
				}
			}
		}

		return idGenerator;
	}

}
