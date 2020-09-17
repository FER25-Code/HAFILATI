package com.map.fer.t_bus;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.map.fer.t_bus.About.AboutActivity;
import com.map.fer.t_bus.Alert.AlertActivity;
import com.map.fer.t_bus.Alert.AlertCon;
import com.map.fer.t_bus.Bus.BusActivity;
import com.map.fer.t_bus.BusByStationWithQR.TimeBusActivity;
import com.map.fer.t_bus.Direction.FetchURL;
import com.map.fer.t_bus.Direction.TaskLoadedCallback;
import com.map.fer.t_bus.Evaluation.Evaluation;
import com.map.fer.t_bus.Line.LineActivity;
import com.map.fer.t_bus.Login.LoginActivity;
import com.map.fer.t_bus.QRcode.ScannerActivity;
import com.map.fer.t_bus.Station.AppController;
import com.map.fer.t_bus.Station.Stations;
import com.map.fer.t_bus.Station.MyStation;
import com.map.fer.t_bus.StepView.StepView;
import com.map.fer.t_bus.TICKETS.TicketsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, TaskLoadedCallback {
    GoogleMap mMap;
    EditText AddressOne, AddressTwo;
    private ProgressDialog pDialog;
    private ArrayList<MyStation> St = new ArrayList<MyStation>();
    private final String TAG = "Stations";
    String url;
    JSONObject obj;
    MyStation s = new MyStation();
    Button search;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;
    private ArrayList<MyStation> myStations = new ArrayList<MyStation>();
    NavigationView navigationView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //apple map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
//        mapView = (MapView) findViewById(R.id.mapView);
//        mapView.getMapAsync(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
//        NavigationView navigationView2 = findViewById(R.id.nav_view2);
//        navigationView2.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        // set item as selected to persist highlight
//                        menuItem.setChecked(true);
//                        // close drawer when item is tapped
//                        // Add code here to update the UI based on the item selected
//                        // For example, swap UI fragments here
//                        return true;
//                    }
//                });
        AddressOne = findViewById(R.id.addressOne);
        AddressTwo = findViewById(R.id.addressTwo);
        search = (Button) findViewById(R.id.recherchebutton);
        search.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          String one = String.valueOf(AddressOne.getText());
                                          String two = String.valueOf(AddressTwo.getText());
                                          url = getString(R.string.api_prefix) + "betweenstations" + "/" + one + "/" + two;
                                          Toast.makeText(MainActivity.this, "" + url + "....." + two, Toast.LENGTH_LONG).show();

                                          JsonArrayRequest stationReq = new JsonArrayRequest(url, // creat array
                                                  new Response.Listener<JSONArray>() {
                                                      @Override
                                                      public void onResponse(JSONArray response) {
                                                          Log.d(TAG, response.toString());
                                                          hidePDialog();

                                                          // Parsing json
                                                          for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                                                              try {
                                                                  Log.e(TAG, "start try ");
                                                                  obj = response.getJSONObject(i); // get and set all valors

                                                                  s.setLine_number(obj.getString("line_number"));
                                                                  s.setColor_type(obj.getString("color_type"));
                                                                  s.setLatitude1(obj.getDouble("latitude1"));
                                                                  s.setLongitude1(obj.getDouble("longitude1"));
                                                                  s.setLatitude2(obj.getDouble("latitude2"));
                                                                  s.setLongitude2(obj.getDouble("longitude2"));
                                                                  St.add(s);
                                                                  Log.e(TAG, "end try ");

                                                              } catch (JSONException e) {
                                                                  Toast.makeText(MainActivity.this, "server not run "
                                                                          , Toast.LENGTH_SHORT).show();
                                                              }

                                                          }

                                                          // notifying list adapter about data changes
                                                          // so that it renders the list view with updated data
                                                          Toast.makeText(MainActivity.this, "Line nub :" +
                                                                  "" + s.getLatitude1(), Toast.LENGTH_SHORT).show();
                                                          // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(,), 10));
                                                          place1 = new MarkerOptions().position(new LatLng(s.getLatitude1(), s.getLongitude1())).title("Location 1").icon(BitmapDescriptorFactory.fromResource(R.drawable.busmarker));
                                                          place2 = new MarkerOptions().position(new LatLng(s.getLatitude2(), s.getLongitude2())).title("Location 2").icon(BitmapDescriptorFactory.fromResource(R.drawable.busmarker));
                                                          mMap.addMarker(place1);
                                                          mMap.addMarker(place2);
                                                          new FetchURL(MainActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
                                                       //   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(s.getLatitude1(), s.getLongitude1()), 10));
                                                      }
                                                  }, new Response.ErrorListener() {
                                              @Override
                                              public void onErrorResponse(VolleyError error) {
                                                  VolleyLog.d(TAG, "Error: volley  " + error.getMessage());
                                                  hidePDialog();
                                              }

                                          });
                                          AppController.getInstance().addToRequestQueue(stationReq);

                                      }
                                  }


        );


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNv);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.station:
                        Intent intentstation = new Intent(MainActivity.this, Stations.class);
                        startActivity(intentstation);
                        break;
                    case R.id.nav_tickets:
                        Intent intent2=new Intent(MainActivity.this, TicketsActivity.class);
                        Bundle bundle2= new Bundle();
                        bundle2.putString("result", "Wallet");
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;
                    case R.id.nav_qr:
                        new IntentIntegrator(MainActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
                        break;
                    case R.id.nav_ser:
                        Intent intentc = new Intent(MainActivity.this, BusActivity.class);
                        startActivity(intentc);
                        break;
                    case R.id.nav_alarmbus:
                        Intent intentLine = new Intent(MainActivity.this, LineActivity.class);
                        startActivity(intentLine);
                        break;
                }
                return true;
            }
        });
        getStation();
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_alarm) {

            Intent intents = new Intent(MainActivity.this, AlertActivity.class);
            startActivity(intents);
        } else if (id == R.id.star) {
            Intent intent = new Intent(MainActivity.this, Evaluation.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(MainActivity.this, AlertCon.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Constantine = new LatLng(36.351735, 6.622009);
        //  mMap.addMarker(new MarkerOptions().position(Constantine).title("Gare Routière Est"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constantine, 10));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(36.351735, 6.622009)).title("Gare Routière Est").snippet("clcik to get datails").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus4)));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(36.344385, 6.616097)).title("la fac 1").snippet("clcik to get datails").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus4)));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(36.333081, 6.614981)).title("la fac 2").snippet("clcik to get datails").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus4)));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(36.329330, 6.617996)).title("zarzara ").snippet("clcik to get datails").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus4)));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(36.324412, 6.619799)).title(" khaznadar ").snippet("clcik to get datails").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus4)));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(36.318629, 6.617288)).title("belhaj").snippet("clcik to get datails").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus4)));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(36.313217, 6.622556)).title("zouaghi").snippet("clcik to get datails").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus4)));


    }


//Qr code
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //show dialogue with result
                showResult(result.getContents());
               // Intent intent = new Intent(MainActivity.this, BusActivity.class);
              //  startActivity(intent);
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }

        }

    //Qr code
    //method to construct dialogue with scan results
    public void showResult(final String result) {
        // TODO: 02/07/2019 test if qrstation or tickts
        String[] arrOfStr = result.split("/");
        String part1 = arrOfStr[0];
        if (part1.equals("Ticktbyline")){

            Intent intent2=new Intent(this, TicketsActivity.class);
            Bundle bundle2= new Bundle();
            bundle2.putString("result", result);
            intent2.putExtras(bundle2);
            startActivity(intent2);
        }else {

        Intent intent=new Intent(this, TimeBusActivity.class);
        Bundle bundle= new Bundle();
        bundle.putString("result", result);
        intent.putExtras(bundle);
        startActivity(intent);
         }
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
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
    private void getStation() {
        url = getString(R.string.api_prefix) + "stationbylinebybus" + "/" + 1;
        JsonArrayRequest stationRq = new JsonArrayRequest(url, // creat array
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("vb", "start Url");

                        for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                            try {
                                JSONObject obj = response.getJSONObject(i); // get and set all valors
                                MyStation s = new MyStation();
                                s.setNameStation(obj.getString("NAME"));
                                s.setLatitude1(obj.getDouble("latitude"));
                                s.setLongitude1(obj.getDouble("longitude"));
                                myStations.add(s);
                                mMap.addMarker(new MarkerOptions().position(new LatLng(s.getLatitude1(), s.getLongitude1())).title(s.getNameStation()).icon(BitmapDescriptorFactory.fromResource(R.drawable.busvf)));
                                int ic = 0 ,c=1;
                                while (ic <  myStations.size() && c < myStations.size()) {
                                    Double xp = myStations.get(ic).getLatitude1();
                                    Double   yp = myStations.get(ic).getLongitude1();
                                    Double  xp1 = myStations.get(c).getLatitude1();
                                    Double   yp1 = myStations.get(c).getLongitude1();
                                    Polyline line2 = mMap.addPolyline(new PolylineOptions().add(new LatLng(xp, yp),new LatLng(xp1,yp1)).width(20).color(Color.BLUE).geodesic(true));
                                    Log.d("poss"+xp,""+yp);
                                    Log.d("poss"+xp1,""+yp1);
                                    ic++;
                                    c++;
                                }
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(s.getLatitude1(),s.getLongitude1()), 12));


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

}
