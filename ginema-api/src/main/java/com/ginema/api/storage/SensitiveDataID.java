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

import com.ginema.api.configuration.Configuration;
import com.ginemalapi.idgenerator.IDGenerator;

/**
 * The holder for a holding the unique identifier or a SensitiveData. The strategy for the
 * generation of the id it implemented by {@link IDGenerator}
 * 
 * @author mccalv
 *
 */
public class SensitiveDataID {



  private String id;

  public String getId() {
    return id;
  }

  /**
   * Check the passedId and assign it to the local String
   * 
   * @Throw {@link IllegalArgumentException} in case the id is not conform to the
   *        IdGenerator.fromString used
   * @param id
   */
  public SensitiveDataID(String id) {
    this.id = Configuration.getIDGenerator().fromString(id);

  }

  /**
   * Generate a new Id based on the ID Strategy register
   */
  public SensitiveDataID() {
    this.id = Configuration.getIDGenerator().generate();
        //Configuration.getIDGenerator().generate().getId();
  
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SensitiveDataID [id=" + id + "]";
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SensitiveDataID other = (SensitiveDataID) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
}
