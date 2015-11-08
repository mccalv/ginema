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
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.commons.lang.ClassUtils;

import com.ginema.api.avro.BooleanEntry;
import com.ginema.api.avro.BytesEntry;
import com.ginema.api.avro.DateEntry;
import com.ginema.api.avro.DoubleEntry;
import com.ginema.api.avro.LongEntry;
import com.ginema.api.avro.SensitiveDataHolder;
import com.ginema.api.avro.StringEntry;
import com.ginema.api.reflection.ReflectionUtils;
import com.ginema.api.storage.SensitiveDataField;
import com.ginema.api.storage.SensitiveDataID;
import com.ginema.api.storage.SensitiveDataRoot;

/**
 * Basic scope of this class is to provide utility to extract sensitive data from an object in order
 * to be serialized or sent.
 * 
 * 
 * @author mccalv
 * @param <T>
 *
 */

public class SensitiveDataExtractor {


  /**
   * Given an domain object annotated with {@link SensitiveDataRoot}, checks all fields of type
   * {@link SensitiveDataField} and populates a
   * {@link com.ginema.api.storage.SensitiveDataHolder}, which is the Apache avro object designed to contain all
   * sensitive data.
   * 
   * @throw {@link IllegalArgumentException} if the object is not annotated with
   *        {@link SensitiveDataRoot} and does not contain a field of type {@link SensitiveDataID}
   * 
   * @param the object
   */
  public static SensitiveDataHolder extractSensitiveData(Object o) {
    SensitiveDataRoot sensitiveDataRoot = ReflectionUtils.getAnnotation(o, SensitiveDataRoot.class);
    if (sensitiveDataRoot == null) {
      throwIllegalArgumentException();
    }

    SensitiveDataHolder sensitiveDataHolder = new SensitiveDataHolder();
    sensitiveDataHolder.setDomain(sensitiveDataRoot.name());
    try {


      enrichWithId(o, sensitiveDataHolder);
      enrichObjectTree(o, sensitiveDataHolder);
      return sensitiveDataHolder;
    } catch (Exception e) {
      throw new RuntimeException(e.toString(), e);
    }

  }

  private static void enrichWithId(Object o, SensitiveDataHolder holder) throws Exception {
    boolean asId = false;
    for (Field field : o.getClass().getDeclaredFields()) {
      if (!ReflectionUtils.isPrimitive(field)) {

        if (ReflectionUtils.isAssignableFrom(field.getType(), SensitiveDataID.class)) {
          Method getter = PropertyDescriptorHolder.getGetterMethod(o.getClass(), field.getName());
          holder.setId(((SensitiveDataID) getter.invoke(o, null)).getId());

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
        Method getter = PropertyDescriptorHolder.getGetterMethod(o.getClass(), f.getName());
        if (getter == null && !java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
          throw new IllegalArgumentException("No getter found for property " + f.getName());
        }
        if (getter == null)
          continue;
        Object value = getter.invoke(o, null);

        if (ClassUtils.isAssignable(f.getType(), SensitiveDataField.class)) {
          populateHolderMapByType(holder, (SensitiveDataField<?>) value);
        }
        checkAndEnrichObject(holder, value);
        checkAndEnrichCollection(holder, value);
      }
    }

  }

  @SuppressWarnings("rawtypes")
  private static void checkAndEnrichCollection(SensitiveDataHolder holder, Object value)
      throws Exception {
    if (value != null && ReflectionUtils.isACollection(value)) {
      for (Object element : (java.util.Collection) value) {
        checkAndEnrichObject(holder, element);
      }
    }
  }

  private static void checkAndEnrichObject(SensitiveDataHolder holder, Object value)
      throws Exception {
    if (value != null && !ReflectionUtils.isJDKClass(value)
        && ReflectionUtils.isAssignableFrom(value.getClass(), Object.class)) {
      enrichObjectTree(value, holder);

    }
  }

  public static <V> void populate(SensitiveDataHolder h, Map<String, V> map,
      BiConsumer<SensitiveDataHolder, Map<String, V>> biconsumer, String s, V value) {
    if (map == null)
      map = new HashMap<String, V>();

    map.put(s, value);
    biconsumer.accept(h, map);


  }

  private static void populateHolderMapByType(SensitiveDataHolder holder,
      SensitiveDataField<?> value) {
    if (value == null || value.getValue() == null)
      return;
    @SuppressWarnings("rawtypes")
    Class clazz = value.getValue().getClass();
    String id = value.getIdentifier().getId();
    String name = value.getName();
    if (ClassUtils.isAssignable(clazz, Date.class)) {

      populate(holder, holder.getDates(), SensitiveDataHolder::setDates, id,
          new DateEntry(name, ((Date) value.getValue()).getTime()));
    }
    if (ClassUtils.isAssignable(clazz, String.class)) {
      populate(holder, holder.getStrings(), SensitiveDataHolder::setStrings, id,
          new StringEntry(name, (String) value.getValue()));
      return;
    }
    if (ClassUtils.isAssignable(clazz, Long.class)) {
      populate(holder, holder.getLongs(), SensitiveDataHolder::setLongs, id,
          new LongEntry(name, (Long) value.getValue()));
      return;
    }
    if (ClassUtils.isAssignable(clazz, Double.class)) {
      populate(holder, holder.getDoubles(), SensitiveDataHolder::setDoubles, id,
          new DoubleEntry(name, (Double) value.getValue()));
      return;
    }
    if (ClassUtils.isAssignable(clazz, Boolean.class)) {
      populate(holder, holder.getBooleans(), SensitiveDataHolder::setBooleans, id,
          new BooleanEntry(name, (Boolean) value.getValue()));
      return;
    }
    if (ClassUtils.isAssignable(clazz, byte[].class)) {
      BytesEntry bytesEntry = new BytesEntry(name, ByteBuffer.wrap((byte[]) value.getValue()));
      populate(holder, holder.getBytes(), SensitiveDataHolder::setBytes, id, bytesEntry);
      return;

    }


  }

  private static void throwIllegalArgumentException() {
    throw new IllegalArgumentException(
        "To Enrich Object it has to be marked with " + SensitiveDataRoot.class.getName()
            + " and contains at least one field of type  " + SensitiveDataID.class.getName());
  }

}
