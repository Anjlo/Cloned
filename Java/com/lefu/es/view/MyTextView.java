package com.lefu.es.view;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.lefu.es.system.LoadingActivity;

public class MyTextView
  extends LinearLayout
{
  private Context context;
  private String tLeft;
  private String tRight;
  TextView tvST;
  
  public MyTextView(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
  }
  
  public MyTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    init();
  }
  
  public void init()
  {
    removeAllViewsInLayout();
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    setOrientation(0);
    setGravity(17);
    this.tvST = new TextView(this.context);
    this.tvST.setLayoutParams(localLayoutParams);
    this.tvST.setTextColor(-1);
    label131:
    LinearLayout localLinearLayout;
    TextView localTextView1;
    View localView;
    TextView localTextView2;
    if (LoadingActivity.isPad) {
      if (LoadingActivity.isV)
      {
        this.tvST.setTextSize(120.0F);
        this.tvST.setText(this.tLeft);
        if ((this.tLeft != null) && (this.tLeft.equals(getContext().getString(2131361844))))
        {
          if (!LoadingActivity.isPad) {
            break label462;
          }
          this.tvST.setTextSize(20.0F);
        }
        addView(this.tvST);
        if ((this.tRight != null) && (this.tRight.indexOf("/") > 0))
        {
          String[] arrayOfString = this.tRight.split("/");
          int i = Integer.parseInt(arrayOfString[0]);
          int j = Integer.parseInt(arrayOfString[1]);
          localLinearLayout = new LinearLayout(this.context);
          localLayoutParams.leftMargin = 20;
          localLinearLayout.setLayoutParams(localLayoutParams);
          localLinearLayout.setOrientation(1);
          localTextView1 = new TextView(this.context);
          localTextView1.setTextSize(18.0F);
          localTextView1.setTextColor(-1);
          localTextView1.getPaint().setFakeBoldText(true);
          localTextView1.setGravity(1);
          localTextView1.setText(i);
          localView = new View(this.context);
          localView.setLayoutParams(new LinearLayout.LayoutParams(50, 4));
          localView.setBackgroundColor(-1);
          localTextView2 = new TextView(this.context);
          localTextView2.setGravity(1);
          localTextView2.setTextColor(-1);
          localTextView2.getPaint().setFakeBoldText(true);
          localTextView2.setTextSize(18.0F);
          localTextView2.setText(j);
          if (LoadingActivity.isPad)
          {
            if (!LoadingActivity.isV) {
              break label474;
            }
            localView.setLayoutParams(new LinearLayout.LayoutParams(80, 4));
            localTextView1.setTextSize(75.0F);
            localTextView2.setTextSize(75.0F);
          }
        }
      }
    }
    for (;;)
    {
      localLinearLayout.addView(localTextView1);
      localLinearLayout.addView(localView);
      localLinearLayout.addView(localTextView2);
      addView(localLinearLayout);
      return;
      this.tvST.setTextSize(80.0F);
      break;
      this.tvST.setTextSize(20.0F);
      break;
      label462:
      this.tvST.setTextSize(16.0F);
      break label131;
      label474:
      localView.setLayoutParams(new LinearLayout.LayoutParams(40, 4));
      localTextView1.setTextSize(30.0F);
      localTextView2.setTextSize(30.0F);
    }
  }
  
  public void setTexts(String paramString1, String paramString2)
  {
    this.tLeft = paramString1;
    this.tRight = paramString2;
    init();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\MyTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */