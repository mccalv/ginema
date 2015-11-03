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
package com.ginema.api.avro.util;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

/**
 * Utility class to manage the Avro Schema
 * 
 * @author mccalv
 *
 */
public class SchemaHelper {
  private static final String VALUE = "value";

  /**
   * Build the schema and convert it to JSON. The same must be the same of the oone in "avro" folder
   * 
   * @return
   */
 public static String toJson() {
    // @formatter:off
    Schema schema = SchemaBuilder.record("SensitiveDataHolder").
        namespace("com.ginema.api.avro")
        .fields()
        .name("id").type().nullable().stringType().noDefault()
        .name("domain").type().nullable().stringType().noDefault()
        
             
        .name("dates").type().nullable().map().values().
            record("DateEntry").fields()
             .nullableString("name","")
             .optionalLong(VALUE)
        .endRecord().noDefault()
        

        .name("strings").type().nullable().map().values().
          record("StringEntry").fields()
            .nullableString("name","")
            .optionalString(VALUE)
        .endRecord().noDefault()
    
        .name("longs").type().nullable().map().values().
        record("LongEntry").fields()
          .nullableString("name","")
          .optionalLong(VALUE)
      .endRecord().noDefault()
  
      .name("integers").type().nullable().map().values().
      record("IntegerEntry").fields()
        .nullableString("name","")
        .optionalInt(VALUE)
      .endRecord().noDefault()

      .name("floats").type().nullable().map().values().
      record("FloatEntry").fields()
        .nullableString("name","")
        .optionalFloat(VALUE)
      .endRecord().noDefault()
      
      .name("doubles").type().nullable().map().values().
      record("DoubleEntry").fields()
        .nullableString("name","")
        .optionalDouble(VALUE)
      .endRecord().noDefault()

      .name("bytes").type().nullable().map().values().
      record("BytesEntry").fields()
        .nullableString("name","")
        .optionalBytes(VALUE)
      .endRecord().noDefault()
      
      
      .name("booleans").type().nullable().map().values().
      record("BooleanEntry").fields()
        .nullableString("name","")
        .optionalBoolean(VALUE)
      .endRecord().noDefault()
           
        
        .endRecord();
    // @formatter:on
    return (schema.toString());

  }
}
