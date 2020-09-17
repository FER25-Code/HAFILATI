package com.map.fer.t_bus.TICKETS;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.map.fer.t_bus.R;
import com.map.fer.t_bus.Station.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;

import static android.support.v4.content.ContextCompat.startActivity;

public class TicketsAdd extends Dialog {
    private ArrayList<MyTicket> myTickets2 = new ArrayList<MyTicket>();

    String url, url1 ;

    public TicketsAdd(@NonNull @android.support.annotation.NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_add_activity);

        final Button addTiket = findViewById(R.id.buttonadd);
        EditText code = findViewById(R.id.codetiket);
        addTiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            url1 = "http://192.168.43.77:8080/api/Wallet/1";
               JsonArrayRequest walletReq = new JsonArrayRequest(url1, // creat array
                 new Response.Listener<JSONArray>() {
                    @Override
                       public void onResponse(JSONArray response) {
                          for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                            try {
                                final MyTicket s = new MyTicket();
                                JSONObject obj = response.getJSONObject(i); // get and set all valors
                                s.setAmountTotal(obj.getInt("amounttotal"));
                                myTickets2.add(s);
                                double c;
                                c= 1000+ s.getAmountTotal();
                                url =  "http://192.168.43.77:8080/api/Wallet/edit" + "/" + 1 + "/" + c;
                                JsonArrayRequest UpdateReq = new JsonArrayRequest(url, // creat array
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {
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
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });




                cancel();
            }
        });
    }


}
