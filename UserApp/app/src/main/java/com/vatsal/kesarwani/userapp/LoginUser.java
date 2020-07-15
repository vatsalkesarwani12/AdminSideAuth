package com.vatsal.kesarwani.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginUser extends AppCompatActivity {

    private TextView username,email;
    private EditText password;
    private Button login;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private String spassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        init();

        username.setText(sharedPreferences.getString(AppConfig.USERNAME,""));
        email.setText(sharedPreferences.getString(AppConfig.EMAIL,""));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check()){
                    return;
                }

                loginInto();
            }
        });
    }

    private void loginInto() {

        mAuth.signInWithEmailAndPassword(sharedPreferences.getString(AppConfig.EMAIL,""),spassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginUser.this, "Login In Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }
                        else{
                            Toast.makeText(LoginUser.this, "Invalid Cred "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean check() {
        spassword=password.getText().toString();
        return password.length() >= 5;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }

        FirebaseDatabase.getInstance().getReference(AppConfig.base)
                .orderByChild("Email")
                .equalTo(sharedPreferences.getString(AppConfig.EMAIL,""))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            DataParam dataParam=dataSnapshot.getValue(DataParam.class);
                            sharedPreferences.edit()
                                    .putInt(AppConfig.STATE,dataParam.Status)
                                    .apply();
                            if (dataParam.Status == 3){
                                login.setEnabled(true);
                            }
                            else {
                                Toast.makeText(LoginUser.this, "Wait for the notification..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Database status : ","cancelled "+error.getMessage());
                    }
                });
        /*Toast.makeText(this, "status: "+sharedPreferences.getInt(AppConfig.STATE,2), Toast.LENGTH_SHORT).show();
        if(sharedPreferences.getInt(AppConfig.STATE,2) == 3){
            login.setEnabled(true);
        }*/
    }

    private void init() {
        username=findViewById(R.id.username_login);
        email=findViewById(R.id.email_login);
        password=findViewById(R.id.password_login);
        login=findViewById(R.id.login);
        sharedPreferences=getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE);
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}