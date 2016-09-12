package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomDialogActivity
  extends Activity
{
  public static Handler handler;
  LinearLayout layout1;
  LinearLayout layout2;
  Button okBtn;
  ProgressBar pbar1;
  TextView tv1;
  
  @SuppressLint({"HandlerLeak"})
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
      setContentView(2130903088);
      this.layout1 = ((LinearLayout)findViewById(2131165477));
      this.layout2 = ((LinearLayout)findViewById(2131165304));
      this.tv1 = ((TextView)findViewById(2131165223));
      this.pbar1 = ((ProgressBar)findViewById(2131165478));
      this.okBtn = ((Button)findViewById(2131165479));
      this.okBtn.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          CustomDialogActivity.this.finish();
        }
      });
      Bundle localBundle = getIntent().getExtras();
      try
      {
        boolean bool = localBundle.containsKey("error");
        localObject = null;
        if (bool)
        {
          String str = localBundle.getString("error");
          localObject = str;
        }
      }
      catch (Exception localException)
      {
        for (;;)
        {
          Object localObject = null;
          continue;
          if ((localObject != null) && ("3".equals(localObject)))
          {
            this.layout1.setVisibility(0);
            this.okBtn.setVisibility(0);
            this.layout2.setVisibility(8);
            this.pbar1.setVisibility(8);
            this.tv1.setText(getText(2131361881));
          }
          else
          {
            this.layout1.setVisibility(0);
            this.layout2.setVisibility(8);
          }
        }
      }
      if ((localObject == null) || (!"2".equals(localObject))) {
        break;
      }
      this.layout1.setVisibility(8);
      this.layout2.setVisibility(0);
      handler = new Handler()
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          switch (paramAnonymousMessage.what)
          {
          default: 
            CustomDialogActivity.this.finish();
          }
          for (;;)
          {
            super.handleMessage(paramAnonymousMessage);
            return;
            CustomDialogActivity.this.layout1.setVisibility(8);
            CustomDialogActivity.this.layout2.setVisibility(0);
            CustomDialogActivity.this.finish();
            continue;
            CustomDialogActivity.this.finish();
          }
        }
      };
      handler.postDelayed(new Runnable()
      {
        public void run()
        {
          CustomDialogActivity.this.finish();
        }
      }, 10000L);
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


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\CustomDialogActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */