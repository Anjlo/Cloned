package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class FloatSerializer
  implements ObjectSerializer
{
  public static FloatSerializer instance = new FloatSerializer();
  
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
      float f = ((Float)paramObject1).floatValue();
      if (Float.isNaN(f))
      {
        localSerializeWriter.writeNull();
        return;
      }
      if (Float.isInfinite(f))
      {
        localSerializeWriter.writeNull();
        return;
      }
      String str = Float.toString(f);
      if (str.endsWith(".0")) {
        str = str.substring(0, -2 + str.length());
      }
      localSerializeWriter.write(str);
    } while (!paramJSONSerializer.isEnabled(SerializerFeature.WriteClassName));
    localSerializeWriter.write('F');
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\FloatSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */