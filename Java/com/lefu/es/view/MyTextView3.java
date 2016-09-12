package com.lefu.es.view;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.lefu.es.system.LoadingActivity;

public class MyTextView3
  extends LinearLayout
{
  private Context context;
  TextView danwei;
  private String tLeft;
  private String tRight;
  TextView tvBig;
  TextView tvST;
  TextView tvSmall;
  
  public MyTextView3(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
  }
  
  public MyTextView3(Context paramContext, AttributeSet paramAttributeSet)
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
    setGravity(16);
    this.tvST = new TextView(this.context);
    this.tvST.setLayoutParams(localLayoutParams);
    this.tvST.setTextColor(-16777216);
    this.tvST.setTextSize(14.0F);
    if ("0:0".equals(this.tLeft)) {
      this.tLeft = ("0:0 " + getContext().getText(2131361892).toString());
    }
    this.tvST.setText(this.tLeft);
    if ((this.tLeft != null) && (this.tLeft.equals(getContext().getString(2131361844)))) {
      this.tvST.setTextSize(20.0F);
    }
    if (LoadingActivity.isPad) {
      this.tvST.setTextSize(30.0F);
    }
    addView(this.tvST);
    if ((this.tRight != null) && (this.tRight.indexOf("/") > 0))
    {
      String[] arrayOfString = this.tRight.split("/");
      int i = Integer.parseInt(arrayOfString[0]);
      int j = Integer.parseInt(arrayOfString[1]);
      LinearLayout localLinearLayout1 = new LinearLayout(this.context);
      localLinearLayout1.setOrientation(0);
      LinearLayout localLinearLayout2 = new LinearLayout(this.context);
      localLayoutParams.leftMargin = 20;
      localLinearLayout2.setLayoutParams(localLayoutParams);
      localLinearLayout2.setOrientation(1);
      this.tvSmall = new TextView(this.context);
      this.tvSmall.setTextSize(8.0F);
      if (LoadingActivity.isPad) {
        this.tvSmall.setTextSize(14.0F);
      }
      this.tvSmall.setTextColor(-16777216);
      this.tvSmall.getPaint().setFakeBoldText(true);
      this.tvSmall.setGravity(1);
      this.tvSmall.setText(i);
      View localView = new View(this.context);
      localView.setLayoutParams(new LinearLayout.LayoutParams(20, 1));
      localView.setBackgroundColor(-16777216);
      this.tvBig = new TextView(this.context);
      this.tvBig.setGravity(1);
      this.tvBig.setTextColor(-16777216);
      this.tvBig.getPaint().setFakeBoldText(true);
      this.danwei = new TextView(this.context);
      this.danwei.setGravity(1);
      this.danwei.setTextColor(-16777216);
      this.danwei.setTextSize(10.0F);
      if (LoadingActivity.isPad) {
        this.danwei.setTextSize(20.0F);
      }
      this.tvBig.setTextSize(8.0F);
      if (LoadingActivity.isPad) {
        this.tvBig.setTextSize(20.0F);
      }
      this.tvBig.setText(j);
      this.danwei.setText(getContext().getText(2131361892));
      localLinearLayout2.addView(this.tvSmall);
      localLinearLayout2.addView(localView);
      localLinearLayout2.addView(this.tvBig);
      localLinearLayout1.addView(localLinearLayout2);
      localLinearLayout1.addView(this.danwei);
      addView(localLinearLayout1);
    }
  }
  
  public void init(boolean paramBoolean)
  {
    removeAllViewsInLayout();
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    setOrientation(0);
    setGravity(16);
    this.tvST = new TextView(this.context);
    this.tvST.setLayoutParams(localLayoutParams);
    this.tvST.setTextColor(-1);
    this.tvST.setTextSize(14.0F);
    if ("0:0".equals(this.tLeft)) {
      this.tLeft = ("0:0 " + getContext().getText(2131361892).toString());
    }
    this.tvST.setText(this.tLeft);
    if ((this.tLeft != null) && (this.tLeft.equals(getContext().getString(2131361844)))) {
      this.tvST.setTextSize(20.0F);
    }
    if (LoadingActivity.isPad) {
      this.tvST.setTextSize(30.0F);
    }
    addView(this.tvST);
    if ((this.tRight != null) && (this.tRight.indexOf("/") > 0))
    {
      String[] arrayOfString = this.tRight.split("/");
      int i = Integer.parseInt(arrayOfString[0]);
      int j = Integer.parseInt(arrayOfString[1]);
      LinearLayout localLinearLayout1 = new LinearLayout(this.context);
      localLinearLayout1.setOrientation(0);
      LinearLayout localLinearLayout2 = new LinearLayout(this.context);
      localLayoutParams.leftMargin = 20;
      localLinearLayout2.setLayoutParams(localLayoutParams);
      localLinearLayout2.setOrientation(1);
      this.tvSmall = new TextView(this.context);
      this.tvSmall.setTextSize(8.0F);
      if (LoadingActivity.isPad) {
        this.tvSmall.setTextSize(14.0F);
      }
      this.tvSmall.setTextColor(-1);
      this.tvSmall.getPaint().setFakeBoldText(true);
      this.tvSmall.setGravity(1);
      this.tvSmall.setText(i);
      View localView = new View(this.context);
      localView.setLayoutParams(new LinearLayout.LayoutParams(20, 1));
      localView.setBackgroundColor(-1);
      this.tvBig = new TextView(this.context);
      this.tvBig.setGravity(1);
      this.tvBig.setTextColor(-1);
      this.tvBig.getPaint().setFakeBoldText(true);
      this.danwei = new TextView(this.context);
      this.danwei.setGravity(1);
      this.danwei.setTextColor(-1);
      this.danwei.setTextSize(10.0F);
      if (LoadingActivity.isPad) {
        this.danwei.setTextSize(20.0F);
      }
      this.tvBig.setTextSize(8.0F);
      if (LoadingActivity.isPad) {
        this.tvBig.setTextSize(20.0F);
      }
      this.tvBig.setText(j);
      this.danwei.setText(getContext().getText(2131361892));
      localLinearLayout2.addView(this.tvSmall);
      localLinearLayout2.addView(localView);
      localLinearLayout2.addView(this.tvBig);
      localLinearLayout1.addView(localLinearLayout2);
      localLinearLayout1.addView(this.danwei);
      addView(localLinearLayout1);
    }
  }
  
  public void setTexBlackColor()
  {
    if (this.tvST != null) {
      this.tvST.setTextColor(this.context.getResources().getColor(2131230732));
    }
    if (this.tvSmall != null) {
      this.tvSmall.setTextColor(this.context.getResources().getColor(2131230732));
    }
    if (this.tvBig != null) {
      this.tvBig.setTextColor(this.context.getResources().getColor(2131230732));
    }
    if (this.danwei != null) {
      this.danwei.setTextColor(this.context.getResources().getColor(2131230732));
    }
  }
  
  public void setTextWhiteColor()
  {
    if (this.tvST != null) {
      this.tvST.setTextColor(this.context.getResources().getColor(2131230727));
    }
    if (this.tvSmall != null) {
      this.tvSmall.setTextColor(this.context.getResources().getColor(2131230727));
    }
    if (this.tvBig != null) {
      this.tvBig.setTextColor(this.context.getResources().getColor(2131230727));
    }
    if (this.danwei != null) {
      this.danwei.setTextColor(this.context.getResources().getColor(2131230727));
    }
  }
  
  public void setTexts(String paramString1, String paramString2)
  {
    this.tLeft = paramString1;
    this.tRight = paramString2;
    init();
  }
  
  public void setTexts(String paramString1, String paramString2, boolean paramBoolean)
  {
    this.tLeft = paramString1;
    this.tRight = paramString2;
    init(paramBoolean);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\MyTextView3.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */