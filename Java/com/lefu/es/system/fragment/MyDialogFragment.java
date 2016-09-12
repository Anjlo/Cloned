package com.lefu.es.system.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.lefu.es.entity.NutrientBo;
import java.util.ArrayList;
import java.util.Iterator;

public class MyDialogFragment
  extends DialogFragment
  implements AdapterView.OnItemClickListener
{
  ListView mylist;
  ArrayList<NutrientBo> nutrientList = null;
  
  public static MyDialogFragment newInstance(ArrayList<NutrientBo> paramArrayList)
  {
    MyDialogFragment localMyDialogFragment = new MyDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("nutrientList", paramArrayList);
    localMyDialogFragment.setArguments(localBundle);
    return localMyDialogFragment;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    int i;
    ArrayList localArrayList;
    Iterator localIterator;
    if (this.nutrientList != null)
    {
      i = this.nutrientList.size();
      localArrayList = new ArrayList();
      localIterator = this.nutrientList.iterator();
    }
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        String[] arrayOfString = (String[])localArrayList.toArray(new String[i]);
        ArrayAdapter localArrayAdapter = new ArrayAdapter(getActivity(), 17367043, arrayOfString);
        this.mylist.setAdapter(localArrayAdapter);
        this.mylist.setOnItemClickListener(this);
        return;
      }
      localArrayList.add(((NutrientBo)localIterator.next()).getNutrientDesc());
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.nutrientList = ((ArrayList)getArguments().getSerializable("nutrientList"));
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903069, null, false);
    this.mylist = ((ListView)localView.findViewById(2131165427));
    getDialog().getWindow().requestFeature(1);
    return localView;
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    dismiss();
    ((NatureSelectListener)getActivity()).natureSelectComplete((NutrientBo)this.nutrientList.get(paramInt));
  }
  
  public void onStart()
  {
    super.onStart();
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    WindowManager.LayoutParams localLayoutParams = getDialog().getWindow().getAttributes();
    localLayoutParams.width = localDisplayMetrics.widthPixels;
    localLayoutParams.height = getResources().getDimensionPixelOffset(2131296264);
    localLayoutParams.gravity = 48;
    getDialog().getWindow().setAttributes(localLayoutParams);
  }
  
  public static abstract interface NatureSelectListener
  {
    public abstract void natureSelectComplete(NutrientBo paramNutrientBo);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\fragment\MyDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */