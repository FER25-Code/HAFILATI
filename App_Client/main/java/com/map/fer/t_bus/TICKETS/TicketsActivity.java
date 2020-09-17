package com.map.fer.t_bus.TICKETS;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.map.fer.t_bus.Bus.MyBus;
import com.map.fer.t_bus.R;
import com.map.fer.t_bus.Station.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TicketsActivity extends AppCompatActivity {
    private final String TAG = "Linename";
    String url,url1,url2;
    private ArrayList<MyTicket> myTickets = new ArrayList<MyTicket>();
    private ArrayList<MyTicket> myTickets2 = new ArrayList<MyTicket>();
    private RecyclerView recyclerViewTiket;
    private LinearLayoutManager layoutManagerTiket;
    private TicketAdapter adaptertiket;
    Context contest = this;
    ImageButton addTikets ;
    private ProgressDialog pDialog;
    TextView AmountTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets_activity);
        addTikets = findViewById(R.id.addT);
        recyclerViewTiket = findViewById(R.id.recyclerviewtiket);
        adaptertiket = new TicketAdapter(contest, myTickets);
        recyclerViewTiket.setAdapter(adaptertiket);
        layoutManagerTiket = new LinearLayoutManager(TicketsActivity.this);
        recyclerViewTiket.setLayoutManager(layoutManagerTiket);
        recyclerViewTiket.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewTiket.setItemAnimator(new DefaultItemAnimator());
        AmountTotal = findViewById(R.id.amountTotal);
        Intent intent2 = getIntent();
        Bundle bundle2 = intent2.getExtras();
        final String result = bundle2.getString("result");
        if (result.equals("Wallet")) {
            url1 = getString(R.string.api_prefix) + "Wallet/1";
            JsonArrayRequest walletReq = new JsonArrayRequest(url1, // creat array
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, "getAmount");
                            for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                                try {
                                    final MyTicket s = new MyTicket();
                                    JSONObject obj = response.getJSONObject(i); // get and set all valors
                                    s.setAmountTotal(obj.getInt("amounttotal"));
                                    myTickets2.add(s);

                                    AmountTotal.setText(String.valueOf(s.getAmountTotal()));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adaptertiket.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());


                }

            });
            AppController.getInstance().addToRequestQueue(walletReq);
        } else {

        url = getString(R.string.api_prefix) + result;
        pDialog = new ProgressDialog(this); // loading
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        final MyTicket s = new MyTicket();
        JsonArrayRequest stationReq = new JsonArrayRequest(url, // creat array
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hidePDialog();
                        Log.d(TAG, response.toString());
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                            try {
                                JSONObject obj = response.getJSONObject(i); // get and set all valors
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                Date date = new Date();
                                String str = dateFormat.format(date);
                                String[] arrOfStr = str.split(" ");
                                String part1 = arrOfStr[0];
                                String part = arrOfStr[1];
                                s.setDate(part1);
                                s.setTime(part);
                                s.setNameLine(obj.getString("name"));
                                s.setNbrLine(obj.getInt("line_number"));
                                s.setAmount(obj.getInt("amount"));
                                myTickets.add(s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adaptertiket.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }

        });
        AppController.getInstance().addToRequestQueue(stationReq);

        url1 = getString(R.string.api_prefix) + "Wallet/1";
        JsonArrayRequest walletReq = new JsonArrayRequest(url1, // creat array
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "getAmount");
                        for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                            try {
                                JSONObject obj = response.getJSONObject(i); // get and set all valors

                                s.setAmountTotal(obj.getInt("amounttotal"));
                                myTickets2.add(s);
                                double c;
                                c = s.getAmountTotal() - s.getAmount();
                                AmountTotal.setText(String.valueOf(c));

                                url2 = getString(R.string.api_prefix) + "Wallet/edit" + "/" + 1 + "/" + c;

                                JsonArrayRequest UpdateReq = new JsonArrayRequest(url2, // creat array
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {

                                                // notifying list adapter about data changes
                                                // so that it renders the list view with updated data

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        VolleyLog.d("tag", "Error: " + error.getMessage());

                                    }

                                });

                                AppController.getInstance().addToRequestQueue(UpdateReq);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adaptertiket.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());


            }

        });
        AppController.getInstance().addToRequestQueue(walletReq);
    }
        addTikets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TicketsAdd dialog= new TicketsAdd(TicketsActivity.this);
                dialog.show();
            }
        });


    }




    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
