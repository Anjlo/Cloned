package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class CollectionSerializer
  implements ObjectSerializer
{
  public static final CollectionSerializer instance = new CollectionSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
    {
      if (localSerializeWriter.isEnabled(SerializerFeature.WriteNullListAsEmpty))
      {
        localSerializeWriter.write("[]");
        return;
      }
      localSerializeWriter.writeNull();
      return;
    }
    boolean bool1 = paramJSONSerializer.isEnabled(SerializerFeature.WriteClassName);
    Type localType = null;
    if (bool1)
    {
      boolean bool2 = paramType instanceof ParameterizedType;
      localType = null;
      if (bool2) {
        localType = ((ParameterizedType)paramType).getActualTypeArguments()[0];
      }
    }
    Collection localCollection = (Collection)paramObject1;
    SerialContext localSerialContext = paramJSONSerializer.getContext();
    paramJSONSerializer.setContext(localSerialContext, paramObject1, paramObject2);
    if (paramJSONSerializer.isEnabled(SerializerFeature.WriteClassName))
    {
      if (HashSet.class != localCollection.getClass()) {
        break label204;
      }
      localSerializeWriter.append("Set");
    }
    label204:
    label368:
    for (;;)
    {
      try
      {
        localSerializeWriter.append('[');
        localIterator = localCollection.iterator();
        i = 0;
      }
      finally
      {
        try
        {
          Iterator localIterator;
          if (localIterator.hasNext())
          {
            Object localObject3 = localIterator.next();
            j = i + 1;
            if (i != 0) {
              localSerializeWriter.append(',');
            }
            if (localObject3 == null)
            {
              localSerializeWriter.writeNull();
              i = j;
              continue;
              if (TreeSet.class != localCollection.getClass()) {
                continue;
              }
              localSerializeWriter.append("TreeSet");
              continue;
            }
            Class localClass = localObject3.getClass();
            if (localClass == Integer.class)
            {
              localSerializeWriter.writeInt(((Integer)localObject3).intValue());
              i = j;
              continue;
            }
            if (localClass == Long.class)
            {
              localSerializeWriter.writeLong(((Long)localObject3).longValue());
              if (!localSerializeWriter.isEnabled(SerializerFeature.WriteClassName)) {
                break label368;
              }
              localSerializeWriter.write('L');
              i = j;
              continue;
            }
            paramJSONSerializer.getObjectWriter(localClass).write(paramJSONSerializer, localObject3, Integer.valueOf(j - 1), localType);
            i = j;
            continue;
          }
          localSerializeWriter.append(']');
          paramJSONSerializer.setContext(localSerialContext);
          return;
        }
        finally
        {
          int j;
          continue;
          int i = j;
        }
        localObject1 = finally;
        paramJSONSerializer.setContext(localSerialContext);
      }
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\CollectionSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */