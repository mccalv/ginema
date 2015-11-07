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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import com.ginema.api.domain.SimpleDomainObject;
import com.ginema.api.storage.SensitiveDataField;
import com.ginema.api.storage.SensitiveDataID;

public class SensitiveDataExtractorTest {


  public static final Date DATE = new Date();
  public static final Date DATE2 = new Date(DATE.getTime() - 100000L);

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


    SensitiveDataField<Date> date = new SensitiveDataField<Date>(DATE);
    SensitiveDataField<Date> date2 = new SensitiveDataField<Date>(DATE2);
    s.setDate(date);

    s.setName(name);
    s.setSurnname(surname);

    SimpleDomainObject s2 = new SimpleDomainObject();
    s2.setName(name2);
    s2.setSurnname(surname2);
    s2.setDate(date2);

    SimpleDomainObject s3 = new SimpleDomainObject();
    s3.setName(name3);
    s3.setSurnname(surname3);

    s.addChildren(s2);
    s.addChildren(s3);
   // long time = System.currentTimeMillis();
    com.ginema.api.avro.SensitiveDataHolder enrich = SensitiveDataExtractor.extractSensitiveData(s);
    assertNotNull("name", enrich.getStrings().get(name.getIdentifier().getId()));
    assertNotNull("suname", enrich.getStrings().get(surname.getIdentifier().getId()));
    assertNotNull("name2", enrich.getStrings().get(name2.getIdentifier().getId()));
    assertNotNull("suname2", enrich.getStrings().get(surname2.getIdentifier().getId()));
    assertNotNull("name3", enrich.getStrings().get(name3.getIdentifier().getId()));
    assertNotNull("suname3", enrich.getStrings().get(surname3.getIdentifier().getId()));
    assertEquals(Long.valueOf(DATE.getTime()),
        enrich.getDates().get(date.getIdentifier().getId()).getValue());
    assertEquals(Long.valueOf(DATE2.getTime()),
        enrich.getDates().get(date2.getIdentifier().getId()).getValue());
    long time = System.currentTimeMillis();
    
    System.out.println(enrich);
    System.out.println("Enriching time:" +(System.currentTimeMillis()-time));
    
  }

}
