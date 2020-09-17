package com.map.fer.t_bus.Station;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.map.fer.t_bus.Bus.MyBus;
import com.map.fer.t_bus.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StationView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView stationName;
    TextView nbus;
    ArrayList<MyBus> bus_station;
    ListView listView;
    private static BusByStationAdapter adapter;
    double latitude, longitude;
    String station;


    private ProgressDialog pDialog;

    private final String TAG = "Stations";
    private boolean hasOptionsMenu;
    Context contest = this;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        //String bus=bundle.getString("busnbr");
        String arrival_time = bundle.getString("arrival_time");
        String departure_time = bundle.getString("departure_time");
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");
        station = bundle.getString("station");

        url = getString(R.string.api_prefix) +"busbyStation"+"/"+station;
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
                        // get all inforations stocked in database
                        for (int i = 0; i < response.length(); i++)
                            try {

                                JSONObject obj = response.getJSONObject(i); // get and set all valors
                                MyBus s = new MyBus();
                                s.setBus(obj.getString("nbr_bus"));
                                s.setLatitude(obj.getDouble("latitude"));
                                s.setLongitude(obj.getDouble("longitude"));
                            //    bus_station.add(s);

                                double earthRadius = 6371;
                                double latDiff = Math.toRadians(latitude-s.getLatitude());
                                double lngDiff = Math.toRadians(longitude-s.getLongitude());
                                double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                                        Math.cos(Math.toRadians(s.getLatitude()))*
                                                Math.cos(Math.toRadians(latitude))* Math.sin(lngDiff /2) *
                                                Math.sin(lngDiff /2);
                                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                                double distance = earthRadius * c;

                                int meterConversion = 1609;
                                int time = (int) ((distance * meterConversion / 80000)*60);
                                Log.e("distance", ""+time);
                                s.setArrivalTime(""+time);
                                bus_station.add(s);

                                mMap.addMarker(new MarkerOptions().position(new LatLng(s.getLatitude(), s.getLongitude())).title("bus").snippet(s.getBus()).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();


            }

        });

        AppController.getInstance().addToRequestQueue(stationReq);


        listView = (ListView) findViewById(R.id.list);

        bus_station = new ArrayList<>();


        adapter = new BusByStationAdapter(bus_station, getApplicationContext());

        listView.setAdapter(adapter);


    }


    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onMapReady(GoogleMap gMap) {


        mMap = gMap;

        for (int j = 0; j < bus_station.size(); j++) {

            // bus_station.get(j);
            Log.e("bussssssss", "" + bus_station.get(j));
        }

        LatLng Constantine = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constantine, 15));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(station).snippet("clcik to get datails").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus4)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(station).snippet("clcik to get datails").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus4)));


    }


}












