package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class AppendableSerializer
  implements ObjectSerializer
{
  public static final AppendableSerializer instance = new AppendableSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    if (paramObject1 == null)
    {
      SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
      if (localSerializeWriter.isEnabled(SerializerFeature.WriteNullStringAsEmpty))
      {
        localSerializeWriter.writeString("");
        return;
      }
      localSerializeWriter.writeNull();
      return;
    }
    paramJSONSerializer.write(paramObject1.toString());
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\AppendableSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */