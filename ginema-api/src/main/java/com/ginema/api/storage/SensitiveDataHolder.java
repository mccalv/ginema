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
package com.ginema.api.storage;

import java.util.LinkedHashMap;

/**
 * 
 * @author mccalv
 *
 */
public class SensitiveDataHolder {

  private LinkedHashMap<SensitiveDataID, SensitiveDataField<?>> map = new LinkedHashMap<>();

  private String domain;
  private SensitiveDataID sensitiveDataId;

  private SensitiveDataHolder() {

  }

  public static SensitiveDataHolder builder() {
    return new SensitiveDataHolder();

  }

  public SensitiveDataHolder withId(SensitiveDataID field) {
    this.sensitiveDataId = field;
    return this;

  }

  public SensitiveDataHolder withDomain(String domain) {
    this.domain = domain;
    return this;

  }

  /**
   * @return the map
   */
  public LinkedHashMap<SensitiveDataID, SensitiveDataField<?>> getMap() {
    return map;
  }

  /**
   * @return the domain
   */
  public String getDomain() {
    return domain;
  }

  /**
   * @return the sensitiveDataId
   */
  public SensitiveDataID getSensitiveDataId() {
    return sensitiveDataId;
  }

  public SensitiveDataHolder withField(SensitiveDataField<?> field) {
    this.map.put(field.getIdentifier(), field);
    return this;

  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SensitiveDataHolder [map=" + map + ", domain=" + domain + ", sensitiveDataId="
        + sensitiveDataId + "]";
  }

}
