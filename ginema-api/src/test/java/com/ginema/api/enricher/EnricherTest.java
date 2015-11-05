/*******************************************************************************
 * 
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
package com.ginema.api.enricher;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.ginema.api.domain.SimpleDomainObject;
import com.ginema.api.storage.SensitiveDataField;
import com.ginema.api.storage.SensitiveDataID;

public class EnricherTest {



  @Test
  public void testEnricherSimpleObject() {
    SimpleDomainObject s = new SimpleDomainObject();
    s.setId(new SensitiveDataID());
    SensitiveDataField<String> name = new SensitiveDataField<String>("name");
    SensitiveDataField<String> surname = new SensitiveDataField<String>("surname");
    
    SensitiveDataField<String> name2 = new SensitiveDataField<String>("name2");
    SensitiveDataField<String> surname2 = new SensitiveDataField<String>("surname2");
    
    SensitiveDataField<String> name3 = new SensitiveDataField<String>("name3");
    SensitiveDataField<String> surname3 = new SensitiveDataField<String>("surname3");

    s.setName(name);
    s.setSurnname(surname);
    
    SimpleDomainObject s2 = new SimpleDomainObject();
    s2.setName(name2);
    s2.setSurnname(surname2);
  
    SimpleDomainObject s3 = new SimpleDomainObject();
    s3.setName(name3);
    s3.setSurnname(surname3);
  
    s.addChildren(s2);
    s.addChildren(s3);
    com.ginema.api.avro.SensitiveDataHolder enrich = SensitiveDataEnricher.enrich(s);
    assertNotNull(enrich.getStrings().get(name.getIdentifier().getId()));
    System.out.println(enrich);
  }

}
