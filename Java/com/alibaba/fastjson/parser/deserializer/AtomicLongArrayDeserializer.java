package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicLongArray;

public class AtomicLongArrayDeserializer
  implements ObjectDeserializer
{
  public static final AtomicLongArrayDeserializer instance = new AtomicLongArrayDeserializer();
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    Object localObject;
    if (paramDefaultJSONParser.getLexer().token() == 8)
    {
      paramDefaultJSONParser.getLexer().nextToken(16);
      localObject = null;
    }
    for (;;)
    {
      return (T)localObject;
      JSONArray localJSONArray = new JSONArray();
      paramDefaultJSONParser.parseArray(localJSONArray);
      localObject = new AtomicLongArray(localJSONArray.size());
      for (int i = 0; i < localJSONArray.size(); i++) {
        ((AtomicLongArray)localObject).set(i, localJSONArray.getLong(i).longValue());
      }
    }
  }
  
  public int getFastMatchToken()
  {
    return 14;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\AtomicLongArrayDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */