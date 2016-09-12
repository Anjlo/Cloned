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
import android.os.Handler;
import android.os.Message;
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
import com.lefu.es.constant.AppData;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.constant.imageUtil;
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
import java.util.Calendar;

public class UserEditActivity
  extends Activity
{
  private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
  private static final int PHOTO_REQUEST_CUT = 3;
  private static final int PHOTO_REQUEST_GALLERY = 2;
  private static final int PHOTO_REQUEST_TAKEPHOTO = 1;
  public static final int SAVE_ATION = 1;
  private int[] RadioButtonID = { 2131165463, 2131165464, 2131165465 };
  private long ageClickTime = System.currentTimeMillis();
  private TextView ageET;
  private android.app.AlertDialog alertDialog;
  private RadioButton amateur_btn_checkbox;
  private Button amatuerBtn;
  private Bitmap bitmap;
  private int centerIndex;
  private TextView danwei_tv;
  private android.app.AlertDialog dialog;
  SharedPreferences.Editor editor;
  private Button femaleBtn;
  private RadioGroup group;
  private EditText heightET;
  private EditText heightET2;
  private TextView heng_tv;
  private TextView heng_tv2;
  private ImageView ib_upphoto;
  private EditText idET;
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
        UserEditActivity.this.exit();
        return;
      case 2131165396: 
        UserEditActivity.this.saveUser();
        return;
      }
      if (System.currentTimeMillis() - UserEditActivity.this.ageClickTime > 1000L) {
        UserEditActivity.this.showDateTimePicker();
      }
      UserEditActivity.this.ageClickTime = System.currentTimeMillis();
    }
  };
  private boolean isKG = true;
  private boolean isOK;
  View.OnClickListener kgOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      default: 
        return;
      case 2131165375: 
        EditText localEditText3 = UserEditActivity.this.heightET;
        InputFilter[] arrayOfInputFilter3 = new InputFilter[1];
        arrayOfInputFilter3[0] = new InputFilter.LengthFilter(3);
        localEditText3.setFilters(arrayOfInputFilter3);
        String str7;
        String str8;
        String str5;
        if (UserEditActivity.this.heightET2.getVisibility() == 0)
        {
          UserEditActivity.this.heightET2.setVisibility(8);
          UserEditActivity.this.heng_tv.setVisibility(8);
          str7 = UserEditActivity.this.heightET.getText().toString();
          if (("".equals(str7.trim())) || ("0.0".equals(str7.trim()))) {
            str7 = "0";
          }
          str8 = UserEditActivity.this.heightET2.getText().toString();
          if ("".equals(str8.trim())) {
            str8 = "0";
          }
          if ((str7.equals(UserEditActivity.this.org_hei1)) && (str8.equals(UserEditActivity.this.org_hei2))) {
            UserEditActivity.this.heightET.setText(UserEditActivity.this.org_hei);
          }
        }
        else if ((UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_LB)) || (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_ST)))
        {
          UserEditActivity.this.target_edittv2.setVisibility(8);
          UserEditActivity.this.heng_tv2.setVisibility(8);
          str5 = UserEditActivity.this.target_edittv.getText().toString();
          if ((str5 == null) || ("".equals(str5.trim()))) {
            str5 = "0";
          }
          if (!"0".equals(str5)) {
            break label473;
          }
        }
        for (String str6 = "0";; str6 = UtilTooth.onePoint(UserEditActivity.this.targetweight))
        {
          UserEditActivity.this.target_edittv.setText(str6);
          UserEditActivity.this.isKG = true;
          UtilConstants.CHOICE_KG = UtilConstants.UNIT_KG;
          UserEditActivity.this.danwei_tv.setText("cm");
          UserEditActivity.this.target_danwei_tv.setText(UtilConstants.UNIT_KG);
          return;
          UserEditActivity.this.org_hei1 = str7;
          UserEditActivity.this.org_hei2 = str8;
          int i = UtilTooth.ft_in2CMArray(new String[] { str7, str8 });
          UserEditActivity.this.heightET.setText(i);
          UserEditActivity.this.org_hei = i;
          break;
          UserEditActivity.this.targetweight = UtilTooth.lbToKg_target(Float.parseFloat(str5));
        }
      case 2131165376: 
        label473:
        EditText localEditText2 = UserEditActivity.this.heightET;
        InputFilter[] arrayOfInputFilter2 = new InputFilter[1];
        arrayOfInputFilter2[0] = new InputFilter.LengthFilter(1);
        localEditText2.setFilters(arrayOfInputFilter2);
        if (UserEditActivity.this.heightET2.getVisibility() == 8)
        {
          UserEditActivity.this.heightET2.setVisibility(0);
          UserEditActivity.this.heng_tv.setVisibility(0);
          if (!"".equals(UserEditActivity.this.heightET.getText().toString()))
          {
            UserEditActivity.this.org_hei = Float.parseFloat(UserEditActivity.this.heightET.getText().toString().trim());
            String[] arrayOfString2 = UtilTooth.cm2FT_INArray(Float.parseFloat(UserEditActivity.this.heightET.getText().toString()));
            if (arrayOfString2 != null)
            {
              UserEditActivity.this.heightET.setText(arrayOfString2[0]);
              UserEditActivity.this.heightET2.setText(arrayOfString2[1]);
            }
          }
        }
        if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_LB))
        {
          UserEditActivity.this.target_edittv2.setVisibility(8);
          UserEditActivity.this.heng_tv2.setVisibility(8);
        }
        while (!UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_KG))
        {
          UserEditActivity.this.isKG = false;
          UtilConstants.CHOICE_KG = UtilConstants.UNIT_ST;
          UserEditActivity.this.danwei_tv.setText("ft:in");
          UserEditActivity.this.target_danwei_tv.setText(UtilConstants.UNIT_LB);
          return;
        }
        UserEditActivity.this.target_edittv2.setVisibility(8);
        UserEditActivity.this.heng_tv2.setVisibility(8);
        String str3 = UserEditActivity.this.target_edittv.getText().toString();
        if ((str3 == null) || ("".equals(str3.trim()))) {
          str3 = "0";
        }
        if ("0".equals(str3)) {}
        for (String str4 = "0";; str4 = UtilTooth.onePoint(UserEditActivity.this.targetweightLB))
        {
          UserEditActivity.this.target_edittv.setText(str4);
          UserEditActivity.this.target_edittv2.setText("0");
          break;
          UserEditActivity.this.targetweightLB = UtilTooth.kgToLB_target(Float.parseFloat(str3));
        }
      }
      EditText localEditText1 = UserEditActivity.this.heightET;
      InputFilter[] arrayOfInputFilter1 = new InputFilter[1];
      arrayOfInputFilter1[0] = new InputFilter.LengthFilter(1);
      localEditText1.setFilters(arrayOfInputFilter1);
      if (UserEditActivity.this.heightET2.getVisibility() == 8)
      {
        UserEditActivity.this.heightET2.setVisibility(0);
        UserEditActivity.this.heng_tv.setVisibility(0);
        if (!"".equals(UserEditActivity.this.heightET.getText().toString()))
        {
          UserEditActivity.this.org_hei = Float.parseFloat(UserEditActivity.this.heightET.getText().toString().trim());
          String[] arrayOfString1 = UtilTooth.cm2FT_INArray(Float.parseFloat(UserEditActivity.this.heightET.getText().toString()));
          if (arrayOfString1 != null)
          {
            UserEditActivity.this.heightET.setText(arrayOfString1[0]);
            UserEditActivity.this.heightET2.setText(arrayOfString1[1]);
          }
        }
      }
      if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_ST))
      {
        UserEditActivity.this.target_edittv2.setVisibility(8);
        UserEditActivity.this.heng_tv2.setVisibility(8);
      }
      while (!UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_KG))
      {
        UserEditActivity.this.isKG = false;
        UtilConstants.CHOICE_KG = UtilConstants.UNIT_LB;
        UserEditActivity.this.danwei_tv.setText("ft:in");
        UserEditActivity.this.target_danwei_tv.setText(UtilConstants.UNIT_LB);
        return;
      }
      UserEditActivity.this.target_edittv2.setVisibility(8);
      UserEditActivity.this.heng_tv2.setVisibility(8);
      String str1 = UserEditActivity.this.target_edittv.getText().toString();
      if ((str1 == null) || ("".equals(str1.trim()))) {
        str1 = "0";
      }
      if ("0".equals(str1)) {}
      for (String str2 = "0";; str2 = UtilTooth.onePoint(UserEditActivity.this.targetweightLB))
      {
        UserEditActivity.this.target_edittv.setText(str2);
        UserEditActivity.this.target_edittv2.setText("0");
        break;
        UserEditActivity.this.targetweightLB = UtilTooth.kgToLB_target(Float.parseFloat(str1));
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
        UserEditActivity.this.userType = "0";
        UserEditActivity.this.ordinaryBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837632));
        UserEditActivity.this.amatuerBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837634));
        UserEditActivity.this.professBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837636));
        UserEditActivity.this.ordinary_btn_checkbox.setChecked(true);
        UserEditActivity.this.amateur_btn_checkbox.setChecked(false);
        UserEditActivity.this.profess_btn_checkbox.setChecked(false);
        UserEditActivity.this.showAlertDailog(UserEditActivity.this.getResources().getString(2131361795));
        return;
      case 2131165387: 
        UserEditActivity.this.userType = "1";
        UserEditActivity.this.ordinaryBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837631));
        UserEditActivity.this.amatuerBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837635));
        UserEditActivity.this.professBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837636));
        UserEditActivity.this.ordinary_btn_checkbox.setChecked(false);
        UserEditActivity.this.amateur_btn_checkbox.setChecked(true);
        UserEditActivity.this.profess_btn_checkbox.setChecked(false);
        UserEditActivity.this.showAlertDailog(UserEditActivity.this.getResources().getString(2131361794));
        return;
      }
      UserEditActivity.this.userType = "2";
      UserEditActivity.this.ordinaryBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837631));
      UserEditActivity.this.amatuerBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837634));
      UserEditActivity.this.professBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837637));
      UserEditActivity.this.ordinary_btn_checkbox.setChecked(false);
      UserEditActivity.this.amateur_btn_checkbox.setChecked(false);
      UserEditActivity.this.profess_btn_checkbox.setChecked(true);
      UserEditActivity.this.showAlertDailog(UserEditActivity.this.getResources().getString(2131361796));
    }
  };
  private Button maleBtn;
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
        UserEditActivity.this.showSetPhotoDialog();
      }
      do
      {
        return;
        if (i == 2131165463)
        {
          UserEditActivity.this.alertDialog.dismiss();
          UserEditActivity.this.camera();
          return;
        }
        if (i == 2131165464)
        {
          UserEditActivity.this.alertDialog.dismiss();
          UserEditActivity.this.gallery();
          return;
        }
      } while (i != 2131165465);
      UserEditActivity.this.alertDialog.dismiss();
    }
  };
  String photoImg = "";
  private Button professBtn;
  private RadioButton profess_btn_checkbox;
  private Button[] rb_dialog = new Button[3];
  private final Handler saveHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      do
      {
        do
        {
          return;
        } while (UtilConstants.CURRENT_USER == null);
        UserModel localUserModel = UserEditActivity.this.creatUserModel();
        if (AppData.isCheckScale) {
          break label270;
        }
        try
        {
          if (UtilConstants.CURRENT_SCALE.equals(UtilConstants.KITCHEN_SCALE)) {
            localUserModel.setDanwei(UtilConstants.UNIT_KG);
          }
          UserEditActivity.this.uservice.update(localUserModel);
          UtilConstants.CURRENT_USER = UserEditActivity.this.uservice.find(UtilConstants.CURRENT_USER.getId());
          UtilConstants.SELECT_USER = UtilConstants.CURRENT_USER.getId();
        }
        catch (Exception localException)
        {
          for (;;)
          {
            Intent localIntent2;
            localException.printStackTrace();
            continue;
            if (UtilConstants.BABY_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
              localIntent2.setClass(UserEditActivity.this, BabyScaleActivity.class);
            } else if (UtilConstants.BODY_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
              localIntent2.setClass(UserEditActivity.this, BodyFatScaleActivity.class);
            } else if (UtilConstants.KITCHEN_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
              localIntent2.setClass(UserEditActivity.this, KitchenScaleActivity.class);
            }
          }
        }
        localIntent2 = new Intent();
        if (!UtilConstants.BATHROOM_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
          break;
        }
        localIntent2.setClass(UserEditActivity.this, BathScaleActivity.class);
        localIntent2.putExtra("ItemID", UtilConstants.SELECT_USER);
        localIntent2.addFlags(131072);
        UserEditActivity.this.startActivityForResult(localIntent2, 99);
      } while (SettingActivity.detailActivty == null);
      SettingActivity.detailActivty.finish();
      return;
      label270:
      int i = Build.VERSION.SDK_INT;
      Intent localIntent1 = new Intent();
      localIntent1.setFlags(268435456);
      if (i < 18) {
        localIntent1.setClass(UserEditActivity.this, AutoBTActivity.class);
      }
      for (;;)
      {
        UserEditActivity.this.startActivity(localIntent1);
        UserEditActivity.this.finish();
        return;
        localIntent1.setClass(UserEditActivity.this, AutoBLEActivity.class);
      }
    }
  };
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
        UserEditActivity.this.sex = "1";
        UserEditActivity.this.maleBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837617));
        UserEditActivity.this.femaleBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837712));
        UserEditActivity.this.sex_male_checkbox.setChecked(true);
        UserEditActivity.this.sex_female_checkbox.setChecked(false);
        return;
      }
      UserEditActivity.this.sex = "0";
      UserEditActivity.this.maleBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837616));
      UserEditActivity.this.femaleBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837713));
      UserEditActivity.this.sex_male_checkbox.setChecked(false);
      UserEditActivity.this.sex_female_checkbox.setChecked(true);
    }
  };
  private RadioButton sex_female_checkbox;
  private RadioButton sex_male_checkbox;
  private RadioButton strb;
  private SharedPreferencesUtil su = new SharedPreferencesUtil(this);
  private LinearLayout taget_layout = null;
  private float targ_new = 0.0F;
  private float targ_old = 0.0F;
  private TextView target_danwei_tv;
  private EditText target_edittv = null;
  private EditText target_edittv2 = null;
  private float targetweight = 0.0F;
  private float targetweightLB = 0.0F;
  private File tempFile;
  private RadioGroup unitgroup;
  private String userType;
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
    finish();
    if (AppData.isCheckScale) {
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
    this.taget_layout = ((LinearLayout)findViewById(2131165390));
    this.target_edittv = ((EditText)findViewById(2131165391));
    this.target_edittv2 = ((EditText)findViewById(2131165393));
    this.sex_male_checkbox = ((RadioButton)findViewById(2131165370));
    this.sex_female_checkbox = ((RadioButton)findViewById(2131165372));
    this.ordinary_btn_checkbox = ((RadioButton)findViewById(2131165384));
    this.amateur_btn_checkbox = ((RadioButton)findViewById(2131165386));
    this.profess_btn_checkbox = ((RadioButton)findViewById(2131165388));
    if (UtilConstants.BATHROOM_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
      this.taget_layout.setVisibility(0);
    }
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
    this.ib_upphoto = ((ImageView)findViewById(2131165225));
    this.ib_upphoto.setOnClickListener(this.photoClickListener);
    UtilConstants.CHOICE_KG = UtilConstants.UNIT_KG;
    if (UtilConstants.CURRENT_USER != null)
    {
      UtilConstants.CHOICE_KG = UtilConstants.CURRENT_USER.getDanwei();
      if ((!UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_LB)) && (!UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_ST))) {
        UtilConstants.CHOICE_KG = UtilConstants.UNIT_KG;
      }
      this.nameET.setText(UtilConstants.CURRENT_USER.getUserName());
      if ((UtilConstants.CURRENT_USER.getSex() == null) || (!UtilConstants.CURRENT_USER.getSex().equals("0"))) {
        break label1162;
      }
      this.femaleBtn.setBackgroundDrawable(getResources().getDrawable(2130837713));
      if ((UtilConstants.CURRENT_USER.getPer_photo() != null) && (!"".equals(UtilConstants.CURRENT_USER.getPer_photo())) && (!UtilConstants.CURRENT_USER.getPer_photo().equals("null")))
      {
        this.photoImg = UtilConstants.CURRENT_USER.getPer_photo();
        Bitmap localBitmap = imageUtil.getBitmapfromPath(UtilConstants.CURRENT_USER.getPer_photo());
        this.ib_upphoto.setImageBitmap(localBitmap);
      }
      if (!UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_ST)) {
        break label1182;
      }
      this.isKG = false;
      this.strb.setChecked(true);
      this.heightET2.setVisibility(0);
      this.heng_tv.setVisibility(0);
      this.danwei_tv.setText(getText(2131361895));
      this.target_edittv2.setVisibility(0);
      this.heng_tv2.setVisibility(0);
      this.target_danwei_tv.setText(getText(2131361890));
      label800:
      if (UtilConstants.CURRENT_USER != null)
      {
        this.org_hei = UtilConstants.CURRENT_USER.getBheigth();
        this.org_hei1 = UtilTooth.cm2FT_INArray(UtilConstants.CURRENT_USER.getBheigth())[0];
        this.org_hei2 = UtilTooth.cm2FT_INArray(UtilConstants.CURRENT_USER.getBheigth())[0];
      }
      this.targ_old = UtilConstants.CURRENT_USER.getTargweight();
      this.targ_new = this.targ_old;
      if (!this.isKG) {
        break label1345;
      }
      this.heightET.setText((int)UtilConstants.CURRENT_USER.getBheigth());
      this.target_edittv.setText(UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight()));
      label927:
      this.ageET.setText(UtilConstants.CURRENT_USER.getBirth());
      if (!UtilConstants.CURRENT_USER.getSex().equals("1")) {
        break label1465;
      }
      this.maleBtn.setBackgroundDrawable(getResources().getDrawable(2130837617));
      this.femaleBtn.setBackgroundDrawable(getResources().getDrawable(2130837712));
      this.sex = "1";
      this.sex_male_checkbox.setChecked(true);
      this.sex_female_checkbox.setChecked(false);
      label1010:
      if (!"1".equals(UtilConstants.CURRENT_USER.getLevel())) {
        break label1524;
      }
      this.userType = "1";
      this.amatuerBtn.setBackgroundDrawable(getResources().getDrawable(2130837635));
      this.ordinary_btn_checkbox.setChecked(false);
      this.amateur_btn_checkbox.setChecked(true);
      this.profess_btn_checkbox.setChecked(false);
    }
    for (;;)
    {
      this.ageET.setOnTouchListener(new View.OnTouchListener()
      {
        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
          if (System.currentTimeMillis() - UserEditActivity.this.ageClickTime > 1000L) {
            UserEditActivity.this.showDateTimePicker();
          }
          UserEditActivity.this.ageClickTime = System.currentTimeMillis();
          return false;
        }
      });
      this.sex_male_checkbox.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          UserEditActivity.this.sex = "1";
          UserEditActivity.this.maleBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837617));
          UserEditActivity.this.femaleBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837712));
          UserEditActivity.this.sex_male_checkbox.setChecked(true);
          UserEditActivity.this.sex_female_checkbox.setChecked(false);
        }
      });
      this.sex_female_checkbox.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          UserEditActivity.this.sex = "0";
          UserEditActivity.this.maleBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837616));
          UserEditActivity.this.femaleBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837713));
          UserEditActivity.this.sex_male_checkbox.setChecked(false);
          UserEditActivity.this.sex_female_checkbox.setChecked(true);
        }
      });
      this.ordinary_btn_checkbox.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          UserEditActivity.this.userType = "0";
          UserEditActivity.this.ordinaryBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837632));
          UserEditActivity.this.amatuerBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837634));
          UserEditActivity.this.professBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837636));
          UserEditActivity.this.ordinary_btn_checkbox.setChecked(true);
          UserEditActivity.this.amateur_btn_checkbox.setChecked(false);
          UserEditActivity.this.profess_btn_checkbox.setChecked(false);
          UserEditActivity.this.showAlertDailog(UserEditActivity.this.getResources().getString(2131361795));
        }
      });
      this.amateur_btn_checkbox.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          UserEditActivity.this.userType = "1";
          UserEditActivity.this.ordinaryBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837631));
          UserEditActivity.this.amatuerBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837635));
          UserEditActivity.this.professBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837636));
          UserEditActivity.this.ordinary_btn_checkbox.setChecked(false);
          UserEditActivity.this.amateur_btn_checkbox.setChecked(true);
          UserEditActivity.this.profess_btn_checkbox.setChecked(false);
          UserEditActivity.this.showAlertDailog(UserEditActivity.this.getResources().getString(2131361794));
        }
      });
      this.profess_btn_checkbox.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          UserEditActivity.this.userType = "2";
          UserEditActivity.this.ordinaryBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837631));
          UserEditActivity.this.amatuerBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837634));
          UserEditActivity.this.professBtn.setBackgroundDrawable(UserEditActivity.this.getResources().getDrawable(2130837637));
          UserEditActivity.this.ordinary_btn_checkbox.setChecked(false);
          UserEditActivity.this.amateur_btn_checkbox.setChecked(false);
          UserEditActivity.this.profess_btn_checkbox.setChecked(true);
          UserEditActivity.this.showAlertDailog(UserEditActivity.this.getResources().getString(2131361796));
        }
      });
      return;
      label1162:
      this.maleBtn.setBackgroundDrawable(getResources().getDrawable(2130837617));
      break;
      label1182:
      if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_LB))
      {
        this.isKG = false;
        this.lbrb.setChecked(true);
        this.heightET2.setVisibility(0);
        this.heng_tv.setVisibility(0);
        this.danwei_tv.setText(getText(2131361895));
        this.target_edittv2.setVisibility(0);
        this.heng_tv2.setVisibility(0);
        this.target_danwei_tv.setText(getText(2131361890));
        break label800;
      }
      this.kgrb.setChecked(true);
      this.heightET2.setVisibility(8);
      this.heng_tv.setVisibility(8);
      this.danwei_tv.setText(getText(2131361894));
      this.target_edittv2.setVisibility(8);
      this.heng_tv2.setVisibility(8);
      this.target_danwei_tv.setText(getText(2131361889));
      break label800;
      label1345:
      this.heightET.setText(UtilTooth.cm2FT_INArray(UtilConstants.CURRENT_USER.getBheigth())[0]);
      this.heightET2.setText(UtilTooth.cm2FT_INArray(UtilConstants.CURRENT_USER.getBheigth())[1]);
      this.target_edittv2.setVisibility(8);
      this.heng_tv2.setVisibility(8);
      String str = this.target_edittv.getText().toString().trim();
      if ((str != null) && ("".equals(str))) {}
      float f = UtilTooth.kgToLB_target(UtilConstants.CURRENT_USER.getTargweight());
      this.target_edittv.setText(UtilTooth.onePoint(f));
      break label927;
      label1465:
      this.maleBtn.setBackgroundDrawable(getResources().getDrawable(2130837616));
      this.femaleBtn.setBackgroundDrawable(getResources().getDrawable(2130837713));
      this.sex = "0";
      this.sex_male_checkbox.setChecked(false);
      this.sex_female_checkbox.setChecked(true);
      break label1010;
      label1524:
      if ("2".equals(UtilConstants.CURRENT_USER.getLevel()))
      {
        this.userType = "2";
        this.professBtn.setBackgroundDrawable(getResources().getDrawable(2130837637));
        this.ordinary_btn_checkbox.setChecked(false);
        this.amateur_btn_checkbox.setChecked(false);
        this.profess_btn_checkbox.setChecked(true);
      }
      else
      {
        this.userType = "0";
        this.ordinaryBtn.setBackgroundDrawable(getResources().getDrawable(2130837632));
        this.ordinary_btn_checkbox.setChecked(true);
        this.amateur_btn_checkbox.setChecked(false);
        this.profess_btn_checkbox.setChecked(false);
      }
    }
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
    Message localMessage = this.saveHandler.obtainMessage(1);
    this.saveHandler.sendMessage(localMessage);
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
    String str1;
    String str2;
    String str5;
    String str9;
    float f;
    if (UtilConstants.CURRENT_USER != null)
    {
      UtilConstants.CURRENT_USER.setUserName(this.nameET.getText().toString());
      UtilConstants.CURRENT_USER.setLevel(this.userType);
      UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
      UtilConstants.CURRENT_USER.setDanwei(UtilConstants.CHOICE_KG);
      UtilConstants.CURRENT_USER.setPer_photo(this.photoImg);
      UtilConstants.CURRENT_USER.setSex(this.sex);
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
      str5 = this.ageET.getText().toString().trim();
      if ((str5 == null) || ("".equals(str5))) {
        str5 = "0";
      }
      if (this.monthET != null)
      {
        str9 = this.monthET.getText().toString();
        if (!"".equals(str9.trim())) {
          break label521;
        }
        UtilConstants.CURRENT_USER.setAgeMonth(0);
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
          break label535;
        }
        UtilConstants.CURRENT_USER.setTargweight(f);
      }
      label354:
      if (!this.isKG) {
        break label549;
      }
      UtilConstants.CURRENT_USER.setBheigth(Float.parseFloat(str1));
      label371:
      if (!this.isKG) {
        break label574;
      }
      UtilConstants.CURRENT_USER.setBheigth(Float.parseFloat(str1));
    }
    for (;;)
    {
      this.su.editSharedPreferences("lefuconfig", "unit", UtilConstants.CHOICE_KG);
      UtilConstants.CURRENT_USER.setBirth(str5);
      if ((str5 != null) && (!"".equals(str5)) && (str5.lastIndexOf("-") > 0))
      {
        String str6 = str5.substring(1 + str5.lastIndexOf("-"));
        String str7 = str5.substring(0, str5.lastIndexOf("-"));
        str5 = str6 + "-" + str7;
      }
      UtilConstants.CURRENT_USER.setAgeYear(Tool.getAgeByBirthday(Tool.StringToDate(str5, "yyyy-MM-dd")));
      return UtilConstants.CURRENT_USER;
      label521:
      UtilConstants.CURRENT_USER.setAgeMonth(Integer.parseInt(str9));
      break;
      label535:
      UtilConstants.CURRENT_USER.setTargweight(UtilTooth.lbToKg_target(f));
      break label354;
      label549:
      UtilConstants.CURRENT_USER.setBheigth(UtilTooth.ft_in2CMArray(new String[] { str1, str2 }));
      break label371;
      label574:
      UtilConstants.CURRENT_USER.setBheigth(UtilTooth.ft_in2CMArray(new String[] { str1, str2 }));
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
    setContentView(2130903062);
    this.editor = getSharedPreferences("rcp_shape", 0).edit();
    this.centerIndex = getIntent().getIntExtra("center", -100);
    ExitApplication.getInstance().addActivity(this);
    UtilConstants.su = new SharedPreferencesUtil(this);
    if (UtilConstants.CURRENT_USER == null)
    {
      AppData.isCheckScale = true;
      UtilConstants.CURRENT_USER = (UserModel)JSONObject.parseObject((String)UtilConstants.su.readbackUp("lefuconfig", "addUser", ""), UserModel.class);
    }
    initView();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
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
    this.wheelMain.setTime(i, j, k);
    this.dialog = new AlertDialog.Builder(this).setView(localView).show();
    Window localWindow = this.dialog.getWindow();
    localWindow.setGravity(80);
    localWindow.setWindowAnimations(2131427351);
    ((Button)localView.findViewById(2131165418)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UserEditActivity.this.isOK = true;
        UserEditActivity.this.ageET.setText(UserEditActivity.this.wheelMain.getTime());
        UserEditActivity.this.dialog.dismiss();
      }
    });
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\UserEditActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */