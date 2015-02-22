package me.on247.civicreporting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends Activity{
    OnClickListener onClick_handler = new ClickEvent(this){
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btn_reports){
               Intent reportsIntent=new Intent(this.activity_src,Reports.class);
               startActivity(reportsIntent);
            }
            if(v.getId()==R.id.btn_myreports){
                Intent myReportsIntent=new Intent(this.activity_src,MyReports.class);
                startActivity(myReportsIntent);

            }
            if(v.getId()==R.id.btn_incident){
                Intent myReportsIntent=new Intent(this.activity_src,Incident.class);
                startActivity(myReportsIntent);
            }
            if(v.getId()==R.id.btn_map){
                Intent myReportsIntent=new Intent(this.activity_src,Map.class);
                startActivity(myReportsIntent);
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState)  {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnIncident=(Button)findViewById(R.id.btn_incident);
        Button btnReports=(Button)findViewById(R.id.btn_reports);
        Button btnMyReports=(Button)findViewById(R.id.btn_myreports);
        Button btnMap=(Button)findViewById(R.id.btn_map);
        btnIncident.setOnClickListener(onClick_handler);
        btnReports.setOnClickListener(onClick_handler);
        btnMyReports.setOnClickListener(onClick_handler);
        btnIncident.setOnClickListener(onClick_handler);
    }
}
