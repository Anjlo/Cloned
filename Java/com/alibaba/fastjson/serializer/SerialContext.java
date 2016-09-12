package com.alibaba.fastjson.serializer;

public class SerialContext
{
  private int features = 0;
  private final Object fieldName;
  private final Object object;
  private final SerialContext parent;
  
  public SerialContext(SerialContext paramSerialContext, Object paramObject1, Object paramObject2)
  {
    this.parent = paramSerialContext;
    this.object = paramObject1;
    this.fieldName = paramObject2;
  }
  
  public int getFeatures()
  {
    return this.features;
  }
  
  public Object getFieldName()
  {
    return this.fieldName;
  }
  
  public Object getObject()
  {
    return this.object;
  }
  
  public SerialContext getParent()
  {
    return this.parent;
  }
  
  public String getPath()
  {
    if (this.parent == null) {
      return "$";
    }
    if ((this.fieldName instanceof Integer)) {
      return this.parent.getPath() + "[" + this.fieldName + "]";
    }
    return this.parent.getPath() + "." + this.fieldName;
  }
  
  public void setFeatures(int paramInt)
  {
    this.features = paramInt;
  }
  
  public String toString()
  {
    return getPath();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\SerialContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */