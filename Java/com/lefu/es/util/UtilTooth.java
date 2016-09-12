package com.lefu.es.util;

import android.annotation.SuppressLint;
import android.util.Log;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint({"SimpleDateFormat"})
public class UtilTooth
{
  public static final int BAR_WEIGHT_FAT = 40;
  public static final int BAR_WEIGHT_HEALTHY = 73;
  public static final int BAR_WEIGHT_OVERWEIGHT = 30;
  public static final int BAR_WEIGHT_SUM = 170;
  public static final int BAR_WEIGHT_THIN = 27;
  
  public static float changeBMI(float paramFloat, int paramInt)
  {
    float f1 = paramInt / 170.0F;
    float f2 = f1 * 27.0F / 18.5F;
    float f3 = 73.0F * f1 / 5.5F;
    float f4 = 40.0F * f1 / 4.0F;
    float f5 = 30.0F * f1 / 11.0F;
    float f6;
    if (paramFloat < 18.5D) {
      f6 = paramFloat * f2;
    }
    for (;;)
    {
      return f6 - 5.0F;
      if (paramFloat < 24.0F) {
        f6 = f1 * 27.0F + f3 * (paramFloat - 18.5F);
      } else if (paramFloat < 28.0F) {
        f6 = 100.0F * f1 + f4 * (paramFloat - 24.0F);
      } else if (paramFloat < 39.0F) {
        f6 = 140.0F * f1 + f5 * (paramFloat - 28.0F);
      } else {
        f6 = paramInt - 8;
      }
    }
  }
  
  public static float changeBMIBaby(float paramFloat, int paramInt)
  {
    float f1 = paramInt / 170.0F;
    float f2 = f1 * 27.0F / 15.0F;
    float f3 = 73.0F * f1 / 3.0F;
    float f4 = 40.0F * f1 / 4.0F;
    float f5 = f1 * 30.0F / 8.0F;
    float f6;
    if (paramFloat < 15.0F) {
      f6 = paramFloat * f2;
    }
    for (;;)
    {
      return f6 - 5.0F;
      if (paramFloat < 18.0F) {
        f6 = f1 * 27.0F + f3 * (paramFloat - 15.0F);
      } else if (paramFloat < 22.0F) {
        f6 = 100.0F * f1 + f4 * (paramFloat - 18.0F);
      } else if (paramFloat < 30.0F) {
        f6 = 140.0F * f1 + f5 * (paramFloat - 22.0F);
      } else {
        f6 = paramInt - 8;
      }
    }
  }
  
  public static String cm2FT_IN(float paramFloat)
  {
    int i = (int)(paramFloat / 30.48F);
    int j = Math.round(12.0F * (paramFloat / 30.48F - i));
    return i + "'" + j + "\"";
  }
  
  public static String[] cm2FT_INArray(float paramFloat)
  {
    int i = (int)(paramFloat / 30.48F);
    int j = Math.round(12.0F * (paramFloat / 30.48F - i));
    if (j >= 12)
    {
      int k = j / 12;
      int m = j % 12;
      i += k;
      j = m;
    }
    String[] arrayOfString = new String[2];
    arrayOfString[0] = i;
    arrayOfString[1] = j;
    return arrayOfString;
  }
  
  public static float cm2foot(float paramFloat)
  {
    return paramFloat / 30.48F;
  }
  
  public static String countBMI(float paramFloat1, float paramFloat2)
  {
    float f = paramFloat1 / (paramFloat2 * paramFloat2);
    return new DecimalFormat("######.0").format(f);
  }
  
  public static float countBMI2(float paramFloat1, float paramFloat2)
  {
    return paramFloat1 / (paramFloat2 * paramFloat2);
  }
  
  public static String dateTimeChange(Date paramDate)
  {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramDate);
  }
  
  public static float foot2cm(float paramFloat)
  {
    return 30.48F * paramFloat;
  }
  
  public static int ft_in2CM(String paramString)
  {
    int i = paramString.indexOf("'");
    int j = paramString.indexOf("\"");
    float f = 0.0F;
    if (i > 0) {
      f = 30.48F * Integer.parseInt(paramString.substring(0, i));
    }
    if (j >= 0) {
      f += 30.48F * (Integer.parseInt(paramString.substring(i + 1, j)) / 12.0F);
    }
    return (int)f;
  }
  
  public static int ft_in2CMArray(String[] paramArrayOfString)
  {
    int i = paramArrayOfString.length;
    float f = 0.0F;
    if (i > 0) {
      f = 30.48F * Integer.parseInt(paramArrayOfString[0]);
    }
    if (paramArrayOfString.length >= 0) {
      f += 30.48F * (Integer.parseInt(paramArrayOfString[1]) / 12.0F);
    }
    return (int)f;
  }
  
  public static String gToLb(float paramFloat)
  {
    return String.valueOf(new BigDecimal((float)(0.0022046D * paramFloat)).setScale(2, 4).floatValue());
  }
  
  public static String gToOz(float paramFloat)
  {
    return String.valueOf(new BigDecimal((float)(0.035274D * paramFloat)).setScale(2, 4).floatValue());
  }
  
  public static float gTog(float paramFloat)
  {
    return new BigDecimal(paramFloat * 1.0F).setScale(2, 4).floatValue();
  }
  
  public static String gTogS(float paramFloat)
  {
    return String.valueOf(new BigDecimal(paramFloat * 1.0F).setScale(2, 4).floatValue());
  }
  
  public static String keep0Point(float paramFloat)
  {
    return String.valueOf(new BigDecimal(paramFloat).setScale(0, 4).intValue());
  }
  
  public static float keep1Point2(float paramFloat)
  {
    return new BigDecimal(paramFloat).setScale(2, 4).floatValue();
  }
  
  public static String keep2Point(float paramFloat)
  {
    return String.valueOf(new BigDecimal(paramFloat).setScale(2, 4).floatValue());
  }
  
  public static String kgToFloz(float paramFloat)
  {
    float f = 0.1F * ((32768.0F + 2311.0F * (paramFloat * 10.0F)) / 65536.0F);
    DecimalFormat localDecimalFormat = new DecimalFormat();
    localDecimalFormat.setMaximumFractionDigits(1);
    localDecimalFormat.setGroupingSize(0);
    localDecimalFormat.setRoundingMode(RoundingMode.FLOOR);
    return localDecimalFormat.format(f);
  }
  
  public static String kgToLB(float paramFloat)
  {
    return kgToLB_new(paramFloat);
  }
  
  public static float kgToLB_F(float paramFloat)
  {
    return new BigDecimal(2.0F * ((1.0F + 11023.0F * (10.0F * (paramFloat * 10.0F)) / 50000.0F) / 2.0F) / 10.0F).setScale(1, 4).floatValue();
  }
  
  public static String kgToLB_ForBodyScale(float paramFloat)
  {
    int i = (int)Math.floor(1.0F + (32768.0F + 144479.0F * (paramFloat * 10.0F)) / 65536.0F);
    if (i % 2 != 0) {
      i--;
    }
    DecimalFormat localDecimalFormat = new DecimalFormat();
    localDecimalFormat.setMaximumFractionDigits(1);
    localDecimalFormat.setGroupingSize(0);
    localDecimalFormat.setRoundingMode(RoundingMode.FLOOR);
    return localDecimalFormat.format(0.1D * i);
  }
  
  public static String kgToLB_ForFatScale(float paramFloat)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(String.valueOf(paramFloat));
    BigDecimal localBigDecimal2 = new BigDecimal("1155845");
    BigDecimal localBigDecimal3 = new BigDecimal("16");
    BigDecimal localBigDecimal4 = new BigDecimal("65536");
    BigDecimal localBigDecimal5 = new BigDecimal("2");
    return String.valueOf(new BigDecimal(String.valueOf(localBigDecimal1.multiply(localBigDecimal2).doubleValue())).divide(localBigDecimal3, 5, 6).divide(localBigDecimal4, 1, 4).multiply(localBigDecimal5).floatValue());
  }
  
  public static double kgToLB_ForFatScale2(double paramDouble)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(String.valueOf(paramDouble));
    BigDecimal localBigDecimal2 = new BigDecimal("1155845");
    BigDecimal localBigDecimal3 = new BigDecimal("16");
    BigDecimal localBigDecimal4 = new BigDecimal("65536");
    BigDecimal localBigDecimal5 = new BigDecimal("2");
    return new BigDecimal(String.valueOf(localBigDecimal1.multiply(localBigDecimal2).doubleValue())).divide(localBigDecimal3, 5, 6).divide(localBigDecimal4, 1, 4).multiply(localBigDecimal5).doubleValue();
  }
  
  public static float kgToLB_ForFatScale3(float paramFloat)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(String.valueOf(paramFloat));
    BigDecimal localBigDecimal2 = new BigDecimal("1155845");
    BigDecimal localBigDecimal3 = new BigDecimal("16");
    BigDecimal localBigDecimal4 = new BigDecimal("65536");
    BigDecimal localBigDecimal5 = new BigDecimal("2");
    return new BigDecimal(String.valueOf(localBigDecimal1.multiply(localBigDecimal2).doubleValue())).divide(localBigDecimal3, 5, 6).divide(localBigDecimal4, 1, 4).multiply(localBigDecimal5).floatValue();
  }
  
  public static String kgToLB_new(float paramFloat)
  {
    return String.valueOf((0xFFFE & 1 + (32768 + 144479 * (int)(paramFloat * 10.0F)) / 65536) / 10.0F);
  }
  
  public static float kgToLB_target(float paramFloat)
  {
    return new BigDecimal(2.2046225F * paramFloat).setScale(5, 4).floatValue();
  }
  
  public static String kgToLBoz(float paramFloat)
  {
    float f1 = (32768.0F + 23117.0F * paramFloat) / 65536.0F;
    int i = (int)(f1 * 0.1F) / 16;
    float f2 = f1 * 0.1F % 16.0F;
    DecimalFormat localDecimalFormat = new DecimalFormat();
    localDecimalFormat.setMaximumFractionDigits(1);
    localDecimalFormat.setGroupingSize(0);
    localDecimalFormat.setRoundingMode(RoundingMode.FLOOR);
    return i + ":" + localDecimalFormat.format(f2);
  }
  
  public static String kgToLbForScaleBaby(float paramFloat)
  {
    float f1 = (float)(2.2046D * paramFloat);
    int i = (int)(f1 / 1.0F);
    float f2 = myround(16.0F * (f1 % 1.0F));
    return i + ":" + f2;
  }
  
  public static String kgToLbForScaleBaby_china(float paramFloat)
  {
    return myround2((float)(0.1574803D * paramFloat));
  }
  
  public static int kgToML(float paramFloat)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(String.valueOf(paramFloat));
    BigDecimal localBigDecimal2 = new BigDecimal("63701");
    BigDecimal localBigDecimal3 = new BigDecimal("65536");
    return new BigDecimal(String.valueOf(localBigDecimal1.multiply(localBigDecimal2).doubleValue())).divide(localBigDecimal3, 0, 4).intValue();
  }
  
  public static String kgToStLb(float paramFloat)
  {
    float f = (float)(0.1575D * paramFloat);
    int i = (int)(f / 1.0F);
    int j = (int)(14.0F * (f - i) / 1.0F);
    return i + ":" + j;
  }
  
  public static String kgToStLbForScaleFat(float paramFloat)
  {
    float f1 = (float)(0.1575D * paramFloat);
    int i = (int)(f1 / 1.0F);
    int j = (int)(14.0F * (f1 - i) / 1.0F);
    float f2 = 14.0F * (f1 - i) % 1.0F;
    String str = "";
    if ((f2 < 0.0F) || (f2 >= 0.125D))
    {
      if ((f2 < 0.125D) || (f2 >= 0.375D)) {
        break label117;
      }
      str = "1/4";
    }
    while (str.equals(""))
    {
      return i + ":" + j;
      label117:
      if ((f2 >= 0.375D) && (f2 < 0.625D)) {
        str = "1/2";
      } else if ((f2 >= 0.625D) && (f2 < 0.875D)) {
        str = "3/4";
      } else if ((f2 >= 0.875D) && (f2 < 1.0F)) {
        str = "";
      }
    }
    return i + ":" + j + "(" + str + ")";
  }
  
  public static String[] kgToStLbForScaleFat2(float paramFloat)
  {
    float f1 = (float)(0.1575D * paramFloat);
    int i = (int)(f1 / 1.0F);
    int j = (int)(14.0F * (f1 - i) / 1.0F);
    float f2 = 14.0F * (f1 - i) % 1.0F;
    String str = "";
    if ((f2 < 0.0F) || (f2 >= 0.125D))
    {
      if ((f2 < 0.125D) || (f2 >= 0.375D)) {
        break label124;
      }
      str = "1/4";
    }
    for (;;)
    {
      String[] arrayOfString = new String[2];
      arrayOfString[0] = (i + ":" + j);
      arrayOfString[1] = str;
      return arrayOfString;
      label124:
      if ((f2 >= 0.375D) && (f2 < 0.625D)) {
        str = "1/2";
      } else if ((f2 >= 0.625D) && (f2 < 0.875D)) {
        str = "3/4";
      } else if ((f2 >= 0.875D) && (f2 < 1.0F)) {
        str = "";
      }
    }
  }
  
  public static double kgToStLb_B(float paramFloat)
  {
    float f1 = (float)(0.1575D * paramFloat);
    int i = (int)(f1 / 1.0F);
    int j = (int)(14.0F * (f1 - i) / 1.0F);
    float f2 = 14.0F * (f1 - i) % 1.0F;
    String str = "0";
    if ((f2 < 0.0F) || (f2 >= 0.125D))
    {
      if ((f2 < 0.125D) || (f2 >= 0.375D)) {
        break label133;
      }
      str = "14";
    }
    while (j < 10)
    {
      return Double.parseDouble(i + ".0" + j + "474" + str + "8787");
      label133:
      if ((f2 >= 0.375D) && (f2 < 0.625D)) {
        str = "12";
      } else if ((f2 >= 0.625D) && (f2 < 0.875D)) {
        str = "34";
      } else if ((f2 >= 0.875D) && (f2 < 1.0F)) {
        str = "0";
      }
    }
    return Double.parseDouble(i + "." + j + "474" + str + "8787");
  }
  
  public static double kgToStLb_F(float paramFloat)
  {
    float f1 = (float)(0.1575D * paramFloat);
    int i = (int)(f1 / 1.0F);
    int j = (int)(14.0F * (f1 - i) / 1.0F);
    float f2 = 14.0F * (f1 - i) % 1.0F;
    String str = "0";
    if ((f2 < 0.0F) || (f2 >= 0.125D))
    {
      if ((f2 < 0.125D) || (f2 >= 0.375D)) {
        break label133;
      }
      str = "14";
    }
    while (j < 10)
    {
      return Double.parseDouble(i + ".0" + j + "474" + str + "8787");
      label133:
      if ((f2 >= 0.375D) && (f2 < 0.625D)) {
        str = "12";
      } else if ((f2 >= 0.625D) && (f2 < 0.875D)) {
        str = "34";
      } else if ((f2 >= 0.875D) && (f2 < 1.0F)) {
        str = "0";
      }
    }
    return Double.parseDouble(i + "." + j + "474" + str + "8787");
  }
  
  public static String kgToStLb_china(float paramFloat)
  {
    return myround2((float)(0.1574803D * paramFloat));
  }
  
  public static String lbToKg(float paramFloat)
  {
    float f = (float)(0.45359D * paramFloat);
    DecimalFormat localDecimalFormat = new DecimalFormat("######.00");
    Log.v("tag", "string2:" + localDecimalFormat.format(f));
    return localDecimalFormat.format(f);
  }
  
  public static String lbToKg2_new(float paramFloat)
  {
    return String.valueOf(new BigDecimal((float)(0.45359D * paramFloat)).setScale(5, 3).floatValue());
  }
  
  public static String lbToKg_new(float paramFloat)
  {
    return String.valueOf(new BigDecimal((float)(0.45359D * paramFloat)).setScale(1, 4).floatValue());
  }
  
  public static float lbToKg_target(float paramFloat)
  {
    return new BigDecimal(paramFloat * 0.4535924F).setScale(5, 4).floatValue();
  }
  
  public static String lbToLBOZ(float paramFloat)
  {
    int i = (int)(paramFloat / 1.0F);
    float f = myround(16.0F * (paramFloat % 1.0F));
    return i + ":" + f;
  }
  
  public static double lbToLBOZ_F(double paramDouble)
  {
    float f = (float)(2.2046D * paramDouble);
    int i = (int)(f / 1.0F);
    String str = myroundString(new StringBuilder(String.valueOf(new BigDecimal(16.0F * (f % 1.0F)).setScale(1, 4).floatValue())).toString()).replace(".", "47") + "0556";
    return new BigDecimal(Float.toString(i)).add(new BigDecimal("0." + str)).doubleValue();
  }
  
  public static double lbToLBOZ_F(float paramFloat)
  {
    float f = (float)(2.2046D * paramFloat);
    int i = (int)(f / 1.0F);
    String str = myroundString(new StringBuilder(String.valueOf(new BigDecimal(16.0F * (f % 1.0F)).setScale(1, 4).floatValue())).toString()).replace(".", "47") + "0556";
    return new BigDecimal(Float.toString(i)).add(new BigDecimal("0." + str)).doubleValue();
  }
  
  public static String lbToST(float paramFloat)
  {
    float f = paramFloat * 14.0F;
    return new DecimalFormat("######.00").format(f);
  }
  
  public static float lbTog(float paramFloat)
  {
    return new BigDecimal((float)(453.59237D * paramFloat)).setScale(2, 4).floatValue();
  }
  
  public static String lbTog_int(float paramFloat)
  {
    return String.valueOf(new BigDecimal((float)(453.59237D * paramFloat)).setScale(1, 4).floatValue());
  }
  
  public static String lbTog_new(float paramFloat)
  {
    return String.valueOf(new BigDecimal((float)(453.59237D * paramFloat)).setScale(1, 4).floatValue());
  }
  
  public static float myround(float paramFloat)
  {
    return (float)(Math.round(10.0F * paramFloat) / 10.0D);
  }
  
  public static float myround2(float paramFloat)
  {
    return (float)(Math.round(100.0F * paramFloat) / 100.0D);
  }
  
  public static String myroundString(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    int i;
    do
    {
      return paramString;
      i = 2 + paramString.indexOf(".");
    } while (i >= paramString.length());
    return paramString.substring(0, i);
  }
  
  public static String myroundString3(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    int i;
    do
    {
      return paramString;
      i = 3 + paramString.indexOf(".");
    } while (i >= paramString.length());
    return paramString.substring(0, i);
  }
  
  public static String onePoint(float paramFloat)
  {
    return String.valueOf(new BigDecimal(paramFloat).setScale(1, 4).floatValue());
  }
  
  public static float ozTog(float paramFloat)
  {
    return new BigDecimal((float)(28.3495231D * paramFloat)).setScale(2, 4).floatValue();
  }
  
  public static double round(double paramDouble, int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
    }
    return new BigDecimal(Double.toString(paramDouble)).divide(new BigDecimal("1"), paramInt, 5).doubleValue();
  }
  
  public static Date stringToTime(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try
    {
      Date localDate = localSimpleDateFormat.parse(paramString);
      return localDate;
    }
    catch (ParseException localParseException) {}
    return null;
  }
  
  public static String toOnePonit(float paramFloat)
  {
    return String.valueOf(new BigDecimal(paramFloat).setScale(1, 3).floatValue());
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\util\UtilTooth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */