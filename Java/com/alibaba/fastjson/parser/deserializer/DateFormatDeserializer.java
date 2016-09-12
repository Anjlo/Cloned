package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

public class DateFormatDeserializer
  extends AbstractDateDeserializer
  implements ObjectDeserializer
{
  public static final DateFormatDeserializer instance = new DateFormatDeserializer();
  
  protected <T> T cast(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject1, Object paramObject2)
  {
    if ((paramObject2 instanceof String))
    {
      String str = (String)paramObject2;
      if (str.length() == 0) {
        return null;
      }
      return new SimpleDateFormat(str);
    }
    throw new JSONException("parse error");
  }
  
  public int getFastMatchToken()
  {
    return 2;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\DateFormatDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */