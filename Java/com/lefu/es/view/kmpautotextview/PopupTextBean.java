package com.lefu.es.view.kmpautotextview;

import java.io.Serializable;

public class PopupTextBean
  implements Serializable
{
  public int mEndIndex = -1;
  public int mStartIndex = -1;
  public String mTarget;
  
  public PopupTextBean(String paramString)
  {
    this.mTarget = paramString;
  }
  
  public PopupTextBean(String paramString, int paramInt)
  {
    this.mTarget = paramString;
    this.mStartIndex = paramInt;
    if (-1 != paramInt) {
      this.mEndIndex = (paramInt + paramString.length());
    }
  }
  
  public PopupTextBean(String paramString, int paramInt1, int paramInt2)
  {
    this.mTarget = paramString;
    this.mStartIndex = paramInt1;
    this.mEndIndex = paramInt2;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\kmpautotextview\PopupTextBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */