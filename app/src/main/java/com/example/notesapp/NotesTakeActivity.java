package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.notesapp.Model.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakeActivity extends AppCompatActivity {
    EditText titleEdt, notesEdt;
    ImageView saveBtn;
    Notes notes;

    boolean isOldNotes = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes_take);

        saveBtn = findViewById(R.id.savebtn);
        titleEdt = findViewById(R.id.titleEdt);
        notesEdt = findViewById(R.id.noteEdt);

        notes = new Notes();
        try {
          notes = (Notes) getIntent().getSerializableExtra("old_notes");
          titleEdt.setText(notes.getTitle());
          notesEdt.setText(notes.getNotes());
          isOldNotes = true;
        }catch (Exception e){
           e.printStackTrace();
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isOldNotes) {
                    notes = new Notes();
                }
                String title = titleEdt.getText().toString();
                String description = notesEdt.getText().toString();
                if (description.isEmpty()){
                    Toast.makeText(NotesTakeActivity.this, "Please enter the description", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                notes.setTitle(title);
                notes.setNotes(description);
                notes.setDate(format.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", notes);
                setResult(NotesTakeActivity.RESULT_OK, intent);
                finish();
            }
        });
    }
}