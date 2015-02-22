package me.on247.civicreporting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alonso L on 2/22/2015.
 */
public abstract class RadioEvent implements RadioGroup.OnCheckedChangeListener{
    JSONArray currentMenuData;
    Context appctx;
    Activity activity;
    RadioEvent(){
    }
    RadioEvent(Activity l){
        this.activity=l;
    }
    public void setContext(Context ctx){
        this.appctx=ctx;
    }
    public void updateMenu(RadioGroup group){
        for(int i=0;i<currentMenuData.length();i++){
            JSONObject currentItem= null;
            try {
                currentItem = currentMenuData.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RadioButton newOption=new RadioButton(appctx);
            try {
                newOption.setText(currentItem.getString("cat_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            newOption.setTextColor(Color.BLACK);
            newOption.setTextSize(16.0F);
            newOption.setPadding(40,10,0,0);
            group.addView(newOption);
        }
        /*

        */
    }
    public void setMenu(JSONArray menuData){
        this.currentMenuData=menuData;
    }
    public void clearMenu(RadioGroup v){
        v.removeAllViews();
    }
    abstract public void onCheckedChanged(RadioGroup group, int checkedId);
}
