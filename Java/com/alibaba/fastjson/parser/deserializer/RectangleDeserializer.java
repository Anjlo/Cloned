package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import java.awt.Rectangle;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

public class RectangleDeserializer
  implements AutowiredObjectDeserializer
{
  public static final RectangleDeserializer instance = new RectangleDeserializer();
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONScanner localJSONScanner = (JSONScanner)paramDefaultJSONParser.getLexer();
    if (localJSONScanner.token() == 8)
    {
      localJSONScanner.nextToken();
      return null;
    }
    if ((localJSONScanner.token() != 12) && (localJSONScanner.token() != 16)) {
      throw new JSONException("syntax error");
    }
    localJSONScanner.nextToken();
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    String str;
    label190:
    label200:
    label249:
    for (;;)
    {
      if (localJSONScanner.token() == 13)
      {
        localJSONScanner.nextToken();
        return new Rectangle(i, j, k, m);
      }
      int n;
      if (localJSONScanner.token() == 4)
      {
        str = localJSONScanner.stringVal();
        localJSONScanner.nextTokenWithColon(2);
        if (localJSONScanner.token() != 2) {
          break label190;
        }
        n = localJSONScanner.intValue();
        localJSONScanner.nextToken();
        if (!str.equalsIgnoreCase("x")) {
          break label200;
        }
        i = n;
      }
      for (;;)
      {
        if (localJSONScanner.token() != 16) {
          break label249;
        }
        localJSONScanner.nextToken(4);
        break;
        throw new JSONException("syntax error");
        throw new JSONException("syntax error");
        if (str.equalsIgnoreCase("y"))
        {
          j = n;
        }
        else if (str.equalsIgnoreCase("width"))
        {
          k = n;
        }
        else
        {
          if (!str.equalsIgnoreCase("height")) {
            break label251;
          }
          m = n;
        }
      }
    }
    label251:
    throw new JSONException("syntax error, " + str);
  }
  
  public Set<Type> getAutowiredFor()
  {
    return Collections.singleton(Rectangle.class);
  }
  
  public int getFastMatchToken()
  {
    return 12;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\RectangleDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */