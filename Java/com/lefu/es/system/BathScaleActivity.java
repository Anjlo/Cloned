package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.lefu.es.adapter.MyPageAdapter;
import com.lefu.es.ble.BlueSingleton;
import com.lefu.es.ble.BluetoothLeService;
import com.lefu.es.ble.BluetoothLeService.LocalBinder;
import com.lefu.es.cache.CacheHelper;
import com.lefu.es.constant.AppData;
import com.lefu.es.constant.BluetoolUtil;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.constant.imageUtil;
import com.lefu.es.db.RecordDao;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.BluetoothChatService;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.RecordService;
import com.lefu.es.service.TimeService;
import com.lefu.es.service.UserService;
import com.lefu.es.util.DisplayUtil;
import com.lefu.es.util.MyUtil;
import com.lefu.es.util.SharedPreferencesUtil;
import com.lefu.es.util.StringUtils;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.view.GuideView;
import com.lefu.es.view.GuideView.Builder;
import com.lefu.es.view.GuideView.Direction;
import com.lefu.es.view.GuideView.MyShape;
import com.lefu.es.view.GuideView.OnClickCallback;
import com.lefu.es.view.MyTextView;
import com.lefu.es.view.MyTextView2;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@SuppressLint({"HandlerLeak"})
public class BathScaleActivity
  extends Activity
  implements Runnable
{
  private static final boolean D = true;
  public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
  public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
  public static final String PAR_KEY = "com.tutor.objecttran.par";
  private static final int REQUEST_ENABLE_BT_CLICK = 31;
  private static final String TAG = "BathScaleActivity";
  private static boolean receiverReleased = false;
  private Runnable CheckHasDataRunnable = new Runnable()
  {
    public void run()
    {
      if ((!AppData.hasCheckData) && (BathScaleActivity.this.isCurrentActivoty) && (!UtilConstants.isTipChangeScale))
      {
        BathScaleActivity.this.scaleChangeAlert();
        UtilConstants.isTipChangeScale = true;
      }
    }
  };
  private int ItemID;
  private Runnable ScanRunnable = new Runnable()
  {
    public void run()
    {
      BathScaleActivity.this.startDiscovery();
    }
  };
  private MyPageAdapter adapter = null;
  private ImageView backgroundImage;
  View.OnClickListener btnOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      default: 
        return;
      }
      Intent localIntent = new Intent();
      localIntent.setFlags(4194304);
      localIntent.setClass(BathScaleActivity.this, UserListActivity.class);
      BathScaleActivity.this.startActivity(localIntent);
    }
  };
  private MyTextView2 compare_tv;
  private Records curRecord;
  private Button deletdImg;
  private GuideView guideView;
  Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      switch (paramAnonymousMessage.what)
      {
      default: 
      case 0: 
        do
        {
          return;
          BathScaleActivity.this.setViews();
          BathScaleActivity.this.Init(CacheHelper.recordListDesc);
        } while ((!TextUtils.isEmpty(UtilConstants.FIRST_INSTALL_BATH)) || (CacheHelper.recordListDesc.size() <= 0));
        BathScaleActivity.this.showTipMask();
        return;
      case 1: 
        BathScaleActivity.this.setViews();
        return;
      case 5: 
        BathScaleActivity.this.exit();
        ExitApplication.getInstance().exit(BathScaleActivity.this);
        return;
      }
      if (UtilConstants.su == null) {
        UtilConstants.su = new SharedPreferencesUtil(LoadingActivity.mainActivty);
      }
      UtilConstants.su.editSharedPreferences("lefuconfig", "scale", UtilConstants.CURRENT_SCALE);
      try
      {
        BathScaleActivity.this.uservice.update(UtilConstants.CURRENT_USER);
        ExitApplication.getInstance().exit(BathScaleActivity.this);
        Intent localIntent = new Intent();
        localIntent.setClass(BathScaleActivity.this, LoadingActivity.class);
        BathScaleActivity.this.startActivity(localIntent);
        return;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          localException.printStackTrace();
        }
      }
    }
  };
  private ImageView headImg;
  View.OnClickListener imgOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      Intent localIntent = new Intent();
      localIntent.setClass(BathScaleActivity.this, RecordListActivity.class);
      localIntent.putExtra("type", 0);
      localIntent.addFlags(131072);
      BathScaleActivity.this.startActivityForResult(localIntent, 0);
    }
  };
  private LayoutInflater inflater;
  private Button infoImg;
  private ImageView intentImg;
  private boolean isCurrentActivoty = true;
  private boolean isServiceReg = false;
  private ImageView leftImg;
  private BluetoothAdapter mBluetoothAdapter;
  private BluetoothLeService mBluetoothLeService;
  private BluetoothAdapter mBtAdapter;
  private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      if ("com.example.bluetooth.le.ACTION_GATT_CONNECTED".equals(str1)) {
        BathScaleActivity.this.singleton.setmConnected(true);
      }
      String str2;
      label341:
      label489:
      label491:
      label574:
      do
      {
        do
        {
          do
          {
            do
            {
              return;
              if (!"com.example.bluetooth.le.ACTION_GATT_DISCONNECTED".equals(str1)) {
                break;
              }
              BathScaleActivity.this.scale_connect_state.setText(2131361917);
            } while (!BathScaleActivity.this.singleton.getmConnected());
            BathScaleActivity.this.singleton.setmConnected(false);
            if (BathScaleActivity.this.mBluetoothLeService != null)
            {
              boolean bool = BathScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
              Log.d("BathScaleActivity", "Connect request result=" + bool);
            }
            BathScaleActivity.this.singleton.scanLeDevice(true, BathScaleActivity.this, BathScaleActivity.this.mServiceConnection);
            return;
            if (!"com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED".equals(str1)) {
              break;
            }
            BathScaleActivity.this.sendCodeCount = 0;
            BathScaleActivity.this.scale_connect_state.setText(2131361916);
            AppData.hasCheckData = true;
            Toast.makeText(BathScaleActivity.this, BathScaleActivity.this.getString(2131361910), 1).show();
            Toast.makeText(BathScaleActivity.this, BathScaleActivity.this.getString(2131361910), 1).show();
          } while (BathScaleActivity.this.mBluetoothLeService == null);
          new Thread(new Runnable()
          {
            public void run()
            {
              if ((BathScaleActivity.this.singleton.getmConnected()) && (BathScaleActivity.this.mBluetoothLeService != null)) {
                BathScaleActivity.this.send_Data();
              }
            }
          }).start();
          return;
        } while (!"com.example.bluetooth.le.ACTION_DATA_AVAILABLE".equals(str1));
        str2 = paramAnonymousIntent.getStringExtra("com.example.bluetooth.le.EXTRA_DATA");
        System.out.println("收到数据：" + str2);
        if (str2.equals(UtilConstants.ERROR_CODE)) {
          if (BathScaleActivity.this.sendCodeCount <= 2)
          {
            if (BathScaleActivity.this.mBluetoothLeService != null) {
              BathScaleActivity.this.send_Data();
            }
            BathScaleActivity localBathScaleActivity = BathScaleActivity.this;
            localBathScaleActivity.sendCodeCount = (1 + localBathScaleActivity.sendCodeCount);
            if (System.currentTimeMillis() - UtilConstants.receiveDataTime < 1000L) {
              break label489;
            }
            UtilConstants.receiveDataTime = System.currentTimeMillis();
            if ((str2.startsWith(UtilConstants.BATHROOM_SCALE)) || (str2.length() <= 31)) {
              break label574;
            }
            if (!str2.startsWith(UtilConstants.BODY_SCALE)) {
              break label491;
            }
            UtilConstants.CURRENT_SCALE = UtilConstants.BODY_SCALE;
            UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
            RecordDao.dueDate(BathScaleActivity.this.recordService, str2);
          }
        }
        for (;;)
        {
          BathScaleActivity.this.handler.sendEmptyMessage(101);
          return;
          Toast.makeText(BathScaleActivity.this, BathScaleActivity.this.getString(2131361909), 0).show();
          break label341;
          if (!str2.equals(UtilConstants.ERROR_CODE_TEST)) {
            break label341;
          }
          Toast.makeText(BathScaleActivity.this, BathScaleActivity.this.getString(2131361912), 0).show();
          break label341;
          break;
          if (str2.startsWith(UtilConstants.BABY_SCALE))
          {
            UtilConstants.CURRENT_SCALE = UtilConstants.BABY_SCALE;
            UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
            RecordDao.dueDate(BathScaleActivity.this.recordService, str2);
          }
          else if (str2.startsWith(UtilConstants.KITCHEN_SCALE))
          {
            UtilConstants.CURRENT_SCALE = UtilConstants.KITCHEN_SCALE;
            UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
            RecordDao.dueKitchenDate(BathScaleActivity.this.recordService, str2, null);
          }
        }
        if (str2.equals(UtilConstants.ERROR_CODE_GETDATE))
        {
          BathScaleActivity.this.openErrorDiolg("2");
          return;
        }
      } while ((!str2.startsWith("c")) || (str2.length() != 32));
      BathScaleActivity.this.dueDate(str2);
    }
  };
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
          if ((str2 != null) && (str2.equalsIgnoreCase("Electronic Scale")))
          {
            BluetoolUtil.mChatService.connect(localBluetoothDevice, true);
            BathScaleActivity.this.stopDiscovery();
            BathScaleActivity.this.handler.postDelayed(BathScaleActivity.this.ScanRunnable, 15000L);
          }
        }
      }
      while (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(str1))
      {
        BluetoothDevice localBluetoothDevice;
        String str2;
        return;
      }
      BathScaleActivity.this.stopDiscovery();
      BathScaleActivity.this.handler.postDelayed(BathScaleActivity.this.ScanRunnable, 10000L);
    }
  };
  public final ServiceConnection mServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      BathScaleActivity.this.mBluetoothLeService = ((BluetoothLeService.LocalBinder)paramAnonymousIBinder).getService();
      if (!BathScaleActivity.this.mBluetoothLeService.initialize())
      {
        System.out.println("Unable to initialize Bluetooth");
        BathScaleActivity.this.finish();
      }
      BathScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
      BathScaleActivity.this.isServiceReg = true;
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      BathScaleActivity.this.mBluetoothLeService = null;
      BathScaleActivity.this.isServiceReg = false;
    }
  };
  View.OnClickListener menuBtnOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      default: 
        return;
      case 2131165226: 
        Intent localIntent2 = new Intent();
        localIntent2.setFlags(268435456);
        localIntent2.setClass(BathScaleActivity.this, InfoActivity.class);
        BathScaleActivity.this.startActivity(localIntent2);
        return;
      case 2131165227: 
        Intent localIntent1 = new Intent();
        localIntent1.setFlags(268435456);
        localIntent1.setClass(BathScaleActivity.this, SettingActivity.class);
        BathScaleActivity.this.startActivity(localIntent1);
        return;
      }
      BathScaleActivity.this.dialog(BathScaleActivity.this.getString(2131361845), paramAnonymousView.getId());
    }
  };
  private TextView norecord_tv;
  private ViewPager pager;
  private List<Records> rList;
  private RecordService recordService;
  private ImageView rightImg;
  RelativeLayout rlGuide = null;
  private TextView scale_connect_state;
  private int selectedPosition = -1;
  private int sendCodeCount = 0;
  private Button setingImg;
  private BlueSingleton singleton;
  private MyTextView2 targetv = null;
  private TextView time_tv;
  private TextView tvBmi = null;
  private TextView username_tv;
  private UserService uservice;
  private ArrayList<View> views = new ArrayList();
  
  private void Init(List<Records> paramList)
  {
    LayoutInflater localLayoutInflater;
    Iterator localIterator;
    if (this.views == null)
    {
      this.views = new ArrayList();
      if ((paramList != null) && (paramList.size() > 0))
      {
        this.rightImg.setVisibility(0);
        this.leftImg.setVisibility(0);
        this.rightImg.setImageDrawable(getResources().getDrawable(2130837533));
        this.norecord_tv.setVisibility(4);
        localLayoutInflater = LayoutInflater.from(this);
        View localView1 = localLayoutInflater.inflate(2130903079, null);
        this.views.add(localView1);
        localIterator = paramList.iterator();
      }
    }
    else
    {
      for (;;)
      {
        if (!localIterator.hasNext())
        {
          View localView3 = localLayoutInflater.inflate(2130903079, null);
          this.views.add(localView3);
          this.adapter = new MyPageAdapter(this.views);
          this.pager.setAdapter(this.adapter);
          this.adapter.notifyDataSetChanged();
          this.pager.setCurrentItem(1);
          return;
          this.views.clear();
          break;
        }
        View localView2 = creatView((Records)localIterator.next(), null);
        this.views.add(localView2);
      }
    }
    this.leftImg.setImageDrawable(getResources().getDrawable(2130837602));
    this.rightImg.setImageDrawable(getResources().getDrawable(2130837641));
    this.rightImg.setVisibility(4);
    this.leftImg.setVisibility(4);
    this.norecord_tv.setVisibility(4);
    this.pager.removeAllViews();
  }
  
  private void exit()
  {
    stopScanService();
    ((NotificationManager)getSystemService("notification")).cancel(0);
    if (LoadingActivity.mainActivty != null) {
      LoadingActivity.mainActivty.finish();
    }
    finish();
  }
  
  private void initView()
  {
    this.uservice = new UserService(this);
    this.norecord_tv = ((TextView)findViewById(2131165206));
    this.username_tv = ((TextView)findViewById(2131165199));
    this.compare_tv = ((MyTextView2)findViewById(2131165208));
    this.targetv = ((MyTextView2)findViewById(2131165224));
    if (UtilConstants.su != null) {
      UtilConstants.CHOICE_KG = (String)UtilConstants.su.readbackUp("lefuconfig", "unit", UtilConstants.UNIT_KG);
    }
    if (UtilConstants.CURRENT_USER != null) {
      this.compare_tv.setTexts("0.0" + UtilConstants.CURRENT_USER.getDanwei(), null);
    }
    this.leftImg = ((ImageView)findViewById(2131165209));
    this.rightImg = ((ImageView)findViewById(2131165210));
    this.rlGuide = ((RelativeLayout)findViewById(2131165213));
    this.tvBmi = ((TextView)findViewById(2131165214));
    this.time_tv = ((TextView)findViewById(2131165198));
    this.scale_connect_state = ((TextView)findViewById(2131165201));
    this.infoImg = ((Button)findViewById(2131165226));
    this.setingImg = ((Button)findViewById(2131165227));
    this.deletdImg = ((Button)findViewById(2131165228));
    this.headImg = ((ImageView)findViewById(2131165225));
    this.intentImg = ((ImageView)findViewById(2131165200));
    this.backgroundImage = ((ImageView)findViewById(2131165205));
    this.infoImg.setOnClickListener(this.menuBtnOnClickListener);
    this.deletdImg.setOnClickListener(this.menuBtnOnClickListener);
    this.setingImg.setOnClickListener(this.menuBtnOnClickListener);
    this.headImg.setOnClickListener(this.btnOnClickListener);
    this.intentImg.setOnClickListener(this.btnOnClickListener);
    this.pager = ((ViewPager)findViewById(2131165207));
    this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
    {
      public void onPageScrollStateChanged(int paramAnonymousInt) {}
      
      public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2) {}
      
      public void onPageSelected(int paramAnonymousInt)
      {
        if ((BathScaleActivity.this.views != null) && (BathScaleActivity.this.views.size() > 0))
        {
          if ((paramAnonymousInt == -1 + BathScaleActivity.this.views.size()) || (paramAnonymousInt == 0)) {
            if (paramAnonymousInt == 0)
            {
              BathScaleActivity.this.pager.setCurrentItem(paramAnonymousInt + 1);
              if (BathScaleActivity.this.pager.getCurrentItem() >= 2) {
                break label226;
              }
              BathScaleActivity.this.leftImg.setImageDrawable(BathScaleActivity.this.getResources().getDrawable(2130837602));
            }
          }
          for (;;)
          {
            if (BathScaleActivity.this.pager.getCurrentItem() < -2 + BathScaleActivity.this.views.size()) {
              break label251;
            }
            BathScaleActivity.this.rightImg.setImageDrawable(BathScaleActivity.this.getResources().getDrawable(2130837641));
            return;
            BathScaleActivity.this.pager.setCurrentItem(paramAnonymousInt - 1);
            break;
            if (paramAnonymousInt > 0) {}
            for (BathScaleActivity.this.selectedPosition = (paramAnonymousInt - 1);; BathScaleActivity.this.selectedPosition = 0)
            {
              BathScaleActivity.this.curRecord = ((Records)CacheHelper.recordListDesc.get(BathScaleActivity.this.selectedPosition));
              BathScaleActivity.this.handler.sendEmptyMessage(1);
              break;
            }
            label226:
            BathScaleActivity.this.leftImg.setImageDrawable(BathScaleActivity.this.getResources().getDrawable(2130837528));
          }
          label251:
          BathScaleActivity.this.rightImg.setImageDrawable(BathScaleActivity.this.getResources().getDrawable(2130837532));
          return;
        }
        BathScaleActivity.this.leftImg.setImageDrawable(BathScaleActivity.this.getResources().getDrawable(2130837602));
        BathScaleActivity.this.rightImg.setImageDrawable(BathScaleActivity.this.getResources().getDrawable(2130837641));
      }
    });
    if (UtilConstants.CURRENT_USER != null)
    {
      this.username_tv.setText(UtilConstants.CURRENT_USER.getUserName());
      if (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_KG)) {
        break label515;
      }
      this.targetv.setTexts(UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight()) + getText(2131361889), null);
    }
    for (;;)
    {
      if ((UtilConstants.CURRENT_USER.getPer_photo() != null) && (!"".equals(UtilConstants.CURRENT_USER.getPer_photo())) && (!UtilConstants.CURRENT_USER.getPer_photo().equals("null")))
      {
        Bitmap localBitmap = imageUtil.getBitmapfromPath(UtilConstants.CURRENT_USER.getPer_photo());
        this.headImg.setImageBitmap(localBitmap);
      }
      return;
      label515:
      if ((UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ST))) {
        this.targetv.setTexts(UtilTooth.onePoint(UtilTooth.kgToLB_target(UtilConstants.CURRENT_USER.getTargweight())) + getText(2131361890), null);
      } else {
        this.targetv.setTexts(UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight()) + getText(2131361889), null);
      }
    }
  }
  
  private void openErrorDiolg(String paramString)
  {
    try
    {
      Intent localIntent = new Intent(this, CustomDialogActivity.class);
      Bundle localBundle = new Bundle();
      localBundle.putString("error", paramString);
      localIntent.putExtras(localBundle);
      localIntent.addFlags(268435456);
      startActivity(localIntent);
      return;
    }
    catch (Exception localException) {}
  }
  
  private void sendDateToScale()
  {
    System.out.println("发送数据=" + MyUtil.getUserInfo());
    BluetoothGattCharacteristic localBluetoothGattCharacteristic1 = this.mBluetoothLeService.getCharacteristic(this.mBluetoothLeService.getSupportedGattServices(), "fff1");
    if (localBluetoothGattCharacteristic1 != null)
    {
      localBluetoothGattCharacteristic1.setValue(StringUtils.hexStringToByteArray(MyUtil.getUserInfo()));
      this.mBluetoothLeService.wirteCharacteristic(localBluetoothGattCharacteristic1);
      localBluetoothGattCharacteristic1.getProperties();
    }
    try
    {
      Thread.sleep(500L);
      BluetoothGattCharacteristic localBluetoothGattCharacteristic2 = this.mBluetoothLeService.getCharacteristic(this.mBluetoothLeService.getSupportedGattServices(), "fff4");
      if (localBluetoothGattCharacteristic2 != null) {
        this.mBluetoothLeService.setCharacteristicNotification(localBluetoothGattCharacteristic2, true);
      }
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        localInterruptedException.printStackTrace();
      }
    }
  }
  
  private void send_Data()
  {
    sendDateToScale();
    try
    {
      Thread.sleep(500L);
      sendDateToScale();
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        localInterruptedException.printStackTrace();
      }
    }
  }
  
  private void setViews()
  {
    if (UtilConstants.CURRENT_USER != null)
    {
      this.username_tv.setText(UtilConstants.CURRENT_USER.getUserName());
      if (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_KG)) {
        break label387;
      }
      this.targetv.setTexts(UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight()) + getText(2131361889), null);
    }
    for (;;)
    {
      if ((UtilConstants.CURRENT_USER.getPer_photo() != null) && (!"".equals(UtilConstants.CURRENT_USER.getPer_photo())) && (!UtilConstants.CURRENT_USER.getPer_photo().equals("null")))
      {
        Bitmap localBitmap = imageUtil.getBitmapfromPath(UtilConstants.CURRENT_USER.getPer_photo());
        this.headImg.setImageBitmap(localBitmap);
      }
      if (this.curRecord == null) {
        break label1740;
      }
      Date localDate = UtilTooth.stringToTime(this.curRecord.getRecordTime());
      if (localDate != null)
      {
        Locale.getDefault();
        this.time_tv.setText(StringUtils.getDateString(localDate, 5));
      }
      float f1 = UtilTooth.countBMI2(this.curRecord.getRweight(), UtilConstants.CURRENT_USER.getBheigth() / 100.0F);
      this.curRecord.setRbmi(UtilTooth.myround(f1));
      this.tvBmi.setText(this.curRecord.getRbmi());
      RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
      int j = DisplayUtil.dip2px(this, 64.0F);
      int k = DisplayUtil.getWidth(this) - j;
      localLayoutParams2.setMargins((int)UtilTooth.changeBMI(this.curRecord.getRbmi(), k), 0, 0, 0);
      this.rlGuide.setLayoutParams(localLayoutParams2);
      if (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_KG)) {
        break label729;
      }
      this.backgroundImage.setBackgroundResource(2130837542);
      if ((this.curRecord.getCompareRecord() != null) && (!"".equals(this.curRecord.getCompareRecord()))) {
        break;
      }
      this.compare_tv.setTexts("0.0", null);
      this.compare_tv.setTexts("0.0 " + getText(2131361889), null);
      return;
      label387:
      if ((UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ST))) {
        this.targetv.setTexts(UtilTooth.onePoint(UtilTooth.kgToLB_target(UtilConstants.CURRENT_USER.getTargweight())) + getText(2131361890), null);
      } else {
        this.targetv.setTexts(UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight()) + getText(2131361889), null);
      }
    }
    float f5 = new BigDecimal(Double.parseDouble(this.curRecord.getCompareRecord())).setScale(2, 4).floatValue();
    if (f5 > 0.0F)
    {
      this.compare_tv.setTexts("↑" + UtilTooth.myroundString3(new StringBuilder(String.valueOf(Math.abs(f5))).toString()) + getText(2131361889), null);
      return;
    }
    if (f5 < 0.0F)
    {
      this.compare_tv.setTexts("↓" + UtilTooth.myroundString3(new StringBuilder(String.valueOf(Math.abs(f5))).toString()) + getText(2131361889), null);
      return;
    }
    this.compare_tv.setTexts(UtilTooth.myroundString3(new StringBuilder(String.valueOf(this.curRecord.getCompareRecord())).toString()) + getText(2131361889), null);
    return;
    label729:
    if ((UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB)))
    {
      this.backgroundImage.setBackgroundResource(2130837543);
      if ((this.curRecord.getCompareRecord() == null) || ("".equals(this.curRecord.getCompareRecord().trim())))
      {
        this.curRecord.setCompareRecord("0");
        this.compare_tv.setTexts("0.0  " + getText(2131361890), null);
        return;
      }
      float f2 = Float.parseFloat(this.curRecord.getCompareRecord());
      if (f2 > 0.0F)
      {
        this.compare_tv.setTexts("↑" + UtilTooth.kgToLB(Math.abs(Float.parseFloat(this.curRecord.getCompareRecord()))) + " " + getText(2131361890), null);
        return;
      }
      if (f2 < 0.0F)
      {
        this.compare_tv.setTexts("↓" + UtilTooth.kgToLB(Math.abs(Float.parseFloat(this.curRecord.getCompareRecord()))) + " " + getText(2131361890), null);
        return;
      }
      this.compare_tv.setTexts("0.0 " + getText(2131361890), null);
      return;
    }
    if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ST))
    {
      if ((this.curRecord.getCompareRecord() == null) || ("".equals(this.curRecord.getCompareRecord().trim())))
      {
        this.curRecord.setCompareRecord("0");
        this.compare_tv.setTexts("0.0 " + getText(2131361892), null);
      }
      float f4 = Float.parseFloat(this.curRecord.getCompareRecord());
      String str = UtilTooth.kgToLB_new(Math.abs(Float.parseFloat(this.curRecord.getCompareRecord())));
      String[] arrayOfString = UtilTooth.kgToStLbForScaleFat2(Math.abs(Float.parseFloat(this.curRecord.getCompareRecord())));
      if (f4 > 0.0F)
      {
        if (this.curRecord.getScaleType().equals(UtilConstants.BODY_SCALE))
        {
          if (arrayOfString[1] != null)
          {
            this.compare_tv.setTexts("↑" + arrayOfString[0], arrayOfString[1]);
            return;
          }
          this.compare_tv.setTexts("↑" + arrayOfString[0] + getText(2131361892), null);
          return;
        }
        this.compare_tv.setTexts("↑" + str + getText(2131361892), null);
        return;
      }
      if (f4 < 0.0F)
      {
        if (this.curRecord.getScaleType().equals(UtilConstants.BODY_SCALE))
        {
          if (arrayOfString[1] != null)
          {
            this.compare_tv.setTexts("↓" + arrayOfString[0], arrayOfString[1]);
            return;
          }
          this.compare_tv.setTexts("↓" + arrayOfString[0] + getText(2131361892), null);
          return;
        }
        this.compare_tv.setTexts("↓" + str + getText(2131361892), null);
        return;
      }
      this.compare_tv.setTexts("0.0 " + getText(2131361892), null);
      return;
    }
    this.backgroundImage.setBackgroundResource(2130837542);
    if ((this.curRecord.getCompareRecord() == null) || ("".equals(this.curRecord.getCompareRecord())))
    {
      this.compare_tv.setTexts("0.0", null);
      this.compare_tv.setTexts("0.0 " + getText(2131361889), null);
      return;
    }
    float f3 = new BigDecimal(Double.parseDouble(this.curRecord.getCompareRecord())).setScale(2, 4).floatValue();
    if (f3 > 0.0F)
    {
      this.compare_tv.setTexts("↑" + UtilTooth.myroundString3(new StringBuilder(String.valueOf(Math.abs(f3))).toString()) + getText(2131361889), null);
      return;
    }
    if (f3 < 0.0F)
    {
      this.compare_tv.setTexts("↓" + UtilTooth.myroundString3(new StringBuilder(String.valueOf(Math.abs(f3))).toString()) + getText(2131361889), null);
      return;
    }
    this.compare_tv.setTexts(UtilTooth.myroundString3(new StringBuilder(String.valueOf(this.curRecord.getCompareRecord())).toString()) + getText(2131361889), null);
    return;
    label1740:
    if (UtilConstants.CURRENT_USER != null) {
      this.compare_tv.setTexts("0.0 " + UtilConstants.CURRENT_USER.getDanwei(), null);
    }
    this.tvBmi.setText("0.0");
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-2, -2);
    int i = DisplayUtil.dip2px(this, 64.0F);
    localLayoutParams1.setMargins((int)UtilTooth.changeBMI(0.0F, DisplayUtil.getWidth(this) - i), 0, 0, 0);
    this.rlGuide.setLayoutParams(localLayoutParams1);
  }
  
  private void showTipMask()
  {
    TextView localTextView = new TextView(this);
    localTextView.setText(getResources().getString(2131361792));
    localTextView.setTextColor(getResources().getColor(2131230727));
    localTextView.setTextSize(16.0F);
    localTextView.setGravity(17);
    this.guideView = GuideView.Builder.newInstance(this).setTargetView(this.pager).setCustomGuideView(localTextView).setDirction(GuideView.Direction.BOTTOM).setShape(GuideView.MyShape.CIRCULAR).setBgColor(getResources().getColor(2131230720)).setRadius(200).setOnclickListener(new GuideView.OnClickCallback()
    {
      public void onClickedGuideView()
      {
        BathScaleActivity.this.guideView.hide();
        if (UtilConstants.su == null) {
          UtilConstants.su = new SharedPreferencesUtil(BathScaleActivity.this);
        }
        UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_bath", "1");
        UtilConstants.FIRST_INSTALL_BATH = "1";
      }
    }).build();
    this.guideView.show();
  }
  
  private void stopScanService()
  {
    if (UtilConstants.serveIntent != null) {
      stopService(UtilConstants.serveIntent);
    }
  }
  
  public View creatView(Records paramRecords, ViewGroup paramViewGroup)
  {
    View localView1 = null;
    MyTextView localMyTextView = null;
    TextView localTextView1 = null;
    if (0 == 0)
    {
      localView1 = this.inflater.inflate(2130903085, null);
      localMyTextView = (MyTextView)localView1.findViewById(2131165429);
      localTextView1 = (TextView)localView1.findViewById(2131165430);
      TextView localTextView2 = (TextView)localView1.findViewById(2131165431);
      View localView2 = localView1.findViewById(2131165428);
      localTextView2.setVisibility(8);
      localView2.setVisibility(8);
      localView1.setTag(paramRecords);
      localView1.setOnClickListener(this.imgOnClickListener);
    }
    if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_ST)) {
      if (UtilConstants.CURRENT_SCALE.equals(UtilConstants.BODY_SCALE))
      {
        String[] arrayOfString = UtilTooth.kgToStLbForScaleFat2(paramRecords.getRweight());
        localMyTextView.setTexts(arrayOfString[0], arrayOfString[1]);
        if (localTextView1 != null) {
          localTextView1.setText(getText(2131361892));
        }
      }
    }
    do
    {
      do
      {
        do
        {
          return localView1;
          localMyTextView.setTexts(UtilTooth.kgToLB_new(paramRecords.getRweight()), null);
        } while (localTextView1 == null);
        localTextView1.setText(getText(2131361892));
        return localView1;
        if (!UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_LB)) {
          break;
        }
        localMyTextView.setTexts(UtilTooth.kgToLB_ForFatScale(paramRecords.getRweight()), null);
      } while (localTextView1 == null);
      localTextView1.setText(getText(2131361890));
      return localView1;
      localMyTextView.setTexts(paramRecords.getRweight(), null);
    } while (localTextView1 == null);
    localTextView1.setText(getText(2131361889));
    return localView1;
  }
  
  protected void dialog(String paramString, final int paramInt)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setMessage(paramString);
    localBuilder.setNegativeButton(2131361853, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    });
    localBuilder.setPositiveButton(2131361852, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        try
        {
          int i = paramInt;
          switch (i)
          {
          }
        }
        catch (Exception localException)
        {
          for (;;) {}
        }
        paramAnonymousDialogInterface.dismiss();
        return;
        if (BathScaleActivity.this.curRecord != null) {
          BathScaleActivity.this.recordService.delete(BathScaleActivity.this.curRecord);
        }
        CacheHelper.recordListDesc = BathScaleActivity.this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_USER.getScaleType(), BathScaleActivity.this.ItemID, 167.0F);
        if ((CacheHelper.recordListDesc != null) && (CacheHelper.recordListDesc.size() > 0)) {}
        for (BathScaleActivity.this.curRecord = ((Records)CacheHelper.recordListDesc.get(0));; BathScaleActivity.this.curRecord = null)
        {
          BathScaleActivity.this.handler.sendEmptyMessage(0);
          break;
        }
      }
    });
    localBuilder.create().show();
  }
  
  public void dueDate(String paramString)
  {
    try
    {
      if (!BlueSingleton.isIsdoing())
      {
        BlueSingleton.setIsdoing(true);
        Intent localIntent = new Intent();
        localIntent.setFlags(268435456);
        localIntent.putExtra("duedate", paramString);
        Records localRecords = MyUtil.parseMeaage(this.recordService, paramString);
        if ((localRecords.getScaleType() != null) && (localRecords.getScaleType().equalsIgnoreCase(UtilConstants.CURRENT_SCALE)) && (localRecords != null) && (localRecords.getUgroup() != null) && (Integer.parseInt(localRecords.getUgroup().replace("P", "")) < 10))
        {
          Bundle localBundle = new Bundle();
          localBundle.putSerializable("record", localRecords);
          localIntent.putExtras(localBundle);
          localIntent.setClass(getApplicationContext(), ReceiveAlertActivity.class);
          startActivity(localIntent);
        }
      }
      return;
    }
    finally {}
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 99)
    {
      this.ItemID = paramIntent.getIntExtra("ItemID", 0);
      this.handler.sendEmptyMessage(0);
    }
    while ((paramInt1 != 31) || (paramInt2 != 0)) {
      return;
    }
    Toast.makeText(this, getString(2131361913), 1).show();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903042);
    this.recordService = new RecordService(this);
    this.inflater = ((LayoutInflater)getSystemService("layout_inflater"));
    this.ItemID = getIntent().getIntExtra("ItemID", 0);
    initView();
    SharedPreferencesUtil localSharedPreferencesUtil = UtilConstants.su;
    String str = null;
    if (localSharedPreferencesUtil != null) {
      str = (String)UtilConstants.su.readbackUp("lefuconfig", "bluetooth_type" + UtilConstants.CURRENT_USER.getId(), String.class);
    }
    System.out.println("蓝牙类型:" + str);
    if ((str != null) && ("BT".equals(str)))
    {
      BluetoolUtil.bleflag = false;
      if (!BluetoolUtil.bleflag) {
        break label220;
      }
    }
    for (;;)
    {
      try
      {
        this.mBluetoothAdapter = ((BluetoothManager)getSystemService("bluetooth")).getAdapter();
        if (!this.mBluetoothAdapter.isEnabled()) {
          startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 31);
        }
        ExitApplication.getInstance().addActivity(this);
        return;
        BluetoolUtil.bleflag = true;
      }
      catch (NoClassDefFoundError localNoClassDefFoundError)
      {
        localNoClassDefFoundError.printStackTrace();
        continue;
      }
      label220:
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
    }
  }
  
  protected void onDestroy()
  {
    exit();
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
    {
      this.handler.sendEmptyMessage(5);
      return true;
    }
    if (paramInt == 3)
    {
      exit();
      ExitApplication.getInstance().exit(this);
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  protected void onPause()
  {
    this.isCurrentActivoty = false;
    super.onPause();
  }
  
  protected void onResume()
  {
    super.onResume();
    this.isCurrentActivoty = true;
    if (!AppData.hasCheckData) {
      this.handler.postDelayed(this.CheckHasDataRunnable, 30000L);
    }
    AppData.isCheckScale = false;
    if (AppData.reCheck)
    {
      Intent localIntent = new Intent();
      localIntent.setFlags(268435456);
      localIntent.setClass(this, ReCheckActivity.class);
      startActivity(localIntent);
      exit();
      ExitApplication.getInstance().exit(this);
    }
    do
    {
      do
      {
        return;
        new Thread(this).start();
      } while (!BluetoolUtil.bleflag);
      this.singleton = BlueSingleton.getInstance(this.handler);
    } while ((!this.mBluetoothAdapter.isEnabled()) || (this.singleton.getmConnected()) || (this.singleton.getmScanning()));
    this.singleton.scanLeDevice(true, this, this.mServiceConnection);
  }
  
  protected void onStart()
  {
    if ((!BluetoolUtil.bleflag) && (UtilConstants.serveIntent == null))
    {
      UtilConstants.serveIntent = new Intent(this, TimeService.class);
      startService(UtilConstants.serveIntent);
      new Thread(this.ScanRunnable).start();
      TimeService.scale_connect_state = this.scale_connect_state;
    }
    if ((BluetoolUtil.bleflag) && (!receiverReleased))
    {
      registerReceiver(this.mGattUpdateReceiver, BluetoothLeService.makeGattUpdateIntentFilter());
      receiverReleased = false;
    }
    super.onStart();
  }
  
  public void run()
  {
    if (this.ItemID != 0) {
      try
      {
        UtilConstants.CURRENT_USER = this.uservice.find(this.ItemID);
        new SharedPreferencesUtil(this).editSharedPreferences("lefuconfig", "user", Integer.valueOf(this.ItemID));
        CacheHelper.recordListDesc = this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.BATHROOM_SCALE, this.ItemID, 167.0F);
        if ((CacheHelper.recordListDesc != null) && (CacheHelper.recordListDesc.size() > 0)) {}
        for (this.curRecord = ((Records)CacheHelper.recordListDesc.get(0));; this.curRecord = null)
        {
          this.handler.sendEmptyMessage(0);
          return;
        }
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  public void scaleChangeAlert()
  {
    Intent localIntent = new Intent();
    localIntent.setClass(getApplicationContext(), ScaleChangeAlertActivity.class);
    startActivity(localIntent);
  }
  
  public void startDiscovery()
  {
    IntentFilter localIntentFilter1 = new IntentFilter("android.bluetooth.device.action.FOUND");
    registerReceiver(this.mReceiver, localIntentFilter1);
    IntentFilter localIntentFilter2 = new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
    registerReceiver(this.mReceiver, localIntentFilter2);
    this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    this.mBtAdapter.startDiscovery();
  }
  
  public void stopDiscovery()
  {
    this.mBtAdapter.cancelDiscovery();
    unregisterReceiver(this.mReceiver);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\BathScaleActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */