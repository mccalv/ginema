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
/**
 * 
 */
package com.ginema.api.storage;

import java.util.Date;

import org.junit.Test;

import com.ginema.api.enricher.SensitiveDataEnricher;
import com.ginema.api.storage.SensitiveDataField;
import com.ginema.api.storage.SensitiveDataID;
import com.ginema.api.storage.SensitiveDataRoot;

/**
 * Junit test for {@link SensitiveDataEnricher}
 * 
 * @author mccalv
 *
 */
public class SensitiveDateEnricherTest {



  public static class DomainObject {
    private SensitiveDataField<String> name;

    private SensitiveDataField<Date> surname;



  }

  @SensitiveDataRoot(name = "domainObject2")
  public static class DomainObject2 {

    private SensitiveDataID id;

    private SensitiveDataField<String> name;

    private SensitiveDataField<Date> surname;



  }

  @Test(expected = IllegalArgumentException.class)
  public void testEnrichment() {

    SensitiveDataEnricher.enrich(new DomainObject());
  }
}
