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
package com.ginema.api.domain;

import java.util.ArrayList;
import java.util.List;

import com.ginema.api.storage.SensitiveDataField;
import com.ginema.api.storage.SensitiveDataID;
import com.ginema.api.storage.SensitiveDataRoot;

@SensitiveDataRoot(name = "aSimpleObject")
public class SimpleDomainObject {

  private SensitiveDataID id;
  private SensitiveDataField<String> name;
  private SensitiveDataField<String> surnname;
  private SimpleDomainObject child;
  private List<SimpleDomainObject> children = new ArrayList<SimpleDomainObject>();


  /**
   * @return the child
   */
  public SimpleDomainObject getChild() {
    return child;
  }

  /**
   * @param child the child to set
   */
  public void setChild(SimpleDomainObject child) {
    this.child = child;
  }

  /**
   * @return the id
   */
  public SensitiveDataID getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(SensitiveDataID id) {
    this.id = id;
  }

  /**
   * @return the name
   */
  public SensitiveDataField<String> getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(SensitiveDataField<String> name) {
    this.name = name;
  }

  /**
   * @return the surnname
   */
  public SensitiveDataField<String> getSurnname() {
    return surnname;
  }

  /**
   * @return the surnname
   */
  public void addChildren(SimpleDomainObject simpleObject) {
    children.add(simpleObject);
  }

  /**
   * @param surnname the surnname to set
   */
  public void setSurnname(SensitiveDataField<String> surnname) {
    this.surnname = surnname;
  }

}
