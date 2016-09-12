package com.lefu.es.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.lefu.es.entity.NutrientAtrrBo;
import com.lefu.es.util.Tool;
import com.lefu.es.util.UtilTooth;
import java.util.List;

public class NutrientDetailAdapter
  extends BaseAdapter
{
  private List<NutrientAtrrBo> atrrArray;
  private Context context;
  private float mWeight;
  
  public NutrientDetailAdapter(Context paramContext, List<NutrientAtrrBo> paramList, float paramFloat)
  {
    this.atrrArray = paramList;
    this.context = paramContext;
    this.mWeight = paramFloat;
  }
  
  public int getCount()
  {
    if ((this.atrrArray == null) || (this.atrrArray.size() == 0)) {
      return 0;
    }
    return this.atrrArray.size();
  }
  
  public Object getItem(int paramInt)
  {
    if ((this.atrrArray == null) || (this.atrrArray.size() == 0)) {
      return null;
    }
    return this.atrrArray.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return 0L;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ViewHolder localViewHolder;
    if (paramView == null)
    {
      localViewHolder = new ViewHolder();
      paramView = View.inflate(this.context, 2130903078, null);
      localViewHolder.atrrName = ((TextView)paramView.findViewById(2131165455));
      localViewHolder.atrrValue = ((TextView)paramView.findViewById(2131165456));
      paramView.setTag(localViewHolder);
    }
    for (;;)
    {
      localViewHolder.atrrName.setText(((NutrientAtrrBo)this.atrrArray.get(paramInt)).getAttrName());
      if ((TextUtils.isEmpty(((NutrientAtrrBo)this.atrrArray.get(paramInt)).getAttrValue())) || (!Tool.isDigitsOnly(((NutrientAtrrBo)this.atrrArray.get(paramInt)).getAttrValue()))) {
        break;
      }
      float f = 0.01F * (Float.parseFloat(((NutrientAtrrBo)this.atrrArray.get(paramInt)).getAttrValue()) * this.mWeight);
      localViewHolder.atrrValue.setText(UtilTooth.keep2Point(f));
      return paramView;
      localViewHolder = (ViewHolder)paramView.getTag();
    }
    localViewHolder.atrrValue.setText("0.0");
    return paramView;
  }
  
  class ViewHolder
  {
    TextView atrrName;
    TextView atrrValue;
    
    ViewHolder() {}
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\adapter\NutrientDetailAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */