package org.achartengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MultipleCategorySeries
  implements Serializable
{
  private List<String> mCategories = new ArrayList();
  private String mTitle;
  private List<String[]> mTitles = new ArrayList();
  private List<double[]> mValues = new ArrayList();
  
  public MultipleCategorySeries(String paramString)
  {
    this.mTitle = paramString;
  }
  
  public void add(String paramString, String[] paramArrayOfString, double[] paramArrayOfDouble)
  {
    this.mCategories.add(paramString);
    this.mTitles.add(paramArrayOfString);
    this.mValues.add(paramArrayOfDouble);
  }
  
  public void add(String[] paramArrayOfString, double[] paramArrayOfDouble)
  {
    add(this.mCategories.size(), paramArrayOfString, paramArrayOfDouble);
  }
  
  public void clear()
  {
    this.mCategories.clear();
    this.mTitles.clear();
    this.mValues.clear();
  }
  
  public int getCategoriesCount()
  {
    return this.mCategories.size();
  }
  
  public String getCategory(int paramInt)
  {
    return (String)this.mCategories.get(paramInt);
  }
  
  public int getItemCount(int paramInt)
  {
    return ((double[])this.mValues.get(paramInt)).length;
  }
  
  public String[] getTitles(int paramInt)
  {
    return (String[])this.mTitles.get(paramInt);
  }
  
  public double[] getValues(int paramInt)
  {
    return (double[])this.mValues.get(paramInt);
  }
  
  public void remove(int paramInt)
  {
    this.mCategories.remove(paramInt);
    this.mTitles.remove(paramInt);
    this.mValues.remove(paramInt);
  }
  
  public XYSeries toXYSeries()
  {
    return new XYSeries(this.mTitle);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\model\MultipleCategorySeries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */