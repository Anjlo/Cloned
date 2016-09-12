package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;

public class JSONArrayDeserializer
  implements ObjectDeserializer
{
  public static final JSONArrayDeserializer instance = new JSONArrayDeserializer();
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONArray localJSONArray = new JSONArray();
    paramDefaultJSONParser.parseArray(localJSONArray);
    return localJSONArray;
  }
  
  public int getFastMatchToken()
  {
    return 14;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\JSONArrayDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */