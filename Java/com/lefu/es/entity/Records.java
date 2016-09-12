package com.lefu.es.entity;

import java.io.Serializable;

public class Records
  implements Serializable
{
  private static final long serialVersionUID = 1781102348190850847L;
  private float bodyAge;
  private String compareRecord;
  private int id;
  public boolean isNull = false;
  private String level;
  private String rPhoto;
  private float rbmi = 0.0F;
  private float rbmr;
  private float rbodyfat;
  private float rbodywater;
  private float rbone;
  private String recordTime;
  private float rmuscle;
  private float rvisceralfat;
  private float rweight;
  private String sAge;
  private String sHeight;
  private String sbmi = "0";
  private String sbmr;
  private String sbodyfat;
  private String sbodywater;
  private String sbone;
  private String scaleType;
  private String sex;
  private String smuscle;
  private String svisceralfat;
  private String sweight;
  private String ugroup;
  private int unitType;
  private int useId;
  private UserModel user;
  
  public Records() {}
  
  public Records(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    this.id = paramInt1;
    this.recordTime = paramString3;
    this.useId = paramInt2;
    this.compareRecord = paramString4;
    this.scaleType = paramString1;
    this.ugroup = paramString2;
    this.rbmi = paramFloat2;
    this.rbmr = paramFloat8;
    this.rbodyfat = paramFloat4;
    this.rbodywater = paramFloat6;
    this.rbone = paramFloat3;
    this.rmuscle = paramFloat5;
    this.rvisceralfat = paramFloat7;
    this.rweight = paramFloat1;
  }
  
  public Records(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    this.id = paramInt1;
    this.recordTime = paramString3;
    this.useId = paramInt2;
    this.compareRecord = paramString4;
    this.scaleType = paramString1;
    this.ugroup = paramString2;
    this.rbmi = paramFloat2;
    this.rbmr = paramFloat8;
    this.rbodyfat = paramFloat4;
    this.rbodywater = paramFloat6;
    this.rbone = paramFloat3;
    this.rmuscle = paramFloat5;
    this.rvisceralfat = paramFloat7;
    this.rweight = paramFloat1;
    this.bodyAge = paramFloat9;
  }
  
  public Records(int paramInt, String paramString1, String paramString2)
  {
    this.isNull = true;
    this.useId = paramInt;
    this.scaleType = paramString1;
    this.ugroup = paramString2;
  }
  
  public Records(int paramInt, String paramString1, String paramString2, String paramString3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    this.useId = paramInt;
    this.compareRecord = paramString3;
    this.scaleType = paramString1;
    this.ugroup = paramString2;
    this.rbmi = paramFloat2;
    this.rbmr = paramFloat8;
    this.rbodyfat = paramFloat4;
    this.rbodywater = paramFloat6;
    this.rbone = paramFloat3;
    this.rmuscle = paramFloat5;
    this.rvisceralfat = paramFloat7;
    this.rweight = paramFloat1;
  }
  
  public Records(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    this.recordTime = paramString3;
    this.useId = paramInt;
    this.compareRecord = paramString4;
    this.scaleType = paramString1;
    this.ugroup = paramString2;
    this.rbmi = paramFloat2;
    this.rbmr = paramFloat8;
    this.rbodyfat = paramFloat4;
    this.rbodywater = paramFloat6;
    this.rbone = paramFloat3;
    this.rmuscle = paramFloat5;
    this.rvisceralfat = paramFloat7;
    this.rweight = paramFloat1;
  }
  
  public Records(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    this.recordTime = paramString3;
    this.useId = paramInt;
    this.compareRecord = paramString4;
    this.scaleType = paramString1;
    this.ugroup = paramString2;
    this.rbmi = paramFloat2;
    this.rbmr = paramFloat8;
    this.rbodyfat = paramFloat4;
    this.rbodywater = paramFloat6;
    this.rbone = paramFloat3;
    this.rmuscle = paramFloat5;
    this.rvisceralfat = paramFloat7;
    this.rweight = paramFloat1;
    this.bodyAge = paramFloat9;
  }
  
  public float getBodyAge()
  {
    return this.bodyAge;
  }
  
  public String getCompareRecord()
  {
    return this.compareRecord;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public String getLevel()
  {
    return this.level;
  }
  
  public float getRbmi()
  {
    return this.rbmi;
  }
  
  public float getRbmr()
  {
    return this.rbmr;
  }
  
  public float getRbodyfat()
  {
    return this.rbodyfat;
  }
  
  public float getRbodywater()
  {
    return this.rbodywater;
  }
  
  public float getRbone()
  {
    return this.rbone;
  }
  
  public String getRecordTime()
  {
    return this.recordTime;
  }
  
  public float getRmuscle()
  {
    return this.rmuscle;
  }
  
  public String getRphoto()
  {
    return this.rPhoto;
  }
  
  public float getRvisceralfat()
  {
    return this.rvisceralfat;
  }
  
  public float getRweight()
  {
    return this.rweight;
  }
  
  public String getSbmi()
  {
    return this.sbmi;
  }
  
  public String getSbmr()
  {
    return this.sbmr;
  }
  
  public String getSbodyfat()
  {
    return this.sbodyfat;
  }
  
  public String getSbodywater()
  {
    return this.sbodywater;
  }
  
  public String getSbone()
  {
    return this.sbone;
  }
  
  public String getScaleType()
  {
    return this.scaleType;
  }
  
  public String getSex()
  {
    return this.sex;
  }
  
  public String getSmuscle()
  {
    return this.smuscle;
  }
  
  public String getSvisceralfat()
  {
    return this.svisceralfat;
  }
  
  public String getSweight()
  {
    return this.sweight;
  }
  
  public String getUgroup()
  {
    return this.ugroup;
  }
  
  public int getUnitType()
  {
    return this.unitType;
  }
  
  public int getUseId()
  {
    return this.useId;
  }
  
  public UserModel getUser()
  {
    return this.user;
  }
  
  public String getsAge()
  {
    return this.sAge;
  }
  
  public String getsHeight()
  {
    return this.sHeight;
  }
  
  public void setBodyAge(float paramFloat)
  {
    this.bodyAge = paramFloat;
  }
  
  public void setCompareRecord(String paramString)
  {
    this.compareRecord = paramString;
  }
  
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
  
  public void setLevel(String paramString)
  {
    this.level = paramString;
  }
  
  public void setRbmi(float paramFloat)
  {
    this.rbmi = paramFloat;
  }
  
  public void setRbmr(float paramFloat)
  {
    this.rbmr = paramFloat;
  }
  
  public void setRbodyfat(float paramFloat)
  {
    this.rbodyfat = paramFloat;
  }
  
  public void setRbodywater(float paramFloat)
  {
    this.rbodywater = paramFloat;
  }
  
  public void setRbone(float paramFloat)
  {
    this.rbone = paramFloat;
  }
  
  public void setRecordTime(String paramString)
  {
    this.recordTime = paramString;
  }
  
  public void setRmuscle(float paramFloat)
  {
    this.rmuscle = paramFloat;
  }
  
  public void setRphoto(String paramString)
  {
    this.rPhoto = paramString;
  }
  
  public void setRvisceralfat(float paramFloat)
  {
    this.rvisceralfat = paramFloat;
  }
  
  public void setRweight(float paramFloat)
  {
    this.rweight = paramFloat;
  }
  
  public void setSbmi(String paramString)
  {
    this.sbmi = paramString;
  }
  
  public void setSbmr(String paramString)
  {
    this.sbmr = paramString;
  }
  
  public void setSbodyfat(String paramString)
  {
    this.sbodyfat = paramString;
  }
  
  public void setSbodywater(String paramString)
  {
    this.sbodywater = paramString;
  }
  
  public void setSbone(String paramString)
  {
    this.sbone = paramString;
  }
  
  public void setScaleType(String paramString)
  {
    this.scaleType = paramString;
  }
  
  public void setSex(String paramString)
  {
    this.sex = paramString;
  }
  
  public void setSmuscle(String paramString)
  {
    this.smuscle = paramString;
  }
  
  public void setSvisceralfat(String paramString)
  {
    this.svisceralfat = paramString;
  }
  
  public void setSweight(String paramString)
  {
    this.sweight = paramString;
  }
  
  public void setUgroup(String paramString)
  {
    this.ugroup = paramString;
  }
  
  public void setUnitType(int paramInt)
  {
    this.unitType = paramInt;
  }
  
  public void setUseId(int paramInt)
  {
    this.useId = paramInt;
  }
  
  public void setUser(UserModel paramUserModel)
  {
    this.user = paramUserModel;
  }
  
  public void setsAge(String paramString)
  {
    this.sAge = paramString;
  }
  
  public void setsHeight(String paramString)
  {
    this.sHeight = paramString;
  }
  
  public String toString()
  {
    return "Records [scaleType=" + this.scaleType + ", ugroup=" + this.ugroup + ", recordTime=" + this.recordTime + ", compareRecord=" + this.compareRecord + ", rweight=" + this.rweight + ", rbmi=" + this.rbmi + ", rbone=" + this.rbone + ", rbodyfat=" + this.rbodyfat + ", rmuscle=" + this.rmuscle + ", rbodywater=" + this.rbodywater + ", rvisceralfat=" + this.rvisceralfat + ", rbmr=" + this.rbmr + ", level=" + this.level + ", sex=" + this.sex + ", sweight=" + this.sweight + ", sbmi=" + this.sbmi + ", sbone=" + this.sbone + ", sbodyfat=" + this.sbodyfat + ", smuscle=" + this.smuscle + ", sbodywater=" + this.sbodywater + ", svisceralfat=" + this.svisceralfat + ", sbmr=" + this.sbmr + ", sHeight=" + this.sHeight + ", sAge=" + this.sAge + "]";
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\entity\Records.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */