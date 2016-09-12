package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ArrayListStringDeserializer
  implements ObjectDeserializer
{
  public static final ArrayListStringDeserializer instance = new ArrayListStringDeserializer();
  
  public static void parseArray(DefaultJSONParser paramDefaultJSONParser, Collection paramCollection)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken(16);
      return;
    }
    if (localJSONLexer.token() == 21) {
      localJSONLexer.nextToken();
    }
    if (localJSONLexer.token() != 14) {
      throw new JSONException("exepct '[', but " + localJSONLexer.token());
    }
    localJSONLexer.nextToken(4);
    if (localJSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
      while (localJSONLexer.token() == 16) {
        localJSONLexer.nextToken();
      }
    }
    if (localJSONLexer.token() == 15)
    {
      localJSONLexer.nextToken(16);
      return;
    }
    String str;
    if (localJSONLexer.token() == 4)
    {
      str = localJSONLexer.stringVal();
      localJSONLexer.nextToken(16);
    }
    for (;;)
    {
      paramCollection.add(str);
      if (localJSONLexer.token() != 16) {
        break;
      }
      localJSONLexer.nextToken(4);
      break;
      Object localObject = paramDefaultJSONParser.parse();
      if (localObject == null) {
        str = null;
      } else {
        str = localObject.toString();
      }
    }
  }
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken(16);
      return null;
    }
    Object localObject;
    if ((paramType == Set.class) || (paramType == HashSet.class)) {
      localObject = new HashSet();
    }
    for (;;)
    {
      if (localObject == null) {
        localObject = new ArrayList();
      }
      parseArray(paramDefaultJSONParser, (Collection)localObject);
      return (T)localObject;
      boolean bool = paramType instanceof ParameterizedType;
      localObject = null;
      if (bool)
      {
        Type localType = ((ParameterizedType)paramType).getRawType();
        if (localType != Set.class)
        {
          localObject = null;
          if (localType != HashSet.class) {}
        }
        else
        {
          localObject = new HashSet();
        }
      }
    }
  }
  
  public int getFastMatchToken()
  {
    return 14;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\ArrayListStringDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */