package webservice;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;

/**
 * Created by alpha on 20/05/2018.
 */

public class WebServiceManager {

    public static  void getAction(String  uri){

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet(uri);
        String text = null;
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);

            HttpEntity entity = response.getEntity();

            //text = getASCIIContentFromEntity(entity);
            Log.e("test",entity.getContentType().toString());

        } catch (Exception e) {

            //System.out.print(e.toString());
            Log.e("test",e.toString());
        }
    }

    public static String getActionStop(){
     return  "stop";
    }

    public static String getActionPause(){
     return  "pause";
    }
}
