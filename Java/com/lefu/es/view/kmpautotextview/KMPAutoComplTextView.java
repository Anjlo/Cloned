package com.lefu.es.view.kmpautotextview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.TextView;
import com.lefu.es.entity.NutrientBo;
import com.lefu.iwellness.newes.system.R.styleable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class KMPAutoComplTextView
  extends AutoCompleteTextView
{
  private static final int DEFAULT_HIGHLIGHT = Color.parseColor("#FF4081");
  private static final int DEFAULT_TEXTCOLOR = Color.parseColor("#80000000");
  private static final int DEFAULT_TEXT_PIXEL_SIZE = 12;
  private LayoutInflater inflater;
  private KMPAdapter mAdapter;
  private ColorStateList mHighLightColor;
  private boolean mIsIgnoreCase;
  private OnPopupItemClickListener mListener;
  private List<PopupTextBean> mSourceDatas;
  private List<PopupTextBean> mTempDatas;
  private ColorStateList mTextColor;
  private float mTextSize;
  NutrientBo selectNutrient = null;
  TextView textView = null;
  
  public KMPAutoComplTextView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public KMPAutoComplTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 16842859);
  }
  
  public KMPAutoComplTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private List<String> getResultDatas(List<String> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0)) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramList.iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        this.mSourceDatas = new ArrayList();
        this.mSourceDatas.addAll(localArrayList);
        return paramList;
      }
      localArrayList.add(new PopupTextBean((String)localIterator.next()));
    }
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    this.inflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
    if (paramAttributeSet != null)
    {
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.KMPAutoComplTextView);
      this.mTextColor = localTypedArray.getColorStateList(1);
      this.mHighLightColor = localTypedArray.getColorStateList(0);
      this.mTextSize = localTypedArray.getDimensionPixelSize(2, 12);
      this.mIsIgnoreCase = localTypedArray.getBoolean(3, false);
      localTypedArray.recycle();
    }
    initListener();
  }
  
  private void initListener()
  {
    addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
        KMPAutoComplTextView.this.onInputTextChanged(paramAnonymousEditable.toString());
      }
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        if ((TextUtils.isEmpty(paramAnonymousCharSequence.toString())) && (KMPAutoComplTextView.this.textView != null))
        {
          KMPAutoComplTextView.this.textView.setText("");
          KMPAutoComplTextView.this.selectNutrient = null;
        }
      }
    });
  }
  
  private void matchResult(String paramString)
  {
    List localList = this.mSourceDatas;
    if ((TextUtils.isEmpty(paramString)) || (localList == null) || (localList.size() == 0)) {
      return;
    }
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Iterator localIterator = localList.iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        this.mTempDatas = new ArrayList();
        this.mTempDatas.clear();
        this.mTempDatas.addAll(localArrayList1);
        this.mAdapter.mList.clear();
        this.mAdapter.mList.addAll(localArrayList2);
        return;
      }
      PopupTextBean localPopupTextBean = (PopupTextBean)localIterator.next();
      int i = matchString(localPopupTextBean.mTarget, paramString, this.mIsIgnoreCase);
      if (-1 != i)
      {
        localArrayList1.add(new PopupTextBean(localPopupTextBean.mTarget, i, i + paramString.length()));
        localArrayList2.add(localPopupTextBean.mTarget);
      }
    }
  }
  
  private static int[] next(char[] paramArrayOfChar)
  {
    int[] arrayOfInt = new int[paramArrayOfChar.length];
    arrayOfInt[0] = -1;
    int i = 0;
    int j = -1;
    for (;;)
    {
      if (i >= -1 + paramArrayOfChar.length) {
        return arrayOfInt;
      }
      if ((j == -1) || (paramArrayOfChar[i] == paramArrayOfChar[j]))
      {
        i++;
        j++;
        if (paramArrayOfChar[i] != paramArrayOfChar[j]) {
          arrayOfInt[i] = j;
        } else {
          arrayOfInt[i] = arrayOfInt[j];
        }
      }
      else
      {
        j = arrayOfInt[j];
      }
    }
  }
  
  private void onInputTextChanged(String paramString)
  {
    matchResult(paramString);
    if (this.mAdapter.mList.size() == 0) {
      dismissDropDown();
    }
    do
    {
      return;
      this.mAdapter.notifyDataSetChanged();
    } while ((isPopupShowing()) && (this.mAdapter.mList.size() <= 0));
    showDropDown();
  }
  
  public boolean getMatchIgnoreCase()
  {
    return this.mIsIgnoreCase;
  }
  
  public void inputTextNull(TextView paramTextView, NutrientBo paramNutrientBo)
  {
    this.textView = paramTextView;
    this.selectNutrient = paramNutrientBo;
  }
  
  public int matchString(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean)
  {
    char[] arrayOfChar1 = paramCharSequence2.toString().toCharArray();
    char[] arrayOfChar2 = paramCharSequence1.toString().toCharArray();
    int[] arrayOfInt = next(arrayOfChar1);
    int i = 0;
    int j = 0;
    for (;;)
    {
      if ((i > -1 + arrayOfChar2.length) || (j > -1 + arrayOfChar1.length))
      {
        if (j >= arrayOfChar1.length) {
          break;
        }
        return -1;
      }
      if (paramBoolean)
      {
        if ((j == -1) || (arrayOfChar2[i] == arrayOfChar1[j]) || (String.valueOf(arrayOfChar2[i]).equalsIgnoreCase(String.valueOf(arrayOfChar1[j]))))
        {
          i++;
          j++;
        }
        else
        {
          j = arrayOfInt[j];
        }
      }
      else if ((j == -1) || (arrayOfChar2[i] == arrayOfChar1[j]))
      {
        i++;
        j++;
      }
      else
      {
        j = arrayOfInt[j];
      }
    }
    return i - arrayOfChar1.length;
  }
  
  public void setDatas(List<String> paramList)
  {
    this.mAdapter = new KMPAdapter(getContext(), getResultDatas(paramList));
    setAdapter(this.mAdapter);
  }
  
  public void setMatchIgnoreCase(boolean paramBoolean)
  {
    this.mIsIgnoreCase = paramBoolean;
  }
  
  public void setOnPopupItemClickListener(OnPopupItemClickListener paramOnPopupItemClickListener)
  {
    this.mListener = paramOnPopupItemClickListener;
    setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        if (KMPAutoComplTextView.this.mListener == null) {
          return;
        }
        KMPAutoComplTextView.this.mListener.onPopupItemClick(KMPAutoComplTextView.this.getText().toString());
      }
    });
  }
  
  class KMPAdapter
    extends BaseAdapter
    implements Filterable
  {
    private Context mContext;
    private MyFilter mFilter;
    private List<String> mList;
    
    public KMPAdapter(List<String> paramList)
    {
      this.mContext = paramList;
      this.mList = new ArrayList();
      Collection localCollection;
      this.mList.addAll(localCollection);
    }
    
    public int getCount()
    {
      if (this.mList == null) {
        return 0;
      }
      return this.mList.size();
    }
    
    public Filter getFilter()
    {
      if (this.mFilter == null) {
        this.mFilter = new MyFilter(null);
      }
      return this.mFilter;
    }
    
    public Object getItem(int paramInt)
    {
      if (this.mList == null) {
        return null;
      }
      return (String)this.mList.get(paramInt);
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      ViewHolder localViewHolder;
      PopupTextBean localPopupTextBean;
      SpannableString localSpannableString;
      int i;
      label103:
      int j;
      if (paramView == null)
      {
        localViewHolder = new ViewHolder(null);
        paramView = KMPAutoComplTextView.this.inflater.inflate(2130903080, null);
        localViewHolder.tv = ((TextView)paramView.findViewById(2131165195));
        paramView.setTag(localViewHolder);
        localPopupTextBean = (PopupTextBean)KMPAutoComplTextView.this.mTempDatas.get(paramInt);
        localSpannableString = new SpannableString(localPopupTextBean.mTarget);
        TextView localTextView1 = localViewHolder.tv;
        if (KMPAutoComplTextView.this.mTextColor != null) {
          break label215;
        }
        i = KMPAutoComplTextView.DEFAULT_TEXTCOLOR;
        localTextView1.setTextColor(i);
        TextView localTextView2 = localViewHolder.tv;
        if (KMPAutoComplTextView.this.mTextSize != 0.0F) {
          break label230;
        }
        j = 12;
        label133:
        localTextView2.setTextSize(j);
        if (-1 == localPopupTextBean.mStartIndex) {
          break label267;
        }
        if (KMPAutoComplTextView.this.mHighLightColor != null) {
          break label252;
        }
      }
      label215:
      label230:
      label252:
      for (int k = KMPAutoComplTextView.DEFAULT_HIGHLIGHT;; k = KMPAutoComplTextView.this.mHighLightColor.getDefaultColor())
      {
        localSpannableString.setSpan(new ForegroundColorSpan(k), localPopupTextBean.mStartIndex, localPopupTextBean.mEndIndex, 33);
        localViewHolder.tv.setText(localSpannableString);
        return paramView;
        localViewHolder = (ViewHolder)paramView.getTag();
        break;
        i = KMPAutoComplTextView.this.mTextColor.getDefaultColor();
        break label103;
        j = DisplayUtils.px2sp(KMPAutoComplTextView.this.getContext(), KMPAutoComplTextView.this.mTextSize);
        break label133;
      }
      label267:
      localViewHolder.tv.setText(localPopupTextBean.mTarget);
      return paramView;
    }
    
    private class MyFilter
      extends Filter
    {
      private MyFilter() {}
      
      protected Filter.FilterResults performFiltering(CharSequence paramCharSequence)
      {
        Filter.FilterResults localFilterResults = new Filter.FilterResults();
        if (KMPAutoComplTextView.KMPAdapter.this.mList == null) {
          KMPAutoComplTextView.KMPAdapter.this.mList = new ArrayList();
        }
        localFilterResults.values = KMPAutoComplTextView.KMPAdapter.this.mList;
        localFilterResults.count = KMPAutoComplTextView.KMPAdapter.this.mList.size();
        return localFilterResults;
      }
      
      protected void publishResults(CharSequence paramCharSequence, Filter.FilterResults paramFilterResults)
      {
        if (paramFilterResults.count > 0)
        {
          KMPAutoComplTextView.KMPAdapter.this.notifyDataSetChanged();
          return;
        }
        KMPAutoComplTextView.KMPAdapter.this.notifyDataSetInvalidated();
      }
    }
    
    private class ViewHolder
    {
      TextView tv;
      
      private ViewHolder() {}
    }
  }
  
  public static abstract interface OnPopupItemClickListener
  {
    public abstract void onPopupItemClick(CharSequence paramCharSequence);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\kmpautotextview\KMPAutoComplTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */