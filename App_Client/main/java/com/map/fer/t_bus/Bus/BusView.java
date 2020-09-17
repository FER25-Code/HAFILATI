package com.map.fer.t_bus.Bus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.baoyachi.stepview.VerticalStepView;
import com.map.fer.t_bus.Line.LineStation;
import com.map.fer.t_bus.Line.MyLine;
import com.map.fer.t_bus.R;
import com.map.fer.t_bus.Station.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BusView extends AppCompatActivity {
    private ProgressDialog pDialog;
    private final String TAG = "Line";
    String url;
    private ArrayList<MyLine> list0 = new ArrayList<MyLine>();
    MyLine s = new MyLine();
    List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_view_activity);
        Intent i =getIntent();
        Bundle bundle = i.getExtras();
        int idLine=bundle.getInt("idline");
        final VerticalStepView mSetpview =  findViewById(R.id.step_viewLinebus);
        url = getString(R.string.api_prefix)+"stationbyline"+"/"+idLine;
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
                                s.setName(obj.getString("name"));
                                list0.add(s);
                                list.add(s.getName());
                                mSetpview.
                                        setStepsViewIndicatorComplectingPosition(list0.size()-1)//设置完成的步数
                                        .reverseDraw(false)//default is true
                                        .setStepViewTexts(list)//总步骤
                                        .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                                        .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(BusView.this, android.R.color.black))//设置StepsViewIndicator完成线的颜色
                                        .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(BusView.this, R.color.black))//设置StepsViewIndicator未完成线的颜色
                                        .setStepViewComplectedTextColor(ContextCompat.getColor(BusView.this, android.R.color.black))//设置StepsView text完成线的颜色
                                        .setStepViewUnComplectedTextColor(ContextCompat.getColor(BusView.this, R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(BusView.this, R.drawable.busstop))//设置StepsViewIndicator CompleteIcon
                                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(BusView.this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                                 .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(BusView.this, R.drawable.busstop))
                                ;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapterbus about data changes
                        // so that it renders the list view with updated data

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