package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

public class JSONLibDataFormatSerializer
  implements ObjectSerializer
{
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    if (paramObject1 == null)
    {
      paramJSONSerializer.getWriter().writeNull();
      return;
    }
    Date localDate = (Date)paramObject1;
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("date", Integer.valueOf(localDate.getDate()));
    localJSONObject.put("day", Integer.valueOf(localDate.getDay()));
    localJSONObject.put("hours", Integer.valueOf(localDate.getHours()));
    localJSONObject.put("minutes", Integer.valueOf(localDate.getMinutes()));
    localJSONObject.put("month", Integer.valueOf(localDate.getMonth()));
    localJSONObject.put("seconds", Integer.valueOf(localDate.getSeconds()));
    localJSONObject.put("time", Long.valueOf(localDate.getTime()));
    localJSONObject.put("timezoneOffset", Integer.valueOf(localDate.getTimezoneOffset()));
    localJSONObject.put("year", Integer.valueOf(localDate.getYear()));
    paramJSONSerializer.write(localJSONObject);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\JSONLibDataFormatSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */