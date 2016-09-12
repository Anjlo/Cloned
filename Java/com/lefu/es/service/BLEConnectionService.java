package com.lefu.es.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;
import com.lefu.es.ble.BlueSingleton;

public class BLEConnectionService
  extends Service
{
  BluetoothAdapter adapter;
  BlueSingleton blueSingleton;
  boolean isScan = false;
  Thread myThread = new Thread()
  {
    public void run()
    {
      super.run();
      BLEConnectionService.this.isScan = BLEConnectionService.this.blueSingleton.getmScanning();
      for (;;)
      {
        if (!BLEConnectionService.this.isScan)
        {
          BLEConnectionService.this.adapter.startLeScan(BLEConnectionService.this.blueSingleton.mLeScanCallback);
          try
          {
            Thread.sleep(3000L);
          }
          catch (InterruptedException localInterruptedException)
          {
            localInterruptedException.printStackTrace();
          }
        }
      }
    }
  };
  
  public IBinder onBind(Intent paramIntent)
  {
    this.myThread.start();
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.blueSingleton = BlueSingleton.getInstance(null);
    this.adapter = BluetoothAdapter.getDefaultAdapter();
  }
  
  public void onDestroy()
  {
    super.onDestroy();
  }
  
  public void onStart(Intent paramIntent, int paramInt)
  {
    super.onStart(paramIntent, paramInt);
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }
  
  public boolean onUnbind(Intent paramIntent)
  {
    return super.onUnbind(paramIntent);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\service\BLEConnectionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */