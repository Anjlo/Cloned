package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.LinearLayout;
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
public class BodyFatScaleActivity
  extends Activity
  implements Runnable
{
  private static final boolean D = true;
  public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
  public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
  private static final int REQUEST_ENABLE_BT_CLICK = 31;
  private static final String TAG = "BodyFatActivity";
  private static boolean receiverReleased = false;
  private Runnable CheckHasDataRunnable = new Runnable()
  {
    public void run()
    {
      if ((!AppData.hasCheckData) && (BodyFatScaleActivity.this.isCurrentActivoty) && (!UtilConstants.isTipChangeScale))
      {
        BodyFatScaleActivity.this.scaleChangeAlert();
        UtilConstants.isTipChangeScale = true;
      }
    }
  };
  private int ItemID;
  private Runnable ScanRunnable = new Runnable()
  {
    public void run()
    {
      BodyFatScaleActivity.this.startDiscovery();
    }
  };
  private MyPageAdapter adapter = null;
  private TextView bmi_tv;
  private TextView bmr_tv;
  private TextView bodyfat_tv;
  private TextView bodywater_tv;
  private TextView bone_tv;
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
      localIntent.setClass(BodyFatScaleActivity.this, UserListActivity.class);
      BodyFatScaleActivity.this.startActivity(localIntent);
    }
  };
  private MyTextView2 compare_tv;
  private Records curRecord;
  private Button deletdImg;
  private GuideView guideView;
  private GuideView guideView2;
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
          BodyFatScaleActivity.this.setViews();
          BodyFatScaleActivity.this.Init(CacheHelper.recordListDesc);
        } while ((!TextUtils.isEmpty(UtilConstants.FIRST_INSTALL_BODY)) || (CacheHelper.recordListDesc.size() != 1));
        BodyFatScaleActivity.this.showTipMask();
        return;
      case 1: 
        BodyFatScaleActivity.this.setViews();
        return;
      case 5: 
        BodyFatScaleActivity.this.exit();
        ExitApplication.getInstance().exit(BodyFatScaleActivity.this);
        return;
      }
      if (UtilConstants.su == null) {
        UtilConstants.su = new SharedPreferencesUtil(LoadingActivity.mainActivty);
      }
      UtilConstants.su.editSharedPreferences("lefuconfig", "scale", UtilConstants.CURRENT_SCALE);
      try
      {
        BodyFatScaleActivity.this.uservice.update(UtilConstants.CURRENT_USER);
        ExitApplication.getInstance().exit(BodyFatScaleActivity.this);
        Intent localIntent = new Intent();
        localIntent.setClass(BodyFatScaleActivity.this, LoadingActivity.class);
        BodyFatScaleActivity.this.startActivity(localIntent);
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
      localIntent.setClass(BodyFatScaleActivity.this, RecordListActivity.class);
      localIntent.putExtra("type", 0);
      if (paramAnonymousView.getTag() != null) {
        localIntent.putExtra("id", ((Records)paramAnonymousView.getTag()).getId());
      }
      for (;;)
      {
        localIntent.addFlags(131072);
        BodyFatScaleActivity.this.startActivityForResult(localIntent, 0);
        return;
        localIntent.putExtra("id", -1);
      }
    }
  };
  private LayoutInflater inflater;
  private Button infoImg;
  private ImageView intentImg;
  private boolean isCurrentActivoty = true;
  private boolean isNotOpenBL = false;
  private boolean isServiceReg = false;
  View.OnClickListener layoutClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      Intent localIntent = new Intent();
      localIntent.setClass(BodyFatScaleActivity.this, RecordListActivity.class);
      if (paramAnonymousView.getTag() != null) {
        localIntent.putExtra("type", ((Integer)paramAnonymousView.getTag()).intValue());
      }
      localIntent.addFlags(131072);
      BodyFatScaleActivity.this.startActivityForResult(localIntent, 0);
    }
  };
  private ImageView leftImg;
  private LinearLayout lineLayout1;
  private LinearLayout lineLayout2;
  private LinearLayout lineLayout3;
  private LinearLayout lineLayout4;
  private LinearLayout lineLayout5;
  private LinearLayout lineLayout7;
  private LinearLayout lineLayout8;
  private LinearLayout linearLayout77;
  private BluetoothAdapter mBluetoothAdapter;
  private BluetoothLeService mBluetoothLeService;
  private BluetoothAdapter mBtAdapter;
  private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      if ("com.example.bluetooth.le.ACTION_GATT_CONNECTED".equals(str1)) {
        BodyFatScaleActivity.this.singleton.setmConnected(true);
      }
      String str2;
      label341:
      label509:
      label511:
      label594:
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
            BodyFatScaleActivity.this.scale_connect_state.setText(2131361917);
          } while ((BodyFatScaleActivity.this.isNotOpenBL) || (!BodyFatScaleActivity.this.singleton.getmConnected()));
          BodyFatScaleActivity.this.singleton.setmConnected(false);
          if (BodyFatScaleActivity.this.mBluetoothLeService != null)
          {
            boolean bool = BodyFatScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
            Log.d("BodyFatActivity", "Connect request result=" + bool);
          }
          BodyFatScaleActivity.this.singleton.scanLeDevice(true, BodyFatScaleActivity.this, BodyFatScaleActivity.this.mServiceConnection);
          return;
          if ("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED".equals(str1))
          {
            BodyFatScaleActivity.this.sendCodeCount = 0;
            BodyFatScaleActivity.this.scale_connect_state.setText(2131361916);
            AppData.hasCheckData = true;
            Toast.makeText(BodyFatScaleActivity.this, BodyFatScaleActivity.this.getString(2131361910), 1).show();
            Toast.makeText(BodyFatScaleActivity.this, BodyFatScaleActivity.this.getString(2131361910), 1).show();
            new Thread(new Runnable()
            {
              public void run()
              {
                if (BodyFatScaleActivity.this.mBluetoothLeService != null) {
                  BodyFatScaleActivity.this.send_Data();
                }
              }
            }).start();
            return;
          }
        } while (!"com.example.bluetooth.le.ACTION_DATA_AVAILABLE".equals(str1));
        str2 = paramAnonymousIntent.getStringExtra("com.example.bluetooth.le.EXTRA_DATA");
        System.out.println("读取到数据：" + str2);
        if (str2.equals(UtilConstants.ERROR_CODE)) {
          if (BodyFatScaleActivity.this.sendCodeCount < 1)
          {
            if (BodyFatScaleActivity.this.mBluetoothLeService != null) {
              BodyFatScaleActivity.this.send_Data();
            }
            BodyFatScaleActivity localBodyFatScaleActivity = BodyFatScaleActivity.this;
            localBodyFatScaleActivity.sendCodeCount = (1 + localBodyFatScaleActivity.sendCodeCount);
            if (System.currentTimeMillis() - UtilConstants.receiveDataTime < 1000L) {
              break label509;
            }
            UtilConstants.receiveDataTime = System.currentTimeMillis();
            if ((str2.startsWith(UtilConstants.BODY_SCALE)) || (str2.length() <= 31)) {
              break label594;
            }
            if (!str2.startsWith(UtilConstants.BATHROOM_SCALE)) {
              break label511;
            }
            UtilConstants.CURRENT_SCALE = UtilConstants.BATHROOM_SCALE;
            UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
            RecordDao.dueDate(BodyFatScaleActivity.this.recordService, str2);
          }
        }
        for (;;)
        {
          BodyFatScaleActivity.this.handler.sendEmptyMessage(101);
          return;
          Toast.makeText(BodyFatScaleActivity.this, BodyFatScaleActivity.this.getString(2131361909), 1).show();
          Toast.makeText(BodyFatScaleActivity.this, BodyFatScaleActivity.this.getString(2131361909), 1).show();
          break label341;
          if (!str2.equals(UtilConstants.ERROR_CODE_TEST)) {
            break label341;
          }
          Toast.makeText(BodyFatScaleActivity.this, BodyFatScaleActivity.this.getString(2131361912), 0).show();
          break label341;
          break;
          if (str2.startsWith(UtilConstants.BABY_SCALE))
          {
            UtilConstants.CURRENT_SCALE = UtilConstants.BABY_SCALE;
            UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
            RecordDao.dueDate(BodyFatScaleActivity.this.recordService, str2);
          }
          else if (str2.startsWith(UtilConstants.KITCHEN_SCALE))
          {
            UtilConstants.CURRENT_SCALE = UtilConstants.KITCHEN_SCALE;
            UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
            RecordDao.dueKitchenDate(BodyFatScaleActivity.this.recordService, str2, null);
          }
        }
        if (str2.equals(UtilConstants.ERROR_CODE_GETDATE))
        {
          BodyFatScaleActivity.this.openErrorDiolg("2");
          return;
        }
      } while ((!str2.startsWith("c")) || (str2.length() <= 31));
      BodyFatScaleActivity.this.dueDate(str2);
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
            BodyFatScaleActivity.this.stopDiscovery();
            BodyFatScaleActivity.this.handler.postDelayed(BodyFatScaleActivity.this.ScanRunnable, 15000L);
          }
        }
      }
      while (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(str1))
      {
        BluetoothDevice localBluetoothDevice;
        String str2;
        return;
      }
      BodyFatScaleActivity.this.stopDiscovery();
      BodyFatScaleActivity.this.handler.postDelayed(BodyFatScaleActivity.this.ScanRunnable, 10000L);
    }
  };
  public final ServiceConnection mServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      BodyFatScaleActivity.this.mBluetoothLeService = ((BluetoothLeService.LocalBinder)paramAnonymousIBinder).getService();
      if (!BodyFatScaleActivity.this.mBluetoothLeService.initialize()) {
        BodyFatScaleActivity.this.finish();
      }
      BodyFatScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
      try
      {
        Thread.sleep(200L);
        BodyFatScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
        BodyFatScaleActivity.this.isServiceReg = true;
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
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      BodyFatScaleActivity.this.isServiceReg = false;
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
        localIntent2.setClass(BodyFatScaleActivity.this, InfoActivity.class);
        BodyFatScaleActivity.this.startActivity(localIntent2);
        return;
      case 2131165227: 
        Intent localIntent1 = new Intent();
        localIntent1.setFlags(268435456);
        localIntent1.setClass(BodyFatScaleActivity.this, SettingActivity.class);
        BodyFatScaleActivity.this.startActivity(localIntent1);
        return;
      }
      BodyFatScaleActivity.this.dialog(BodyFatScaleActivity.this.getString(2131361845), paramAnonymousView.getId());
    }
  };
  private TextView musle_tv;
  private TextView norecord_tv;
  private ViewPager pager;
  private TextView physicage_tv;
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
  private TextView visal_tv;
  private TextView weight_textView17;
  
  private void Init(List<Records> paramList)
  {
    LayoutInflater localLayoutInflater;
    Iterator localIterator;
    if (this.views == null)
    {
      this.views = new ArrayList();
      if ((paramList != null) && (paramList.size() > 0))
      {
        this.weight_textView17.setVisibility(8);
        this.rightImg.setVisibility(0);
        this.leftImg.setVisibility(0);
        if (paramList.size() > 1) {
          this.rightImg.setImageDrawable(getResources().getDrawable(2130837533));
        }
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
    this.weight_textView17.setVisibility(0);
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
    this.weight_textView17 = ((TextView)findViewById(2131165263));
    this.norecord_tv = ((TextView)findViewById(2131165206));
    this.username_tv = ((TextView)findViewById(2131165199));
    this.compare_tv = ((MyTextView2)findViewById(2131165208));
    this.targetv = ((MyTextView2)findViewById(2131165231));
    if (UtilConstants.su != null) {
      UtilConstants.CHOICE_KG = (String)UtilConstants.su.readbackUp("lefuconfig", "unit", UtilConstants.UNIT_KG);
    }
    if (UtilConstants.CURRENT_USER != null) {
      this.compare_tv.setTexts("0.0" + UtilConstants.CURRENT_USER.getDanwei(), null);
    }
    this.rlGuide = ((RelativeLayout)findViewById(2131165213));
    this.bodywater_tv = ((TextView)findViewById(2131165224));
    this.bodyfat_tv = ((TextView)findViewById(2131165235));
    this.bone_tv = ((TextView)findViewById(2131165240));
    this.musle_tv = ((TextView)findViewById(2131165254));
    this.visal_tv = ((TextView)findViewById(2131165248));
    this.bmi_tv = ((TextView)findViewById(2131165244));
    this.bmr_tv = ((TextView)findViewById(2131165257));
    this.time_tv = ((TextView)findViewById(2131165198));
    this.physicage_tv = ((TextView)findViewById(2131165261));
    this.tvBmi = ((TextView)findViewById(2131165214));
    this.scale_connect_state = ((TextView)findViewById(2131165201));
    this.infoImg = ((Button)findViewById(2131165226));
    this.setingImg = ((Button)findViewById(2131165227));
    this.deletdImg = ((Button)findViewById(2131165228));
    this.headImg = ((ImageView)findViewById(2131165264));
    this.intentImg = ((ImageView)findViewById(2131165200));
    this.leftImg = ((ImageView)findViewById(2131165205));
    this.rightImg = ((ImageView)findViewById(2131165209));
    this.infoImg.setOnClickListener(this.menuBtnOnClickListener);
    this.deletdImg.setOnClickListener(this.menuBtnOnClickListener);
    this.setingImg.setOnClickListener(this.menuBtnOnClickListener);
    this.headImg.setOnClickListener(this.btnOnClickListener);
    this.intentImg.setOnClickListener(this.btnOnClickListener);
    this.pager = ((ViewPager)findViewById(2131165207));
    this.pager.bringToFront();
    this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
    {
      public void onPageScrollStateChanged(int paramAnonymousInt) {}
      
      public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2) {}
      
      public void onPageSelected(int paramAnonymousInt)
      {
        if ((BodyFatScaleActivity.this.views != null) && (BodyFatScaleActivity.this.views.size() > 0))
        {
          if ((paramAnonymousInt == -1 + BodyFatScaleActivity.this.views.size()) || (paramAnonymousInt == 0)) {
            if (paramAnonymousInt == 0)
            {
              BodyFatScaleActivity.this.pager.setCurrentItem(paramAnonymousInt + 1);
              if (BodyFatScaleActivity.this.pager.getCurrentItem() >= 2) {
                break label226;
              }
              BodyFatScaleActivity.this.leftImg.setImageDrawable(BodyFatScaleActivity.this.getResources().getDrawable(2130837602));
            }
          }
          for (;;)
          {
            if (BodyFatScaleActivity.this.pager.getCurrentItem() < -2 + BodyFatScaleActivity.this.views.size()) {
              break label251;
            }
            BodyFatScaleActivity.this.rightImg.setImageDrawable(BodyFatScaleActivity.this.getResources().getDrawable(2130837641));
            return;
            BodyFatScaleActivity.this.pager.setCurrentItem(paramAnonymousInt - 1);
            break;
            if (paramAnonymousInt > 0) {}
            for (BodyFatScaleActivity.this.selectedPosition = (paramAnonymousInt - 1);; BodyFatScaleActivity.this.selectedPosition = 0)
            {
              BodyFatScaleActivity.this.curRecord = ((Records)CacheHelper.recordListDesc.get(BodyFatScaleActivity.this.selectedPosition));
              BodyFatScaleActivity.this.handler.sendEmptyMessage(1);
              break;
            }
            label226:
            BodyFatScaleActivity.this.leftImg.setImageDrawable(BodyFatScaleActivity.this.getResources().getDrawable(2130837529));
          }
          label251:
          BodyFatScaleActivity.this.rightImg.setImageDrawable(BodyFatScaleActivity.this.getResources().getDrawable(2130837533));
          return;
        }
        BodyFatScaleActivity.this.leftImg.setImageDrawable(BodyFatScaleActivity.this.getResources().getDrawable(2130837602));
        BodyFatScaleActivity.this.rightImg.setImageDrawable(BodyFatScaleActivity.this.getResources().getDrawable(2130837641));
      }
    });
    if (UtilConstants.CURRENT_USER != null)
    {
      this.username_tv.setText(UtilConstants.CURRENT_USER.getUserName());
      if (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_KG)) {
        break label634;
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
      label634:
      if ((UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ST))) {
        this.targetv.setTexts(UtilTooth.onePoint(UtilTooth.kgToLB_target(UtilConstants.CURRENT_USER.getTargweight())) + getText(2131361890), null);
      } else {
        this.targetv.setTexts(UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight()) + getText(2131361889), null);
      }
    }
  }
  
  private void layoutView()
  {
    this.lineLayout1 = ((LinearLayout)findViewById(2131165211));
    this.lineLayout2 = ((LinearLayout)findViewById(2131165234));
    this.lineLayout3 = ((LinearLayout)findViewById(2131165242));
    this.lineLayout4 = ((LinearLayout)findViewById(2131165238));
    this.lineLayout5 = ((LinearLayout)findViewById(2131165246));
    this.lineLayout7 = ((LinearLayout)findViewById(2131165251));
    this.lineLayout8 = ((LinearLayout)findViewById(2131165252));
    this.linearLayout77 = ((LinearLayout)findViewById(2131165259));
    this.lineLayout1.setTag(Integer.valueOf(4));
    this.lineLayout2.setTag(Integer.valueOf(2));
    this.lineLayout3.setTag(Integer.valueOf(6));
    this.lineLayout4.setTag(Integer.valueOf(1));
    this.lineLayout5.setTag(Integer.valueOf(5));
    this.lineLayout7.setTag(Integer.valueOf(3));
    this.lineLayout8.setTag(Integer.valueOf(7));
    this.linearLayout77.setTag(Integer.valueOf(8));
    this.linearLayout77.setVisibility(8);
    this.lineLayout1.setOnClickListener(this.layoutClickListener);
    this.lineLayout2.setOnClickListener(this.layoutClickListener);
    this.lineLayout3.setOnClickListener(this.layoutClickListener);
    this.lineLayout4.setOnClickListener(this.layoutClickListener);
    this.lineLayout5.setOnClickListener(this.layoutClickListener);
    this.lineLayout7.setOnClickListener(this.layoutClickListener);
    this.lineLayout8.setOnClickListener(this.layoutClickListener);
    this.linearLayout77.setOnClickListener(this.layoutClickListener);
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
        break label654;
      }
      this.targetv.setTexts(UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight()) + getText(2131361889), null);
      if ((UtilConstants.CURRENT_USER.getPer_photo() != null) && (!"".equals(UtilConstants.CURRENT_USER.getPer_photo())) && (!UtilConstants.CURRENT_USER.getPer_photo().equals("null")))
      {
        Bitmap localBitmap = imageUtil.getBitmapfromPath(UtilConstants.CURRENT_USER.getPer_photo());
        this.headImg.setImageBitmap(localBitmap);
      }
    }
    if (this.curRecord != null)
    {
      this.bodywater_tv.setText(this.curRecord.getRbodywater() + "%");
      this.bodyfat_tv.setText(this.curRecord.getRbodyfat() + "%");
      if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_KG))
      {
        this.bone_tv.setText(this.curRecord.getRbone() + UtilConstants.UNIT_KG);
        this.musle_tv.setText(this.curRecord.getRmuscle() + UtilConstants.UNIT_KG);
        label287:
        this.visal_tv.setText(this.curRecord.getRvisceralfat());
        this.bmi_tv.setText(this.curRecord.getRbmi());
        this.bmr_tv.setText(this.curRecord.getRbmr() + "kcal");
        if (this.curRecord.getBodyAge() <= 0.0F) {
          break label1052;
        }
        this.linearLayout77.setVisibility(0);
        this.physicage_tv.setText(UtilTooth.keep0Point(this.curRecord.getBodyAge()));
      }
      for (;;)
      {
        Date localDate = UtilTooth.stringToTime(this.curRecord.getRecordTime());
        if (localDate != null)
        {
          Locale.getDefault();
          this.time_tv.setText(StringUtils.getDateString(localDate, 5));
        }
        if (UtilConstants.CURRENT_USER != null)
        {
          float f5 = UtilTooth.countBMI2(this.curRecord.getRweight(), UtilConstants.CURRENT_USER.getBheigth() / 100.0F);
          this.curRecord.setRbmi(UtilTooth.myround(f5));
          this.tvBmi.setText(this.curRecord.getRbmi());
        }
        RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        int j = DisplayUtil.dip2px(this, 64.0F);
        int k = DisplayUtil.getWidth(this) - j;
        localLayoutParams2.setMargins((int)UtilTooth.changeBMI(this.curRecord.getRbmi(), k), 0, 0, 0);
        this.rlGuide.setLayoutParams(localLayoutParams2);
        if (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_KG)) {
          break label1282;
        }
        if ((this.curRecord.getCompareRecord() != null) && (!"".equals(this.curRecord.getCompareRecord()))) {
          break label1074;
        }
        this.compare_tv.setTexts("0.0", null);
        this.compare_tv.setTexts("0.0 " + getText(2131361889), null);
        return;
        label654:
        if ((UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ST)))
        {
          this.targetv.setTexts(UtilTooth.onePoint(UtilTooth.kgToLB_target(UtilConstants.CURRENT_USER.getTargweight())) + getText(2131361890), null);
          break;
        }
        this.targetv.setTexts(UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight()) + getText(2131361889), null);
        break;
        if ((UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB)))
        {
          this.bone_tv.setText(UtilTooth.kgToLB(this.curRecord.getRbone()) + UtilConstants.UNIT_LB);
          this.musle_tv.setText(UtilTooth.kgToLB(this.curRecord.getRmuscle()) + UtilConstants.UNIT_LB);
          break label287;
        }
        if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ST))
        {
          this.bone_tv.setText(UtilTooth.kgToLB(this.curRecord.getRbone()) + UtilConstants.UNIT_ST);
          this.musle_tv.setText(UtilTooth.kgToLB(this.curRecord.getRmuscle()) + UtilConstants.UNIT_ST);
          break label287;
        }
        this.bone_tv.setText(this.curRecord.getRbone() + UtilConstants.UNIT_KG);
        this.musle_tv.setText(this.curRecord.getRmuscle() + UtilConstants.UNIT_KG);
        break label287;
        label1052:
        this.linearLayout77.setVisibility(8);
        this.physicage_tv.setText("0");
      }
      label1074:
      float f4 = new BigDecimal(Double.parseDouble(this.curRecord.getCompareRecord())).setScale(2, 4).floatValue();
      if (f4 > 0.0F)
      {
        this.compare_tv.setTexts("↑" + UtilTooth.myroundString3(new StringBuilder(String.valueOf(Math.abs(f4))).toString()) + getText(2131361889), null);
        return;
      }
      if (f4 < 0.0F)
      {
        this.compare_tv.setTexts("↓" + UtilTooth.myroundString3(new StringBuilder(String.valueOf(Math.abs(f4))).toString()) + getText(2131361889), null);
        return;
      }
      this.compare_tv.setTexts(UtilTooth.myroundString3(new StringBuilder(String.valueOf(this.curRecord.getCompareRecord())).toString()) + getText(2131361889), null);
      return;
      label1282:
      if ((UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB)))
      {
        if ((this.curRecord.getCompareRecord() == null) || ("".equals(this.curRecord.getCompareRecord().trim())))
        {
          this.curRecord.setCompareRecord("0");
          this.compare_tv.setTexts("0.0  " + getText(2131361890), null);
          return;
        }
        float f1 = Float.parseFloat(this.curRecord.getCompareRecord());
        if (f1 > 0.0F)
        {
          this.compare_tv.setTexts("↑" + UtilTooth.kgToLB(Math.abs(Float.parseFloat(this.curRecord.getCompareRecord()))) + " " + getText(2131361890), null);
          return;
        }
        if (f1 < 0.0F)
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
        float f3 = Float.parseFloat(this.curRecord.getCompareRecord());
        String str = UtilTooth.kgToLB_new(Math.abs(Float.parseFloat(this.curRecord.getCompareRecord())));
        String[] arrayOfString = UtilTooth.kgToStLbForScaleFat2(Math.abs(Float.parseFloat(this.curRecord.getCompareRecord())));
        if (f3 > 0.0F)
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
        if (f3 < 0.0F)
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
      if ((this.curRecord.getCompareRecord() == null) || ("".equals(this.curRecord.getCompareRecord())))
      {
        this.compare_tv.setTexts("0.0", null);
        this.compare_tv.setTexts("0.0 " + getText(2131361889), null);
        return;
      }
      float f2 = new BigDecimal(Double.parseDouble(this.curRecord.getCompareRecord())).setScale(2, 4).floatValue();
      if (f2 > 0.0F)
      {
        this.compare_tv.setTexts("↑" + UtilTooth.myroundString3(new StringBuilder(String.valueOf(Math.abs(f2))).toString()) + getText(2131361889), null);
        return;
      }
      if (f2 < 0.0F)
      {
        this.compare_tv.setTexts("↓" + UtilTooth.myroundString3(new StringBuilder(String.valueOf(Math.abs(f2))).toString()) + getText(2131361889), null);
        return;
      }
      this.compare_tv.setTexts(UtilTooth.myroundString3(new StringBuilder(String.valueOf(this.curRecord.getCompareRecord())).toString()) + getText(2131361889), null);
      return;
    }
    this.bodywater_tv.setText("0.0%");
    this.bodyfat_tv.setText("0.0%");
    this.bone_tv.setText("0.0");
    this.musle_tv.setText("0.0");
    this.visal_tv.setText("0");
    this.bmi_tv.setText("0");
    this.bmr_tv.setText("0kcal");
    this.tvBmi.setText("0.0");
    this.physicage_tv.setText("0");
    if (UtilConstants.CURRENT_USER != null) {
      this.compare_tv.setTexts("0.0" + UtilConstants.CURRENT_USER.getDanwei(), null);
    }
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-2, -2);
    int i = DisplayUtil.dip2px(this, 64.0F);
    localLayoutParams1.setMargins((int)UtilTooth.changeBMI(0.0F, DisplayUtil.getWidth(this) - i), 0, 0, 0);
    this.rlGuide.setLayoutParams(localLayoutParams1);
  }
  
  private void showAlertDailog(String paramString)
  {
    new com.lefu.es.view.AlertDialog(this).builder().setTitle(getResources().getString(2131361793)).setMsg(paramString).setPositiveButton(getResources().getString(2131361852), new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (UtilConstants.su == null) {
          UtilConstants.su = new SharedPreferencesUtil(BodyFatScaleActivity.this);
        }
        UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_dailog", "1");
        UtilConstants.FIRST_INSTALL_DAILOG = "1";
      }
    }).show();
  }
  
  private void showTipMask()
  {
    TextView localTextView1 = new TextView(this);
    localTextView1.setText("");
    localTextView1.setTextColor(getResources().getColor(2131230727));
    localTextView1.setTextSize(16.0F);
    localTextView1.setGravity(17);
    TextView localTextView2 = new TextView(this);
    localTextView2.setText(getResources().getString(2131361792));
    localTextView2.setTextColor(getResources().getColor(2131230727));
    localTextView2.setTextSize(16.0F);
    localTextView2.setGravity(17);
    this.guideView2 = GuideView.Builder.newInstance(this).setTargetView(this.pager).setCustomGuideView(localTextView2).setDirction(GuideView.Direction.BOTTOM).setShape(GuideView.MyShape.CIRCULAR).setBgColor(getResources().getColor(2131230720)).setRadius(200).setOnclickListener(new GuideView.OnClickCallback()
    {
      public void onClickedGuideView()
      {
        BodyFatScaleActivity.this.guideView2.hide();
        BodyFatScaleActivity.this.guideView.hide();
        if (UtilConstants.su == null) {
          UtilConstants.su = new SharedPreferencesUtil(BodyFatScaleActivity.this);
        }
        UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_body", "1");
        UtilConstants.FIRST_INSTALL_BODY = "1";
      }
    }).build();
    this.guideView = GuideView.Builder.newInstance(this).setTargetView(this.lineLayout1).setCustomGuideView(localTextView1).setDirction(GuideView.Direction.BOTTOM).setShape(GuideView.MyShape.ELLIPSE).setBgColor(getResources().getColor(2131230720)).setOnclickListener(new GuideView.OnClickCallback()
    {
      public void onClickedGuideView()
      {
        BodyFatScaleActivity.this.guideView.hide();
        BodyFatScaleActivity.this.guideView2.hide();
        if (UtilConstants.su == null) {
          UtilConstants.su = new SharedPreferencesUtil(BodyFatScaleActivity.this);
        }
        UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_body", "1");
        UtilConstants.FIRST_INSTALL_BODY = "1";
      }
    }).build();
    this.guideView.show();
    this.guideView2.show();
  }
  
  private void stopScanService()
  {
    if (UtilConstants.serveIntent != null) {
      stopService(UtilConstants.serveIntent);
    }
    if (BluetoolUtil.bleflag) {
      this.singleton.scanLeDevice(false, this, this.mServiceConnection);
    }
  }
  
  public View creatView(Records paramRecords, ViewGroup paramViewGroup)
  {
    View localView = null;
    MyTextView localMyTextView = null;
    TextView localTextView = null;
    if (0 == 0)
    {
      localView = this.inflater.inflate(2130903085, null);
      localMyTextView = (MyTextView)localView.findViewById(2131165429);
      localTextView = (TextView)localView.findViewById(2131165430);
      localView.setTag(paramRecords);
      localView.setOnClickListener(this.imgOnClickListener);
    }
    if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ST)) {
      if (UtilConstants.CURRENT_SCALE.equals(UtilConstants.BODY_SCALE))
      {
        String[] arrayOfString = UtilTooth.kgToStLbForScaleFat2(paramRecords.getRweight());
        localMyTextView.setTexts(arrayOfString[0], arrayOfString[1]);
        if (localTextView != null) {
          localTextView.setText(getText(2131361892));
        }
      }
    }
    do
    {
      do
      {
        do
        {
          return localView;
          localMyTextView.setTexts(UtilTooth.kgToLB_ForFatScale(paramRecords.getRweight()), null);
        } while (localTextView == null);
        localTextView.setText(getText(2131361892));
        return localView;
        if (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) {
          break;
        }
        localMyTextView.setTexts(UtilTooth.kgToLB_ForFatScale(paramRecords.getRweight()), null);
      } while (localTextView == null);
      localTextView.setText(getText(2131361890));
      return localView;
      localMyTextView.setTexts(paramRecords.getRweight(), null);
    } while (localTextView == null);
    localTextView.setText(getText(2131361889));
    return localView;
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
        if (BodyFatScaleActivity.this.curRecord != null) {
          BodyFatScaleActivity.this.recordService.delete(BodyFatScaleActivity.this.curRecord);
        }
        CacheHelper.recordListDesc = BodyFatScaleActivity.this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, BodyFatScaleActivity.this.ItemID, UtilConstants.CURRENT_USER.getBheigth());
        if ((CacheHelper.recordListDesc != null) && (CacheHelper.recordListDesc.size() > 0)) {}
        for (BodyFatScaleActivity.this.curRecord = ((Records)CacheHelper.recordListDesc.get(0));; BodyFatScaleActivity.this.curRecord = null)
        {
          BodyFatScaleActivity.this.handler.sendEmptyMessage(0);
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
        if ((localRecords != null) && (localRecords.getScaleType() != null) && (localRecords.getScaleType().equalsIgnoreCase(UtilConstants.CURRENT_SCALE)))
        {
          if ((TextUtils.isEmpty(UtilConstants.FIRST_INSTALL_DAILOG)) && (localRecords.getRbodywater() == 0.0F)) {
            showAlertDailog(getResources().getString(2131361803));
          }
          if ((localRecords.getUgroup() != null) && (Integer.parseInt(localRecords.getUgroup().replace("P", "")) < 10))
          {
            Bundle localBundle = new Bundle();
            localBundle.putSerializable("record", localRecords);
            localIntent.putExtras(localBundle);
            localIntent.setClass(getApplicationContext(), ReceiveAlertActivity.class);
            startActivity(localIntent);
          }
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
    setContentView(2130903043);
    this.recordService = new RecordService(this);
    this.inflater = ((LayoutInflater)getSystemService("layout_inflater"));
    this.ItemID = getIntent().getIntExtra("ItemID", 0);
    initView();
    layoutView();
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
        break label229;
      }
    }
    for (;;)
    {
      try
      {
        this.mBluetoothAdapter = ((BluetoothManager)getSystemService("bluetooth")).getAdapter();
        if (!this.mBluetoothAdapter.isEnabled())
        {
          startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 31);
          this.isNotOpenBL = true;
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
      label229:
      if (BluetoolUtil.mBluetoothAdapter == null) {
        BluetoolUtil.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      }
      if ((BluetoolUtil.mBluetoothAdapter != null) && (!BluetoolUtil.mBluetoothAdapter.isEnabled())) {
        startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 3);
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
    stopScanService();
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
        CacheHelper.recordListDesc = this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, this.ItemID, 167.0F);
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
    System.out.println("BT开始扫描...");
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


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\BodyFatScaleActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */