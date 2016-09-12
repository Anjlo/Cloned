package com.lefu.es.system;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.UserService;
import com.lefu.es.util.SharedPreferencesUtil;
import java.util.List;

public class ReCheckActivity
  extends Activity
  implements Runnable
{
  private UserService uservice;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    UtilConstants.su = new SharedPreferencesUtil(this);
    this.uservice = new UserService(this);
    ExitApplication.getInstance().addActivity(this);
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
  }
  
  protected void onResume()
  {
    super.onResume();
    new Thread(this).start();
  }
  
  public void run()
  {
    UtilConstants.CURRENT_SCALE = (String)UtilConstants.su.readbackUp("lefuconfig", "scale", "");
    UtilConstants.SELECT_USER = ((Integer)UtilConstants.su.readbackUp("lefuconfig", "user", Integer.valueOf(0))).intValue();
    if (UtilConstants.SELECT_USER == 0) {}
    try
    {
      if (this.uservice == null) {
        this.uservice = new UserService(this);
      }
      List localList2 = this.uservice.getAllDatas();
      if ((localList2 != null) && (localList2.size() > 0))
      {
        UtilConstants.CURRENT_USER = (UserModel)localList2.get(0);
        UtilConstants.SELECT_USER = ((UserModel)localList2.get(0)).getId();
      }
    }
    catch (Exception localException2)
    {
      int i;
      Intent localIntent;
      for (;;) {}
    }
    i = Build.VERSION.SDK_INT;
    localIntent = new Intent();
    localIntent.setFlags(268435456);
    if (i < 18) {
      localIntent.setClass(this, AutoBTActivity.class);
    }
    for (;;)
    {
      startActivity(localIntent);
      return;
      try
      {
        if (this.uservice == null) {
          this.uservice = new UserService(this);
        }
        UtilConstants.CURRENT_USER = this.uservice.find(UtilConstants.SELECT_USER);
        if (UtilConstants.CURRENT_USER != null) {
          break;
        }
        List localList1 = this.uservice.getAllDatas();
        if ((localList1 == null) || (localList1.size() <= 0)) {
          break;
        }
        UtilConstants.CURRENT_USER = (UserModel)localList1.get(0);
        UtilConstants.SELECT_USER = ((UserModel)localList1.get(0)).getId();
      }
      catch (Exception localException1) {}
      break;
      localIntent.setClass(this, AutoBLEActivity.class);
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\ReCheckActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */