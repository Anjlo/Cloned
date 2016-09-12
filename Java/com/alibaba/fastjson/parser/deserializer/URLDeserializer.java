package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

public class URLDeserializer
  implements ObjectDeserializer
{
  public static final URLDeserializer instance = new URLDeserializer();
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    String str = (String)paramDefaultJSONParser.parse();
    if (str == null) {
      return null;
    }
    try
    {
      URL localURL = new URL(str);
      return localURL;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      throw new JSONException("create url error", localMalformedURLException);
    }
  }
  
  public int getFastMatchToken()
  {
    return 4;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\URLDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */