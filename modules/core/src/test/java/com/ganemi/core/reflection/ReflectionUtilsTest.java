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

package com.ganemi.core.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.apache.avro.reflect.Nullable;
import org.apache.commons.lang.ClassUtils;
import org.junit.Test;

import com.ganemi.core.storage.SensitiveDataField;
import com.ganemi.core.storage.SensitiveDataID;
import com.ganemi.core.storage.SensitiveDataRoot;

/**
 * {@link ReflectionUtils} unit test
 * 
 * @author mccalv
 *
 */
@SuppressWarnings("unused")
public class ReflectionUtilsTest {
  @SensitiveDataRoot(name = "aObject")
  public static class AnObject {


    private String id;

    private String aField;


    private String surnname;
    public AnObject s;

  }

  @Test
  public void testShouldIsAssignableFrom() {
    assertTrue(ReflectionUtils.isAssignableFrom(SensitiveDataID.class, SensitiveDataID.class));

  }

  @Test
  public void testShouldGetAnnotatedFields() {

    assertEquals(0,
        ReflectionUtils.getAnnotatedFields(new AnObject(), SuppressWarnings.class).count());
    assertEquals(0, ReflectionUtils.getAnnotatedFields(new AnObject(), Nullable.class).count());

  }

  @Test
  public void testShouldGetIsAssignable() {
    assertTrue(ClassUtils.isAssignable(Integer.class, Number.class, true));
    assertTrue(
        ReflectionUtils.isAssignableFrom(Long.class, SensitiveDataField.SUPPORTED_FIELD_TYPES));
    assertTrue(
        ReflectionUtils.isAssignableFrom(Integer.class, SensitiveDataField.SUPPORTED_FIELD_TYPES));
    assertTrue(
        ReflectionUtils.isAssignableFrom(Short.class, SensitiveDataField.SUPPORTED_FIELD_TYPES));
    assertTrue(
        ReflectionUtils.isAssignableFrom(byte.class, SensitiveDataField.SUPPORTED_FIELD_TYPES));
    assertTrue(
        ReflectionUtils.isAssignableFrom(int.class, SensitiveDataField.SUPPORTED_FIELD_TYPES));
    assertTrue(
        ReflectionUtils.isAssignableFrom(byte[].class, SensitiveDataField.SUPPORTED_FIELD_TYPES));

  }

  @Test
  public void testShouldTestIsACollection() {
    assertTrue(ReflectionUtils.isACollection(new ArrayList<String>()));
    assertTrue(ReflectionUtils.isACollection(SensitiveDataField.SUPPORTED_FIELD_TYPES));
    assertFalse(ReflectionUtils.isACollection(new String[] {}));

  }

  @Test
  public void testShouldTestIsPrimitive() {
    assertTrue(ReflectionUtils.isPrimitive(1));

    assertTrue(ReflectionUtils.isPrimitive(1L));
    assertFalse(ReflectionUtils.isPrimitive(new Date()));
    assertFalse(ReflectionUtils.isACollection(new String[] {}));
    assertFalse(ReflectionUtils.isPrimitive("a String"));

  }

  @Test
  public void testShouldTestIfSensitiveDataRoot() {
    assertTrue(ReflectionUtils.isAnnotatedWith(new AnObject(), SensitiveDataRoot.class));
    assertFalse(ReflectionUtils.isAnnotatedWith(this, SensitiveDataRoot.class));
  }
}
