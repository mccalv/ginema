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
package com.ginema.api.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.ginema.api.idgenerator.impl.RandomIDGenerator;

/**
 * Test the {@link Configuration} when a system property is defined and the defaul generator is
 * RandomIDGenerator
 * 
 * @author mccalv
 *
 */
public class ConfigurationWithoutPropertyTest {
  @Before
  public void setProperty() {
    System.setProperty("com.ginema.idgenerator",
        "com.ginema.api.idgenerator.impl.RandomIDGenerator"); // Already

  }

  @Test
  public void shouldApplyConfigurationTest() {
    String generated = Configuration.getIDGenerator().generate();
    System.out.println(generated);
    /*
    assertTrue(Configuration.getIDGenerator() instanceof RandomIDGenerator);
    assertNotNull(generated);
    assertEquals(generated, Configuration.getIDGenerator().fromString(generated));
    assertEquals(generated, Configuration.getIDGenerator().fromString(generated));
*/
  }

}
