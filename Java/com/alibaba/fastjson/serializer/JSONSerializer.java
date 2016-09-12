package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.util.ServiceLoader;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public class JSONSerializer
{
  private final SerializeConfig config;
  private SerialContext context;
  private DateFormat dateFormat;
  private String dateFormatPatterm = JSON.DEFFAULT_DATE_FORMAT;
  private String indent = "\t";
  private int indentCount = 0;
  private List<NameFilter> nameFilters = null;
  private final SerializeWriter out;
  private List<PropertyFilter> propertyFilters = null;
  private List<PropertyPreFilter> propertyPreFilters = null;
  private IdentityHashMap<Object, SerialContext> references = null;
  private List<ValueFilter> valueFilters = null;
  
  public JSONSerializer()
  {
    this(new SerializeWriter(), SerializeConfig.getGlobalInstance());
  }
  
  @Deprecated
  public JSONSerializer(JSONSerializerMap paramJSONSerializerMap)
  {
    this(new SerializeWriter(), paramJSONSerializerMap);
  }
  
  public JSONSerializer(SerializeConfig paramSerializeConfig)
  {
    this(new SerializeWriter(), paramSerializeConfig);
  }
  
  public JSONSerializer(SerializeWriter paramSerializeWriter)
  {
    this(paramSerializeWriter, SerializeConfig.getGlobalInstance());
  }
  
  public JSONSerializer(SerializeWriter paramSerializeWriter, SerializeConfig paramSerializeConfig)
  {
    this.out = paramSerializeWriter;
    this.config = paramSerializeConfig;
  }
  
  public static final void write(SerializeWriter paramSerializeWriter, Object paramObject)
  {
    new JSONSerializer(paramSerializeWriter).write(paramObject);
  }
  
  public static final void write(Writer paramWriter, Object paramObject)
  {
    SerializeWriter localSerializeWriter = new SerializeWriter();
    try
    {
      new JSONSerializer(localSerializeWriter).write(paramObject);
      localSerializeWriter.writeTo(paramWriter);
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
  
  public void close()
  {
    this.out.close();
  }
  
  public void config(SerializerFeature paramSerializerFeature, boolean paramBoolean)
  {
    this.out.config(paramSerializerFeature, paramBoolean);
  }
  
  public boolean containsReference(Object paramObject)
  {
    if (isEnabled(SerializerFeature.DisableCircularReferenceDetect)) {}
    while (this.references == null) {
      return false;
    }
    return this.references.containsKey(paramObject);
  }
  
  public void decrementIdent()
  {
    this.indentCount = (-1 + this.indentCount);
  }
  
  public SerialContext getContext()
  {
    return this.context;
  }
  
  public DateFormat getDateFormat()
  {
    if (this.dateFormat == null) {
      this.dateFormat = new SimpleDateFormat(this.dateFormatPatterm);
    }
    return this.dateFormat;
  }
  
  public String getDateFormatPattern()
  {
    return this.dateFormatPatterm;
  }
  
  public int getIndentCount()
  {
    return this.indentCount;
  }
  
  public SerializeConfig getMapping()
  {
    return this.config;
  }
  
  public List<NameFilter> getNameFilters()
  {
    if (this.nameFilters == null) {
      this.nameFilters = new ArrayList();
    }
    return this.nameFilters;
  }
  
  public List<NameFilter> getNameFiltersDirect()
  {
    return this.nameFilters;
  }
  
  public ObjectSerializer getObjectWriter(Class<?> paramClass)
  {
    ObjectSerializer localObjectSerializer1 = (ObjectSerializer)this.config.get(paramClass);
    if (localObjectSerializer1 == null) {
      try
      {
        Iterator localIterator3 = ServiceLoader.load(AutowiredObjectSerializer.class, Thread.currentThread().getContextClassLoader()).iterator();
        while (localIterator3.hasNext())
        {
          Object localObject2 = localIterator3.next();
          if ((localObject2 instanceof AutowiredObjectSerializer))
          {
            AutowiredObjectSerializer localAutowiredObjectSerializer2 = (AutowiredObjectSerializer)localObject2;
            Iterator localIterator4 = localAutowiredObjectSerializer2.getAutowiredFor().iterator();
            while (localIterator4.hasNext())
            {
              Type localType2 = (Type)localIterator4.next();
              this.config.put(localType2, localAutowiredObjectSerializer2);
            }
          }
        }
        if (localObjectSerializer1 != null) {
          break label268;
        }
      }
      catch (ClassCastException localClassCastException2)
      {
        localObjectSerializer1 = (ObjectSerializer)this.config.get(paramClass);
      }
    }
    ClassLoader localClassLoader = JSON.class.getClassLoader();
    if (localClassLoader != Thread.currentThread().getContextClassLoader()) {
      try
      {
        Iterator localIterator1 = ServiceLoader.load(AutowiredObjectSerializer.class, localClassLoader).iterator();
        while (localIterator1.hasNext())
        {
          Object localObject1 = localIterator1.next();
          if ((localObject1 instanceof AutowiredObjectSerializer))
          {
            AutowiredObjectSerializer localAutowiredObjectSerializer1 = (AutowiredObjectSerializer)localObject1;
            Iterator localIterator2 = localAutowiredObjectSerializer1.getAutowiredFor().iterator();
            while (localIterator2.hasNext())
            {
              Type localType1 = (Type)localIterator2.next();
              this.config.put(localType1, localAutowiredObjectSerializer1);
            }
          }
        }
        if (localObjectSerializer1 != null) {
          break label305;
        }
      }
      catch (ClassCastException localClassCastException1)
      {
        localObjectSerializer1 = (ObjectSerializer)this.config.get(paramClass);
      }
    }
    label268:
    if (Map.class.isAssignableFrom(paramClass)) {
      this.config.put(paramClass, MapSerializer.instance);
    }
    for (;;)
    {
      localObjectSerializer1 = (ObjectSerializer)this.config.get(paramClass);
      label305:
      return localObjectSerializer1;
      if (List.class.isAssignableFrom(paramClass))
      {
        this.config.put(paramClass, ListSerializer.instance);
      }
      else if (Collection.class.isAssignableFrom(paramClass))
      {
        this.config.put(paramClass, CollectionSerializer.instance);
      }
      else if (Date.class.isAssignableFrom(paramClass))
      {
        this.config.put(paramClass, DateSerializer.instance);
      }
      else if (JSONAware.class.isAssignableFrom(paramClass))
      {
        this.config.put(paramClass, JSONAwareSerializer.instance);
      }
      else if (JSONStreamAware.class.isAssignableFrom(paramClass))
      {
        this.config.put(paramClass, JSONStreamAwareSerializer.instance);
      }
      else if ((paramClass.isEnum()) || ((paramClass.getSuperclass() != null) && (paramClass.getSuperclass().isEnum())))
      {
        this.config.put(paramClass, EnumSerializer.instance);
      }
      else if (paramClass.isArray())
      {
        Class localClass2 = paramClass.getComponentType();
        ObjectSerializer localObjectSerializer3 = getObjectWriter(localClass2);
        SerializeConfig localSerializeConfig2 = this.config;
        ArraySerializer localArraySerializer = new ArraySerializer(localClass2, localObjectSerializer3);
        localSerializeConfig2.put(paramClass, localArraySerializer);
      }
      else if (Throwable.class.isAssignableFrom(paramClass))
      {
        SerializeConfig localSerializeConfig1 = this.config;
        ExceptionSerializer localExceptionSerializer = new ExceptionSerializer(paramClass);
        localSerializeConfig1.put(paramClass, localExceptionSerializer);
      }
      else if (TimeZone.class.isAssignableFrom(paramClass))
      {
        this.config.put(paramClass, TimeZoneSerializer.instance);
      }
      else if (Appendable.class.isAssignableFrom(paramClass))
      {
        this.config.put(paramClass, AppendableSerializer.instance);
      }
      else if (Charset.class.isAssignableFrom(paramClass))
      {
        this.config.put(paramClass, CharsetSerializer.instance);
      }
      else if (Enumeration.class.isAssignableFrom(paramClass))
      {
        this.config.put(paramClass, EnumerationSeriliazer.instance);
      }
      else if (Calendar.class.isAssignableFrom(paramClass))
      {
        this.config.put(paramClass, CalendarSerializer.instance);
      }
      else if (Clob.class.isAssignableFrom(paramClass))
      {
        this.config.put(paramClass, ClobSeriliazer.instance);
      }
      else
      {
        Class[] arrayOfClass = paramClass.getInterfaces();
        int i = arrayOfClass.length;
        for (int j = 0;; j++)
        {
          int k = 0;
          int m = 0;
          Class localClass1;
          if (j < i)
          {
            localClass1 = arrayOfClass[j];
            if (!localClass1.getName().equals("net.sf.cglib.proxy.Factory")) {
              break label791;
            }
          }
          for (k = 1;; k = 0)
          {
            if ((k == 0) && (m == 0)) {
              break label820;
            }
            ObjectSerializer localObjectSerializer2 = getObjectWriter(paramClass.getSuperclass());
            this.config.put(paramClass, localObjectSerializer2);
            return localObjectSerializer2;
            label791:
            if (!localClass1.getName().equals("javassist.util.proxy.ProxyObject")) {
              break;
            }
            m = 1;
          }
        }
        label820:
        if (Proxy.isProxyClass(paramClass)) {
          this.config.put(paramClass, this.config.createJavaBeanSerializer(paramClass));
        } else {
          this.config.put(paramClass, this.config.createJavaBeanSerializer(paramClass));
        }
      }
    }
  }
  
  public List<PropertyFilter> getPropertyFilters()
  {
    if (this.propertyFilters == null) {
      this.propertyFilters = new ArrayList();
    }
    return this.propertyFilters;
  }
  
  public List<PropertyFilter> getPropertyFiltersDirect()
  {
    return this.propertyFilters;
  }
  
  public List<PropertyPreFilter> getPropertyPreFilters()
  {
    if (this.propertyPreFilters == null) {
      this.propertyPreFilters = new ArrayList();
    }
    return this.propertyPreFilters;
  }
  
  public List<PropertyPreFilter> getPropertyPreFiltersDirect()
  {
    return this.propertyPreFilters;
  }
  
  public Collection<SerialContext> getReferences()
  {
    if (this.references == null) {
      this.references = new IdentityHashMap();
    }
    return this.references.values();
  }
  
  public SerialContext getSerialContext(Object paramObject)
  {
    if (this.references == null) {
      return null;
    }
    return (SerialContext)this.references.get(paramObject);
  }
  
  public List<ValueFilter> getValueFilters()
  {
    if (this.valueFilters == null) {
      this.valueFilters = new ArrayList();
    }
    return this.valueFilters;
  }
  
  public List<ValueFilter> getValueFiltersDirect()
  {
    return this.valueFilters;
  }
  
  public SerializeWriter getWriter()
  {
    return this.out;
  }
  
  public void incrementIndent()
  {
    this.indentCount = (1 + this.indentCount);
  }
  
  public boolean isEnabled(SerializerFeature paramSerializerFeature)
  {
    return this.out.isEnabled(paramSerializerFeature);
  }
  
  public boolean isWriteClassName()
  {
    return isEnabled(SerializerFeature.WriteClassName);
  }
  
  public final boolean isWriteClassName(Type paramType, Object paramObject)
  {
    if (!this.out.isEnabled(SerializerFeature.WriteClassName)) {}
    for (;;)
    {
      return false;
      if ((paramType == null) && (isEnabled(SerializerFeature.NotWriteRootClassName))) {
        if (this.context.getParent() != null) {
          break label47;
        }
      }
      label47:
      for (int i = 1; i == 0; i = 0) {
        return true;
      }
    }
  }
  
  public void popContext()
  {
    if (this.context != null) {
      this.context = this.context.getParent();
    }
  }
  
  public void println()
  {
    this.out.write('\n');
    for (int i = 0; i < this.indentCount; i++) {
      this.out.write(this.indent);
    }
  }
  
  public void setContext(SerialContext paramSerialContext)
  {
    this.context = paramSerialContext;
  }
  
  public void setContext(SerialContext paramSerialContext, Object paramObject)
  {
    if (isEnabled(SerializerFeature.DisableCircularReferenceDetect)) {
      return;
    }
    this.context = new SerialContext(paramSerialContext, paramObject, null);
    if (this.references == null) {
      this.references = new IdentityHashMap();
    }
    this.references.put(paramObject, this.context);
  }
  
  public void setContext(SerialContext paramSerialContext, Object paramObject1, Object paramObject2)
  {
    if (isEnabled(SerializerFeature.DisableCircularReferenceDetect)) {
      return;
    }
    this.context = new SerialContext(paramSerialContext, paramObject1, paramObject2);
    if (this.references == null) {
      this.references = new IdentityHashMap();
    }
    this.references.put(paramObject1, this.context);
  }
  
  public void setContext(Object paramObject1, Object paramObject2)
  {
    setContext(this.context, paramObject1, paramObject2);
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateFormatPatterm = paramString;
    if (this.dateFormat != null) {
      this.dateFormat = null;
    }
  }
  
  public void setDateFormat(DateFormat paramDateFormat)
  {
    this.dateFormat = paramDateFormat;
  }
  
  public String toString()
  {
    return this.out.toString();
  }
  
  public final void write(Object paramObject)
  {
    if (paramObject == null)
    {
      this.out.writeNull();
      return;
    }
    ObjectSerializer localObjectSerializer = getObjectWriter(paramObject.getClass());
    try
    {
      localObjectSerializer.write(this, paramObject, null, null);
      return;
    }
    catch (IOException localIOException)
    {
      throw new JSONException(localIOException.getMessage(), localIOException);
    }
  }
  
  public final void write(String paramString)
  {
    StringSerializer.instance.write(this, paramString);
  }
  
  public void writeNull()
  {
    this.out.writeNull();
  }
  
  public void writeReference(Object paramObject)
  {
    if (isEnabled(SerializerFeature.DisableCircularReferenceDetect)) {
      return;
    }
    SerialContext localSerialContext1 = getContext();
    if (paramObject == localSerialContext1.getObject())
    {
      this.out.write("{\"$ref\":\"@\"}");
      return;
    }
    SerialContext localSerialContext2 = localSerialContext1.getParent();
    if ((localSerialContext2 != null) && (paramObject == localSerialContext2.getObject()))
    {
      this.out.write("{\"$ref\":\"..\"}");
      return;
    }
    for (SerialContext localSerialContext3 = localSerialContext1;; localSerialContext3 = localSerialContext3.getParent()) {
      if (localSerialContext3.getParent() == null)
      {
        if (paramObject != localSerialContext3.getObject()) {
          break;
        }
        this.out.write("{\"$ref\":\"$\"}");
        return;
      }
    }
    String str = getSerialContext(paramObject).getPath();
    this.out.write("{\"$ref\":\"");
    this.out.write(str);
    this.out.write("\"}");
  }
  
  public final void writeWithFieldName(Object paramObject1, Object paramObject2)
  {
    writeWithFieldName(paramObject1, paramObject2, null);
  }
  
  public final void writeWithFieldName(Object paramObject1, Object paramObject2, Type paramType)
  {
    if (paramObject1 == null) {}
    try
    {
      this.out.writeNull();
      return;
    }
    catch (IOException localIOException)
    {
      throw new JSONException(localIOException.getMessage(), localIOException);
    }
    getObjectWriter(paramObject1.getClass()).write(this, paramObject1, paramObject2, paramType);
  }
  
  public final void writeWithFormat(Object paramObject, String paramString)
  {
    if ((paramObject instanceof Date))
    {
      String str = new SimpleDateFormat(paramString).format((Date)paramObject);
      this.out.writeString(str);
      return;
    }
    write(paramObject);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\JSONSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */