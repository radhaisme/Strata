/*
 * Copyright (C) 2018 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.index;

import static com.opengamma.strata.basics.currency.Currency.GBP;
import static com.opengamma.strata.collect.TestHelper.assertSerialization;
import static com.opengamma.strata.collect.TestHelper.coverBeanEquals;
import static com.opengamma.strata.collect.TestHelper.coverImmutableBean;
import static com.opengamma.strata.collect.TestHelper.date;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;
import com.opengamma.strata.basics.ReferenceData;
import com.opengamma.strata.basics.currency.CurrencyAmount;
import com.opengamma.strata.collect.TestHelper;
import com.opengamma.strata.product.PositionInfo;
import com.opengamma.strata.product.SecurityInfo;
import com.opengamma.strata.product.SecurityPriceInfo;
import com.opengamma.strata.product.TradeInfo;

/**
 * Test {@link OvernightFutureSecurity}.
 */
@Test
public class OvernightFutureSecurityTest {

  private static final OvernightFuture PRODUCT = OvernightFutureTest.sut();
  private static final OvernightFuture PRODUCT2 = OvernightFutureTest.sut2();
  private static final SecurityPriceInfo PRICE_INFO = SecurityPriceInfo.of(0.1, CurrencyAmount.of(GBP, 25));
  private static final SecurityInfo INFO = SecurityInfo.of(PRODUCT.getSecurityId(), PRICE_INFO);
  private static final SecurityInfo INFO2 = SecurityInfo.of(PRODUCT2.getSecurityId(), PRICE_INFO);

  //-------------------------------------------------------------------------
  public void test_builder() {
    OvernightFutureSecurity test = sut();
    assertEquals(test.getInfo(), INFO);
    assertEquals(test.getSecurityId(), PRODUCT.getSecurityId());
    assertEquals(test.getCurrency(), PRODUCT.getCurrency());
    assertEquals(test.getUnderlyingIds(), ImmutableSet.of());
  }

  //-------------------------------------------------------------------------
  public void test_createProduct() {
    OvernightFutureSecurity test = sut();
    assertEquals(test.createProduct(ReferenceData.empty()), PRODUCT);
    TradeInfo tradeInfo = TradeInfo.of(date(2016, 6, 30));
    OvernightFutureTrade expectedTrade = OvernightFutureTrade.builder()
        .info(tradeInfo)
        .product(PRODUCT)
        .quantity(100)
        .price(0.995)
        .build();
    assertEquals(test.createTrade(tradeInfo, 100, 0.995, ReferenceData.empty()), expectedTrade);

    PositionInfo positionInfo = PositionInfo.empty();
    OvernightFuturePosition expectedPosition1 = OvernightFuturePosition.builder()
        .info(positionInfo)
        .product(PRODUCT)
        .longQuantity(100)
        .build();
    TestHelper.assertEqualsBean(test.createPosition(positionInfo, 100, ReferenceData.empty()), expectedPosition1);
    OvernightFuturePosition expectedPosition2 = OvernightFuturePosition.builder()
        .info(positionInfo)
        .product(PRODUCT)
        .longQuantity(100)
        .shortQuantity(50)
        .build();
    assertEquals(test.createPosition(positionInfo, 100, 50, ReferenceData.empty()), expectedPosition2);
  }

  //-------------------------------------------------------------------------
  public void coverage() {
    coverImmutableBean(sut());
    coverBeanEquals(sut(), sut2());
  }

  public void test_serialization() {
    assertSerialization(sut());
  }

  //-------------------------------------------------------------------------
  static OvernightFutureSecurity sut() {
    return OvernightFutureSecurity.builder()
        .info(INFO)
        .notional(PRODUCT.getNotional())
        .accrualFactor(PRODUCT.getAccrualFactor())
        .index(PRODUCT.getIndex())
        .accrualMethod(PRODUCT.getAccrualMethod())
        .lastTradeDate(PRODUCT.getLastTradeDate())
        .startDate(PRODUCT.getStartDate())
        .endDate(PRODUCT.getEndDate())
        .rounding(PRODUCT.getRounding())
        .build();
  }

  static OvernightFutureSecurity sut2() {
    return OvernightFutureSecurity.builder()
        .info(INFO2)
        .notional(PRODUCT2.getNotional())
        .accrualFactor(PRODUCT2.getAccrualFactor())
        .index(PRODUCT2.getIndex())
        .accrualMethod(PRODUCT2.getAccrualMethod())
        .lastTradeDate(PRODUCT2.getLastTradeDate())
        .startDate(PRODUCT2.getStartDate())
        .endDate(PRODUCT2.getEndDate())
        .rounding(PRODUCT2.getRounding())
        .build();
  }

}
