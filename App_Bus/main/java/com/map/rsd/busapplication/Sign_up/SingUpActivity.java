package com.map.rsd.busapplication.Sign_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import com.map.rsd.busapplication.MapsActivity;
import com.map.rsd.busapplication.R;
import com.map.rsd.busapplication.Volley.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class SingUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerOwner, spinnerType, spinnerCity, spinnerMark, spinnerLine,spinnerSige;
    List<String> list = new ArrayList<String>();
    private ArrayList<MyLine> Line = new ArrayList<MyLine>();
    private ArrayList<MyVehicle> Vehicle = new ArrayList<MyVehicle>();
    List<String> SOwner = new ArrayList<String>();
    List<String> SType = new ArrayList<String>();
    List<String> LinsName = new ArrayList<String>();
    List<DateB> listspiner = new ArrayList<DateB>();
    Button send;
    List<String> listd = new ArrayList<String>();

    String url,  text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_up_activity);
        LinsName.add("Line");
        SType.add("Type");
        SOwner.add("Owner");
        spinnerMark = findViewById(R.id.spinnerMark);
        spinnerOwner = findViewById(R.id.spinnerOwner);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerCity = findViewById(R.id.spinnerCity);
        spinnerLine = findViewById(R.id.spinnerLine);
        spinnerSige = findViewById(R.id.spinnerSige);
        send=findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           url = getString(R.string.api_prefix)+"insertuser"+"/"+listd.get(0)+"/"+listd.get(0)+"/"+listd.get(0)
                   +"/"+listd.get(0)+"/"+listd.get(0);
                DateB dateB = new DateB();

          //       Log.e("line : "+dateB.getLine(),"Sige ..............."+dateB.getSige()+"...........mark : "+dateB.getMark());
                 Log.e("line : "+listd.get(0),"Sige ..............."+listd.get(1)+"...........mark : "+listd.get(2));
                JsonArrayRequest stationReq = new JsonArrayRequest(url, // creat array
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

                AppController.getInstance().addToRequestQueue(stationReq);
            }
        });




        LineSp();
        Vehicle();
        Citysp();
        //start One Time
        SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        String firstTime =preferences.getString("FirstTimeInstall","");
        if(firstTime.equals("Yes")){
            Intent intent = new Intent(SingUpActivity.this, MapsActivity.class);
            startActivity(intent);
        }else {
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("FirstTimeInstall","Yes");
            editor.apply();
        }

    }

    static final String[] City = new String[]{"City", "Adrar 	", "Chlef 	", "Laghouat 	",
            "Oum El Bouaghi", "Batna 	", "Béjaïa 	", "Biskra 	", "Béchar 	",
            "Blida 	", "Bouira 	", "Tamanrasset", "Tébessa ", "Tlemcen ",
            "Tiaret ", "Tizi Ouzou", "Alger 	",
            "Djelfa 	", "Jijel 	", "Sétif 	", "Saïda 	", "Skikda 	", "Sidi Bel Abbès ",
            "Annaba ", "Guelma ", "Constantine",
            "Médéa ", "Mostaganem ", "M’Sila 	", "Mascara ", "Ouargla 	", "Oran 	", "El Bayadh", "Illizi ", "Bordj Bou Arreridj",
            "Boumerdès", "El Tarf 	", "Tindouf", "Tissemsilt", "El Oued",
            "Khenchela", " Souk Ahras ", "Tipaza ", "Mila ", "Aïn Defla ", "Naâma 	", "Aïn Témouchent ", "Ghardaia ", "Relizane "
    };
    static final String[] Sige = new String[]{ "Sige","21","31","47","55"};
    static final String[] Mark = new String[]{ "Mark","Foton","Mitsubishi","Higer","Isuzu","Nissan","King Long","Toyota"};

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         text = parent.getItemAtPosition(position).toString();
        // textInputLayout.setTextInputAccessibilityDelegate(text);
        int x =1 ;
        listd.add(text);
        DateB dateB = new DateB();
        if (position > 0) {

            // Notify the selected item text
            Toast.makeText(getApplicationContext(), "Selected : " + text, Toast.LENGTH_SHORT).show();


        }

    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void LineSp() {


        url = getString(R.string.api_prefix) + "allline";
        JsonArrayRequest lineReq = new JsonArrayRequest(url, // creat array
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("d", response.toString());


                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                            try {
                                JSONObject obj = response.getJSONObject(i); // get and set all valors
                                MyLine s = new MyLine();
                                s.setName(obj.getString("name"));
                                Line.add(s);
                                LinsName.add(s.getName());


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        //    adapterLine.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("e", "Error: " + error.getMessage());


            }

        });

        AppController.getInstance().addToRequestQueue(lineReq);
        ArrayAdapter<String> arrayAdapterd = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, LinsName);
        arrayAdapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLine.setAdapter(arrayAdapterd);
        spinnerLine.setOnItemSelectedListener(this);
        spinnerLine.setPrompt("Select one ..");
    }

    public void Citysp() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, City);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(arrayAdapter);
        spinnerCity.setOnItemSelectedListener(this);
        spinnerCity.setPrompt("Select one ..");
        //Mark
        ArrayAdapter<String> arrayAdapterdMark = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Mark);
        arrayAdapterdMark.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMark.setAdapter(arrayAdapterdMark);
        spinnerMark.setOnItemSelectedListener(this);
        spinnerMark.setPrompt("Select one ..");
        //Sige
        ArrayAdapter<String> arrayAdapterSige = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Sige);
        arrayAdapterSige.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSige.setAdapter(arrayAdapterSige);
        spinnerSige.setOnItemSelectedListener(this);
        spinnerSige.setPrompt("Select one ..");

    }

    public void Vehicle() {

        url = getString(R.string.api_prefix) + "businfo";
        JsonArrayRequest lineReq = new JsonArrayRequest(url, // creat array
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("d", response.toString());


                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                            try {
                                JSONObject obj = response.getJSONObject(i); // get and set all valors
                                MyVehicle v = new MyVehicle();
                                v.setOwner(obj.getString("username"));
                                v.setTypeName(obj.getString("name"));
                                Vehicle.add(v);
                                SOwner.add(v.getOwner());
                                SType.add(v.getTypeName());


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        //    adapterLine.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("e", "Error: " + error.getMessage());


            }

        });

        AppController.getInstance().addToRequestQueue(lineReq);

        //Owner
        ArrayAdapter<String> arrayAdapterdOwner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, SOwner);
        arrayAdapterdOwner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOwner.setAdapter(arrayAdapterdOwner);
        spinnerOwner.setOnItemSelectedListener(this);
        spinnerOwner.setPrompt("Select one ..");

        //Type
        ArrayAdapter<String> arrayAdapterdType = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, SType);
        arrayAdapterdType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(arrayAdapterdType);
        spinnerType.setOnItemSelectedListener(this);
        spinnerType.setPrompt("Select one ..");

    }

}
