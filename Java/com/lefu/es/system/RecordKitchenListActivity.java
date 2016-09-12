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
public class RecordKitchenListActivity
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
        return;
      case 2131165269: 
        RecordKitchenListActivity.this.finish();
        return;
      case 2131165271: 
        RecordKitchenListActivity.this.linebg.setBackgroundDrawable(RecordKitchenListActivity.this.getResources().getDrawable(2130837603));
        RecordKitchenListActivity.this.delist.setVisibility(8);
        RecordKitchenListActivity.this.lv.setVisibility(8);
        RecordKitchenListActivity.this.charcontainer.setVisibility(0);
        return;
      case 2131165272: 
        RecordKitchenListActivity.this.linebg.setBackgroundDrawable(RecordKitchenListActivity.this.getResources().getDrawable(2130837604));
        RecordKitchenListActivity.this.delist.setVisibility(0);
        RecordKitchenListActivity.this.lv.setVisibility(0);
        RecordKitchenListActivity.this.charcontainer.setVisibility(8);
        return;
      case 2131165280: 
        RecordKitchenListActivity.this.deleteImg.setBackgroundDrawable(RecordKitchenListActivity.this.getResources().getDrawable(2130837556));
        RecordKitchenListActivity.this.delallImg.setBackgroundDrawable(RecordKitchenListActivity.this.getResources().getDrawable(2130837554));
        if (RecordKitchenListActivity.this.selectedPosition == -1)
        {
          Toast.makeText(RecordKitchenListActivity.this, RecordKitchenListActivity.this.getString(2131361914), 1);
          return;
        }
        RecordKitchenListActivity.this.dialog(RecordKitchenListActivity.this.getString(2131361845), paramAnonymousView.getId());
        return;
      case 2131165281: 
        RecordKitchenListActivity.this.deleteImg.setBackgroundDrawable(RecordKitchenListActivity.this.getResources().getDrawable(2130837553));
        RecordKitchenListActivity.this.delallImg.setBackgroundDrawable(RecordKitchenListActivity.this.getResources().getDrawable(2130837555));
        RecordKitchenListActivity.this.dialog(RecordKitchenListActivity.this.getString(2131361847), paramAnonymousView.getId());
        return;
      }
      if (RecordKitchenListActivity.this.lastRecod != null)
      {
        StringBuffer localStringBuffer = new StringBuffer();
        if (UtilConstants.CURRENT_USER != null)
        {
          localStringBuffer.append(UtilConstants.CURRENT_USER.getUserName());
          localStringBuffer.append("\n");
        }
        if (RecordKitchenListActivity.this.lastRecod != null)
        {
          localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361919) + StringUtils.getDateShareString(RecordKitchenListActivity.this.lastRecod.getRecordTime(), 6));
          localStringBuffer.append("\n");
          localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361918) + RecordKitchenListActivity.this.lastRecod.getRphoto());
          localStringBuffer.append("\n");
        }
        if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_LB))
        {
          localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361920) + UtilTooth.kgToLBoz(RecordKitchenListActivity.this.lastRecod.getRweight()));
          localStringBuffer.append(RecordKitchenListActivity.this.getText(2131361893));
        }
        for (;;)
        {
          localStringBuffer.append("\n");
          localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361961) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRmuscle()) + "kcal\n");
          localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361962) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRbodywater()) + "g\n");
          localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361963) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRbodyfat()) + "g\n");
          localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361964) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRbone()) + "g\n");
          localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361965) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRbmr()) + "mg\n");
          localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361966) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRvisceralfat()) + " g\n");
          localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361967) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRbmi()) + "mg\n");
          localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361968) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getBodyAge()) + "mg\n");
          Intent localIntent = new Intent();
          localIntent.setAction("android.intent.action.SEND");
          localIntent.putExtra("android.intent.extra.TEXT", localStringBuffer.toString());
          localIntent.setType("text/plain");
          RecordKitchenListActivity.this.startActivity(localIntent);
          return;
          if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_FATLB))
          {
            localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361920) + UtilTooth.kgToFloz(RecordKitchenListActivity.this.lastRecod.getRweight()));
            localStringBuffer.append(RecordKitchenListActivity.this.getText(2131361900));
          }
          else if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_G))
          {
            localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361920) + RecordKitchenListActivity.this.lastRecod.getRweight());
            localStringBuffer.append(RecordKitchenListActivity.this.getText(2131361889));
          }
          else if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ML))
          {
            localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361920) + RecordKitchenListActivity.this.lastRecod.getRweight());
            localStringBuffer.append(RecordKitchenListActivity.this.getText(2131361899));
          }
          else if (UtilConstants.CURRENT_USER.getDanwei().equals(UtilConstants.UNIT_ML2))
          {
            localStringBuffer.append(RecordKitchenListActivity.this.getString(2131361920) + UtilTooth.kgToML(RecordKitchenListActivity.this.lastRecod.getRweight()));
            localStringBuffer.append(RecordKitchenListActivity.this.getText(2131361901));
          }
        }
      }
      Toast.makeText(RecordKitchenListActivity.this, RecordKitchenListActivity.this.getString(2131361914), 0).show();
    }
  };
  private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener()
  {
    public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
    {
      RecordKitchenListActivity.this.selectedPosition = paramAnonymousInt;
      ListView localListView = (ListView)paramAnonymousAdapterView;
      RecordKitchenListActivity.this.lastRecod = ((Records)localListView.getItemAtPosition(paramAnonymousInt));
      RecordKitchenListActivity.this.recordAdaptor.setSelectedPosition(paramAnonymousInt);
      RecordKitchenListActivity.this.recordAdaptor.notifyDataSetInvalidated();
      RecordKitchenListActivity.this.handler.sendEmptyMessage(1);
      Intent localIntent = new Intent();
      localIntent.setClass(RecordKitchenListActivity.this, RecordListItemActivity.class);
      Bundle localBundle = new Bundle();
      localBundle.putSerializable("record", RecordKitchenListActivity.this.lastRecod);
      localIntent.putExtras(localBundle);
      RecordKitchenListActivity.this.startActivity(localIntent);
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
    if ((TextUtils.isEmpty(UtilConstants.FIRST_INSTALL_NATURION_DAILOG)) && (CacheHelper.recordList.size() > 0)) {
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
    try
    {
      if (UtilConstants.CURRENT_USER != null)
      {
        CacheHelper.recordList = this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, UtilConstants.CURRENT_USER.getId(), 167.0F);
        intiListView(CacheHelper.recordList);
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
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
        break label708;
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
          RecordKitchenListActivity.this.chartClick();
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
          d6 = UtilTooth.round(UtilTooth.kgToLB_F((float)d6), 2);
          continue;
          d6 = UtilTooth.kgToStLb_F((float)d6);
          continue;
          d6 = UtilTooth.kgToStLb_B((float)d6);
          continue;
          d6 = UtilTooth.lbToLBOZ_F(f);
        }
      }
      label708:
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
    localTextView.setText(getResources().getString(2131361798));
    localTextView.setTextColor(getResources().getColor(2131230727));
    localTextView.setTextSize(16.0F);
    localTextView.setGravity(17);
    this.guideView2 = GuideView.Builder.newInstance(this).setTargetView(this.shareImage).setCustomGuideView(localTextView).setDirction(GuideView.Direction.BOTTOM).setShape(GuideView.MyShape.CIRCULAR).setBgColor(getResources().getColor(2131230720)).setOnclickListener(new GuideView.OnClickCallback()
    {
      public void onClickedGuideView()
      {
        RecordKitchenListActivity.this.guideView2.hide();
        if (UtilConstants.su == null) {
          UtilConstants.su = new SharedPreferencesUtil(RecordKitchenListActivity.this);
        }
        UtilConstants.su.editSharedPreferences("lefuconfig", "first_naturion_dailog", "1");
        UtilConstants.FIRST_INSTALL_NATURION_DAILOG = "1";
      }
    }).build();
    this.guideView2.show();
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
          if ((RecordKitchenListActivity.this.lastRecod != null) && (UtilConstants.CURRENT_USER != null))
          {
            RecordKitchenListActivity.this.recordService.delete(RecordKitchenListActivity.this.lastRecod);
            CacheHelper.recordListDesc = RecordKitchenListActivity.this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, UtilConstants.CURRENT_USER.getId(), UtilConstants.CURRENT_USER.getBheigth());
            if ((CacheHelper.recordListDesc != null) && (CacheHelper.recordListDesc.size() > 0))
            {
              RecordKitchenListActivity.this.lastRecod = ((Records)CacheHelper.recordListDesc.get(0));
              RecordKitchenListActivity.this.recordid = RecordKitchenListActivity.this.lastRecod.getId();
            }
            RecordKitchenListActivity.this.loadData();
            continue;
            if (UtilConstants.CURRENT_USER != null)
            {
              RecordKitchenListActivity.this.recordService.deleteByUseridAndScale(UtilConstants.CURRENT_USER.getId(), UtilConstants.CURRENT_SCALE);
              CacheHelper.recordListDesc = RecordKitchenListActivity.this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, UtilConstants.CURRENT_USER.getId(), UtilConstants.CURRENT_USER.getBheigth());
              RecordKitchenListActivity.this.lastRecod = null;
              RecordKitchenListActivity.this.recordid = -1;
              RecordKitchenListActivity.this.loadData();
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
    setContentView(2130903045);
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
          if ((RecordKitchenListActivity.this.lv != null) && (RecordKitchenListActivity.this.recordAdaptor != null) && (RecordKitchenListActivity.this.recordAdaptor.selectedPosition >= 0)) {
            RecordKitchenListActivity.this.lv.setSelection(RecordKitchenListActivity.this.recordAdaptor.selectedPosition);
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


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\RecordKitchenListActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */