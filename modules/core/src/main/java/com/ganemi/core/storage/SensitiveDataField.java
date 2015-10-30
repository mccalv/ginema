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
 * A sensitive data field is the holder for a sensitive data of a supported types:
 * <ul>
 * <li>String
 * <li>Date
 * <li>Number
 * <li>bytes[]
 * The holder contains another important 
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

		}
	};

	private String identifier;
	private T value;

	public SensitiveDataField(T value) {
		this(null, value);
	}

	public SensitiveDataField(String identifier, T value) {
		if (!ReflectionUtils.isAssignableFrom(value.getClass(), SUPPORTED_FIELD_TYPES)) {
			throw new IllegalArgumentException(
					"Type: " + value.getClass().getName() + " not supported. Sensitive data can be only of types:"
							+ Arrays.toString(SUPPORTED_FIELD_TYPES.toArray()));
		}

		this.identifier = identifier;
		this.value = value;
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
