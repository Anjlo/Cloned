package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Type;
import java.util.Map;

public class StringFieldDeserializer
  extends FieldDeserializer
{
  private final ObjectDeserializer fieldValueDeserilizer;
  
  public StringFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    super(paramClass, paramFieldInfo);
    this.fieldValueDeserilizer = paramParserConfig.getDeserializer(paramFieldInfo);
  }
  
  public int getFastMatchToken()
  {
    return this.fieldValueDeserilizer.getFastMatchToken();
  }
  
  public void parseField(DefaultJSONParser paramDefaultJSONParser, Object paramObject, Type paramType, Map<String, Object> paramMap)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    String str;
    if (localJSONLexer.token() == 4)
    {
      str = localJSONLexer.stringVal();
      localJSONLexer.nextToken(16);
    }
    while (paramObject == null)
    {
      paramMap.put(this.fieldInfo.getName(), str);
      return;
      Object localObject = paramDefaultJSONParser.parse();
      if (localObject == null) {
        str = null;
      } else {
        str = localObject.toString();
      }
    }
    setValue(paramObject, str);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\StringFieldDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */