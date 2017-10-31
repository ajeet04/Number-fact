package com.example.raghvendrapandey.knowledgeworld;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Raghvendra Pandey on 10/17/2017.
 */

public class FetchApi extends AsyncTask<String,String,String> {
    HttpURLConnection connection=null;
    BufferedReader reader=null;
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
        super.onPostExecute(s);

    }
}
