package com.lefu.es.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Image
{
  public static final int ASPECT_FREE = 1;
  public static final int ASPECT_SQUARE = 2;
  private static int RESULT_PICTURE = 2;
  private static int height;
  static File tempImage = null;
  private static int width = 0;
  
  static
  {
    height = 0;
  }
  
  public static boolean bitmap2File(Bitmap paramBitmap, String paramString)
  {
    Bitmap.CompressFormat localCompressFormat = Bitmap.CompressFormat.JPEG;
    try
    {
      FileOutputStream localFileOutputStream1 = new FileOutputStream(paramString);
      localFileOutputStream2 = localFileOutputStream1;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      for (;;)
      {
        localFileNotFoundException.printStackTrace();
        FileOutputStream localFileOutputStream2 = null;
      }
    }
    return paramBitmap.compress(localCompressFormat, 100, localFileOutputStream2);
  }
  
  public static void doCropImage(Activity paramActivity, String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 != 0) && (paramInt2 != 0))
    {
      width = paramInt1;
      height = paramInt2;
    }
    tempImage = new File(paramString);
    paramActivity.startActivityForResult(Intent.createChooser(getCropImageIntent(paramInt3), "����"), RESULT_PICTURE);
  }
  
  public static Bitmap drawableToBitmap(Drawable paramDrawable)
  {
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    if (paramDrawable.getOpacity() != -1) {}
    for (Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;; localConfig = Bitmap.Config.RGB_565)
    {
      Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
      Canvas localCanvas = new Canvas(localBitmap);
      paramDrawable.setBounds(0, 0, i, j);
      paramDrawable.draw(localCanvas);
      return localBitmap;
    }
  }
  
  public static Bitmap file2Bitmap(String paramString, int paramInt)
  {
    Bitmap localBitmap = null;
    try
    {
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inSampleSize = paramInt;
      FileInputStream localFileInputStream = new FileInputStream(paramString);
      localBitmap = BitmapFactory.decodeStream(localFileInputStream, null, localOptions);
      localFileInputStream.close();
      return localBitmap;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localBitmap;
  }
  
  public static boolean file2file(String paramString1, String paramString2)
  {
    File localFile;
    boolean bool2;
    do
    {
      FileOutputStream localFileOutputStream2;
      do
      {
        try
        {
          FileInputStream localFileInputStream1 = new FileInputStream(paramString1);
          FileInputStream localFileInputStream2 = localFileInputStream1;
          FileOutputStream localFileOutputStream1;
          byte[] arrayOfByte;
          int i;
          while (localFileInputStream2 == null) {}
        }
        catch (FileNotFoundException localFileNotFoundException1)
        {
          try
          {
            localFileOutputStream1 = new FileOutputStream(paramString2);
            localFileOutputStream2 = localFileOutputStream1;
            if ((localFileInputStream2 != null) && (localFileOutputStream2 != null)) {
              arrayOfByte = new byte[102400];
            }
          }
          catch (FileNotFoundException localFileNotFoundException1)
          {
            try
            {
              for (;;)
              {
                i = localFileInputStream2.read(arrayOfByte);
                if (i <= 0)
                {
                  localFileInputStream2.close();
                  localFileOutputStream2.close();
                  bool1 = true;
                  return bool1;
                  localFileNotFoundException2 = localFileNotFoundException2;
                  localFileNotFoundException2.printStackTrace();
                  localFileInputStream2 = null;
                  continue;
                  localFileNotFoundException1 = localFileNotFoundException1;
                  localFileNotFoundException1.printStackTrace();
                  localFileOutputStream2 = null;
                }
                else
                {
                  localFileOutputStream2.write(arrayOfByte, 0, i);
                }
              }
              bool1 = false;
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
              return false;
            }
          }
        }
        bool1 = false;
      } while (localFileOutputStream2 != null);
      localFile = new File(paramString2);
      bool2 = localFile.exists();
      boolean bool1 = false;
    } while (bool2);
    try
    {
      localFile.createNewFile();
      file2file(paramString1, paramString2);
      return false;
    }
    catch (IOException localIOException) {}
    return false;
  }
  
  public static Intent getCropImageIntent(int paramInt)
  {
    Intent localIntent = new Intent("com.android.camera.action.CROP");
    try
    {
      localIntent.setDataAndType(Uri.fromFile(tempImage), "image/*");
      localIntent.putExtra("crop", "true");
      if (paramInt == 2)
      {
        localIntent.putExtra("aspectX", 1);
        localIntent.putExtra("aspectY", 1);
      }
      if ((width != 0) && (height != 0))
      {
        localIntent.putExtra("outputX", width);
        localIntent.putExtra("outputY", height);
      }
      localIntent.putExtra("output", Uri.fromFile(tempImage));
      localIntent.putExtra("outputFormat", "JPEG");
      localIntent.putExtra("return-data", true);
      return localIntent;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localIntent;
  }
  
  public static void getImage(Activity paramActivity, int paramInt)
  {
    Intent localIntent = new Intent("android.intent.action.GET_CONTENT");
    localIntent.setType("image/*");
    paramActivity.startActivityForResult(Intent.createChooser(localIntent, "ѡ��ͼƬ"), paramInt);
  }
  
  public static void getImageFromCamere(Activity paramActivity, String paramString, int paramInt)
  {
    tempImage = new File(paramString);
    Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
    localIntent.putExtra("output", Uri.fromFile(tempImage));
    paramActivity.startActivityForResult(localIntent, paramInt);
  }
  
  public static void getImageFromCamere(Context paramContext)
  {
    paramContext.startActivity(new Intent("android.media.action.IMAGE_CAPTURE"));
  }
  
  public static Bitmap toRoundCorner(Bitmap paramBitmap, int paramInt)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint();
    Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
    RectF localRectF = new RectF(localRect);
    float f = paramInt;
    localPaint.setAntiAlias(true);
    localCanvas.drawARGB(0, 0, 0, 0);
    localPaint.setColor(-12434878);
    localCanvas.drawRoundRect(localRectF, f, f, localPaint);
    localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
    return localBitmap;
  }
  
  public static Drawable zoomDrawable(Drawable paramDrawable, int paramInt1, int paramInt2)
  {
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    Bitmap localBitmap = drawableToBitmap(paramDrawable);
    Matrix localMatrix = new Matrix();
    localMatrix.postScale(paramInt1 / i, paramInt2 / j);
    return new BitmapDrawable(Bitmap.createBitmap(localBitmap, 0, 0, i, j, localMatrix, true));
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\util\Image.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */