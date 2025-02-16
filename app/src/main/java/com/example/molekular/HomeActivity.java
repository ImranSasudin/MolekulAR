package com.example.molekular;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    Button quiz, notes, scanatom;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        final String role = sharedPref.getString("role", "");

        quiz = findViewById(R.id.quiz_btn);
        notes = findViewById(R.id.notes_btn);
        scanatom = findViewById(R.id.scanatom_btn);

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),NotesActivity.class));
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (role.equalsIgnoreCase("student")) {
                    startActivity(new Intent(getApplicationContext(),QuizStudentActivity.class));
                } else if (role.equalsIgnoreCase("teacher")) {
                    startActivity(new Intent(getApplicationContext(),QuizActivity.class));
                }

            }
        });
    }



    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            HomeActivity.super.onBackPressed();
           //this.finish();
            //System.exit(0);
             return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
