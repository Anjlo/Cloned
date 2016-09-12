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

public class MyTextView2
  extends LinearLayout
{
  private Context context;
  private String tLeft;
  private String tRight;
  TextView tvST;
  
  public MyTextView2(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
  }
  
  public MyTextView2(Context paramContext, AttributeSet paramAttributeSet)
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
    this.tvST.setTextColor(-12303292);
    this.tvST.setTextSize(12.0F);
    this.tvST.setSingleLine(true);
    if ("0.0".equals(this.tLeft)) {
      this.tLeft = ("0.0 " + getContext().getText(2131361892).toString());
    }
    this.tvST.setText(this.tLeft);
    if ((this.tLeft != null) && (this.tLeft.equals(getContext().getString(2131361844)))) {
      this.tvST.setTextSize(12.0F);
    }
    if (LoadingActivity.isPad) {
      this.tvST.setTextSize(20.0F);
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
      TextView localTextView1 = new TextView(this.context);
      localTextView1.setTextSize(7.0F);
      if (LoadingActivity.isPad) {
        localTextView1.setTextSize(12.0F);
      }
      localTextView1.setTextColor(-12303292);
      localTextView1.getPaint().setFakeBoldText(true);
      localTextView1.setGravity(1);
      localTextView1.setText(i);
      View localView = new View(this.context);
      localView.setLayoutParams(new LinearLayout.LayoutParams(20, 1));
      localView.setBackgroundColor(-12303292);
      TextView localTextView2 = new TextView(this.context);
      localTextView2.setGravity(1);
      localTextView2.setTextColor(-12303292);
      localTextView2.getPaint().setFakeBoldText(true);
      localTextView2.setTextSize(7.0F);
      if (LoadingActivity.isPad) {
        localTextView2.setTextSize(12.0F);
      }
      localTextView2.setText(j + " ");
      TextView localTextView3 = new TextView(this.context);
      localTextView3.setGravity(16);
      localTextView3.setTextColor(-12303292);
      localTextView3.setTextSize(9.0F);
      if (LoadingActivity.isPad) {
        localTextView3.setTextSize(20.0F);
      }
      localTextView3.setText(" " + getContext().getText(2131361892).toString());
      localLinearLayout2.addView(localTextView1);
      localLinearLayout2.addView(localView);
      localLinearLayout2.addView(localTextView2);
      localLinearLayout1.addView(localLinearLayout2);
      localLinearLayout1.addView(localTextView3);
      addView(localLinearLayout1);
    }
  }
  
  public void init(String paramString)
  {
    int i = 1;
    removeAllViewsInLayout();
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    setOrientation(0);
    setGravity(16);
    this.tvST = new TextView(this.context);
    this.tvST.setLayoutParams(localLayoutParams);
    this.tvST.setTextColor(-12303292);
    this.tvST.setTextSize(12.0F);
    this.tvST.setSingleLine(true);
    if ((paramString == null) || ("".equals(paramString))) {
      i = 0;
    }
    if ("0.0".equals(this.tLeft))
    {
      this.tLeft = ("0.0 " + getContext().getText(2131361892).toString());
      i = 0;
    }
    if ((this.tLeft != null) && (this.tLeft.equals(getContext().getString(2131361844))))
    {
      i = 0;
      this.tvST.setTextSize(12.0F);
    }
    if (LoadingActivity.isPad) {
      this.tvST.setTextSize(20.0F);
    }
    addView(this.tvST);
    if ((this.tRight != null) && (this.tRight.indexOf("/") > 0))
    {
      i = 0;
      String[] arrayOfString = this.tRight.split("/");
      int j = Integer.parseInt(arrayOfString[0]);
      int k = Integer.parseInt(arrayOfString[1]);
      LinearLayout localLinearLayout1 = new LinearLayout(this.context);
      localLinearLayout1.setOrientation(0);
      LinearLayout localLinearLayout2 = new LinearLayout(this.context);
      localLayoutParams.leftMargin = 20;
      localLinearLayout2.setLayoutParams(localLayoutParams);
      localLinearLayout2.setOrientation(1);
      TextView localTextView1 = new TextView(this.context);
      localTextView1.setTextSize(7.0F);
      if (LoadingActivity.isPad) {
        localTextView1.setTextSize(12.0F);
      }
      localTextView1.setTextColor(-12303292);
      localTextView1.getPaint().setFakeBoldText(true);
      localTextView1.setGravity(1);
      localTextView1.setText(j);
      View localView = new View(this.context);
      localView.setLayoutParams(new LinearLayout.LayoutParams(20, 1));
      localView.setBackgroundColor(-1);
      TextView localTextView2 = new TextView(this.context);
      localTextView2.setGravity(1);
      localTextView2.setTextColor(-12303292);
      localTextView2.getPaint().setFakeBoldText(true);
      localTextView2.setTextSize(7.0F);
      if (LoadingActivity.isPad) {
        localTextView2.setTextSize(12.0F);
      }
      localTextView2.setText(k + " ");
      TextView localTextView3 = new TextView(this.context);
      localTextView3.setGravity(16);
      localTextView3.setTextColor(-12303292);
      localTextView3.setTextSize(9.0F);
      if (LoadingActivity.isPad) {
        localTextView3.setTextSize(20.0F);
      }
      localTextView3.setText(" " + getContext().getText(2131361892).toString());
      localLinearLayout2.addView(localTextView1);
      localLinearLayout2.addView(localView);
      localLinearLayout2.addView(localTextView2);
      localLinearLayout1.addView(localLinearLayout2);
      localLinearLayout1.addView(localTextView3);
      addView(localLinearLayout1);
    }
    if (i != 0)
    {
      this.tvST.setText(this.tLeft + getContext().getText(2131361892));
      return;
    }
    this.tvST.setText(this.tLeft);
  }
  
  public void setTexts(String paramString1, String paramString2)
  {
    this.tLeft = paramString1;
    this.tRight = paramString2;
    init();
  }
  
  public void setTexts(String paramString1, String paramString2, String paramString3)
  {
    this.tLeft = paramString1;
    this.tRight = paramString2;
    init(paramString3);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\MyTextView2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */