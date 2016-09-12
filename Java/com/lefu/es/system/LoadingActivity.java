package com.lefu.es.system;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.DisplayMetrics;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.UserService;
import com.lefu.es.util.SharedPreferencesUtil;
import java.util.List;

public class LoadingActivity
  extends Activity
{
  public static boolean isPad = false;
  public static boolean isV = true;
  public static LoadingActivity mainActivty = null;
  private UserService uservice;
  
  private void getFirstUser()
  {
    try
    {
      List localList = this.uservice.getAllDatas();
      if ((localList != null) && (localList.size() > 0))
      {
        UtilConstants.CURRENT_USER = (UserModel)localList.get(0);
        UtilConstants.SELECT_USER = ((UserModel)localList.get(0)).getId();
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  private void isPad()
  {
    new DisplayMetrics();
    DisplayMetrics localDisplayMetrics = getResources().getDisplayMetrics();
    int i = localDisplayMetrics.widthPixels;
    int j = localDisplayMetrics.heightPixels;
    float f = localDisplayMetrics.density;
    if (Math.sqrt(Math.pow(i, 2.0D) + Math.pow(j, 2.0D)) / (160.0F * f) >= 6.0D) {
      isPad = true;
    }
    if (i > j)
    {
      isV = false;
      return;
    }
    isV = true;
  }
  
  public void loadData()
  {
    UtilConstants.SELECT_USER = ((Integer)UtilConstants.su.readbackUp("lefuconfig", "user", Integer.valueOf(0))).intValue();
    if (UtilConstants.SELECT_USER == 0) {
      getFirstUser();
    }
    while (UtilConstants.CURRENT_USER == null)
    {
      Intent localIntent1 = new Intent();
      localIntent1.setFlags(268435456);
      localIntent1.setClass(this, UserAddActivity.class);
      startActivity(localIntent1);
      return;
      try
      {
        UtilConstants.CURRENT_USER = this.uservice.find(UtilConstants.SELECT_USER);
        if (UtilConstants.CURRENT_USER == null) {
          getFirstUser();
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    UtilConstants.CURRENT_SCALE = UtilConstants.CURRENT_USER.getScaleType();
    String str = (String)UtilConstants.su.readbackUp("lefuconfig", "bluetooth_type" + UtilConstants.CURRENT_USER.getId(), String.class);
    if ((str != null) && (!"".equals(str)))
    {
      UtilConstants.su.editSharedPreferences("lefuconfig", "reCheck", Boolean.valueOf(false));
      Intent localIntent3 = new Intent();
      if (UtilConstants.BATHROOM_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
        localIntent3.setClass(this, BathScaleActivity.class);
      }
      for (;;)
      {
        localIntent3.putExtra("ItemID", UtilConstants.SELECT_USER);
        startActivity(localIntent3);
        return;
        if (UtilConstants.BABY_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
          localIntent3.setClass(this, BabyScaleActivity.class);
        } else if (UtilConstants.KITCHEN_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
          localIntent3.setClass(this, KitchenScaleActivity.class);
        } else {
          localIntent3.setClass(this, BodyFatScaleActivity.class);
        }
      }
    }
    int i = Build.VERSION.SDK_INT;
    Intent localIntent2 = new Intent();
    localIntent2.setFlags(268435456);
    if (i < 18) {
      localIntent2.setClass(this, AutoBTActivity.class);
    }
    for (;;)
    {
      startActivity(localIntent2);
      return;
      localIntent2.setClass(this, AutoBLEActivity.class);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903056);
    mainActivty = this;
    isPad();
    UtilConstants.su = new SharedPreferencesUtil(this);
    UtilConstants.FIRST_INSTALL_BODY = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_body", "");
    UtilConstants.FIRST_INSTALL_DETAIL = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_detail", "");
    UtilConstants.FIRST_INSTALL_DAILOG = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_dailog", "");
    UtilConstants.FIRST_INSTALL_SHARE = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_share", "");
    UtilConstants.FIRST_INSTALL_BATH = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_bath", "");
    UtilConstants.FIRST_INSTALL_BABY = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_baby", "");
    UtilConstants.FIRST_INSTALL_NATURION = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_naturion", "");
    UtilConstants.FIRST_INSTALL_NATURION_DAILOG = (String)UtilConstants.su.readbackUp("lefuconfig", "first_naturion_dailog", "");
    this.uservice = new UserService(this);
    ExitApplication.getInstance().addActivity(this);
    UtilConstants.checkScaleTimes = 0;
    UtilConstants.su = new SharedPreferencesUtil(this);
    UtilConstants.SELECT_LANGUAGE = 1;
  }
  
  protected void onDestroy()
  {
    if (UtilConstants.serveIntent != null) {
      stopService(UtilConstants.serveIntent);
    }
    System.exit(0);
    super.onDestroy();
  }
  
  protected void onResume()
  {
    super.onResume();
    loadData();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\LoadingActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */