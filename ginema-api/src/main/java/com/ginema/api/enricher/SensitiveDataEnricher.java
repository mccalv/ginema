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
package com.ginema.api.enricher;

import java.lang.reflect.Field;
import java.util.Date;

import org.apache.commons.lang.ClassUtils;

import com.ginema.api.avro.SensitiveDataHolder;
import com.ginema.api.reflection.ReflectionUtils;
import com.ginema.api.storage.SensitiveDataField;
import com.ginema.api.storage.SensitiveDataID;
import com.ginema.api.storage.SensitiveDataRoot;

/**
 * Basic scope of this class is to enrich an
 * 
 * @author mccalv
 * @param <T>
 *
 */

public class SensitiveDataEnricher {

  /**
   * Given an object
   * 
   * @param d
   */
  public static void enrich(SensitiveDataHolder holder, Object o) {
    SensitiveDataRoot sensitiveDataRoot = ReflectionUtils.getAnnotation(o, SensitiveDataRoot.class);
    if (sensitiveDataRoot == null) {
      throwIllegalArgumentException();
    }
    if (sensitiveDataRoot.name().equals(holder.getDomain())) {
      throwIllegalArgumentException();
    }
    try {

      checkId(o, holder);
      enrichObjectTree(o, holder);

    } catch (Exception e) {
      throw new RuntimeException(e.toString(), e);
    }

  }

  private static void checkId(Object o, SensitiveDataHolder holder) throws Exception {
    boolean hasId = false;
    for (Field f : o.getClass().getDeclaredFields()) {
      if (!ReflectionUtils.isPrimitive(f)) {
        if (ReflectionUtils.isAssignableFrom(f.getType(), SensitiveDataID.class)) {
          f.setAccessible(true);
          SensitiveDataID sensitiveDataID = (SensitiveDataID) f.get(o);
          if (!sensitiveDataID.getId().equals(holder.getId())) {
            throwIllegalArgumentException();
          }
          hasId = true;
        }
      }
    }
    if (!hasId) {
      throwIllegalArgumentException();
    }
  }

  /**
   * Enriches the object tree. In case of the collection falls back to each element of the
   * collection
   * 
   * @param o
   * @param holder
   * @throws IllegalAccessException
   */
  @SuppressWarnings("rawtypes")
  private static void enrichObjectTree(Object o, SensitiveDataHolder holder) throws Exception {
    for (Field f : o.getClass().getDeclaredFields()) {
      if (!ReflectionUtils.isPrimitive(f)) {
        f.setAccessible(true);
        Object value = f.get(o);
        if (ClassUtils.isAssignable(f.getType(), SensitiveDataField.class)) {
          f.setAccessible(true);
          populateHolderMapByType(o, f, (SensitiveDataField<?>) value, holder);
          // holder.withField((SensitiveDataField<?>) value);
        }
        checkAndEnrichObject(holder, value);
        if (value!= null && ReflectionUtils.isACollection(value)) {
          for (Object element : (java.util.Collection) value) {
            checkAndEnrichObject(holder, element);
          }

        }
      }
    }

  }

  /**
   * Check if the object is not a primitive or base type and then try to enrich it
   * 
   * @param holder
   * @param value
   * @throws Exception
   */
  private static void checkAndEnrichObject(SensitiveDataHolder holder, Object value)
      throws Exception {
    if (value != null && !ReflectionUtils.isJDKClass(value.getClass())
        && ReflectionUtils.isAssignableFrom(value.getClass(), Object.class)) {
      enrichObjectTree(value, holder);

    }
  }



  private static <V> void populate(Object obj, Field field, String id, V value) {
    field.setAccessible(true);
    try {
      field.set(obj, new SensitiveDataField<V>(id, value));
    } catch (Exception ex) {
      throwIllegalArgumentException(ex);
    }
  }

  private static void populateHolderMapByType(Object obj, Field field, SensitiveDataField<?> value,
      SensitiveDataHolder holder) {
    if (value==null|| value.getValue() == null)
      return;
    @SuppressWarnings("rawtypes")
    Class clazz = value.getValue().getClass();
    String id = value.getIdentifier().getId();

    if (ClassUtils.isAssignable(clazz, Date.class)) {
      populate(obj, field, id, holder.getDates().get(id));
    }
    if (ClassUtils.isAssignable(clazz, String.class)) {
      populate(obj, field, id, holder.getStrings().get(id));
      return;
    }
    if (ClassUtils.isAssignable(clazz, Long.class)) {
      populate(obj, field, id, holder.getLongs().get(id));
      return;
    }

    if (ClassUtils.isAssignable(clazz, Double.class)) {
      populate(obj, field, id, holder.getDoubles().get(id));
      return;
    }

    if (ClassUtils.isAssignable(clazz, Boolean.class)) {
      populate(obj, field, id, holder.getBooleans().get(id));
      return;
    }
    if (ClassUtils.isAssignable(clazz, byte[].class)) {
      populate(obj, field, id, holder.getBytes().get(id));
      return;

    }
  }

  private static void throwIllegalArgumentException(Exception exception) {
    throw new IllegalArgumentException(
        "Exception occuring while enriching: " + exception.getMessage());
  }

  private static void throwIllegalArgumentException() {
    throw new IllegalArgumentException(
        "To Enrich Object it has to be marked with " + SensitiveDataRoot.class.getName()
            + " and contains at least one field of type  " + SensitiveDataID.class.getName());
  }
}
