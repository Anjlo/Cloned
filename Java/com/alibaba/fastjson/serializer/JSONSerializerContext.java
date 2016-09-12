package com.alibaba.fastjson.serializer;

public final class JSONSerializerContext
{
  public static final int DEFAULT_TABLE_SIZE = 128;
  private final Entry[] buckets;
  private final int indexMask;
  
  public JSONSerializerContext()
  {
    this(128);
  }
  
  public JSONSerializerContext(int paramInt)
  {
    this.indexMask = (paramInt - 1);
    this.buckets = new Entry[paramInt];
  }
  
  public final boolean put(Object paramObject)
  {
    int i = System.identityHashCode(paramObject);
    int j = i & this.indexMask;
    for (Entry localEntry1 = this.buckets[j]; localEntry1 != null; localEntry1 = localEntry1.next) {
      if (paramObject == localEntry1.object) {
        return true;
      }
    }
    Entry localEntry2 = new Entry(paramObject, i, this.buckets[j]);
    this.buckets[j] = localEntry2;
    return false;
  }
  
  protected static final class Entry
  {
    public final int hashCode;
    public Entry next;
    public final Object object;
    
    public Entry(Object paramObject, int paramInt, Entry paramEntry)
    {
      this.object = paramObject;
      this.next = paramEntry;
      this.hashCode = paramInt;
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\serializer\JSONSerializerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */