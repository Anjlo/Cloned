package com.lefu.es.constant;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import java.io.FileNotFoundException;

public class imageUtil
{
  public static Bitmap getBitmapfromPath(String paramString)
  {
    Bitmap localBitmap = null;
    if (paramString != null)
    {
      boolean bool = "".equals(paramString);
      localBitmap = null;
      if (!bool)
      {
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inSampleSize = 2;
        localBitmap = BitmapFactory.decodeFile(paramString, localOptions);
      }
    }
    return localBitmap;
  }
  
  public Bitmap getBitmapTodifferencePath(String paramString, Context paramContext)
  {
    if (paramString.length() < 7) {
      return null;
    }
    if ("content".equals(paramString.substring(0, 7))) {
      try
      {
        BitmapFactory.Options localOptions2 = new BitmapFactory.Options();
        localOptions2.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(paramContext.getContentResolver().openInputStream(Uri.parse(paramString)), null, localOptions2);
        if (localOptions2.outWidth > 100)
        {
          localOptions2.inSampleSize = (1 + (1 + localOptions2.outWidth / 100));
          localOptions2.outWidth = 100;
          localOptions2.outHeight /= localOptions2.inSampleSize;
        }
        localOptions2.inJustDecodeBounds = false;
        localOptions2.inPurgeable = true;
        localOptions2.inInputShareable = true;
        Bitmap localBitmap = BitmapFactory.decodeStream(paramContext.getContentResolver().openInputStream(Uri.parse(paramString)), null, localOptions2);
        return localBitmap;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        localFileNotFoundException.printStackTrace();
        return null;
      }
    }
    BitmapFactory.Options localOptions1 = new BitmapFactory.Options();
    localOptions1.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(paramString, localOptions1);
    if (localOptions1.outWidth > 100)
    {
      localOptions1.inSampleSize = (1 + (1 + localOptions1.outWidth / 100));
      localOptions1.outWidth = 100;
      localOptions1.outHeight /= localOptions1.inSampleSize;
    }
    localOptions1.inJustDecodeBounds = false;
    localOptions1.inPurgeable = true;
    localOptions1.inInputShareable = true;
    return BitmapFactory.decodeFile(paramString, localOptions1);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\constant\imageUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */