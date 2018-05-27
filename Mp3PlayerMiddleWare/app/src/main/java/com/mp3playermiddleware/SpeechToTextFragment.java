package com.mp3playermiddleware;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;
import mp3.Config;
import mp3playermiddleware.ActionMusic;

import static android.app.Activity.RESULT_OK;

/**
 * Created by alpha on 27/04/2018.
 */



public class SpeechToTextFragment extends android.support.v4.app.Fragment {

    private TextView mTextMessage;
    private TextView selectedItem;
    private static final int REQ_CODE_SPEECH_INPUT= 100;

    private String action ;
    private String music ;
    private  ActionMusic am;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewSTT = inflater.inflate(R.layout.fragment_speach_to_text, container, false);
        selectedItem = (TextView)getActivity().findViewById(R.id.textViewSelectedItem);
        mTextMessage = (TextView) viewSTT.findViewById(R.id.message);

        am = new ActionMusic();
        startVoiceInput();

        return viewSTT;
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Bonjour, Parler pour lancer la musique");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mTextMessage.setText(result.get(0));
                    //new  LongRunningGetIO().execute();
                    //mTextMessage.setText(action);
                    //selectedItem.setText(am.getMusic());
                   // Log.e("test", am.getMusic());

                }
                break;
            }

        }
    }

    private  class LongRunningGetIO extends AsyncTask<Void, Void, String> {

       // List<String> categories;

        protected void getAction(HttpEntity entity) throws IllegalStateException, IOException, JSONException {
            InputStream in = entity.getContent();

            BufferedReader rd = new BufferedReader(new InputStreamReader(in));

            StringBuffer out = new StringBuffer();

            String line ="";
            while ((line = rd.readLine()) != null){
                out.append(line);
            }

            //categories = new ArrayList<String>();
            //Afficher la réponse dans la console
            System.out.println(out.toString());
            JSONArray array = new JSONArray(out.toString());
            //for(int i = 0; i< array.length(); i++){
                JSONObject object = array.getJSONObject(0);
                //action = object.getString("action");
                am.setAction(object.getString("action"));
                if (!object.isNull("music"))
                    am.setMusic(object.getString("music"));
                //else  music = "";

                /*Log.e("OK",action);
                Log.e("OK",music);*/
           //}
        }

        @Override
        protected String doInBackground(Void... voids) {
            String url = "http://"+ Config.ip_server +":8080/AnalyseurRequete/getActionAndObject/"+mTextMessage.getText();
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(url.replace(" ","%20"));
            httpGet.addHeader("Accept","application/json");
            try {

                HttpResponse response = httpClient.execute(httpGet, localContext);

                HttpEntity entity = response.getEntity();


                getAction(entity);

                //selectedItem.setText(music);


            } catch (Exception e) {

                //return e.getLocalizedMessage();
                Log.e("test",e.toString());
            }
            return "";
        }
    }
}
