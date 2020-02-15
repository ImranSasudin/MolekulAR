package com.example.molekular.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.molekular.EditNotesActivity;
import com.example.molekular.Model.Notes;
import com.example.molekular.R;
import com.example.molekular.ViewNotesActivity;

import java.util.List;

public class QuestionAdapter {

    /*List<Notes> noteslist;
    private Context context;
    public NotesAdapter(List<Notes> noteslist, Context context){
        this.context = context;
        this.noteslist = noteslist;
    }

    @NonNull
    @Override
    public NotesAdapter.MyHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notes_row,viewGroup,false);

        NotesAdapter.MyHolder myHolder = new NotesAdapter.MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder (@NonNull NotesAdapter.MyHolder myHolder, int position) {
        Notes notes = noteslist.get(position);

        myHolder.title.setText(notes.getTitle());
        //myHolder.description.setText(notes.getDescription());
    }

    @Override
    public int getItemCount() { return noteslist.size();}

    class MyHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyHolder (@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Notes notes = noteslist.get(getAdapterPosition());

                    final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
                    String role = sharedPref.getString("role", "");

                    Intent i = null;
                    if (role.equalsIgnoreCase("student")) {
                        i = new Intent(context, ViewNotesActivity.class);
                    } else if (role.equalsIgnoreCase("teacher")) {
                        i = new Intent(context, EditNotesActivity.class);
                    }

                    i.putExtra("id", notes.id);
                    i.putExtra("title", notes.title);
                    i.putExtra("description", notes.description);

                    context.startActivity(i);

                }
            });
        }
    }*/
}
