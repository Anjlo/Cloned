package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.DeserializeBeanInfo;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class JavaBeanDeserializer
  implements ObjectDeserializer
{
  private DeserializeBeanInfo beanInfo;
  private final Class<?> clazz;
  private final Map<String, FieldDeserializer> feildDeserializerMap = new IdentityHashMap();
  private final List<FieldDeserializer> fieldDeserializers = new ArrayList();
  private final Type type;
  
  public JavaBeanDeserializer(ParserConfig paramParserConfig, Class<?> paramClass)
  {
    this(paramParserConfig, paramClass, paramClass);
  }
  
  public JavaBeanDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, Type paramType)
  {
    this.clazz = paramClass;
    this.type = paramType;
    this.beanInfo = DeserializeBeanInfo.computeSetters(paramClass, paramType);
    Iterator localIterator = this.beanInfo.getFieldList().iterator();
    while (localIterator.hasNext()) {
      addFieldDeserializer(paramParserConfig, paramClass, (FieldInfo)localIterator.next());
    }
  }
  
  public JavaBeanDeserializer(DeserializeBeanInfo paramDeserializeBeanInfo)
  {
    this.beanInfo = paramDeserializeBeanInfo;
    this.clazz = paramDeserializeBeanInfo.getClazz();
    this.type = paramDeserializeBeanInfo.getType();
  }
  
  private void addFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    FieldDeserializer localFieldDeserializer = createFieldDeserializer(paramParserConfig, paramClass, paramFieldInfo);
    this.feildDeserializerMap.put(paramFieldInfo.getName().intern(), localFieldDeserializer);
    this.fieldDeserializers.add(localFieldDeserializer);
  }
  
  public FieldDeserializer createFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    return paramParserConfig.createFieldDeserializer(paramParserConfig, paramClass, paramFieldInfo);
  }
  
  public Object createInstance(DefaultJSONParser paramDefaultJSONParser, Type paramType)
  {
    if (((paramType instanceof Class)) && (this.clazz.isInterface()))
    {
      Class localClass = (Class)paramType;
      ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
      JSONObject localJSONObject = new JSONObject();
      return Proxy.newProxyInstance(localClassLoader, new Class[] { localClass }, localJSONObject);
    }
    if (this.beanInfo.getDefaultConstructor() == null) {
      return null;
    }
    try
    {
      Constructor localConstructor = this.beanInfo.getDefaultConstructor();
      Object localObject3;
      if (localConstructor.getParameterTypes().length == 0) {
        localObject3 = localConstructor.newInstance(new Object[0]);
      }
      Object localObject1;
      for (Object localObject2 = localObject3; paramDefaultJSONParser.isEnabled(Feature.InitStringFieldAsEmpty); localObject2 = localObject1)
      {
        Iterator localIterator = this.beanInfo.getFieldList().iterator();
        for (;;)
        {
          if (!localIterator.hasNext()) {
            break label275;
          }
          FieldInfo localFieldInfo = (FieldInfo)localIterator.next();
          if (localFieldInfo.getFieldClass() == String.class) {
            try
            {
              localFieldInfo.set(localObject2, "");
            }
            catch (Exception localException2)
            {
              throw new JSONException("create instance error, class " + this.clazz.getName(), localException2);
            }
          }
        }
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramDefaultJSONParser.getContext().getObject();
        localObject1 = localConstructor.newInstance(arrayOfObject);
      }
      return localObject2;
    }
    catch (Exception localException1)
    {
      throw new JSONException("create instance error, class " + this.clazz.getName(), localException1);
    }
  }
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONScanner localJSONScanner = (JSONScanner)paramDefaultJSONParser.getLexer();
    if (localJSONScanner.token() == 8)
    {
      localJSONScanner.nextToken(16);
      return null;
    }
    ParseContext localParseContext1 = paramDefaultJSONParser.getContext();
    Object localObject1 = null;
    Object localObject2 = null;
    try
    {
      if (localJSONScanner.token() == 13)
      {
        localJSONScanner.nextToken(16);
        Object localObject13 = createInstance(paramDefaultJSONParser, paramType);
        if (0 != 0) {
          null.setObject(null);
        }
        paramDefaultJSONParser.setContext(localParseContext1);
        return (T)localObject13;
      }
      int i = localJSONScanner.token();
      localObject1 = null;
      localObject2 = null;
      if (i != 12)
      {
        int j = localJSONScanner.token();
        localObject1 = null;
        localObject2 = null;
        if (j != 16)
        {
          StringBuffer localStringBuffer = new StringBuffer().append("syntax error, expect {, actual ").append(localJSONScanner.tokenName()).append(", pos ").append(localJSONScanner.pos());
          if ((paramObject instanceof String)) {
            localStringBuffer.append(", fieldName ").append(paramObject);
          }
          throw new JSONException(localStringBuffer.toString());
        }
      }
    }
    finally {}
    for (;;)
    {
      if (localObject1 != null) {
        ((ParseContext)localObject1).setObject(localObject2);
      }
      paramDefaultJSONParser.setContext(localParseContext1);
      throw ((Throwable)localObject3);
      if (paramDefaultJSONParser.getResolveStatus() == 2) {
        paramDefaultJSONParser.setResolveStatus(0);
      }
      localObject6 = null;
      try
      {
        for (;;)
        {
          label229:
          String str1 = localJSONScanner.scanSymbol(paramDefaultJSONParser.getSymbolTable());
          Object localObject7;
          if (str1 == null) {
            if (localJSONScanner.token() == 13)
            {
              localJSONScanner.nextToken(16);
              localObject7 = localObject6;
            }
          }
          for (;;)
          {
            label266:
            if (localObject2 == null) {
              if (localObject7 == null)
              {
                Object localObject11 = createInstance(paramDefaultJSONParser, paramType);
                if (localObject1 != null) {
                  ((ParseContext)localObject1).setObject(localObject11);
                }
                paramDefaultJSONParser.setContext(localParseContext1);
                return (T)localObject11;
                if ((localJSONScanner.token() == 16) && (paramDefaultJSONParser.isEnabled(Feature.AllowArbitraryCommas))) {
                  break;
                }
                if ("$ref" == str1)
                {
                  localJSONScanner.nextTokenWithColon(4);
                  if (localJSONScanner.token() == 4)
                  {
                    String str2 = localJSONScanner.stringVal();
                    if ("@".equals(str2)) {
                      localObject2 = localParseContext1.getObject();
                    }
                    for (;;)
                    {
                      localJSONScanner.nextToken(13);
                      if (localJSONScanner.token() == 13) {
                        break;
                      }
                      throw new JSONException("illegal ref");
                      if ("..".equals(str2))
                      {
                        ParseContext localParseContext4 = localParseContext1.getParentContext();
                        if (localParseContext4.getObject() != null)
                        {
                          localObject2 = localParseContext4.getObject();
                        }
                        else
                        {
                          paramDefaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(localParseContext4, str2));
                          paramDefaultJSONParser.setResolveStatus(1);
                        }
                      }
                      else if ("$".equals(str2))
                      {
                        for (ParseContext localParseContext2 = localParseContext1; localParseContext2.getParentContext() != null; localParseContext2 = localParseContext2.getParentContext()) {}
                        if (localParseContext2.getObject() != null)
                        {
                          localObject2 = localParseContext2.getObject();
                        }
                        else
                        {
                          paramDefaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(localParseContext2, str2));
                          paramDefaultJSONParser.setResolveStatus(1);
                        }
                      }
                      else
                      {
                        paramDefaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(localParseContext1, str2));
                        paramDefaultJSONParser.setResolveStatus(1);
                      }
                    }
                  }
                  throw new JSONException("illegal ref, " + JSONToken.name(localJSONScanner.token()));
                  localJSONScanner.nextToken(16);
                  ParseContext localParseContext3 = paramDefaultJSONParser.setContext(localParseContext1, localObject2, paramObject);
                  if (localParseContext3 != null) {
                    localParseContext3.setObject(localObject2);
                  }
                  paramDefaultJSONParser.setContext(localParseContext1);
                  return (T)localObject2;
                }
                if (JSON.DEFAULT_TYPE_KEY == str1)
                {
                  localJSONScanner.nextTokenWithColon(4);
                  if (localJSONScanner.token() == 4)
                  {
                    String str3 = localJSONScanner.stringVal();
                    localJSONScanner.nextToken(16);
                    if (((paramType instanceof Class)) && (str3.equals(((Class)paramType).getName())))
                    {
                      if (localJSONScanner.token() != 13) {
                        break;
                      }
                      localJSONScanner.nextToken();
                      localObject7 = localObject6;
                      continue;
                    }
                    Class localClass = TypeUtils.loadClass(str3);
                    Object localObject12 = paramDefaultJSONParser.getConfig().getDeserializer(localClass).deserialze(paramDefaultJSONParser, localClass, paramObject);
                    if (localObject1 != null) {
                      ((ParseContext)localObject1).setObject(localObject2);
                    }
                    paramDefaultJSONParser.setContext(localParseContext1);
                    return (T)localObject12;
                  }
                  throw new JSONException("syntax error");
                }
                if ((localObject2 != null) || (localObject6 != null)) {
                  break label1233;
                }
                localObject2 = createInstance(paramDefaultJSONParser, paramType);
                if (localObject2 != null) {
                  break label1226;
                }
                localObject7 = new HashMap(this.fieldDeserializers.size());
                label812:
                ParseContext localParseContext5 = paramDefaultJSONParser.setContext(localParseContext1, localObject2, paramObject);
                localObject1 = localParseContext5;
              }
            }
          }
          for (Object localObject8 = localObject2;; localObject8 = localObject2)
          {
            try
            {
              if (!parseField(paramDefaultJSONParser, str1, localObject8, paramType, (Map)localObject7))
              {
                if (localJSONScanner.token() != 13) {
                  break label1244;
                }
                localJSONScanner.nextToken();
                localObject2 = localObject8;
                break label266;
              }
              if (localJSONScanner.token() == 16)
              {
                localObject6 = localObject7;
                localObject2 = localObject8;
                break label229;
              }
              if (localJSONScanner.token() == 13)
              {
                localJSONScanner.nextToken(16);
                localObject2 = localObject8;
                break label266;
              }
              if ((localJSONScanner.token() != 18) && (localJSONScanner.token() != 1)) {
                break label976;
              }
              throw new JSONException("syntax error, unexpect token " + JSONToken.name(localJSONScanner.token()));
            }
            finally
            {
              localObject2 = localObject8;
            }
            break;
            label976:
            localObject6 = localObject7;
            localObject2 = localObject8;
            break label229;
            List localList = this.beanInfo.getFieldList();
            int k = localList.size();
            Object[] arrayOfObject = new Object[k];
            for (int m = 0; m < k; m++) {
              arrayOfObject[m] = ((Map)localObject7).get(((FieldInfo)localList.get(m)).getName());
            }
            Constructor localConstructor = this.beanInfo.getCreatorConstructor();
            if (localConstructor != null) {}
            for (;;)
            {
              try
              {
                Object localObject10 = this.beanInfo.getCreatorConstructor().newInstance(arrayOfObject);
                localObject2 = localObject10;
                if (localObject1 != null) {
                  ((ParseContext)localObject1).setObject(localObject2);
                }
                paramDefaultJSONParser.setContext(localParseContext1);
                return (T)localObject2;
              }
              catch (Exception localException2)
              {
                throw new JSONException("create instance error, " + this.beanInfo.getCreatorConstructor().toGenericString(), localException2);
              }
              Method localMethod = this.beanInfo.getFactoryMethod();
              if (localMethod != null) {
                try
                {
                  Object localObject9 = this.beanInfo.getFactoryMethod().invoke(null, arrayOfObject);
                  localObject2 = localObject9;
                }
                catch (Exception localException1)
                {
                  throw new JSONException("create factory method error, " + this.beanInfo.getFactoryMethod().toString(), localException1);
                }
              }
            }
            label1226:
            localObject7 = localObject6;
            break label812;
            label1233:
            localObject7 = localObject6;
          }
          label1244:
          localObject6 = localObject7;
          localObject2 = localObject8;
        }
      }
      finally {}
    }
  }
  
  public Class<?> getClazz()
  {
    return this.clazz;
  }
  
  public int getFastMatchToken()
  {
    return 12;
  }
  
  public Map<String, FieldDeserializer> getFieldDeserializerMap()
  {
    return this.feildDeserializerMap;
  }
  
  public Type getType()
  {
    return this.type;
  }
  
  public boolean parseField(DefaultJSONParser paramDefaultJSONParser, String paramString, Object paramObject, Type paramType, Map<String, Object> paramMap)
  {
    JSONScanner localJSONScanner = (JSONScanner)paramDefaultJSONParser.getLexer();
    FieldDeserializer localFieldDeserializer = (FieldDeserializer)this.feildDeserializerMap.get(paramString);
    if (localFieldDeserializer == null)
    {
      Iterator localIterator = this.feildDeserializerMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (((String)localEntry.getKey()).equalsIgnoreCase(paramString)) {
          localFieldDeserializer = (FieldDeserializer)localEntry.getValue();
        }
      }
    }
    if (localFieldDeserializer == null)
    {
      if (!paramDefaultJSONParser.isEnabled(Feature.IgnoreNotMatch)) {
        throw new JSONException("setter not found, class " + this.clazz.getName() + ", property " + paramString);
      }
      localJSONScanner.nextTokenWithColon();
      paramDefaultJSONParser.parse();
      return false;
    }
    localJSONScanner.nextTokenWithColon(localFieldDeserializer.getFastMatchToken());
    localFieldDeserializer.parseField(paramDefaultJSONParser, paramObject, paramType, paramMap);
    return true;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\JavaBeanDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */