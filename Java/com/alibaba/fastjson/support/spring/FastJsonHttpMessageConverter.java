package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class FastJsonHttpMessageConverter
  extends AbstractHttpMessageConverter<Object>
{
  public static final Charset UTF8 = Charset.forName("UTF-8");
  private Charset charset = UTF8;
  private SerializerFeature[] serializerFeature;
  
  public FastJsonHttpMessageConverter()
  {
    super(arrayOfMediaType);
  }
  
  public Charset getCharset()
  {
    return this.charset;
  }
  
  public SerializerFeature[] getFeatures()
  {
    return this.serializerFeature;
  }
  
  protected Object readInternal(Class<? extends Object> paramClass, HttpInputMessage paramHttpInputMessage)
    throws IOException, HttpMessageNotReadableException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    InputStream localInputStream = paramHttpInputMessage.getBody();
    byte[] arrayOfByte1 = new byte['Ѐ'];
    byte[] arrayOfByte2;
    for (;;)
    {
      int i = localInputStream.read(arrayOfByte1);
      if (i == -1)
      {
        arrayOfByte2 = localByteArrayOutputStream.toByteArray();
        if (this.charset != UTF8) {
          break;
        }
        return JSON.parseObject(arrayOfByte2, paramClass, new Feature[0]);
      }
      if (i > 0) {
        localByteArrayOutputStream.write(arrayOfByte1, 0, i);
      }
    }
    return JSON.parseObject(arrayOfByte2, 0, arrayOfByte2.length, this.charset.newDecoder(), paramClass, new Feature[0]);
  }
  
  public void setCharset(Charset paramCharset)
  {
    this.charset = paramCharset;
  }
  
  public void setFeatures(SerializerFeature... paramVarArgs)
  {
    this.serializerFeature = paramVarArgs;
  }
  
  protected boolean supports(Class<?> paramClass)
  {
    return true;
  }
  
  protected void writeInternal(Object paramObject, HttpOutputMessage paramHttpOutputMessage)
    throws IOException, HttpMessageNotWritableException
  {
    OutputStream localOutputStream = paramHttpOutputMessage.getBody();
    byte[] arrayOfByte;
    if (this.charset == UTF8)
    {
      if (this.serializerFeature != null) {}
      for (arrayOfByte = JSON.toJSONBytes(paramObject, this.serializerFeature);; arrayOfByte = JSON.toJSONBytes(paramObject, new SerializerFeature[0]))
      {
        localOutputStream.write(arrayOfByte);
        return;
      }
    }
    if (this.serializerFeature != null) {}
    for (String str = JSON.toJSONString(paramObject, this.serializerFeature);; str = JSON.toJSONString(paramObject))
    {
      arrayOfByte = str.getBytes(this.charset);
      break;
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\support\spring\FastJsonHttpMessageConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */