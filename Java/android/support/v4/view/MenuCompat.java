package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.MenuItem;

public class MenuCompat
{
  static final MenuVersionImpl IMPL = new BaseMenuVersionImpl();
  
  static
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      IMPL = new HoneycombMenuVersionImpl();
      return;
    }
  }
  
  public static boolean setShowAsAction(MenuItem paramMenuItem, int paramInt)
  {
    return IMPL.setShowAsAction(paramMenuItem, paramInt);
  }
  
  static class BaseMenuVersionImpl
    implements MenuCompat.MenuVersionImpl
  {
    public boolean setShowAsAction(MenuItem paramMenuItem, int paramInt)
    {
      return false;
    }
  }
  
  static class HoneycombMenuVersionImpl
    implements MenuCompat.MenuVersionImpl
  {
    public boolean setShowAsAction(MenuItem paramMenuItem, int paramInt)
    {
      MenuItemCompatHoneycomb.setShowAsAction(paramMenuItem, paramInt);
      return true;
    }
  }
  
  static abstract interface MenuVersionImpl
  {
    public abstract boolean setShowAsAction(MenuItem paramMenuItem, int paramInt);
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\android\support\v4\view\MenuCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */