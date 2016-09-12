package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class IntegerSerializer
  implements ObjectSerializer
{
  public static IntegerSerializer instance = new IntegerSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Number localNumber = (Number)paramObject1;
    if (localNumber == null)
    {
      if (localSerializeWriter.isEnabled(SerializerFeature.WriteNullNumberAsZero))
      {
        localSerializeWriter.write('0');
        return;
      }
      localSerializeWriter.writeNull();
      return;
    }
    localSerializeWriter.writeInt(localNumber.intValue());
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\IntegerSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */