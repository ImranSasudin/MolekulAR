package com.example.molekular;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.molekular.Model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuizActivity extends AppCompatActivity {
    Button submit;
    EditText questionText, choice1Text, choice2Text, choice3Text, choice4Text;
    RadioGroup radioGroup;
    RadioButton radio1, radio2, radio3, radio4;

    String question, choice1, choice2, choice3, choice4, correctAnswer;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_quiz);

        submit = findViewById(R.id.submit_btn);
        questionText = findViewById(R.id.question);

        choice1Text = findViewById(R.id.choice_1);
        choice2Text = findViewById(R.id.choice_2);
        choice3Text = findViewById(R.id.choice_3);
        choice4Text = findViewById(R.id.choice_4);

        radioGroup = findViewById(R.id.answersgrp);
        radio1 = findViewById(R.id.radioButton1);
        radio2 = findViewById(R.id.radioButton2);
        radio3 = findViewById(R.id.radioButton3);
        radio4 = findViewById(R.id.radioButton4);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question = questionText.getText().toString();

                choice1 = choice1Text.getText().toString();
                choice2 = choice2Text.getText().toString();
                choice3 = choice3Text.getText().toString();
                choice4 = choice4Text.getText().toString();

                if(TextUtils.isEmpty(question)){
                    questionText.setError("Question is required");
                    questionText.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(choice1)){
                    choice1Text.setError("Choice 1 is required");
                    choice1Text.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(choice2)){
                    choice2Text.setError("Choice 2 is required");
                    choice2Text.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(choice3)){
                    choice3Text.setError("Choice 3 is required");
                    choice3Text.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(choice4)){
                    choice4Text.setError("Choice 4 is required");
                    choice4Text.requestFocus();
                    return;
                }
                if(radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "Please select the correct answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(radio1.isChecked()){
                    correctAnswer = choice1;
                }
                else if(radio2.isChecked()){
                    correctAnswer = choice2;
                }
                else if(radio3.isChecked()){
                    correctAnswer = choice3;
                }
                else if(radio4.isChecked()){
                    correctAnswer = choice4;
                }

                String id = databaseReference.push().getKey();
                Question questionQ = new Question(id, question, choice1, choice2, choice3, choice4, correctAnswer);
                databaseReference.child("Questions").child(id).setValue(questionQ).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(AddQuizActivity.this,"Question Added", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                                }
                            });

            }
        });
    }


}
