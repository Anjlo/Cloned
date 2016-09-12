package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class FloatArraySerializer
  implements ObjectSerializer
{
  public static final FloatArraySerializer instance = new FloatArraySerializer();
  
  public final void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
    {
      if (localSerializeWriter.isEnabled(SerializerFeature.WriteNullListAsEmpty))
      {
        localSerializeWriter.write("[]");
        return;
      }
      localSerializeWriter.writeNull();
      return;
    }
    float[] arrayOfFloat = (float[])paramObject1;
    int i = -1 + arrayOfFloat.length;
    if (i == -1)
    {
      localSerializeWriter.append("[]");
      return;
    }
    localSerializeWriter.append('[');
    int j = 0;
    if (j < i)
    {
      float f2 = arrayOfFloat[j];
      if (Float.isNaN(f2)) {
        localSerializeWriter.writeNull();
      }
      for (;;)
      {
        localSerializeWriter.append(',');
        j++;
        break;
        localSerializeWriter.append(Float.toString(f2));
      }
    }
    float f1 = arrayOfFloat[i];
    if (Float.isNaN(f1)) {
      localSerializeWriter.writeNull();
    }
    for (;;)
    {
      localSerializeWriter.append(']');
      return;
      localSerializeWriter.append(Float.toString(f1));
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\FloatArraySerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */