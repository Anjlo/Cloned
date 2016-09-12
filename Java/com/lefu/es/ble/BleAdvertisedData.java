package com.lefu.es.ble;

import java.util.List;
import java.util.UUID;

public class BleAdvertisedData
{
  private String mName;
  private List<UUID> mUuids;
  
  public BleAdvertisedData(List<UUID> paramList, String paramString)
  {
    this.mUuids = paramList;
    this.mName = paramString;
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public List<UUID> getUuids()
  {
    return this.mUuids;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\ble\BleAdvertisedData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */