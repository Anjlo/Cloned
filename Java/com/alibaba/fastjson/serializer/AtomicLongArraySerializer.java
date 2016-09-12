package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicLongArray;

public class AtomicLongArraySerializer
  implements ObjectSerializer
{
  public static final AtomicLongArraySerializer instance = new AtomicLongArraySerializer();
  
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
    AtomicLongArray localAtomicLongArray = (AtomicLongArray)paramObject1;
    int i = localAtomicLongArray.length();
    localSerializeWriter.append('[');
    for (int j = 0; j < i; j++)
    {
      long l = localAtomicLongArray.get(j);
      if (j != 0) {
        localSerializeWriter.write(',');
      }
      localSerializeWriter.writeLong(l);
    }
    localSerializeWriter.append(']');
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\AtomicLongArraySerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */