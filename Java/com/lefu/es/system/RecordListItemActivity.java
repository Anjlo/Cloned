package com.lefu.es.system;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.view.MyTextView4;

public class RecordListItemActivity
  extends Activity
  implements View.OnClickListener
{
  ImageView chaImage = null;
  private Records record = null;
  private TableRow row_phsicalage = null;
  TextView tv_name_title = null;
  TextView tvdetail_bmi = null;
  TextView tvdetail_bmi_title = null;
  TextView tvdetail_bmr = null;
  TextView tvdetail_bmr_title = null;
  TextView tvdetail_bodyfat = null;
  TextView tvdetail_bodyfat_title = null;
  TextView tvdetail_bodywater = null;
  TextView tvdetail_bodywater_title = null;
  TextView tvdetail_bone = null;
  TextView tvdetail_bone_title = null;
  TextView tvdetail_muscle = null;
  TextView tvdetail_muscle_title;
  TextView tvdetail_phsicalage = null;
  TextView tvdetail_phsicalage_title = null;
  TextView tvdetail_visceral = null;
  TextView tvdetail_visceral_title = null;
  MyTextView4 tvdetail_weight = null;
  TextView tvdetail_weight_title;
  
  private void initResourceRefs()
  {
    this.chaImage = ((ImageView)findViewById(2131165283));
    this.tvdetail_weight_title = ((TextView)findViewById(2131165285));
    this.tvdetail_weight = ((MyTextView4)findViewById(2131165286));
    this.tvdetail_bmr = ((TextView)findViewById(2131165300));
    this.tvdetail_bmr_title = ((TextView)findViewById(2131165299));
    this.tvdetail_bone = ((TextView)findViewById(2131165288));
    this.tvdetail_bodyfat = ((TextView)findViewById(2131165290));
    this.tvdetail_bodyfat_title = ((TextView)findViewById(2131165289));
    this.tvdetail_muscle = ((TextView)findViewById(2131165292));
    this.tvdetail_phsicalage = ((TextView)findViewById(2131165303));
    this.tvdetail_phsicalage_title = ((TextView)findViewById(2131165302));
    this.row_phsicalage = ((TableRow)findViewById(2131165301));
    this.tvdetail_bone_title = ((TextView)findViewById(2131165287));
    this.tvdetail_muscle_title = ((TextView)findViewById(2131165291));
    this.tvdetail_bodywater = ((TextView)findViewById(2131165294));
    this.tvdetail_bodywater_title = ((TextView)findViewById(2131165293));
    this.tvdetail_visceral = ((TextView)findViewById(2131165296));
    this.tvdetail_visceral_title = ((TextView)findViewById(2131165295));
    this.tvdetail_bmi = ((TextView)findViewById(2131165298));
    this.tvdetail_bmi_title = ((TextView)findViewById(2131165297));
    this.tv_name_title = ((TextView)findViewById(2131165284));
    this.chaImage.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        RecordListItemActivity.this.finish();
      }
    });
  }
  
  public void creatView(Records paramRecords)
  {
    if (paramRecords != null)
    {
      if (!UtilConstants.KITCHEN_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
        break label893;
      }
      this.tv_name_title.setText(paramRecords.getRphoto());
      if (UtilConstants.CURRENT_USER == null) {
        break label837;
      }
      if (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) {
        break label497;
      }
      this.tvdetail_weight_title.setText(getText(2131361817).toString());
      if (this.tvdetail_weight != null) {
        this.tvdetail_weight.setTexts(UtilTooth.kgToLBoz(paramRecords.getRweight()), null);
      }
    }
    for (;;)
    {
      this.tvdetail_bone_title.setText(getText(2131361969).toString());
      this.tvdetail_bone.setText(UtilTooth.keep2Point(paramRecords.getRbodywater()));
      this.tvdetail_bodyfat_title.setText(getText(2131361970).toString());
      if (this.tvdetail_bodyfat != null) {
        this.tvdetail_bodyfat.setText(UtilTooth.keep2Point(paramRecords.getRbodyfat()));
      }
      this.tvdetail_muscle_title.setText(getText(2131361973).toString());
      this.tvdetail_muscle.setText(UtilTooth.keep2Point(paramRecords.getRbone()));
      this.tvdetail_bodywater_title.setText(getText(2131361976).toString());
      if (this.tvdetail_bodywater != null) {
        this.tvdetail_bodywater.setText(UtilTooth.keep2Point(paramRecords.getRmuscle()));
      }
      this.tvdetail_visceral_title.setText(getText(2131361979).toString());
      if (this.tvdetail_visceral != null) {
        this.tvdetail_visceral.setText(UtilTooth.keep2Point(paramRecords.getRvisceralfat()));
      }
      this.tvdetail_bmi_title.setText(getText(2131361982).toString());
      if (this.tvdetail_bmi != null) {
        this.tvdetail_bmi.setText(UtilTooth.keep2Point(paramRecords.getRbmi()));
      }
      this.tvdetail_bmr_title.setText(getText(2131361985).toString());
      if (this.tvdetail_bmr != null) {
        this.tvdetail_bmr.setText(UtilTooth.keep2Point(paramRecords.getRbmr()));
      }
      this.tvdetail_phsicalage_title.setText(getText(2131361988).toString());
      this.tvdetail_phsicalage.setText(UtilTooth.keep2Point(paramRecords.getBodyAge()));
      return;
      label497:
      if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB))
      {
        this.tvdetail_weight_title.setText(getText(2131361820).toString());
        if (this.tvdetail_weight != null) {
          this.tvdetail_weight.setTexts(UtilTooth.kgToFloz(paramRecords.getRweight()), null);
        }
      }
      else if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_G))
      {
        this.tvdetail_weight_title.setText(getText(2131361813).toString());
        if (this.tvdetail_weight != null) {
          this.tvdetail_weight.setTexts(UtilTooth.keep2Point(paramRecords.getRweight()), null);
        }
      }
      else if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ML))
      {
        this.tvdetail_weight_title.setText(getText(2131361814).toString());
        if (this.tvdetail_weight != null) {
          this.tvdetail_weight.setTexts(UtilTooth.keep2Point(paramRecords.getRweight()), null);
        }
      }
      else if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ML2))
      {
        this.tvdetail_weight_title.setText(getText(2131361815).toString());
        if (this.tvdetail_weight != null) {
          this.tvdetail_weight.setTexts(UtilTooth.kgToML(paramRecords.getRweight()), null);
        }
      }
      else
      {
        this.tvdetail_weight_title.setText(getText(2131361817).toString());
        if (this.tvdetail_weight != null)
        {
          this.tvdetail_weight.setTexts(UtilTooth.kgToLBoz(paramRecords.getRweight()), null);
          continue;
          label837:
          this.tvdetail_weight_title.setText(getText(2131361813).toString());
          if (this.tvdetail_weight != null) {
            this.tvdetail_weight.setTexts(UtilTooth.keep2Point(paramRecords.getRweight()), null);
          }
        }
      }
    }
    label893:
    this.tv_name_title.setText("");
    if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_KG))
    {
      this.tvdetail_weight_title.setText(getText(2131361816).toString());
      if (this.tvdetail_weight != null) {
        this.tvdetail_weight.setTexts(paramRecords.getRweight(), null);
      }
      label966:
      if (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_KG)) {
        break label1532;
      }
      this.tvdetail_bone_title.setText(getText(2131361829).toString());
      this.tvdetail_muscle_title.setText(getText(2131361832).toString());
      this.tvdetail_bone.setText(paramRecords.getRbone());
      this.tvdetail_muscle.setText(paramRecords.getRmuscle());
      label1067:
      if (paramRecords.getBodyAge() <= 0.0F) {
        break label1856;
      }
      this.tvdetail_phsicalage.setText(UtilTooth.keep0Point(paramRecords.getBodyAge()));
    }
    for (;;)
    {
      if (this.tvdetail_bmr != null) {
        this.tvdetail_bmr.setText(paramRecords.getRbmr());
      }
      if (this.tvdetail_bodyfat != null) {
        this.tvdetail_bodyfat.setText(paramRecords.getRbodyfat());
      }
      if (this.tvdetail_bodywater != null) {
        this.tvdetail_bodywater.setText(paramRecords.getRbodywater());
      }
      if (this.tvdetail_visceral != null) {
        this.tvdetail_visceral.setText(paramRecords.getRvisceralfat());
      }
      if (this.tvdetail_bmi == null) {
        break;
      }
      float f = UtilTooth.countBMI2(paramRecords.getRweight(), UtilConstants.CURRENT_USER.getBheigth() / 100.0F);
      this.tvdetail_bmi.setText(UtilTooth.myround(f));
      return;
      if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_LB))
      {
        this.tvdetail_weight_title.setText(getText(2131361818).toString());
        if (this.tvdetail_weight == null) {
          break label966;
        }
        this.tvdetail_weight.setTexts(UtilTooth.kgToLB_new(paramRecords.getRweight()), null);
        break label966;
      }
      if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_ST))
      {
        this.tvdetail_weight_title.setText(getText(2131361819).toString());
        if (this.tvdetail_weight != null) {
          this.tvdetail_weight.setTexts(UtilTooth.kgToStLb_B(paramRecords.getRweight()), null);
        }
        String[] arrayOfString = UtilTooth.kgToStLbForScaleFat2(paramRecords.getRweight());
        if ((arrayOfString[1] != null) && (arrayOfString[1].indexOf("/") > 0))
        {
          this.tvdetail_weight.setTexts(arrayOfString[0], arrayOfString[1]);
          break label966;
        }
        this.tvdetail_weight.setTexts(arrayOfString[0] + getText(2131361892), null);
        break label966;
      }
      this.tvdetail_weight_title.setText(getText(2131361816).toString());
      if (this.tvdetail_weight == null) {
        break label966;
      }
      this.tvdetail_weight.setTexts(paramRecords.getRweight(), null);
      break label966;
      label1532:
      if ((UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB)))
      {
        this.tvdetail_bone_title.setText(getText(2131361830).toString());
        this.tvdetail_muscle_title.setText(getText(2131361833).toString());
        this.tvdetail_bone.setText(UtilTooth.kgToLB(paramRecords.getRbone()));
        this.tvdetail_muscle.setText(UtilTooth.kgToLB(paramRecords.getRmuscle()));
        break label1067;
      }
      if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ST))
      {
        this.tvdetail_bone_title.setText(getText(2131361831).toString());
        this.tvdetail_muscle_title.setText(getText(2131361834).toString());
        this.tvdetail_bone.setText(UtilTooth.kgToLB(paramRecords.getRbone()));
        this.tvdetail_muscle.setText(UtilTooth.kgToLB(paramRecords.getRmuscle()));
        break label1067;
      }
      this.tvdetail_bone_title.setText(getText(2131361829).toString());
      this.tvdetail_muscle_title.setText(getText(2131361832).toString());
      this.tvdetail_bone.setText(paramRecords.getRbone());
      this.tvdetail_muscle.setText(paramRecords.getRmuscle());
      break label1067;
      label1856:
      this.row_phsicalage.setVisibility(8);
    }
  }
  
  public void onClick(View paramView)
  {
    paramView.getId();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRequestedOrientation(2);
    requestWindowFeature(5);
    requestWindowFeature(1);
    if ((UtilConstants.BABY_SCALE.equals(UtilConstants.CURRENT_SCALE)) || (UtilConstants.BATHROOM_SCALE.equals(UtilConstants.CURRENT_SCALE))) {
      setContentView(2130903048);
    }
    for (;;)
    {
      initResourceRefs();
      this.record = ((Records)getIntent().getSerializableExtra("record"));
      creatView(this.record);
      return;
      setContentView(2130903047);
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
    {
      finish();
      return true;
    }
    if (paramInt == 3)
    {
      if (UtilConstants.serveIntent != null) {
        stopService(UtilConstants.serveIntent);
      }
      ((NotificationManager)getSystemService("notification")).cancel(0);
      if (LoadingActivity.mainActivty != null) {
        LoadingActivity.mainActivty.finish();
      }
      finish();
      ExitApplication.getInstance().exit(this);
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\RecordListItemActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */