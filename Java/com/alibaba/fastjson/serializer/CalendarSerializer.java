package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Calendar;

public class CalendarSerializer
  implements ObjectSerializer
{
  public static final CalendarSerializer instance = new CalendarSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    paramJSONSerializer.write(((Calendar)paramObject1).getTime());
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\CalendarSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */