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
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang.ClassUtils;

import com.ginema.api.avro.SensitiveDataHolder;
import com.ginema.api.reflection.ReflectionUtils;
import com.ginema.api.storage.SensitiveDataField;
import com.ginema.api.storage.SensitiveDataID;
import com.ginema.api.storage.SensitiveDataRoot;

/**
 * Basic scope of this class is to restore the sensitive data to the object in order to be used by
 * the application.
 * 
 * @author mccalv
 * @param <T>
 *
 */
@SuppressWarnings("unchecked")

public class SensitiveDataEnricher {

  /**
   * It merges the sensitive data coming from the holder with the fields of type
   * {@link SensitiveDataField}. The reference or "link" between the 2 objects are the
   * {@link SensitiveDataID} contained in each field of the previous type.
   * 
   * @param holder, the SensitiveDataHolder containing the populated values
   * @param object, the object containing just the sensitive data ids
   */
  public void enrich(SensitiveDataHolder holder, Object o) {
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

  private void checkId(Object o, SensitiveDataHolder holder) throws Exception {
    boolean hasId = false;
    for (Field f : o.getClass().getDeclaredFields()) {
      if (!ReflectionUtils.isPrimitive(f)) {
        if (ReflectionUtils.isAssignableFrom(f.getType(), SensitiveDataID.class)) {
          Method m = PropertyDescriptorHolder.getGetterMethod(o.getClass(), f.getName());
          SensitiveDataID sensitiveDataID = (SensitiveDataID) m.invoke(o, null);
          if (!sensitiveDataID.getId().equals(holder.getId())) {
            throwIllegalArgumentException();
          }
          hasId = true;
          return;
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
  private void enrichObjectTree(Object o, SensitiveDataHolder holder) throws Exception {
    for (Field f : o.getClass().getDeclaredFields()) {
      if (!ReflectionUtils.isPrimitive(f)) {
        Object value =
            PropertyDescriptorHolder.getGetterMethod(o.getClass(), f.getName()).invoke(o);
        if (ClassUtils.isAssignable(f.getType(), SensitiveDataField.class)) {
          populateHolderMapByType(o, f, (SensitiveDataField<?>) value, holder);
        }
        checkAndEnrichObject(holder, value);
        if (value != null && ReflectionUtils.isACollection(value)) {
          for (Object element : (java.util.Collection) value) {
            checkAndEnrichObject(holder, element);
          }

        }
      }
    }

  }

  /**
   * Check if the object is not a Java native
   * 
   * @param holder
   * @param value
   * @throws Exception
   */
  private void checkAndEnrichObject(SensitiveDataHolder holder, Object value) throws Exception {
    if (value != null && !ReflectionUtils.isJDKClass(value.getClass())
        && ReflectionUtils.isAssignableFrom(value.getClass(), Object.class)) {
      enrichObjectTree(value, holder);

    }
  }



  private void populateHolderMapByType(Object obj, Field field,
      @SuppressWarnings("rawtypes") SensitiveDataField value, SensitiveDataHolder holder) {
    if (value == null || value.getIdentifier() == null)
      return;
    // Class clazz =value.getValue().getClass();
    String id = value.getIdentifier().getId();

    if (holder.getDates() != null && holder.getDates().get(id) != null) {
      value.setValue(new Date(holder.getDates().get(id).getValue()));

    }
    if (holder.getStrings() != null && holder.getStrings().get(id) != null) {
      value.setValue(holder.getStrings().get(id).getValue());

    }

    if (holder.getLongs() != null && holder.getLongs().get(id) != null) {
      value.setValue(holder.getLongs().get(id).getValue());

    }
    if (holder.getBooleans() != null && holder.getBooleans().get(id) != null) {
      value.setValue(holder.getBooleans().get(id).getValue());

    }
    if (holder.getIntegers() != null && holder.getIntegers().get(id) != null) {
      value.setValue(holder.getIntegers().get(id).getValue());

    }
    if (holder.getBytes() != null && holder.getBytes().get(id) != null) {
      value.setValue(holder.getBytes().get(id).getValue().get());

    }



  }



  private static void throwIllegalArgumentException() {
    throw new IllegalArgumentException(
        "To Enrich Object it has to be marked with " + SensitiveDataRoot.class.getName()
            + " and contains at least one field of type  " + SensitiveDataID.class.getName());
  }
}
