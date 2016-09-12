package com.lefu.es.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.lefu.es.db.DBOpenHelper;
import com.lefu.es.entity.UserModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class UserService
{
  private DBOpenHelper dbHelper;
  private SQLiteDatabase dbs;
  List<String> gros = new ArrayList(Arrays.asList(new String[] { "P0", "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P9" }));
  
  public UserService(Context paramContext)
  {
    this.dbHelper = new DBOpenHelper(paramContext);
  }
  
  public void closeDB()
  {
    if (this.dbHelper != null)
    {
      this.dbHelper.close();
      this.dbHelper = null;
    }
  }
  
  public void delete(int paramInt)
    throws Exception
  {
    this.dbs = this.dbHelper.getWritableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    localSQLiteDatabase.execSQL("delete from user where id=?", arrayOfObject);
    this.dbs.close();
  }
  
  public UserModel find(int paramInt)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramInt);
    Cursor localCursor = localSQLiteDatabase.rawQuery("select * from user where id=?", arrayOfString);
    UserModel localUserModel = null;
    if (localCursor != null)
    {
      boolean bool = localCursor.moveToFirst();
      localUserModel = null;
      if (bool) {
        localUserModel = new UserModel(paramInt, localCursor.getString(localCursor.getColumnIndex("username")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("sex")), localCursor.getString(localCursor.getColumnIndex("level")), localCursor.getFloat(localCursor.getColumnIndex("bheigth")), localCursor.getInt(localCursor.getColumnIndex("ageyear")), localCursor.getInt(localCursor.getColumnIndex("agemonth")), localCursor.getInt(localCursor.getColumnIndex("number")), localCursor.getString(localCursor.getColumnIndex("scaletype")), localCursor.getString(localCursor.getColumnIndex("uniqueid")), localCursor.getString(localCursor.getColumnIndex("birth")), localCursor.getString(localCursor.getColumnIndex("per_photo")), localCursor.getFloat(localCursor.getColumnIndex("targweight")), localCursor.getString(localCursor.getColumnIndex("danwei")));
      }
    }
    localCursor.close();
    this.dbs.close();
    return localUserModel;
  }
  
  public UserModel findUserByGupandScale(String paramString1, String paramString2)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select * from user where ugroup=? and scaletype=?", new String[] { paramString1, paramString2 });
    boolean bool = localCursor.moveToFirst();
    UserModel localUserModel = null;
    if (bool) {
      localUserModel = new UserModel(localCursor.getInt(localCursor.getColumnIndex("id")), localCursor.getString(localCursor.getColumnIndex("username")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("sex")), localCursor.getString(localCursor.getColumnIndex("level")), localCursor.getFloat(localCursor.getColumnIndex("bheigth")), localCursor.getInt(localCursor.getColumnIndex("ageyear")), localCursor.getInt(localCursor.getColumnIndex("agemonth")), localCursor.getInt(localCursor.getColumnIndex("number")), localCursor.getString(localCursor.getColumnIndex("scaletype")), localCursor.getString(localCursor.getColumnIndex("uniqueid")), localCursor.getString(localCursor.getColumnIndex("birth")), localCursor.getString(localCursor.getColumnIndex("per_photo")), localCursor.getFloat(localCursor.getColumnIndex("targweight")), localCursor.getString(localCursor.getColumnIndex("danwei")));
    }
    localCursor.close();
    this.dbs.close();
    return localUserModel;
  }
  
  public String getAddUserGroup(String paramString)
  {
    try
    {
      List localList = getAllUserGroupByScaleType(paramString);
      Iterator localIterator = this.gros.iterator();
      String str;
      boolean bool;
      do
      {
        if (!localIterator.hasNext()) {
          return "";
        }
        str = (String)localIterator.next();
        bool = localList.contains(str);
      } while (bool);
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public List<UserModel> getAllDatas()
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select * from user ", null);
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      if (!localCursor.moveToNext())
      {
        localCursor.close();
        this.dbs.close();
        return localArrayList;
      }
      localArrayList.add(new UserModel(localCursor.getInt(localCursor.getColumnIndex("id")), localCursor.getString(localCursor.getColumnIndex("username")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("sex")), localCursor.getString(localCursor.getColumnIndex("level")), localCursor.getFloat(localCursor.getColumnIndex("bheigth")), localCursor.getInt(localCursor.getColumnIndex("ageyear")), localCursor.getInt(localCursor.getColumnIndex("agemonth")), localCursor.getInt(localCursor.getColumnIndex("number")), localCursor.getString(localCursor.getColumnIndex("scaletype")), localCursor.getString(localCursor.getColumnIndex("uniqueid")), localCursor.getString(localCursor.getColumnIndex("birth")), localCursor.getString(localCursor.getColumnIndex("per_photo")), localCursor.getFloat(localCursor.getColumnIndex("targweight")), localCursor.getString(localCursor.getColumnIndex("danwei"))));
    }
  }
  
  public List<UserModel> getAllUserByScaleType()
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select * from user", null);
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
      String str1 = localCursor.getString(localCursor.getColumnIndex("username"));
      String str2 = localCursor.getString(localCursor.getColumnIndex("ugroup"));
      String str3 = localCursor.getString(localCursor.getColumnIndex("sex"));
      String str4 = localCursor.getString(localCursor.getColumnIndex("level"));
      float f1 = localCursor.getFloat(localCursor.getColumnIndex("bheigth"));
      int j = localCursor.getInt(localCursor.getColumnIndex("ageyear"));
      int k = localCursor.getInt(localCursor.getColumnIndex("agemonth"));
      int m = localCursor.getInt(localCursor.getColumnIndex("number"));
      String str5 = localCursor.getString(localCursor.getColumnIndex("uniqueid"));
      String str6 = localCursor.getString(localCursor.getColumnIndex("birth"));
      String str7 = localCursor.getString(localCursor.getColumnIndex("per_photo"));
      float f2 = localCursor.getFloat(localCursor.getColumnIndex("targweight"));
      String str8 = localCursor.getString(localCursor.getColumnIndex("danwei"));
      localArrayList.add(new UserModel(i, str1, str2, str3, str4, f1, j, k, m, localCursor.getString(localCursor.getColumnIndex("scaletype")), str5, str6, str7, f2, str8));
    }
  }
  
  public List<String> getAllUserGroupByScaleType(String paramString)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select * from user where scaletype = ? order by ugroup ", new String[] { paramString });
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      if (!localCursor.moveToNext())
      {
        localCursor.close();
        this.dbs.close();
        return localArrayList;
      }
      localArrayList.add(localCursor.getString(localCursor.getColumnIndex("ugroup")));
    }
  }
  
  public Long getCount()
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select count(*) from user", null);
    localCursor.moveToFirst();
    Long localLong = Long.valueOf(localCursor.getLong(0));
    localCursor.close();
    return localLong;
  }
  
  public int getMaxGroup()
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select max(number) from user", new String[0]);
    int i = -1;
    if (localCursor != null) {}
    try
    {
      if (localCursor.moveToFirst())
      {
        int j = localCursor.getInt(localCursor.getColumnIndex("max(number)"));
        i = j;
      }
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    localCursor.close();
    this.dbs.close();
    return i;
  }
  
  public List<UserModel> getScrollData(int paramInt1, int paramInt2)
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = String.valueOf(paramInt1);
    arrayOfString[1] = String.valueOf(paramInt2);
    Cursor localCursor = localSQLiteDatabase.rawQuery("select * from user limit ?,? ", arrayOfString);
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      if (!localCursor.moveToNext())
      {
        localCursor.close();
        this.dbs.close();
        return localArrayList;
      }
      localArrayList.add(new UserModel(localCursor.getInt(localCursor.getColumnIndex("id")), localCursor.getString(localCursor.getColumnIndex("username")), localCursor.getString(localCursor.getColumnIndex("ugroup")), localCursor.getString(localCursor.getColumnIndex("sex")), localCursor.getString(localCursor.getColumnIndex("level")), localCursor.getFloat(localCursor.getColumnIndex("bheigth")), localCursor.getInt(localCursor.getColumnIndex("ageyear")), localCursor.getInt(localCursor.getColumnIndex("agemonth")), localCursor.getInt(localCursor.getColumnIndex("number")), localCursor.getString(localCursor.getColumnIndex("scaletype")), "", localCursor.getString(localCursor.getColumnIndex("birth")), localCursor.getString(localCursor.getColumnIndex("per_photo")), localCursor.getFloat(localCursor.getColumnIndex("targweight")), localCursor.getString(localCursor.getColumnIndex("danwei"))));
    }
  }
  
  public int maxid()
    throws Exception
  {
    this.dbs = this.dbHelper.getReadableDatabase();
    Cursor localCursor = this.dbs.rawQuery("select max(id) from user", null);
    localCursor.moveToFirst();
    int i = localCursor.getInt(0);
    localCursor.close();
    return i;
  }
  
  public void save(UserModel paramUserModel)
    throws Exception
  {
    this.dbs = this.dbHelper.getWritableDatabase();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    Object[] arrayOfObject = new Object[14];
    arrayOfObject[0] = paramUserModel.getUserName();
    arrayOfObject[1] = paramUserModel.getGroup();
    arrayOfObject[2] = paramUserModel.getSex();
    arrayOfObject[3] = paramUserModel.getLevel();
    arrayOfObject[4] = Float.valueOf(paramUserModel.getBheigth());
    arrayOfObject[5] = Integer.valueOf(paramUserModel.getAgeYear());
    arrayOfObject[6] = Integer.valueOf(paramUserModel.getAgeMonth());
    arrayOfObject[7] = Integer.valueOf(paramUserModel.getNumber());
    arrayOfObject[8] = paramUserModel.getScaleType();
    arrayOfObject[9] = paramUserModel.getUniqueID();
    arrayOfObject[10] = paramUserModel.getBirth();
    arrayOfObject[11] = paramUserModel.getPer_photo();
    arrayOfObject[12] = Float.valueOf(paramUserModel.getTargweight());
    arrayOfObject[13] = paramUserModel.getDanwei();
    localSQLiteDatabase.execSQL("insert into user(username,ugroup,sex,level,bheigth,ageyear,agemonth,number,scaletype,uniqueid,birth,per_photo,targweight,danwei) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", arrayOfObject);
    this.dbs.close();
  }
  
  public void update(UserModel paramUserModel)
    throws Exception
  {
    this.dbs = this.dbHelper.getWritableDatabase();
    this.dbs.beginTransaction();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    Object[] arrayOfObject = new Object[15];
    arrayOfObject[0] = paramUserModel.getUserName();
    arrayOfObject[1] = paramUserModel.getGroup();
    arrayOfObject[2] = paramUserModel.getSex();
    arrayOfObject[3] = paramUserModel.getLevel();
    arrayOfObject[4] = Float.valueOf(paramUserModel.getBheigth());
    arrayOfObject[5] = Integer.valueOf(paramUserModel.getAgeYear());
    arrayOfObject[6] = Integer.valueOf(paramUserModel.getAgeMonth());
    arrayOfObject[7] = Integer.valueOf(paramUserModel.getNumber());
    arrayOfObject[8] = paramUserModel.getScaleType();
    arrayOfObject[9] = paramUserModel.getUniqueID();
    arrayOfObject[10] = paramUserModel.getBirth();
    arrayOfObject[11] = paramUserModel.getPer_photo();
    arrayOfObject[12] = Float.valueOf(paramUserModel.getTargweight());
    arrayOfObject[13] = paramUserModel.getDanwei();
    arrayOfObject[14] = Integer.valueOf(paramUserModel.getId());
    localSQLiteDatabase.execSQL("update  user set username=?,ugroup=?,sex=?,level=?,bheigth=?,ageyear=?,agemonth=?,number=?,scaletype=?,uniqueid=?,birth=?,per_photo=?,targweight=?,danwei=? where id=? ", arrayOfObject);
    this.dbs.setTransactionSuccessful();
    this.dbs.endTransaction();
  }
  
  public void update2(UserModel paramUserModel)
    throws Exception
  {
    this.dbs = this.dbHelper.getWritableDatabase();
    this.dbs.beginTransaction();
    SQLiteDatabase localSQLiteDatabase = this.dbs;
    Object[] arrayOfObject = new Object[8];
    arrayOfObject[0] = paramUserModel.getSex();
    arrayOfObject[1] = paramUserModel.getLevel();
    arrayOfObject[2] = Float.valueOf(paramUserModel.getBheigth());
    arrayOfObject[3] = Integer.valueOf(paramUserModel.getAgeYear());
    arrayOfObject[4] = paramUserModel.getUniqueID();
    arrayOfObject[5] = Float.valueOf(paramUserModel.getTargweight());
    arrayOfObject[6] = paramUserModel.getDanwei();
    arrayOfObject[7] = Integer.valueOf(paramUserModel.getId());
    localSQLiteDatabase.execSQL("update  user set sex=?,level=?,bheigth=?,ageyear=? ,uniqueid=? ,targweight=?,danwei=? where id=? ", arrayOfObject);
    this.dbs.setTransactionSuccessful();
    this.dbs.endTransaction();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\service\UserService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */