package com.abedkhan.server;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.abedkhan.server.databinding.ActivityMainBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;

    RequestQueue requestQueue;
    String name, username,mail;
    String url = "http://localhost/php/test/viewdata.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




                requestQueue = Volley.newRequestQueue(this);

//        handling insert btn
                binding.submitBtn.setOnClickListener(view -> {


                    name = binding.fullName.getText().toString().trim();
                    username = binding.username.getText().toString().trim();
                    mail = binding.email.getText().toString().trim();

                    if(name.isEmpty()){
                        binding.fullName.setError("Empty!!");
                    } else if (username.isEmpty() || mail.isEmpty()) {
                        binding.username.setError("Field empty!");
                    }
                    else {
                        saveDataToDataBase(name, username, mail);
                    }
                });

                binding.submitBtn.setOnClickListener(view -> {
                    startActivity(new Intent(MainActivity.this, ViewActivity.class));
                });


//        INSERT INTO user (SNo, Name, Age, Gender, Details, Date)
//        VALUES ('1', 'Osama Bin Hashim', '21', 'Male', 'Hello, this is Osama Bin Hashim. This is the test database of mine. ', current_timestamp());

            }

            private void saveDataToDataBase(String name, String username, String mail) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                        Log.i("TAG", "onResponse: " + response);
//                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                binding.submitBtn.setEnabled(true);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                Log.i("TAG", "onErrorResponse: "+error.getLocalizedMessage());
                        Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        binding.submitBtn.setEnabled(true);
                    }
                })
//            Post Main Part
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Name",name);
                        params.put("userName", username);
                        params.put("mail", mail);
                        return params;
                    }
                };

                requestQueue.add(stringRequest);






        
    }
}