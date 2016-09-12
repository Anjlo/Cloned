package org.achartengine.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.achartengine.model.Point;
import org.achartengine.model.SeriesSelection;

public class PieMapper
  implements Serializable
{
  private int mCenterX;
  private int mCenterY;
  private int mPieChartRadius;
  private List<PieSegment> mPieSegmentList = new ArrayList();
  
  public void addPieSegment(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.mPieSegmentList.add(new PieSegment(paramInt, paramFloat1, paramFloat2, paramFloat3));
  }
  
  public boolean areAllSegmentPresent(int paramInt)
  {
    return this.mPieSegmentList.size() == paramInt;
  }
  
  public void clearPieSegments()
  {
    this.mPieSegmentList.clear();
  }
  
  public double getAngle(Point paramPoint)
  {
    double d1 = paramPoint.getX() - this.mCenterX;
    double d2 = Math.atan2(-(paramPoint.getY() - this.mCenterY), d1);
    if (d2 < 0.0D) {}
    for (double d3 = Math.abs(d2);; d3 = 6.283185307179586D - d2) {
      return Math.toDegrees(d3);
    }
  }
  
  public SeriesSelection getSeriesAndPointForScreenCoordinate(Point paramPoint)
  {
    double d;
    Iterator localIterator;
    if (isOnPieChart(paramPoint))
    {
      d = getAngle(paramPoint);
      localIterator = this.mPieSegmentList.iterator();
    }
    PieSegment localPieSegment;
    do
    {
      if (!localIterator.hasNext()) {
        return null;
      }
      localPieSegment = (PieSegment)localIterator.next();
    } while (!localPieSegment.isInSegment(d));
    return new SeriesSelection(0, localPieSegment.getDataIndex(), localPieSegment.getValue(), localPieSegment.getValue());
  }
  
  public boolean isOnPieChart(Point paramPoint)
  {
    return Math.pow(this.mCenterX - paramPoint.getX(), 2.0D) + Math.pow(this.mCenterY - paramPoint.getY(), 2.0D) <= this.mPieChartRadius * this.mPieChartRadius;
  }
  
  public void setDimensions(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mPieChartRadius = paramInt1;
    this.mCenterX = paramInt2;
    this.mCenterY = paramInt3;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\chart\PieMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */