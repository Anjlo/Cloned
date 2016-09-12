package com.lefu.es.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class DisplayUtil
{
  public static int dip2px(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
  }
  
  public static int getWidth(Context paramContext)
  {
    new DisplayMetrics();
    return paramContext.getResources().getDisplayMetrics().widthPixels;
  }
  
  public static int px2dip(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().density);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\util\DisplayUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */