package com.alibaba.fastjson.serializer;

import java.lang.reflect.Type;
import java.util.Set;

public abstract interface AutowiredObjectSerializer
  extends ObjectSerializer
{
  public abstract Set<Type> getAutowiredFor();
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\AutowiredObjectSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */