package com.alibaba.fastjson.parser;

@Deprecated
public class JavaBeanMapping
  extends ParserConfig
{
  private static final JavaBeanMapping instance = new JavaBeanMapping();
  
  public static JavaBeanMapping getGlobalInstance()
  {
    return instance;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\JavaBeanMapping.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */