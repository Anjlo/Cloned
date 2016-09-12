package com.lefu.es.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import com.lefu.es.cache.CacheHelper;
import com.lefu.es.crach.CrashHandler;
import com.lefu.es.db.DatabaseCreatOrUpdateHelper;
import java.util.List;

public class IwellnessApplication
  extends Application
{
  public static IwellnessApplication app = null;
  
  private void initCacheDates()
  {
    if ((CacheHelper.nutrientList == null) || (CacheHelper.nutrientList.size() == 0))
    {
      SQLiteDatabase localSQLiteDatabase = DatabaseCreatOrUpdateHelper.createOrUpdateDB(this);
      CacheHelper.cacheAllNutrientsDate(this, localSQLiteDatabase);
      if (localSQLiteDatabase != null) {
        localSQLiteDatabase.close();
      }
    }
  }
  
  public void onCreate()
  {
    super.onCreate();
    app = this;
    CrashHandler.getInstance().init(getApplicationContext());
    new initAsyncTask().execute(new Integer[0]);
  }
  
  class initAsyncTask
    extends AsyncTask<Integer, Integer, Integer>
  {
    initAsyncTask() {}
    
    protected Integer doInBackground(Integer... paramVarArgs)
    {
      IwellnessApplication.this.initCacheDates();
      return Integer.valueOf(0);
    }
    
    protected void onPostExecute(Integer paramInteger)
    {
      Log.i("Application*****", "缓存初始化完成");
    }
    
    protected void onPreExecute()
    {
      super.onPreExecute();
    }
    
    protected void onProgressUpdate(Integer... paramVarArgs) {}
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\application\IwellnessApplication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */