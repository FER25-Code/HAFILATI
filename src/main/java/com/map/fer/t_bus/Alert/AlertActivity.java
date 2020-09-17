package com.map.fer.t_bus.Alert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.map.fer.t_bus.Line.MyLine;
import com.map.fer.t_bus.Login.LoginActivity;
import com.map.fer.t_bus.R;
import com.map.fer.t_bus.Station.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlertActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button b1;
    EditText editText;
    String url, type;
    Spinner spinner,spinnerLine;
    private ArrayList<MyAlert> alerts = new ArrayList<MyAlert>();
    List<String> TypAlert = new ArrayList<String>();
    private ArrayList<MyLine> Line = new ArrayList<MyLine>();
    List<String> LinsName = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_activity);
        editText = findViewById(R.id.editTextAlert);
        b1 = findViewById(R.id.buttonSalert);
        spinner = findViewById(R.id.spinnerAlert);
        spinnerLine = findViewById(R.id.spinnerline);
        TypAlert.add("Alert Type");
        LinsName.add("Line ");
        getAlert();
        LineSp();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo we need to change this
                url = getString(R.string.api_prefix)+"insert"+"/"+type;

                JsonArrayRequest stationReq = new JsonArrayRequest(url, // creat array
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Toast.makeText(AlertActivity.this, "" + url + ".....Insert ok" , Toast.LENGTH_LONG).show();
                                editText.setText(" ");
                                // notifying list adapter about data changes
                                // so that it renders the list view with updated data

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("tag", "Error: " + error.getMessage());
                        Toast.makeText(AlertActivity.this, "" + url + ".....Insert no" , Toast.LENGTH_LONG).show();


                    }

                });

                AppController.getInstance().addToRequestQueue(stationReq);
            }
        });

    }


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
       type = parent.getItemAtPosition(position).toString();
        // textInputLayout.setTextInputAccessibilityDelegate(text);
        if (position > 0) {
            // Notify the selected item text
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

    public void getAlert() {
        url = getString(R.string.api_prefix) +"alertPostype";
        JsonArrayRequest lineReq = new JsonArrayRequest(url, // creat array
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("d", response.toString());
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {    // get all inforations stocked in database
                            try {
                                JSONObject obj = response.getJSONObject(i); // get and set all valors
                                MyAlert v = new MyAlert();
                                v.setId(obj.getInt("id"));
                                v.setName(obj.getString("NAME"));
                                v.setDescription(obj.getString("descripton"));
                                v.setLevel(obj.getInt("level"));
                                alerts.add(v);
                                TypAlert.add(v.getDescription());
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
                android.R.layout.simple_spinner_item, TypAlert);
        arrayAdapterdOwner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterdOwner);
        spinner.setOnItemSelectedListener(this);
        spinner.setPrompt("Select one ..");
    }
    public void LineSp() {

        Toast.makeText(this, "Line Server", Toast.LENGTH_SHORT).show();
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
}
