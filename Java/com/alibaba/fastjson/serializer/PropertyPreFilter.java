package com.alibaba.fastjson.serializer;

public abstract interface PropertyPreFilter
  extends SerializeFilter
{
  public abstract boolean apply(JSONSerializer paramJSONSerializer, Object paramObject, String paramString);
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\PropertyPreFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */