package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ArrayListStringFieldDeserializer
  extends FieldDeserializer
{
  public ArrayListStringFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    super(paramClass, paramFieldInfo);
  }
  
  public int getFastMatchToken()
  {
    return 14;
  }
  
  public void parseField(DefaultJSONParser paramDefaultJSONParser, Object paramObject, Type paramType, Map<String, Object> paramMap)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    Object localObject;
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken(16);
      localObject = null;
    }
    while (paramObject == null)
    {
      paramMap.put(this.fieldInfo.getName(), localObject);
      return;
      localObject = new ArrayList();
      ArrayListStringDeserializer.parseArray(paramDefaultJSONParser, (Collection)localObject);
    }
    setValue(paramObject, localObject);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\ArrayListStringFieldDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */