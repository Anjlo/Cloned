package com.lefu.es.util;

import com.lefu.es.constant.UtilConstants;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.RecordService;

public class MyUtil
{
  private static Records lastRecod;
  
  private static String getBone(int paramInt1, int paramInt2)
  {
    return UtilTooth.myroundString(0.01D * (100.0D * (0.1D * paramInt1 / (0.1D * paramInt2))) * (0.1D * paramInt2));
  }
  
  public static String getUserInfo()
  {
    UserModel localUserModel = UtilConstants.CURRENT_USER;
    if (localUserModel == null) {
      return null;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(localUserModel.getGroup().replace("P", "0"));
    localStringBuffer.append("0").append(localUserModel.getSex());
    localStringBuffer.append("0").append(localUserModel.getLevel());
    int i = (int)localUserModel.getBheigth();
    int j;
    if (i > 15)
    {
      localStringBuffer.append(Integer.toHexString(i));
      j = localUserModel.getAgeYear();
      if (j <= 15) {
        break label194;
      }
      localStringBuffer.append(Integer.toHexString(j));
      label109:
      if (!localUserModel.getDanwei().equals(UtilConstants.UNIT_KG)) {
        break label222;
      }
      localStringBuffer.append("01");
    }
    for (;;)
    {
      String str = StringUtils.getBCC(StringUtils.hexStringToByteArray(localStringBuffer.toString()));
      return "FE" + localStringBuffer.toString() + str;
      localStringBuffer.append("0" + Integer.toHexString(i));
      break;
      label194:
      localStringBuffer.append("0" + Integer.toHexString(j));
      break label109;
      label222:
      if (localUserModel.getDanwei().equals(UtilConstants.UNIT_LB)) {
        localStringBuffer.append("02");
      } else if (localUserModel.getDanwei().equals(UtilConstants.UNIT_ST)) {
        localStringBuffer.append("04");
      } else if (localUserModel.getDanwei().equals(UtilConstants.UNIT_FATLB)) {
        localStringBuffer.append("02");
      } else {
        localStringBuffer.append("01");
      }
    }
  }
  
  public static Records parseMeaage(RecordService paramRecordService, String paramString)
  {
    Records localRecords = new Records();
    localRecords.setScaleType(paramString.substring(0, 2));
    localRecords.setUgroup("P" + StringUtils.hexToTen(paramString.substring(3, 4)));
    localRecords.setLevel(StringUtils.hexToTen(paramString.substring(2, 3)));
    String str = StringUtils.hexToBirary(paramString.substring(4, 6));
    int m;
    if (str.length() < 8) {
      m = str.length();
    }
    for (;;)
    {
      int i;
      int j;
      label443:
      float f1;
      label469:
      float f2;
      label494:
      float f3;
      label519:
      float f4;
      label544:
      float f5;
      if (m >= 8)
      {
        localRecords.setSex(str.substring(0, 1));
        localRecords.setsAge(StringUtils.binaryToTen(str.substring(1)));
        localRecords.setsHeight(StringUtils.hexToTen(paramString.substring(6, 8)));
        i = StringUtils.hexToTen(paramString.substring(8, 12));
        localRecords.setSweight(0.1D * i);
        localRecords.setSbodyfat(0.1D * StringUtils.hexToTen(paramString.substring(12, 16)));
        localRecords.setSbone(0.1D * StringUtils.hexToTen(paramString.substring(16, 18)));
        localRecords.setSmuscle(0.1D * StringUtils.hexToTen(paramString.substring(18, 22)));
        localRecords.setSvisceralfat(StringUtils.hexToTen(paramString.substring(22, 24)));
        localRecords.setSbodywater(0.1D * StringUtils.hexToTen(paramString.substring(24, 28)));
        localRecords.setSbmr(1 * StringUtils.hexToTen(paramString.substring(28, 32)));
        if (paramString.length() > 32) {
          localRecords.setBodyAge(1 * StringUtils.hexToTen(paramString.substring(32, 34)));
        }
        if (!StringUtils.isNumber(localRecords.getSbmr())) {
          break label755;
        }
        j = Integer.parseInt(localRecords.getSbmr()) / 1;
        localRecords.setRbmr(j);
        if (!StringUtils.isNumber(localRecords.getSbodyfat())) {
          break label761;
        }
        f1 = Float.parseFloat(localRecords.getSbodyfat());
        localRecords.setRbodyfat(f1);
        if (!StringUtils.isNumber(localRecords.getSbodywater())) {
          break label767;
        }
        f2 = Float.parseFloat(localRecords.getSbodywater());
        localRecords.setRbodywater(f2);
        if (!StringUtils.isNumber(localRecords.getSbone())) {
          break label773;
        }
        f3 = Float.parseFloat(localRecords.getSbone());
        localRecords.setRbone(f3);
        if (!StringUtils.isNumber(localRecords.getSmuscle())) {
          break label779;
        }
        f4 = Float.parseFloat(localRecords.getSmuscle());
        localRecords.setRmuscle(f4);
        boolean bool = StringUtils.isNumber(localRecords.getSvisceralfat());
        int k = 0;
        if (bool) {
          k = Integer.parseInt(localRecords.getSvisceralfat()) / 1;
        }
        localRecords.setRvisceralfat(k);
        if (!StringUtils.isNumber(localRecords.getSweight())) {
          break label785;
        }
        f5 = Float.parseFloat(localRecords.getSweight());
        label604:
        localRecords.setRweight(f5);
        if (!UtilConstants.BABY_SCALE.equals(localRecords.getScaleType())) {
          break label791;
        }
        localRecords.setRweight(UtilTooth.myround2((float)(0.1D * localRecords.getRweight())));
        label640:
        if (!UtilConstants.KITCHEN_SCALE.equals(localRecords.getScaleType())) {
          break label872;
        }
        localRecords.setSbmi("0.0");
        localRecords.setRbmi(0.0F);
      }
      try
      {
        for (;;)
        {
          if (!UtilConstants.KITCHEN_SCALE.equals(localRecords.getScaleType())) {
            lastRecod = paramRecordService.findLastRecordsByScaleType(localRecords.getScaleType(), localRecords.getUgroup());
          }
          if (lastRecod == null) {
            break label943;
          }
          localRecords.setCompareRecord(UtilTooth.myround(localRecords.getRweight() - lastRecod.getRweight()));
          return localRecords;
          str = "0" + str;
          m++;
          break;
          label755:
          j = 0;
          break label443;
          label761:
          f1 = 0.0F;
          break label469;
          label767:
          f2 = 0.0F;
          break label494;
          label773:
          f3 = 0.0F;
          break label519;
          label779:
          f4 = 0.0F;
          break label544;
          label785:
          f5 = 0.0F;
          break label604;
          label791:
          if (UtilConstants.KITCHEN_SCALE.equals(localRecords.getScaleType()))
          {
            localRecords.setSweight(i);
            localRecords.setRweight(Float.parseFloat(localRecords.getSweight()));
            localRecords.setUnitType(StringUtils.hexToTen(paramString.substring(12, 14)));
            break label640;
          }
          localRecords.setRweight(UtilTooth.myround((float)(0.1D * localRecords.getRweight())));
          break label640;
          label872:
          if ((StringUtils.isNumber(localRecords.getsHeight())) && (!"0".equals(localRecords.getsHeight()))) {
            localRecords.setSbmi(UtilTooth.countBMI(localRecords.getRweight(), Float.parseFloat(localRecords.getsHeight()) / 100.0F));
          }
          if (StringUtils.isNumber(localRecords.getSbmi())) {
            localRecords.setRbmi(UtilTooth.myround(Float.parseFloat(localRecords.getSbmi())));
          }
        }
        label943:
        localRecords.setCompareRecord("0.0");
        return localRecords;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return localRecords;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\util\MyUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */