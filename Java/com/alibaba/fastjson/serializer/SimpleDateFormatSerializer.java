package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatSerializer
  implements ObjectSerializer
{
  private final String pattern;
  
  public SimpleDateFormatSerializer(String paramString)
  {
    this.pattern = paramString;
  }
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    if (paramObject1 == null)
    {
      paramJSONSerializer.getWriter().writeNull();
      return;
    }
    Date localDate = (Date)paramObject1;
    paramJSONSerializer.write(new SimpleDateFormat(this.pattern).format(localDate));
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\SimpleDateFormatSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */