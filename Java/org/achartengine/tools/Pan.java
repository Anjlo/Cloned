package org.achartengine.tools;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.RoundChart;
import org.achartengine.chart.XYChart;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

public class Pan
  extends AbstractTool
{
  private boolean limitsReachedX = false;
  private boolean limitsReachedY = false;
  private List<PanListener> mPanListeners = new ArrayList();
  
  public Pan(AbstractChart paramAbstractChart)
  {
    super(paramAbstractChart);
  }
  
  private double getAxisRatio(double[] paramArrayOfDouble)
  {
    return Math.abs(paramArrayOfDouble[1] - paramArrayOfDouble[0]) / Math.abs(paramArrayOfDouble[3] - paramArrayOfDouble[2]);
  }
  
  /* Error */
  private void notifyPanListeners()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 21	org/achartengine/tools/Pan:mPanListeners	Ljava/util/List;
    //   6: invokeinterface 40 1 0
    //   11: astore_2
    //   12: aload_2
    //   13: invokeinterface 46 1 0
    //   18: istore_3
    //   19: iload_3
    //   20: ifne +6 -> 26
    //   23: aload_0
    //   24: monitorexit
    //   25: return
    //   26: aload_2
    //   27: invokeinterface 50 1 0
    //   32: checkcast 52	org/achartengine/tools/PanListener
    //   35: invokeinterface 55 1 0
    //   40: goto -28 -> 12
    //   43: astore_1
    //   44: aload_0
    //   45: monitorexit
    //   46: aload_1
    //   47: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	48	0	this	Pan
    //   43	4	1	localObject	Object
    //   11	16	2	localIterator	java.util.Iterator
    //   18	2	3	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	12	43	finally
    //   12	19	43	finally
    //   26	40	43	finally
  }
  
  public void addPanListener(PanListener paramPanListener)
  {
    try
    {
      this.mPanListeners.add(paramPanListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void apply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (!AbstractChart.isMoveOut) {
      return;
    }
    if (Math.abs(paramFloat3 - paramFloat1) > 100.0F)
    {
      paramFloat3 = paramFloat1;
      paramFloat4 = paramFloat2;
    }
    Log.e("test", ">>>>>>>> Pan apply");
    int i = 1;
    int j = 1;
    int k = 1;
    int m = 1;
    double[] arrayOfDouble1;
    int i1;
    label91:
    XYChart localXYChart;
    int i2;
    if ((this.mChart instanceof XYChart))
    {
      int n = this.mRenderer.getScalesCount();
      arrayOfDouble1 = this.mRenderer.getPanLimits();
      if ((arrayOfDouble1 != null) && (arrayOfDouble1.length == 4))
      {
        i1 = 1;
        localXYChart = (XYChart)this.mChart;
        i2 = 0;
        if (i2 < n) {
          break label121;
        }
      }
    }
    for (;;)
    {
      notifyPanListeners();
      return;
      i1 = 0;
      break label91;
      label121:
      double[] arrayOfDouble2 = getRange(i2);
      double[] arrayOfDouble3 = localXYChart.getCalcRange(i2);
      if ((this.limitsReachedX) && (this.limitsReachedY) && (((arrayOfDouble2[0] == arrayOfDouble2[1]) && (arrayOfDouble3[0] == arrayOfDouble3[1])) || ((arrayOfDouble2[2] == arrayOfDouble2[3]) && (arrayOfDouble3[2] == arrayOfDouble3[3])))) {
        break;
      }
      checkRange(arrayOfDouble2, i2);
      double[] arrayOfDouble4 = localXYChart.toRealPoint(paramFloat1, paramFloat2, i2);
      double[] arrayOfDouble5 = localXYChart.toRealPoint(paramFloat3, paramFloat4, i2);
      double d1 = arrayOfDouble4[0] - arrayOfDouble5[0];
      double d2 = arrayOfDouble4[1] - arrayOfDouble5[1];
      double d3 = getAxisRatio(arrayOfDouble2);
      if (localXYChart.isVertical(this.mRenderer))
      {
        double d4 = d3 * -d2;
        double d5 = d1 / d3;
        d1 = d4;
        d2 = d5;
      }
      if (this.mRenderer.isPanXEnabled())
      {
        if (arrayOfDouble1 != null)
        {
          if (k != 0)
          {
            if (arrayOfDouble1[0] > d1 + arrayOfDouble2[0]) {
              break label504;
            }
            k = 1;
          }
          label334:
          if (m != 0)
          {
            if (arrayOfDouble1[1] < d1 + arrayOfDouble2[1]) {
              break label510;
            }
            m = 1;
          }
        }
        label357:
        if ((i1 != 0) && ((k == 0) || (m == 0))) {
          break label516;
        }
        setXRange(d1 + arrayOfDouble2[0], d1 + arrayOfDouble2[1], i2);
        this.limitsReachedX = false;
      }
      else
      {
        label397:
        if (this.mRenderer.isPanYEnabled())
        {
          if (arrayOfDouble1 != null)
          {
            if (j != 0)
            {
              if (arrayOfDouble1[2] > d2 + arrayOfDouble2[2]) {
                break label524;
              }
              j = 1;
            }
            label435:
            if (i != 0)
            {
              if (arrayOfDouble1[3] < d2 + arrayOfDouble2[3]) {
                break label530;
              }
              i = 1;
            }
          }
          label458:
          if ((i1 != 0) && ((j == 0) || (i == 0))) {
            break label536;
          }
          setYRange(d2 + arrayOfDouble2[2], d2 + arrayOfDouble2[3], i2);
        }
      }
      label504:
      label510:
      label516:
      label524:
      label530:
      label536:
      for (this.limitsReachedY = false;; this.limitsReachedY = true)
      {
        i2++;
        break;
        k = 0;
        break label334;
        m = 0;
        break label357;
        this.limitsReachedX = true;
        break label397;
        j = 0;
        break label435;
        i = 0;
        break label458;
      }
      RoundChart localRoundChart = (RoundChart)this.mChart;
      Log.i("test", ">>>   " + localRoundChart.getCenterX());
      localRoundChart.setCenterX(localRoundChart.getCenterX() + (int)(paramFloat3 - paramFloat1));
      localRoundChart.setCenterY(localRoundChart.getCenterY() + (int)(paramFloat4 - paramFloat2));
    }
  }
  
  public void removePanListener(PanListener paramPanListener)
  {
    try
    {
      this.mPanListeners.remove(paramPanListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\tools\Pan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */