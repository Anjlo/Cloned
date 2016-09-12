package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.ThreadLocalCache;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class JSON
  implements JSONStreamAware, JSONAware
{
  public static int DEFAULT_GENERATE_FEATURE = 0x0 | SerializerFeature.QuoteFieldNames.getMask() | SerializerFeature.SkipTransientField.getMask() | SerializerFeature.WriteEnumUsingToString.getMask() | SerializerFeature.SortField.getMask() | SerializerFeature.WriteTabAsSpecial.getMask();
  public static int DEFAULT_PARSER_FEATURE = 0;
  public static String DEFAULT_TYPE_KEY = "@type";
  public static String DEFFAULT_DATE_FORMAT;
  public static final String VERSION = "1.1.30";
  
  static
  {
    DEFAULT_PARSER_FEATURE = 0x0 | Feature.AutoCloseSource.getMask() | Feature.InternFieldNames.getMask() | Feature.UseBigDecimal.getMask() | Feature.AllowUnQuotedFieldNames.getMask() | Feature.AllowSingleQuotes.getMask() | Feature.AllowArbitraryCommas.getMask() | Feature.SortFeidFastMatch.getMask() | Feature.IgnoreNotMatch.getMask();
    DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  }
  
  public static <T> int handleResovleTask(DefaultJSONParser paramDefaultJSONParser, T paramT)
  {
    if (paramDefaultJSONParser.isEnabled(Feature.DisableCircularReferenceDetect))
    {
      i = 0;
      return i;
    }
    int i = paramDefaultJSONParser.getResolveTaskList().size();
    int j = 0;
    label26:
    DefaultJSONParser.ResolveTask localResolveTask;
    FieldDeserializer localFieldDeserializer;
    Object localObject1;
    String str;
    if (j < i)
    {
      localResolveTask = (DefaultJSONParser.ResolveTask)paramDefaultJSONParser.getResolveTaskList().get(j);
      localFieldDeserializer = localResolveTask.getFieldDeserializer();
      ParseContext localParseContext = localResolveTask.getOwnerContext();
      localObject1 = null;
      if (localParseContext != null) {
        localObject1 = localResolveTask.getOwnerContext().getObject();
      }
      str = localResolveTask.getReferenceValue();
      if (!str.startsWith("$")) {
        break label118;
      }
    }
    label118:
    for (Object localObject2 = paramDefaultJSONParser.getObject(str);; localObject2 = localResolveTask.getContext().getObject())
    {
      localFieldDeserializer.setValue(localObject1, localObject2);
      j++;
      break label26;
      break;
    }
  }
  
  public static final Object parse(String paramString)
  {
    return parse(paramString, DEFAULT_PARSER_FEATURE);
  }
  
  public static final Object parse(String paramString, int paramInt)
  {
    if (paramString == null) {
      return null;
    }
    DefaultJSONParser localDefaultJSONParser = new DefaultJSONParser(paramString, ParserConfig.getGlobalInstance(), paramInt);
    Object localObject = localDefaultJSONParser.parse();
    handleResovleTask(localDefaultJSONParser, localObject);
    localDefaultJSONParser.close();
    return localObject;
  }
  
  public static final Object parse(String paramString, Feature... paramVarArgs)
  {
    int i = DEFAULT_PARSER_FEATURE;
    int j = paramVarArgs.length;
    for (int k = 0; k < j; k++) {
      i = Feature.config(i, paramVarArgs[k], true);
    }
    return parse(paramString, i);
  }
  
  public static final Object parse(byte[] paramArrayOfByte, int paramInt1, int paramInt2, CharsetDecoder paramCharsetDecoder, int paramInt3)
  {
    paramCharsetDecoder.reset();
    char[] arrayOfChar = ThreadLocalCache.getChars((int)(paramInt2 * paramCharsetDecoder.maxCharsPerByte()));
    ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte, paramInt1, paramInt2);
    CharBuffer localCharBuffer = CharBuffer.wrap(arrayOfChar);
    IOUtils.decode(paramCharsetDecoder, localByteBuffer, localCharBuffer);
    DefaultJSONParser localDefaultJSONParser = new DefaultJSONParser(arrayOfChar, localCharBuffer.position(), ParserConfig.getGlobalInstance(), paramInt3);
    Object localObject = localDefaultJSONParser.parse();
    handleResovleTask(localDefaultJSONParser, localObject);
    localDefaultJSONParser.close();
    return localObject;
  }
  
  public static final Object parse(byte[] paramArrayOfByte, int paramInt1, int paramInt2, CharsetDecoder paramCharsetDecoder, Feature... paramVarArgs)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      return null;
    }
    int i = DEFAULT_PARSER_FEATURE;
    int j = paramVarArgs.length;
    for (int k = 0; k < j; k++) {
      i = Feature.config(i, paramVarArgs[k], true);
    }
    return parse(paramArrayOfByte, paramInt1, paramInt2, paramCharsetDecoder, i);
  }
  
  public static final Object parse(byte[] paramArrayOfByte, Feature... paramVarArgs)
  {
    return parse(paramArrayOfByte, 0, paramArrayOfByte.length, ThreadLocalCache.getUTF8Decoder(), paramVarArgs);
  }
  
  public static final JSONArray parseArray(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    DefaultJSONParser localDefaultJSONParser = new DefaultJSONParser(paramString, ParserConfig.getGlobalInstance());
    JSONLexer localJSONLexer = localDefaultJSONParser.getLexer();
    JSONArray localJSONArray;
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken();
      localJSONArray = null;
    }
    for (;;)
    {
      localDefaultJSONParser.close();
      return localJSONArray;
      if (localJSONLexer.token() == 20)
      {
        localJSONArray = null;
      }
      else
      {
        localJSONArray = new JSONArray();
        localDefaultJSONParser.parseArray(localJSONArray);
        handleResovleTask(localDefaultJSONParser, localJSONArray);
      }
    }
  }
  
  public static final <T> List<T> parseArray(String paramString, Class<T> paramClass)
  {
    if (paramString == null) {
      return null;
    }
    DefaultJSONParser localDefaultJSONParser = new DefaultJSONParser(paramString, ParserConfig.getGlobalInstance());
    JSONLexer localJSONLexer = localDefaultJSONParser.getLexer();
    Object localObject;
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken();
      localObject = null;
    }
    for (;;)
    {
      localDefaultJSONParser.close();
      return (List<T>)localObject;
      localObject = new ArrayList();
      localDefaultJSONParser.parseArray(paramClass, (Collection)localObject);
      handleResovleTask(localDefaultJSONParser, localObject);
    }
  }
  
  public static final List<Object> parseArray(String paramString, Type[] paramArrayOfType)
  {
    if (paramString == null) {
      return null;
    }
    DefaultJSONParser localDefaultJSONParser = new DefaultJSONParser(paramString, ParserConfig.getGlobalInstance());
    Object[] arrayOfObject = localDefaultJSONParser.parseArray(paramArrayOfType);
    if (arrayOfObject == null) {}
    for (Object localObject = null;; localObject = Arrays.asList(arrayOfObject))
    {
      handleResovleTask(localDefaultJSONParser, localObject);
      localDefaultJSONParser.close();
      return (List<Object>)localObject;
    }
  }
  
  public static final JSONObject parseObject(String paramString)
  {
    Object localObject = parse(paramString);
    if ((localObject instanceof JSONObject)) {
      return (JSONObject)localObject;
    }
    return (JSONObject)toJSON(localObject);
  }
  
  public static final JSONObject parseObject(String paramString, Feature... paramVarArgs)
  {
    return (JSONObject)parse(paramString, paramVarArgs);
  }
  
  public static final <T> T parseObject(String paramString, TypeReference<T> paramTypeReference, Feature... paramVarArgs)
  {
    return (T)parseObject(paramString, paramTypeReference.getType(), ParserConfig.getGlobalInstance(), DEFAULT_PARSER_FEATURE, paramVarArgs);
  }
  
  public static final <T> T parseObject(String paramString, Class<T> paramClass)
  {
    return (T)parseObject(paramString, paramClass, new Feature[0]);
  }
  
  public static final <T> T parseObject(String paramString, Class<T> paramClass, Feature... paramVarArgs)
  {
    return (T)parseObject(paramString, paramClass, ParserConfig.getGlobalInstance(), DEFAULT_PARSER_FEATURE, paramVarArgs);
  }
  
  public static final <T> T parseObject(String paramString, Type paramType, int paramInt, Feature... paramVarArgs)
  {
    if (paramString == null) {
      return null;
    }
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++) {
      paramInt = Feature.config(paramInt, paramVarArgs[j], true);
    }
    DefaultJSONParser localDefaultJSONParser = new DefaultJSONParser(paramString, ParserConfig.getGlobalInstance(), paramInt);
    Object localObject = localDefaultJSONParser.parseObject(paramType);
    handleResovleTask(localDefaultJSONParser, localObject);
    localDefaultJSONParser.close();
    return (T)localObject;
  }
  
  public static final <T> T parseObject(String paramString, Type paramType, ParserConfig paramParserConfig, int paramInt, Feature... paramVarArgs)
  {
    if (paramString == null) {
      return null;
    }
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++) {
      paramInt = Feature.config(paramInt, paramVarArgs[j], true);
    }
    DefaultJSONParser localDefaultJSONParser = new DefaultJSONParser(paramString, paramParserConfig, paramInt);
    Object localObject = localDefaultJSONParser.parseObject(paramType);
    handleResovleTask(localDefaultJSONParser, localObject);
    localDefaultJSONParser.close();
    return (T)localObject;
  }
  
  public static final <T> T parseObject(String paramString, Type paramType, Feature... paramVarArgs)
  {
    return (T)parseObject(paramString, paramType, ParserConfig.getGlobalInstance(), DEFAULT_PARSER_FEATURE, paramVarArgs);
  }
  
  public static final <T> T parseObject(byte[] paramArrayOfByte, int paramInt1, int paramInt2, CharsetDecoder paramCharsetDecoder, Type paramType, Feature... paramVarArgs)
  {
    paramCharsetDecoder.reset();
    char[] arrayOfChar = ThreadLocalCache.getChars((int)(paramInt2 * paramCharsetDecoder.maxCharsPerByte()));
    ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte, paramInt1, paramInt2);
    CharBuffer localCharBuffer = CharBuffer.wrap(arrayOfChar);
    IOUtils.decode(paramCharsetDecoder, localByteBuffer, localCharBuffer);
    return (T)parseObject(arrayOfChar, localCharBuffer.position(), paramType, paramVarArgs);
  }
  
  public static final <T> T parseObject(byte[] paramArrayOfByte, Type paramType, Feature... paramVarArgs)
  {
    return (T)parseObject(paramArrayOfByte, 0, paramArrayOfByte.length, ThreadLocalCache.getUTF8Decoder(), paramType, paramVarArgs);
  }
  
  public static final <T> T parseObject(char[] paramArrayOfChar, int paramInt, Type paramType, Feature... paramVarArgs)
  {
    if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0)) {
      return null;
    }
    int i = DEFAULT_PARSER_FEATURE;
    int j = paramVarArgs.length;
    for (int k = 0; k < j; k++) {
      i = Feature.config(i, paramVarArgs[k], true);
    }
    DefaultJSONParser localDefaultJSONParser = new DefaultJSONParser(paramArrayOfChar, paramInt, ParserConfig.getGlobalInstance(), i);
    Object localObject = localDefaultJSONParser.parseObject(paramType);
    handleResovleTask(localDefaultJSONParser, localObject);
    localDefaultJSONParser.close();
    return (T)localObject;
  }
  
  public static final Object toJSON(Object paramObject)
  {
    return toJSON(paramObject, ParserConfig.getGlobalInstance());
  }
  
  public static final Object toJSON(Object paramObject, ParserConfig paramParserConfig)
  {
    if (paramObject == null) {
      paramObject = null;
    }
    Class localClass;
    do
    {
      return paramObject;
      if ((paramObject instanceof JSON)) {
        return (JSON)paramObject;
      }
      if ((paramObject instanceof Map))
      {
        Map localMap = (Map)paramObject;
        JSONObject localJSONObject2 = new JSONObject(localMap.size());
        Iterator localIterator3 = localMap.entrySet().iterator();
        while (localIterator3.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator3.next();
          localJSONObject2.put(TypeUtils.castToString(localEntry.getKey()), toJSON(localEntry.getValue()));
        }
        return localJSONObject2;
      }
      if ((paramObject instanceof Collection))
      {
        Collection localCollection = (Collection)paramObject;
        JSONArray localJSONArray2 = new JSONArray(localCollection.size());
        Iterator localIterator2 = localCollection.iterator();
        while (localIterator2.hasNext()) {
          localJSONArray2.add(toJSON(localIterator2.next()));
        }
        return localJSONArray2;
      }
      localClass = paramObject.getClass();
      if (localClass.isEnum()) {
        return ((Enum)paramObject).name();
      }
      if (localClass.isArray())
      {
        int i = Array.getLength(paramObject);
        JSONArray localJSONArray1 = new JSONArray(i);
        for (int j = 0; j < i; j++) {
          localJSONArray1.add(toJSON(Array.get(paramObject, j)));
        }
        return localJSONArray1;
      }
    } while (paramParserConfig.isPrimitive(localClass));
    try
    {
      List localList = TypeUtils.computeGetters(localClass, null);
      JSONObject localJSONObject1 = new JSONObject(localList.size());
      Iterator localIterator1 = localList.iterator();
      while (localIterator1.hasNext())
      {
        FieldInfo localFieldInfo = (FieldInfo)localIterator1.next();
        Object localObject = toJSON(localFieldInfo.get(paramObject));
        localJSONObject1.put(localFieldInfo.getName(), localObject);
      }
      JSONException localJSONException;
      return localJSONObject1;
    }
    catch (Exception localException)
    {
      localJSONException = new JSONException("toJSON error", localException);
      throw localJSONException;
    }
  }
  
  public static final byte[] toJSONBytes(Object paramObject, SerializeConfig paramSerializeConfig, SerializerFeature... paramVarArgs)
  {
    SerializeWriter localSerializeWriter = new SerializeWriter();
    try
    {
      JSONSerializer localJSONSerializer = new JSONSerializer(localSerializeWriter, paramSerializeConfig);
      int i = paramVarArgs.length;
      for (int j = 0; j < i; j++) {
        localJSONSerializer.config(paramVarArgs[j], true);
      }
      localJSONSerializer.write(paramObject);
      byte[] arrayOfByte = localSerializeWriter.toBytes("UTF-8");
      return arrayOfByte;
    }
    finally
    {
      localSerializeWriter.close();
    }
  }
  
  public static final byte[] toJSONBytes(Object paramObject, SerializerFeature... paramVarArgs)
  {
    SerializeWriter localSerializeWriter = new SerializeWriter();
    try
    {
      JSONSerializer localJSONSerializer = new JSONSerializer(localSerializeWriter);
      int i = paramVarArgs.length;
      for (int j = 0; j < i; j++) {
        localJSONSerializer.config(paramVarArgs[j], true);
      }
      localJSONSerializer.write(paramObject);
      byte[] arrayOfByte = localSerializeWriter.toBytes("UTF-8");
      return arrayOfByte;
    }
    finally
    {
      localSerializeWriter.close();
    }
  }
  
  public static final String toJSONString(Object paramObject)
  {
    return toJSONString(paramObject, new SerializerFeature[0]);
  }
  
  public static final String toJSONString(Object paramObject, SerializeConfig paramSerializeConfig, SerializerFeature... paramVarArgs)
  {
    SerializeWriter localSerializeWriter = new SerializeWriter();
    try
    {
      JSONSerializer localJSONSerializer = new JSONSerializer(localSerializeWriter, paramSerializeConfig);
      int i = paramVarArgs.length;
      for (int j = 0; j < i; j++) {
        localJSONSerializer.config(paramVarArgs[j], true);
      }
      localJSONSerializer.write(paramObject);
      String str = localSerializeWriter.toString();
      return str;
    }
    finally
    {
      localSerializeWriter.close();
    }
  }
  
  public static final String toJSONString(Object paramObject, SerializeFilter paramSerializeFilter, SerializerFeature... paramVarArgs)
  {
    SerializeWriter localSerializeWriter = new SerializeWriter();
    try
    {
      JSONSerializer localJSONSerializer = new JSONSerializer(localSerializeWriter);
      int i = paramVarArgs.length;
      for (int j = 0; j < i; j++) {
        localJSONSerializer.config(paramVarArgs[j], true);
      }
      localJSONSerializer.config(SerializerFeature.WriteDateUseDateFormat, true);
      if (paramSerializeFilter != null)
      {
        if ((paramSerializeFilter instanceof PropertyPreFilter)) {
          localJSONSerializer.getPropertyPreFilters().add((PropertyPreFilter)paramSerializeFilter);
        }
        if ((paramSerializeFilter instanceof NameFilter)) {
          localJSONSerializer.getNameFilters().add((NameFilter)paramSerializeFilter);
        }
        if ((paramSerializeFilter instanceof ValueFilter)) {
          localJSONSerializer.getValueFilters().add((ValueFilter)paramSerializeFilter);
        }
        if ((paramSerializeFilter instanceof PropertyFilter)) {
          localJSONSerializer.getPropertyFilters().add((PropertyFilter)paramSerializeFilter);
        }
      }
      localJSONSerializer.write(paramObject);
      String str = localSerializeWriter.toString();
      return str;
    }
    finally
    {
      localSerializeWriter.close();
    }
  }
  
  public static final String toJSONString(Object paramObject, boolean paramBoolean)
  {
    if (!paramBoolean) {
      return toJSONString(paramObject);
    }
    SerializerFeature[] arrayOfSerializerFeature = new SerializerFeature[1];
    arrayOfSerializerFeature[0] = SerializerFeature.PrettyFormat;
    return toJSONString(paramObject, arrayOfSerializerFeature);
  }
  
  public static final String toJSONString(Object paramObject, SerializerFeature... paramVarArgs)
  {
    SerializeWriter localSerializeWriter = new SerializeWriter();
    try
    {
      JSONSerializer localJSONSerializer = new JSONSerializer(localSerializeWriter);
      int i = paramVarArgs.length;
      for (int j = 0; j < i; j++) {
        localJSONSerializer.config(paramVarArgs[j], true);
      }
      localJSONSerializer.write(paramObject);
      String str = localSerializeWriter.toString();
      return str;
    }
    finally
    {
      localSerializeWriter.close();
    }
  }
  
  public static final String toJSONStringWithDateFormat(Object paramObject, String paramString, SerializerFeature... paramVarArgs)
  {
    SerializeWriter localSerializeWriter = new SerializeWriter();
    try
    {
      JSONSerializer localJSONSerializer = new JSONSerializer(localSerializeWriter);
      int i = paramVarArgs.length;
      for (int j = 0; j < i; j++) {
        localJSONSerializer.config(paramVarArgs[j], true);
      }
      localJSONSerializer.config(SerializerFeature.WriteDateUseDateFormat, true);
      if (paramString != null) {
        localJSONSerializer.setDateFormat(paramString);
      }
      localJSONSerializer.write(paramObject);
      String str = localSerializeWriter.toString();
      return str;
    }
    finally
    {
      localSerializeWriter.close();
    }
  }
  
  public static final String toJSONStringZ(Object paramObject, SerializeConfig paramSerializeConfig, SerializerFeature... paramVarArgs)
  {
    SerializeWriter localSerializeWriter = new SerializeWriter(paramVarArgs);
    try
    {
      new JSONSerializer(localSerializeWriter, paramSerializeConfig).write(paramObject);
      String str = localSerializeWriter.toString();
      return str;
    }
    finally
    {
      localSerializeWriter.close();
    }
  }
  
  public static final <T> T toJavaObject(JSON paramJSON, Class<T> paramClass)
  {
    return (T)TypeUtils.cast(paramJSON, paramClass, ParserConfig.getGlobalInstance());
  }
  
  public String toJSONString()
  {
    SerializeWriter localSerializeWriter = new SerializeWriter();
    try
    {
      new JSONSerializer(localSerializeWriter).write(this);
      String str = localSerializeWriter.toString();
      return str;
    }
    finally
    {
      localSerializeWriter.close();
    }
  }
  
  public String toString()
  {
    return toJSONString();
  }
  
  public void writeJSONString(Appendable paramAppendable)
  {
    SerializeWriter localSerializeWriter = new SerializeWriter();
    try
    {
      new JSONSerializer(localSerializeWriter).write(this);
      paramAppendable.append(localSerializeWriter.toString());
      return;
    }
    catch (IOException localIOException)
    {
      throw new JSONException(localIOException.getMessage(), localIOException);
    }
    finally
    {
      localSerializeWriter.close();
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\JSON.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */