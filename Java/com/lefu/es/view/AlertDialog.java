package com.lefu.es.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AlertDialog
{
  private Button btn_neg;
  private Button btn_pos;
  private Context context;
  private Dialog dialog;
  private Display display;
  private ImageView img_line;
  private LinearLayout lLayout_bg;
  private boolean showMsg = false;
  private boolean showNegBtn = false;
  private boolean showPosBtn = false;
  private boolean showTitle = false;
  private TextView txt_msg;
  private TextView txt_title;
  
  public AlertDialog(Context paramContext)
  {
    this.context = paramContext;
    this.display = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
  }
  
  private void setLayout()
  {
    if ((!this.showTitle) && (!this.showMsg))
    {
      this.txt_title.setText("");
      this.txt_title.setVisibility(8);
    }
    if (this.showTitle) {
      this.txt_title.setVisibility(0);
    }
    if (this.showMsg) {
      this.txt_msg.setVisibility(0);
    }
    if ((!this.showPosBtn) && (!this.showNegBtn))
    {
      this.btn_pos.setText("");
      this.btn_pos.setVisibility(0);
      this.btn_pos.setBackgroundResource(2130837526);
      this.btn_pos.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          AlertDialog.this.dialog.dismiss();
        }
      });
    }
    if ((this.showPosBtn) && (this.showNegBtn))
    {
      this.btn_pos.setVisibility(0);
      this.btn_pos.setBackgroundResource(2130837525);
      this.btn_neg.setVisibility(0);
      this.btn_neg.setBackgroundResource(2130837524);
      this.img_line.setVisibility(0);
    }
    if ((this.showPosBtn) && (!this.showNegBtn))
    {
      this.btn_pos.setVisibility(0);
      this.btn_pos.setBackgroundResource(2130837526);
    }
    if ((!this.showPosBtn) && (this.showNegBtn))
    {
      this.btn_neg.setVisibility(0);
      this.btn_neg.setBackgroundResource(2130837526);
    }
  }
  
  public AlertDialog builder()
  {
    View localView = LayoutInflater.from(this.context).inflate(2130903087, null);
    this.lLayout_bg = ((LinearLayout)localView.findViewById(2131165472));
    this.txt_title = ((TextView)localView.findViewById(2131165468));
    this.txt_title.setVisibility(8);
    this.txt_msg = ((TextView)localView.findViewById(2131165473));
    this.txt_msg.setVisibility(8);
    this.btn_neg = ((Button)localView.findViewById(2131165474));
    this.btn_neg.setVisibility(8);
    this.btn_pos = ((Button)localView.findViewById(2131165476));
    this.btn_pos.setVisibility(8);
    this.img_line = ((ImageView)localView.findViewById(2131165475));
    this.img_line.setVisibility(8);
    this.dialog = new Dialog(this.context, 2131427360);
    this.dialog.setContentView(localView);
    this.lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int)(0.85D * this.display.getWidth()), -2));
    return this;
  }
  
  public AlertDialog setCancelable(boolean paramBoolean)
  {
    this.dialog.setCancelable(paramBoolean);
    return this;
  }
  
  public AlertDialog setMsg(String paramString)
  {
    this.showMsg = true;
    if ("".equals(paramString))
    {
      this.txt_msg.setText("");
      return this;
    }
    this.txt_msg.setText(paramString);
    return this;
  }
  
  public AlertDialog setNegativeButton(String paramString, final View.OnClickListener paramOnClickListener)
  {
    this.showNegBtn = true;
    if ("".equals(paramString)) {
      this.btn_neg.setText("");
    }
    for (;;)
    {
      this.btn_neg.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (paramOnClickListener != null) {
            paramOnClickListener.onClick(paramAnonymousView);
          }
          AlertDialog.this.dialog.dismiss();
        }
      });
      return this;
      this.btn_neg.setText(paramString);
    }
  }
  
  public AlertDialog setPositiveButton(String paramString, final View.OnClickListener paramOnClickListener)
  {
    this.showPosBtn = true;
    if ("".equals(paramString)) {
      this.btn_pos.setText("");
    }
    for (;;)
    {
      this.btn_pos.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          paramOnClickListener.onClick(paramAnonymousView);
          AlertDialog.this.dialog.dismiss();
        }
      });
      return this;
      this.btn_pos.setText(paramString);
    }
  }
  
  public AlertDialog setTitle(String paramString)
  {
    this.showTitle = true;
    if ("".equals(paramString))
    {
      this.txt_title.setText("");
      return this;
    }
    this.txt_title.setText(paramString);
    return this;
  }
  
  public void show()
  {
    setLayout();
    this.dialog.show();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\view\AlertDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */