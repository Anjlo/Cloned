package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.sql.Clob;
import java.sql.SQLException;

public class ClobSeriliazer
  implements ObjectSerializer
{
  public static final ClobSeriliazer instance = new ClobSeriliazer();
  
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    if (paramObject1 == null) {}
    try
    {
      paramJSONSerializer.writeNull();
      return;
    }
    catch (SQLException localSQLException)
    {
      char[] arrayOfChar;
      throw new IOException("write clob error", localSQLException);
    }
    Reader localReader = ((Clob)paramObject1).getCharacterStream();
    StringWriter localStringWriter = new StringWriter();
    arrayOfChar = new char['Ѐ'];
    for (;;)
    {
      int i = localReader.read(arrayOfChar);
      if (i == -1) {
        break;
      }
      localStringWriter.write(arrayOfChar, 0, i);
    }
    localReader.close();
    paramJSONSerializer.write(localStringWriter.toString());
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\ClobSeriliazer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */