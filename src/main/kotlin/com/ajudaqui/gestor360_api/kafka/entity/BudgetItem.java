/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.ajudaqui.gestor360_api.kafka.entity;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class BudgetItem extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 5085930566984618394L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"BudgetItem\",\"namespace\":\"com.ajudaqui.gestor360_api.kafka.entity\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"brand\",\"type\":\"string\"},{\"name\":\"quantity\",\"type\":\"double\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<BudgetItem> ENCODER =
      new BinaryMessageEncoder<BudgetItem>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<BudgetItem> DECODER =
      new BinaryMessageDecoder<BudgetItem>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<BudgetItem> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<BudgetItem> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<BudgetItem>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this BudgetItem to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a BudgetItem from a ByteBuffer. */
  public static BudgetItem fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.CharSequence name;
  @Deprecated public java.lang.CharSequence brand;
  @Deprecated public double quantity;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public BudgetItem() {}

  /**
   * All-args constructor.
   * @param name The new value for name
   * @param brand The new value for brand
   * @param quantity The new value for quantity
   */
  public BudgetItem(java.lang.CharSequence name, java.lang.CharSequence brand, java.lang.Double quantity) {
    this.name = name;
    this.brand = brand;
    this.quantity = quantity;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return name;
    case 1: return brand;
    case 2: return quantity;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: name = (java.lang.CharSequence)value$; break;
    case 1: brand = (java.lang.CharSequence)value$; break;
    case 2: quantity = (java.lang.Double)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'name' field.
   * @return The value of the 'name' field.
   */
  public java.lang.CharSequence getName() {
    return name;
  }

  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.CharSequence value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'brand' field.
   * @return The value of the 'brand' field.
   */
  public java.lang.CharSequence getBrand() {
    return brand;
  }

  /**
   * Sets the value of the 'brand' field.
   * @param value the value to set.
   */
  public void setBrand(java.lang.CharSequence value) {
    this.brand = value;
  }

  /**
   * Gets the value of the 'quantity' field.
   * @return The value of the 'quantity' field.
   */
  public java.lang.Double getQuantity() {
    return quantity;
  }

  /**
   * Sets the value of the 'quantity' field.
   * @param value the value to set.
   */
  public void setQuantity(java.lang.Double value) {
    this.quantity = value;
  }

  /**
   * Creates a new BudgetItem RecordBuilder.
   * @return A new BudgetItem RecordBuilder
   */
  public static com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder newBuilder() {
    return new com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder();
  }

  /**
   * Creates a new BudgetItem RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new BudgetItem RecordBuilder
   */
  public static com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder newBuilder(com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder other) {
    return new com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder(other);
  }

  /**
   * Creates a new BudgetItem RecordBuilder by copying an existing BudgetItem instance.
   * @param other The existing instance to copy.
   * @return A new BudgetItem RecordBuilder
   */
  public static com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder newBuilder(com.ajudaqui.gestor360_api.kafka.entity.BudgetItem other) {
    return new com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder(other);
  }

  /**
   * RecordBuilder for BudgetItem instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<BudgetItem>
    implements org.apache.avro.data.RecordBuilder<BudgetItem> {

    private java.lang.CharSequence name;
    private java.lang.CharSequence brand;
    private double quantity;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.brand)) {
        this.brand = data().deepCopy(fields()[1].schema(), other.brand);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.quantity)) {
        this.quantity = data().deepCopy(fields()[2].schema(), other.quantity);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing BudgetItem instance
     * @param other The existing instance to copy.
     */
    private Builder(com.ajudaqui.gestor360_api.kafka.entity.BudgetItem other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.brand)) {
        this.brand = data().deepCopy(fields()[1].schema(), other.brand);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.quantity)) {
        this.quantity = data().deepCopy(fields()[2].schema(), other.quantity);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'name' field.
      * @return The value.
      */
    public java.lang.CharSequence getName() {
      return name;
    }

    /**
      * Sets the value of the 'name' field.
      * @param value The value of 'name'.
      * @return This builder.
      */
    public com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder setName(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.name = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'name' field has been set.
      * @return True if the 'name' field has been set, false otherwise.
      */
    public boolean hasName() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'name' field.
      * @return This builder.
      */
    public com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder clearName() {
      name = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'brand' field.
      * @return The value.
      */
    public java.lang.CharSequence getBrand() {
      return brand;
    }

    /**
      * Sets the value of the 'brand' field.
      * @param value The value of 'brand'.
      * @return This builder.
      */
    public com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder setBrand(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.brand = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'brand' field has been set.
      * @return True if the 'brand' field has been set, false otherwise.
      */
    public boolean hasBrand() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'brand' field.
      * @return This builder.
      */
    public com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder clearBrand() {
      brand = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'quantity' field.
      * @return The value.
      */
    public java.lang.Double getQuantity() {
      return quantity;
    }

    /**
      * Sets the value of the 'quantity' field.
      * @param value The value of 'quantity'.
      * @return This builder.
      */
    public com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder setQuantity(double value) {
      validate(fields()[2], value);
      this.quantity = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'quantity' field has been set.
      * @return True if the 'quantity' field has been set, false otherwise.
      */
    public boolean hasQuantity() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'quantity' field.
      * @return This builder.
      */
    public com.ajudaqui.gestor360_api.kafka.entity.BudgetItem.Builder clearQuantity() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public BudgetItem build() {
      try {
        BudgetItem record = new BudgetItem();
        record.name = fieldSetFlags()[0] ? this.name : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.brand = fieldSetFlags()[1] ? this.brand : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.quantity = fieldSetFlags()[2] ? this.quantity : (java.lang.Double) defaultValue(fields()[2]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<BudgetItem>
    WRITER$ = (org.apache.avro.io.DatumWriter<BudgetItem>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<BudgetItem>
    READER$ = (org.apache.avro.io.DatumReader<BudgetItem>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}