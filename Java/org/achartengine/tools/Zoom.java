package org.achartengine.tools;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.RoundChart;
import org.achartengine.chart.XYChart;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

public class Zoom
  extends AbstractTool
{
  public static final int ZOOM_AXIS_X = 1;
  public static final int ZOOM_AXIS_XY = 0;
  public static final int ZOOM_AXIS_Y = 2;
  private boolean limitsReachedX = false;
  private boolean limitsReachedY = false;
  private boolean mZoomIn;
  private List<ZoomListener> mZoomListeners = new ArrayList();
  private float mZoomRate;
  
  public Zoom(AbstractChart paramAbstractChart, boolean paramBoolean, float paramFloat)
  {
    super(paramAbstractChart);
    this.mZoomIn = paramBoolean;
    setZoomRate(paramFloat);
  }
  
  /* Error */
  private void notifyZoomListeners(ZoomEvent paramZoomEvent)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 32	org/achartengine/tools/Zoom:mZoomListeners	Ljava/util/List;
    //   6: invokeinterface 61 1 0
    //   11: astore_3
    //   12: aload_3
    //   13: invokeinterface 67 1 0
    //   18: istore 4
    //   20: iload 4
    //   22: ifne +6 -> 28
    //   25: aload_0
    //   26: monitorexit
    //   27: return
    //   28: aload_3
    //   29: invokeinterface 71 1 0
    //   34: checkcast 73	org/achartengine/tools/ZoomListener
    //   37: aload_1
    //   38: invokeinterface 76 2 0
    //   43: goto -31 -> 12
    //   46: astore_2
    //   47: aload_0
    //   48: monitorexit
    //   49: aload_2
    //   50: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	51	0	this	Zoom
    //   0	51	1	paramZoomEvent	ZoomEvent
    //   46	4	2	localObject	Object
    //   11	18	3	localIterator	java.util.Iterator
    //   18	3	4	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	12	46	finally
    //   12	20	46	finally
    //   28	43	46	finally
  }
  
  public void addZoomListener(ZoomListener paramZoomListener)
  {
    try
    {
      this.mZoomListeners.add(paramZoomListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void apply(final int paramInt)
  {
    Log.e("test", ">>>>>>>> Zoom apply");
    if ((this.mChart instanceof XYChart))
    {
      new Thread(new Runnable()
      {
        public void run()
        {
          int i = Zoom.this.mRenderer.getScalesCount();
          int j = 0;
          if (j >= i) {
            return;
          }
          double[] arrayOfDouble1 = Zoom.this.getRange(j);
          Zoom.this.checkRange(arrayOfDouble1, j);
          double[] arrayOfDouble2 = Zoom.this.mRenderer.getZoomLimits();
          double d1 = (arrayOfDouble1[0] + arrayOfDouble1[1]) / 2.0D;
          double d2 = (arrayOfDouble1[2] + arrayOfDouble1[3]) / 2.0D;
          double d3 = arrayOfDouble1[1] - arrayOfDouble1[0];
          double d4 = arrayOfDouble1[3] - arrayOfDouble1[2];
          double d5 = d1 - d3 / 2.0D;
          double d6 = d1 + d3 / 2.0D;
          double d7 = d2 - d4 / 2.0D;
          double d8 = d2 + d4 / 2.0D;
          boolean bool1;
          label175:
          boolean bool2;
          label216:
          label359:
          double d9;
          if (j == 0)
          {
            Zoom localZoom1 = Zoom.this;
            if ((arrayOfDouble2 != null) && ((d5 <= arrayOfDouble2[0]) || (d6 >= arrayOfDouble2[1])))
            {
              bool1 = true;
              localZoom1.limitsReachedX = bool1;
              Zoom localZoom2 = Zoom.this;
              if ((arrayOfDouble2 == null) || ((d7 > arrayOfDouble2[2]) && (d8 < arrayOfDouble2[3]))) {
                break label566;
              }
              bool2 = true;
              localZoom2.limitsReachedY = bool2;
            }
          }
          else
          {
            if (!Zoom.this.mZoomIn) {
              break label572;
            }
            if ((Zoom.this.mRenderer.isZoomXEnabled()) && ((paramInt == 1) || (paramInt == 0)) && ((!Zoom.this.limitsReachedX) || (Zoom.this.mZoomRate >= 1.0F))) {
              d3 /= Zoom.this.mZoomRate;
            }
            if ((Zoom.this.mRenderer.isZoomYEnabled()) && ((paramInt == 2) || (paramInt == 0)) && ((!Zoom.this.limitsReachedY) || (Zoom.this.mZoomRate >= 1.0F))) {
              d4 /= Zoom.this.mZoomRate;
            }
            if (arrayOfDouble2 == null) {
              break label677;
            }
            d9 = Math.min(Zoom.this.mRenderer.getZoomInLimitX(), arrayOfDouble2[1] - arrayOfDouble2[0]);
          }
          for (double d10 = Math.min(Zoom.this.mRenderer.getZoomInLimitY(), arrayOfDouble2[3] - arrayOfDouble2[2]);; d10 = Zoom.this.mRenderer.getZoomInLimitY())
          {
            double d11 = Math.max(d3, d9);
            double d12 = Math.max(d4, d10);
            if ((Zoom.this.mRenderer.isZoomXEnabled()) && ((paramInt == 1) || (paramInt == 0)))
            {
              double d15 = d1 - d11 / 2.0D;
              double d16 = d1 + d11 / 2.0D;
              Zoom.this.setXRange(d15, d16, j);
            }
            if ((Zoom.this.mRenderer.isZoomYEnabled()) && ((paramInt == 2) || (paramInt == 0)))
            {
              double d13 = d2 - d12 / 2.0D;
              double d14 = d2 + d12 / 2.0D;
              Zoom.this.setYRange(d13, d14, j);
            }
            j++;
            break;
            bool1 = false;
            break label175;
            label566:
            bool2 = false;
            break label216;
            label572:
            if ((Zoom.this.mRenderer.isZoomXEnabled()) && (!Zoom.this.limitsReachedX) && ((paramInt == 1) || (paramInt == 0))) {
              d3 *= Zoom.this.mZoomRate;
            }
            if ((!Zoom.this.mRenderer.isZoomYEnabled()) || (Zoom.this.limitsReachedY) || ((paramInt != 2) && (paramInt != 0))) {
              break label359;
            }
            d4 *= Zoom.this.mZoomRate;
            break label359;
            label677:
            d9 = Zoom.this.mRenderer.getZoomInLimitX();
          }
        }
      }).start();
      return;
    }
    DefaultRenderer localDefaultRenderer = ((RoundChart)this.mChart).getRenderer();
    if (this.mZoomIn)
    {
      localDefaultRenderer.setScale(localDefaultRenderer.getScale() * this.mZoomRate);
      return;
    }
    localDefaultRenderer.setScale(localDefaultRenderer.getScale() / this.mZoomRate);
  }
  
  /* Error */
  public void notifyZoomResetListeners()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 32	org/achartengine/tools/Zoom:mZoomListeners	Ljava/util/List;
    //   6: invokeinterface 61 1 0
    //   11: astore_2
    //   12: aload_2
    //   13: invokeinterface 67 1 0
    //   18: istore_3
    //   19: iload_3
    //   20: ifne +6 -> 26
    //   23: aload_0
    //   24: monitorexit
    //   25: return
    //   26: aload_2
    //   27: invokeinterface 71 1 0
    //   32: checkcast 73	org/achartengine/tools/ZoomListener
    //   35: invokeinterface 132 1 0
    //   40: goto -28 -> 12
    //   43: astore_1
    //   44: aload_0
    //   45: monitorexit
    //   46: aload_1
    //   47: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	48	0	this	Zoom
    //   43	4	1	localObject	Object
    //   11	16	2	localIterator	java.util.Iterator
    //   18	2	3	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	12	43	finally
    //   12	19	43	finally
    //   26	40	43	finally
  }
  
  public void removeZoomListener(ZoomListener paramZoomListener)
  {
    try
    {
      this.mZoomListeners.remove(paramZoomListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setZoomRate(float paramFloat)
  {
    this.mZoomRate = paramFloat;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\tools\Zoom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */