package com.example.molekular;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.molekular.Model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizStudentActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    RadioGroup radioGroup;
    RadioButton radio1, radio2, radio3, radio4;
    TextView questionText;
    Button nextQ, quit;
    private Context context;

    List<Question> questions = new ArrayList<>();
    int num = 0;
    String getCorrectAnswer= "";
    int correct = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);



        nextQ = findViewById(R.id.buttonnext);
        quit = findViewById(R.id.buttonquit);

        questionText = findViewById(R.id.question);

        radioGroup = findViewById(R.id.answersgrp);
        radio1 = findViewById(R.id.radioButton1);
        radio2 = findViewById(R.id.radioButton2);
        radio3 = findViewById(R.id.radioButton3);
        radio4 = findViewById(R.id.radioButton4);

        progressDialog = new ProgressDialog(this);
        // Loading bar
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Questions");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final int index = (int) dataSnapshot.getChildrenCount();
                if (dataSnapshot != null) {
                    /*Random random = new Random();
                    int index = random.nextInt((int) dataSnapshot.getChildrenCount());
                    int count = 0;
                    int i = 0;*/

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                         Question questions1 = snapshot.getValue(Question.class);
                         questions.add(questions1);
//                        questionArr.add(question.getQuestion());
//                        choice1Arr.add(question.getChoice1());
//                        choice2Arr.add(question.getChoice2());
//                        choice3Arr.add(question.getChoice3());
//                        choice4Arr.add(question.getChoice4());
//                        correctAnswerArr.add(question.getCorrectAnswer());
//
//                        // i++;
                    }
                    progressDialog.dismiss();
                }

                final Random random = new Random();
                System.out.println("HEREEEEEEEEEE: " + index);

                final String[] questionArr = new String[index];
                final String[] choice1Arr = new String[index];
                final String[] choice2Arr = new String[index];
                final String[] choice3Arr = new String[index];
                final String[] choice4Arr = new String[index];
                final String[] questionAnswerArr = new String[index];

                int i = 0;
                for (Question q:questions){
                    questionArr[i] = q.getQuestion();
                    choice1Arr[i] = q.getChoice1();
                    choice2Arr[i] = q.getChoice2();
                    choice3Arr[i] = q.getChoice3();
                    choice4Arr[i] = q.getChoice4();
                    questionAnswerArr[i] = q.getCorrectAnswer();

                    i++;
                }


                questionText.setText(questionArr[num]);
                radio1.setText(choice1Arr[num]);
                radio2.setText(choice2Arr[num]);
                radio3.setText(choice3Arr[num]);
                radio4.setText(choice4Arr[num]);

                nextQ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (radioGroup.getCheckedRadioButtonId() == -1) {
                            Toast.makeText(getApplicationContext(), "Select one answer", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        RadioButton ans =  findViewById(radioGroup.getCheckedRadioButtonId());
                        String ansText = ans.getText().toString();


                       // int questionNum = 1;
                        if(ansText.equals(questionAnswerArr[num])){
                            correct++;
                            System.out.println("SATUUUU" + correct);
                          //  questionNum++;
                            Toast.makeText(getApplicationContext(), "Congratulation! You answered correctly!", Toast.LENGTH_SHORT).show();

                        }
                        else{
                           // questionNum++;
                            Toast.makeText(getApplicationContext(), "Oh no! Your answer is wrong.", Toast.LENGTH_SHORT).show();
                        }
                        radioGroup.clearCheck();
                        num++;
                        System.out.println("DUAAAAA" + correct);

                        if(num < index){

                            questionText.setText(questionArr[num]);
                            radio1.setText(choice1Arr[num]);
                            radio2.setText(choice2Arr[num]);
                            radio3.setText(choice3Arr[num]);
                            radio4.setText(choice4Arr[num]);
                        }
                        else{
                            System.out.println("TIGAAAAA" + correct);
                            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),ResultActivity.class);

                            intent.putExtra("correct", correct);
                            intent.putExtra("totalQuestion", index);

                            startActivity(intent);
                        }


                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

            }
        });
    }
}
