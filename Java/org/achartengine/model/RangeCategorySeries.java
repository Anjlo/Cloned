package org.achartengine.model;

import java.util.ArrayList;
import java.util.List;

public class RangeCategorySeries
  extends CategorySeries
{
  private List<Double> mMaxValues = new ArrayList();
  
  public RangeCategorySeries(String paramString)
  {
    super(paramString);
  }
  
  public void add(double paramDouble1, double paramDouble2)
  {
    try
    {
      super.add(paramDouble1);
      this.mMaxValues.add(Double.valueOf(paramDouble2));
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void add(String paramString, double paramDouble1, double paramDouble2)
  {
    try
    {
      super.add(paramString, paramDouble1);
      this.mMaxValues.add(Double.valueOf(paramDouble2));
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
      this.mMaxValues.clear();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public double getMaximumValue(int paramInt)
  {
    return ((Double)this.mMaxValues.get(paramInt)).doubleValue();
  }
  
  public double getMinimumValue(int paramInt)
  {
    return getValue(paramInt);
  }
  
  public void remove(int paramInt)
  {
    try
    {
      super.remove(paramInt);
      this.mMaxValues.remove(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public XYSeries toXYSeries()
  {
    XYSeries localXYSeries = new XYSeries(getTitle());
    int i = getItemCount();
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return localXYSeries;
      }
      localXYSeries.add(j + 1, getMinimumValue(j));
      localXYSeries.add(1.000001D + j, getMaximumValue(j));
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\model\RangeCategorySeries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */