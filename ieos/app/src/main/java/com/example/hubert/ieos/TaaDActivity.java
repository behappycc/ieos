package com.example.hubert.ieos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.os.AsyncTask;

public class TaaDActivity extends Activity {
    private TextView txtSpeechResult;
    private TextView txtServerResult;
    private String speechResult;
    public String httpResponseResult;
    private Button btnStreaming;
    public String webcamturnon = "webcam turn on";
    public String webcamturnoff = "webcam turn off";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taad);

        setupViewComponent();
        showResult();

        //SendResultTask sendResultTask = new SendResultTask();
        //sendResultTask.execute();
    }

    private Button.OnClickListener btnStreamingOnClick = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent();
            intent.setClass(TaaDActivity.this, StreamingActivity.class);
            startActivity(intent);
        }
    };

    private void setupViewComponent() {
        txtSpeechResult = (TextView) findViewById(R.id.txtSpeechResult);
        txtServerResult = (TextView) findViewById(R.id.txtServerResult);
        btnStreaming = (Button) findViewById(R.id.btnStreaming);

        btnStreaming.setOnClickListener(btnStreamingOnClick);
    }

    private void showResult() {
        Bundle bundle = getIntent().getExtras();
        String temp = bundle.getString("speechResult");
        txtSpeechResult.setText(temp);
        speechResult = temp;
        if(temp.equals(webcamturnon)){
            Intent intent = new Intent();
            intent.setClass(TaaDActivity.this, StreamingActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent();
            intent.setClass(TaaDActivity.this, SpeechActivity.class);
            startActivity(intent);
        }
    }

    private class SendResultTask extends AsyncTask<Object, Integer, Long> {
        public SendResultTask(

        ) {

        }

        protected Long doInBackground(Object... abc) {
            HttpClient httpClient = new DefaultHttpClient();
            // replace with your url
            HttpPost httpPost = new HttpPost("http://140.112.42.145:2012/login");

            //Post Data
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
            nameValuePair.add(new BasicNameValuePair("speechResult", speechResult));


            //Encoding POST data
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            } catch (UnsupportedEncodingException e) {
                // log exception
                e.printStackTrace();
            }

            //making POST request.
            try {
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                // write response to log
                Log.d("Http Post Response:", result);
            } catch (ClientProtocolException e) {
                // Log exception
                e.printStackTrace();
            } catch (IOException e) {
                // Log exception
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
            if (httpResponseResult == null) {
                //Intent intent = new Intent();
                //intent.setClass(LoginActivity.this, SpeechActivity.class);
                //startActivity(intent);
            }

            }
        }
    }


