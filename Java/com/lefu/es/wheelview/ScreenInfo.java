package com.lefu.es.wheelview;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ScreenInfo
{
  private Activity activity;
  private float density;
  private int densityDpi;
  private int height;
  private int width;
  
  public ScreenInfo(Activity paramActivity)
  {
    this.activity = paramActivity;
    ini();
  }
  
  private void ini()
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    this.activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    this.width = localDisplayMetrics.widthPixels;
    this.height = localDisplayMetrics.heightPixels;
    this.density = localDisplayMetrics.density;
    this.densityDpi = localDisplayMetrics.densityDpi;
  }
  
  public Activity getActivity()
  {
    return this.activity;
  }
  
  public float getDensity()
  {
    return this.density;
  }
  
  public int getDensityDpi()
  {
    return this.densityDpi;
  }
  
  public int getHeight()
  {
    return this.height;
  }
  
  public int getWidth()
  {
    return this.width;
  }
  
  public void setActivity(Activity paramActivity)
  {
    this.activity = paramActivity;
  }
  
  public void setDensity(float paramFloat)
  {
    this.density = paramFloat;
  }
  
  public void setDensityDpi(int paramInt)
  {
    this.densityDpi = paramInt;
  }
  
  public void setHeight(int paramInt)
  {
    this.height = paramInt;
  }
  
  public void setWidth(int paramInt)
  {
    this.width = paramInt;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\wheelview\ScreenInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */