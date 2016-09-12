package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;

public class ClassDerializer
  implements ObjectDeserializer
{
  public static final ClassDerializer instance = new ClassDerializer();
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken();
      return null;
    }
    if (localJSONLexer.token() != 4) {
      throw new JSONException("expect className");
    }
    String str = localJSONLexer.stringVal();
    localJSONLexer.nextToken(16);
    return TypeUtils.loadClass(str);
  }
  
  public int getFastMatchToken()
  {
    return 4;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\ClassDerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */