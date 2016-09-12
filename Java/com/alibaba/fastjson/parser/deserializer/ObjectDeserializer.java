package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;

public abstract interface ObjectDeserializer
{
  public abstract <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject);
  
  public abstract int getFastMatchToken();
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\ObjectDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */