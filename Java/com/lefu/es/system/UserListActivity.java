package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.lefu.es.adapter.UserlistviewAdapter;
import com.lefu.es.constant.AppData;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.UserService;
import com.lefu.es.util.SharedPreferencesUtil;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"HandlerLeak"})
public class UserListActivity
  extends Activity
{
  private ImageView addphoto_imageView;
  private TextView backText;
  private ListView brithListview;
  private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if ("ACTION_NO_USER".equals(paramAnonymousIntent.getAction())) {
        UserListActivity.this.toAddUser();
      }
    }
  };
  private TextView editText;
  Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      UserListActivity.this.userAdapter.notifyDataSetChanged();
    }
  };
  View.OnClickListener imgOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      case 2131165408: 
      default: 
        return;
      case 2131165409: 
        UserListActivity.this.toAddUser();
        return;
      case 2131165406: 
        if (!UserListActivity.this.isEdit) {}
        for (UserListActivity.this.isEdit = true;; UserListActivity.this.isEdit = false)
        {
          UserListActivity.this.userAdapter.setEdit(UserListActivity.this.isEdit);
          UserListActivity.this.userAdapter.notifyDataSetChanged();
          return;
        }
      }
      UserListActivity.this.checkUser();
    }
  };
  private boolean isEdit = false;
  AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
  {
    public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
    {
      UtilConstants.CURRENT_USER = (UserModel)UserListActivity.this.userAdapter.users.get(paramAnonymousInt);
      UtilConstants.CURRENT_SCALE = UtilConstants.CURRENT_USER.getScaleType();
      UtilConstants.SELECT_USER = UtilConstants.CURRENT_USER.getId();
      System.out.println("当前用户称类型：" + UtilConstants.CURRENT_SCALE);
      if ((UtilConstants.CURRENT_USER != null) && (UtilConstants.CURRENT_USER.getDanwei() != null) && (!"".equals(UtilConstants.CURRENT_USER.getDanwei())) && (UtilConstants.su != null))
      {
        UtilConstants.su.editSharedPreferences("lefuconfig", "unit", UtilConstants.CURRENT_USER.getDanwei());
        UtilConstants.su.editSharedPreferences("lefuconfig", "user", Integer.valueOf(UtilConstants.CURRENT_USER.getId()));
      }
      if ((UtilConstants.CURRENT_USER.getScaleType() == null) || (!"".equals(UtilConstants.CURRENT_USER.getScaleType()))) {}
      for (String str = UtilConstants.UNIT_KG;; str = UtilConstants.CURRENT_USER.getScaleType())
      {
        UtilConstants.CHOICE_KG = str;
        ExitApplication.getInstance().exit(UserListActivity.this);
        UserListActivity.this.startActivity(new Intent(UserListActivity.this, LoadingActivity.class));
        return;
      }
    }
  };
  Intent serveIntent;
  private UserlistviewAdapter userAdapter;
  public List<UserModel> users = new ArrayList();
  private UserService uservice;
  
  private void checkUser()
  {
    if (this.userAdapter.getItem(0) == null)
    {
      toAddUser();
      return;
    }
    if ((UtilConstants.CURRENT_USER == null) || (AppData.isCheckScale))
    {
      Toast.makeText(this, getString(2131361937), 0).show();
      return;
    }
    finish();
  }
  
  private void toAddUser()
  {
    ExitApplication.getInstance().exit(this);
    startActivity(new Intent(this, UserAddActivity.class));
  }
  
  public void dataInit()
  {
    if (this.uservice == null) {
      this.uservice = new UserService(this);
    }
    this.users.clear();
    try
    {
      this.users = this.uservice.getAllUserByScaleType();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903063);
    viewInit();
    ExitApplication.getInstance().addActivity(this);
  }
  
  protected void onDestroy()
  {
    unregisterReceiver(this.broadcastReceiver);
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
    {
      checkUser();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  protected void onResume()
  {
    super.onResume();
  }
  
  protected void onStart()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("ACTION_NO_USER");
    registerReceiver(this.broadcastReceiver, localIntentFilter);
    super.onStart();
  }
  
  public void viewInit()
  {
    this.addphoto_imageView = ((ImageView)findViewById(2131165409));
    this.addphoto_imageView.setOnClickListener(this.imgOnClickListener);
    this.brithListview = ((ListView)findViewById(2131165408));
    this.brithListview.setOnItemClickListener(this.onItemClickListener);
    this.editText = ((TextView)findViewById(2131165406));
    this.editText.setOnClickListener(this.imgOnClickListener);
    this.backText = ((TextView)findViewById(2131165407));
    this.backText.setOnClickListener(this.imgOnClickListener);
    dataInit();
    this.userAdapter = new UserlistviewAdapter(getApplicationContext(), 2130903084, this.users);
    this.brithListview.setAdapter(this.userAdapter);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\UserListActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */