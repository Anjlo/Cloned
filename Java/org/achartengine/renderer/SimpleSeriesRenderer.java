package org.achartengine.renderer;

import java.io.Serializable;
import java.text.NumberFormat;

public class SimpleSeriesRenderer
  implements Serializable
{
  private NumberFormat mChartValuesFormat;
  private int mColor = -16776961;
  private boolean mDisplayBoundingPoints = true;
  private boolean mGradientEnabled = false;
  private int mGradientStartColor;
  private double mGradientStartValue;
  private int mGradientStopColor;
  private double mGradientStopValue;
  private boolean mHighlighted;
  private boolean mShowLegendItem = true;
  private BasicStroke mStroke;
  
  public NumberFormat getChartValuesFormat()
  {
    return this.mChartValuesFormat;
  }
  
  public int getColor()
  {
    return this.mColor;
  }
  
  public int getGradientStartColor()
  {
    return this.mGradientStartColor;
  }
  
  public double getGradientStartValue()
  {
    return this.mGradientStartValue;
  }
  
  public int getGradientStopColor()
  {
    return this.mGradientStopColor;
  }
  
  public double getGradientStopValue()
  {
    return this.mGradientStopValue;
  }
  
  public BasicStroke getStroke()
  {
    return this.mStroke;
  }
  
  public boolean isDisplayBoundingPoints()
  {
    return this.mDisplayBoundingPoints;
  }
  
  public boolean isGradientEnabled()
  {
    return this.mGradientEnabled;
  }
  
  public boolean isHighlighted()
  {
    return this.mHighlighted;
  }
  
  public boolean isShowLegendItem()
  {
    return this.mShowLegendItem;
  }
  
  public void setChartValuesFormat(NumberFormat paramNumberFormat)
  {
    this.mChartValuesFormat = paramNumberFormat;
  }
  
  public void setColor(int paramInt)
  {
    this.mColor = paramInt;
  }
  
  public void setDisplayBoundingPoints(boolean paramBoolean)
  {
    this.mDisplayBoundingPoints = paramBoolean;
  }
  
  public void setGradientEnabled(boolean paramBoolean)
  {
    this.mGradientEnabled = paramBoolean;
  }
  
  public void setGradientStart(double paramDouble, int paramInt)
  {
    this.mGradientStartValue = paramDouble;
    this.mGradientStartColor = paramInt;
  }
  
  public void setGradientStop(double paramDouble, int paramInt)
  {
    this.mGradientStopValue = paramDouble;
    this.mGradientStopColor = paramInt;
  }
  
  public void setHighlighted(boolean paramBoolean)
  {
    this.mHighlighted = paramBoolean;
  }
  
  public void setShowLegendItem(boolean paramBoolean)
  {
    this.mShowLegendItem = paramBoolean;
  }
  
  public void setStroke(BasicStroke paramBasicStroke)
  {
    this.mStroke = paramBasicStroke;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\renderer\SimpleSeriesRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */