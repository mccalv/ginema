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
import java.nio.ByteBuffer;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

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

     SensitiveDataHolder sensitiveDataHolder =new SensitiveDataHolder();
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
    for (Field f : o.getClass().getDeclaredFields()) {
      if (!ReflectionUtils.isPrimitive(f)) {

        if (ReflectionUtils.isAssignableFrom(f.getType(), SensitiveDataID.class)) {
          f.setAccessible(true);

          holder.setId(((SensitiveDataID) f.get(o)).getId());
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
          populateHolderMapByType(holder, (SensitiveDataField<?>) value);
          // holder.withField((SensitiveDataField<?>) value);
        }
        if (value != null && !ReflectionUtils.isJDKClass(value.getClass())
            && ReflectionUtils.isAssignableFrom(value.getClass(), Object.class)) {
          enrichObjectTree(value, holder);

        }

      }
    }

  }

  public static <V> void populateTypedMap(SensitiveDataField<?> value, Map<String, V> map, V v) {
    // Initializes the map if null
    if (map == null)
      map = new HashMap<String, V>();

    map.put(value.getIdentifier().getId(), v);

  }

  private static void populateHolderMapByType(SensitiveDataHolder holder, SensitiveDataField<?> value) {
    if (value.getValue() == null)
      return;
    @SuppressWarnings("rawtypes")
    Class clazz = value.getValue().getClass();
    if (ClassUtils.isAssignable(clazz, Date.class)) {
      populateTypedMap(value, holder.getDates(),
          new DateEntry(value.getIdentifier().getId(), ((Date) value.getValue()).getTime()));
      return;
    }
    if (ClassUtils.isAssignable(clazz, String.class)) {
      populateTypedMap(value, holder.getStrings(),
          new StringEntry(value.getIdentifier().getId(), ((String) value.getValue())));
      return;
    }
    if (ClassUtils.isAssignable(clazz, Long.class)) {
      populateTypedMap(value, holder.getLongs(),
          new LongEntry(value.getIdentifier().getId(), ((Long) value.getValue())));
      return;
    }
  
    if (ClassUtils.isAssignable(clazz, Double.class)) {
      populateTypedMap(value, holder.getDoubles(),
          new DoubleEntry(value.getIdentifier().getId(), ((Double) value.getValue())));
      return;
    }

    if (ClassUtils.isAssignable(clazz, Boolean.class)) {
      populateTypedMap(value, holder.getBooleans(),
          new BooleanEntry( value.getIdentifier().getId(),(Boolean) value.getValue()));
      return;
    }
    if (ClassUtils.isAssignable(clazz, byte[].class)) {
      populateTypedMap(value, holder.getBytes(), new BytesEntry(value.getIdentifier().getId(),
          ByteBuffer.wrap((byte[]) value.getValue())));
      return;
    }



  }

  private static void throwIllegalArgumentException() {
    throw new IllegalArgumentException(
        "To Enrich Object it has to be marked with " + SensitiveDataRoot.class.getName()
            + " and contains at least one field of type  " + SensitiveDataID.class.getName());
  }

}
