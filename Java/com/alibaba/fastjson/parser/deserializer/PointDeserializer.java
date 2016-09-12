package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import java.awt.Point;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

public class PointDeserializer
  implements AutowiredObjectDeserializer
{
  public static final PointDeserializer instance = new PointDeserializer();
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONScanner localJSONScanner = (JSONScanner)paramDefaultJSONParser.getLexer();
    if (localJSONScanner.token() == 8)
    {
      localJSONScanner.nextToken(16);
      return null;
    }
    if ((localJSONScanner.token() != 12) && (localJSONScanner.token() != 16)) {
      throw new JSONException("syntax error");
    }
    localJSONScanner.nextToken();
    int i = 0;
    int j = 0;
    String str;
    label202:
    label233:
    label248:
    for (;;)
    {
      if (localJSONScanner.token() == 13)
      {
        localJSONScanner.nextToken();
        return new Point(i, j);
      }
      int k;
      if (localJSONScanner.token() == 4)
      {
        str = localJSONScanner.stringVal();
        if (JSON.DEFAULT_TYPE_KEY.equals(str))
        {
          paramDefaultJSONParser.acceptType("java.awt.Point");
          continue;
        }
        localJSONScanner.nextTokenWithColon(2);
        if (localJSONScanner.token() != 2) {
          break label202;
        }
        k = localJSONScanner.intValue();
        localJSONScanner.nextToken();
        if (!str.equalsIgnoreCase("x")) {
          break label233;
        }
        i = k;
      }
      for (;;)
      {
        if (localJSONScanner.token() != 16) {
          break label248;
        }
        localJSONScanner.nextToken(4);
        break;
        throw new JSONException("syntax error");
        throw new JSONException("syntax error : " + localJSONScanner.tokenName());
        if (!str.equalsIgnoreCase("y")) {
          break label250;
        }
        j = k;
      }
    }
    label250:
    throw new JSONException("syntax error, " + str);
  }
  
  public Set<Type> getAutowiredFor()
  {
    return Collections.singleton(Point.class);
  }
  
  public int getFastMatchToken()
  {
    return 12;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\PointDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */