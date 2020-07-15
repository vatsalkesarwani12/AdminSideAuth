package com.vatsal.kesarwani.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.TextUtilsCompat;
import androidx.core.util.PatternsCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class NewUser extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private EditText username,email,password,cnpassword;
    private Button register;
    //private DatabaseReference df;
    private String susername,semail,spassword,scnpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        intit();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!check()){
                    return;
                }

                Toast.makeText(NewUser.this, "Successful", Toast.LENGTH_SHORT).show();
                Log.d("Working", semail+" "+spassword+" "+scnpassword);

                DataParam dataParam=new DataParam(semail,spassword,2);

                FirebaseDatabase.getInstance().getReference(AppConfig.base)
                        .child(susername)
                        .setValue(dataParam)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()){
                                    Toast.makeText(NewUser.this, "Error : "+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    sharedPreferences.edit()
                                            .putInt(AppConfig.STATE,2)
                                            .putString(AppConfig.EMAIL,semail)
                                            .putString(AppConfig.USERNAME,susername)
                                            .apply();

                                    Toast.makeText(NewUser.this, "Wait for the Notification for Access Grant!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),LoginUser.class));
                                }
                            }
                        });

            }
        });
    }

    private boolean check() {
        semail=email.getText().toString();
        spassword=password.getText().toString();
        scnpassword=cnpassword.getText().toString();
        susername=username.getText().toString();
        if (susername.length()<4){
            return false;
        }
        if (!PatternsCompat.EMAIL_ADDRESS.matcher(semail).matches()){
            return false;
        }
        if (spassword.length()<5){
            return false;
        }
        if (scnpassword.length()<5){
            return false;
        }
        if (!spassword.equals(scnpassword)){
            return false;
        }
        return true;
    }

    private void intit() {
        sharedPreferences=getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE);
        username=findViewById(R.id.name_new);
        email=findViewById(R.id.email_new);
        password=findViewById(R.id.password_new);
        cnpassword=findViewById(R.id.cnpassword_new);
        register=findViewById(R.id.register);
        //df=FirebaseDatabase.getInstance().getReference("NewUser");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}