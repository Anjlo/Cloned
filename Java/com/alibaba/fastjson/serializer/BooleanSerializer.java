package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class BooleanSerializer
  implements ObjectSerializer
{
  public static final BooleanSerializer instance = new BooleanSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Boolean localBoolean = (Boolean)paramObject1;
    if (localBoolean == null)
    {
      if (localSerializeWriter.isEnabled(SerializerFeature.WriteNullBooleanAsFalse))
      {
        localSerializeWriter.write("false");
        return;
      }
      localSerializeWriter.writeNull();
      return;
    }
    if (localBoolean.booleanValue())
    {
      localSerializeWriter.write("true");
      return;
    }
    localSerializeWriter.write("false");
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\BooleanSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */