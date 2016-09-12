package com.lefu.es.wheelview;

public class ArrayWheelAdapter<T>
  implements WheelAdapter
{
  public static final int DEFAULT_LENGTH = -1;
  private T[] items;
  private int length;
  
  public ArrayWheelAdapter(T[] paramArrayOfT)
  {
    this(paramArrayOfT, -1);
  }
  
  public ArrayWheelAdapter(T[] paramArrayOfT, int paramInt)
  {
    this.items = paramArrayOfT;
    this.length = paramInt;
  }
  
  public String getItem(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.items.length)) {
      return this.items[paramInt].toString();
    }
    return null;
  }
  
  public int getItemsCount()
  {
    return this.items.length;
  }
  
  public int getMaximumLength()
  {
    return this.length;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\wheelview\ArrayWheelAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */