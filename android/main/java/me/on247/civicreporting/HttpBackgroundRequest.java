package me.on247.civicreporting;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Alonso L on 2/22/2015.
 */
public class HttpBackgroundRequest extends AsyncTask<String,Void,Void>
{
    HttpResponse res;
    public void setResponse(HttpResponse res){
        this.res=res;
    }
    @Override
    protected Void doInBackground(String... params) {
        AndroidHttpClient httpclient = AndroidHttpClient.newInstance("CivicReporting/0.1");
        HttpHost server = new HttpHost(params[0]);
        try {
            String encodedData = URLEncoder.encode(params[1], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final HttpGet request = new HttpGet(params[1]);
        BasicResponseHandler handler = new BasicResponseHandler() {
            @Override
            public String handleResponse(HttpResponse response) throws HttpResponseException, IOException {
                Log.d("HTTP:", response.getStatusLine().toString());
                setResponse(res);
                return super.handleResponse(response);
            }
        };
        try {
            httpclient.execute(server,request,handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate() {

    }

    protected Void onPostExecute() {
        Log.d("onPostxecute","OK");
        return null;
    }
}
