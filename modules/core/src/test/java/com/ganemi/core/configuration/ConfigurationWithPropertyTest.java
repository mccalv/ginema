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
package com.ganemi.core.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.ganemi.core.configuration.Configuration;
import com.ganemi.core.idgenerator.impl.UUIDGenerator;
import com.ganemi.core.storage.SensitiveDataID;

/**
 * Test the {@link Configuration} when no system property is defined, then it should fall back to
 * the {@link UUIDGenerator}
 * 
 * @author mccalv
 *
 */
public class ConfigurationWithPropertyTest {
  @Test
  public void shouldApplyConfigurationTest() {
    SensitiveDataID generate = Configuration.getIDGenerator().generate();
    assertNotNull(generate.getId());
    assertEquals(generate, Configuration.getIDGenerator().fromString(generate.getId()));
    System.setProperty("com.ganemi.idgenerator", "wrongValue"); // Already
                                                                // set
    assertEquals(generate, Configuration.getIDGenerator().fromString(generate.getId()));

  }

}
