package org.achartengine.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DialRenderer
  extends DefaultRenderer
{
  private double mAngleMax = 30.0D;
  private double mAngleMin = 330.0D;
  private double mMajorTickSpacing = Double.MAX_VALUE;
  private double mMaxValue = -1.7976931348623157E308D;
  private double mMinValue = Double.MAX_VALUE;
  private double mMinorTickSpacing = Double.MAX_VALUE;
  private List<Type> mVisualTypes = new ArrayList();
  
  public double getAngleMax()
  {
    return this.mAngleMax;
  }
  
  public double getAngleMin()
  {
    return this.mAngleMin;
  }
  
  public double getMajorTicksSpacing()
  {
    return this.mMajorTickSpacing;
  }
  
  public double getMaxValue()
  {
    return this.mMaxValue;
  }
  
  public double getMinValue()
  {
    return this.mMinValue;
  }
  
  public double getMinorTicksSpacing()
  {
    return this.mMinorTickSpacing;
  }
  
  public Type getVisualTypeForIndex(int paramInt)
  {
    if (paramInt < this.mVisualTypes.size()) {
      return (Type)this.mVisualTypes.get(paramInt);
    }
    return Type.NEEDLE;
  }
  
  public boolean isMaxValueSet()
  {
    return this.mMaxValue != -1.7976931348623157E308D;
  }
  
  public boolean isMinValueSet()
  {
    return this.mMinValue != Double.MAX_VALUE;
  }
  
  public void setAngleMax(double paramDouble)
  {
    this.mAngleMax = paramDouble;
  }
  
  public void setAngleMin(double paramDouble)
  {
    this.mAngleMin = paramDouble;
  }
  
  public void setMajorTicksSpacing(double paramDouble)
  {
    this.mMajorTickSpacing = paramDouble;
  }
  
  public void setMaxValue(double paramDouble)
  {
    this.mMaxValue = paramDouble;
  }
  
  public void setMinValue(double paramDouble)
  {
    this.mMinValue = paramDouble;
  }
  
  public void setMinorTicksSpacing(double paramDouble)
  {
    this.mMinorTickSpacing = paramDouble;
  }
  
  public void setVisualTypes(Type[] paramArrayOfType)
  {
    this.mVisualTypes.clear();
    this.mVisualTypes.addAll(Arrays.asList(paramArrayOfType));
  }
  
  public static enum Type
  {
    ARROW,  NEEDLE;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\renderer\DialRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */