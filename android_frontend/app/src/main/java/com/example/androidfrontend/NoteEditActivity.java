package com.example.androidfrontend;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidfrontend.model.Note;
import com.example.androidfrontend.model.NoteRepository;

/**
 * Activity for creating or editing a note.
 */
public class NoteEditActivity extends AppCompatActivity {
    private EditText noteTitle, noteContent;
    private ImageButton saveBtn;
    private NoteRepository repository;
    private Note editingNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_LightNoteApp);
        setContentView(R.layout.activity_note_edit);

        noteTitle = findViewById(R.id.editNoteTitle);
        noteContent = findViewById(R.id.editNoteContent);
        saveBtn = findViewById(R.id.saveNoteBtn);

        repository = new NoteRepository(this);

        if (getIntent().hasExtra("note")) {
            editingNote = (Note) getIntent().getSerializableExtra("note");
            noteTitle.setText(editingNote.getTitle());
            noteContent.setText(editingNote.getContent());
        }

        saveBtn.setOnClickListener(v -> {
            String title = noteTitle.getText().toString().trim();
            String content = noteContent.getText().toString().trim();

            if (TextUtils.isEmpty(title)) {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (editingNote == null) {
                Note note = new Note(title, content);
                java.util.List<com.example.androidfrontend.model.Note> notes = repository.getNotes();
                notes.add(note);
                repository.saveNotes(notes);
            } else {
                editingNote.setTitle(title);
                editingNote.setContent(content);
                java.util.List<com.example.androidfrontend.model.Note> notes = repository.getNotes();
                for (int i = 0; i < notes.size(); i++) {
                    if (notes.get(i).getId().equals(editingNote.getId())) {
                        notes.set(i, editingNote);
                        break;
                    }
                }
                repository.saveNotes(notes);
            }
            finish();
        });
    }
}
