package com.example.raghvendrapandey.knowledgeworld;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class SpecificFact extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar sToolbar;
    private Spinner spinner;
    private Button sResult, randomActivity;
    private EditText setData, date, month;
    private TextView specResult, title, slash;
    private String item = "";
    private String url = "";
    HttpURLConnection connection = null;
    BufferedReader reader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_fact);
        sToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(sToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Specific Fact");
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //final FetchApi data=new FetchApi();
        spinner = (Spinner) findViewById(R.id.spinner1);
        sResult = (Button) findViewById(R.id.sRes);
        randomActivity = (Button) findViewById(R.id.sf);
        specResult = (TextView) findViewById(R.id.specRes);
        specResult.setMovementMethod(new ScrollingMovementMethod());
        setData = (EditText) findViewById(R.id.setdata);
        month = (EditText) findViewById(R.id.date);
        date = (EditText) findViewById(R.id.month);
        specResult.setVisibility(View.INVISIBLE);
        title = (TextView) findViewById(R.id.title);
        slash = (TextView) findViewById(R.id.slash);
        date.setEnabled(false);
        month.setEnabled(false);
        date.setVisibility(View.INVISIBLE);
        month.setVisibility(View.INVISIBLE);
        slash.setVisibility(View.INVISIBLE);
        //;
        randomActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RActivity = new Intent(SpecificFact.this, RandomFact.class);
                startActivity(RActivity);
                finish();
            }
        });
        month.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (month.getText().toString().length() == 2)     //size as per your requirement
                {
                    date.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        spinner.setOnItemSelectedListener(this);

        sResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(setData.getWindowToken(), 0);
                String inputData = setData.getText().toString();


                if (item.equals("Select One")) {
                   Toast.makeText(SpecificFact.this, "Please select one category", Toast.LENGTH_LONG).show();
                }
                if (item.equals("Trivia")) {
                    if (!inputData.equals("")) {
                        title.setText("please Enter Number eg. 12345");
                        url = "http://numbersapi.com/" + inputData + "/trivia";
                        title.setText("please Enter Number");
                        setData.setHint("12345");
                        //specResult.setText(a);
                    } else
                        Toast.makeText(SpecificFact.this, "Please enter the number", Toast.LENGTH_SHORT).show();

                }
                if (item.equals("Math")) {
                    if (!inputData.equals("")) {
                        title.setText("please Enter Number eg. 12345");
                        url = "http://numbersapi.com/" + inputData + "/math";
                        setData.setHint("12345");
                    } else {
                        specResult.setVisibility(View.INVISIBLE);
                        Toast.makeText(SpecificFact.this, "Please enter the number", Toast.LENGTH_SHORT).show();
                    }
                }
                if (item.equals("Date")) {

                    title.setText("please Enter Date eg. 07/23");
                    String a = date.getText().toString();
                    String b = month.getText().toString();
                    if(!a.equals("")&& (!b.equals(""))) {
                        if ((Integer.parseInt(b) > 0 && Integer.parseInt(b) <= 12) && (Integer.parseInt(a) > 0 && Integer.parseInt(a) <= 31)) {
                            String c = b + "/" + a;
                            url = "http://numbersapi.com/" + c + "/date";


                            // title.setText("please Enter Date");
                            setData.setHint("22/07");
                        } else {
                            Toast.makeText(SpecificFact.this, "Please enter correct format", Toast.LENGTH_SHORT).show();
                            month.setText("");
                            date.setText("");
                        }
                    }
                    else
                        Toast.makeText(SpecificFact.this, "Please enter the date", Toast.LENGTH_SHORT).show();
                }
                if (item.equals("Year")) {
                    if (!inputData.equals("")) {
                        title.setText("please Enter year eg. 1993");
                        String a = setData.getText().toString();
                        url = "http://numbersapi.com/" + inputData + "/year";

                        // title.setText("please Enter year");
                        setData.setHint("1993");
                    } else
                        Toast.makeText(SpecificFact.this, "Please enter the number", Toast.LENGTH_SHORT).show();

                }

                if (isNetworkAvailable())
                    new SFetchApi().execute(url);
                else
                    Toast.makeText(SpecificFact.this, "Network is not available", Toast.LENGTH_SHORT).show();


            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        sResult.setText("Know the fact about " + item);
        String strHint = "1993";
        String strHintD = "07/23";
        String strHintN = "12345";

        specResult.setVisibility(View.INVISIBLE);
        if (item.equals("Select One")) {
            sResult.setText("Know the facts");

            //Toast.makeText(SpecificFact.this, "Please select one category", Toast.LENGTH_LONG).show();
            // sResult.setText("Know the fact about " + item);
            month.setText("");
            url = "";
            date.setText("");
            specResult.setVisibility(View.INVISIBLE);
        }

        if (item.equals("Trivia")) {

            title.setText("please Enter Number eg. 12345");

            month.setText("");

            date.setText("");
            setData.setText("");

            SpannableString span = new SpannableString(strHintN);
            span.setSpan(new RelativeSizeSpan(0.5f), 0, strHintN.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setData.setHint(span);
            setData.setInputType(InputType.TYPE_CLASS_NUMBER);
            sResult.setText("Know the fact about " + item);
            specResult.setVisibility(View.INVISIBLE);
            sResult.setText("Know the fact about " + item);
            specResult.setVisibility(View.INVISIBLE);
            date.setEnabled(false);
            month.setEnabled(false);
            date.setVisibility(View.INVISIBLE);
            month.setVisibility(View.INVISIBLE);
            slash.setVisibility(View.INVISIBLE);
            setData.setEnabled(true);
            setData.setVisibility(View.VISIBLE);
            month.setText("");
            date.setText("");

        }
        if (item.equals("Math")) {

            sResult.setText("do you want to know more about math?");
            title.setText("please Enter Number eg. 12345");
            SpannableString span = new SpannableString(strHintN);
            span.setSpan(new RelativeSizeSpan(0.5f), 0, strHintN.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setData.setHint(span);
            url = "";
            setData.setText("");
            setData.setInputType(InputType.TYPE_CLASS_NUMBER);
            sResult.setText("Know the fact about " + item);
            specResult.setVisibility(View.INVISIBLE);
            date.setEnabled(false);
            month.setEnabled(false);
            date.setVisibility(View.INVISIBLE);
            month.setVisibility(View.INVISIBLE);
            slash.setVisibility(View.INVISIBLE);
            setData.setEnabled(true);
            setData.setVisibility(View.VISIBLE);
            month.setText("");
        }
        date.setText("");
        if (item.equals("Date")) {

            title.setText("please Enter Date eg. MM/DD");
            url = "";
            setData.setText("");
            SpannableString span = new SpannableString(strHintD);
            span.setSpan(new RelativeSizeSpan(0.5f), 0, strHintD.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setData.setHint(span);
            // setData.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_CLASS_NUMBER);
            sResult.setText("Know the fact about " + item);
            specResult.setVisibility(View.INVISIBLE);
            date.setEnabled(true);
            month.setEnabled(true);
            date.setVisibility(View.VISIBLE);
            month.setVisibility(View.VISIBLE);
            slash.setVisibility(View.VISIBLE);
            setData.setEnabled(false);
            setData.setVisibility(View.INVISIBLE);
        }
        if (item.equals("Year")) {
            url = "";
            setData.setInputType(InputType.TYPE_CLASS_NUMBER);
            title.setText("please Enter year eg. 1993");
            SpannableString span = new SpannableString(strHint);
            span.setSpan(new RelativeSizeSpan(0.5f), 0, strHint.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setData.setHint(span);
            ;
            setData.setText("");
            sResult.setText("Know the fact about " + item);
            specResult.setVisibility(View.INVISIBLE);
            date.setEnabled(false);
            month.setEnabled(false);
            date.setVisibility(View.INVISIBLE);
            month.setVisibility(View.INVISIBLE);
            slash.setVisibility(View.INVISIBLE);
            setData.setEnabled(true);
            setData.setVisibility(View.VISIBLE);
            month.setText("");
        }
        date.setText("");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class SFetchApi extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            try {
                URL myUrl = new URL(params[0]);
                connection = (HttpURLConnection) myUrl.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                Log.d("ajeet", buffer.toString());
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
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
            super.onPostExecute(s);
            if (s != null) {
                specResult.setVisibility(View.VISIBLE);
                specResult.setText(s);
            }
        }
    }

    boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }
}