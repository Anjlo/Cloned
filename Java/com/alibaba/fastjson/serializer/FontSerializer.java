package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import java.awt.Font;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

public class FontSerializer
  implements AutowiredObjectSerializer
{
  public static final FontSerializer instance = new FontSerializer();
  
  public Set<Type> getAutowiredFor()
  {
    return Collections.singleton(Font.class);
  }
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Font localFont = (Font)paramObject1;
    if (localFont == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    char c = '{';
    if (localSerializeWriter.isEnabled(SerializerFeature.WriteClassName))
    {
      localSerializeWriter.write('{');
      localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
      localSerializeWriter.writeString(Font.class.getName());
      c = ',';
    }
    localSerializeWriter.writeFieldValue(c, "name", localFont.getName());
    localSerializeWriter.writeFieldValue(',', "style", localFont.getStyle());
    localSerializeWriter.writeFieldValue(',', "size", localFont.getSize());
    localSerializeWriter.write('}');
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\FontSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */