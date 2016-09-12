package com.lefu.es.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.lefu.es.db.DBOpenHelper;
import com.lefu.es.entity.Records;
import com.lefu.es.util.UtilTooth;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordService
{
  private Context context;
  private DBOpenHelper dbHelper;
  private SQLiteDatabase dbs;
  
  public RecordService(Context paramContext)
  {
    this.dbHelper = new DBOpenHelper(paramContext);
    this.context = paramContext;
  }
  
  public void closeDB()
  {
    if (this.dbHelper != null)
    {
      this.dbHelper.close();
      this.dbHelper = null;
    }
  }
  
  public void delete(Records paramRecords)
    throws Exception
  {
    this.dbs = this.dbHelper.getWritableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramRecords.getId());
    localSQLiteDatabase.execSQL("delete from userrecord where id=?", arrayOfObject);
    deletePhoto(paramRecords.getRphoto());
  }
  
  public void deleteByUseridAndScale(String paramString1, String paramString2)
  {
    for (;;)
    {
      try
      {
        localList = getAllDatasByScaleAndGroup(paramString2, paramString1, 100.0F);
        if (localList.size() > 0)
        {
          i = 0;
          int j = localList.size();
          if (i < j) {
            continue;
          }
        }
      }
      catch (Exception localException)
      {
        List localList;
        int i;
        localException.printStackTrace();
        continue;
      }
      this.dbs = this.dbHelper.getWritableDatabase();
      this.dbs.beginTransaction();
      this.dbs.execSQL("delete from userrecord where useid=? and scaleType=?", new Object[] { paramString1, paramString2 });
      this.dbs.setTransactionSuccessful();
      this.dbs.endTransaction();
      this.dbs.close();
      return;
      deletePhoto(((Records)localList.get(i)).getRphoto());
      i++;
    }
  }
  
  public void deletePhoto(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 3))
    {
      File localFile = new File(paramString);
      if (localFile.exists()) {
        localFile.delete();
      }
    }
  }
  
  public Records find(int paramInt)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramInt);
    Cursor localCursor = localSQLiteDatabase.rawQuery("select useid,scaleType,ugroup,datetime(recordtime,'localtime'),comparerecord,rweight, rbmi, rbone, rbodyfat, rmuscle, rbodywater, rvisceralfat, rbmr,photo,bodyage from userrecord where id=? order by recordtime desc  ", arrayOfString);
    boolean bool = localCursor.moveToFirst();
    Records localRecords = null;
    if (bool)
    {
      localRecords = new Records(paramInt, localCursor.getInt(localCursor.getColumnIndex("useid")), localCursor.getString(localCursor.getColumnIndex("scaleType")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("datetime(recordtime,'localtime')")), localCursor.getString(localCursor.getColumnIndex("comparerecord")), localCursor.getFloat(localCursor.getColumnIndex("rweight")), localCursor.getFloat(localCursor.getColumnIndex("rbmi")), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage")));
      String str = localCursor.getString(localCursor.getColumnIndex("photo"));
      if ((str != null) && (str.length() > 3)) {
        localRecords.setRphoto(str);
      }
    }
    localCursor.close();
    this.dbs.close();
    return localRecords;
  }
  
  public Records findLastRecords(int paramInt)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramInt);
    Cursor localCursor = localSQLiteDatabase.rawQuery("select id, useid,scaleType,ugroup,datetime(recordtime,'localtime'),comparerecord,rweight, rbmi, rbone, rbodyfat, rmuscle, rbodywater, rvisceralfat, rbmr,photo,max(datetime(recordtime,'localtime')),bodyage from userrecord where useid = ? group by scaletype order by recordtime desc", arrayOfString);
    boolean bool = localCursor.moveToFirst();
    Records localRecords = null;
    if (bool)
    {
      localRecords = new Records(localCursor.getInt(localCursor.getColumnIndex("id")), paramInt, localCursor.getString(localCursor.getColumnIndex("scaleType")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("datetime(recordtime,'localtime')")), localCursor.getString(localCursor.getColumnIndex("comparerecord")), localCursor.getFloat(localCursor.getColumnIndex("rweight")), localCursor.getFloat(localCursor.getColumnIndex("rbmi")), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage")));
      String str = localCursor.getString(localCursor.getColumnIndex("photo"));
      if ((str != null) && (str.length() > 3)) {
        localRecords.setRphoto(str);
      }
    }
    localCursor.close();
    this.dbs.close();
    return localRecords;
  }
  
  public List<Records> findLastRecords()
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(1);
    Cursor localCursor = localSQLiteDatabase.rawQuery("select id, useid,scaleType,ugroup,recordtime,datetime(recordtime,'localtime'),comparerecord,rweight, rbmi, rbone, rbodyfat, rmuscle, rbodywater, rvisceralfat, rbmr,photo,bodyage,recordtime from userrecord where 1=?  group by scaletype,ugroup order by recordtime desc ", arrayOfString);
    ArrayList localArrayList = new ArrayList();
    if (localCursor.moveToNext())
    {
      Records localRecords = new Records(localCursor.getInt(localCursor.getColumnIndex("id")), localCursor.getInt(localCursor.getColumnIndex("useid")), localCursor.getString(localCursor.getColumnIndex("scaleType")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("datetime(recordtime,'localtime')")), localCursor.getString(localCursor.getColumnIndex("comparerecord")), localCursor.getFloat(localCursor.getColumnIndex("rweight")), localCursor.getFloat(localCursor.getColumnIndex("rbmi")), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage")));
      String str = localCursor.getString(localCursor.getColumnIndex("photo"));
      if ((str != null) && (str.length() > 3)) {
        localRecords.setRphoto(str);
      }
      localArrayList.add(localRecords);
    }
    localCursor.close();
    this.dbs.close();
    return localArrayList;
  }
  
  public Records findLastRecordsByScaleType(String paramString1, String paramString2)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select *,max(datetime(recordtime,'localtime')) md from userrecord group by useid having scaleType=? and ugroup=? order by md desc", new String[] { paramString1, paramString2 });
    boolean bool = localCursor.moveToFirst();
    Records localRecords = null;
    if (bool)
    {
      localRecords = new Records(localCursor.getInt(localCursor.getColumnIndex("id")), localCursor.getInt(localCursor.getColumnIndex("useid")), localCursor.getString(localCursor.getColumnIndex("scaleType")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("md")), localCursor.getString(localCursor.getColumnIndex("comparerecord")), localCursor.getFloat(localCursor.getColumnIndex("rweight")), localCursor.getFloat(localCursor.getColumnIndex("rbmi")), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")));
      String str = localCursor.getString(localCursor.getColumnIndex("photo"));
      if ((str != null) && (str.length() > 3)) {
        localRecords.setRphoto(str);
      }
    }
    localCursor.close();
    this.dbs.close();
    return localRecords;
  }
  
  public List<Records> findLastRecordsByScaleType2(String paramString)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select *,max(datetime(recordtime,'localtime')) md from userrecord group by useid having scaleType=?  order by ugroup asc,recordtime desc", new String[] { paramString });
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      if (!localCursor.moveToNext())
      {
        localCursor.close();
        this.dbs.close();
        return localArrayList;
      }
      Records localRecords = new Records(localCursor.getInt(localCursor.getColumnIndex("id")), localCursor.getInt(localCursor.getColumnIndex("useid")), localCursor.getString(localCursor.getColumnIndex("scaleType")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("md")), localCursor.getString(localCursor.getColumnIndex("comparerecord")), localCursor.getFloat(localCursor.getColumnIndex("rweight")), localCursor.getFloat(localCursor.getColumnIndex("rbmi")), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage")));
      String str = localCursor.getString(localCursor.getColumnIndex("photo"));
      if ((str != null) && (str.length() > 3)) {
        localRecords.setRphoto(str);
      }
      localArrayList.add(localRecords);
    }
  }
  
  public Records findLastRecordsByScaleTypeAndUser(String paramString1, String paramString2)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select *,max(datetime(recordtime,'localtime')) md from userrecord where scaleType=? and useid=? order by md desc", new String[] { paramString1, paramString2 });
    boolean bool = localCursor.moveToFirst();
    Records localRecords = null;
    if (bool)
    {
      localRecords = new Records(localCursor.getInt(localCursor.getColumnIndex("id")), localCursor.getInt(localCursor.getColumnIndex("useid")), localCursor.getString(localCursor.getColumnIndex("scaleType")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("md")), localCursor.getString(localCursor.getColumnIndex("comparerecord")), localCursor.getFloat(localCursor.getColumnIndex("rweight")), localCursor.getFloat(localCursor.getColumnIndex("rbmi")), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getInt(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage")));
      String str = localCursor.getString(localCursor.getColumnIndex("photo"));
      if ((str != null) && (str.length() > 3)) {
        localRecords.setRphoto(str);
      }
    }
    localCursor.close();
    this.dbs.close();
    return localRecords;
  }
  
  public Records findLastRecordsByUID(int paramInt)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramInt);
    Cursor localCursor = localSQLiteDatabase.rawQuery("select *,max(datetime(recordtime,'localtime')) md from userrecord where useid =? order by md desc", arrayOfString);
    boolean bool = localCursor.moveToFirst();
    Records localRecords = null;
    if (bool)
    {
      localRecords = new Records(localCursor.getInt(localCursor.getColumnIndex("id")), localCursor.getInt(localCursor.getColumnIndex("useid")), localCursor.getString(localCursor.getColumnIndex("scaleType")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("md")), localCursor.getString(localCursor.getColumnIndex("comparerecord")), localCursor.getFloat(localCursor.getColumnIndex("rweight")), localCursor.getFloat(localCursor.getColumnIndex("rbmi")), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage")));
      String str = localCursor.getString(localCursor.getColumnIndex("photo"));
      if ((str != null) && (str.length() > 3)) {
        localRecords.setRphoto(str);
      }
    }
    localCursor.close();
    this.dbs.close();
    return localRecords;
  }
  
  public List<Records> findLastTowRecordsByScaleType(String paramString1, String paramString2)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select * from userrecord where scaleType=? and ugroup=? order by recordtime desc limit 0,5", new String[] { paramString1, paramString2 });
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2;
    if (!localCursor.moveToNext())
    {
      localCursor.close();
      this.dbs.close();
      localArrayList2 = new ArrayList();
    }
    for (int i = -1 + localArrayList1.size();; i--)
    {
      if (i < 0)
      {
        return localArrayList2;
        Records localRecords = new Records(localCursor.getInt(localCursor.getColumnIndex("id")), localCursor.getInt(localCursor.getColumnIndex("useid")), localCursor.getString(localCursor.getColumnIndex("scaleType")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("recordtime")), localCursor.getString(localCursor.getColumnIndex("comparerecord")), localCursor.getFloat(localCursor.getColumnIndex("rweight")), localCursor.getFloat(localCursor.getColumnIndex("rbmi")), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage")));
        String str = localCursor.getString(localCursor.getColumnIndex("photo"));
        if ((str != null) && (str.length() > 3)) {
          localRecords.setRphoto(str);
        }
        localArrayList1.add(localRecords);
        break;
      }
      localArrayList2.add((Records)localArrayList1.get(i));
    }
  }
  
  public List<Records> findRecordsByScaleTypeAndFoodNameAndKg(String paramString1, String paramString2, float paramFloat)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    String[] arrayOfString = new String[3];
    arrayOfString[0] = paramString1;
    arrayOfString[1] = paramString2;
    arrayOfString[2] = String.valueOf(paramFloat);
    Cursor localCursor = localSQLiteDatabase.rawQuery("select *,max(datetime(recordtime,'localtime')) md from userrecord group by useid having scaleType=? and photo=? and rweight=?  order by recordtime desc", arrayOfString);
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      if (!localCursor.moveToNext())
      {
        localCursor.close();
        this.dbs.close();
        return localArrayList;
      }
      Records localRecords = new Records(localCursor.getInt(localCursor.getColumnIndex("id")), localCursor.getInt(localCursor.getColumnIndex("useid")), localCursor.getString(localCursor.getColumnIndex("scaleType")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("md")), localCursor.getString(localCursor.getColumnIndex("comparerecord")), localCursor.getFloat(localCursor.getColumnIndex("rweight")), localCursor.getFloat(localCursor.getColumnIndex("rbmi")), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage")));
      String str = localCursor.getString(localCursor.getColumnIndex("photo"));
      if ((str != null) && (str.length() > 3)) {
        localRecords.setRphoto(str);
      }
      localArrayList.add(localRecords);
    }
  }
  
  public List<Records> getAllDatas()
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select *,max(datetime(recordtime,'localtime')) md from userrecord group by ugroup having scaleType=? order by md desc ", new String[] { "ce" });
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      if (!localCursor.moveToNext())
      {
        localCursor.close();
        this.dbs.close();
        return localArrayList;
      }
      Records localRecords = new Records(localCursor.getInt(localCursor.getColumnIndex("id")), localCursor.getInt(localCursor.getColumnIndex("useid")), localCursor.getString(localCursor.getColumnIndex("scaleType")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("md")), localCursor.getString(localCursor.getColumnIndex("comparerecord")), localCursor.getFloat(localCursor.getColumnIndex("rweight")), localCursor.getFloat(localCursor.getColumnIndex("rbmi")), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage")));
      String str = localCursor.getString(localCursor.getColumnIndex("photo"));
      if ((str != null) && (str.length() > 3)) {
        localRecords.setRphoto(str);
      }
      localArrayList.add(localRecords);
    }
  }
  
  public List<Records> getAllDatasByScaleAndGroup(String paramString1, String paramString2, float paramFloat)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select *,datetime(recordtime,'localtime') md from userrecord where scaleType=? and ugroup=? order by recordtime desc ", new String[] { paramString1, paramString2 });
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      if (!localCursor.moveToNext())
      {
        localCursor.close();
        this.dbs.close();
        return localArrayList;
      }
      int i = localCursor.getInt(localCursor.getColumnIndex("id"));
      int j = localCursor.getInt(localCursor.getColumnIndex("useid"));
      String str1 = localCursor.getString(localCursor.getColumnIndex("scaleType"));
      String str2 = localCursor.getString(localCursor.getColumnIndex("ugroup"));
      String str3 = localCursor.getString(localCursor.getColumnIndex("md"));
      String str4 = localCursor.getString(localCursor.getColumnIndex("comparerecord"));
      float f = localCursor.getFloat(localCursor.getColumnIndex("rweight"));
      Records localRecords = new Records(i, j, str1, str2, str3, str4, f, UtilTooth.myround(UtilTooth.countBMI2(f, paramFloat / 100.0F)), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage")));
      String str5 = localCursor.getString(localCursor.getColumnIndex("photo"));
      if ((str5 != null) && (str5.length() > 3)) {
        localRecords.setRphoto(str5);
      }
      localArrayList.add(localRecords);
    }
  }
  
  public List<Records> getAllDatasByScaleAndGroupDesc(String paramString1, String paramString2, float paramFloat)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select *,datetime(recordtime,'localtime') md from userrecord where scaleType=? and ugroup=? order by recordtime asc ", new String[] { paramString1, paramString2 });
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      if (!localCursor.moveToNext())
      {
        localCursor.close();
        this.dbs.close();
        return localArrayList;
      }
      int i = localCursor.getInt(localCursor.getColumnIndex("id"));
      int j = localCursor.getInt(localCursor.getColumnIndex("useid"));
      String str1 = localCursor.getString(localCursor.getColumnIndex("scaleType"));
      String str2 = localCursor.getString(localCursor.getColumnIndex("ugroup"));
      String str3 = localCursor.getString(localCursor.getColumnIndex("md"));
      String str4 = localCursor.getString(localCursor.getColumnIndex("comparerecord"));
      float f = localCursor.getFloat(localCursor.getColumnIndex("rweight"));
      Records localRecords = new Records(i, j, str1, str2, str3, str4, f, UtilTooth.myround(UtilTooth.countBMI2(f, paramFloat / 100.0F)), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage")));
      String str5 = localCursor.getString(localCursor.getColumnIndex("photo"));
      if ((str5 != null) && (str5.length() > 3)) {
        localRecords.setRphoto(str5);
      }
      localArrayList.add(localRecords);
    }
  }
  
  public List<Records> getAllDatasByScaleAndIDAsc(String paramString, int paramInt, float paramFloat)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = paramString;
    arrayOfString[1] = String.valueOf(paramInt);
    Cursor localCursor = localSQLiteDatabase.rawQuery("select *,datetime(recordtime,'localtime') md from userrecord where scaleType=? and useid=? order by recordtime asc ", arrayOfString);
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      if (!localCursor.moveToNext())
      {
        localCursor.close();
        this.dbs.close();
        return localArrayList;
      }
      Records localRecords = new Records(localCursor.getInt(localCursor.getColumnIndex("id")), localCursor.getInt(localCursor.getColumnIndex("useid")), localCursor.getString(localCursor.getColumnIndex("scaleType")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("md")), localCursor.getString(localCursor.getColumnIndex("comparerecord")), localCursor.getFloat(localCursor.getColumnIndex("rweight")), localCursor.getFloat(localCursor.getColumnIndex("rbmi")), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage")));
      String str = localCursor.getString(localCursor.getColumnIndex("photo"));
      if ((str != null) && (str.length() > 3)) {
        localRecords.setRphoto(str);
      }
      localArrayList.add(localRecords);
    }
  }
  
  public List<Records> getAllDatasByScaleAndIDDesc(String paramString, int paramInt, float paramFloat)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = paramString;
    arrayOfString[1] = String.valueOf(paramInt);
    Cursor localCursor = localSQLiteDatabase.rawQuery("select * from userrecord where scaleType=? and useid=? order by recordtime desc ", arrayOfString);
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      if (!localCursor.moveToNext())
      {
        localCursor.close();
        this.dbs.close();
        return localArrayList;
      }
      Records localRecords = new Records(localCursor.getInt(localCursor.getColumnIndex("id")), localCursor.getInt(localCursor.getColumnIndex("useid")), localCursor.getString(localCursor.getColumnIndex("scaleType")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("recordtime")), localCursor.getString(localCursor.getColumnIndex("comparerecord")), localCursor.getFloat(localCursor.getColumnIndex("rweight")), localCursor.getFloat(localCursor.getColumnIndex("rbmi")), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage")));
      String str = localCursor.getString(localCursor.getColumnIndex("photo"));
      if ((str != null) && (str.length() > 3)) {
        localRecords.setRphoto(str);
      }
      localArrayList.add(localRecords);
    }
  }
  
  public Long getCount(int paramInt)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramInt);
    Cursor localCursor = localSQLiteDatabase.rawQuery("select count(*) from userrecord where useid=?", arrayOfString);
    localCursor.moveToFirst();
    Long localLong = Long.valueOf(localCursor.getLong(0));
    localCursor.close();
    return localLong;
  }
  
  public List<Records> getScrollData(int paramInt1, int paramInt2, int paramInt3)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    String[] arrayOfString = new String[3];
    arrayOfString[0] = String.valueOf(paramInt3);
    arrayOfString[1] = String.valueOf(paramInt1);
    arrayOfString[2] = String.valueOf(paramInt2);
    Cursor localCursor = localSQLiteDatabase.rawQuery("select id, useid,scaleType,ugroup,recordtime,datetime(recordtime,'localtime'),comparerecord,rweight, rbmi, rbone, rbodyfat, rmuscle, rbodywater, rvisceralfat, rbmr,bodyage  from userrecord where useid=? order by recordtime desc  limit ?,?", arrayOfString);
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      if (!localCursor.moveToNext())
      {
        localCursor.close();
        this.dbs.close();
        return localArrayList;
      }
      localArrayList.add(new Records(localCursor.getInt(localCursor.getColumnIndex("id")), paramInt3, localCursor.getString(localCursor.getColumnIndex("scaleType")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("datetime(recordtime,'localtime')")), localCursor.getString(localCursor.getColumnIndex("comparerecord")), localCursor.getFloat(localCursor.getColumnIndex("rweight")), localCursor.getFloat(localCursor.getColumnIndex("rbmi")), localCursor.getFloat(localCursor.getColumnIndex("rbone")), localCursor.getFloat(localCursor.getColumnIndex("rbodyfat")), localCursor.getFloat(localCursor.getColumnIndex("rmuscle")), localCursor.getFloat(localCursor.getColumnIndex("rbodywater")), localCursor.getFloat(localCursor.getColumnIndex("rvisceralfat")), localCursor.getFloat(localCursor.getColumnIndex("rbmr")), localCursor.getFloat(localCursor.getColumnIndex("bodyage"))));
    }
  }
  
  public void save(Records paramRecords)
  {
    if (paramRecords != null) {
      try
      {
        if (paramRecords.getUseId() > 0)
        {
          this.dbs = this.dbHelper.getWritableDatabase();
          if ((paramRecords.getRecordTime() != null) && (paramRecords.getRecordTime().length() > 0))
          {
            SQLiteDatabase localSQLiteDatabase2 = this.dbs;
            Object[] arrayOfObject2 = new Object[15];
            arrayOfObject2[0] = Integer.valueOf(paramRecords.getUseId());
            arrayOfObject2[1] = paramRecords.getScaleType();
            arrayOfObject2[2] = paramRecords.getUgroup();
            arrayOfObject2[3] = paramRecords.getRecordTime();
            arrayOfObject2[4] = paramRecords.getCompareRecord();
            arrayOfObject2[5] = Float.valueOf(paramRecords.getRweight());
            arrayOfObject2[6] = Float.valueOf(paramRecords.getRbmi());
            arrayOfObject2[7] = Float.valueOf(paramRecords.getRbone());
            arrayOfObject2[8] = Float.valueOf(paramRecords.getRbodyfat());
            arrayOfObject2[9] = Float.valueOf(paramRecords.getRmuscle());
            arrayOfObject2[10] = Float.valueOf(paramRecords.getRbodywater());
            arrayOfObject2[11] = Float.valueOf(paramRecords.getRvisceralfat());
            arrayOfObject2[12] = Float.valueOf(paramRecords.getRbmr());
            arrayOfObject2[13] = Float.valueOf(paramRecords.getBodyAge());
            arrayOfObject2[14] = paramRecords.getRphoto();
            localSQLiteDatabase2.execSQL("insert into userrecord(useid,scaleType,ugroup,recordtime,comparerecord,rweight, rbmi, rbone, rbodyfat, rmuscle, rbodywater, rvisceralfat, rbmr,bodyage,photo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", arrayOfObject2);
          }
          for (;;)
          {
            this.dbs.close();
            return;
            SQLiteDatabase localSQLiteDatabase1 = this.dbs;
            Object[] arrayOfObject1 = new Object[14];
            arrayOfObject1[0] = Integer.valueOf(paramRecords.getUseId());
            arrayOfObject1[1] = paramRecords.getScaleType();
            arrayOfObject1[2] = paramRecords.getUgroup();
            arrayOfObject1[3] = paramRecords.getCompareRecord();
            arrayOfObject1[4] = Float.valueOf(paramRecords.getRweight());
            arrayOfObject1[5] = Float.valueOf(paramRecords.getRbmi());
            arrayOfObject1[6] = Float.valueOf(paramRecords.getRbone());
            arrayOfObject1[7] = Float.valueOf(paramRecords.getRbodyfat());
            arrayOfObject1[8] = Float.valueOf(paramRecords.getRmuscle());
            arrayOfObject1[9] = Float.valueOf(paramRecords.getRbodywater());
            arrayOfObject1[10] = Float.valueOf(paramRecords.getRvisceralfat());
            arrayOfObject1[11] = Float.valueOf(paramRecords.getRbmr());
            arrayOfObject1[12] = Float.valueOf(paramRecords.getBodyAge());
            arrayOfObject1[13] = paramRecords.getRphoto();
            localSQLiteDatabase1.execSQL("insert into userrecord(useid,scaleType,ugroup,comparerecord,rweight, rbmi, rbone, rbodyfat, rmuscle, rbodywater, rvisceralfat, rbmr,bodyage,photo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", arrayOfObject1);
          }
        }
        return;
      }
      catch (Exception localException)
      {
        Log.e("RecordService", "save Record failed" + localException.getMessage());
      }
    }
  }
  
  public void update(Records paramRecords)
    throws Exception
  {
    this.dbs = this.dbHelper.getWritableDatabase();
    this.dbs.beginTransaction();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    Object[] arrayOfObject = new Object[13];
    arrayOfObject[0] = Integer.valueOf(paramRecords.getUseId());
    arrayOfObject[1] = paramRecords.getScaleType();
    arrayOfObject[2] = paramRecords.getUgroup();
    arrayOfObject[3] = Float.valueOf(paramRecords.getRweight());
    arrayOfObject[4] = Float.valueOf(paramRecords.getRbmi());
    arrayOfObject[5] = Float.valueOf(paramRecords.getRbone());
    arrayOfObject[6] = Float.valueOf(paramRecords.getRbodyfat());
    arrayOfObject[7] = Float.valueOf(paramRecords.getRmuscle());
    arrayOfObject[8] = Float.valueOf(paramRecords.getRbodywater());
    arrayOfObject[9] = Float.valueOf(paramRecords.getRvisceralfat());
    arrayOfObject[10] = Float.valueOf(paramRecords.getRbmr());
    arrayOfObject[11] = Float.valueOf(paramRecords.getBodyAge());
    arrayOfObject[12] = Integer.valueOf(paramRecords.getId());
    localSQLiteDatabase.execSQL("update  userrecord set useid=?,scaleType=?,ugroup=?,recordtime=now(),comparerecord=?,rweight=?, rbmi=?, rbone=?, rbodyfat=?, rmuscle=?, rbodywater=?, rvisceralfat=?, rbmr=?,bodyage=? where id=? ", arrayOfObject);
    this.dbs.setTransactionSuccessful();
    this.dbs.endTransaction();
  }
  
  public void updatePhoto(int paramInt, String paramString)
  {
    this.dbs = this.dbHelper.getWritableDatabase();
    this.dbs.beginTransaction();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramString;
    arrayOfObject[1] = Integer.valueOf(paramInt);
    localSQLiteDatabase.execSQL("update  userrecord set photo=? where id=? ", arrayOfObject);
    this.dbs.setTransactionSuccessful();
    this.dbs.endTransaction();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\service\RecordService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */