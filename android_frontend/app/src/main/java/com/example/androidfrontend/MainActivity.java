package com.example.androidfrontend;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.androidfrontend.model.Note;
import com.example.androidfrontend.model.NoteRepository;
import com.example.androidfrontend.ui.NoteAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnNoteClickListener {
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteRepository repository;
    private List<Note> allNotes;
    private EditText searchBar;
    private ImageButton addNoteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_LightNoteApp); // Apply light minimal custom theme
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.notesRecyclerView);
        searchBar = findViewById(R.id.searchBar);
        addNoteBtn = findViewById(R.id.addNoteBtn);

        repository = new NoteRepository(this);
        allNotes = repository.getNotes();

        adapter = new NoteAdapter(allNotes, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addNoteBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoteEditActivity.class);
            startActivity(intent);
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterNotes(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        allNotes = repository.getNotes();
        adapter.setNotes(allNotes);
        filterNotes(searchBar.getText().toString());
    }

    private void filterNotes(String query) {
        if (query == null || query.trim().isEmpty()) {
            adapter.setNotes(allNotes);
            return;
        }
        List<Note> filtered = new ArrayList<>();
        for (Note note : allNotes) {
            if (note.getTitle().toLowerCase().contains(query.toLowerCase())
                    || note.getContent().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(note);
            }
        }
        adapter.setNotes(filtered);
    }

    @Override
    public void onNoteClick(Note note) {
        Intent intent = new Intent(this, NoteViewActivity.class);
        intent.putExtra("note", note);
        startActivity(intent);
    }

    @Override
    public void onNoteLongClick(Note note) {
        new AlertDialog.Builder(this)
            .setTitle("Delete note?")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Delete", (dialog, which) -> {
                allNotes.removeIf(n -> n.getId().equals(note.getId()));
                repository.saveNotes(allNotes);
                filterNotes(searchBar.getText().toString());
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
}
