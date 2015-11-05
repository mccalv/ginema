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
 * Basic scope of this class is to enrich an
 * 
 * @author mccalv
 * @param <T>
 *
 */

public class SensitiveDataEnricher {
  @FunctionalInterface
  public interface Predicate<T> {
    boolean test(T t);
  }

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

  /*
   * 
   * @FunctionalInterface public interface BiFunction<T, U, R> { R apply(T t, U u); }
   * 
   * public static <V> void populateTypedMapFunctional(SensitiveDataHolder r, SensitiveDataField<?>
   * value, Consumer<V> consumer, Map<String, V> map, Supplier<V> supplier) {
   * 
   * 
   * BiConsumer<SensitiveDataHolder, Map<String, StringEntry>> studentNameSetter =
   * SensitiveDataHolder::setStrings;
   * 
   * 
   * 
   * Consumer<SensitiveDataHolder> studentNameGetter = SensitiveDataHolder::getStrings;
   * 
   * 
   * 
   * 
   * BiFunction<SensitiveDataHolder, Map<String, V>, V> initIfNull = (t1, t2) -> { if (t2 == null)
   * return v; return null; };
   * 
   * 
   * // Initializes the map if null Predicate<Map<String, V>> predicate = t -> t == null |
   * t.isEmpty(); if (predicate.test(map)) if (map == null) map = new HashMap<String, V>();
   * map.put(value.getIdentifier().getId(), v);
   * 
   * }
   */

  private static void populateHolderMapByType(SensitiveDataHolder holder,
      SensitiveDataField<?> value) {
    if (value.getValue() == null)
      return;
    @SuppressWarnings("rawtypes")
    Class clazz = value.getValue().getClass();
    String id = value.getIdentifier().getId();

    if (ClassUtils.isAssignable(clazz, Date.class)) {

      populate(holder, holder.getDates(), SensitiveDataHolder::setDates, id,
          new DateEntry(id, ((Date) value.getValue()).getTime()));
    }
    if (ClassUtils.isAssignable(clazz, String.class)) {
      System.out.println((String) value.getValue());
      populate(holder, holder.getStrings(), SensitiveDataHolder::setStrings, id,
          new StringEntry(id, (String) value.getValue()));
      return;
    }
    if (ClassUtils.isAssignable(clazz, Long.class)) {
      populate(holder, holder.getLongs(), SensitiveDataHolder::setLongs, id,
          new LongEntry(id, (Long) value.getValue()));

      return;
    }

    if (ClassUtils.isAssignable(clazz, Double.class)) {
      populate(holder, holder.getDoubles(), SensitiveDataHolder::setDoubles, id,
          new DoubleEntry(id, (Double) value.getValue()));
      return;
    }

    if (ClassUtils.isAssignable(clazz, Boolean.class)) {
      populate(holder, holder.getBooleans(), SensitiveDataHolder::setBooleans, id,
          new BooleanEntry(id, (Boolean) value.getValue()));
      return;
    }
    if (ClassUtils.isAssignable(clazz, byte[].class)) {
      BytesEntry bytesEntry =
          new BytesEntry(value.getIdentifier().getId(), ByteBuffer.wrap((byte[]) value.getValue()));
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
