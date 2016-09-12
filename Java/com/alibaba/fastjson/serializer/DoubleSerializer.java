package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class DoubleSerializer
  implements ObjectSerializer
{
  public static final DoubleSerializer instance = new DoubleSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null) {
      if (paramJSONSerializer.isEnabled(SerializerFeature.WriteNullNumberAsZero)) {
        localSerializeWriter.write('0');
      }
    }
    do
    {
      return;
      localSerializeWriter.writeNull();
      return;
      double d = ((Double)paramObject1).doubleValue();
      if (Double.isNaN(d))
      {
        localSerializeWriter.writeNull();
        return;
      }
      if (Double.isInfinite(d))
      {
        localSerializeWriter.writeNull();
        return;
      }
      String str = Double.toString(d);
      if (str.endsWith(".0")) {
        str = str.substring(0, -2 + str.length());
      }
      localSerializeWriter.append(str);
    } while (!paramJSONSerializer.isEnabled(SerializerFeature.WriteClassName));
    localSerializeWriter.write('D');
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\DoubleSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */