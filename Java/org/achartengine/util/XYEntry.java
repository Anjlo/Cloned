package org.achartengine.util;

import java.util.Map.Entry;

public class XYEntry<K, V>
  implements Map.Entry<K, V>
{
  private final K key;
  private V value;
  
  public XYEntry(K paramK, V paramV)
  {
    this.key = paramK;
    this.value = paramV;
  }
  
  public K getKey()
  {
    return (K)this.key;
  }
  
  public V getValue()
  {
    return (V)this.value;
  }
  
  public V setValue(V paramV)
  {
    this.value = paramV;
    return (V)this.value;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\util\XYEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */