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

package com.ganemi.core.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.junit.Test;

import com.ganemi.core.SensitiveData;
import com.ganemi.core.reflection.ReflectionUtils;
import com.ganemi.core.storage.SensitiveDataField;

/**
 * 
 * @author mccalv
 *
 */
public class ReflectionUtilsTest {

	public static class AnObject {
		@SensitiveData
		private String name;

		private String aField;

		@SensitiveData
		private String surnname;
		public AnObject s;

	}

	@Test
	public void testShouldGetAnnotatedFields() {
		List<Field> annotatedFields = ReflectionUtils.getAnnotatedFields(new AnObject(), SensitiveData.class);
		assertEquals(2, annotatedFields.size());
	}

	@Test
	public void testShouldGetIsAssignable() {
		assertTrue(ClassUtils.isAssignable(Integer.class, Number.class, true));
		assertTrue(ReflectionUtils.isAssignableFrom(Long.class, SensitiveDataField.SUPPORTED_FIELD_TYPES));
		assertTrue(ReflectionUtils.isAssignableFrom(Integer.class, SensitiveDataField.SUPPORTED_FIELD_TYPES));
		assertTrue(ReflectionUtils.isAssignableFrom(Short.class, SensitiveDataField.SUPPORTED_FIELD_TYPES));
		assertTrue(ReflectionUtils.isAssignableFrom(byte.class, SensitiveDataField.SUPPORTED_FIELD_TYPES));
		assertTrue(ReflectionUtils.isAssignableFrom(int.class, SensitiveDataField.SUPPORTED_FIELD_TYPES));
		assertTrue(ReflectionUtils.isAssignableFrom(byte[].class, SensitiveDataField.SUPPORTED_FIELD_TYPES));

	}

}
