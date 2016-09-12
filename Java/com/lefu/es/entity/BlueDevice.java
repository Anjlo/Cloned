package com.lefu.es.entity;

public class BlueDevice
{
  private String deviceName;
  private String uuid;
  
  public BlueDevice() {}
  
  public BlueDevice(String paramString1, String paramString2)
  {
    this.uuid = paramString1;
    this.deviceName = paramString2;
  }
  
  public String getDeviceName()
  {
    return this.deviceName;
  }
  
  public String getUuid()
  {
    return this.uuid;
  }
  
  public void setDeviceName(String paramString)
  {
    this.deviceName = paramString;
  }
  
  public void setUuid(String paramString)
  {
    this.uuid = paramString;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\entity\BlueDevice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */