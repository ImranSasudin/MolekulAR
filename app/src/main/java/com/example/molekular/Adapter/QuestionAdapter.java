package com.example.molekular.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.molekular.EditNotesActivity;
import com.example.molekular.Model.Notes;
import com.example.molekular.Model.Question;
import com.example.molekular.R;
import com.example.molekular.ViewNotesActivity;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyHolder> {

    List<Question> questionList;
    private Context context;
    public QuestionAdapter(List<Question> questionList, Context context){
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionAdapter.MyHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.question_row,viewGroup,false);

        QuestionAdapter.MyHolder myHolder = new QuestionAdapter.MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder (@NonNull QuestionAdapter.MyHolder myHolder, int position) {
        Question question = questionList.get(position);

        myHolder.title.setText(question.getQuestion());
        //myHolder.description.setText(notes.getDescription());
    }

    @Override
    public int getItemCount() { return questionList.size();}

    class MyHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyHolder (@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Question question = questionList.get(getAdapterPosition());

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
                    builder.setTitle("Answers")
                            .setView(webView)
                            .setNeutralButton("OK", null)
                            .show();
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
