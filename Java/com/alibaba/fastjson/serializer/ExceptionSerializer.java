package com.alibaba.fastjson.serializer;

import java.lang.reflect.Type;

public class ExceptionSerializer
  extends JavaBeanSerializer
{
  public ExceptionSerializer(Class<?> paramClass)
  {
    super(paramClass);
  }
  
  protected boolean isWriteClassName(JSONSerializer paramJSONSerializer, Object paramObject1, Type paramType, Object paramObject2)
  {
    return true;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\ExceptionSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */