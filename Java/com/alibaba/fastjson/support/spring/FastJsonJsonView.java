package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

public class FastJsonJsonView
  extends AbstractView
{
  public static final String DEFAULT_CONTENT_TYPE = "application/json";
  public static final Charset UTF8 = Charset.forName("UTF-8");
  private Charset charset = UTF8;
  private boolean disableCaching = true;
  private Set<String> renderedAttributes;
  private SerializerFeature[] serializerFeature;
  private boolean updateContentLength = false;
  
  public FastJsonJsonView()
  {
    setContentType("application/json");
    setExposePathVariables(false);
  }
  
  protected Object filterModel(Map<String, Object> paramMap)
  {
    HashMap localHashMap = new HashMap(paramMap.size());
    if (!CollectionUtils.isEmpty(this.renderedAttributes)) {}
    for (Set localSet = this.renderedAttributes;; localSet = paramMap.keySet())
    {
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if ((!(localEntry.getValue() instanceof BindingResult)) && (localSet.contains(localEntry.getKey()))) {
          localHashMap.put(localEntry.getKey(), localEntry.getValue());
        }
      }
    }
    return localHashMap;
  }
  
  public Charset getCharset()
  {
    return this.charset;
  }
  
  public SerializerFeature[] getFeatures()
  {
    return this.serializerFeature;
  }
  
  protected void prepareResponse(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    setResponseContentType(paramHttpServletRequest, paramHttpServletResponse);
    paramHttpServletResponse.setCharacterEncoding(UTF8.name());
    if (this.disableCaching)
    {
      paramHttpServletResponse.addHeader("Pragma", "no-cache");
      paramHttpServletResponse.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
      paramHttpServletResponse.addDateHeader("Expires", 1L);
    }
  }
  
  protected void renderMergedOutputModel(Map<String, Object> paramMap, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    Object localObject1 = filterModel(paramMap);
    byte[] arrayOfByte;
    if (this.charset == UTF8) {
      if (this.serializerFeature != null)
      {
        arrayOfByte = JSON.toJSONBytes(localObject1, this.serializerFeature);
        if (!this.updateContentLength) {
          break label129;
        }
      }
    }
    label129:
    for (Object localObject2 = createTemporaryOutputStream();; localObject2 = paramHttpServletResponse.getOutputStream())
    {
      ((OutputStream)localObject2).write(arrayOfByte);
      if (this.updateContentLength) {
        writeToResponse(paramHttpServletResponse, (ByteArrayOutputStream)localObject2);
      }
      return;
      arrayOfByte = JSON.toJSONBytes(localObject1, new SerializerFeature[0]);
      break;
      if (this.serializerFeature != null) {}
      for (String str = JSON.toJSONString(localObject1, this.serializerFeature);; str = JSON.toJSONString(localObject1))
      {
        arrayOfByte = str.getBytes(this.charset);
        break;
      }
    }
  }
  
  public void setCharset(Charset paramCharset)
  {
    this.charset = paramCharset;
  }
  
  public void setDisableCaching(boolean paramBoolean)
  {
    this.disableCaching = paramBoolean;
  }
  
  public void setFeatures(SerializerFeature... paramVarArgs)
  {
    this.serializerFeature = paramVarArgs;
  }
  
  public void setRenderedAttributes(Set<String> paramSet)
  {
    this.renderedAttributes = paramSet;
  }
  
  public void setSerializerFeature(SerializerFeature[] paramArrayOfSerializerFeature)
  {
    this.serializerFeature = paramArrayOfSerializerFeature;
  }
  
  public void setUpdateContentLength(boolean paramBoolean)
  {
    this.updateContentLength = paramBoolean;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\support\spring\FastJsonJsonView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */