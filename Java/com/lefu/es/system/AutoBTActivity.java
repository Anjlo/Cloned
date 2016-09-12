package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.lefu.es.constant.AppData;
import com.lefu.es.constant.BluetoolUtil;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.progressbar.NumberProgressBar;
import com.lefu.es.service.BluetoothChatService;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.TimeService;
import com.lefu.es.util.SharedPreferencesUtil;
import java.io.PrintStream;

@SuppressLint({"HandlerLeak"})
public class AutoBTActivity
  extends Activity
{
  private static int checkTime = 30000;
  private static boolean isOnlySupprotBT = false;
  private static boolean isRegisterReceiver = false;
  View.OnClickListener OnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (paramAnonymousView.getId() == 2131165194) {
        AutoBTActivity.this.backToUserEdit();
      }
    }
  };
  private Runnable ScanRunnable = new Runnable()
  {
    public void run()
    {
      AutoBTActivity.this.startDiscovery();
      AutoBTActivity.this.handler.postDelayed(this, 7000L);
    }
  };
  private Runnable TimeoutRunnable = new Runnable()
  {
    public void run()
    {
      if (!AutoBTActivity.this.isBack)
      {
        if (TimeService.scaleType != null) {
          break label103;
        }
        if (System.currentTimeMillis() - AutoBTActivity.this.startTime <= AutoBTActivity.checkTime) {
          break label54;
        }
        if (!AppData.hasCheckData) {
          AutoBTActivity.this.handler.sendEmptyMessage(2);
        }
      }
      return;
      label54:
      if (!AppData.hasCheckData) {
        AutoBTActivity.this.handler.sendEmptyMessage(4);
      }
      for (;;)
      {
        AutoBTActivity.this.handler.postDelayed(this, 1000L);
        return;
        AutoBTActivity.this.bnp.setProgress(100);
      }
      label103:
      AutoBTActivity.this.handler.sendEmptyMessage(3);
    }
  };
  private Button backButton;
  private NumberProgressBar bnp;
  private BluetoothDevice connectdevice;
  Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 2: 
        if (UtilConstants.checkScaleTimes == 0)
        {
          Toast.makeText(AutoBTActivity.this, AutoBTActivity.this.getString(2131361906), 1).show();
          UtilConstants.checkScaleTimes = 1 + UtilConstants.checkScaleTimes;
          AutoBTActivity.this.startActivity(new Intent(AutoBTActivity.this, AutoBLEActivity.class));
          AutoBTActivity.this.finish();
          return;
        }
        Toast.makeText(AutoBTActivity.this, AutoBTActivity.this.getString(2131361907), 1).show();
        return;
      case 3: 
        AutoBTActivity.this.handler.removeCallbacks(AutoBTActivity.this.TimeoutRunnable);
        UtilConstants.serveIntent = null;
        Intent localIntent = new Intent();
        localIntent.setClass(AutoBTActivity.this, LoadingActivity.class);
        ExitApplication.getInstance().exit(AutoBTActivity.this);
        AutoBTActivity.this.startActivity(localIntent);
        return;
      }
      if (AutoBTActivity.this.keepScaleWorking) {
        Toast.makeText(AutoBTActivity.this, AutoBTActivity.this.getString(2131361911), 0).show();
      }
      for (AutoBTActivity.this.keepScaleWorking = false; AutoBTActivity.isOnlySupprotBT; AutoBTActivity.this.keepScaleWorking = true)
      {
        AutoBTActivity.this.bnp.incrementProgressBy(4);
        return;
      }
      AutoBTActivity.this.bnp.incrementProgressBy(2);
    }
  };
  private boolean isBack = false;
  private boolean keepScaleWorking = true;
  private BluetoothAdapter mBtAdapter;
  private final BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      if ("android.bluetooth.device.action.FOUND".equals(str1))
      {
        localBluetoothDevice = (BluetoothDevice)paramAnonymousIntent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        if (localBluetoothDevice != null)
        {
          str2 = localBluetoothDevice.getName();
          System.out.println(str2 + "=" + localBluetoothDevice.getAddress());
          if ((str2 != null) && (str2.equalsIgnoreCase("Electronic Scale")) && (AutoBTActivity.this.connectdevice == null))
          {
            AutoBTActivity.this.connectdevice = localBluetoothDevice;
            BluetoolUtil.mChatService.connect(localBluetoothDevice, true);
            AutoBTActivity.this.stopDiscovery();
          }
        }
      }
      while (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(str1))
      {
        BluetoothDevice localBluetoothDevice;
        String str2;
        return;
      }
      AutoBTActivity.this.stopDiscovery();
    }
  };
  private SearchDevicesView search_device_view;
  private long startTime = System.currentTimeMillis();
  
  private void backToUserEdit()
  {
    boolean bool = true;
    this.isBack = bool;
    Boolean localBoolean = (Boolean)UtilConstants.su.readbackUp("lefuconfig", "reCheck", Boolean.valueOf(false));
    ExitApplication.getInstance().exit(this);
    if (localBoolean != null) {}
    while ((bool & localBoolean.booleanValue()))
    {
      startActivity(new Intent(this, LoadingActivity.class));
      return;
      bool = false;
    }
    startActivity(new Intent(this, UserEditActivity.class));
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903040);
    this.backButton = ((Button)findViewById(2131165194));
    this.backButton.setOnClickListener(this.OnClickListener);
    ExitApplication.getInstance().addActivity(this);
    UtilConstants.su = new SharedPreferencesUtil(this);
    if (BluetoolUtil.mBluetoothAdapter == null) {
      BluetoolUtil.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }
    if ((BluetoolUtil.mBluetoothAdapter != null) && (!BluetoolUtil.mBluetoothAdapter.isEnabled())) {
      startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 3);
    }
    if (UtilConstants.serveIntent == null)
    {
      UtilConstants.serveIntent = new Intent(this, TimeService.class);
      startService(UtilConstants.serveIntent);
    }
    new Thread(this.ScanRunnable).start();
    new Thread(this.TimeoutRunnable).start();
    this.search_device_view = ((SearchDevicesView)findViewById(2131165196));
    this.search_device_view.setWillNotDraw(false);
    this.search_device_view.setSearching(true);
    this.bnp = ((NumberProgressBar)findViewById(2131165197));
    if (Build.VERSION.SDK_INT < 18) {
      isOnlySupprotBT = true;
    }
    if (isOnlySupprotBT)
    {
      checkTime = 25000;
      this.bnp.setProgress(0);
    }
    for (;;)
    {
      AppData.isCheckScale = true;
      return;
      this.bnp.setProgress(40);
    }
  }
  
  protected void onDestroy()
  {
    isRegisterReceiver = false;
    if (UtilConstants.serveIntent != null) {
      stopService(UtilConstants.serveIntent);
    }
    if (this.mBtAdapter != null) {
      this.mBtAdapter.cancelDiscovery();
    }
    this.handler.removeCallbacks(this.ScanRunnable);
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      backToUserEdit();
      return true;
    }
    if ((paramInt == 3) && (UtilConstants.serveIntent != null)) {
      stopService(UtilConstants.serveIntent);
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
  
  public void startDiscovery()
  {
    IntentFilter localIntentFilter1 = new IntentFilter("android.bluetooth.device.action.FOUND");
    registerReceiver(this.mReceiver, localIntentFilter1);
    IntentFilter localIntentFilter2 = new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
    registerReceiver(this.mReceiver, localIntentFilter2);
    isRegisterReceiver = true;
    this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    this.mBtAdapter.startDiscovery();
  }
  
  public void stopDiscovery()
  {
    this.mBtAdapter.cancelDiscovery();
    if (isRegisterReceiver)
    {
      unregisterReceiver(this.mReceiver);
      isRegisterReceiver = false;
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\AutoBTActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */