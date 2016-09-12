package com.lefu.es.util;

import android.content.Context;
import android.os.Environment;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils
{
  private String FILESPATH;
  private String SDPATH;
  private Context context;
  private boolean hasSD = false;
  
  public FileUtils(Context paramContext)
  {
    this.context = paramContext;
    this.hasSD = Environment.getExternalStorageState().equals("mounted");
    this.SDPATH = Environment.getExternalStorageDirectory().getPath();
    this.FILESPATH = this.context.getFilesDir().getPath();
  }
  
  public File createSDFile(String paramString)
    throws IOException
  {
    File localFile = new File(this.SDPATH + "//" + paramString);
    if (!localFile.exists()) {
      localFile.createNewFile();
    }
    return localFile;
  }
  
  public boolean deleteSDFile(String paramString)
  {
    File localFile = new File(this.SDPATH + "//" + paramString);
    if ((localFile == null) || (!localFile.exists()) || (localFile.isDirectory())) {
      return false;
    }
    return localFile.delete();
  }
  
  public String getFILESPATH()
  {
    return this.FILESPATH;
  }
  
  public String getSDPATH()
  {
    return this.SDPATH;
  }
  
  public boolean hasSD()
  {
    return this.hasSD;
  }
  
  public String readSDFile(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    File localFile = new File(this.SDPATH + "//" + paramString);
    for (;;)
    {
      try
      {
        FileInputStream localFileInputStream = new FileInputStream(localFile);
        i = localFileInputStream.read();
        if (i != -1) {
          continue;
        }
        localFileInputStream.close();
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        int i;
        char c;
        localFileNotFoundException.printStackTrace();
        continue;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        continue;
      }
      return localStringBuffer.toString();
      c = (char)i;
      localStringBuffer.append(c);
    }
  }
  
  public void writeSDFile(String paramString1, String paramString2)
  {
    try
    {
      FileWriter localFileWriter = new FileWriter(this.SDPATH + "//" + paramString2);
      File localFile = new File(this.SDPATH + "//" + paramString2);
      localFileWriter.write(paramString1);
      DataOutputStream localDataOutputStream = new DataOutputStream(new FileOutputStream(localFile));
      localDataOutputStream.writeShort(2);
      localDataOutputStream.writeUTF("");
      localFileWriter.flush();
      localFileWriter.close();
      return;
    }
    catch (Exception localException) {}
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\util\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */