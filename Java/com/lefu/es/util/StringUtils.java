package com.lefu.es.util;

import android.annotation.SuppressLint;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint({"SimpleDateFormat", "DefaultLocale"})
public class StringUtils
{
  public static final String[] monthArray = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
  public static final String[] weekArray = { "Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat" };
  
  public static int binaryToTen(String paramString)
  {
    if ((paramString == null) || ((paramString != null) && ("".equals(paramString)))) {
      return 0;
    }
    return Integer.valueOf(paramString, 2).intValue();
  }
  
  public static String byte2hex(byte paramByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    char[] arrayOfChar = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
    int i = (paramByte & 0xF0) >> 4;
    int j = paramByte & 0xF;
    localStringBuffer.append(arrayOfChar[i]);
    localStringBuffer.append(arrayOfChar[j]);
    return localStringBuffer.toString();
  }
  
  public static String bytes2HexString(byte[] paramArrayOfByte)
  {
    String str1 = "";
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfByte.length) {
        return str1;
      }
      String str2 = Integer.toHexString(0xFF & paramArrayOfByte[i]);
      if (str2.length() == 1) {
        str2 = '0' + str2;
      }
      str1 = str1 + str2;
    }
  }
  
  public static String bytesToHexString(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length <= 0)) {
      return null;
    }
    char[] arrayOfChar = new char[2];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfByte.length) {
        return localStringBuilder.toString().toUpperCase();
      }
      arrayOfChar[0] = Character.forDigit(0xF & paramArrayOfByte[i] >>> 4, 16);
      arrayOfChar[1] = Character.forDigit(0xF & paramArrayOfByte[i], 16);
      localStringBuilder.append(arrayOfChar);
    }
  }
  
  public static String calcCheckSum(String paramString)
  {
    byte[] arrayOfByte1 = paramString.getBytes();
    byte[] arrayOfByte2 = new byte[4];
    int i = 0;
    String str;
    if (i >= arrayOfByte1.length)
    {
      arrayOfByte2[0] = ((byte)(0xFFFFFFFF ^ arrayOfByte2[0]));
      arrayOfByte2[1] = ((byte)(0xFFFFFFFF ^ arrayOfByte2[1]));
      arrayOfByte2[2] = ((byte)(0xFFFFFFFF ^ arrayOfByte2[2]));
      arrayOfByte2[3] = ((byte)(0xFFFFFFFF ^ arrayOfByte2[3]));
      str = "";
    }
    for (int j = 0;; j++)
    {
      if (j >= 4)
      {
        return str;
        arrayOfByte2[0] = ((byte)(arrayOfByte2[0] ^ arrayOfByte1[i]));
        arrayOfByte2[1] = ((byte)(arrayOfByte2[1] ^ arrayOfByte1[(i + 1)]));
        arrayOfByte2[2] = ((byte)(arrayOfByte2[2] ^ arrayOfByte1[(i + 2)]));
        arrayOfByte2[3] = ((byte)(arrayOfByte2[3] ^ arrayOfByte1[(i + 3)]));
        i += 4;
        break;
      }
      str = str + byte2hex(arrayOfByte2[j]);
    }
  }
  
  public static String getBCC(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = new byte[1];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfByte.length)
      {
        String str = Integer.toHexString(0xFF & arrayOfByte[0]);
        if (str.length() == 1) {
          str = '0' + str;
        }
        return "" + str.toUpperCase();
      }
      arrayOfByte[0] = ((byte)(arrayOfByte[0] ^ paramArrayOfByte[i]));
    }
  }
  
  public static String getDateShareString(String paramString, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString != null) {}
    for (;;)
    {
      try
      {
        if (!"".equals(paramString))
        {
          localDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paramString);
          int i = localDate.getDay();
          (1 + localDate.getMonth());
          if (localDate.getHours() >= 10) {
            continue;
          }
          localObject1 = "0" + localDate.getHours();
          localStringBuffer.append(localObject1);
          StringBuilder localStringBuilder1 = new StringBuilder(":");
          if (localDate.getMinutes() >= 10) {
            continue;
          }
          localObject2 = "0" + localDate.getMinutes();
          localStringBuffer.append(localObject2);
          StringBuilder localStringBuilder2 = new StringBuilder(":");
          if (localDate.getSeconds() >= 10) {
            continue;
          }
          localObject3 = "0" + localDate.getSeconds();
          localStringBuffer.append(localObject3);
          localStringBuffer.append(", " + weekArray[i]);
          localStringBuffer.append("," + (1 + localDate.getMonth()));
          localStringBuffer.append("/ " + localDate.getDate());
          localStringBuffer.append("/" + (1900 + localDate.getYear()));
        }
      }
      catch (Exception localException)
      {
        Date localDate;
        Object localObject1;
        Object localObject2;
        Object localObject3;
        Integer localInteger;
        continue;
      }
      return localStringBuffer.toString();
      localObject1 = Integer.valueOf(localDate.getHours());
      continue;
      localObject2 = Integer.valueOf(localDate.getMinutes());
      continue;
      localInteger = Integer.valueOf(localDate.getSeconds());
      localObject3 = localInteger;
    }
  }
  
  private static String getDateStr(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return paramInt;
    case 1: 
    case 21: 
    case 31: 
      return paramInt + "st";
    case 2: 
    case 22: 
      return paramInt + "nd";
    case 3: 
    case 23: 
      return paramInt + "rd";
    }
    return paramInt + "th";
  }
  
  public static String getDateString(Date paramDate, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramDate.getDay();
    int j = 1 + paramDate.getMonth();
    switch (paramInt)
    {
    default: 
    case 1: 
    case 2: 
    case 3: 
    case 4: 
      for (;;)
      {
        return localStringBuffer.toString();
        Object localObject6;
        StringBuilder localStringBuilder4;
        if (paramDate.getHours() < 10)
        {
          localObject6 = "0" + paramDate.getHours();
          localStringBuffer.append(localObject6);
          localStringBuilder4 = new StringBuilder(":");
          if (paramDate.getMinutes() >= 10) {
            break label269;
          }
        }
        for (Object localObject7 = "0" + paramDate.getMinutes();; localObject7 = Integer.valueOf(paramDate.getMinutes()))
        {
          localStringBuffer.append(localObject7);
          localStringBuffer.append(", " + weekArray[i]);
          localStringBuffer.append(", " + getDateStr(paramDate.getDate()) + " " + monthArray[(j - 1)]);
          localStringBuffer.append(", " + (1900 + paramDate.getYear()));
          break;
          localObject6 = Integer.valueOf(paramDate.getHours());
          break label95;
        }
        localStringBuffer.append(weekArray[i]);
        localStringBuffer.append(", " + getDateStr(paramDate.getDate()) + " " + monthArray[(j - 1)]);
        localStringBuffer.append(", " + (1900 + paramDate.getYear()));
        continue;
        localStringBuffer.append(monthArray[(j - 1)]);
        localStringBuffer.append(", " + (1900 + paramDate.getYear()));
        continue;
        localStringBuffer.append(1900 + paramDate.getYear());
      }
    case 5: 
      label95:
      label269:
      Object localObject4;
      label455:
      StringBuilder localStringBuilder3;
      if (paramDate.getHours() < 10)
      {
        localObject4 = "0" + paramDate.getHours();
        localStringBuffer.append(localObject4);
        localStringBuilder3 = new StringBuilder(":");
        if (paramDate.getMinutes() >= 10) {
          break label636;
        }
      }
      label636:
      for (Object localObject5 = "0" + paramDate.getMinutes();; localObject5 = Integer.valueOf(paramDate.getMinutes()))
      {
        localStringBuffer.append(localObject5);
        localStringBuffer.append(", " + weekArray[i]);
        localStringBuffer.append("," + (1 + paramDate.getMonth()));
        localStringBuffer.append("/ " + paramDate.getDate());
        localStringBuffer.append("/" + (1900 + paramDate.getYear()));
        break;
        localObject4 = Integer.valueOf(paramDate.getHours());
        break label455;
      }
    }
    Object localObject1;
    label678:
    Object localObject2;
    label726:
    StringBuilder localStringBuilder2;
    if (paramDate.getHours() < 10)
    {
      localObject1 = "0" + paramDate.getHours();
      localStringBuffer.append(localObject1);
      StringBuilder localStringBuilder1 = new StringBuilder(":");
      if (paramDate.getMinutes() >= 10) {
        break label915;
      }
      localObject2 = "0" + paramDate.getMinutes();
      localStringBuffer.append(localObject2);
      localStringBuilder2 = new StringBuilder(":");
      if (paramDate.getSeconds() >= 10) {
        break label927;
      }
    }
    label915:
    label927:
    for (Object localObject3 = "0" + paramDate.getSeconds();; localObject3 = Integer.valueOf(paramDate.getSeconds()))
    {
      localStringBuffer.append(localObject3);
      localStringBuffer.append(", " + weekArray[i]);
      localStringBuffer.append("," + (1 + paramDate.getMonth()));
      localStringBuffer.append("/ " + paramDate.getDate());
      localStringBuffer.append("/" + (1900 + paramDate.getYear()));
      break;
      localObject1 = Integer.valueOf(paramDate.getHours());
      break label678;
      localObject2 = Integer.valueOf(paramDate.getMinutes());
      break label726;
    }
  }
  
  public static int getSubChecknum(byte[] paramArrayOfByte)
  {
    int i = 0xFF & paramArrayOfByte[0];
    for (int j = 1;; j++)
    {
      if (j > -1 + paramArrayOfByte.length) {
        return i;
      }
      i ^= paramArrayOfByte[j];
    }
  }
  
  public static byte[] hexStringToByteArray(String paramString)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i = 0;
    int k;
    try
    {
      int j = paramString.length();
      if (i >= j) {
        return localByteArrayOutputStream.toByteArray();
      }
      k = paramString.charAt(i);
      if (i + 1 >= paramString.length()) {
        throw new IllegalArgumentException("hexUtil.odd");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    int m = i + 1;
    int n = paramString.charAt(m);
    int i1;
    if ((k >= 48) && (k <= 57)) {
      i1 = (byte)(0 + 16 * (k - 48));
    }
    for (;;)
    {
      localByteArrayOutputStream.write(i2);
      i += 2;
      break;
      label116:
      throw new IllegalArgumentException("hexUtil.bad");
      label232:
      do
      {
        throw new IllegalArgumentException("hexUtil.bad");
        for (;;)
        {
          if ((n < 48) || (n > 57)) {
            break label232;
          }
          i2 = (byte)(i1 + (n - 48));
          break;
          if ((k >= 97) && (k <= 102))
          {
            i1 = (byte)(0 + 16 * (10 + (k - 97)));
          }
          else
          {
            if ((k < 65) || (k > 70)) {
              break label116;
            }
            i1 = (byte)(0 + 16 * (10 + (k - 65)));
          }
        }
        if ((n >= 97) && (n <= 102))
        {
          i2 = (byte)(i1 + (10 + (n - 97)));
          break;
        }
      } while ((n < 65) || (n > 70));
      int i2 = (byte)(i1 + (10 + (n - 65)));
    }
  }
  
  public static String hexToBirary(String paramString)
  {
    if ((paramString == null) || ((paramString != null) && ("".equals(paramString)))) {
      return null;
    }
    return Integer.toBinaryString(Integer.valueOf(paramString, 16).intValue());
  }
  
  public static int hexToTen(String paramString)
  {
    if ((paramString == null) || ((paramString != null) && ("".equals(paramString)))) {
      return 0;
    }
    return Integer.valueOf(paramString, 16).intValue();
  }
  
  public static boolean isNumber(String paramString)
  {
    if ((paramString == null) || ("".equals(paramString))) {}
    String str1;
    String str2;
    do
    {
      return false;
      int i = paramString.indexOf(".");
      if (i < 0) {
        return isNumeric(paramString);
      }
      str1 = paramString.substring(0, i);
      str2 = paramString.substring(i + 1);
    } while ((!isNumeric(str1)) || (!isNumeric(str2)));
    return true;
  }
  
  public static boolean isNumeric(String paramString)
  {
    if ((paramString == null) || ("".equals(paramString))) {
      return false;
    }
    return Pattern.compile("[0-9]*").matcher(paramString).matches();
  }
  
  public static String tenToBinary(int paramInt)
  {
    return Integer.toBinaryString(paramInt);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\util\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */