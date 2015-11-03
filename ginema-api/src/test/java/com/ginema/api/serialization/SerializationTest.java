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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import com.ginema.api.avro.DateEntry;
import com.ginema.api.avro.SensitiveDataHolder;
import com.ginema.api.avro.util.SchemaHelper;

/**
 * A base test for serialization in Avro
 * 
 * @author mccalv
 *
 */
public class SerializationTest {
  private final static File AVRO_SERIALIZED_FILE = new File("sensitiveDataHolder.avro");
  private final static Date DATE = new Date();
  private final static String ID_GENERATED = UUID.randomUUID().toString();
  private final static String ID_HOLDER = UUID.randomUUID().toString();
  private final static String DOMAIN = "domain";

  @Test
  public void testSchemaBuilderAndJsonShouldBeEqual() throws Exception {
  
      assertEquals(SchemaHelper.getGinemaSchemaFromClassPath(), SchemaHelper.getGinemaSchema());

  }

  /** Test the serialization and serialization using Apache Avro */
  @Test
  public void testSerialization() throws Exception {
    // Deserialize users from disk
    SensitiveDataHolder sensitiveDataHolder = new SensitiveDataHolder();
    sensitiveDataHolder.setId(ID_HOLDER);
    sensitiveDataHolder.setDomain(DOMAIN);
    sensitiveDataHolder.setDates(new HashMap<String, DateEntry>());
    sensitiveDataHolder.getDates().put(ID_GENERATED, new DateEntry(ID_GENERATED, DATE.getTime()));
    DatumWriter<SensitiveDataHolder> userDatumWriter =
        new SpecificDatumWriter<SensitiveDataHolder>(SensitiveDataHolder.class);
    DataFileWriter<SensitiveDataHolder> dataFileWriter =
        new DataFileWriter<SensitiveDataHolder>(userDatumWriter);
    dataFileWriter.create(sensitiveDataHolder.getSchema(), AVRO_SERIALIZED_FILE);

    dataFileWriter.append(sensitiveDataHolder);
    dataFileWriter.close();


    DatumReader<SensitiveDataHolder> userDatumReader =
        new SpecificDatumReader<SensitiveDataHolder>(SensitiveDataHolder.class);
    DataFileReader<SensitiveDataHolder> dataFileReader =
        new DataFileReader<SensitiveDataHolder>(AVRO_SERIALIZED_FILE, userDatumReader);
    SensitiveDataHolder sensitideDataHolder = null;
    while (dataFileReader.hasNext()) {
      // Reuse user object by passing it to next(). This saves us from
      // allocating and garbage collecting many objects for files with
      // many items.
      sensitideDataHolder = dataFileReader.next();
      assertEquals(ID_HOLDER, sensitideDataHolder.getId().toString());
      assertEquals(DOMAIN, sensitideDataHolder.getDomain().toString());

      assertEquals(DATE, new Date(sensitideDataHolder.getDates().get(ID_GENERATED).getValue()));

      System.out.println(sensitideDataHolder);
    }

    AVRO_SERIALIZED_FILE.delete();
    dataFileReader.close();

  }

}
