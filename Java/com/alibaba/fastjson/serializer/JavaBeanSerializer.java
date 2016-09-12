package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JavaBeanSerializer
  implements ObjectSerializer
{
  private final FieldSerializer[] getters;
  private final FieldSerializer[] sortedGetters;
  
  public JavaBeanSerializer(Class<?> paramClass)
  {
    this(paramClass, (Map)null);
  }
  
  public JavaBeanSerializer(Class<?> paramClass, Map<String, String> paramMap)
  {
    ArrayList localArrayList1 = new ArrayList();
    Iterator localIterator1 = TypeUtils.computeGetters(paramClass, paramMap, false).iterator();
    while (localIterator1.hasNext()) {
      localArrayList1.add(createFieldSerializer((FieldInfo)localIterator1.next()));
    }
    this.getters = ((FieldSerializer[])localArrayList1.toArray(new FieldSerializer[localArrayList1.size()]));
    ArrayList localArrayList2 = new ArrayList();
    Iterator localIterator2 = TypeUtils.computeGetters(paramClass, paramMap, true).iterator();
    while (localIterator2.hasNext()) {
      localArrayList2.add(createFieldSerializer((FieldInfo)localIterator2.next()));
    }
    this.sortedGetters = ((FieldSerializer[])localArrayList2.toArray(new FieldSerializer[localArrayList2.size()]));
  }
  
  public JavaBeanSerializer(Class<?> paramClass, String... paramVarArgs)
  {
    this(paramClass, createAliasMap(paramVarArgs));
  }
  
  static Map<String, String> createAliasMap(String... paramVarArgs)
  {
    HashMap localHashMap = new HashMap();
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++)
    {
      String str = paramVarArgs[j];
      localHashMap.put(str, str);
    }
    return localHashMap;
  }
  
  public FieldSerializer createFieldSerializer(FieldInfo paramFieldInfo)
  {
    if (paramFieldInfo.getFieldClass() == Number.class) {
      return new NumberFieldSerializer(paramFieldInfo);
    }
    return new ObjectFieldSerializer(paramFieldInfo);
  }
  
  public FieldSerializer[] getGetters()
  {
    return this.getters;
  }
  
  protected boolean isWriteClassName(JSONSerializer paramJSONSerializer, Object paramObject1, Type paramType, Object paramObject2)
  {
    return paramJSONSerializer.isWriteClassName(paramType, paramObject1);
  }
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    if (paramJSONSerializer.containsReference(paramObject1))
    {
      writeReference(paramJSONSerializer, paramObject1);
      return;
    }
    FieldSerializer[] arrayOfFieldSerializer;
    SerialContext localSerialContext;
    if (localSerializeWriter.isEnabled(SerializerFeature.SortField))
    {
      arrayOfFieldSerializer = this.sortedGetters;
      localSerialContext = paramJSONSerializer.getContext();
      paramJSONSerializer.setContext(localSerialContext, paramObject1, paramObject2);
    }
    int i;
    label152:
    int j;
    FieldSerializer localFieldSerializer;
    try
    {
      localSerializeWriter.append('{');
      if ((arrayOfFieldSerializer.length > 0) && (localSerializeWriter.isEnabled(SerializerFeature.PrettyFormat)))
      {
        paramJSONSerializer.incrementIndent();
        paramJSONSerializer.println();
      }
      boolean bool1 = isWriteClassName(paramJSONSerializer, paramObject1, paramType, paramObject2);
      i = 0;
      if (!bool1) {
        break label449;
      }
      Class localClass = paramObject1.getClass();
      i = 0;
      if (localClass == paramType) {
        break label449;
      }
      localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
      paramJSONSerializer.write(paramObject1.getClass());
      i = 1;
    }
    catch (Exception localException)
    {
      Field localField;
      boolean bool2;
      localJSONException = new JSONException("write javaBean error", localException);
      throw localJSONException;
    }
    finally
    {
      paramJSONSerializer.setContext(localSerialContext);
    }
    if (j < arrayOfFieldSerializer.length)
    {
      localFieldSerializer = arrayOfFieldSerializer[j];
      if (paramJSONSerializer.isEnabled(SerializerFeature.SkipTransientField))
      {
        localField = localFieldSerializer.getField();
        if (localField != null)
        {
          bool2 = Modifier.isTransient(localField.getModifiers());
          if (!bool2) {}
        }
      }
    }
    for (;;)
    {
      j++;
      break label152;
      arrayOfFieldSerializer = this.getters;
      break;
      Object localObject2 = localFieldSerializer.getPropertyValue(paramObject1);
      if (FilterUtils.apply(paramJSONSerializer, paramObject1, localFieldSerializer.getName(), localObject2))
      {
        String str = FilterUtils.processKey(paramJSONSerializer, paramObject1, localFieldSerializer.getName(), localObject2);
        Object localObject3 = FilterUtils.processValue(paramJSONSerializer, paramObject1, localFieldSerializer.getName(), localObject2);
        if ((localObject3 != null) || (localFieldSerializer.isWriteNull()) || (paramJSONSerializer.isEnabled(SerializerFeature.WriteMapNullValue)))
        {
          if (i != 0)
          {
            localSerializeWriter.append(',');
            if (localSerializeWriter.isEnabled(SerializerFeature.PrettyFormat)) {
              paramJSONSerializer.println();
            }
          }
          if (str != localFieldSerializer.getName())
          {
            localSerializeWriter.writeFieldName(str);
            paramJSONSerializer.write(localObject3);
          }
          else if (localObject2 != localObject3)
          {
            localFieldSerializer.writePrefix(paramJSONSerializer);
            paramJSONSerializer.write(localObject3);
          }
          else
          {
            JSONException localJSONException;
            localFieldSerializer.writeProperty(paramJSONSerializer, localObject3);
            break label455;
            if ((arrayOfFieldSerializer.length > 0) && (localSerializeWriter.isEnabled(SerializerFeature.PrettyFormat)))
            {
              paramJSONSerializer.decrementIdent();
              paramJSONSerializer.println();
            }
            localSerializeWriter.append('}');
            paramJSONSerializer.setContext(localSerialContext);
            return;
            label449:
            j = 0;
            break label152;
          }
          label455:
          i = 1;
        }
      }
    }
  }
  
  public void writeReference(JSONSerializer paramJSONSerializer, Object paramObject)
  {
    paramJSONSerializer.writeReference(paramObject);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\JavaBeanSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */