package com.lefu.es.util;

import android.os.Environment;
import android.util.Log;
import com.lefu.es.constant.UtilConstants;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LogUtils
{
  public static void d(String paramString1, String paramString2)
  {
    if (UtilConstants.LOG_DEBUG)
    {
      Log.d(paramString1, paramString2);
      FileLogger.getInstance().addLog(paramString1, paramString2);
    }
  }
  
  public static void d(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (UtilConstants.LOG_DEBUG)
    {
      Log.d(paramString1, paramString2, paramThrowable);
      FileLogger.getInstance().addLog(paramString1, paramString2, paramThrowable);
    }
  }
  
  public static void e(String paramString1, String paramString2)
  {
    if (UtilConstants.LOG_DEBUG)
    {
      Log.e(paramString1, paramString2);
      FileLogger.getInstance().addLog(paramString1, paramString2);
    }
  }
  
  public static void e(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (UtilConstants.LOG_DEBUG)
    {
      Log.e(paramString1, paramString2, paramThrowable);
      FileLogger.getInstance().addLog(paramString1, paramString2, paramThrowable);
    }
  }
  
  public static void i(String paramString1, String paramString2)
  {
    if (UtilConstants.LOG_DEBUG)
    {
      Log.i(paramString1, paramString2);
      FileLogger.getInstance().addLog(paramString1, paramString2);
    }
  }
  
  public static void i(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (UtilConstants.LOG_DEBUG)
    {
      Log.i(paramString1, paramString2, paramThrowable);
      FileLogger.getInstance().addLog(paramString1, paramString2, paramThrowable);
    }
  }
  
  public static void print(Object paramObject)
  {
    if (UtilConstants.LOG_DEBUG)
    {
      System.out.print(paramObject);
      FileLogger.getInstance().addLog("System.out", paramObject.toString());
    }
  }
  
  public static void printStackTrace(Throwable paramThrowable)
  {
    if (UtilConstants.LOG_DEBUG)
    {
      paramThrowable.printStackTrace();
      FileLogger.getInstance().addLog("System.out", paramThrowable);
    }
  }
  
  public static void println()
  {
    if (UtilConstants.LOG_DEBUG)
    {
      System.out.println();
      FileLogger.getInstance().addLog("", "");
    }
  }
  
  public static void println(Object paramObject)
  {
    if (UtilConstants.LOG_DEBUG)
    {
      System.out.println(paramObject);
      FileLogger.getInstance().addLog("System.out", paramObject.toString());
    }
  }
  
  public static void setFileLogPath(String paramString)
  {
    FileLogger.getInstance().setLogPath(paramString);
  }
  
  public static void stopFileLogger()
  {
    FileLogger.getInstance().stop();
  }
  
  public static void v(String paramString1, String paramString2)
  {
    if (UtilConstants.LOG_DEBUG)
    {
      Log.v(paramString1, paramString2);
      FileLogger.getInstance().addLog(paramString1, paramString2);
    }
  }
  
  public static void v(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (UtilConstants.LOG_DEBUG)
    {
      Log.v(paramString1, paramString2, paramThrowable);
      FileLogger.getInstance().addLog(paramString1, paramString2, paramThrowable);
    }
  }
  
  public static void w(String paramString1, String paramString2)
  {
    if (UtilConstants.LOG_DEBUG)
    {
      Log.w(paramString1, paramString2);
      FileLogger.getInstance().addLog(paramString1, paramString2);
    }
  }
  
  public static void w(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (UtilConstants.LOG_DEBUG)
    {
      Log.w(paramString1, paramString2, paramThrowable);
      FileLogger.getInstance().addLog(paramString1, paramString2, paramThrowable);
    }
  }
  
  private static class FileLogger
    implements Runnable
  {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static SimpleDateFormat dateTimeFormat;
    private static FileLogger inst = new FileLogger();
    private boolean isRunning;
    private final ArrayList<String> logList = new ArrayList();
    private String logPath;
    private Thread thread;
    
    static
    {
      dateTimeFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());
    }
    
    public static FileLogger getInstance()
    {
      return inst;
    }
    
    private String getLog()
    {
      try
      {
        if (this.logList.size() > 0)
        {
          String str = (String)this.logList.remove(0);
          return str;
        }
        return null;
      }
      finally {}
    }
    
    public static String getTime()
    {
      return dateTimeFormat.format(new Date());
    }
    
    private boolean isSDCardAvailable()
    {
      return Environment.getExternalStorageState().equals("mounted");
    }
    
    private void notifyWrite()
    {
      if ((!this.isRunning) && (isSDCardAvailable()))
      {
        this.isRunning = true;
        this.thread = new Thread(this);
        this.thread.start();
      }
    }
    
    public void addLog(String paramString1, String paramString2)
    {
      if ((!isSDCardAvailable()) || (this.logPath == null)) {
        return;
      }
      try
      {
        this.logList.add("[" + getTime() + "][" + paramString1 + "]  " + paramString2);
        notifyWrite();
        return;
      }
      finally {}
    }
    
    public void addLog(String paramString1, String paramString2, Throwable paramThrowable)
    {
      if ((!isSDCardAvailable()) || (this.logPath == null)) {
        return;
      }
      try
      {
        addLog(paramString1, paramString2);
        addLog(paramString1, paramThrowable);
        return;
      }
      finally {}
    }
    
    public void addLog(String paramString, Throwable paramThrowable)
    {
      if ((!isSDCardAvailable()) || (this.logPath == null)) {
        return;
      }
      for (;;)
      {
        StackTraceElement[] arrayOfStackTraceElement1;
        int i;
        StackTraceElement[] arrayOfStackTraceElement2;
        int j;
        try
        {
          addLog(paramString, paramThrowable.toString());
          arrayOfStackTraceElement1 = paramThrowable.getStackTrace();
          i = 0;
          if (i >= arrayOfStackTraceElement1.length)
          {
            Throwable localThrowable = paramThrowable.getCause();
            if (localThrowable != null)
            {
              this.logList.add("    Caused by: " + localThrowable.toString());
              arrayOfStackTraceElement2 = localThrowable.getStackTrace();
              j = 0;
              if (j < arrayOfStackTraceElement2.length) {
                break label153;
              }
            }
            notifyWrite();
            return;
          }
        }
        finally {}
        StackTraceElement localStackTraceElement1 = arrayOfStackTraceElement1[i];
        this.logList.add("    " + localStackTraceElement1.toString());
        i++;
        continue;
        label153:
        StackTraceElement localStackTraceElement2 = arrayOfStackTraceElement2[j];
        this.logList.add("    " + localStackTraceElement2.toString());
        j++;
      }
    }
    
    public String getLogFileName()
    {
      return dateFormat.format(new Date()) + ".txt";
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_1
      //   2: aconst_null
      //   3: astore_2
      //   4: iconst_0
      //   5: istore_3
      //   6: aload_0
      //   7: invokespecial 167	com/lefu/es/util/LogUtils$FileLogger:getLog	()Ljava/lang/String;
      //   10: astore 4
      //   12: aload_0
      //   13: getfield 90	com/lefu/es/util/LogUtils$FileLogger:isRunning	Z
      //   16: ifne +14 -> 30
      //   19: aload_0
      //   20: iconst_0
      //   21: putfield 90	com/lefu/es/util/LogUtils$FileLogger:isRunning	Z
      //   24: aload_0
      //   25: aconst_null
      //   26: putfield 99	com/lefu/es/util/LogUtils$FileLogger:thread	Ljava/lang/Thread;
      //   29: return
      //   30: aload 4
      //   32: ifnonnull +25 -> 57
      //   35: iload_3
      //   36: iconst_5
      //   37: if_icmpgt -18 -> 19
      //   40: iinc 3 1
      //   43: ldc2_w 168
      //   46: invokestatic 173	java/lang/Thread:sleep	(J)V
      //   49: goto -43 -> 6
      //   52: astore 12
      //   54: goto -48 -> 6
      //   57: aload_0
      //   58: invokespecial 92	com/lefu/es/util/LogUtils$FileLogger:isSDCardAvailable	()Z
      //   61: ifeq -42 -> 19
      //   64: aload_0
      //   65: getfield 106	com/lefu/es/util/LogUtils$FileLogger:logPath	Ljava/lang/String;
      //   68: ifnull -49 -> 19
      //   71: aload_0
      //   72: getfield 106	com/lefu/es/util/LogUtils$FileLogger:logPath	Ljava/lang/String;
      //   75: astore 7
      //   77: new 175	java/io/File
      //   80: dup
      //   81: aload 7
      //   83: invokespecial 176	java/io/File:<init>	(Ljava/lang/String;)V
      //   86: invokestatic 182	com/lefu/es/util/Tool:createDirs	(Ljava/io/File;)V
      //   89: new 175	java/io/File
      //   92: dup
      //   93: new 108	java/lang/StringBuilder
      //   96: dup
      //   97: aload 7
      //   99: invokestatic 160	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
      //   102: invokespecial 113	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   105: aload_0
      //   106: invokevirtual 184	com/lefu/es/util/LogUtils$FileLogger:getLogFileName	()Ljava/lang/String;
      //   109: invokevirtual 119	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   112: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   115: invokespecial 176	java/io/File:<init>	(Ljava/lang/String;)V
      //   118: astore 8
      //   120: aload 8
      //   122: invokevirtual 187	java/io/File:exists	()Z
      //   125: ifne +34 -> 159
      //   128: ldc -67
      //   130: new 108	java/lang/StringBuilder
      //   133: dup
      //   134: ldc -65
      //   136: invokespecial 113	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   139: aload 8
      //   141: invokevirtual 194	java/io/File:getPath	()Ljava/lang/String;
      //   144: invokevirtual 119	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   147: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   150: invokestatic 199	com/lefu/es/util/LogUtils:i	(Ljava/lang/String;Ljava/lang/String;)V
      //   153: aload 8
      //   155: invokevirtual 202	java/io/File:createNewFile	()Z
      //   158: pop
      //   159: new 204	java/io/FileOutputStream
      //   162: dup
      //   163: aload 8
      //   165: iconst_1
      //   166: invokespecial 207	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
      //   169: astore 9
      //   171: new 209	java/io/BufferedWriter
      //   174: dup
      //   175: new 211	java/io/OutputStreamWriter
      //   178: dup
      //   179: aload 9
      //   181: invokespecial 214	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;)V
      //   184: invokespecial 217	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
      //   187: astore 10
      //   189: aload 10
      //   191: aload 4
      //   193: invokevirtual 220	java/io/BufferedWriter:write	(Ljava/lang/String;)V
      //   196: aload 10
      //   198: invokevirtual 223	java/io/BufferedWriter:newLine	()V
      //   201: aload 10
      //   203: invokestatic 226	com/lefu/es/util/Tool:close	(Ljava/io/Writer;)V
      //   206: aload 9
      //   208: invokestatic 228	com/lefu/es/util/Tool:close	(Ljava/io/OutputStream;)V
      //   211: aload 10
      //   213: astore_2
      //   214: aload 9
      //   216: astore_1
      //   217: goto -211 -> 6
      //   220: astore 6
      //   222: aload 6
      //   224: invokevirtual 231	java/lang/Throwable:printStackTrace	()V
      //   227: aload_2
      //   228: invokestatic 226	com/lefu/es/util/Tool:close	(Ljava/io/Writer;)V
      //   231: aload_1
      //   232: invokestatic 228	com/lefu/es/util/Tool:close	(Ljava/io/OutputStream;)V
      //   235: goto -216 -> 19
      //   238: astore 5
      //   240: aload_2
      //   241: invokestatic 226	com/lefu/es/util/Tool:close	(Ljava/io/Writer;)V
      //   244: aload_1
      //   245: invokestatic 228	com/lefu/es/util/Tool:close	(Ljava/io/OutputStream;)V
      //   248: aload 5
      //   250: athrow
      //   251: astore 5
      //   253: aload 9
      //   255: astore_1
      //   256: goto -16 -> 240
      //   259: astore 5
      //   261: aload 10
      //   263: astore_2
      //   264: aload 9
      //   266: astore_1
      //   267: goto -27 -> 240
      //   270: astore 6
      //   272: aload 9
      //   274: astore_1
      //   275: goto -53 -> 222
      //   278: astore 6
      //   280: aload 10
      //   282: astore_2
      //   283: aload 9
      //   285: astore_1
      //   286: goto -64 -> 222
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	289	0	this	FileLogger
      //   1	285	1	localObject1	Object
      //   3	280	2	localObject2	Object
      //   5	36	3	i	int
      //   10	182	4	str1	String
      //   238	11	5	localObject3	Object
      //   251	1	5	localObject4	Object
      //   259	1	5	localObject5	Object
      //   220	3	6	localThrowable1	Throwable
      //   270	1	6	localThrowable2	Throwable
      //   278	1	6	localThrowable3	Throwable
      //   75	23	7	str2	String
      //   118	46	8	localFile	File
      //   169	115	9	localFileOutputStream	java.io.FileOutputStream
      //   187	94	10	localBufferedWriter	java.io.BufferedWriter
      //   52	1	12	localInterruptedException	InterruptedException
      // Exception table:
      //   from	to	target	type
      //   43	49	52	java/lang/InterruptedException
      //   71	159	220	java/lang/Throwable
      //   159	171	220	java/lang/Throwable
      //   71	159	238	finally
      //   159	171	238	finally
      //   222	227	238	finally
      //   171	189	251	finally
      //   189	201	259	finally
      //   171	189	270	java/lang/Throwable
      //   189	201	278	java/lang/Throwable
    }
    
    public void setLogPath(String paramString)
    {
      if ((paramString != null) && (!paramString.endsWith(File.separator)))
      {
        this.logPath = (paramString + File.separator);
        return;
      }
      this.logPath = paramString;
    }
    
    public void stop()
    {
      this.isRunning = false;
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\util\LogUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */