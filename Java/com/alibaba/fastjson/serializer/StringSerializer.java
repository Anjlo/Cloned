package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class StringSerializer
  implements ObjectSerializer
{
  public static StringSerializer instance = new StringSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    write(paramJSONSerializer, (String)paramObject1);
  }
  
  public void write(JSONSerializer paramJSONSerializer, String paramString)
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramString == null)
    {
      if (localSerializeWriter.isEnabled(SerializerFeature.WriteNullStringAsEmpty))
      {
        localSerializeWriter.writeString("");
        return;
      }
      localSerializeWriter.writeNull();
      return;
    }
    localSerializeWriter.writeString(paramString);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\StringSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */