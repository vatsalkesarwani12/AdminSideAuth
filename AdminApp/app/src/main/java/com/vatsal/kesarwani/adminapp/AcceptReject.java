package com.vatsal.kesarwani.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AcceptReject extends AppCompatActivity {
    private Intent intent;
    private TextView email;
    private Button allow,pend;
    private FirebaseAuth mAuth;
    private String semail,spassword;
    private static final String TAG = "AcceptReject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_reject);

        init();

        pend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
    }

    private void signin() {

        mAuth.createUserWithEmailAndPassword(semail, spassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(AcceptReject.this, "UserAccount Created", Toast.LENGTH_SHORT).show();
                            updateDB();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AcceptReject.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    private void updateDB() {
        final DatabaseReference df= FirebaseDatabase.getInstance().getReference("NewUser");
                df.orderByChild("Email")
                .equalTo(semail)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()){
                            String key=data.getKey();
                            df.child(key).child("Status").setValue(3);
                        }
                        Toast.makeText(AcceptReject.this, "Status Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Request.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AcceptReject.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void init() {
        intent=getIntent();
        email=findViewById(R.id.email_user);
        email.setText(intent.getStringExtra("mail"));
        allow=findViewById(R.id.allow);
        pend=findViewById(R.id.pend);
        mAuth=FirebaseAuth.getInstance();
        semail=intent.getStringExtra("mail");
        spassword=intent.getStringExtra("pass");
    }
}