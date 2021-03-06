package com.alibaba.fastjson.asm;

public class Type
{
  public static final int ARRAY = 9;
  public static final int BOOLEAN = 1;
  public static final Type BOOLEAN_TYPE;
  public static final int BYTE = 3;
  public static final Type BYTE_TYPE;
  public static final int CHAR = 2;
  public static final Type CHAR_TYPE;
  public static final int DOUBLE = 8;
  public static final Type DOUBLE_TYPE = new Type(8, null, 1141048066, 1);
  public static final int FLOAT = 6;
  public static final Type FLOAT_TYPE;
  public static final int INT = 5;
  public static final Type INT_TYPE;
  public static final int LONG = 7;
  public static final Type LONG_TYPE;
  public static final int OBJECT = 10;
  public static final int SHORT = 4;
  public static final Type SHORT_TYPE;
  public static final int VOID;
  public static final Type VOID_TYPE = new Type(0, null, 1443168256, 1);
  private final char[] buf;
  private final int len;
  private final int off;
  private final int sort;
  
  static
  {
    BOOLEAN_TYPE = new Type(1, null, 1509950721, 1);
    CHAR_TYPE = new Type(2, null, 1124075009, 1);
    BYTE_TYPE = new Type(3, null, 1107297537, 1);
    SHORT_TYPE = new Type(4, null, 1392510721, 1);
    INT_TYPE = new Type(5, null, 1224736769, 1);
    FLOAT_TYPE = new Type(6, null, 1174536705, 1);
    LONG_TYPE = new Type(7, null, 1241579778, 1);
  }
  
  private Type(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
  {
    this.sort = paramInt1;
    this.buf = paramArrayOfChar;
    this.off = paramInt2;
    this.len = paramInt3;
  }
  
  public static int getArgumentsAndReturnSizes(String paramString)
  {
    int i = 1;
    int j = 1;
    for (;;)
    {
      int k = j + 1;
      int m = paramString.charAt(j);
      if (m == 41)
      {
        int i3 = paramString.charAt(k);
        int i4 = i << 2;
        int i5;
        if (i3 == 86) {
          i5 = 0;
        }
        for (;;)
        {
          return i5 | i4;
          if ((i3 == 68) || (i3 == 74)) {
            i5 = 2;
          } else {
            i5 = 1;
          }
        }
      }
      if (m == 76)
      {
        int i2;
        for (int i1 = k;; i1 = i2)
        {
          i2 = i1 + 1;
          if (paramString.charAt(i1) == ';') {
            break;
          }
        }
        i++;
        j = i2;
      }
      else if (m == 91)
      {
        int n;
        for (j = k;; j++)
        {
          n = paramString.charAt(j);
          if (n != 91) {
            break;
          }
        }
        if ((n == 68) || (n == 74)) {
          i--;
        }
      }
      else if ((m == 68) || (m == 74))
      {
        i += 2;
        j = k;
      }
      else
      {
        i++;
        j = k;
      }
    }
  }
  
  public static Type getType(String paramString)
  {
    return getType(paramString.toCharArray(), 0);
  }
  
  private static Type getType(char[] paramArrayOfChar, int paramInt)
  {
    int j;
    switch (paramArrayOfChar[paramInt])
    {
    default: 
      j = 1;
    }
    while (paramArrayOfChar[(paramInt + j)] != ';')
    {
      j++;
      continue;
      return VOID_TYPE;
      return BOOLEAN_TYPE;
      return CHAR_TYPE;
      return BYTE_TYPE;
      return SHORT_TYPE;
      return INT_TYPE;
      return FLOAT_TYPE;
      return LONG_TYPE;
      return DOUBLE_TYPE;
      for (int i = 1; paramArrayOfChar[(paramInt + i)] == '['; i++) {}
      if (paramArrayOfChar[(paramInt + i)] == 'L')
      {
        i++;
        while (paramArrayOfChar[(paramInt + i)] != ';') {
          i++;
        }
      }
      return new Type(9, paramArrayOfChar, paramInt, i + 1);
    }
    return new Type(10, paramArrayOfChar, paramInt + 1, j - 1);
  }
  
  public String getDescriptor()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.buf == null) {
      localStringBuffer.append((char)((0xFF000000 & this.off) >>> 24));
    }
    for (;;)
    {
      return localStringBuffer.toString();
      if (this.sort == 9)
      {
        localStringBuffer.append(this.buf, this.off, this.len);
      }
      else
      {
        localStringBuffer.append('L');
        localStringBuffer.append(this.buf, this.off, this.len);
        localStringBuffer.append(';');
      }
    }
  }
  
  public String getInternalName()
  {
    return new String(this.buf, this.off, this.len);
  }
  
  public int getSort()
  {
    return this.sort;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\asm\Type.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */