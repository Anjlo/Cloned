package org.achartengine.tools;

public class ZoomEvent
{
  private boolean mZoomIn;
  private float mZoomRate;
  
  public ZoomEvent(boolean paramBoolean, float paramFloat)
  {
    this.mZoomIn = paramBoolean;
    this.mZoomRate = paramFloat;
  }
  
  public float getZoomRate()
  {
    return this.mZoomRate;
  }
  
  public boolean isZoomIn()
  {
    return this.mZoomIn;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\tools\ZoomEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */