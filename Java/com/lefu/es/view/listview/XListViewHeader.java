package com.lefu.es.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

public class XListViewHeader
  extends LinearLayout
{
  public static final int STATE_NORMAL = 0;
  public static final int STATE_READY = 1;
  public static final int STATE_REFRESHING = 2;
  private final int ROTATE_ANIM_DURATION = 180;
  private ImageView mArrowImageView;
  private LinearLayout mContainer;
  private TextView mHintTextView;
  private ProgressBar mProgressBar;
  private Animation mRotateDownAnim;
  private Animation mRotateUpAnim;
  private int mState = 0;
  
  public XListViewHeader(Context paramContext)
  {
    super(paramContext);
    initView(paramContext);
  }
  
  public XListViewHeader(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initView(paramContext);
  }
  
  private void initView(Context paramContext)
  {
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, 0);
    this.mContainer = ((LinearLayout)LayoutInflater.from(paramContext).inflate(2130903090, null));
    addView(this.mContainer, localLayoutParams);
    setGravity(80);
    this.mArrowImageView = ((ImageView)findViewById(2131165487));
    this.mHintTextView = ((TextView)findViewById(2131165485));
    this.mProgressBar = ((ProgressBar)findViewById(2131165488));
    this.mRotateUpAnim = new RotateAnimation(0.0F, -180.0F, 1, 0.5F, 1, 0.5F);
    this.mRotateUpAnim.setDuration(180L);
    this.mRotateUpAnim.setFillAfter(true);
    this.mRotateDownAnim = new RotateAnimation(-180.0F, 0.0F, 1, 0.5F, 1, 0.5F);
    this.mRotateDownAnim.setDuration(180L);
    this.mRotateDownAnim.setFillAfter(true);
  }
  
  public int getVisiableHeight()
  {
    return this.mContainer.getLayoutParams().height;
  }
  
  public void setState(int paramInt)
  {
    if (paramInt == this.mState) {
      return;
    }
    if (paramInt == 2)
    {
      this.mArrowImageView.clearAnimation();
      this.mArrowImageView.setVisibility(4);
      this.mProgressBar.setVisibility(0);
      switch (paramInt)
      {
      }
    }
    for (;;)
    {
      this.mState = paramInt;
      return;
      this.mArrowImageView.setVisibility(0);
      this.mProgressBar.setVisibility(4);
      break;
      if (this.mState == 1) {
        this.mArrowImageView.startAnimation(this.mRotateDownAnim);
      }
      if (this.mState == 2) {
        this.mArrowImageView.clearAnimation();
      }
      this.mHintTextView.setText(2131362010);
      continue;
      if (this.mState != 1)
      {
        this.mArrowImageView.clearAnimation();
        this.mArrowImageView.startAnimation(this.mRotateUpAnim);
        this.mHintTextView.setText(2131362011);
        continue;
        this.mHintTextView.setText(2131362012);
      }
    }
  }
  
  public void setVisiableHeight(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)this.mContainer.getLayoutParams();
    localLayoutParams.height = paramInt;
    this.mContainer.setLayoutParams(localLayoutParams);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\listview\XListViewHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */