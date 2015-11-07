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

public class SensitiveDataEnricherTest {
  public static final Date DATE = new Date();
  public static final Date DATE2 = new Date(DATE.getTime() - 100000L);

  @Test
  public void testEnricher() {

    UUIDGenerator generator = new UUIDGenerator();
    String id1 = generator.generate();
    String id2 = generator.generate();

    SensitiveDataID id = new SensitiveDataID();

    SensitiveDataHolder holder = new SensitiveDataHolder();
    holder.setId(id.getId());
    holder.setDates(new HashMap<String, DateEntry>());
    holder.setStrings(new HashMap<String, StringEntry>());

    holder.getDates().put(id1, new DateEntry(null, DATE.getTime()));
    holder.getStrings().put(id2, new StringEntry(null, "name"));;

    SimpleDomainObject s = new SimpleDomainObject();
    s.setId(id);
    s.setName(new SensitiveDataField<String>(id2, null));
    s.setDate(new SensitiveDataField<Date>(id1, null));
    SensitiveDataEnricher.enrich(holder, s);

  }
}
