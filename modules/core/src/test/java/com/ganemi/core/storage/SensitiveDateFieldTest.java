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
package com.ganemi.core.storage;

import java.util.Date;
import java.util.HashSet;

import org.junit.Test;

import com.ganemi.core.configuration.Configuration;

public class SensitiveDateFieldTest {

  @Test(expected = IllegalArgumentException.class)
  public void testNotSupportedType() {
    new SensitiveDataField<>("a", new HashSet<String>());
    new SensitiveDataField<>("a", "a");
    new SensitiveDataField<>("a", new Date());
    new SensitiveDataField<>("a", 1L);
    new SensitiveDataField<>("a", 1);
  }

  @Test()
  public void testSupportedType() {
    //Get a correct id
    String id = Configuration.getIDGenerator().generate().getId();
    new SensitiveDataField<String>(id, "a");
    new SensitiveDataField<Date>(id, new Date());
    new SensitiveDataField<Long>(id, 1L);
    new SensitiveDataField<Integer>(id, 1);

  }


}
