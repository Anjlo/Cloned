package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class TimestampDeserializer
  extends AbstractDateDeserializer
  implements ObjectDeserializer
{
  public static final TimestampDeserializer instance = new TimestampDeserializer();
  
  protected <T> T cast(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject1, Object paramObject2)
  {
    if (paramObject2 == null) {}
    String str;
    do
    {
      return null;
      if ((paramObject2 instanceof Date)) {
        return new Timestamp(((Date)paramObject2).getTime());
      }
      if ((paramObject2 instanceof Number)) {
        return new Timestamp(((Number)paramObject2).longValue());
      }
      if (!(paramObject2 instanceof String)) {
        break;
      }
      str = (String)paramObject2;
    } while (str.length() == 0);
    DateFormat localDateFormat = paramDefaultJSONParser.getDateFormat();
    try
    {
      Timestamp localTimestamp = new Timestamp(localDateFormat.parse(str).getTime());
      return localTimestamp;
    }
    catch (ParseException localParseException)
    {
      return new Timestamp(Long.parseLong(str));
    }
    throw new JSONException("parse error");
  }
  
  public int getFastMatchToken()
  {
    return 2;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\TimestampDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */