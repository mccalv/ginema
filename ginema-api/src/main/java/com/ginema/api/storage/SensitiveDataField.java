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
package com.ginema.api.storage;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ginema.api.configuration.Configuration;
import com.ginema.api.idgenerator.IDGenerator;
import com.ginema.api.reflection.ReflectionUtils;

/**
 * A sensitive data field is the holder for a sensitive data of a supported types:
 * <ul>
 * <li>String
 * <li>Date
 * <li>Number
 * <li>bytes[].
 * </ul>
 * The holder contains the wrapped sensitive value and a {@link SensitiveDataID} which contains the
 * identifier
 * 
 * @author mccalv
 *
 */
public class SensitiveDataField<T> {

  @SuppressWarnings("rawtypes")
  public static final Set<Class> SUPPORTED_FIELD_TYPES = new HashSet<Class>() {
    private static final long serialVersionUID = 1167902970132800721L;

    {
      add(String.class);
      add(Date.class);
      add(Boolean.class);
      // Supported number
      add(Integer.class);
      add(Long.class);
      add(Double.class);
      add(Float.class);
      add(byte[].class);

    }
  };


  private SensitiveDataID identifier;
  private T value;
  private String name;

  public SensitiveDataField(T value) {
    this(null, value);
  }



  /**
   * @return the name
   */
  public String getName() {
    return name;
  }



  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }



  public SensitiveDataField(String identifier, T value) {
    if (value!= null && !ReflectionUtils.isAssignableFrom(value.getClass(), SUPPORTED_FIELD_TYPES)) {
      throw new IllegalArgumentException("Type: " + value.getClass().getName()
          + " not supported. Sensitive data can be only of types:"
          + Arrays.toString(SUPPORTED_FIELD_TYPES.toArray()));
    }

    IDGenerator idGenerator = Configuration.getIDGenerator();
    // Generates the Id or verify it and assign to value
    this.identifier = identifier == null ? new SensitiveDataID()
        : new SensitiveDataID(idGenerator.fromString(identifier));

   if(value!=null) this.value = value;
  }



  /**
   * @return the identifier
   */
  public SensitiveDataID getIdentifier() {
    return identifier;
  }

  /**
   * @return the value
   */
  public T getValue() {
    return value;
  }

  /**
   * @param value the value to set
   */
  public void setValue(T value) {
    this.value = value;
  }



  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SensitiveDataField [identifier=" + identifier + ", value=" + value + "]";
  }

}
