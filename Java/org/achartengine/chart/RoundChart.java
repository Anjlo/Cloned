package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public abstract class RoundChart
  extends AbstractChart
{
  protected static final int NO_VALUE = Integer.MAX_VALUE;
  protected static final int SHAPE_WIDTH = 10;
  protected int mCenterX = Integer.MAX_VALUE;
  protected int mCenterY = Integer.MAX_VALUE;
  protected CategorySeries mDataset;
  protected DefaultRenderer mRenderer;
  
  public RoundChart(CategorySeries paramCategorySeries, DefaultRenderer paramDefaultRenderer)
  {
    this.mDataset = paramCategorySeries;
    this.mRenderer = paramDefaultRenderer;
  }
  
  public void drawLegendShape(Canvas paramCanvas, SimpleSeriesRenderer paramSimpleSeriesRenderer, float paramFloat1, float paramFloat2, int paramInt, Paint paramPaint)
  {
    paramCanvas.drawRect(paramFloat1, paramFloat2 - 5.0F, paramFloat1 + 10.0F, paramFloat2 + 5.0F, paramPaint);
  }
  
  public void drawTitle(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, Paint paramPaint)
  {
    if (this.mRenderer.isShowLabels())
    {
      paramPaint.setColor(this.mRenderer.getLabelsColor());
      paramPaint.setTextAlign(Paint.Align.CENTER);
      paramPaint.setTextSize(this.mRenderer.getChartTitleTextSize());
      drawString(paramCanvas, this.mRenderer.getChartTitle(), paramInt1 + paramInt3 / 2, paramInt2 + this.mRenderer.getChartTitleTextSize(), paramPaint);
    }
  }
  
  public int getCenterX()
  {
    return this.mCenterX;
  }
  
  public int getCenterY()
  {
    return this.mCenterY;
  }
  
  public int getLegendShapeWidth(int paramInt)
  {
    return 10;
  }
  
  public DefaultRenderer getRenderer()
  {
    return this.mRenderer;
  }
  
  public void setCenterX(int paramInt)
  {
    this.mCenterX = paramInt;
  }
  
  public void setCenterY(int paramInt)
  {
    this.mCenterY = paramInt;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\chart\RoundChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */