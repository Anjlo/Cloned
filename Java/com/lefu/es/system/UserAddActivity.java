package com.lefu.es.system;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.UserService;
import com.lefu.es.util.Image;
import com.lefu.es.util.ImageUtil;
import com.lefu.es.util.SharedPreferencesUtil;
import com.lefu.es.util.Tool;
import com.lefu.es.util.UID;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.wheelview.ScreenInfo;
import com.lefu.es.wheelview.WheelMain;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserAddActivity
  extends Activity
{
  private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
  private static final int PHOTO_REQUEST_CUT = 3;
  private static final int PHOTO_REQUEST_GALLERY = 2;
  private static final int PHOTO_REQUEST_TAKEPHOTO = 1;
  private int[] RadioButtonID = { 2131165463, 2131165464, 2131165465 };
  private long ageClickTime = System.currentTimeMillis();
  private TextView ageET;
  private android.app.AlertDialog alertDialog;
  private RadioButton amateur_btn_checkbox;
  private Button amatuerBtn;
  private LinearLayout birth_layout = null;
  private Bitmap bitmap;
  private int centerIndex;
  private TextView danwei_tv;
  private android.app.AlertDialog dialog;
  SharedPreferences.Editor editor;
  private Button femaleBtn;
  private RadioGroup group;
  private EditText heightET;
  private EditText heightET2;
  private EditText heightkgET;
  private TextView heng_tv;
  private TextView heng_tv2;
  private ImageView ib_upphoto;
  private ImageView imageCancel;
  private ImageView imageSave;
  View.OnClickListener imgOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      default: 
        return;
      case 2131165395: 
        UserAddActivity.this.exit();
        return;
      case 2131165396: 
        UserAddActivity.this.saveUser();
        return;
      case 2131165379: 
        if (System.currentTimeMillis() - UserAddActivity.this.ageClickTime > 1000L) {
          UserAddActivity.this.showDateTimePicker();
        }
        UserAddActivity.this.ageClickTime = System.currentTimeMillis();
        return;
      }
      if (System.currentTimeMillis() - UserAddActivity.this.ageClickTime > 2000L) {
        UserAddActivity.this.showDateTimePicker();
      }
      UserAddActivity.this.ageClickTime = System.currentTimeMillis();
    }
  };
  private boolean isKG = true;
  View.OnClickListener kgOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      default: 
        return;
      case 2131165375: 
        EditText localEditText3 = UserAddActivity.this.heightET;
        InputFilter[] arrayOfInputFilter3 = new InputFilter[1];
        arrayOfInputFilter3[0] = new InputFilter.LengthFilter(3);
        localEditText3.setFilters(arrayOfInputFilter3);
        String str7;
        String str8;
        String str5;
        if (UserAddActivity.this.heightET2.getVisibility() == 0)
        {
          UserAddActivity.this.heightET2.setVisibility(8);
          UserAddActivity.this.heng_tv.setVisibility(8);
          str7 = UserAddActivity.this.heightET.getText().toString();
          if (("".equals(str7.trim())) || ("0.0".equals(str7.trim()))) {
            str7 = "0";
          }
          str8 = UserAddActivity.this.heightET2.getText().toString();
          if ("".equals(str8.trim())) {
            str8 = "0";
          }
          if ((str7.equals(UserAddActivity.this.org_hei1)) && (str8.equals(UserAddActivity.this.org_hei2))) {
            UserAddActivity.this.heightET.setText(UserAddActivity.this.org_hei);
          }
        }
        else if ((UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_LB)) || (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_ST)))
        {
          UserAddActivity.this.target_edittv2.setVisibility(8);
          UserAddActivity.this.heng_tv2.setVisibility(8);
          str5 = UserAddActivity.this.target_edittv.getText().toString();
          if ((str5 == null) || ("".equals(str5.trim()))) {
            str5 = "0";
          }
          if (!"0".equals(str5)) {
            break label473;
          }
        }
        for (String str6 = "0";; str6 = UtilTooth.onePoint(UserAddActivity.this.targetweight))
        {
          UserAddActivity.this.target_edittv.setText(str6);
          UserAddActivity.this.isKG = true;
          UtilConstants.CHOICE_KG = UtilConstants.UNIT_KG;
          UserAddActivity.this.danwei_tv.setText("cm");
          UserAddActivity.this.target_danwei_tv.setText(UtilConstants.UNIT_KG);
          return;
          UserAddActivity.this.org_hei1 = str7;
          UserAddActivity.this.org_hei2 = str8;
          int i = UtilTooth.ft_in2CMArray(new String[] { str7, str8 });
          UserAddActivity.this.heightET.setText(i);
          UserAddActivity.this.org_hei = i;
          break;
          UserAddActivity.this.targetweight = UtilTooth.lbToKg_target(Float.parseFloat(str5));
        }
      case 2131165376: 
        label473:
        EditText localEditText2 = UserAddActivity.this.heightET;
        InputFilter[] arrayOfInputFilter2 = new InputFilter[1];
        arrayOfInputFilter2[0] = new InputFilter.LengthFilter(1);
        localEditText2.setFilters(arrayOfInputFilter2);
        if (UserAddActivity.this.heightET2.getVisibility() == 8)
        {
          UserAddActivity.this.heightET2.setVisibility(0);
          UserAddActivity.this.heng_tv.setVisibility(0);
          if (!"".equals(UserAddActivity.this.heightET.getText().toString()))
          {
            UserAddActivity.this.org_hei = Float.parseFloat(UserAddActivity.this.heightET.getText().toString().trim());
            String[] arrayOfString2 = UtilTooth.cm2FT_INArray(Float.parseFloat(UserAddActivity.this.heightET.getText().toString()));
            if (arrayOfString2 != null)
            {
              UserAddActivity.this.heightET.setText(arrayOfString2[0]);
              UserAddActivity.this.heightET2.setText(arrayOfString2[1]);
            }
          }
        }
        if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_LB))
        {
          UserAddActivity.this.target_edittv2.setVisibility(8);
          UserAddActivity.this.heng_tv2.setVisibility(8);
        }
        while (!UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_KG))
        {
          UserAddActivity.this.isKG = false;
          UtilConstants.CHOICE_KG = UtilConstants.UNIT_ST;
          UserAddActivity.this.danwei_tv.setText("ft:in");
          UserAddActivity.this.target_danwei_tv.setText(UtilConstants.UNIT_LB);
          return;
        }
        UserAddActivity.this.target_edittv2.setVisibility(8);
        UserAddActivity.this.heng_tv2.setVisibility(8);
        String str3 = UserAddActivity.this.target_edittv.getText().toString();
        if ((str3 == null) || ("".equals(str3.trim()))) {
          str3 = "0";
        }
        if ("0".equals(str3)) {}
        for (String str4 = "0";; str4 = UtilTooth.onePoint(UserAddActivity.this.targetweightLB))
        {
          UserAddActivity.this.target_edittv.setText(str4);
          UserAddActivity.this.target_edittv2.setText("0");
          break;
          UserAddActivity.this.targetweightLB = UtilTooth.kgToLB_target(Float.parseFloat(str3));
        }
      }
      EditText localEditText1 = UserAddActivity.this.heightET;
      InputFilter[] arrayOfInputFilter1 = new InputFilter[1];
      arrayOfInputFilter1[0] = new InputFilter.LengthFilter(1);
      localEditText1.setFilters(arrayOfInputFilter1);
      if (UserAddActivity.this.heightET2.getVisibility() == 8)
      {
        UserAddActivity.this.heightET2.setVisibility(0);
        UserAddActivity.this.heng_tv.setVisibility(0);
        if (!"".equals(UserAddActivity.this.heightET.getText().toString()))
        {
          UserAddActivity.this.org_hei = Float.parseFloat(UserAddActivity.this.heightET.getText().toString().trim());
          String[] arrayOfString1 = UtilTooth.cm2FT_INArray(Float.parseFloat(UserAddActivity.this.heightET.getText().toString()));
          if (arrayOfString1 != null)
          {
            UserAddActivity.this.heightET.setText(arrayOfString1[0]);
            UserAddActivity.this.heightET2.setText(arrayOfString1[1]);
          }
        }
      }
      if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_ST))
      {
        UserAddActivity.this.target_edittv2.setVisibility(8);
        UserAddActivity.this.heng_tv2.setVisibility(8);
      }
      while (!UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_KG))
      {
        UserAddActivity.this.isKG = false;
        UtilConstants.CHOICE_KG = UtilConstants.UNIT_LB;
        UserAddActivity.this.danwei_tv.setText("ft:in");
        UserAddActivity.this.target_danwei_tv.setText(UtilConstants.UNIT_LB);
        return;
      }
      UserAddActivity.this.target_edittv2.setVisibility(8);
      UserAddActivity.this.heng_tv2.setVisibility(8);
      String str1 = UserAddActivity.this.target_edittv.getText().toString();
      if ((str1 == null) || ("".equals(str1.trim()))) {
        str1 = "0";
      }
      if ("0".equals(str1)) {}
      for (String str2 = "0";; str2 = UtilTooth.onePoint(UserAddActivity.this.targetweightLB))
      {
        UserAddActivity.this.target_edittv.setText(str2);
        UserAddActivity.this.target_edittv2.setText("0");
        break;
        UserAddActivity.this.targetweightLB = UtilTooth.kgToLB_target(Float.parseFloat(str1));
      }
    }
  };
  private RadioButton kgrb;
  private RadioButton lbrb;
  View.OnClickListener leverOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      case 2131165386: 
      case 2131165388: 
      default: 
        return;
      case 2131165385: 
        UserAddActivity.this.userType = "0";
        UserAddActivity.this.ordinaryBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837632));
        UserAddActivity.this.amatuerBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837634));
        UserAddActivity.this.professBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837636));
        UserAddActivity.this.ordinary_btn_checkbox.setChecked(true);
        UserAddActivity.this.amateur_btn_checkbox.setChecked(false);
        UserAddActivity.this.profess_btn_checkbox.setChecked(false);
        UserAddActivity.this.showAlertDailog(UserAddActivity.this.getResources().getString(2131361795));
        return;
      case 2131165387: 
        UserAddActivity.this.userType = "1";
        UserAddActivity.this.ordinaryBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837631));
        UserAddActivity.this.amatuerBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837635));
        UserAddActivity.this.professBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837636));
        UserAddActivity.this.ordinary_btn_checkbox.setChecked(false);
        UserAddActivity.this.amateur_btn_checkbox.setChecked(true);
        UserAddActivity.this.profess_btn_checkbox.setChecked(false);
        UserAddActivity.this.showAlertDailog(UserAddActivity.this.getResources().getString(2131361794));
        return;
      }
      UserAddActivity.this.userType = "2";
      UserAddActivity.this.ordinaryBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837631));
      UserAddActivity.this.amatuerBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837634));
      UserAddActivity.this.professBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837637));
      UserAddActivity.this.ordinary_btn_checkbox.setChecked(false);
      UserAddActivity.this.amateur_btn_checkbox.setChecked(false);
      UserAddActivity.this.profess_btn_checkbox.setChecked(true);
      UserAddActivity.this.showAlertDailog(UserAddActivity.this.getResources().getString(2131361796));
    }
  };
  private Button maleBtn;
  private int maxGroup = 0;
  private EditText monthET;
  private EditText nameET;
  private Button ordinaryBtn;
  private RadioButton ordinary_btn_checkbox;
  private float org_hei = 0.0F;
  private String org_hei1 = "0";
  private String org_hei2 = "0";
  String path = Environment.getExternalStorageDirectory() + "/brithPhoto/";
  View.OnClickListener photoClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      int i = paramAnonymousView.getId();
      if (i == 2131165225) {
        UserAddActivity.this.showSetPhotoDialog();
      }
      do
      {
        return;
        if (i == 2131165463)
        {
          UserAddActivity.this.alertDialog.dismiss();
          UserAddActivity.this.camera();
          return;
        }
        if (i == 2131165464)
        {
          UserAddActivity.this.alertDialog.dismiss();
          UserAddActivity.this.gallery();
          return;
        }
      } while (i != 2131165465);
      UserAddActivity.this.alertDialog.dismiss();
    }
  };
  String photoImg = "";
  private Button professBtn;
  private RadioButton profess_btn_checkbox;
  private Button[] rb_dialog = new Button[3];
  String sex = "1";
  View.OnClickListener sexOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      case 2131165372: 
      default: 
        return;
      case 2131165371: 
        UserAddActivity.this.sex = "1";
        UserAddActivity.this.maleBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837617));
        UserAddActivity.this.femaleBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837712));
        UserAddActivity.this.sex_male_checkbox.setChecked(true);
        UserAddActivity.this.sex_female_checkbox.setChecked(false);
        return;
      }
      UserAddActivity.this.sex = "0";
      UserAddActivity.this.maleBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837616));
      UserAddActivity.this.femaleBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837713));
      UserAddActivity.this.sex_male_checkbox.setChecked(false);
      UserAddActivity.this.sex_female_checkbox.setChecked(true);
    }
  };
  private RadioButton sex_female_checkbox;
  private RadioButton sex_male_checkbox;
  String str;
  private RadioButton strb;
  private LinearLayout taget_layout = null;
  private TextView target_danwei_tv;
  private EditText target_edittv = null;
  private EditText target_edittv2 = null;
  private float targetweight = 0.0F;
  private float targetweightLB = 0.0F;
  private File tempFile;
  String tmpage;
  int tmpge = 0;
  private RadioGroup unitgroup;
  private String userType = "0";
  private UserService uservice;
  private WheelMain wheelMain;
  
  private void crop(Uri paramUri)
  {
    Intent localIntent = new Intent("com.android.camera.action.CROP");
    localIntent.setDataAndType(paramUri, "image/*");
    localIntent.putExtra("crop", "true");
    localIntent.putExtra("aspectX", 1);
    localIntent.putExtra("aspectY", 1);
    localIntent.putExtra("outputX", 100);
    localIntent.putExtra("outputY", 100);
    localIntent.putExtra("outputFormat", "png");
    localIntent.putExtra("noFaceDetection", true);
    localIntent.putExtra("return-data", true);
    startActivityForResult(localIntent, 3);
  }
  
  private void exit()
  {
    try
    {
      if (this.uservice.getCount().longValue() > 0L)
      {
        startActivity(new Intent(this, UserListActivity.class));
        return;
      }
      ExitApplication.getInstance().exit(this);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  private String getPhotoFileName()
  {
    Date localDate = new Date(System.currentTimeMillis());
    return new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss").format(localDate) + ".jpg";
  }
  
  private boolean hasSdcard()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }
  
  private void initView()
  {
    this.uservice = new UserService(this);
    this.nameET = ((EditText)findViewById(2131165369));
    this.heightET = ((EditText)findViewById(2131165380));
    this.heightET2 = ((EditText)findViewById(2131165382));
    this.ageET = ((TextView)findViewById(2131165379));
    this.birth_layout = ((LinearLayout)findViewById(2131165378));
    this.sex_male_checkbox = ((RadioButton)findViewById(2131165370));
    this.sex_female_checkbox = ((RadioButton)findViewById(2131165372));
    this.ordinary_btn_checkbox = ((RadioButton)findViewById(2131165384));
    this.amateur_btn_checkbox = ((RadioButton)findViewById(2131165386));
    this.profess_btn_checkbox = ((RadioButton)findViewById(2131165388));
    this.taget_layout = ((LinearLayout)findViewById(2131165390));
    this.target_edittv = ((EditText)findViewById(2131165391));
    this.target_edittv2 = ((EditText)findViewById(2131165393));
    this.heng_tv = ((TextView)findViewById(2131165381));
    this.heng_tv2 = ((TextView)findViewById(2131165392));
    this.danwei_tv = ((TextView)findViewById(2131165383));
    this.target_danwei_tv = ((TextView)findViewById(2131165394));
    this.unitgroup = ((RadioGroup)findViewById(2131165374));
    this.kgrb = ((RadioButton)findViewById(2131165375));
    this.strb = ((RadioButton)findViewById(2131165376));
    this.lbrb = ((RadioButton)findViewById(2131165377));
    this.kgrb.setOnClickListener(this.kgOnClickListener);
    this.strb.setOnClickListener(this.kgOnClickListener);
    this.lbrb.setOnClickListener(this.kgOnClickListener);
    this.kgrb.setChecked(true);
    this.maleBtn = ((Button)findViewById(2131165371));
    this.femaleBtn = ((Button)findViewById(2131165373));
    this.ordinaryBtn = ((Button)findViewById(2131165385));
    this.amatuerBtn = ((Button)findViewById(2131165387));
    this.professBtn = ((Button)findViewById(2131165389));
    this.maleBtn.setOnClickListener(this.sexOnClickListener);
    this.femaleBtn.setOnClickListener(this.sexOnClickListener);
    this.ordinaryBtn.setOnClickListener(this.leverOnClickListener);
    this.amatuerBtn.setOnClickListener(this.leverOnClickListener);
    this.professBtn.setOnClickListener(this.leverOnClickListener);
    this.imageCancel = ((ImageView)findViewById(2131165395));
    this.imageSave = ((ImageView)findViewById(2131165396));
    this.imageCancel.setOnClickListener(this.imgOnClickListener);
    this.imageSave.setOnClickListener(this.imgOnClickListener);
    this.birth_layout.setOnClickListener(this.imgOnClickListener);
    this.ib_upphoto = ((ImageView)findViewById(2131165225));
    this.ib_upphoto.setOnClickListener(this.photoClickListener);
    this.ageET.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        if (System.currentTimeMillis() - UserAddActivity.this.ageClickTime > 1000L) {
          UserAddActivity.this.showDateTimePicker();
        }
        UserAddActivity.this.ageClickTime = System.currentTimeMillis();
        return false;
      }
    });
    this.sex_male_checkbox.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UserAddActivity.this.sex = "1";
        UserAddActivity.this.maleBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837617));
        UserAddActivity.this.femaleBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837712));
        UserAddActivity.this.sex_male_checkbox.setChecked(true);
        UserAddActivity.this.sex_female_checkbox.setChecked(false);
      }
    });
    this.sex_female_checkbox.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UserAddActivity.this.sex = "0";
        UserAddActivity.this.maleBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837616));
        UserAddActivity.this.femaleBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837713));
        UserAddActivity.this.sex_male_checkbox.setChecked(false);
        UserAddActivity.this.sex_female_checkbox.setChecked(true);
      }
    });
    this.ordinary_btn_checkbox.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UserAddActivity.this.userType = "0";
        UserAddActivity.this.ordinaryBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837632));
        UserAddActivity.this.amatuerBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837634));
        UserAddActivity.this.professBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837636));
        UserAddActivity.this.ordinary_btn_checkbox.setChecked(true);
        UserAddActivity.this.amateur_btn_checkbox.setChecked(false);
        UserAddActivity.this.profess_btn_checkbox.setChecked(false);
        UserAddActivity.this.showAlertDailog(UserAddActivity.this.getResources().getString(2131361795));
      }
    });
    this.amateur_btn_checkbox.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UserAddActivity.this.userType = "1";
        UserAddActivity.this.ordinaryBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837631));
        UserAddActivity.this.amatuerBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837635));
        UserAddActivity.this.professBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837636));
        UserAddActivity.this.ordinary_btn_checkbox.setChecked(false);
        UserAddActivity.this.amateur_btn_checkbox.setChecked(true);
        UserAddActivity.this.profess_btn_checkbox.setChecked(false);
        UserAddActivity.this.showAlertDailog(UserAddActivity.this.getResources().getString(2131361794));
      }
    });
    this.profess_btn_checkbox.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UserAddActivity.this.userType = "2";
        UserAddActivity.this.ordinaryBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837631));
        UserAddActivity.this.amatuerBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837634));
        UserAddActivity.this.professBtn.setBackgroundDrawable(UserAddActivity.this.getResources().getDrawable(2130837637));
        UserAddActivity.this.ordinary_btn_checkbox.setChecked(false);
        UserAddActivity.this.amateur_btn_checkbox.setChecked(false);
        UserAddActivity.this.profess_btn_checkbox.setChecked(true);
        UserAddActivity.this.showAlertDailog(UserAddActivity.this.getResources().getString(2131361796));
      }
    });
  }
  
  private void saveUser()
  {
    String str1 = this.nameET.getText().toString();
    if ((str1 == null) || ("".equals(str1.trim())))
    {
      Toast.makeText(this, getString(2131361868), 0).show();
      return;
    }
    String str2 = this.ageET.getText().toString();
    if ((str2 == null) || ("".equals(str2.trim())))
    {
      Toast.makeText(this, getString(2131361870), 0).show();
      return;
    }
    String str3 = str2.substring(1 + str2.lastIndexOf("-"));
    String str4 = str2.substring(0, str2.lastIndexOf("-"));
    String str5 = str3 + "-" + str4;
    int i = Tool.getAgeByBirthday(Tool.StringToDate(str5, "yyyy-MM-dd"));
    if ((str5.lastIndexOf("-") > 0) && ((i < 1) || (i > 120)))
    {
      Toast.makeText(this, getString(2131361871), 0).show();
      return;
    }
    if (this.isKG)
    {
      String str8 = this.heightET.getText().toString();
      if ((str8 == null) || ("".equals(str8.trim())))
      {
        Toast.makeText(this, getString(2131361869), 3000).show();
        return;
      }
      if (i >= 10)
      {
        if ((Float.parseFloat(str8) < 100.0F) || (Float.parseFloat(str8) > 255.0F)) {
          Toast.makeText(this, getString(2131361873), 3000).show();
        }
      }
      else if ((Float.parseFloat(str8) < 30.0F) || (Float.parseFloat(str8) > 255.0F)) {
        Toast.makeText(this, getString(2131361874), 3000).show();
      }
    }
    else
    {
      String str6 = this.heightET.getText().toString();
      if ("".equals(str6.trim())) {
        str6 = "0";
      }
      String str7 = this.heightET2.getText().toString();
      if ("".equals(str7.trim())) {
        str7 = "0";
      }
      int j = (int)Float.parseFloat(str6);
      int k = (int)Float.parseFloat(str7);
      if (i >= 10)
      {
        if ((j < 3) || ((j == 3) && (k < 3)) || (j > 8) || ((j == 8) && (k > 4))) {
          Toast.makeText(this, getString(2131361875), 3000).show();
        }
      }
      else if ((j < 1) || (j > 8) || ((j == 8) && (k > 4)))
      {
        Toast.makeText(this, getString(2131361876), 3000).show();
        return;
      }
    }
    if ((this.userType == null) || ("".equals(this.userType.trim())))
    {
      Toast.makeText(this, getString(2131361936), 0).show();
      return;
    }
    UserModel localUserModel = creatUserModel();
    if (localUserModel != null)
    {
      UtilConstants.CURRENT_USER = localUserModel;
      UtilConstants.su.editSharedPreferences("lefuconfig", "addUser", JSONObject.toJSONString(UtilConstants.CURRENT_USER));
      int m = Build.VERSION.SDK_INT;
      Intent localIntent = new Intent();
      localIntent.setFlags(268435456);
      if (m < 18) {
        localIntent.setClass(this, AutoBTActivity.class);
      }
      for (;;)
      {
        startActivity(localIntent);
        return;
        localIntent.setClass(this, AutoBLEActivity.class);
      }
    }
    Toast.makeText(this, getString(2131361948), 0).show();
  }
  
  private void showAlertDailog(String paramString)
  {
    new com.lefu.es.view.AlertDialog(this).builder().setTitle(getResources().getString(2131361793)).setMsg(paramString).setPositiveButton(getResources().getString(2131361852), new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView) {}
    }).show();
  }
  
  private void showSetPhotoDialog()
  {
    View localView = getLayoutInflater().inflate(2130903083, (ViewGroup)findViewById(2131165441));
    for (int i = 0;; i++)
    {
      if (i >= this.rb_dialog.length)
      {
        this.alertDialog = new AlertDialog.Builder(this).setView(localView).show();
        Window localWindow = this.alertDialog.getWindow();
        localWindow.setGravity(80);
        localWindow.setWindowAnimations(2131427351);
        return;
      }
      this.rb_dialog[i] = ((Button)localView.findViewById(this.RadioButtonID[i]));
      this.rb_dialog[i].setOnClickListener(this.photoClickListener);
    }
  }
  
  public void camera()
  {
    Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
    if (hasSdcard()) {
      localIntent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp_photo.jpg")));
    }
    startActivityForResult(localIntent, 1);
  }
  
  public UserModel creatUserModel()
  {
    UserModel localUserModel = new UserModel();
    try
    {
      this.maxGroup = this.uservice.getMaxGroup();
      if (this.maxGroup >= 9) {}
      for (this.maxGroup = 0;; this.maxGroup = (1 + this.maxGroup))
      {
        localUserModel.setNumber(this.maxGroup);
        localUserModel.setGroup("P" + this.maxGroup);
        localUserModel.setUserName(this.nameET.getText().toString());
        localUserModel.setSex(this.sex);
        localUserModel.setLevel(this.userType);
        localUserModel.setScaleType(UtilConstants.SELECT_SCALE);
        localUserModel.setPer_photo(this.photoImg);
        localUserModel.setDanwei(UtilConstants.CHOICE_KG);
        str1 = this.heightET.getText().toString().trim();
        str2 = this.heightET2.getText().toString().trim();
        if ((str1 == null) || ("".equals(str1))) {
          str1 = "0";
        }
        if ((str2 == null) || ("".equals(str2))) {
          str2 = "0";
        }
        String str3 = this.target_edittv.getText().toString().trim();
        String str4 = this.target_edittv2.getText().toString().trim();
        if (((str3 == null) || (!"".equals(str3))) || ((str4 != null) && ("".equals(str4)))) {}
        String str5 = this.ageET.getText().toString().trim();
        if ((str5 == null) || ("".equals(str5))) {
          str5 = "0";
        }
        if (this.monthET != null)
        {
          str9 = this.monthET.getText().toString();
          if (!"".equals(str9.trim())) {
            break;
          }
          localUserModel.setAgeMonth(0);
        }
        if (this.target_edittv != null)
        {
          String str8 = this.target_edittv.getText().toString().trim();
          if ((str8 == null) || ("".equals(str8))) {
            str8 = "0";
          }
          f = Float.parseFloat(str8);
          if (f < 1.0F) {
            f = 0.0F;
          }
          if (!UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_KG)) {
            break label591;
          }
          localUserModel.setTargweight(f);
        }
        if (!this.isKG) {
          break label603;
        }
        localUserModel.setBheigth(Float.parseFloat(str1));
        if (UtilConstants.su == null) {
          UtilConstants.su = new SharedPreferencesUtil(this);
        }
        UtilConstants.su.editSharedPreferences("lefuconfig", "unit", UtilConstants.CHOICE_KG);
        localUserModel.setBirth(str5);
        if ((str5 != null) && (!"".equals(str5)) && (str5.lastIndexOf("-") > 0))
        {
          String str6 = str5.substring(1 + str5.lastIndexOf("-"));
          String str7 = str5.substring(0, str5.lastIndexOf("-"));
          str5 = str6 + "-" + str7;
        }
        localUserModel.setAgeYear(Tool.getAgeByBirthday(Tool.StringToDate(str5, "yyyy-MM-dd")));
        return localUserModel;
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        String str1;
        String str2;
        String str9;
        float f;
        continue;
        localUserModel.setAgeMonth(Integer.parseInt(str9));
        continue;
        label591:
        localUserModel.setTargweight(UtilTooth.lbToKg_target(f));
        continue;
        label603:
        localUserModel.setBheigth(UtilTooth.ft_in2CMArray(new String[] { str1, str2 }));
      }
    }
  }
  
  public void gallery()
  {
    Intent localIntent = new Intent("android.intent.action.PICK");
    localIntent.setType("image/*");
    startActivityForResult(localIntent, 2);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    }
    for (;;)
    {
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
      if (hasSdcard())
      {
        this.tempFile = new File(Environment.getExternalStorageDirectory(), "temp_photo.jpg");
        crop(Uri.fromFile(this.tempFile));
      }
      else
      {
        Toast.makeText(this, getString(2131361928), 0).show();
        continue;
        if (paramIntent != null)
        {
          crop(paramIntent.getData());
          continue;
          try
          {
            this.bitmap = ((Bitmap)paramIntent.getParcelableExtra("data"));
            this.bitmap = Image.toRoundCorner(this.bitmap, 8);
            this.ib_upphoto.setImageBitmap(this.bitmap);
            this.photoImg = ImageUtil.saveBitmap("/sdcard/healthscale/userheader/", UID.getImage(), this.bitmap);
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
      }
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903059);
    this.editor = getSharedPreferences("rcp_shape", 0).edit();
    this.centerIndex = getIntent().getIntExtra("center", -100);
    initView();
    ExitApplication.getInstance().addActivity(this);
    UtilConstants.su = new SharedPreferencesUtil(this);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      exit();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public void showDateTimePicker()
  {
    View localView = LayoutInflater.from(this).inflate(2130903066, null);
    localView.setMinimumWidth(getWindowManager().getDefaultDisplay().getWidth());
    ScreenInfo localScreenInfo = new ScreenInfo(this);
    this.wheelMain = new WheelMain(localView);
    this.wheelMain.screenheight = localScreenInfo.getHeight();
    Calendar localCalendar = Calendar.getInstance();
    int i = localCalendar.get(1);
    int j = localCalendar.get(2);
    int k = localCalendar.get(5);
    this.wheelMain.setTime(j, k, i);
    this.dialog = new AlertDialog.Builder(this).setView(localView).show();
    Window localWindow = this.dialog.getWindow();
    localWindow.setGravity(80);
    localWindow.setWindowAnimations(2131427351);
    ((Button)localView.findViewById(2131165418)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UserAddActivity.this.ageET.setText(UserAddActivity.this.wheelMain.getTime());
        UserAddActivity.this.dialog.dismiss();
      }
    });
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\UserAddActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */