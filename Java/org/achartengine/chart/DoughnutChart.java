package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class DoughnutChart
  extends RoundChart
{
  private MultipleCategorySeries mDataset;
  private int mStep;
  
  public DoughnutChart(MultipleCategorySeries paramMultipleCategorySeries, DefaultRenderer paramDefaultRenderer)
  {
    super(null, paramDefaultRenderer);
    this.mDataset = paramMultipleCategorySeries;
  }
  
  public void draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Paint paramPaint)
  {
    paramPaint.setAntiAlias(this.mRenderer.isAntialiasing());
    paramPaint.setStyle(Paint.Style.FILL);
    paramPaint.setTextSize(this.mRenderer.getLabelsTextSize());
    int i = getLegendSize(this.mRenderer, paramInt4 / 5, 0.0F);
    int j = paramInt1 + paramInt3;
    int k = this.mDataset.getCategoriesCount();
    String[] arrayOfString1 = new String[k];
    int i1;
    double d2;
    int i2;
    float f1;
    float f2;
    ArrayList localArrayList;
    int i3;
    for (int m = 0;; m++)
    {
      if (m >= k)
      {
        if (this.mRenderer.isFitLegend()) {
          i = drawLegend(paramCanvas, this.mRenderer, arrayOfString1, paramInt1, j, paramInt2, paramInt3, paramInt4, i, paramPaint, true);
        }
        int n = paramInt2 + paramInt4 - i;
        drawBackground(this.mRenderer, paramCanvas, paramInt1, paramInt2, paramInt3, paramInt4, paramPaint, false, 0);
        this.mStep = 7;
        i1 = Math.min(Math.abs(j - paramInt1), Math.abs(n - paramInt2));
        double d1 = 0.35D * this.mRenderer.getScale();
        d2 = 0.2D / k;
        i2 = (int)(d1 * i1);
        if (this.mCenterX == Integer.MAX_VALUE) {
          this.mCenterX = ((paramInt1 + j) / 2);
        }
        if (this.mCenterY == Integer.MAX_VALUE) {
          this.mCenterY = ((n + paramInt2) / 2);
        }
        f1 = 0.9F * i2;
        f2 = 1.1F * i2;
        localArrayList = new ArrayList();
        i3 = 0;
        if (i3 < k) {
          break;
        }
        localArrayList.clear();
        drawLegend(paramCanvas, this.mRenderer, arrayOfString1, paramInt1, j, paramInt2, paramInt3, paramInt4, i, paramPaint, false);
        drawTitle(paramCanvas, paramInt1, paramInt2, paramInt3, paramPaint);
        return;
      }
      arrayOfString1[m] = this.mDataset.getCategory(m);
    }
    int i4 = this.mDataset.getItemCount(i3);
    double d3 = 0.0D;
    String[] arrayOfString2 = new String[i4];
    int i5 = 0;
    label361:
    float f3;
    RectF localRectF;
    int i6;
    label421:
    int i10;
    if (i5 >= i4)
    {
      f3 = this.mRenderer.getStartAngle();
      localRectF = new RectF(this.mCenterX - i2, this.mCenterY - i2, i2 + this.mCenterX, i2 + this.mCenterY);
      i6 = 0;
      if (i6 < i4) {
        break label589;
      }
      i10 = (int)(i2 - d2 * i1);
      f1 = (float)(f1 - (d2 * i1 - 2.0D));
      if (this.mRenderer.getBackgroundColor() == 0) {
        break label729;
      }
      paramPaint.setColor(this.mRenderer.getBackgroundColor());
    }
    for (;;)
    {
      paramPaint.setStyle(Paint.Style.FILL);
      paramCanvas.drawArc(new RectF(this.mCenterX - i10, this.mCenterY - i10, i10 + this.mCenterX, i10 + this.mCenterY), 0.0F, 360.0F, true, paramPaint);
      i2 = i10 - 1;
      i3++;
      break;
      d3 += this.mDataset.getValues(i3)[i5];
      arrayOfString2[i5] = this.mDataset.getTitles(i3)[i5];
      i5++;
      break label361;
      label589:
      paramPaint.setColor(this.mRenderer.getSeriesRendererAt(i6).getColor());
      float f4 = (float)(360.0D * ((float)this.mDataset.getValues(i3)[i6] / d3));
      paramCanvas.drawArc(localRectF, f3, f4, true, paramPaint);
      String str = this.mDataset.getTitles(i3)[i6];
      DefaultRenderer localDefaultRenderer = this.mRenderer;
      int i7 = this.mCenterX;
      int i8 = this.mCenterY;
      int i9 = this.mRenderer.getLabelsColor();
      drawLabel(paramCanvas, str, localDefaultRenderer, localArrayList, i7, i8, f1, f2, f3, f4, paramInt1, j, i9, paramPaint, true, false);
      f3 += f4;
      i6++;
      break label421;
      label729:
      paramPaint.setColor(-1);
    }
  }
  
  public void drawLegendShape(Canvas paramCanvas, SimpleSeriesRenderer paramSimpleSeriesRenderer, float paramFloat1, float paramFloat2, int paramInt, Paint paramPaint)
  {
    this.mStep = (-1 + this.mStep);
    paramCanvas.drawCircle(10.0F + paramFloat1 - this.mStep, paramFloat2, this.mStep, paramPaint);
  }
  
  public int getLegendShapeWidth(int paramInt)
  {
    return 10;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\chart\DoughnutChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */