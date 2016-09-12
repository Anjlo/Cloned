package org.achartengine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.achartengine.chart.AbstractChart;

public class GraphicalActivity
  extends Activity
{
  private AbstractChart mChart;
  private GraphicalView mView;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getIntent().getExtras();
    this.mChart = ((AbstractChart)localBundle.getSerializable("chart"));
    this.mView = new GraphicalView(this, this.mChart);
    this.mView.setBackgroundColor(-65536);
    String str = localBundle.getString("title");
    if (str == null) {
      requestWindowFeature(1);
    }
    for (;;)
    {
      setContentView(this.mView);
      return;
      if (str.length() > 0) {
        setTitle(str);
      }
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\org\achartengine\GraphicalActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */