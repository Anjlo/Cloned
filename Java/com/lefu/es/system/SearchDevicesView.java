package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SearchDevicesView
  extends BaseView
{
  public static final boolean D = true;
  public static final String TAG = "SearchDevicesView";
  private long TIME_DIFF = 1500L;
  int[] argColor = { 243, 243, 250 };
  private Bitmap bitmap;
  private Bitmap bitmap1;
  private Bitmap bitmap2;
  int[] innerCircle0 = { 185, 255, 255 };
  int[] innerCircle1 = { 223, 255, 255 };
  int[] innerCircle2 = { 236, 255, 255 };
  private boolean isSearching = false;
  int[] lineColor = { 123, 123, 123 };
  private float offsetArgs = 0.0F;
  
  public SearchDevicesView(Context paramContext)
  {
    super(paramContext);
    initBitmap();
  }
  
  public SearchDevicesView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initBitmap();
  }
  
  public SearchDevicesView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initBitmap();
  }
  
  private void initBitmap()
  {
    if (this.bitmap == null) {
      this.bitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(this.context.getResources(), 2130837581));
    }
    if (this.bitmap1 == null) {
      this.bitmap1 = Bitmap.createBitmap(BitmapFactory.decodeResource(this.context.getResources(), 2130837609));
    }
    if (this.bitmap2 == null) {
      this.bitmap2 = Bitmap.createBitmap(BitmapFactory.decodeResource(this.context.getResources(), 2130837580));
    }
  }
  
  public boolean isSearching()
  {
    return this.isSearching;
  }
  
  @SuppressLint({"DrawAllocation"})
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    paramCanvas.drawBitmap(this.bitmap, getWidth() / 2 - this.bitmap.getWidth() / 2, getHeight() / 2 - this.bitmap.getHeight() / 2, null);
    if (this.isSearching)
    {
      Rect localRect = new Rect(getWidth() / 2 - this.bitmap2.getWidth(), getHeight() / 2, getWidth() / 2, getHeight() / 2 + this.bitmap2.getHeight());
      paramCanvas.rotate(this.offsetArgs, getWidth() / 2, getHeight() / 2);
      paramCanvas.drawBitmap(this.bitmap2, null, localRect, null);
      this.offsetArgs = (3.0F + this.offsetArgs);
    }
    for (;;)
    {
      paramCanvas.drawBitmap(this.bitmap1, getWidth() / 2 - this.bitmap1.getWidth() / 2, getHeight() / 2 - this.bitmap1.getHeight() / 2, null);
      if (this.isSearching) {
        invalidate();
      }
      return;
      paramCanvas.drawBitmap(this.bitmap2, getWidth() / 2 - this.bitmap2.getWidth(), getHeight() / 2, null);
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void setSearching(boolean paramBoolean)
  {
    this.isSearching = paramBoolean;
    this.offsetArgs = 0.0F;
    invalidate();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\SearchDevicesView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */