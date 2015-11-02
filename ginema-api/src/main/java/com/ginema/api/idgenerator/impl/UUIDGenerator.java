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
package com.ginema.api.idgenerator.impl;

import java.util.UUID;

import com.ginemalapi.idgenerator.IDGenerator;

/**
 * Implementation based on java UUID generator
 * 
 * @author mccalv
 *
 */
public class UUIDGenerator implements IDGenerator {

  /*
   * (non-Javadoc)
   * 
   * @see com.ganemi.core.id.IDGenerator#generate()
   */
  @Override
  public String generate() {
    return UUID.randomUUID().toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ganemi.core.id.IDGenerator#fromString(java.lang.String)
   */
  @Override
  public String fromString(String s) {
    // TODO Auto-generated method stub
    return UUID.fromString(s).toString();
  }

}
