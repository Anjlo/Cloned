package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;

public class BigIntegerSerializer
  implements ObjectSerializer
{
  public static final BigIntegerSerializer instance = new BigIntegerSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
    {
      if (localSerializeWriter.isEnabled(SerializerFeature.WriteNullNumberAsZero))
      {
        localSerializeWriter.write('0');
        return;
      }
      localSerializeWriter.writeNull();
      return;
    }
    localSerializeWriter.write(((BigInteger)paramObject1).toString());
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\BigIntegerSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */