package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import java.awt.Point;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

public class PointSerializer
  implements AutowiredObjectSerializer
{
  public static final PointSerializer instance = new PointSerializer();
  
  public Set<Type> getAutowiredFor()
  {
    return Collections.singleton(Point.class);
  }
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Point localPoint = (Point)paramObject1;
    if (localPoint == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    char c = '{';
    if (localSerializeWriter.isEnabled(SerializerFeature.WriteClassName))
    {
      localSerializeWriter.write('{');
      localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
      localSerializeWriter.writeString(Point.class.getName());
      c = ',';
    }
    localSerializeWriter.writeFieldValue(c, "x", localPoint.getX());
    localSerializeWriter.writeFieldValue(',', "y", localPoint.getY());
    localSerializeWriter.write('}');
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\PointSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */