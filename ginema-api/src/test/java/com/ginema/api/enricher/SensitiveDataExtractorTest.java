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
  public static final String NAME1 = "name";

  public static final String SURNAME1 = "surname";


  public static final String NAME2 = "name2";
  public static final String SURNAME2 = "surname2";

  public static final String NAME3 = "name3";
  public static final String SURNAME3 = "surname3";

  @Test
  public void testEnricherSimpleObject() {

    SensitiveDataField<String> name = new SensitiveDataField<String>(NAME1);
    SensitiveDataField<String> surname = new SensitiveDataField<String>(SURNAME1);

    SensitiveDataField<String> name2 = new SensitiveDataField<String>(NAME2);
    SensitiveDataField<String> surname2 = new SensitiveDataField<String>(SURNAME2);

    SensitiveDataField<String> name3 = new SensitiveDataField<String>(NAME3);
    SensitiveDataField<String> surname3 = new SensitiveDataField<String>(SURNAME3);


    SensitiveDataField<Date> date = new SensitiveDataField<Date>(DATE);
    SensitiveDataField<Date> date2 = new SensitiveDataField<Date>(DATE2);

    // long time = System.currentTimeMillis();

    for (int i = 0; i < 100; i++) {
      SimpleDomainObject s =
          buildObject(name, surname, name2, surname2, name3, surname3, date, date2);


      com.ginema.api.avro.SensitiveDataHolder enrich =
          new SensitiveDataExtractor().extractSensitiveData(s);
      assertNotNull(NAME1, enrich.getStrings().get(name.getIdentifier().getId()));
      assertNotNull(SURNAME1, enrich.getStrings().get(surname.getIdentifier().getId()));
      assertNotNull(NAME3, enrich.getStrings().get(name2.getIdentifier().getId()));
      assertNotNull(SURNAME2, enrich.getStrings().get(surname2.getIdentifier().getId()));
      assertNotNull(NAME3, enrich.getStrings().get(name3.getIdentifier().getId()));
      assertNotNull(SURNAME3, enrich.getStrings().get(surname3.getIdentifier().getId()));
      assertEquals(Long.valueOf(DATE.getTime()),
          enrich.getDates().get(date.getIdentifier().getId()).getValue());
      assertEquals(Long.valueOf(DATE2.getTime()),
          enrich.getDates().get(date2.getIdentifier().getId()).getValue());

    }
  }

  private SimpleDomainObject buildObject(SensitiveDataField<String> name,
      SensitiveDataField<String> surname, SensitiveDataField<String> name2,
      SensitiveDataField<String> surname2, SensitiveDataField<String> name3,
      SensitiveDataField<String> surname3, SensitiveDataField<Date> date,
      SensitiveDataField<Date> date2) {
    SimpleDomainObject s = new SimpleDomainObject();
    s.setId(new SensitiveDataID());

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
    return s;
  }

}
