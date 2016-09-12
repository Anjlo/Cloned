package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

public class ASMClassLoader
  extends ClassLoader
{
  private static ProtectionDomain DOMAIN = (ProtectionDomain)AccessController.doPrivileged(new PrivilegedAction()
  {
    public Object run()
    {
      return ASMClassLoader.class.getProtectionDomain();
    }
  });
  
  public ASMClassLoader()
  {
    super(getParentClassLoader());
  }
  
  public static Class<?> forName(String paramString)
  {
    try
    {
      Class localClass = Thread.currentThread().getContextClassLoader().loadClass(paramString);
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new JSONException("class nout found : " + paramString);
    }
  }
  
  static ClassLoader getParentClassLoader()
  {
    ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
    try
    {
      localClassLoader.loadClass(JSON.class.getName());
      return localClassLoader;
    }
    catch (ClassNotFoundException localClassNotFoundException) {}
    return JSON.class.getClassLoader();
  }
  
  public Class<?> defineClassPublic(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws ClassFormatError
  {
    return defineClass(paramString, paramArrayOfByte, paramInt1, paramInt2, DOMAIN);
  }
  
  public boolean isExternalClass(Class<?> paramClass)
  {
    ClassLoader localClassLoader = paramClass.getClassLoader();
    if (localClassLoader == null) {
      return false;
    }
    for (Object localObject = this;; localObject = ((ClassLoader)localObject).getParent())
    {
      if (localObject == null) {
        break label30;
      }
      if (localObject == localClassLoader) {
        break;
      }
    }
    label30:
    return true;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\util\ASMClassLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */