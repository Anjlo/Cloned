package com.lefu.es.ble;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.lefu.es.util.StringUtils;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@SuppressLint({"NewApi"})
public class BluetoothLeService
  extends Service
{
  private static final String TAG = BluetoothLeService.class.getSimpleName();
  private final IBinder mBinder = new LocalBinder();
  private BluetoothAdapter mBluetoothAdapter;
  private String mBluetoothDeviceAddress;
  private BluetoothGatt mBluetoothGatt;
  private BluetoothManager mBluetoothManager;
  private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback()
  {
    public void onCharacteristicChanged(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattCharacteristic paramAnonymousBluetoothGattCharacteristic)
    {
      BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_DATA_AVAILABLE", paramAnonymousBluetoothGattCharacteristic);
    }
    
    public void onCharacteristicRead(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattCharacteristic paramAnonymousBluetoothGattCharacteristic, int paramAnonymousInt)
    {
      if (paramAnonymousInt == 0)
      {
        BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_DATA_AVAILABLE", paramAnonymousBluetoothGattCharacteristic);
        BluetoothLeService.this.disconnect();
      }
    }
    
    public void onCharacteristicWrite(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattCharacteristic paramAnonymousBluetoothGattCharacteristic, int paramAnonymousInt) {}
    
    public void onConnectionStateChange(BluetoothGatt paramAnonymousBluetoothGatt, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      if (paramAnonymousInt2 == 2)
      {
        BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_GATT_CONNECTED");
        BluetoothLeService.this.mBluetoothGatt.discoverServices();
      }
      while (paramAnonymousInt2 != 0) {
        return;
      }
      BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED");
    }
    
    public void onDescriptorWrite(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattDescriptor paramAnonymousBluetoothGattDescriptor, int paramAnonymousInt) {}
    
    public void onReadRemoteRssi(BluetoothGatt paramAnonymousBluetoothGatt, int paramAnonymousInt1, int paramAnonymousInt2) {}
    
    public void onServicesDiscovered(BluetoothGatt paramAnonymousBluetoothGatt, int paramAnonymousInt)
    {
      if (paramAnonymousInt == 0) {
        BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED");
      }
    }
  };
  
  private void broadcastUpdate(String paramString)
  {
    sendBroadcast(new Intent(paramString));
  }
  
  private void broadcastUpdate(String paramString, BluetoothGattCharacteristic paramBluetoothGattCharacteristic)
  {
    Intent localIntent = new Intent(paramString);
    localIntent.putExtra("com.example.bluetooth.le.EXTRA_DATA", StringUtils.bytes2HexString(paramBluetoothGattCharacteristic.getValue()));
    sendBroadcast(localIntent);
  }
  
  public static IntentFilter makeGattUpdateIntentFilter()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.example.bluetooth.le.ACTION_GATT_CONNECTED");
    localIntentFilter.addAction("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED");
    localIntentFilter.addAction("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED");
    localIntentFilter.addAction("com.example.bluetooth.le.ACTION_DATA_AVAILABLE");
    return localIntentFilter;
  }
  
  public void close()
  {
    if (this.mBluetoothGatt != null)
    {
      this.mBluetoothGatt.close();
      this.mBluetoothGatt = null;
    }
  }
  
  public boolean connect(String paramString)
  {
    boolean bool = true;
    if ((this.mBluetoothAdapter == null) || (paramString == null)) {
      bool = false;
    }
    do
    {
      return bool;
      if ((this.mBluetoothDeviceAddress == null) || (!paramString.equals(this.mBluetoothDeviceAddress)) || (this.mBluetoothGatt == null)) {
        break;
      }
    } while (this.mBluetoothGatt.connect());
    return false;
    BluetoothDevice localBluetoothDevice = this.mBluetoothAdapter.getRemoteDevice(paramString);
    if (localBluetoothDevice == null) {
      return false;
    }
    this.mBluetoothGatt = localBluetoothDevice.connectGatt(this, bool, this.mGattCallback);
    this.mBluetoothDeviceAddress = paramString;
    return bool;
  }
  
  public void disconnect()
  {
    if ((this.mBluetoothAdapter == null) || (this.mBluetoothGatt == null)) {
      return;
    }
    this.mBluetoothGatt.disconnect();
  }
  
  public BluetoothGattCharacteristic getCharacteristic(List<BluetoothGattService> paramList, String paramString)
  {
    if (paramList == null) {}
    BluetoothGattService localBluetoothGattService;
    do
    {
      Iterator localIterator;
      while (!localIterator.hasNext())
      {
        return null;
        localIterator = paramList.iterator();
      }
      localBluetoothGattService = (BluetoothGattService)localIterator.next();
    } while (!localBluetoothGattService.getUuid().toString().substring(4, 8).endsWith("fff0"));
    return localBluetoothGattService.getCharacteristic(UUID.fromString("0000" + paramString + "-0000-1000-8000-00805f9b34fb"));
  }
  
  public List<BluetoothGattService> getSupportedGattServices()
  {
    if (this.mBluetoothGatt == null) {
      return null;
    }
    return this.mBluetoothGatt.getServices();
  }
  
  public boolean initialize()
  {
    if (this.mBluetoothManager == null)
    {
      this.mBluetoothManager = ((BluetoothManager)getSystemService("bluetooth"));
      if (this.mBluetoothManager == null)
      {
        Log.e(TAG, "Unable to initialize BluetoothManager.");
        return false;
      }
    }
    this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
    if (this.mBluetoothAdapter == null)
    {
      Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
      return false;
    }
    return true;
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
  
  public boolean onUnbind(Intent paramIntent)
  {
    close();
    return super.onUnbind(paramIntent);
  }
  
  public void setCharacteristicNotification(BluetoothGattCharacteristic paramBluetoothGattCharacteristic, boolean paramBoolean)
  {
    if ((this.mBluetoothAdapter != null) && (this.mBluetoothGatt != null))
    {
      this.mBluetoothGatt.setCharacteristicNotification(paramBluetoothGattCharacteristic, paramBoolean);
      BluetoothGattDescriptor localBluetoothGattDescriptor = paramBluetoothGattCharacteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
      if (localBluetoothGattDescriptor != null)
      {
        localBluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        this.mBluetoothGatt.writeDescriptor(localBluetoothGattDescriptor);
      }
    }
  }
  
  public void wirteCharacteristic(BluetoothGattCharacteristic paramBluetoothGattCharacteristic)
  {
    if ((this.mBluetoothAdapter != null) && (this.mBluetoothGatt != null)) {
      this.mBluetoothGatt.writeCharacteristic(paramBluetoothGattCharacteristic);
    }
  }
  
  public class LocalBinder
    extends Binder
  {
    public LocalBinder() {}
    
    public BluetoothLeService getService()
    {
      return BluetoothLeService.this;
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\ble\BluetoothLeService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */