package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;
import java.util.Map;

public final class MapResolveFieldDeserializer
  extends FieldDeserializer
{
  private final String key;
  private final Map map;
  
  public MapResolveFieldDeserializer(Map paramMap, String paramString)
  {
    super(null, null);
    this.key = paramString;
    this.map = paramMap;
  }
  
  public int getFastMatchToken()
  {
    return 0;
  }
  
  public void parseField(DefaultJSONParser paramDefaultJSONParser, Object paramObject, Type paramType, Map<String, Object> paramMap) {}
  
  public void setValue(Object paramObject1, Object paramObject2)
  {
    this.map.put(this.key, paramObject2);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\MapResolveFieldDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */