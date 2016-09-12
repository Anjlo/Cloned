package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import java.awt.Color;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

public class ColorDeserializer
  implements AutowiredObjectDeserializer
{
  public static final ColorDeserializer instance = new ColorDeserializer();
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONScanner localJSONScanner = (JSONScanner)paramDefaultJSONParser.getLexer();
    if ((localJSONScanner.token() != 12) && (localJSONScanner.token() != 16)) {
      throw new JSONException("syntax error");
    }
    localJSONScanner.nextToken();
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    String str;
    label173:
    label183:
    label232:
    for (;;)
    {
      if (localJSONScanner.token() == 13)
      {
        localJSONScanner.nextToken();
        return new Color(i, j, k, m);
      }
      int n;
      if (localJSONScanner.token() == 4)
      {
        str = localJSONScanner.stringVal();
        localJSONScanner.nextTokenWithColon(2);
        if (localJSONScanner.token() != 2) {
          break label173;
        }
        n = localJSONScanner.intValue();
        localJSONScanner.nextToken();
        if (!str.equalsIgnoreCase("r")) {
          break label183;
        }
        i = n;
      }
      for (;;)
      {
        if (localJSONScanner.token() != 16) {
          break label232;
        }
        localJSONScanner.nextToken(4);
        break;
        throw new JSONException("syntax error");
        throw new JSONException("syntax error");
        if (str.equalsIgnoreCase("g"))
        {
          j = n;
        }
        else if (str.equalsIgnoreCase("b"))
        {
          k = n;
        }
        else
        {
          if (!str.equalsIgnoreCase("alpha")) {
            break label234;
          }
          m = n;
        }
      }
    }
    label234:
    throw new JSONException("syntax error, " + str);
  }
  
  public Set<Type> getAutowiredFor()
  {
    return Collections.singleton(Color.class);
  }
  
  public int getFastMatchToken()
  {
    return 12;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\ColorDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */