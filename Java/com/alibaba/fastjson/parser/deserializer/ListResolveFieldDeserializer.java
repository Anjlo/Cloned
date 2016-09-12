package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public final class ListResolveFieldDeserializer
  extends FieldDeserializer
{
  private final int index;
  private final List list;
  private final DefaultJSONParser parser;
  
  public ListResolveFieldDeserializer(DefaultJSONParser paramDefaultJSONParser, List paramList, int paramInt)
  {
    super(null, null);
    this.parser = paramDefaultJSONParser;
    this.index = paramInt;
    this.list = paramList;
  }
  
  public int getFastMatchToken()
  {
    return 0;
  }
  
  public DefaultJSONParser getParser()
  {
    return this.parser;
  }
  
  public void parseField(DefaultJSONParser paramDefaultJSONParser, Object paramObject, Type paramType, Map<String, Object> paramMap) {}
  
  public void setValue(Object paramObject1, Object paramObject2)
  {
    this.list.set(this.index, paramObject2);
    JSONArray localJSONArray;
    Object localObject1;
    if ((this.list instanceof JSONArray))
    {
      localJSONArray = (JSONArray)this.list;
      localObject1 = localJSONArray.getRelatedArray();
      if ((localObject1 != null) && (Array.getLength(localObject1) > this.index)) {
        if (localJSONArray.getComponentType() == null) {
          break label96;
        }
      }
    }
    label96:
    for (Object localObject2 = TypeUtils.cast(paramObject2, localJSONArray.getComponentType(), this.parser.getConfig());; localObject2 = paramObject2)
    {
      Array.set(localObject1, this.index, localObject2);
      return;
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\ListResolveFieldDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */