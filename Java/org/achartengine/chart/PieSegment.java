package org.achartengine.chart;

import java.io.Serializable;

public class PieSegment
  implements Serializable
{
  private int mDataIndex;
  private float mEndAngle;
  private float mStartAngle;
  private float mValue;
  
  public PieSegment(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.mStartAngle = paramFloat2;
    this.mEndAngle = (paramFloat3 + paramFloat2);
    this.mDataIndex = paramInt;
    this.mValue = paramFloat1;
  }
  
  protected int getDataIndex()
  {
    return this.mDataIndex;
  }
  
  protected float getEndAngle()
  {
    return this.mEndAngle;
  }
  
  protected float getStartAngle()
  {
    return this.mStartAngle;
  }
  
  protected float getValue()
  {
    return this.mValue;
  }
  
  public boolean isInSegment(double paramDouble)
  {
    if ((paramDouble >= this.mStartAngle) && (paramDouble <= this.mEndAngle)) {
      return true;
    }
    double d1 = paramDouble % 360.0D;
    double d2 = this.mStartAngle;
    for (double d3 = this.mEndAngle;; d3 -= 360.0D)
    {
      if (d3 <= 360.0D)
      {
        if ((d1 >= d2) && (d1 <= d3)) {
          break;
        }
        return false;
      }
      d2 -= 360.0D;
    }
  }
  
  public String toString()
  {
    return "mDataIndex=" + this.mDataIndex + ",mValue=" + this.mValue + ",mStartAngle=" + this.mStartAngle + ",mEndAngle=" + this.mEndAngle;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\chart\PieSegment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */