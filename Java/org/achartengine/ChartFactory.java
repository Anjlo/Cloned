package org.achartengine;

import android.content.Context;
import android.content.Intent;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.BubbleChart;
import org.achartengine.chart.CombinedXYChart;
import org.achartengine.chart.CubicLineChart;
import org.achartengine.chart.DialChart;
import org.achartengine.chart.DoughnutChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PieChart;
import org.achartengine.chart.RangeBarChart;
import org.achartengine.chart.ScatterChart;
import org.achartengine.chart.TimeChart;
import org.achartengine.chart.TimeChart.DateChangeCallback;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

public class ChartFactory
{
  public static final String CHART = "chart";
  public static final String TITLE = "title";
  
  private static boolean checkMultipleSeriesItems(MultipleCategorySeries paramMultipleCategorySeries, int paramInt)
  {
    int i = paramMultipleCategorySeries.getCategoriesCount();
    boolean bool = true;
    int j = 0;
    if ((j >= i) || (!bool)) {
      return bool;
    }
    if (paramMultipleCategorySeries.getValues(j).length == paramMultipleCategorySeries.getTitles(j).length) {}
    for (bool = true;; bool = false)
    {
      j++;
      break;
    }
  }
  
  private static void checkParameters(CategorySeries paramCategorySeries, DefaultRenderer paramDefaultRenderer)
  {
    if ((paramCategorySeries == null) || (paramDefaultRenderer == null) || (paramCategorySeries.getItemCount() != paramDefaultRenderer.getSeriesRendererCount())) {
      throw new IllegalArgumentException("Dataset and renderer should be not null and the dataset number of items should be equal to the number of series renderers");
    }
  }
  
  private static void checkParameters(MultipleCategorySeries paramMultipleCategorySeries, DefaultRenderer paramDefaultRenderer)
  {
    if ((paramMultipleCategorySeries == null) || (paramDefaultRenderer == null) || (!checkMultipleSeriesItems(paramMultipleCategorySeries, paramDefaultRenderer.getSeriesRendererCount()))) {
      throw new IllegalArgumentException("Titles and values should be not null and the dataset number of items should be equal to the number of series renderers");
    }
  }
  
  private static void checkParameters(XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer)
  {
    if ((paramXYMultipleSeriesDataset == null) || (paramXYMultipleSeriesRenderer == null) || (paramXYMultipleSeriesDataset.getSeriesCount() != paramXYMultipleSeriesRenderer.getSeriesRendererCount())) {
      throw new IllegalArgumentException("Dataset and renderer should be not null and should have the same number of series");
    }
  }
  
  public static final Intent getBarChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, BarChart.Type paramType)
  {
    return getBarChartIntent(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, paramType, "");
  }
  
  public static final Intent getBarChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, BarChart.Type paramType, String paramString)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    Intent localIntent = new Intent(paramContext, GraphicalActivity.class);
    localIntent.putExtra("chart", new BarChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, paramType));
    localIntent.putExtra("title", paramString);
    return localIntent;
  }
  
  public static final GraphicalView getBarChartView(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, BarChart.Type paramType)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    return new GraphicalView(paramContext, new BarChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, paramType));
  }
  
  public static final Intent getBubbleChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer)
  {
    return getBubbleChartIntent(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, "");
  }
  
  public static final Intent getBubbleChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, String paramString)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    Intent localIntent = new Intent(paramContext, GraphicalActivity.class);
    localIntent.putExtra("chart", new BubbleChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer));
    localIntent.putExtra("title", paramString);
    return localIntent;
  }
  
  public static final GraphicalView getBubbleChartView(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    return new GraphicalView(paramContext, new BubbleChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer));
  }
  
  public static final Intent getCombinedXYChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, String[] paramArrayOfString, String paramString)
  {
    if ((paramXYMultipleSeriesDataset == null) || (paramXYMultipleSeriesRenderer == null) || (paramArrayOfString == null) || (paramXYMultipleSeriesDataset.getSeriesCount() != paramArrayOfString.length)) {
      throw new IllegalArgumentException("Datasets, renderers and types should be not null and the datasets series count should be equal to the types length");
    }
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    Intent localIntent = new Intent(paramContext, GraphicalActivity.class);
    localIntent.putExtra("chart", new CombinedXYChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, paramArrayOfString));
    localIntent.putExtra("title", paramString);
    return localIntent;
  }
  
  public static final GraphicalView getCombinedXYChartView(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, String[] paramArrayOfString)
  {
    if ((paramXYMultipleSeriesDataset == null) || (paramXYMultipleSeriesRenderer == null) || (paramArrayOfString == null) || (paramXYMultipleSeriesDataset.getSeriesCount() != paramArrayOfString.length)) {
      throw new IllegalArgumentException("Dataset, renderer and types should be not null and the datasets series count should be equal to the types length");
    }
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    return new GraphicalView(paramContext, new CombinedXYChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, paramArrayOfString));
  }
  
  public static final GraphicalView getCubeLineChartView(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, float paramFloat)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    return new GraphicalView(paramContext, new CubicLineChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, paramFloat));
  }
  
  public static final Intent getCubicLineChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, float paramFloat)
  {
    return getCubicLineChartIntent(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, paramFloat, "");
  }
  
  public static final Intent getCubicLineChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, float paramFloat, String paramString)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    Intent localIntent = new Intent(paramContext, GraphicalActivity.class);
    localIntent.putExtra("chart", new CubicLineChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, paramFloat));
    localIntent.putExtra("title", paramString);
    return localIntent;
  }
  
  public static final Intent getDialChartIntent(Context paramContext, CategorySeries paramCategorySeries, DialRenderer paramDialRenderer, String paramString)
  {
    checkParameters(paramCategorySeries, paramDialRenderer);
    Intent localIntent = new Intent(paramContext, GraphicalActivity.class);
    localIntent.putExtra("chart", new DialChart(paramCategorySeries, paramDialRenderer));
    localIntent.putExtra("title", paramString);
    return localIntent;
  }
  
  public static final GraphicalView getDialChartView(Context paramContext, CategorySeries paramCategorySeries, DialRenderer paramDialRenderer)
  {
    checkParameters(paramCategorySeries, paramDialRenderer);
    return new GraphicalView(paramContext, new DialChart(paramCategorySeries, paramDialRenderer));
  }
  
  public static final Intent getDoughnutChartIntent(Context paramContext, MultipleCategorySeries paramMultipleCategorySeries, DefaultRenderer paramDefaultRenderer, String paramString)
  {
    checkParameters(paramMultipleCategorySeries, paramDefaultRenderer);
    Intent localIntent = new Intent(paramContext, GraphicalActivity.class);
    localIntent.putExtra("chart", new DoughnutChart(paramMultipleCategorySeries, paramDefaultRenderer));
    localIntent.putExtra("title", paramString);
    return localIntent;
  }
  
  public static final GraphicalView getDoughnutChartView(Context paramContext, MultipleCategorySeries paramMultipleCategorySeries, DefaultRenderer paramDefaultRenderer)
  {
    checkParameters(paramMultipleCategorySeries, paramDefaultRenderer);
    return new GraphicalView(paramContext, new DoughnutChart(paramMultipleCategorySeries, paramDefaultRenderer));
  }
  
  public static final Intent getLineChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer)
  {
    return getLineChartIntent(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, "");
  }
  
  public static final Intent getLineChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, String paramString)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    Intent localIntent = new Intent(paramContext, GraphicalActivity.class);
    localIntent.putExtra("chart", new LineChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer));
    localIntent.putExtra("title", paramString);
    return localIntent;
  }
  
  public static final GraphicalView getLineChartView(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    return new GraphicalView(paramContext, new LineChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer));
  }
  
  public static final Intent getPieChartIntent(Context paramContext, CategorySeries paramCategorySeries, DefaultRenderer paramDefaultRenderer, String paramString)
  {
    checkParameters(paramCategorySeries, paramDefaultRenderer);
    Intent localIntent = new Intent(paramContext, GraphicalActivity.class);
    localIntent.putExtra("chart", new PieChart(paramCategorySeries, paramDefaultRenderer));
    localIntent.putExtra("title", paramString);
    return localIntent;
  }
  
  public static final GraphicalView getPieChartView(Context paramContext, CategorySeries paramCategorySeries, DefaultRenderer paramDefaultRenderer)
  {
    checkParameters(paramCategorySeries, paramDefaultRenderer);
    return new GraphicalView(paramContext, new PieChart(paramCategorySeries, paramDefaultRenderer));
  }
  
  public static final Intent getRangeBarChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, BarChart.Type paramType, String paramString)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    Intent localIntent = new Intent(paramContext, GraphicalActivity.class);
    localIntent.putExtra("chart", new RangeBarChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, paramType));
    localIntent.putExtra("title", paramString);
    return localIntent;
  }
  
  public static final GraphicalView getRangeBarChartView(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, BarChart.Type paramType)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    return new GraphicalView(paramContext, new RangeBarChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, paramType));
  }
  
  public static final Intent getScatterChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer)
  {
    return getScatterChartIntent(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, "");
  }
  
  public static final Intent getScatterChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, String paramString)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    Intent localIntent = new Intent(paramContext, GraphicalActivity.class);
    localIntent.putExtra("chart", new ScatterChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer));
    localIntent.putExtra("title", paramString);
    return localIntent;
  }
  
  public static final GraphicalView getScatterChartView(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    return new GraphicalView(paramContext, new ScatterChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer));
  }
  
  public static final Intent getTimeChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, String paramString1, String paramString2, TimeChart.DateChangeCallback paramDateChangeCallback)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    Intent localIntent = new Intent(paramContext, GraphicalActivity.class);
    TimeChart localTimeChart = new TimeChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, paramDateChangeCallback);
    localTimeChart.setDateFormat(paramString1);
    localIntent.putExtra("chart", localTimeChart);
    localIntent.putExtra("title", paramString2);
    return localIntent;
  }
  
  public static final Intent getTimeChartIntent(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, String paramString, TimeChart.DateChangeCallback paramDateChangeCallback)
  {
    return getTimeChartIntent(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, paramString, "", paramDateChangeCallback);
  }
  
  public static final GraphicalView getTimeChartView(Context paramContext, boolean paramBoolean, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer, String paramString, TimeChart.DateChangeCallback paramDateChangeCallback)
  {
    checkParameters(paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer);
    TimeChart localTimeChart = new TimeChart(paramContext, paramBoolean, paramXYMultipleSeriesDataset, paramXYMultipleSeriesRenderer, paramDateChangeCallback);
    localTimeChart.setDateFormat(paramString);
    return new GraphicalView(paramContext, localTimeChart);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\ChartFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */