package com.seal.yahoowebscraping;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    private TextView priceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        priceView = (TextView) findViewById(R.id.price);
        ParseYahooTask task = new ParseYahooTask();
        task.execute("AAPL", "", "");
    }


    // Read a webpage and return its contents as a string
    public static String readURL(String address) {
        String result = "";
        try {
            URL url = new URL(address);
            result = new Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + address);
        }

        return result;
    }


    public static String getStockInfos(String symbol) {
        String infos = readURL("http://104.131.82.149:7575/q?s=" + symbol);
        return infos;
    }

    // Parsing a website in the background

    private class ParseYahooTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String price = getStockInfos(params[0]);
            return price;
        }

        protected void onPostExecute(String price) {
            // Set the price field here.
            priceView.setText(price);
        }


    }
}