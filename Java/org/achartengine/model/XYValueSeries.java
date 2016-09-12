package org.achartengine.model;

import java.util.ArrayList;
import java.util.List;

public class XYValueSeries
  extends XYSeries
{
  private double mMaxValue = -1.7976931348623157E308D;
  private double mMinValue = Double.MAX_VALUE;
  private List<Double> mValue = new ArrayList();
  
  public XYValueSeries(String paramString)
  {
    super(paramString);
  }
  
  private void initRange()
  {
    this.mMinValue = Double.MAX_VALUE;
    this.mMaxValue = Double.MAX_VALUE;
    int i = getItemCount();
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return;
      }
      updateRange(getValue(j));
    }
  }
  
  private void updateRange(double paramDouble)
  {
    this.mMinValue = Math.min(this.mMinValue, paramDouble);
    this.mMaxValue = Math.max(this.mMaxValue, paramDouble);
  }
  
  public void add(double paramDouble1, double paramDouble2)
  {
    try
    {
      add(paramDouble1, paramDouble2, 0.0D);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void add(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    try
    {
      super.add(paramDouble1, paramDouble2);
      this.mValue.add(Double.valueOf(paramDouble3));
      updateRange(paramDouble3);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void clear()
  {
    try
    {
      super.clear();
      this.mValue.clear();
      initRange();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public double getMaxValue()
  {
    return this.mMaxValue;
  }
  
  public double getMinValue()
  {
    return this.mMinValue;
  }
  
  public double getValue(int paramInt)
  {
    try
    {
      double d = ((Double)this.mValue.get(paramInt)).doubleValue();
      return d;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void remove(int paramInt)
  {
    try
    {
      super.remove(paramInt);
      double d = ((Double)this.mValue.remove(paramInt)).doubleValue();
      if ((d == this.mMinValue) || (d == this.mMaxValue)) {
        initRange();
      }
      return;
    }
    finally {}
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\model\XYValueSeries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */