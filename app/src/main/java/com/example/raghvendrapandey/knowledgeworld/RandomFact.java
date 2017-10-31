package com.example.raghvendrapandey.knowledgeworld;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RandomFact extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {
    private Toolbar mToolbar;
    private Spinner spinner;
    private Button mResult,SActivity;
    private TextView showResult;
    private String item="";
    private String url="";
    HttpURLConnection connection=null;
    BufferedReader reader=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_fact);
        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Random fact");

       //final FetchApi data=new FetchApi();
         spinner = (Spinner) findViewById(R.id.spinner1);
        mResult=(Button)findViewById(R.id.sRes);
        SActivity=(Button)findViewById(R.id.sf);
        showResult=(TextView)findViewById(R.id.result);
        showResult.setVisibility(View.INVISIBLE);
        showResult.setMovementMethod(new ScrollingMovementMethod());


        spinner.setOnItemSelectedListener(this);
        SActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RActivity = new Intent(RandomFact.this, SpecificFact.class);
                startActivity(RActivity);
                finish();

            }
        });

        mResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(item.equals("Select One")){
                    mResult.setText("Know the facts");
                    Toast.makeText(RandomFact.this, "Please select one category", Toast.LENGTH_LONG).show();
                }
                if(item.equals("Trivia")){
                    url="http://numbersapi.com/random/trivia";
                    mResult.setText("do you want to know more about Trivia?");

                }
                if(item.equals("Math")){
                    url="http://numbersapi.com/random/math";
                    mResult.setText("do you want to know more about math?");
                }
                if(item.equals("Date")){
                    url="http://numbersapi.com/random/date";
                    mResult.setText("do you want to know more about date?");
                }
                if(item.equals("Year")){
                    url="http://numbersapi.com/random/year";
                    mResult.setText("do you want to know more about year?");
                }

                if(isNetworkAvailable())
               new FetchApi().execute(url);
                else
                    Toast.makeText(RandomFact.this, "Network is not available", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       item = parent.getItemAtPosition(position).toString();
        mResult.setText("Know the fact about "+item);
        showResult.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public class FetchApi extends AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... params) {
            try{URL myUrl=new URL(params[0]);
                connection= (HttpURLConnection) myUrl.openConnection();
                connection.connect();
                InputStream stream=connection.getInputStream();
                reader=new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer=new StringBuffer();
                String line="";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }
                Log.d("ajeet", buffer.toString());
                return buffer.toString();
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            finally {
                if(connection!=null){
                    connection.disconnect();
                }
                try{
                    if(reader!=null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null){
            super.onPostExecute(s);
            showResult.setVisibility(View.VISIBLE);
            showResult.setText(s);
            }
        }
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }
    boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
