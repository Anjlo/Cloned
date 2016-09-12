package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.lefu.es.adapter.NutrientDetailAdapter;
import com.lefu.es.entity.NutrientAtrrBo;
import com.lefu.es.entity.NutrientBo;

@SuppressLint({"SimpleDateFormat"})
public class NutrientDetail2Activity
  extends Activity
  implements View.OnClickListener
{
  private TextView back_tv;
  private ListView detailist_contains;
  private TextView list_textview;
  private NutrientDetailAdapter nutrientAdapter = null;
  private NutrientBo nutrientBo = null;
  
  public static Intent creatIntent(Context paramContext, NutrientBo paramNutrientBo)
  {
    Intent localIntent = new Intent(paramContext, NutrientDetail2Activity.class);
    localIntent.putExtra("nutrient", paramNutrientBo);
    return localIntent;
  }
  
  private void initDate(NutrientBo paramNutrientBo)
  {
    if (paramNutrientBo != null)
    {
      this.nutrientAdapter = new NutrientDetailAdapter(this, NutrientAtrrBo.formatNutrientAtrrBo(paramNutrientBo), paramNutrientBo.getFoodWeight());
      this.detailist_contains.setAdapter(this.nutrientAdapter);
    }
  }
  
  private void initView()
  {
    this.back_tv = ((TextView)findViewById(2131165269));
    this.list_textview = ((TextView)findViewById(2131165272));
    this.detailist_contains = ((ListView)findViewById(2131165278));
    this.back_tv.setOnClickListener(this);
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    }
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903057);
    this.nutrientBo = ((NutrientBo)getIntent().getSerializableExtra("nutrient"));
    initView();
    initDate(this.nutrientBo);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\NutrientDetail2Activity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */