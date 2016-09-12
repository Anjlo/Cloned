package com.lefu.es.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NutrientAtrrBo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String attrName;
  private String attrValue;
  
  public NutrientAtrrBo(String paramString1, String paramString2)
  {
    this.attrName = paramString1;
    this.attrValue = paramString2;
  }
  
  public static List<NutrientAtrrBo> formatNutrientAtrrBo(NutrientBo paramNutrientBo)
  {
    if (paramNutrientBo == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    NutrientAtrrBo localNutrientAtrrBo1 = new NutrientAtrrBo("Water(g)", paramNutrientBo.getNutrientWater());
    NutrientAtrrBo localNutrientAtrrBo2 = new NutrientAtrrBo("Energ(kcal)", paramNutrientBo.getNutrientEnerg());
    NutrientAtrrBo localNutrientAtrrBo3 = new NutrientAtrrBo("Protein(g)", paramNutrientBo.getNutrientProtein());
    NutrientAtrrBo localNutrientAtrrBo4 = new NutrientAtrrBo("Ash(g)", paramNutrientBo.getNutrientAsh());
    NutrientAtrrBo localNutrientAtrrBo5 = new NutrientAtrrBo("Carbohydrt(g)", paramNutrientBo.getNutrientCarbohydrt());
    NutrientAtrrBo localNutrientAtrrBo6 = new NutrientAtrrBo("Fiber_TD(g)", paramNutrientBo.getNutrientFiberTD());
    NutrientAtrrBo localNutrientAtrrBo7 = new NutrientAtrrBo("Sugar_Tot(g)", paramNutrientBo.getNutrientSugarTot());
    NutrientAtrrBo localNutrientAtrrBo8 = new NutrientAtrrBo("Calcium(mg)", paramNutrientBo.getNutrientCalcium());
    NutrientAtrrBo localNutrientAtrrBo9 = new NutrientAtrrBo("Iron(mg)", paramNutrientBo.getNutrientIron());
    NutrientAtrrBo localNutrientAtrrBo10 = new NutrientAtrrBo("Magnesium(mg)", paramNutrientBo.getNutrientMagnesium());
    NutrientAtrrBo localNutrientAtrrBo11 = new NutrientAtrrBo("Phosphorus(mg)", paramNutrientBo.getNutrientPhosphorus());
    NutrientAtrrBo localNutrientAtrrBo12 = new NutrientAtrrBo("Potassium(mg)", paramNutrientBo.getNutrientPotassium());
    NutrientAtrrBo localNutrientAtrrBo13 = new NutrientAtrrBo("Sodium(mg)", paramNutrientBo.getNutrientSodium());
    NutrientAtrrBo localNutrientAtrrBo14 = new NutrientAtrrBo("Zinc(mg)", paramNutrientBo.getNutrientZinc());
    NutrientAtrrBo localNutrientAtrrBo15 = new NutrientAtrrBo("Copper(mg)", paramNutrientBo.getNutrientCopper());
    NutrientAtrrBo localNutrientAtrrBo16 = new NutrientAtrrBo("Manganese(mg)", paramNutrientBo.getNutrientManganese());
    NutrientAtrrBo localNutrientAtrrBo17 = new NutrientAtrrBo("Selenium(µg)", paramNutrientBo.getNutrientSelenium());
    NutrientAtrrBo localNutrientAtrrBo18 = new NutrientAtrrBo("Vit_C(mg)", paramNutrientBo.getNutrientVitc());
    NutrientAtrrBo localNutrientAtrrBo19 = new NutrientAtrrBo("Thiamin(mg)", paramNutrientBo.getNutrientThiamin());
    NutrientAtrrBo localNutrientAtrrBo20 = new NutrientAtrrBo("Riboflavin(mg)", paramNutrientBo.getNutrientRiboflavin());
    NutrientAtrrBo localNutrientAtrrBo21 = new NutrientAtrrBo("Niacin(mg)", paramNutrientBo.getNutrientNiacin());
    NutrientAtrrBo localNutrientAtrrBo22 = new NutrientAtrrBo("Panto_Acid(mg)", paramNutrientBo.getNutrientPantoAcid());
    NutrientAtrrBo localNutrientAtrrBo23 = new NutrientAtrrBo("Vit_B6(mg)", paramNutrientBo.getNutrientVitB6());
    NutrientAtrrBo localNutrientAtrrBo24 = new NutrientAtrrBo("Folate_Tot(µg)", paramNutrientBo.getNutrientFolateTot());
    NutrientAtrrBo localNutrientAtrrBo25 = new NutrientAtrrBo("Folic_Acid(µg)", paramNutrientBo.getNutrientFolicAcid());
    NutrientAtrrBo localNutrientAtrrBo26 = new NutrientAtrrBo("Food_Folate(µg)", paramNutrientBo.getNutrientFoodFolate());
    NutrientAtrrBo localNutrientAtrrBo27 = new NutrientAtrrBo("Folate_DFE(µg)", paramNutrientBo.getNutrientFolateDfe());
    NutrientAtrrBo localNutrientAtrrBo28 = new NutrientAtrrBo("Choline_Tot(µg)", paramNutrientBo.getNutrientCholineTot());
    NutrientAtrrBo localNutrientAtrrBo29 = new NutrientAtrrBo("Vit_B12(µg)", paramNutrientBo.getNutrientVitB12());
    NutrientAtrrBo localNutrientAtrrBo30 = new NutrientAtrrBo("Vit_A_IU(µg)", paramNutrientBo.getNutrientVitAiu());
    NutrientAtrrBo localNutrientAtrrBo31 = new NutrientAtrrBo("Vit_A_RAE(µg)", paramNutrientBo.getNutrientVitArae());
    NutrientAtrrBo localNutrientAtrrBo32 = new NutrientAtrrBo("Retinol(µg)", paramNutrientBo.getNutrientRetinol());
    NutrientAtrrBo localNutrientAtrrBo33 = new NutrientAtrrBo("Alpha_Carot(µg)", paramNutrientBo.getNutrientAlphaCarot());
    NutrientAtrrBo localNutrientAtrrBo34 = new NutrientAtrrBo("Beta_Carot(µg)", paramNutrientBo.getNutrientBetaCarot());
    NutrientAtrrBo localNutrientAtrrBo35 = new NutrientAtrrBo("Beta_Crypt(µg)", paramNutrientBo.getNutrientBetaCrypt());
    NutrientAtrrBo localNutrientAtrrBo36 = new NutrientAtrrBo("Lycopene(µg)", paramNutrientBo.getNutrientLycopene());
    NutrientAtrrBo localNutrientAtrrBo37 = new NutrientAtrrBo("Lut+Zea(µg)", paramNutrientBo.getNutrientLutZea());
    NutrientAtrrBo localNutrientAtrrBo38 = new NutrientAtrrBo("Vit_E(µg)", paramNutrientBo.getNutrientVite());
    NutrientAtrrBo localNutrientAtrrBo39 = new NutrientAtrrBo("Vit_D(µg)", paramNutrientBo.getNutrientVitd());
    NutrientAtrrBo localNutrientAtrrBo40 = new NutrientAtrrBo("Vit_D_IU(µg)", paramNutrientBo.getNutrientVitDiu());
    NutrientAtrrBo localNutrientAtrrBo41 = new NutrientAtrrBo("Vit_K(µg)", paramNutrientBo.getNutrientVitK());
    NutrientAtrrBo localNutrientAtrrBo42 = new NutrientAtrrBo("FA_Sat(g)", paramNutrientBo.getNutrientFaSat());
    NutrientAtrrBo localNutrientAtrrBo43 = new NutrientAtrrBo("FA_Mono(g)", paramNutrientBo.getNutrientFaMono());
    NutrientAtrrBo localNutrientAtrrBo44 = new NutrientAtrrBo("FA_Poly(g)", paramNutrientBo.getNutrientFaPoly());
    NutrientAtrrBo localNutrientAtrrBo45 = new NutrientAtrrBo("Cholestrl(mg)", paramNutrientBo.getNutrientCholestrl());
    NutrientAtrrBo localNutrientAtrrBo46 = new NutrientAtrrBo("GmWt_1", paramNutrientBo.getNutrientGmWt1());
    NutrientAtrrBo localNutrientAtrrBo47 = new NutrientAtrrBo("GmWt_2", paramNutrientBo.getNutrientGmWt2());
    NutrientAtrrBo localNutrientAtrrBo48 = new NutrientAtrrBo("Refuse_Pct", paramNutrientBo.getNutrientRefusePct());
    localArrayList.add(localNutrientAtrrBo1);
    localArrayList.add(localNutrientAtrrBo2);
    localArrayList.add(localNutrientAtrrBo3);
    localArrayList.add(localNutrientAtrrBo4);
    localArrayList.add(localNutrientAtrrBo5);
    localArrayList.add(localNutrientAtrrBo6);
    localArrayList.add(localNutrientAtrrBo7);
    localArrayList.add(localNutrientAtrrBo8);
    localArrayList.add(localNutrientAtrrBo9);
    localArrayList.add(localNutrientAtrrBo10);
    localArrayList.add(localNutrientAtrrBo11);
    localArrayList.add(localNutrientAtrrBo12);
    localArrayList.add(localNutrientAtrrBo13);
    localArrayList.add(localNutrientAtrrBo14);
    localArrayList.add(localNutrientAtrrBo15);
    localArrayList.add(localNutrientAtrrBo16);
    localArrayList.add(localNutrientAtrrBo17);
    localArrayList.add(localNutrientAtrrBo18);
    localArrayList.add(localNutrientAtrrBo19);
    localArrayList.add(localNutrientAtrrBo20);
    localArrayList.add(localNutrientAtrrBo21);
    localArrayList.add(localNutrientAtrrBo22);
    localArrayList.add(localNutrientAtrrBo23);
    localArrayList.add(localNutrientAtrrBo24);
    localArrayList.add(localNutrientAtrrBo25);
    localArrayList.add(localNutrientAtrrBo26);
    localArrayList.add(localNutrientAtrrBo27);
    localArrayList.add(localNutrientAtrrBo28);
    localArrayList.add(localNutrientAtrrBo29);
    localArrayList.add(localNutrientAtrrBo30);
    localArrayList.add(localNutrientAtrrBo31);
    localArrayList.add(localNutrientAtrrBo32);
    localArrayList.add(localNutrientAtrrBo33);
    localArrayList.add(localNutrientAtrrBo34);
    localArrayList.add(localNutrientAtrrBo35);
    localArrayList.add(localNutrientAtrrBo36);
    localArrayList.add(localNutrientAtrrBo37);
    localArrayList.add(localNutrientAtrrBo38);
    localArrayList.add(localNutrientAtrrBo39);
    localArrayList.add(localNutrientAtrrBo40);
    localArrayList.add(localNutrientAtrrBo41);
    localArrayList.add(localNutrientAtrrBo42);
    localArrayList.add(localNutrientAtrrBo43);
    localArrayList.add(localNutrientAtrrBo44);
    localArrayList.add(localNutrientAtrrBo45);
    localArrayList.add(localNutrientAtrrBo46);
    localArrayList.add(localNutrientAtrrBo47);
    localArrayList.add(localNutrientAtrrBo48);
    return localArrayList;
  }
  
  public String getAttrName()
  {
    return this.attrName;
  }
  
  public String getAttrValue()
  {
    return this.attrValue;
  }
  
  public void setAttrName(String paramString)
  {
    this.attrName = paramString;
  }
  
  public void setAttrValue(String paramString)
  {
    this.attrValue = paramString;
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\lefu\es\entity\NutrientAtrrBo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */