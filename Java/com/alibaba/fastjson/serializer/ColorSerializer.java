package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

public class ColorSerializer
  implements AutowiredObjectSerializer
{
  public static final ColorSerializer instance = new ColorSerializer();
  
  public Set<Type> getAutowiredFor()
  {
    return Collections.singleton(Color.class);
  }
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Color localColor = (Color)paramObject1;
    if (localColor == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    char c = '{';
    if (localSerializeWriter.isEnabled(SerializerFeature.WriteClassName))
    {
      localSerializeWriter.write('{');
      localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
      localSerializeWriter.writeString(Color.class.getName());
      c = ',';
    }
    localSerializeWriter.writeFieldValue(c, "r", localColor.getRed());
    localSerializeWriter.writeFieldValue(',', "g", localColor.getGreen());
    localSerializeWriter.writeFieldValue(',', "b", localColor.getBlue());
    if (localColor.getAlpha() > 0) {
      localSerializeWriter.writeFieldValue(',', "alpha", localColor.getAlpha());
    }
    localSerializeWriter.write('}');
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\ColorSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */