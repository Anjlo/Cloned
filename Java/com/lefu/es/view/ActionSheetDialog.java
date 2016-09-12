package com.lefu.es.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ActionSheetDialog
{
  private Context context;
  private Dialog dialog;
  private Display display;
  private LinearLayout lLayout_content;
  private ScrollView sLayout_content;
  private List<SheetItem> sheetItemList;
  private boolean showTitle = false;
  private TextView txt_cancel;
  private TextView txt_title;
  
  public ActionSheetDialog(Context paramContext)
  {
    this.context = paramContext;
    this.display = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
  }
  
  private void setSheetItems()
  {
    if ((this.sheetItemList == null) || (this.sheetItemList.size() <= 0)) {
      return;
    }
    int i = this.sheetItemList.size();
    if (i >= 7)
    {
      LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)this.sLayout_content.getLayoutParams();
      localLayoutParams.height = (this.display.getHeight() / 2);
      this.sLayout_content.setLayoutParams(localLayoutParams);
    }
    int j = 1;
    label73:
    final int k;
    SheetItemColor localSheetItemColor;
    final OnSheetItemClickListener localOnSheetItemClickListener;
    TextView localTextView;
    if (j <= i)
    {
      k = j;
      SheetItem localSheetItem = (SheetItem)this.sheetItemList.get(j - 1);
      String str = localSheetItem.name;
      localSheetItemColor = localSheetItem.color;
      localOnSheetItemClickListener = localSheetItem.itemClickListener;
      localTextView = new TextView(this.context);
      localTextView.setText(str);
      localTextView.setTextSize(18.0F);
      localTextView.setGravity(17);
      if (i != 1) {
        break label264;
      }
      if (!this.showTitle) {
        break label254;
      }
      localTextView.setBackgroundResource(2130837506);
      label171:
      if (localSheetItemColor != null) {
        break label341;
      }
      localTextView.setTextColor(Color.parseColor(SheetItemColor.Blue.getName()));
    }
    for (;;)
    {
      localTextView.setLayoutParams(new LinearLayout.LayoutParams(-1, (int)(0.5F + 45.0F * this.context.getResources().getDisplayMetrics().density)));
      localTextView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          localOnSheetItemClickListener.onClick(k);
          ActionSheetDialog.this.dialog.dismiss();
        }
      });
      this.lLayout_content.addView(localTextView);
      j++;
      break label73;
      break;
      label254:
      localTextView.setBackgroundResource(2130837512);
      break label171;
      label264:
      if (this.showTitle)
      {
        if ((j >= 1) && (j < i))
        {
          localTextView.setBackgroundResource(2130837509);
          break label171;
        }
        localTextView.setBackgroundResource(2130837506);
        break label171;
      }
      if (j == 1)
      {
        localTextView.setBackgroundResource(2130837515);
        break label171;
      }
      if (j < i)
      {
        localTextView.setBackgroundResource(2130837509);
        break label171;
      }
      localTextView.setBackgroundResource(2130837506);
      break label171;
      label341:
      localTextView.setTextColor(Color.parseColor(localSheetItemColor.getName()));
    }
  }
  
  public ActionSheetDialog addSheetItem(String paramString, SheetItemColor paramSheetItemColor, OnSheetItemClickListener paramOnSheetItemClickListener)
  {
    if (this.sheetItemList == null) {
      this.sheetItemList = new ArrayList();
    }
    this.sheetItemList.add(new SheetItem(paramString, paramSheetItemColor, paramOnSheetItemClickListener));
    return this;
  }
  
  public ActionSheetDialog builder()
  {
    View localView = LayoutInflater.from(this.context).inflate(2130903086, null);
    localView.setMinimumWidth(this.display.getWidth());
    this.sLayout_content = ((ScrollView)localView.findViewById(2131165469));
    this.lLayout_content = ((LinearLayout)localView.findViewById(2131165470));
    this.txt_title = ((TextView)localView.findViewById(2131165468));
    this.txt_cancel = ((TextView)localView.findViewById(2131165471));
    this.txt_cancel.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ActionSheetDialog.this.dialog.dismiss();
      }
    });
    this.dialog = new Dialog(this.context, 2131427361);
    this.dialog.setContentView(localView);
    Window localWindow = this.dialog.getWindow();
    localWindow.setGravity(83);
    WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
    localLayoutParams.x = 0;
    localLayoutParams.y = 0;
    localWindow.setAttributes(localLayoutParams);
    return this;
  }
  
  public ActionSheetDialog setCancelable(boolean paramBoolean)
  {
    this.dialog.setCancelable(paramBoolean);
    return this;
  }
  
  public ActionSheetDialog setCanceledOnTouchOutside(boolean paramBoolean)
  {
    this.dialog.setCanceledOnTouchOutside(paramBoolean);
    return this;
  }
  
  public ActionSheetDialog setTitle(String paramString)
  {
    this.showTitle = true;
    this.txt_title.setVisibility(0);
    this.txt_title.setText(paramString);
    return this;
  }
  
  public void show()
  {
    setSheetItems();
    this.dialog.show();
  }
  
  public static abstract interface OnSheetItemClickListener
  {
    public abstract void onClick(int paramInt);
  }
  
  public class SheetItem
  {
    ActionSheetDialog.SheetItemColor color;
    ActionSheetDialog.OnSheetItemClickListener itemClickListener;
    String name;
    
    public SheetItem(String paramString, ActionSheetDialog.SheetItemColor paramSheetItemColor, ActionSheetDialog.OnSheetItemClickListener paramOnSheetItemClickListener)
    {
      this.name = paramString;
      this.color = paramSheetItemColor;
      this.itemClickListener = paramOnSheetItemClickListener;
    }
  }
  
  public static enum SheetItemColor
  {
    Black("#333333"),  Red("#FD4A2E"),  Blue("#0690e8");
    
    private String name;
    
    private SheetItemColor(String paramString1)
    {
      this.name = paramString1;
    }
    
    public String getName()
    {
      return this.name;
    }
    
    public void setName(String paramString)
    {
      this.name = paramString;
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\ActionSheetDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */