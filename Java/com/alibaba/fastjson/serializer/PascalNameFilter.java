package com.alibaba.fastjson.serializer;

public class PascalNameFilter
  implements NameFilter
{
  public String process(Object paramObject1, String paramString, Object paramObject2)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return paramString;
    }
    char c = Character.toUpperCase(paramString.charAt(0));
    return c + paramString.substring(1);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\PascalNameFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */