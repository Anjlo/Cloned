package com.lefu.es.util;

public class otherUtil
{
  public static String cyclicalm(int paramInt)
  {
    String[] arrayOfString1 = { "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸" };
    String[] arrayOfString2 = { "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥" };
    return arrayOfString1[(paramInt % 10)] + arrayOfString2[(paramInt % 12)];
  }
  
  public static String[] getYera()
  {
    String[] arrayOfString = new String[107];
    int i = 1942;
    for (int j = 0;; j++)
    {
      if (j >= arrayOfString.length) {
        return arrayOfString;
      }
      arrayOfString[j] = (cyclicalm(36 + (i - 1900)) + "年(" + i + ")");
      i++;
    }
  }
  
  public static boolean isGregorianLeapYear(int paramInt)
  {
    int i = paramInt % 4;
    boolean bool = false;
    if (i == 0) {
      bool = true;
    }
    if (paramInt % 100 == 0) {
      bool = false;
    }
    if (paramInt % 400 == 0) {
      bool = true;
    }
    return bool;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\util\otherUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */