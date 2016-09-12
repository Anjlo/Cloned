package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.lefu.es.ble.BlueSingleton;
import com.lefu.es.ble.BluetoothLeService;
import com.lefu.es.ble.BluetoothLeService.LocalBinder;
import com.lefu.es.constant.BluetoolUtil;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.db.RecordDao;
import com.lefu.es.entity.UserModel;
import com.lefu.es.progressbar.NumberProgressBar;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.RecordService;
import com.lefu.es.service.UserService;
import com.lefu.es.util.MyUtil;
import com.lefu.es.util.SharedPreferencesUtil;
import com.lefu.es.util.StringUtils;
import java.io.PrintStream;

@SuppressLint({"HandlerLeak"})
public class AutoBLEActivity
  extends Activity
{
  private static final int REQUEST_ENABLE_BT_CLICK = 31;
  private static boolean receiverReleased = false;
  View.OnClickListener OnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (paramAnonymousView.getId() == 2131165194) {
        AutoBLEActivity.this.backToUserEdit();
      }
    }
  };
  private Runnable TimeoutRunnable = new Runnable()
  {
    public void run()
    {
      if ((AutoBLEActivity.this.scaleType == null) && (!AutoBLEActivity.this.isBack))
      {
        if (System.currentTimeMillis() - AutoBLEActivity.this.startTime <= 20000L) {
          break label61;
        }
        if (!AutoBLEActivity.this.isConneced) {
          AutoBLEActivity.this.handler.sendEmptyMessage(1);
        }
      }
      return;
      label61:
      if (!AutoBLEActivity.this.isConneced) {
        AutoBLEActivity.this.handler.sendEmptyMessage(3);
      }
      for (;;)
      {
        AutoBLEActivity.this.handler.postDelayed(this, 1000L);
        return;
        AutoBLEActivity.this.bnp.setProgress(100);
      }
    }
  };
  private Button backButton;
  private NumberProgressBar bnp;
  Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      switch (paramAnonymousMessage.what)
      {
      default: 
      case 1: 
        do
        {
          return;
        } while (AutoBLEActivity.this.isBack);
        Intent localIntent2 = new Intent();
        localIntent2.setFlags(268435456);
        localIntent2.setClass(AutoBLEActivity.this, AutoBTActivity.class);
        AutoBLEActivity.this.startActivity(localIntent2);
        AutoBLEActivity.this.finish();
        return;
      case 2: 
        AutoBLEActivity.this.handler.removeCallbacks(AutoBLEActivity.this.TimeoutRunnable);
        ExitApplication.getInstance().exit(AutoBLEActivity.this);
        Intent localIntent1 = new Intent();
        localIntent1.setClass(AutoBLEActivity.this, LoadingActivity.class);
        AutoBLEActivity.this.startActivity(localIntent1);
        return;
      }
      if (AutoBLEActivity.this.keepScaleWorking) {
        Toast.makeText(AutoBLEActivity.this, AutoBLEActivity.this.getString(2131361911), 0).show();
      }
      for (AutoBLEActivity.this.keepScaleWorking = false;; AutoBLEActivity.this.keepScaleWorking = true)
      {
        AutoBLEActivity.this.bnp.incrementProgressBy(2);
        return;
      }
    }
  };
  private boolean isBack = false;
  private boolean isConneced = false;
  private boolean isServiceReg = false;
  private boolean keepScaleWorking = true;
  private BluetoothAdapter mBluetoothAdapter;
  private BluetoothLeService mBluetoothLeService;
  private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      if ("com.example.bluetooth.le.ACTION_GATT_CONNECTED".equals(str1)) {
        AutoBLEActivity.this.singleton.setmConnected(true);
      }
      String str2;
      String str3;
      label275:
      do
      {
        do
        {
          do
          {
            return;
          } while ("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED".equals(str1));
          if ("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED".equals(str1))
          {
            AutoBLEActivity.this.sendCodeCount = 0;
            AutoBLEActivity.this.isConneced = true;
            Toast localToast1 = Toast.makeText(AutoBLEActivity.this, AutoBLEActivity.this.getString(2131361915), 0);
            localToast1.setGravity(17, 0, 0);
            localToast1.show();
            Toast localToast2 = Toast.makeText(AutoBLEActivity.this, AutoBLEActivity.this.getString(2131361915), 0);
            localToast2.setGravity(17, 0, 0);
            localToast2.show();
            new Thread(new Runnable()
            {
              public void run()
              {
                if ((AutoBLEActivity.this.singleton.getmConnected()) && (AutoBLEActivity.this.mBluetoothLeService != null)) {
                  AutoBLEActivity.this.send_data();
                }
              }
            }).start();
            return;
          }
        } while (!"com.example.bluetooth.le.ACTION_DATA_AVAILABLE".equals(str1));
        str2 = paramAnonymousIntent.getStringExtra("com.example.bluetooth.le.EXTRA_DATA");
        System.out.println("检测读取到数据：" + str2);
        if (!str2.equals(UtilConstants.ERROR_CODE)) {
          break label563;
        }
        if (AutoBLEActivity.this.sendCodeCount >= 1) {
          break;
        }
        if (AutoBLEActivity.this.mBluetoothLeService != null) {
          AutoBLEActivity.this.send_data();
        }
        AutoBLEActivity localAutoBLEActivity = AutoBLEActivity.this;
        localAutoBLEActivity.sendCodeCount = (1 + localAutoBLEActivity.sendCodeCount);
        str3 = "";
        if ((!str2.toLowerCase().startsWith(UtilConstants.BODY_SCALE)) || (str2.length() <= 31)) {
          break label598;
        }
        str3 = UtilConstants.BODY_SCALE;
      } while ((str2.length() <= 31) || (System.currentTimeMillis() - UtilConstants.receiveDataTime <= 1000L));
      UtilConstants.receiveDataTime = System.currentTimeMillis();
      UtilConstants.CURRENT_SCALE = str3;
      AutoBLEActivity.this.scaleType = UtilConstants.CURRENT_SCALE;
      UtilConstants.su.editSharedPreferences("lefuconfig", "scale", UtilConstants.CURRENT_SCALE);
      Boolean localBoolean = (Boolean)UtilConstants.su.readbackUp("lefuconfig", "reCheck", Boolean.valueOf(false));
      UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
      if ((localBoolean == null) || (!localBoolean.booleanValue())) {}
      for (;;)
      {
        try
        {
          AutoBLEActivity.this.uservice.save(UtilConstants.CURRENT_USER);
          UtilConstants.CURRENT_USER = AutoBLEActivity.this.uservice.find(AutoBLEActivity.this.uservice.maxid());
          UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
          UtilConstants.SELECT_USER = UtilConstants.CURRENT_USER.getId();
          UtilConstants.su.editSharedPreferences("lefuconfig", "user", Integer.valueOf(UtilConstants.SELECT_USER));
          UtilConstants.su.editSharedPreferences("lefuconfig", "bluetooth_type" + UtilConstants.CURRENT_USER.getId(), "BLE");
          if (!str2.toLowerCase().startsWith(UtilConstants.KITCHEN_SCALE)) {
            break label730;
          }
          RecordDao.dueKitchenDate(AutoBLEActivity.this.recordService, str2, null);
          AutoBLEActivity.this.handler.sendEmptyMessage(2);
          return;
          Toast.makeText(AutoBLEActivity.this, AutoBLEActivity.this.getString(2131361909), 1).show();
          Toast.makeText(AutoBLEActivity.this, AutoBLEActivity.this.getString(2131361909), 1).show();
          break;
          label563:
          if (!str2.equals(UtilConstants.ERROR_CODE_TEST)) {
            break;
          }
          Toast.makeText(AutoBLEActivity.this, AutoBLEActivity.this.getString(2131361912), 1).show();
          break;
          label598:
          if ((str2.toLowerCase().startsWith(UtilConstants.BATHROOM_SCALE)) && (str2.length() > 31))
          {
            str3 = UtilConstants.BATHROOM_SCALE;
            break label275;
          }
          if ((str2.toLowerCase().startsWith(UtilConstants.BABY_SCALE)) && (str2.length() > 31))
          {
            str3 = UtilConstants.BABY_SCALE;
            break label275;
          }
          if ((!str2.toLowerCase().startsWith(UtilConstants.KITCHEN_SCALE)) || (str2.length() <= 31)) {
            break label275;
          }
          str3 = UtilConstants.KITCHEN_SCALE;
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
          continue;
        }
        try
        {
          AutoBLEActivity.this.uservice.update(UtilConstants.CURRENT_USER);
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        continue;
        label730:
        RecordDao.dueDate(AutoBLEActivity.this.recordService, str2);
      }
    }
  };
  public final ServiceConnection mServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      AutoBLEActivity.this.mBluetoothLeService = ((BluetoothLeService.LocalBinder)paramAnonymousIBinder).getService();
      if (!AutoBLEActivity.this.mBluetoothLeService.initialize()) {
        AutoBLEActivity.this.finish();
      }
      AutoBLEActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
      AutoBLEActivity.this.sleep(200L);
      AutoBLEActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
      AutoBLEActivity.this.isServiceReg = true;
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      AutoBLEActivity.this.isServiceReg = false;
    }
  };
  private RecordService recordService;
  private String scaleType = null;
  private SearchDevicesView search_device_view;
  private int sendCodeCount = 0;
  private BlueSingleton singleton;
  private long startTime = System.currentTimeMillis();
  private UserService uservice;
  
  private void backToUserEdit()
  {
    this.isBack = true;
    Boolean localBoolean = (Boolean)UtilConstants.su.readbackUp("lefuconfig", "reCheck", Boolean.valueOf(false));
    ExitApplication.getInstance().exit(this);
    if ((localBoolean != null) && (localBoolean.booleanValue()))
    {
      startActivity(new Intent(this, LoadingActivity.class));
      return;
    }
    startActivity(new Intent(this, UserEditActivity.class));
  }
  
  private void send_data()
  {
    BluetoothGattCharacteristic localBluetoothGattCharacteristic1 = this.mBluetoothLeService.getCharacteristic(this.mBluetoothLeService.getSupportedGattServices(), "fff4");
    this.mBluetoothLeService.setCharacteristicNotification(localBluetoothGattCharacteristic1, true);
    try
    {
      sleep(500L);
      BluetoothGattCharacteristic localBluetoothGattCharacteristic2 = this.mBluetoothLeService.getCharacteristic(this.mBluetoothLeService.getSupportedGattServices(), "fff1");
      if (localBluetoothGattCharacteristic2 != null)
      {
        String str = MyUtil.getUserInfo();
        System.out.println("写入数据:" + str);
        localBluetoothGattCharacteristic2.setValue(StringUtils.hexStringToByteArray(str));
        this.mBluetoothLeService.wirteCharacteristic(localBluetoothGattCharacteristic2);
        localBluetoothGattCharacteristic2.getProperties();
      }
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  private void sleep(long paramLong)
  {
    try
    {
      Thread.sleep(paramLong);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903040);
    this.backButton = ((Button)findViewById(2131165194));
    this.backButton.setOnClickListener(this.OnClickListener);
    this.recordService = new RecordService(this);
    this.uservice = new UserService(this);
    ExitApplication.getInstance().addActivity(this);
    UtilConstants.su = new SharedPreferencesUtil(this);
    try
    {
      this.mBluetoothAdapter = ((BluetoothManager)getSystemService("bluetooth")).getAdapter();
      if (!this.mBluetoothAdapter.isEnabled()) {
        startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 31);
      }
      this.singleton = BlueSingleton.getInstance(this.handler);
      this.singleton.isExit = false;
      this.singleton.isSearch = false;
      this.singleton.setmConnected(false);
      if ((this.mBluetoothAdapter.isEnabled()) && (!this.singleton.getmConnected()) && (!this.singleton.getmScanning())) {
        this.singleton.scanLeDevice(true, this, this.mServiceConnection);
      }
      if (!receiverReleased)
      {
        registerReceiver(this.mGattUpdateReceiver, BluetoothLeService.makeGattUpdateIntentFilter());
        receiverReleased = false;
      }
      this.search_device_view = ((SearchDevicesView)findViewById(2131165196));
      this.search_device_view.setWillNotDraw(false);
      this.search_device_view.setSearching(true);
      this.bnp = ((NumberProgressBar)findViewById(2131165197));
      new Thread(this.TimeoutRunnable).start();
      com.lefu.es.constant.AppData.isCheckScale = true;
      if (UtilConstants.CURRENT_USER == null) {
        UtilConstants.CURRENT_USER = (UserModel)JSONObject.parseObject((String)UtilConstants.su.readbackUp("lefuconfig", "addUser", ""), UserModel.class);
      }
      return;
    }
    catch (NoClassDefFoundError localNoClassDefFoundError)
    {
      for (;;)
      {
        localNoClassDefFoundError.printStackTrace();
      }
    }
  }
  
  protected void onDestroy()
  {
    this.singleton.isExit = true;
    unregisterReceiver(this.mGattUpdateReceiver);
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      backToUserEdit();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  protected void onPause()
  {
    super.onPause();
  }
  
  protected void onResume()
  {
    super.onResume();
  }
  
  protected void onStart()
  {
    super.onStart();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\AutoBLEActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */