package com.lefu.es.system;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lefu.es.cache.CacheHelper;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.constant.imageUtil;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.UserService;
import com.lefu.es.util.FileUtils;
import com.lefu.es.util.SharedPreferencesUtil;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class SettingActivity
  extends Activity
{
  public static SettingActivity detailActivty = null;
  private RelativeLayout about_layout;
  private Button babyScale_btn;
  private Button bathroom_btn;
  private Button bodyfat_btn;
  private Button chinesetw_btn;
  private Button close_btn;
  private Button english_btn;
  private FileUtils fileutil = null;
  private ImageView head_img;
  private ImageView home_img_btn;
  View.OnClickListener imgOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      default: 
        return;
      case 2131165405: 
        SettingActivity.this.home_img_btn.setBackgroundDrawable(SettingActivity.this.getResources().getDrawable(2130837585));
        SettingActivity.this.back2Scale();
        return;
      case 2131165457: 
        if (!SettingActivity.this.fileutil.hasSD())
        {
          Toast.makeText(SettingActivity.this, SettingActivity.this.getString(2131361928), 0).show();
          SettingActivity.this.savesAlertDialog.dismiss();
          return;
        }
        if ((CacheHelper.recordListDesc != null) && (CacheHelper.recordListDesc.size() != 0)) {}
        for (;;)
        {
          try
          {
            SettingActivity.this.fileutil.createSDFile("myrecords.txt");
            localObject = "\n";
            localIterator = CacheHelper.recordListDesc.iterator();
            if (localIterator.hasNext()) {
              continue;
            }
            SettingActivity.this.fileutil.writeSDFile((String)localObject, "myrecords.txt");
            Toast.makeText(SettingActivity.this, SettingActivity.this.getString(2131361929), 0).show();
          }
          catch (IOException localIOException)
          {
            Object localObject;
            Iterator localIterator;
            Records localRecords;
            String str2;
            Toast.makeText(SettingActivity.this, SettingActivity.this.getString(2131361930), 0).show();
            continue;
          }
          SettingActivity.this.savesAlertDialog.dismiss();
          return;
          localRecords = (Records)localIterator.next();
          str2 = localObject + localRecords.getRecordTime() + "," + localRecords.getRphoto() + " Weight:" + localRecords.getRweight() + "g,Carbohydrate:" + localRecords.getRbodywater() + "kcal,Energ:" + localRecords.getRbodyfat() + "g,Protein:" + localRecords.getRbone() + "g,Fiber:" + localRecords.getRvisceralfat() + "g,Lipid:" + localRecords.getRmuscle() + "g,Cholesterol:" + localRecords.getRbmi() + "g，Calcium:" + localRecords.getBodyAge() + "mg,Vitamin C" + localRecords.getRbmr() + "mg \n";
          localObject = str2;
          continue;
          Toast.makeText(SettingActivity.this, SettingActivity.this.getString(2131361931), 0).show();
        }
      case 2131165458: 
        SettingActivity.this.savesAlertDialog.dismiss();
        return;
      case 2131165459: 
        UtilConstants.CURRENT_SCALE = UtilConstants.BODY_SCALE;
        UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
        try
        {
          SettingActivity.this.uservice.update(UtilConstants.CURRENT_USER);
          SettingActivity.this.scaleAlertDialog.dismiss();
          return;
        }
        catch (Exception localException4)
        {
          for (;;)
          {
            localException4.printStackTrace();
          }
        }
      case 2131165460: 
        UtilConstants.CURRENT_SCALE = UtilConstants.BATHROOM_SCALE;
        UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
        try
        {
          SettingActivity.this.uservice.update(UtilConstants.CURRENT_USER);
          SettingActivity.this.scaleAlertDialog.dismiss();
          return;
        }
        catch (Exception localException3)
        {
          for (;;)
          {
            localException3.printStackTrace();
          }
        }
      case 2131165461: 
        UtilConstants.CURRENT_SCALE = UtilConstants.BABY_SCALE;
        UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
        try
        {
          SettingActivity.this.uservice.update(UtilConstants.CURRENT_USER);
          SettingActivity.this.scaleAlertDialog.dismiss();
          return;
        }
        catch (Exception localException2)
        {
          for (;;)
          {
            localException2.printStackTrace();
          }
        }
      case 2131165462: 
        UtilConstants.CURRENT_SCALE = UtilConstants.KITCHEN_SCALE;
        UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
        try
        {
          SettingActivity.this.uservice.update(UtilConstants.CURRENT_USER);
          SettingActivity.this.scaleAlertDialog.dismiss();
          return;
        }
        catch (Exception localException1)
        {
          for (;;)
          {
            localException1.printStackTrace();
          }
        }
      case 2131165442: 
        if (UtilConstants.su == null) {
          UtilConstants.su = new SharedPreferencesUtil(SettingActivity.this);
        }
        UtilConstants.su.editSharedPreferences("lefuconfig", "lan", Integer.valueOf(2));
        UtilConstants.SELECT_LANGUAGE = 2;
        Locale.setDefault(Locale.TRADITIONAL_CHINESE);
        Configuration localConfiguration2 = SettingActivity.this.getBaseContext().getResources().getConfiguration();
        localConfiguration2.locale = Locale.TRADITIONAL_CHINESE;
        SettingActivity.this.getBaseContext().getResources().updateConfiguration(localConfiguration2, SettingActivity.this.getBaseContext().getResources().getDisplayMetrics());
        SettingActivity.this.lanageAlertDialog.dismiss();
        SettingActivity.this.back2Scale();
        return;
      case 2131165443: 
        if (UtilConstants.su == null) {
          UtilConstants.su = new SharedPreferencesUtil(SettingActivity.this);
        }
        UtilConstants.su.editSharedPreferences("lefuconfig", "lan", Integer.valueOf(1));
        UtilConstants.SELECT_LANGUAGE = 1;
        Locale.setDefault(Locale.ENGLISH);
        Configuration localConfiguration1 = SettingActivity.this.getBaseContext().getResources().getConfiguration();
        localConfiguration1.locale = Locale.ENGLISH;
        SettingActivity.this.getBaseContext().getResources().updateConfiguration(localConfiguration1, SettingActivity.this.getBaseContext().getResources().getDisplayMetrics());
        SettingActivity.this.lanageAlertDialog.dismiss();
        SettingActivity.this.back2Scale();
        return;
      }
      String str1 = SettingActivity.this.getResources().getString(2131361800);
      Intent localIntent = new Intent("android.intent.action.SENDTO", Uri.parse("mailto:info@lefu.cc"));
      localIntent.putExtra("android.intent.extra.SUBJECT", str1);
      localIntent.putExtra("android.intent.extra.TEXT", " ");
      SettingActivity.this.startActivity(Intent.createChooser(localIntent, str1));
    }
  };
  private RelativeLayout info_layout;
  View.OnClickListener itemOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (paramAnonymousView == SettingActivity.this.info_layout)
      {
        Intent localIntent1 = new Intent();
        localIntent1.setFlags(268435456);
        localIntent1.setClass(SettingActivity.this, UserEditActivity.class);
        SettingActivity.this.startActivity(localIntent1);
      }
      do
      {
        return;
        if (paramAnonymousView == SettingActivity.this.scale_layout)
        {
          SettingActivity.this.showScaleDialog();
          return;
        }
        if (paramAnonymousView == SettingActivity.this.langue_layout)
        {
          SettingActivity.this.showLangueDialog();
          return;
        }
        if (paramAnonymousView == SettingActivity.this.save_layout)
        {
          SettingActivity.this.showSaveasDialog();
          return;
        }
        if (paramAnonymousView == SettingActivity.this.about_layout)
        {
          Intent localIntent2 = new Intent();
          localIntent2.setClass(SettingActivity.this, HelpActivity.class);
          localIntent2.putExtra("IsHelp", true);
          localIntent2.addFlags(131072);
          SettingActivity.this.startActivity(localIntent2);
          return;
        }
      } while (paramAnonymousView != SettingActivity.this.linke_contract);
      Uri localUri = Uri.parse("info@lefu.cc");
      String[] arrayOfString = { "1817032971@qq.com" };
      Intent localIntent3 = new Intent("android.intent.action.SENDTO", localUri);
      localIntent3.putExtra("android.intent.extra.CC", arrayOfString);
      localIntent3.putExtra("android.intent.extra.SUBJECT", SettingActivity.this.getResources().getString(2131361800));
      localIntent3.putExtra("android.intent.extra.TEXT", "");
      SettingActivity.this.startActivity(Intent.createChooser(localIntent3, SettingActivity.this.getResources().getString(2131361801)));
    }
  };
  private Button kitchenScale_btn;
  private AlertDialog lanageAlertDialog;
  private RelativeLayout langue_layout;
  private RelativeLayout linke_contract;
  private TextView name_tv;
  private RelativeLayout save_layout;
  private AlertDialog savesAlertDialog;
  private AlertDialog scaleAlertDialog;
  private RelativeLayout scale_layout;
  private Button txt_btn;
  private UserService uservice;
  
  private void back2Scale()
  {
    ExitApplication.getInstance().exit(this);
    startActivity(new Intent(this, LoadingActivity.class));
  }
  
  private void initview()
  {
    this.fileutil = new FileUtils(this);
    this.info_layout = ((RelativeLayout)findViewById(2131165397));
    this.scale_layout = ((RelativeLayout)findViewById(2131165399));
    this.save_layout = ((RelativeLayout)findViewById(2131165401));
    this.about_layout = ((RelativeLayout)findViewById(2131165402));
    this.langue_layout = ((RelativeLayout)findViewById(2131165403));
    this.home_img_btn = ((ImageView)findViewById(2131165405));
    this.linke_contract = ((RelativeLayout)findViewById(2131165404));
    this.langue_layout.setOnClickListener(this.itemOnClickListener);
    this.info_layout.setOnClickListener(this.itemOnClickListener);
    this.scale_layout.setOnClickListener(this.itemOnClickListener);
    this.save_layout.setOnClickListener(this.itemOnClickListener);
    this.about_layout.setOnClickListener(this.itemOnClickListener);
    this.home_img_btn.setOnClickListener(this.imgOnClickListener);
    this.linke_contract.setOnClickListener(this.imgOnClickListener);
    this.head_img = ((ImageView)findViewById(2131165225));
    this.name_tv = ((TextView)findViewById(2131165199));
    if (UtilConstants.CURRENT_USER != null)
    {
      this.name_tv.setText(UtilConstants.CURRENT_USER.getUserName());
      if ((UtilConstants.CURRENT_USER.getPer_photo() != null) && (!"".equals(UtilConstants.CURRENT_USER.getPer_photo())) && (!UtilConstants.CURRENT_USER.getPer_photo().equals("null")))
      {
        Bitmap localBitmap = imageUtil.getBitmapfromPath(UtilConstants.CURRENT_USER.getPer_photo());
        this.head_img.setImageBitmap(localBitmap);
      }
    }
  }
  
  private void showLangueDialog()
  {
    View localView = getLayoutInflater().inflate(2130903074, (ViewGroup)findViewById(2131165441));
    this.chinesetw_btn = ((Button)localView.findViewById(2131165442));
    this.english_btn = ((Button)localView.findViewById(2131165443));
    this.chinesetw_btn.setOnClickListener(this.imgOnClickListener);
    this.english_btn.setOnClickListener(this.imgOnClickListener);
    this.lanageAlertDialog = new AlertDialog.Builder(this).setView(localView).show();
    Window localWindow = this.lanageAlertDialog.getWindow();
    localWindow.setGravity(80);
    localWindow.setWindowAnimations(2131427351);
  }
  
  private void showSaveasDialog()
  {
    View localView = getLayoutInflater().inflate(2130903081, (ViewGroup)findViewById(2131165441));
    this.txt_btn = ((Button)localView.findViewById(2131165457));
    this.close_btn = ((Button)localView.findViewById(2131165458));
    this.txt_btn.setOnClickListener(this.imgOnClickListener);
    this.close_btn.setOnClickListener(this.imgOnClickListener);
    this.savesAlertDialog = new AlertDialog.Builder(this).setView(localView).show();
    Window localWindow = this.savesAlertDialog.getWindow();
    localWindow.setGravity(80);
    localWindow.setWindowAnimations(2131427351);
  }
  
  private void showScaleDialog()
  {
    View localView = getLayoutInflater().inflate(2130903082, (ViewGroup)findViewById(2131165441));
    this.bodyfat_btn = ((Button)localView.findViewById(2131165459));
    this.bathroom_btn = ((Button)localView.findViewById(2131165460));
    this.babyScale_btn = ((Button)localView.findViewById(2131165461));
    this.kitchenScale_btn = ((Button)localView.findViewById(2131165462));
    this.bodyfat_btn.setOnClickListener(this.imgOnClickListener);
    this.bathroom_btn.setOnClickListener(this.imgOnClickListener);
    this.babyScale_btn.setOnClickListener(this.imgOnClickListener);
    this.kitchenScale_btn.setOnClickListener(this.imgOnClickListener);
    this.scaleAlertDialog = new AlertDialog.Builder(this).setView(localView).show();
    Window localWindow = this.scaleAlertDialog.getWindow();
    localWindow.setGravity(80);
    localWindow.setWindowAnimations(2131427351);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.uservice = new UserService(this);
    setContentView(2130903061);
    detailActivty = this;
    initview();
    ExitApplication.getInstance().addActivity(this);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
    {
      back2Scale();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\SettingActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */