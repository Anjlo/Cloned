package com.lefu.es.util;

import android.content.Context;
import android.content.Intent;

public class BTUtils
{
  public static void enableDiscoverable(Context paramContext)
  {
    Intent localIntent = new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
    localIntent.putExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", 300);
    paramContext.startActivity(localIntent);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\util\BTUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */