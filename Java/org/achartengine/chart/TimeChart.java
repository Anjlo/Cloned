package org.achartengine.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import com.lefu.es.constant.UtilConstants;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

public class TimeChart
  extends LineChart
{
  public static final long DAY = 86400000L;
  public static final String TYPE = "Time";
  private Handler handler;
  private DateChangeCallback mDateChangeCallback;
  private String mDateFormat;
  private Double mStartPoint;
  private double[] panLs;
  
  TimeChart() {}
  
  public TimeChart(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, DateChangeCallback paramDateChangeCallback)
  {
    super(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    this.mDateChangeCallback = paramDateChangeCallback;
    this.handler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        super.handleMessage(paramAnonymousMessage);
        TimeChart.this.mDateChangeCallback.dateChangeCallback(paramAnonymousMessage.what);
      }
    };
    this.mDateChangeCallback = paramDateChangeCallback;
    this.panLs = paramXYMultipleSeriesRenderer.getPanLimits();
  }
  
  private DateFormat getDateFormat(double paramDouble1, double paramDouble2)
  {
    if (this.mDateFormat != null) {
      try
      {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(this.mDateFormat);
        return localSimpleDateFormat;
      }
      catch (Exception localException) {}
    }
    new SimpleDateFormat("yyyy-MM-dd");
    double d = paramDouble2 - paramDouble1;
    if (d <= 900000.0D)
    {
      this.handler.sendEmptyMessage(0);
      Locale localLocale7 = Locale.getDefault();
      if ((localLocale7.getCountry().equals(Locale.TAIWAN.getCountry())) || (localLocale7.getCountry().equals(Locale.CHINA.getCountry())) || (UtilConstants.SELECT_LANGUAGE == 2)) {
        this.mRenderer.setXTitle(" 時 ");
      }
      for (;;)
      {
        return new SimpleDateFormat("HH");
        this.mRenderer.setXTitle("Hour");
      }
    }
    if (d < 4.32E7D)
    {
      this.handler.sendEmptyMessage(1);
      Locale localLocale6 = Locale.getDefault();
      if ((localLocale6.getCountry().equals(Locale.TAIWAN.getCountry())) || (localLocale6.getCountry().equals(Locale.CHINA.getCountry())) || (UtilConstants.SELECT_LANGUAGE == 2)) {
        this.mRenderer.setXTitle(" 時 ");
      }
      for (;;)
      {
        return new SimpleDateFormat("HH");
        this.mRenderer.setXTitle("Hour");
      }
    }
    if (d < 8.64E7D)
    {
      this.handler.sendEmptyMessage(2);
      Locale localLocale5 = Locale.getDefault();
      if ((localLocale5.getCountry().equals(Locale.TAIWAN.getCountry())) || (localLocale5.getCountry().equals(Locale.CHINA.getCountry())) || (UtilConstants.SELECT_LANGUAGE == 2)) {
        this.mRenderer.setXTitle(" 時 ");
      }
      for (;;)
      {
        return new SimpleDateFormat("HH");
        this.mRenderer.setXTitle("Hour");
      }
    }
    if (d < 2.592E9D)
    {
      this.handler.sendEmptyMessage(3);
      Locale localLocale4 = Locale.getDefault();
      if ((localLocale4.getCountry().equals(Locale.TAIWAN.getCountry())) || (localLocale4.getCountry().equals(Locale.CHINA.getCountry())) || (UtilConstants.SELECT_LANGUAGE == 2)) {
        this.mRenderer.setXTitle(" 日 ");
      }
      for (;;)
      {
        return new SimpleDateFormat("dd");
        this.mRenderer.setXTitle("DAY");
      }
    }
    if (d < 3.1536E10D)
    {
      this.handler.sendEmptyMessage(4);
      Locale localLocale3 = Locale.getDefault();
      if ((localLocale3.getCountry().equals(Locale.TAIWAN.getCountry())) || (localLocale3.getCountry().equals(Locale.CHINA.getCountry())) || (UtilConstants.SELECT_LANGUAGE == 2)) {
        this.mRenderer.setXTitle(" 月 ");
      }
      for (;;)
      {
        return new SimpleDateFormat("MM");
        this.mRenderer.setXTitle("Month");
      }
    }
    if (d < 3.456E10D)
    {
      this.handler.sendEmptyMessage(4);
      Locale localLocale2 = Locale.getDefault();
      if ((localLocale2.getCountry().equals(Locale.TAIWAN.getCountry())) || (localLocale2.getCountry().equals(Locale.CHINA.getCountry())) || (UtilConstants.SELECT_LANGUAGE == 2)) {
        this.mRenderer.setXTitle(" 月 ");
      }
      for (;;)
      {
        return new SimpleDateFormat("MM");
        this.mRenderer.setXTitle("Month");
      }
    }
    this.handler.sendEmptyMessage(5);
    Locale localLocale1 = Locale.getDefault();
    if ((localLocale1.getCountry().equals(Locale.TAIWAN.getCountry())) || (localLocale1.getCountry().equals(Locale.CHINA.getCountry())) || (UtilConstants.SELECT_LANGUAGE == 2)) {
      this.mRenderer.setXTitle(" 年 ");
    }
    for (;;)
    {
      return new SimpleDateFormat("yyyy");
      this.mRenderer.setXTitle("Year");
    }
  }
  
  protected void drawXLabels(List<Double> paramList, Double[] paramArrayOfDouble, Canvas paramCanvas, Paint paramPaint, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    int i = paramList.size();
    boolean bool1;
    boolean bool2;
    DateFormat localDateFormat;
    if (i > 0)
    {
      bool1 = this.mRenderer.isShowLabels();
      bool2 = this.mRenderer.isShowGridY();
      double d1 = ((Double)paramList.get(0)).doubleValue();
      double d2 = ((Double)paramList.get(0)).doubleValue();
      if (paramList.size() > 1) {
        d2 = ((Double)paramList.get(1)).doubleValue();
      }
      localDateFormat = getDateFormat(d1, d2);
    }
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        drawXTextLabels(paramArrayOfDouble, paramCanvas, paramPaint, true, paramInt1, paramInt2, paramInt3, paramDouble1, paramDouble2, paramDouble3);
        return;
      }
      long l = Math.round(((Double)paramList.get(j)).doubleValue());
      float f = (float)(paramInt1 + paramDouble1 * (l - paramDouble2));
      if (bool1)
      {
        paramPaint.setColor(this.mRenderer.getXLabelsColor());
        paramCanvas.drawLine(f, paramInt3, f, paramInt3 + this.mRenderer.getLabelsTextSize() / 3.0F, paramPaint);
        if ((j == 2) && (this.mRenderer.getXTitle().equals("Year"))) {
          l = 31536000000L + Math.round(((Double)paramList.get(1)).doubleValue());
        }
        if ((i == 2) && (j == 1) && (this.mRenderer.getXTitle().equals("Year"))) {
          l = 31536000000L + Math.round(((Double)paramList.get(0)).doubleValue());
        }
        drawText(paramCanvas, localDateFormat.format(new Date(l)), f, paramInt3 + 4.0F * this.mRenderer.getLabelsTextSize() / 3.0F + this.mRenderer.getXLabelsPadding(), paramPaint, this.mRenderer.getXLabelsAngle());
      }
      if (bool2)
      {
        paramPaint.setColor(this.mRenderer.getGridColor(0));
        paramCanvas.drawLine(f, paramInt3, f, paramInt2, paramPaint);
      }
    }
  }
  
  public String getChartType()
  {
    return "Time";
  }
  
  public String getDateFormat()
  {
    return this.mDateFormat;
  }
  
  protected List<Double> getXLabels(double paramDouble1, double paramDouble2, int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    XYSeries localXYSeries;
    int k;
    int m;
    int n;
    int i1;
    int i4;
    if (!this.mRenderer.isXRoundedLabels()) {
      if (this.mDataset.getSeriesCount() > 0)
      {
        localXYSeries = this.mDataset.getSeriesAt(0);
        k = localXYSeries.getItemCount();
        m = 0;
        n = -1;
        i1 = 0;
        if (i1 >= k)
        {
          if (m >= paramInt) {
            break label151;
          }
          i4 = n;
          label73:
          if (i4 < n + m) {
            break label127;
          }
        }
      }
    }
    label127:
    label151:
    double d1;
    do
    {
      for (;;)
      {
        return localArrayList;
        double d4 = localXYSeries.getX(i1);
        if ((paramDouble1 <= d4) && (d4 <= paramDouble2))
        {
          m++;
          if (n < 0) {
            n = i1;
          }
        }
        i1++;
        break;
        localArrayList.add(Double.valueOf(localXYSeries.getX(i4)));
        i4++;
        break label73;
        float f = m / paramInt;
        int i2 = 0;
        for (int i3 = 0; (i3 < k) && (i2 < paramInt); i3++)
        {
          double d5 = localXYSeries.getX(Math.round(f * i3));
          if ((paramDouble1 <= d5) && (d5 <= paramDouble2))
          {
            localArrayList.add(Double.valueOf(d5));
            i2++;
          }
        }
      }
      return super.getXLabels(paramDouble1, paramDouble2, paramInt);
      if (this.mStartPoint == null) {
        this.mStartPoint = Double.valueOf(8.64E7D + (paramDouble1 - paramDouble1 % 8.64E7D) + 1000 * (60 * new Date(Math.round(paramDouble1)).getTimezoneOffset()));
      }
      if (paramInt > 25) {
        paramInt = 25;
      }
      d1 = (paramDouble2 - paramDouble1) / paramInt;
    } while (d1 <= 0.0D);
    double d2 = 8.64E7D;
    int i;
    if (d1 <= 8.64E7D) {
      for (;;)
      {
        if (d1 >= d2 / 2.0D)
        {
          double d3 = this.mStartPoint.doubleValue() - d2 * Math.floor((this.mStartPoint.doubleValue() - paramDouble1) / d2);
          int j;
          for (i = 0;; i = j)
          {
            if (d3 >= paramDouble2) {
              break label490;
            }
            j = i + 1;
            if (i > paramInt) {
              break;
            }
            localArrayList.add(Double.valueOf(d3));
            d3 += d2;
          }
        }
        if (d2 <= 2700000.0D) {
          d2 /= 3.0D;
        } else {
          d2 /= 2.0D;
        }
      }
    }
    label488:
    for (;;)
    {
      if (d2 <= 2.592E9D) {}
      for (d2 *= 2.0D;; d2 *= 2.0D)
      {
        if (d1 > d2) {
          break label488;
        }
        break;
      }
    }
    label490:
    return localArrayList;
  }
  
  public void setDateFormat(String paramString)
  {
    this.mDateFormat = paramString;
  }
  
  public static abstract interface DateChangeCallback
  {
    public abstract void dateChangeCallback(int paramInt);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\chart\TimeChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */