package com.lefu.es.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.constant.imageUtil;
import com.lefu.es.db.DBOpenHelper;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.UserService;
import java.util.ArrayList;
import java.util.List;

public class UserlistviewAdapter
  extends BaseAdapter
{
  private Bitmap bitmap;
  private Context cont;
  private LayoutInflater inflater;
  private boolean isEdit;
  private int resource;
  private int selectedPosition = -1;
  private UserModel user;
  public List<UserModel> users = new ArrayList();
  private UserService uservice;
  
  public UserlistviewAdapter(Context paramContext, int paramInt, List<UserModel> paramList)
  {
    this.cont = paramContext;
    this.resource = paramInt;
    this.users = paramList;
    this.inflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
  }
  
  public int getCount()
  {
    if ((this.users == null) || (this.users.size() == 0)) {
      return 0;
    }
    return this.users.size();
  }
  
  public Object getItem(int paramInt)
  {
    if ((this.users == null) || (this.users.size() == 0)) {
      return null;
    }
    return this.users.get(paramInt);
  }
  
  public int getItemID(int paramInt)
  {
    return ((UserModel)this.users.get(paramInt)).getId();
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ViewCache localViewCache = new ViewCache(null);
    View localView = this.inflater.inflate(this.resource, null);
    localViewCache.photo = ((ImageView)localView.findViewById(2131165225));
    localViewCache.nameView = ((TextView)localView.findViewById(2131165466));
    localViewCache.deletimg = ((ImageView)localView.findViewById(2131165467));
    this.user = ((UserModel)this.users.get(paramInt));
    if (this.user != null)
    {
      this.bitmap = null;
      localViewCache.nameView.setText(this.user.getUserName());
      if ((this.user.getPer_photo() != null) && (!"".equals(this.user.getPer_photo())))
      {
        this.bitmap = imageUtil.getBitmapfromPath(this.user.getPer_photo());
        localViewCache.photo.setImageBitmap(this.bitmap);
      }
    }
    if (!this.isEdit) {
      localViewCache.deletimg.setVisibility(8);
    }
    for (;;)
    {
      localViewCache.deletimg.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          try
          {
            if (UserlistviewAdapter.this.uservice == null) {
              UserlistviewAdapter.this.uservice = new UserService(UserlistviewAdapter.this.cont);
            }
            int i = UserlistviewAdapter.this.getItemID(paramInt);
            UserlistviewAdapter.this.uservice.delete(i);
            UserlistviewAdapter.this.users.remove(paramInt);
            UserlistviewAdapter.this.notifyDataSetChanged();
            if ((UtilConstants.CURRENT_USER != null) && (UtilConstants.CURRENT_USER.getId() == i)) {
              UtilConstants.CURRENT_USER = null;
            }
            if ((UserlistviewAdapter.this.users == null) || (UserlistviewAdapter.this.users.size() == 0))
            {
              Intent localIntent = new Intent("ACTION_NO_USER");
              UserlistviewAdapter.this.cont.sendBroadcast(localIntent);
            }
            int j = new UserService(UserlistviewAdapter.this.cont).getMaxGroup();
            SQLiteDatabase localSQLiteDatabase = new DBOpenHelper(UserlistviewAdapter.this.cont).getReadableDatabase();
            Object[] arrayOfObject = new Object[2];
            arrayOfObject[0] = Integer.valueOf(j);
            arrayOfObject[1] = UtilConstants.SELECT_SCALE;
            localSQLiteDatabase.execSQL("update user u set max(number) = ? where scaletype = ?", arrayOfObject);
            localSQLiteDatabase.close();
            return;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
      });
      return localView;
      localViewCache.deletimg.setVisibility(0);
    }
  }
  
  public void setEdit(boolean paramBoolean)
  {
    this.isEdit = paramBoolean;
  }
  
  public void setSelectedPosition(int paramInt)
  {
    this.selectedPosition = paramInt;
  }
  
  private final class ViewCache
  {
    public ImageView deletimg;
    public TextView nameView;
    public ImageView photo;
    
    private ViewCache() {}
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\adapter\UserlistviewAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */