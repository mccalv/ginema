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
package com.ganemi.core.reflection;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang.ClassUtils;

/**
 * @author mccalv
 *
 */
public class ReflectionUtils {

	@SuppressWarnings("unchecked")
	public static List<Field> getAnnotatedFields(Object a, @SuppressWarnings("rawtypes") Class annotation) {
		List<Field> declaredFields = new LinkedList<>(Arrays.asList(a.getClass().getDeclaredFields()));
		declaredFields.removeIf(p -> p.getAnnotation(annotation) != null);
		return declaredFields;
	}

	@SuppressWarnings("unchecked")
	public static boolean isAssignableFrom(Class c, Set<Class> allowedClasses) {
		return allowedClasses.stream().filter(clazz -> ClassUtils.isAssignable(c, clazz, true)).findFirst().isPresent();

	}

}
