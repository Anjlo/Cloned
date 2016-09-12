package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONStreamAware;
import java.io.IOException;
import java.lang.reflect.Type;

public class JSONStreamAwareSerializer
  implements ObjectSerializer
{
  public static JSONStreamAwareSerializer instance = new JSONStreamAwareSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    ((JSONStreamAware)paramObject1).writeJSONString(localSerializeWriter);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\JSONStreamAwareSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */