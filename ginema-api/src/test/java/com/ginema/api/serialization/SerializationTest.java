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
package com.ginema.api.serialization;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

import com.ginema.api.avro.DateEntry;
import com.ginema.api.avro.SensitiveDataHolder;

/**
 * @author mccalv
 *
 */
public class SerializationTest {

  private File file;

  public void testSerialization() throws Exception {
    // Deserialize users from disk
    SensitiveDataHolder sensitiveDataHolder = new SensitiveDataHolder();
    sensitiveDataHolder.setId("id");
    sensitiveDataHolder.setDomain("domain");
    sensitiveDataHolder.getDates().put("dd", new DateEntry(UUID.randomUUID().toString(), new Date().getTime()));
    DatumWriter<SensitiveDataHolder> userDatumWriter =
        new SpecificDatumWriter<SensitiveDataHolder>(SensitiveDataHolder.class);
    DataFileWriter<SensitiveDataHolder> dataFileWriter =
        new DataFileWriter<SensitiveDataHolder>(userDatumWriter);
     dataFileWriter.create(sensitiveDataHolder.getSchema(), new File("users.avro"));
    dataFileWriter.close();



  }

}
