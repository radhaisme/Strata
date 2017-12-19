/*
 * Copyright (C) 2017 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.etd;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;

import org.joda.beans.Bean;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.ImmutableDefaults;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.product.PortfolioItemSummary;
import com.opengamma.strata.product.ProductType;
import com.opengamma.strata.product.SecurityId;
import com.opengamma.strata.product.SecurityQuantity;
import com.opengamma.strata.product.Trade;
import com.opengamma.strata.product.TradeInfo;
import com.opengamma.strata.product.common.SummarizerUtils;

/**
 * A trade representing an ETD option.
 * <p>
 * A trade in an underlying {@link EtdOptionSecurity}.
 */
@BeanDefinition
public final class EtdOptionTrade
    implements Trade, SecurityQuantity, ImmutableBean, Serializable {

  /**
   * The additional trade information, defaulted to an empty instance.
   * <p>
   * This allows additional information to be attached to the trade.
   */
  @PropertyDefinition(overrideGet = true)
  private final TradeInfo info;
  /**
   * The security that was traded.
   */
  @PropertyDefinition(validate = "notNull")
  private final EtdOptionSecurity security;
  /**
   * The quantity that was traded.
   * <p>
   * This is the number of contracts that were traded.
   * This will be positive if buying and negative if selling.
   */
  @PropertyDefinition(overrideGet = true)
  private final double quantity;
  /**
   * The price that was traded, in decimal form.
   * <p>
   * This is the price agreed when the trade occurred.
   */
  @PropertyDefinition
  private final double price;

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance from trade information, security, quantity and price.
   * 
   * @param tradeInfo  the trade information
   * @param security  the security that was traded
   * @param quantity  the quantity that was traded
   * @param price  the price that was traded
   * @return the trade
   */
  public static EtdOptionTrade of(
      TradeInfo tradeInfo,
      EtdOptionSecurity security,
      double quantity,
      double price) {

    return new EtdOptionTrade(tradeInfo, security, quantity, price);
  }

  @ImmutableDefaults
  private static void applyDefaults(Builder builder) {
    builder.info = TradeInfo.empty();
  }

  //-------------------------------------------------------------------------
  @Override
  public PortfolioItemSummary summarize() {
    // O-ECAG-OGBS-201706-P1.50 x 200, Jun17 P1.50
    String option = security.summaryDescription();
    String description =
        getSecurityId().getStandardId().getValue() + " x " + SummarizerUtils.value(getQuantity()) + ", " + option;
    return SummarizerUtils.summary(this, ProductType.ETD_OPTION, description, getCurrency());
  }

  /**
   * Gets the security identifier.
   * <p>
   * This identifier uniquely identifies the security within the system.
   * 
   * @return the security identifier
   */
  @Override
  public SecurityId getSecurityId() {
    return security.getSecurityId();
  }

  /**
   * Gets the currency of the trade.
   * <p>
   * This is typically the same as the currency of the product.
   * 
   * @return the trading currency
   */
  public Currency getCurrency() {
    return security.getCurrency();
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code EtdOptionTrade}.
   * @return the meta-bean, not null
   */
  public static EtdOptionTrade.Meta meta() {
    return EtdOptionTrade.Meta.INSTANCE;
  }

  static {
    MetaBean.register(EtdOptionTrade.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static EtdOptionTrade.Builder builder() {
    return new EtdOptionTrade.Builder();
  }

  private EtdOptionTrade(
      TradeInfo info,
      EtdOptionSecurity security,
      double quantity,
      double price) {
    JodaBeanUtils.notNull(security, "security");
    this.info = info;
    this.security = security;
    this.quantity = quantity;
    this.price = price;
  }

  @Override
  public EtdOptionTrade.Meta metaBean() {
    return EtdOptionTrade.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the additional trade information, defaulted to an empty instance.
   * <p>
   * This allows additional information to be attached to the trade.
   * @return the value of the property
   */
  @Override
  public TradeInfo getInfo() {
    return info;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the security that was traded.
   * @return the value of the property, not null
   */
  public EtdOptionSecurity getSecurity() {
    return security;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the quantity that was traded.
   * <p>
   * This is the number of contracts that were traded.
   * This will be positive if buying and negative if selling.
   * @return the value of the property
   */
  @Override
  public double getQuantity() {
    return quantity;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the price that was traded, in decimal form.
   * <p>
   * This is the price agreed when the trade occurred.
   * @return the value of the property
   */
  public double getPrice() {
    return price;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      EtdOptionTrade other = (EtdOptionTrade) obj;
      return JodaBeanUtils.equal(info, other.info) &&
          JodaBeanUtils.equal(security, other.security) &&
          JodaBeanUtils.equal(quantity, other.quantity) &&
          JodaBeanUtils.equal(price, other.price);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(info);
    hash = hash * 31 + JodaBeanUtils.hashCode(security);
    hash = hash * 31 + JodaBeanUtils.hashCode(quantity);
    hash = hash * 31 + JodaBeanUtils.hashCode(price);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(160);
    buf.append("EtdOptionTrade{");
    buf.append("info").append('=').append(info).append(',').append(' ');
    buf.append("security").append('=').append(security).append(',').append(' ');
    buf.append("quantity").append('=').append(quantity).append(',').append(' ');
    buf.append("price").append('=').append(JodaBeanUtils.toString(price));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code EtdOptionTrade}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code info} property.
     */
    private final MetaProperty<TradeInfo> info = DirectMetaProperty.ofImmutable(
        this, "info", EtdOptionTrade.class, TradeInfo.class);
    /**
     * The meta-property for the {@code security} property.
     */
    private final MetaProperty<EtdOptionSecurity> security = DirectMetaProperty.ofImmutable(
        this, "security", EtdOptionTrade.class, EtdOptionSecurity.class);
    /**
     * The meta-property for the {@code quantity} property.
     */
    private final MetaProperty<Double> quantity = DirectMetaProperty.ofImmutable(
        this, "quantity", EtdOptionTrade.class, Double.TYPE);
    /**
     * The meta-property for the {@code price} property.
     */
    private final MetaProperty<Double> price = DirectMetaProperty.ofImmutable(
        this, "price", EtdOptionTrade.class, Double.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "info",
        "security",
        "quantity",
        "price");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3237038:  // info
          return info;
        case 949122880:  // security
          return security;
        case -1285004149:  // quantity
          return quantity;
        case 106934601:  // price
          return price;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public EtdOptionTrade.Builder builder() {
      return new EtdOptionTrade.Builder();
    }

    @Override
    public Class<? extends EtdOptionTrade> beanType() {
      return EtdOptionTrade.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code info} property.
     * @return the meta-property, not null
     */
    public MetaProperty<TradeInfo> info() {
      return info;
    }

    /**
     * The meta-property for the {@code security} property.
     * @return the meta-property, not null
     */
    public MetaProperty<EtdOptionSecurity> security() {
      return security;
    }

    /**
     * The meta-property for the {@code quantity} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> quantity() {
      return quantity;
    }

    /**
     * The meta-property for the {@code price} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> price() {
      return price;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3237038:  // info
          return ((EtdOptionTrade) bean).getInfo();
        case 949122880:  // security
          return ((EtdOptionTrade) bean).getSecurity();
        case -1285004149:  // quantity
          return ((EtdOptionTrade) bean).getQuantity();
        case 106934601:  // price
          return ((EtdOptionTrade) bean).getPrice();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code EtdOptionTrade}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<EtdOptionTrade> {

    private TradeInfo info;
    private EtdOptionSecurity security;
    private double quantity;
    private double price;

    /**
     * Restricted constructor.
     */
    private Builder() {
      applyDefaults(this);
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(EtdOptionTrade beanToCopy) {
      this.info = beanToCopy.getInfo();
      this.security = beanToCopy.getSecurity();
      this.quantity = beanToCopy.getQuantity();
      this.price = beanToCopy.getPrice();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3237038:  // info
          return info;
        case 949122880:  // security
          return security;
        case -1285004149:  // quantity
          return quantity;
        case 106934601:  // price
          return price;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 3237038:  // info
          this.info = (TradeInfo) newValue;
          break;
        case 949122880:  // security
          this.security = (EtdOptionSecurity) newValue;
          break;
        case -1285004149:  // quantity
          this.quantity = (Double) newValue;
          break;
        case 106934601:  // price
          this.price = (Double) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public EtdOptionTrade build() {
      return new EtdOptionTrade(
          info,
          security,
          quantity,
          price);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the additional trade information, defaulted to an empty instance.
     * <p>
     * This allows additional information to be attached to the trade.
     * @param info  the new value
     * @return this, for chaining, not null
     */
    public Builder info(TradeInfo info) {
      this.info = info;
      return this;
    }

    /**
     * Sets the security that was traded.
     * @param security  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder security(EtdOptionSecurity security) {
      JodaBeanUtils.notNull(security, "security");
      this.security = security;
      return this;
    }

    /**
     * Sets the quantity that was traded.
     * <p>
     * This is the number of contracts that were traded.
     * This will be positive if buying and negative if selling.
     * @param quantity  the new value
     * @return this, for chaining, not null
     */
    public Builder quantity(double quantity) {
      this.quantity = quantity;
      return this;
    }

    /**
     * Sets the price that was traded, in decimal form.
     * <p>
     * This is the price agreed when the trade occurred.
     * @param price  the new value
     * @return this, for chaining, not null
     */
    public Builder price(double price) {
      this.price = price;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(160);
      buf.append("EtdOptionTrade.Builder{");
      buf.append("info").append('=').append(JodaBeanUtils.toString(info)).append(',').append(' ');
      buf.append("security").append('=').append(JodaBeanUtils.toString(security)).append(',').append(' ');
      buf.append("quantity").append('=').append(JodaBeanUtils.toString(quantity)).append(',').append(' ');
      buf.append("price").append('=').append(JodaBeanUtils.toString(price));
      buf.append('}');
      return buf.toString();
    }

  }

  //-------------------------- AUTOGENERATED END --------------------------
}
