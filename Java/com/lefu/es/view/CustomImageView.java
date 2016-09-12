package com.lefu.es.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import com.lefu.iwellness.newes.system.R.styleable;

public class CustomImageView
  extends View
{
  private static final int TYPE_CIRCLE = 0;
  private static final int TYPE_ROUND = 1;
  private int mHeight;
  private int mRadius;
  private Bitmap mSrc;
  private int mWidth;
  private int type;
  
  public CustomImageView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CustomImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public CustomImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.getTheme().obtainStyledAttributes(paramAttributeSet, R.styleable.CustomImageView, paramInt, 0);
    int i = localTypedArray.getIndexCount();
    int j = 0;
    if (j >= i)
    {
      localTypedArray.recycle();
      return;
    }
    int k = localTypedArray.getIndex(j);
    switch (k)
    {
    }
    for (;;)
    {
      j++;
      break;
      this.mSrc = BitmapFactory.decodeResource(getResources(), localTypedArray.getResourceId(k, 0));
      continue;
      this.type = localTypedArray.getInt(k, 0);
      continue;
      this.type = localTypedArray.getDimensionPixelSize(k, (int)TypedValue.applyDimension(1, 10.0F, getResources().getDisplayMetrics()));
    }
  }
  
  private Bitmap createCircleImage(Bitmap paramBitmap, int paramInt)
  {
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    Bitmap localBitmap = Bitmap.createBitmap(paramInt, paramInt, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    localCanvas.drawCircle(paramInt / 2, paramInt / 2, paramInt / 2, localPaint);
    localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint);
    return localBitmap;
  }
  
  private Bitmap createRoundConerImage(Bitmap paramBitmap)
  {
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    Bitmap localBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    localCanvas.drawRoundRect(new RectF(0.0F, 0.0F, paramBitmap.getWidth(), paramBitmap.getHeight()), 50.0F, 50.0F, localPaint);
    localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint);
    return localBitmap;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    switch (this.type)
    {
    default: 
      return;
    case 0: 
      int i = Math.min(this.mWidth, this.mHeight);
      this.mSrc = Bitmap.createScaledBitmap(this.mSrc, i, i, false);
      paramCanvas.drawBitmap(createCircleImage(this.mSrc, i), 0.0F, 0.0F, null);
      return;
    }
    paramCanvas.drawBitmap(createRoundConerImage(this.mSrc), 0.0F, 0.0F, null);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt1);
    int m;
    int n;
    if (i == 1073741824)
    {
      this.mWidth = j;
      m = View.MeasureSpec.getMode(paramInt2);
      n = View.MeasureSpec.getSize(paramInt2);
      if (m != 1073741824) {
        break label100;
      }
      this.mHeight = n;
    }
    for (;;)
    {
      setMeasuredDimension(this.mWidth, this.mHeight);
      return;
      int k = getPaddingLeft() + getPaddingRight() + this.mSrc.getWidth();
      if (i != Integer.MIN_VALUE) {
        break;
      }
      this.mWidth = Math.min(k, j);
      break;
      label100:
      int i1 = getPaddingTop() + getPaddingBottom() + this.mSrc.getHeight();
      if (m == Integer.MIN_VALUE) {
        this.mHeight = Math.min(i1, n);
      }
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\CustomImageView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */