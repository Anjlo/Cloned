package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerSerializer
  implements ObjectSerializer
{
  public static final AtomicIntegerSerializer instance = new AtomicIntegerSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    paramJSONSerializer.getWriter().writeInt(((AtomicInteger)paramObject1).get());
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\AtomicIntegerSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */