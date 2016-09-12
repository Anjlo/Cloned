package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class DoubleArraySerializer
  implements ObjectSerializer
{
  public static final DoubleArraySerializer instance = new DoubleArraySerializer();
  
  public final void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
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
    double[] arrayOfDouble = (double[])paramObject1;
    int i = -1 + arrayOfDouble.length;
    if (i == -1)
    {
      localSerializeWriter.append("[]");
      return;
    }
    localSerializeWriter.append('[');
    int j = 0;
    if (j < i)
    {
      double d2 = arrayOfDouble[j];
      if (Double.isNaN(d2)) {
        localSerializeWriter.writeNull();
      }
      for (;;)
      {
        localSerializeWriter.append(',');
        j++;
        break;
        localSerializeWriter.append(Double.toString(d2));
      }
    }
    double d1 = arrayOfDouble[i];
    if (Double.isNaN(d1)) {
      localSerializeWriter.writeNull();
    }
    for (;;)
    {
      localSerializeWriter.append(']');
      return;
      localSerializeWriter.append(Double.toString(d1));
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\DoubleArraySerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */