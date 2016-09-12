package com.lefu.es.util;

import android.annotation.SuppressLint;
import android.os.Environment;
import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tool
{
  @SuppressLint({"SimpleDateFormat"})
  public static Date StringToDate(String paramString1, String paramString2)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
    try
    {
      Date localDate = localSimpleDateFormat.parse(paramString1);
      return localDate;
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    return null;
  }
  
  public static boolean checkParameter(String paramString)
  {
    return 0 + (1 + paramString.indexOf("'")) + (1 + paramString.indexOf("\"")) + (1 + paramString.indexOf(";")) + (1 + paramString.indexOf("1=1")) + (1 + paramString.indexOf("|")) + (1 + paramString.indexOf("<")) + (1 + paramString.indexOf(">")) + (1 + paramString.indexOf("-")) == 0;
  }
  
  public static void close(OutputStream paramOutputStream)
  {
    if (paramOutputStream != null) {}
    try
    {
      paramOutputStream.close();
      return;
    }
    catch (Exception localException) {}
  }
  
  public static void close(Writer paramWriter)
  {
    if (paramWriter != null) {}
    try
    {
      paramWriter.close();
      return;
    }
    catch (Exception localException) {}
  }
  
  public static void createDirs(File paramFile)
  {
    if ((paramFile != null) && (!paramFile.exists())) {
      paramFile.mkdirs();
    }
  }
  
  public static int getAgeByBirthday(Date paramDate)
  {
    int i2;
    if (paramDate == null) {
      i2 = 0;
    }
    int i;
    int j;
    int k;
    int m;
    int n;
    int i1;
    do
    {
      return i2;
      Calendar localCalendar = Calendar.getInstance();
      if (localCalendar.before(paramDate)) {
        throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
      }
      i = localCalendar.get(1);
      j = 1 + localCalendar.get(2);
      k = localCalendar.get(5);
      localCalendar.setTime(paramDate);
      m = localCalendar.get(1);
      n = 1 + localCalendar.get(2);
      i1 = localCalendar.get(5);
      i2 = i - m;
    } while ((i < m) || (j < n) || (k < i1));
    return i2 + 1;
  }
  
  public static boolean isDigitsOnly(String paramString)
  {
    return Boolean.valueOf(paramString.matches("-?[0-9]+.*[0-9]*")).booleanValue();
  }
  
  public static boolean isHasSdcard()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }
  
  @SuppressLint({"SimpleDateFormat"})
  public static String randomImage()
  {
    Date localDate = new Date(System.currentTimeMillis());
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/mnt/sdcard/");
    localStringBuffer.append("HealthScale/");
    localStringBuffer.append("Photos");
    localStringBuffer.append("/" + localSimpleDateFormat.format(localDate) + ".jpg");
    return localStringBuffer.toString();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\util\Tool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */