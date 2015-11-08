package com.ginema.api.enricher;

import java.util.Date;
import java.util.HashMap;

import org.junit.Test;

import com.ginema.api.avro.DateEntry;
import com.ginema.api.avro.SensitiveDataHolder;
import com.ginema.api.avro.StringEntry;
import com.ginema.api.domain.SimpleDomainObject;
import com.ginema.api.idgenerator.impl.UUIDGenerator;
import com.ginema.api.storage.SensitiveDataField;
import com.ginema.api.storage.SensitiveDataID;

import static org.junit.Assert.assertEquals;

public class SensitiveDataEnricherTest {
  private static final String NAME = "name";
  public static final Date DATE = new Date();
  public static final Date DATE2 = new Date(DATE.getTime() - 100000L);


  private final String ID1 = new UUIDGenerator().generate();
  private final String ID2 = new UUIDGenerator().generate();

  @Test
  public void testEnricher() {

    for (int i = 0; i < 100; i++) {
      checkEnrichment();
    }



  }

  private void checkEnrichment() {
    SensitiveDataHolder holder = new SensitiveDataHolder();
    SensitiveDataID id = new SensitiveDataID();
    holder.setId(id.getId());
    holder.setDates(new HashMap<String, DateEntry>());
    holder.setStrings(new HashMap<String, StringEntry>());
    holder.getDates().put(ID2, new DateEntry(null, DATE.getTime()));
    holder.getStrings().put(ID1, new StringEntry(null, NAME));;

    SimpleDomainObject s = new SimpleDomainObject();
    s.setId(id);
    s.setName(new SensitiveDataField<String>(ID1, null));
    s.setDate(new SensitiveDataField<Date>(ID2, null));

    long time = System.nanoTime();
    SensitiveDataEnricher.enrich(holder, s);
    time = System.nanoTime() - time;
    // System.out.println("NanoSeconds:" +time);
    assertEquals(s.getName().getValue(), NAME);
    assertEquals(s.getDate().getValue(), DATE);
  }
}
