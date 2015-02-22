package me.on247.civicreporting;

import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.http.AndroidHttpClient;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Incident extends ActionBarActivity {
    String menusString;
    JSONObject menuData;
    LocationManager locationm;
    Location currentLoc;
    String category;
    String currentPath="" ;

    ClickEvent sendReport= new ClickEvent(this) {
        @Override
        public void onClick(View v) {
             JSONObject datos=new JSONObject();
            RadioGroup selectionMenu=(RadioGroup)findViewById(R.id.report_options);
            int id = selectionMenu.getCheckedRadioButtonId();
            RadioButton selection = (RadioButton)selectionMenu.findViewById(id);
            String detalles=(String)selection.getText();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            String timestamp=df.format(new Date());
            try {
                datos.put("lat", currentLoc.getLatitude());
                datos.put("lon", currentLoc.getLongitude());
                datos.put("detalle", detalles);
                datos.put("timestamp",timestamp);
                datos.put("tema",category);
                new HttpBackgroundRequest().execute("www.google.com","/");
            } catch (JSONException e) {
                e.printStackTrace();
            }


    Log.d("Report/data" ,datos.toString());
}
    };
    LocationEvent locationListener=new LocationEvent<Incident>(this){

        @Override
        public void onLocationChanged(Location location) {
           double lat=location.getLatitude();
           double lon=location.getLongitude();
           WebView webView = (WebView) findViewById(R.id.webView);
           String mapurl="https://www.google.com.mx/maps/@";
           mapurl+=String.valueOf(lat)+",";
           mapurl+=String.valueOf(lon)+",18z";
           Log.d("Maps/LocationChanged",mapurl);
           webView.loadUrl(mapurl);
           Incident incidentInstance=(Incident)this.activity;
            incidentInstance.currentLoc=location;
;        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("MAPS", provider);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    RadioEvent optionSelectHandler=new RadioEvent(this) {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            JSONObject menuSelected= null;
            RadioButton btn=(RadioButton)group.findViewById(checkedId);
            int index=group.indexOfChild(btn);
            try {
                menuSelected = this.currentMenuData.getJSONObject(index);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try{
                if(currentMenuData.getJSONObject(0).has("is_root")){
                    Incident incidentInstance=(Incident)activity;
                    RadioButton selectionItem=(RadioButton)group.getChildAt(index);
                    incidentInstance.category=(String)selectionItem.getText();
                }
                if(menuSelected.getJSONArray("cat_content")!=null){
                    this.setMenu(menuSelected.getJSONArray("cat_content"));
                    this.clearMenu(group);
                    this.updateMenu(group);
                    Incident incidentInstance=(Incident)activity;
                    RadioButton selectionItem=(RadioButton)group.getChildAt(index);
                    incidentInstance.currentPath+=index+"|";
                    Log.d("Menu:",incidentInstance.currentPath);
                }

            }
            catch(Exception e){
                if(menuSelected.has("report_send")||!menuSelected.has("cat_name")){
                    Button btnConfirm=(Button)findViewById(R.id.btn_confirm);
                    btnConfirm.setEnabled(true);
                }
            }

        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident);
        InputStream jsonInput = getResources().openRawResource(R.raw.menus);
        try {
            int sizeOfJSONFile = jsonInput.available();
            byte[] rawdata = new byte[sizeOfJSONFile];
            jsonInput.read(rawdata);
            jsonInput.close();
            menusString = new String(rawdata, "UTF-8");
            try {
                menuData = new JSONObject(menusString);
                generateMainMenu();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        locationm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria c=new Criteria();
        locationm.requestSingleUpdate(c,locationListener,null);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://maps.google.com/maps?~~");
        Button button = (Button)findViewById(R.id.btn_confirm);
       button.setOnClickListener(sendReport);



    }
    public void generateMainMenu(){
        JSONArray report_categories = null;
        RadioGroup optionGroup=(RadioGroup)findViewById(R.id.report_options);
        try {
            try {
                report_categories = menuData.getJSONArray("categories");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i=0;i<report_categories.length();i++){
                JSONObject selectionItem=report_categories.getJSONObject(i);
                RadioButton newOption=new RadioButton(this.getBaseContext());
                newOption.setText(selectionItem.getString("cat_name"));
                newOption.setTextColor(Color.BLACK);
                newOption.setTextSize(16.0F);
                newOption.setPadding(40, 10, 0, 0);
                optionGroup.addView(newOption);
                optionSelectHandler.setMenu(report_categories);
                optionGroup.setOnCheckedChangeListener(optionSelectHandler);
                optionSelectHandler.setContext(this.getApplicationContext());
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}

