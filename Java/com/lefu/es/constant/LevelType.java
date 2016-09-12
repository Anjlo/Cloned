package com.lefu.es.constant;

public enum LevelType
{
  PROFESSIONAL("Professional", 2131361863),  AMAEUR("Amaeur", 2131361862),  ORDINARY("Ordinary", 2131361861);
  
  private int id;
  private String value;
  
  private LevelType(String paramString1, int paramInt1)
  {
    this.id = paramInt1;
    this.value = paramString1;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public String getValue()
  {
    return this.value;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\constant\LevelType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */