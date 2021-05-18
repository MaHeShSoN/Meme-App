package com.exampley.imusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.android.volley.*;


import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.exampley.imusicapp.MySingleton;
import com.exampley.imusicapp.R;

import org.json.*;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String currentUrl = "";
    public String properUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMeam();
    }
    private void loadMeam(){

        setProgress(View.VISIBLE);

        currentUrl="https://meme-api.herokuapp.com/gimme";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, currentUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String url = response.getString("url");
                            properUrl = url;
                            ImageView imageView2  = (ImageView)findViewById(R.id.imageView2);
                            Glide.with(MainActivity.this).load(url).into(imageView2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Image is not valid", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(MainActivity.this, "erroe in app", Toast.LENGTH_SHORT).show();
                        Log.d("Falis","Responce is   "+ error);
                    }
                });

        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);

    }

    public void nextMeam(View view) {
        try {
            loadMeam();
        }
        catch (Exception e){
            Toast.makeText(this, " cannot use next button", Toast.LENGTH_SHORT).show();
        }

    }

    public void shareMeme(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"Hey , Checkout this cool meme I got from Reddit "+ properUrl);
        Intent chosser =Intent.createChooser(intent, "Share this meme using ");
        startActivity(chosser);

    }
}