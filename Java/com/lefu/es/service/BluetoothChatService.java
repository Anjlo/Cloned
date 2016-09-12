package com.lefu.es.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.lefu.es.constant.BTConstant;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BluetoothChatService
{
  private static final boolean D = true;
  private static final String NAME_SECURE = "BluetoothChatSecure";
  private static final String TAG = "BluetoothChatService";
  public static int mState;
  private final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
  private ConnectThread mConnectThread;
  private ConnectedThread mConnectedThread;
  private final Handler mHandler;
  private AcceptThread mInsecureAcceptThread;
  private AcceptThread mSecureAcceptThread;
  
  public BluetoothChatService(Context paramContext, Handler paramHandler)
  {
    mState = 0;
    this.mHandler = paramHandler;
  }
  
  private void connectionFailed()
  {
    Message localMessage = this.mHandler.obtainMessage(5);
    Bundle localBundle = new Bundle();
    localBundle.putString("toast", "Unable to connect device");
    localMessage.setData(localBundle);
    this.mHandler.sendMessage(localMessage);
    start();
  }
  
  private void connectionLost()
  {
    Message localMessage = this.mHandler.obtainMessage(5);
    Bundle localBundle = new Bundle();
    localBundle.putString("toast", "Device connection was lost");
    localMessage.setData(localBundle);
    this.mHandler.sendMessage(localMessage);
    start();
  }
  
  private void setState(int paramInt)
  {
    try
    {
      mState = paramInt;
      if (this.mHandler != null) {
        this.mHandler.obtainMessage(1, paramInt, -1).sendToTarget();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void connect(BluetoothDevice paramBluetoothDevice, boolean paramBoolean)
  {
    try
    {
      Log.d("BluetoothChatService", "connect to: " + paramBluetoothDevice);
      try
      {
        if ((mState == 2) && (this.mConnectThread != null))
        {
          this.mConnectThread.cancel();
          this.mConnectThread = null;
        }
        if (this.mConnectedThread != null)
        {
          this.mConnectedThread.cancel();
          this.mConnectedThread = null;
        }
        this.mConnectThread = new ConnectThread(paramBluetoothDevice, paramBoolean);
        this.mConnectThread.start();
        setState(2);
      }
      catch (Exception localException)
      {
        for (;;)
        {
          connectionFailed();
        }
      }
      return;
    }
    finally {}
  }
  
  public boolean connectDevice(BluetoothDevice paramBluetoothDevice)
  {
    if (paramBluetoothDevice == null) {
      return false;
    }
    try
    {
      BluetoothSocket localBluetoothSocket = paramBluetoothDevice.createRfcommSocketToServiceRecord(BTConstant.MY_UUID_INSECURE);
      if (localBluetoothSocket != null)
      {
        localBluetoothSocket.connect();
        localBluetoothSocket.close();
        return true;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      connectionFailed();
    }
    return false;
  }
  
  public void connected(BluetoothSocket paramBluetoothSocket, BluetoothDevice paramBluetoothDevice, String paramString)
  {
    try
    {
      Log.d("BluetoothChatService", "connected, Socket Type:" + paramString);
      try
      {
        if (this.mConnectThread != null)
        {
          this.mConnectThread.cancel();
          this.mConnectThread = null;
        }
        if (this.mConnectedThread != null)
        {
          this.mConnectedThread.cancel();
          this.mConnectedThread = null;
        }
        if (this.mSecureAcceptThread != null)
        {
          this.mSecureAcceptThread.cancel();
          this.mSecureAcceptThread = null;
        }
        if (this.mInsecureAcceptThread != null)
        {
          this.mInsecureAcceptThread.cancel();
          this.mInsecureAcceptThread = null;
        }
        this.mConnectedThread = new ConnectedThread(paramBluetoothSocket, paramString);
        this.mConnectedThread.start();
        Message localMessage = this.mHandler.obtainMessage(4);
        Bundle localBundle = new Bundle();
        localBundle.putString("device_name", paramBluetoothDevice.getName());
        localMessage.setData(localBundle);
        this.mHandler.sendMessage(localMessage);
        setState(3);
      }
      catch (Exception localException)
      {
        for (;;)
        {
          connectionFailed();
        }
      }
      return;
    }
    finally {}
  }
  
  public int getState()
  {
    try
    {
      int i = mState;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void start()
  {
    try
    {
      Log.d("BluetoothChatService", "start");
      try
      {
        if (this.mConnectThread != null)
        {
          this.mConnectThread.cancel();
          this.mConnectThread = null;
        }
        if (this.mConnectedThread != null)
        {
          this.mConnectedThread.cancel();
          this.mConnectedThread = null;
        }
        setState(1);
        if (this.mSecureAcceptThread == null)
        {
          this.mSecureAcceptThread = new AcceptThread(true);
          this.mSecureAcceptThread.start();
        }
        if (this.mInsecureAcceptThread == null)
        {
          this.mInsecureAcceptThread = new AcceptThread(false);
          this.mInsecureAcceptThread.start();
        }
      }
      catch (Exception localException)
      {
        for (;;)
        {
          localException.printStackTrace();
        }
      }
      return;
    }
    finally {}
  }
  
  public boolean write(byte[] paramArrayOfByte)
  {
    for (;;)
    {
      try {}catch (Exception localException)
      {
        ConnectedThread localConnectedThread;
        continue;
      }
      try
      {
        if (mState != 3) {
          return false;
        }
        localConnectedThread = this.mConnectedThread;
        localConnectedThread.write(paramArrayOfByte);
        return true;
      }
      finally {}
    }
  }
  
  private class AcceptThread
    extends Thread
  {
    private String mSocketType;
    private final BluetoothServerSocket mmServerSocket;
    
    public AcceptThread(boolean paramBoolean)
    {
      if (1 != 0) {}
      for (String str = "Secure";; str = "Insecure")
      {
        this.mSocketType = str;
        localObject = null;
        if (1 != 0) {}
        try
        {
          BluetoothAdapter localBluetoothAdapter = BluetoothChatService.this.mAdapter;
          localObject = null;
          if (localBluetoothAdapter != null)
          {
            boolean bool = BluetoothChatService.this.mAdapter.isEnabled();
            localObject = null;
            if (bool)
            {
              BluetoothServerSocket localBluetoothServerSocket = BluetoothChatService.this.mAdapter.listenUsingRfcommWithServiceRecord("BluetoothChatSecure", BTConstant.MY_UUID_SECURE);
              localObject = localBluetoothServerSocket;
            }
          }
        }
        catch (IOException localIOException)
        {
          for (;;)
          {
            Log.e("BluetoothChatService", "Socket Type: " + this.mSocketType + "listen() failed", localIOException);
            localObject = null;
          }
        }
        this.mmServerSocket = ((BluetoothServerSocket)localObject);
        return;
      }
    }
    
    public void cancel()
    {
      try
      {
        if (this.mmServerSocket != null) {
          this.mmServerSocket.close();
        }
        return;
      }
      catch (Exception localException)
      {
        Log.e("BluetoothChatService", "Socket Type" + this.mSocketType + "close() of server failed", localException);
      }
    }
  }
  
  private class ConnectThread
    extends Thread
  {
    private String mSocketType;
    private final BluetoothDevice mmDevice;
    private final BluetoothSocket mmSocket;
    
    public ConnectThread(BluetoothDevice paramBluetoothDevice, boolean paramBoolean)
    {
      this.mmDevice = paramBluetoothDevice;
      if (1 != 0) {}
      for (String str = "Secure";; str = "Insecure")
      {
        this.mSocketType = str;
        localObject = null;
        if (1 != 0) {}
        try
        {
          BluetoothSocket localBluetoothSocket = paramBluetoothDevice.createRfcommSocketToServiceRecord(BTConstant.MY_UUID_SECURE);
          localObject = localBluetoothSocket;
        }
        catch (Exception localException)
        {
          for (;;)
          {
            Log.e("BluetoothChatService", "Socket Type: " + this.mSocketType + "create() failed", localException);
            localObject = null;
          }
        }
        this.mmSocket = ((BluetoothSocket)localObject);
        return;
      }
    }
    
    public void cancel()
    {
      try
      {
        if (this.mmSocket != null) {
          this.mmSocket.close();
        }
        return;
      }
      catch (IOException localIOException)
      {
        Log.e("BluetoothChatService", "close() of connect " + this.mSocketType + " socket failed", localIOException);
      }
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: ldc 45
      //   2: new 47	java/lang/StringBuilder
      //   5: dup
      //   6: ldc 83
      //   8: invokespecial 52	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   11: aload_0
      //   12: getfield 27	com/lefu/es/service/BluetoothChatService$ConnectThread:mSocketType	Ljava/lang/String;
      //   15: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   18: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   21: invokestatic 87	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
      //   24: pop
      //   25: aload_0
      //   26: new 47	java/lang/StringBuilder
      //   29: dup
      //   30: ldc 89
      //   32: invokespecial 52	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   35: aload_0
      //   36: getfield 27	com/lefu/es/service/BluetoothChatService$ConnectThread:mSocketType	Ljava/lang/String;
      //   39: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   42: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   45: invokevirtual 92	com/lefu/es/service/BluetoothChatService$ConnectThread:setName	(Ljava/lang/String;)V
      //   48: aload_0
      //   49: getfield 18	com/lefu/es/service/BluetoothChatService$ConnectThread:this$0	Lcom/lefu/es/service/BluetoothChatService;
      //   52: invokestatic 98	com/lefu/es/service/BluetoothChatService:access$0	(Lcom/lefu/es/service/BluetoothChatService;)Landroid/bluetooth/BluetoothAdapter;
      //   55: ifnull +14 -> 69
      //   58: aload_0
      //   59: getfield 18	com/lefu/es/service/BluetoothChatService$ConnectThread:this$0	Lcom/lefu/es/service/BluetoothChatService;
      //   62: invokestatic 98	com/lefu/es/service/BluetoothChatService:access$0	(Lcom/lefu/es/service/BluetoothChatService;)Landroid/bluetooth/BluetoothAdapter;
      //   65: invokevirtual 104	android/bluetooth/BluetoothAdapter:cancelDiscovery	()Z
      //   68: pop
      //   69: aload_0
      //   70: getfield 41	com/lefu/es/service/BluetoothChatService$ConnectThread:mmSocket	Landroid/bluetooth/BluetoothSocket;
      //   73: ifnull +10 -> 83
      //   76: aload_0
      //   77: getfield 41	com/lefu/es/service/BluetoothChatService$ConnectThread:mmSocket	Landroid/bluetooth/BluetoothSocket;
      //   80: invokevirtual 107	android/bluetooth/BluetoothSocket:connect	()V
      //   83: aload_0
      //   84: getfield 18	com/lefu/es/service/BluetoothChatService$ConnectThread:this$0	Lcom/lefu/es/service/BluetoothChatService;
      //   87: astore 6
      //   89: aload 6
      //   91: monitorenter
      //   92: aload_0
      //   93: getfield 18	com/lefu/es/service/BluetoothChatService$ConnectThread:this$0	Lcom/lefu/es/service/BluetoothChatService;
      //   96: aconst_null
      //   97: invokestatic 111	com/lefu/es/service/BluetoothChatService:access$2	(Lcom/lefu/es/service/BluetoothChatService;Lcom/lefu/es/service/BluetoothChatService$ConnectThread;)V
      //   100: aload 6
      //   102: monitorexit
      //   103: aload_0
      //   104: getfield 18	com/lefu/es/service/BluetoothChatService$ConnectThread:this$0	Lcom/lefu/es/service/BluetoothChatService;
      //   107: aload_0
      //   108: getfield 41	com/lefu/es/service/BluetoothChatService$ConnectThread:mmSocket	Landroid/bluetooth/BluetoothSocket;
      //   111: aload_0
      //   112: getfield 23	com/lefu/es/service/BluetoothChatService$ConnectThread:mmDevice	Landroid/bluetooth/BluetoothDevice;
      //   115: aload_0
      //   116: getfield 27	com/lefu/es/service/BluetoothChatService$ConnectThread:mSocketType	Ljava/lang/String;
      //   119: invokevirtual 115	com/lefu/es/service/BluetoothChatService:connected	(Landroid/bluetooth/BluetoothSocket;Landroid/bluetooth/BluetoothDevice;Ljava/lang/String;)V
      //   122: return
      //   123: astore_2
      //   124: ldc 45
      //   126: new 47	java/lang/StringBuilder
      //   129: dup
      //   130: invokespecial 116	java/lang/StringBuilder:<init>	()V
      //   133: aload_0
      //   134: getfield 23	com/lefu/es/service/BluetoothChatService$ConnectThread:mmDevice	Landroid/bluetooth/BluetoothDevice;
      //   137: invokevirtual 119	android/bluetooth/BluetoothDevice:getName	()Ljava/lang/String;
      //   140: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   143: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   146: invokestatic 121	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
      //   149: pop
      //   150: aload_0
      //   151: getfield 41	com/lefu/es/service/BluetoothChatService$ConnectThread:mmSocket	Landroid/bluetooth/BluetoothSocket;
      //   154: ifnull +10 -> 164
      //   157: aload_0
      //   158: getfield 41	com/lefu/es/service/BluetoothChatService$ConnectThread:mmSocket	Landroid/bluetooth/BluetoothSocket;
      //   161: invokevirtual 76	android/bluetooth/BluetoothSocket:close	()V
      //   164: aload_0
      //   165: getfield 18	com/lefu/es/service/BluetoothChatService$ConnectThread:this$0	Lcom/lefu/es/service/BluetoothChatService;
      //   168: invokestatic 125	com/lefu/es/service/BluetoothChatService:access$1	(Lcom/lefu/es/service/BluetoothChatService;)V
      //   171: return
      //   172: astore_3
      //   173: ldc 45
      //   175: new 47	java/lang/StringBuilder
      //   178: dup
      //   179: ldc 127
      //   181: invokespecial 52	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   184: aload_0
      //   185: getfield 27	com/lefu/es/service/BluetoothChatService$ConnectThread:mSocketType	Ljava/lang/String;
      //   188: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   191: ldc -127
      //   193: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   196: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   199: aload_3
      //   200: invokestatic 68	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   203: pop
      //   204: goto -40 -> 164
      //   207: astore 7
      //   209: aload 6
      //   211: monitorexit
      //   212: aload 7
      //   214: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	215	0	this	ConnectThread
      //   123	1	2	localIOException1	IOException
      //   172	28	3	localIOException2	IOException
      //   207	6	7	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   48	69	123	java/io/IOException
      //   69	83	123	java/io/IOException
      //   124	164	172	java/io/IOException
      //   92	103	207	finally
      //   209	212	207	finally
    }
  }
  
  private class ConnectedThread
    extends Thread
  {
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final BluetoothSocket mmSocket;
    
    public ConnectedThread(BluetoothSocket paramBluetoothSocket, String paramString)
    {
      Log.d("BluetoothChatService", "create ConnectedThread: " + paramString);
      this.mmSocket = paramBluetoothSocket;
      InputStream localInputStream = null;
      try
      {
        localInputStream = paramBluetoothSocket.getInputStream();
        OutputStream localOutputStream2 = paramBluetoothSocket.getOutputStream();
        localOutputStream1 = localOutputStream2;
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          Log.e("BluetoothChatService", "temp sockets not created", localIOException);
          OutputStream localOutputStream1 = null;
        }
      }
      this.mmInStream = localInputStream;
      this.mmOutStream = localOutputStream1;
    }
    
    public void cancel()
    {
      try
      {
        if (this.mmSocket != null) {
          this.mmSocket.close();
        }
        return;
      }
      catch (IOException localIOException)
      {
        Log.e("BluetoothChatService", "close() of connect socket failed", localIOException);
      }
    }
    
    public void run()
    {
      Log.i("BluetoothChatService", "BEGIN mConnectedThread");
      byte[] arrayOfByte = new byte['Ѐ'];
      try
      {
        for (;;)
        {
          if (this.mmInStream != null)
          {
            int i = this.mmInStream.read(arrayOfByte);
            if (BluetoothChatService.this.mHandler != null) {
              BluetoothChatService.this.mHandler.obtainMessage(2, i, -1, arrayOfByte).sendToTarget();
            }
          }
        }
        try
        {
          if (this.mmInStream != null) {
            this.mmInStream.close();
          }
        }
        catch (Exception localException2)
        {
          try
          {
            if (this.mmOutStream != null) {
              this.mmOutStream.close();
            }
          }
          catch (Exception localException2)
          {
            try
            {
              for (;;)
              {
                if (this.mmSocket != null) {
                  this.mmSocket.close();
                }
                BluetoothChatService.this.start();
                return;
                localException1 = localException1;
                localException1.printStackTrace();
                continue;
                localException2 = localException2;
                localException2.printStackTrace();
              }
            }
            catch (Exception localException3)
            {
              for (;;)
              {
                localException3.printStackTrace();
              }
            }
          }
        }
      }
      catch (IOException localIOException)
      {
        Log.e("BluetoothChatService", "disconnected", localIOException);
        BluetoothChatService.this.connectionLost();
      }
    }
    
    public void write(byte[] paramArrayOfByte)
    {
      try
      {
        if (this.mmOutStream != null)
        {
          this.mmOutStream.write(paramArrayOfByte);
          if (BluetoothChatService.this.mHandler != null) {
            BluetoothChatService.this.mHandler.obtainMessage(3, -1, -1, paramArrayOfByte).sendToTarget();
          }
        }
        return;
      }
      catch (IOException localIOException)
      {
        Log.e("BluetoothChatService", "Exception during write", localIOException);
      }
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\service\BluetoothChatService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */