package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentManager;
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
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.lefu.es.entity.NutrientBo;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.BluetoothChatService;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.RecordService;
import com.lefu.es.service.TimeService;
import com.lefu.es.service.UserService;
import com.lefu.es.system.fragment.MyDialogFragment;
import com.lefu.es.system.fragment.MyDialogFragment.NatureSelectListener;
import com.lefu.es.util.MyUtil;
import com.lefu.es.util.SharedPreferencesUtil;
import com.lefu.es.util.StringUtils;
import com.lefu.es.util.Tool;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.view.GuideView;
import com.lefu.es.view.GuideView.Builder;
import com.lefu.es.view.GuideView.Direction;
import com.lefu.es.view.GuideView.MyShape;
import com.lefu.es.view.GuideView.OnClickCallback;
import com.lefu.es.view.MyTextView;
import com.lefu.es.view.kmpautotextview.KMPAutoComplTextView;
import com.lefu.es.view.kmpautotextview.KMPAutoComplTextView.OnPopupItemClickListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@SuppressLint({"HandlerLeak"})
public class KitchenScaleActivity
  extends Activity
  implements Runnable, MyDialogFragment.NatureSelectListener
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
      if ((!AppData.hasCheckData) && (KitchenScaleActivity.this.isCurrentActivoty) && (!UtilConstants.isTipChangeScale))
      {
        KitchenScaleActivity.this.scaleChangeAlert();
        UtilConstants.isTipChangeScale = true;
      }
    }
  };
  private int ItemID;
  private Runnable ScanRunnable = new Runnable()
  {
    public void run()
    {
      KitchenScaleActivity.this.startDiscovery();
    }
  };
  private MyPageAdapter adapter = null;
  private ArrayAdapter<String> autoAdapter = null;
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
      case 2131165200: 
      default: 
      case 2131165264: 
      case 2131165309: 
      case 2131165308: 
        do
        {
          return;
          Intent localIntent = new Intent();
          localIntent.setClass(KitchenScaleActivity.this, UserListActivity.class);
          KitchenScaleActivity.this.startActivity(localIntent);
          return;
          if (TextUtils.isEmpty(KitchenScaleActivity.this.search_et.getText().toString()))
          {
            Toast.makeText(KitchenScaleActivity.this, "Please input something", 0).show();
            return;
          }
          ArrayList localArrayList = CacheHelper.findNutrientByName(KitchenScaleActivity.this.search_et.getText().toString());
          if ((localArrayList == null) || (localArrayList.size() == 0))
          {
            Toast.makeText(KitchenScaleActivity.this, "No Data had been found", 0).show();
            return;
          }
          FragmentManager localFragmentManager = KitchenScaleActivity.this.getFragmentManager();
          MyDialogFragment.newInstance(localArrayList).show(localFragmentManager, "dialog");
          return;
        } while ((KitchenScaleActivity.this.curRecord == null) || (KitchenScaleActivity.this.selectNutrient == null));
        for (;;)
        {
          try
          {
            List localList = KitchenScaleActivity.this.recordService.findRecordsByScaleTypeAndFoodNameAndKg(UtilConstants.KITCHEN_SCALE, KitchenScaleActivity.this.selectNutrient.getNutrientDesc(), KitchenScaleActivity.this.curRecord.getRweight());
            if ((localList != null) && (localList.size() > 0)) {
              break;
            }
            KitchenScaleActivity.this.curRecord.setRphoto(KitchenScaleActivity.this.selectNutrient.getNutrientDesc());
            if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB))
            {
              KitchenScaleActivity.this.curRecord.setUnitType(3);
              KitchenScaleActivity.this.curRecord = RecordDao.handleKitchenData(KitchenScaleActivity.this.recordService, KitchenScaleActivity.this.curRecord, KitchenScaleActivity.this.selectNutrient);
              CacheHelper.recordListDesc = KitchenScaleActivity.this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, KitchenScaleActivity.this.ItemID, UtilConstants.CURRENT_USER.getBheigth());
              KitchenScaleActivity.this.handler.sendEmptyMessage(0);
              KitchenScaleActivity.this.food_name.setText("");
              return;
            }
          }
          catch (Exception localException2)
          {
            localException2.printStackTrace();
            return;
          }
          if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) {
            KitchenScaleActivity.this.curRecord.setUnitType(1);
          } else if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ML)) {
            KitchenScaleActivity.this.curRecord.setUnitType(2);
          } else if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ML2)) {
            KitchenScaleActivity.this.curRecord.setUnitType(4);
          } else if ((UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_KG)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_G))) {
            KitchenScaleActivity.this.curRecord.setUnitType(0);
          }
        }
      }
      try
      {
        if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB)) {
          UtilConstants.CURRENT_USER.setDanwei(UtilConstants.UNIT_G);
        }
        for (;;)
        {
          KitchenScaleActivity.this.handler.sendEmptyMessage(0);
          KitchenScaleActivity.this.uservice.update(UtilConstants.CURRENT_USER);
          return;
          if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) {
            UtilConstants.CURRENT_USER.setDanwei(UtilConstants.UNIT_FATLB);
          } else if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ML)) {
            UtilConstants.CURRENT_USER.setDanwei(UtilConstants.UNIT_LB);
          } else if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ML2)) {
            UtilConstants.CURRENT_USER.setDanwei(UtilConstants.UNIT_ML);
          } else if ((UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_KG)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_G))) {
            UtilConstants.CURRENT_USER.setDanwei(UtilConstants.UNIT_ML2);
          }
        }
        return;
      }
      catch (Exception localException1) {}
    }
  };
  private Records curRecord;
  private Button deletdImg;
  private TextView food_name;
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
          KitchenScaleActivity.this.setViews();
          KitchenScaleActivity.this.Init(CacheHelper.recordListDesc);
        } while ((!TextUtils.isEmpty(UtilConstants.FIRST_INSTALL_NATURION)) || (CacheHelper.recordListDesc.size() <= 0));
        KitchenScaleActivity.this.showTipMask();
        return;
      case 1: 
        KitchenScaleActivity.this.setViews();
        return;
      case 5: 
        KitchenScaleActivity.this.exit();
        ExitApplication.getInstance().exit(KitchenScaleActivity.this);
        return;
      }
      if (UtilConstants.su == null) {
        UtilConstants.su = new SharedPreferencesUtil(LoadingActivity.mainActivty);
      }
      UtilConstants.su.editSharedPreferences("lefuconfig", "scale", UtilConstants.CURRENT_SCALE);
      try
      {
        KitchenScaleActivity.this.uservice.update(UtilConstants.CURRENT_USER);
        ExitApplication.getInstance().exit(KitchenScaleActivity.this);
        Intent localIntent = new Intent();
        localIntent.setClass(KitchenScaleActivity.this, LoadingActivity.class);
        KitchenScaleActivity.this.startActivity(localIntent);
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
      localIntent.setClass(KitchenScaleActivity.this, RecordKitchenListActivity.class);
      localIntent.putExtra("type", 0);
      if (paramAnonymousView.getTag() != null) {
        localIntent.putExtra("id", ((Records)paramAnonymousView.getTag()).getId());
      }
      for (;;)
      {
        localIntent.addFlags(131072);
        KitchenScaleActivity.this.startActivityForResult(localIntent, 0);
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
      localIntent.setClass(KitchenScaleActivity.this, RecordKitchenListActivity.class);
      if (paramAnonymousView.getTag() != null) {
        localIntent.putExtra("type", ((Integer)paramAnonymousView.getTag()).intValue());
      }
      localIntent.addFlags(131072);
      KitchenScaleActivity.this.startActivityForResult(localIntent, 0);
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
        KitchenScaleActivity.this.singleton.setmConnected(true);
      }
      String str2;
      label439:
      do
      {
        for (;;)
        {
          return;
          if ("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED".equals(str1))
          {
            KitchenScaleActivity.this.scale_connect_state.setText(2131361917);
            if ((!KitchenScaleActivity.this.isNotOpenBL) && (KitchenScaleActivity.this.singleton.getmConnected()))
            {
              KitchenScaleActivity.this.singleton.setmConnected(false);
              if (KitchenScaleActivity.this.mBluetoothLeService != null)
              {
                boolean bool = KitchenScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
                Log.d("BodyFatActivity", "Connect request result=" + bool);
              }
              KitchenScaleActivity.this.singleton.scanLeDevice(true, KitchenScaleActivity.this, KitchenScaleActivity.this.mServiceConnection);
            }
          }
          else
          {
            if ("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED".equals(str1))
            {
              KitchenScaleActivity.this.sendCodeCount = 0;
              KitchenScaleActivity.this.scale_connect_state.setText(2131361916);
              AppData.hasCheckData = true;
              Toast.makeText(KitchenScaleActivity.this, KitchenScaleActivity.this.getString(2131361915), 1).show();
              Toast.makeText(KitchenScaleActivity.this, KitchenScaleActivity.this.getString(2131361915), 1).show();
              new Thread(new Runnable()
              {
                public void run()
                {
                  if (KitchenScaleActivity.this.mBluetoothLeService != null) {
                    KitchenScaleActivity.this.send_Data();
                  }
                }
              }).start();
              return;
            }
            if ("com.example.bluetooth.le.ACTION_DATA_AVAILABLE".equals(str1))
            {
              str2 = paramAnonymousIntent.getStringExtra("com.example.bluetooth.le.EXTRA_DATA");
              System.out.println("读取到数据：" + str2);
              if (str2.equals(UtilConstants.ERROR_CODE)) {
                if (KitchenScaleActivity.this.sendCodeCount < 1)
                {
                  if (KitchenScaleActivity.this.mBluetoothLeService != null) {
                    KitchenScaleActivity.this.send_Data();
                  }
                  KitchenScaleActivity localKitchenScaleActivity = KitchenScaleActivity.this;
                  localKitchenScaleActivity.sendCodeCount = (1 + localKitchenScaleActivity.sendCodeCount);
                }
              }
              while (System.currentTimeMillis() - UtilConstants.receiveDataTime >= 1000L)
              {
                UtilConstants.receiveDataTime = System.currentTimeMillis();
                if (!str2.startsWith(UtilConstants.KITCHEN_SCALE)) {
                  break label439;
                }
                KitchenScaleActivity.this.dueDate(str2);
                return;
                Toast.makeText(KitchenScaleActivity.this, KitchenScaleActivity.this.getString(2131361909), 1).show();
                continue;
                if (str2.equals(UtilConstants.ERROR_CODE_TEST)) {
                  Toast.makeText(KitchenScaleActivity.this, KitchenScaleActivity.this.getString(2131361912), 0).show();
                }
              }
            }
          }
        }
        if (str2.length() > 31)
        {
          if (str2.startsWith(UtilConstants.BATHROOM_SCALE)) {
            UtilConstants.CURRENT_SCALE = UtilConstants.BATHROOM_SCALE;
          }
          for (;;)
          {
            UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
            RecordDao.dueDate(KitchenScaleActivity.this.recordService, str2);
            KitchenScaleActivity.this.handler.sendEmptyMessage(101);
            return;
            if (str2.startsWith(UtilConstants.BABY_SCALE)) {
              UtilConstants.CURRENT_SCALE = UtilConstants.BABY_SCALE;
            } else if (str2.startsWith(UtilConstants.BODY_SCALE)) {
              UtilConstants.CURRENT_SCALE = UtilConstants.BODY_SCALE;
            }
          }
        }
      } while (!str2.equals(UtilConstants.ERROR_CODE_GETDATE));
      KitchenScaleActivity.this.openErrorDiolg("2");
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
            KitchenScaleActivity.this.stopDiscovery();
            KitchenScaleActivity.this.handler.postDelayed(KitchenScaleActivity.this.ScanRunnable, 15000L);
          }
        }
      }
      while (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(str1))
      {
        BluetoothDevice localBluetoothDevice;
        String str2;
        return;
      }
      KitchenScaleActivity.this.stopDiscovery();
      KitchenScaleActivity.this.handler.postDelayed(KitchenScaleActivity.this.ScanRunnable, 10000L);
    }
  };
  public final ServiceConnection mServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      KitchenScaleActivity.this.mBluetoothLeService = ((BluetoothLeService.LocalBinder)paramAnonymousIBinder).getService();
      if (!KitchenScaleActivity.this.mBluetoothLeService.initialize()) {
        KitchenScaleActivity.this.finish();
      }
      KitchenScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
      try
      {
        Thread.sleep(200L);
        KitchenScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
        KitchenScaleActivity.this.isServiceReg = true;
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
      KitchenScaleActivity.this.isServiceReg = false;
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
        if (UtilConstants.CURRENT_SCALE.equals(UtilConstants.KITCHEN_SCALE))
        {
          Intent localIntent2 = new Intent();
          localIntent2.setFlags(268435456);
          localIntent2.setClass(KitchenScaleActivity.this, KitchenInfoActivity.class);
          KitchenScaleActivity.this.startActivity(localIntent2);
          return;
        }
        Intent localIntent3 = new Intent();
        localIntent3.setFlags(268435456);
        localIntent3.setClass(KitchenScaleActivity.this, InfoActivity.class);
        KitchenScaleActivity.this.startActivity(localIntent3);
        return;
      case 2131165227: 
        Intent localIntent1 = new Intent();
        localIntent1.setFlags(268435456);
        localIntent1.setClass(KitchenScaleActivity.this, SettingActivity.class);
        KitchenScaleActivity.this.startActivity(localIntent1);
        return;
      }
      KitchenScaleActivity.this.dialog(KitchenScaleActivity.this.getString(2131361845), paramAnonymousView.getId());
    }
  };
  private TextView milkname_tx;
  private TextView musle_tv;
  private TextView norecord_tv;
  private ViewPager pager;
  private TextView physicage_tv;
  private List<Records> rList;
  private RecordService recordService;
  private ImageView rightImg;
  private TextView save_tv;
  private TextView scale_connect_state;
  private KMPAutoComplTextView search_et;
  private TextView search_tv;
  private NutrientBo selectNutrient = null;
  private int selectedPosition = -1;
  private int sendCodeCount = 0;
  private Button setingImg;
  private BlueSingleton singleton;
  private TextView time_tv;
  private Button unit_btn;
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
    this.weight_textView17.setVisibility(8);
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
    try
    {
      this.uservice = new UserService(this);
      this.weight_textView17 = ((TextView)findViewById(2131165263));
      this.norecord_tv = ((TextView)findViewById(2131165206));
      this.username_tv = ((TextView)findViewById(2131165199));
      this.milkname_tx = ((TextView)findViewById(2131165311));
      if (UtilConstants.su != null) {
        UtilConstants.CHOICE_KG = (String)UtilConstants.su.readbackUp("lefuconfig", "unit", UtilConstants.UNIT_KG);
      }
      this.bodywater_tv = ((TextView)findViewById(2131165224));
      this.bodyfat_tv = ((TextView)findViewById(2131165235));
      this.bone_tv = ((TextView)findViewById(2131165240));
      this.bmi_tv = ((TextView)findViewById(2131165254));
      this.visal_tv = ((TextView)findViewById(2131165248));
      this.musle_tv = ((TextView)findViewById(2131165244));
      this.bmr_tv = ((TextView)findViewById(2131165257));
      this.time_tv = ((TextView)findViewById(2131165198));
      this.physicage_tv = ((TextView)findViewById(2131165261));
      this.scale_connect_state = ((TextView)findViewById(2131165201));
      this.food_name = ((TextView)findViewById(2131165307));
      this.search_et = ((KMPAutoComplTextView)findViewById(2131165310));
      this.search_tv = ((TextView)findViewById(2131165309));
      this.save_tv = ((TextView)findViewById(2131165308));
      this.infoImg = ((Button)findViewById(2131165226));
      this.setingImg = ((Button)findViewById(2131165227));
      this.deletdImg = ((Button)findViewById(2131165228));
      this.headImg = ((ImageView)findViewById(2131165264));
      this.intentImg = ((ImageView)findViewById(2131165200));
      this.leftImg = ((ImageView)findViewById(2131165205));
      this.rightImg = ((ImageView)findViewById(2131165209));
      this.unit_btn = ((Button)findViewById(2131165312));
      this.infoImg.setOnClickListener(this.menuBtnOnClickListener);
      this.deletdImg.setOnClickListener(this.menuBtnOnClickListener);
      this.setingImg.setOnClickListener(this.menuBtnOnClickListener);
      this.headImg.setOnClickListener(this.btnOnClickListener);
      this.intentImg.setOnClickListener(this.btnOnClickListener);
      this.search_tv.setOnClickListener(this.btnOnClickListener);
      this.save_tv.setOnClickListener(this.btnOnClickListener);
      this.unit_btn.setOnClickListener(this.btnOnClickListener);
      this.search_et.setDatas(CacheHelper.nutrientTempNameList);
      this.search_et.inputTextNull(this.food_name, this.selectNutrient);
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          if ((CacheHelper.nutrientNameList != null) && (CacheHelper.nutrientNameList.size() > 0)) {
            KitchenScaleActivity.this.search_et.setDatas(CacheHelper.nutrientNameList);
          }
        }
      }, 5000L);
      this.search_et.setOnPopupItemClickListener(new KMPAutoComplTextView.OnPopupItemClickListener()
      {
        public void onPopupItemClick(CharSequence paramAnonymousCharSequence)
        {
          if (TextUtils.isEmpty(paramAnonymousCharSequence)) {
            return;
          }
          NutrientBo localNutrientBo = CacheHelper.queryNutrientsByName(paramAnonymousCharSequence.toString());
          if (localNutrientBo == null)
          {
            Toast.makeText(KitchenScaleActivity.this, "No Data had been found", 0).show();
            return;
          }
          KitchenScaleActivity.this.search_et.setText(localNutrientBo.getNutrientDesc());
          KitchenScaleActivity.this.selectNutrient = localNutrientBo;
          KitchenScaleActivity.this.natureSelectComplete(localNutrientBo);
        }
      });
      this.pager = ((ViewPager)findViewById(2131165207));
      this.pager.bringToFront();
      this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
      {
        public void onPageScrollStateChanged(int paramAnonymousInt) {}
        
        public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2) {}
        
        public void onPageSelected(int paramAnonymousInt)
        {
          if ((KitchenScaleActivity.this.views != null) && (KitchenScaleActivity.this.views.size() > 0))
          {
            if ((paramAnonymousInt == -1 + KitchenScaleActivity.this.views.size()) || (paramAnonymousInt == 0)) {
              if (paramAnonymousInt == 0)
              {
                KitchenScaleActivity.this.pager.setCurrentItem(paramAnonymousInt + 1);
                if (KitchenScaleActivity.this.pager.getCurrentItem() >= 2) {
                  break label226;
                }
                KitchenScaleActivity.this.leftImg.setImageDrawable(KitchenScaleActivity.this.getResources().getDrawable(2130837602));
              }
            }
            for (;;)
            {
              if (KitchenScaleActivity.this.pager.getCurrentItem() < -2 + KitchenScaleActivity.this.views.size()) {
                break label251;
              }
              KitchenScaleActivity.this.rightImg.setImageDrawable(KitchenScaleActivity.this.getResources().getDrawable(2130837641));
              return;
              KitchenScaleActivity.this.pager.setCurrentItem(paramAnonymousInt - 1);
              break;
              if (paramAnonymousInt > 0) {}
              for (KitchenScaleActivity.this.selectedPosition = (paramAnonymousInt - 1);; KitchenScaleActivity.this.selectedPosition = 0)
              {
                KitchenScaleActivity.this.curRecord = ((Records)CacheHelper.recordListDesc.get(KitchenScaleActivity.this.selectedPosition));
                KitchenScaleActivity.this.handler.sendEmptyMessage(1);
                break;
              }
              label226:
              KitchenScaleActivity.this.leftImg.setImageDrawable(KitchenScaleActivity.this.getResources().getDrawable(2130837529));
            }
            label251:
            KitchenScaleActivity.this.rightImg.setImageDrawable(KitchenScaleActivity.this.getResources().getDrawable(2130837533));
            return;
          }
          KitchenScaleActivity.this.leftImg.setImageDrawable(KitchenScaleActivity.this.getResources().getDrawable(2130837602));
          KitchenScaleActivity.this.rightImg.setImageDrawable(KitchenScaleActivity.this.getResources().getDrawable(2130837641));
        }
      });
      return;
    }
    catch (Exception localException)
    {
      Log.e("BodyFatActivity", localException.getMessage());
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
    String str = MyUtil.getUserInfo();
    if (TextUtils.isEmpty(str)) {}
    for (;;)
    {
      return;
      System.out.println("发送数据=" + str);
      BluetoothGattCharacteristic localBluetoothGattCharacteristic1 = this.mBluetoothLeService.getCharacteristic(this.mBluetoothLeService.getSupportedGattServices(), "fff1");
      if (localBluetoothGattCharacteristic1 != null)
      {
        localBluetoothGattCharacteristic1.setValue(StringUtils.hexStringToByteArray(str));
        this.mBluetoothLeService.wirteCharacteristic(localBluetoothGattCharacteristic1);
        localBluetoothGattCharacteristic1.getProperties();
      }
      try
      {
        Thread.sleep(500L);
        BluetoothGattCharacteristic localBluetoothGattCharacteristic2 = this.mBluetoothLeService.getCharacteristic(this.mBluetoothLeService.getSupportedGattServices(), "fff4");
        if (localBluetoothGattCharacteristic2 == null) {
          continue;
        }
        this.mBluetoothLeService.setCharacteristicNotification(localBluetoothGattCharacteristic2, true);
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
      if ((UtilConstants.CURRENT_USER.getPer_photo() != null) && (!"".equals(UtilConstants.CURRENT_USER.getPer_photo())) && (!UtilConstants.CURRENT_USER.getPer_photo().equals("null")))
      {
        Bitmap localBitmap = imageUtil.getBitmapfromPath(UtilConstants.CURRENT_USER.getPer_photo());
        this.headImg.setImageBitmap(localBitmap);
      }
    }
    if (this.curRecord != null)
    {
      Date localDate = UtilTooth.stringToTime(this.curRecord.getRecordTime());
      if (localDate != null)
      {
        Locale.getDefault();
        this.time_tv.setText(StringUtils.getDateString(localDate, 5));
      }
      this.bodywater_tv.setText(UtilTooth.keep2Point(this.curRecord.getRbodywater()) + "kcal");
      this.bodyfat_tv.setText(UtilTooth.keep2Point(this.curRecord.getRbodyfat()) + "g");
      this.bone_tv.setText(UtilTooth.keep2Point(this.curRecord.getRbone()) + "g");
      this.musle_tv.setText(UtilTooth.keep2Point(this.curRecord.getRmuscle()) + "g");
      this.visal_tv.setText(UtilTooth.keep2Point(this.curRecord.getRvisceralfat()) + "g");
      this.bmr_tv.setText(UtilTooth.keep2Point(this.curRecord.getRbmi()) + "mg");
      this.bmi_tv.setText(UtilTooth.keep2Point(this.curRecord.getRbmr()) + "mg");
      this.physicage_tv.setText(UtilTooth.keep2Point(this.curRecord.getBodyAge()) + "mg");
      if ((UtilConstants.CURRENT_USER != null) && (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ML2)))
      {
        this.milkname_tx.setVisibility(0);
        return;
      }
      this.milkname_tx.setVisibility(8);
      return;
    }
    this.bodywater_tv.setText("0.0kcal");
    this.bodyfat_tv.setText("0.0g");
    this.bone_tv.setText("0.0g");
    this.musle_tv.setText("0.0g");
    this.visal_tv.setText("0.0g");
    this.bmi_tv.setText("0.0mg");
    this.bmr_tv.setText("0.0mg");
    this.physicage_tv.setText("0.0mg");
  }
  
  private void showTipMask()
  {
    TextView localTextView1 = new TextView(this);
    localTextView1.setText("");
    localTextView1.setTextColor(getResources().getColor(2131230727));
    localTextView1.setTextSize(16.0F);
    localTextView1.setGravity(17);
    TextView localTextView2 = new TextView(this);
    localTextView2.setText(getResources().getString(2131361802));
    localTextView2.setTextColor(getResources().getColor(2131230727));
    localTextView2.setTextSize(16.0F);
    localTextView2.setGravity(17);
    this.guideView2 = GuideView.Builder.newInstance(this).setTargetView(this.pager).setCustomGuideView(localTextView2).setDirction(GuideView.Direction.BOTTOM).setShape(GuideView.MyShape.CIRCULAR).setBgColor(getResources().getColor(2131230720)).setRadius(200).setOnclickListener(new GuideView.OnClickCallback()
    {
      public void onClickedGuideView()
      {
        KitchenScaleActivity.this.guideView2.hide();
        KitchenScaleActivity.this.guideView.hide();
        if (UtilConstants.su == null) {
          UtilConstants.su = new SharedPreferencesUtil(KitchenScaleActivity.this);
        }
        UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_naturion", "1");
        UtilConstants.FIRST_INSTALL_NATURION = "1";
      }
    }).build();
    this.guideView = GuideView.Builder.newInstance(this).setTargetView(this.lineLayout1).setCustomGuideView(localTextView1).setDirction(GuideView.Direction.BOTTOM).setShape(GuideView.MyShape.ELLIPSE).setBgColor(getResources().getColor(2131230720)).setOnclickListener(new GuideView.OnClickCallback()
    {
      public void onClickedGuideView()
      {
        KitchenScaleActivity.this.guideView.hide();
        KitchenScaleActivity.this.guideView2.hide();
        if (UtilConstants.su == null) {
          UtilConstants.su = new SharedPreferencesUtil(KitchenScaleActivity.this);
        }
        UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_naturion", "1");
        UtilConstants.FIRST_INSTALL_NATURION = "1";
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
      localView = this.inflater.inflate(2130903071, null);
      localMyTextView = (MyTextView)localView.findViewById(2131165429);
      localTextView = (TextView)localView.findViewById(2131165430);
      localView.setTag(paramRecords);
      localView.setOnClickListener(this.imgOnClickListener);
    }
    if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB))
    {
      localMyTextView.setTexts(UtilTooth.kgToFloz(paramRecords.getRweight()), null);
      if (localTextView != null) {
        localTextView.setText(getText(2131361900));
      }
    }
    do
    {
      do
      {
        do
        {
          do
          {
            return localView;
            if (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) {
              break;
            }
            localMyTextView.setTexts(UtilTooth.kgToLBoz(paramRecords.getRweight()), null);
          } while (localTextView == null);
          localTextView.setText(getText(2131361893));
          return localView;
          if (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ML2)) {
            break;
          }
          localMyTextView.setTexts(UtilTooth.keep0Point(UtilTooth.kgToML(paramRecords.getRweight())), null);
        } while (localTextView == null);
        localTextView.setText(getText(2131361901));
        return localView;
        if (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ML)) {
          break;
        }
        localMyTextView.setTexts(UtilTooth.keep0Point(paramRecords.getRweight()), null);
      } while (localTextView == null);
      localTextView.setText(getText(2131361899));
      return localView;
      localMyTextView.setTexts(UtilTooth.keep0Point(paramRecords.getRweight()), null);
    } while (localTextView == null);
    localTextView.setText(getText(2131361897));
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
        if (KitchenScaleActivity.this.curRecord != null) {
          KitchenScaleActivity.this.recordService.delete(KitchenScaleActivity.this.curRecord);
        }
        CacheHelper.recordListDesc = KitchenScaleActivity.this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, KitchenScaleActivity.this.ItemID, UtilConstants.CURRENT_USER.getBheigth());
        if ((CacheHelper.recordListDesc != null) && (CacheHelper.recordListDesc.size() > 0)) {}
        for (KitchenScaleActivity.this.curRecord = ((Records)CacheHelper.recordListDesc.get(0));; KitchenScaleActivity.this.curRecord = null)
        {
          KitchenScaleActivity.this.handler.sendEmptyMessage(0);
          break;
        }
      }
    });
    localBuilder.create().show();
  }
  
  /* Error */
  public void dueDate(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic 1030	com/lefu/es/ble/BlueSingleton:isIsdoing	()Z
    //   5: ifne +160 -> 165
    //   8: iconst_1
    //   9: invokestatic 1034	com/lefu/es/ble/BlueSingleton:setIsdoing	(Z)V
    //   12: new 624	android/content/Intent
    //   15: dup
    //   16: invokespecial 1035	android/content/Intent:<init>	()V
    //   19: astore_3
    //   20: aload_3
    //   21: ldc_w 643
    //   24: invokevirtual 1038	android/content/Intent:setFlags	(I)Landroid/content/Intent;
    //   27: pop
    //   28: aload_3
    //   29: ldc_w 1040
    //   32: aload_1
    //   33: invokevirtual 1044	android/content/Intent:putExtra	(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   36: pop
    //   37: aload_0
    //   38: getfield 374	com/lefu/es/system/KitchenScaleActivity:recordService	Lcom/lefu/es/service/RecordService;
    //   41: aload_1
    //   42: invokestatic 1048	com/lefu/es/util/MyUtil:parseMeaage	(Lcom/lefu/es/service/RecordService;Ljava/lang/String;)Lcom/lefu/es/entity/Records;
    //   45: astore 6
    //   47: aload 6
    //   49: ifnull +116 -> 165
    //   52: aload 6
    //   54: invokevirtual 1051	com/lefu/es/entity/Records:getScaleType	()Ljava/lang/String;
    //   57: ifnull +108 -> 165
    //   60: aload 6
    //   62: invokevirtual 1051	com/lefu/es/entity/Records:getScaleType	()Ljava/lang/String;
    //   65: getstatic 1054	com/lefu/es/constant/UtilConstants:CURRENT_SCALE	Ljava/lang/String;
    //   68: invokevirtual 1058	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   71: ifeq +94 -> 165
    //   74: aload_0
    //   75: getfield 386	com/lefu/es/system/KitchenScaleActivity:food_name	Landroid/widget/TextView;
    //   78: invokevirtual 1061	android/widget/TextView:getText	()Ljava/lang/CharSequence;
    //   81: invokestatic 665	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   84: ifeq +84 -> 168
    //   87: aload_0
    //   88: aconst_null
    //   89: putfield 147	com/lefu/es/system/KitchenScaleActivity:selectNutrient	Lcom/lefu/es/entity/NutrientBo;
    //   92: aload 6
    //   94: invokevirtual 1064	com/lefu/es/entity/Records:getUgroup	()Ljava/lang/String;
    //   97: ifnull +68 -> 165
    //   100: aload 6
    //   102: invokevirtual 1064	com/lefu/es/entity/Records:getUgroup	()Ljava/lang/String;
    //   105: ldc_w 1066
    //   108: ldc_w 757
    //   111: invokevirtual 1070	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   114: invokestatic 1074	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   117: bipush 10
    //   119: if_icmpge +46 -> 165
    //   122: new 631	android/os/Bundle
    //   125: dup
    //   126: invokespecial 632	android/os/Bundle:<init>	()V
    //   129: astore 7
    //   131: aload 7
    //   133: ldc_w 1076
    //   136: aload 6
    //   138: invokevirtual 1080	android/os/Bundle:putSerializable	(Ljava/lang/String;Ljava/io/Serializable;)V
    //   141: aload_3
    //   142: aload 7
    //   144: invokevirtual 642	android/content/Intent:putExtras	(Landroid/os/Bundle;)Landroid/content/Intent;
    //   147: pop
    //   148: aload_3
    //   149: aload_0
    //   150: invokevirtual 1084	com/lefu/es/system/KitchenScaleActivity:getApplicationContext	()Landroid/content/Context;
    //   153: ldc_w 1086
    //   156: invokevirtual 1090	android/content/Intent:setClass	(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;
    //   159: pop
    //   160: aload_0
    //   161: aload_3
    //   162: invokevirtual 651	com/lefu/es/system/KitchenScaleActivity:startActivity	(Landroid/content/Intent;)V
    //   165: aload_0
    //   166: monitorexit
    //   167: return
    //   168: aload_0
    //   169: getfield 147	com/lefu/es/system/KitchenScaleActivity:selectNutrient	Lcom/lefu/es/entity/NutrientBo;
    //   172: ifnull -80 -> 92
    //   175: aload 6
    //   177: aload_0
    //   178: getfield 147	com/lefu/es/system/KitchenScaleActivity:selectNutrient	Lcom/lefu/es/entity/NutrientBo;
    //   181: invokevirtual 1095	com/lefu/es/entity/NutrientBo:getNutrientDesc	()Ljava/lang/String;
    //   184: invokevirtual 1098	com/lefu/es/entity/Records:setRphoto	(Ljava/lang/String;)V
    //   187: goto -95 -> 92
    //   190: astore_2
    //   191: aload_0
    //   192: monitorexit
    //   193: aload_2
    //   194: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	195	0	this	KitchenScaleActivity
    //   0	195	1	paramString	String
    //   190	4	2	localObject	Object
    //   19	143	3	localIntent	Intent
    //   45	131	6	localRecords	Records
    //   129	14	7	localBundle	Bundle
    // Exception table:
    //   from	to	target	type
    //   2	47	190	finally
    //   52	92	190	finally
    //   92	165	190	finally
    //   168	187	190	finally
  }
  
  public void natureSelectComplete(NutrientBo paramNutrientBo)
  {
    if (paramNutrientBo != null)
    {
      this.food_name.setText(paramNutrientBo.getNutrientDesc());
      this.selectNutrient = paramNutrientBo;
      if (this.curRecord != null)
      {
        boolean bool1 = TextUtils.isEmpty(paramNutrientBo.getNutrientCalcium());
        float f1 = 0.0F;
        if (!bool1)
        {
          boolean bool16 = Tool.isDigitsOnly(paramNutrientBo.getNutrientCalcium());
          f1 = 0.0F;
          if (bool16) {
            f1 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientCalcium()) * this.curRecord.getRweight());
          }
        }
        boolean bool2 = TextUtils.isEmpty(paramNutrientBo.getNutrientProtein());
        float f2 = 0.0F;
        if (!bool2)
        {
          boolean bool15 = Tool.isDigitsOnly(paramNutrientBo.getNutrientProtein());
          f2 = 0.0F;
          if (bool15) {
            f2 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientProtein()) * this.curRecord.getRweight());
          }
        }
        boolean bool3 = TextUtils.isEmpty(paramNutrientBo.getNutrientEnerg());
        float f3 = 0.0F;
        if (!bool3)
        {
          boolean bool14 = Tool.isDigitsOnly(paramNutrientBo.getNutrientEnerg());
          f3 = 0.0F;
          if (bool14) {
            f3 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientEnerg()) * this.curRecord.getRweight());
          }
        }
        boolean bool4 = TextUtils.isEmpty(paramNutrientBo.getNutrientCarbohydrt());
        float f4 = 0.0F;
        if (!bool4)
        {
          boolean bool13 = Tool.isDigitsOnly(paramNutrientBo.getNutrientCarbohydrt());
          f4 = 0.0F;
          if (bool13) {
            f4 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientCarbohydrt()) * this.curRecord.getRweight());
          }
        }
        boolean bool5 = TextUtils.isEmpty(paramNutrientBo.getNutrientLipidTot());
        float f5 = 0.0F;
        if (!bool5)
        {
          boolean bool12 = Tool.isDigitsOnly(paramNutrientBo.getNutrientLipidTot());
          f5 = 0.0F;
          if (bool12) {
            f5 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientLipidTot()) * this.curRecord.getRweight());
          }
        }
        boolean bool6 = TextUtils.isEmpty(paramNutrientBo.getNutrientFiberTD());
        float f6 = 0.0F;
        if (!bool6)
        {
          boolean bool11 = Tool.isDigitsOnly(paramNutrientBo.getNutrientFiberTD());
          f6 = 0.0F;
          if (bool11) {
            f6 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientFiberTD()) * this.curRecord.getRweight());
          }
        }
        boolean bool7 = TextUtils.isEmpty(paramNutrientBo.getNutrientCholestrl());
        float f7 = 0.0F;
        if (!bool7)
        {
          boolean bool10 = Tool.isDigitsOnly(paramNutrientBo.getNutrientCholestrl());
          f7 = 0.0F;
          if (bool10) {
            f7 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientCholestrl()) * this.curRecord.getRweight());
          }
        }
        boolean bool8 = TextUtils.isEmpty(paramNutrientBo.getNutrientVitB6());
        float f8 = 0.0F;
        if (!bool8)
        {
          boolean bool9 = Tool.isDigitsOnly(paramNutrientBo.getNutrientVitB6());
          f8 = 0.0F;
          if (bool9) {
            f8 = 0.01F * (Float.parseFloat(paramNutrientBo.getNutrientVitB6()) * this.curRecord.getRweight());
          }
        }
        this.bodywater_tv.setText(UtilTooth.keep2Point(f3) + "kcal");
        this.bodyfat_tv.setText(UtilTooth.keep2Point(f2) + "g");
        this.bone_tv.setText(UtilTooth.keep2Point(f5) + "g");
        this.musle_tv.setText(UtilTooth.keep2Point(f4) + "g");
        this.visal_tv.setText(UtilTooth.keep2Point(f6) + "g");
        this.bmr_tv.setText(UtilTooth.keep2Point(f7) + "mg");
        this.bmi_tv.setText(UtilTooth.keep2Point(f8) + "mg");
        this.physicage_tv.setText(UtilTooth.keep2Point(f1) + "mg");
      }
    }
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
    setContentView(2130903052);
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
  
  public void saveSearchDate() {}
  
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


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\KitchenScaleActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */