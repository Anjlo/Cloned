package org.achartengine;

import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.RoundChart;
import org.achartengine.chart.XYChart;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.tools.Pan;
import org.achartengine.tools.PanListener;
import org.achartengine.tools.Zoom;
import org.achartengine.tools.ZoomListener;

public class TouchHandler
  implements ITouchHandler
{
  public static int isZoom = 0;
  public static int orientation = 0;
  private GraphicalView graphicalView;
  private int lastOrientation = 0;
  private AbstractChart mChart;
  private Pan mPan;
  private Zoom mPinchZoom;
  private DefaultRenderer mRenderer;
  private float oldX;
  private float oldX2;
  private float oldY;
  private float oldY2;
  private RectF zoomR = new RectF();
  
  public TouchHandler(GraphicalView paramGraphicalView, AbstractChart paramAbstractChart)
  {
    this.mChart = paramAbstractChart;
    this.graphicalView = paramGraphicalView;
    this.zoomR = this.graphicalView.getZoomRectangle();
    if ((paramAbstractChart instanceof XYChart)) {}
    for (this.mRenderer = ((XYChart)paramAbstractChart).getRenderer();; this.mRenderer = ((RoundChart)paramAbstractChart).getRenderer())
    {
      if (this.mRenderer.isPanEnabled()) {
        this.mPan = new Pan(paramAbstractChart);
      }
      if (this.mRenderer.isZoomEnabled()) {
        this.mPinchZoom = new Zoom(paramAbstractChart, true, 1.0F);
      }
      return;
    }
  }
  
  private void applyZoom(float paramFloat, int paramInt)
  {
    if (((isZoom == 2) && (GraphicalView.mZoomOutEnable)) || ((isZoom == 1) && (GraphicalView.mZoomInEnable)))
    {
      float f = Math.min(Math.max(paramFloat, 0.9F), 1.1F);
      if ((f > 0.9D) && (f < 1.1D))
      {
        if (!AbstractChart.isMoveOut) {
          break label79;
        }
        this.mPinchZoom.setZoomRate(f);
        this.mPinchZoom.apply(paramInt);
      }
    }
    label79:
    do
    {
      return;
      AbstractChart.isMoveOut = true;
      if (AbstractChart.sIndex == 2)
      {
        move(20.0F + this.oldX, this.oldY);
        return;
      }
    } while (AbstractChart.sIndex != 1);
    move(this.oldX - 20.0F, this.oldY);
  }
  
  public void addPanListener(PanListener paramPanListener)
  {
    if (this.mPan != null) {
      this.mPan.addPanListener(paramPanListener);
    }
  }
  
  public void addZoomListener(ZoomListener paramZoomListener)
  {
    if (this.mPinchZoom != null) {
      this.mPinchZoom.addZoomListener(paramZoomListener);
    }
  }
  
  public boolean handleTouch(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    if ((this.mRenderer != null) && (i == 2))
    {
      if ((this.oldX >= 0.0F) || (this.oldY >= 0.0F))
      {
        float f1 = paramMotionEvent.getX(0);
        float f2 = paramMotionEvent.getY(0);
        String str;
        if (f1 - this.oldX > 0.0F)
        {
          orientation = 2;
          if (orientation != 2) {
            break label335;
          }
          str = "��";
          label74:
          Log.e("test", "����" + str);
          if ((paramMotionEvent.getPointerCount() <= 1) || ((this.oldX2 < 0.0F) && (this.oldY2 < 0.0F)) || (!this.mRenderer.isZoomEnabled())) {
            break label432;
          }
          f3 = paramMotionEvent.getX(1);
          f4 = paramMotionEvent.getY(1);
          if (Math.abs(f3 - f1) <= Math.abs(this.oldX2 - this.oldX)) {
            break label342;
          }
          isZoom = 1;
          f5 = Math.abs(f1 - f3);
          f6 = Math.abs(f2 - f4);
          f7 = Math.abs(this.oldX - this.oldX2);
          f8 = Math.abs(this.oldY - this.oldY2);
          f9 = Math.abs(f2 - this.oldY) / Math.abs(f1 - this.oldX);
          f10 = Math.abs(f4 - this.oldY2) / Math.abs(f3 - this.oldX2);
          if ((f9 > 0.25D) || (f10 > 0.25D)) {
            break label349;
          }
          applyZoom(f5 / f7, 1);
          this.oldX2 = f3;
          this.oldY2 = f4;
        }
        label335:
        label342:
        label349:
        label382:
        label432:
        while (!this.mRenderer.isPanEnabled())
        {
          float f5;
          float f6;
          float f7;
          float f8;
          for (;;)
          {
            float f3;
            float f4;
            float f9;
            float f10;
            this.oldX = f1;
            this.oldY = f2;
            this.graphicalView.repaint();
            return true;
            orientation = 1;
            break;
            str = "��";
            break label74;
            isZoom = 2;
            continue;
            if ((f9 < 3.73D) || (f10 < 3.73D)) {
              break label382;
            }
            applyZoom(f6 / f8, 2);
          }
          if (Math.abs(f1 - this.oldX) >= Math.abs(f2 - this.oldY)) {}
          for (float f11 = f5 / f7;; f11 = f6 / f8)
          {
            applyZoom(f11, 0);
            break;
          }
        }
        if (AbstractChart.isMoveOut)
        {
          Log.e("test", "Touch: 1");
          this.mPan.apply(this.oldX, this.oldY, f1, f2);
        }
        for (;;)
        {
          this.oldX2 = 0.0F;
          this.oldY2 = 0.0F;
          break;
          if (AbstractChart.sIndex == orientation)
          {
            AbstractChart.isMoveOut = true;
            if (AbstractChart.sIndex == 2) {
              move(20.0F + this.oldX, this.oldY);
            } else if (AbstractChart.sIndex == 1) {
              move(this.oldX - 20.0F, this.oldY);
            }
          }
        }
      }
    }
    else if (i == 0)
    {
      isZoom = 0;
      this.oldX = paramMotionEvent.getX(0);
      this.oldY = paramMotionEvent.getY(0);
      if ((this.mRenderer != null) && (this.mRenderer.isZoomEnabled()) && (this.zoomR.contains(this.oldX, this.oldY)))
      {
        if (this.oldX < this.zoomR.left + this.zoomR.width() / 3.0F) {
          this.graphicalView.zoomIn();
        }
        for (;;)
        {
          return true;
          if (this.oldX < this.zoomR.left + 2.0F * this.zoomR.width() / 3.0F) {
            this.graphicalView.zoomOut();
          } else {
            this.graphicalView.zoomReset();
          }
        }
      }
    }
    else if ((i == 1) || (i == 6))
    {
      this.oldX = 0.0F;
      this.oldY = 0.0F;
      this.oldX2 = 0.0F;
      this.oldY2 = 0.0F;
      if (i == 6)
      {
        this.oldX = -1.0F;
        this.oldY = -1.0F;
      }
      isZoom = 0;
    }
    return !this.mRenderer.isClickEnabled();
  }
  
  public void move(float paramFloat1, float paramFloat2)
  {
    this.mPan.apply(this.oldX, this.oldY, paramFloat1, paramFloat2);
  }
  
  public void removePanListener(PanListener paramPanListener)
  {
    if (this.mPan != null) {
      this.mPan.removePanListener(paramPanListener);
    }
  }
  
  public void removeZoomListener(ZoomListener paramZoomListener)
  {
    if (this.mPinchZoom != null) {
      this.mPinchZoom.removeZoomListener(paramZoomListener);
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\TouchHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */