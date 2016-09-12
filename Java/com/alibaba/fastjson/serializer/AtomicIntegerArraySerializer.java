package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArraySerializer
  implements ObjectSerializer
{
  public static final AtomicIntegerArraySerializer instance = new AtomicIntegerArraySerializer();
  
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
    AtomicIntegerArray localAtomicIntegerArray = (AtomicIntegerArray)paramObject1;
    int i = localAtomicIntegerArray.length();
    localSerializeWriter.append('[');
    for (int j = 0; j < i; j++)
    {
      int k = localAtomicIntegerArray.get(j);
      if (j != 0) {
        localSerializeWriter.write(',');
      }
      localSerializeWriter.writeInt(k);
    }
    localSerializeWriter.append(']');
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\AtomicIntegerArraySerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */