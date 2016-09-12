package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class CharacterSerializer
  implements ObjectSerializer
{
  public static final CharacterSerializer instance = new CharacterSerializer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Character localCharacter = (Character)paramObject1;
    if (localCharacter == null)
    {
      localSerializeWriter.writeString("");
      return;
    }
    if (localCharacter.charValue() == 0)
    {
      localSerializeWriter.writeString("\000");
      return;
    }
    localSerializeWriter.writeString(localCharacter.toString());
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\CharacterSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */