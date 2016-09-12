package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressDeserializer
  implements ObjectDeserializer
{
  public static final InetAddressDeserializer instance = new InetAddressDeserializer();
  
  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    String str = (String)paramDefaultJSONParser.parse();
    if (str == null) {}
    while (str.length() == 0) {
      return null;
    }
    try
    {
      InetAddress localInetAddress = InetAddress.getByName(str);
      return localInetAddress;
    }
    catch (UnknownHostException localUnknownHostException)
    {
      throw new JSONException("deserialize error", localUnknownHostException);
    }
  }
  
  public int getFastMatchToken()
  {
    return 4;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\deserializer\InetAddressDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */