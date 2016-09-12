package com.lefu.es.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint({"SimpleDateFormat", "DefaultLocale"})
public class UID
{
  private static final int ROTATION = 99999;
  private static StringBuilder buf = new StringBuilder();
  private static Date date = new Date();
  private static int seq = 0;
  
  public static String geUserID()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    return "user" + localSimpleDateFormat.format(new Date());
  }
  
  public static String getImage()
  {
    return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_photo.jpg";
  }
  
  public static String getRegisterID()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    return "register" + localSimpleDateFormat.format(new Date());
  }
  
  public static String next()
  {
    try
    {
      if (seq > 99999) {
        seq = 0;
      }
      buf.delete(0, buf.length());
      date.setTime(System.currentTimeMillis());
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = date;
      String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS", arrayOfObject);
      return str;
    }
    finally {}
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\util\UID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */