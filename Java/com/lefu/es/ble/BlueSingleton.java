package com.lefu.es.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import java.io.PrintStream;
import java.io.Serializable;

public class BlueSingleton
  implements Serializable
{
  static boolean isdoings = false;
  private static final long serialVersionUID = -7953127527312783591L;
  private static BlueSingleton uniqueInstance = null;
  private Activity activity;
  public boolean isExit = false;
  public boolean isSearch = false;
  private BluetoothAdapter mBluetoothAdapter;
  private boolean mConnected = false;
  public BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback()
  {
    public void onLeScan(final BluetoothDevice paramAnonymousBluetoothDevice, int paramAnonymousInt, final byte[] paramAnonymousArrayOfByte)
    {
      new Runnable()
      {
        public void run()
        {
          try
          {
            BleAdvertisedData localBleAdvertisedData = BleUtil.parseAdertisedData(paramAnonymousArrayOfByte);
            String str = paramAnonymousBluetoothDevice.getName();
            if (str == null) {
              str = localBleAdvertisedData.getName();
            }
            if ((paramAnonymousBluetoothDevice != null) && (str != null) && (str.indexOf("Scale") >= 0))
            {
              System.out.println("发现BLE称=" + str + "[" + paramAnonymousBluetoothDevice.getAddress() + "]");
              BlueSingleton.this.isSearch = true;
              if (BlueSingleton.this.mScanning)
              {
                BlueSingleton.this.mBluetoothAdapter.stopLeScan(BlueSingleton.this.mLeScanCallback);
                BlueSingleton.this.mScanning = false;
              }
              com.lefu.es.constant.BluetoolUtil.mDeviceAddress = paramAnonymousBluetoothDevice.getAddress();
              BlueSingleton.this.activity.getApplicationContext().bindService(new Intent(BlueSingleton.this.activity, BluetoothLeService.class), BlueSingleton.this.mServiceConnection, 1);
            }
            return;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
      }.run();
    }
  };
  private boolean mScanning = false;
  private ServiceConnection mServiceConnection;
  
  public static BlueSingleton getInstance(Handler paramHandler)
  {
    if (uniqueInstance == null) {
      uniqueInstance = new BlueSingleton();
    }
    return uniqueInstance;
  }
  
  public static boolean isIsdoing()
  {
    try
    {
      boolean bool = isdoings;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static void setIsdoing(boolean paramBoolean)
  {
    try
    {
      isdoings = paramBoolean;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getSearchState()
  {
    return this.isSearch;
  }
  
  public boolean getmConnected()
  {
    return this.mConnected;
  }
  
  public boolean getmScanning()
  {
    return this.mScanning;
  }
  
  public void linkout()
  {
    if ((this.mScanning) && (!this.mConnected)) {
      scanLeDevice(false, this.activity, this.mServiceConnection);
    }
  }
  
  public void scanLeDevice(boolean paramBoolean, Activity paramActivity, ServiceConnection paramServiceConnection)
  {
    this.activity = paramActivity;
    this.mServiceConnection = paramServiceConnection;
    BluetoothManager localBluetoothManager = (BluetoothManager)paramActivity.getSystemService("bluetooth");
    if (this.mBluetoothAdapter == null) {
      this.mBluetoothAdapter = localBluetoothManager.getAdapter();
    }
    if (paramBoolean)
    {
      this.mScanning = true;
      new ScanThread().start();
      return;
    }
    this.mScanning = false;
    this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
  }
  
  public void setmConnected(boolean paramBoolean)
  {
    this.mConnected = paramBoolean;
  }
  
  public void setmScanning(boolean paramBoolean)
  {
    this.mScanning = paramBoolean;
  }
  
  public void setmServiceConnection(ServiceConnection paramServiceConnection)
  {
    this.mServiceConnection = paramServiceConnection;
  }
  
  class ScanThread
    extends Thread
  {
    public ScanThread() {}
    
    public void run()
    {
      super.run();
      for (;;)
      {
        if ((BlueSingleton.this.isExit) || (BlueSingleton.this.getSearchState())) {
          return;
        }
        BlueSingleton.this.mScanning = true;
        BlueSingleton.this.mBluetoothAdapter.startLeScan(BlueSingleton.this.mLeScanCallback);
        try
        {
          Thread.sleep(3000L);
          if (BlueSingleton.this.getSearchState()) {
            continue;
          }
          BlueSingleton.this.mScanning = false;
          BlueSingleton.this.mBluetoothAdapter.stopLeScan(BlueSingleton.this.mLeScanCallback);
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;)
          {
            localInterruptedException.printStackTrace();
          }
        }
      }
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\ble\BlueSingleton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */