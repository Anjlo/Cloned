package org.achartengine.model;

import java.io.Serializable;

public final class Point
  implements Serializable
{
  private float mX;
  private float mY;
  
  public Point() {}
  
  public Point(float paramFloat1, float paramFloat2)
  {
    this.mX = paramFloat1;
    this.mY = paramFloat2;
  }
  
  public float getX()
  {
    return this.mX;
  }
  
  public float getY()
  {
    return this.mY;
  }
  
  public void setX(float paramFloat)
  {
    this.mX = paramFloat;
  }
  
  public void setY(float paramFloat)
  {
    this.mY = paramFloat;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\model\Point.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */