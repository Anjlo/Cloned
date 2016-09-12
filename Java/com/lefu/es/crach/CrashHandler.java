package com.lefu.es.crach;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@SuppressLint({"SdCardPath"})
public class CrashHandler
  implements Thread.UncaughtExceptionHandler
{
  private static CrashHandler INSTANCE = new CrashHandler();
  public static final String TAG = "CrashHandler";
  @SuppressLint({"SimpleDateFormat"})
  private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
  private Map<String, String> infos = new HashMap();
  private Context mContext;
  private Thread.UncaughtExceptionHandler mDefaultHandler;
  
  public static CrashHandler getInstance()
  {
    return INSTANCE;
  }
  
  private boolean handleException(Throwable paramThrowable)
  {
    if (paramThrowable == null) {
      return false;
    }
    collectDeviceInfo(this.mContext);
    saveCrashInfo2File(paramThrowable);
    return true;
  }
  
  private String saveCrashInfo2File(Throwable paramThrowable)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = this.infos.entrySet().iterator();
    StringWriter localStringWriter;
    PrintWriter localPrintWriter;
    if (!localIterator.hasNext())
    {
      localStringWriter = new StringWriter();
      localPrintWriter = new PrintWriter(localStringWriter);
      paramThrowable.printStackTrace(localPrintWriter);
    }
    for (Throwable localThrowable = paramThrowable.getCause();; localThrowable = localThrowable.getCause())
    {
      if (localThrowable == null)
      {
        localPrintWriter.close();
        localStringBuffer.append(localStringWriter.toString());
      }
      try
      {
        long l = System.currentTimeMillis();
        String str1 = this.formatter.format(new Date());
        String str2 = "crash-" + str1 + "-" + l + ".log";
        if (Environment.getExternalStorageState().equals("mounted"))
        {
          File localFile = new File("/sdcard/crash/");
          if (!localFile.exists()) {
            localFile.mkdirs();
          }
          FileOutputStream localFileOutputStream = new FileOutputStream("/sdcard/crash/" + str2);
          localFileOutputStream.write(localStringBuffer.toString().getBytes());
          localFileOutputStream.close();
        }
        return str2;
      }
      catch (Exception localException)
      {
        Map.Entry localEntry;
        String str3;
        String str4;
        Log.e("CrashHandler", "an error occured while writing file...", localException);
      }
      localEntry = (Map.Entry)localIterator.next();
      str3 = (String)localEntry.getKey();
      str4 = (String)localEntry.getValue();
      localStringBuffer.append(str3 + "=" + str4 + "\n");
      break;
      localThrowable.printStackTrace(localPrintWriter);
    }
    return null;
  }
  
  public void collectDeviceInfo(Context paramContext)
  {
    try
    {
      localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 1);
      if (localPackageInfo != null)
      {
        if (localPackageInfo.versionName != null) {
          break label102;
        }
        str1 = "null";
        String str2 = localPackageInfo.versionCode;
        this.infos.put("versionName", str1);
        this.infos.put("versionCode", str2);
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      Field[] arrayOfField;
      for (;;)
      {
        PackageInfo localPackageInfo;
        String str1;
        int i;
        label102:
        Log.e("CrashHandler", "an error occured when collect package info", localNameNotFoundException);
      }
      localField = arrayOfField[j];
    }
    arrayOfField = Build.class.getDeclaredFields();
    i = arrayOfField.length;
    int j = 0;
    for (;;)
    {
      if (j >= i)
      {
        return;
        str1 = localPackageInfo.versionName;
        break;
      }
      try
      {
        Field localField;
        localField.setAccessible(true);
        this.infos.put(localField.getName(), localField.get(null).toString());
        Log.d("CrashHandler", localField.getName() + " : " + localField.get(null));
        j++;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          Log.e("CrashHandler", "an error occured when collect crash info", localException);
        }
      }
    }
  }
  
  public void init(Context paramContext)
  {
    this.mContext = paramContext;
    this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }
  
  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    if ((!handleException(paramThrowable)) && (this.mDefaultHandler != null))
    {
      Toast.makeText(this.mContext, "Program encountered problems", 1).show();
      this.mDefaultHandler.uncaughtException(paramThread, paramThrowable);
      return;
    }
    Process.killProcess(Process.myPid());
    System.exit(1);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\crach\CrashHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */