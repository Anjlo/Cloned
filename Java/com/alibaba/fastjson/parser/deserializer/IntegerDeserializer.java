package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.math.BigDecimal;

public class IntegerDeserializer
  implements ObjectDeserializer
{
  public static final IntegerDeserializer instance = new IntegerDeserializer();
  
  public static <T> T deserialze(DefaultJSONParser paramDefaultJSONParser)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken(16);
      return null;
    }
    if (localJSONLexer.token() == 2)
    {
      int i = localJSONLexer.intValue();
      localJSONLexer.nextToken(16);
      return Integer.valueOf(i);
    }
    if (localJSONLexer.token() == 3)
    {
      BigDecimal localBigDecimal = localJSONLexer.decimalValue();
      localJSONLexer.nextToken(16);
      return Integer.valueOf(localBigDecimal.intValue());
    }
    return TypeUtils.castToInt(paramDefaultJSONParser.parse());
  }
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    return (T)deserialze(paramDefaultJSONParser);
  }
  
  public int getFastMatchToken()
  {
    return 2;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\IntegerDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */