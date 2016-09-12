package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class ObjectArraySerializer
  implements ObjectSerializer
{
  public static final ObjectArraySerializer instance = new ObjectArraySerializer();
  
  public final void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Object[] arrayOfObject = (Object[])paramObject1;
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
    int i = arrayOfObject.length;
    int j = i - 1;
    if (j == -1)
    {
      localSerializeWriter.append("[]");
      return;
    }
    SerialContext localSerialContext = paramJSONSerializer.getContext();
    paramJSONSerializer.setContext(localSerialContext, paramObject1, paramObject2);
    Object localObject1 = null;
    ObjectSerializer localObjectSerializer = null;
    for (;;)
    {
      int k;
      Object localObject4;
      Class localClass;
      try
      {
        localSerializeWriter.append('[');
        if (localSerializeWriter.isEnabled(SerializerFeature.PrettyFormat))
        {
          paramJSONSerializer.incrementIndent();
          paramJSONSerializer.println();
          int m = 0;
          if (m < i)
          {
            if (m != 0)
            {
              localSerializeWriter.write(',');
              paramJSONSerializer.println();
            }
            paramJSONSerializer.write(arrayOfObject[m]);
            m++;
            continue;
          }
          paramJSONSerializer.decrementIdent();
          paramJSONSerializer.println();
          localSerializeWriter.write(']');
          return;
        }
        k = 0;
        if (k >= j) {
          break label288;
        }
        localObject4 = arrayOfObject[k];
        if (localObject4 == null)
        {
          localSerializeWriter.append("null,");
        }
        else
        {
          localClass = localObject4.getClass();
          if (localClass == localObject1)
          {
            localObjectSerializer.write(paramJSONSerializer, localObject4, null, null);
            localSerializeWriter.append(',');
          }
        }
      }
      finally
      {
        paramJSONSerializer.setContext(localSerialContext);
      }
      localObject1 = localClass;
      localObjectSerializer = paramJSONSerializer.getObjectWriter(localClass);
      localObjectSerializer.write(paramJSONSerializer, localObject4, null, null);
      continue;
      label288:
      Object localObject3 = arrayOfObject[j];
      if (localObject3 == null) {
        localSerializeWriter.append("null]");
      }
      for (;;)
      {
        paramJSONSerializer.setContext(localSerialContext);
        return;
        paramJSONSerializer.write(localObject3);
        localSerializeWriter.append(']');
      }
      k++;
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\ObjectArraySerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */