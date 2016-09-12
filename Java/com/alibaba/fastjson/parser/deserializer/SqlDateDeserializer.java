package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

public class SqlDateDeserializer
  extends AbstractDateDeserializer
  implements ObjectDeserializer
{
  public static final SqlDateDeserializer instance = new SqlDateDeserializer();
  
  protected <T> T cast(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject1, Object paramObject2)
  {
    if (paramObject2 == null) {
      return null;
    }
    if ((paramObject2 instanceof java.util.Date)) {
      return new java.sql.Date(((java.util.Date)paramObject2).getTime());
    }
    if ((paramObject2 instanceof Number)) {
      return new java.sql.Date(((Number)paramObject2).longValue());
    }
    if ((paramObject2 instanceof String))
    {
      String str = (String)paramObject2;
      if (str.length() == 0) {
        return null;
      }
      JSONScanner localJSONScanner = new JSONScanner(str);
      long l;
      if (localJSONScanner.scanISO8601DateIfMatch()) {
        l = localJSONScanner.getCalendar().getTimeInMillis();
      }
      for (;;)
      {
        return new java.sql.Date(l);
        DateFormat localDateFormat = paramDefaultJSONParser.getDateFormat();
        try
        {
          java.sql.Date localDate = new java.sql.Date(localDateFormat.parse(str).getTime());
          return localDate;
        }
        catch (ParseException localParseException)
        {
          l = Long.parseLong(str);
        }
      }
    }
    throw new JSONException("parse error : " + paramObject2);
  }
  
  public int getFastMatchToken()
  {
    return 2;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\SqlDateDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */