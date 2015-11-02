/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.ginema.avro;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class LongEntry extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"LongEntry\",\"namespace\":\"com.ginema.avro\",\"fields\":[{\"name\":\"n\",\"type\":[\"string\",\"null\"]},{\"name\":\"v\",\"type\":\"long\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence n;
  @Deprecated public long v;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public LongEntry() {}

  /**
   * All-args constructor.
   */
  public LongEntry(java.lang.CharSequence n, java.lang.Long v) {
    this.n = n;
    this.v = v;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return n;
    case 1: return v;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: n = (java.lang.CharSequence)value$; break;
    case 1: v = (java.lang.Long)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'n' field.
   */
  public java.lang.CharSequence getN() {
    return n;
  }

  /**
   * Sets the value of the 'n' field.
   * @param value the value to set.
   */
  public void setN(java.lang.CharSequence value) {
    this.n = value;
  }

  /**
   * Gets the value of the 'v' field.
   */
  public java.lang.Long getV() {
    return v;
  }

  /**
   * Sets the value of the 'v' field.
   * @param value the value to set.
   */
  public void setV(java.lang.Long value) {
    this.v = value;
  }

  /** Creates a new LongEntry RecordBuilder */
  public static com.ginema.avro.LongEntry.Builder newBuilder() {
    return new com.ginema.avro.LongEntry.Builder();
  }
  
  /** Creates a new LongEntry RecordBuilder by copying an existing Builder */
  public static com.ginema.avro.LongEntry.Builder newBuilder(com.ginema.avro.LongEntry.Builder other) {
    return new com.ginema.avro.LongEntry.Builder(other);
  }
  
  /** Creates a new LongEntry RecordBuilder by copying an existing LongEntry instance */
  public static com.ginema.avro.LongEntry.Builder newBuilder(com.ginema.avro.LongEntry other) {
    return new com.ginema.avro.LongEntry.Builder(other);
  }
  
  /**
   * RecordBuilder for LongEntry instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<LongEntry>
    implements org.apache.avro.data.RecordBuilder<LongEntry> {

    private java.lang.CharSequence n;
    private long v;

    /** Creates a new Builder */
    private Builder() {
      super(com.ginema.avro.LongEntry.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.ginema.avro.LongEntry.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.n)) {
        this.n = data().deepCopy(fields()[0].schema(), other.n);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.v)) {
        this.v = data().deepCopy(fields()[1].schema(), other.v);
        fieldSetFlags()[1] = true;
      }
    }
    
    /** Creates a Builder by copying an existing LongEntry instance */
    private Builder(com.ginema.avro.LongEntry other) {
            super(com.ginema.avro.LongEntry.SCHEMA$);
      if (isValidValue(fields()[0], other.n)) {
        this.n = data().deepCopy(fields()[0].schema(), other.n);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.v)) {
        this.v = data().deepCopy(fields()[1].schema(), other.v);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'n' field */
    public java.lang.CharSequence getN() {
      return n;
    }
    
    /** Sets the value of the 'n' field */
    public com.ginema.avro.LongEntry.Builder setN(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.n = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'n' field has been set */
    public boolean hasN() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'n' field */
    public com.ginema.avro.LongEntry.Builder clearN() {
      n = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'v' field */
    public java.lang.Long getV() {
      return v;
    }
    
    /** Sets the value of the 'v' field */
    public com.ginema.avro.LongEntry.Builder setV(long value) {
      validate(fields()[1], value);
      this.v = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'v' field has been set */
    public boolean hasV() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'v' field */
    public com.ginema.avro.LongEntry.Builder clearV() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public LongEntry build() {
      try {
        LongEntry record = new LongEntry();
        record.n = fieldSetFlags()[0] ? this.n : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.v = fieldSetFlags()[1] ? this.v : (java.lang.Long) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
