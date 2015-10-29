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
package com.ganemi.core.storage;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ganemi.core.reflection.ReflectionUtils;

/**
 * @author mccalv
 *
 */
public class SensitiveDataField<T> {

	@SuppressWarnings("rawtypes")
	public static final Set<Class> SUPPORTED_FIELD_TYPES = new HashSet<Class>() {
		private static final long serialVersionUID = 1167902970132800721L;

		{
			add(String.class);
			add(Date.class);
			add(Number.class);
			add(byte[].class);
		//	add(Integer.class);
			
		}
	};

	private Class<T> c;
	private String identifier;
	private T value;

	public SensitiveDataField(Class<T> c, String identifier, T value) {
		if (!ReflectionUtils.isAssignableFrom(c, SUPPORTED_FIELD_TYPES)) {
			throw new IllegalArgumentException(
					"Type: " + c.getName() + " not supported. Sensitive data can be only of types:"
							+ Arrays.toString(SUPPORTED_FIELD_TYPES.toArray()));
		}

		this.c = c;
		this.identifier = identifier;
		this.value = value;
	}

	/**
	 * @return the c
	 */
	public Class<T> getC() {
		return c;
	}

	/**
	 * @param c
	 *            the c to set
	 */
	public void setC(Class<T> c) {
		this.c = c;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

}
