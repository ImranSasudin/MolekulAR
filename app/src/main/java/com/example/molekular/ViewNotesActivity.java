package com.example.molekular;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.molekular.Model.Notes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewNotesActivity extends AppCompatActivity {

    TextView titleText, descriptionText;
    String title,description;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_notes_activity);

        titleText =  findViewById(R.id.title);
        descriptionText = findViewById(R.id.description);

        final Intent i = getIntent();

        final String getId = i.getStringExtra("id");
        String getTitle = i.getStringExtra("title");
        String getDescription = i.getStringExtra("description");

        databaseReference = FirebaseDatabase.getInstance().getReference();

        titleText.setText(getTitle);
        descriptionText.setText(getDescription);

    }


    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(),NotesActivity.class));

    }
}
