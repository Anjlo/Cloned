package com.lefu.es.constant;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.ArrayAdapter;
import com.lefu.es.service.BluetoothChatService;
import java.util.HashSet;
import java.util.Set;

public class BluetoolUtil
{
  public static final String DEVICE_NAME = "device_name";
  public static final int MESSAGE_DEVICE_NAME = 4;
  public static final int MESSAGE_READ = 2;
  public static final int MESSAGE_STATE_CHANGE = 1;
  public static final int MESSAGE_TOAST = 5;
  public static final int MESSAGE_WRITE = 3;
  public static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
  public static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
  public static final int REQUEST_ENABLE_BT = 3;
  public static final String TOAST = "toast";
  public static boolean bleflag = false;
  public static Set<BluetoothDevice> devices;
  public static BluetoothDevice lastDevice;
  public static BluetoothAdapter mBluetoothAdapter;
  public static BluetoothChatService mChatService;
  public static String mConnectedDeviceName;
  public static ArrayAdapter<String> mConversationArrayAdapter;
  public static String mDeviceAddress;
  public static StringBuffer mOutStringBuffer;
  public static BluetoothDevice remoteDevice = null;
  
  static
  {
    mConnectedDeviceName = null;
    mBluetoothAdapter = null;
    mChatService = null;
    lastDevice = null;
    devices = new HashSet();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\constant\BluetoolUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */