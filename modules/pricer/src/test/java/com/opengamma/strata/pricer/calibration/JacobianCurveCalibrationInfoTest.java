/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.pricer.calibration;

import static com.opengamma.strata.collect.TestHelper.assertSerialization;
import static com.opengamma.strata.collect.TestHelper.coverBeanEquals;
import static com.opengamma.strata.collect.TestHelper.coverImmutableBean;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.opengamma.strata.market.curve.CurveName;
import com.opengamma.strata.market.curve.definition.CurveParameterSize;
import com.opengamma.strata.math.impl.matrix.DoubleMatrix2D;

/**
 * Test {@link JacobianCurveCalibrationInfo}.
 */
@Test
public class JacobianCurveCalibrationInfoTest {

  private static final CurveName NAME1 = CurveName.of("Test1");
  private static final CurveName NAME2 = CurveName.of("Test2");
  private static final CurveParameterSize CPS1 = CurveParameterSize.of(NAME1, 3);
  private static final CurveParameterSize CPS2 = CurveParameterSize.of(NAME2, 2);
  private static final List<CurveParameterSize> CPS = ImmutableList.of(CPS1, CPS2);
  private static final DoubleMatrix2D MATRIX = new DoubleMatrix2D(new double[][] { {1d, 2d}, {2d, 3d}});
  private static final DoubleMatrix2D MATRIX2 = new DoubleMatrix2D(new double[][] { {2d, 2d}, {3d, 3d}});

  //-------------------------------------------------------------------------
  public void test_of() {
    JacobianCurveCalibrationInfo test = JacobianCurveCalibrationInfo.of(CPS, MATRIX);
    assertEquals(test.getOrder(), CPS);
    assertEquals(test.getJacobianMatrix(), MATRIX);
    assertEquals(test.getCurveCount(), 2);
    assertEquals(test.getTotalParameterCount(), 5);
  }

  //-------------------------------------------------------------------------
  public void test_split() {
    JacobianCurveCalibrationInfo test = JacobianCurveCalibrationInfo.of(CPS, MATRIX);
    double[] array = {1, 2, 3, 4, 5};
    double[] array1 = {1, 2, 3};
    double[] array2 = {4, 5};
    assertEquals(test.splitValues(array), ImmutableMap.of(NAME1, array1, NAME2, array2));
  }

  //-------------------------------------------------------------------------
  public void coverage() {
    JacobianCurveCalibrationInfo test = JacobianCurveCalibrationInfo.of(CPS, MATRIX);
    coverImmutableBean(test);
    JacobianCurveCalibrationInfo test2 = JacobianCurveCalibrationInfo.of(ImmutableList.of(CPS1), MATRIX2);
    coverBeanEquals(test, test2);
  }

  public void test_serialization() {
    JacobianCurveCalibrationInfo test = JacobianCurveCalibrationInfo.of(CPS, MATRIX);
    assertSerialization(test);
  }

}
