package com.vatsal.kesarwani.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Splash.this, "Welcome 🙏", Toast.LENGTH_SHORT).show();
                if (FirebaseAuth.getInstance().getCurrentUser()==null)
                    startActivity(new Intent(getApplicationContext(),SignIn.class));
                else
                    startActivity(new Intent(getApplicationContext(),Request.class));
            }
        }, 2000);
    }
}