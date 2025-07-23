package com.example.androidfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidfrontend.model.Note;

/**
 * Activity for viewing a note.
 */
public class NoteViewActivity extends AppCompatActivity {
    private TextView titleView, contentView;
    private ImageButton editBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_LightNoteApp);
        setContentView(R.layout.activity_note_view);

        titleView = findViewById(R.id.viewNoteTitle);
        contentView = findViewById(R.id.viewNoteContent);
        editBtn = findViewById(R.id.editBtn);
        backBtn = findViewById(R.id.backBtn);

        Note note = (Note) getIntent().getSerializableExtra("note");
        if (note != null) {
            titleView.setText(note.getTitle());
            contentView.setText(note.getContent());
        }

        editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoteEditActivity.class);
            intent.putExtra("note", note);
            startActivity(intent);
            finish();
        });
        backBtn.setOnClickListener(v -> finish());
    }
}
