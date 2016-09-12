package com.lefu.es.view.spinner;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class HintSpinner<T>
{
  private static final String TAG = HintSpinner.class.getSimpleName();
  private final HintAdapter<T> adapter;
  private final Callback<T> callback;
  private final Spinner spinner;
  
  public HintSpinner(Spinner paramSpinner, HintAdapter<T> paramHintAdapter, Callback<T> paramCallback)
  {
    this.spinner = paramSpinner;
    this.adapter = paramHintAdapter;
    this.callback = paramCallback;
  }
  
  private boolean isHintPosition(int paramInt)
  {
    return paramInt == this.adapter.getHintPosition();
  }
  
  public void init()
  {
    this.spinner.setAdapter(this.adapter);
    this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        Log.d(HintSpinner.TAG, "position selected: " + paramAnonymousInt);
        if (HintSpinner.this.callback == null) {
          throw new IllegalStateException("callback cannot be null");
        }
        if (!HintSpinner.this.isHintPosition(paramAnonymousInt))
        {
          Object localObject = HintSpinner.this.spinner.getItemAtPosition(paramAnonymousInt);
          HintSpinner.this.callback.onItemSelected(paramAnonymousInt, localObject);
        }
      }
      
      public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView)
      {
        Log.d(HintSpinner.TAG, "Nothing selected");
      }
    });
    selectHint();
  }
  
  public void selectHint()
  {
    this.spinner.setSelection(this.adapter.getHintPosition(), true);
  }
  
  public static abstract interface Callback<T>
  {
    public abstract void onItemSelected(int paramInt, T paramT);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\spinner\HintSpinner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */