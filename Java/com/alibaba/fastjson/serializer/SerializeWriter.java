package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.CharTypes;
import com.alibaba.fastjson.util.Base64;
import com.alibaba.fastjson.util.IOUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.nio.charset.Charset;

public final class SerializeWriter
  extends Writer
{
  private static final ThreadLocal<SoftReference<char[]>> bufLocal = new ThreadLocal();
  protected char[] buf;
  protected int count;
  private int features;
  
  public SerializeWriter()
  {
    this.features = JSON.DEFAULT_GENERATE_FEATURE;
    SoftReference localSoftReference = (SoftReference)bufLocal.get();
    if (localSoftReference != null)
    {
      this.buf = ((char[])localSoftReference.get());
      bufLocal.set(null);
    }
    if (this.buf == null) {
      this.buf = new char['Ѐ'];
    }
  }
  
  public SerializeWriter(int paramInt)
  {
    if (paramInt <= 0) {
      throw new IllegalArgumentException("Negative initial size: " + paramInt);
    }
    this.buf = new char[paramInt];
  }
  
  public SerializeWriter(SerializerFeature... paramVarArgs)
  {
    SoftReference localSoftReference = (SoftReference)bufLocal.get();
    if (localSoftReference != null)
    {
      this.buf = ((char[])localSoftReference.get());
      bufLocal.set(null);
    }
    if (this.buf == null) {
      this.buf = new char['Ѐ'];
    }
    int i = 0;
    int j = paramVarArgs.length;
    for (int k = 0; k < j; k++) {
      i |= paramVarArgs[k].getMask();
    }
    this.features = i;
  }
  
  static final boolean isSpecial(char paramChar, int paramInt)
  {
    if (paramChar == ' ') {}
    do
    {
      return false;
      if ((paramChar == '/') && (SerializerFeature.isEnabled(paramInt, SerializerFeature.WriteSlashAsSpecial))) {
        return true;
      }
    } while (((paramChar > '#') && (paramChar != '\\')) || ((paramChar != '\b') && (paramChar != '\n') && (paramChar != '\r') && (paramChar != '\f') && (paramChar != '\\') && (paramChar != '"') && ((paramChar != '\t') || (!SerializerFeature.isEnabled(paramInt, SerializerFeature.WriteTabAsSpecial)))));
    return true;
  }
  
  private void writeFieldValueStringWithDoubleQuote(char paramChar, String paramString1, String paramString2, boolean paramBoolean)
  {
    int i = paramString1.length();
    int j = this.count;
    int k;
    if (paramString2 == null) {
      k = 4;
    }
    int i4;
    for (int m = j + (i + 8);; m = j + (6 + (i + k)))
    {
      int n = this.buf.length;
      if (m > n) {
        expandCapacity(m);
      }
      this.buf[this.count] = paramChar;
      int i1 = 2 + this.count;
      int i2 = i1 + i;
      this.buf[(1 + this.count)] = '"';
      paramString1.getChars(0, i, this.buf, i1);
      this.count = m;
      this.buf[i2] = '"';
      int i3 = i2 + 1;
      char[] arrayOfChar1 = this.buf;
      i4 = i3 + 1;
      arrayOfChar1[i3] = ':';
      if (paramString2 != null) {
        break;
      }
      char[] arrayOfChar7 = this.buf;
      int i19 = i4 + 1;
      arrayOfChar7[i4] = 'n';
      char[] arrayOfChar8 = this.buf;
      int i20 = i19 + 1;
      arrayOfChar8[i19] = 'u';
      char[] arrayOfChar9 = this.buf;
      int i21 = i20 + 1;
      arrayOfChar9[i20] = 'l';
      char[] arrayOfChar10 = this.buf;
      (i21 + 1);
      arrayOfChar10[i21] = 'l';
      return;
      k = paramString2.length();
    }
    char[] arrayOfChar2 = this.buf;
    int i5 = i4 + 1;
    arrayOfChar2[i4] = '"';
    int i6 = i5 + k;
    char[] arrayOfChar3 = this.buf;
    paramString2.getChars(0, k, arrayOfChar3, i5);
    int i7;
    int i9;
    if ((paramBoolean) && (!isEnabled(SerializerFeature.DisableCheckSpecialChar)))
    {
      i7 = 0;
      int i8 = -1;
      i9 = -1;
      int i10 = 0;
      int i11 = i5;
      if (i11 < i6)
      {
        char c = this.buf[i11];
        if (c >= ']') {}
        for (;;)
        {
          i11++;
          break;
          if (isSpecial(c, this.features))
          {
            i7++;
            i8 = i11;
            i10 = c;
            if (i9 == -1) {
              i9 = i11;
            }
          }
        }
      }
      if (i7 > 0)
      {
        int i12 = m + i7;
        if (i12 > this.buf.length) {
          expandCapacity(i12);
        }
        this.count = i12;
        if (i7 != 1) {
          break label485;
        }
        System.arraycopy(this.buf, i8 + 1, this.buf, i8 + 2, -1 + (i6 - i8));
        this.buf[i8] = '\\';
        this.buf[(i8 + 1)] = CharTypes.replaceChars[i10];
      }
    }
    label485:
    while (i7 <= 1)
    {
      this.buf[(-1 + this.count)] = '"';
      return;
    }
    int i13 = i9 - i5;
    int i14 = i9;
    int i15 = i13;
    label506:
    int i16;
    if (i15 < paramString2.length())
    {
      i16 = paramString2.charAt(i15);
      if (((i16 >= CharTypes.specicalFlags_doubleQuotes.length) || (CharTypes.specicalFlags_doubleQuotes[i16] == 0)) && ((i16 != 9) || (!isEnabled(SerializerFeature.WriteTabAsSpecial))) && ((i16 != 47) || (!isEnabled(SerializerFeature.WriteSlashAsSpecial)))) {
        break label626;
      }
      char[] arrayOfChar5 = this.buf;
      int i18 = i14 + 1;
      arrayOfChar5[i14] = '\\';
      char[] arrayOfChar6 = this.buf;
      i14 = i18 + 1;
      arrayOfChar6[i18] = CharTypes.replaceChars[i16];
      i6++;
    }
    for (;;)
    {
      i15++;
      break label506;
      break;
      label626:
      char[] arrayOfChar4 = this.buf;
      int i17 = i14 + 1;
      arrayOfChar4[i14] = i16;
      i14 = i17;
    }
  }
  
  private void writeKeyWithDoubleQuoteIfHasSpecial(String paramString)
  {
    boolean[] arrayOfBoolean = CharTypes.specicalFlags_doubleQuotes;
    int i = paramString.length();
    int j = 1 + (i + this.count);
    if (j > this.buf.length) {
      expandCapacity(j);
    }
    int k = this.count;
    int m = k + i;
    paramString.getChars(0, i, this.buf, k);
    this.count = j;
    int n = 0;
    int i1 = k;
    if (i1 < m)
    {
      int i2 = this.buf[i1];
      if ((i2 < arrayOfBoolean.length) && (arrayOfBoolean[i2] != 0))
      {
        if (n != 0) {
          break label250;
        }
        j += 3;
        if (j > this.buf.length) {
          expandCapacity(j);
        }
        this.count = j;
        System.arraycopy(this.buf, i1 + 1, this.buf, i1 + 3, -1 + (m - i1));
        System.arraycopy(this.buf, 0, this.buf, 1, i1);
        this.buf[k] = '"';
        char[] arrayOfChar2 = this.buf;
        int i3 = i1 + 1;
        arrayOfChar2[i3] = '\\';
        char[] arrayOfChar3 = this.buf;
        i1 = i3 + 1;
        arrayOfChar3[i1] = CharTypes.replaceChars[i2];
        m += 2;
        this.buf[(-2 + this.count)] = '"';
        n = 1;
      }
      for (;;)
      {
        i1++;
        break;
        label250:
        j++;
        if (j > this.buf.length) {
          expandCapacity(j);
        }
        this.count = j;
        System.arraycopy(this.buf, i1 + 1, this.buf, i1 + 2, m - i1);
        this.buf[i1] = '\\';
        char[] arrayOfChar1 = this.buf;
        i1++;
        arrayOfChar1[i1] = CharTypes.replaceChars[i2];
        m++;
      }
    }
    this.buf[(-1 + this.count)] = ':';
  }
  
  private void writeKeyWithSingleQuote(String paramString)
  {
    boolean[] arrayOfBoolean = CharTypes.specicalFlags_singleQuotes;
    int i = paramString.length();
    int j = 3 + (i + this.count);
    if (j > this.buf.length) {
      expandCapacity(j);
    }
    int k = 1 + this.count;
    int m = k + i;
    this.buf[this.count] = '\'';
    paramString.getChars(0, i, this.buf, k);
    this.count = j;
    for (int n = k; n < m; n++)
    {
      int i1 = this.buf[n];
      if (((i1 < arrayOfBoolean.length) && (arrayOfBoolean[i1] != 0)) || ((i1 == 9) && (isEnabled(SerializerFeature.WriteTabAsSpecial))) || ((i1 == 47) && (isEnabled(SerializerFeature.WriteSlashAsSpecial))))
      {
        j++;
        if (j > this.buf.length) {
          expandCapacity(j);
        }
        this.count = j;
        System.arraycopy(this.buf, n + 1, this.buf, n + 2, -1 + (m - n));
        this.buf[n] = '\\';
        char[] arrayOfChar = this.buf;
        n++;
        arrayOfChar[n] = CharTypes.replaceChars[i1];
        m++;
      }
    }
    this.buf[(-2 + this.count)] = '\'';
    this.buf[(-1 + this.count)] = ':';
  }
  
  private void writeKeyWithSingleQuoteIfHasSpecial(String paramString)
  {
    boolean[] arrayOfBoolean = CharTypes.specicalFlags_singleQuotes;
    int i = paramString.length();
    int j = 1 + (i + this.count);
    if (j > this.buf.length) {
      expandCapacity(j);
    }
    int k = this.count;
    int m = k + i;
    paramString.getChars(0, i, this.buf, k);
    this.count = j;
    int n = 0;
    int i1 = k;
    if (i1 < m)
    {
      int i2 = this.buf[i1];
      if ((i2 < arrayOfBoolean.length) && (arrayOfBoolean[i2] != 0))
      {
        if (n != 0) {
          break label250;
        }
        j += 3;
        if (j > this.buf.length) {
          expandCapacity(j);
        }
        this.count = j;
        System.arraycopy(this.buf, i1 + 1, this.buf, i1 + 3, -1 + (m - i1));
        System.arraycopy(this.buf, 0, this.buf, 1, i1);
        this.buf[k] = '\'';
        char[] arrayOfChar2 = this.buf;
        int i3 = i1 + 1;
        arrayOfChar2[i3] = '\\';
        char[] arrayOfChar3 = this.buf;
        i1 = i3 + 1;
        arrayOfChar3[i1] = CharTypes.replaceChars[i2];
        m += 2;
        this.buf[(-2 + this.count)] = '\'';
        n = 1;
      }
      for (;;)
      {
        i1++;
        break;
        label250:
        j++;
        if (j > this.buf.length) {
          expandCapacity(j);
        }
        this.count = j;
        System.arraycopy(this.buf, i1 + 1, this.buf, i1 + 2, m - i1);
        this.buf[i1] = '\\';
        char[] arrayOfChar1 = this.buf;
        i1++;
        arrayOfChar1[i1] = CharTypes.replaceChars[i2];
        m++;
      }
    }
    this.buf[(j - 1)] = ':';
  }
  
  private void writeStringWithDoubleQuote(String paramString, char paramChar)
  {
    writeStringWithDoubleQuote(paramString, paramChar, true);
  }
  
  private void writeStringWithDoubleQuote(String paramString, char paramChar, boolean paramBoolean)
  {
    if (paramString == null)
    {
      writeNull();
      if (paramChar != 0) {
        write(paramChar);
      }
      return;
    }
    int i = paramString.length();
    int j = 2 + (i + this.count);
    if (paramChar != 0) {
      j++;
    }
    if (j > this.buf.length) {
      expandCapacity(j);
    }
    int k = 1 + this.count;
    int m = k + i;
    this.buf[this.count] = '"';
    paramString.getChars(0, i, this.buf, k);
    this.count = j;
    if (isEnabled(SerializerFeature.BrowserCompatible))
    {
      int i13 = -1;
      int i14 = k;
      if (i14 < m)
      {
        int i17 = this.buf[i14];
        if ((i17 == 34) || (i17 == 47) || (i17 == 92))
        {
          i13 = i14;
          j++;
        }
        for (;;)
        {
          i14++;
          break;
          if ((i17 == 8) || (i17 == 12) || (i17 == 10) || (i17 == 13) || (i17 == 9))
          {
            i13 = i14;
            j++;
          }
          else if (i17 < 32)
          {
            i13 = i14;
            j += 5;
          }
          else if (i17 >= 127)
          {
            i13 = i14;
            j += 5;
          }
        }
      }
      if (j > this.buf.length) {
        expandCapacity(j);
      }
      this.count = j;
      int i15 = i13;
      if (i15 >= k)
      {
        int i16 = this.buf[i15];
        if ((i16 == 8) || (i16 == 12) || (i16 == 10) || (i16 == 13) || (i16 == 9))
        {
          System.arraycopy(this.buf, i15 + 1, this.buf, i15 + 2, -1 + (m - i15));
          this.buf[i15] = '\\';
          this.buf[(i15 + 1)] = CharTypes.replaceChars[i16];
          m++;
        }
        for (;;)
        {
          i15--;
          break;
          if ((i16 == 34) || (i16 == 47) || (i16 == 92))
          {
            System.arraycopy(this.buf, i15 + 1, this.buf, i15 + 2, -1 + (m - i15));
            this.buf[i15] = '\\';
            this.buf[(i15 + 1)] = i16;
            m++;
          }
          else if (i16 < 32)
          {
            System.arraycopy(this.buf, i15 + 1, this.buf, i15 + 6, -1 + (m - i15));
            this.buf[i15] = '\\';
            this.buf[(i15 + 1)] = 'u';
            this.buf[(i15 + 2)] = '0';
            this.buf[(i15 + 3)] = '0';
            this.buf[(i15 + 4)] = CharTypes.ASCII_CHARS[(i16 * 2)];
            this.buf[(i15 + 5)] = CharTypes.ASCII_CHARS[(1 + i16 * 2)];
            m += 5;
          }
          else if (i16 >= 127)
          {
            System.arraycopy(this.buf, i15 + 1, this.buf, i15 + 6, -1 + (m - i15));
            this.buf[i15] = '\\';
            this.buf[(i15 + 1)] = 'u';
            this.buf[(i15 + 2)] = CharTypes.digits[(0xF & i16 >>> 12)];
            this.buf[(i15 + 3)] = CharTypes.digits[(0xF & i16 >>> 8)];
            this.buf[(i15 + 4)] = CharTypes.digits[(0xF & i16 >>> 4)];
            this.buf[(i15 + 5)] = CharTypes.digits[(i16 & 0xF)];
            m += 5;
          }
        }
      }
      if (paramChar != 0)
      {
        this.buf[(-2 + this.count)] = '"';
        this.buf[(-1 + this.count)] = paramChar;
        return;
      }
      this.buf[(-1 + this.count)] = '"';
      return;
    }
    int n = -1;
    int i1 = -1;
    int i2 = 0;
    int i3 = 0;
    if (paramBoolean)
    {
      int i11 = k;
      if (i11 < m)
      {
        int i12 = this.buf[i11];
        if (i12 >= 93) {}
        for (;;)
        {
          i11++;
          break;
          if ((i12 != 32) && ((i12 < 48) || (i12 == 92)) && (((i12 < CharTypes.specicalFlags_doubleQuotes.length) && (CharTypes.specicalFlags_doubleQuotes[i12] != 0)) || ((i12 == 9) && (isEnabled(SerializerFeature.WriteTabAsSpecial))) || ((i12 == 47) && (isEnabled(SerializerFeature.WriteSlashAsSpecial)))))
          {
            i3++;
            n = i11;
            i2 = i12;
            if (i1 == -1) {
              i1 = i11;
            }
          }
        }
      }
    }
    int i4 = j + i3;
    if (i4 > this.buf.length) {
      expandCapacity(i4);
    }
    this.count = i4;
    if (i3 == 1)
    {
      System.arraycopy(this.buf, n + 1, this.buf, n + 2, -1 + (m - n));
      this.buf[n] = '\\';
      this.buf[(n + 1)] = CharTypes.replaceChars[i2];
    }
    while (paramChar != 0)
    {
      this.buf[(-2 + this.count)] = '"';
      this.buf[(-1 + this.count)] = paramChar;
      return;
      if (i3 > 1)
      {
        int i5 = i1 - k;
        int i6 = i1;
        int i7 = i5;
        label1044:
        int i8;
        if (i7 < paramString.length())
        {
          i8 = paramString.charAt(i7);
          if (((i8 >= CharTypes.specicalFlags_doubleQuotes.length) || (CharTypes.specicalFlags_doubleQuotes[i8] == 0)) && ((i8 != 9) || (!isEnabled(SerializerFeature.WriteTabAsSpecial))) && ((i8 != 47) || (!isEnabled(SerializerFeature.WriteSlashAsSpecial)))) {
            break label1164;
          }
          char[] arrayOfChar2 = this.buf;
          int i10 = i6 + 1;
          arrayOfChar2[i6] = '\\';
          char[] arrayOfChar3 = this.buf;
          i6 = i10 + 1;
          arrayOfChar3[i10] = CharTypes.replaceChars[i8];
          m++;
        }
        for (;;)
        {
          i7++;
          break label1044;
          break;
          label1164:
          char[] arrayOfChar1 = this.buf;
          int i9 = i6 + 1;
          arrayOfChar1[i6] = i8;
          i6 = i9;
        }
      }
    }
    this.buf[(-1 + this.count)] = '"';
  }
  
  private void writeStringWithSingleQuote(String paramString)
  {
    if (paramString == null)
    {
      int i10 = 4 + this.count;
      if (i10 > this.buf.length) {
        expandCapacity(i10);
      }
      "null".getChars(0, 4, this.buf, this.count);
      this.count = i10;
      return;
    }
    int i = paramString.length();
    int j = 2 + (i + this.count);
    if (j > this.buf.length) {
      expandCapacity(j);
    }
    int k = 1 + this.count;
    int m = k + i;
    this.buf[this.count] = '\'';
    paramString.getChars(0, i, this.buf, k);
    this.count = j;
    int n = 0;
    int i1 = -1;
    int i2 = 0;
    for (int i3 = k; i3 < m; i3++)
    {
      int i9 = this.buf[i3];
      if ((i9 == 8) || (i9 == 10) || (i9 == 13) || (i9 == 12) || (i9 == 92) || (i9 == 39) || ((i9 == 9) && (isEnabled(SerializerFeature.WriteTabAsSpecial))) || ((i9 == 47) && (isEnabled(SerializerFeature.WriteSlashAsSpecial))))
      {
        n++;
        i1 = i3;
        i2 = i9;
      }
    }
    int i4 = j + n;
    if (i4 > this.buf.length) {
      expandCapacity(i4);
    }
    this.count = i4;
    if (n == 1)
    {
      System.arraycopy(this.buf, i1 + 1, this.buf, i1 + 2, -1 + (m - i1));
      this.buf[i1] = '\\';
      this.buf[(i1 + 1)] = CharTypes.replaceChars[i2];
    }
    for (;;)
    {
      this.buf[(-1 + this.count)] = '\'';
      return;
      if (n > 1)
      {
        System.arraycopy(this.buf, i1 + 1, this.buf, i1 + 2, -1 + (m - i1));
        this.buf[i1] = '\\';
        char[] arrayOfChar = this.buf;
        int i5 = i1 + 1;
        arrayOfChar[i5] = CharTypes.replaceChars[i2];
        int i6 = m + 1;
        for (int i7 = i5 - 2; i7 >= k; i7--)
        {
          int i8 = this.buf[i7];
          if ((i8 == 8) || (i8 == 10) || (i8 == 13) || (i8 == 12) || (i8 == 92) || (i8 == 39) || ((i8 == 9) && (isEnabled(SerializerFeature.WriteTabAsSpecial))) || ((i8 == 47) && (isEnabled(SerializerFeature.WriteSlashAsSpecial))))
          {
            System.arraycopy(this.buf, i7 + 1, this.buf, i7 + 2, -1 + (i6 - i7));
            this.buf[i7] = '\\';
            this.buf[(i7 + 1)] = CharTypes.replaceChars[i8];
            i6++;
          }
        }
      }
    }
  }
  
  public SerializeWriter append(char paramChar)
  {
    write(paramChar);
    return this;
  }
  
  public SerializeWriter append(CharSequence paramCharSequence)
  {
    if (paramCharSequence == null) {}
    for (String str = "null";; str = paramCharSequence.toString())
    {
      write(str, 0, str.length());
      return this;
    }
  }
  
  public SerializeWriter append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    if (paramCharSequence == null) {
      paramCharSequence = "null";
    }
    String str = paramCharSequence.subSequence(paramInt1, paramInt2).toString();
    write(str, 0, str.length());
    return this;
  }
  
  public void close()
  {
    if (this.buf.length <= 8192) {
      bufLocal.set(new SoftReference(this.buf));
    }
    this.buf = null;
  }
  
  public void config(SerializerFeature paramSerializerFeature, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.features |= paramSerializerFeature.getMask();
      return;
    }
    this.features &= (0xFFFFFFFF ^ paramSerializerFeature.getMask());
  }
  
  public void expandCapacity(int paramInt)
  {
    int i = 1 + 3 * this.buf.length / 2;
    if (i < paramInt) {
      i = paramInt;
    }
    char[] arrayOfChar = new char[i];
    System.arraycopy(this.buf, 0, arrayOfChar, 0, this.count);
    this.buf = arrayOfChar;
  }
  
  public void flush() {}
  
  public boolean isEnabled(SerializerFeature paramSerializerFeature)
  {
    return SerializerFeature.isEnabled(this.features, paramSerializerFeature);
  }
  
  public void reset()
  {
    this.count = 0;
  }
  
  public int size()
  {
    return this.count;
  }
  
  public byte[] toBytes(String paramString)
  {
    if (paramString == null) {
      paramString = "UTF-8";
    }
    return new SerialWriterStringEncoder(Charset.forName(paramString)).encode(this.buf, 0, this.count);
  }
  
  public char[] toCharArray()
  {
    char[] arrayOfChar = new char[this.count];
    System.arraycopy(this.buf, 0, arrayOfChar, 0, this.count);
    return arrayOfChar;
  }
  
  public String toString()
  {
    return new String(this.buf, 0, this.count);
  }
  
  public void write(char paramChar)
  {
    int i = 1 + this.count;
    if (i > this.buf.length) {
      expandCapacity(i);
    }
    this.buf[this.count] = paramChar;
    this.count = i;
  }
  
  public void write(int paramInt)
  {
    int i = 1 + this.count;
    if (i > this.buf.length) {
      expandCapacity(i);
    }
    this.buf[this.count] = ((char)paramInt);
    this.count = i;
  }
  
  public void write(String paramString)
  {
    if (paramString == null)
    {
      writeNull();
      return;
    }
    int i = paramString.length();
    int j = i + this.count;
    if (j > this.buf.length) {
      expandCapacity(j);
    }
    paramString.getChars(0, i, this.buf, this.count);
    this.count = j;
  }
  
  public void write(String paramString, int paramInt1, int paramInt2)
  {
    int i = paramInt2 + this.count;
    if (i > this.buf.length) {
      expandCapacity(i);
    }
    paramString.getChars(paramInt1, paramInt1 + paramInt2, this.buf, this.count);
    this.count = i;
  }
  
  public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 > paramArrayOfChar.length) || (paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfChar.length) || (paramInt1 + paramInt2 < 0)) {
      throw new IndexOutOfBoundsException();
    }
    if (paramInt2 == 0) {
      return;
    }
    int i = paramInt2 + this.count;
    if (i > this.buf.length) {
      expandCapacity(i);
    }
    System.arraycopy(paramArrayOfChar, paramInt1, this.buf, this.count, paramInt2);
    this.count = i;
  }
  
  public void writeBooleanArray(boolean[] paramArrayOfBoolean)
    throws IOException
  {
    int[] arrayOfInt = new int[paramArrayOfBoolean.length];
    int i = 2;
    int j = 0;
    if (j < paramArrayOfBoolean.length)
    {
      if (j != 0) {
        i++;
      }
      if (paramArrayOfBoolean[j] != 0) {}
      for (int i10 = 4;; i10 = 5)
      {
        arrayOfInt[j] = i10;
        i += i10;
        j++;
        break;
      }
    }
    int k = i + this.count;
    if (k > this.buf.length) {
      expandCapacity(k);
    }
    this.buf[this.count] = '[';
    int m = 1 + this.count;
    int n = 0;
    if (n < paramArrayOfBoolean.length)
    {
      if (n != 0)
      {
        char[] arrayOfChar10 = this.buf;
        int i9 = m + 1;
        arrayOfChar10[m] = ',';
        m = i9;
      }
      if (paramArrayOfBoolean[n] != 0)
      {
        char[] arrayOfChar6 = this.buf;
        int i6 = m + 1;
        arrayOfChar6[m] = 't';
        char[] arrayOfChar7 = this.buf;
        int i7 = i6 + 1;
        arrayOfChar7[i6] = 'r';
        char[] arrayOfChar8 = this.buf;
        int i8 = i7 + 1;
        arrayOfChar8[i7] = 'u';
        char[] arrayOfChar9 = this.buf;
        m = i8 + 1;
        arrayOfChar9[i8] = 'e';
      }
      for (;;)
      {
        n++;
        break;
        char[] arrayOfChar1 = this.buf;
        int i1 = m + 1;
        arrayOfChar1[m] = 'f';
        char[] arrayOfChar2 = this.buf;
        int i2 = i1 + 1;
        arrayOfChar2[i1] = 'a';
        char[] arrayOfChar3 = this.buf;
        int i3 = i2 + 1;
        arrayOfChar3[i2] = 'l';
        char[] arrayOfChar4 = this.buf;
        int i4 = i3 + 1;
        arrayOfChar4[i3] = 's';
        char[] arrayOfChar5 = this.buf;
        int i5 = i4 + 1;
        arrayOfChar5[i4] = 'e';
        m = i5;
      }
    }
    this.buf[m] = ']';
    this.count = k;
  }
  
  public void writeByteArray(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    if (i == 0)
    {
      write("\"\"");
      return;
    }
    char[] arrayOfChar1 = Base64.CA;
    int j = 3 * (i / 3);
    int k = 1 + (i - 1) / 3 << 2;
    int m = this.count;
    int n = 2 + (k + this.count);
    if (n > this.buf.length) {
      expandCapacity(n);
    }
    this.count = n;
    char[] arrayOfChar2 = this.buf;
    int i1 = m + 1;
    arrayOfChar2[m] = '"';
    int i2 = i1;
    int i14;
    for (int i3 = 0; i3 < j; i3 = i14)
    {
      int i10 = i3 + 1;
      int i11 = (0xFF & paramArrayOfByte[i3]) << 16;
      int i12 = i10 + 1;
      int i13 = i11 | (0xFF & paramArrayOfByte[i10]) << 8;
      i14 = i12 + 1;
      int i15 = i13 | 0xFF & paramArrayOfByte[i12];
      char[] arrayOfChar4 = this.buf;
      int i16 = i2 + 1;
      arrayOfChar4[i2] = arrayOfChar1[(0x3F & i15 >>> 18)];
      char[] arrayOfChar5 = this.buf;
      int i17 = i16 + 1;
      arrayOfChar5[i16] = arrayOfChar1[(0x3F & i15 >>> 12)];
      char[] arrayOfChar6 = this.buf;
      int i18 = i17 + 1;
      arrayOfChar6[i17] = arrayOfChar1[(0x3F & i15 >>> 6)];
      char[] arrayOfChar7 = this.buf;
      i2 = i18 + 1;
      arrayOfChar7[i18] = arrayOfChar1[(i15 & 0x3F)];
    }
    int i4 = i - j;
    int i6;
    int i7;
    char[] arrayOfChar3;
    int i8;
    if (i4 > 0)
    {
      int i5 = (0xFF & paramArrayOfByte[j]) << 10;
      if (i4 != 2) {
        break label422;
      }
      i6 = (0xFF & paramArrayOfByte[(i - 1)]) << 2;
      i7 = i5 | i6;
      this.buf[(n - 5)] = arrayOfChar1[(i7 >> 12)];
      this.buf[(n - 4)] = arrayOfChar1[(0x3F & i7 >>> 6)];
      arrayOfChar3 = this.buf;
      i8 = n - 3;
      if (i4 != 2) {
        break label428;
      }
    }
    label422:
    label428:
    for (int i9 = arrayOfChar1[(i7 & 0x3F)];; i9 = 61)
    {
      arrayOfChar3[i8] = i9;
      this.buf[(n - 2)] = '=';
      this.buf[(n - 1)] = '"';
      return;
      i6 = 0;
      break;
    }
  }
  
  public void writeFieldEmptyList(char paramChar, String paramString)
  {
    write(paramChar);
    writeFieldName(paramString);
    write("[]");
  }
  
  public void writeFieldName(String paramString)
  {
    writeFieldName(paramString, false);
  }
  
  public void writeFieldName(String paramString, boolean paramBoolean)
  {
    if (paramString == null)
    {
      write("null:");
      return;
    }
    if (isEnabled(SerializerFeature.UseSingleQuotes))
    {
      if (isEnabled(SerializerFeature.QuoteFieldNames))
      {
        writeKeyWithSingleQuote(paramString);
        return;
      }
      writeKeyWithSingleQuoteIfHasSpecial(paramString);
      return;
    }
    if (isEnabled(SerializerFeature.QuoteFieldNames))
    {
      writeKeyWithDoubleQuote(paramString, paramBoolean);
      return;
    }
    writeKeyWithDoubleQuoteIfHasSpecial(paramString);
  }
  
  public void writeFieldNull(char paramChar, String paramString)
  {
    write(paramChar);
    writeFieldName(paramString);
    writeNull();
  }
  
  public void writeFieldNullBoolean(char paramChar, String paramString)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (isEnabled(SerializerFeature.WriteNullBooleanAsFalse))
    {
      write("false");
      return;
    }
    writeNull();
  }
  
  public void writeFieldNullList(char paramChar, String paramString)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (isEnabled(SerializerFeature.WriteNullListAsEmpty))
    {
      write("[]");
      return;
    }
    writeNull();
  }
  
  public void writeFieldNullNumber(char paramChar, String paramString)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (isEnabled(SerializerFeature.WriteNullNumberAsZero))
    {
      write('0');
      return;
    }
    writeNull();
  }
  
  public void writeFieldNullString(char paramChar, String paramString)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (isEnabled(SerializerFeature.WriteNullStringAsEmpty))
    {
      writeString("");
      return;
    }
    writeNull();
  }
  
  public void writeFieldValue(char paramChar1, String paramString, char paramChar2)
  {
    write(paramChar1);
    writeFieldName(paramString);
    if (paramChar2 == 0)
    {
      writeString("\000");
      return;
    }
    writeString(Character.toString(paramChar2));
  }
  
  public void writeFieldValue(char paramChar, String paramString, double paramDouble)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (paramDouble == 0.0D)
    {
      write('0');
      return;
    }
    if (Double.isNaN(paramDouble))
    {
      writeNull();
      return;
    }
    if (Double.isInfinite(paramDouble))
    {
      writeNull();
      return;
    }
    String str = Double.toString(paramDouble);
    if (str.endsWith(".0")) {
      str = str.substring(0, -2 + str.length());
    }
    write(str);
  }
  
  public void writeFieldValue(char paramChar, String paramString, float paramFloat)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (paramFloat == 0.0F)
    {
      write('0');
      return;
    }
    if (Float.isNaN(paramFloat))
    {
      writeNull();
      return;
    }
    if (Float.isInfinite(paramFloat))
    {
      writeNull();
      return;
    }
    String str = Float.toString(paramFloat);
    if (str.endsWith(".0")) {
      str = str.substring(0, -2 + str.length());
    }
    write(str);
  }
  
  public void writeFieldValue(char paramChar, String paramString, int paramInt)
  {
    if ((paramInt == Integer.MIN_VALUE) || (!isEnabled(SerializerFeature.QuoteFieldNames)))
    {
      writeFieldValue1(paramChar, paramString, paramInt);
      return;
    }
    int i;
    if (isEnabled(SerializerFeature.UseSingleQuotes))
    {
      i = 39;
      if (paramInt >= 0) {
        break label186;
      }
    }
    label186:
    for (int j = 1 + IOUtils.stringSize(-paramInt);; j = IOUtils.stringSize(paramInt))
    {
      int k = paramString.length();
      int m = j + (4 + (k + this.count));
      if (m > this.buf.length) {
        expandCapacity(m);
      }
      int n = this.count;
      this.count = m;
      this.buf[n] = paramChar;
      int i1 = 1 + (n + k);
      this.buf[(n + 1)] = i;
      paramString.getChars(0, k, this.buf, n + 2);
      this.buf[(i1 + 1)] = i;
      this.buf[(i1 + 2)] = ':';
      IOUtils.getChars(paramInt, this.count, this.buf);
      return;
      i = 34;
      break;
    }
  }
  
  public void writeFieldValue(char paramChar, String paramString, long paramLong)
  {
    if ((paramLong == Long.MIN_VALUE) || (!isEnabled(SerializerFeature.QuoteFieldNames)))
    {
      writeFieldValue1(paramChar, paramString, paramLong);
      return;
    }
    int i;
    if (isEnabled(SerializerFeature.UseSingleQuotes))
    {
      i = 39;
      if (paramLong >= 0L) {
        break label188;
      }
    }
    label188:
    for (int j = 1 + IOUtils.stringSize(-paramLong);; j = IOUtils.stringSize(paramLong))
    {
      int k = paramString.length();
      int m = j + (4 + (k + this.count));
      if (m > this.buf.length) {
        expandCapacity(m);
      }
      int n = this.count;
      this.count = m;
      this.buf[n] = paramChar;
      int i1 = 1 + (n + k);
      this.buf[(n + 1)] = i;
      paramString.getChars(0, k, this.buf, n + 2);
      this.buf[(i1 + 1)] = i;
      this.buf[(i1 + 2)] = ':';
      IOUtils.getChars(paramLong, this.count, this.buf);
      return;
      i = 34;
      break;
    }
  }
  
  public void writeFieldValue(char paramChar, String paramString, Enum<?> paramEnum)
  {
    if (paramEnum == null)
    {
      write(paramChar);
      writeFieldName(paramString);
      writeNull();
      return;
    }
    if (isEnabled(SerializerFeature.WriteEnumUsingToString))
    {
      if (isEnabled(SerializerFeature.UseSingleQuotes))
      {
        writeFieldValue(paramChar, paramString, paramEnum.name());
        return;
      }
      writeFieldValueStringWithDoubleQuote(paramChar, paramString, paramEnum.name(), false);
      return;
    }
    writeFieldValue(paramChar, paramString, paramEnum.ordinal());
  }
  
  public void writeFieldValue(char paramChar, String paramString1, String paramString2)
  {
    if (isEnabled(SerializerFeature.QuoteFieldNames))
    {
      if (isEnabled(SerializerFeature.UseSingleQuotes))
      {
        write(paramChar);
        writeFieldName(paramString1);
        if (paramString2 == null)
        {
          writeNull();
          return;
        }
        writeString(paramString2);
        return;
      }
      if (isEnabled(SerializerFeature.BrowserCompatible))
      {
        write(paramChar);
        writeStringWithDoubleQuote(paramString1, ':');
        writeStringWithDoubleQuote(paramString2, '\000');
        return;
      }
      writeFieldValueStringWithDoubleQuote(paramChar, paramString1, paramString2, true);
      return;
    }
    write(paramChar);
    writeFieldName(paramString1);
    if (paramString2 == null)
    {
      writeNull();
      return;
    }
    writeString(paramString2);
  }
  
  public void writeFieldValue(char paramChar, String paramString, BigDecimal paramBigDecimal)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (paramBigDecimal == null)
    {
      writeNull();
      return;
    }
    write(paramBigDecimal.toString());
  }
  
  public void writeFieldValue(char paramChar, String paramString, boolean paramBoolean)
  {
    int i;
    if (isEnabled(SerializerFeature.UseSingleQuotes))
    {
      i = 39;
      if (!paramBoolean) {
        break label154;
      }
    }
    int i1;
    label154:
    for (int j = 4;; j = 5)
    {
      int k = paramString.length();
      int m = j + (4 + (k + this.count));
      if (m > this.buf.length) {
        expandCapacity(m);
      }
      int n = this.count;
      this.count = m;
      this.buf[n] = paramChar;
      i1 = 1 + (n + k);
      this.buf[(n + 1)] = i;
      paramString.getChars(0, k, this.buf, n + 2);
      this.buf[(i1 + 1)] = i;
      if (!paramBoolean) {
        break label160;
      }
      System.arraycopy(":true".toCharArray(), 0, this.buf, i1 + 2, 5);
      return;
      i = 34;
      break;
    }
    label160:
    System.arraycopy(":false".toCharArray(), 0, this.buf, i1 + 2, 6);
  }
  
  public void writeFieldValue1(char paramChar, String paramString, int paramInt)
  {
    write(paramChar);
    writeFieldName(paramString);
    writeInt(paramInt);
  }
  
  public void writeFieldValue1(char paramChar, String paramString, long paramLong)
  {
    write(paramChar);
    writeFieldName(paramString);
    writeLong(paramLong);
  }
  
  public void writeFieldValue1(char paramChar, String paramString, boolean paramBoolean)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (paramBoolean)
    {
      write("true");
      return;
    }
    write("false");
  }
  
  public void writeInt(int paramInt)
  {
    if (paramInt == Integer.MIN_VALUE)
    {
      write("-2147483648");
      return;
    }
    if (paramInt < 0) {}
    for (int i = 1 + IOUtils.stringSize(-paramInt);; i = IOUtils.stringSize(paramInt))
    {
      int j = i + this.count;
      if (j > this.buf.length) {
        expandCapacity(j);
      }
      IOUtils.getChars(paramInt, j, this.buf);
      this.count = j;
      return;
    }
  }
  
  public void writeIntAndChar(int paramInt, char paramChar)
  {
    if (paramInt == Integer.MIN_VALUE)
    {
      write("-2147483648");
      write(paramChar);
      return;
    }
    if (paramInt < 0) {}
    for (int i = 1 + IOUtils.stringSize(-paramInt);; i = IOUtils.stringSize(paramInt))
    {
      int j = i + this.count;
      int k = j + 1;
      if (k > this.buf.length) {
        expandCapacity(k);
      }
      IOUtils.getChars(paramInt, j, this.buf);
      this.buf[j] = paramChar;
      this.count = k;
      return;
    }
  }
  
  public void writeIntArray(int[] paramArrayOfInt)
  {
    int[] arrayOfInt = new int[paramArrayOfInt.length];
    int i = 2;
    int j = 0;
    while (j < paramArrayOfInt.length)
    {
      if (j != 0) {
        i++;
      }
      int i3 = paramArrayOfInt[j];
      int i4;
      if (i3 == Integer.MIN_VALUE)
      {
        i4 = "-2147483648".length();
        arrayOfInt[j] = i4;
        i += i4;
        j++;
      }
      else
      {
        if (i3 < 0) {}
        for (i4 = 1 + IOUtils.stringSize(-i3);; i4 = IOUtils.stringSize(i3)) {
          break;
        }
      }
    }
    int k = i + this.count;
    if (k > this.buf.length) {
      expandCapacity(k);
    }
    this.buf[this.count] = '[';
    int m = 1 + this.count;
    int n = 0;
    if (n < paramArrayOfInt.length)
    {
      if (n != 0)
      {
        char[] arrayOfChar = this.buf;
        int i2 = m + 1;
        arrayOfChar[m] = ',';
        m = i2;
      }
      int i1 = paramArrayOfInt[n];
      if (i1 == Integer.MIN_VALUE)
      {
        System.arraycopy("-2147483648".toCharArray(), 0, this.buf, m, arrayOfInt[n]);
        m += arrayOfInt[n];
      }
      for (;;)
      {
        n++;
        break;
        m += arrayOfInt[n];
        IOUtils.getChars(i1, m, this.buf);
      }
    }
    this.buf[m] = ']';
    this.count = k;
  }
  
  public void writeKeyWithDoubleQuote(String paramString)
  {
    writeKeyWithDoubleQuote(paramString, true);
  }
  
  public void writeKeyWithDoubleQuote(String paramString, boolean paramBoolean)
  {
    boolean[] arrayOfBoolean = CharTypes.specicalFlags_doubleQuotes;
    int i = paramString.length();
    int j = 3 + (i + this.count);
    if (j > this.buf.length) {
      expandCapacity(j);
    }
    int k = 1 + this.count;
    int m = k + i;
    this.buf[this.count] = '"';
    paramString.getChars(0, i, this.buf, k);
    this.count = j;
    if (paramBoolean) {
      for (int n = k; n < m; n++)
      {
        int i1 = this.buf[n];
        if (((i1 < arrayOfBoolean.length) && (arrayOfBoolean[i1] != 0)) || ((i1 == 9) && (isEnabled(SerializerFeature.WriteTabAsSpecial))) || ((i1 == 47) && (isEnabled(SerializerFeature.WriteSlashAsSpecial))))
        {
          j++;
          if (j > this.buf.length) {
            expandCapacity(j);
          }
          this.count = j;
          System.arraycopy(this.buf, n + 1, this.buf, n + 2, -1 + (m - n));
          this.buf[n] = '\\';
          char[] arrayOfChar = this.buf;
          n++;
          arrayOfChar[n] = CharTypes.replaceChars[i1];
          m++;
        }
      }
    }
    this.buf[(-2 + this.count)] = '"';
    this.buf[(-1 + this.count)] = ':';
  }
  
  public void writeLong(long paramLong)
  {
    if (paramLong == Long.MIN_VALUE)
    {
      write("-9223372036854775808");
      return;
    }
    if (paramLong < 0L) {}
    for (int i = 1 + IOUtils.stringSize(-paramLong);; i = IOUtils.stringSize(paramLong))
    {
      int j = i + this.count;
      if (j > this.buf.length) {
        expandCapacity(j);
      }
      IOUtils.getChars(paramLong, j, this.buf);
      this.count = j;
      return;
    }
  }
  
  public void writeLongAndChar(long paramLong, char paramChar)
    throws IOException
  {
    if (paramLong == Long.MIN_VALUE)
    {
      write("-9223372036854775808");
      write(paramChar);
      return;
    }
    if (paramLong < 0L) {}
    for (int i = 1 + IOUtils.stringSize(-paramLong);; i = IOUtils.stringSize(paramLong))
    {
      int j = i + this.count;
      int k = j + 1;
      if (k > this.buf.length) {
        expandCapacity(k);
      }
      IOUtils.getChars(paramLong, j, this.buf);
      this.buf[j] = paramChar;
      this.count = k;
      return;
    }
  }
  
  public void writeLongArray(long[] paramArrayOfLong)
  {
    int[] arrayOfInt = new int[paramArrayOfLong.length];
    int i = 2;
    int j = 0;
    while (j < paramArrayOfLong.length)
    {
      if (j != 0) {
        i++;
      }
      long l2 = paramArrayOfLong[j];
      int i2;
      if (l2 == Long.MIN_VALUE)
      {
        i2 = "-9223372036854775808".length();
        arrayOfInt[j] = i2;
        i += i2;
        j++;
      }
      else
      {
        if (l2 < 0L) {}
        for (i2 = 1 + IOUtils.stringSize(-l2);; i2 = IOUtils.stringSize(l2)) {
          break;
        }
      }
    }
    int k = i + this.count;
    if (k > this.buf.length) {
      expandCapacity(k);
    }
    this.buf[this.count] = '[';
    int m = 1 + this.count;
    int n = 0;
    if (n < paramArrayOfLong.length)
    {
      if (n != 0)
      {
        char[] arrayOfChar = this.buf;
        int i1 = m + 1;
        arrayOfChar[m] = ',';
        m = i1;
      }
      long l1 = paramArrayOfLong[n];
      if (l1 == Long.MIN_VALUE)
      {
        System.arraycopy("-9223372036854775808".toCharArray(), 0, this.buf, m, arrayOfInt[n]);
        m += arrayOfInt[n];
      }
      for (;;)
      {
        n++;
        break;
        m += arrayOfInt[n];
        IOUtils.getChars(l1, m, this.buf);
      }
    }
    this.buf[m] = ']';
    this.count = k;
  }
  
  public void writeNull()
  {
    int i = 4 + this.count;
    if (i > this.buf.length) {
      expandCapacity(i);
    }
    this.buf[this.count] = 'n';
    this.buf[(1 + this.count)] = 'u';
    this.buf[(2 + this.count)] = 'l';
    this.buf[(3 + this.count)] = 'l';
    this.count = i;
  }
  
  public void writeShortArray(short[] paramArrayOfShort)
    throws IOException
  {
    int[] arrayOfInt = new int[paramArrayOfShort.length];
    int i = 2;
    for (int j = 0; j < paramArrayOfShort.length; j++)
    {
      if (j != 0) {
        i++;
      }
      int i3 = IOUtils.stringSize(paramArrayOfShort[j]);
      arrayOfInt[j] = i3;
      i += i3;
    }
    int k = i + this.count;
    if (k > this.buf.length) {
      expandCapacity(k);
    }
    this.buf[this.count] = '[';
    int m = 1 + this.count;
    for (int n = 0; n < paramArrayOfShort.length; n++)
    {
      if (n != 0)
      {
        char[] arrayOfChar = this.buf;
        int i2 = m + 1;
        arrayOfChar[m] = ',';
        m = i2;
      }
      int i1 = paramArrayOfShort[n];
      m += arrayOfInt[n];
      IOUtils.getChars(i1, m, this.buf);
    }
    this.buf[m] = ']';
    this.count = k;
  }
  
  public void writeString(String paramString)
  {
    if (isEnabled(SerializerFeature.UseSingleQuotes))
    {
      writeStringWithSingleQuote(paramString);
      return;
    }
    writeStringWithDoubleQuote(paramString, '\000');
  }
  
  public void writeString(String paramString, char paramChar)
  {
    if (isEnabled(SerializerFeature.UseSingleQuotes))
    {
      writeStringWithSingleQuote(paramString);
      write(paramChar);
      return;
    }
    writeStringWithDoubleQuote(paramString, paramChar);
  }
  
  public void writeTo(OutputStream paramOutputStream, String paramString)
    throws IOException
  {
    paramOutputStream.write(new String(this.buf, 0, this.count).getBytes(paramString));
  }
  
  public void writeTo(OutputStream paramOutputStream, Charset paramCharset)
    throws IOException
  {
    paramOutputStream.write(new String(this.buf, 0, this.count).getBytes(paramCharset));
  }
  
  public void writeTo(Writer paramWriter)
    throws IOException
  {
    paramWriter.write(this.buf, 0, this.count);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\SerializeWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */