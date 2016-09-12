package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapSerializer
  implements ObjectSerializer
{
  public static MapSerializer instance = new MapSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    Object localObject1 = (Map)paramObject1;
    if ((localSerializeWriter.isEnabled(SerializerFeature.SortField)) && (!(localObject1 instanceof SortedMap)) && (!(localObject1 instanceof LinkedHashMap))) {}
    try
    {
      TreeMap localTreeMap = new TreeMap((Map)localObject1);
      localObject1 = localTreeMap;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        SerialContext localSerialContext;
        int k;
        int j;
        continue;
        if (k != 0)
        {
          continue;
          if (j == 0) {}
        }
      }
    }
    if (paramJSONSerializer.containsReference(paramObject1))
    {
      paramJSONSerializer.writeReference(paramObject1);
      return;
    }
    localSerialContext = paramJSONSerializer.getContext();
    paramJSONSerializer.setContext(localSerialContext, paramObject1, paramObject2);
    for (;;)
    {
      Object localObject3;
      ObjectSerializer localObjectSerializer;
      int i;
      Object localObject4;
      Object localObject5;
      try
      {
        localSerializeWriter.write('{');
        paramJSONSerializer.incrementIndent();
        localObject3 = null;
        localObjectSerializer = null;
        i = 1;
        if (localSerializeWriter.isEnabled(SerializerFeature.WriteClassName))
        {
          localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
          localSerializeWriter.writeString(paramObject1.getClass().getName());
          i = 0;
        }
        Iterator localIterator1 = ((Map)localObject1).entrySet().iterator();
        if (!localIterator1.hasNext()) {
          break;
        }
        Map.Entry localEntry = (Map.Entry)localIterator1.next();
        localObject4 = localEntry.getValue();
        localObject5 = localEntry.getKey();
        if ((localObject5 == null) || ((localObject5 instanceof String)))
        {
          String str = (String)localObject5;
          List localList1 = paramJSONSerializer.getPropertyPreFiltersDirect();
          if (localList1 != null)
          {
            k = 1;
            Iterator localIterator5 = localList1.iterator();
            if (!localIterator5.hasNext()) {
              break label670;
            }
            if (((PropertyPreFilter)localIterator5.next()).apply(paramJSONSerializer, paramObject1, str)) {
              continue;
            }
            k = 0;
            break label670;
          }
          List localList2 = paramJSONSerializer.getPropertyFiltersDirect();
          if (localList2 != null)
          {
            j = 1;
            Iterator localIterator4 = localList2.iterator();
            if (!localIterator4.hasNext()) {
              break label678;
            }
            if (((PropertyFilter)localIterator4.next()).apply(paramObject1, str, localObject4)) {
              continue;
            }
            j = 0;
            break label678;
          }
          List localList3 = paramJSONSerializer.getNameFiltersDirect();
          if (localList3 != null)
          {
            Iterator localIterator2 = localList3.iterator();
            if (localIterator2.hasNext())
            {
              str = ((NameFilter)localIterator2.next()).process(paramObject1, str, localObject4);
              continue;
            }
          }
          List localList4 = paramJSONSerializer.getValueFiltersDirect();
          if (localList4 != null)
          {
            Iterator localIterator3 = localList4.iterator();
            if (localIterator3.hasNext())
            {
              localObject4 = ((ValueFilter)localIterator3.next()).process(paramObject1, str, localObject4);
              continue;
            }
          }
          if ((localObject4 == null) && (!paramJSONSerializer.isEnabled(SerializerFeature.WriteMapNullValue))) {
            continue;
          }
          if (i == 0) {
            localSerializeWriter.write(',');
          }
          if (localSerializeWriter.isEnabled(SerializerFeature.PrettyFormat)) {
            paramJSONSerializer.println();
          }
          localSerializeWriter.writeFieldName(str, true);
          if (localObject4 != null) {
            break label558;
          }
          localSerializeWriter.writeNull();
          i = 0;
          continue;
        }
        if (i != 0) {
          break label542;
        }
      }
      finally
      {
        paramJSONSerializer.setContext(localSerialContext);
      }
      localSerializeWriter.write(',');
      label542:
      paramJSONSerializer.write(localObject5);
      localSerializeWriter.write(':');
      continue;
      label558:
      Class localClass = localObject4.getClass();
      if (localClass == localObject3)
      {
        localObjectSerializer.write(paramJSONSerializer, localObject4, localObject5, null);
        i = 0;
      }
      else
      {
        localObject3 = localClass;
        localObjectSerializer = paramJSONSerializer.getObjectWriter(localClass);
        localObjectSerializer.write(paramJSONSerializer, localObject4, localObject5, null);
        i = 0;
      }
    }
    paramJSONSerializer.setContext(localSerialContext);
    paramJSONSerializer.decrementIdent();
    if ((localSerializeWriter.isEnabled(SerializerFeature.PrettyFormat)) && (((Map)localObject1).size() > 0)) {
      paramJSONSerializer.println();
    }
    localSerializeWriter.write('}');
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\MapSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */