package com.abedkhan.server;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.abedkhan.server.databinding.ActivityViewBinding;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewActivity extends AppCompatActivity {
    ActivityViewBinding binding;
    RequestQueue requestQueue;
    String url = "http://localhost/php/test/viewdata.php";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



  //      Sending GET request to the server
        JsonArrayRequest arrayRequest = new JsonArrayRequest (Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("TAG", "Length is: " + response.length());
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    //  getting data from the server
                                    JSONObject json0bject = response.getJSONObject(i);
//                                    String id = jsonObject.gettring("id");

                                    String id = String.valueOf(json0bject.getInt("id"));
                                    String name = json0bject.getString("Name");
                                    String username = json0bject.getString("username");
                                    String mail = json0bject.getString("mail");


                                    Log.i("TAG", "Responded data-> id: " + id + " name: " + name + "username" + username + " mail: " + mail);

                                //    binding.fullName.append("->ID: " + id + " name: " + name + "username" + username + " mail: " + mail + "\n\n");



                                    binding.fullName.append(" name: " + name);
                                    binding.username.append( "username" + username );
                                    binding.email.append(" mail: " + mail );
                                } catch (Exception e) {
                                    binding.fullName.append(e.getLocalizedMessage());
                                }
                            }
                        }
                    }
                },
                                    new Response. ErrorListener() {

                                        @Override
                                        public void onErrorResponse (VolleyError error) {
                                            Log.i("TAG", "onErrorResponse: " + error.getLocalizedMessage());
                                            binding.fullName.append(error.getLocalizedMessage());
                                            binding.email.append(error.getLocalizedMessage());
                                            binding.username.append(error.getLocalizedMessage());

                                        }
                                        });
                                        requestQueue = Volley.newRequestQueue( this);
                                       requestQueue.add(arrayRequest);



      }
 }

