package com.map.fer.t_bus.Station;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.map.fer.t_bus.R;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Stations extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ProgressDialog pDialog;
    private final String TAG = "Stations";
    SearchView sv;
    private ArrayList<MyStation> station = new ArrayList<MyStation>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private    StationAdapter  adapter;
    Context contest=this;
    String url;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new StationAdapter(contest,station);
        sv= (SearchView) findViewById(R.id.mSearch);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(Stations.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());




        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                adapter.getFilter().filter(query);
                return false;
            }
        });



        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout1);

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        fetch();
                                    }
                                }
        );

    }

        private void fetch() {

            // showing refresh animation before making http call
            swipeRefreshLayout.setRefreshing(true);
            url = getString(R.string.api_prefix)+"busperstation";
        // get informations from server using volly
        pDialog = new ProgressDialog(this); // loading
        JsonArrayRequest stationReq = new JsonArrayRequest(url, // creat array
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        adapter.notifyDataSetChanged();
                        station.clear();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                            try {

                                JSONObject obj = response.getJSONObject(i); // get and set all valors
                                MyStation s = new MyStation();
                                s.setstation(obj.getString("NAME"));
                                s.setlatitude(obj.getDouble("latitude"));
                                s.setlongitude(obj.getDouble("longitude"));
                                s.setbus(obj.getString("nbr_bus"));
                                s.setarrival_time(obj.getString("departure_date"));
                                s.setdeparture_time(obj.getString("finish_date"));
                                station.add(s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();


// stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }

        });

        AppController.getInstance().addToRequestQueue(stationReq);





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

    @Override
    public void onRefresh() {
        fetch();
    }
}