package com.alibaba.fastjson.serializer;

public class EnumerationSeriliazer
  implements ObjectSerializer
{
  public static EnumerationSeriliazer instance = new EnumerationSeriliazer();
  
  /* Error */
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, java.lang.reflect.Type paramType)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 26	com/alibaba/fastjson/serializer/JSONSerializer:getWriter	()Lcom/alibaba/fastjson/serializer/SerializeWriter;
    //   4: astore 5
    //   6: aload_2
    //   7: ifnonnull +28 -> 35
    //   10: aload 5
    //   12: getstatic 32	com/alibaba/fastjson/serializer/SerializerFeature:WriteNullListAsEmpty	Lcom/alibaba/fastjson/serializer/SerializerFeature;
    //   15: invokevirtual 38	com/alibaba/fastjson/serializer/SerializeWriter:isEnabled	(Lcom/alibaba/fastjson/serializer/SerializerFeature;)Z
    //   18: ifeq +11 -> 29
    //   21: aload 5
    //   23: ldc 40
    //   25: invokevirtual 43	com/alibaba/fastjson/serializer/SerializeWriter:write	(Ljava/lang/String;)V
    //   28: return
    //   29: aload 5
    //   31: invokevirtual 46	com/alibaba/fastjson/serializer/SerializeWriter:writeNull	()V
    //   34: return
    //   35: aload_1
    //   36: getstatic 49	com/alibaba/fastjson/serializer/SerializerFeature:WriteClassName	Lcom/alibaba/fastjson/serializer/SerializerFeature;
    //   39: invokevirtual 50	com/alibaba/fastjson/serializer/JSONSerializer:isEnabled	(Lcom/alibaba/fastjson/serializer/SerializerFeature;)Z
    //   42: istore 6
    //   44: aconst_null
    //   45: astore 7
    //   47: iload 6
    //   49: ifeq +32 -> 81
    //   52: aload 4
    //   54: instanceof 52
    //   57: istore 19
    //   59: aconst_null
    //   60: astore 7
    //   62: iload 19
    //   64: ifeq +17 -> 81
    //   67: aload 4
    //   69: checkcast 52	java/lang/reflect/ParameterizedType
    //   72: invokeinterface 56 1 0
    //   77: iconst_0
    //   78: aaload
    //   79: astore 7
    //   81: aload_2
    //   82: checkcast 58	java/util/Enumeration
    //   85: astore 8
    //   87: aload_1
    //   88: invokevirtual 62	com/alibaba/fastjson/serializer/JSONSerializer:getContext	()Lcom/alibaba/fastjson/serializer/SerialContext;
    //   91: astore 9
    //   93: aload_1
    //   94: aload 9
    //   96: aload_2
    //   97: aload_3
    //   98: invokevirtual 66	com/alibaba/fastjson/serializer/JSONSerializer:setContext	(Lcom/alibaba/fastjson/serializer/SerialContext;Ljava/lang/Object;Ljava/lang/Object;)V
    //   101: iconst_0
    //   102: istore 10
    //   104: aload 5
    //   106: bipush 91
    //   108: invokevirtual 70	com/alibaba/fastjson/serializer/SerializeWriter:append	(C)Lcom/alibaba/fastjson/serializer/SerializeWriter;
    //   111: pop
    //   112: iload 10
    //   114: istore 13
    //   116: aload 8
    //   118: invokeinterface 74 1 0
    //   123: ifeq +157 -> 280
    //   126: aload 8
    //   128: invokeinterface 78 1 0
    //   133: astore 16
    //   135: iload 13
    //   137: iconst_1
    //   138: iadd
    //   139: istore 10
    //   141: iload 13
    //   143: ifeq +11 -> 154
    //   146: aload 5
    //   148: bipush 44
    //   150: invokevirtual 70	com/alibaba/fastjson/serializer/SerializeWriter:append	(C)Lcom/alibaba/fastjson/serializer/SerializeWriter;
    //   153: pop
    //   154: aload 16
    //   156: ifnonnull +15 -> 171
    //   159: aload 5
    //   161: invokevirtual 46	com/alibaba/fastjson/serializer/SerializeWriter:writeNull	()V
    //   164: iload 10
    //   166: istore 13
    //   168: goto -52 -> 116
    //   171: aload 16
    //   173: invokevirtual 82	java/lang/Object:getClass	()Ljava/lang/Class;
    //   176: astore 17
    //   178: aload 17
    //   180: ldc 84
    //   182: if_acmpne +23 -> 205
    //   185: aload 5
    //   187: aload 16
    //   189: checkcast 84	java/lang/Integer
    //   192: invokevirtual 88	java/lang/Integer:intValue	()I
    //   195: invokevirtual 92	com/alibaba/fastjson/serializer/SerializeWriter:writeInt	(I)V
    //   198: iload 10
    //   200: istore 13
    //   202: goto -86 -> 116
    //   205: aload 17
    //   207: ldc 94
    //   209: if_acmpne +41 -> 250
    //   212: aload 5
    //   214: aload 16
    //   216: checkcast 94	java/lang/Long
    //   219: invokevirtual 98	java/lang/Long:longValue	()J
    //   222: invokevirtual 102	com/alibaba/fastjson/serializer/SerializeWriter:writeLong	(J)V
    //   225: aload 5
    //   227: getstatic 49	com/alibaba/fastjson/serializer/SerializerFeature:WriteClassName	Lcom/alibaba/fastjson/serializer/SerializerFeature;
    //   230: invokevirtual 38	com/alibaba/fastjson/serializer/SerializeWriter:isEnabled	(Lcom/alibaba/fastjson/serializer/SerializerFeature;)Z
    //   233: ifeq -121 -> 112
    //   236: aload 5
    //   238: bipush 76
    //   240: invokevirtual 105	com/alibaba/fastjson/serializer/SerializeWriter:write	(C)V
    //   243: iload 10
    //   245: istore 13
    //   247: goto -131 -> 116
    //   250: aload_1
    //   251: aload 17
    //   253: invokevirtual 109	com/alibaba/fastjson/serializer/JSONSerializer:getObjectWriter	(Ljava/lang/Class;)Lcom/alibaba/fastjson/serializer/ObjectSerializer;
    //   256: aload_1
    //   257: aload 16
    //   259: iload 10
    //   261: iconst_1
    //   262: isub
    //   263: invokestatic 113	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   266: aload 7
    //   268: invokeinterface 115 5 0
    //   273: iload 10
    //   275: istore 13
    //   277: goto -161 -> 116
    //   280: aload 5
    //   282: bipush 93
    //   284: invokevirtual 70	com/alibaba/fastjson/serializer/SerializeWriter:append	(C)Lcom/alibaba/fastjson/serializer/SerializeWriter;
    //   287: pop
    //   288: aload_1
    //   289: aload 9
    //   291: invokevirtual 118	com/alibaba/fastjson/serializer/JSONSerializer:setContext	(Lcom/alibaba/fastjson/serializer/SerialContext;)V
    //   294: return
    //   295: astore 11
    //   297: aload_1
    //   298: aload 9
    //   300: invokevirtual 118	com/alibaba/fastjson/serializer/JSONSerializer:setContext	(Lcom/alibaba/fastjson/serializer/SerialContext;)V
    //   303: aload 11
    //   305: athrow
    //   306: astore 11
    //   308: iload 13
    //   310: pop
    //   311: goto -14 -> 297
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	314	0	this	EnumerationSeriliazer
    //   0	314	1	paramJSONSerializer	JSONSerializer
    //   0	314	2	paramObject1	Object
    //   0	314	3	paramObject2	Object
    //   0	314	4	paramType	java.lang.reflect.Type
    //   4	277	5	localSerializeWriter	SerializeWriter
    //   42	6	6	bool1	boolean
    //   45	222	7	localType	java.lang.reflect.Type
    //   85	42	8	localEnumeration	java.util.Enumeration
    //   91	208	9	localSerialContext	SerialContext
    //   102	172	10	i	int
    //   295	9	11	localObject1	Object
    //   306	1	11	localObject2	Object
    //   114	195	13	j	int
    //   133	125	16	localObject3	Object
    //   176	76	17	localClass	Class
    //   57	6	19	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   104	112	295	finally
    //   146	154	295	finally
    //   159	164	295	finally
    //   171	178	295	finally
    //   185	198	295	finally
    //   212	243	295	finally
    //   250	273	295	finally
    //   116	135	306	finally
    //   280	288	306	finally
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\EnumerationSeriliazer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */