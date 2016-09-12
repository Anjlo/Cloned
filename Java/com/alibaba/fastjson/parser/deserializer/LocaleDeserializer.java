package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;
import java.util.Locale;

public class LocaleDeserializer
  implements ObjectDeserializer
{
  public static final LocaleDeserializer instance = new LocaleDeserializer();
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    String str = (String)paramDefaultJSONParser.parse();
    if (str == null) {
      return null;
    }
    String[] arrayOfString = str.split("_");
    if (arrayOfString.length == 1) {
      return new Locale(arrayOfString[0]);
    }
    if (arrayOfString.length == 2) {
      return new Locale(arrayOfString[0], arrayOfString[1]);
    }
    return new Locale(arrayOfString[0], arrayOfString[1], arrayOfString[2]);
  }
  
  public int getFastMatchToken()
  {
    return 4;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\LocaleDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */