package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ThrowableDeserializer
  extends JavaBeanDeserializer
{
  public ThrowableDeserializer(ParserConfig paramParserConfig, Class<?> paramClass)
  {
    super(paramParserConfig, paramClass);
  }
  
  private Throwable createException(String paramString, Throwable paramThrowable, Class<?> paramClass)
    throws Exception
  {
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Constructor[] arrayOfConstructor = paramClass.getConstructors();
    int i = arrayOfConstructor.length;
    int j = 0;
    if (j < i)
    {
      Constructor localConstructor = arrayOfConstructor[j];
      if (localConstructor.getParameterTypes().length == 0) {
        localObject1 = localConstructor;
      }
      for (;;)
      {
        j++;
        break;
        if ((localConstructor.getParameterTypes().length == 1) && (localConstructor.getParameterTypes()[0] == String.class)) {
          localObject2 = localConstructor;
        } else if ((localConstructor.getParameterTypes().length == 2) && (localConstructor.getParameterTypes()[0] == String.class) && (localConstructor.getParameterTypes()[1] == Throwable.class)) {
          localObject3 = localConstructor;
        }
      }
    }
    if (localObject3 != null) {
      return (Throwable)((Constructor)localObject3).newInstance(new Object[] { paramString, paramThrowable });
    }
    if (localObject2 != null) {
      return (Throwable)((Constructor)localObject2).newInstance(new Object[] { paramString });
    }
    if (localObject1 != null) {
      return (Throwable)((Constructor)localObject1).newInstance(new Object[0]);
    }
    return null;
  }
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONScanner localJSONScanner = (JSONScanner)paramDefaultJSONParser.getLexer();
    Throwable localThrowable;
    Object localObject1;
    String str1;
    StackTraceElement[] arrayOfStackTraceElement;
    HashMap localHashMap;
    label88:
    String str2;
    Object localObject2;
    if (paramDefaultJSONParser.getResolveStatus() == 2)
    {
      paramDefaultJSONParser.setResolveStatus(0);
      localThrowable = null;
      localObject1 = null;
      if (paramType != null)
      {
        boolean bool1 = paramType instanceof Class;
        localObject1 = null;
        if (bool1)
        {
          Class localClass = (Class)paramType;
          boolean bool2 = Throwable.class.isAssignableFrom(localClass);
          localObject1 = null;
          if (bool2) {
            localObject1 = localClass;
          }
        }
      }
      str1 = null;
      arrayOfStackTraceElement = null;
      localHashMap = new HashMap();
      str2 = localJSONScanner.scanSymbol(paramDefaultJSONParser.getSymbolTable());
      if (str2 != null) {
        break label195;
      }
      if (localJSONScanner.token() != 13) {
        break label174;
      }
      localJSONScanner.nextToken(16);
      if (localObject1 != null) {
        break label405;
      }
      localObject2 = new Exception(str1, localThrowable);
    }
    for (;;)
    {
      if (arrayOfStackTraceElement != null) {
        ((Throwable)localObject2).setStackTrace(arrayOfStackTraceElement);
      }
      return (T)localObject2;
      if (localJSONScanner.token() == 12) {
        break;
      }
      throw new JSONException("syntax error");
      label174:
      if ((localJSONScanner.token() == 16) && (localJSONScanner.isEnabled(Feature.AllowArbitraryCommas))) {
        break label88;
      }
      label195:
      localJSONScanner.nextTokenWithColon(4);
      if (JSON.DEFAULT_TYPE_KEY.equals(str2)) {
        if (localJSONScanner.token() == 4)
        {
          localObject1 = TypeUtils.loadClass(localJSONScanner.stringVal());
          localJSONScanner.nextToken(16);
        }
      }
      for (;;)
      {
        if ((localJSONScanner.token() == 16) || (localJSONScanner.token() != 13)) {
          break label403;
        }
        localJSONScanner.nextToken(16);
        break;
        throw new JSONException("syntax error");
        if ("message".equals(str2))
        {
          if (localJSONScanner.token() == 8) {}
          for (str1 = null;; str1 = localJSONScanner.stringVal())
          {
            localJSONScanner.nextToken();
            break;
            if (localJSONScanner.token() != 4) {
              break label328;
            }
          }
          label328:
          throw new JSONException("syntax error");
        }
        if ("cause".equals(str2)) {
          localThrowable = (Throwable)deserialze(paramDefaultJSONParser, null, "cause");
        } else if ("stackTrace".equals(str2)) {
          arrayOfStackTraceElement = (StackTraceElement[])paramDefaultJSONParser.parseObject(StackTraceElement[].class);
        } else {
          localHashMap.put(str2, paramDefaultJSONParser.parse());
        }
      }
      label403:
      break label88;
      try
      {
        label405:
        localObject2 = createException(str1, localThrowable, (Class)localObject1);
        if (localObject2 != null) {
          continue;
        }
        Exception localException2 = new Exception(str1, localThrowable);
        localObject2 = localException2;
      }
      catch (Exception localException1)
      {
        throw new JSONException("create instance error", localException1);
      }
    }
  }
  
  public int getFastMatchToken()
  {
    return 12;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\ThrowableDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */