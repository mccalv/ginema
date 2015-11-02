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
package com.ganemi.core.enricher;

import org.junit.Test;

import com.ganemi.core.domain.SimpleDomainObject;
import com.ganemi.core.storage.SensitiveDataField;
import com.ganemi.core.storage.SensitiveDataHolder;
import com.ganemi.core.storage.SensitiveDataID;

public class EnricherTest {
  @Test
  public void testId() {
    new SensitiveDataID();

  }

  @Test
  public void testEnricherSimpleObject() {
    SimpleDomainObject s = new SimpleDomainObject();
    s.setId(new SensitiveDataID());
    s.setName(new SensitiveDataField<String>("name"));
    s.setSurnname(new SensitiveDataField<String>("surname"));
    s.setChild(s);
    SensitiveDataHolder enrich = SensitiveDataEnricher.enrich(s);
    System.out.println(enrich);
  }
}
