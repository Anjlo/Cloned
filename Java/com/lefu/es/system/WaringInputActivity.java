package com.lefu.es.system;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class WaringInputActivity
  extends Activity
{
  private String readMessage;
  private TextView tx = null;
  
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
      setContentView(2130903065);
      Bundle localBundle = getIntent().getExtras();
      if (localBundle.containsKey("waringmsg")) {
        this.readMessage = localBundle.getString("waringmsg");
      }
      this.tx = ((TextView)findViewById(2131165224));
      if ((this.tx != null) && (this.readMessage != null)) {
        this.tx.setText(this.readMessage);
      }
      return;
      setRequestedOrientation(1);
    }
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
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((paramMotionEvent.getAction() == 0) && (isOutOfBounds(this, paramMotionEvent))) {
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
    }
    finish();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\WaringInputActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */