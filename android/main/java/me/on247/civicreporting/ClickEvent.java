package me.on247.civicreporting;

import android.app.Activity;
import android.view.View;

/**
 * Created by Alonso L on 2/21/2015.
 */
public abstract class ClickEvent implements View.OnClickListener{
    Activity activity_src;
    ClickEvent(Activity activity){
        this.activity_src=activity;
    }
    abstract public void onClick(View v);
}
