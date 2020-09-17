package com.map.fer.t_bus.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.map.fer.t_bus.MainActivity;
import com.map.fer.t_bus.R;
import com.map.fer.t_bus.Station.AppController;

import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity {
    EditText firstname, lastname,password,confrimpassword,email;
    Button login;
    String Fname, Lname,Pword,CPword,Email,url,usern="univ2",rol="User";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        firstname=findViewById(R.id.editText2);
        lastname=findViewById(R.id.editText5);
        password=findViewById(R.id.editText7);
        confrimpassword=findViewById(R.id.editText3);
        email=findViewById(R.id.editText8);
        login=findViewById(R.id.btnjoin);
        SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        String firstTime =preferences.getString("FirstTimeInstall","");
        if(firstTime.equals("Yes")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }else {
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("FirstTimeInstall","Yes");
            editor.apply();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fname = String.valueOf(firstname.getText());
                Lname = String.valueOf(lastname.getText());
                Pword =  String.valueOf(password.getText());
                CPword= String.valueOf(confrimpassword.getText());
                Email= String.valueOf(email.getText());
             //Todo we need to change this
                 url = getString(R.string.api_prefix)+"insertuser"+"/"+usern+"/"+Fname+"/"+Lname+"/"+Email+"/"+Pword;

                JsonArrayRequest stationReq = new JsonArrayRequest(url, // creat array
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Toast.makeText(LoginActivity.this, "" + url + ".....Insert ok" , Toast.LENGTH_LONG).show();

                                // notifying list adapter about data changes
                                // so that it renders the list view with updated data
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("tag", "Error: " + error.getMessage());
                        Toast.makeText(LoginActivity.this, "" + url + ".....Insert no" , Toast.LENGTH_LONG).show();

                    }

                });

                AppController.getInstance().addToRequestQueue(stationReq);
            }
        });

    }
}
