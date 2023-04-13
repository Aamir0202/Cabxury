package com.example.cabxury;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class driver_signup extends AppCompatActivity {

    EditText email, pass, name, phone_number,model;
    String semail, spassword, sname, sphone_number,smodel;
    Button driver_sign_up_btn;
    String url = "https://cabxury.000webhostapp.com/driversignup.php";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_signup);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        phone_number = (EditText) findViewById(R.id.phone_number);
        model = (EditText) findViewById(R.id.model_number);

        driver_sign_up_btn = (Button) findViewById(R.id.dconfirmbtn);

        driver_sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                semail = email.getText().toString().trim();
                spassword = pass.getText().toString().trim();
                sname = name.getText().toString().trim();
                sphone_number = phone_number.getText().toString().trim();
                smodel = model.getText().toString().trim();


                StringRequest rq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(driver_signup.this, response, Toast.LENGTH_SHORT).show();
                        Intent maps = new Intent(driver_signup.this, MapsMarkerActivity.class);
                        startActivity(maps);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(driver_signup.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map getParams() throws AuthFailureError {
                        //return super.getParams();
                        Map<String, String> para = new HashMap<String, String>();

                        para.put("name", sname);
                        para.put("email", semail);
                        para.put("phone_number", sphone_number);
                        para.put("pass", spassword);
                        para.put("car_model", smodel);
                        return para;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(driver_signup.this);
                requestQueue.add(rq);

            }
        });

    }


}