package com.example.cabxury;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class DriverLoginRegisterActivity extends AppCompatActivity {

    EditText email, pass;
    Button btn,dsignbtn;
    String semail, spassword;
    String url = "https://cabxury.000webhostapp.com/driverlogin.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_register);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        btn = (Button) findViewById(R.id.dlogbtn);
        dsignbtn= findViewById(R.id.dsignbtn);

        dsignbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent y= new Intent(DriverLoginRegisterActivity.this,driver_signup.class);
                startActivity(y);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                semail = email.getText().toString().trim();
                spassword = pass.getText().toString().trim();

                StringRequest rq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Login successfully"))
                        {
                            Toast.makeText(DriverLoginRegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                            Intent maps = new Intent(DriverLoginRegisterActivity.this, MapsMarkerActivity.class);
                            startActivity(maps);
                        }
                        else {
                            Toast.makeText(DriverLoginRegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DriverLoginRegisterActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map getParams() throws AuthFailureError {
                        //return super.getParams();
                        Map para = new HashMap();

                        para.put("email", semail);
                        para.put("pass", spassword);
                        return para;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(DriverLoginRegisterActivity.this);
                requestQueue.add(rq);

            }
        });

    }
}
