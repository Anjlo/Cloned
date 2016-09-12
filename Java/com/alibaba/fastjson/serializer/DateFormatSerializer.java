package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

public class DateFormatSerializer
  implements ObjectSerializer
{
  public static final DateFormatSerializer instance = new DateFormatSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    String str = ((SimpleDateFormat)paramObject1).toPattern();
    if ((localSerializeWriter.isEnabled(SerializerFeature.WriteClassName)) && (paramObject1.getClass() != paramType))
    {
      localSerializeWriter.write('{');
      localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
      paramJSONSerializer.write(paramObject1.getClass().getName());
      localSerializeWriter.writeFieldValue(',', "val", str);
      localSerializeWriter.write('}');
      return;
    }
    localSerializeWriter.writeString(str);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\DateFormatSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */