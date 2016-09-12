package com.lefu.es.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;

public class MyPageAdapter
  extends PagerAdapter
{
  private ArrayList<View> listViews;
  
  public MyPageAdapter(ArrayList<View> paramArrayList)
  {
    this.listViews = paramArrayList;
  }
  
  public void destroyItem(View paramView, int paramInt, Object paramObject)
  {
    if ((this.listViews == null) || (this.listViews.size() <= paramInt)) {}
    while (this.listViews.get(paramInt) == null) {
      return;
    }
    ((ViewPager)paramView).removeView((View)this.listViews.get(paramInt));
  }
  
  public void finishUpdate(View paramView) {}
  
  public int getCount()
  {
    if (this.listViews == null) {
      return 0;
    }
    return this.listViews.size();
  }
  
  public Object instantiateItem(View paramView, int paramInt)
  {
    try
    {
      if ((this.listViews != null) && (this.listViews.size() > 0) && (paramInt < this.listViews.size())) {
        ((ViewPager)paramView).addView((View)this.listViews.get(paramInt), 0);
      }
      if ((this.listViews != null) && (this.listViews.size() > 0) && (paramInt < this.listViews.size())) {
        return this.listViews.get(paramInt);
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Log.e("zhou", "exception��" + localException.getMessage());
      }
    }
    return null;
  }
  
  public boolean isViewFromObject(View paramView, Object paramObject)
  {
    return paramView == paramObject;
  }
  
  public void setListViews(ArrayList<View> paramArrayList)
  {
    if ((this.listViews != null) && (this.listViews.size() > 0)) {
      this.listViews.clear();
    }
    this.listViews = paramArrayList;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\adapter\MyPageAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */