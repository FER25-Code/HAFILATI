package com.map.fer.t_bus.Line;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.map.fer.t_bus.R;
import com.map.fer.t_bus.Station.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LineActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private final String TAG = "Line";
    String url;
    SearchView svLine;
    private ArrayList<MyLine> Line = new ArrayList<MyLine>();
    private RecyclerView recyclerViewLine;
    private LinearLayoutManager layoutManagerLine;
    private LineAdapter adapterLine;

    Toolbar toolbar;
    Context contest = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_activity);
     toolbar = findViewById(R.id.toolbarL);


        recyclerViewLine = findViewById(R.id.recyclerviewLine);
        adapterLine = new LineAdapter(contest, Line);
        svLine = findViewById(R.id.mSearchLien);
        recyclerViewLine.setAdapter(adapterLine);
        layoutManagerLine = new LinearLayoutManager(LineActivity.this);
        recyclerViewLine.setLayoutManager(layoutManagerLine);
        recyclerViewLine.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewLine.setItemAnimator(new DefaultItemAnimator());


        svLine.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                adapterLine.getFilter().filter(query);
                return false;
            }
        });

        //      url = "https://moumenmadrid65.000webhostapp.com/station.php";
        url = getString(R.string.api_prefix)+"allline";

        // get informations from server using volly
        pDialog = new ProgressDialog(this); // loading
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        JsonArrayRequest lineReq = new JsonArrayRequest(url, // creat array
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        adapterLine.notifyDataSetChanged();
                        Line.clear();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                            try {
                                JSONObject obj = response.getJSONObject(i); // get and set all valors
                                MyLine s = new MyLine();
                                s.setNbrLine(obj.getInt("line_number"));
                                s.setName(obj.getString("name"));
                                s.setColor(obj.getString("color_type"));
                                Line.add(s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list lineAdapter about data changes
                        // so that it renders the list view with updated data
                        adapterLine.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();


            }

        });

        AppController.getInstance().addToRequestQueue(lineReq);



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