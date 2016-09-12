package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import java.util.ArrayList;
import java.util.List;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.Point;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class PieChart
  extends RoundChart
{
  private PieMapper mPieMapper = new PieMapper();
  
  public PieChart(CategorySeries paramCategorySeries, DefaultRenderer paramDefaultRenderer)
  {
    super(paramCategorySeries, paramDefaultRenderer);
  }
  
  public void draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Paint paramPaint)
  {
    paramPaint.setAntiAlias(this.mRenderer.isAntialiasing());
    paramPaint.setStyle(Paint.Style.FILL);
    paramPaint.setTextSize(this.mRenderer.getLabelsTextSize());
    int i = getLegendSize(this.mRenderer, paramInt4 / 5, 0.0F);
    int j = paramInt1 + paramInt3;
    int k = this.mDataset.getItemCount();
    double d1 = 0.0D;
    String[] arrayOfString = new String[k];
    int m = 0;
    float f1;
    int i1;
    if (m >= k)
    {
      if (this.mRenderer.isFitLegend()) {
        i = drawLegend(paramCanvas, this.mRenderer, arrayOfString, paramInt1, j, paramInt2, paramInt3, paramInt4, i, paramPaint, true);
      }
      int n = paramInt2 + paramInt4 - i;
      drawBackground(this.mRenderer, paramCanvas, paramInt1, paramInt2, paramInt3, paramInt4, paramPaint, false, 0);
      f1 = this.mRenderer.getStartAngle();
      i1 = (int)(0.35D * Math.min(Math.abs(j - paramInt1), Math.abs(n - paramInt2)) * this.mRenderer.getScale());
      if (this.mCenterX == Integer.MAX_VALUE) {
        this.mCenterX = ((paramInt1 + j) / 2);
      }
      if (this.mCenterY == Integer.MAX_VALUE) {
        this.mCenterY = ((n + paramInt2) / 2);
      }
      this.mPieMapper.setDimensions(i1, this.mCenterX, this.mCenterY);
      if (!this.mPieMapper.areAllSegmentPresent(k)) {
        break label425;
      }
    }
    float f2;
    float f3;
    RectF localRectF;
    ArrayList localArrayList;
    int i3;
    label425:
    for (int i2 = 0;; i2 = 1)
    {
      if (i2 != 0) {
        this.mPieMapper.clearPieSegments();
      }
      f2 = 0.9F * i1;
      f3 = 1.1F * i1;
      localRectF = new RectF(this.mCenterX - i1, this.mCenterY - i1, i1 + this.mCenterX, i1 + this.mCenterY);
      localArrayList = new ArrayList();
      i3 = 0;
      if (i3 < k) {
        break label431;
      }
      localArrayList.clear();
      drawLegend(paramCanvas, this.mRenderer, arrayOfString, paramInt1, j, paramInt2, paramInt3, paramInt4, i, paramPaint, false);
      drawTitle(paramCanvas, paramInt1, paramInt2, paramInt3, paramPaint);
      return;
      d1 += this.mDataset.getValue(m);
      arrayOfString[m] = this.mDataset.getCategory(m);
      m++;
      break;
    }
    label431:
    SimpleSeriesRenderer localSimpleSeriesRenderer = this.mRenderer.getSeriesRendererAt(i3);
    label488:
    float f4;
    float f5;
    if (localSimpleSeriesRenderer.isGradientEnabled())
    {
      paramPaint.setShader(new RadialGradient(this.mCenterX, this.mCenterY, f3, localSimpleSeriesRenderer.getGradientStartColor(), localSimpleSeriesRenderer.getGradientStopColor(), Shader.TileMode.MIRROR));
      f4 = (float)this.mDataset.getValue(i3);
      f5 = (float)(360.0D * (f4 / d1));
      if (!localSimpleSeriesRenderer.isHighlighted()) {
        break label823;
      }
      double d2 = Math.toRadians(90.0F - (f1 + f5 / 2.0F));
      float f8 = (float)(0.1D * i1 * Math.sin(d2));
      float f9 = (float)(0.1D * i1 * Math.cos(d2));
      localRectF.offset(f8, f9);
      paramCanvas.drawArc(localRectF, f1, f5, true, paramPaint);
      localRectF.offset(-f8, -f9);
    }
    for (;;)
    {
      paramPaint.setColor(localSimpleSeriesRenderer.getColor());
      paramPaint.setShader(null);
      drawLabel(paramCanvas, this.mDataset.getCategory(i3), this.mRenderer, localArrayList, this.mCenterX, this.mCenterY, f2, f3, f1, f5, paramInt1, j, this.mRenderer.getLabelsColor(), paramPaint, true, false);
      if (this.mRenderer.isDisplayValues())
      {
        String str = getLabel(this.mRenderer.getSeriesRendererAt(i3).getChartValuesFormat(), this.mDataset.getValue(i3));
        DefaultRenderer localDefaultRenderer = this.mRenderer;
        int i4 = this.mCenterX;
        int i5 = this.mCenterY;
        float f6 = f2 / 2.0F;
        float f7 = f3 / 2.0F;
        int i6 = this.mRenderer.getLabelsColor();
        drawLabel(paramCanvas, str, localDefaultRenderer, localArrayList, i4, i5, f6, f7, f1, f5, paramInt1, j, i6, paramPaint, false, true);
      }
      if (i2 != 0) {
        this.mPieMapper.addPieSegment(i3, f4, f1, f5);
      }
      f1 += f5;
      i3++;
      break;
      paramPaint.setColor(localSimpleSeriesRenderer.getColor());
      break label488;
      label823:
      paramCanvas.drawArc(localRectF, f1, f5, true, paramPaint);
    }
  }
  
  public SeriesSelection getSeriesAndPointForScreenCoordinate(Point paramPoint)
  {
    return this.mPieMapper.getSeriesAndPointForScreenCoordinate(paramPoint);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\chart\PieChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */