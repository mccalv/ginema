/**
 * 
 */
package com.ganemi.core.storage;

import java.util.Date;

import org.junit.Test;

import com.ganemi.core.enricher.SensitiveDataEnricher;

/**
 * Junit test for {@link SensitiveDataEnricher}
 * 
 * @author mccalv
 *
 */
public class SensitiveDateEnricherTest {



  public static class DomainObject {
    private SensitiveDataField<String> name;

    private SensitiveDataField<Date> surname;



  }

  @SensitiveDataRoot(name = "domainObject2")
  public static class DomainObject2 {

    private SensitiveDataID id;

    private SensitiveDataField<String> name;

    private SensitiveDataField<Date> surname;



  }

  @Test(expected = IllegalArgumentException.class)
  public void testEnrichment() {

    SensitiveDataEnricher.enrich(new DomainObject());
  }
}
