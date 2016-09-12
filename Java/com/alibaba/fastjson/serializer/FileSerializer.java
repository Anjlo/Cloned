package com.alibaba.fastjson.serializer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

public class FileSerializer
  implements ObjectSerializer
{
  public static FileSerializer instance = new FileSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    paramJSONSerializer.write(((File)paramObject1).getPath());
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\FileSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */