package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;
import com.lefu.es.constant.UtilConstants;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InfoActivity
  extends Activity
{
  WebView weView;
  
  public void onClickBack(View paramView)
  {
    finish();
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (LoadingActivity.isPad)
    {
      setRequestedOrientation(2);
      setContentView(2130903051);
      this.weView = ((WebView)findViewById(2131165306));
      this.weView.getSettings().setJavaScriptEnabled(true);
      this.weView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
      this.weView.getSettings().setSupportZoom(true);
      this.weView.getSettings().setBuiltInZoomControls(true);
      int i = getResources().getDisplayMetrics().densityDpi;
      switch (i)
      {
      default: 
        label128:
        this.weView.setInitialScale(25);
        this.weView.getSettings().setUseWideViewPort(true);
        this.weView.getSettings().setLoadWithOverviewMode(true);
        if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_KG)) {
          if (UtilConstants.CURRENT_SCALE.equals(UtilConstants.BABY_SCALE))
          {
            this.weView.loadUrl("file:///android_asset/info_baby_kg.htm");
            label192:
            if (Integer.parseInt(Build.VERSION.SDK) < 11) {
              break label327;
            }
            WebSettings localWebSettings = this.weView.getSettings();
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Boolean.valueOf(false);
            setZoomControlGoneX(localWebSettings, arrayOfObject);
          }
        }
        break;
      }
    }
    for (;;)
    {
      this.weView.setOnTouchListener(new View.OnTouchListener()
      {
        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
          switch (paramAnonymousMotionEvent.getAction())
          {
          }
          return false;
        }
      });
      return;
      setRequestedOrientation(1);
      break;
      break label128;
      break label128;
      break label128;
      this.weView.loadUrl("file:///android_asset/info.htm");
      break label192;
      if (UtilConstants.CURRENT_SCALE.equals(UtilConstants.BABY_SCALE))
      {
        this.weView.loadUrl("file:///android_asset/info_baby_lboz.htm");
        break label192;
      }
      this.weView.loadUrl("file:///android_asset/info_lb.htm");
      break label192;
      label327:
      setZoomControlGone(this.weView);
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 3) {
      ((NotificationManager)getSystemService("notification")).cancel(0);
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  protected void onResume()
  {
    super.onResume();
  }
  
  public void setZoomControlGone(View paramView)
  {
    try
    {
      Field localField = WebView.class.getDeclaredField("mZoomButtonsController");
      localField.setAccessible(true);
      ZoomButtonsController localZoomButtonsController = new ZoomButtonsController(paramView);
      localZoomButtonsController.getZoomControls().setVisibility(8);
      return;
    }
    catch (SecurityException localSecurityException)
    {
      try
      {
        localField.set(paramView, localZoomButtonsController);
        return;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        localIllegalArgumentException.printStackTrace();
        return;
        localSecurityException = localSecurityException;
        localSecurityException.printStackTrace();
        return;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        localIllegalAccessException.printStackTrace();
        return;
      }
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      localNoSuchFieldException.printStackTrace();
    }
  }
  
  public void setZoomControlGoneX(WebSettings paramWebSettings, Object[] paramArrayOfObject)
  {
    Class localClass = paramWebSettings.getClass();
    for (;;)
    {
      try
      {
        Class[] arrayOfClass = new Class[paramArrayOfObject.length];
        int i = 0;
        int j = paramArrayOfObject.length;
        Method[] arrayOfMethod;
        int k;
        if (i >= j)
        {
          arrayOfMethod = localClass.getMethods();
          k = 0;
          if (k < arrayOfMethod.length) {}
        }
        else
        {
          arrayOfClass[i] = paramArrayOfObject[i].getClass();
          i++;
          continue;
        }
        boolean bool = arrayOfMethod[k].getName().equals("setDisplayZoomControls");
        if (bool) {
          try
          {
            Method localMethod = arrayOfMethod[k];
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Boolean.valueOf(false);
            localMethod.invoke(paramWebSettings, arrayOfObject);
            return;
          }
          catch (Exception localException2)
          {
            localException2.printStackTrace();
            return;
          }
        }
        k++;
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
        return;
      }
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\InfoActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */