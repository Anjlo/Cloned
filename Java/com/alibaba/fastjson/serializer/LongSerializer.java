package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class LongSerializer
  implements ObjectSerializer
{
  public static LongSerializer instance = new LongSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null) {
      if (localSerializeWriter.isEnabled(SerializerFeature.WriteNullNumberAsZero)) {
        localSerializeWriter.write('0');
      }
    }
    long l;
    do
    {
      return;
      localSerializeWriter.writeNull();
      return;
      l = ((Long)paramObject1).longValue();
      localSerializeWriter.writeLong(l);
    } while ((!paramJSONSerializer.isEnabled(SerializerFeature.WriteClassName)) || (l > 2147483647L) || (l < -2147483648L) || (paramType == Long.class));
    localSerializeWriter.write('L');
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\LongSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */