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

import com.ganemi.core.reflection.ReflectionUtils;
import com.ganemi.core.storage.SensitiveDataHolder;
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
    if (!ReflectionUtils.isAnnotatedWith(o, SensitiveDataRoot.class)) {
      throw new IllegalArgumentException("To Enrich Object it has to be marked with "
          + SensitiveDataRoot.class.getName() + " and contains at least one field annotated with ");
    }
    boolean asId = false;
    for (Field f : o.getClass().getDeclaredFields()) {
      // ReflectionUtils.isAssignableFrom(c, allowedClasses), allowedClasses)f.getType().
    }
    return null;

  }

}
