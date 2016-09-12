package com.lefu.es.db;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import com.lefu.es.util.LogUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DatabaseCreatOrUpdateHelper
{
  private static final String BASE_DATABASE_NAME = "basedb.db";
  public static final String BASE_DATABASE_PATH = DATABASE_PAGE_PATH + "/" + "basedb.db";
  private static final int BUFFER_SIZE = 400000;
  public static final String DATABASE_PAGE_PATH;
  public static final String PACKAGE_NAME = "com.lefu.iwellness.newes.system";
  private static String TAG = DatabaseCreatOrUpdateHelper.class.getSimpleName();
  
  static
  {
    DATABASE_PAGE_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + "com.lefu.iwellness.newes.system" + "/databases";
  }
  
  public static SQLiteDatabase createOrUpdateDB(Context paramContext)
  {
    if (!new File(BASE_DATABASE_PATH).exists())
    {
      new File(DATABASE_PAGE_PATH).mkdirs();
      try
      {
        importDateBase(paramContext);
        SQLiteDatabase localSQLiteDatabase2 = SQLiteDatabase.openOrCreateDatabase(BASE_DATABASE_PATH, null);
        return localSQLiteDatabase2;
      }
      catch (FileNotFoundException localFileNotFoundException2)
      {
        LogUtils.e(TAG, "onCreate DB failed:" + localFileNotFoundException2.getMessage());
        return null;
      }
      catch (IOException localIOException2)
      {
        LogUtils.e(TAG, "onCreate DB failed:" + localIOException2.getMessage());
        return null;
      }
    }
    LogUtils.e(TAG, "f.exists()");
    try
    {
      paramContext.deleteDatabase(BASE_DATABASE_PATH);
      importDateBase(paramContext);
      SQLiteDatabase localSQLiteDatabase1 = SQLiteDatabase.openOrCreateDatabase(BASE_DATABASE_PATH, null);
      return localSQLiteDatabase1;
    }
    catch (FileNotFoundException localFileNotFoundException1)
    {
      LogUtils.e(TAG, "onCreate DB failed:" + localFileNotFoundException1.getMessage());
      return null;
    }
    catch (IOException localIOException1)
    {
      LogUtils.e(TAG, "onCreate DB failed:" + localIOException1.getMessage());
    }
    return null;
  }
  
  public static void importDateBase(Context paramContext)
    throws IOException
  {
    InputStream localInputStream = paramContext.getResources().openRawResource(2131034112);
    FileOutputStream localFileOutputStream = new FileOutputStream(BASE_DATABASE_PATH);
    byte[] arrayOfByte = new byte[400000];
    for (;;)
    {
      int i = localInputStream.read(arrayOfByte);
      if (i <= 0)
      {
        localFileOutputStream.close();
        localInputStream.close();
        return;
      }
      localFileOutputStream.write(arrayOfByte, 0, i);
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\db\DatabaseCreatOrUpdateHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */