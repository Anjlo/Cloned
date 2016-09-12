package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.UUID;

public class UUIDSerializer
  implements ObjectSerializer
{
  public static final UUIDSerializer instance = new UUIDSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    if (paramObject1 == null)
    {
      paramJSONSerializer.writeNull();
      return;
    }
    paramJSONSerializer.write(((UUID)paramObject1).toString());
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\UUIDSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */