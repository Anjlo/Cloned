package com.lefu.es.cache;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.lefu.es.entity.BlueDevice;
import com.lefu.es.entity.NutrientBo;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.RecordService;
import com.lefu.es.service.UserService;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CacheHelper
{
  private static final String TAG = "CacheHelper";
  public static List<BlueDevice> devicesList;
  public static List<NutrientBo> nutrientList;
  public static List<String> nutrientNameList;
  public static List<NutrientBo> nutrientTempList = new ArrayList();
  public static List<String> nutrientTempNameList;
  public static List<Records> recordLastList;
  public static List<Records> recordList;
  public static List<Records> recordListDesc;
  private static RecordService recordService;
  public static List<UserModel> userList = new ArrayList();
  private static UserService userService;
  
  static
  {
    recordList = new ArrayList();
    recordListDesc = new ArrayList();
    recordLastList = new ArrayList();
    devicesList = new ArrayList();
    nutrientList = new ArrayList();
    nutrientNameList = new ArrayList();
    nutrientTempNameList = new ArrayList();
  }
  
  public static void cacheAllNutrientsDate(Context paramContext, SQLiteDatabase paramSQLiteDatabase)
  {
    if (paramSQLiteDatabase != null)
    {
      queryAllTempNutrients(paramContext, paramSQLiteDatabase);
      queryAllNutrients(paramContext, paramSQLiteDatabase);
    }
  }
  
  public static ArrayList<NutrientBo> findNutrientByName(String paramString)
  {
    Object localObject;
    if ((TextUtils.isEmpty(paramString)) || (nutrientList == null) || (nutrientList.size() == 0)) {
      localObject = null;
    }
    for (;;)
    {
      return (ArrayList<NutrientBo>)localObject;
      localObject = new ArrayList();
      Iterator localIterator = nutrientList.iterator();
      while (localIterator.hasNext())
      {
        NutrientBo localNutrientBo = (NutrientBo)localIterator.next();
        if ((!TextUtils.isEmpty(localNutrientBo.getNutrientDesc())) && (localNutrientBo.getNutrientDesc().contains(paramString))) {
          ((ArrayList)localObject).add(localNutrientBo);
        }
      }
    }
  }
  
  public static Records getRecordLast(int paramInt)
  {
    Iterator localIterator;
    if ((recordListDesc != null) && (recordListDesc.size() > 0)) {
      localIterator = recordListDesc.iterator();
    }
    Records localRecords;
    do
    {
      if (!localIterator.hasNext()) {
        return null;
      }
      localRecords = (Records)localIterator.next();
    } while (localRecords.getId() != paramInt);
    return localRecords;
  }
  
  public static List<Records> getRecordLastListByScaleType(String paramString)
  {
    List localList = recordLastList;
    ArrayList localArrayList = null;
    Iterator localIterator;
    if (localList != null)
    {
      int i = recordLastList.size();
      localArrayList = null;
      if (i > 0)
      {
        localArrayList = new ArrayList();
        localIterator = localArrayList.iterator();
      }
    }
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        Collections.sort(localArrayList, new SortByName());
        return localArrayList;
      }
      Records localRecords = (Records)localIterator.next();
      if (localRecords.getScaleType().equals(paramString)) {
        localArrayList.add(localRecords);
      }
    }
  }
  
  public static UserModel getUserByGroandScaleType(String paramString1, String paramString2)
  {
    Iterator localIterator;
    if ((userList != null) && (userList.size() > 0) && (paramString1 != null) && (paramString2 != null)) {
      localIterator = userList.iterator();
    }
    UserModel localUserModel;
    do
    {
      if (!localIterator.hasNext()) {
        return null;
      }
      localUserModel = (UserModel)localIterator.next();
    } while ((!paramString1.equals(localUserModel.getGroup())) || (!paramString2.equals(localUserModel.getScaleType())));
    return localUserModel;
  }
  
  public static UserModel getUserById(int paramInt)
  {
    Iterator localIterator;
    if ((userList != null) && (userList.size() > 0) && (paramInt != 0)) {
      localIterator = userList.iterator();
    }
    UserModel localUserModel;
    do
    {
      if (!localIterator.hasNext()) {
        return null;
      }
      localUserModel = (UserModel)localIterator.next();
    } while (localUserModel.getId() != paramInt);
    return localUserModel;
  }
  
  public static void intiCaches()
  {
    if (userList == null)
    {
      userList = new ArrayList();
      if (recordList != null) {
        break label103;
      }
      recordList = new ArrayList();
      label32:
      if (recordLastList != null) {
        break label114;
      }
      recordLastList = new ArrayList();
      label48:
      if (devicesList != null) {
        break label125;
      }
      devicesList = new ArrayList();
    }
    for (;;)
    {
      try
      {
        userList = userService.getAllDatas();
        recordList = recordService.getAllDatas();
        recordLastList = recordService.findLastRecords();
        return;
      }
      catch (Exception localException)
      {
        label103:
        label114:
        label125:
        Log.e("CacheHelper", localException.getMessage());
        localException.printStackTrace();
      }
      userList.clear();
      break;
      recordList.clear();
      break label32;
      recordLastList.clear();
      break label48;
      devicesList.clear();
    }
  }
  
  public static void main(String[] paramArrayOfString)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("P6");
    localArrayList.add("P1");
    localArrayList.add("P3");
    localArrayList.add("P5");
    localArrayList.add("P4");
    localArrayList.add("P2");
    Collections.sort(localArrayList);
    Iterator localIterator = localArrayList.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      String str = (String)localIterator.next();
      System.out.println(str);
    }
  }
  
  private static void queryAllNutrients(Context paramContext, SQLiteDatabase paramSQLiteDatabase)
  {
    if (paramSQLiteDatabase != null) {
      try
      {
        Cursor localCursor = paramSQLiteDatabase.rawQuery("select * from base_nutrient", null);
        for (;;)
        {
          if (!localCursor.moveToNext())
          {
            localCursor.close();
            Log.e("queryAllNutrients*****", "获取到营养缓存数据共: " + nutrientList.size());
            return;
          }
          NutrientBo localNutrientBo = new NutrientBo();
          localNutrientBo.setNutrientNo(localCursor.getString(localCursor.getColumnIndex("nutrientNo")));
          localNutrientBo.setNutrientDesc(localCursor.getString(localCursor.getColumnIndex("nutrientDesc")));
          localNutrientBo.setNutrientWater(localCursor.getString(localCursor.getColumnIndex("nutrientWater")));
          localNutrientBo.setNutrientEnerg(localCursor.getString(localCursor.getColumnIndex("nutrientEnerg")));
          localNutrientBo.setNutrientProtein(localCursor.getString(localCursor.getColumnIndex("nutrientProtein")));
          localNutrientBo.setNutrientLipidTot(localCursor.getString(localCursor.getColumnIndex("nutrientLipidTot")));
          localNutrientBo.setNutrientAsh(localCursor.getString(localCursor.getColumnIndex("nutrientAsh")));
          localNutrientBo.setNutrientCarbohydrt(localCursor.getString(localCursor.getColumnIndex("nutrientCarbohydrt")));
          localNutrientBo.setNutrientFiberTD(localCursor.getString(localCursor.getColumnIndex("nutrientFiberTD")));
          localNutrientBo.setNutrientSugarTot(localCursor.getString(localCursor.getColumnIndex("nutrientSugarTot")));
          localNutrientBo.setNutrientCalcium(localCursor.getString(localCursor.getColumnIndex("nutrientCalcium")));
          localNutrientBo.setNutrientIron(localCursor.getString(localCursor.getColumnIndex("nutrientIron")));
          localNutrientBo.setNutrientMagnesium(localCursor.getString(localCursor.getColumnIndex("nutrientMagnesium")));
          localNutrientBo.setNutrientPhosphorus(localCursor.getString(localCursor.getColumnIndex("nutrientPhosphorus")));
          localNutrientBo.setNutrientPotassium(localCursor.getString(localCursor.getColumnIndex("nutrientPotassium")));
          localNutrientBo.setNutrientSodium(localCursor.getString(localCursor.getColumnIndex("nutrientSodium")));
          localNutrientBo.setNutrientZinc(localCursor.getString(localCursor.getColumnIndex("nutrientZinc")));
          localNutrientBo.setNutrientCopper(localCursor.getString(localCursor.getColumnIndex("nutrientCopper")));
          localNutrientBo.setNutrientManganese(localCursor.getString(localCursor.getColumnIndex("nutrientManganese")));
          localNutrientBo.setNutrientSelenium(localCursor.getString(localCursor.getColumnIndex("nutrientSelenium")));
          localNutrientBo.setNutrientVitc(localCursor.getString(localCursor.getColumnIndex("nutrientVitc")));
          localNutrientBo.setNutrientThiamin(localCursor.getString(localCursor.getColumnIndex("nutrientThiamin")));
          localNutrientBo.setNutrientRiboflavin(localCursor.getString(localCursor.getColumnIndex("nutrientRiboflavin")));
          localNutrientBo.setNutrientNiacin(localCursor.getString(localCursor.getColumnIndex("nutrientNiacin")));
          localNutrientBo.setNutrientPantoAcid(localCursor.getString(localCursor.getColumnIndex("nutrientPantoAcid")));
          localNutrientBo.setNutrientVitB6(localCursor.getString(localCursor.getColumnIndex("nutrientVitB6")));
          localNutrientBo.setNutrientFolateTot(localCursor.getString(localCursor.getColumnIndex("nutrientFolateTot")));
          localNutrientBo.setNutrientFolicAcid(localCursor.getString(localCursor.getColumnIndex("nutrientFolicAcid")));
          localNutrientBo.setNutrientFoodFolate(localCursor.getString(localCursor.getColumnIndex("nutrientFoodFolate")));
          localNutrientBo.setNutrientFolateDfe(localCursor.getString(localCursor.getColumnIndex("nutrientFolateDfe")));
          localNutrientBo.setNutrientCholineTot(localCursor.getString(localCursor.getColumnIndex("nutrientCholineTot")));
          localNutrientBo.setNutrientVitB12(localCursor.getString(localCursor.getColumnIndex("nutrientVitB12")));
          localNutrientBo.setNutrientVitAiu(localCursor.getString(localCursor.getColumnIndex("nutrientVitAiu")));
          localNutrientBo.setNutrientVitArae(localCursor.getString(localCursor.getColumnIndex("nutrientVitArae")));
          localNutrientBo.setNutrientRetinol(localCursor.getString(localCursor.getColumnIndex("nutrientRetinol")));
          localNutrientBo.setNutrientAlphaCarot(localCursor.getString(localCursor.getColumnIndex("nutrientAlphaCarot")));
          localNutrientBo.setNutrientBetaCarot(localCursor.getString(localCursor.getColumnIndex("nutrientBetaCarot")));
          localNutrientBo.setNutrientBetaCrypt(localCursor.getString(localCursor.getColumnIndex("nutrientBetaCrypt")));
          localNutrientBo.setNutrientLycopene(localCursor.getString(localCursor.getColumnIndex("nutrientLycopene")));
          localNutrientBo.setNutrientLutZea(localCursor.getString(localCursor.getColumnIndex("nutrientLutZea")));
          localNutrientBo.setNutrientVite(localCursor.getString(localCursor.getColumnIndex("nutrientVite")));
          localNutrientBo.setNutrientVitd(localCursor.getString(localCursor.getColumnIndex("nutrientVitd")));
          localNutrientBo.setNutrientVitDiu(localCursor.getString(localCursor.getColumnIndex("nutrientVitDiu")));
          localNutrientBo.setNutrientVitK(localCursor.getString(localCursor.getColumnIndex("nutrientVitK")));
          localNutrientBo.setNutrientFaSat(localCursor.getString(localCursor.getColumnIndex("nutrientFaSat")));
          localNutrientBo.setNutrientFaMono(localCursor.getString(localCursor.getColumnIndex("nutrientFaMono")));
          localNutrientBo.setNutrientFaPoly(localCursor.getString(localCursor.getColumnIndex("nutrientFaPoly")));
          localNutrientBo.setNutrientCholestrl(localCursor.getString(localCursor.getColumnIndex("nutrientCholestrl")));
          localNutrientBo.setNutrientGmWt1(localCursor.getString(localCursor.getColumnIndex("nutrientGmWt1")));
          localNutrientBo.setNutrientGmWtDesc1(localCursor.getString(localCursor.getColumnIndex("nutrientGmWtDesc1")));
          localNutrientBo.setNutrientGmWt2(localCursor.getString(localCursor.getColumnIndex("nutrientGmWt2")));
          localNutrientBo.setNutrientGmWtDesc2(localCursor.getString(localCursor.getColumnIndex("nutrientGmWtDesc2")));
          localNutrientBo.setNutrientRefusePct(localCursor.getString(localCursor.getColumnIndex("nutrientRefusePct")));
          if (!TextUtils.isEmpty(localNutrientBo.getNutrientDesc()))
          {
            nutrientNameList.add(localNutrientBo.getNutrientDesc());
            nutrientList.add(localNutrientBo);
          }
        }
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  public static List<NutrientBo> queryAllNutrientsByName(String paramString)
  {
    List localList = nutrientList;
    ArrayList localArrayList = null;
    Iterator localIterator;
    if (localList != null)
    {
      int i = nutrientList.size();
      localArrayList = null;
      if (i > 0)
      {
        localArrayList = new ArrayList();
        localIterator = nutrientList.iterator();
      }
    }
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return localArrayList;
      }
      NutrientBo localNutrientBo = (NutrientBo)localIterator.next();
      if ((!TextUtils.isEmpty(localNutrientBo.getNutrientDesc())) && (localNutrientBo.getNutrientDesc().contains(paramString))) {
        localArrayList.add(localNutrientBo);
      }
    }
  }
  
  public static List<NutrientBo> queryAllNutrientsByPager(int paramInt)
  {
    if ((nutrientList != null) && (nutrientList.size() > 0))
    {
      int i = paramInt - 1;
      if (i < 0) {
        i = 0;
      }
      int j = i * 30;
      int k = j + 30;
      if (k >= -1 + nutrientList.size()) {
        k = nutrientList.size();
      }
      return nutrientList.subList(j, k);
    }
    return null;
  }
  
  private static void queryAllTempNutrients(Context paramContext, SQLiteDatabase paramSQLiteDatabase)
  {
    if (paramSQLiteDatabase != null) {
      try
      {
        Cursor localCursor = paramSQLiteDatabase.rawQuery("select * from base_nutrient limit 0,100", null);
        for (;;)
        {
          if (!localCursor.moveToNext())
          {
            localCursor.close();
            Log.e("queryAllTempNutrients*****", "获取到营养临时缓存数据共: " + nutrientTempList.size());
            return;
          }
          NutrientBo localNutrientBo = new NutrientBo();
          localNutrientBo.setNutrientNo(localCursor.getString(localCursor.getColumnIndex("nutrientNo")));
          localNutrientBo.setNutrientDesc(localCursor.getString(localCursor.getColumnIndex("nutrientDesc")));
          localNutrientBo.setNutrientWater(localCursor.getString(localCursor.getColumnIndex("nutrientWater")));
          localNutrientBo.setNutrientEnerg(localCursor.getString(localCursor.getColumnIndex("nutrientEnerg")));
          localNutrientBo.setNutrientProtein(localCursor.getString(localCursor.getColumnIndex("nutrientProtein")));
          localNutrientBo.setNutrientLipidTot(localCursor.getString(localCursor.getColumnIndex("nutrientLipidTot")));
          localNutrientBo.setNutrientAsh(localCursor.getString(localCursor.getColumnIndex("nutrientAsh")));
          localNutrientBo.setNutrientCarbohydrt(localCursor.getString(localCursor.getColumnIndex("nutrientCarbohydrt")));
          localNutrientBo.setNutrientFiberTD(localCursor.getString(localCursor.getColumnIndex("nutrientFiberTD")));
          localNutrientBo.setNutrientSugarTot(localCursor.getString(localCursor.getColumnIndex("nutrientSugarTot")));
          localNutrientBo.setNutrientCalcium(localCursor.getString(localCursor.getColumnIndex("nutrientCalcium")));
          localNutrientBo.setNutrientIron(localCursor.getString(localCursor.getColumnIndex("nutrientIron")));
          localNutrientBo.setNutrientMagnesium(localCursor.getString(localCursor.getColumnIndex("nutrientMagnesium")));
          localNutrientBo.setNutrientPhosphorus(localCursor.getString(localCursor.getColumnIndex("nutrientPhosphorus")));
          localNutrientBo.setNutrientPotassium(localCursor.getString(localCursor.getColumnIndex("nutrientPotassium")));
          localNutrientBo.setNutrientSodium(localCursor.getString(localCursor.getColumnIndex("nutrientSodium")));
          localNutrientBo.setNutrientZinc(localCursor.getString(localCursor.getColumnIndex("nutrientZinc")));
          localNutrientBo.setNutrientCopper(localCursor.getString(localCursor.getColumnIndex("nutrientCopper")));
          localNutrientBo.setNutrientManganese(localCursor.getString(localCursor.getColumnIndex("nutrientManganese")));
          localNutrientBo.setNutrientSelenium(localCursor.getString(localCursor.getColumnIndex("nutrientSelenium")));
          localNutrientBo.setNutrientVitc(localCursor.getString(localCursor.getColumnIndex("nutrientVitc")));
          localNutrientBo.setNutrientThiamin(localCursor.getString(localCursor.getColumnIndex("nutrientThiamin")));
          localNutrientBo.setNutrientRiboflavin(localCursor.getString(localCursor.getColumnIndex("nutrientRiboflavin")));
          localNutrientBo.setNutrientNiacin(localCursor.getString(localCursor.getColumnIndex("nutrientNiacin")));
          localNutrientBo.setNutrientPantoAcid(localCursor.getString(localCursor.getColumnIndex("nutrientPantoAcid")));
          localNutrientBo.setNutrientVitB6(localCursor.getString(localCursor.getColumnIndex("nutrientVitB6")));
          localNutrientBo.setNutrientFolateTot(localCursor.getString(localCursor.getColumnIndex("nutrientFolateTot")));
          localNutrientBo.setNutrientFolicAcid(localCursor.getString(localCursor.getColumnIndex("nutrientFolicAcid")));
          localNutrientBo.setNutrientFoodFolate(localCursor.getString(localCursor.getColumnIndex("nutrientFoodFolate")));
          localNutrientBo.setNutrientFolateDfe(localCursor.getString(localCursor.getColumnIndex("nutrientFolateDfe")));
          localNutrientBo.setNutrientCholineTot(localCursor.getString(localCursor.getColumnIndex("nutrientCholineTot")));
          localNutrientBo.setNutrientVitB12(localCursor.getString(localCursor.getColumnIndex("nutrientVitB12")));
          localNutrientBo.setNutrientVitAiu(localCursor.getString(localCursor.getColumnIndex("nutrientVitAiu")));
          localNutrientBo.setNutrientVitArae(localCursor.getString(localCursor.getColumnIndex("nutrientVitArae")));
          localNutrientBo.setNutrientRetinol(localCursor.getString(localCursor.getColumnIndex("nutrientRetinol")));
          localNutrientBo.setNutrientAlphaCarot(localCursor.getString(localCursor.getColumnIndex("nutrientAlphaCarot")));
          localNutrientBo.setNutrientBetaCarot(localCursor.getString(localCursor.getColumnIndex("nutrientBetaCarot")));
          localNutrientBo.setNutrientBetaCrypt(localCursor.getString(localCursor.getColumnIndex("nutrientBetaCrypt")));
          localNutrientBo.setNutrientLycopene(localCursor.getString(localCursor.getColumnIndex("nutrientLycopene")));
          localNutrientBo.setNutrientLutZea(localCursor.getString(localCursor.getColumnIndex("nutrientLutZea")));
          localNutrientBo.setNutrientVite(localCursor.getString(localCursor.getColumnIndex("nutrientVite")));
          localNutrientBo.setNutrientVitd(localCursor.getString(localCursor.getColumnIndex("nutrientVitd")));
          localNutrientBo.setNutrientVitDiu(localCursor.getString(localCursor.getColumnIndex("nutrientVitDiu")));
          localNutrientBo.setNutrientVitK(localCursor.getString(localCursor.getColumnIndex("nutrientVitK")));
          localNutrientBo.setNutrientFaSat(localCursor.getString(localCursor.getColumnIndex("nutrientFaSat")));
          localNutrientBo.setNutrientFaMono(localCursor.getString(localCursor.getColumnIndex("nutrientFaMono")));
          localNutrientBo.setNutrientFaPoly(localCursor.getString(localCursor.getColumnIndex("nutrientFaPoly")));
          localNutrientBo.setNutrientCholestrl(localCursor.getString(localCursor.getColumnIndex("nutrientCholestrl")));
          localNutrientBo.setNutrientGmWt1(localCursor.getString(localCursor.getColumnIndex("nutrientGmWt1")));
          localNutrientBo.setNutrientGmWtDesc1(localCursor.getString(localCursor.getColumnIndex("nutrientGmWtDesc1")));
          localNutrientBo.setNutrientGmWt2(localCursor.getString(localCursor.getColumnIndex("nutrientGmWt2")));
          localNutrientBo.setNutrientGmWtDesc2(localCursor.getString(localCursor.getColumnIndex("nutrientGmWtDesc2")));
          localNutrientBo.setNutrientRefusePct(localCursor.getString(localCursor.getColumnIndex("nutrientRefusePct")));
          if (!TextUtils.isEmpty(localNutrientBo.getNutrientDesc()))
          {
            nutrientTempNameList.add(localNutrientBo.getNutrientDesc());
            nutrientTempList.add(localNutrientBo);
          }
        }
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  public static NutrientBo queryNutrientsByName(String paramString)
  {
    Iterator localIterator;
    if ((!TextUtils.isEmpty(paramString)) && (nutrientList != null) && (nutrientList.size() > 0)) {
      localIterator = nutrientList.iterator();
    }
    NutrientBo localNutrientBo;
    do
    {
      if (!localIterator.hasNext()) {
        return null;
      }
      localNutrientBo = (NutrientBo)localIterator.next();
    } while ((TextUtils.isEmpty(localNutrientBo.getNutrientDesc())) || (!localNutrientBo.getNutrientDesc().equals(paramString)));
    return localNutrientBo;
  }
  
  public static boolean updateUserById(UserModel paramUserModel)
  {
    Iterator localIterator;
    if ((userList != null) && (userList.size() > 0) && (paramUserModel != null)) {
      localIterator = userList.iterator();
    }
    UserModel localUserModel;
    do
    {
      if (!localIterator.hasNext()) {
        return false;
      }
      localUserModel = (UserModel)localIterator.next();
    } while (localUserModel.getId() != paramUserModel.getId());
    userList.remove(localUserModel);
    userList.add(paramUserModel);
    return true;
  }
  
  static class SortByName
    implements Comparator
  {
    public int compare(Object paramObject1, Object paramObject2)
    {
      Records localRecords1 = (Records)paramObject1;
      Records localRecords2 = (Records)paramObject2;
      return localRecords1.getUgroup().compareTo(localRecords2.getUgroup());
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\cache\CacheHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */