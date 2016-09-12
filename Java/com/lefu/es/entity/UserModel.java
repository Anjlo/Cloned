package com.lefu.es.entity;

import java.io.Serializable;

public class UserModel
  implements Serializable
{
  private static final long serialVersionUID = -8295238475381724571L;
  private int ageMonth = 0;
  private int ageYear = 0;
  private float bheigth = 0.0F;
  private String birth;
  private String danwei = "kg";
  private String group;
  private int id;
  private String level;
  private int number;
  private String per_photo;
  private String scaleType;
  private String sex;
  private float targweight;
  private String uniqueID;
  private String userName;
  
  public UserModel() {}
  
  public UserModel(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, float paramFloat, int paramInt2, int paramInt3, int paramInt4, String paramString5, String paramString6)
  {
    this.id = paramInt1;
    this.userName = paramString1;
    this.group = paramString2;
    this.sex = paramString3;
    this.level = paramString4;
    this.bheigth = paramFloat;
    this.ageYear = paramInt2;
    this.ageMonth = paramInt3;
    this.number = paramInt4;
    this.scaleType = paramString5;
    this.uniqueID = paramString6;
  }
  
  public UserModel(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, float paramFloat, int paramInt2, int paramInt3, int paramInt4, String paramString5, String paramString6, String paramString7)
  {
    this.id = paramInt1;
    this.userName = paramString1;
    this.group = paramString2;
    this.sex = paramString3;
    this.level = paramString4;
    this.bheigth = paramFloat;
    this.ageYear = paramInt2;
    this.ageMonth = paramInt3;
    this.number = paramInt4;
    this.scaleType = paramString5;
    this.uniqueID = paramString6;
    this.birth = paramString7;
  }
  
  public UserModel(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, float paramFloat, int paramInt2, int paramInt3, int paramInt4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    this.id = paramInt1;
    this.userName = paramString1;
    this.group = paramString2;
    this.sex = paramString3;
    this.level = paramString4;
    this.bheigth = paramFloat;
    this.ageYear = paramInt2;
    this.ageMonth = paramInt3;
    this.number = paramInt4;
    this.scaleType = paramString5;
    this.uniqueID = paramString6;
    this.birth = paramString7;
    this.per_photo = paramString8;
  }
  
  public UserModel(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, float paramFloat1, int paramInt2, int paramInt3, int paramInt4, String paramString5, String paramString6, String paramString7, String paramString8, float paramFloat2)
  {
    this.id = paramInt1;
    this.userName = paramString1;
    this.group = paramString2;
    this.sex = paramString3;
    this.level = paramString4;
    this.bheigth = paramFloat1;
    this.ageYear = paramInt2;
    this.ageMonth = paramInt3;
    this.number = paramInt4;
    this.scaleType = paramString5;
    this.uniqueID = paramString6;
    this.birth = paramString7;
    this.per_photo = paramString8;
    this.targweight = paramFloat2;
  }
  
  public UserModel(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, float paramFloat1, int paramInt2, int paramInt3, int paramInt4, String paramString5, String paramString6, String paramString7, String paramString8, float paramFloat2, String paramString9)
  {
    this.id = paramInt1;
    this.userName = paramString1;
    this.group = paramString2;
    this.sex = paramString3;
    this.level = paramString4;
    this.bheigth = paramFloat1;
    this.ageYear = paramInt2;
    this.ageMonth = paramInt3;
    this.number = paramInt4;
    this.scaleType = paramString5;
    this.uniqueID = paramString6;
    this.birth = paramString7;
    this.per_photo = paramString8;
    this.targweight = paramFloat2;
    this.danwei = paramString9;
  }
  
  public UserModel(String paramString1, String paramString2, String paramString3, String paramString4, float paramFloat, int paramInt1, int paramInt2, int paramInt3, String paramString5)
  {
    this.userName = paramString1;
    this.group = paramString2;
    this.sex = paramString3;
    this.level = paramString4;
    this.bheigth = paramFloat;
    this.ageYear = paramInt1;
    this.ageMonth = paramInt2;
    this.number = paramInt3;
    this.scaleType = paramString5;
  }
  
  public UserModel(String paramString1, String paramString2, String paramString3, String paramString4, float paramFloat, int paramInt1, int paramInt2, int paramInt3, String paramString5, String paramString6)
  {
    this.userName = paramString1;
    this.group = paramString2;
    this.sex = paramString3;
    this.level = paramString4;
    this.bheigth = paramFloat;
    this.ageYear = paramInt1;
    this.ageMonth = paramInt2;
    this.number = paramInt3;
    this.scaleType = paramString5;
    this.uniqueID = paramString6;
  }
  
  public UserModel(String paramString1, String paramString2, String paramString3, String paramString4, float paramFloat, int paramInt1, int paramInt2, int paramInt3, String paramString5, String paramString6, String paramString7)
  {
    this.userName = paramString1;
    this.group = paramString2;
    this.sex = paramString3;
    this.level = paramString4;
    this.bheigth = paramFloat;
    this.ageYear = paramInt1;
    this.ageMonth = paramInt2;
    this.number = paramInt3;
    this.scaleType = paramString5;
    this.uniqueID = paramString6;
    this.birth = paramString7;
  }
  
  public int getAgeMonth()
  {
    return this.ageMonth;
  }
  
  public int getAgeYear()
  {
    return this.ageYear;
  }
  
  public float getBheigth()
  {
    return this.bheigth;
  }
  
  public String getBirth()
  {
    return this.birth;
  }
  
  public String getDanwei()
  {
    return this.danwei;
  }
  
  public String getGroup()
  {
    return this.group;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public String getLevel()
  {
    return this.level;
  }
  
  public int getNumber()
  {
    return this.number;
  }
  
  public String getPer_photo()
  {
    return this.per_photo;
  }
  
  public String getScaleType()
  {
    return this.scaleType;
  }
  
  public String getSex()
  {
    return this.sex;
  }
  
  public float getTargweight()
  {
    return this.targweight;
  }
  
  public String getUniqueID()
  {
    if ((this.uniqueID != null) && (this.uniqueID.length() > 0)) {
      return this.uniqueID;
    }
    return "";
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setAgeMonth(int paramInt)
  {
    this.ageMonth = paramInt;
  }
  
  public void setAgeYear(int paramInt)
  {
    this.ageYear = paramInt;
  }
  
  public void setBheigth(float paramFloat)
  {
    this.bheigth = paramFloat;
  }
  
  public void setBirth(String paramString)
  {
    this.birth = paramString;
  }
  
  public void setDanwei(String paramString)
  {
    this.danwei = paramString;
  }
  
  public void setGroup(String paramString)
  {
    this.group = paramString;
  }
  
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
  
  public void setLevel(String paramString)
  {
    this.level = paramString;
  }
  
  public void setNumber(int paramInt)
  {
    this.number = paramInt;
  }
  
  public void setPer_photo(String paramString)
  {
    this.per_photo = paramString;
  }
  
  public void setScaleType(String paramString)
  {
    this.scaleType = paramString;
  }
  
  public void setSex(String paramString)
  {
    this.sex = paramString;
  }
  
  public void setTargweight(float paramFloat)
  {
    this.targweight = paramFloat;
  }
  
  public void setUniqueID(String paramString)
  {
    this.uniqueID = paramString;
  }
  
  public void setUserName(String paramString)
  {
    this.userName = paramString;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\entity\UserModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */