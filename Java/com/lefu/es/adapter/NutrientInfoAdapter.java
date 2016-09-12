package com.lefu.es.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.lefu.es.entity.NutrientBo;
import java.util.List;

public class NutrientInfoAdapter
  extends BaseAdapter
{
  private List<NutrientBo> atrrArray;
  private Context context;
  
  public NutrientInfoAdapter(Context paramContext, List<NutrientBo> paramList)
  {
    this.atrrArray = paramList;
    this.context = paramContext;
  }
  
  public void appendList(List<NutrientBo> paramList)
  {
    if ((!this.atrrArray.containsAll(paramList)) && (paramList != null) && (paramList.size() > 0))
    {
      this.atrrArray.addAll(paramList);
      notifyDataSetChanged();
    }
  }
  
  public void clear()
  {
    this.atrrArray.clear();
    notifyDataSetChanged();
  }
  
  public void clearList(List<NutrientBo> paramList)
  {
    if (this.atrrArray != null)
    {
      this.atrrArray.clear();
      this.atrrArray.addAll(paramList);
      notifyDataSetChanged();
    }
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
      paramView = View.inflate(this.context, 2130903073, null);
      localViewHolder.atrrName = ((TextView)paramView.findViewById(2131165432));
      localViewHolder.excel_Carbohydrt = ((TextView)paramView.findViewById(2131165433));
      localViewHolder.excel_Energ = ((TextView)paramView.findViewById(2131165434));
      localViewHolder.excel_Protein = ((TextView)paramView.findViewById(2131165435));
      localViewHolder.excel_Lipid = ((TextView)paramView.findViewById(2131165436));
      localViewHolder.excel_VitC = ((TextView)paramView.findViewById(2131165437));
      localViewHolder.excel_Fiber = ((TextView)paramView.findViewById(2131165438));
      localViewHolder.excel_Cholesterol = ((TextView)paramView.findViewById(2131165439));
      localViewHolder.excel_Calcium = ((TextView)paramView.findViewById(2131165440));
      paramView.setTag(localViewHolder);
    }
    for (;;)
    {
      localViewHolder.atrrName.setText(((NutrientBo)this.atrrArray.get(paramInt)).getNutrientDesc());
      localViewHolder.excel_Carbohydrt.setText(((NutrientBo)this.atrrArray.get(paramInt)).getNutrientCarbohydrt());
      localViewHolder.excel_Energ.setText(((NutrientBo)this.atrrArray.get(paramInt)).getNutrientEnerg());
      localViewHolder.excel_Protein.setText(((NutrientBo)this.atrrArray.get(paramInt)).getNutrientProtein());
      localViewHolder.excel_Lipid.setText(((NutrientBo)this.atrrArray.get(paramInt)).getNutrientLipidTot());
      localViewHolder.excel_VitC.setText(((NutrientBo)this.atrrArray.get(paramInt)).getNutrientVitc());
      localViewHolder.excel_Fiber.setText(((NutrientBo)this.atrrArray.get(paramInt)).getNutrientFiberTD());
      localViewHolder.excel_Cholesterol.setText(((NutrientBo)this.atrrArray.get(paramInt)).getNutrientCholestrl());
      localViewHolder.excel_Calcium.setText(((NutrientBo)this.atrrArray.get(paramInt)).getNutrientCalcium());
      return paramView;
      localViewHolder = (ViewHolder)paramView.getTag();
    }
  }
  
  class ViewHolder
  {
    TextView atrrName;
    TextView excel_Calcium;
    TextView excel_Carbohydrt;
    TextView excel_Cholesterol;
    TextView excel_Energ;
    TextView excel_Fiber;
    TextView excel_Lipid;
    TextView excel_Protein;
    TextView excel_VitC;
    
    ViewHolder() {}
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\adapter\NutrientInfoAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */