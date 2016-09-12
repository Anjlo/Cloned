package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class ClassSerializer
  implements ObjectSerializer
{
  public static final ClassSerializer instance = new ClassSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    paramJSONSerializer.getWriter().writeString(((Class)paramObject1).getName());
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\ClassSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */