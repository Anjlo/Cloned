package com.lefu.es.entity;

public class UserGroupModel
{
  public static UserGroupModel group0 = new UserGroupModel("P0", "P0");
  public static UserGroupModel group1 = new UserGroupModel("P1", "P1");
  public static UserGroupModel group2 = new UserGroupModel("P2", "P2");
  public static UserGroupModel group3 = new UserGroupModel("P3", "P3");
  public static UserGroupModel group4 = new UserGroupModel("P4", "P4");
  public static UserGroupModel group5 = new UserGroupModel("P5", "P5");
  public static UserGroupModel group6 = new UserGroupModel("P6", "P6");
  public static UserGroupModel group7 = new UserGroupModel("P7", "P7");
  public static UserGroupModel group8 = new UserGroupModel("P8", "P8");
  private String groupName;
  private String groupNumber;
  private int id;
  
  public UserGroupModel() {}
  
  public UserGroupModel(String paramString1, String paramString2)
  {
    this.groupName = paramString2;
    this.groupNumber = paramString1;
  }
  
  public String getGroupName()
  {
    return this.groupName;
  }
  
  public String getGroupNumber()
  {
    return this.groupNumber;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setGroupName(String paramString)
  {
    this.groupName = paramString;
  }
  
  public void setGroupNumber(String paramString)
  {
    this.groupNumber = paramString;
  }
  
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\entity\UserGroupModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */