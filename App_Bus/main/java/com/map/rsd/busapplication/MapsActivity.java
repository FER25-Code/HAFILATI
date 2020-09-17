package com.map.rsd.busapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.map.rsd.busapplication.Alert.AlertActivity;
import com.map.rsd.busapplication.Alert.AlertCon;
import com.map.rsd.busapplication.Direction.TaskLoadedCallback;
import com.map.rsd.busapplication.Event.EventAdapter;
import com.map.rsd.busapplication.Event.Event_Item;
import com.map.rsd.busapplication.Event.Events;
import com.map.rsd.busapplication.Services.LocationMonitoringService;
import com.map.rsd.busapplication.Sign_up.SingUpActivity;
import com.map.rsd.busapplication.SqLite.DB_sqlite;
import com.map.rsd.busapplication.Station.MyStation;
import com.map.rsd.busapplication.Volley.AppController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap, lmap, sMap;
    private static final String TAG = GpsService.class.getSimpleName();
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;
    LatLng sydney, MyLoc;
    Marker b = null;
    MediaPlayer player;
    //  private ProgressDialog pDialog;
    private final String TAGe = "Events";
    SearchView sv;
    private ArrayList<Event_Item> event = new ArrayList<Event_Item>();
    private ArrayList<MyStation> myStations = new ArrayList<MyStation>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private EventAdapter adapter;
    Toolbar toolbar;
    Context contest = this;
    String url;
    //CountDown
    private CountDownTimer countDownTimer ;
    private  long timeMs = 60000;//1min
    private boolean timerRunning;
    TextView  textTime ;

    // private SwipeRefreshLayout swipeRefreshLayout;
    /**
     * Code used in requesting runtime permissions.
     */
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private boolean mAlreadyStartedService = false;
    private TextView mMsgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_service);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button SendAlert= findViewById(R.id.sendalert);
        Button consultialert = findViewById(R.id.consultialert);
        Button logout= findViewById(R.id.logoutid);

        Button Starting = findViewById(R.id.starting);
        Button stop = findViewById(R.id.stop);
        Button getCloser = findViewById(R.id.getcloser);

        mMsgView =  findViewById(R.id.msgView);
        textTime =findViewById(R.id.Time);


        Starting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player == null) {
                    player = MediaPlayer.create(MapsActivity.this, R.raw.start);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }

                player.start();
          //      UpdateTimer();


            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player == null) {
                    player = MediaPlayer.create(MapsActivity.this, R.raw.stop);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();

                        }
                    });

                }

                player.start();
               //      StartStop();

            }
        });
        getCloser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player == null) {
                    player = MediaPlayer.create(MapsActivity.this, R.raw.start);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                player.start();

            }


        });
        SendAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSend = new Intent(MapsActivity.this, AlertActivity.class);
                startActivity(intentSend);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
              //  finish();
               // System.exit(0);
            }
        });
        consultialert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intantconslti = new Intent(MapsActivity.this, AlertCon.class);
                startActivity(intantconslti);


            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String latitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LATITUDE);
                        String longitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LONGITUDE);
                        if (b == null) {
                            if (latitude != null && longitude != null) {
                             // mMsgView.setText(getString(R.string.msg_location_service_started) + "\n Latitude : " + latitude + "\n Longitude: " + longitude);
                                sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                b = mMap.addMarker(new MarkerOptions().position(sydney).title("My location").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus4)));
                                //   b.setVisible(false);
                             Log.e("Position De bus :Long ...."+longitude,"Lat: ...."+latitude);

                            }

                        } else {
                            b.remove();
                            b = null;
                        }


                    }
                }
                , new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST)

        );

        onResume();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewc);
        adapter = new EventAdapter(contest, event);

        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(MapsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //    swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layoutc);

        //   swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
//        swipeRefreshLayout.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        swipeRefreshLayout.setRefreshing(true);
//
//
//                                    }
//                                }
//        );
        fetch();
        getStation();
    }


    @Override
    public void onResume() {
        super.onResume();

        startStep1();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready ca be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        sMap = googleMap;
        lmap = googleMap;
        // Add a marker in Sydney and move the camera
//       LatLng sydney = new LatLng(Double.parseDouble(latitude) ,Double.parseDouble(longitude) );
//        mMap.addMarker(new MarkerOptions().position(sydney).title("My location"));
        //     mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // so that it renders the list view with updated data
//        Toast.makeText(MapsActivity.this, "Line nub :" +
//                "" + s.getLatitude1(), Toast.LENGTH_SHORT).show();
//        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(,), 10));
//        place1 = new MarkerOptions().position(new LatLng(36.445415, 6.632913)).title("Location 1").icon(BitmapDescriptorFactory.fromResource(R.drawable.iconbus));
//        place2 = new MarkerOptions().position(new LatLng(36.364748, 6.617278)).title("Location 2").icon(BitmapDescriptorFactory.fromResource(R.drawable.iconbus));
//        lmap.addMarker(place1);
//        lmap.addMarker(place2);
   //     Polyline line = lmap.addPolyline(new PolylineOptions().add(new LatLng(36.445415, 6.632913), new LatLng(36.364748, 6.617278)).width(20).color(Color.BLUE).geodesic(true));
        //  new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
//         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(s.getLatitude1(), s.getLongitude1()), 10));
        LatLng lng = new LatLng(36.364748, 6.617278);
       // lmap.moveCamera(CameraUpdateFactory.newLatLngZoom(lng, 30));
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = lmap.addPolyline((PolylineOptions) values[0]);
    }

    //GpsClass

    /**
     * Step 1: Check Google Play services
     */
    private void startStep1() {

        //Check whether this user has installed Google play service which is being used by Location updates.
        if (isGooglePlayServicesAvailable()) {

            //Passing null to indicate that it is executing for the first time.
            startStep2(null);

        } else {
            Toast.makeText(getApplicationContext(), R.string.no_google_playservice_available, Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Step 2: Check & Prompt Internet connection
     */
    private Boolean startStep2(DialogInterface dialog) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            promptInternetConnect();
            return false;
        }


        if (dialog != null) {
            dialog.dismiss();
        }

        //Yes there is active internet connection. Next check Location is granted by user or not.

        if (checkPermissions()) { //Yes permissions are granted by the user. Go to the next step.
            startStep3();
        } else {  //No user has not granted the permissions yet. Request now.
            requestPermissions();
        }
        return true;
    }

    /**
     * Show A Dialog with button to refresh the internet state.
     */
    private void promptInternetConnect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setTitle(R.string.title_alert_no_intenet);
        builder.setMessage(R.string.msg_alert_no_internet);

        String positiveText = getString(R.string.btn_label_refresh);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //Block the Application Execution until user grants the permissions
                        if (startStep2(dialog)) {

                            //Now make sure about location permission.
                            if (checkPermissions()) {

                                //Step 2: Start the Location Monitor Service
                                //Everything is there to start the service.
                                startStep3();
                            } else if (!checkPermissions()) {
                                requestPermissions();
                            }

                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Step 3: Start the Location Monitor Service
     */
    private void startStep3() {

        //And it will be keep running until you close the entire application from task manager.
        //This method will executed only once.

        if (!mAlreadyStartedService && mMsgView != null) {

            mMsgView.setText("");

            //Start location sharing service to app server.........
            Intent intent = new Intent(this, LocationMonitoringService.class);
            startService(intent);

            mAlreadyStartedService = true;
            //Ends................................................
        }
    }

    /**
     * Return the availability of GooglePlayServices
     */
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionState2 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;

    }

    /**
     * Start permissions requests.
     */
    private void requestPermissions() {

        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        boolean shouldProvideRationale2 =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);


        // Provide an additional rationale to the img_user. This would happen if the img_user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale || shouldProvideRationale2) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MapsActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the img_user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Dispatch onStop() to all fragments.
     */
    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If img_user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
                Log.i(TAG, "User interaction wasmsgView cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(TAG, "Permission granted, updates requested, starting location updates");
                startStep3();

            } else {
                // Permission denied.

                // Notify the img_user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the img_user for permission (device policy or "Never ask
                // again" prompts). Therefore, a img_user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation,
                        R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }


    @Override
    public void onDestroy() {


        //Stop location sharing service to app server.........

        stopService(new Intent(this, LocationMonitoringService.class));
        mAlreadyStartedService = false;
        //Ends................................................


        super.onDestroy();
    }


    private void fetch() {
        // showing refresh animation before making http call
        // swipeRefreshLayout.setRefreshing(true);
//         url = "https://moumenmadrid65.000webhostapp.com/station.php";

        url = getString(R.string.api_prefix)+"eventContex"+"/"+36.251994+"/"+ 6.581255;
        // get informations from server using volly
        //    pDialog = new ProgressDialog(this); // loading
        // Showing progress dialog before making http request
//        pDialog.setMessage("Loading...");
//        pDialog.show();
        Log.e("fatch", "onReceive:url");

        JsonArrayRequest stationReq = new JsonArrayRequest(url, // creat array
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAGe, response.toString());
                        adapter.notifyDataSetChanged();
                        event.clear();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                            try {

                                JSONObject obj = response.getJSONObject(i); // get and set all valors
                                Event_Item s = new Event_Item();
//                                s.setstation(obj.getString("station_name"));
//                                s.setbus(obj.getString("nbus"));
//                                s.setarrival_time(obj.getString("arrival_time"));
//                                s.setdeparture_time(obj.getString("departure_time"));

                                s.setname(obj.getString("name"));
                                s.setdescription(obj.getString("description"));
                                s.setstart_date(obj.getString("start_date"));
                                s.setend_date(obj.getString("end_date"));
                                DB_sqlite db_sqlite = new DB_sqlite(MapsActivity.this);
                                event.add(s);
                                if (db_sqlite.IinsertEvent(s.getdescription(),s.getstart_date(),s.getend_date(),s.getname())==true){
                                    Log.i("instart,,.","instart");
                                }else {
                                    Log.i("no ..","instart");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                        // stopping swipe refresh
                        //swipeRefreshLayout.setRefreshing(false);

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());


// stopping swipe refresh
                //        swipeRefreshLayout.setRefreshing(false);
            }

        });


        AppController.getInstance().addToRequestQueue(stationReq);


    }

    private void getStation() {
        url = getString(R.string.api_prefix) + "stationbylinebybus" + "/" + 1;
        JsonArrayRequest stationRq = new JsonArrayRequest(url, // creat array
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAGe, "start Url");

                        for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                            try {
                                JSONObject obj = response.getJSONObject(i); // get and set all valors
                                MyStation s = new MyStation();
                                s.setNameStation(obj.getString("NAME"));
                                s.setLatitude(obj.getDouble("latitude"));
                                s.setLongitude(obj.getDouble("longitude"));
                                myStations.add(s);
                                mMap.addMarker(new MarkerOptions().position(new LatLng(s.getLatitude(), s.getLongitude())).title(s.getNameStation()).icon(BitmapDescriptorFactory.fromResource(R.drawable.busvf)));
                                int ic = 0 ,c=1;
                                while (ic <  myStations.size() && c < myStations.size()) {
                                    Double xp = myStations.get(ic).getLatitude();
                                    Double   yp = myStations.get(ic).getLongitude();
                                    Double  xp1 = myStations.get(c).getLatitude();
                                    Double   yp1 = myStations.get(c).getLongitude();
                                    Polyline line2 = lmap.addPolyline(new PolylineOptions().add(new LatLng(xp, yp),new LatLng(xp1,yp1)).width(20).color(Color.BLUE).geodesic(true));
                                    Log.d("poss"+xp,""+yp);
                                    Log.d("poss"+xp1,""+yp1);
                                    ic++;
                                    c++;
                                }
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(s.getLatitude(),s.getLongitude()), 14));




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }

        });

        AppController.getInstance().addToRequestQueue(stationRq);
        Log.i("for station ", "start");

    }

    public void StartTime(){
        CountDownTimer countDownTimer = new CountDownTimer(timeMs,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeMs =1 ;
                 UpdateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        timerRunning =true;
    }

    public void StopTimer(){
        countDownTimer.cancel();
         timerRunning =false ;
    }
 public  void StartStop(){
     if (timerRunning){
         StopTimer();
     }else {
         StartTime();
     }
 }
    public void  UpdateTimer(){
 int min  = (int)timeMs/60000;
 int sec =(int)timeMs %60000/1000;
String TimeL ;
TimeL = ""+min;
TimeL += ":";
   if (sec <10 ) TimeL +="0";
   TimeL +=sec;

        textTime.setText(TimeL);

    }
    public void sendposition(double x, double y ){

        url = getString(R.string.api_prefix)+"eventContex"+"/"+x+"/"+y;
    }
}


