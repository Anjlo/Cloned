package com.alibaba.fastjson.annotation;

import com.alibaba.fastjson.serializer.SerializerFeature;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface JSONType
{
  boolean alphabetic() default true;
  
  boolean asm() default true;
  
  String[] ignores() default {};
  
  Class<?> mappingTo() default "Ljava/lang/Void;";
  
  String[] orders() default {};
  
  SerializerFeature[] serialzeFeatures() default {};
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\annotation\JSONType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */