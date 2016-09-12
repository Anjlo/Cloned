package com.alibaba.fastjson.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

public class AntiCollisionHashMap<K, V>
  extends AbstractMap<K, V>
  implements Map<K, V>, Cloneable, Serializable
{
  static final int DEFAULT_INITIAL_CAPACITY = 16;
  static final float DEFAULT_LOAD_FACTOR = 0.75F;
  static final int KEY = 16777619;
  static final int MAXIMUM_CAPACITY = 1073741824;
  static final int M_MASK = -2023358765;
  static final int SEED = -2128831035;
  private static final long serialVersionUID = 362498820763181265L;
  private transient Set<Map.Entry<K, V>> entrySet = null;
  volatile transient Set<K> keySet = null;
  final float loadFactor;
  volatile transient int modCount;
  final int random = new Random().nextInt(99999);
  transient int size;
  transient Entry<K, V>[] table;
  int threshold;
  volatile transient Collection<V> values = null;
  
  public AntiCollisionHashMap()
  {
    this.loadFactor = 0.75F;
    this.threshold = 12;
    this.table = new Entry[16];
    init();
  }
  
  public AntiCollisionHashMap(int paramInt)
  {
    this(paramInt, 0.75F);
  }
  
  public AntiCollisionHashMap(int paramInt, float paramFloat)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Illegal initial capacity: " + paramInt);
    }
    if (paramInt > 1073741824) {
      paramInt = 1073741824;
    }
    if ((paramFloat <= 0.0F) || (Float.isNaN(paramFloat))) {
      throw new IllegalArgumentException("Illegal load factor: " + paramFloat);
    }
    int i = 1;
    while (i < paramInt) {
      i <<= 1;
    }
    this.loadFactor = paramFloat;
    this.threshold = ((int)(paramFloat * i));
    this.table = new Entry[i];
    init();
  }
  
  public AntiCollisionHashMap(Map<? extends K, ? extends V> paramMap)
  {
    this(Math.max(1 + (int)(paramMap.size() / 0.75F), 16), 0.75F);
    putAllForCreate(paramMap);
  }
  
  private boolean containsNullValue()
  {
    Entry[] arrayOfEntry = this.table;
    for (int i = 0; i < arrayOfEntry.length; i++) {
      for (Entry localEntry = arrayOfEntry[i]; localEntry != null; localEntry = localEntry.next) {
        if (localEntry.value == null) {
          return true;
        }
      }
    }
    return false;
  }
  
  private Set<Map.Entry<K, V>> entrySet0()
  {
    Set localSet = this.entrySet;
    if (localSet != null) {
      return localSet;
    }
    EntrySet localEntrySet = new EntrySet(null);
    this.entrySet = localEntrySet;
    return localEntrySet;
  }
  
  private V getForNullKey()
  {
    for (Entry localEntry = this.table[0]; localEntry != null; localEntry = localEntry.next) {
      if (localEntry.key == null) {
        return (V)localEntry.value;
      }
    }
    return null;
  }
  
  static int hash(int paramInt)
  {
    int i = paramInt * paramInt;
    int j = i ^ i >>> 20 ^ i >>> 12;
    return j ^ j >>> 7 ^ j >>> 4;
  }
  
  private int hashString(String paramString)
  {
    int i = -2128831035 * this.random;
    for (int j = 0; j < paramString.length(); j++) {
      i = 16777619 * i ^ paramString.charAt(j);
    }
    return 0x8765FED3 & (i ^ i >> 1);
  }
  
  static int indexFor(int paramInt1, int paramInt2)
  {
    return paramInt1 & paramInt2 - 1;
  }
  
  private void putAllForCreate(Map<? extends K, ? extends V> paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      putForCreate(localEntry.getKey(), localEntry.getValue());
    }
  }
  
  private void putForCreate(K paramK, V paramV)
  {
    int i;
    int j;
    if (paramK == null)
    {
      i = 0;
      j = indexFor(i, this.table.length);
    }
    for (Entry localEntry = this.table[j];; localEntry = localEntry.next)
    {
      if (localEntry == null) {
        break label116;
      }
      if (localEntry.hash == i)
      {
        Object localObject = localEntry.key;
        if ((localObject == paramK) || ((paramK != null) && (paramK.equals(localObject))))
        {
          localEntry.value = paramV;
          return;
          if ((paramK instanceof String))
          {
            i = hash(hashString((String)paramK));
            break;
          }
          i = hash(paramK.hashCode());
          break;
        }
      }
    }
    label116:
    createEntry(i, paramK, paramV, j);
  }
  
  private V putForNullKey(V paramV)
  {
    for (Entry localEntry = this.table[0]; localEntry != null; localEntry = localEntry.next) {
      if (localEntry.key == null)
      {
        Object localObject = localEntry.value;
        localEntry.value = paramV;
        localEntry.recordAccess(this);
        return (V)localObject;
      }
    }
    this.modCount = (1 + this.modCount);
    addEntry(0, null, paramV, 0);
    return null;
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    this.table = new Entry[paramObjectInputStream.readInt()];
    init();
    int i = paramObjectInputStream.readInt();
    for (int j = 0; j < i; j++) {
      putForCreate(paramObjectInputStream.readObject(), paramObjectInputStream.readObject());
    }
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    if (this.size > 0) {}
    for (Iterator localIterator = entrySet0().iterator();; localIterator = null)
    {
      paramObjectOutputStream.defaultWriteObject();
      paramObjectOutputStream.writeInt(this.table.length);
      paramObjectOutputStream.writeInt(this.size);
      if (localIterator == null) {
        break;
      }
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        paramObjectOutputStream.writeObject(localEntry.getKey());
        paramObjectOutputStream.writeObject(localEntry.getValue());
      }
    }
  }
  
  void addEntry(int paramInt1, K paramK, V paramV, int paramInt2)
  {
    Entry localEntry = this.table[paramInt2];
    this.table[paramInt2] = new Entry(paramInt1, paramK, paramV, localEntry);
    int i = this.size;
    this.size = (i + 1);
    if (i >= this.threshold) {
      resize(2 * this.table.length);
    }
  }
  
  int capacity()
  {
    return this.table.length;
  }
  
  public void clear()
  {
    this.modCount = (1 + this.modCount);
    Entry[] arrayOfEntry = this.table;
    for (int i = 0; i < arrayOfEntry.length; i++) {
      arrayOfEntry[i] = null;
    }
    this.size = 0;
  }
  
  public Object clone()
  {
    try
    {
      localAntiCollisionHashMap = (AntiCollisionHashMap)super.clone();
      localAntiCollisionHashMap.table = new Entry[this.table.length];
      localAntiCollisionHashMap.entrySet = null;
      localAntiCollisionHashMap.modCount = 0;
      localAntiCollisionHashMap.size = 0;
      localAntiCollisionHashMap.init();
      localAntiCollisionHashMap.putAllForCreate(this);
      return localAntiCollisionHashMap;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      for (;;)
      {
        AntiCollisionHashMap localAntiCollisionHashMap = null;
      }
    }
  }
  
  public boolean containsKey(Object paramObject)
  {
    return getEntry(paramObject) != null;
  }
  
  public boolean containsValue(Object paramObject)
  {
    if (paramObject == null) {
      return containsNullValue();
    }
    Entry[] arrayOfEntry = this.table;
    for (int i = 0; i < arrayOfEntry.length; i++) {
      for (Entry localEntry = arrayOfEntry[i]; localEntry != null; localEntry = localEntry.next) {
        if (paramObject.equals(localEntry.value)) {
          return true;
        }
      }
    }
    return false;
  }
  
  void createEntry(int paramInt1, K paramK, V paramV, int paramInt2)
  {
    Entry localEntry = this.table[paramInt2];
    this.table[paramInt2] = new Entry(paramInt1, paramK, paramV, localEntry);
    this.size = (1 + this.size);
  }
  
  public Set<Map.Entry<K, V>> entrySet()
  {
    return entrySet0();
  }
  
  public V get(Object paramObject)
  {
    if (paramObject == null) {
      return (V)getForNullKey();
    }
    int i;
    if ((paramObject instanceof String)) {
      i = hash(hashString((String)paramObject));
    }
    for (Entry localEntry = this.table[indexFor(i, this.table.length)];; localEntry = localEntry.next)
    {
      if (localEntry == null) {
        break label100;
      }
      if (localEntry.hash == i)
      {
        Object localObject = localEntry.key;
        if ((localObject == paramObject) || (paramObject.equals(localObject)))
        {
          return (V)localEntry.value;
          i = hash(paramObject.hashCode());
          break;
        }
      }
    }
    label100:
    return null;
  }
  
  final Entry<K, V> getEntry(Object paramObject)
  {
    int i;
    if (paramObject == null) {
      i = 0;
    }
    for (Entry localEntry = this.table[indexFor(i, this.table.length)];; localEntry = localEntry.next)
    {
      if (localEntry == null) {
        break label101;
      }
      if (localEntry.hash == i)
      {
        Object localObject = localEntry.key;
        if ((localObject == paramObject) || ((paramObject != null) && (paramObject.equals(localObject))))
        {
          return localEntry;
          if ((paramObject instanceof String))
          {
            i = hash(hashString((String)paramObject));
            break;
          }
          i = hash(paramObject.hashCode());
          break;
        }
      }
    }
    label101:
    return null;
  }
  
  void init() {}
  
  public boolean isEmpty()
  {
    return this.size == 0;
  }
  
  public Set<K> keySet()
  {
    Set localSet = this.keySet;
    if (localSet != null) {
      return localSet;
    }
    KeySet localKeySet = new KeySet(null);
    this.keySet = localKeySet;
    return localKeySet;
  }
  
  float loadFactor()
  {
    return this.loadFactor;
  }
  
  Iterator<Map.Entry<K, V>> newEntryIterator()
  {
    return new EntryIterator(null);
  }
  
  Iterator<K> newKeyIterator()
  {
    return new KeyIterator(null);
  }
  
  Iterator<V> newValueIterator()
  {
    return new ValueIterator(null);
  }
  
  public V put(K paramK, V paramV)
  {
    if (paramK == null) {
      return (V)putForNullKey(paramV);
    }
    int i;
    int j;
    if ((paramK instanceof String))
    {
      i = hash(hashString((String)paramK));
      j = indexFor(i, this.table.length);
    }
    for (Entry localEntry = this.table[j];; localEntry = localEntry.next)
    {
      if (localEntry == null) {
        break label128;
      }
      if (localEntry.hash == i)
      {
        Object localObject1 = localEntry.key;
        if ((localObject1 == paramK) || (paramK.equals(localObject1)))
        {
          Object localObject2 = localEntry.value;
          localEntry.value = paramV;
          localEntry.recordAccess(this);
          return (V)localObject2;
          i = hash(paramK.hashCode());
          break;
        }
      }
    }
    label128:
    this.modCount = (1 + this.modCount);
    addEntry(i, paramK, paramV, j);
    return null;
  }
  
  public void putAll(Map<? extends K, ? extends V> paramMap)
  {
    int i = paramMap.size();
    if (i == 0) {}
    for (;;)
    {
      return;
      if (i > this.threshold)
      {
        int j = (int)(1.0F + i / this.loadFactor);
        if (j > 1073741824) {
          j = 1073741824;
        }
        int k = this.table.length;
        while (k < j) {
          k <<= 1;
        }
        if (k > this.table.length) {
          resize(k);
        }
      }
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        put(localEntry.getKey(), localEntry.getValue());
      }
    }
  }
  
  public V remove(Object paramObject)
  {
    Entry localEntry = removeEntryForKey(paramObject);
    if (localEntry == null) {
      return null;
    }
    return (V)localEntry.value;
  }
  
  final Entry<K, V> removeEntryForKey(Object paramObject)
  {
    int i;
    int j;
    Object localObject1;
    if (paramObject == null)
    {
      i = 0;
      j = indexFor(i, this.table.length);
      localObject1 = this.table[j];
    }
    Entry localEntry;
    for (Object localObject2 = localObject1;; localObject2 = localEntry)
    {
      if (localObject2 != null)
      {
        localEntry = ((Entry)localObject2).next;
        if (((Entry)localObject2).hash != i) {
          break label162;
        }
        Object localObject3 = ((Entry)localObject2).key;
        if ((localObject3 != paramObject) && ((paramObject == null) || (!paramObject.equals(localObject3)))) {
          break label162;
        }
        this.modCount = (1 + this.modCount);
        this.size = (-1 + this.size);
        if (localObject1 != localObject2) {
          break label152;
        }
        this.table[j] = localEntry;
      }
      for (;;)
      {
        ((Entry)localObject2).recordRemoval(this);
        return (Entry<K, V>)localObject2;
        if ((paramObject instanceof String))
        {
          i = hash(hashString((String)paramObject));
          break;
        }
        i = hash(paramObject.hashCode());
        break;
        label152:
        ((Entry)localObject1).next = localEntry;
      }
      label162:
      localObject1 = localObject2;
    }
  }
  
  final Entry<K, V> removeMapping(Object paramObject)
  {
    Object localObject3;
    if (!(paramObject instanceof Map.Entry)) {
      localObject3 = null;
    }
    label187:
    for (;;)
    {
      return (Entry<K, V>)localObject3;
      Map.Entry localEntry = (Map.Entry)paramObject;
      Object localObject1 = localEntry.getKey();
      int i;
      int j;
      Object localObject2;
      if (localObject1 == null)
      {
        i = 0;
        j = indexFor(i, this.table.length);
        localObject2 = this.table[j];
      }
      Entry localEntry1;
      for (localObject3 = localObject2;; localObject3 = localEntry1)
      {
        if (localObject3 == null) {
          break label187;
        }
        localEntry1 = ((Entry)localObject3).next;
        if ((((Entry)localObject3).hash == i) && (((Entry)localObject3).equals(localEntry)))
        {
          this.modCount = (1 + this.modCount);
          this.size = (-1 + this.size);
          if (localObject2 == localObject3) {
            this.table[j] = localEntry1;
          }
          for (;;)
          {
            ((Entry)localObject3).recordRemoval(this);
            return (Entry<K, V>)localObject3;
            if ((localObject1 instanceof String))
            {
              i = hash(hashString((String)localObject1));
              break;
            }
            i = hash(localObject1.hashCode());
            break;
            ((Entry)localObject2).next = localEntry1;
          }
        }
        localObject2 = localObject3;
      }
    }
  }
  
  void resize(int paramInt)
  {
    if (this.table.length == 1073741824)
    {
      this.threshold = Integer.MAX_VALUE;
      return;
    }
    Entry[] arrayOfEntry = new Entry[paramInt];
    transfer(arrayOfEntry);
    this.table = arrayOfEntry;
    this.threshold = ((int)(paramInt * this.loadFactor));
  }
  
  public int size()
  {
    return this.size;
  }
  
  void transfer(Entry[] paramArrayOfEntry)
  {
    Entry[] arrayOfEntry = this.table;
    int i = paramArrayOfEntry.length;
    for (int j = 0; j < arrayOfEntry.length; j++)
    {
      Object localObject = arrayOfEntry[j];
      if (localObject != null)
      {
        arrayOfEntry[j] = null;
        do
        {
          Entry localEntry = ((Entry)localObject).next;
          int k = indexFor(((Entry)localObject).hash, i);
          ((Entry)localObject).next = paramArrayOfEntry[k];
          paramArrayOfEntry[k] = localObject;
          localObject = localEntry;
        } while (localObject != null);
      }
    }
  }
  
  public Collection<V> values()
  {
    Collection localCollection = this.values;
    if (localCollection != null) {
      return localCollection;
    }
    Values localValues = new Values(null);
    this.values = localValues;
    return localValues;
  }
  
  static class Entry<K, V>
    implements Map.Entry<K, V>
  {
    final int hash;
    final K key;
    Entry<K, V> next;
    V value;
    
    Entry(int paramInt, K paramK, V paramV, Entry<K, V> paramEntry)
    {
      this.value = paramV;
      this.next = paramEntry;
      this.key = paramK;
      this.hash = paramInt;
    }
    
    public final boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof Map.Entry)) {}
      Object localObject3;
      Object localObject4;
      do
      {
        Map.Entry localEntry;
        Object localObject1;
        Object localObject2;
        do
        {
          return false;
          localEntry = (Map.Entry)paramObject;
          localObject1 = getKey();
          localObject2 = localEntry.getKey();
        } while ((localObject1 != localObject2) && ((localObject1 == null) || (!localObject1.equals(localObject2))));
        localObject3 = getValue();
        localObject4 = localEntry.getValue();
      } while ((localObject3 != localObject4) && ((localObject3 == null) || (!localObject3.equals(localObject4))));
      return true;
    }
    
    public final K getKey()
    {
      return (K)this.key;
    }
    
    public final V getValue()
    {
      return (V)this.value;
    }
    
    public final int hashCode()
    {
      int i;
      int j;
      if (this.key == null)
      {
        i = 0;
        Object localObject = this.value;
        j = 0;
        if (localObject != null) {
          break label35;
        }
      }
      for (;;)
      {
        return i ^ j;
        i = this.key.hashCode();
        break;
        label35:
        j = this.value.hashCode();
      }
    }
    
    void recordAccess(AntiCollisionHashMap<K, V> paramAntiCollisionHashMap) {}
    
    void recordRemoval(AntiCollisionHashMap<K, V> paramAntiCollisionHashMap) {}
    
    public final V setValue(V paramV)
    {
      Object localObject = this.value;
      this.value = paramV;
      return (V)localObject;
    }
    
    public final String toString()
    {
      return getKey() + "=" + getValue();
    }
  }
  
  private final class EntryIterator
    extends AntiCollisionHashMap<K, V>.HashIterator<Map.Entry<K, V>>
  {
    private EntryIterator()
    {
      super();
    }
    
    public Map.Entry<K, V> next()
    {
      return nextEntry();
    }
  }
  
  private final class EntrySet
    extends AbstractSet<Map.Entry<K, V>>
  {
    private EntrySet() {}
    
    public void clear()
    {
      AntiCollisionHashMap.this.clear();
    }
    
    public boolean contains(Object paramObject)
    {
      if (!(paramObject instanceof Map.Entry)) {}
      Map.Entry localEntry;
      AntiCollisionHashMap.Entry localEntry1;
      do
      {
        return false;
        localEntry = (Map.Entry)paramObject;
        localEntry1 = AntiCollisionHashMap.this.getEntry(localEntry.getKey());
      } while ((localEntry1 == null) || (!localEntry1.equals(localEntry)));
      return true;
    }
    
    public Iterator<Map.Entry<K, V>> iterator()
    {
      return AntiCollisionHashMap.this.newEntryIterator();
    }
    
    public boolean remove(Object paramObject)
    {
      return AntiCollisionHashMap.this.removeMapping(paramObject) != null;
    }
    
    public int size()
    {
      return AntiCollisionHashMap.this.size;
    }
  }
  
  private abstract class HashIterator<E>
    implements Iterator<E>
  {
    AntiCollisionHashMap.Entry<K, V> current;
    int expectedModCount = AntiCollisionHashMap.this.modCount;
    int index;
    AntiCollisionHashMap.Entry<K, V> next;
    
    HashIterator()
    {
      if (AntiCollisionHashMap.this.size > 0)
      {
        AntiCollisionHashMap.Entry[] arrayOfEntry = AntiCollisionHashMap.this.table;
        AntiCollisionHashMap.Entry localEntry;
        do
        {
          if (this.index >= arrayOfEntry.length) {
            break;
          }
          int i = this.index;
          this.index = (i + 1);
          localEntry = arrayOfEntry[i];
          this.next = localEntry;
        } while (localEntry == null);
      }
    }
    
    public final boolean hasNext()
    {
      return this.next != null;
    }
    
    final AntiCollisionHashMap.Entry<K, V> nextEntry()
    {
      if (AntiCollisionHashMap.this.modCount != this.expectedModCount) {
        throw new ConcurrentModificationException();
      }
      AntiCollisionHashMap.Entry localEntry1 = this.next;
      if (localEntry1 == null) {
        throw new NoSuchElementException();
      }
      AntiCollisionHashMap.Entry localEntry2 = localEntry1.next;
      this.next = localEntry2;
      if (localEntry2 == null)
      {
        AntiCollisionHashMap.Entry[] arrayOfEntry = AntiCollisionHashMap.this.table;
        AntiCollisionHashMap.Entry localEntry3;
        do
        {
          if (this.index >= arrayOfEntry.length) {
            break;
          }
          int i = this.index;
          this.index = (i + 1);
          localEntry3 = arrayOfEntry[i];
          this.next = localEntry3;
        } while (localEntry3 == null);
      }
      this.current = localEntry1;
      return localEntry1;
    }
    
    public void remove()
    {
      if (this.current == null) {
        throw new IllegalStateException();
      }
      if (AntiCollisionHashMap.this.modCount != this.expectedModCount) {
        throw new ConcurrentModificationException();
      }
      Object localObject = this.current.key;
      this.current = null;
      AntiCollisionHashMap.this.removeEntryForKey(localObject);
      this.expectedModCount = AntiCollisionHashMap.this.modCount;
    }
  }
  
  private final class KeyIterator
    extends AntiCollisionHashMap<K, V>.HashIterator<K>
  {
    private KeyIterator()
    {
      super();
    }
    
    public K next()
    {
      return (K)nextEntry().getKey();
    }
  }
  
  private final class KeySet
    extends AbstractSet<K>
  {
    private KeySet() {}
    
    public void clear()
    {
      AntiCollisionHashMap.this.clear();
    }
    
    public boolean contains(Object paramObject)
    {
      return AntiCollisionHashMap.this.containsKey(paramObject);
    }
    
    public Iterator<K> iterator()
    {
      return AntiCollisionHashMap.this.newKeyIterator();
    }
    
    public boolean remove(Object paramObject)
    {
      return AntiCollisionHashMap.this.removeEntryForKey(paramObject) != null;
    }
    
    public int size()
    {
      return AntiCollisionHashMap.this.size;
    }
  }
  
  private final class ValueIterator
    extends AntiCollisionHashMap<K, V>.HashIterator<V>
  {
    private ValueIterator()
    {
      super();
    }
    
    public V next()
    {
      return (V)nextEntry().value;
    }
  }
  
  private final class Values
    extends AbstractCollection<V>
  {
    private Values() {}
    
    public void clear()
    {
      AntiCollisionHashMap.this.clear();
    }
    
    public boolean contains(Object paramObject)
    {
      return AntiCollisionHashMap.this.containsValue(paramObject);
    }
    
    public Iterator<V> iterator()
    {
      return AntiCollisionHashMap.this.newValueIterator();
    }
    
    public int size()
    {
      return AntiCollisionHashMap.this.size;
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\util\AntiCollisionHashMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */