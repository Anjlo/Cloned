package com.alibaba.fastjson.serializer;

import java.util.HashSet;
import java.util.Set;

public class SimplePropertyPreFilter
  implements PropertyPreFilter
{
  private final Class<?> clazz;
  private final Set<String> excludes = new HashSet();
  private final Set<String> includes = new HashSet();
  
  public SimplePropertyPreFilter(Class<?> paramClass, String... paramVarArgs)
  {
    this.clazz = paramClass;
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++)
    {
      String str = paramVarArgs[j];
      if (str != null) {
        this.includes.add(str);
      }
    }
  }
  
  public SimplePropertyPreFilter(String... paramVarArgs)
  {
    this(null, paramVarArgs);
  }
  
  public boolean apply(JSONSerializer paramJSONSerializer, Object paramObject, String paramString)
  {
    if (paramObject == null) {}
    do
    {
      do
      {
        return true;
      } while ((this.clazz != null) && (!this.clazz.isInstance(paramObject)));
      if (this.excludes.contains(paramString)) {
        return false;
      }
    } while ((this.includes.size() == 0) || (this.includes.contains(paramString)));
    return false;
  }
  
  public Class<?> getClazz()
  {
    return this.clazz;
  }
  
  public Set<String> getExcludes()
  {
    return this.excludes;
  }
  
  public Set<String> getIncludes()
  {
    return this.includes;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\SimplePropertyPreFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */