package com.lefu.es.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.lefu.es.constant.AppData;
import com.lefu.es.constant.BluetoolUtil;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.db.RecordDao;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.system.ReceiveAlertActivity;
import com.lefu.es.util.MyUtil;
import com.lefu.es.util.SharedPreferencesUtil;
import com.lefu.es.util.StringUtils;
import java.io.PrintStream;

@SuppressLint({"HandlerLeak"})
public class TimeService
  extends Service
{
  static boolean isdoings = false;
  public static String scaleType;
  public static TextView scale_connect_state = null;
  private int count = 0;
  private final Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      label161:
      String str1;
      label427:
      label470:
      label502:
      for (;;)
      {
        return;
        switch (paramAnonymousMessage.arg1)
        {
        case 2: 
        default: 
          return;
        }
        TimeService.this.count = 0;
        AppData.hasCheckData = true;
        Toast.makeText(TimeService.this, TimeService.this.getString(2131361910), 0).show();
        Toast.makeText(TimeService.this, TimeService.this.getString(2131361910), 0).show();
        TimeService.this.save2Device();
        if (TimeService.scale_connect_state != null)
        {
          TimeService.scale_connect_state.setText(2131361916);
          return;
          byte[] arrayOfByte1 = (byte[])paramAnonymousMessage.obj;
          byte[] arrayOfByte2 = new byte[paramAnonymousMessage.arg1];
          int i;
          if (arrayOfByte2 != null)
          {
            i = 0;
            if (i < arrayOfByte2.length) {}
          }
          else
          {
            str1 = StringUtils.bytes2HexString(arrayOfByte2);
            Intent localIntent = new Intent("ACTION_READ_DATA");
            localIntent.putExtra("readMessage", str1);
            TimeService.this.sendBroadcast(localIntent);
            System.out.println("读取到数据：" + str1);
            if (str1.length() >= 31)
            {
              if (System.currentTimeMillis() - UtilConstants.receiveDataTime < 1000L) {
                continue;
              }
              UtilConstants.receiveDataTime = System.currentTimeMillis();
            }
            if (!str1.equals(UtilConstants.ERROR_CODE)) {
              break label470;
            }
            if (TimeService.this.count >= 3) {
              break label427;
            }
            TimeService localTimeService = TimeService.this;
            localTimeService.count = (1 + localTimeService.count);
            TimeService.this.save2Device();
          }
          for (;;)
          {
            if ((str1.equals(UtilConstants.ERROR_CODE_GETDATE)) || (str1.length() < 32)) {
              break label502;
            }
            String str2 = (String)UtilConstants.su.readbackUp("lefuconfig", "bluetooth_type" + UtilConstants.CURRENT_USER.getId(), String.class);
            if ((str2 == null) || ("".equals(str2)) || (AppData.isCheckScale)) {
              break label504;
            }
            TimeService.this.dueDate(str1);
            TimeService.this.send_shutdown();
            if (TimeService.scale_connect_state == null) {
              break;
            }
            TimeService.scale_connect_state.setText(2131361917);
            return;
            arrayOfByte2[i] = arrayOfByte1[i];
            i++;
            break label161;
            Toast.makeText(TimeService.this, TimeService.this.getString(2131361909), 1).show();
            Toast.makeText(TimeService.this, TimeService.this.getString(2131361909), 1).show();
            continue;
            if (str1.equals(UtilConstants.ERROR_CODE_TEST)) {
              Toast.makeText(TimeService.this, TimeService.this.getString(2131361912), 0).show();
            }
          }
        }
      }
      label504:
      String str3 = "";
      if ((str1.toLowerCase().startsWith(UtilConstants.BODY_SCALE)) && (str1.length() > 31)) {
        str3 = UtilConstants.BODY_SCALE;
      }
      if ((str1.toLowerCase().startsWith(UtilConstants.BATHROOM_SCALE)) && (str1.length() > 31)) {
        str3 = UtilConstants.BATHROOM_SCALE;
      }
      if ((str1.toLowerCase().startsWith(UtilConstants.BABY_SCALE)) && (str1.length() > 31)) {
        str3 = UtilConstants.BABY_SCALE;
      }
      if ((str1.toLowerCase().startsWith(UtilConstants.KITCHEN_SCALE)) && (str1.length() > 31)) {
        str3 = UtilConstants.KITCHEN_SCALE;
      }
      UtilConstants.CURRENT_SCALE = str3;
      TimeService.scaleType = UtilConstants.CURRENT_SCALE;
      UtilConstants.su.editSharedPreferences("lefuconfig", "scale", UtilConstants.CURRENT_SCALE);
      Boolean localBoolean = (Boolean)UtilConstants.su.readbackUp("lefuconfig", "reCheck", Boolean.valueOf(false));
      UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
      if ((localBoolean == null) || (!localBoolean.booleanValue())) {}
      for (;;)
      {
        try
        {
          TimeService.this.uservice.save(UtilConstants.CURRENT_USER);
          UtilConstants.CURRENT_USER = TimeService.this.uservice.find(TimeService.this.uservice.maxid());
          UtilConstants.SELECT_USER = UtilConstants.CURRENT_USER.getId();
          UtilConstants.su.editSharedPreferences("lefuconfig", "user", Integer.valueOf(UtilConstants.SELECT_USER));
          UtilConstants.su.editSharedPreferences("lefuconfig", "bluetooth_type" + UtilConstants.CURRENT_USER.getId(), "BT");
          RecordDao.dueDate(TimeService.this.recordService, str1);
          TimeService.this.send_shutdown();
          return;
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
          continue;
        }
        try
        {
          TimeService.this.uservice.update(UtilConstants.CURRENT_USER);
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
      }
    }
  };
  private RecordService recordService;
  private UserService uservice;
  
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
  
  private void save2Device()
  {
    if (BluetoolUtil.mChatService != null)
    {
      String str = MyUtil.getUserInfo();
      System.out.println("发送数据：" + str);
      byte[] arrayOfByte = StringUtils.hexStringToByteArray(str);
      BluetoolUtil.mChatService.write(arrayOfByte);
    }
  }
  
  private void send_shutdown()
  {
    if (BluetoolUtil.mChatService != null)
    {
      byte[] arrayOfByte = StringUtils.hexStringToByteArray(UtilConstants.SCALE_ORDER_SHUTDOWN);
      BluetoolUtil.mChatService.write(arrayOfByte);
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
  
  public void dueDate(String paramString)
  {
    try
    {
      if (!isdoings)
      {
        setIsdoing(true);
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
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    this.uservice = new UserService(this);
    this.recordService = new RecordService(this);
    if (BluetoolUtil.mChatService == null) {
      BluetoolUtil.mChatService = new BluetoothChatService(this, this.handler);
    }
    if ((BluetoolUtil.mChatService != null) && (BluetoolUtil.mChatService.getState() == 0)) {
      BluetoolUtil.mChatService.start();
    }
    if (UtilConstants.CURRENT_USER == null) {
      UtilConstants.CURRENT_USER = (UserModel)JSONObject.parseObject((String)UtilConstants.su.readbackUp("lefuconfig", "addUser", ""), UserModel.class);
    }
  }
  
  public void onDestroy()
  {
    stopSelf();
    super.onDestroy();
  }
  
  public void onStart(Intent paramIntent, int paramInt)
  {
    super.onStart(paramIntent, paramInt);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\service\TimeService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */