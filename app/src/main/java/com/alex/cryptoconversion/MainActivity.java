package com.alex.cryptoconversion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.droidparts.activity.support.v7.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private final String BTC_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";
    private final String XRP_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/XRP";
    private final String ZEC_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/ZEC";
    private final String ETH_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/ETH";
    private final String LTC_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/LTC";
    private final String XMR_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/XMR";

    TextView mPriceTextView;
    Spinner cryptoSpinner, spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureNextButton();
       configureNextNextButton();

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);

        cryptoSpinner = (Spinner) findViewById(R.id.crypto_spinner);
        spinner = (Spinner) findViewById(R.id.currency_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);
        ArrayAdapter<CharSequence> cryptAdapter = ArrayAdapter.createFromResource(this,
                R.array.crypto_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        cryptAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner.setAdapter(adapter);
        cryptoSpinner.setAdapter(cryptAdapter);

        spinner.setOnItemSelectedListener(this);
        cryptoSpinner.setOnItemSelectedListener(this);

    }

    private void apiRequest(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("Bitcoin", "JSON: " + response.toString());

                try{
                    String price = response.getString("last");
                    mPriceTextView.setText(price);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("Bitcoin", "Request fail! Status code: " + statusCode);
                Log.d("Bitcoin", "Fail response: " + response);
                Log.e("ERROR", e.toString());

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

       String sp1 = String.valueOf(cryptoSpinner.getSelectedItem());
       String sp2 = String.valueOf(spinner.getSelectedItem());

       if(sp1.contentEquals("BTC")) {
           String btnFinalUrl = BTC_URL + sp2;
           apiRequest(btnFinalUrl);
       }
        if(sp1.contentEquals("XRP")) {
            String xrpFinalUrl = XRP_URL + sp2;
            apiRequest(xrpFinalUrl);
        }
        if(sp1.contentEquals("ZEC")) {
            String zecFinalUrl = ZEC_URL + sp2;
            apiRequest(zecFinalUrl);
        }
        if(sp1.contentEquals("ETH")) {
            String ethFinalUrl = ETH_URL + sp2;
            apiRequest(ethFinalUrl);
        }
        if(sp1.contentEquals("LTC")) {
            String ltcFinalUrl = LTC_URL + sp2;
            apiRequest(ltcFinalUrl);
        }
        if(sp1.contentEquals("XMR")) {
            String xmrFinalUrl = XMR_URL + sp2;
            apiRequest(xmrFinalUrl);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //
    }

    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TrackerActivity.class));
            }
        });
    }

    private void configureNextNextButton() {
        Button nextButton = (Button) findViewById(R.id.nextNextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InvestCalculator.class));
            }
        });
    }


}


