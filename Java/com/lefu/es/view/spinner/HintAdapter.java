package com.lefu.es.view.spinner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class HintAdapter<T>
  extends ArrayAdapter<T>
{
  private static final int DEFAULT_LAYOUT_RESOURCE = 17367049;
  private static final String TAG = HintAdapter.class.getSimpleName();
  private String hintResource;
  private final LayoutInflater layoutInflater;
  private int layoutResource;
  
  public HintAdapter(Context paramContext, int paramInt1, int paramInt2, List<T> paramList)
  {
    this(paramContext, paramInt1, paramContext.getString(paramInt2), paramList);
  }
  
  public HintAdapter(Context paramContext, int paramInt, String paramString, List<T> paramList)
  {
    super(paramContext, paramInt, paramList);
    this.layoutResource = paramInt;
    this.hintResource = paramString;
    this.layoutInflater = LayoutInflater.from(paramContext);
  }
  
  public HintAdapter(Context paramContext, int paramInt, List<T> paramList)
  {
    this(paramContext, 17367049, paramContext.getString(paramInt), paramList);
  }
  
  public HintAdapter(Context paramContext, String paramString, List<T> paramList)
  {
    this(paramContext, 17367049, paramString, paramList);
  }
  
  private View getDefaultView(ViewGroup paramViewGroup)
  {
    View localView = inflateDefaultLayout(paramViewGroup);
    TextView localTextView = (TextView)localView.findViewById(16908308);
    localTextView.setText("");
    localTextView.setHint(this.hintResource);
    return localView;
  }
  
  private View inflateDefaultLayout(ViewGroup paramViewGroup)
  {
    return inflateLayout(17367049, paramViewGroup, false);
  }
  
  private View inflateLayout(int paramInt, ViewGroup paramViewGroup, boolean paramBoolean)
  {
    return this.layoutInflater.inflate(paramInt, paramViewGroup, paramBoolean);
  }
  
  protected View getCustomView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = inflateDefaultLayout(paramViewGroup);
    Object localObject = getItem(paramInt);
    TextView localTextView = (TextView)localView.findViewById(16908308);
    localTextView.setText(localObject.toString());
    localTextView.setHint("");
    return localView;
  }
  
  public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    return getCustomView(paramInt, paramView, paramViewGroup);
  }
  
  public int getHintPosition()
  {
    int i = getCount();
    if (i > 0) {
      i++;
    }
    return i;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    Log.d(TAG, "position: " + paramInt + ", getCount: " + getCount());
    if (paramInt == getHintPosition()) {
      return getDefaultView(paramViewGroup);
    }
    return getCustomView(paramInt, paramView, paramViewGroup);
  }
  
  public View inflateLayout(ViewGroup paramViewGroup, boolean paramBoolean)
  {
    return this.layoutInflater.inflate(this.layoutResource, paramViewGroup, paramBoolean);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\spinner\HintAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */