package org.achartengine.chart;

import android.graphics.RectF;

public class ClickableArea
{
  private RectF rect;
  private double x;
  private double y;
  
  public ClickableArea(RectF paramRectF, double paramDouble1, double paramDouble2)
  {
    this.rect = paramRectF;
    this.x = paramDouble1;
    this.y = paramDouble2;
  }
  
  public RectF getRect()
  {
    return this.rect;
  }
  
  public double getX()
  {
    return this.x;
  }
  
  public double getY()
  {
    return this.y;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\chart\ClickableArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */