package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.Map;

public class IntegerFieldDeserializer
  extends FieldDeserializer
{
  public IntegerFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    super(paramClass, paramFieldInfo);
  }
  
  public int getFastMatchToken()
  {
    return 2;
  }
  
  public void parseField(DefaultJSONParser paramDefaultJSONParser, Object paramObject, Type paramType, Map<String, Object> paramMap)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    int i;
    if (localJSONLexer.token() == 2)
    {
      i = localJSONLexer.intValue();
      localJSONLexer.nextToken(16);
      if (paramObject == null) {
        paramMap.put(this.fieldInfo.getName(), Integer.valueOf(i));
      }
    }
    Object localObject;
    for (;;)
    {
      return;
      setValue(paramObject, i);
      return;
      if (localJSONLexer.token() == 8)
      {
        localObject = null;
        localJSONLexer.nextToken(16);
      }
      while ((localObject != null) || (getFieldClass() != Integer.TYPE))
      {
        if (paramObject != null) {
          break label141;
        }
        paramMap.put(this.fieldInfo.getName(), localObject);
        return;
        localObject = TypeUtils.castToInt(paramDefaultJSONParser.parse());
      }
    }
    label141:
    setValue(paramObject, localObject);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\IntegerFieldDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */