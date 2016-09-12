package com.lefu.es.service;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExitApplication
  extends Application
{
  private static ExitApplication ea;
  private List<Activity> list = new ArrayList();
  
  public static ExitApplication getInstance()
  {
    if (ea == null) {
      ea = new ExitApplication();
    }
    return ea;
  }
  
  public void addActivity(Activity paramActivity)
  {
    this.list.add(paramActivity);
  }
  
  public void exit(Context paramContext)
  {
    Iterator localIterator = this.list.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      ((Activity)localIterator.next()).finish();
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\service\ExitApplication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */