package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class DeserializeBeanInfo
{
  private final Class<?> clazz;
  private Constructor<?> creatorConstructor;
  private Constructor<?> defaultConstructor;
  private Method factoryMethod;
  private final List<FieldInfo> fieldList = new ArrayList();
  private final Type type;
  
  public DeserializeBeanInfo(Class<?> paramClass)
  {
    this.clazz = paramClass;
    this.type = paramClass;
  }
  
  public static DeserializeBeanInfo computeSetters(Class<?> paramClass)
  {
    return computeSetters(paramClass, paramClass);
  }
  
  public static DeserializeBeanInfo computeSetters(Class<?> paramClass, Type paramType)
  {
    DeserializeBeanInfo localDeserializeBeanInfo = new DeserializeBeanInfo(paramClass);
    Constructor localConstructor1 = getDefaultConstructor(paramClass);
    int i7;
    label42:
    Method localMethod3;
    String str3;
    if (localConstructor1 != null)
    {
      localConstructor1.setAccessible(true);
      localDeserializeBeanInfo.setDefaultConstructor(localConstructor1);
      Method[] arrayOfMethod1 = paramClass.getMethods();
      int i6 = arrayOfMethod1.length;
      i7 = 0;
      if (i7 >= i6) {
        break label786;
      }
      localMethod3 = arrayOfMethod1[i7];
      str3 = localMethod3.getName();
      if (str3.length() >= 4) {
        break label512;
      }
    }
    for (;;)
    {
      i7++;
      break label42;
      if ((localConstructor1 != null) || (paramClass.isInterface()) || (Modifier.isAbstract(paramClass.getModifiers()))) {
        break;
      }
      Constructor localConstructor2 = getCreatorConstructor(paramClass);
      if (localConstructor2 != null)
      {
        localConstructor2.setAccessible(true);
        localDeserializeBeanInfo.setCreatorConstructor(localConstructor2);
        for (int i1 = 0;; i1++)
        {
          int i2 = localConstructor2.getParameterTypes().length;
          if (i1 >= i2) {
            break;
          }
          Annotation[] arrayOfAnnotation2 = localConstructor2.getParameterAnnotations()[i1];
          int i3 = arrayOfAnnotation2.length;
          JSONField localJSONField2;
          for (int i4 = 0;; i4++)
          {
            int i5 = i4;
            localJSONField2 = null;
            if (i5 < i3)
            {
              Annotation localAnnotation2 = arrayOfAnnotation2[i4];
              if ((localAnnotation2 instanceof JSONField)) {
                localJSONField2 = (JSONField)localAnnotation2;
              }
            }
            else
            {
              if (localJSONField2 != null) {
                break;
              }
              throw new JSONException("illegal json creator");
            }
          }
          Class localClass2 = localConstructor2.getParameterTypes()[i1];
          Type localType2 = localConstructor2.getGenericParameterTypes()[i1];
          Field localField2 = getField(paramClass, localJSONField2.name());
          if (localField2 != null) {
            localField2.setAccessible(true);
          }
          localDeserializeBeanInfo.add(new FieldInfo(localJSONField2.name(), paramClass, localClass2, localType2, null, localField2));
        }
      }
      Method localMethod1 = getFactoryMethod(paramClass);
      if (localMethod1 != null)
      {
        localMethod1.setAccessible(true);
        localDeserializeBeanInfo.setFactoryMethod(localMethod1);
        for (int i = 0;; i++)
        {
          int j = localMethod1.getParameterTypes().length;
          if (i >= j) {
            break;
          }
          Annotation[] arrayOfAnnotation1 = localMethod1.getParameterAnnotations()[i];
          int k = arrayOfAnnotation1.length;
          JSONField localJSONField1;
          for (int m = 0;; m++)
          {
            int n = m;
            localJSONField1 = null;
            if (n < k)
            {
              Annotation localAnnotation1 = arrayOfAnnotation1[m];
              if ((localAnnotation1 instanceof JSONField)) {
                localJSONField1 = (JSONField)localAnnotation1;
              }
            }
            else
            {
              if (localJSONField1 != null) {
                break;
              }
              throw new JSONException("illegal json creator");
            }
          }
          Class localClass1 = localMethod1.getParameterTypes()[i];
          Type localType1 = localMethod1.getGenericParameterTypes()[i];
          Field localField1 = getField(paramClass, localJSONField1.name());
          if (localField1 != null) {
            localField1.setAccessible(true);
          }
          localDeserializeBeanInfo.add(new FieldInfo(localJSONField1.name(), paramClass, localClass1, localType1, null, localField1));
        }
      }
      throw new JSONException("default constructor not found. " + paramClass);
      label512:
      if ((!Modifier.isStatic(localMethod3.getModifiers())) && ((localMethod3.getReturnType().equals(Void.TYPE)) || (localMethod3.getReturnType().equals(paramClass))) && (localMethod3.getParameterTypes().length == 1))
      {
        JSONField localJSONField3 = (JSONField)localMethod3.getAnnotation(JSONField.class);
        if (localJSONField3 != null)
        {
          if (!localJSONField3.deserialize()) {
            continue;
          }
          if (localJSONField3.name().length() != 0)
          {
            localDeserializeBeanInfo.add(new FieldInfo(localJSONField3.name(), localMethod3, null, paramClass, paramType));
            localMethod3.setAccessible(true);
            continue;
          }
        }
        if ((str3.startsWith("set")) && (Character.isUpperCase(str3.charAt(3))))
        {
          String str4 = Character.toLowerCase(str3.charAt(3)) + str3.substring(4);
          Field localField4 = getField(paramClass, str4);
          if (localField4 != null)
          {
            JSONField localJSONField4 = (JSONField)localField4.getAnnotation(JSONField.class);
            if ((localJSONField4 != null) && (localJSONField4.name().length() != 0))
            {
              localDeserializeBeanInfo.add(new FieldInfo(localJSONField4.name(), localMethod3, localField4, paramClass, paramType));
              continue;
            }
          }
          localDeserializeBeanInfo.add(new FieldInfo(str4, localMethod3, null, paramClass, paramType));
          localMethod3.setAccessible(true);
        }
      }
    }
    label786:
    Field[] arrayOfField = paramClass.getFields();
    int i8 = arrayOfField.length;
    int i9 = 0;
    if (i9 < i8)
    {
      Field localField3 = arrayOfField[i9];
      if (Modifier.isStatic(localField3.getModifiers())) {}
      for (;;)
      {
        i9++;
        break;
        if (Modifier.isPublic(localField3.getModifiers()))
        {
          int i12 = 0;
          Iterator localIterator = localDeserializeBeanInfo.getFieldList().iterator();
          while (localIterator.hasNext()) {
            if (((FieldInfo)localIterator.next()).getName().equals(localField3.getName())) {
              i12 = 1;
            }
          }
          if (i12 == 0) {
            localDeserializeBeanInfo.add(new FieldInfo(localField3.getName(), null, localField3, paramClass, paramType));
          }
        }
      }
    }
    Method[] arrayOfMethod2 = paramClass.getMethods();
    int i10 = arrayOfMethod2.length;
    int i11 = 0;
    if (i11 < i10)
    {
      Method localMethod2 = arrayOfMethod2[i11];
      String str1 = localMethod2.getName();
      if (str1.length() < 4) {}
      for (;;)
      {
        i11++;
        break;
        if ((!Modifier.isStatic(localMethod2.getModifiers())) && (str1.startsWith("get")) && (Character.isUpperCase(str1.charAt(3))) && (localMethod2.getParameterTypes().length == 0) && (Collection.class.isAssignableFrom(localMethod2.getReturnType())))
        {
          String str2 = Character.toLowerCase(str1.charAt(3)) + str1.substring(4);
          if (localDeserializeBeanInfo.getField(str2) == null)
          {
            localDeserializeBeanInfo.add(new FieldInfo(str2, localMethod2, null, paramClass, paramType));
            localMethod2.setAccessible(true);
          }
        }
      }
    }
    return localDeserializeBeanInfo;
  }
  
  public static Constructor<?> getCreatorConstructor(Class<?> paramClass)
  {
    Constructor[] arrayOfConstructor = paramClass.getDeclaredConstructors();
    int i = arrayOfConstructor.length;
    for (int j = 0;; j++)
    {
      Object localObject = null;
      if (j < i)
      {
        Constructor localConstructor = arrayOfConstructor[j];
        if ((JSONCreator)localConstructor.getAnnotation(JSONCreator.class) != null)
        {
          if (0 != 0) {
            throw new JSONException("multi-json creator");
          }
          localObject = localConstructor;
        }
      }
      else
      {
        return (Constructor<?>)localObject;
      }
    }
  }
  
  public static Constructor<?> getDefaultConstructor(Class<?> paramClass)
  {
    Object localObject;
    if (Modifier.isAbstract(paramClass.getModifiers())) {
      localObject = null;
    }
    label142:
    for (;;)
    {
      return (Constructor<?>)localObject;
      Constructor[] arrayOfConstructor1 = paramClass.getDeclaredConstructors();
      int i = arrayOfConstructor1.length;
      int j = 0;
      localObject = null;
      Constructor[] arrayOfConstructor2;
      int k;
      if (j < i)
      {
        Constructor localConstructor2 = arrayOfConstructor1[j];
        if (localConstructor2.getParameterTypes().length == 0) {
          localObject = localConstructor2;
        }
      }
      else
      {
        if ((localObject != null) || (!paramClass.isMemberClass()) || (Modifier.isStatic(paramClass.getModifiers()))) {
          continue;
        }
        arrayOfConstructor2 = paramClass.getDeclaredConstructors();
        k = arrayOfConstructor2.length;
      }
      for (int m = 0;; m++)
      {
        if (m >= k) {
          break label142;
        }
        Constructor localConstructor1 = arrayOfConstructor2[m];
        if ((localConstructor1.getParameterTypes().length == 1) && (localConstructor1.getParameterTypes()[0].equals(paramClass.getDeclaringClass())))
        {
          return localConstructor1;
          j++;
          break;
        }
      }
    }
  }
  
  public static Method getFactoryMethod(Class<?> paramClass)
  {
    Method[] arrayOfMethod = paramClass.getDeclaredMethods();
    int i = arrayOfMethod.length;
    int j = 0;
    Object localObject = null;
    if (j < i)
    {
      Method localMethod = arrayOfMethod[j];
      if (!Modifier.isStatic(localMethod.getModifiers())) {}
      while ((!paramClass.isAssignableFrom(localMethod.getReturnType())) || ((JSONCreator)localMethod.getAnnotation(JSONCreator.class) == null))
      {
        j++;
        break;
      }
      if (0 != 0) {
        throw new JSONException("multi-json creator");
      }
      localObject = localMethod;
    }
    return (Method)localObject;
  }
  
  public static Field getField(Class<?> paramClass, String paramString)
  {
    try
    {
      Field localField = paramClass.getDeclaredField(paramString);
      return localField;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public boolean add(FieldInfo paramFieldInfo)
  {
    Iterator localIterator = this.fieldList.iterator();
    while (localIterator.hasNext()) {
      if (((FieldInfo)localIterator.next()).getName().equals(paramFieldInfo.getName())) {
        return false;
      }
    }
    this.fieldList.add(paramFieldInfo);
    return true;
  }
  
  public Class<?> getClazz()
  {
    return this.clazz;
  }
  
  public Constructor<?> getCreatorConstructor()
  {
    return this.creatorConstructor;
  }
  
  public Constructor<?> getDefaultConstructor()
  {
    return this.defaultConstructor;
  }
  
  public Method getFactoryMethod()
  {
    return this.factoryMethod;
  }
  
  public FieldInfo getField(String paramString)
  {
    Iterator localIterator = this.fieldList.iterator();
    while (localIterator.hasNext())
    {
      FieldInfo localFieldInfo = (FieldInfo)localIterator.next();
      if (localFieldInfo.getName().equals(paramString)) {
        return localFieldInfo;
      }
    }
    return null;
  }
  
  public List<FieldInfo> getFieldList()
  {
    return this.fieldList;
  }
  
  public Type getType()
  {
    return this.type;
  }
  
  public void setCreatorConstructor(Constructor<?> paramConstructor)
  {
    this.creatorConstructor = paramConstructor;
  }
  
  public void setDefaultConstructor(Constructor<?> paramConstructor)
  {
    this.defaultConstructor = paramConstructor;
  }
  
  public void setFactoryMethod(Method paramMethod)
  {
    this.factoryMethod = paramMethod;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\util\DeserializeBeanInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */