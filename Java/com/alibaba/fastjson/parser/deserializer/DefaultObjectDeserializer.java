package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DefaultObjectDeserializer
  implements ObjectDeserializer
{
  public static final DefaultObjectDeserializer instance = new DefaultObjectDeserializer();
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Class<T> paramClass)
  {
    if (paramDefaultJSONParser.getLexer().token() == 8)
    {
      paramDefaultJSONParser.getLexer().nextToken(16);
      return null;
    }
    Object localObject1;
    if (paramClass.isAssignableFrom(HashMap.class)) {
      localObject1 = new HashMap();
    }
    while (paramClass == Class.class)
    {
      Object localObject2 = paramDefaultJSONParser.parse();
      if (localObject2 == null)
      {
        return null;
        if (paramClass.isAssignableFrom(TreeMap.class))
        {
          localObject1 = new TreeMap();
        }
        else if (paramClass.isAssignableFrom(ConcurrentHashMap.class))
        {
          localObject1 = new ConcurrentHashMap();
        }
        else if (paramClass.isAssignableFrom(Properties.class))
        {
          localObject1 = new Properties();
        }
        else
        {
          boolean bool = paramClass.isAssignableFrom(IdentityHashMap.class);
          localObject1 = null;
          if (bool) {
            localObject1 = new IdentityHashMap();
          }
        }
      }
      else
      {
        if (!(localObject2 instanceof String)) {
          break label177;
        }
        return ASMClassLoader.forName((String)localObject2);
      }
    }
    if (paramClass == Serializable.class) {
      return (T)paramDefaultJSONParser.parse();
    }
    label177:
    if (localObject1 == null) {
      throw new JSONException("not support type : " + paramClass);
    }
    try
    {
      parseObject(paramDefaultJSONParser, localObject1);
      return (T)localObject1;
    }
    catch (JSONException localJSONException)
    {
      throw localJSONException;
    }
    catch (Throwable localThrowable)
    {
      throw new JSONException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, ParameterizedType paramParameterizedType, Object paramObject)
  {
    for (;;)
    {
      try
      {
        JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
        if (localJSONLexer.token() == 8)
        {
          localJSONLexer.nextToken();
          return null;
        }
        Type localType1 = paramParameterizedType.getRawType();
        if (!(localType1 instanceof Class)) {
          continue;
        }
        localClass = (Class)localType1;
        if (!Map.class.isAssignableFrom(localClass)) {
          continue;
        }
        if (!Modifier.isAbstract(localClass.getModifiers())) {
          continue;
        }
        if (localClass != Map.class) {
          continue;
        }
        localObject = new HashMap();
      }
      catch (JSONException localJSONException)
      {
        Class localClass;
        Type localType2;
        Type localType3;
        throw localJSONException;
        if (localClass != HashMap.class) {
          continue;
        }
        Object localObject = new HashMap();
        continue;
        localObject = (Map)localClass.newInstance();
        continue;
        return (T)parseMap(paramDefaultJSONParser, (Map)localObject, localType2, localType3, paramObject);
        throw new JSONException("not support type : " + paramParameterizedType);
      }
      catch (Throwable localThrowable)
      {
        throw new JSONException(localThrowable.getMessage(), localThrowable);
      }
      localType2 = paramParameterizedType.getActualTypeArguments()[0];
      localType3 = paramParameterizedType.getActualTypeArguments()[1];
      if (localType2 != String.class) {
        continue;
      }
      return parseMap(paramDefaultJSONParser, (Map)localObject, localType3, paramObject);
      if (localClass == SortedMap.class)
      {
        localObject = new TreeMap();
      }
      else
      {
        if (localClass != ConcurrentMap.class) {
          continue;
        }
        localObject = new ConcurrentHashMap();
      }
    }
    throw new JSONException("can not create instance : " + localClass);
  }
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    if ((paramType instanceof Class)) {
      return (T)deserialze(paramDefaultJSONParser, (Class)paramType);
    }
    if ((paramType instanceof ParameterizedType)) {
      return (T)deserialze(paramDefaultJSONParser, (ParameterizedType)paramType, paramObject);
    }
    if ((paramType instanceof TypeVariable)) {
      return (T)paramDefaultJSONParser.parse(paramObject);
    }
    if ((paramType instanceof WildcardType)) {
      return (T)paramDefaultJSONParser.parse(paramObject);
    }
    if ((paramType instanceof GenericArrayType))
    {
      Type localType = ((GenericArrayType)paramType).getGenericComponentType();
      ArrayList localArrayList = new ArrayList();
      paramDefaultJSONParser.parseArray(localType, localArrayList);
      if ((localType instanceof Class))
      {
        Object[] arrayOfObject = (Object[])Array.newInstance((Class)localType, localArrayList.size());
        localArrayList.toArray(arrayOfObject);
        return arrayOfObject;
      }
    }
    throw new JSONException("not support type : " + paramType);
  }
  
  public int getFastMatchToken()
  {
    return 12;
  }
  
  public Object parseMap(DefaultJSONParser paramDefaultJSONParser, Map<Object, Object> paramMap, Type paramType1, Type paramType2, Object paramObject)
  {
    JSONScanner localJSONScanner = (JSONScanner)paramDefaultJSONParser.getLexer();
    if ((localJSONScanner.token() != 12) && (localJSONScanner.token() != 16)) {
      throw new JSONException("syntax error, expect {, actual " + localJSONScanner.tokenName());
    }
    ObjectDeserializer localObjectDeserializer1 = paramDefaultJSONParser.getConfig().getDeserializer(paramType1);
    ObjectDeserializer localObjectDeserializer2 = paramDefaultJSONParser.getConfig().getDeserializer(paramType2);
    localJSONScanner.nextToken(localObjectDeserializer1.getFastMatchToken());
    ParseContext localParseContext1 = paramDefaultJSONParser.getContext();
    for (;;)
    {
      String str;
      Object localObject4;
      try
      {
        if (localJSONScanner.token() == 13)
        {
          localJSONScanner.nextToken(16);
          return paramMap;
        }
        if ((localJSONScanner.token() != 4) || (!localJSONScanner.isRef())) {
          break label432;
        }
        localJSONScanner.nextTokenWithColon(4);
        if (localJSONScanner.token() != 4) {
          break label381;
        }
        str = localJSONScanner.stringVal();
        if ("@".equals(str))
        {
          localObject4 = localParseContext1.getObject();
          localJSONScanner.nextToken(13);
          if (localJSONScanner.token() == 13) {
            break label416;
          }
          throw new JSONException("illegal ref");
        }
      }
      finally
      {
        paramDefaultJSONParser.setContext(localParseContext1);
      }
      if ("..".equals(str))
      {
        ParseContext localParseContext3 = localParseContext1.getParentContext();
        if (localParseContext3.getObject() != null)
        {
          localObject4 = localParseContext3.getObject();
        }
        else
        {
          paramDefaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(localParseContext3, str));
          paramDefaultJSONParser.setResolveStatus(1);
          localObject4 = null;
        }
      }
      else if ("$".equals(str))
      {
        for (ParseContext localParseContext2 = localParseContext1; localParseContext2.getParentContext() != null; localParseContext2 = localParseContext2.getParentContext()) {}
        if (localParseContext2.getObject() != null)
        {
          localObject4 = localParseContext2.getObject();
        }
        else
        {
          paramDefaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(localParseContext2, str));
          paramDefaultJSONParser.setResolveStatus(1);
          localObject4 = null;
        }
      }
      else
      {
        paramDefaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(localParseContext1, str));
        paramDefaultJSONParser.setResolveStatus(1);
        localObject4 = null;
        continue;
        label381:
        throw new JSONException("illegal ref, " + JSONToken.name(localJSONScanner.token()));
        label416:
        localJSONScanner.nextToken(16);
        paramDefaultJSONParser.setContext(localParseContext1);
        return localObject4;
        label432:
        if ((paramMap.size() == 0) && (localJSONScanner.token() == 4) && (JSON.DEFAULT_TYPE_KEY.equals(localJSONScanner.stringVal())))
        {
          localJSONScanner.nextTokenWithColon(4);
          localJSONScanner.nextToken(16);
          if (localJSONScanner.token() == 13)
          {
            localJSONScanner.nextToken();
            paramDefaultJSONParser.setContext(localParseContext1);
            return paramMap;
          }
          localJSONScanner.nextToken(localObjectDeserializer1.getFastMatchToken());
        }
        Object localObject2 = localObjectDeserializer1.deserialze(paramDefaultJSONParser, paramType1, null);
        if (localJSONScanner.token() != 17) {
          throw new JSONException("syntax error, expect :, actual " + localJSONScanner.token());
        }
        localJSONScanner.nextToken(localObjectDeserializer2.getFastMatchToken());
        Object localObject3 = localObjectDeserializer2.deserialze(paramDefaultJSONParser, paramType2, localObject2);
        if ((paramMap.size() == 0) && (localParseContext1 != null) && (localParseContext1.getObject() != paramMap)) {
          paramDefaultJSONParser.setContext(localParseContext1, paramMap, paramObject);
        }
        paramMap.put(localObject2, localObject3);
        if (localJSONScanner.token() == 16) {
          localJSONScanner.nextToken(localObjectDeserializer1.getFastMatchToken());
        }
      }
    }
  }
  
  public Map parseMap(DefaultJSONParser paramDefaultJSONParser, Map<String, Object> paramMap, Type paramType, Object paramObject)
  {
    JSONScanner localJSONScanner = (JSONScanner)paramDefaultJSONParser.getLexer();
    if (localJSONScanner.token() != 12) {
      throw new JSONException("syntax error, expect {, actual " + localJSONScanner.token());
    }
    ParseContext localParseContext = paramDefaultJSONParser.getContext();
    label397:
    label549:
    int j;
    do
    {
      String str;
      Class localClass;
      do
      {
        int i;
        try
        {
          localJSONScanner.skipWhitespace();
          i = localJSONScanner.getCurrent();
          if (paramDefaultJSONParser.isEnabled(Feature.AllowArbitraryCommas)) {
            while (i == 44)
            {
              localJSONScanner.incrementBufferPosition();
              localJSONScanner.skipWhitespace();
              i = localJSONScanner.getCurrent();
            }
          }
          if (i == 34)
          {
            str = localJSONScanner.scanSymbol(paramDefaultJSONParser.getSymbolTable(), '"');
            localJSONScanner.skipWhitespace();
            if (localJSONScanner.getCurrent() == ':') {
              break label397;
            }
            throw new JSONException("expect ':' at " + localJSONScanner.pos());
          }
        }
        finally
        {
          paramDefaultJSONParser.setContext(localParseContext);
        }
        if (i == 125)
        {
          localJSONScanner.incrementBufferPosition();
          localJSONScanner.resetStringPosition();
          localJSONScanner.nextToken(16);
          paramDefaultJSONParser.setContext(localParseContext);
          return paramMap;
        }
        if (i == 39)
        {
          if (!paramDefaultJSONParser.isEnabled(Feature.AllowSingleQuotes)) {
            throw new JSONException("syntax error");
          }
          str = localJSONScanner.scanSymbol(paramDefaultJSONParser.getSymbolTable(), '\'');
          localJSONScanner.skipWhitespace();
          if (localJSONScanner.getCurrent() != ':') {
            throw new JSONException("expect ':' at " + localJSONScanner.pos());
          }
        }
        else
        {
          if (!paramDefaultJSONParser.isEnabled(Feature.AllowUnQuotedFieldNames)) {
            throw new JSONException("syntax error");
          }
          str = localJSONScanner.scanSymbolUnQuoted(paramDefaultJSONParser.getSymbolTable());
          localJSONScanner.skipWhitespace();
          char c = localJSONScanner.getCurrent();
          if (c != ':') {
            throw new JSONException("expect ':' at " + localJSONScanner.pos() + ", actual " + c);
          }
        }
        localJSONScanner.incrementBufferPosition();
        localJSONScanner.skipWhitespace();
        localJSONScanner.getCurrent();
        localJSONScanner.resetStringPosition();
        if (str != JSON.DEFAULT_TYPE_KEY) {
          break label549;
        }
        localClass = TypeUtils.loadClass(localJSONScanner.scanSymbol(paramDefaultJSONParser.getSymbolTable(), '"'));
        if (localClass != paramMap.getClass()) {
          break;
        }
        localJSONScanner.nextToken(16);
      } while (localJSONScanner.token() != 13);
      localJSONScanner.nextToken(16);
      paramDefaultJSONParser.setContext(localParseContext);
      return paramMap;
      ObjectDeserializer localObjectDeserializer = paramDefaultJSONParser.getConfig().getDeserializer(localClass);
      localJSONScanner.nextToken(16);
      paramDefaultJSONParser.setResolveStatus(2);
      if ((localParseContext != null) && (!(paramObject instanceof Integer))) {
        paramDefaultJSONParser.popContext();
      }
      Map localMap = (Map)localObjectDeserializer.deserialze(paramDefaultJSONParser, localClass, paramObject);
      paramDefaultJSONParser.setContext(localParseContext);
      return localMap;
      localJSONScanner.nextToken();
      Object localObject2;
      if (localJSONScanner.token() == 8)
      {
        localObject2 = null;
        localJSONScanner.nextToken();
      }
      for (;;)
      {
        paramMap.put(str, localObject2);
        paramDefaultJSONParser.checkMapResolve(paramMap, str);
        paramDefaultJSONParser.setContext(localParseContext, localObject2, str);
        j = localJSONScanner.token();
        if ((j != 20) && (j != 15)) {
          break;
        }
        paramDefaultJSONParser.setContext(localParseContext);
        return paramMap;
        localObject2 = paramDefaultJSONParser.parseObject(paramType);
      }
    } while (j != 13);
    localJSONScanner.nextToken();
    paramDefaultJSONParser.setContext(localParseContext);
    return paramMap;
  }
  
  public void parseObject(DefaultJSONParser paramDefaultJSONParser, Object paramObject)
  {
    Class localClass1 = paramObject.getClass();
    Map localMap = paramDefaultJSONParser.getConfig().getFieldDeserializers(localClass1);
    JSONScanner localJSONScanner = (JSONScanner)paramDefaultJSONParser.getLexer();
    if (localJSONScanner.token() == 13)
    {
      localJSONScanner.nextToken(16);
      return;
    }
    if ((localJSONScanner.token() != 12) && (localJSONScanner.token() != 16)) {
      throw new JSONException("syntax error, expect {, actual " + localJSONScanner.tokenName());
    }
    Object[] arrayOfObject = new Object[1];
    FieldDeserializer localFieldDeserializer;
    do
    {
      String str;
      do
      {
        str = localJSONScanner.scanSymbol(paramDefaultJSONParser.getSymbolTable());
        if (str != null) {
          break;
        }
        if (localJSONScanner.token() == 13)
        {
          localJSONScanner.nextToken(16);
          return;
        }
      } while ((localJSONScanner.token() == 16) && (paramDefaultJSONParser.isEnabled(Feature.AllowArbitraryCommas)));
      localFieldDeserializer = (FieldDeserializer)localMap.get(str);
      if (localFieldDeserializer != null) {
        break;
      }
      if (!paramDefaultJSONParser.isEnabled(Feature.IgnoreNotMatch)) {
        throw new JSONException("setter not found, class " + localClass1.getName() + ", property " + str);
      }
      localJSONScanner.nextTokenWithColon();
      paramDefaultJSONParser.parse();
    } while (localJSONScanner.token() != 13);
    localJSONScanner.nextToken();
    return;
    localMethod = localFieldDeserializer.getMethod();
    Class localClass2 = localMethod.getParameterTypes()[0];
    Type localType = localMethod.getGenericParameterTypes()[0];
    if (localClass2 == Integer.TYPE)
    {
      localJSONScanner.nextTokenWithColon(2);
      arrayOfObject[0] = IntegerDeserializer.deserialze(paramDefaultJSONParser);
    }
    for (;;)
    {
      try
      {
        localMethod.invoke(paramObject, arrayOfObject);
        if ((localJSONScanner.token() == 16) || (localJSONScanner.token() != 13)) {
          break;
        }
        localJSONScanner.nextToken(16);
        return;
      }
      catch (Exception localException)
      {
        ObjectDeserializer localObjectDeserializer;
        throw new JSONException("set proprety error, " + localMethod.getName(), localException);
      }
      if (localClass2 == String.class)
      {
        localJSONScanner.nextTokenWithColon(4);
        arrayOfObject[0] = StringDeserializer.deserialze(paramDefaultJSONParser);
      }
      else if (localClass2 == Long.TYPE)
      {
        localJSONScanner.nextTokenWithColon(2);
        arrayOfObject[0] = LongDeserializer.deserialze(paramDefaultJSONParser);
      }
      else if (localClass2 == List.class)
      {
        localJSONScanner.nextTokenWithColon(12);
        arrayOfObject[0] = CollectionDeserializer.instance.deserialze(paramDefaultJSONParser, localType, null);
      }
      else
      {
        localObjectDeserializer = paramDefaultJSONParser.getConfig().getDeserializer(localClass2, localType);
        localJSONScanner.nextTokenWithColon(localObjectDeserializer.getFastMatchToken());
        arrayOfObject[0] = localObjectDeserializer.deserialze(paramDefaultJSONParser, localType, null);
      }
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\DefaultObjectDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */