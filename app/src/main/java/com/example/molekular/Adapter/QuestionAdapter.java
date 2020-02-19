package com.example.molekular.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.molekular.EditNotesActivity;
import com.example.molekular.HomeActivity;
import com.example.molekular.Model.Notes;
import com.example.molekular.Model.Question;
import com.example.molekular.NotesActivity;
import com.example.molekular.QuizActivity;
import com.example.molekular.R;
import com.example.molekular.ViewNotesActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyHolder> {
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    List<Question> questionList;
    private Context context;

    public QuestionAdapter(List<Question> questionList, Context context) {
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.question_row, viewGroup, false);

        QuestionAdapter.MyHolder myHolder = new QuestionAdapter.MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.MyHolder myHolder, int position) {
        Question question = questionList.get(position);

        myHolder.title.setText(question.getQuestion());
        //myHolder.description.setText(notes.getDescription());
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Question question = questionList.get(getAdapterPosition());

                    final String id = question.getId();
                    String choice1 = question.choice1;
                    String choice2 = question.choice2;
                    String choice3 = question.choice3;
                    String choice4 = question.choice4;
                    String correctAnswer = question.correctAnswer;

                    String str = "<html><body><ul><li>" + choice1 + "</li><li>" + choice2 + "</li><li>" + choice3 + "</li><li>" + choice4 + "</li></ul><br>Correct Answer: " + correctAnswer;
                    // create a WebView with the current stats
                    WebView webView = new WebView(context);
                    webView.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);

                    // display the WebView in an AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Details");
                    builder.setView(webView);
                    builder.setCancelable(true);

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            progressDialog = new ProgressDialog(context);
                            // Loading bar
                            progressDialog.setMessage("Please Wait...");
                            progressDialog.show();

                            databaseReference = FirebaseDatabase.getInstance().getReference();

                            databaseReference.child("Questions").child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context.getApplicationContext(), "Question Deleted", Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, QuizActivity.class));
                                }
                            });
                        }
                    });

                    builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Do something when want to stay in the app

                        }
                    });

                    // Create the alert dialog using alert dialog builder
                    AlertDialog dialog = builder.create();

                    // Finally, display the dialog when user press back button
                    dialog.show();
                    /*Intent i = new Intent(context, EditNotesActivity.class);
                    i.putExtra("id", notes.id);
                    i.putExtra("title", notes.title);
                    i.putExtra("description", notes.description);

                    context.startActivity(i);*/

                }
            });
        }
    }
}
