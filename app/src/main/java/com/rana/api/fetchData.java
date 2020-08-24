package com.rana.api;

import android.os.AsyncTask;
import  android.util.Base64;
import android.webkit.HttpAuthHandler;

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

public class fetchData extends AsyncTask<Void,Void,Void> {

    String data="";
    String dataParsed="";
    String singleParsed="";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url=new URL("https://api-tracerind.covidindiataskforce.org/api/GetAllPatient/");
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            String header = "Basic " + new String(android.util.Base64.encode("dev:citf@123".getBytes(), android.util.Base64.NO_WRAP));
            httpURLConnection.addRequestProperty("Authorization", header);
            InputStream inputStream= httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while(line!=null)
            {
            line=bufferedReader.readLine();
            data=data+line;
            }



            try {
                JSONArray jsonArray = new JSONArray(data);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject= jsonArray.getJSONObject(i+1);
                    singleParsed="Name:"+jsonObject.get("name")+"\n";
                    dataParsed = dataParsed + singleParsed;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }






        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
     }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.fetcheddata.setText(this.dataParsed);
    }
}
