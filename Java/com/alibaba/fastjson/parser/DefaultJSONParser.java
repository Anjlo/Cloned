package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ASMJavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.DefaultObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.IntegerDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ListResolveFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.MapResolveFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.StringDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DefaultJSONParser
  extends AbstractJSONParser
{
  public static final int NONE = 0;
  public static final int NeedToResolve = 1;
  public static final int TypeNameRedirect = 2;
  private static final Set<Class<?>> primitiveClasses = new HashSet();
  protected ParserConfig config;
  protected ParseContext context;
  private ParseContext[] contextArray = new ParseContext[8];
  private int contextArrayIndex = 0;
  private DateFormat dateFormat;
  private String dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
  private DefaultObjectDeserializer derializer = new DefaultObjectDeserializer();
  protected final Object input;
  protected final JSONLexer lexer;
  private int resolveStatus = 0;
  private final List<ResolveTask> resolveTaskList = new ArrayList();
  protected final SymbolTable symbolTable;
  
  static
  {
    primitiveClasses.add(Boolean.TYPE);
    primitiveClasses.add(Byte.TYPE);
    primitiveClasses.add(Short.TYPE);
    primitiveClasses.add(Integer.TYPE);
    primitiveClasses.add(Long.TYPE);
    primitiveClasses.add(Float.TYPE);
    primitiveClasses.add(Double.TYPE);
    primitiveClasses.add(Boolean.class);
    primitiveClasses.add(Byte.class);
    primitiveClasses.add(Short.class);
    primitiveClasses.add(Integer.class);
    primitiveClasses.add(Long.class);
    primitiveClasses.add(Float.class);
    primitiveClasses.add(Double.class);
    primitiveClasses.add(BigInteger.class);
    primitiveClasses.add(BigDecimal.class);
    primitiveClasses.add(String.class);
  }
  
  public DefaultJSONParser(Object paramObject, JSONLexer paramJSONLexer, ParserConfig paramParserConfig)
  {
    this.lexer = paramJSONLexer;
    this.input = paramObject;
    this.config = paramParserConfig;
    this.symbolTable = paramParserConfig.getSymbolTable();
    paramJSONLexer.nextToken(12);
  }
  
  public DefaultJSONParser(String paramString)
  {
    this(paramString, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
  }
  
  public DefaultJSONParser(String paramString, ParserConfig paramParserConfig)
  {
    this(paramString, new JSONScanner(paramString, JSON.DEFAULT_PARSER_FEATURE), paramParserConfig);
  }
  
  public DefaultJSONParser(String paramString, ParserConfig paramParserConfig, int paramInt)
  {
    this(paramString, new JSONScanner(paramString, paramInt), paramParserConfig);
  }
  
  public DefaultJSONParser(char[] paramArrayOfChar, int paramInt1, ParserConfig paramParserConfig, int paramInt2)
  {
    this(paramArrayOfChar, new JSONScanner(paramArrayOfChar, paramInt1, paramInt2), paramParserConfig);
  }
  
  private void addContext(ParseContext paramParseContext)
  {
    int i = this.contextArrayIndex;
    this.contextArrayIndex = (i + 1);
    if (i >= this.contextArray.length)
    {
      ParseContext[] arrayOfParseContext = new ParseContext[3 * this.contextArray.length / 2];
      System.arraycopy(this.contextArray, 0, arrayOfParseContext, 0, this.contextArray.length);
      this.contextArray = arrayOfParseContext;
    }
    this.contextArray[i] = paramParseContext;
  }
  
  public final void accept(int paramInt)
  {
    JSONLexer localJSONLexer = getLexer();
    if (localJSONLexer.token() == paramInt)
    {
      localJSONLexer.nextToken();
      return;
    }
    throw new JSONException("syntax error, expect " + JSONToken.name(paramInt) + ", actual " + JSONToken.name(localJSONLexer.token()));
  }
  
  public void acceptType(String paramString)
  {
    JSONScanner localJSONScanner = (JSONScanner)this.lexer;
    localJSONScanner.nextTokenWithColon();
    if (localJSONScanner.token() != 4) {
      throw new JSONException("type not match error");
    }
    if (paramString.equals(localJSONScanner.stringVal()))
    {
      localJSONScanner.nextToken();
      if (localJSONScanner.token() == 16) {
        localJSONScanner.nextToken();
      }
      return;
    }
    throw new JSONException("type not match error");
  }
  
  public void addResolveTask(ResolveTask paramResolveTask)
  {
    this.resolveTaskList.add(paramResolveTask);
  }
  
  public void checkListResolve(Collection paramCollection)
  {
    if (this.resolveStatus == 1)
    {
      int i = -1 + paramCollection.size();
      List localList = (List)paramCollection;
      ResolveTask localResolveTask = getLastResolveTask();
      localResolveTask.setFieldDeserializer(new ListResolveFieldDeserializer(this, localList, i));
      localResolveTask.setOwnerContext(this.context);
      setResolveStatus(0);
    }
  }
  
  public void checkMapResolve(Map paramMap, String paramString)
  {
    if (this.resolveStatus == 1)
    {
      MapResolveFieldDeserializer localMapResolveFieldDeserializer = new MapResolveFieldDeserializer(paramMap, paramString);
      ResolveTask localResolveTask = getLastResolveTask();
      localResolveTask.setFieldDeserializer(localMapResolveFieldDeserializer);
      localResolveTask.setOwnerContext(this.context);
      setResolveStatus(0);
    }
  }
  
  public void clearContext(ParseContext paramParseContext, int paramInt)
  {
    for (int i = paramInt; i < this.contextArrayIndex; i++) {
      this.contextArray[i] = null;
    }
    this.contextArrayIndex = paramInt;
    this.context = paramParseContext;
  }
  
  public void close()
  {
    JSONLexer localJSONLexer = getLexer();
    try
    {
      if ((isEnabled(Feature.AutoCloseSource)) && (!localJSONLexer.isEOF())) {
        throw new JSONException("not close json text, token : " + JSONToken.name(localJSONLexer.token()));
      }
    }
    finally
    {
      localJSONLexer.close();
    }
    localJSONLexer.close();
  }
  
  public void config(Feature paramFeature, boolean paramBoolean)
  {
    getLexer().config(paramFeature, paramBoolean);
  }
  
  public ParserConfig getConfig()
  {
    return this.config;
  }
  
  public ParseContext getContext()
  {
    return this.context;
  }
  
  public int getContextLength()
  {
    return this.contextArrayIndex;
  }
  
  public String getDateFomartPattern()
  {
    return this.dateFormatPattern;
  }
  
  public DateFormat getDateFormat()
  {
    if (this.dateFormat == null) {
      this.dateFormat = new SimpleDateFormat(this.dateFormatPattern);
    }
    return this.dateFormat;
  }
  
  public String getInput()
  {
    if ((this.input instanceof char[])) {
      return new String((char[])this.input);
    }
    return this.input.toString();
  }
  
  public ResolveTask getLastResolveTask()
  {
    return (ResolveTask)this.resolveTaskList.get(-1 + this.resolveTaskList.size());
  }
  
  public JSONLexer getLexer()
  {
    return this.lexer;
  }
  
  public Object getObject(String paramString)
  {
    for (int i = 0; i < this.contextArrayIndex; i++) {
      if (paramString.equals(this.contextArray[i].getPath())) {
        return this.contextArray[i].getObject();
      }
    }
    return null;
  }
  
  public int getResolveStatus()
  {
    return this.resolveStatus;
  }
  
  public List<ResolveTask> getResolveTaskList()
  {
    return this.resolveTaskList;
  }
  
  public SymbolTable getSymbolTable()
  {
    return this.symbolTable;
  }
  
  public boolean isEnabled(Feature paramFeature)
  {
    return getLexer().isEnabled(paramFeature);
  }
  
  public Object parse()
  {
    return parse(null);
  }
  
  public Object parse(Object paramObject)
  {
    JSONLexer localJSONLexer = getLexer();
    HashSet localHashSet;
    switch (localJSONLexer.token())
    {
    case 5: 
    case 10: 
    case 11: 
    case 13: 
    case 15: 
    case 16: 
    case 17: 
    case 18: 
    case 19: 
    default: 
      throw new JSONException("syntax error, pos " + localJSONLexer.getBufferPosition());
    case 21: 
      localJSONLexer.nextToken();
      localHashSet = new HashSet();
      parseArray(localHashSet, paramObject);
    }
    boolean bool;
    do
    {
      return localHashSet;
      localJSONLexer.nextToken();
      TreeSet localTreeSet = new TreeSet();
      parseArray(localTreeSet, paramObject);
      return localTreeSet;
      JSONArray localJSONArray = new JSONArray();
      parseArray(localJSONArray, paramObject);
      return localJSONArray;
      return parseObject(new JSONObject(), paramObject);
      Number localNumber2 = localJSONLexer.integerValue();
      localJSONLexer.nextToken();
      return localNumber2;
      Number localNumber1 = localJSONLexer.decimalValue(isEnabled(Feature.UseBigDecimal));
      localJSONLexer.nextToken();
      return localNumber1;
      String str = localJSONLexer.stringVal();
      localJSONLexer.nextToken(16);
      if (localJSONLexer.isEnabled(Feature.AllowISO8601DateFormat))
      {
        JSONScanner localJSONScanner = new JSONScanner(str);
        if (localJSONScanner.scanISO8601DateIfMatch()) {
          return localJSONScanner.getCalendar().getTime();
        }
      }
      return str;
      localJSONLexer.nextToken();
      return null;
      localJSONLexer.nextToken();
      return Boolean.TRUE;
      localJSONLexer.nextToken();
      return Boolean.FALSE;
      localJSONLexer.nextToken(18);
      if (localJSONLexer.token() != 18) {
        throw new JSONException("syntax error");
      }
      localJSONLexer.nextToken(10);
      accept(10);
      long l = localJSONLexer.integerValue().longValue();
      accept(2);
      accept(11);
      return new Date(l);
      bool = localJSONLexer.isBlankInput();
      localHashSet = null;
    } while (bool);
    throw new JSONException("unterminated json string, pos " + localJSONLexer.getBufferPosition());
  }
  
  public <T> List<T> parseArray(Class<T> paramClass)
  {
    ArrayList localArrayList = new ArrayList();
    parseArray(paramClass, localArrayList);
    return localArrayList;
  }
  
  public void parseArray(Class<?> paramClass, Collection paramCollection)
  {
    parseArray(paramClass, paramCollection);
  }
  
  public void parseArray(Type paramType, Collection paramCollection)
  {
    parseArray(paramType, paramCollection, null);
  }
  
  public void parseArray(Type paramType, Collection paramCollection, Object paramObject)
  {
    if ((this.lexer.token() == 21) || (this.lexer.token() == 22)) {
      this.lexer.nextToken();
    }
    if (this.lexer.token() != 14) {
      throw new JSONException("exepct '[', but " + JSONToken.name(this.lexer.token()));
    }
    Object localObject1;
    ParseContext localParseContext;
    if (Integer.TYPE == paramType)
    {
      localObject1 = IntegerDeserializer.instance;
      this.lexer.nextToken(2);
      localParseContext = getContext();
      setContext(paramCollection, paramObject);
    }
    for (int i = 0;; i++)
    {
      try
      {
        if (!isEnabled(Feature.AllowArbitraryCommas)) {
          break label228;
        }
        while (this.lexer.token() == 16) {
          this.lexer.nextToken();
        }
        if (String.class != paramType) {
          break label199;
        }
      }
      finally
      {
        setContext(localParseContext);
      }
      localObject1 = StringDeserializer.instance;
      this.lexer.nextToken(4);
      break;
      label199:
      localObject1 = this.config.getDeserializer(paramType);
      this.lexer.nextToken(((ObjectDeserializer)localObject1).getFastMatchToken());
      break;
      label228:
      int j = this.lexer.token();
      if (j == 15)
      {
        setContext(localParseContext);
        this.lexer.nextToken(16);
        return;
      }
      if (Integer.TYPE == paramType)
      {
        paramCollection.add(IntegerDeserializer.deserialze(this));
        if (this.lexer.token() == 16) {
          this.lexer.nextToken(((ObjectDeserializer)localObject1).getFastMatchToken());
        }
      }
      else
      {
        if (String.class == paramType)
        {
          String str;
          if (this.lexer.token() == 4)
          {
            str = this.lexer.stringVal();
            this.lexer.nextToken(16);
          }
          for (;;)
          {
            paramCollection.add(str);
            break;
            Object localObject3 = parse();
            if (localObject3 == null) {
              str = null;
            } else {
              str = localObject3.toString();
            }
          }
        }
        if (this.lexer.token() == 8) {
          this.lexer.nextToken();
        }
        Object localObject4;
        for (Object localObject5 = null;; localObject5 = localObject4)
        {
          paramCollection.add(localObject5);
          checkListResolve(paramCollection);
          break;
          localObject4 = ((ObjectDeserializer)localObject1).deserialze(this, paramType, Integer.valueOf(i));
        }
      }
    }
  }
  
  public final void parseArray(Collection paramCollection)
  {
    parseArray(paramCollection, null);
  }
  
  public final void parseArray(Collection paramCollection, Object paramObject)
  {
    JSONLexer localJSONLexer = getLexer();
    if ((localJSONLexer.token() == 21) || (localJSONLexer.token() == 22)) {
      localJSONLexer.nextToken();
    }
    if (localJSONLexer.token() != 14) {
      throw new JSONException("syntax error, expect [, actual " + JSONToken.name(localJSONLexer.token()) + ", pos " + localJSONLexer.pos());
    }
    localJSONLexer.nextToken(4);
    ParseContext localParseContext = getContext();
    setContext(paramCollection, paramObject);
    int i = 0;
    try
    {
      if (isEnabled(Feature.AllowArbitraryCommas)) {
        while (localJSONLexer.token() == 16) {
          localJSONLexer.nextToken();
        }
      }
      switch (localJSONLexer.token())
      {
      }
    }
    finally
    {
      setContext(localParseContext);
    }
    Object localObject2 = parse();
    for (;;)
    {
      paramCollection.add(localObject2);
      checkListResolve(paramCollection);
      String str;
      if (localJSONLexer.token() == 16)
      {
        localJSONLexer.nextToken(4);
        break label508;
        localObject2 = localJSONLexer.integerValue();
        localJSONLexer.nextToken(16);
        continue;
        if (localJSONLexer.isEnabled(Feature.UseBigDecimal)) {}
        for (localObject2 = localJSONLexer.decimalValue(true);; localObject2 = localJSONLexer.decimalValue(false))
        {
          localJSONLexer.nextToken(16);
          break;
        }
        str = localJSONLexer.stringVal();
        localJSONLexer.nextToken(16);
        if (!localJSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
          break label521;
        }
        JSONScanner localJSONScanner = new JSONScanner(str);
        if (!localJSONScanner.scanISO8601DateIfMatch()) {
          break label514;
        }
        localObject2 = localJSONScanner.getCalendar().getTime();
        continue;
        localObject2 = Boolean.TRUE;
        localJSONLexer.nextToken(16);
        continue;
        localObject2 = Boolean.FALSE;
        localJSONLexer.nextToken(16);
        continue;
        localObject2 = parseObject(new JSONObject(), Integer.valueOf(i));
        continue;
        JSONArray localJSONArray = new JSONArray();
        parseArray(localJSONArray, Integer.valueOf(i));
        localObject2 = localJSONArray;
        continue;
        localJSONLexer.nextToken(4);
        localObject2 = null;
        continue;
        localJSONLexer.nextToken(16);
        setContext(localParseContext);
        return;
      }
      label508:
      i++;
      break;
      label514:
      localObject2 = str;
      continue;
      label521:
      localObject2 = str;
    }
  }
  
  public Object[] parseArray(Type[] paramArrayOfType)
  {
    if (this.lexer.token() == 8)
    {
      this.lexer.nextToken(16);
      return null;
    }
    if (this.lexer.token() != 14) {
      throw new JSONException("syntax error : " + this.lexer.tokenName());
    }
    Object[] arrayOfObject = new Object[paramArrayOfType.length];
    if (paramArrayOfType.length == 0)
    {
      this.lexer.nextToken(15);
      if (this.lexer.token() != 15) {
        throw new JSONException("syntax error");
      }
      this.lexer.nextToken(16);
      return new Object[0];
    }
    this.lexer.nextToken(2);
    int i = 0;
    Object localObject;
    if (i < paramArrayOfType.length)
    {
      if (this.lexer.token() != 8) {
        break label230;
      }
      localObject = null;
      this.lexer.nextToken(16);
    }
    for (;;)
    {
      arrayOfObject[i] = localObject;
      if (this.lexer.token() != 15) {
        break;
      }
      if (this.lexer.token() == 15) {
        break label721;
      }
      throw new JSONException("syntax error");
      label230:
      Type localType = paramArrayOfType[i];
      if ((localType == Integer.TYPE) || (localType == Integer.class))
      {
        if (this.lexer.token() == 2)
        {
          localObject = Integer.valueOf(this.lexer.intValue());
          this.lexer.nextToken(16);
        }
        else
        {
          localObject = TypeUtils.cast(parse(), localType, this.config);
        }
      }
      else if (localType == String.class)
      {
        if (this.lexer.token() == 4)
        {
          localObject = this.lexer.stringVal();
          this.lexer.nextToken(16);
        }
        else
        {
          localObject = TypeUtils.cast(parse(), localType, this.config);
        }
      }
      else
      {
        int j = -1 + paramArrayOfType.length;
        Class localClass1 = null;
        boolean bool1 = false;
        if (i == j)
        {
          boolean bool2 = localType instanceof Class;
          localClass1 = null;
          bool1 = false;
          if (bool2)
          {
            Class localClass2 = (Class)localType;
            bool1 = localClass2.isArray();
            localClass1 = localClass2.getComponentType();
          }
        }
        if ((bool1) && (this.lexer.token() != 14))
        {
          ArrayList localArrayList = new ArrayList();
          ObjectDeserializer localObjectDeserializer = this.config.getDeserializer(localClass1);
          int k = localObjectDeserializer.getFastMatchToken();
          if (this.lexer.token() != 15)
          {
            for (;;)
            {
              localArrayList.add(localObjectDeserializer.deserialze(this, localType, null));
              if (this.lexer.token() != 16) {
                break;
              }
              this.lexer.nextToken(k);
            }
            if (this.lexer.token() != 15) {}
          }
          else
          {
            localObject = TypeUtils.cast(localArrayList, localType, this.config);
            continue;
          }
          throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
        }
        else
        {
          localObject = this.config.getDeserializer(localType).deserialze(this, localType, null);
        }
      }
    }
    if (this.lexer.token() != 16) {
      throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
    }
    if (i == -1 + paramArrayOfType.length) {
      this.lexer.nextToken(15);
    }
    for (;;)
    {
      i++;
      break;
      this.lexer.nextToken(2);
    }
    label721:
    this.lexer.nextToken(16);
    return arrayOfObject;
  }
  
  public Object parseArrayWithType(Type paramType)
  {
    if (this.lexer.token() == 8)
    {
      this.lexer.nextToken();
      return null;
    }
    Type[] arrayOfType1 = ((ParameterizedType)paramType).getActualTypeArguments();
    if (arrayOfType1.length != 1) {
      throw new JSONException("not support type " + paramType);
    }
    Type localType1 = arrayOfType1[0];
    if ((localType1 instanceof Class))
    {
      ArrayList localArrayList1 = new ArrayList();
      parseArray((Class)localType1, localArrayList1);
      return localArrayList1;
    }
    if ((localType1 instanceof WildcardType))
    {
      WildcardType localWildcardType = (WildcardType)localType1;
      Type localType3 = localWildcardType.getUpperBounds()[0];
      if (Object.class.equals(localType3))
      {
        if (localWildcardType.getLowerBounds().length == 0) {
          return parse();
        }
        throw new JSONException("not support type : " + paramType);
      }
      ArrayList localArrayList4 = new ArrayList();
      parseArray((Class)localType3, localArrayList4);
      return localArrayList4;
    }
    if ((localType1 instanceof TypeVariable))
    {
      TypeVariable localTypeVariable = (TypeVariable)localType1;
      Type[] arrayOfType2 = localTypeVariable.getBounds();
      if (arrayOfType2.length != 1) {
        throw new JSONException("not support : " + localTypeVariable);
      }
      Type localType2 = arrayOfType2[0];
      if ((localType2 instanceof Class))
      {
        ArrayList localArrayList3 = new ArrayList();
        parseArray((Class)localType2, localArrayList3);
        return localArrayList3;
      }
    }
    if ((localType1 instanceof ParameterizedType))
    {
      ParameterizedType localParameterizedType = (ParameterizedType)localType1;
      ArrayList localArrayList2 = new ArrayList();
      parseArray(localParameterizedType, localArrayList2);
      return localArrayList2;
    }
    throw new JSONException("TODO : " + paramType);
  }
  
  public JSONObject parseObject()
  {
    JSONObject localJSONObject = new JSONObject();
    parseObject(localJSONObject);
    return localJSONObject;
  }
  
  public <T> T parseObject(Class<T> paramClass)
  {
    return (T)parseObject(paramClass);
  }
  
  public <T> T parseObject(Type paramType)
  {
    if (this.lexer.token() == 8)
    {
      this.lexer.nextToken();
      return null;
    }
    ObjectDeserializer localObjectDeserializer = this.config.getDeserializer(paramType);
    try
    {
      Object localObject = localObjectDeserializer.deserialze(this, paramType, null);
      return (T)localObject;
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
  
  public Object parseObject(Map paramMap)
  {
    return parseObject(paramMap, null);
  }
  
  public final Object parseObject(Map paramMap, Object paramObject)
  {
    JSONScanner localJSONScanner1 = (JSONScanner)this.lexer;
    if ((localJSONScanner1.token() != 12) && (localJSONScanner1.token() != 16)) {
      throw new JSONException("syntax error, expect {, actual " + localJSONScanner1.tokenName());
    }
    ParseContext localParseContext1 = getContext();
    int i = 0;
    int j;
    Object localObject2;
    int m;
    try
    {
      localJSONScanner1.skipWhitespace();
      j = localJSONScanner1.getCurrent();
      if (isEnabled(Feature.AllowArbitraryCommas)) {
        while (j == 44)
        {
          localJSONScanner1.incrementBufferPosition();
          localJSONScanner1.skipWhitespace();
          j = localJSONScanner1.getCurrent();
        }
      }
      if (j == 34)
      {
        localObject2 = localJSONScanner1.scanSymbol(this.symbolTable, '"');
        localJSONScanner1.skipWhitespace();
        int k = localJSONScanner1.getCurrent();
        m = 0;
        if (k == 58) {
          break label459;
        }
        throw new JSONException("expect ':' at " + localJSONScanner1.pos() + ", name " + localObject2);
      }
    }
    finally
    {
      setContext(localParseContext1);
    }
    if (j == 125)
    {
      localJSONScanner1.incrementBufferPosition();
      localJSONScanner1.resetStringPosition();
      localJSONScanner1.nextToken();
      setContext(localParseContext1);
      return paramMap;
    }
    if (j == 39)
    {
      if (!isEnabled(Feature.AllowSingleQuotes)) {
        throw new JSONException("syntax error");
      }
      localObject2 = localJSONScanner1.scanSymbol(this.symbolTable, '\'');
      localJSONScanner1.skipWhitespace();
      int i3 = localJSONScanner1.getCurrent();
      m = 0;
      if (i3 != 58) {
        throw new JSONException("expect ':' at " + localJSONScanner1.pos());
      }
    }
    else
    {
      if (j == 26) {
        throw new JSONException("syntax error");
      }
      if (j != 44) {
        break label1685;
      }
      throw new JSONException("syntax error");
      label356:
      localJSONScanner1.resetStringPosition();
      localJSONScanner1.scanNumber();
      if (localJSONScanner1.token() == 2) {}
      for (localObject2 = localJSONScanner1.integerValue();; localObject2 = localJSONScanner1.decimalValue(true))
      {
        int i2 = localJSONScanner1.getCurrent();
        m = 0;
        if (i2 == 58) {
          break;
        }
        throw new JSONException("expect ':' at " + localJSONScanner1.pos() + ", name " + localObject2);
      }
      label446:
      localJSONScanner1.nextToken();
      localObject2 = parse();
      m = 1;
    }
    label459:
    int n;
    Class localClass;
    label529:
    char c;
    do
    {
      if (m == 0)
      {
        localJSONScanner1.incrementBufferPosition();
        localJSONScanner1.skipWhitespace();
      }
      n = localJSONScanner1.getCurrent();
      localJSONScanner1.resetStringPosition();
      if (localObject2 != JSON.DEFAULT_TYPE_KEY) {
        break label805;
      }
      String str3 = localJSONScanner1.scanSymbol(this.symbolTable, '"');
      localClass = TypeUtils.loadClass(str3);
      if (localClass != null) {
        break label621;
      }
      paramMap.put(JSON.DEFAULT_TYPE_KEY, str3);
      break;
      if (!isEnabled(Feature.AllowUnQuotedFieldNames)) {
        throw new JSONException("syntax error");
      }
      localObject2 = localJSONScanner1.scanSymbolUnQuoted(this.symbolTable);
      localJSONScanner1.skipWhitespace();
      c = localJSONScanner1.getCurrent();
      m = 0;
    } while (c == ':');
    throw new JSONException("expect ':' at " + localJSONScanner1.pos() + ", actual " + c);
    label621:
    localJSONScanner1.nextToken(16);
    if (localJSONScanner1.token() == 13)
    {
      localJSONScanner1.nextToken(16);
      try
      {
        ObjectDeserializer localObjectDeserializer = this.config.getDeserializer(localClass);
        Object localObject10;
        if ((localObjectDeserializer instanceof ASMJavaBeanDeserializer)) {
          localObject10 = ((ASMJavaBeanDeserializer)localObjectDeserializer).createInstance(this, localClass);
        }
        for (;;)
        {
          if (localObject10 == null)
          {
            Object localObject12 = localClass.newInstance();
            localObject10 = localObject12;
          }
          setContext(localParseContext1);
          return localObject10;
          boolean bool = localObjectDeserializer instanceof JavaBeanDeserializer;
          localObject10 = null;
          if (bool)
          {
            Object localObject11 = ((JavaBeanDeserializer)localObjectDeserializer).createInstance(this, localClass);
            localObject10 = localObject11;
          }
        }
        JSONException localJSONException;
        setResolveStatus(2);
      }
      catch (Exception localException)
      {
        localJSONException = new JSONException("create instance error", localException);
        throw localJSONException;
      }
    }
    if ((this.context != null) && (!(paramObject instanceof Integer))) {
      popContext();
    }
    Object localObject9 = this.config.getDeserializer(localClass).deserialze(this, localClass, paramObject);
    setContext(localParseContext1);
    return localObject9;
    label805:
    Object localObject4;
    label865:
    Object localObject5;
    Object localObject3;
    int i1;
    if (localObject2 == "$ref")
    {
      localJSONScanner1.nextToken(4);
      if (localJSONScanner1.token() == 4)
      {
        String str1 = localJSONScanner1.stringVal();
        localJSONScanner1.nextToken(13);
        if ("@".equals(str1))
        {
          if (getContext() == null) {
            break label1740;
          }
          localObject4 = getContext().getObject();
          if (localJSONScanner1.token() != 13) {
            throw new JSONException("syntax error");
          }
        }
        else
        {
          if ("..".equals(str1))
          {
            ParseContext localParseContext3 = localParseContext1.getParentContext();
            if (localParseContext3.getObject() != null)
            {
              localObject5 = getContext().getObject();
              break label1726;
            }
            ResolveTask localResolveTask3 = new ResolveTask(localParseContext3, str1);
            addResolveTask(localResolveTask3);
            setResolveStatus(1);
            localObject5 = null;
            break label1726;
          }
          if ("$".equals(str1))
          {
            for (ParseContext localParseContext2 = localParseContext1; localParseContext2.getParentContext() != null; localParseContext2 = localParseContext2.getParentContext()) {}
            if (localParseContext2.getObject() != null)
            {
              localObject3 = localParseContext2.getObject();
              break label1733;
            }
            ResolveTask localResolveTask1 = new ResolveTask(localParseContext2, str1);
            addResolveTask(localResolveTask1);
            setResolveStatus(1);
            localObject3 = null;
            break label1733;
          }
          ResolveTask localResolveTask2 = new ResolveTask(localParseContext1, str1);
          addResolveTask(localResolveTask2);
          setResolveStatus(1);
          break label1740;
        }
        localJSONScanner1.nextToken(16);
        setContext(localParseContext1);
        return localObject4;
      }
      else
      {
        throw new JSONException("illegal ref, " + JSONToken.name(localJSONScanner1.token()));
      }
    }
    else
    {
      if (i == 0)
      {
        setContext(paramMap, paramObject);
        i = 1;
      }
      if (n != 34) {
        break label1746;
      }
      localJSONScanner1.scanString();
      String str2 = localJSONScanner1.stringVal();
      Object localObject6 = str2;
      if (localJSONScanner1.isEnabled(Feature.AllowISO8601DateFormat))
      {
        JSONScanner localJSONScanner2 = new JSONScanner(str2);
        if (localJSONScanner2.scanISO8601DateIfMatch()) {
          localObject6 = localJSONScanner2.getCalendar().getTime();
        }
      }
      if (paramMap.getClass() == JSONObject.class) {
        paramMap.put(localObject2.toString(), localObject6);
      }
      for (;;)
      {
        localJSONScanner1.skipWhitespace();
        i1 = localJSONScanner1.getCurrent();
        if (i1 != 44) {
          break label1609;
        }
        localJSONScanner1.incrementBufferPosition();
        break;
        paramMap.put(localObject2, localObject6);
      }
    }
    for (;;)
    {
      label1247:
      localJSONScanner1.scanNumber();
      if (localJSONScanner1.token() == 2) {}
      for (Object localObject7 = localJSONScanner1.integerValue();; localObject7 = localJSONScanner1.decimalValue())
      {
        paramMap.put(localObject2, localObject7);
        break;
      }
      label1609:
      label1685:
      label1726:
      label1733:
      label1740:
      label1746:
      do
      {
        if (n == 91)
        {
          localJSONScanner1.nextToken();
          JSONArray localJSONArray = new JSONArray();
          parseArray(localJSONArray, localObject2);
          paramMap.put(localObject2, localJSONArray);
          if (localJSONScanner1.token() == 13)
          {
            localJSONScanner1.nextToken();
            setContext(localParseContext1);
            return paramMap;
          }
          if (localJSONScanner1.token() == 16) {
            break;
          }
          throw new JSONException("syntax error");
        }
        if (n == 123)
        {
          localJSONScanner1.nextToken();
          Object localObject8 = parseObject(new JSONObject(), localObject2);
          checkMapResolve(paramMap, localObject2.toString());
          if (paramMap.getClass() == JSONObject.class) {
            paramMap.put(localObject2.toString(), localObject8);
          }
          for (;;)
          {
            setContext(localParseContext1, localObject8, localObject2);
            if (localJSONScanner1.token() != 13) {
              break;
            }
            localJSONScanner1.nextToken();
            setContext(localParseContext1);
            setContext(localParseContext1);
            return paramMap;
            paramMap.put(localObject2, localObject8);
          }
          if (localJSONScanner1.token() == 16) {
            break;
          }
          throw new JSONException("syntax error, " + localJSONScanner1.tokenName());
        }
        localJSONScanner1.nextToken();
        paramMap.put(localObject2, parse());
        if (localJSONScanner1.token() == 13)
        {
          localJSONScanner1.nextToken();
          setContext(localParseContext1);
          return paramMap;
        }
        if (localJSONScanner1.token() == 16) {
          break;
        }
        throw new JSONException("syntax error, position at " + localJSONScanner1.pos() + ", name " + localObject2);
        if (i1 == 125)
        {
          localJSONScanner1.incrementBufferPosition();
          localJSONScanner1.resetStringPosition();
          localJSONScanner1.nextToken();
          setContext(paramMap, paramObject);
          setContext(localParseContext1);
          return paramMap;
        }
        throw new JSONException("syntax error, position at " + localJSONScanner1.pos() + ", name " + localObject2);
        if (((j >= 48) && (j <= 57)) || (j == 45)) {
          break label356;
        }
        if (j == 123) {
          break label446;
        }
        if (j != 91) {
          break label529;
        }
        break label446;
        localObject4 = localObject5;
        break label865;
        localObject4 = localObject3;
        break label865;
        localObject4 = null;
        break label865;
        if ((n >= 48) && (n <= 57)) {
          break label1247;
        }
      } while (n != 45);
    }
  }
  
  public void parseObject(Object paramObject)
  {
    this.derializer.parseObject(this, paramObject);
  }
  
  public void popContext()
  {
    if (isEnabled(Feature.DisableCircularReferenceDetect)) {
      return;
    }
    this.context = this.context.getParentContext();
    this.contextArray[(-1 + this.contextArrayIndex)] = null;
    this.contextArrayIndex = (-1 + this.contextArrayIndex);
  }
  
  public void setConfig(ParserConfig paramParserConfig)
  {
    this.config = paramParserConfig;
  }
  
  public ParseContext setContext(ParseContext paramParseContext, Object paramObject1, Object paramObject2)
  {
    if (isEnabled(Feature.DisableCircularReferenceDetect)) {
      return null;
    }
    this.context = new ParseContext(paramParseContext, paramObject1, paramObject2);
    addContext(this.context);
    return this.context;
  }
  
  public ParseContext setContext(Object paramObject1, Object paramObject2)
  {
    if (isEnabled(Feature.DisableCircularReferenceDetect)) {
      return null;
    }
    return setContext(this.context, paramObject1, paramObject2);
  }
  
  public void setContext(ParseContext paramParseContext)
  {
    if (isEnabled(Feature.DisableCircularReferenceDetect)) {
      return;
    }
    this.context = paramParseContext;
  }
  
  public void setDateFomrat(DateFormat paramDateFormat)
  {
    this.dateFormat = paramDateFormat;
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateFormatPattern = paramString;
    this.dateFormat = null;
  }
  
  public void setResolveStatus(int paramInt)
  {
    this.resolveStatus = paramInt;
  }
  
  public static class ResolveTask
  {
    private final ParseContext context;
    private FieldDeserializer fieldDeserializer;
    private ParseContext ownerContext;
    private final String referenceValue;
    
    public ResolveTask(ParseContext paramParseContext, String paramString)
    {
      this.context = paramParseContext;
      this.referenceValue = paramString;
    }
    
    public ParseContext getContext()
    {
      return this.context;
    }
    
    public FieldDeserializer getFieldDeserializer()
    {
      return this.fieldDeserializer;
    }
    
    public ParseContext getOwnerContext()
    {
      return this.ownerContext;
    }
    
    public String getReferenceValue()
    {
      return this.referenceValue;
    }
    
    public void setFieldDeserializer(FieldDeserializer paramFieldDeserializer)
    {
      this.fieldDeserializer = paramFieldDeserializer;
    }
    
    public void setOwnerContext(ParseContext paramParseContext)
    {
      this.ownerContext = paramParseContext;
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\DefaultJSONParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */