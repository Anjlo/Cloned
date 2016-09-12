package com.lefu.es.entity;

public class Email
{
  private String email;
  private int id;
  private String name;
  
  public Email(int paramInt, String paramString1, String paramString2)
  {
    this.id = paramInt;
    this.name = paramString1;
    this.email = paramString2;
  }
  
  public Email(String paramString1, String paramString2)
  {
    this.name = paramString1;
    this.email = paramString2;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setEmail(String paramString)
  {
    this.email = paramString;
  }
  
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\entity\Email.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */