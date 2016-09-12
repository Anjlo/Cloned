package com.lefu.es.system;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

public class GetFatDateErrorActivity
  extends Activity
{
  public static Handler handler;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903049);
    if (LoadingActivity.isPad) {
      setRequestedOrientation(2);
    }
    for (;;)
    {
      requestWindowFeature(5);
      requestWindowFeature(1);
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
  
  protected void onPause()
  {
    finish();
    super.onPause();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\GetFatDateErrorActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */