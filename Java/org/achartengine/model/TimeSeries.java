package org.achartengine.model;

import java.util.Date;

public class TimeSeries
  extends XYSeries
{
  public TimeSeries(String paramString)
  {
    super(paramString);
  }
  
  public void add(Date paramDate, double paramDouble)
  {
    try
    {
      super.add(paramDate.getTime(), paramDouble);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  protected double getPadding()
  {
    return 1.0D;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\model\TimeSeries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */