package com.map.fer.t_bus.BusByStationWithQR;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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

import java.util.ArrayList;

public class TimeBusActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    String url;
    private final String TAG = "busByStation";
    private ArrayList<MyBus> BusTime = new ArrayList<MyBus>();
    SearchView svbus;
    private RecyclerView recyclerViewBus;
    private LinearLayoutManager layoutManagerBusT;
    private TimeBusAdapter adapterBust;
    Toolbar toolbar;
    Context contest = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_bus_activity);
        toolbar = findViewById(R.id.toolbarL);
        // setSupportActionBar(toolbar);


        recyclerViewBus = findViewById(R.id.recyclerviewBusTm);
        adapterBust = new TimeBusAdapter(contest, BusTime);
        svbus = findViewById(R.id.mSearchBusTm);
        recyclerViewBus.setAdapter(adapterBust);
        layoutManagerBusT = new LinearLayoutManager(com.map.fer.t_bus.BusByStationWithQR.TimeBusActivity.this);
        recyclerViewBus.setLayoutManager(layoutManagerBusT);
        recyclerViewBus.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewBus.setItemAnimator(new DefaultItemAnimator());


        svbus.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                adapterBust.getFilter().filter(query);
                return false;
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String result = bundle.getString("result");
        url = getString(R.string.api_prefix) +"busStation"+"/"+result;

        // get informations from server using volly
        pDialog = new ProgressDialog(this); // loading
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest stationReq = new JsonArrayRequest(url, // creat array
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                            try {
                                JSONObject obj = response.getJSONObject(i); // get and set all valors
                                MyBus s = new MyBus();
                                s.setNbrBus(obj.getInt("nbr_bus"));
                                s.setLinename(obj.getString("name"));
                                s.setStartTime(obj.getString("departure_date"));
                                s.setArrivalTime(obj.getString("finish_date"));
                                s.setMark("mark");
                                s.setFar(5);
                                BusTime.add(s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapterBust.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();


            }

        });

        AppController.getInstance().addToRequestQueue(stationReq);



        /**/

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}