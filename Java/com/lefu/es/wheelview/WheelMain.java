package com.lefu.es.wheelview;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.lefu.es.util.otherUtil;
import java.util.Arrays;
import java.util.List;

public class WheelMain
{
  private static int END_YEAR = 2049;
  private static int START_YEAR = 1915;
  private boolean isLinter;
  public int screenheight;
  private View view;
  private WheelView wv_day;
  private WheelView wv_hours;
  private WheelView wv_mins;
  private WheelView wv_month;
  private WheelView wv_year;
  
  public WheelMain(View paramView)
  {
    this.view = paramView;
    setView(paramView);
    viewInit();
  }
  
  public static int getEND_YEAR()
  {
    return END_YEAR;
  }
  
  public static int getSTART_YEAR()
  {
    return START_YEAR;
  }
  
  public static void setEND_YEAR(int paramInt)
  {
    END_YEAR = paramInt;
  }
  
  public static void setSTART_YEAR(int paramInt)
  {
    START_YEAR = paramInt;
  }
  
  public int getDay()
  {
    return 1 + this.wv_day.getCurrentItem();
  }
  
  public int getHours()
  {
    return this.wv_hours.getCurrentItem();
  }
  
  public int getMin()
  {
    return this.wv_mins.getCurrentItem();
  }
  
  public int getMonth()
  {
    return 1 + this.wv_month.getCurrentItem();
  }
  
  public String getTime()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(1 + this.wv_month.getCurrentItem()).append("-").append(1 + this.wv_day.getCurrentItem()).append("-").append(this.wv_year.getCurrentItem() + START_YEAR);
    return localStringBuffer.toString();
  }
  
  public View getView()
  {
    return this.view;
  }
  
  public int getYear()
  {
    return this.wv_year.getCurrentItem() + START_YEAR;
  }
  
  public void initDateTimePicker(int paramInt1, int paramInt2, int paramInt3)
  {
    this.wv_year.setVisibility(0);
    this.wv_month.setVisibility(0);
    this.wv_day.setVisibility(0);
    this.wv_hours.setVisibility(8);
    this.wv_mins.setVisibility(8);
    String[] arrayOfString1 = { "1", "3", "5", "7", "8", "10", "12" };
    String[] arrayOfString2 = { "4", "6", "9", "11" };
    final List localList1 = Arrays.asList(arrayOfString1);
    final List localList2 = Arrays.asList(arrayOfString2);
    this.wv_year.setLabel("Year");
    this.wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));
    this.wv_year.setCyclic(true);
    this.wv_year.setCurrentItem(paramInt3 - START_YEAR);
    this.wv_year.setVisibleItems(5);
    this.wv_month.setLabel("Month");
    this.wv_month.setAdapter(new NumericWheelAdapter(1, 12));
    this.wv_month.setCyclic(true);
    this.wv_month.setCurrentItem(paramInt1);
    this.wv_month.setVisibleItems(5);
    this.wv_day.setCyclic(true);
    this.wv_day.setLabel("Day");
    if (this.isLinter)
    {
      if (!localList1.contains(String.valueOf(paramInt1 + 1))) {
        break label354;
      }
      this.wv_day.setAdapter(new NumericWheelAdapter(1, 31));
    }
    for (;;)
    {
      this.wv_day.setCurrentItem(paramInt2 - 1);
      this.wv_day.setVisibleItems(5);
      OnWheelChangedListener local3 = new OnWheelChangedListener()
      {
        public void onChanged(WheelView paramAnonymousWheelView, int paramAnonymousInt1, int paramAnonymousInt2)
        {
          int i;
          if (WheelMain.this.isLinter)
          {
            i = paramAnonymousInt2 + WheelMain.START_YEAR;
            if (localList1.contains(String.valueOf(1 + WheelMain.this.wv_month.getCurrentItem()))) {
              WheelMain.this.wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            }
          }
          else
          {
            return;
          }
          if (localList2.contains(String.valueOf(1 + WheelMain.this.wv_month.getCurrentItem())))
          {
            WheelMain.this.wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            return;
          }
          if (((i % 4 == 0) && (i % 100 != 0)) || (i % 400 == 0))
          {
            WheelMain.this.wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            return;
          }
          WheelMain.this.wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
      };
      OnWheelChangedListener local4 = new OnWheelChangedListener()
      {
        public void onChanged(WheelView paramAnonymousWheelView, int paramAnonymousInt1, int paramAnonymousInt2)
        {
          int i;
          if (WheelMain.this.isLinter)
          {
            i = paramAnonymousInt2 + 1;
            if (localList1.contains(String.valueOf(i))) {
              WheelMain.this.wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            }
          }
          else
          {
            return;
          }
          if (localList2.contains(String.valueOf(i)))
          {
            WheelMain.this.wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            return;
          }
          if ((((WheelMain.this.wv_year.getCurrentItem() + WheelMain.START_YEAR) % 4 == 0) && ((WheelMain.this.wv_year.getCurrentItem() + WheelMain.START_YEAR) % 100 != 0)) || ((WheelMain.this.wv_year.getCurrentItem() + WheelMain.START_YEAR) % 400 == 0))
          {
            WheelMain.this.wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            return;
          }
          WheelMain.this.wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
      };
      this.wv_year.addChangingListener(local3);
      this.wv_month.addChangingListener(local4);
      return;
      label354:
      if (localList2.contains(String.valueOf(paramInt1 + 1))) {
        this.wv_day.setAdapter(new NumericWheelAdapter(1, 30));
      } else if (((paramInt3 % 4 == 0) && (paramInt3 % 100 != 0)) || (paramInt3 % 400 == 0)) {
        this.wv_day.setAdapter(new NumericWheelAdapter(1, 29));
      } else {
        this.wv_day.setAdapter(new NumericWheelAdapter(1, 28));
      }
    }
  }
  
  public void setTime(final int paramInt1, final int paramInt2, final int paramInt3)
  {
    int i = 2 * (this.screenheight / 100);
    this.wv_day.TEXT_SIZE = i;
    this.wv_month.TEXT_SIZE = i;
    this.wv_year.TEXT_SIZE = i;
    this.isLinter = true;
    initDateTimePicker(paramInt1, paramInt2, paramInt3);
    final Button localButton1 = (Button)this.view.findViewById(2131165411);
    final Button localButton2 = (Button)this.view.findViewById(2131165412);
    localButton1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WheelMain.this.isLinter = true;
        localButton1.setBackgroundResource(2130837628);
        localButton2.setBackgroundResource(2130837711);
        WheelMain.this.initDateTimePicker(paramInt1, paramInt2, paramInt3);
      }
    });
    localButton2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WheelMain.this.isLinter = false;
        localButton2.setBackgroundResource(2130837628);
        localButton1.setBackgroundResource(2130837711);
        WheelMain.this.showlunarTimePicker();
      }
    });
  }
  
  public void setView(View paramView)
  {
    this.view = paramView;
  }
  
  public void showHours(int paramInt1, int paramInt2)
  {
    this.wv_year.setVisibility(8);
    this.wv_month.setVisibility(8);
    this.wv_day.setVisibility(8);
    this.wv_hours.setVisibility(0);
    this.wv_mins.setVisibility(0);
    this.wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
    this.wv_hours.setCyclic(true);
    this.wv_hours.setLabel("Hour");
    this.wv_hours.setCurrentItem(paramInt1);
    this.wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
    this.wv_mins.setCyclic(true);
    this.wv_mins.setLabel("Mins");
    this.wv_mins.setCurrentItem(paramInt2);
    int i = 4 * (this.screenheight / 100);
    this.wv_hours.TEXT_SIZE = i;
    this.wv_mins.TEXT_SIZE = i;
  }
  
  public void showlunarTimePicker()
  {
    this.wv_year.setVisibility(0);
    this.wv_month.setVisibility(0);
    this.wv_day.setVisibility(0);
    this.wv_hours.setVisibility(8);
    this.wv_mins.setVisibility(8);
    String[] arrayOfString1 = otherUtil.getYera();
    String[] arrayOfString2 = { "正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "冬月", "腊月" };
    String[] arrayOfString3 = { "初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十" };
    this.wv_year.setCyclic(true);
    this.wv_year.setVisibleItems(5);
    this.wv_year.setLabel("");
    this.wv_year.setAdapter(new ArrayWheelAdapter(arrayOfString1, 10));
    this.wv_month.setCyclic(true);
    this.wv_month.setVisibleItems(5);
    this.wv_month.setLabel("");
    this.wv_month.setAdapter(new ArrayWheelAdapter(arrayOfString2, 5));
    this.wv_day.setCyclic(true);
    this.wv_day.setVisibleItems(5);
    this.wv_day.setLabel("");
    this.wv_day.setAdapter(new ArrayWheelAdapter(arrayOfString3, 5));
  }
  
  public void viewInit()
  {
    this.wv_year = ((WheelView)this.view.findViewById(2131165415));
    this.wv_month = ((WheelView)this.view.findViewById(2131165413));
    this.wv_day = ((WheelView)this.view.findViewById(2131165414));
    this.wv_hours = ((WheelView)this.view.findViewById(2131165416));
    this.wv_mins = ((WheelView)this.view.findViewById(2131165417));
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\wheelview\WheelMain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */