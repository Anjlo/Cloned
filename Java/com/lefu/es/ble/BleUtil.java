package com.lefu.es.ble;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class BleUtil
{
  public static BleAdvertisedData parseAdertisedData(byte[] paramArrayOfByte)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject = null;
    if (paramArrayOfByte == null) {
      return new BleAdvertisedData(localArrayList, null);
    }
    ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte).order(ByteOrder.LITTLE_ENDIAN);
    for (;;)
    {
      if (localByteBuffer.remaining() <= 2) {}
      int i;
      do
      {
        return new BleAdvertisedData(localArrayList, (String)localObject);
        i = localByteBuffer.get();
      } while (i == 0);
      switch (localByteBuffer.get())
      {
      case 4: 
      case 5: 
      case 8: 
      default: 
        localByteBuffer.position(-1 + (i + localByteBuffer.position()));
        break;
      case 2: 
      case 3: 
        do
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Short.valueOf(localByteBuffer.getShort());
          localArrayList.add(UUID.fromString(String.format("%08x-0000-1000-8000-00805f9b34fb", arrayOfObject)));
          i = (byte)(i - 2);
        } while (i >= 2);
        break;
      case 6: 
      case 7: 
        do
        {
          long l = localByteBuffer.getLong();
          localArrayList.add(new UUID(localByteBuffer.getLong(), l));
          i = (byte)(i - 16);
        } while (i >= 16);
        break;
      case 9: 
        byte[] arrayOfByte = new byte[i - 1];
        localByteBuffer.get(arrayOfByte);
        try
        {
          String str = new String(arrayOfByte, "utf-8");
          localObject = str;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          localUnsupportedEncodingException.printStackTrace();
        }
      }
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\ble\BleUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */