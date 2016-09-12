package com.lefu.es.system;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.util.SharedPreferencesUtil;

public class ScaleChangeAlertActivity
  extends Activity
{
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
      setContentView(2130903044);
      if (UtilConstants.su == null) {
        UtilConstants.su = new SharedPreferencesUtil(this);
      }
      return;
      setRequestedOrientation(1);
    }
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
    {
      finish();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  protected void onResume()
  {
    super.onResume();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0) {
      return true;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void saveOrCancle(View paramView)
  {
    if ((paramView instanceof Button)) {}
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131165266: 
      UtilConstants.su.editSharedPreferences("lefuconfig", "reCheck", Boolean.valueOf(false));
      finish();
      return;
    }
    com.lefu.es.constant.AppData.reCheck = true;
    UtilConstants.su.editSharedPreferences("lefuconfig", "reCheck", Boolean.valueOf(true));
    finish();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\ScaleChangeAlertActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */