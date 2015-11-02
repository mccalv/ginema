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
package com.ginemalapi.idgenerator;

import com.ginema.api.storage.SensitiveDataID;

/**
 * An interface to generate SensitivaData Unique ID. The implementor define the strategy for the
 * unique id generation
 *
 * 
 * @author mccalv
 *
 */
public interface IDGenerator {
  /**
   * Generates a new SensitiveDataID
   * 
   * @return
   */
  String generate();

  /**
   * Checks the validity of the passed String and returns the generated object
   * 
   * @param s
   * @return
   */
  String fromString(String s);

}
