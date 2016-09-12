package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.lefu.es.cache.CacheHelper;
import com.lefu.es.entity.NutrientBo;
import com.lefu.es.system.fragment.SearchDialogFragment.NatureSelectListener;
import com.lefu.es.util.Tool;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.view.kmpautotextview.KMPAutoComplTextView;
import com.lefu.es.view.kmpautotextview.KMPAutoComplTextView.OnPopupItemClickListener;
import com.lefu.es.view.spinner.HintAdapter;
import com.lefu.es.view.spinner.HintSpinner;
import com.lefu.es.view.spinner.HintSpinner.Callback;
import java.util.ArrayList;
import java.util.List;

public class KitchenInfoActivity
  extends Activity
  implements SearchDialogFragment.NatureSelectListener
{
  private TextView Alpha_Carot_excel_title;
  private TextView Ash_excel_title;
  private TextView Beta_Carot_excel_title;
  private TextView Beta_Crypt_excel_title;
  private TextView Calcium_excel_title;
  private TextView Carbohydrt_excel_title;
  private TextView Cholesterol_excel_title;
  private TextView Choline_Tot_excel_title;
  private TextView Copper_excel_title;
  private TextView Energ_excel_title;
  private TextView FA_Mono_excel_title;
  private TextView FA_Poly_excel_title;
  private TextView FA_Sat_excel_title;
  private TextView Fiber_excel_title;
  private TextView Folate_DFE_excel_title;
  private TextView Folate_Tot_excel_title;
  private TextView Folic_Acid_excel_title;
  private TextView Food_Folate_excel_title;
  private TextView GmWt_1_excel_title;
  private TextView GmWt_2_excel_title;
  private TextView Iron_excel_title;
  private TextView Lipid_excel_title;
  private TextView LutZea_excel_title;
  private TextView Lycopene_excel_title;
  private TextView Magnesium_excel_title;
  private TextView Manganese_excel_title;
  private TextView Niacin_excel_title;
  private TextView Panto_Acid_excel_title;
  private TextView Phosphorus_excel_title;
  private TextView Potassium_excel_title;
  private TextView Protein_excel_title;
  private TextView Refuse_Pct_excel_title;
  private TextView Retinol_excel_title;
  private TextView Riboflavin_excel_title;
  private TextView Selenium_excel_title;
  private TextView Sodium_excel_title;
  private TextView Sugar_excel_title;
  private TextView Thiamin_excel_title;
  private TextView Vit_A_IU_excel_title;
  private TextView Vit_A_RAE_excel_title;
  private TextView Vit_B12_excel_title;
  private TextView Vit_B6_excel_title;
  private TextView Vit_C_excel_title;
  private TextView Vit_D_IU_excel_title;
  private TextView Vit_D_excel_title;
  private TextView Vit_E_excel_title;
  private TextView Vit_K_excel_title;
  private TextView Water_excel_title;
  private TextView Zinc_excel_title;
  View.OnClickListener btnOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      default: 
        return;
      case 2131165315: 
        if (TextUtils.isEmpty(KitchenInfoActivity.this.search_et.getText().toString()))
        {
          Toast.makeText(KitchenInfoActivity.this, "Please input something", 0).show();
          return;
        }
        NutrientBo localNutrientBo = CacheHelper.queryNutrientsByName(KitchenInfoActivity.this.search_et.getText().toString());
        if (localNutrientBo == null)
        {
          Toast.makeText(KitchenInfoActivity.this, "No Data had been found", 0).show();
          return;
        }
        KitchenInfoActivity.this.search_et.setText(localNutrientBo.getNutrientDesc());
        KitchenInfoActivity.this.selectNutrient = localNutrientBo;
        KitchenInfoActivity.this.setViewDate(localNutrientBo, 0.0F);
        return;
      }
      KitchenInfoActivity.this.finish();
    }
  };
  private TextView btn_mback;
  int currentPage = 0;
  private String currentUnit = null;
  private HintSpinner<String> defaultHintSpinner;
  private List<String> defaults;
  private float lastWeight = 0.0F;
  private boolean needChage = true;
  private TextView search_btn;
  private KMPAutoComplTextView search_et;
  private NutrientBo selectNutrient = null;
  private AutoCompleteTextView weight_et;
  
  private void initView()
  {
    this.currentUnit = getResources().getString(2131361897);
    this.search_et = ((KMPAutoComplTextView)findViewById(2131165310));
    this.weight_et = ((AutoCompleteTextView)findViewById(2131165316));
    this.search_btn = ((TextView)findViewById(2131165315));
    this.btn_mback = ((TextView)findViewById(2131165194));
    this.search_btn.setOnClickListener(this.btnOnClickListener);
    this.btn_mback.setOnClickListener(this.btnOnClickListener);
    this.Water_excel_title = ((TextView)findViewById(2131165319));
    this.Energ_excel_title = ((TextView)findViewById(2131165320));
    this.Protein_excel_title = ((TextView)findViewById(2131165321));
    this.Lipid_excel_title = ((TextView)findViewById(2131165322));
    this.Ash_excel_title = ((TextView)findViewById(2131165323));
    this.Carbohydrt_excel_title = ((TextView)findViewById(2131165324));
    this.Fiber_excel_title = ((TextView)findViewById(2131165325));
    this.Sugar_excel_title = ((TextView)findViewById(2131165326));
    this.Calcium_excel_title = ((TextView)findViewById(2131165327));
    this.Iron_excel_title = ((TextView)findViewById(2131165328));
    this.Magnesium_excel_title = ((TextView)findViewById(2131165329));
    this.Phosphorus_excel_title = ((TextView)findViewById(2131165330));
    this.Potassium_excel_title = ((TextView)findViewById(2131165331));
    this.Sodium_excel_title = ((TextView)findViewById(2131165332));
    this.Zinc_excel_title = ((TextView)findViewById(2131165333));
    this.Copper_excel_title = ((TextView)findViewById(2131165334));
    this.Manganese_excel_title = ((TextView)findViewById(2131165335));
    this.Selenium_excel_title = ((TextView)findViewById(2131165336));
    this.Vit_C_excel_title = ((TextView)findViewById(2131165337));
    this.Thiamin_excel_title = ((TextView)findViewById(2131165338));
    this.Riboflavin_excel_title = ((TextView)findViewById(2131165339));
    this.Niacin_excel_title = ((TextView)findViewById(2131165340));
    this.Panto_Acid_excel_title = ((TextView)findViewById(2131165341));
    this.Vit_B6_excel_title = ((TextView)findViewById(2131165342));
    this.Folate_Tot_excel_title = ((TextView)findViewById(2131165343));
    this.Folic_Acid_excel_title = ((TextView)findViewById(2131165344));
    this.Food_Folate_excel_title = ((TextView)findViewById(2131165345));
    this.Folate_DFE_excel_title = ((TextView)findViewById(2131165346));
    this.Choline_Tot_excel_title = ((TextView)findViewById(2131165347));
    this.Vit_B12_excel_title = ((TextView)findViewById(2131165348));
    this.Vit_A_IU_excel_title = ((TextView)findViewById(2131165349));
    this.Vit_A_RAE_excel_title = ((TextView)findViewById(2131165350));
    this.Retinol_excel_title = ((TextView)findViewById(2131165351));
    this.Alpha_Carot_excel_title = ((TextView)findViewById(2131165352));
    this.Beta_Carot_excel_title = ((TextView)findViewById(2131165353));
    this.Beta_Crypt_excel_title = ((TextView)findViewById(2131165354));
    this.Lycopene_excel_title = ((TextView)findViewById(2131165355));
    this.LutZea_excel_title = ((TextView)findViewById(2131165356));
    this.Vit_E_excel_title = ((TextView)findViewById(2131165357));
    this.Vit_D_excel_title = ((TextView)findViewById(2131165358));
    this.Vit_D_IU_excel_title = ((TextView)findViewById(2131165359));
    this.Vit_K_excel_title = ((TextView)findViewById(2131165360));
    this.FA_Sat_excel_title = ((TextView)findViewById(2131165361));
    this.FA_Mono_excel_title = ((TextView)findViewById(2131165362));
    this.FA_Poly_excel_title = ((TextView)findViewById(2131165363));
    this.Cholesterol_excel_title = ((TextView)findViewById(2131165364));
    this.GmWt_1_excel_title = ((TextView)findViewById(2131165365));
    this.GmWt_2_excel_title = ((TextView)findViewById(2131165366));
    this.Refuse_Pct_excel_title = ((TextView)findViewById(2131165367));
    this.search_et.setDatas(CacheHelper.nutrientNameList);
    this.search_et.setOnPopupItemClickListener(new KMPAutoComplTextView.OnPopupItemClickListener()
    {
      public void onPopupItemClick(CharSequence paramAnonymousCharSequence)
      {
        if (TextUtils.isEmpty(paramAnonymousCharSequence)) {
          return;
        }
        NutrientBo localNutrientBo = CacheHelper.queryNutrientsByName(paramAnonymousCharSequence.toString());
        if (localNutrientBo == null)
        {
          Toast.makeText(KitchenInfoActivity.this, "No Data had been found", 0).show();
          return;
        }
        KitchenInfoActivity.this.search_et.setText(localNutrientBo.getNutrientDesc());
        KitchenInfoActivity.this.selectNutrient = localNutrientBo;
        KitchenInfoActivity.this.setViewDate(localNutrientBo, 0.0F);
      }
    });
    this.defaults = new ArrayList();
    this.defaults.add(getResources().getString(2131361897));
    this.defaults.add(getResources().getString(2131361893));
    this.defaults.add(getResources().getString(2131361899));
    this.defaults.add(getResources().getString(2131361900));
    this.defaults.add(getResources().getString(2131361901));
    this.defaultHintSpinner = new HintSpinner((Spinner)findViewById(2131165317), new HintAdapter(this, 2131362069, this.defaults), new HintSpinner.Callback()
    {
      public void onItemSelected(int paramAnonymousInt, String paramAnonymousString)
      {
        KitchenInfoActivity.this.showSelectedItem(paramAnonymousString);
      }
    });
    this.defaultHintSpinner.init();
    this.weight_et.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable) {}
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        String str = paramAnonymousCharSequence.toString().trim();
        if (!TextUtils.isEmpty(str))
        {
          if (Tool.isDigitsOnly(str)) {
            for (;;)
            {
              try
              {
                if ((KitchenInfoActivity.this.currentUnit == null) || (!KitchenInfoActivity.this.needChage)) {
                  break;
                }
                if (KitchenInfoActivity.this.currentUnit.equals(KitchenInfoActivity.this.getResources().getString(2131361900)))
                {
                  KitchenInfoActivity.this.lastWeight = UtilTooth.lbTog(Float.parseFloat(KitchenInfoActivity.this.weight_et.getText().toString()));
                  if (KitchenInfoActivity.this.selectNutrient != null) {
                    KitchenInfoActivity.this.setViewDate(KitchenInfoActivity.this.selectNutrient, KitchenInfoActivity.this.lastWeight);
                  }
                  KitchenInfoActivity.this.needChage = true;
                  return;
                }
                if (!KitchenInfoActivity.this.currentUnit.equals(KitchenInfoActivity.this.getResources().getString(2131361893))) {
                  break label260;
                }
                KitchenInfoActivity.this.lastWeight = UtilTooth.ozTog(Float.parseFloat(KitchenInfoActivity.this.weight_et.getText().toString()));
                continue;
                KitchenInfoActivity.this.lastWeight = Float.parseFloat(str);
              }
              catch (Exception localException)
              {
                if (!KitchenInfoActivity.this.needChage) {
                  break;
                }
              }
              if (KitchenInfoActivity.this.selectNutrient != null) {
                KitchenInfoActivity.this.setViewDate(KitchenInfoActivity.this.selectNutrient, KitchenInfoActivity.this.lastWeight);
              }
              KitchenInfoActivity.this.needChage = true;
              return;
              label260:
              if (KitchenInfoActivity.this.currentUnit.equals(KitchenInfoActivity.this.getResources().getString(2131361901))) {
                KitchenInfoActivity.this.lastWeight = UtilTooth.kgToML(Float.parseFloat(KitchenInfoActivity.this.weight_et.getText().toString()));
              } else {
                KitchenInfoActivity.this.lastWeight = UtilTooth.gTog(Float.parseFloat(KitchenInfoActivity.this.weight_et.getText().toString()));
              }
            }
          }
        }
        else if (KitchenInfoActivity.this.needChage)
        {
          if (KitchenInfoActivity.this.selectNutrient != null) {
            KitchenInfoActivity.this.setViewDate(KitchenInfoActivity.this.selectNutrient, 0.0F);
          }
          KitchenInfoActivity.this.needChage = true;
        }
      }
    });
  }
  
  private void setViewDate(NutrientBo paramNutrientBo, float paramFloat)
  {
    if (paramNutrientBo != null)
    {
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientWater())) {
        break label1354;
      }
      if (paramFloat != 0.0F) {
        break label1328;
      }
      this.Water_excel_title.setText(paramNutrientBo.getNutrientWater());
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientEnerg())) {
        break label1393;
      }
      if (paramFloat != 0.0F) {
        break label1367;
      }
      this.Energ_excel_title.setText(paramNutrientBo.getNutrientEnerg());
      label58:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientProtein())) {
        break label1432;
      }
      if (paramFloat != 0.0F) {
        break label1406;
      }
      this.Protein_excel_title.setText(paramNutrientBo.getNutrientProtein());
      label85:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientLipidTot())) {
        break label1471;
      }
      if (paramFloat != 0.0F) {
        break label1445;
      }
      this.Lipid_excel_title.setText(paramNutrientBo.getNutrientLipidTot());
      label112:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientAsh())) {
        break label1510;
      }
      if (paramFloat != 0.0F) {
        break label1484;
      }
      this.Ash_excel_title.setText(paramNutrientBo.getNutrientAsh());
      label139:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientCarbohydrt())) {
        break label1549;
      }
      if (paramFloat != 0.0F) {
        break label1523;
      }
      this.Carbohydrt_excel_title.setText(paramNutrientBo.getNutrientCarbohydrt());
      label166:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientFiberTD())) {
        break label1588;
      }
      if (paramFloat != 0.0F) {
        break label1562;
      }
      this.Fiber_excel_title.setText(paramNutrientBo.getNutrientFiberTD());
      label193:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientSugarTot())) {
        break label1627;
      }
      if (paramFloat != 0.0F) {
        break label1601;
      }
      this.Sugar_excel_title.setText(paramNutrientBo.getNutrientSugarTot());
      label220:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientCalcium())) {
        break label1666;
      }
      if (paramFloat != 0.0F) {
        break label1640;
      }
      this.Calcium_excel_title.setText(paramNutrientBo.getNutrientCalcium());
      label247:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientIron())) {
        break label1705;
      }
      if (paramFloat != 0.0F) {
        break label1679;
      }
      this.Iron_excel_title.setText(paramNutrientBo.getNutrientIron());
      label274:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientMagnesium())) {
        break label1744;
      }
      if (paramFloat != 0.0F) {
        break label1718;
      }
      this.Magnesium_excel_title.setText(paramNutrientBo.getNutrientMagnesium());
      label301:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientPhosphorus())) {
        break label1783;
      }
      if (paramFloat != 0.0F) {
        break label1757;
      }
      this.Phosphorus_excel_title.setText(paramNutrientBo.getNutrientPhosphorus());
      label328:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientPotassium())) {
        break label1822;
      }
      if (paramFloat != 0.0F) {
        break label1796;
      }
      this.Potassium_excel_title.setText(paramNutrientBo.getNutrientPotassium());
      label355:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientSodium())) {
        break label1861;
      }
      if (paramFloat != 0.0F) {
        break label1835;
      }
      this.Sodium_excel_title.setText(paramNutrientBo.getNutrientSodium());
      label382:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientZinc())) {
        break label1900;
      }
      if (paramFloat != 0.0F) {
        break label1874;
      }
      this.Zinc_excel_title.setText(paramNutrientBo.getNutrientZinc());
      label409:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientCopper())) {
        break label1939;
      }
      if (paramFloat != 0.0F) {
        break label1913;
      }
      this.Copper_excel_title.setText(paramNutrientBo.getNutrientCopper());
      label436:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientManganese())) {
        break label1978;
      }
      if (paramFloat != 0.0F) {
        break label1952;
      }
      this.Manganese_excel_title.setText(paramNutrientBo.getNutrientManganese());
      label463:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientSelenium())) {
        break label2017;
      }
      if (paramFloat != 0.0F) {
        break label1991;
      }
      this.Selenium_excel_title.setText(paramNutrientBo.getNutrientSelenium());
      label490:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientVitc())) {
        break label2056;
      }
      if (paramFloat != 0.0F) {
        break label2030;
      }
      this.Vit_C_excel_title.setText(paramNutrientBo.getNutrientVitc());
      label517:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientThiamin())) {
        break label2095;
      }
      if (paramFloat != 0.0F) {
        break label2069;
      }
      this.Thiamin_excel_title.setText(paramNutrientBo.getNutrientThiamin());
      label544:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientRiboflavin())) {
        break label2134;
      }
      if (paramFloat != 0.0F) {
        break label2108;
      }
      this.Riboflavin_excel_title.setText(paramNutrientBo.getNutrientRiboflavin());
      label571:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientNiacin())) {
        break label2173;
      }
      if (paramFloat != 0.0F) {
        break label2147;
      }
      this.Niacin_excel_title.setText(paramNutrientBo.getNutrientNiacin());
      label598:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientPantoAcid())) {
        break label2212;
      }
      if (paramFloat != 0.0F) {
        break label2186;
      }
      this.Panto_Acid_excel_title.setText(paramNutrientBo.getNutrientPantoAcid());
      label625:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientVitB6())) {
        break label2251;
      }
      if (paramFloat != 0.0F) {
        break label2225;
      }
      this.Vit_B6_excel_title.setText(paramNutrientBo.getNutrientVitB6());
      label652:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientFolateTot())) {
        break label2290;
      }
      if (paramFloat != 0.0F) {
        break label2264;
      }
      this.Folate_Tot_excel_title.setText(paramNutrientBo.getNutrientFolateTot());
      label679:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientFolicAcid())) {
        break label2329;
      }
      if (paramFloat != 0.0F) {
        break label2303;
      }
      this.Folic_Acid_excel_title.setText(paramNutrientBo.getNutrientFolicAcid());
      label706:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientFoodFolate())) {
        break label2368;
      }
      if (paramFloat != 0.0F) {
        break label2342;
      }
      this.Food_Folate_excel_title.setText(paramNutrientBo.getNutrientFoodFolate());
      label733:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientFolateDfe())) {
        break label2407;
      }
      if (paramFloat != 0.0F) {
        break label2381;
      }
      this.Folate_DFE_excel_title.setText(paramNutrientBo.getNutrientFolateDfe());
      label760:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientCholineTot())) {
        break label2446;
      }
      if (paramFloat != 0.0F) {
        break label2420;
      }
      this.Choline_Tot_excel_title.setText(paramNutrientBo.getNutrientCholineTot());
      label787:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientVitB12())) {
        break label2485;
      }
      if (paramFloat != 0.0F) {
        break label2459;
      }
      this.Vit_B12_excel_title.setText(paramNutrientBo.getNutrientVitB12());
      label814:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientVitAiu())) {
        break label2524;
      }
      if (paramFloat != 0.0F) {
        break label2498;
      }
      this.Vit_A_IU_excel_title.setText(paramNutrientBo.getNutrientVitAiu());
      label841:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientVitArae())) {
        break label2563;
      }
      if (paramFloat != 0.0F) {
        break label2537;
      }
      this.Vit_A_RAE_excel_title.setText(paramNutrientBo.getNutrientVitArae());
      label868:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientRetinol())) {
        break label2602;
      }
      if (paramFloat != 0.0F) {
        break label2576;
      }
      this.Retinol_excel_title.setText(paramNutrientBo.getNutrientRetinol());
      label895:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientAlphaCarot())) {
        break label2641;
      }
      if (paramFloat != 0.0F) {
        break label2615;
      }
      this.Alpha_Carot_excel_title.setText(paramNutrientBo.getNutrientAlphaCarot());
      label922:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientBetaCarot())) {
        break label2680;
      }
      if (paramFloat != 0.0F) {
        break label2654;
      }
      this.Beta_Carot_excel_title.setText(paramNutrientBo.getNutrientBetaCarot());
      label949:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientBetaCrypt())) {
        break label2719;
      }
      if (paramFloat != 0.0F) {
        break label2693;
      }
      this.Beta_Crypt_excel_title.setText(paramNutrientBo.getNutrientBetaCrypt());
      label976:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientLycopene())) {
        break label2758;
      }
      if (paramFloat != 0.0F) {
        break label2732;
      }
      this.Lycopene_excel_title.setText(paramNutrientBo.getNutrientLycopene());
      label1003:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientLutZea())) {
        break label2797;
      }
      if (paramFloat != 0.0F) {
        break label2771;
      }
      this.LutZea_excel_title.setText(paramNutrientBo.getNutrientLutZea());
      label1030:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientVite())) {
        break label2836;
      }
      if (paramFloat != 0.0F) {
        break label2810;
      }
      this.Vit_E_excel_title.setText(paramNutrientBo.getNutrientVite());
      label1057:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientVitd())) {
        break label2875;
      }
      if (paramFloat != 0.0F) {
        break label2849;
      }
      this.Vit_D_excel_title.setText(paramNutrientBo.getNutrientVitd());
      label1084:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientVitDiu())) {
        break label2914;
      }
      if (paramFloat != 0.0F) {
        break label2888;
      }
      this.Vit_D_IU_excel_title.setText(paramNutrientBo.getNutrientVitDiu());
      label1111:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientVitK())) {
        break label2953;
      }
      if (paramFloat != 0.0F) {
        break label2927;
      }
      this.Vit_K_excel_title.setText(paramNutrientBo.getNutrientVitK());
      label1138:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientFaSat())) {
        break label2992;
      }
      if (paramFloat != 0.0F) {
        break label2966;
      }
      this.FA_Sat_excel_title.setText(paramNutrientBo.getNutrientFaSat());
      label1165:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientFaMono())) {
        break label3031;
      }
      if (paramFloat != 0.0F) {
        break label3005;
      }
      this.FA_Mono_excel_title.setText(paramNutrientBo.getNutrientFaMono());
      label1192:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientFaPoly())) {
        break label3070;
      }
      if (paramFloat != 0.0F) {
        break label3044;
      }
      this.FA_Poly_excel_title.setText(paramNutrientBo.getNutrientFaPoly());
      label1219:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientCholestrl())) {
        break label3109;
      }
      if (paramFloat != 0.0F) {
        break label3083;
      }
      this.Cholesterol_excel_title.setText(paramNutrientBo.getNutrientCholestrl());
      label1246:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientGmWt1())) {
        break label3148;
      }
      if (paramFloat != 0.0F) {
        break label3122;
      }
      this.GmWt_1_excel_title.setText(paramNutrientBo.getNutrientGmWt1());
      label1273:
      if (TextUtils.isEmpty(paramNutrientBo.getNutrientGmWt2())) {
        break label3187;
      }
      if (paramFloat != 0.0F) {
        break label3161;
      }
      this.GmWt_2_excel_title.setText(paramNutrientBo.getNutrientGmWt2());
    }
    for (;;)
    {
      if (!TextUtils.isEmpty(paramNutrientBo.getNutrientRefusePct()))
      {
        if (paramFloat == 0.0F)
        {
          this.Refuse_Pct_excel_title.setText(paramNutrientBo.getNutrientRefusePct());
          return;
          label1328:
          this.Water_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientWater()))));
          break;
          label1354:
          this.Water_excel_title.setText("0.0");
          break;
          label1367:
          this.Energ_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientEnerg()))));
          break label58;
          label1393:
          this.Energ_excel_title.setText("0.0");
          break label58;
          label1406:
          this.Protein_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientProtein()))));
          break label85;
          label1432:
          this.Protein_excel_title.setText("0.0");
          break label85;
          label1445:
          this.Lipid_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientLipidTot()))));
          break label112;
          label1471:
          this.Lipid_excel_title.setText("0.0");
          break label112;
          label1484:
          this.Ash_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientAsh()))));
          break label139;
          label1510:
          this.Ash_excel_title.setText("0.0");
          break label139;
          label1523:
          this.Carbohydrt_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientCarbohydrt()))));
          break label166;
          label1549:
          this.Carbohydrt_excel_title.setText("0.0");
          break label166;
          label1562:
          this.Fiber_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientFiberTD()))));
          break label193;
          label1588:
          this.Fiber_excel_title.setText("0.0");
          break label193;
          label1601:
          this.Sugar_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientSugarTot()))));
          break label220;
          label1627:
          this.Sugar_excel_title.setText("0.0");
          break label220;
          label1640:
          this.Calcium_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientCalcium()))));
          break label247;
          label1666:
          this.Calcium_excel_title.setText("0.0");
          break label247;
          label1679:
          this.Iron_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientIron()))));
          break label274;
          label1705:
          this.Iron_excel_title.setText("0.0");
          break label274;
          label1718:
          this.Magnesium_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientMagnesium()))));
          break label301;
          label1744:
          this.Magnesium_excel_title.setText("0.0");
          break label301;
          label1757:
          this.Phosphorus_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientPhosphorus()))));
          break label328;
          label1783:
          this.Phosphorus_excel_title.setText("0.0");
          break label328;
          label1796:
          this.Potassium_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientPotassium()))));
          break label355;
          label1822:
          this.Potassium_excel_title.setText("0.0");
          break label355;
          label1835:
          this.Sodium_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientSodium()))));
          break label382;
          label1861:
          this.Sodium_excel_title.setText("0.0");
          break label382;
          label1874:
          this.Zinc_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientZinc()))));
          break label409;
          label1900:
          this.Zinc_excel_title.setText("0.0");
          break label409;
          label1913:
          this.Copper_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientCopper()))));
          break label436;
          label1939:
          this.Copper_excel_title.setText("0.0");
          break label436;
          label1952:
          this.Manganese_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientManganese()))));
          break label463;
          label1978:
          this.Manganese_excel_title.setText("0.0");
          break label463;
          label1991:
          this.Selenium_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientSelenium()))));
          break label490;
          label2017:
          this.Selenium_excel_title.setText("0.0");
          break label490;
          label2030:
          this.Vit_C_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientVitc()))));
          break label517;
          label2056:
          this.Vit_C_excel_title.setText("0.0");
          break label517;
          label2069:
          this.Thiamin_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientThiamin()))));
          break label544;
          label2095:
          this.Thiamin_excel_title.setText("0.0");
          break label544;
          label2108:
          this.Riboflavin_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientRiboflavin()))));
          break label571;
          label2134:
          this.Riboflavin_excel_title.setText("0.0");
          break label571;
          label2147:
          this.Niacin_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientNiacin()))));
          break label598;
          label2173:
          this.Niacin_excel_title.setText("0.0");
          break label598;
          label2186:
          this.Panto_Acid_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientPantoAcid()))));
          break label625;
          label2212:
          this.Panto_Acid_excel_title.setText("0.0");
          break label625;
          label2225:
          this.Vit_B6_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientVitB6()))));
          break label652;
          label2251:
          this.Vit_B6_excel_title.setText("0.0");
          break label652;
          label2264:
          this.Folate_Tot_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientFolateTot()))));
          break label679;
          label2290:
          this.Folate_Tot_excel_title.setText("0.0");
          break label679;
          label2303:
          this.Folic_Acid_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientFolicAcid()))));
          break label706;
          label2329:
          this.Folic_Acid_excel_title.setText("0.0");
          break label706;
          label2342:
          this.Food_Folate_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientFoodFolate()))));
          break label733;
          label2368:
          this.Food_Folate_excel_title.setText("0.0");
          break label733;
          label2381:
          this.Folate_DFE_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientFolateDfe()))));
          break label760;
          label2407:
          this.Folate_DFE_excel_title.setText("0.0");
          break label760;
          label2420:
          this.Choline_Tot_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientCholineTot()))));
          break label787;
          label2446:
          this.Choline_Tot_excel_title.setText("0.0");
          break label787;
          label2459:
          this.Vit_B12_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientVitB12()))));
          break label814;
          label2485:
          this.Vit_B12_excel_title.setText("0.0");
          break label814;
          label2498:
          this.Vit_A_IU_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientVitAiu()))));
          break label841;
          label2524:
          this.Vit_A_IU_excel_title.setText("0.0");
          break label841;
          label2537:
          this.Vit_A_RAE_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientVitArae()))));
          break label868;
          label2563:
          this.Vit_A_RAE_excel_title.setText("0.0");
          break label868;
          label2576:
          this.Retinol_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientRetinol()))));
          break label895;
          label2602:
          this.Retinol_excel_title.setText("0.0");
          break label895;
          label2615:
          this.Alpha_Carot_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientAlphaCarot()))));
          break label922;
          label2641:
          this.Alpha_Carot_excel_title.setText("0.0");
          break label922;
          label2654:
          this.Beta_Carot_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientBetaCarot()))));
          break label949;
          label2680:
          this.Beta_Carot_excel_title.setText("0.0");
          break label949;
          label2693:
          this.Beta_Crypt_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientBetaCrypt()))));
          break label976;
          label2719:
          this.Beta_Crypt_excel_title.setText("0.0");
          break label976;
          label2732:
          this.Lycopene_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientLycopene()))));
          break label1003;
          label2758:
          this.Lycopene_excel_title.setText("0.0");
          break label1003;
          label2771:
          this.LutZea_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientLutZea()))));
          break label1030;
          label2797:
          this.LutZea_excel_title.setText("0.0");
          break label1030;
          label2810:
          this.Vit_E_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientVite()))));
          break label1057;
          label2836:
          this.Vit_E_excel_title.setText("0.0");
          break label1057;
          label2849:
          this.Vit_D_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientVitd()))));
          break label1084;
          label2875:
          this.Vit_D_excel_title.setText("0.0");
          break label1084;
          label2888:
          this.Vit_D_IU_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientVitDiu()))));
          break label1111;
          label2914:
          this.Vit_D_IU_excel_title.setText("0.0");
          break label1111;
          label2927:
          this.Vit_K_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientVitK()))));
          break label1138;
          label2953:
          this.Vit_K_excel_title.setText("0.0");
          break label1138;
          label2966:
          this.FA_Sat_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientFaSat()))));
          break label1165;
          label2992:
          this.FA_Sat_excel_title.setText("0.0");
          break label1165;
          label3005:
          this.FA_Mono_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientFaMono()))));
          break label1192;
          label3031:
          this.FA_Mono_excel_title.setText("0.0");
          break label1192;
          label3044:
          this.FA_Poly_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientFaPoly()))));
          break label1219;
          label3070:
          this.FA_Poly_excel_title.setText("0.0");
          break label1219;
          label3083:
          this.Cholesterol_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientCholestrl()))));
          break label1246;
          label3109:
          this.Cholesterol_excel_title.setText("0.0");
          break label1246;
          label3122:
          this.GmWt_1_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientGmWt1()))));
          break label1273;
          label3148:
          this.GmWt_1_excel_title.setText("0.0");
          break label1273;
          label3161:
          this.GmWt_2_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientGmWt2()))));
          continue;
          label3187:
          this.GmWt_2_excel_title.setText("0.0");
          continue;
        }
        this.Refuse_Pct_excel_title.setText(UtilTooth.keep2Point(0.01F * (paramFloat * Float.parseFloat(paramNutrientBo.getNutrientRefusePct()))));
        return;
      }
    }
    this.Refuse_Pct_excel_title.setText("0.0");
  }
  
  private void showSelectedItem(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {}
    while (paramString.equals(this.currentUnit)) {
      return;
    }
    if (this.lastWeight > 0.0F)
    {
      this.needChage = false;
      if (!paramString.equals(getResources().getString(2131361900))) {
        break label70;
      }
      this.weight_et.setText(UtilTooth.kgToFloz(this.lastWeight));
    }
    for (;;)
    {
      this.currentUnit = paramString;
      return;
      label70:
      if (paramString.equals(getResources().getString(2131361893))) {
        this.weight_et.setText(UtilTooth.kgToLBoz(this.lastWeight));
      } else {
        this.weight_et.setText(UtilTooth.gTogS(this.lastWeight));
      }
    }
  }
  
  public void natureSelectComplete(NutrientBo paramNutrientBo)
  {
    if (paramNutrientBo != null)
    {
      this.search_et.setText(paramNutrientBo.getNutrientDesc());
      this.selectNutrient = paramNutrientBo;
      setViewDate(this.selectNutrient, 0.0F);
    }
  }
  
  public void onClickBack(View paramView)
  {
    finish();
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903055);
    initView();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 3) {
      ((NotificationManager)getSystemService("notification")).cancel(0);
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  protected void onResume()
  {
    super.onResume();
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\system\KitchenInfoActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */