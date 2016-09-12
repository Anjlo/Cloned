package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.DialRenderer.Type;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class DialChart
  extends RoundChart
{
  private static final int NEEDLE_RADIUS = 10;
  private DialRenderer mRenderer;
  
  public DialChart(CategorySeries paramCategorySeries, DialRenderer paramDialRenderer)
  {
    super(paramCategorySeries, paramDialRenderer);
    this.mRenderer = paramDialRenderer;
  }
  
  private void drawNeedle(Canvas paramCanvas, double paramDouble1, int paramInt1, int paramInt2, double paramDouble2, boolean paramBoolean, Paint paramPaint)
  {
    double d = Math.toRadians(90.0D);
    int i = (int)(10.0D * Math.sin(paramDouble1 - d));
    int j = (int)(10.0D * Math.cos(paramDouble1 - d));
    int k = (int)(paramDouble2 * Math.sin(paramDouble1));
    int m = (int)(paramDouble2 * Math.cos(paramDouble1));
    int n = paramInt1 + k;
    int i1 = paramInt2 + m;
    float[] arrayOfFloat;
    if (paramBoolean)
    {
      int i2 = paramInt1 + (int)(0.85D * paramDouble2 * Math.sin(paramDouble1));
      int i3 = paramInt2 + (int)(0.85D * paramDouble2 * Math.cos(paramDouble1));
      arrayOfFloat = new float[6];
      arrayOfFloat[0] = (i2 - i);
      arrayOfFloat[1] = (i3 - j);
      arrayOfFloat[2] = n;
      arrayOfFloat[3] = i1;
      arrayOfFloat[4] = (i2 + i);
      arrayOfFloat[5] = (i3 + j);
      float f = paramPaint.getStrokeWidth();
      paramPaint.setStrokeWidth(5.0F);
      paramCanvas.drawLine(paramInt1, paramInt2, n, i1, paramPaint);
      paramPaint.setStrokeWidth(f);
    }
    for (;;)
    {
      drawPath(paramCanvas, arrayOfFloat, paramPaint, true);
      return;
      arrayOfFloat = new float[6];
      arrayOfFloat[0] = (paramInt1 - i);
      arrayOfFloat[1] = (paramInt2 - j);
      arrayOfFloat[2] = n;
      arrayOfFloat[3] = i1;
      arrayOfFloat[4] = (paramInt1 + i);
      arrayOfFloat[5] = (paramInt2 + j);
    }
  }
  
  private void drawTicks(Canvas paramCanvas, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt1, int paramInt2, double paramDouble5, double paramDouble6, double paramDouble7, Paint paramPaint, boolean paramBoolean)
  {
    for (double d1 = paramDouble1;; d1 += paramDouble7)
    {
      if (d1 > paramDouble2) {
        return;
      }
      double d2 = getAngleForValue(d1, paramDouble3, paramDouble4, paramDouble1, paramDouble2);
      double d3 = Math.sin(d2);
      double d4 = Math.cos(d2);
      int i = Math.round(paramInt1 + (float)(paramDouble6 * d3));
      int j = Math.round(paramInt2 + (float)(paramDouble6 * d4));
      int k = Math.round(paramInt1 + (float)(paramDouble5 * d3));
      int m = Math.round(paramInt2 + (float)(paramDouble5 * d4));
      paramCanvas.drawLine(i, j, k, m, paramPaint);
      if (paramBoolean)
      {
        paramPaint.setTextAlign(Paint.Align.LEFT);
        if (i <= k) {
          paramPaint.setTextAlign(Paint.Align.RIGHT);
        }
        String str = d1;
        if (Math.round(d1) == d1) {
          str = d1;
        }
        float f1 = i;
        float f2 = j;
        paramCanvas.drawText(str, f1, f2, paramPaint);
      }
    }
  }
  
  private double getAngleForValue(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5)
  {
    double d1 = paramDouble3 - paramDouble2;
    double d2 = paramDouble5 - paramDouble4;
    return Math.toRadians(paramDouble2 + d1 * (paramDouble1 - paramDouble4) / d2);
  }
  
  public void draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Paint paramPaint)
  {
    paramPaint.setAntiAlias(this.mRenderer.isAntialiasing());
    paramPaint.setStyle(Paint.Style.FILL);
    paramPaint.setTextSize(this.mRenderer.getLabelsTextSize());
    int i = getLegendSize(this.mRenderer, paramInt4 / 5, 0.0F);
    int j = paramInt1 + paramInt3;
    int k = this.mDataset.getItemCount();
    String[] arrayOfString = new String[k];
    int m = 0;
    int i1;
    float f1;
    float f2;
    double d1;
    double d2;
    double d3;
    double d4;
    int i2;
    if (m >= k)
    {
      if (this.mRenderer.isFitLegend()) {
        i = drawLegend(paramCanvas, this.mRenderer, arrayOfString, paramInt1, j, paramInt2, paramInt3, paramInt4, i, paramPaint, true);
      }
      int n = paramInt2 + paramInt4 - i;
      drawBackground(this.mRenderer, paramCanvas, paramInt1, paramInt2, paramInt3, paramInt4, paramPaint, false, 0);
      i1 = (int)(0.35D * Math.min(Math.abs(j - paramInt1), Math.abs(n - paramInt2)) * this.mRenderer.getScale());
      if (this.mCenterX == Integer.MAX_VALUE) {
        this.mCenterX = ((paramInt1 + j) / 2);
      }
      if (this.mCenterY == Integer.MAX_VALUE) {
        this.mCenterY = ((n + paramInt2) / 2);
      }
      f1 = 0.9F * i1;
      f2 = 1.1F * i1;
      d1 = this.mRenderer.getMinValue();
      d2 = this.mRenderer.getMaxValue();
      d3 = this.mRenderer.getAngleMin();
      d4 = this.mRenderer.getAngleMax();
      if ((!this.mRenderer.isMinValueSet()) || (!this.mRenderer.isMaxValueSet())) {
        i2 = this.mRenderer.getSeriesRendererCount();
      }
    }
    int i7;
    for (int i3 = 0;; i3++)
    {
      if (i3 >= i2)
      {
        if (d1 == d2)
        {
          d1 *= 0.5D;
          d2 *= 1.5D;
        }
        paramPaint.setColor(this.mRenderer.getLabelsColor());
        double d6 = this.mRenderer.getMinorTicksSpacing();
        double d7 = this.mRenderer.getMajorTicksSpacing();
        if (d6 == Double.MAX_VALUE) {
          d6 = (d2 - d1) / 30.0D;
        }
        if (d7 == Double.MAX_VALUE) {
          d7 = (d2 - d1) / 10.0D;
        }
        drawTicks(paramCanvas, d1, d2, d3, d4, this.mCenterX, this.mCenterY, f2, i1, d6, paramPaint, false);
        int i4 = this.mCenterX;
        int i5 = this.mCenterY;
        double d8 = f2;
        double d9 = f1;
        drawTicks(paramCanvas, d1, d2, d3, d4, i4, i5, d8, d9, d7, paramPaint, true);
        int i6 = this.mRenderer.getSeriesRendererCount();
        i7 = 0;
        if (i7 < i6) {
          break label611;
        }
        drawLegend(paramCanvas, this.mRenderer, arrayOfString, paramInt1, j, paramInt2, paramInt3, paramInt4, i, paramPaint, false);
        drawTitle(paramCanvas, paramInt1, paramInt2, paramInt3, paramPaint);
        return;
        arrayOfString[m] = this.mDataset.getCategory(m);
        m++;
        break;
      }
      double d5 = this.mDataset.getValue(i3);
      if (!this.mRenderer.isMinValueSet()) {
        d1 = Math.min(d1, d5);
      }
      if (!this.mRenderer.isMaxValueSet()) {
        d2 = Math.max(d2, d5);
      }
    }
    label611:
    double d10 = getAngleForValue(this.mDataset.getValue(i7), d3, d4, d1, d2);
    paramPaint.setColor(this.mRenderer.getSeriesRendererAt(i7).getColor());
    if (this.mRenderer.getVisualTypeForIndex(i7) == DialRenderer.Type.ARROW) {}
    for (boolean bool = true;; bool = false)
    {
      drawNeedle(paramCanvas, d10, this.mCenterX, this.mCenterY, f1, bool, paramPaint);
      i7++;
      break;
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\chart\DialChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */