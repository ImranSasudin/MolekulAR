package com.example.molekular;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.molekular.Model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText usernameText, passwordText;
    Button loginButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        // Assign input from view
        usernameText=findViewById(R.id.username);
        passwordText=findViewById(R.id.password);
        loginButton=findViewById(R.id.login_btn);

        // If login button clicked
        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // Assign string from text(view)
                final String username = usernameText.getText().toString().trim();
                final String password = passwordText.getText().toString().trim();

                System.out.println(username);
                System.out.println(password);

                // Prompt error if input is/are empty
                if (TextUtils.isEmpty(username)){
                    usernameText.setError("Username is required");
                    usernameText.requestFocus();
                }
                else if (TextUtils.isEmpty(password)){
                    passwordText.setError("Password is required");
                    passwordText.requestFocus();
                }
                else{
                    // Loading bar
                    progressDialog.setMessage("Logging in...");
                    progressDialog.show();

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference().child("Account").child(username);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Username exist
                            if (dataSnapshot.exists()){

                                Account account = dataSnapshot.getValue(Account.class);

                                String userPassword = account.getPassword();

                                // Password betul
                                if(userPassword.equalsIgnoreCase(password)){

                                    System.out.println("Success Login");
                                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                    startActivity(new Intent(getApplicationContext(),SplashActivity.class));
                                }
                                else{
                                    // Password salah
                                    System.out.println("Password salah");
                                    Toast.makeText(MainActivity.this, "Login failed.. Your username/password is incorrect", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }


                            }
                            else {
                                // Username not exist
                                System.out.println("Id not exist");
                                Toast.makeText(MainActivity.this, "Login failed.. Your username/password is incorrect", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                        }
                    });
                }
            }
        });


    }
}
