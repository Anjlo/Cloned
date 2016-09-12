package com.lefu.es.constant;

import android.content.Intent;
import com.lefu.es.entity.UserModel;
import com.lefu.es.util.SharedPreferencesUtil;
import java.io.File;

public class UtilConstants
{
  public static final String APPLICATION_PATH = "HealthScale/";
  public static String BABY_SCALE;
  public static String BATHROOM_SCALE;
  public static final int BMI_SINGLE = 6;
  public static final int BMR_SINGLE = 7;
  public static final int BODYFAT_SINGLE = 2;
  public static final int BODYWATER_SINGLE = 4;
  public static String BODY_SCALE;
  public static final int BONE_SINGLE = 1;
  public static String CHOICE_KG;
  public static String CURRENT_SCALE;
  public static UserModel CURRENT_USER;
  public static String ERROR_CODE;
  public static String ERROR_CODE_GETDATE;
  public static String ERROR_CODE_TEST;
  public static String FIRST_INSTALL_BABY;
  public static String FIRST_INSTALL_BATH;
  public static String FIRST_INSTALL_BODY = "";
  public static String FIRST_INSTALL_DAILOG;
  public static String FIRST_INSTALL_DETAIL = "";
  public static String FIRST_INSTALL_NATURION;
  public static String FIRST_INSTALL_NATURION_DAILOG;
  public static String FIRST_INSTALL_SHARE;
  public static final String IMAGE_CACHE_PATH = "Photos";
  public static String KITCHEN_SCALE;
  public static boolean LOG_DEBUG = false;
  public static int MAX_GROUP = 0;
  public static final int MUSCALE_SINGLE = 3;
  public static final int PHSICALAGE_SINGLE = 8;
  public static String SCALE_ORDER_SHUTDOWN;
  public static final String SDCARD_ROOT_PATH = "/mnt/sdcard/";
  public static int SELECT_LANGUAGE = 0;
  public static String SELECT_SCALE;
  public static int SELECT_USER = 0;
  public static String UNIT_FATLB;
  public static String UNIT_FLOZ;
  public static String UNIT_G;
  public static String UNIT_KG;
  public static String UNIT_LB;
  public static String UNIT_ML;
  public static String UNIT_ML2;
  public static String UNIT_ST;
  public static final String USER_HEADER_CACHE_PATH = "/sdcard/healthscale/userheader/";
  public static final int VISCALEFAT_SINGLE = 5;
  public static final int WEIGHT_SINGLE = 0;
  public static int checkScaleTimes = 0;
  public static String homeUrl;
  public static boolean isTipChangeScale = false;
  public static boolean isWeight = false;
  public static long receiveDataTime = 0L;
  public static final int scaleChangeMessage = 101;
  public static final String scaleName = "Electronic Scale";
  public static Intent serveIntent;
  public static SharedPreferencesUtil su;
  
  static
  {
    FIRST_INSTALL_DAILOG = "";
    FIRST_INSTALL_SHARE = "";
    FIRST_INSTALL_BATH = "";
    FIRST_INSTALL_BABY = "";
    FIRST_INSTALL_NATURION = "";
    FIRST_INSTALL_NATURION_DAILOG = "";
    LOG_DEBUG = true;
    homeUrl = "http://www.familyscale.com/";
    BODY_SCALE = "cf";
    BATHROOM_SCALE = "ce";
    BABY_SCALE = "cb";
    SELECT_SCALE = "cf";
    KITCHEN_SCALE = "ca";
    SELECT_USER = 0;
    CURRENT_SCALE = BODY_SCALE;
    CURRENT_USER = null;
    SELECT_LANGUAGE = 0;
    CHOICE_KG = "kg";
    UNIT_KG = "kg";
    UNIT_ST = "st:lb";
    UNIT_LB = "lb";
    UNIT_G = "g";
    UNIT_ML = "ml";
    UNIT_ML2 = "ml(milk)";
    UNIT_FATLB = "lb:oz";
    UNIT_FLOZ = "fl:oz";
    isWeight = false;
    MAX_GROUP = 10;
    ERROR_CODE = "fd31000000000031";
    ERROR_CODE_TEST = "fd31000000000033";
    ERROR_CODE_GETDATE = "fd33000000000033";
    SCALE_ORDER_SHUTDOWN = "fd35000000000035";
    su = null;
    receiveDataTime = 0L;
    isTipChangeScale = false;
  }
  
  public static void initDir()
  {
    File localFile = new File("/mnt/sdcard/HealthScale/Photos");
    if (!localFile.exists()) {
      localFile.mkdirs();
    }
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\constant\UtilConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */