package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.ZoomButtonsController;
import com.lefu.es.constant.UtilConstants;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HelpActivity
  extends Activity
{
  TextView tvTitle;
  WebView weView;
  
  public void onBackPressed()
  {
    finish();
  }
  
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
      setContentView(2130903050);
      this.tvTitle = ((TextView)findViewById(2131165195));
      this.weView = ((WebView)findViewById(2131165306));
      this.weView.getSettings().setJavaScriptEnabled(true);
      this.weView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
      this.weView.getSettings().setSupportZoom(true);
      this.weView.getSettings().setBuiltInZoomControls(true);
      this.weView.setInitialScale(200);
      this.weView.setWebChromeClient(new MyWebChromeClient());
      this.weView.setWebViewClient(new WebViewClient()
      {
        public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
        {
          HelpActivity.this.weView.loadUrl("file:///android_asset/help.html");
          return false;
        }
      });
      this.weView.loadUrl("file:///android_asset/help.html");
      if (Integer.parseInt(Build.VERSION.SDK) < 11) {
        break label202;
      }
      WebSettings localWebSettings = this.weView.getSettings();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(false);
      setZoomControlGoneX(localWebSettings, arrayOfObject);
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
      label202:
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
  
  public void openIntentate(View paramView)
  {
    Intent localIntent = new Intent();
    localIntent.setData(Uri.parse(UtilConstants.homeUrl));
    localIntent.setAction("android.intent.action.VIEW");
    startActivity(localIntent);
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
  
  class MyWebChromeClient
    extends WebChromeClient
  {
    MyWebChromeClient() {}
    
    public boolean onJsAlert(WebView paramWebView, String paramString1, String paramString2, JsResult paramJsResult)
    {
      paramJsResult.cancel();
      return true;
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\HelpActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */