package com.map.fer.t_bus.Evaluation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.map.fer.t_bus.R;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.map.fer.t_bus.Station.AppController;
import org.json.JSONArray;



public class Evaluation  extends AppCompatActivity {

        EditText bus;
        Button b1;
        String url ;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.evaluation_activity);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        bus=findViewById(R.id.bus);
final RatingBar simpleRatingBar = (RatingBar)findViewById(R.id.ratingBar);
        simpleRatingBar.setBackgroundColor(Color.GRAY); // set background color for a rating bar
        b1 = findViewById(R.id.buttonSubmit);
        b1.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        //Todo we need to change this
        url = getString(R.string.api_prefix)+"/"+"api/"+"evaluate"+"/"+1+"/"+bus.getText()+"/"+simpleRatingBar.getRating();
        Log.e("url", " "+url);
        JsonArrayRequest stationReq = new JsonArrayRequest(url, // creat array
        new Response.Listener<JSONArray>() {
@Override
public void onResponse(JSONArray response) {
        //Toast.makeText(AlertActivity.this, "" + url + ".....Insert ok" , Toast.LENGTH_LONG).show();
         bus.setText(" ");
        // notifying list adapter about data changes
        // so that it renders the list view with updated data
        bus.setText(" ");

        }
        }, new Response.ErrorListener() {
@Override
public void onErrorResponse(VolleyError error) {



        }

        });

        AppController.getInstance().addToRequestQueue(stationReq);
        }
        });

        }








        }


