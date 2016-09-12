package com.lefu.es.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filter.FilterResults;

public class DiseaseAutoCompleteTextView
  extends AutoCompleteTextView
  implements View.OnFocusChangeListener
{
  private boolean hasFoucs;
  private InputStatu inputStatu;
  private Drawable mClearDrawable;
  
  public DiseaseAutoCompleteTextView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DiseaseAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 16842862);
  }
  
  public DiseaseAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  private void init()
  {
    setThreshold(1);
    this.mClearDrawable = getCompoundDrawables()[2];
    if (this.mClearDrawable == null) {
      this.mClearDrawable = getResources().getDrawable(2130837659);
    }
    setClearIconVisible(false);
    setOnFocusChangeListener(this);
    setAdapter(new HintAdapter(getContext(), 2130903070));
  }
  
  public void onFocusChange(View paramView, boolean paramBoolean)
  {
    this.hasFoucs = paramBoolean;
    if (paramBoolean)
    {
      int i = getText().length();
      boolean bool = false;
      if (i > 0) {
        bool = true;
      }
      setClearIconVisible(bool);
      return;
    }
    setClearIconVisible(false);
  }
  
  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.hasFoucs) {
      if (paramCharSequence.length() <= 0) {
        break label26;
      }
    }
    label26:
    for (boolean bool = true;; bool = false)
    {
      setClearIconVisible(bool);
      return;
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 1;
    if ((paramMotionEvent.getAction() == i) && (getCompoundDrawables()[2] != null)) {
      if ((paramMotionEvent.getX() <= getWidth() - getTotalPaddingRight()) || (paramMotionEvent.getX() >= getWidth() - getPaddingRight())) {
        break label71;
      }
    }
    for (;;)
    {
      if (i != 0) {
        setText("");
      }
      return super.onTouchEvent(paramMotionEvent);
      label71:
      i = 0;
    }
  }
  
  protected void setClearIconVisible(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (Drawable localDrawable = this.mClearDrawable;; localDrawable = null)
    {
      setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], localDrawable, getCompoundDrawables()[3]);
      if (this.inputStatu != null) {
        this.inputStatu.onInput(paramBoolean);
      }
      return;
    }
  }
  
  public void setInputStatu(InputStatu paramInputStatu)
  {
    this.inputStatu = paramInputStatu;
  }
  
  private class HintAdapter
    extends ArrayAdapter<String>
  {
    private Filter mFilter = null;
    
    public HintAdapter(Context paramContext, int paramInt)
    {
      super(paramInt);
    }
    
    public Filter getFilter()
    {
      if (this.mFilter == null) {
        this.mFilter = new Filter()
        {
          protected Filter.FilterResults performFiltering(CharSequence paramAnonymousCharSequence)
          {
            return null;
          }
          
          protected void publishResults(CharSequence paramAnonymousCharSequence, Filter.FilterResults paramAnonymousFilterResults)
          {
            DiseaseAutoCompleteTextView.HintAdapter.this.clear();
          }
        };
      }
      return this.mFilter;
    }
  }
  
  public static abstract interface InputStatu
  {
    public abstract void onInput(boolean paramBoolean);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\DiseaseAutoCompleteTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */