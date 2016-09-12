package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.lefu.es.adapter.RecordDetailAdaptor;
import com.lefu.es.cache.CacheHelper;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.constant.imageUtil;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.RecordService;
import com.lefu.es.util.Image;
import com.lefu.es.util.SharedPreferencesUtil;
import com.lefu.es.util.StringUtils;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.view.GuideView;
import com.lefu.es.view.GuideView.Builder;
import com.lefu.es.view.GuideView.Direction;
import com.lefu.es.view.GuideView.MyShape;
import com.lefu.es.view.GuideView.OnClickCallback;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.TimeChart.DateChangeCallback;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

@SuppressLint({"SimpleDateFormat"})
public class RecordListActivity
  extends Activity
  implements View.OnClickListener, TimeChart.DateChangeCallback
{
  private TextView back_tv;
  private LinearLayout charcontainer;
  private LinearLayout chartContainer;
  private int dateType = 5;
  private ImageView delallImg;
  private ImageView deleteImg;
  private LinearLayout delist;
  private TextView graph_tv;
  private GuideView guideView;
  private GuideView guideView2;
  private Handler handler;
  private ImageView headImage;
  View.OnClickListener imgOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      case 2131165273: 
      case 2131165274: 
      case 2131165275: 
      case 2131165276: 
      case 2131165277: 
      case 2131165278: 
      case 2131165279: 
      default: 
      case 2131165269: 
      case 2131165271: 
      case 2131165272: 
        do
        {
          return;
          RecordListActivity.this.finish();
          return;
          RecordListActivity.this.linebg.setBackgroundDrawable(RecordListActivity.this.getResources().getDrawable(2130837603));
          RecordListActivity.this.delist.setVisibility(8);
          RecordListActivity.this.lv.setVisibility(8);
          RecordListActivity.this.charcontainer.setVisibility(0);
          return;
          RecordListActivity.this.linebg.setBackgroundDrawable(RecordListActivity.this.getResources().getDrawable(2130837604));
          RecordListActivity.this.delist.setVisibility(0);
          RecordListActivity.this.lv.setVisibility(0);
          RecordListActivity.this.charcontainer.setVisibility(8);
        } while (!TextUtils.isEmpty(UtilConstants.FIRST_INSTALL_SHARE));
        RecordListActivity.this.showTipMask2();
        return;
      case 2131165280: 
        RecordListActivity.this.deleteImg.setBackgroundDrawable(RecordListActivity.this.getResources().getDrawable(2130837556));
        RecordListActivity.this.delallImg.setBackgroundDrawable(RecordListActivity.this.getResources().getDrawable(2130837554));
        if (RecordListActivity.this.selectedPosition == -1)
        {
          Toast.makeText(RecordListActivity.this, RecordListActivity.this.getString(2131361914), 1);
          return;
        }
        RecordListActivity.this.dialog(RecordListActivity.this.getString(2131361845), paramAnonymousView.getId());
        return;
      case 2131165281: 
        RecordListActivity.this.deleteImg.setBackgroundDrawable(RecordListActivity.this.getResources().getDrawable(2130837553));
        RecordListActivity.this.delallImg.setBackgroundDrawable(RecordListActivity.this.getResources().getDrawable(2130837555));
        RecordListActivity.this.dialog(RecordListActivity.this.getString(2131361847), paramAnonymousView.getId());
        return;
      }
      if (RecordListActivity.this.lastRecod != null)
      {
        StringBuffer localStringBuffer = new StringBuffer();
        if (UtilConstants.CURRENT_USER != null)
        {
          localStringBuffer.append(UtilConstants.CURRENT_USER.getUserName());
          localStringBuffer.append("\n");
        }
        localStringBuffer.append(RecordListActivity.this.getString(2131361919) + StringUtils.getDateShareString(RecordListActivity.this.lastRecod.getRecordTime(), 6));
        localStringBuffer.append("\n");
        if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_KG))
        {
          localStringBuffer.append(RecordListActivity.this.getString(2131361920) + RecordListActivity.this.lastRecod.getRweight());
          localStringBuffer.append(RecordListActivity.this.getText(2131361889));
          localStringBuffer.append("\n");
          if (!UtilConstants.CURRENT_SCALE.equals(UtilConstants.BATHROOM_SCALE))
          {
            localStringBuffer.append(RecordListActivity.this.getString(2131361921) + RecordListActivity.this.lastRecod.getRbodywater() + "%\n");
            localStringBuffer.append(RecordListActivity.this.getString(2131361922) + RecordListActivity.this.lastRecod.getRbodyfat() + "%\n");
            if (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_KG)) {
              break label1143;
            }
            localStringBuffer.append(RecordListActivity.this.getString(2131361923) + RecordListActivity.this.lastRecod.getRbone());
            localStringBuffer.append(RecordListActivity.this.getText(2131361889) + "\n");
          }
          label731:
          float f = UtilTooth.myround(UtilTooth.countBMI2(RecordListActivity.this.lastRecod.getRweight(), UtilConstants.CURRENT_USER.getBheigth() / 100.0F));
          localStringBuffer.append(RecordListActivity.this.getString(2131361924) + f + "\n");
          if (!UtilConstants.CURRENT_SCALE.equals(UtilConstants.BATHROOM_SCALE))
          {
            localStringBuffer.append(RecordListActivity.this.getString(2131361925) + RecordListActivity.this.lastRecod.getRvisceralfat() + "\n");
            localStringBuffer.append(RecordListActivity.this.getString(2131361926) + RecordListActivity.this.lastRecod.getRbmr() + " kcal\n");
            if (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_KG)) {
              break label1346;
            }
            localStringBuffer.append(RecordListActivity.this.getString(2131361927) + RecordListActivity.this.lastRecod.getRmuscle());
            localStringBuffer.append(RecordListActivity.this.getText(2131361889) + "\n");
          }
        }
        for (;;)
        {
          Intent localIntent = new Intent();
          localIntent.setAction("android.intent.action.SEND");
          localIntent.putExtra("android.intent.extra.TEXT", localStringBuffer.toString());
          localIntent.setType("text/plain");
          RecordListActivity.this.startActivity(localIntent);
          return;
          if ((!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) && (!UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ST))) {
            break;
          }
          localStringBuffer.append(RecordListActivity.this.getString(2131361920) + UtilTooth.kgToLB_ForFatScale(Math.abs(Float.parseFloat(new StringBuilder(String.valueOf(RecordListActivity.this.lastRecod.getRweight())).toString()))));
          localStringBuffer.append(RecordListActivity.this.getText(2131361890));
          break;
          label1143:
          if ((UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ST)))
          {
            localStringBuffer.append(RecordListActivity.this.getString(2131361923) + UtilTooth.kgToLB(Math.abs(Float.parseFloat(new StringBuilder(String.valueOf(RecordListActivity.this.lastRecod.getRbone())).toString()))));
            localStringBuffer.append(RecordListActivity.this.getText(2131361890) + "\n");
            break label731;
          }
          localStringBuffer.append(RecordListActivity.this.getString(2131361923) + RecordListActivity.this.lastRecod.getRbone());
          localStringBuffer.append(RecordListActivity.this.getText(2131361889) + "\n");
          break label731;
          label1346:
          if ((UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB)) || (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ST)))
          {
            localStringBuffer.append(RecordListActivity.this.getString(2131361927) + UtilTooth.kgToLB(Math.abs(Float.parseFloat(new StringBuilder(String.valueOf(RecordListActivity.this.lastRecod.getRmuscle())).toString()))));
            localStringBuffer.append(RecordListActivity.this.getText(2131361890) + "\n");
          }
          else
          {
            localStringBuffer.append(RecordListActivity.this.getString(2131361927) + RecordListActivity.this.lastRecod.getRmuscle());
            localStringBuffer.append(RecordListActivity.this.getText(2131361889) + "\n");
          }
        }
      }
      Toast.makeText(RecordListActivity.this, RecordListActivity.this.getString(2131361914), 0).show();
    }
  };
  private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener()
  {
    public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
    {
      RecordListActivity.this.selectedPosition = paramAnonymousInt;
      ListView localListView = (ListView)paramAnonymousAdapterView;
      RecordListActivity.this.lastRecod = ((Records)localListView.getItemAtPosition(paramAnonymousInt));
      RecordListActivity.this.mChart.setSeriesSelection(RecordListActivity.this.getchartSelectIndex(RecordListActivity.this.lastRecod.getId()));
      RecordListActivity.this.mChart.invalidate();
      RecordListActivity.this.recordAdaptor.setSelectedPosition(paramAnonymousInt);
      RecordListActivity.this.recordAdaptor.notifyDataSetInvalidated();
      RecordListActivity.this.handler.sendEmptyMessage(1);
      Intent localIntent = new Intent();
      localIntent.setClass(RecordListActivity.this, RecordListItemActivity.class);
      Bundle localBundle = new Bundle();
      localBundle.putSerializable("record", RecordListActivity.this.lastRecod);
      localIntent.putExtras(localBundle);
      RecordListActivity.this.startActivity(localIntent);
    }
  };
  private Records lastRecod;
  private LinearLayout linebg;
  private TextView list_tv;
  private ListView lv;
  private GraphicalView mChart;
  private RecordDetailAdaptor recordAdaptor;
  private RecordService recordService;
  private int recordid = 0;
  private int selectedPosition = -1;
  private ImageView shareImage;
  private int type = 0;
  private TextView username_tv;
  
  private void chartClick()
  {
    new SimpleDateFormat("dd-MMM-yyyy");
    SeriesSelection localSeriesSelection = this.mChart.getCurrentSeriesAndPoint();
    if (localSeriesSelection != null)
    {
      this.mChart.invalidate();
      int i = localSeriesSelection.getPointIndex();
      if ((CacheHelper.recordListDesc != null) && (i >= 0) && (CacheHelper.recordListDesc.size() > i))
      {
        this.lastRecod = ((Records)CacheHelper.recordListDesc.get(i));
        this.selectedPosition = getSelectIndex(this.lastRecod.getId());
        this.handler.sendEmptyMessage(1);
      }
    }
  }
  
  private XYMultipleSeriesRenderer getDemoRenderer()
  {
    XYMultipleSeriesRenderer localXYMultipleSeriesRenderer = new XYMultipleSeriesRenderer();
    localXYMultipleSeriesRenderer.setAxisTitleTextSize(16.0F);
    localXYMultipleSeriesRenderer.setChartTitleTextSize(20.0F);
    localXYMultipleSeriesRenderer.setLabelsTextSize(15.0F);
    localXYMultipleSeriesRenderer.setLegendTextSize(15.0F);
    localXYMultipleSeriesRenderer.setPointSize(6.5F);
    localXYMultipleSeriesRenderer.setXLabels(4);
    localXYMultipleSeriesRenderer.setXTitle(getText(2131361885).toString());
    localXYMultipleSeriesRenderer.setXLabelsAlign(Paint.Align.LEFT);
    localXYMultipleSeriesRenderer.setYLabelsAlign(Paint.Align.RIGHT);
    localXYMultipleSeriesRenderer.setZoomEnabled(true, false);
    localXYMultipleSeriesRenderer.setPanEnabled(true, false);
    localXYMultipleSeriesRenderer.setAxesColor(-3355444);
    localXYMultipleSeriesRenderer.setLabelsColor(-1);
    localXYMultipleSeriesRenderer.setApplyBackgroundColor(true);
    localXYMultipleSeriesRenderer.setMarginsColor(getResources().getColor(2131230755));
    int[] arrayOfInt = new int[4];
    arrayOfInt[1] = 30;
    arrayOfInt[2] = 10;
    localXYMultipleSeriesRenderer.setMargins(arrayOfInt);
    localXYMultipleSeriesRenderer.setClickEnabled(true);
    localXYMultipleSeriesRenderer.setSelectableBuffer(40);
    localXYMultipleSeriesRenderer.setShowLegend(false);
    localXYMultipleSeriesRenderer.setBackgroundColor(getResources().getColor(2131230754));
    XYSeriesRenderer localXYSeriesRenderer = new XYSeriesRenderer();
    localXYSeriesRenderer.setColor(getResources().getColor(2131230756));
    localXYSeriesRenderer.setLineWidth(5.0F);
    localXYSeriesRenderer.setPointStyle(PointStyle.CIRCLE);
    localXYSeriesRenderer.setFillBelowLine(true);
    localXYSeriesRenderer.setFillBelowLineColor(0);
    localXYSeriesRenderer.setFillPoints(true);
    localXYSeriesRenderer.setDisplayChartValues(true);
    localXYSeriesRenderer.setChartValuesTextSize(20.0F);
    localXYMultipleSeriesRenderer.addSeriesRenderer(localXYSeriesRenderer);
    return localXYMultipleSeriesRenderer;
  }
  
  private int getSelectIndex(int paramInt)
  {
    List localList = CacheHelper.recordList;
    int i = 0;
    Iterator localIterator;
    if (localList != null)
    {
      int j = CacheHelper.recordList.size();
      i = 0;
      if (j > 0) {
        localIterator = CacheHelper.recordList.iterator();
      }
    }
    for (;;)
    {
      if (!localIterator.hasNext()) {}
      while (((Records)localIterator.next()).getId() == paramInt) {
        return i;
      }
      i++;
    }
  }
  
  private int getchartSelectIndex(int paramInt)
  {
    List localList = CacheHelper.recordListDesc;
    int i = 0;
    Iterator localIterator;
    if (localList != null)
    {
      int j = CacheHelper.recordListDesc.size();
      i = 0;
      if (j > 0) {
        localIterator = CacheHelper.recordListDesc.iterator();
      }
    }
    for (;;)
    {
      if (!localIterator.hasNext()) {}
      while (((Records)localIterator.next()).getId() == paramInt) {
        return i;
      }
      i++;
    }
  }
  
  private void initView()
  {
    this.recordService = new RecordService(this);
    this.username_tv = ((TextView)findViewById(2131165268));
    this.headImage = ((ImageView)findViewById(2131165225));
    if (UtilConstants.CURRENT_USER != null)
    {
      this.username_tv.setText(UtilConstants.CURRENT_USER.getUserName());
      if ((UtilConstants.CURRENT_USER.getPer_photo() != null) && (!"".equals(UtilConstants.CURRENT_USER.getPer_photo())) && (!UtilConstants.CURRENT_USER.getPer_photo().equals("null")))
      {
        Bitmap localBitmap = Image.toRoundCorner(new imageUtil().getBitmapTodifferencePath(UtilConstants.CURRENT_USER.getPer_photo(), this), 8);
        this.headImage.setImageBitmap(localBitmap);
      }
    }
    this.back_tv = ((TextView)findViewById(2131165269));
    this.back_tv.setOnClickListener(this.imgOnClickListener);
    this.graph_tv = ((TextView)findViewById(2131165271));
    this.list_tv = ((TextView)findViewById(2131165272));
    this.graph_tv.setOnClickListener(this.imgOnClickListener);
    this.list_tv.setOnClickListener(this.imgOnClickListener);
    this.linebg = ((LinearLayout)findViewById(2131165273));
    this.charcontainer = ((LinearLayout)findViewById(2131165279));
    this.delist = ((LinearLayout)findViewById(2131165274));
    this.deleteImg = ((ImageView)findViewById(2131165280));
    this.delallImg = ((ImageView)findViewById(2131165281));
    this.shareImage = ((ImageView)findViewById(2131165270));
    this.deleteImg.setOnClickListener(this.imgOnClickListener);
    this.delallImg.setOnClickListener(this.imgOnClickListener);
    this.shareImage.setOnClickListener(this.imgOnClickListener);
    this.lv = ((ListView)findViewById(2131165278));
    this.lv.setOnItemClickListener(this.itemClickListener);
    this.lv.setSelector(2130837608);
    this.chartContainer = ((LinearLayout)findViewById(2131165279));
    loadData();
    if ((TextUtils.isEmpty(UtilConstants.FIRST_INSTALL_DETAIL)) && (CacheHelper.recordListDesc.size() == 1)) {
      showTipMask();
    }
  }
  
  private void intiListView(List<Records> paramList)
  {
    if ((paramList != null) && (paramList.size() > 0))
    {
      this.recordAdaptor = new RecordDetailAdaptor(getApplicationContext(), paramList, this.lv, 2130903075);
      if (this.selectedPosition >= paramList.size()) {
        this.selectedPosition = (-1 + paramList.size());
      }
      int i = 0;
      Iterator localIterator = paramList.iterator();
      for (;;)
      {
        if (!localIterator.hasNext()) {}
        for (;;)
        {
          this.selectedPosition = i;
          this.recordAdaptor.setSelectedPosition(this.selectedPosition);
          this.lv.setAdapter(this.recordAdaptor);
          this.handler.sendEmptyMessage(2);
          return;
          if (((Records)localIterator.next()).getId() != this.recordid) {
            break;
          }
          this.selectedPosition = i;
        }
        i++;
      }
    }
    ArrayList localArrayList = new ArrayList();
    this.selectedPosition = -1;
    this.recordAdaptor = new RecordDetailAdaptor(getApplicationContext(), localArrayList, this.lv, 2130903075);
    this.lv.setAdapter(this.recordAdaptor);
  }
  
  private void loadData()
  {
    double[] arrayOfDouble;
    int i;
    Records localRecords;
    try
    {
      if (UtilConstants.CURRENT_USER != null)
      {
        CacheHelper.recordListDesc = this.recordService.getAllDatasByScaleAndIDAsc(UtilConstants.CURRENT_SCALE, UtilConstants.CURRENT_USER.getId(), 167.0F);
        CacheHelper.recordList = this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, UtilConstants.CURRENT_USER.getId(), 167.0F);
      }
      if (CacheHelper.recordListDesc != null)
      {
        arrayOfDate = new Date[CacheHelper.recordListDesc.size()];
        arrayOfDouble = new double[CacheHelper.recordListDesc.size()];
        intiListView(CacheHelper.recordList);
        if ((CacheHelper.recordListDesc != null) && (CacheHelper.recordListDesc.size() > 0)) {
          this.lastRecod = ((Records)CacheHelper.recordListDesc.get(0));
        }
        i = 0;
        if (i >= CacheHelper.recordListDesc.size()) {
          openChart(arrayOfDate, arrayOfDouble);
        }
      }
      else
      {
        return;
      }
    }
    catch (Exception localException)
    {
      Date[] arrayOfDate;
      for (;;)
      {
        localException.printStackTrace();
      }
      localRecords = (Records)CacheHelper.recordListDesc.get(i);
      arrayOfDate[i] = UtilTooth.stringToTime(localRecords.getRecordTime());
      if (this.type != 0) {
        break label228;
      }
    }
    UtilConstants.isWeight = true;
    label188:
    if (this.type == 0) {
      if (localRecords.getScaleType().contains(UtilConstants.BABY_SCALE)) {
        arrayOfDouble[i] = UtilTooth.myround2(localRecords.getRweight());
      }
    }
    for (;;)
    {
      i++;
      break;
      label228:
      UtilConstants.isWeight = false;
      break label188;
      arrayOfDouble[i] = localRecords.getRweight();
      continue;
      if (this.type == 6) {
        arrayOfDouble[i] = localRecords.getRbmi();
      } else if (this.type == 7) {
        arrayOfDouble[i] = UtilTooth.myround(localRecords.getRbmr());
      } else if (this.type == 2) {
        arrayOfDouble[i] = UtilTooth.myround(localRecords.getRbodyfat());
      } else if (this.type == 4) {
        arrayOfDouble[i] = UtilTooth.myround(localRecords.getRbodywater());
      } else if (this.type == 1) {
        arrayOfDouble[i] = UtilTooth.myround(localRecords.getRbone());
      } else if (this.type == 3) {
        arrayOfDouble[i] = UtilTooth.myround(localRecords.getRmuscle());
      } else if (this.type == 5) {
        arrayOfDouble[i] = UtilTooth.myround(localRecords.getRvisceralfat());
      }
    }
  }
  
  private void openChart(Date[] paramArrayOfDate, double[] paramArrayOfDouble)
  {
    TimeSeries localTimeSeries;
    double d1;
    double d2;
    long l1;
    long l2;
    int i;
    String str;
    int j;
    XYMultipleSeriesRenderer localXYMultipleSeriesRenderer;
    double d3;
    label108:
    label244:
    int k;
    Iterator localIterator;
    if ((paramArrayOfDate != null) && (paramArrayOfDouble != null))
    {
      localTimeSeries = new TimeSeries("Views");
      d1 = 500000.0D;
      d2 = 0.0D;
      l1 = 0L;
      l2 = 8888888888888888L;
      i = 1;
      str = UtilConstants.CURRENT_SCALE;
      if (!UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_KG)) {
        break label381;
      }
      i = 1;
      j = 0;
      if (j < paramArrayOfDate.length) {
        break label497;
      }
      XYMultipleSeriesDataset localXYMultipleSeriesDataset = new XYMultipleSeriesDataset();
      localXYMultipleSeriesDataset.addSeries(localTimeSeries);
      localXYMultipleSeriesRenderer = getDemoRenderer();
      if (d1 - 10.0D < 0.0D) {
        break label706;
      }
      d3 = d1 - 10.0D;
      localXYMultipleSeriesRenderer.setYAxisMin(d3);
      localXYMultipleSeriesRenderer.setYAxisMax(10.0D + d2);
      long l3 = l1;
      double d4 = Double.parseDouble(l3 - 31536000L);
      double d5 = Double.parseDouble(31536000L + l3);
      localXYMultipleSeriesRenderer.setXAxisMin(d4);
      localXYMultipleSeriesRenderer.setXAxisMax(d5);
      switch (this.type)
      {
      default: 
        this.mChart = ChartFactory.getTimeChartView(getBaseContext(), UtilConstants.isWeight, localXYMultipleSeriesDataset, localXYMultipleSeriesRenderer, null, this);
        if (CacheHelper.recordListDesc != null)
        {
          k = 0;
          localIterator = CacheHelper.recordListDesc.iterator();
          label283:
          if (localIterator.hasNext()) {
            break;
          }
        }
        break;
      }
    }
    label293:
    for (this.selectedPosition = k;; this.selectedPosition = (-1 + paramArrayOfDate.length))
    {
      this.mChart.setSeriesSelection(this.selectedPosition);
      this.mChart.setSoundEffectsEnabled(false);
      this.mChart.invalidate();
      org.achartengine.chart.XYChart.weightType = i;
      this.mChart.setBackgroundColor(0);
      this.mChart.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          RecordListActivity.this.chartClick();
        }
      });
      this.chartContainer.removeAllViewsInLayout();
      this.chartContainer.addView(this.mChart);
      this.handler.sendEmptyMessage(1);
      return;
      label381:
      if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_LB))
      {
        if ((str.equals(UtilConstants.BODY_SCALE)) || (str.equals(UtilConstants.BATHROOM_SCALE)))
        {
          i = 2;
          break;
        }
        i = 5;
        break;
      }
      if (UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_ST))
      {
        if (str.equals(UtilConstants.BODY_SCALE))
        {
          i = 3;
          break;
        }
        if (str.equals(UtilConstants.BATHROOM_SCALE))
        {
          i = 4;
          break;
        }
        i = 5;
        break;
      }
      if (!UtilConstants.CHOICE_KG.equals(UtilConstants.UNIT_FATLB)) {
        break;
      }
      i = 5;
      break;
      label497:
      long l4 = paramArrayOfDate[j].getTime();
      if (l4 > l1) {
        l1 = l4;
      }
      if (l4 < l2) {
        l2 = l4;
      }
      double d6 = UtilTooth.round(paramArrayOfDouble[j], 2);
      float f = (float)paramArrayOfDouble[j];
      if (UtilConstants.isWeight) {
        switch (i)
        {
        }
      }
      for (;;)
      {
        if (d6 > 0.0D)
        {
          localTimeSeries.add(paramArrayOfDate[j], d6);
          if (d6 > d2) {
            d2 = d6;
          }
          if (d6 < d1) {
            d1 = d6;
          }
        }
        j++;
        break;
        if (UtilConstants.CURRENT_SCALE.equals(UtilConstants.BABY_SCALE))
        {
          d6 = UtilTooth.round(paramArrayOfDouble[j], 2);
          continue;
          d6 = UtilTooth.round(UtilTooth.kgToLB_ForFatScale2(d6), 2);
          continue;
          d6 = UtilTooth.kgToStLb_F((float)d6);
          continue;
          d6 = UtilTooth.kgToStLb_B((float)d6);
          continue;
          d6 = UtilTooth.lbToLBOZ_F(f);
        }
      }
      label706:
      d3 = 0.0D;
      break label108;
      if (UtilConstants.UNIT_KG.equals(UtilConstants.CHOICE_KG))
      {
        localXYMultipleSeriesRenderer.setYTitle(getText(2131361816).toString());
        break label244;
      }
      if (UtilConstants.UNIT_LB.equals(UtilConstants.CHOICE_KG))
      {
        localXYMultipleSeriesRenderer.setYTitle(getText(2131361818).toString());
        break label244;
      }
      if (UtilConstants.UNIT_FATLB.equals(UtilConstants.CHOICE_KG))
      {
        localXYMultipleSeriesRenderer.setYTitle(getText(2131361817).toString());
        break label244;
      }
      localXYMultipleSeriesRenderer.setYTitle(getText(2131361819).toString());
      break label244;
      localXYMultipleSeriesRenderer.setYTitle(getText(2131361827).toString());
      break label244;
      localXYMultipleSeriesRenderer.setYTitle(getText(2131361826).toString());
      break label244;
      localXYMultipleSeriesRenderer.setYTitle(getText(2131361822).toString());
      break label244;
      localXYMultipleSeriesRenderer.setYTitle("   " + getText(2131361824).toString());
      break label244;
      localXYMultipleSeriesRenderer.setYTitle(getText(2131361821).toString());
      break label244;
      localXYMultipleSeriesRenderer.setYTitle("       " + getText(2131361823).toString());
      break label244;
      localXYMultipleSeriesRenderer.setYTitle(getText(2131361825).toString());
      break label244;
      if (((Records)localIterator.next()).getId() == this.recordid)
      {
        this.selectedPosition = k;
        break label293;
      }
      k++;
      break label283;
    }
  }
  
  private void showTipMask()
  {
    TextView localTextView = new TextView(this);
    localTextView.setText(getResources().getString(2131361797));
    localTextView.setTextColor(getResources().getColor(2131230727));
    localTextView.setTextSize(16.0F);
    localTextView.setGravity(17);
    this.guideView2 = GuideView.Builder.newInstance(this).setTargetView(this.list_tv).setCustomGuideView(localTextView).setDirction(GuideView.Direction.BOTTOM).setShape(GuideView.MyShape.CIRCULAR).setBgColor(getResources().getColor(2131230720)).setRadius(100).setOnclickListener(new GuideView.OnClickCallback()
    {
      public void onClickedGuideView()
      {
        RecordListActivity.this.guideView2.hide();
        if (UtilConstants.su == null) {
          UtilConstants.su = new SharedPreferencesUtil(RecordListActivity.this);
        }
        UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_detail", "1");
        UtilConstants.FIRST_INSTALL_DETAIL = "1";
      }
    }).build();
    this.guideView2.show();
  }
  
  private void showTipMask2()
  {
    TextView localTextView = new TextView(this);
    localTextView.setText(getResources().getString(2131361798));
    localTextView.setTextColor(getResources().getColor(2131230727));
    localTextView.setTextSize(16.0F);
    localTextView.setGravity(17);
    this.guideView = GuideView.Builder.newInstance(this).setTargetView(this.shareImage).setCustomGuideView(localTextView).setDirction(GuideView.Direction.BOTTOM).setShape(GuideView.MyShape.CIRCULAR).setBgColor(getResources().getColor(2131230720)).setOnclickListener(new GuideView.OnClickCallback()
    {
      public void onClickedGuideView()
      {
        RecordListActivity.this.guideView.hide();
        if (UtilConstants.su == null) {
          UtilConstants.su = new SharedPreferencesUtil(RecordListActivity.this);
        }
        UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_share", "1");
        UtilConstants.FIRST_INSTALL_SHARE = "1";
      }
    }).build();
    this.guideView.show();
  }
  
  public void dateChangeCallback(int paramInt)
  {
    try
    {
      Date localDate2 = UtilTooth.stringToTime(this.lastRecod.getRecordTime());
      localDate1 = localDate2;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Date localDate1 = null;
      }
    }
    switch (paramInt)
    {
    }
    do
    {
      do
      {
        do
        {
          do
          {
            return;
            GraphicalView.mZoomInEnable = false;
            return;
            GraphicalView.mZoomInEnable = true;
            GraphicalView.mZoomOutEnable = true;
            return;
          } while (localDate1 == null);
          this.dateType = 5;
          return;
        } while (localDate1 == null);
        this.dateType = 5;
        return;
        GraphicalView.mZoomInEnable = true;
        GraphicalView.mZoomOutEnable = true;
      } while (localDate1 == null);
      this.dateType = 5;
      return;
      GraphicalView.mZoomOutEnable = false;
    } while (localDate1 == null);
    this.dateType = 5;
  }
  
  protected void dialog(String paramString, final int paramInt)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setMessage(paramString);
    localBuilder.setNegativeButton(2131361853, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    });
    localBuilder.setPositiveButton(2131361852, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        for (;;)
        {
          try
          {
            int i = paramInt;
            switch (i)
            {
            }
          }
          catch (Exception localException)
          {
            continue;
          }
          paramAnonymousDialogInterface.dismiss();
          return;
          if ((RecordListActivity.this.lastRecod != null) && (UtilConstants.CURRENT_USER != null))
          {
            RecordListActivity.this.recordService.delete(RecordListActivity.this.lastRecod);
            CacheHelper.recordListDesc = RecordListActivity.this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, UtilConstants.CURRENT_USER.getId(), UtilConstants.CURRENT_USER.getBheigth());
            if ((CacheHelper.recordListDesc != null) && (CacheHelper.recordListDesc.size() > 0))
            {
              RecordListActivity.this.lastRecod = ((Records)CacheHelper.recordListDesc.get(0));
              RecordListActivity.this.recordid = RecordListActivity.this.lastRecod.getId();
            }
            RecordListActivity.this.loadData();
            continue;
            if (UtilConstants.CURRENT_USER != null)
            {
              RecordListActivity.this.recordService.deleteByUseridAndScale(UtilConstants.CURRENT_USER.getId(), UtilConstants.CURRENT_SCALE);
              CacheHelper.recordListDesc = RecordListActivity.this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, UtilConstants.CURRENT_USER.getId(), UtilConstants.CURRENT_USER.getBheigth());
              RecordListActivity.this.lastRecod = null;
              RecordListActivity.this.recordid = -1;
              RecordListActivity.this.loadData();
            }
          }
        }
      }
    });
    localBuilder.create().show();
  }
  
  public void onClick(View paramView) {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903046);
    this.handler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        switch (paramAnonymousMessage.what)
        {
        }
        for (;;)
        {
          super.handleMessage(paramAnonymousMessage);
          return;
          if ((RecordListActivity.this.lv != null) && (RecordListActivity.this.recordAdaptor != null) && (RecordListActivity.this.recordAdaptor.selectedPosition >= 0)) {
            RecordListActivity.this.lv.setSelection(RecordListActivity.this.recordAdaptor.selectedPosition);
          }
        }
      }
    };
    Bundle localBundle = getIntent().getExtras();
    this.type = localBundle.getInt("type");
    this.recordid = localBundle.getInt("id");
    initView();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\RecordListActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */