package com.alibaba.fastjson.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class ParameterizedTypeImpl
  implements ParameterizedType
{
  private final Type[] actualTypeArguments;
  private int hash;
  private final Type ownerType;
  private final Type rawType;
  
  public ParameterizedTypeImpl(Type[] paramArrayOfType, Type paramType1, Type paramType2)
  {
    if ((paramArrayOfType == null) || (paramArrayOfType.length == 0) || (paramType2 == null)) {
      throw new IllegalArgumentException();
    }
    this.actualTypeArguments = paramArrayOfType;
    this.ownerType = paramType1;
    this.rawType = paramType2;
    if (paramType1 != null) {}
    for (int i = paramType1.hashCode();; i = 0)
    {
      this.hash = (i + 31);
      this.hash = (31 * this.hash + paramType2.hashCode());
      this.hash = (31 * this.hash + Arrays.hashCode(paramArrayOfType));
      return;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    ParameterizedType localParameterizedType;
    do
    {
      do
      {
        return true;
        if (paramObject == null) {
          return false;
        }
        if (!(paramObject instanceof ParameterizedType)) {
          return false;
        }
        localParameterizedType = (ParameterizedType)paramObject;
        if (!Arrays.equals(this.actualTypeArguments, localParameterizedType.getActualTypeArguments())) {
          return false;
        }
        if (this.ownerType == null)
        {
          if (localParameterizedType.getOwnerType() != null) {
            return false;
          }
        }
        else if (!this.ownerType.equals(localParameterizedType.getOwnerType())) {
          return false;
        }
        if (this.rawType != null) {
          break;
        }
      } while (localParameterizedType.getRawType() == null);
      return false;
    } while (this.rawType.equals(localParameterizedType.getRawType()));
    return false;
  }
  
  public Type[] getActualTypeArguments()
  {
    return this.actualTypeArguments;
  }
  
  public Type getOwnerType()
  {
    return this.ownerType;
  }
  
  public Type getRawType()
  {
    return this.rawType;
  }
  
  public int hashCode()
  {
    return this.hash;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\util\ParameterizedTypeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */