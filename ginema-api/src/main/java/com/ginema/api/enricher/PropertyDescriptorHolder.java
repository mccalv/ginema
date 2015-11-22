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
package com.ginema.api.enricher;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * This class mantains the cache and the descriptors for property methods
 * 
 * @author mccalv
 *
 */
public class PropertyDescriptorHolder {

  /* Cached map for property description and getters */
  public static Map<Class<?>, PropertyDescriptor[]> classPropertyDescriptors =
      java.util.Collections.synchronizedMap(new HashMap<Class<?>, PropertyDescriptor[]>());
  public static Map<PropertyKey<Class<?>, String>, Method> propertyGetters =
      java.util.Collections.synchronizedMap(new HashMap<PropertyKey<Class<?>, String>, Method>());


  /**
   * A pair of Class and property name used as key from the property getters map
   * 
   * @author mccalv
   *
   * @param <Class> The Class to which the property belogns
   * @param <String>The property name
   */

  @SuppressWarnings("hiding")
  public static class PropertyKey<Class, String> {
    private Class clazz;
    private String methodName;

    public PropertyKey(Class clazz, String propertyName) {

      this.clazz = clazz;
      this.methodName = propertyName;
    }

    /**
     * @return the clazz
     */
    public Class getClazz() {
      return clazz;
    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
      return methodName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
      result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
      return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      PropertyKey other = (PropertyKey) obj;
      if (clazz == null) {
        if (other.clazz != null)
          return false;
      } else if (!clazz.equals(other.clazz))
        return false;
      if (methodName == null) {
        if (other.methodName != null)
          return false;
      } else if (!methodName.equals(other.methodName))
        return false;
      return true;
    }

  }

  static PropertyDescriptor[] getPropertyDescriptor(Class<?> clazz) {
    if (!classPropertyDescriptors.containsKey(clazz)) {
      try {

        PropertyDescriptor desc[] =
            Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();
        classPropertyDescriptors.put(clazz, desc);
        
        return desc;
      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
    }
    return classPropertyDescriptors.get(clazz);
  }

  static Method getGetterMethod(Class<?> clazz, String name) throws IntrospectionException {
    PropertyKey<Class<?>, String> p = new PropertyKey<Class<?>, String>(clazz, name);
    if (!propertyGetters.containsKey(p)) {
      PropertyDescriptor desc[] = getPropertyDescriptor(clazz);
      for (PropertyDescriptor d : desc) {
        if (d.getName().equals(name)) {
        //  return d.getReadMethod();
          propertyGetters.put(p, d.getReadMethod());
        }
      }
    }

    return propertyGetters.get(p);
  }

}
