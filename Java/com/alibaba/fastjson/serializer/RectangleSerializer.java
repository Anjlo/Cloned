package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import java.awt.Rectangle;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

public class RectangleSerializer
  implements AutowiredObjectSerializer
{
  public static final RectangleSerializer instance = new RectangleSerializer();
  
  public Set<Type> getAutowiredFor()
  {
    return Collections.singleton(Rectangle.class);
  }
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Rectangle localRectangle = (Rectangle)paramObject1;
    if (localRectangle == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    char c = '{';
    if (localSerializeWriter.isEnabled(SerializerFeature.WriteClassName))
    {
      localSerializeWriter.write('{');
      localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
      localSerializeWriter.writeString(Rectangle.class.getName());
      c = ',';
    }
    localSerializeWriter.writeFieldValue(c, "x", localRectangle.getX());
    localSerializeWriter.writeFieldValue(',', "y", localRectangle.getY());
    localSerializeWriter.writeFieldValue(',', "width", localRectangle.getWidth());
    localSerializeWriter.writeFieldValue(',', "height", localRectangle.getHeight());
    localSerializeWriter.write('}');
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\RectangleSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */