package com.alibaba.fastjson;

import java.lang.reflect.Type;
import java.util.List;

public class TypeReference<T>
{
  public static final Type LIST_STRING = new TypeReference() {}.getType();
  private final Type type = ((java.lang.reflect.ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  
  public Type getType()
  {
    return this.type;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\TypeReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */