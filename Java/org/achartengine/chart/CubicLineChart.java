package org.achartengine.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import java.util.List;
import org.achartengine.model.Point;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

public class CubicLineChart
  extends LineChart
{
  public static final String TYPE = "Cubic";
  private float firstMultiplier;
  private Point p1 = new Point();
  private Point p2 = new Point();
  private Point p3 = new Point();
  private float secondMultiplier;
  
  public CubicLineChart()
  {
    this.firstMultiplier = 0.33F;
    this.secondMultiplier = (1.0F - this.firstMultiplier);
  }
  
  public CubicLineChart(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, float paramFloat)
  {
    super(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    this.firstMultiplier = paramFloat;
    this.secondMultiplier = (1.0F - this.firstMultiplier);
  }
  
  private void calc(List<Float> paramList, Point paramPoint, int paramInt1, int paramInt2, float paramFloat)
  {
    float f1 = ((Float)paramList.get(paramInt1)).floatValue();
    float f2 = ((Float)paramList.get(paramInt1 + 1)).floatValue();
    float f3 = ((Float)paramList.get(paramInt2)).floatValue();
    float f4 = ((Float)paramList.get(paramInt2 + 1)).floatValue();
    float f5 = f3 - f1;
    float f6 = f4 - f2;
    paramPoint.setX(f1 + f5 * paramFloat);
    paramPoint.setY(f2 + f6 * paramFloat);
  }
  
  protected void drawPath(Canvas paramCanvas, List<Float> paramList, Paint paramPaint, boolean paramBoolean, int paramInt)
  {
    Path localPath = new Path();
    localPath.moveTo(((Float)paramList.get(0)).floatValue(), ((Float)paramList.get(1)).floatValue());
    int i = paramList.size();
    if (paramBoolean) {
      i -= 4;
    }
    int j = 0;
    if (j >= i) {
      if (!paramBoolean) {}
    }
    for (int n = i;; n += 2)
    {
      if (n >= i + 4)
      {
        localPath.lineTo(((Float)paramList.get(0)).floatValue(), ((Float)paramList.get(1)).floatValue());
        paramCanvas.drawPath(localPath, paramPaint);
        return;
        int k;
        if (j + 2 < i)
        {
          k = j + 2;
          label138:
          if (j + 4 >= i) {
            break label299;
          }
        }
        label299:
        for (int m = j + 4;; m = k)
        {
          calc(paramList, this.p1, j, k, this.secondMultiplier);
          this.p2.setX(((Float)paramList.get(k)).floatValue());
          this.p2.setY(((Float)paramList.get(k + 1)).floatValue());
          Point localPoint = this.p3;
          float f = this.firstMultiplier;
          calc(paramList, localPoint, k, m, f);
          localPath.cubicTo(this.p1.getX(), this.p1.getY(), this.p2.getX(), this.p2.getY(), this.p3.getX(), this.p3.getY());
          j += 2;
          break;
          k = j;
          break label138;
        }
      }
      localPath.lineTo(((Float)paramList.get(n)).floatValue(), ((Float)paramList.get(n + 1)).floatValue());
    }
  }
  
  public String getChartType()
  {
    return "Cubic";
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\chart\CubicLineChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */