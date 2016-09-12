package com.alibaba.fastjson.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ServiceLoader
{
  private static final String PREFIX = "META-INF/services/";
  private static final Set<String> loadedUrls = new HashSet();
  
  public static void close(Closeable paramCloseable)
    throws IOException
  {
    if (paramCloseable != null) {
      paramCloseable.close();
    }
  }
  
  public static <T> Set<T> load(Class<T> paramClass, ClassLoader paramClassLoader)
  {
    localHashSet1 = new HashSet();
    String str1 = paramClass.getName();
    String str2 = "META-INF/services/" + str1;
    HashSet localHashSet2 = new HashSet();
    try
    {
      Enumeration localEnumeration = paramClassLoader.getResources(str2);
      while (localEnumeration.hasMoreElements())
      {
        URL localURL = (URL)localEnumeration.nextElement();
        if (!loadedUrls.contains(localURL.toString()))
        {
          load(localURL, localHashSet2);
          loadedUrls.add(localURL.toString());
        }
      }
      Iterator localIterator;
      String str3;
      return localHashSet1;
    }
    catch (IOException localIOException)
    {
      localIterator = localHashSet2.iterator();
      while (localIterator.hasNext())
      {
        str3 = (String)localIterator.next();
        try
        {
          localHashSet1.add(paramClassLoader.loadClass(str3).newInstance());
        }
        catch (Exception localException) {}
      }
    }
  }
  
  public static void load(URL paramURL, Set<String> paramSet)
    throws IOException
  {
    InputStream localInputStream = null;
    try
    {
      localInputStream = paramURL.openStream();
      BufferedReader localBufferedReader2 = new BufferedReader(new InputStreamReader(localInputStream, "utf-8"));
      try
      {
        for (;;)
        {
          String str1 = localBufferedReader2.readLine();
          String str2 = str1;
          if (str2 == null)
          {
            close(localBufferedReader2);
            close(localInputStream);
            return;
          }
          int i = str2.indexOf('#');
          if (i >= 0) {
            str2 = str2.substring(0, i);
          }
          String str3 = str2.trim();
          if (str3.length() != 0) {
            paramSet.add(str3);
          }
        }
        close(localBufferedReader1);
      }
      finally
      {
        localBufferedReader1 = localBufferedReader2;
      }
    }
    finally
    {
      BufferedReader localBufferedReader1 = null;
    }
    close(localInputStream);
    throw ((Throwable)localObject1);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\util\ServiceLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */