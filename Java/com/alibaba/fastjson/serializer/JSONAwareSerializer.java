package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONAware;
import java.io.IOException;
import java.lang.reflect.Type;

public class JSONAwareSerializer
  implements ObjectSerializer
{
  public static JSONAwareSerializer instance = new JSONAwareSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    paramJSONSerializer.getWriter().write(((JSONAware)paramObject1).toJSONString());
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\JSONAwareSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */