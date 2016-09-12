package com.lefu.es.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MyBar
  extends LinearLayout
{
  public MyBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    int i = Integer.parseInt(Build.VERSION.SDK);
    Log.e("test", "ϵͳ�汾��" + i);
    setOrientation(0);
    setWeightSum(170.0F);
    View localView1 = new View(paramContext);
    View localView4;
    if (i >= 15)
    {
      localView1.setBackgroundResource(2130837624);
      localView1.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 27.0F));
      addView(localView1);
      View localView2 = new View(paramContext);
      localView2.setBackgroundColor(Color.parseColor("#54b32c"));
      localView2.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 73.0F));
      addView(localView2);
      View localView3 = new View(paramContext);
      localView3.setBackgroundColor(Color.parseColor("#f7af00"));
      localView3.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 40.0F));
      addView(localView3);
      localView4 = new View(paramContext);
      if (i < 15) {
        break label231;
      }
      localView4.setBackgroundResource(2130837626);
    }
    for (;;)
    {
      localView4.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 30.0F));
      addView(localView4);
      return;
      localView1.setBackgroundResource(2130837625);
      break;
      label231:
      localView4.setBackgroundResource(2130837627);
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\MyBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */