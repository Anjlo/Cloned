package com.lefu.es.system;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Button;
import com.lefu.es.ble.BlueSingleton;
import com.lefu.es.cache.CacheHelper;
import com.lefu.es.constant.BluetoolUtil;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.db.RecordDao;
import com.lefu.es.entity.NutrientBo;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.RecordService;
import com.lefu.es.service.TimeService;
import com.lefu.es.service.UserService;

public class ReceiveAlertActivity
  extends Activity
{
  String readMessage;
  private Records recod;
  private RecordService recordService;
  private SoundPool soundpool;
  private UserService uservice;
  
  private boolean isOutOfBounds(Activity paramActivity, MotionEvent paramMotionEvent)
  {
    int i = (int)paramMotionEvent.getX();
    int j = (int)paramMotionEvent.getY();
    int k = ViewConfiguration.get(paramActivity).getScaledWindowTouchSlop();
    View localView = paramActivity.getWindow().getDecorView();
    return (i < -k) || (j < -k) || (i > k + localView.getWidth()) || (j > k + localView.getHeight());
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (LoadingActivity.isPad) {
      setRequestedOrientation(2);
    }
    for (;;)
    {
      requestWindowFeature(5);
      requestWindowFeature(1);
      setContentView(2130903058);
      this.uservice = new UserService(this);
      this.recordService = new RecordService(this);
      Bundle localBundle = getIntent().getExtras();
      this.recod = ((Records)getIntent().getSerializableExtra("record"));
      this.readMessage = localBundle.getString("duedate");
      playSound();
      return;
      setRequestedOrientation(1);
    }
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    if (this.uservice != null) {
      this.uservice.closeDB();
    }
    if (this.recordService != null) {
      this.recordService.closeDB();
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
    {
      finish();
      if (!BluetoolUtil.bleflag) {
        TimeService.setIsdoing(false);
      }
      for (;;)
      {
        return true;
        BlueSingleton.setIsdoing(false);
      }
    }
    if (paramInt == 3) {
      ((NotificationManager)getSystemService("notification")).cancel(0);
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  protected void onResume()
  {
    super.onResume();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((paramMotionEvent.getAction() == 0) && (isOutOfBounds(this, paramMotionEvent))) {
      return true;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void playSound()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        ReceiveAlertActivity.this.soundpool = new SoundPool(10, 1, 5);
        int i = ReceiveAlertActivity.this.soundpool.load(ReceiveAlertActivity.this, 2131034113, 0);
        int j = ((AudioManager)ReceiveAlertActivity.this.getSystemService("audio")).getStreamVolume(3);
        try
        {
          Thread.sleep(500L);
          ReceiveAlertActivity.this.soundpool.play(i, j, j, 1, 0, 1.0F);
          return;
        }
        catch (Exception localException)
        {
          for (;;) {}
        }
      }
    }).start();
  }
  
  public void saveOrCancle(View paramView)
  {
    if ((paramView instanceof Button)) {}
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131165266: 
      if (!BluetoolUtil.bleflag) {
        TimeService.setIsdoing(false);
      }
      for (;;)
      {
        finish();
        return;
        BlueSingleton.setIsdoing(false);
      }
    }
    com.lefu.es.constant.AppData.hasCheckData = true;
    label143:
    label153:
    Intent localIntent;
    if (!BluetoolUtil.bleflag)
    {
      TimeService.setIsdoing(true);
      if ((this.recod != null) && (this.recod.getScaleType() != null))
      {
        if (!UtilConstants.KITCHEN_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
          break label221;
        }
        boolean bool = TextUtils.isEmpty(this.recod.getRphoto());
        NutrientBo localNutrientBo = null;
        if (!bool) {
          localNutrientBo = CacheHelper.queryNutrientsByName(this.recod.getRphoto());
        }
        RecordDao.dueKitchenDate(this.recordService, this.readMessage, localNutrientBo);
        if (BluetoolUtil.bleflag) {
          break label239;
        }
        TimeService.setIsdoing(false);
        localIntent = new Intent();
        if (!UtilConstants.BATHROOM_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
          break label246;
        }
        localIntent.setClass(this, BathScaleActivity.class);
      }
    }
    for (;;)
    {
      localIntent.putExtra("ItemID", UtilConstants.CURRENT_USER.getId());
      localIntent.addFlags(131072);
      startActivity(localIntent);
      finish();
      return;
      BlueSingleton.setIsdoing(true);
      break;
      label221:
      RecordDao.handleData(this.recordService, this.recod, this.readMessage);
      break label143;
      label239:
      BlueSingleton.setIsdoing(false);
      break label153;
      label246:
      if (UtilConstants.BODY_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
        localIntent.setClass(this, BodyFatScaleActivity.class);
      } else if (UtilConstants.BABY_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
        localIntent.setClass(this, BabyScaleActivity.class);
      } else if (UtilConstants.KITCHEN_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
        localIntent.setClass(this, KitchenScaleActivity.class);
      }
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\ReceiveAlertActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */