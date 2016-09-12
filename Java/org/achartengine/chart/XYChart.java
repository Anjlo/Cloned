package org.achartengine.chart;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import com.lefu.es.system.LoadingActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import org.achartengine.TouchHandler;
import org.achartengine.model.Point;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.util.MathHelper;

public abstract class XYChart
  extends AbstractChart
{
  static Bitmap[] bArray;
  static Bitmap bitmap1;
  static Bitmap bitmap2;
  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  public static int weightType = 1;
  private Map<Integer, List<ClickableArea>> clickableAreas = new HashMap();
  private Context context;
  private boolean isWeight = false;
  private final Map<Integer, double[]> mCalcRange = new HashMap();
  private Point mCenter;
  protected XYMultipleSeriesDataset mDataset;
  protected XYMultipleSeriesRenderer mRenderer;
  private float mScale;
  private Rect mScreenR;
  private float mTranslate;
  float offsetX;
  float offsetX2;
  float offsetY;
  float offsetY2;
  float offtemp;
  private SeriesSelection seriesSelection;
  int showCount = 0;
  
  protected XYChart() {}
  
  public XYChart(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer)
  {
    this.context = paramContext;
    this.isWeight = paramBoolean;
    this.mDataset = paramXYMultipleSeriesDataset;
    this.mRenderer = paramXYMultipleSeriesRenderer;
    this.offsetY = paramContext.getResources().getDimension(2131296258);
    this.offsetX = paramContext.getResources().getDimension(2131296259);
    this.offtemp = paramContext.getResources().getDimension(2131296259);
    this.offsetX2 = paramContext.getResources().getDimension(2131296260);
    this.offsetY2 = paramContext.getResources().getDimension(2131296261);
    if (LoadingActivity.isPad) {
      this.offsetY2 = (5.0F + this.offsetY2);
    }
    Bitmap[] arrayOfBitmap = new Bitmap[8];
    arrayOfBitmap[0] = BitmapFactory.decodeResource(paramContext.getResources(), 2130837598);
    arrayOfBitmap[1] = BitmapFactory.decodeResource(paramContext.getResources(), 2130837594);
    arrayOfBitmap[2] = BitmapFactory.decodeResource(paramContext.getResources(), 2130837600);
    arrayOfBitmap[3] = BitmapFactory.decodeResource(paramContext.getResources(), 2130837599);
    arrayOfBitmap[4] = BitmapFactory.decodeResource(paramContext.getResources(), 2130837601);
    arrayOfBitmap[5] = BitmapFactory.decodeResource(paramContext.getResources(), 2130837595);
    arrayOfBitmap[6] = BitmapFactory.decodeResource(paramContext.getResources(), 2130837596);
    arrayOfBitmap[7] = BitmapFactory.decodeResource(paramContext.getResources(), 2130837597);
    bArray = arrayOfBitmap;
  }
  
  private int getLabelLinePos(Paint.Align paramAlign)
  {
    int i = 4;
    if (paramAlign == Paint.Align.LEFT) {
      i = -i;
    }
    return i;
  }
  
  private List<Double> getValidLabels(List<Double> paramList)
  {
    ArrayList localArrayList = new ArrayList(paramList);
    Iterator localIterator = paramList.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return localArrayList;
      }
      Double localDouble = (Double)localIterator.next();
      if (localDouble.isNaN()) {
        localArrayList.remove(localDouble);
      }
    }
  }
  
  private void setStroke(Paint.Cap paramCap, Paint.Join paramJoin, float paramFloat, Paint.Style paramStyle, PathEffect paramPathEffect, Paint paramPaint)
  {
    paramPaint.setStrokeCap(paramCap);
    paramPaint.setStrokeJoin(paramJoin);
    paramPaint.setStrokeMiter(paramFloat);
    paramPaint.setPathEffect(paramPathEffect);
    paramPaint.setStyle(paramStyle);
  }
  
  private void transform(Canvas paramCanvas, float paramFloat, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      paramCanvas.scale(1.0F / this.mScale, this.mScale);
      paramCanvas.translate(this.mTranslate, -this.mTranslate);
      paramCanvas.rotate(-paramFloat, this.mCenter.getX(), this.mCenter.getY());
      return;
    }
    paramCanvas.rotate(paramFloat, this.mCenter.getX(), this.mCenter.getY());
    paramCanvas.translate(-this.mTranslate, this.mTranslate);
    paramCanvas.scale(this.mScale, 1.0F / this.mScale);
  }
  
  protected abstract ClickableArea[] clickableAreasForPoints(List<Float> paramList, List<Double> paramList1, float paramFloat, int paramInt1, int paramInt2);
  
  public void draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Paint paramPaint)
  {
    paramPaint.setAntiAlias(this.mRenderer.isAntialiasing());
    int i = getLegendSize(this.mRenderer, paramInt4 / 5, this.mRenderer.getAxisTitleTextSize());
    int[] arrayOfInt = this.mRenderer.getMargins();
    int j = paramInt1 + arrayOfInt[1];
    int k = paramInt2 + arrayOfInt[0];
    int m = paramInt1 + paramInt3 - arrayOfInt[3];
    int n = this.mDataset.getSeriesCount();
    String[] arrayOfString = new String[n];
    int i1 = 0;
    int i2;
    label306:
    XYMultipleSeriesRenderer.Orientation localOrientation;
    float f1;
    int i3;
    int i4;
    label447:
    int i5;
    if (i1 >= n)
    {
      if ((this.mRenderer.isFitLegend()) && (this.mRenderer.isShowLegend())) {
        i = drawLegend(paramCanvas, this.mRenderer, arrayOfString, j, m, paramInt2, paramInt3, paramInt4, i, paramPaint, true);
      }
      i2 = paramInt2 + paramInt4 - arrayOfInt[2] - i;
      if (this.mScreenR == null) {
        this.mScreenR = new Rect();
      }
      this.mScreenR.set(j, k, m, i2);
      drawBackground(this.mRenderer, paramCanvas, paramInt1, paramInt2, paramInt3, paramInt4, paramPaint, false, 0);
      if ((paramPaint.getTypeface() == null) || ((this.mRenderer.getTextTypeface() != null) && (paramPaint.getTypeface().equals(this.mRenderer.getTextTypeface()))) || (!paramPaint.getTypeface().toString().equals(this.mRenderer.getTextTypefaceName())) || (paramPaint.getTypeface().getStyle() != this.mRenderer.getTextTypefaceStyle()))
      {
        if (this.mRenderer.getTextTypeface() == null) {
          break label583;
        }
        paramPaint.setTypeface(this.mRenderer.getTextTypeface());
      }
      localOrientation = this.mRenderer.getOrientation();
      if (localOrientation == XYMultipleSeriesRenderer.Orientation.VERTICAL)
      {
        m -= i;
        i2 += i - 20;
      }
      f1 = this.mRenderer.getAxisTitleTextSize();
      paramPaint.setColor(this.mRenderer.getLabelsColor());
      paramPaint.setTextSize(f1);
      paramPaint.setTextAlign(Paint.Align.CENTER);
      drawText(paramCanvas, this.mRenderer.getXTitle(), -60 + (paramInt1 + paramInt3), i2 - 20, paramPaint, 0.0F);
      drawText(paramCanvas, this.mRenderer.getYTitle(), paramInt1 + 80, paramInt2 + 50, paramPaint, 0.0F);
      i3 = localOrientation.getAngle();
      if (i3 != 90) {
        break label609;
      }
      i4 = 1;
      this.mScale = (paramInt4 / paramInt3);
      this.mTranslate = (Math.abs(paramInt3 - paramInt4) / 2);
      if (this.mScale < 1.0F) {
        this.mTranslate = (-1.0F * this.mTranslate);
      }
      this.mCenter = new Point((paramInt1 + paramInt3) / 2, (paramInt2 + paramInt4) / 2);
      if (i4 != 0) {
        transform(paramCanvas, i3, false);
      }
      i5 = -2147483647;
    }
    int i8;
    for (int i6 = 0;; i6++)
    {
      if (i6 >= n)
      {
        i8 = i5 + 1;
        if (i8 >= 0) {
          break label644;
        }
        return;
        arrayOfString[i1] = this.mDataset.getSeriesAt(i1).getTitle();
        i1++;
        break;
        label583:
        paramPaint.setTypeface(Typeface.create(this.mRenderer.getTextTypefaceName(), this.mRenderer.getTextTypefaceStyle()));
        break label306;
        label609:
        i4 = 0;
        break label447;
      }
      int i7 = this.mDataset.getSeriesAt(i6).getScaleNumber();
      i5 = Math.max(i5, i7);
    }
    label644:
    double[] arrayOfDouble1 = new double[i8];
    double[] arrayOfDouble2 = new double[i8];
    double[] arrayOfDouble3 = new double[i8];
    double[] arrayOfDouble4 = new double[i8];
    boolean[] arrayOfBoolean1 = new boolean[i8];
    boolean[] arrayOfBoolean2 = new boolean[i8];
    boolean[] arrayOfBoolean3 = new boolean[i8];
    boolean[] arrayOfBoolean4 = new boolean[i8];
    int i9 = 0;
    double[] arrayOfDouble5;
    double[] arrayOfDouble6;
    int i10;
    int i12;
    if (i9 >= i8)
    {
      arrayOfDouble5 = new double[i8];
      arrayOfDouble6 = new double[i8];
      i10 = 0;
      if (i10 < n) {
        break label1522;
      }
      i12 = 0;
      label727:
      if (i12 < i8) {
        break label1777;
      }
      i13 = 0;
      this.clickableAreas = new HashMap();
    }
    label897:
    int i23;
    label915:
    boolean bool2;
    int i27;
    label1112:
    int i26;
    label1162:
    label1213:
    int i24;
    int i25;
    label1354:
    label1522:
    label1777:
    XYSeries localXYSeries2;
    int i15;
    label1921:
    for (int i14 = 0;; i14++)
    {
      if (i14 >= n)
      {
        drawBackground(this.mRenderer, paramCanvas, paramInt1, i2, paramInt3, paramInt4 - i2, paramPaint, true, this.mRenderer.getMarginsColor());
        drawBackground(this.mRenderer, paramCanvas, paramInt1, paramInt2, paramInt3, arrayOfInt[0], paramPaint, true, this.mRenderer.getMarginsColor());
        if (localOrientation != XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
          break label2651;
        }
        drawBackground(this.mRenderer, paramCanvas, paramInt1, paramInt2, j - paramInt1, paramInt4 - paramInt2, paramPaint, true, this.mRenderer.getMarginsColor());
        XYMultipleSeriesRenderer localXYMultipleSeriesRenderer2 = this.mRenderer;
        int i30 = arrayOfInt[3];
        int i31 = paramInt4 - paramInt2;
        int i32 = this.mRenderer.getMarginsColor();
        drawBackground(localXYMultipleSeriesRenderer2, paramCanvas, m, paramInt2, i30, i31, paramPaint, true, i32);
        if ((!this.mRenderer.isShowLabels()) || (i13 == 0)) {
          break label2738;
        }
        i23 = 1;
        boolean bool1 = this.mRenderer.isShowGridX();
        bool2 = this.mRenderer.isShowCustomTextGridY();
        if ((i23 != 0) || (bool1))
        {
          List localList = getValidLabels(getXLabels(arrayOfDouble1[0], arrayOfDouble2[0], this.mRenderer.getXLabels()));
          Map localMap = getYLabels(arrayOfDouble3, arrayOfDouble4, i8);
          if (i23 != 0)
          {
            paramPaint.setColor(this.mRenderer.getXLabelsColor());
            paramPaint.setTextSize(this.mRenderer.getLabelsTextSize());
            paramPaint.setTextAlign(this.mRenderer.getXLabelsAlign());
          }
          Double[] arrayOfDouble7 = this.mRenderer.getXTextLabelLocations();
          double d7 = arrayOfDouble5[0];
          double d8 = arrayOfDouble1[0];
          double d9 = arrayOfDouble2[0];
          drawXLabels(localList, arrayOfDouble7, paramCanvas, paramPaint, j, k, i2, d7, d8, d9);
          drawYLabels(localMap, paramCanvas, paramPaint, i8, j, m, i2, arrayOfDouble6, arrayOfDouble3);
          if (i23 != 0)
          {
            paramPaint.setColor(this.mRenderer.getLabelsColor());
            i27 = 0;
            if (i27 < i8) {
              break label2744;
            }
          }
          if (i23 != 0)
          {
            paramPaint.setColor(this.mRenderer.getLabelsColor());
            paramPaint.setTextSize(f1);
            paramPaint.setTextAlign(Paint.Align.CENTER);
            if (localOrientation != XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
              break label3191;
            }
            i26 = 0;
            if (i26 < i8) {
              break label3144;
            }
            paramPaint.setTextSize(this.mRenderer.getChartTitleTextSize());
            drawText(paramCanvas, this.mRenderer.getChartTitle(), paramInt1 + paramInt3 / 2, paramInt2 + this.mRenderer.getChartTitleTextSize(), paramPaint, 0.0F);
          }
        }
        if ((localOrientation != XYMultipleSeriesRenderer.Orientation.HORIZONTAL) && (localOrientation == XYMultipleSeriesRenderer.Orientation.VERTICAL))
        {
          transform(paramCanvas, i3, true);
          transform(paramCanvas, i3, false);
        }
        if (this.mRenderer.isShowAxes())
        {
          paramPaint.setColor(this.mRenderer.getAxesColor());
          paramCanvas.drawLine(j, i2, m, i2, paramPaint);
          i24 = 0;
          i25 = 0;
          if ((i25 < i8) && (i24 == 0)) {
            break label3310;
          }
          if (localOrientation != XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
            break label3340;
          }
          paramCanvas.drawLine(j, k, j, i2, paramPaint);
          if (i24 != 0) {
            paramCanvas.drawLine(m, k, m, i2, paramPaint);
          }
        }
        if (i4 == 0) {
          break label3367;
        }
        transform(paramCanvas, i3, true);
        return;
        arrayOfDouble1[i9] = this.mRenderer.getXAxisMin(i9);
        arrayOfDouble2[i9] = this.mRenderer.getXAxisMax(i9);
        arrayOfDouble3[i9] = this.mRenderer.getYAxisMin(i9);
        arrayOfDouble4[i9] = this.mRenderer.getYAxisMax(i9);
        arrayOfBoolean1[i9] = this.mRenderer.isMinXSet(i9);
        arrayOfBoolean2[i9] = this.mRenderer.isMaxXSet(i9);
        arrayOfBoolean3[i9] = this.mRenderer.isMinYSet(i9);
        arrayOfBoolean4[i9] = this.mRenderer.isMaxYSet(i9);
        if (this.mCalcRange.get(Integer.valueOf(i9)) == null) {
          this.mCalcRange.put(Integer.valueOf(i9), new double[4]);
        }
        i9++;
        break;
        XYSeries localXYSeries1 = this.mDataset.getSeriesAt(i10);
        int i11 = localXYSeries1.getScaleNumber();
        if (localXYSeries1.getItemCount() == 0) {}
        for (;;)
        {
          i10++;
          break;
          if (arrayOfBoolean1[i11] == 0)
          {
            double d4 = localXYSeries1.getMinX();
            arrayOfDouble1[i11] = Math.min(arrayOfDouble1[i11], d4);
            ((double[])this.mCalcRange.get(Integer.valueOf(i11)))[0] = arrayOfDouble1[i11];
          }
          if (arrayOfBoolean2[i11] == 0)
          {
            double d3 = localXYSeries1.getMaxX();
            arrayOfDouble2[i11] = Math.max(arrayOfDouble2[i11], d3);
            ((double[])this.mCalcRange.get(Integer.valueOf(i11)))[1] = arrayOfDouble2[i11];
          }
          if (arrayOfBoolean3[i11] == 0)
          {
            double d2 = localXYSeries1.getMinY();
            arrayOfDouble3[i11] = Math.min(arrayOfDouble3[i11], (float)d2);
            ((double[])this.mCalcRange.get(Integer.valueOf(i11)))[2] = arrayOfDouble3[i11];
          }
          if (arrayOfBoolean4[i11] == 0)
          {
            double d1 = localXYSeries1.getMaxY();
            arrayOfDouble4[i11] = Math.max(arrayOfDouble4[i11], (float)d1);
            ((double[])this.mCalcRange.get(Integer.valueOf(i11)))[3] = arrayOfDouble4[i11];
          }
        }
        if (arrayOfDouble2[i12] - arrayOfDouble1[i12] != 0.0D) {
          arrayOfDouble5[i12] = ((m - j) / (arrayOfDouble2[i12] - arrayOfDouble1[i12]));
        }
        if (arrayOfDouble4[i12] - arrayOfDouble3[i12] != 0.0D) {
          arrayOfDouble6[i12] = ((float)((i2 - k) / (arrayOfDouble4[i12] - arrayOfDouble3[i12])));
        }
        if (i12 > 0)
        {
          arrayOfDouble5[i12] = arrayOfDouble5[0];
          arrayOfDouble1[i12] = arrayOfDouble1[0];
          arrayOfDouble2[i12] = arrayOfDouble2[0];
        }
        i12++;
        break label727;
      }
      localXYSeries2 = this.mDataset.getSeriesAt(i14);
      i15 = localXYSeries2.getScaleNumber();
      if (localXYSeries2.getItemCount() != 0) {
        break label1927;
      }
    }
    label1927:
    int i13 = 1;
    XYSeriesRenderer localXYSeriesRenderer = (XYSeriesRenderer)this.mRenderer.getSeriesRendererAt(i14);
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    float f2 = Math.min(i2, (float)(i2 + arrayOfDouble6[i15] * arrayOfDouble3[i15]));
    LinkedList localLinkedList = new LinkedList();
    this.clickableAreas.put(Integer.valueOf(i14), localLinkedList);
    for (;;)
    {
      int i16;
      int i17;
      Iterator localIterator;
      int i18;
      Rect localRect;
      int i19;
      try
      {
        SortedMap localSortedMap = localXYSeries2.getRange(arrayOfDouble1[i15], arrayOfDouble2[i15], localXYSeriesRenderer.isDisplayBoundingPoints());
        i16 = -1;
        i17 = 0;
        localIterator = localSortedMap.entrySet().iterator();
        if (!localIterator.hasNext())
        {
          i18 = localXYSeries2.getAnnotationCount();
          if (i18 > 0)
          {
            paramPaint.setColor(localXYSeriesRenderer.getAnnotationsColor());
            paramPaint.setTextSize(localXYSeriesRenderer.getAnnotationsTextSize());
            paramPaint.setTextAlign(localXYSeriesRenderer.getAnnotationsTextAlign());
            localRect = new Rect();
            i19 = 0;
            break label3369;
          }
          if (localArrayList1.size() > 0)
          {
            drawSeries(localXYSeries2, paramCanvas, paramPaint, localArrayList1, localXYSeriesRenderer, f2, i14, localOrientation, i16);
            localLinkedList.addAll(Arrays.asList(clickableAreasForPoints(localArrayList1, localArrayList2, f2, i14, i16)));
          }
          break label1921;
        }
      }
      finally {}
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      i17++;
      double d5 = ((Double)localEntry.getKey()).doubleValue();
      double d6 = ((Double)localEntry.getValue()).doubleValue();
      if ((i16 < 0) && ((!isNullValue(d6)) || (isRenderNullValues()))) {
        i16 = localXYSeries2.getIndexForKey(d5);
      }
      localArrayList2.add((Double)localEntry.getKey());
      localArrayList2.add((Double)localEntry.getValue());
      if (!isNullValue(d6))
      {
        localArrayList1.add(Float.valueOf((float)(j + arrayOfDouble5[i15] * (d5 - arrayOfDouble1[i15]))));
        localArrayList1.add(Float.valueOf((float)(i2 - arrayOfDouble6[i15] * (d6 - arrayOfDouble3[i15]))));
      }
      else if (isRenderNullValues())
      {
        localArrayList1.add(Float.valueOf((float)(j + arrayOfDouble5[i15] * (d5 - arrayOfDouble1[i15]))));
        localArrayList1.add(Float.valueOf((float)(i2 - arrayOfDouble6[i15] * -arrayOfDouble3[i15])));
      }
      else
      {
        if (localArrayList1.size() > 0)
        {
          drawSeries(localXYSeries2, paramCanvas, paramPaint, localArrayList1, localXYSeriesRenderer, f2, i14, localOrientation, i16);
          localLinkedList.addAll(Arrays.asList(clickableAreasForPoints(localArrayList1, localArrayList2, f2, i14, i16)));
          localArrayList1.clear();
          localArrayList2.clear();
          i16 = -1;
        }
        localLinkedList.add(null);
        continue;
        label2651:
        label2738:
        label2744:
        label2955:
        label2996:
        label3051:
        label3144:
        label3191:
        label3310:
        label3340:
        label3367:
        label3369:
        do
        {
          float f3 = (float)(j + arrayOfDouble5[i15] * (localXYSeries2.getAnnotationX(i19) - arrayOfDouble1[i15]));
          float f4 = (float)(i2 - arrayOfDouble6[i15] * (localXYSeries2.getAnnotationY(i19) - arrayOfDouble3[i15]));
          paramPaint.getTextBounds(localXYSeries2.getAnnotationAt(i19), 0, localXYSeries2.getAnnotationAt(i19).length(), localRect);
          if ((f3 < f3 + localRect.width()) && (f4 < paramCanvas.getHeight())) {
            drawString(paramCanvas, localXYSeries2.getAnnotationAt(i19), f3, f4, paramPaint);
          }
          i19++;
          continue;
          if (localOrientation != XYMultipleSeriesRenderer.Orientation.VERTICAL) {
            break label897;
          }
          XYMultipleSeriesRenderer localXYMultipleSeriesRenderer1 = this.mRenderer;
          int i20 = paramInt3 - m;
          int i21 = paramInt4 - paramInt2;
          int i22 = this.mRenderer.getMarginsColor();
          drawBackground(localXYMultipleSeriesRenderer1, paramCanvas, m, paramInt2, i20, i21, paramPaint, true, i22);
          drawBackground(this.mRenderer, paramCanvas, paramInt1, paramInt2, j - paramInt1, paramInt4 - paramInt2, paramPaint, true, this.mRenderer.getMarginsColor());
          break label897;
          i23 = 0;
          break label915;
          Paint.Align localAlign = this.mRenderer.getYAxisAlign(i27);
          Double[] arrayOfDouble8 = this.mRenderer.getYTextLabelLocations(i27);
          int i28 = arrayOfDouble8.length;
          int i29 = 0;
          if (i29 >= i28)
          {
            i27++;
            break label1112;
          }
          Double localDouble = arrayOfDouble8[i29];
          float f5;
          String str;
          if ((arrayOfDouble3[i27] <= localDouble.doubleValue()) && (localDouble.doubleValue() <= arrayOfDouble4[i27]))
          {
            f5 = (float)(i2 - arrayOfDouble6[i27] * (localDouble.doubleValue() - arrayOfDouble3[i27]));
            str = this.mRenderer.getYTextLabel(localDouble, i27);
            paramPaint.setColor(this.mRenderer.getYLabelsColor(i27));
            paramPaint.setTextAlign(this.mRenderer.getYLabelsAlign(i27));
            if (localOrientation != XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
              break label3051;
            }
            if (localAlign != Paint.Align.LEFT) {
              break label2996;
            }
            paramCanvas.drawLine(j + getLabelLinePos(localAlign), f5, j, f5, paramPaint);
            drawText(paramCanvas, str, j, f5 - this.mRenderer.getYLabelsVerticalPadding(), paramPaint, this.mRenderer.getYLabelsAngle());
            if (bool2)
            {
              paramPaint.setColor(this.mRenderer.getGridColor(i27));
              paramCanvas.drawLine(j, f5, m, f5, paramPaint);
            }
          }
          for (;;)
          {
            i29++;
            break;
            paramCanvas.drawLine(m, f5, m + getLabelLinePos(localAlign), f5, paramPaint);
            drawText(paramCanvas, str, m, f5 - this.mRenderer.getYLabelsVerticalPadding(), paramPaint, this.mRenderer.getYLabelsAngle());
            break label2955;
            paramCanvas.drawLine(m - getLabelLinePos(localAlign), f5, m, f5, paramPaint);
            drawText(paramCanvas, str, m + 10, f5 - this.mRenderer.getYLabelsVerticalPadding(), paramPaint, this.mRenderer.getYLabelsAngle());
            if (bool2)
            {
              paramPaint.setColor(this.mRenderer.getGridColor(i27));
              paramCanvas.drawLine(m, f5, j, f5, paramPaint);
            }
          }
          this.mRenderer.getYAxisAlign(i26);
          drawText(paramCanvas, this.mRenderer.getYTitle(i26), paramInt1 + 80, paramInt2 + 50, paramPaint, 0.0F);
          i26++;
          break label1162;
          if (localOrientation != XYMultipleSeriesRenderer.Orientation.VERTICAL) {
            break label1213;
          }
          drawText(paramCanvas, this.mRenderer.getXTitle(), paramInt1 + paramInt3 / 2, paramInt2 + paramInt4 - f1 + this.mRenderer.getXLabelsPadding(), paramPaint, -90.0F);
          drawText(paramCanvas, this.mRenderer.getYTitle(), m + 20, paramInt2 + paramInt4 / 2, paramPaint, 0.0F);
          paramPaint.setTextSize(this.mRenderer.getChartTitleTextSize());
          drawText(paramCanvas, this.mRenderer.getChartTitle(), f1 + paramInt1, k + paramInt4 / 2, paramPaint, 0.0F);
          break label1213;
          if (this.mRenderer.getYAxisAlign(i25) == Paint.Align.RIGHT) {}
          for (i24 = 1;; i24 = 0)
          {
            i25++;
            break;
          }
          if (localOrientation != XYMultipleSeriesRenderer.Orientation.VERTICAL) {
            break label1354;
          }
          paramCanvas.drawLine(m, k, m, i2, paramPaint);
          break label1354;
          break;
        } while (i19 < i18);
      }
    }
  }
  
  protected void drawChartValuesText(Canvas paramCanvas, XYSeries paramXYSeries, XYSeriesRenderer paramXYSeriesRenderer, Paint paramPaint, List<Float> paramList, int paramInt1, int paramInt2)
  {
    this.showCount = 0;
    float f1 = 500000.0F;
    float f2 = 0.0F;
    float f5;
    int k;
    int j;
    float f4;
    if (paramList.size() / 2 == paramXYSeries.getItemCount())
    {
      f5 = ((Float)paramList.get(-2 + paramList.size())).floatValue();
      k = paramCanvas.getWidth() / 2;
      if (paramList.size() / 2 == 1) {
        if ((f5 < k - 30) || (f5 > k + 30))
        {
          isMoveOut = false;
          if (f5 < k - 30) {
            sIndex = 2;
          }
        }
        else
        {
          if (paramList.size() <= 1) {
            break label763;
          }
          ((Float)paramList.get(0)).floatValue();
          ((Float)paramList.get(1)).floatValue();
          j = 0;
          if (j < paramList.size()) {
            break label366;
          }
          if (f1 - 20.0F < 0.0F) {
            break label757;
          }
          f4 = f1 - 20.0F;
          label194:
          this.mRenderer.setYAxisMin(f4);
          this.mRenderer.setYAxisMax(20.0F + f2);
        }
      }
    }
    for (;;)
    {
      return;
      sIndex = 1;
      break;
      if (f5 >= k - 10) {
        break;
      }
      sIndex = 2;
      isMoveOut = false;
      break;
      double d = paramXYSeries.getY(-1 + paramXYSeries.getItemCount());
      Log.v("tag", "lastYValue:" + d);
      if (d != paramXYSeries.getY(paramInt2 + (-2 + paramList.size()) / 2)) {
        break;
      }
      sIndex = 2;
      if ((((Float)paramList.get(-2 + paramList.size())).floatValue() >= -10 + paramCanvas.getWidth() / 2) || (TouchHandler.orientation == 2)) {
        break;
      }
      isMoveOut = false;
      break;
      label366:
      String str = getLabel(paramXYSeriesRenderer.getChartValuesFormat(), paramXYSeries.getY(paramInt2 + j / 2));
      float f3 = Float.parseFloat(str);
      if (f3 > f2) {
        f2 = f3;
      }
      if (f3 < f1) {
        f1 = f3;
      }
      if (j == 2) {
        if ((Math.abs(((Float)paramList.get(2)).floatValue() - ((Float)paramList.get(0)).floatValue()) > paramXYSeriesRenderer.getDisplayChartValuesDistance()) || (Math.abs(((Float)paramList.get(3)).floatValue() - ((Float)paramList.get(1)).floatValue()) > paramXYSeriesRenderer.getDisplayChartValuesDistance()))
        {
          drawTextAndBackground(paramInt2, paramCanvas, getLabel(paramXYSeriesRenderer.getChartValuesFormat(), paramXYSeries.getY(paramInt2)), ((Float)paramList.get(0)).floatValue(), ((Float)paramList.get(1)).floatValue() - paramXYSeriesRenderer.getChartValuesSpacing(), paramPaint, 0.0F);
          drawTextAndBackground(paramInt2 + 1, paramCanvas, getLabel(paramXYSeriesRenderer.getChartValuesFormat(), paramXYSeries.getY(paramInt2 + 1)), ((Float)paramList.get(2)).floatValue(), ((Float)paramList.get(3)).floatValue() - paramXYSeriesRenderer.getChartValuesSpacing(), paramPaint, 0.0F);
          ((Float)paramList.get(2)).floatValue();
          ((Float)paramList.get(3)).floatValue();
        }
      }
      for (;;)
      {
        j += 2;
        break;
        if (j != 2)
        {
          drawTextAndBackground(paramInt2 + j / 2, paramCanvas, str, ((Float)paramList.get(j)).floatValue(), ((Float)paramList.get(j + 1)).floatValue() - paramXYSeriesRenderer.getChartValuesSpacing(), paramPaint, 0.0F);
          ((Float)paramList.get(j)).floatValue();
          ((Float)paramList.get(j + 1)).floatValue();
        }
      }
      label757:
      f4 = 0.0F;
      break label194;
      label763:
      for (int i = 0; i < paramList.size(); i += 2)
      {
        paramPaint.setColor(65280);
        drawText(paramCanvas, getLabel(paramXYSeriesRenderer.getChartValuesFormat(), paramXYSeries.getY(paramInt2 + i / 2)), ((Float)paramList.get(i)).floatValue(), ((Float)paramList.get(i + 1)).floatValue() - paramXYSeriesRenderer.getChartValuesSpacing(), paramPaint, 0.0F);
      }
    }
  }
  
  public abstract void drawSeries(Canvas paramCanvas, Paint paramPaint, List<Float> paramList, XYSeriesRenderer paramXYSeriesRenderer, float paramFloat, int paramInt1, int paramInt2);
  
  protected void drawSeries(XYSeries paramXYSeries, Canvas paramCanvas, Paint paramPaint, List<Float> paramList, XYSeriesRenderer paramXYSeriesRenderer, float paramFloat, int paramInt1, XYMultipleSeriesRenderer.Orientation paramOrientation, int paramInt2)
  {
    BasicStroke localBasicStroke = paramXYSeriesRenderer.getStroke();
    Paint.Cap localCap = paramPaint.getStrokeCap();
    Paint.Join localJoin = paramPaint.getStrokeJoin();
    float f = paramPaint.getStrokeMiter();
    PathEffect localPathEffect = paramPaint.getPathEffect();
    Paint.Style localStyle = paramPaint.getStyle();
    if (localBasicStroke != null)
    {
      float[] arrayOfFloat = localBasicStroke.getIntervals();
      DashPathEffect localDashPathEffect = null;
      if (arrayOfFloat != null) {
        localDashPathEffect = new DashPathEffect(localBasicStroke.getIntervals(), localBasicStroke.getPhase());
      }
      setStroke(localBasicStroke.getCap(), localBasicStroke.getJoin(), localBasicStroke.getMiter(), Paint.Style.FILL_AND_STROKE, localDashPathEffect, paramPaint);
    }
    drawSeries(paramCanvas, paramPaint, paramList, paramXYSeriesRenderer, paramFloat, paramInt1, paramInt2);
    if (isRenderPoints(paramXYSeriesRenderer))
    {
      ScatterChart localScatterChart = getPointsChart();
      if (localScatterChart != null) {
        localScatterChart.drawSeries(paramCanvas, paramPaint, paramList, paramXYSeriesRenderer, paramFloat, paramInt1, paramInt2);
      }
    }
    paramPaint.setTextSize(paramXYSeriesRenderer.getChartValuesTextSize());
    if (paramOrientation == XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
      paramPaint.setTextAlign(Paint.Align.CENTER);
    }
    for (;;)
    {
      if (paramXYSeriesRenderer.isDisplayChartValues())
      {
        paramPaint.setTextAlign(paramXYSeriesRenderer.getChartValuesTextAlign());
        drawChartValuesText(paramCanvas, paramXYSeries, paramXYSeriesRenderer, paramPaint, paramList, paramInt1, paramInt2);
      }
      if (localBasicStroke != null) {
        setStroke(localCap, localJoin, f, localStyle, localPathEffect, paramPaint);
      }
      return;
      paramPaint.setTextAlign(Paint.Align.LEFT);
    }
  }
  
  protected void drawText(Canvas paramCanvas, String paramString, float paramFloat1, float paramFloat2, Paint paramPaint, float paramFloat3)
  {
    float f = paramFloat3 + -this.mRenderer.getOrientation().getAngle();
    if (f != 0.0F) {
      paramCanvas.rotate(f, paramFloat1, paramFloat2);
    }
    drawString(paramCanvas, paramString, paramFloat1, paramFloat2, paramPaint);
    if (f != 0.0F) {
      paramCanvas.rotate(-f, paramFloat1, paramFloat2);
    }
  }
  
  protected void drawTextAndBackground(int paramInt, Canvas paramCanvas, String paramString, float paramFloat1, float paramFloat2, Paint paramPaint, float paramFloat3)
  {
    bitmap1 = bArray[0];
    bitmap2 = bArray[1];
    this.offsetX = this.offtemp;
    for (;;)
    {
      try
      {
        if (this.isWeight)
        {
          int i = weightType;
          switch (i)
          {
          }
        }
      }
      catch (Exception localException)
      {
        float f1;
        float f2;
        float f4;
        Log.e("test", "!! ERROR :" + paramString);
        continue;
        paramPaint.setTextSize(20.0F);
        continue;
        paramPaint.setTextSize(17.0F);
        int i3 = paramString.indexOf(".");
        int i4 = paramString.indexOf("474");
        int i5 = paramString.indexOf("878");
        StringBuffer localStringBuffer3 = new StringBuffer();
        localStringBuffer3.append(paramString.substring(0, i3));
        localStringBuffer3.append(":");
        int i6 = i3 + 1;
        localStringBuffer3.append(Integer.parseInt(paramString.substring(i6, i4)));
        if (i5 <= 0) {
          continue;
        }
        int i7 = i4 + 3;
        String str3 = paramString.substring(i7, i5);
        if ((str3 == null) || (str3.trim().length() <= 0)) {
          continue;
        }
        Log.e("test", paramString + "=========" + str3);
        if (!str3.equals("14")) {
          continue;
        }
        bitmap1 = bArray[4];
        bitmap2 = bArray[7];
        this.offsetX = this.offsetX2;
        paramString = localStringBuffer3.toString();
        continue;
        if (!str3.equals("12")) {
          continue;
        }
        bitmap1 = bArray[2];
        bitmap2 = bArray[5];
        this.offsetX = this.offsetX2;
        continue;
        if (!str3.equals("34")) {
          continue;
        }
        bitmap1 = bArray[3];
        bitmap2 = bArray[6];
        this.offsetX = this.offsetX2;
        continue;
        paramPaint.setTextSize(17.0F);
        int n = paramString.indexOf(".");
        int i1 = paramString.indexOf("474");
        StringBuffer localStringBuffer2 = new StringBuffer();
        localStringBuffer2.append(paramString.substring(0, n));
        localStringBuffer2.append(":");
        int i2 = n + 1;
        localStringBuffer2.append(Integer.parseInt(paramString.substring(i2, i1)));
        paramString = localStringBuffer2.toString();
        continue;
        paramPaint.setTextSize(17.0F);
        int j = paramString.indexOf(".");
        paramString.indexOf("47");
        int k = paramString.indexOf("055");
        StringBuffer localStringBuffer1 = new StringBuffer();
        localStringBuffer1.append(paramString.substring(0, j));
        localStringBuffer1.append(":");
        int m = j + 1;
        localStringBuffer1.append(paramString.substring(m, k).replace("47", "."));
        String str1 = localStringBuffer1.toString();
        if ((str1 == null) || (str1.indexOf(":") <= 0)) {
          continue;
        }
        paramString = str1.substring(0, str1.indexOf(":")) + "磅:" + str1.substring(1 + str1.indexOf(":"), str1.length()) + "盎司";
        continue;
        String str2 = localStringBuffer1.toString();
        paramString = str2;
        continue;
        this.showCount = (1 + this.showCount);
        if (this.showCount > 4) {
          continue;
        }
        paramCanvas.drawBitmap(bitmap1, paramFloat1 - this.offsetX, f1 - this.offsetY2, null);
        paramPaint.setColor(-1);
        float f3 = paramFloat1 + 3.0F;
        drawString(paramCanvas, paramString, f3, f1, paramPaint);
        if (f2 == 0.0F) {
          continue;
        }
        paramCanvas.rotate(-f2, paramFloat1, f1);
      }
      f1 = paramFloat2 - this.offsetY;
      f2 = paramFloat3 + -this.mRenderer.getOrientation().getAngle();
      if (f2 != 0.0F) {
        paramCanvas.rotate(f2, paramFloat1, f1);
      }
      if (LoadingActivity.isPad) {
        this.offsetX = 40.0F;
      }
      if ((this.seriesSelection == null) || (this.seriesSelection.getPointIndex() != paramInt)) {
        continue;
      }
      paramCanvas.drawBitmap(bitmap2, paramFloat1 - this.offsetX, f1 - this.offsetY2, null);
      paramPaint.setColor(-1);
      f4 = paramFloat1 + 3.0F;
      drawString(paramCanvas, paramString, f4, f1, paramPaint);
      if (f2 != 0.0F) {
        paramCanvas.rotate(-f2, paramFloat1, f1);
      }
      return;
      paramPaint.setTextSize(20.0F);
    }
  }
  
  protected void drawXLabels(List<Double> paramList, Double[] paramArrayOfDouble, Canvas paramCanvas, Paint paramPaint, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    int i = paramList.size();
    boolean bool1 = this.mRenderer.isShowLabels();
    boolean bool2 = this.mRenderer.isShowGridY();
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        drawXTextLabels(paramArrayOfDouble, paramCanvas, paramPaint, bool1, paramInt1, paramInt2, paramInt3, paramDouble1, paramDouble2, paramDouble3);
        return;
      }
      double d = ((Double)paramList.get(j)).doubleValue();
      float f = (float)(paramInt1 + paramDouble1 * (d - paramDouble2));
      if (bool1)
      {
        paramPaint.setColor(this.mRenderer.getXLabelsColor());
        paramCanvas.drawLine(f, paramInt3, f, paramInt3 + this.mRenderer.getLabelsTextSize() / 3.0F, paramPaint);
        drawText(paramCanvas, getLabel(this.mRenderer.getLabelFormat(), d), f, paramInt3 + 4.0F * this.mRenderer.getLabelsTextSize() / 3.0F + this.mRenderer.getXLabelsPadding(), paramPaint, this.mRenderer.getXLabelsAngle());
      }
      if (bool2)
      {
        paramPaint.setColor(this.mRenderer.getGridColor(0));
        paramCanvas.drawLine(f, paramInt3, f, paramInt2, paramPaint);
      }
    }
  }
  
  protected void drawXTextLabels(Double[] paramArrayOfDouble, Canvas paramCanvas, Paint paramPaint, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    new SimpleDateFormat("yyyy-MM-dd");
    boolean bool = this.mRenderer.isShowCustomTextGridX();
    int i;
    if (paramBoolean)
    {
      paramPaint.setColor(this.mRenderer.getXLabelsColor());
      i = paramArrayOfDouble.length;
    }
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return;
      }
      Double localDouble = paramArrayOfDouble[j];
      if ((paramDouble2 <= localDouble.doubleValue()) && (localDouble.doubleValue() <= paramDouble3))
      {
        float f = (float)(paramInt1 + paramDouble1 * (localDouble.doubleValue() - paramDouble2));
        paramPaint.setColor(this.mRenderer.getXLabelsColor());
        paramCanvas.drawLine(f, paramInt3, f, paramInt3 + this.mRenderer.getLabelsTextSize() / 3.0F, paramPaint);
        drawText(paramCanvas, this.mRenderer.getXTextLabel(localDouble), f, paramInt3 + 4.0F * this.mRenderer.getLabelsTextSize() / 3.0F, paramPaint, this.mRenderer.getXLabelsAngle());
        if (bool)
        {
          paramPaint.setColor(this.mRenderer.getGridColor(0));
          paramCanvas.drawLine(f, paramInt3, f, paramInt2, paramPaint);
        }
      }
    }
  }
  
  protected void drawYLabels(Map<Integer, List<Double>> paramMap, Canvas paramCanvas, Paint paramPaint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
  {
    XYMultipleSeriesRenderer.Orientation localOrientation = this.mRenderer.getOrientation();
    boolean bool1 = this.mRenderer.isShowGridX();
    boolean bool2 = this.mRenderer.isShowLabels();
    List localList;
    int k;
    for (int i = 0;; i++)
    {
      if (i >= paramInt1) {
        return;
      }
      paramPaint.setTextAlign(this.mRenderer.getYLabelsAlign(i));
      localList = (List)paramMap.get(Integer.valueOf(i));
      int j = localList.size();
      k = 0;
      if (k < j) {
        break;
      }
    }
    double d = ((Double)localList.get(k)).doubleValue();
    Paint.Align localAlign = this.mRenderer.getYAxisAlign(i);
    int m;
    label140:
    float f;
    if (this.mRenderer.getYTextLabel(Double.valueOf(d), i) != null)
    {
      m = 1;
      f = (float)(paramInt4 - paramArrayOfDouble1[i] * (d - paramArrayOfDouble2[i]));
      if (localOrientation != XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
        break label386;
      }
      if ((bool2) && (m == 0))
      {
        paramPaint.setColor(this.mRenderer.getYLabelsColor(i));
        if (localAlign != Paint.Align.LEFT) {
          break label314;
        }
        paramCanvas.drawLine(paramInt2 + getLabelLinePos(localAlign), f, paramInt2, f, paramPaint);
        drawText(paramCanvas, getLabel(this.mRenderer.getLabelFormat(), d), paramInt2 - this.mRenderer.getYLabelsPadding(), f - this.mRenderer.getYLabelsVerticalPadding(), paramPaint, this.mRenderer.getYLabelsAngle());
      }
      label269:
      if (bool1)
      {
        paramPaint.setColor(this.mRenderer.getGridColor(i));
        paramCanvas.drawLine(paramInt2, f, paramInt3, f, paramPaint);
      }
    }
    for (;;)
    {
      k++;
      break;
      m = 0;
      break label140;
      label314:
      paramCanvas.drawLine(paramInt3, f, paramInt3 + getLabelLinePos(localAlign), f, paramPaint);
      drawText(paramCanvas, getLabel(this.mRenderer.getLabelFormat(), d), paramInt3 + this.mRenderer.getYLabelsPadding(), f - this.mRenderer.getYLabelsVerticalPadding(), paramPaint, this.mRenderer.getYLabelsAngle());
      break label269;
      label386:
      if (localOrientation == XYMultipleSeriesRenderer.Orientation.VERTICAL)
      {
        if ((bool2) && (m == 0))
        {
          paramPaint.setColor(this.mRenderer.getYLabelsColor(i));
          paramCanvas.drawLine(paramInt3 - getLabelLinePos(localAlign), f, paramInt3, f, paramPaint);
          drawText(paramCanvas, getLabel(this.mRenderer.getLabelFormat(), d), paramInt3 + 10 + this.mRenderer.getYLabelsPadding(), f - this.mRenderer.getYLabelsVerticalPadding(), paramPaint, this.mRenderer.getYLabelsAngle());
        }
        if (bool1)
        {
          paramPaint.setColor(this.mRenderer.getGridColor(i));
          paramCanvas.drawLine(paramInt3, f, paramInt2, f, paramPaint);
        }
      }
    }
  }
  
  public double[] getCalcRange(int paramInt)
  {
    return (double[])this.mCalcRange.get(Integer.valueOf(paramInt));
  }
  
  public abstract String getChartType();
  
  public XYMultipleSeriesDataset getDataset()
  {
    return this.mDataset;
  }
  
  public double getDefaultMinimum()
  {
    return Double.MAX_VALUE;
  }
  
  public ScatterChart getPointsChart()
  {
    return null;
  }
  
  public XYMultipleSeriesRenderer getRenderer()
  {
    return this.mRenderer;
  }
  
  protected Rect getScreenR()
  {
    return this.mScreenR;
  }
  
  public SeriesSelection getSeriesAndPointForScreenCoordinate(Point paramPoint)
  {
    int i;
    if (this.clickableAreas != null)
    {
      i = -1 + this.clickableAreas.size();
      if (i >= 0) {}
    }
    else
    {
      return super.getSeriesAndPointForScreenCoordinate(paramPoint);
    }
    int j = 0;
    Iterator localIterator;
    if (this.clickableAreas.get(Integer.valueOf(i)) != null) {
      localIterator = ((List)this.clickableAreas.get(Integer.valueOf(i))).iterator();
    }
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        i--;
        break;
      }
      ClickableArea localClickableArea = (ClickableArea)localIterator.next();
      if (localClickableArea != null)
      {
        RectF localRectF = localClickableArea.getRect();
        if ((localRectF != null) && (localRectF.contains(paramPoint.getX(), paramPoint.getY())))
        {
          this.seriesSelection = new SeriesSelection(i, j + startIndex, localClickableArea.getX(), localClickableArea.getY());
          return this.seriesSelection;
        }
      }
      j++;
    }
  }
  
  protected List<Double> getXLabels(double paramDouble1, double paramDouble2, int paramInt)
  {
    return MathHelper.getLabels(paramDouble1, paramDouble2, paramInt);
  }
  
  protected Map<Integer, List<Double>> getYLabels(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int paramInt)
  {
    HashMap localHashMap = new HashMap();
    for (int i = 0;; i++)
    {
      if (i >= paramInt) {
        return localHashMap;
      }
      localHashMap.put(Integer.valueOf(i), getValidLabels(MathHelper.getLabels(paramArrayOfDouble1[i], paramArrayOfDouble2[i], this.mRenderer.getYLabels())));
    }
  }
  
  protected boolean isRenderNullValues()
  {
    return false;
  }
  
  public boolean isRenderPoints(SimpleSeriesRenderer paramSimpleSeriesRenderer)
  {
    return false;
  }
  
  public void setCalcRange(double[] paramArrayOfDouble, int paramInt)
  {
    this.mCalcRange.put(Integer.valueOf(paramInt), paramArrayOfDouble);
  }
  
  protected void setDatasetRenderer(XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer)
  {
    this.mDataset = paramXYMultipleSeriesDataset;
    this.mRenderer = paramXYMultipleSeriesRenderer;
  }
  
  protected void setScreenR(Rect paramRect)
  {
    this.mScreenR = paramRect;
  }
  
  public void setSeriesSelection(int paramInt)
  {
    this.seriesSelection = new SeriesSelection(0, paramInt, 0.0D, 0.0D);
  }
  
  public double[] toRealPoint(float paramFloat1, float paramFloat2)
  {
    return toRealPoint(paramFloat1, paramFloat2, 0);
  }
  
  public double[] toRealPoint(float paramFloat1, float paramFloat2, int paramInt)
  {
    double d1 = this.mRenderer.getXAxisMin(paramInt);
    double d2 = this.mRenderer.getXAxisMax(paramInt);
    double d3 = this.mRenderer.getYAxisMin(paramInt);
    double d4 = this.mRenderer.getYAxisMax(paramInt);
    if (this.mScreenR != null)
    {
      double[] arrayOfDouble2 = new double[2];
      arrayOfDouble2[0] = (d1 + (paramFloat1 - this.mScreenR.left) * (d2 - d1) / this.mScreenR.width());
      arrayOfDouble2[1] = (d3 + (this.mScreenR.top + this.mScreenR.height() - paramFloat2) * (d4 - d3) / this.mScreenR.height());
      return arrayOfDouble2;
    }
    double[] arrayOfDouble1 = new double[2];
    arrayOfDouble1[0] = paramFloat1;
    arrayOfDouble1[1] = paramFloat2;
    return arrayOfDouble1;
  }
  
  public double[] toScreenPoint(double[] paramArrayOfDouble)
  {
    return toScreenPoint(paramArrayOfDouble, 0);
  }
  
  public double[] toScreenPoint(double[] paramArrayOfDouble, int paramInt)
  {
    double d1 = this.mRenderer.getXAxisMin(paramInt);
    double d2 = this.mRenderer.getXAxisMax(paramInt);
    double d3 = this.mRenderer.getYAxisMin(paramInt);
    double d4 = this.mRenderer.getYAxisMax(paramInt);
    if ((!this.mRenderer.isMinXSet(paramInt)) || (!this.mRenderer.isMaxXSet(paramInt)) || (!this.mRenderer.isMinXSet(paramInt)) || (!this.mRenderer.isMaxYSet(paramInt)))
    {
      double[] arrayOfDouble1 = getCalcRange(paramInt);
      d1 = arrayOfDouble1[0];
      d2 = arrayOfDouble1[1];
      d3 = arrayOfDouble1[2];
      d4 = arrayOfDouble1[3];
    }
    if (this.mScreenR != null)
    {
      double[] arrayOfDouble2 = new double[2];
      arrayOfDouble2[0] = ((paramArrayOfDouble[0] - d1) * this.mScreenR.width() / (d2 - d1) + this.mScreenR.left);
      arrayOfDouble2[1] = ((d4 - paramArrayOfDouble[1]) * this.mScreenR.height() / (d4 - d3) + this.mScreenR.top);
      paramArrayOfDouble = arrayOfDouble2;
    }
    return paramArrayOfDouble;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\chart\XYChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */