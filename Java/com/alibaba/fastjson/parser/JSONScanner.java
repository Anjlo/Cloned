package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.Base64;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;

public final class JSONScanner
  implements JSONLexer
{
  public static final int ARRAY = 2;
  public static final int END = 4;
  public static final byte EOI = 26;
  private static final int INT_MULTMIN_RADIX_TEN = -214748364;
  private static final int INT_N_MULTMAX_RADIX_TEN = -214748364;
  private static final long MULTMIN_RADIX_TEN = -922337203685477580L;
  public static final int NOT_MATCH = -1;
  public static final int NOT_MATCH_NAME = -2;
  private static final long N_MULTMAX_RADIX_TEN = -922337203685477580L;
  public static final int OBJECT = 1;
  public static final int UNKOWN = 0;
  public static final int VALUE = 3;
  private static final int[] digits;
  private static final ThreadLocal<SoftReference<char[]>> sbufRefLocal = new ThreadLocal();
  private static final char[] typeFieldName;
  private static boolean[] whitespaceFlags = new boolean['Ā'];
  public final int ISO8601_LEN_0 = "0000-00-00".length();
  public final int ISO8601_LEN_1 = "0000-00-00T00:00:00".length();
  public final int ISO8601_LEN_2 = "0000-00-00T00:00:00.000".length();
  private int bp;
  private Calendar calendar = null;
  private char ch;
  private int eofPos;
  private int features = JSON.DEFAULT_PARSER_FEATURE;
  boolean hasSpecial;
  private Keywords keywods = Keywords.DEFAULT_KEYWORDS;
  public int matchStat = 0;
  private int np;
  private int pos;
  public int resetCount = 0;
  private boolean resetFlag = false;
  private char[] sbuf;
  private int sp;
  private final String text;
  private int token;
  
  static
  {
    whitespaceFlags[32] = true;
    whitespaceFlags[10] = true;
    whitespaceFlags[13] = true;
    whitespaceFlags[9] = true;
    whitespaceFlags[12] = true;
    whitespaceFlags[8] = true;
    typeFieldName = ("\"" + JSON.DEFAULT_TYPE_KEY + "\":\"").toCharArray();
    digits = new int[103];
    for (int i = 48; i <= 57; i++) {
      digits[i] = (i - 48);
    }
    for (int j = 97; j <= 102; j++) {
      digits[j] = (10 + (j - 97));
    }
    for (int k = 65; k <= 70; k++) {
      digits[k] = (10 + (k - 65));
    }
  }
  
  public JSONScanner(String paramString)
  {
    this(paramString, JSON.DEFAULT_PARSER_FEATURE);
  }
  
  public JSONScanner(String paramString, int paramInt)
  {
    this.features = paramInt;
    SoftReference localSoftReference = (SoftReference)sbufRefLocal.get();
    if (localSoftReference != null)
    {
      this.sbuf = ((char[])localSoftReference.get());
      sbufRefLocal.set(null);
    }
    if (this.sbuf == null) {
      this.sbuf = new char[64];
    }
    this.text = paramString;
    this.bp = -1;
    int i = 1 + this.bp;
    this.bp = i;
    this.ch = charAt(i);
    if (this.ch == 65279)
    {
      int j = 1 + this.bp;
      this.bp = j;
      this.ch = charAt(j);
    }
  }
  
  public JSONScanner(char[] paramArrayOfChar, int paramInt)
  {
    this(paramArrayOfChar, paramInt, JSON.DEFAULT_PARSER_FEATURE);
  }
  
  public JSONScanner(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    this(new String(paramArrayOfChar, 0, paramInt1), paramInt2);
  }
  
  static final boolean charArrayCompare(String paramString, int paramInt, char[] paramArrayOfChar)
  {
    int i = paramArrayOfChar.length;
    if (i + paramInt > paramString.length()) {
      return false;
    }
    for (int j = 0;; j++)
    {
      if (j >= i) {
        break label45;
      }
      if (paramArrayOfChar[j] != paramString.charAt(paramInt + j)) {
        break;
      }
    }
    label45:
    return true;
  }
  
  static final boolean charArrayCompare(char[] paramArrayOfChar1, int paramInt, char[] paramArrayOfChar2)
  {
    int i = paramArrayOfChar2.length;
    for (int j = 0; j < i; j++) {
      if (paramArrayOfChar2[j] != paramArrayOfChar1[(paramInt + j)]) {
        return false;
      }
    }
    return true;
  }
  
  public static final boolean isWhitespace(char paramChar)
  {
    return (paramChar == ' ') || (paramChar == '\n') || (paramChar == '\r') || (paramChar == '\t') || (paramChar == '\f') || (paramChar == '\b');
  }
  
  private void lexError(String paramString, Object... paramVarArgs)
  {
    this.token = 1;
  }
  
  private final void putChar(char paramChar)
  {
    if (this.sp == this.sbuf.length)
    {
      char[] arrayOfChar2 = new char[2 * this.sbuf.length];
      System.arraycopy(this.sbuf, 0, arrayOfChar2, 0, this.sbuf.length);
      this.sbuf = arrayOfChar2;
    }
    char[] arrayOfChar1 = this.sbuf;
    int i = this.sp;
    this.sp = (i + 1);
    arrayOfChar1[i] = paramChar;
  }
  
  public byte[] bytesValue()
  {
    return Base64.decodeFast(this.text, 1 + this.np, this.sp);
  }
  
  public final char charAt(int paramInt)
  {
    if (paramInt >= this.text.length()) {
      return '\032';
    }
    return this.text.charAt(paramInt);
  }
  
  public void close()
  {
    if (this.sbuf.length <= 8192) {
      sbufRefLocal.set(new SoftReference(this.sbuf));
    }
    this.sbuf = null;
  }
  
  public void config(Feature paramFeature, boolean paramBoolean)
  {
    this.features = Feature.config(this.features, paramFeature, paramBoolean);
  }
  
  public Number decimalValue(boolean paramBoolean)
  {
    int i = charAt(-1 + (this.np + this.sp));
    if (i == 70) {
      return Float.valueOf(Float.parseFloat(this.text.substring(this.np, -1 + (this.np + this.sp))));
    }
    if (i == 68) {
      return Double.valueOf(Double.parseDouble(this.text.substring(this.np, -1 + (this.np + this.sp))));
    }
    if (paramBoolean) {
      return decimalValue();
    }
    return Double.valueOf(doubleValue());
  }
  
  public BigDecimal decimalValue()
  {
    int i = charAt(-1 + (this.np + this.sp));
    int j = this.sp;
    if ((i == 76) || (i == 83) || (i == 66) || (i == 70) || (i == 68)) {
      j--;
    }
    return new BigDecimal(this.text.substring(this.np, j + this.np));
  }
  
  public double doubleValue()
  {
    return Double.parseDouble(numberString());
  }
  
  public float floatValue()
  {
    return Float.parseFloat(numberString());
  }
  
  public final int getBufferPosition()
  {
    return this.bp;
  }
  
  public Calendar getCalendar()
  {
    return this.calendar;
  }
  
  public final char getCurrent()
  {
    return this.ch;
  }
  
  public final void incrementBufferPosition()
  {
    int i = 1 + this.bp;
    this.bp = i;
    this.ch = charAt(i);
  }
  
  public int intValue()
  {
    int i = this.np;
    int j = this.np + this.sp;
    int n;
    int k;
    int m;
    int i1;
    label81:
    int i2;
    int i3;
    if (charAt(this.np) == '-')
    {
      n = 1;
      k = Integer.MIN_VALUE;
      m = i + 1;
      if (n != 0) {}
      i1 = 0;
      if (m < j)
      {
        int[] arrayOfInt = digits;
        int i6 = m + 1;
        i1 = -arrayOfInt[charAt(m)];
        m = i6;
      }
      if (m >= j) {
        break label239;
      }
      i2 = m + 1;
      i3 = charAt(m);
      if ((i3 != 76) && (i3 != 83) && (i3 != 66)) {
        break label154;
      }
    }
    for (;;)
    {
      if (n != 0)
      {
        if (i2 > 1 + this.np)
        {
          return i1;
          k = -2147483647;
          m = i;
          n = 0;
          break;
          label154:
          int i4 = digits[i3];
          if (i1 < -214748364) {
            throw new NumberFormatException(numberString());
          }
          int i5 = i1 * 10;
          if (i5 < k + i4) {
            throw new NumberFormatException(numberString());
          }
          i1 = i5 - i4;
          m = i2;
          break label81;
        }
        throw new NumberFormatException(numberString());
      }
      return -i1;
      label239:
      i2 = m;
    }
  }
  
  public Number integerValue()
    throws NumberFormatException
  {
    long l1 = 0L;
    int i = this.np;
    int j = this.np + this.sp;
    int k = 32;
    if (j > 0) {}
    int n;
    long l2;
    label94:
    long l3;
    label104:
    int i3;
    switch (charAt(j - 1))
    {
    default: 
      if (charAt(this.np) == '-')
      {
        n = 1;
        l2 = Long.MIN_VALUE;
        m = i + 1;
        if (n == 0) {
          break label230;
        }
        l3 = -922337203685477580L;
        if (m < j)
        {
          int[] arrayOfInt2 = digits;
          i3 = m + 1;
          l1 = -arrayOfInt2[charAt(m)];
        }
      }
      break;
    }
    int i1;
    for (int m = i3;; m = i1)
    {
      if (m >= j) {
        break label283;
      }
      int[] arrayOfInt1 = digits;
      i1 = m + 1;
      int i2 = arrayOfInt1[charAt(m)];
      if (l1 < l3)
      {
        return new BigInteger(numberString());
        j--;
        k = 76;
        break;
        j--;
        k = 83;
        break;
        j--;
        k = 66;
        break;
        l2 = -9223372036854775807L;
        m = i;
        n = 0;
        break label94;
        label230:
        l3 = -922337203685477580L;
        break label104;
      }
      long l5 = l1 * 10L;
      if (l5 < l2 + i2) {
        return new BigInteger(numberString());
      }
      l1 = l5 - i2;
    }
    label283:
    if (n != 0)
    {
      if (m > 1 + this.np)
      {
        if ((l1 >= -2147483648L) && (k != 76))
        {
          if (k == 83)
          {
            Short localShort2 = Short.valueOf((short)(int)l1);
            return localShort2;
          }
          if (k == 66)
          {
            Byte localByte2 = Byte.valueOf((byte)(int)l1);
            return localByte2;
          }
          Integer localInteger2 = Integer.valueOf((int)l1);
          return localInteger2;
        }
        Long localLong2 = Long.valueOf(l1);
        return localLong2;
      }
      throw new NumberFormatException(numberString());
    }
    long l4 = -l1;
    if ((l4 <= 2147483647L) && (k != 76))
    {
      if (k == 83)
      {
        Short localShort1 = Short.valueOf((short)(int)l4);
        return localShort1;
      }
      if (k == 66)
      {
        Byte localByte1 = Byte.valueOf((byte)(int)l4);
        return localByte1;
      }
      Integer localInteger1 = Integer.valueOf((int)l4);
      return localInteger1;
    }
    Long localLong1 = Long.valueOf(l4);
    return localLong1;
  }
  
  public boolean isBlankInput()
  {
    for (int i = 0; i < this.text.length(); i++) {
      if (!isWhitespace(charAt(i))) {
        return false;
      }
    }
    return true;
  }
  
  public boolean isEOF()
  {
    return this.token == 20;
  }
  
  public boolean isEnabled(Feature paramFeature)
  {
    return Feature.isEnabled(this.features, paramFeature);
  }
  
  public boolean isRef()
  {
    if (this.hasSpecial) {}
    while ((this.sp != 4) || (charAt(1 + this.np) != '$') || (charAt(2 + this.np) != 'r') || (charAt(3 + this.np) != 'e') || (charAt(4 + this.np) != 'f')) {
      return false;
    }
    return true;
  }
  
  public boolean isResetFlag()
  {
    return this.resetFlag;
  }
  
  public long longValue()
    throws NumberFormatException
  {
    long l1 = 0L;
    int i = this.np;
    int j = this.np + this.sp;
    int m;
    long l2;
    int k;
    label83:
    int n;
    int i1;
    if (charAt(this.np) == '-')
    {
      m = 1;
      l2 = Long.MIN_VALUE;
      k = i + 1;
      if ((m == 0) || (k < j))
      {
        int[] arrayOfInt = digits;
        int i3 = k + 1;
        l1 = -arrayOfInt[charAt(k)];
        k = i3;
      }
      if (k >= j) {
        break label245;
      }
      n = k + 1;
      i1 = charAt(k);
      if ((i1 != 76) && (i1 != 83) && (i1 != 66)) {
        break label157;
      }
    }
    for (;;)
    {
      if (m != 0)
      {
        if (n > 1 + this.np)
        {
          return l1;
          l2 = -9223372036854775807L;
          k = i;
          m = 0;
          break;
          label157:
          int i2 = digits[i1];
          if (l1 < -922337203685477580L) {
            throw new NumberFormatException(numberString());
          }
          long l3 = l1 * 10L;
          if (l3 < l2 + i2) {
            throw new NumberFormatException(numberString());
          }
          l1 = l3 - i2;
          k = n;
          break label83;
        }
        throw new NumberFormatException(numberString());
      }
      return -l1;
      label245:
      n = k;
    }
  }
  
  public boolean matchField(char[] paramArrayOfChar)
  {
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar)) {
      return false;
    }
    this.bp += paramArrayOfChar.length;
    this.ch = charAt(this.bp);
    if (this.ch == '{')
    {
      int j = 1 + this.bp;
      this.bp = j;
      this.ch = charAt(j);
      this.token = 12;
    }
    for (;;)
    {
      return true;
      if (this.ch == '[')
      {
        int i = 1 + this.bp;
        this.bp = i;
        this.ch = charAt(i);
        this.token = 14;
      }
      else
      {
        nextToken();
      }
    }
  }
  
  public final void nextToken()
  {
    this.sp = 0;
    for (;;)
    {
      this.pos = this.bp;
      if (this.ch == '"')
      {
        scanString();
        return;
      }
      if (this.ch == ',')
      {
        int i6 = 1 + this.bp;
        this.bp = i6;
        this.ch = charAt(i6);
        this.token = 16;
        return;
      }
      if ((this.ch >= '0') && (this.ch <= '9'))
      {
        scanNumber();
        return;
      }
      if (this.ch == '-')
      {
        scanNumber();
        return;
      }
      switch (this.ch)
      {
      default: 
        if ((this.bp != this.text.length()) && ((this.ch != '\032') || (1 + this.bp != this.text.length()))) {
          break label655;
        }
        if (this.token != 20) {
          break;
        }
        throw new JSONException("EOF error");
      case '\'': 
        if (!isEnabled(Feature.AllowSingleQuotes)) {
          throw new JSONException("Feature.AllowSingleQuotes is false");
        }
        scanStringSingleQuote();
        return;
      case '\b': 
      case '\t': 
      case '\n': 
      case '\f': 
      case '\r': 
      case ' ': 
        int i3 = 1 + this.bp;
        this.bp = i3;
        this.ch = charAt(i3);
      }
    }
    scanTrue();
    return;
    scanTreeSet();
    return;
    scanSet();
    return;
    scanFalse();
    return;
    scanNullOrNew();
    return;
    scanIdent();
    return;
    int i2 = 1 + this.bp;
    this.bp = i2;
    this.ch = charAt(i2);
    this.token = 10;
    return;
    int i1 = 1 + this.bp;
    this.bp = i1;
    this.ch = charAt(i1);
    this.token = 11;
    return;
    int n = 1 + this.bp;
    this.bp = n;
    this.ch = charAt(n);
    this.token = 14;
    return;
    int m = 1 + this.bp;
    this.bp = m;
    this.ch = charAt(m);
    this.token = 15;
    return;
    int k = 1 + this.bp;
    this.bp = k;
    this.ch = charAt(k);
    this.token = 12;
    return;
    int j = 1 + this.bp;
    this.bp = j;
    this.ch = charAt(j);
    this.token = 13;
    return;
    int i = 1 + this.bp;
    this.bp = i;
    this.ch = charAt(i);
    this.token = 17;
    return;
    this.token = 20;
    int i4 = this.eofPos;
    this.bp = i4;
    this.pos = i4;
    return;
    label655:
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = String.valueOf(this.ch);
    lexError("illegal.char", arrayOfObject);
    int i5 = 1 + this.bp;
    this.bp = i5;
    this.ch = charAt(i5);
  }
  
  public void nextToken(int paramInt)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      if ((this.ch == ' ') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\f') || (this.ch == '\b'))
      {
        int i = 1 + this.bp;
        this.bp = i;
        this.ch = charAt(i);
        break;
        if (this.ch == '{')
        {
          this.token = 12;
          int i8 = 1 + this.bp;
          this.bp = i8;
          this.ch = charAt(i8);
          return;
        }
        if (this.ch == '[')
        {
          this.token = 14;
          int i7 = 1 + this.bp;
          this.bp = i7;
          this.ch = charAt(i7);
          return;
          if (this.ch == ',')
          {
            this.token = 16;
            int i6 = 1 + this.bp;
            this.bp = i6;
            this.ch = charAt(i6);
            return;
          }
          if (this.ch == '}')
          {
            this.token = 13;
            int i5 = 1 + this.bp;
            this.bp = i5;
            this.ch = charAt(i5);
            return;
          }
          if (this.ch == ']')
          {
            this.token = 15;
            int i4 = 1 + this.bp;
            this.bp = i4;
            this.ch = charAt(i4);
            return;
          }
          if (this.ch == '\032')
          {
            this.token = 20;
            return;
            if ((this.ch >= '0') && (this.ch <= '9'))
            {
              this.sp = 0;
              this.pos = this.bp;
              scanNumber();
              return;
            }
            if (this.ch == '"')
            {
              this.sp = 0;
              this.pos = this.bp;
              scanString();
              return;
            }
            if (this.ch == '[')
            {
              this.token = 14;
              int i3 = 1 + this.bp;
              this.bp = i3;
              this.ch = charAt(i3);
              return;
            }
            if (this.ch == '{')
            {
              this.token = 12;
              int i2 = 1 + this.bp;
              this.bp = i2;
              this.ch = charAt(i2);
              return;
              if (this.ch == '"')
              {
                this.sp = 0;
                this.pos = this.bp;
                scanString();
                return;
              }
              if ((this.ch >= '0') && (this.ch <= '9'))
              {
                this.sp = 0;
                this.pos = this.bp;
                scanNumber();
                return;
              }
              if (this.ch == '[')
              {
                this.token = 14;
                int i1 = 1 + this.bp;
                this.bp = i1;
                this.ch = charAt(i1);
                return;
              }
              if (this.ch == '{')
              {
                this.token = 12;
                int n = 1 + this.bp;
                this.bp = n;
                this.ch = charAt(n);
                return;
                if (this.ch == '[')
                {
                  this.token = 14;
                  int m = 1 + this.bp;
                  this.bp = m;
                  this.ch = charAt(m);
                  return;
                }
                if (this.ch == '{')
                {
                  this.token = 12;
                  int k = 1 + this.bp;
                  this.bp = k;
                  this.ch = charAt(k);
                  return;
                  if (this.ch == ']')
                  {
                    this.token = 15;
                    int j = 1 + this.bp;
                    this.bp = j;
                    this.ch = charAt(j);
                    return;
                  }
                  if (this.ch == '\032')
                  {
                    this.token = 20;
                    return;
                  }
                }
              }
            }
          }
        }
      }
    }
    nextToken();
  }
  
  public final void nextTokenWithColon()
  {
    for (;;)
    {
      if (this.ch == ':')
      {
        int j = 1 + this.bp;
        this.bp = j;
        this.ch = charAt(j);
        nextToken();
        return;
      }
      if ((this.ch != ' ') && (this.ch != '\n') && (this.ch != '\r') && (this.ch != '\t') && (this.ch != '\f') && (this.ch != '\b')) {
        break;
      }
      int i = 1 + this.bp;
      this.bp = i;
      this.ch = charAt(i);
    }
    throw new JSONException("not match ':' - " + this.ch);
  }
  
  public final void nextTokenWithColon(int paramInt)
  {
    int j;
    if (this.ch == ':')
    {
      j = 1 + this.bp;
      this.bp = j;
    }
    label403:
    int k;
    for (this.ch = charAt(j);; this.ch = charAt(k))
    {
      if (paramInt == 2)
      {
        if ((this.ch >= '0') && (this.ch <= '9'))
        {
          this.sp = 0;
          this.pos = this.bp;
          scanNumber();
          return;
          if (isWhitespace(this.ch))
          {
            int i = 1 + this.bp;
            this.bp = i;
            this.ch = charAt(i);
            break;
          }
          throw new JSONException("not match ':', actual " + this.ch);
        }
        if (this.ch != '"') {
          break label403;
        }
        this.sp = 0;
        this.pos = this.bp;
        scanString();
        return;
      }
      if (paramInt == 4)
      {
        if (this.ch == '"')
        {
          this.sp = 0;
          this.pos = this.bp;
          scanString();
          return;
        }
        if ((this.ch >= '0') && (this.ch <= '9'))
        {
          this.sp = 0;
          this.pos = this.bp;
          scanNumber();
        }
      }
      else if (paramInt == 12)
      {
        if (this.ch == '{')
        {
          this.token = 12;
          int i2 = 1 + this.bp;
          this.bp = i2;
          this.ch = charAt(i2);
          return;
        }
        if (this.ch == '[')
        {
          this.token = 14;
          int i1 = 1 + this.bp;
          this.bp = i1;
          this.ch = charAt(i1);
        }
      }
      else if (paramInt == 14)
      {
        if (this.ch == '[')
        {
          this.token = 14;
          int n = 1 + this.bp;
          this.bp = n;
          this.ch = charAt(n);
          return;
        }
        if (this.ch == '{')
        {
          this.token = 12;
          int m = 1 + this.bp;
          this.bp = m;
          this.ch = charAt(m);
          return;
        }
      }
      if (!isWhitespace(this.ch)) {
        break label440;
      }
      k = 1 + this.bp;
      this.bp = k;
    }
    label440:
    nextToken();
  }
  
  public final String numberString()
  {
    int i = charAt(-1 + (this.np + this.sp));
    int j = this.sp;
    if ((i == 76) || (i == 83) || (i == 66) || (i == 70) || (i == 68)) {
      j--;
    }
    return this.text.substring(this.np, j + this.np);
  }
  
  public final int pos()
  {
    return this.pos;
  }
  
  public void reset(int paramInt1, char paramChar, int paramInt2)
  {
    this.bp = paramInt1;
    this.ch = paramChar;
    this.token = paramInt2;
    this.resetFlag = true;
    this.resetCount = (1 + this.resetCount);
  }
  
  public final void resetStringPosition()
  {
    this.sp = 0;
  }
  
  public void scanFalse()
  {
    int i = this.bp;
    this.bp = (i + 1);
    if (charAt(i) != 'f') {
      throw new JSONException("error parse false");
    }
    int j = this.bp;
    this.bp = (j + 1);
    if (charAt(j) != 'a') {
      throw new JSONException("error parse false");
    }
    int k = this.bp;
    this.bp = (k + 1);
    if (charAt(k) != 'l') {
      throw new JSONException("error parse false");
    }
    int m = this.bp;
    this.bp = (m + 1);
    if (charAt(m) != 's') {
      throw new JSONException("error parse false");
    }
    int n = this.bp;
    this.bp = (n + 1);
    if (charAt(n) != 'e') {
      throw new JSONException("error parse false");
    }
    this.ch = charAt(this.bp);
    if ((this.ch == ' ') || (this.ch == ',') || (this.ch == '}') || (this.ch == ']') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\032') || (this.ch == '\f') || (this.ch == '\b'))
    {
      this.token = 7;
      return;
    }
    throw new JSONException("scan false error");
  }
  
  public boolean scanFieldBoolean(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      return false;
    }
    int i = this.bp + paramArrayOfChar.length;
    int j = i + 1;
    int k = charAt(i);
    int i3;
    boolean bool;
    if (k == 116)
    {
      int i10 = j + 1;
      if (charAt(j) != 'r')
      {
        this.matchStat = -1;
        return false;
      }
      int i11 = i10 + 1;
      if (charAt(i10) != 'u')
      {
        this.matchStat = -1;
        return false;
      }
      int i12 = i11 + 1;
      if (charAt(i11) != 'e')
      {
        this.matchStat = -1;
        return false;
      }
      this.bp = i12;
      i3 = charAt(this.bp);
      bool = true;
    }
    while (i3 == 44)
    {
      int i9 = 1 + this.bp;
      this.bp = i9;
      charAt(i9);
      this.matchStat = 3;
      this.token = 16;
      return bool;
      if (k == 102)
      {
        int m = j + 1;
        if (charAt(j) != 'a')
        {
          this.matchStat = -1;
          return false;
        }
        int n = m + 1;
        if (charAt(m) != 'l')
        {
          this.matchStat = -1;
          return false;
        }
        int i1 = n + 1;
        if (charAt(n) != 's')
        {
          this.matchStat = -1;
          return false;
        }
        int i2 = i1 + 1;
        if (charAt(i1) != 'e')
        {
          this.matchStat = -1;
          return false;
        }
        this.bp = i2;
        i3 = charAt(this.bp);
        bool = false;
      }
      else
      {
        this.matchStat = -1;
        return false;
      }
    }
    if (i3 == 125)
    {
      int i4 = 1 + this.bp;
      this.bp = i4;
      int i5 = charAt(i4);
      if (i5 == 44)
      {
        this.token = 16;
        int i8 = 1 + this.bp;
        this.bp = i8;
        this.ch = charAt(i8);
      }
      for (;;)
      {
        this.matchStat = 4;
        return bool;
        if (i5 == 93)
        {
          this.token = 15;
          int i7 = 1 + this.bp;
          this.bp = i7;
          this.ch = charAt(i7);
        }
        else if (i5 == 125)
        {
          this.token = 13;
          int i6 = 1 + this.bp;
          this.bp = i6;
          this.ch = charAt(i6);
        }
        else
        {
          if (i5 != 26) {
            break;
          }
          this.token = 20;
        }
      }
      this.matchStat = -1;
      return false;
    }
    this.matchStat = -1;
    return false;
  }
  
  public double scanFieldDouble(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      return 0.0D;
    }
    int i = this.bp + paramArrayOfChar.length;
    int j = i + 1;
    int k = charAt(i);
    int i2;
    double d;
    if ((k >= 48) && (k <= 57))
    {
      int m = j - 1;
      int i1;
      for (int n = j;; n = i1)
      {
        i1 = n + 1;
        i2 = charAt(n);
        if ((i2 < 48) || (i2 > 57)) {
          break;
        }
      }
      if (i2 == 46)
      {
        int i10 = i1 + 1;
        int i11 = charAt(i1);
        if ((i11 >= 48) && (i11 <= 57)) {
          for (;;)
          {
            i1 = i10 + 1;
            i2 = charAt(i10);
            if ((i2 < 48) || (i2 > 57)) {
              break;
            }
            i10 = i1;
          }
        }
        this.matchStat = -1;
        return 0.0D;
      }
      int i3 = i1;
      this.bp = (i3 - 1);
      d = Double.parseDouble(this.text.substring(m, i3 - 1));
      if (i2 == 44)
      {
        int i9 = 1 + this.bp;
        this.bp = i9;
        charAt(i9);
        this.matchStat = 3;
        this.token = 16;
        return d;
      }
    }
    else
    {
      this.matchStat = -1;
      return 0.0D;
    }
    if (i2 == 125)
    {
      int i4 = 1 + this.bp;
      this.bp = i4;
      int i5 = charAt(i4);
      if (i5 == 44)
      {
        this.token = 16;
        int i8 = 1 + this.bp;
        this.bp = i8;
        this.ch = charAt(i8);
      }
      for (;;)
      {
        this.matchStat = 4;
        return d;
        if (i5 == 93)
        {
          this.token = 15;
          int i7 = 1 + this.bp;
          this.bp = i7;
          this.ch = charAt(i7);
        }
        else if (i5 == 125)
        {
          this.token = 13;
          int i6 = 1 + this.bp;
          this.bp = i6;
          this.ch = charAt(i6);
        }
        else
        {
          if (i5 != 26) {
            break;
          }
          this.token = 20;
        }
      }
      this.matchStat = -1;
      return 0.0D;
    }
    this.matchStat = -1;
    return 0.0D;
  }
  
  public float scanFieldFloat(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      return 0.0F;
    }
    int i = this.bp + paramArrayOfChar.length;
    int j = i + 1;
    int k = charAt(i);
    int i2;
    float f;
    if ((k >= 48) && (k <= 57))
    {
      int m = j - 1;
      int i1;
      for (int n = j;; n = i1)
      {
        i1 = n + 1;
        i2 = charAt(n);
        if ((i2 < 48) || (i2 > 57)) {
          break;
        }
      }
      if (i2 == 46)
      {
        int i10 = i1 + 1;
        int i11 = charAt(i1);
        if ((i11 >= 48) && (i11 <= 57)) {
          for (;;)
          {
            i1 = i10 + 1;
            i2 = charAt(i10);
            if ((i2 < 48) || (i2 > 57)) {
              break;
            }
            i10 = i1;
          }
        }
        this.matchStat = -1;
        return 0.0F;
      }
      int i3 = i1;
      this.bp = (i3 - 1);
      f = Float.parseFloat(this.text.substring(m, i3 - 1));
      if (i2 == 44)
      {
        int i9 = 1 + this.bp;
        this.bp = i9;
        charAt(i9);
        this.matchStat = 3;
        this.token = 16;
        return f;
      }
    }
    else
    {
      this.matchStat = -1;
      return 0.0F;
    }
    if (i2 == 125)
    {
      int i4 = 1 + this.bp;
      this.bp = i4;
      int i5 = charAt(i4);
      if (i5 == 44)
      {
        this.token = 16;
        int i8 = 1 + this.bp;
        this.bp = i8;
        this.ch = charAt(i8);
      }
      for (;;)
      {
        this.matchStat = 4;
        return f;
        if (i5 == 93)
        {
          this.token = 15;
          int i7 = 1 + this.bp;
          this.bp = i7;
          this.ch = charAt(i7);
        }
        else if (i5 == 125)
        {
          this.token = 13;
          int i6 = 1 + this.bp;
          this.bp = i6;
          this.ch = charAt(i6);
        }
        else
        {
          if (i5 != 26) {
            break;
          }
          this.token = 20;
        }
      }
      this.matchStat = -1;
      return 0.0F;
    }
    this.matchStat = -1;
    return 0.0F;
  }
  
  public int scanFieldInt(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    int m;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      m = 0;
    }
    int i2;
    do
    {
      return m;
      int i = this.bp + paramArrayOfChar.length;
      int j = i + 1;
      int k = charAt(i);
      if ((k >= 48) && (k <= 57))
      {
        m = digits[k];
        int i1;
        for (int n = j;; n = i1)
        {
          i1 = n + 1;
          i2 = charAt(n);
          if ((i2 < 48) || (i2 > 57)) {
            break;
          }
          m = m * 10 + digits[i2];
        }
        if (i2 == 46)
        {
          this.matchStat = -1;
          return 0;
        }
        this.bp = (i1 - 1);
        if (m < 0)
        {
          this.matchStat = -1;
          return 0;
        }
      }
      else
      {
        this.matchStat = -1;
        return 0;
      }
      if (i2 == 44)
      {
        int i8 = 1 + this.bp;
        this.bp = i8;
        charAt(i8);
        this.matchStat = 3;
        this.token = 16;
        return m;
      }
    } while (i2 != 125);
    int i3 = 1 + this.bp;
    this.bp = i3;
    int i4 = charAt(i3);
    if (i4 == 44)
    {
      this.token = 16;
      int i7 = 1 + this.bp;
      this.bp = i7;
      this.ch = charAt(i7);
    }
    for (;;)
    {
      this.matchStat = 4;
      return m;
      if (i4 == 93)
      {
        this.token = 15;
        int i6 = 1 + this.bp;
        this.bp = i6;
        this.ch = charAt(i6);
      }
      else if (i4 == 125)
      {
        this.token = 13;
        int i5 = 1 + this.bp;
        this.bp = i5;
        this.ch = charAt(i5);
      }
      else
      {
        if (i4 != 26) {
          break;
        }
        this.token = 20;
      }
    }
    this.matchStat = -1;
    return 0;
  }
  
  public long scanFieldLong(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      return 0L;
    }
    int i = this.bp + paramArrayOfChar.length;
    int j = i + 1;
    int k = charAt(i);
    long l;
    int i1;
    if ((k >= 48) && (k <= 57))
    {
      l = digits[k];
      int n;
      for (int m = j;; m = n)
      {
        n = m + 1;
        i1 = charAt(m);
        if ((i1 < 48) || (i1 > 57)) {
          break;
        }
        l = 10L * l + digits[i1];
      }
      if (i1 == 46)
      {
        this.token = -1;
        return 0L;
      }
      this.bp = (n - 1);
      if (l < 0L)
      {
        this.matchStat = -1;
        return 0L;
      }
    }
    else
    {
      this.matchStat = -1;
      return 0L;
    }
    if (i1 == 44)
    {
      int i7 = 1 + this.bp;
      this.bp = i7;
      charAt(i7);
      this.matchStat = 3;
      this.token = 16;
      return l;
    }
    if (i1 == 125)
    {
      int i2 = 1 + this.bp;
      this.bp = i2;
      int i3 = charAt(i2);
      if (i3 == 44)
      {
        this.token = 16;
        int i6 = 1 + this.bp;
        this.bp = i6;
        this.ch = charAt(i6);
      }
      for (;;)
      {
        this.matchStat = 4;
        return l;
        if (i3 == 93)
        {
          this.token = 15;
          int i5 = 1 + this.bp;
          this.bp = i5;
          this.ch = charAt(i5);
        }
        else if (i3 == 125)
        {
          this.token = 13;
          int i4 = 1 + this.bp;
          this.bp = i4;
          this.ch = charAt(i4);
        }
        else
        {
          if (i3 != 26) {
            break;
          }
          this.token = 20;
        }
      }
      this.matchStat = -1;
      return 0L;
    }
    this.matchStat = -1;
    return 0L;
  }
  
  public String scanFieldString(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      return stringDefaultValue();
    }
    int i = this.bp + paramArrayOfChar.length;
    int j = i + 1;
    if (charAt(i) != '"')
    {
      this.matchStat = -1;
      return stringDefaultValue();
    }
    int k = this.text.indexOf('"', j);
    if (k == -1) {
      throw new JSONException("unclosed str");
    }
    String str = subString(j, k - j);
    for (int m = 0;; m++)
    {
      int n = str.length();
      int i1 = 0;
      if (m < n)
      {
        if (str.charAt(m) == '\\') {
          i1 = 1;
        }
      }
      else
      {
        if (i1 == 0) {
          break;
        }
        this.matchStat = -1;
        return stringDefaultValue();
      }
    }
    this.bp = (k + 1);
    char c = charAt(this.bp);
    this.ch = c;
    if (c == ',')
    {
      int i7 = 1 + this.bp;
      this.bp = i7;
      this.ch = charAt(i7);
      this.matchStat = 3;
      return str;
    }
    if (c == '}')
    {
      int i2 = 1 + this.bp;
      this.bp = i2;
      int i3 = charAt(i2);
      if (i3 == 44)
      {
        this.token = 16;
        int i6 = 1 + this.bp;
        this.bp = i6;
        this.ch = charAt(i6);
      }
      for (;;)
      {
        this.matchStat = 4;
        return str;
        if (i3 == 93)
        {
          this.token = 15;
          int i5 = 1 + this.bp;
          this.bp = i5;
          this.ch = charAt(i5);
        }
        else if (i3 == 125)
        {
          this.token = 13;
          int i4 = 1 + this.bp;
          this.bp = i4;
          this.ch = charAt(i4);
        }
        else
        {
          if (i3 != 26) {
            break;
          }
          this.token = 20;
        }
      }
      this.matchStat = -1;
      return stringDefaultValue();
    }
    this.matchStat = -1;
    return stringDefaultValue();
  }
  
  public ArrayList<String> scanFieldStringArray(char[] paramArrayOfChar)
  {
    return (ArrayList)scanFieldStringArray(paramArrayOfChar, null);
  }
  
  public Collection<String> scanFieldStringArray(char[] paramArrayOfChar, Class<?> paramClass)
  {
    this.matchStat = 0;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      return null;
    }
    Object localObject;
    if (paramClass.isAssignableFrom(HashSet.class)) {
      localObject = new HashSet();
    }
    int j;
    for (;;)
    {
      int i = this.bp + paramArrayOfChar.length;
      j = i + 1;
      if (charAt(i) != '[')
      {
        this.matchStat = -1;
        return null;
        if (paramClass.isAssignableFrom(ArrayList.class))
        {
          localObject = new ArrayList();
          continue;
        }
        try
        {
          localObject = (Collection)paramClass.newInstance();
        }
        catch (Exception localException)
        {
          throw new JSONException(localException.getMessage(), localException);
        }
      }
    }
    int k = j + 1;
    int m = charAt(j);
    if (m != 34)
    {
      this.matchStat = -1;
      return null;
    }
    int n = k;
    for (;;)
    {
      int i1 = k + 1;
      int i2 = charAt(k);
      int i3;
      int i4;
      if (i2 == 34)
      {
        ((Collection)localObject).add(this.text.substring(n, i1 - 1));
        i3 = i1 + 1;
        i4 = charAt(i1);
        if (i4 != 44) {
          break label257;
        }
        int i10 = i3 + 1;
        m = charAt(i3);
        k = i10;
        break;
      }
      if (i2 == 92)
      {
        this.matchStat = -1;
        return null;
        label257:
        int i6;
        if (i4 == 93)
        {
          int i5 = i3 + 1;
          i6 = charAt(i3);
          this.bp = i5;
          if (i6 == 44)
          {
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return (Collection<String>)localObject;
          }
        }
        else
        {
          this.matchStat = -1;
          return null;
        }
        if (i6 == 125)
        {
          char c = charAt(this.bp);
          if (c == ',')
          {
            this.token = 16;
            int i9 = 1 + this.bp;
            this.bp = i9;
            this.ch = charAt(i9);
          }
          for (;;)
          {
            this.matchStat = 4;
            return (Collection<String>)localObject;
            if (c == ']')
            {
              this.token = 15;
              int i8 = 1 + this.bp;
              this.bp = i8;
              this.ch = charAt(i8);
            }
            else if (c == '}')
            {
              this.token = 13;
              int i7 = 1 + this.bp;
              this.bp = i7;
              this.ch = charAt(i7);
            }
            else
            {
              if (c != '\032') {
                break;
              }
              this.token = 20;
              this.ch = c;
            }
          }
          this.matchStat = -1;
          return null;
        }
        this.matchStat = -1;
        return null;
      }
      k = i1;
    }
  }
  
  public String scanFieldSymbol(char[] paramArrayOfChar, SymbolTable paramSymbolTable)
  {
    this.matchStat = 0;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      return null;
    }
    int i = this.bp + paramArrayOfChar.length;
    int j = i + 1;
    if (charAt(i) != '"')
    {
      this.matchStat = -1;
      return null;
    }
    int k = 0;
    int n;
    for (int m = j;; m = n)
    {
      n = m + 1;
      int i1 = charAt(m);
      char c;
      String str;
      if (i1 == 34)
      {
        this.bp = n;
        c = charAt(this.bp);
        this.ch = c;
        str = paramSymbolTable.addSymbol(this.text, j, -1 + (n - j), k);
        if (c == ',')
        {
          int i7 = 1 + this.bp;
          this.bp = i7;
          this.ch = charAt(i7);
          this.matchStat = 3;
          return str;
        }
      }
      else
      {
        k = i1 + k * 31;
        if (i1 != 92) {
          continue;
        }
        this.matchStat = -1;
        return null;
      }
      if (c == '}')
      {
        int i2 = 1 + this.bp;
        this.bp = i2;
        int i3 = charAt(i2);
        if (i3 == 44)
        {
          this.token = 16;
          int i6 = 1 + this.bp;
          this.bp = i6;
          this.ch = charAt(i6);
        }
        for (;;)
        {
          this.matchStat = 4;
          return str;
          if (i3 == 93)
          {
            this.token = 15;
            int i5 = 1 + this.bp;
            this.bp = i5;
            this.ch = charAt(i5);
          }
          else if (i3 == 125)
          {
            this.token = 13;
            int i4 = 1 + this.bp;
            this.bp = i4;
            this.ch = charAt(i4);
          }
          else
          {
            if (i3 != 26) {
              break;
            }
            this.token = 20;
          }
        }
        this.matchStat = -1;
        return null;
      }
      this.matchStat = -1;
      return null;
    }
  }
  
  public boolean scanISO8601DateIfMatch()
  {
    int i = this.text.length() - this.bp;
    if (i < this.ISO8601_LEN_0) {
      return false;
    }
    int j = charAt(this.bp);
    int k = charAt(1 + this.bp);
    int m = charAt(2 + this.bp);
    int n = charAt(3 + this.bp);
    if ((j != 49) && (j != 50)) {
      return false;
    }
    if ((k < 48) || (k > 57)) {
      return false;
    }
    if ((m < 48) || (m > 57)) {
      return false;
    }
    if ((n < 48) || (n > 57)) {
      return false;
    }
    if (charAt(4 + this.bp) != '-') {
      return false;
    }
    int i1 = charAt(5 + this.bp);
    int i2 = charAt(6 + this.bp);
    if (i1 == 48)
    {
      if ((i2 < 49) || (i2 > 57)) {
        return false;
      }
    }
    else if (i1 == 49)
    {
      if ((i2 != 48) && (i2 != 49) && (i2 != 50)) {
        return false;
      }
    }
    else {
      return false;
    }
    if (charAt(7 + this.bp) != '-') {
      return false;
    }
    int i3 = charAt(8 + this.bp);
    int i4 = charAt(9 + this.bp);
    if (i3 == 48)
    {
      if ((i4 < 49) || (i4 > 57)) {
        return false;
      }
    }
    else if ((i3 == 49) || (i3 == 50))
    {
      if ((i4 < 48) || (i4 > 57)) {
        return false;
      }
    }
    else if (i3 == 51)
    {
      if ((i4 != 48) && (i4 != 49)) {
        return false;
      }
    }
    else {
      return false;
    }
    Locale localLocale = Locale.getDefault();
    this.calendar = Calendar.getInstance(TimeZone.getDefault(), localLocale);
    int i5 = 1000 * digits[j] + 100 * digits[k] + 10 * digits[m] + digits[n];
    int i6 = -1 + (10 * digits[i1] + digits[i2]);
    int i7 = 10 * digits[i3] + digits[i4];
    this.calendar.set(1, i5);
    this.calendar.set(2, i6);
    this.calendar.set(5, i7);
    int i8 = charAt(10 + this.bp);
    if (i8 == 84)
    {
      if (i < this.ISO8601_LEN_1) {
        return false;
      }
    }
    else
    {
      if ((i8 == 34) || (i8 == 26))
      {
        this.calendar.set(11, 0);
        this.calendar.set(12, 0);
        this.calendar.set(13, 0);
        this.calendar.set(14, 0);
        int i9 = 10 + this.bp;
        this.bp = i9;
        this.ch = charAt(i9);
        this.token = 5;
        return true;
      }
      return false;
    }
    int i10 = charAt(11 + this.bp);
    int i11 = charAt(12 + this.bp);
    if (i10 == 48)
    {
      if ((i11 < 48) || (i11 > 57)) {
        return false;
      }
    }
    else if (i10 == 49)
    {
      if ((i11 < 48) || (i11 > 57)) {
        return false;
      }
    }
    else if (i10 == 50)
    {
      if ((i11 < 48) || (i11 > 52)) {
        return false;
      }
    }
    else {
      return false;
    }
    if (charAt(13 + this.bp) != ':') {
      return false;
    }
    int i12 = charAt(14 + this.bp);
    int i13 = charAt(15 + this.bp);
    if ((i12 >= 48) && (i12 <= 53))
    {
      if ((i13 < 48) || (i13 > 57)) {
        return false;
      }
    }
    else if (i12 == 54)
    {
      if (i13 != 48) {
        return false;
      }
    }
    else {
      return false;
    }
    if (charAt(16 + this.bp) != ':') {
      return false;
    }
    int i14 = charAt(17 + this.bp);
    int i15 = charAt(18 + this.bp);
    if ((i14 >= 48) && (i14 <= 53))
    {
      if ((i15 < 48) || (i15 > 57)) {
        return false;
      }
    }
    else if (i14 == 54)
    {
      if (i15 != 48) {
        return false;
      }
    }
    else {
      return false;
    }
    int i16 = 10 * digits[i10] + digits[i11];
    int i17 = 10 * digits[i12] + digits[i13];
    int i18 = 10 * digits[i14] + digits[i15];
    this.calendar.set(11, i16);
    this.calendar.set(12, i17);
    this.calendar.set(13, i18);
    if (charAt(19 + this.bp) == '.')
    {
      if (i < this.ISO8601_LEN_2) {
        return false;
      }
    }
    else
    {
      this.calendar.set(14, 0);
      int i19 = 19 + this.bp;
      this.bp = i19;
      this.ch = charAt(i19);
      this.token = 5;
      return true;
    }
    int i20 = charAt(20 + this.bp);
    int i21 = charAt(21 + this.bp);
    int i22 = charAt(22 + this.bp);
    if ((i20 < 48) || (i20 > 57)) {
      return false;
    }
    if ((i21 < 48) || (i21 > 57)) {
      return false;
    }
    if ((i22 < 48) || (i22 > 57)) {
      return false;
    }
    int i23 = 100 * digits[i20] + 10 * digits[i21] + digits[i22];
    this.calendar.set(14, i23);
    int i24 = 23 + this.bp;
    this.bp = i24;
    this.ch = charAt(i24);
    this.token = 5;
    return true;
  }
  
  public void scanIdent()
  {
    this.np = (-1 + this.bp);
    this.hasSpecial = false;
    do
    {
      this.sp = (1 + this.sp);
      int i = 1 + this.bp;
      this.bp = i;
      this.ch = charAt(i);
    } while (Character.isLetterOrDigit(this.ch));
    String str = stringVal();
    Integer localInteger = this.keywods.getKeyword(str);
    if (localInteger != null)
    {
      this.token = localInteger.intValue();
      return;
    }
    this.token = 18;
  }
  
  public void scanNullOrNew()
  {
    int i = this.bp;
    this.bp = (i + 1);
    if (charAt(i) != 'n') {
      throw new JSONException("error parse null or new");
    }
    if (charAt(this.bp) == 'u')
    {
      this.bp = (1 + this.bp);
      int k = this.bp;
      this.bp = (k + 1);
      if (charAt(k) != 'l') {
        throw new JSONException("error parse true");
      }
      int m = this.bp;
      this.bp = (m + 1);
      if (charAt(m) != 'l') {
        throw new JSONException("error parse true");
      }
      this.ch = charAt(this.bp);
      if ((this.ch == ' ') || (this.ch == ',') || (this.ch == '}') || (this.ch == ']') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\032') || (this.ch == '\f') || (this.ch == '\b'))
      {
        this.token = 8;
        return;
      }
      throw new JSONException("scan true error");
    }
    if (charAt(this.bp) != 'e') {
      throw new JSONException("error parse e");
    }
    this.bp = (1 + this.bp);
    int j = this.bp;
    this.bp = (j + 1);
    if (charAt(j) != 'w') {
      throw new JSONException("error parse w");
    }
    this.ch = charAt(this.bp);
    if ((this.ch == ' ') || (this.ch == ',') || (this.ch == '}') || (this.ch == ']') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\032') || (this.ch == '\f') || (this.ch == '\b'))
    {
      this.token = 9;
      return;
    }
    throw new JSONException("scan true error");
  }
  
  public void scanNumber()
  {
    this.np = this.bp;
    int i10;
    if (this.ch == '-')
    {
      this.sp = (1 + this.sp);
      i10 = 1 + this.bp;
      this.bp = i10;
    }
    int i9;
    for (this.ch = charAt(i10); (this.ch >= '0') && (this.ch <= '9'); this.ch = charAt(i9))
    {
      this.sp = (1 + this.sp);
      i9 = 1 + this.bp;
      this.bp = i9;
    }
    int i = this.ch;
    int j = 0;
    if (i == 46)
    {
      this.sp = (1 + this.sp);
      int i7 = 1 + this.bp;
      this.bp = i7;
      this.ch = charAt(i7);
      j = 1;
      while ((this.ch >= '0') && (this.ch <= '9'))
      {
        this.sp = (1 + this.sp);
        int i8 = 1 + this.bp;
        this.bp = i8;
        this.ch = charAt(i8);
      }
    }
    if (this.ch == 'L')
    {
      this.sp = (1 + this.sp);
      int i6 = 1 + this.bp;
      this.bp = i6;
      this.ch = charAt(i6);
    }
    while (j != 0)
    {
      this.token = 3;
      return;
      if (this.ch == 'S')
      {
        this.sp = (1 + this.sp);
        int i5 = 1 + this.bp;
        this.bp = i5;
        this.ch = charAt(i5);
      }
      else if (this.ch == 'B')
      {
        this.sp = (1 + this.sp);
        int i4 = 1 + this.bp;
        this.bp = i4;
        this.ch = charAt(i4);
      }
      else if (this.ch == 'F')
      {
        this.sp = (1 + this.sp);
        int i3 = 1 + this.bp;
        this.bp = i3;
        this.ch = charAt(i3);
        j = 1;
      }
      else if (this.ch == 'D')
      {
        this.sp = (1 + this.sp);
        int i2 = 1 + this.bp;
        this.bp = i2;
        this.ch = charAt(i2);
        j = 1;
      }
      else if ((this.ch == 'e') || (this.ch == 'E'))
      {
        this.sp = (1 + this.sp);
        int k = 1 + this.bp;
        this.bp = k;
        this.ch = charAt(k);
        int m;
        if ((this.ch == '+') || (this.ch == '-'))
        {
          this.sp = (1 + this.sp);
          m = 1 + this.bp;
          this.bp = m;
        }
        int i1;
        for (this.ch = charAt(m); (this.ch >= '0') && (this.ch <= '9'); this.ch = charAt(i1))
        {
          this.sp = (1 + this.sp);
          i1 = 1 + this.bp;
          this.bp = i1;
        }
        if ((this.ch == 'D') || (this.ch == 'F'))
        {
          int n = 1 + this.bp;
          this.bp = n;
          this.ch = charAt(n);
        }
        j = 1;
      }
    }
    this.token = 2;
  }
  
  public void scanSet()
  {
    int i = this.bp;
    this.bp = (i + 1);
    if (charAt(i) != 'S') {
      throw new JSONException("error parse true");
    }
    int j = this.bp;
    this.bp = (j + 1);
    if (charAt(j) != 'e') {
      throw new JSONException("error parse true");
    }
    int k = this.bp;
    this.bp = (k + 1);
    if (charAt(k) != 't') {
      throw new JSONException("error parse true");
    }
    this.ch = charAt(this.bp);
    if ((this.ch == ' ') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\f') || (this.ch == '\b') || (this.ch == '[') || (this.ch == '('))
    {
      this.token = 21;
      return;
    }
    throw new JSONException("scan set error");
  }
  
  public final void scanString()
  {
    this.np = this.bp;
    this.hasSpecial = false;
    for (;;)
    {
      int i = 1 + this.bp;
      this.bp = i;
      char c1 = charAt(i);
      if (c1 == '"')
      {
        this.token = 4;
        int i11 = 1 + this.bp;
        this.bp = i11;
        this.ch = charAt(i11);
        return;
      }
      if (c1 == '\\')
      {
        if (!this.hasSpecial)
        {
          this.hasSpecial = true;
          if (this.sp >= this.sbuf.length)
          {
            int i10 = 2 * this.sbuf.length;
            if (this.sp > i10) {
              i10 = this.sp;
            }
            char[] arrayOfChar2 = new char[i10];
            System.arraycopy(this.sbuf, 0, arrayOfChar2, 0, this.sbuf.length);
            this.sbuf = arrayOfChar2;
          }
          this.text.getChars(1 + this.np, 1 + this.np + this.sp, this.sbuf, 0);
        }
        int k = 1 + this.bp;
        this.bp = k;
        char c2 = charAt(k);
        switch (c2)
        {
        default: 
          this.ch = c2;
          throw new JSONException("unclosed string : " + c2);
        case '"': 
          putChar('"');
          break;
        case '\\': 
          putChar('\\');
          break;
        case '/': 
          putChar('/');
          break;
        case 'b': 
          putChar('\b');
          break;
        case 'F': 
        case 'f': 
          putChar('\f');
          break;
        case 'n': 
          putChar('\n');
          break;
        case 'r': 
          putChar('\r');
          break;
        case 't': 
          putChar('\t');
          break;
        case 'x': 
          int i6 = 1 + this.bp;
          this.bp = i6;
          int i7 = charAt(i6);
          int i8 = 1 + this.bp;
          this.bp = i8;
          int i9 = charAt(i8);
          putChar((char)(16 * digits[i7] + digits[i9]));
          break;
        case 'u': 
          int m = 1 + this.bp;
          this.bp = m;
          int n = charAt(m);
          int i1 = 1 + this.bp;
          this.bp = i1;
          int i2 = charAt(i1);
          int i3 = 1 + this.bp;
          this.bp = i3;
          int i4 = charAt(i3);
          int i5 = 1 + this.bp;
          this.bp = i5;
          putChar((char)Integer.parseInt(new String(new char[] { n, i2, i4, charAt(i5) }), 16));
          break;
        }
      }
      else if (!this.hasSpecial)
      {
        this.sp = (1 + this.sp);
      }
      else if (this.sp == this.sbuf.length)
      {
        putChar(c1);
      }
      else
      {
        char[] arrayOfChar1 = this.sbuf;
        int j = this.sp;
        this.sp = (j + 1);
        arrayOfChar1[j] = c1;
      }
    }
  }
  
  public final void scanStringSingleQuote()
  {
    this.np = this.bp;
    this.hasSpecial = false;
    for (;;)
    {
      int i = 1 + this.bp;
      this.bp = i;
      char c1 = charAt(i);
      if (c1 == '\'')
      {
        this.token = 4;
        int i10 = 1 + this.bp;
        this.bp = i10;
        this.ch = charAt(i10);
        return;
      }
      if (c1 == '\032') {
        throw new JSONException("unclosed single-quote string");
      }
      if (c1 == '\\')
      {
        if (!this.hasSpecial)
        {
          this.hasSpecial = true;
          if (this.sp > this.sbuf.length)
          {
            char[] arrayOfChar2 = new char[2 * this.sp];
            System.arraycopy(this.sbuf, 0, arrayOfChar2, 0, this.sbuf.length);
            this.sbuf = arrayOfChar2;
          }
          this.text.getChars(1 + this.np, 1 + this.np + this.sp, this.sbuf, 0);
        }
        int k = 1 + this.bp;
        this.bp = k;
        char c2 = charAt(k);
        switch (c2)
        {
        default: 
          this.ch = c2;
          throw new JSONException("unclosed single-quote string");
        case '"': 
          putChar('"');
          break;
        case '\\': 
          putChar('\\');
          break;
        case '/': 
          putChar('/');
          break;
        case '\'': 
          putChar('\'');
          break;
        case 'b': 
          putChar('\b');
          break;
        case 'F': 
        case 'f': 
          putChar('\f');
          break;
        case 'n': 
          putChar('\n');
          break;
        case 'r': 
          putChar('\r');
          break;
        case 't': 
          putChar('\t');
          break;
        case 'x': 
          int i6 = 1 + this.bp;
          this.bp = i6;
          int i7 = charAt(i6);
          int i8 = 1 + this.bp;
          this.bp = i8;
          int i9 = charAt(i8);
          putChar((char)(16 * digits[i7] + digits[i9]));
          break;
        case 'u': 
          int m = 1 + this.bp;
          this.bp = m;
          int n = charAt(m);
          int i1 = 1 + this.bp;
          this.bp = i1;
          int i2 = charAt(i1);
          int i3 = 1 + this.bp;
          this.bp = i3;
          int i4 = charAt(i3);
          int i5 = 1 + this.bp;
          this.bp = i5;
          putChar((char)Integer.parseInt(new String(new char[] { n, i2, i4, charAt(i5) }), 16));
          break;
        }
      }
      else if (!this.hasSpecial)
      {
        this.sp = (1 + this.sp);
      }
      else if (this.sp == this.sbuf.length)
      {
        putChar(c1);
      }
      else
      {
        char[] arrayOfChar1 = this.sbuf;
        int j = this.sp;
        this.sp = (j + 1);
        arrayOfChar1[j] = c1;
      }
    }
  }
  
  public String scanSymbol(SymbolTable paramSymbolTable)
  {
    skipWhitespace();
    if (this.ch == '"') {
      return scanSymbol(paramSymbolTable, '"');
    }
    if (this.ch == '\'')
    {
      if (!isEnabled(Feature.AllowSingleQuotes)) {
        throw new JSONException("syntax error");
      }
      return scanSymbol(paramSymbolTable, '\'');
    }
    if (this.ch == '}')
    {
      int j = 1 + this.bp;
      this.bp = j;
      this.ch = charAt(j);
      this.token = 13;
      return null;
    }
    if (this.ch == ',')
    {
      int i = 1 + this.bp;
      this.bp = i;
      this.ch = charAt(i);
      this.token = 16;
      return null;
    }
    if (this.ch == '\032')
    {
      this.token = 20;
      return null;
    }
    if (!isEnabled(Feature.AllowUnQuotedFieldNames)) {
      throw new JSONException("syntax error");
    }
    return scanSymbolUnQuoted(paramSymbolTable);
  }
  
  public final String scanSymbol(SymbolTable paramSymbolTable, char paramChar)
  {
    int i = 0;
    this.np = this.bp;
    this.sp = 0;
    int j = 0;
    for (;;)
    {
      int k = 1 + this.bp;
      this.bp = k;
      char c1 = charAt(k);
      if (c1 == paramChar)
      {
        this.token = 4;
        int i10 = 1 + this.bp;
        this.bp = i10;
        this.ch = charAt(i10);
        if (j != 0) {
          break;
        }
        return paramSymbolTable.addSymbol(this.text, 1 + this.np, this.sp, i);
      }
      if (c1 == '\032') {
        throw new JSONException("unclosed.str");
      }
      if (c1 == '\\')
      {
        if (j == 0)
        {
          j = 1;
          if (this.sp >= this.sbuf.length)
          {
            int i9 = 2 * this.sbuf.length;
            if (this.sp > i9) {
              i9 = this.sp;
            }
            char[] arrayOfChar2 = new char[i9];
            System.arraycopy(this.sbuf, 0, arrayOfChar2, 0, this.sbuf.length);
            this.sbuf = arrayOfChar2;
          }
          this.text.getChars(1 + this.np, 1 + this.np + this.sp, this.sbuf, 0);
        }
        int n = 1 + this.bp;
        this.bp = n;
        char c2 = charAt(n);
        switch (c2)
        {
        default: 
          this.ch = c2;
          throw new JSONException("unclosed.str.lit");
        case '"': 
          i = 34 + i * 31;
          putChar('"');
          break;
        case '\\': 
          i = 92 + i * 31;
          putChar('\\');
          break;
        case '/': 
          i = 47 + i * 31;
          putChar('/');
          break;
        case 'b': 
          i = 8 + i * 31;
          putChar('\b');
          break;
        case 'F': 
        case 'f': 
          i = 12 + i * 31;
          putChar('\f');
          break;
        case 'n': 
          i = 10 + i * 31;
          putChar('\n');
          break;
        case 'r': 
          i = 13 + i * 31;
          putChar('\r');
          break;
        case 't': 
          i = 9 + i * 31;
          putChar('\t');
          break;
        case 'u': 
          int i1 = 1 + this.bp;
          this.bp = i1;
          int i2 = charAt(i1);
          int i3 = 1 + this.bp;
          this.bp = i3;
          int i4 = charAt(i3);
          int i5 = 1 + this.bp;
          this.bp = i5;
          int i6 = charAt(i5);
          int i7 = 1 + this.bp;
          this.bp = i7;
          int i8 = Integer.parseInt(new String(new char[] { i2, i4, i6, charAt(i7) }), 16);
          i = i8 + i * 31;
          putChar((char)i8);
          break;
        }
      }
      else
      {
        i = c1 + i * 31;
        if (j == 0)
        {
          this.sp = (1 + this.sp);
        }
        else if (this.sp == this.sbuf.length)
        {
          putChar(c1);
        }
        else
        {
          char[] arrayOfChar1 = this.sbuf;
          int m = this.sp;
          this.sp = (m + 1);
          arrayOfChar1[m] = c1;
        }
      }
    }
    return paramSymbolTable.addSymbol(this.sbuf, 0, this.sp, i);
  }
  
  public final String scanSymbolUnQuoted(SymbolTable paramSymbolTable)
  {
    boolean[] arrayOfBoolean1 = CharTypes.firstIdentifierFlags;
    int i = this.ch;
    if ((this.ch >= arrayOfBoolean1.length) || (arrayOfBoolean1[i] != 0)) {}
    for (int j = 1; j == 0; j = 0) {
      throw new JSONException("illegal identifier : " + this.ch);
    }
    boolean[] arrayOfBoolean2 = CharTypes.identifierFlags;
    int k = i;
    this.np = this.bp;
    for (this.sp = 1;; this.sp = (1 + this.sp))
    {
      int m = 1 + this.bp;
      this.bp = m;
      int n = charAt(m);
      if ((n < arrayOfBoolean2.length) && (arrayOfBoolean2[n] == 0))
      {
        this.ch = charAt(this.bp);
        this.token = 18;
        if ((this.sp != 4) || (k != 3392903) || (charAt(this.np) != 'n') || (charAt(1 + this.np) != 'u') || (charAt(2 + this.np) != 'l') || (charAt(3 + this.np) != 'l')) {
          break;
        }
        return null;
      }
      k = n + k * 31;
    }
    return this.text.substring(this.np, this.np + this.sp).intern();
  }
  
  public void scanTreeSet()
  {
    int i = this.bp;
    this.bp = (i + 1);
    if (charAt(i) != 'T') {
      throw new JSONException("error parse true");
    }
    int j = this.bp;
    this.bp = (j + 1);
    if (charAt(j) != 'r') {
      throw new JSONException("error parse true");
    }
    int k = this.bp;
    this.bp = (k + 1);
    if (charAt(k) != 'e') {
      throw new JSONException("error parse true");
    }
    int m = this.bp;
    this.bp = (m + 1);
    if (charAt(m) != 'e') {
      throw new JSONException("error parse true");
    }
    int n = this.bp;
    this.bp = (n + 1);
    if (charAt(n) != 'S') {
      throw new JSONException("error parse true");
    }
    int i1 = this.bp;
    this.bp = (i1 + 1);
    if (charAt(i1) != 'e') {
      throw new JSONException("error parse true");
    }
    int i2 = this.bp;
    this.bp = (i2 + 1);
    if (charAt(i2) != 't') {
      throw new JSONException("error parse true");
    }
    this.ch = charAt(this.bp);
    if ((this.ch == ' ') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\f') || (this.ch == '\b') || (this.ch == '[') || (this.ch == '('))
    {
      this.token = 22;
      return;
    }
    throw new JSONException("scan set error");
  }
  
  public void scanTrue()
  {
    int i = this.bp;
    this.bp = (i + 1);
    if (charAt(i) != 't') {
      throw new JSONException("error parse true");
    }
    int j = this.bp;
    this.bp = (j + 1);
    if (charAt(j) != 'r') {
      throw new JSONException("error parse true");
    }
    int k = this.bp;
    this.bp = (k + 1);
    if (charAt(k) != 'u') {
      throw new JSONException("error parse true");
    }
    int m = this.bp;
    this.bp = (m + 1);
    if (charAt(m) != 'e') {
      throw new JSONException("error parse true");
    }
    this.ch = charAt(this.bp);
    if ((this.ch == ' ') || (this.ch == ',') || (this.ch == '}') || (this.ch == ']') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\032') || (this.ch == '\f') || (this.ch == '\b'))
    {
      this.token = 6;
      return;
    }
    throw new JSONException("scan true error");
  }
  
  public int scanType(String paramString)
  {
    int i = -1;
    this.matchStat = 0;
    if (!charArrayCompare(this.text, this.bp, typeFieldName)) {
      i = -2;
    }
    label78:
    int n;
    do
    {
      return i;
      int j = this.bp + typeFieldName.length;
      int k = paramString.length();
      for (int m = 0;; m++)
      {
        if (m >= k) {
          break label78;
        }
        if (paramString.charAt(m) != charAt(j + m)) {
          break;
        }
      }
      n = j + k;
    } while (charAt(n) != '"');
    int i1 = n + 1;
    this.ch = charAt(i1);
    if (this.ch == ',')
    {
      int i2 = i1 + 1;
      this.ch = charAt(i2);
      this.bp = i2;
      this.token = 16;
      return 3;
    }
    if (this.ch == '}')
    {
      i1++;
      this.ch = charAt(i1);
      if (this.ch != ',') {
        break label216;
      }
      this.token = 16;
      i1++;
      this.ch = charAt(i1);
    }
    for (;;)
    {
      this.matchStat = 4;
      this.bp = i1;
      return this.matchStat;
      label216:
      if (this.ch == ']')
      {
        this.token = 15;
        i1++;
        this.ch = charAt(i1);
      }
      else if (this.ch == '}')
      {
        this.token = 13;
        i1++;
        this.ch = charAt(i1);
      }
      else
      {
        if (this.ch != '\032') {
          break;
        }
        this.token = 20;
      }
    }
  }
  
  public void setResetFlag(boolean paramBoolean)
  {
    this.resetFlag = paramBoolean;
  }
  
  public final void skipWhitespace()
  {
    while (whitespaceFlags[this.ch] != 0)
    {
      int i = 1 + this.bp;
      this.bp = i;
      this.ch = charAt(i);
    }
  }
  
  public String stringDefaultValue()
  {
    if (isEnabled(Feature.InitStringFieldAsEmpty)) {
      return "";
    }
    return null;
  }
  
  public final String stringVal()
  {
    if (!this.hasSpecial) {
      return this.text.substring(1 + this.np, 1 + this.np + this.sp);
    }
    return new String(this.sbuf, 0, this.sp);
  }
  
  public final String subString(int paramInt1, int paramInt2)
  {
    return this.text.substring(paramInt1, paramInt1 + paramInt2);
  }
  
  public final String symbol(SymbolTable paramSymbolTable)
  {
    if (paramSymbolTable == null)
    {
      if (!this.hasSpecial) {
        return this.text.substring(1 + this.np, 1 + this.np + this.sp);
      }
      return new String(this.sbuf, 0, this.sp);
    }
    if (!this.hasSpecial) {
      return paramSymbolTable.addSymbol(this.text, 1 + this.np, this.sp);
    }
    return paramSymbolTable.addSymbol(this.sbuf, 0, this.sp);
  }
  
  public final int token()
  {
    return this.token;
  }
  
  public final String tokenName()
  {
    return JSONToken.name(this.token);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\parser\JSONScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */