package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Type;

public class ArrayDeserializer
  implements ObjectDeserializer
{
  public static final ArrayDeserializer instance = new ArrayDeserializer();
  
  private <T> T toObjectArray(DefaultJSONParser paramDefaultJSONParser, Class<T> paramClass, JSONArray paramJSONArray)
  {
    if (paramJSONArray == null) {
      return null;
    }
    int i = paramJSONArray.size();
    Class localClass = paramClass.getComponentType();
    Object localObject1 = Array.newInstance(localClass, i);
    int j = 0;
    if (j < i)
    {
      Object localObject2 = paramJSONArray.get(j);
      Object localObject3;
      if (localClass.isArray()) {
        if (localClass.isInstance(localObject2))
        {
          localObject3 = localObject2;
          label67:
          Array.set(localObject1, j, localObject3);
        }
      }
      for (;;)
      {
        j++;
        break;
        localObject3 = toObjectArray(paramDefaultJSONParser, localClass, (JSONArray)localObject2);
        break label67;
        Array.set(localObject1, j, TypeUtils.cast(localObject2, localClass, paramDefaultJSONParser.getConfig()));
      }
    }
    paramJSONArray.setRelatedArray(localObject1);
    paramJSONArray.setComponentType(localClass);
    return (T)localObject1;
  }
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken(16);
      return null;
    }
    if (localJSONLexer.token() == 4)
    {
      byte[] arrayOfByte = localJSONLexer.bytesValue();
      localJSONLexer.nextToken(16);
      return arrayOfByte;
    }
    Class localClass1 = (Class)paramType;
    Class localClass2 = localClass1.getComponentType();
    JSONArray localJSONArray = new JSONArray();
    paramDefaultJSONParser.parseArray(localClass2, localJSONArray, paramObject);
    return (T)toObjectArray(paramDefaultJSONParser, localClass1, localJSONArray);
  }
  
  public int getFastMatchToken()
  {
    return 14;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\ArrayDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */