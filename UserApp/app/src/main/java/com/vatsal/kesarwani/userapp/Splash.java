package com.vatsal.kesarwani.userapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences=getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE);

        final int state=sharedPreferences.getInt(AppConfig.STATE,1);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Splash.this, "Welcome 🙏", Toast.LENGTH_SHORT).show();
                if(state>=2){
                    startActivity(new Intent(getApplicationContext(),LoginUser.class));
                }
                else startActivity(new Intent(getApplicationContext(),NewUser.class));
            }
        },2000);
    }
}