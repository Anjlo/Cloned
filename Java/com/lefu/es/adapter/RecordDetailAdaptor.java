package com.lefu.es.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.util.StringUtils;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.view.MyTextView3;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecordDetailAdaptor
  extends BaseAdapter
{
  Context cont;
  private LayoutInflater inflater;
  ListView listView;
  Locale local = Locale.getDefault();
  private int resource;
  String scaleT = "";
  public int selectedPosition = -1;
  public float selectedPositionY = 0.0F;
  private List<Records> users;
  private int weightType = 1;
  
  public RecordDetailAdaptor(Context paramContext, List<Records> paramList, ListView paramListView, int paramInt)
  {
    this.listView = paramListView;
    this.users = paramList;
    this.resource = paramInt;
    this.inflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
    this.cont = paramContext;
    Log.v("tag", "lsw:" + UtilConstants.CHOICE_KG);
    if ((paramList != null) && (paramList.size() > 0))
    {
      this.scaleT = ((Records)paramList.get(0)).getScaleType();
      if (!this.scaleT.equals(UtilConstants.KITCHEN_SCALE)) {
        break label247;
      }
      if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) {
        this.weightType = 6;
      }
    }
    else
    {
      return;
    }
    if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB))
    {
      this.weightType = 5;
      return;
    }
    if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_KG))
    {
      this.weightType = 1;
      return;
    }
    if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ML))
    {
      this.weightType = 7;
      return;
    }
    if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ML2))
    {
      this.weightType = 8;
      return;
    }
    this.weightType = 1;
    return;
    label247:
    if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_KG))
    {
      this.weightType = 1;
      return;
    }
    if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_LB))
    {
      if ((this.scaleT.equals(UtilConstants.BODY_SCALE)) || (this.scaleT.equals(UtilConstants.BATHROOM_SCALE)))
      {
        this.weightType = 2;
        return;
      }
      this.weightType = 5;
      return;
    }
    if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_ST))
    {
      if (this.scaleT.equals(UtilConstants.BODY_SCALE))
      {
        this.weightType = 3;
        return;
      }
      if (this.scaleT.equals(UtilConstants.BATHROOM_SCALE))
      {
        this.weightType = 4;
        return;
      }
      this.weightType = 5;
      return;
    }
    if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_FATLB))
    {
      this.weightType = 5;
      return;
    }
    this.weightType = 1;
  }
  
  public int getCount()
  {
    return this.users.size();
  }
  
  public Object getItem(int paramInt)
  {
    return this.users.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    TextView localTextView1;
    MyTextView3 localMyTextView3;
    TextView localTextView2;
    LinearLayout localLinearLayout;
    ImageView localImageView;
    Records localRecords;
    if (paramView == null)
    {
      paramView = this.inflater.inflate(this.resource, null);
      localTextView1 = (TextView)paramView.findViewById(2131165445);
      localMyTextView3 = (MyTextView3)paramView.findViewById(2131165446);
      localTextView2 = (TextView)paramView.findViewById(2131165447);
      localLinearLayout = (LinearLayout)paramView.findViewById(2131165444);
      localImageView = (ImageView)paramView.findViewById(2131165448);
      ViewCache localViewCache2 = new ViewCache(null);
      localViewCache2.groupView = localTextView1;
      localViewCache2.nameView = localMyTextView3;
      localViewCache2.bmiView = localTextView2;
      localViewCache2.rlayout = localLinearLayout;
      localViewCache2.photoView = localImageView;
      paramView.setTag(localViewCache2);
      localRecords = (Records)this.users.get(paramInt);
      if ((localRecords.getRecordTime() == null) || ("".equals(localRecords.getRecordTime().trim()))) {
        localRecords.setRecordTime("No Date");
      }
      if (this.selectedPosition != paramInt) {
        break label427;
      }
      localTextView1.setSelected(true);
      localMyTextView3.setPressed(true);
      localTextView2.setPressed(true);
      localTextView1.setTextColor(this.cont.getResources().getColor(2131230727));
      localMyTextView3.setTextWhiteColor();
      localTextView2.setTextColor(this.cont.getResources().getColor(2131230727));
      localLinearLayout.setBackgroundDrawable(this.cont.getResources().getDrawable(2130837606));
      label252:
      Date localDate = UtilTooth.stringToTime(localRecords.getRecordTime());
      if (localDate != null) {
        localTextView1.setText(StringUtils.getDateString(localDate, 5));
      }
      if ((localRecords.getRphoto() == null) || (localRecords.getRphoto().length() <= 3)) {
        break label495;
      }
      localImageView.setVisibility(0);
      label304:
      switch (this.weightType)
      {
      }
    }
    for (;;)
    {
      if (!UtilConstants.KITCHEN_SCALE.equals(UtilConstants.CURRENT_SCALE)) {
        break label1673;
      }
      localTextView2.setText(localRecords.getRphoto());
      return paramView;
      ViewCache localViewCache1 = (ViewCache)paramView.getTag();
      localTextView1 = localViewCache1.groupView;
      localMyTextView3 = localViewCache1.nameView;
      localTextView2 = localViewCache1.bmiView;
      localLinearLayout = localViewCache1.rlayout;
      localImageView = localViewCache1.photoView;
      break;
      label427:
      localTextView1.setSelected(false);
      localMyTextView3.setPressed(false);
      localTextView2.setPressed(false);
      localTextView1.setTextColor(this.cont.getResources().getColor(2131230732));
      localMyTextView3.setTexBlackColor();
      localTextView2.setTextColor(this.cont.getResources().getColor(2131230732));
      localLinearLayout.setBackgroundColor(-1);
      break label252;
      label495:
      localImageView.setVisibility(4);
      break label304;
      String str9;
      if (this.scaleT.equals(UtilConstants.BABY_SCALE)) {
        str9 = UtilTooth.round(localRecords.getRweight(), 2);
      }
      for (;;)
      {
        if (this.selectedPosition != paramInt)
        {
          if (UtilConstants.KITCHEN_SCALE.equals(UtilConstants.CURRENT_SCALE))
          {
            localMyTextView3.setTexts(str9 + this.cont.getText(2131361897), null);
            break;
            str9 = localRecords.getRweight();
            continue;
          }
          localMyTextView3.setTexts(str9 + this.cont.getText(2131361889), null);
          break;
        }
      }
      if (UtilConstants.KITCHEN_SCALE.equals(UtilConstants.CURRENT_SCALE))
      {
        localMyTextView3.setTexts(str9 + this.cont.getText(2131361897), null, true);
      }
      else
      {
        localMyTextView3.setTexts(str9 + this.cont.getText(2131361889), null, true);
        continue;
        String str8 = UtilTooth.onePoint(UtilTooth.kgToLB_ForFatScale3(localRecords.getRweight()));
        if (this.selectedPosition != paramInt)
        {
          localMyTextView3.setTexts(str8 + this.cont.getText(2131361890), null);
        }
        else
        {
          localMyTextView3.setTexts(str8 + this.cont.getText(2131361890), null, true);
          continue;
          String[] arrayOfString = UtilTooth.kgToStLbForScaleFat2(localRecords.getRweight());
          if ((arrayOfString[1] != null) && (arrayOfString[1].indexOf("/") > 0))
          {
            if (this.selectedPosition != paramInt) {
              localMyTextView3.setTexts(arrayOfString[0], arrayOfString[1]);
            } else {
              localMyTextView3.setTexts(arrayOfString[0], arrayOfString[1], true);
            }
          }
          else if (this.selectedPosition != paramInt)
          {
            localMyTextView3.setTexts(arrayOfString[0] + this.cont.getText(2131361892), null);
          }
          else
          {
            localMyTextView3.setTexts(arrayOfString[0] + this.cont.getText(2131361892), null, true);
            continue;
            String str7 = UtilTooth.kgToStLb(localRecords.getRweight());
            if (this.selectedPosition != paramInt)
            {
              localMyTextView3.setTexts(str7 + this.cont.getText(2131361892), null);
            }
            else
            {
              localMyTextView3.setTexts(str7 + this.cont.getText(2131361892), null, true);
              continue;
              if (UtilConstants.KITCHEN_SCALE.equals(UtilConstants.CURRENT_SCALE))
              {
                String str6 = UtilTooth.kgToFloz(localRecords.getRweight());
                if (this.selectedPosition != paramInt) {
                  localMyTextView3.setTexts(str6 + this.cont.getText(2131361900), null);
                } else {
                  localMyTextView3.setTexts(str6 + this.cont.getText(2131361900), null, true);
                }
              }
              else
              {
                String str4 = UtilTooth.kgToLbForScaleBaby(localRecords.getRweight());
                if ((str4 != null) && (str4.indexOf(":") > 0))
                {
                  String str5 = str4.substring(0, str4.indexOf(":")) + this.cont.getText(2131361890) + ":" + str4.substring(1 + str4.indexOf(":"), str4.length()) + this.cont.getText(2131361891);
                  if (this.selectedPosition != paramInt) {
                    localMyTextView3.setTexts(str5, null);
                  } else {
                    localMyTextView3.setTexts(str5, null, true);
                  }
                }
                else if (this.selectedPosition != paramInt)
                {
                  localMyTextView3.setTexts(str4, null);
                }
                else
                {
                  localMyTextView3.setTexts(str4, null, true);
                  continue;
                  String str3 = UtilTooth.kgToLBoz(localRecords.getRweight());
                  if (this.selectedPosition != paramInt)
                  {
                    localMyTextView3.setTexts(str3 + this.cont.getText(2131361893), null);
                  }
                  else
                  {
                    localMyTextView3.setTexts(str3 + this.cont.getText(2131361893), null, true);
                    continue;
                    String str2 = localRecords.getRweight();
                    if (this.selectedPosition != paramInt)
                    {
                      localMyTextView3.setTexts(str2 + this.cont.getText(2131361899), null);
                    }
                    else
                    {
                      localMyTextView3.setTexts(str2 + this.cont.getText(2131361899), null, true);
                      continue;
                      String str1 = UtilTooth.kgToML(localRecords.getRweight());
                      if (this.selectedPosition != paramInt) {
                        localMyTextView3.setTexts(str1 + this.cont.getText(2131361901), null);
                      } else {
                        localMyTextView3.setTexts(str1 + this.cont.getText(2131361901), null, true);
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    label1673:
    localTextView2.setText(UtilTooth.myround(UtilTooth.countBMI2(localRecords.getRweight(), UtilConstants.CURRENT_USER.getBheigth() / 100.0F)));
    return paramView;
  }
  
  public void setSelectedPosition(int paramInt)
  {
    this.selectedPosition = paramInt;
  }
  
  private final class ViewCache
  {
    public TextView bmiView;
    public TextView groupView;
    public MyTextView3 nameView;
    public ImageView photoView;
    public LinearLayout rlayout;
    
    private ViewCache() {}
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\adapter\RecordDetailAdaptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */