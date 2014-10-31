package com.telosoft.ligaroba.consumerwatch.custom;

import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by root on 10/25/14.
 */
public class GetterSetterClass {
    private Drawable image;

    private int qauntity;
   private int id;
    private String supername;
    private String pname;
    private int logo;
    private int prices;
    private int superid;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSuperid() {
        return superid;
    }

    public void setSuperid(int superid) {
        this.superid = superid;
    }

    public String getSupername() {
        return supername;
    }

    public void setSupername(String supername) {
        this.supername = supername;
    }

    public int getLogo() {
        return logo;
    }

    public int getPrices() {
        return prices;
    }

    public void setPrices (int prices) {
        this.prices = prices;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public int getQauntity() {
        return qauntity;
    }

    public void setQauntity(int qauntity) {
        this.qauntity = qauntity;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

  /* public double [] store(){
       double [] prize = getQauntity()*getPrices();
   }*/

    public double budget(){

        double budget=0.0;

        budget = budget +(getPrices() * getQauntity());
        Log.e("Budget :  ", " budget= " +budget  + " prices: = " + getPrices() + " qaunty : " + getQauntity());
        return budget;
    }
}
