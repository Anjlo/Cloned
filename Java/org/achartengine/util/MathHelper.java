package org.achartengine.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MathHelper
{
  private static final NumberFormat FORMAT = ;
  public static final double NULL_VALUE = Double.MAX_VALUE;
  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  private static double[] computeLabels(double paramDouble1, double paramDouble2, int paramInt)
  {
    if (Math.abs(paramDouble1 - paramDouble2) < 1.0000000116860974E-7D) {
      return new double[] { paramDouble1, paramDouble1, 0.0D };
    }
    double d1 = paramDouble1;
    double d2 = paramDouble2;
    boolean bool = d1 < d2;
    int i = 0;
    if (bool)
    {
      i = 1;
      double d6 = d1;
      d1 = d2;
      d2 = d6;
    }
    double d3 = roundUp(Math.abs(d1 - d2) / paramInt);
    double d4 = d3 * Math.ceil(d1 / d3);
    double d5 = d3 * Math.floor(d2 / d3);
    if (i != 0)
    {
      double[] arrayOfDouble = new double[3];
      arrayOfDouble[0] = d5;
      arrayOfDouble[1] = d4;
      arrayOfDouble[2] = (-1.0D * d3);
      return arrayOfDouble;
    }
    return new double[] { d4, d5, d3 };
  }
  
  public static List<Double> getLabels(double paramDouble1, double paramDouble2, int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramInt <= 0) {}
    for (;;)
    {
      return localArrayList;
      FORMAT.setMaximumFractionDigits(5);
      double[] arrayOfDouble = computeLabels(paramDouble1, paramDouble2, paramInt);
      int i = 1 + (int)((arrayOfDouble[1] - arrayOfDouble[0]) / arrayOfDouble[2]);
      for (int j = 0; j < i; j++)
      {
        double d1 = arrayOfDouble[0] + j * arrayOfDouble[2];
        try
        {
          double d2 = FORMAT.parse(FORMAT.format(d1)).doubleValue();
          d1 = d2;
        }
        catch (ParseException localParseException)
        {
          for (;;) {}
        }
        Math.round(d1);
        localArrayList.add(Double.valueOf(d1));
      }
    }
  }
  
  public static double[] minmax(List<Double> paramList)
  {
    if (paramList.size() == 0) {
      return new double[2];
    }
    double d1 = ((Double)paramList.get(0)).doubleValue();
    double d2 = d1;
    int i = paramList.size();
    for (int j = 1;; j++)
    {
      if (j >= i) {
        return new double[] { d1, d2 };
      }
      double d3 = ((Double)paramList.get(j)).doubleValue();
      d1 = Math.min(d1, d3);
      d2 = Math.max(d2, d3);
    }
  }
  
  private static double roundUp(double paramDouble)
  {
    int i = (int)Math.floor(Math.log10(paramDouble));
    double d = paramDouble * Math.pow(10.0D, -i);
    if (d > 5.0D) {
      d = 10.0D;
    }
    for (;;)
    {
      return d * Math.pow(10.0D, i);
      if (d > 2.0D) {
        d = 5.0D;
      } else if (d > 1.0D) {
        d = 2.0D;
      }
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\util\MathHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */