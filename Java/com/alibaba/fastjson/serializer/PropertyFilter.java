package com.alibaba.fastjson.serializer;

public abstract interface PropertyFilter
  extends SerializeFilter
{
  public abstract boolean apply(Object paramObject1, String paramString, Object paramObject2);
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\PropertyFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */