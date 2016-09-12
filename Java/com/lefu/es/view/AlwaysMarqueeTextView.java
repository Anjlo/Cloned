package com.lefu.es.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RemoteViews.RemoteView;
import android.widget.TextView;

@RemoteViews.RemoteView
public class AlwaysMarqueeTextView
  extends TextView
{
  public AlwaysMarqueeTextView(Context paramContext)
  {
    super(paramContext);
  }
  
  public AlwaysMarqueeTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public AlwaysMarqueeTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public boolean isFocused()
  {
    return true;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\AlwaysMarqueeTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */