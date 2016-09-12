package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

public class CalendarDeserializer
  implements ObjectDeserializer
{
  public static final CalendarDeserializer instance = new CalendarDeserializer();
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    Date localDate = (Date)DateDeserializer.instance.deserialze(paramDefaultJSONParser, paramType, paramObject);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    return localCalendar;
  }
  
  public int getFastMatchToken()
  {
    return 2;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\CalendarDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */