package com.alex.cryptoconversion;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import cz.msebera.android.httpclient.Header;


public class InvestCalculator extends Activity {

    private final String BTC_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCRON";
    private final String XRP_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/XRPRON";
    private final String ZEC_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/ZECRON";
    private final String ETH_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/ETHRON";
    private final String LTC_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/LTCRON";
    private final String XMR_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/XMRRON";


    EditText inputAmount;
    TextView rezultat;
    Button btc, xrp, zec, xmr, ltc, eth;


    private double cantitatePermisa, sumaDisponibila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invest_calculator);

        inputAmount = (EditText) findViewById(R.id.input_box);
        rezultat = (TextView) findViewById(R.id.result_box);
        btc = (Button) findViewById(R.id.button10);
        xrp = (Button) findViewById(R.id.button9);
        zec = (Button) findViewById(R.id.button11);
        xmr = (Button) findViewById(R.id.button12);
        ltc = (Button) findViewById(R.id.button13);
        eth = (Button) findViewById(R.id.button14);
        configureMainButton();


        btc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumaDisponibila = Double.valueOf(inputAmount.getText().toString());
                apiRequest(BTC_URL);
                String s = " Bitcoin's";
                rezultat.setText(s);
            }
        });

        xrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumaDisponibila = Double.valueOf(inputAmount.getText().toString());
                apiRequest(XRP_URL);
                String s = " Ripple's";
                rezultat.setText(s);
            }
        });

        zec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumaDisponibila = Double.valueOf(inputAmount.getText().toString());
                apiRequest(ZEC_URL);
                String s = " Zcash's";
                rezultat.setText(s);
            }
        });

        xmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumaDisponibila = Double.valueOf(inputAmount.getText().toString());
                apiRequest(XMR_URL);
                String s = " Monero's";
                rezultat.setText(s);
            }
        });

        ltc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumaDisponibila = Double.valueOf(inputAmount.getText().toString());
                apiRequest(LTC_URL);
                String s = " Litecoin's";
                rezultat.setText(s);
            }
        });

        eth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumaDisponibila = Double.valueOf(inputAmount.getText().toString());
                apiRequest(ETH_URL);
                String s = " Ethereum's";
                rezultat.setText(s);
            }
        });

    }



    public void apiRequest(String url) {

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("bitcoin", "JSON: " + response.toString());

                try{
                    int price = response.getInt("last");
                    Log.v("bitcoin", "pretul in INT: " + price);
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setGroupingUsed(true);
                    nf.setMaximumFractionDigits(4);
                    nf.setMinimumIntegerDigits(1);


                    if(sumaDisponibila < price) {
                        cantitatePermisa = sumaDisponibila / price;
                    }
                    if(sumaDisponibila >= price) {
                        cantitatePermisa = price / sumaDisponibila;
                    }

                    rezultat.setText(String.valueOf(nf.format(cantitatePermisa)));


                } catch (JSONException e) {
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



    private void configureMainButton() {
        Button goToMainButton = (Button) findViewById(R.id.backBackButton);
        goToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
