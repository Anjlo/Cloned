package org.achartengine.renderer;

import android.graphics.Color;
import android.graphics.Paint.Align;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class XYMultipleSeriesRenderer
  extends DefaultRenderer
{
  private Map<Integer, double[]> initialRange = new LinkedHashMap();
  private float mAxisTitleTextSize = 16.0F;
  private double mBarSpacing = 0.0D;
  private float mBarWidth = -1.0F;
  private int[] mGridColors;
  private NumberFormat mLabelFormat;
  private int mMarginsColor = 0;
  private double[] mMaxX;
  private double[] mMaxY;
  private double[] mMinX;
  private double[] mMinY;
  private Orientation mOrientation = Orientation.HORIZONTAL;
  private double[] mPanLimits;
  private boolean mPanXEnabled = true;
  private boolean mPanYEnabled = true;
  private float mPointSize = 3.0F;
  private int mXLabels = 5;
  private float mXLabelsAngle;
  private int mXLabelsColor = -3355444;
  private float mXLabelsPadding = 0.0F;
  private boolean mXRoundedLabels = true;
  private Map<Double, String> mXTextLabels = new HashMap();
  private String mXTitle = "";
  private int mYLabels = 5;
  private float mYLabelsAngle;
  private int[] mYLabelsColor = { -3355444 };
  private float mYLabelsPadding = 0.0F;
  private float mYLabelsVerticalPadding = 2.0F;
  private Map<Integer, Map<Double, String>> mYTextLabels = new LinkedHashMap();
  private String[] mYTitle;
  private double mZoomInLimitX = 0.0D;
  private double mZoomInLimitY = 0.0D;
  private double[] mZoomLimits;
  private boolean mZoomXEnabled = true;
  private boolean mZoomYEnabled = true;
  private int scalesCount;
  private Paint.Align xLabelsAlign = Paint.Align.CENTER;
  private Paint.Align[] yAxisAlign;
  private Paint.Align[] yLabelsAlign;
  
  public XYMultipleSeriesRenderer()
  {
    this(1);
  }
  
  public XYMultipleSeriesRenderer(int paramInt)
  {
    this.scalesCount = paramInt;
    initAxesRange(paramInt);
  }
  
  public void addTextLabel(double paramDouble, String paramString)
  {
    addXTextLabel(paramDouble, paramString);
  }
  
  public void addXTextLabel(double paramDouble, String paramString)
  {
    try
    {
      this.mXTextLabels.put(Double.valueOf(paramDouble), paramString);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void addYTextLabel(double paramDouble, String paramString)
  {
    addYTextLabel(paramDouble, paramString, 0);
  }
  
  public void addYTextLabel(double paramDouble, String paramString, int paramInt)
  {
    try
    {
      ((Map)this.mYTextLabels.get(Integer.valueOf(paramInt))).put(Double.valueOf(paramDouble), paramString);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void clearTextLabels()
  {
    clearXTextLabels();
  }
  
  public void clearXTextLabels()
  {
    try
    {
      this.mXTextLabels.clear();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void clearYTextLabels()
  {
    clearYTextLabels(0);
  }
  
  public void clearYTextLabels(int paramInt)
  {
    try
    {
      ((Map)this.mYTextLabels.get(Integer.valueOf(paramInt))).clear();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public float getAxisTitleTextSize()
  {
    return this.mAxisTitleTextSize;
  }
  
  public double getBarSpacing()
  {
    return this.mBarSpacing;
  }
  
  public float getBarWidth()
  {
    return this.mBarWidth;
  }
  
  public double getBarsSpacing()
  {
    return getBarSpacing();
  }
  
  public int getGridColor(int paramInt)
  {
    return this.mGridColors[paramInt];
  }
  
  public double[] getInitialRange()
  {
    return getInitialRange(0);
  }
  
  public double[] getInitialRange(int paramInt)
  {
    return (double[])this.initialRange.get(Integer.valueOf(paramInt));
  }
  
  public NumberFormat getLabelFormat()
  {
    return this.mLabelFormat;
  }
  
  public int getMarginsColor()
  {
    return this.mMarginsColor;
  }
  
  public Orientation getOrientation()
  {
    return this.mOrientation;
  }
  
  public double[] getPanLimits()
  {
    return this.mPanLimits;
  }
  
  public float getPointSize()
  {
    return this.mPointSize;
  }
  
  public int getScalesCount()
  {
    return this.scalesCount;
  }
  
  public double getXAxisMax()
  {
    return getXAxisMax(0);
  }
  
  public double getXAxisMax(int paramInt)
  {
    return this.mMaxX[paramInt];
  }
  
  public double getXAxisMin()
  {
    return getXAxisMin(0);
  }
  
  public double getXAxisMin(int paramInt)
  {
    return this.mMinX[paramInt];
  }
  
  public int getXLabels()
  {
    return this.mXLabels;
  }
  
  public Paint.Align getXLabelsAlign()
  {
    return this.xLabelsAlign;
  }
  
  public float getXLabelsAngle()
  {
    return this.mXLabelsAngle;
  }
  
  public int getXLabelsColor()
  {
    return this.mXLabelsColor;
  }
  
  public float getXLabelsPadding()
  {
    return this.mXLabelsPadding;
  }
  
  public String getXTextLabel(Double paramDouble)
  {
    try
    {
      String str = (String)this.mXTextLabels.get(paramDouble);
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public Double[] getXTextLabelLocations()
  {
    try
    {
      Double[] arrayOfDouble = (Double[])this.mXTextLabels.keySet().toArray(new Double[0]);
      return arrayOfDouble;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String getXTitle()
  {
    return this.mXTitle;
  }
  
  public Paint.Align getYAxisAlign(int paramInt)
  {
    return this.yAxisAlign[paramInt];
  }
  
  public double getYAxisMax()
  {
    return getYAxisMax(0);
  }
  
  public double getYAxisMax(int paramInt)
  {
    return this.mMaxY[paramInt];
  }
  
  public double getYAxisMin()
  {
    return getYAxisMin(0);
  }
  
  public double getYAxisMin(int paramInt)
  {
    return this.mMinY[paramInt];
  }
  
  public int getYLabels()
  {
    return this.mYLabels;
  }
  
  public Paint.Align getYLabelsAlign(int paramInt)
  {
    return this.yLabelsAlign[paramInt];
  }
  
  public float getYLabelsAngle()
  {
    return this.mYLabelsAngle;
  }
  
  public int getYLabelsColor(int paramInt)
  {
    return this.mYLabelsColor[paramInt];
  }
  
  public float getYLabelsPadding()
  {
    return this.mYLabelsPadding;
  }
  
  public float getYLabelsVerticalPadding()
  {
    return this.mYLabelsVerticalPadding;
  }
  
  public String getYTextLabel(Double paramDouble)
  {
    return getYTextLabel(paramDouble, 0);
  }
  
  public String getYTextLabel(Double paramDouble, int paramInt)
  {
    try
    {
      String str = (String)((Map)this.mYTextLabels.get(Integer.valueOf(paramInt))).get(paramDouble);
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public Double[] getYTextLabelLocations()
  {
    return getYTextLabelLocations(0);
  }
  
  public Double[] getYTextLabelLocations(int paramInt)
  {
    try
    {
      Double[] arrayOfDouble = (Double[])((Map)this.mYTextLabels.get(Integer.valueOf(paramInt))).keySet().toArray(new Double[0]);
      return arrayOfDouble;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String getYTitle()
  {
    return getYTitle(0);
  }
  
  public String getYTitle(int paramInt)
  {
    return this.mYTitle[paramInt];
  }
  
  public double getZoomInLimitX()
  {
    return this.mZoomInLimitX;
  }
  
  public double getZoomInLimitY()
  {
    return this.mZoomInLimitY;
  }
  
  public double[] getZoomLimits()
  {
    return this.mZoomLimits;
  }
  
  public void initAxesRange(int paramInt)
  {
    this.mYTitle = new String[paramInt];
    this.yLabelsAlign = new Paint.Align[paramInt];
    this.yAxisAlign = new Paint.Align[paramInt];
    this.mYLabelsColor = new int[paramInt];
    this.mMinX = new double[paramInt];
    this.mMaxX = new double[paramInt];
    this.mMinY = new double[paramInt];
    this.mMaxY = new double[paramInt];
    this.mGridColors = new int[paramInt];
    for (int i = 0;; i++)
    {
      if (i >= paramInt) {
        return;
      }
      this.mYLabelsColor[i] = -3355444;
      this.mGridColors[i] = Color.argb(75, 200, 200, 200);
      initAxesRangeForScale(i);
    }
  }
  
  public void initAxesRangeForScale(int paramInt)
  {
    this.mMinX[paramInt] = Double.MAX_VALUE;
    this.mMaxX[paramInt] = -1.7976931348623157E308D;
    this.mMinY[paramInt] = Double.MAX_VALUE;
    this.mMaxY[paramInt] = -1.7976931348623157E308D;
    double[] arrayOfDouble = new double[4];
    arrayOfDouble[0] = this.mMinX[paramInt];
    arrayOfDouble[1] = this.mMaxX[paramInt];
    arrayOfDouble[2] = this.mMinY[paramInt];
    arrayOfDouble[3] = this.mMaxY[paramInt];
    this.initialRange.put(Integer.valueOf(paramInt), arrayOfDouble);
    this.mYTitle[paramInt] = "";
    this.mYTextLabels.put(Integer.valueOf(paramInt), new HashMap());
    this.yLabelsAlign[paramInt] = Paint.Align.CENTER;
    this.yAxisAlign[paramInt] = Paint.Align.LEFT;
  }
  
  public boolean isInitialRangeSet()
  {
    return isInitialRangeSet(0);
  }
  
  public boolean isInitialRangeSet(int paramInt)
  {
    return this.initialRange.get(Integer.valueOf(paramInt)) != null;
  }
  
  public boolean isMaxXSet()
  {
    return isMaxXSet(0);
  }
  
  public boolean isMaxXSet(int paramInt)
  {
    return this.mMaxX[paramInt] != -1.7976931348623157E308D;
  }
  
  public boolean isMaxYSet()
  {
    return isMaxYSet(0);
  }
  
  public boolean isMaxYSet(int paramInt)
  {
    return this.mMaxY[paramInt] != -1.7976931348623157E308D;
  }
  
  public boolean isMinXSet()
  {
    return isMinXSet(0);
  }
  
  public boolean isMinXSet(int paramInt)
  {
    return this.mMinX[paramInt] != Double.MAX_VALUE;
  }
  
  public boolean isMinYSet()
  {
    return isMinYSet(0);
  }
  
  public boolean isMinYSet(int paramInt)
  {
    return this.mMinY[paramInt] != Double.MAX_VALUE;
  }
  
  public boolean isPanEnabled()
  {
    return (isPanXEnabled()) || (isPanYEnabled());
  }
  
  public boolean isPanXEnabled()
  {
    return this.mPanXEnabled;
  }
  
  public boolean isPanYEnabled()
  {
    return this.mPanYEnabled;
  }
  
  public boolean isXRoundedLabels()
  {
    return this.mXRoundedLabels;
  }
  
  public boolean isZoomEnabled()
  {
    return (isZoomXEnabled()) || (isZoomYEnabled());
  }
  
  public boolean isZoomXEnabled()
  {
    return this.mZoomXEnabled;
  }
  
  public boolean isZoomYEnabled()
  {
    return this.mZoomYEnabled;
  }
  
  public void removeXTextLabel(double paramDouble)
  {
    try
    {
      this.mXTextLabels.remove(Double.valueOf(paramDouble));
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void removeYTextLabel(double paramDouble)
  {
    removeYTextLabel(paramDouble, 0);
  }
  
  public void removeYTextLabel(double paramDouble, int paramInt)
  {
    try
    {
      ((Map)this.mYTextLabels.get(Integer.valueOf(paramInt))).remove(Double.valueOf(paramDouble));
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setAxisTitleTextSize(float paramFloat)
  {
    this.mAxisTitleTextSize = paramFloat;
  }
  
  public void setBarSpacing(double paramDouble)
  {
    this.mBarSpacing = paramDouble;
  }
  
  public void setBarWidth(float paramFloat)
  {
    this.mBarWidth = paramFloat;
  }
  
  public void setGridColor(int paramInt)
  {
    setGridColor(paramInt, 0);
  }
  
  public void setGridColor(int paramInt1, int paramInt2)
  {
    this.mGridColors[paramInt2] = paramInt1;
  }
  
  public void setInitialRange(double[] paramArrayOfDouble)
  {
    setInitialRange(paramArrayOfDouble, 0);
  }
  
  public void setInitialRange(double[] paramArrayOfDouble, int paramInt)
  {
    this.initialRange.put(Integer.valueOf(paramInt), paramArrayOfDouble);
  }
  
  public void setLabelFormat(NumberFormat paramNumberFormat)
  {
    this.mLabelFormat = paramNumberFormat;
  }
  
  public void setMarginsColor(int paramInt)
  {
    this.mMarginsColor = paramInt;
  }
  
  public void setOrientation(Orientation paramOrientation)
  {
    this.mOrientation = paramOrientation;
  }
  
  public void setPanEnabled(boolean paramBoolean)
  {
    setPanEnabled(paramBoolean, paramBoolean);
  }
  
  public void setPanEnabled(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mPanXEnabled = paramBoolean1;
    this.mPanYEnabled = paramBoolean2;
  }
  
  public void setPanLimits(double[] paramArrayOfDouble)
  {
    this.mPanLimits = paramArrayOfDouble;
  }
  
  public void setPointSize(float paramFloat)
  {
    this.mPointSize = paramFloat;
  }
  
  public void setRange(double[] paramArrayOfDouble)
  {
    setRange(paramArrayOfDouble, 0);
  }
  
  public void setRange(double[] paramArrayOfDouble, int paramInt)
  {
    setXAxisMin(paramArrayOfDouble[0], paramInt);
    setXAxisMax(paramArrayOfDouble[1], paramInt);
    setYAxisMin(paramArrayOfDouble[2], paramInt);
    setYAxisMax(paramArrayOfDouble[3], paramInt);
  }
  
  public void setXAxisMax(double paramDouble)
  {
    setXAxisMax(paramDouble, 0);
  }
  
  public void setXAxisMax(double paramDouble, int paramInt)
  {
    if (!isMaxXSet(paramInt)) {
      ((double[])this.initialRange.get(Integer.valueOf(paramInt)))[1] = paramDouble;
    }
    this.mMaxX[paramInt] = paramDouble;
  }
  
  public void setXAxisMin(double paramDouble)
  {
    setXAxisMin(paramDouble, 0);
  }
  
  public void setXAxisMin(double paramDouble, int paramInt)
  {
    if (!isMinXSet(paramInt)) {
      ((double[])this.initialRange.get(Integer.valueOf(paramInt)))[0] = paramDouble;
    }
    this.mMinX[paramInt] = paramDouble;
  }
  
  public void setXLabels(int paramInt)
  {
    this.mXLabels = paramInt;
  }
  
  public void setXLabelsAlign(Paint.Align paramAlign)
  {
    this.xLabelsAlign = paramAlign;
  }
  
  public void setXLabelsAngle(float paramFloat)
  {
    this.mXLabelsAngle = paramFloat;
  }
  
  public void setXLabelsColor(int paramInt)
  {
    this.mXLabelsColor = paramInt;
  }
  
  public void setXLabelsPadding(float paramFloat)
  {
    this.mXLabelsPadding = paramFloat;
  }
  
  public void setXRoundedLabels(boolean paramBoolean)
  {
    this.mXRoundedLabels = paramBoolean;
  }
  
  public void setXTitle(String paramString)
  {
    this.mXTitle = paramString;
  }
  
  public void setYAxisAlign(Paint.Align paramAlign, int paramInt)
  {
    this.yAxisAlign[paramInt] = paramAlign;
  }
  
  public void setYAxisMax(double paramDouble)
  {
    setYAxisMax(paramDouble, 0);
  }
  
  public void setYAxisMax(double paramDouble, int paramInt)
  {
    if (!isMaxYSet(paramInt)) {
      ((double[])this.initialRange.get(Integer.valueOf(paramInt)))[3] = paramDouble;
    }
    this.mMaxY[paramInt] = paramDouble;
  }
  
  public void setYAxisMin(double paramDouble)
  {
    setYAxisMin(paramDouble, 0);
  }
  
  public void setYAxisMin(double paramDouble, int paramInt)
  {
    if (!isMinYSet(paramInt)) {
      ((double[])this.initialRange.get(Integer.valueOf(paramInt)))[2] = paramDouble;
    }
    this.mMinY[paramInt] = paramDouble;
  }
  
  public void setYLabels(int paramInt)
  {
    this.mYLabels = paramInt;
  }
  
  public void setYLabelsAlign(Paint.Align paramAlign)
  {
    setYLabelsAlign(paramAlign, 0);
  }
  
  public void setYLabelsAlign(Paint.Align paramAlign, int paramInt)
  {
    this.yLabelsAlign[paramInt] = paramAlign;
  }
  
  public void setYLabelsAngle(float paramFloat)
  {
    this.mYLabelsAngle = paramFloat;
  }
  
  public void setYLabelsColor(int paramInt1, int paramInt2)
  {
    this.mYLabelsColor[paramInt1] = paramInt2;
  }
  
  public void setYLabelsPadding(float paramFloat)
  {
    this.mYLabelsPadding = paramFloat;
  }
  
  public void setYLabelsVerticalPadding(float paramFloat)
  {
    this.mYLabelsVerticalPadding = paramFloat;
  }
  
  public void setYTitle(String paramString)
  {
    setYTitle(paramString, 0);
  }
  
  public void setYTitle(String paramString, int paramInt)
  {
    this.mYTitle[paramInt] = paramString;
  }
  
  public void setZoomEnabled(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mZoomXEnabled = paramBoolean1;
    this.mZoomYEnabled = paramBoolean2;
  }
  
  public void setZoomInLimitX(double paramDouble)
  {
    this.mZoomInLimitX = paramDouble;
  }
  
  public void setZoomInLimitY(double paramDouble)
  {
    this.mZoomInLimitY = paramDouble;
  }
  
  public void setZoomLimits(double[] paramArrayOfDouble)
  {
    this.mZoomLimits = paramArrayOfDouble;
  }
  
  public static enum Orientation
  {
    VERTICAL(90),  HORIZONTAL(0);
    
    private int mAngle = 0;
    
    private Orientation(int paramInt1)
    {
      this.mAngle = paramInt1;
    }
    
    public int getAngle()
    {
      return this.mAngle;
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\renderer\XYMultipleSeriesRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */