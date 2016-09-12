package org.achartengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XYMultipleSeriesDataset
  implements Serializable
{
  private List<XYSeries> mSeries = new ArrayList();
  
  public void addAllSeries(List<XYSeries> paramList)
  {
    try
    {
      this.mSeries.addAll(paramList);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void addSeries(int paramInt, XYSeries paramXYSeries)
  {
    try
    {
      this.mSeries.add(paramInt, paramXYSeries);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void addSeries(XYSeries paramXYSeries)
  {
    try
    {
      this.mSeries.add(paramXYSeries);
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
      this.mSeries.clear();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public XYSeries[] getSeries()
  {
    try
    {
      XYSeries[] arrayOfXYSeries = (XYSeries[])this.mSeries.toArray(new XYSeries[0]);
      return arrayOfXYSeries;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public XYSeries getSeriesAt(int paramInt)
  {
    try
    {
      XYSeries localXYSeries = (XYSeries)this.mSeries.get(paramInt);
      return localXYSeries;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getSeriesCount()
  {
    try
    {
      int i = this.mSeries.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void removeSeries(int paramInt)
  {
    try
    {
      this.mSeries.remove(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void removeSeries(XYSeries paramXYSeries)
  {
    try
    {
      this.mSeries.remove(paramXYSeries);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\model\XYMultipleSeriesDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */