package com.lefu.es.db;

import android.text.TextUtils;
import android.widget.Toast;
import com.lefu.es.application.IwellnessApplication;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.entity.NutrientBo;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.RecordService;
import com.lefu.es.service.UserService;
import com.lefu.es.util.MyUtil;
import com.lefu.es.util.StringUtils;
import com.lefu.es.util.Tool;
import com.lefu.es.util.UtilTooth;
import java.util.Date;

public class RecordDao
{
  private static UserService uservice;
  
  public static void dueDate(RecordService paramRecordService, String paramString)
  {
    handleData(paramRecordService, MyUtil.parseMeaage(paramRecordService, paramString), paramString);
  }
  
  public static void dueKitchenDate(RecordService paramRecordService, String paramString, NutrientBo paramNutrientBo)
  {
    handleKitchenData(paramRecordService, MyUtil.parseMeaage(paramRecordService, paramString), paramNutrientBo);
  }
  
  public static void handleData(RecordService paramRecordService, Records paramRecords, String paramString)
  {
    if (paramRecords.getScaleType().equalsIgnoreCase(UtilConstants.BODY_SCALE)) {}
    for (;;)
    {
      try
      {
        localUserModel2 = UtilConstants.CURRENT_USER;
        paramRecords.setUseId(localUserModel2.getId());
        paramRecords.setUgroup(localUserModel2.getGroup());
        if (!StringUtils.isNumber(paramRecords.getSbmr())) {
          break label1415;
        }
        m = Integer.parseInt(paramRecords.getSbmr()) / 1;
        paramRecords.setRbmr(m);
        if (!StringUtils.isNumber(paramRecords.getSbodyfat())) {
          break label1421;
        }
        f6 = Float.parseFloat(paramRecords.getSbodyfat());
        paramRecords.setRbodyfat(f6);
        if (!StringUtils.isNumber(paramRecords.getSbodywater())) {
          break label1427;
        }
        f7 = Float.parseFloat(paramRecords.getSbodywater());
        paramRecords.setRbodywater(f7);
        if (!StringUtils.isNumber(paramRecords.getSbone())) {
          break label1433;
        }
        f8 = Float.parseFloat(paramRecords.getSbone());
        paramRecords.setRbone(f8);
        if (!StringUtils.isNumber(paramRecords.getSmuscle())) {
          break label1439;
        }
        f9 = Float.parseFloat(paramRecords.getSmuscle());
        paramRecords.setRmuscle(f9);
        if (!StringUtils.isNumber(paramRecords.getSvisceralfat())) {
          break label1445;
        }
        n = Integer.parseInt(paramRecords.getSvisceralfat()) / 1;
        paramRecords.setRvisceralfat(n);
        if (!StringUtils.isNumber(paramRecords.getSweight())) {
          break label1451;
        }
        f10 = Float.parseFloat(paramRecords.getSweight());
        paramRecords.setRweight(f10);
        paramRecords.setRecordTime(UtilTooth.dateTimeChange(new Date()));
        if (UtilConstants.BABY_SCALE.equals(paramRecords.getScaleType()))
        {
          paramRecords.setRweight(UtilTooth.myround2((float)(0.1D * paramRecords.getRweight())));
          if (StringUtils.isNumber(paramRecords.getSbmi())) {
            paramRecords.setRbmi(UtilTooth.myround(UtilTooth.countBMI2(paramRecords.getRweight(), Float.parseFloat(paramRecords.getsHeight()) / 100.0F)));
          }
          if ((StringUtils.isNumber(paramRecords.getsHeight())) && (!"0".equals(paramRecords.getsHeight()))) {
            paramRecords.setSbmi(String.valueOf(paramRecords.getRbmi()));
          }
        }
      }
      catch (Exception localException2)
      {
        UserModel localUserModel2;
        Records localRecords3;
        localException2.printStackTrace();
        return;
      }
      try
      {
        localRecords3 = paramRecordService.findLastRecordsByScaleTypeAndUser(paramRecords.getScaleType(), String.valueOf(localUserModel2.getId()));
        if (localRecords3 == null) {
          break label409;
        }
        paramRecords.setCompareRecord(UtilTooth.myround(paramRecords.getRweight() - localRecords3.getRweight()));
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
        continue;
      }
      paramRecordService.save(paramRecords);
      return;
      paramRecords.setRweight(UtilTooth.myround(paramRecords.getRweight()));
      continue;
      label409:
      paramRecords.setCompareRecord("0.0");
      continue;
      UserModel localUserModel1 = UtilConstants.CURRENT_USER;
      Records localRecords1 = new Records();
      localRecords1.setUseId(localUserModel1.getId());
      localRecords1.setScaleType(paramString.substring(0, 2));
      localRecords1.setUgroup("P" + StringUtils.hexToTen(paramString.substring(3, 4)));
      localRecords1.setLevel(StringUtils.hexToTen(paramString.substring(2, 3)));
      String str = StringUtils.hexToBirary(paramString.substring(4, 6));
      int k;
      int i;
      label874:
      float f1;
      label903:
      float f2;
      label931:
      float f3;
      label959:
      float f4;
      label987:
      int j;
      label1017:
      float f5;
      if (str.length() < 8)
      {
        k = str.length();
        if (k < 8) {}
      }
      else
      {
        localRecords1.setSex(str.substring(0, 1));
        localRecords1.setsAge(StringUtils.binaryToTen(str.substring(1)));
        localRecords1.setsHeight(StringUtils.hexToTen(paramString.substring(6, 8)));
        localRecords1.setSweight(0.1D * StringUtils.hexToTen(paramString.substring(8, 12)));
        localRecords1.setSbodyfat(0.1D * StringUtils.hexToTen(paramString.substring(12, 16)));
        localRecords1.setSbone(0.1D * StringUtils.hexToTen(paramString.substring(16, 18)));
        localRecords1.setSmuscle(0.1D * StringUtils.hexToTen(paramString.substring(18, 22)));
        localRecords1.setSvisceralfat(StringUtils.hexToTen(paramString.substring(22, 24)));
        localRecords1.setSbodywater(0.1D * StringUtils.hexToTen(paramString.substring(24, 28)));
        localRecords1.setSbmr(1 * StringUtils.hexToTen(paramString.substring(28, 32)));
        if (!StringUtils.isNumber(localRecords1.getSbmr())) {
          break label1347;
        }
        i = Integer.parseInt(localRecords1.getSbmr()) / 1;
        localRecords1.setRbmr(i);
        if (!StringUtils.isNumber(localRecords1.getSbodyfat())) {
          break label1353;
        }
        f1 = Float.parseFloat(localRecords1.getSbodyfat());
        localRecords1.setRbodyfat(f1);
        if (!StringUtils.isNumber(localRecords1.getSbodywater())) {
          break label1359;
        }
        f2 = Float.parseFloat(localRecords1.getSbodywater());
        localRecords1.setRbodywater(f2);
        if (!StringUtils.isNumber(localRecords1.getSbone())) {
          break label1365;
        }
        f3 = Float.parseFloat(localRecords1.getSbone());
        localRecords1.setRbone(f3);
        if (!StringUtils.isNumber(localRecords1.getSmuscle())) {
          break label1371;
        }
        f4 = Float.parseFloat(localRecords1.getSmuscle());
        localRecords1.setRmuscle(f4);
        if (!StringUtils.isNumber(localRecords1.getSvisceralfat())) {
          break label1377;
        }
        j = Integer.parseInt(localRecords1.getSvisceralfat()) / 1;
        localRecords1.setRvisceralfat(j);
        if (!StringUtils.isNumber(localRecords1.getSweight())) {
          break label1383;
        }
        f5 = Float.parseFloat(localRecords1.getSweight());
        label1046:
        localRecords1.setRweight(f5);
        localRecords1.setRecordTime(UtilTooth.dateTimeChange(new Date()));
        if (!UtilConstants.BABY_SCALE.equals(localRecords1.getScaleType())) {
          break label1389;
        }
        localRecords1.setRweight(UtilTooth.myround2((float)(0.1D * localRecords1.getRweight())));
        label1101:
        if ((StringUtils.isNumber(localRecords1.getsHeight())) && (!"0".equals(localRecords1.getsHeight()))) {
          localRecords1.setSbmi(UtilTooth.countBMI(localRecords1.getRweight(), Float.parseFloat(localRecords1.getsHeight()) / 100.0F));
        }
        if (StringUtils.isNumber(localRecords1.getSbmi())) {
          localRecords1.setRbmi(UtilTooth.myround(UtilTooth.countBMI2(localRecords1.getRweight(), Float.parseFloat(localRecords1.getsHeight()) / 100.0F)));
        }
        if ((StringUtils.isNumber(localRecords1.getsHeight())) && (!"0".equals(localRecords1.getsHeight()))) {
          localRecords1.setSbmi(String.valueOf(localRecords1.getRbmi()));
        }
      }
      for (;;)
      {
        try
        {
          Records localRecords2 = paramRecordService.findLastRecordsByScaleTypeAndUser(localRecords1.getScaleType(), String.valueOf(localUserModel1.getId()));
          if (localRecords2 == null) {
            break label1405;
          }
          localRecords1.setCompareRecord(UtilTooth.myround(localRecords1.getRweight() - localRecords2.getRweight()));
          paramRecordService.save(localRecords1);
          return;
        }
        catch (Exception localException1)
        {
          Toast.makeText(IwellnessApplication.app, "save data failed:" + localException1.getMessage(), 3000).show();
          return;
        }
        str = "0" + str;
        k++;
        break;
        label1347:
        i = 0;
        break label874;
        label1353:
        f1 = 0.0F;
        break label903;
        label1359:
        f2 = 0.0F;
        break label931;
        label1365:
        f3 = 0.0F;
        break label959;
        label1371:
        f4 = 0.0F;
        break label987;
        label1377:
        j = 0;
        break label1017;
        label1383:
        f5 = 0.0F;
        break label1046;
        label1389:
        localRecords1.setRweight(UtilTooth.myround(localRecords1.getRweight()));
        break label1101;
        label1405:
        localRecords1.setCompareRecord("0.0");
      }
      label1415:
      int m = 0;
      continue;
      label1421:
      float f6 = 0.0F;
      continue;
      label1427:
      float f7 = 0.0F;
      continue;
      label1433:
      float f8 = 0.0F;
      continue;
      label1439:
      float f9 = 0.0F;
      continue;
      label1445:
      int n = 0;
      continue;
      label1451:
      float f10 = 0.0F;
    }
  }
  
  public static Records handleKitchenData(RecordService paramRecordService, Records paramRecords, NutrientBo paramNutrientBo)
  {
    UserModel localUserModel;
    if ((paramRecords != null) && (paramRecordService != null))
    {
      uservice = new UserService(IwellnessApplication.app);
      localUserModel = UtilConstants.CURRENT_USER;
      if (localUserModel != null)
      {
        if (paramRecords.getUnitType() != 0) {
          break label580;
        }
        localUserModel.setDanwei(UtilConstants.UNIT_G);
        paramRecords.setUseId(localUserModel.getId());
        paramRecords.setUgroup(localUserModel.getGroup());
      }
      paramRecords.setScaleType(UtilConstants.KITCHEN_SCALE);
      paramRecords.setRecordTime(UtilTooth.dateTimeChange(new Date()));
      if (paramNutrientBo == null) {
        break label652;
      }
      boolean bool1 = TextUtils.isEmpty(paramNutrientBo.getNutrientCalcium());
      float f1 = 0.0F;
      if (!bool1)
      {
        boolean bool16 = Tool.isDigitsOnly(paramNutrientBo.getNutrientCalcium());
        f1 = 0.0F;
        if (bool16) {
          f1 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientCalcium()) * paramRecords.getRweight());
        }
      }
      boolean bool2 = TextUtils.isEmpty(paramNutrientBo.getNutrientProtein());
      float f2 = 0.0F;
      if (!bool2)
      {
        boolean bool15 = Tool.isDigitsOnly(paramNutrientBo.getNutrientProtein());
        f2 = 0.0F;
        if (bool15) {
          f2 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientProtein()) * paramRecords.getRweight());
        }
      }
      boolean bool3 = TextUtils.isEmpty(paramNutrientBo.getNutrientEnerg());
      float f3 = 0.0F;
      if (!bool3)
      {
        boolean bool14 = Tool.isDigitsOnly(paramNutrientBo.getNutrientEnerg());
        f3 = 0.0F;
        if (bool14) {
          f3 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientEnerg()) * paramRecords.getRweight());
        }
      }
      boolean bool4 = TextUtils.isEmpty(paramNutrientBo.getNutrientCarbohydrt());
      float f4 = 0.0F;
      if (!bool4)
      {
        boolean bool13 = Tool.isDigitsOnly(paramNutrientBo.getNutrientCarbohydrt());
        f4 = 0.0F;
        if (bool13) {
          f4 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientCarbohydrt()) * paramRecords.getRweight());
        }
      }
      boolean bool5 = TextUtils.isEmpty(paramNutrientBo.getNutrientLipidTot());
      float f5 = 0.0F;
      if (!bool5)
      {
        boolean bool12 = Tool.isDigitsOnly(paramNutrientBo.getNutrientLipidTot());
        f5 = 0.0F;
        if (bool12) {
          f5 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientLipidTot()) * paramRecords.getRweight());
        }
      }
      boolean bool6 = TextUtils.isEmpty(paramNutrientBo.getNutrientFiberTD());
      float f6 = 0.0F;
      if (!bool6)
      {
        boolean bool11 = Tool.isDigitsOnly(paramNutrientBo.getNutrientFiberTD());
        f6 = 0.0F;
        if (bool11) {
          f6 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientFiberTD()) * paramRecords.getRweight());
        }
      }
      boolean bool7 = TextUtils.isEmpty(paramNutrientBo.getNutrientCholestrl());
      float f7 = 0.0F;
      if (!bool7)
      {
        boolean bool10 = Tool.isDigitsOnly(paramNutrientBo.getNutrientCholestrl());
        f7 = 0.0F;
        if (bool10) {
          f7 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientCholestrl()) * paramRecords.getRweight());
        }
      }
      boolean bool8 = TextUtils.isEmpty(paramNutrientBo.getNutrientVitB6());
      float f8 = 0.0F;
      if (!bool8)
      {
        boolean bool9 = Tool.isDigitsOnly(paramNutrientBo.getNutrientVitB6());
        f8 = 0.0F;
        if (bool9) {
          f8 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientVitB6()) * paramRecords.getRweight());
        }
      }
      paramRecords.setRphoto(paramNutrientBo.getNutrientDesc());
      paramRecords.setRbodywater(f3);
      paramRecords.setRbodyfat(f2);
      paramRecords.setRbone(f5);
      paramRecords.setRmuscle(f4);
      paramRecords.setRvisceralfat(f6);
      paramRecords.setRbmi(f7);
      paramRecords.setRbmr(f8);
      paramRecords.setBodyAge(f1);
    }
    for (;;)
    {
      if (localUserModel != null) {}
      label580:
      try
      {
        if (uservice != null) {
          uservice.update(localUserModel);
        }
        paramRecordService.save(paramRecords);
        return paramRecords;
      }
      catch (Exception localException) {}
      if (paramRecords.getUnitType() == 1)
      {
        localUserModel.setDanwei(UtilConstants.UNIT_LB);
        break;
      }
      if (paramRecords.getUnitType() == 2)
      {
        localUserModel.setDanwei(UtilConstants.UNIT_ML);
        break;
      }
      if (paramRecords.getUnitType() == 4)
      {
        localUserModel.setDanwei(UtilConstants.UNIT_ML2);
        break;
      }
      if (paramRecords.getUnitType() != 3) {
        break;
      }
      localUserModel.setDanwei(UtilConstants.UNIT_FATLB);
      break;
      label652:
      paramRecords.setRbodywater(0.0F);
      paramRecords.setRbodyfat(0.0F);
      paramRecords.setRbone(0.0F);
      paramRecords.setRmuscle(0.0F);
      paramRecords.setRvisceralfat(0.0F);
      paramRecords.setRbmi(0.0F);
      paramRecords.setRbmr(0.0F);
      paramRecords.setBodyAge(0.0F);
    }
    return paramRecords;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\db\RecordDao.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */