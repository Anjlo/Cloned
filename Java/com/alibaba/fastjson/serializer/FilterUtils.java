package com.alibaba.fastjson.serializer;

import java.util.Iterator;
import java.util.List;

public class FilterUtils
{
  public static boolean apply(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, byte paramByte)
  {
    List localList = paramJSONSerializer.getPropertyFiltersDirect();
    if (localList != null)
    {
      boolean bool = true;
      Byte localByte = Byte.valueOf(paramByte);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        if (!((PropertyFilter)localIterator.next()).apply(paramObject, paramString, localByte)) {
          bool = false;
        }
      }
      return bool;
    }
    return true;
  }
  
  public static boolean apply(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, char paramChar)
  {
    List localList = paramJSONSerializer.getPropertyFiltersDirect();
    if (localList != null)
    {
      boolean bool = true;
      Character localCharacter = Character.valueOf(paramChar);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        if (!((PropertyFilter)localIterator.next()).apply(paramObject, paramString, localCharacter)) {
          bool = false;
        }
      }
      return bool;
    }
    return true;
  }
  
  public static boolean apply(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, double paramDouble)
  {
    List localList = paramJSONSerializer.getPropertyFiltersDirect();
    if (localList != null)
    {
      boolean bool = true;
      Double localDouble = Double.valueOf(paramDouble);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        if (!((PropertyFilter)localIterator.next()).apply(paramObject, paramString, localDouble)) {
          bool = false;
        }
      }
      return bool;
    }
    return true;
  }
  
  public static boolean apply(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, float paramFloat)
  {
    List localList = paramJSONSerializer.getPropertyFiltersDirect();
    if (localList != null)
    {
      boolean bool = true;
      Float localFloat = Float.valueOf(paramFloat);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        if (!((PropertyFilter)localIterator.next()).apply(paramObject, paramString, localFloat)) {
          bool = false;
        }
      }
      return bool;
    }
    return true;
  }
  
  public static boolean apply(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, int paramInt)
  {
    List localList = paramJSONSerializer.getPropertyFiltersDirect();
    if (localList != null)
    {
      boolean bool = true;
      Integer localInteger = Integer.valueOf(paramInt);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        if (!((PropertyFilter)localIterator.next()).apply(paramObject, paramString, localInteger)) {
          bool = false;
        }
      }
      return bool;
    }
    return true;
  }
  
  public static boolean apply(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, long paramLong)
  {
    List localList = paramJSONSerializer.getPropertyFiltersDirect();
    if (localList != null)
    {
      boolean bool = true;
      Long localLong = Long.valueOf(paramLong);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        if (!((PropertyFilter)localIterator.next()).apply(paramObject, paramString, localLong)) {
          bool = false;
        }
      }
      return bool;
    }
    return true;
  }
  
  public static boolean apply(JSONSerializer paramJSONSerializer, Object paramObject1, String paramString, Object paramObject2)
  {
    List localList = paramJSONSerializer.getPropertyFiltersDirect();
    if (localList == null) {}
    Iterator localIterator;
    do
    {
      while (!localIterator.hasNext())
      {
        return true;
        localIterator = localList.iterator();
      }
    } while (((PropertyFilter)localIterator.next()).apply(paramObject1, paramString, paramObject2));
    return false;
  }
  
  public static boolean apply(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, short paramShort)
  {
    List localList = paramJSONSerializer.getPropertyFiltersDirect();
    if (localList != null)
    {
      boolean bool = true;
      Short localShort = Short.valueOf(paramShort);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        if (!((PropertyFilter)localIterator.next()).apply(paramObject, paramString, localShort)) {
          bool = false;
        }
      }
      return bool;
    }
    return true;
  }
  
  public static boolean applyName(JSONSerializer paramJSONSerializer, Object paramObject, String paramString)
  {
    List localList = paramJSONSerializer.getPropertyPreFiltersDirect();
    if (localList == null) {}
    Iterator localIterator;
    do
    {
      while (!localIterator.hasNext())
      {
        return true;
        localIterator = localList.iterator();
      }
    } while (((PropertyPreFilter)localIterator.next()).apply(paramJSONSerializer, paramObject, paramString));
    return false;
  }
  
  public static String processKey(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, byte paramByte)
  {
    List localList = paramJSONSerializer.getNameFiltersDirect();
    if (localList != null)
    {
      Byte localByte = Byte.valueOf(paramByte);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        paramString = ((NameFilter)localIterator.next()).process(paramObject, paramString, localByte);
      }
    }
    return paramString;
  }
  
  public static String processKey(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, char paramChar)
  {
    List localList = paramJSONSerializer.getNameFiltersDirect();
    if (localList != null)
    {
      Character localCharacter = Character.valueOf(paramChar);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        paramString = ((NameFilter)localIterator.next()).process(paramObject, paramString, localCharacter);
      }
    }
    return paramString;
  }
  
  public static String processKey(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, double paramDouble)
  {
    List localList = paramJSONSerializer.getNameFiltersDirect();
    if (localList != null)
    {
      Double localDouble = Double.valueOf(paramDouble);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        paramString = ((NameFilter)localIterator.next()).process(paramObject, paramString, localDouble);
      }
    }
    return paramString;
  }
  
  public static String processKey(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, float paramFloat)
  {
    List localList = paramJSONSerializer.getNameFiltersDirect();
    if (localList != null)
    {
      Float localFloat = Float.valueOf(paramFloat);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        paramString = ((NameFilter)localIterator.next()).process(paramObject, paramString, localFloat);
      }
    }
    return paramString;
  }
  
  public static String processKey(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, int paramInt)
  {
    List localList = paramJSONSerializer.getNameFiltersDirect();
    if (localList != null)
    {
      Integer localInteger = Integer.valueOf(paramInt);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        paramString = ((NameFilter)localIterator.next()).process(paramObject, paramString, localInteger);
      }
    }
    return paramString;
  }
  
  public static String processKey(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, long paramLong)
  {
    List localList = paramJSONSerializer.getNameFiltersDirect();
    if (localList != null)
    {
      Long localLong = Long.valueOf(paramLong);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        paramString = ((NameFilter)localIterator.next()).process(paramObject, paramString, localLong);
      }
    }
    return paramString;
  }
  
  public static String processKey(JSONSerializer paramJSONSerializer, Object paramObject1, String paramString, Object paramObject2)
  {
    List localList = paramJSONSerializer.getNameFiltersDirect();
    if (localList != null)
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        paramString = ((NameFilter)localIterator.next()).process(paramObject1, paramString, paramObject2);
      }
    }
    return paramString;
  }
  
  public static String processKey(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, short paramShort)
  {
    List localList = paramJSONSerializer.getNameFiltersDirect();
    if (localList != null)
    {
      Short localShort = Short.valueOf(paramShort);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        paramString = ((NameFilter)localIterator.next()).process(paramObject, paramString, localShort);
      }
    }
    return paramString;
  }
  
  public static String processKey(JSONSerializer paramJSONSerializer, Object paramObject, String paramString, boolean paramBoolean)
  {
    List localList = paramJSONSerializer.getNameFiltersDirect();
    if (localList != null)
    {
      Boolean localBoolean = Boolean.valueOf(paramBoolean);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        paramString = ((NameFilter)localIterator.next()).process(paramObject, paramString, localBoolean);
      }
    }
    return paramString;
  }
  
  public static Object processValue(JSONSerializer paramJSONSerializer, Object paramObject1, String paramString, Object paramObject2)
  {
    List localList = paramJSONSerializer.getValueFiltersDirect();
    if (localList != null)
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        paramObject2 = ((ValueFilter)localIterator.next()).process(paramObject1, paramString, paramObject2);
      }
    }
    return paramObject2;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\FilterUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */