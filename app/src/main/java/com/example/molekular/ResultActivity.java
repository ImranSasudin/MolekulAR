package com.example.molekular;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    TextView textid;
    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        textid = findViewById(R.id.text);
        homeButton = findViewById(R.id.home);
        Intent intent = getIntent();

        Integer getCorrect = intent.getIntExtra("correct", 0);
        Integer getTotalQ = intent.getIntExtra("totalQuestion", 0);

        Spanned text = Html.fromHtml("<b>RESULT<br/>CONGRATULATION!<br/>YOUR MARKS:<br/><br/>" + getCorrect + "/" + getTotalQ);
        textid.setText(text);

        homeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
    }
}
