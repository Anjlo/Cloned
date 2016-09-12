package com.lefu.es.progressbar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import com.lefu.iwellness.newes.system.R.styleable;

public class NumberProgressBar
  extends View
{
  private static final String INSTANCE_MAX = "max";
  private static final String INSTANCE_PREFIX = "prefix";
  private static final String INSTANCE_PROGRESS = "progress";
  private static final String INSTANCE_REACHED_BAR_COLOR = "reached_bar_color";
  private static final String INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height";
  private static final String INSTANCE_STATE = "saved_instance";
  private static final String INSTANCE_SUFFIX = "suffix";
  private static final String INSTANCE_TEXT_COLOR = "text_color";
  private static final String INSTANCE_TEXT_SIZE = "text_size";
  private static final String INSTANCE_TEXT_VISIBILITY = "text_visibility";
  private static final String INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color";
  private static final String INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height";
  private static final int PROGRESS_TEXT_VISIBLE;
  private final float default_progress_text_offset = dp2px(3.0F);
  private final float default_reached_bar_height = dp2px(1.5F);
  private final int default_reached_color = Color.rgb(66, 145, 241);
  private final int default_text_color = Color.rgb(66, 145, 241);
  private final float default_text_size = sp2px(10.0F);
  private final float default_unreached_bar_height = dp2px(1.0F);
  private final int default_unreached_color = Color.rgb(204, 204, 204);
  private String mCurrentDrawText;
  private int mCurrentProgress = 0;
  private boolean mDrawReachedBar = true;
  private float mDrawTextEnd;
  private float mDrawTextStart;
  private float mDrawTextWidth;
  private boolean mDrawUnreachedBar = true;
  private boolean mIfDrawText = true;
  private OnProgressBarListener mListener;
  private int mMaxProgress = 100;
  private float mOffset;
  private String mPrefix = "";
  private int mReachedBarColor;
  private float mReachedBarHeight;
  private Paint mReachedBarPaint;
  private RectF mReachedRectF = new RectF(0.0F, 0.0F, 0.0F, 0.0F);
  private String mSuffix = "%";
  private int mTextColor;
  private Paint mTextPaint;
  private float mTextSize;
  private int mUnreachedBarColor;
  private float mUnreachedBarHeight;
  private Paint mUnreachedBarPaint;
  private RectF mUnreachedRectF = new RectF(0.0F, 0.0F, 0.0F, 0.0F);
  
  public NumberProgressBar(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public NumberProgressBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 2130771981);
  }
  
  public NumberProgressBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.getTheme().obtainStyledAttributes(paramAttributeSet, R.styleable.NumberProgressBar, paramInt, 0);
    this.mReachedBarColor = localTypedArray.getColor(3, this.default_reached_color);
    this.mUnreachedBarColor = localTypedArray.getColor(2, this.default_unreached_color);
    this.mTextColor = localTypedArray.getColor(7, this.default_text_color);
    this.mTextSize = localTypedArray.getDimension(6, this.default_text_size);
    this.mReachedBarHeight = localTypedArray.getDimension(4, this.default_reached_bar_height);
    this.mUnreachedBarHeight = localTypedArray.getDimension(5, this.default_unreached_bar_height);
    this.mOffset = localTypedArray.getDimension(8, this.default_progress_text_offset);
    if (localTypedArray.getInt(9, 0) != 0) {
      this.mIfDrawText = false;
    }
    setProgress(localTypedArray.getInt(0, 0));
    setMax(localTypedArray.getInt(1, 100));
    localTypedArray.recycle();
    initializePainters();
  }
  
  private void calculateDrawRectF()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(100 * getProgress() / getMax());
    this.mCurrentDrawText = String.format("%d", arrayOfObject);
    this.mCurrentDrawText = (this.mPrefix + this.mCurrentDrawText + this.mSuffix);
    this.mDrawTextWidth = this.mTextPaint.measureText(this.mCurrentDrawText);
    if (getProgress() == 0) {
      this.mDrawReachedBar = false;
    }
    float f;
    for (this.mDrawTextStart = getPaddingLeft();; this.mDrawTextStart = (this.mReachedRectF.right + this.mOffset))
    {
      this.mDrawTextEnd = ((int)(getHeight() / 2.0F - (this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0F));
      if (this.mDrawTextStart + this.mDrawTextWidth >= getWidth() - getPaddingRight())
      {
        this.mDrawTextStart = (getWidth() - getPaddingRight() - this.mDrawTextWidth);
        this.mReachedRectF.right = (this.mDrawTextStart - this.mOffset);
      }
      f = this.mDrawTextStart + this.mDrawTextWidth + this.mOffset;
      if (f < getWidth() - getPaddingRight()) {
        break;
      }
      this.mDrawUnreachedBar = false;
      return;
      this.mDrawReachedBar = true;
      this.mReachedRectF.left = getPaddingLeft();
      this.mReachedRectF.top = (getHeight() / 2.0F - this.mReachedBarHeight / 2.0F);
      this.mReachedRectF.right = ((getWidth() - getPaddingLeft() - getPaddingRight()) / (1.0F * getMax()) * getProgress() - this.mOffset + getPaddingLeft());
      this.mReachedRectF.bottom = (getHeight() / 2.0F + this.mReachedBarHeight / 2.0F);
    }
    this.mDrawUnreachedBar = true;
    this.mUnreachedRectF.left = f;
    this.mUnreachedRectF.right = (getWidth() - getPaddingRight());
    this.mUnreachedRectF.top = (getHeight() / 2.0F + -this.mUnreachedBarHeight / 2.0F);
    this.mUnreachedRectF.bottom = (getHeight() / 2.0F + this.mUnreachedBarHeight / 2.0F);
  }
  
  private void calculateDrawRectFWithoutProgressText()
  {
    this.mReachedRectF.left = getPaddingLeft();
    this.mReachedRectF.top = (getHeight() / 2.0F - this.mReachedBarHeight / 2.0F);
    this.mReachedRectF.right = ((getWidth() - getPaddingLeft() - getPaddingRight()) / (1.0F * getMax()) * getProgress() + getPaddingLeft());
    this.mReachedRectF.bottom = (getHeight() / 2.0F + this.mReachedBarHeight / 2.0F);
    this.mUnreachedRectF.left = this.mReachedRectF.right;
    this.mUnreachedRectF.right = (getWidth() - getPaddingRight());
    this.mUnreachedRectF.top = (getHeight() / 2.0F + -this.mUnreachedBarHeight / 2.0F);
    this.mUnreachedRectF.bottom = (getHeight() / 2.0F + this.mUnreachedBarHeight / 2.0F);
  }
  
  private void initializePainters()
  {
    this.mReachedBarPaint = new Paint(1);
    this.mReachedBarPaint.setColor(this.mReachedBarColor);
    this.mUnreachedBarPaint = new Paint(1);
    this.mUnreachedBarPaint.setColor(this.mUnreachedBarColor);
    this.mTextPaint = new Paint(1);
    this.mTextPaint.setColor(this.mTextColor);
    this.mTextPaint.setTextSize(this.mTextSize);
  }
  
  private int measure(int paramInt, boolean paramBoolean)
  {
    int i = View.MeasureSpec.getMode(paramInt);
    int j = View.MeasureSpec.getSize(paramInt);
    if (paramBoolean) {}
    int n;
    for (int k = getPaddingLeft() + getPaddingRight(); i == 1073741824; k = getPaddingTop() + getPaddingBottom())
    {
      n = j;
      return n;
    }
    if (paramBoolean) {}
    for (int m = getSuggestedMinimumWidth();; m = getSuggestedMinimumHeight())
    {
      n = m + k;
      if (i != Integer.MIN_VALUE) {
        break;
      }
      if (!paramBoolean) {
        break label99;
      }
      return Math.max(n, j);
    }
    label99:
    return Math.min(n, j);
  }
  
  public float dp2px(float paramFloat)
  {
    return 0.5F + paramFloat * getResources().getDisplayMetrics().density;
  }
  
  public int getMax()
  {
    return this.mMaxProgress;
  }
  
  public String getPrefix()
  {
    return this.mPrefix;
  }
  
  public int getProgress()
  {
    return this.mCurrentProgress;
  }
  
  public float getProgressTextSize()
  {
    return this.mTextSize;
  }
  
  public boolean getProgressTextVisibility()
  {
    return this.mIfDrawText;
  }
  
  public int getReachedBarColor()
  {
    return this.mReachedBarColor;
  }
  
  public float getReachedBarHeight()
  {
    return this.mReachedBarHeight;
  }
  
  public String getSuffix()
  {
    return this.mSuffix;
  }
  
  protected int getSuggestedMinimumHeight()
  {
    return Math.max((int)this.mTextSize, Math.max((int)this.mReachedBarHeight, (int)this.mUnreachedBarHeight));
  }
  
  protected int getSuggestedMinimumWidth()
  {
    return (int)this.mTextSize;
  }
  
  public int getTextColor()
  {
    return this.mTextColor;
  }
  
  public int getUnreachedBarColor()
  {
    return this.mUnreachedBarColor;
  }
  
  public float getUnreachedBarHeight()
  {
    return this.mUnreachedBarHeight;
  }
  
  public void incrementProgressBy(int paramInt)
  {
    if (paramInt > 0) {
      setProgress(paramInt + getProgress());
    }
    if (this.mListener != null) {
      this.mListener.onProgressChange(getProgress(), getMax());
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mIfDrawText) {
      calculateDrawRectF();
    }
    for (;;)
    {
      if (this.mDrawReachedBar) {
        paramCanvas.drawRect(this.mReachedRectF, this.mReachedBarPaint);
      }
      if (this.mDrawUnreachedBar) {
        paramCanvas.drawRect(this.mUnreachedRectF, this.mUnreachedBarPaint);
      }
      if (this.mIfDrawText) {
        paramCanvas.drawText(this.mCurrentDrawText, this.mDrawTextStart, this.mDrawTextEnd, this.mTextPaint);
      }
      return;
      calculateDrawRectFWithoutProgressText();
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(measure(paramInt1, true), measure(paramInt2, false));
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      Bundle localBundle = (Bundle)paramParcelable;
      this.mTextColor = localBundle.getInt("text_color");
      this.mTextSize = localBundle.getFloat("text_size");
      this.mReachedBarHeight = localBundle.getFloat("reached_bar_height");
      this.mUnreachedBarHeight = localBundle.getFloat("unreached_bar_height");
      this.mReachedBarColor = localBundle.getInt("reached_bar_color");
      this.mUnreachedBarColor = localBundle.getInt("unreached_bar_color");
      initializePainters();
      setMax(localBundle.getInt("max"));
      setProgress(localBundle.getInt("progress"));
      setPrefix(localBundle.getString("prefix"));
      setSuffix(localBundle.getString("suffix"));
      if (localBundle.getBoolean("text_visibility")) {}
      for (ProgressTextVisibility localProgressTextVisibility = ProgressTextVisibility.Visible;; localProgressTextVisibility = ProgressTextVisibility.Invisible)
      {
        setProgressTextVisibility(localProgressTextVisibility);
        super.onRestoreInstanceState(localBundle.getParcelable("saved_instance"));
        return;
      }
    }
    super.onRestoreInstanceState(paramParcelable);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("saved_instance", super.onSaveInstanceState());
    localBundle.putInt("text_color", getTextColor());
    localBundle.putFloat("text_size", getProgressTextSize());
    localBundle.putFloat("reached_bar_height", getReachedBarHeight());
    localBundle.putFloat("unreached_bar_height", getUnreachedBarHeight());
    localBundle.putInt("reached_bar_color", getReachedBarColor());
    localBundle.putInt("unreached_bar_color", getUnreachedBarColor());
    localBundle.putInt("max", getMax());
    localBundle.putInt("progress", getProgress());
    localBundle.putString("suffix", getSuffix());
    localBundle.putString("prefix", getPrefix());
    localBundle.putBoolean("text_visibility", getProgressTextVisibility());
    return localBundle;
  }
  
  public void setMax(int paramInt)
  {
    if (paramInt > 0)
    {
      this.mMaxProgress = paramInt;
      invalidate();
    }
  }
  
  public void setOnProgressBarListener(OnProgressBarListener paramOnProgressBarListener)
  {
    this.mListener = paramOnProgressBarListener;
  }
  
  public void setPrefix(String paramString)
  {
    if (paramString == null)
    {
      this.mPrefix = "";
      return;
    }
    this.mPrefix = paramString;
  }
  
  public void setProgress(int paramInt)
  {
    if ((paramInt <= getMax()) && (paramInt >= 0))
    {
      this.mCurrentProgress = paramInt;
      invalidate();
    }
  }
  
  public void setProgressTextColor(int paramInt)
  {
    this.mTextColor = paramInt;
    this.mTextPaint.setColor(this.mTextColor);
    invalidate();
  }
  
  public void setProgressTextSize(float paramFloat)
  {
    this.mTextSize = paramFloat;
    this.mTextPaint.setTextSize(this.mTextSize);
    invalidate();
  }
  
  public void setProgressTextVisibility(ProgressTextVisibility paramProgressTextVisibility)
  {
    if (paramProgressTextVisibility == ProgressTextVisibility.Visible) {}
    for (boolean bool = true;; bool = false)
    {
      this.mIfDrawText = bool;
      invalidate();
      return;
    }
  }
  
  public void setReachedBarColor(int paramInt)
  {
    this.mReachedBarColor = paramInt;
    this.mReachedBarPaint.setColor(this.mReachedBarColor);
    invalidate();
  }
  
  public void setReachedBarHeight(float paramFloat)
  {
    this.mReachedBarHeight = paramFloat;
  }
  
  public void setSuffix(String paramString)
  {
    if (paramString == null)
    {
      this.mSuffix = "";
      return;
    }
    this.mSuffix = paramString;
  }
  
  public void setUnreachedBarColor(int paramInt)
  {
    this.mUnreachedBarColor = paramInt;
    this.mUnreachedBarPaint.setColor(this.mReachedBarColor);
    invalidate();
  }
  
  public void setUnreachedBarHeight(float paramFloat)
  {
    this.mUnreachedBarHeight = paramFloat;
  }
  
  public float sp2px(float paramFloat)
  {
    return paramFloat * getResources().getDisplayMetrics().scaledDensity;
  }
  
  public static enum ProgressTextVisibility
  {
    Invisible,  Visible;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\progressbar\NumberProgressBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */