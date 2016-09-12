package org.achartengine;

import android.view.MotionEvent;
import org.achartengine.tools.PanListener;
import org.achartengine.tools.ZoomListener;

public abstract interface ITouchHandler
{
  public abstract void addPanListener(PanListener paramPanListener);
  
  public abstract void addZoomListener(ZoomListener paramZoomListener);
  
  public abstract boolean handleTouch(MotionEvent paramMotionEvent);
  
  public abstract void move(float paramFloat1, float paramFloat2);
  
  public abstract void removePanListener(PanListener paramPanListener);
  
  public abstract void removeZoomListener(ZoomListener paramZoomListener);
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\ITouchHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */