package com.lefu.es.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class MainPagerAdapter
  extends PagerAdapter
{
  private ArrayList<View> views = new ArrayList();
  
  public int addView(View paramView)
  {
    return addView(paramView, this.views.size());
  }
  
  public int addView(View paramView, int paramInt)
  {
    this.views.add(paramInt, paramView);
    return paramInt;
  }
  
  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    paramViewGroup.removeView((View)this.views.get(paramInt));
  }
  
  public int getCount()
  {
    return this.views.size();
  }
  
  public int getItemPosition(Object paramObject)
  {
    int i = this.views.indexOf(paramObject);
    if (i == -1) {
      i = -2;
    }
    return i;
  }
  
  public View getView(int paramInt)
  {
    return (View)this.views.get(paramInt);
  }
  
  public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
  {
    View localView = (View)this.views.get(paramInt);
    paramViewGroup.addView(localView);
    return localView;
  }
  
  public boolean isViewFromObject(View paramView, Object paramObject)
  {
    return paramView == paramObject;
  }
  
  public void removeAllView(ViewPager paramViewPager)
  {
    paramViewPager.setAdapter(null);
    this.views.removeAll(this.views);
    paramViewPager.setAdapter(this);
  }
  
  public int removeView(ViewPager paramViewPager, int paramInt)
  {
    paramViewPager.setAdapter(null);
    this.views.remove(paramInt);
    paramViewPager.setAdapter(this);
    return paramInt;
  }
  
  public int removeView(ViewPager paramViewPager, View paramView)
  {
    return removeView(paramViewPager, this.views.indexOf(paramView));
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\adapter\MainPagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */