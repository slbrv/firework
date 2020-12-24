package com.firework.Modules.Internet;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Slava on 03.03.2017.
 */

public class JSONParserThread extends Thread{

    protected String address;
    protected JSONObject data;

    public JSONParserThread(String address) {
        this.address = address;
        start();
    }

    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JSONObject temp = null;
        InputStream is;
        try {
            is = url.openStream();
            StringBuilder sb = new StringBuilder();

            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            temp = new JSONObject(sb.toString());
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.data = temp;
        interrupt();
    }

    public JSONObject getData()
    {
        return data;
    }
}
