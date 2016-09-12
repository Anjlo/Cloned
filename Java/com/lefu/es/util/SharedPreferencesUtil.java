package com.lefu.es.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SharedPreferencesUtil
{
  private String TAG = "SharedPreferencesUtil";
  public Context context;
  
  public SharedPreferencesUtil(Context paramContext)
  {
    this.context = paramContext;
  }
  
  public boolean editSharedPreferences(String paramString1, String paramString2, Object paramObject)
  {
    if ((paramString1 != null) && (!"".equals(paramString1))) {
      for (;;)
      {
        SharedPreferences.Editor localEditor;
        try
        {
          localEditor = this.context.getSharedPreferences(paramString1, 0).edit();
          if ((paramObject instanceof Integer))
          {
            localEditor.putInt(paramString2, ((Integer)paramObject).intValue());
            localEditor.commit();
            Log.i(this.TAG, "文件内容修改成功：" + paramString1);
            return true;
          }
          if ((paramObject instanceof String))
          {
            localEditor.putString(paramString2, (String)paramObject);
            continue;
          }
          if (!(paramObject instanceof Float)) {
            break label163;
          }
        }
        catch (Exception localException)
        {
          Log.i(this.TAG, "文件内容修改失败：" + paramString1);
          return false;
        }
        localEditor.putFloat(paramString2, ((Float)paramObject).floatValue());
        continue;
        label163:
        if ((paramObject instanceof Long)) {
          localEditor.putLong(paramString2, ((Long)paramObject).longValue());
        } else if ((paramObject instanceof Boolean)) {
          localEditor.putBoolean(paramString2, ((Boolean)paramObject).booleanValue());
        } else {
          localEditor.putString(paramString2, (String)paramObject);
        }
      }
    }
    Log.i(this.TAG, "文件内容修改失败-->文件名不存在");
    return false;
  }
  
  public Object readbackUp(String paramString1, String paramString2, Object paramObject)
  {
    try
    {
      SharedPreferences localSharedPreferences = this.context.getSharedPreferences(paramString1, 0);
      if ((paramObject instanceof Integer)) {
        return Integer.valueOf(localSharedPreferences.getInt(paramString2, 0));
      }
      if ((paramObject instanceof String)) {
        return localSharedPreferences.getString(paramString2, "");
      }
      if ((paramObject instanceof Float)) {
        return Float.valueOf(localSharedPreferences.getFloat(paramString2, 0.0F));
      }
      if ((paramObject instanceof Long)) {
        return Long.valueOf(localSharedPreferences.getLong(paramString2, 0L));
      }
      if ((paramObject instanceof Boolean)) {
        return Boolean.valueOf(localSharedPreferences.getBoolean(paramString2, false));
      }
      String str = localSharedPreferences.getString(paramString2, "");
      return str;
    }
    catch (Exception localException)
    {
      Log.e(this.TAG, "获取接点信息失败:" + localException.getMessage());
    }
    return null;
  }
  
  public boolean saveSharedPreferences(String paramString, int paramInt, Map<String, Object> paramMap)
  {
    boolean bool = true;
    if ((paramString != null) && (!"".equals(paramString)))
    {
      switch (paramInt)
      {
      }
      for (;;)
      {
        SharedPreferences.Editor localEditor;
        String str;
        Object localObject;
        try
        {
          SharedPreferences localSharedPreferences = this.context.getSharedPreferences(paramString, 0);
          if ((localSharedPreferences == null) || (paramMap == null) || (paramMap.size() <= 0)) {
            break;
          }
          localEditor = localSharedPreferences.edit();
          Iterator localIterator = paramMap.entrySet().iterator();
          if (!localIterator.hasNext())
          {
            localEditor.commit();
            Log.i(this.TAG, "文件内容添加成功：" + paramString);
            return bool;
            localSharedPreferences = this.context.getSharedPreferences(paramString, 32768);
            continue;
          }
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          str = localEntry.getKey().toString();
          localObject = localEntry.getValue();
          if ((localObject instanceof Integer))
          {
            localEditor.putInt(str, ((Integer)localObject).intValue());
            continue;
          }
          if (!(localObject instanceof String)) {
            break label267;
          }
        }
        catch (Exception localException)
        {
          Log.i(this.TAG, "文件内容添加失败：" + localException.getMessage());
          return false;
        }
        localEditor.putString(str, (String)localObject);
        continue;
        label267:
        if ((localObject instanceof Float)) {
          localEditor.putFloat(str, ((Float)localObject).floatValue());
        } else if ((localObject instanceof Long)) {
          localEditor.putLong(str, ((Long)localObject).longValue());
        } else if ((localObject instanceof Boolean)) {
          localEditor.putBoolean(str, ((Boolean)localObject).booleanValue());
        } else {
          localEditor.putString(str, (String)localObject);
        }
      }
    }
    Log.i(this.TAG, "文件内容添加失败-->文件名不存在");
    bool = false;
    return bool;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\util\SharedPreferencesUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */