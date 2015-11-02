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
package com.ganemi.core.enricher;

import java.lang.reflect.Field;

import org.apache.commons.lang.ClassUtils;

import com.ganemi.core.reflection.ReflectionUtils;
import com.ganemi.core.storage.SensitiveDataField;
import com.ganemi.core.storage.SensitiveDataHolder;
import com.ganemi.core.storage.SensitiveDataID;
import com.ganemi.core.storage.SensitiveDataRoot;

/**
 * Basic scope of this class is to enrich an
 * 
 * @author mccalv
 *
 */
public class SensitiveDataEnricher {

  /**
   * Given an object
   * 
   * @param d
   */
  public static SensitiveDataHolder enrich(Object o) {
    SensitiveDataRoot sensitiveDataRoot = ReflectionUtils.getAnnotation(o, SensitiveDataRoot.class);
    if (sensitiveDataRoot == null) {
      throwIllegalArgumentException();
    }

    SensitiveDataHolder holder = SensitiveDataHolder.builder();
    holder.withDomain(sensitiveDataRoot.name());
    try {


      enrichWithId(o, holder);
      enrichObjectTree(o, holder);
      return holder;
    } catch (Exception e) {
      throw new RuntimeException(e.toString(), e);
    }

  }

  private static void enrichWithId(Object o, SensitiveDataHolder holder) throws Exception {
    boolean asId = false;
    for (Field f : o.getClass().getDeclaredFields()) {
      if (!ReflectionUtils.isPrimitive(f)) {

        if (ReflectionUtils.isAssignableFrom(f.getType(), SensitiveDataID.class)) {
          f.setAccessible(true);

          holder.withId((SensitiveDataID) f.get(o));
          asId = true;
        }
      }
    }
    if (!asId) {
      throwIllegalArgumentException();
    }
  }

 

  /**
   * Recursive method to enrich the object
   * 
   * @param o
   * @param holder
   * @throws IllegalAccessException
   */
  private static void enrichObjectTree(Object o, SensitiveDataHolder holder) throws Exception {
    for (Field f : o.getClass().getDeclaredFields()) {
      if (!ReflectionUtils.isPrimitive(f)) {
        f.setAccessible(true);
        Object value = f.get(o);
        if (ClassUtils.isAssignable(f.getType(), SensitiveDataField.class)) {
          f.setAccessible(true);
          holder.withField((SensitiveDataField<?>) value);
        }
        if (value != null && !ReflectionUtils.isJDKClass(value.getClass())
            && ReflectionUtils.isAssignableFrom(value.getClass(), Object.class)) {
          enrichObjectTree(value, holder);

        }

      }
    }

  }

  private static void throwIllegalArgumentException() {
    throw new IllegalArgumentException(
        "To Enrich Object it has to be marked with " + SensitiveDataRoot.class.getName()
            + " and contains at least one field of type  " + SensitiveDataID.class.getName());
  }

}
