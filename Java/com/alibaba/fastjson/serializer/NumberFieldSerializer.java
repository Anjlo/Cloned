package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.util.FieldInfo;

class NumberFieldSerializer
  extends FieldSerializer
{
  public NumberFieldSerializer(FieldInfo paramFieldInfo)
  {
    super(paramFieldInfo);
  }
  
  public void writeProperty(JSONSerializer paramJSONSerializer, Object paramObject)
    throws Exception
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    writePrefix(paramJSONSerializer);
    if (paramObject == null)
    {
      if (localSerializeWriter.isEnabled(SerializerFeature.WriteNullNumberAsZero))
      {
        localSerializeWriter.write('0');
        return;
      }
      localSerializeWriter.writeNull();
      return;
    }
    localSerializeWriter.append(paramObject.toString());
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\NumberFieldSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */