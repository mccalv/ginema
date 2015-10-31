/*******************************************************************************
 * Copyright Mirko Calvaresi mccalv@gmail.com 2015
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
/**
 * 
 */
package com.ganemi.core.reflection;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang.ClassUtils;

/**
 * Utility class to handle basic reflection operation. The implementation is bases on
 * {@link ClassUtils}
 * 
 * @author mccalv
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})

public class ReflectionUtils {

  public static Stream<Field> getAnnotatedFields(Object a, Class annotation) {
		List<Field> declaredFields = new LinkedList<>(Arrays.asList(a.getClass().getDeclaredFields()));
		return declaredFields.stream().filter(p -> p.getAnnotation(annotation) != null);
		    
		
	}

  public static boolean isAssignableFrom(Class c, Set<Class> allowedClasses) {
    return allowedClasses.stream().filter(clazz -> ClassUtils.isAssignable(c, clazz, true))
        .findFirst().isPresent();
  }

  public static boolean isACollection(Object c) {
    return ClassUtils.isAssignable(c.getClass(), Collection.class);
  }

  public static boolean isPrimitive(Object c) {
    return c.getClass().isPrimitive() || ClassUtils.wrapperToPrimitive(c.getClass()) != null;

  }

  public static boolean isAnnotatedWith(Object c, Class clazz) {
    return c.getClass().isAnnotationPresent(clazz);
  }
}
