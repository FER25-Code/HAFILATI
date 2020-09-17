package com.map.rsd.busapplication.Alert;

import android.app.ProgressDialog;
import android.content.Context;

import android.os.Bundle;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.map.rsd.busapplication.R;
import com.map.rsd.busapplication.Volley.AppController;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class AlertCon extends AppCompatActivity {
    private ArrayList<MyAlert> alerts = new ArrayList<MyAlert>();
    private RecyclerView recyclerViewalert;
    private LinearLayoutManager layoutManager;
    private AlertsAdapter AlertsAdapter;
    private ProgressDialog pDialog;
    Context context =this;
    String url;

    private final String TAG = "alert";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_conactivity);
        recyclerViewalert =  findViewById(R.id.recyclerviewalert);
        layoutManager = new LinearLayoutManager(AlertCon.this);
        recyclerViewalert.setLayoutManager(layoutManager);
        recyclerViewalert.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewalert.setItemAnimator(new DefaultItemAnimator());
        fetch();
        AlertsAdapter = new AlertsAdapter(context,alerts);
        recyclerViewalert.setAdapter(AlertsAdapter);
    }
    private void fetch() {


        url = getString(R.string.api_prefix)+"getalert";
        Log.e("fatch", url);
        // get informations from server using volly
        pDialog = new ProgressDialog(this); // loading
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest stationReq = new JsonArrayRequest(url, // creat array
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                            try {
                                JSONObject obj = response.getJSONObject(i); // get and set all valors
                                MyAlert s = new MyAlert();
                                s.setDescription(obj.getString("descripton"));
                                s.setLevel(obj.getInt("level"));
                                s.setName(obj.getString("name"));
                                alerts.add(s);
                                Log.i("hatl'alert"+s.getDescription(),"...."+ s.getName()+"...."+s.getLevel());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        AlertsAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();



            }

        });

        AppController.getInstance().addToRequestQueue(stationReq);


    }


    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


}
