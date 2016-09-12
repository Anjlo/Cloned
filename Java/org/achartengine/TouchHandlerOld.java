package org.achartengine;

import android.graphics.RectF;
import android.view.MotionEvent;
import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.RoundChart;
import org.achartengine.chart.XYChart;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.tools.Pan;
import org.achartengine.tools.PanListener;
import org.achartengine.tools.ZoomListener;

public class TouchHandlerOld
  implements ITouchHandler
{
  private GraphicalView graphicalView;
  private Pan mPan;
  private DefaultRenderer mRenderer;
  private float oldX;
  private float oldY;
  private RectF zoomR = new RectF();
  
  public TouchHandlerOld(GraphicalView paramGraphicalView, AbstractChart paramAbstractChart)
  {
    this.graphicalView = paramGraphicalView;
    this.zoomR = this.graphicalView.getZoomRectangle();
    if ((paramAbstractChart instanceof XYChart)) {}
    for (this.mRenderer = ((XYChart)paramAbstractChart).getRenderer();; this.mRenderer = ((RoundChart)paramAbstractChart).getRenderer())
    {
      if (this.mRenderer.isPanEnabled()) {
        this.mPan = new Pan(paramAbstractChart);
      }
      return;
    }
  }
  
  public void addPanListener(PanListener paramPanListener)
  {
    if (this.mPan != null) {
      this.mPan.addPanListener(paramPanListener);
    }
  }
  
  public void addZoomListener(ZoomListener paramZoomListener) {}
  
  public boolean handleTouch(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    if ((this.mRenderer != null) && (i == 2))
    {
      if ((this.oldX < 0.0F) && (this.oldY < 0.0F)) {
        break label245;
      }
      f1 = paramMotionEvent.getX();
      f2 = paramMotionEvent.getY();
      if (this.mRenderer.isPanEnabled()) {
        this.mPan.apply(this.oldX, this.oldY, f1, f2);
      }
      this.oldX = f1;
      this.oldY = f2;
      this.graphicalView.repaint();
    }
    label245:
    while (!this.mRenderer.isClickEnabled())
    {
      float f1;
      float f2;
      return true;
      if (i == 0)
      {
        this.oldX = paramMotionEvent.getX();
        this.oldY = paramMotionEvent.getY();
        if ((this.mRenderer != null) && (this.mRenderer.isZoomEnabled()) && (this.zoomR.contains(this.oldX, this.oldY)))
        {
          if (this.oldX < this.zoomR.left + this.zoomR.width() / 3.0F)
          {
            this.graphicalView.zoomIn();
            return true;
          }
          if (this.oldX < this.zoomR.left + 2.0F * this.zoomR.width() / 3.0F)
          {
            this.graphicalView.zoomOut();
            return true;
          }
          this.graphicalView.zoomReset();
          return true;
        }
      }
      else if (i == 1)
      {
        this.oldX = 0.0F;
        this.oldY = 0.0F;
      }
    }
    return false;
  }
  
  public void move(float paramFloat1, float paramFloat2) {}
  
  public void removePanListener(PanListener paramPanListener)
  {
    if (this.mPan != null) {
      this.mPan.removePanListener(paramPanListener);
    }
  }
  
  public void removeZoomListener(ZoomListener paramZoomListener) {}
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\TouchHandlerOld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */