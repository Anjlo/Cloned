package org.achartengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.achartengine.util.IndexXYMap;
import org.achartengine.util.XYEntry;

public class XYSeries
  implements Serializable
{
  private static final double PADDING = 1.0E-12D;
  private List<String> mAnnotations = new ArrayList();
  private double mMaxX = -1.7976931348623157E308D;
  private double mMaxY = -1.7976931348623157E308D;
  private double mMinX = Double.MAX_VALUE;
  private double mMinY = Double.MAX_VALUE;
  private final int mScaleNumber;
  private final IndexXYMap<Double, Double> mStringXY = new IndexXYMap();
  private String mTitle;
  private final IndexXYMap<Double, Double> mXY = new IndexXYMap();
  
  public XYSeries(String paramString)
  {
    this(paramString, 0);
  }
  
  public XYSeries(String paramString, int paramInt)
  {
    this.mTitle = paramString;
    this.mScaleNumber = paramInt;
    initRange();
  }
  
  private void initRange()
  {
    this.mMinX = Double.MAX_VALUE;
    this.mMaxX = -1.7976931348623157E308D;
    this.mMinY = Double.MAX_VALUE;
    this.mMaxY = -1.7976931348623157E308D;
    int i = getItemCount();
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return;
      }
      updateRange(getX(j), getY(j));
    }
  }
  
  private void updateRange(double paramDouble1, double paramDouble2)
  {
    this.mMinX = Math.min(this.mMinX, paramDouble1);
    this.mMaxX = Math.max(this.mMaxX, paramDouble1);
    this.mMinY = Math.min(this.mMinY, paramDouble2);
    this.mMaxY = Math.max(this.mMaxY, paramDouble2);
  }
  
  /* Error */
  public void add(double paramDouble1, double paramDouble2)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 38	org/achartengine/model/XYSeries:mXY	Lorg/achartengine/util/IndexXYMap;
    //   6: dload_1
    //   7: invokestatic 95	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   10: invokevirtual 99	org/achartengine/util/IndexXYMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   13: ifnonnull +28 -> 41
    //   16: aload_0
    //   17: getfield 38	org/achartengine/model/XYSeries:mXY	Lorg/achartengine/util/IndexXYMap;
    //   20: dload_1
    //   21: invokestatic 95	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   24: dload_3
    //   25: invokestatic 95	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   28: invokevirtual 103	org/achartengine/util/IndexXYMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   31: pop
    //   32: aload_0
    //   33: dload_1
    //   34: dload_3
    //   35: invokespecial 79	org/achartengine/model/XYSeries:updateRange	(DD)V
    //   38: aload_0
    //   39: monitorexit
    //   40: return
    //   41: aload_0
    //   42: invokevirtual 107	org/achartengine/model/XYSeries:getPadding	()D
    //   45: dstore 6
    //   47: dload_1
    //   48: dload 6
    //   50: dadd
    //   51: dstore_1
    //   52: goto -50 -> 2
    //   55: astore 5
    //   57: aload_0
    //   58: monitorexit
    //   59: aload 5
    //   61: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	62	0	this	XYSeries
    //   0	62	1	paramDouble1	double
    //   0	62	3	paramDouble2	double
    //   55	5	5	localObject	Object
    //   45	4	6	d	double
    // Exception table:
    //   from	to	target	type
    //   2	38	55	finally
    //   41	47	55	finally
  }
  
  /* Error */
  public void add(int paramInt, double paramDouble1, double paramDouble2)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 38	org/achartengine/model/XYSeries:mXY	Lorg/achartengine/util/IndexXYMap;
    //   6: dload_2
    //   7: invokestatic 95	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   10: invokevirtual 99	org/achartengine/util/IndexXYMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   13: ifnonnull +31 -> 44
    //   16: aload_0
    //   17: getfield 38	org/achartengine/model/XYSeries:mXY	Lorg/achartengine/util/IndexXYMap;
    //   20: iload_1
    //   21: dload_2
    //   22: invokestatic 95	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   25: dload 4
    //   27: invokestatic 95	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   30: invokevirtual 111	org/achartengine/util/IndexXYMap:put	(ILjava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   33: pop
    //   34: aload_0
    //   35: dload_2
    //   36: dload 4
    //   38: invokespecial 79	org/achartengine/model/XYSeries:updateRange	(DD)V
    //   41: aload_0
    //   42: monitorexit
    //   43: return
    //   44: aload_0
    //   45: invokevirtual 107	org/achartengine/model/XYSeries:getPadding	()D
    //   48: dstore 7
    //   50: dload_2
    //   51: dload 7
    //   53: dadd
    //   54: dstore_2
    //   55: goto -53 -> 2
    //   58: astore 6
    //   60: aload_0
    //   61: monitorexit
    //   62: aload 6
    //   64: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	65	0	this	XYSeries
    //   0	65	1	paramInt	int
    //   0	65	2	paramDouble1	double
    //   0	65	4	paramDouble2	double
    //   58	5	6	localObject	Object
    //   48	4	7	d	double
    // Exception table:
    //   from	to	target	type
    //   2	41	58	finally
    //   44	50	58	finally
  }
  
  public void addAnnotation(String paramString, double paramDouble1, double paramDouble2)
  {
    this.mAnnotations.add(paramString);
    this.mStringXY.put(Double.valueOf(paramDouble1), Double.valueOf(paramDouble2));
  }
  
  public void clear()
  {
    try
    {
      this.mXY.clear();
      this.mStringXY.clear();
      initRange();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String getAnnotationAt(int paramInt)
  {
    return (String)this.mAnnotations.get(paramInt);
  }
  
  public int getAnnotationCount()
  {
    return this.mAnnotations.size();
  }
  
  public double getAnnotationX(int paramInt)
  {
    return ((Double)this.mStringXY.getXByIndex(paramInt)).doubleValue();
  }
  
  public double getAnnotationY(int paramInt)
  {
    return ((Double)this.mStringXY.getYByIndex(paramInt)).doubleValue();
  }
  
  public int getIndexForKey(double paramDouble)
  {
    return this.mXY.getIndexForKey(Double.valueOf(paramDouble));
  }
  
  public int getItemCount()
  {
    try
    {
      int i = this.mXY.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public double getMaxX()
  {
    return this.mMaxX;
  }
  
  public double getMaxY()
  {
    return this.mMaxY;
  }
  
  public double getMinX()
  {
    return this.mMinX;
  }
  
  public double getMinY()
  {
    return this.mMinY;
  }
  
  protected double getPadding()
  {
    return 1.0E-12D;
  }
  
  /* Error */
  public java.util.SortedMap<Double, Double> getRange(double paramDouble1, double paramDouble2, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload 5
    //   4: ifeq +113 -> 117
    //   7: aload_0
    //   8: getfield 38	org/achartengine/model/XYSeries:mXY	Lorg/achartengine/util/IndexXYMap;
    //   11: dload_1
    //   12: invokestatic 95	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   15: invokevirtual 159	org/achartengine/util/IndexXYMap:headMap	(Ljava/lang/Object;)Ljava/util/SortedMap;
    //   18: astore 8
    //   20: aload 8
    //   22: invokeinterface 165 1 0
    //   27: ifne +17 -> 44
    //   30: aload 8
    //   32: invokeinterface 169 1 0
    //   37: checkcast 91	java/lang/Double
    //   40: invokevirtual 139	java/lang/Double:doubleValue	()D
    //   43: dstore_1
    //   44: aload_0
    //   45: getfield 38	org/achartengine/model/XYSeries:mXY	Lorg/achartengine/util/IndexXYMap;
    //   48: dload_3
    //   49: invokestatic 95	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   52: invokevirtual 172	org/achartengine/util/IndexXYMap:tailMap	(Ljava/lang/Object;)Ljava/util/SortedMap;
    //   55: astore 9
    //   57: aload 9
    //   59: invokeinterface 165 1 0
    //   64: ifne +53 -> 117
    //   67: aload 9
    //   69: invokeinterface 176 1 0
    //   74: invokeinterface 182 1 0
    //   79: astore 10
    //   81: aload 10
    //   83: invokeinterface 187 1 0
    //   88: checkcast 91	java/lang/Double
    //   91: astore 11
    //   93: aload 10
    //   95: invokeinterface 190 1 0
    //   100: ifeq +39 -> 139
    //   103: aload 10
    //   105: invokeinterface 187 1 0
    //   110: checkcast 91	java/lang/Double
    //   113: invokevirtual 139	java/lang/Double:doubleValue	()D
    //   116: dstore_3
    //   117: aload_0
    //   118: getfield 38	org/achartengine/model/XYSeries:mXY	Lorg/achartengine/util/IndexXYMap;
    //   121: dload_1
    //   122: invokestatic 95	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   125: dload_3
    //   126: invokestatic 95	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   129: invokevirtual 194	org/achartengine/util/IndexXYMap:subMap	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/SortedMap;
    //   132: astore 7
    //   134: aload_0
    //   135: monitorexit
    //   136: aload 7
    //   138: areturn
    //   139: aload 11
    //   141: invokevirtual 139	java/lang/Double:doubleValue	()D
    //   144: dstore 12
    //   146: dload_3
    //   147: dload 12
    //   149: dadd
    //   150: dstore_3
    //   151: goto -34 -> 117
    //   154: astore 6
    //   156: aload_0
    //   157: monitorexit
    //   158: aload 6
    //   160: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	161	0	this	XYSeries
    //   0	161	1	paramDouble1	double
    //   0	161	3	paramDouble2	double
    //   0	161	5	paramBoolean	boolean
    //   154	5	6	localObject	Object
    //   132	5	7	localSortedMap1	java.util.SortedMap
    //   18	13	8	localSortedMap2	java.util.SortedMap
    //   55	13	9	localSortedMap3	java.util.SortedMap
    //   79	25	10	localIterator	java.util.Iterator
    //   91	49	11	localDouble	Double
    //   144	4	12	d	double
    // Exception table:
    //   from	to	target	type
    //   7	44	154	finally
    //   44	117	154	finally
    //   117	134	154	finally
    //   139	146	154	finally
  }
  
  public int getScaleNumber()
  {
    return this.mScaleNumber;
  }
  
  public String getTitle()
  {
    return this.mTitle;
  }
  
  public double getX(int paramInt)
  {
    try
    {
      double d = ((Double)this.mXY.getXByIndex(paramInt)).doubleValue();
      return d;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public double getY(int paramInt)
  {
    try
    {
      double d2 = ((Double)this.mXY.getYByIndex(paramInt)).doubleValue();
      d1 = d2;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        localException = localException;
        double d1 = 0.0D;
      }
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
    return d1;
  }
  
  public void remove(int paramInt)
  {
    try
    {
      XYEntry localXYEntry = this.mXY.removeByIndex(paramInt);
      double d1 = ((Double)localXYEntry.getKey()).doubleValue();
      double d2 = ((Double)localXYEntry.getValue()).doubleValue();
      if ((d1 == this.mMinX) || (d1 == this.mMaxX) || (d2 == this.mMinY) || (d2 == this.mMaxY)) {
        initRange();
      }
      return;
    }
    finally {}
  }
  
  public void removeAnnotation(int paramInt)
  {
    this.mAnnotations.remove(paramInt);
    this.mStringXY.removeByIndex(paramInt);
  }
  
  public void setTitle(String paramString)
  {
    this.mTitle = paramString;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\model\XYSeries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */