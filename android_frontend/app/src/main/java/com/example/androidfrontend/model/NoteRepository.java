package com.example.androidfrontend.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Repository to save/load notes via SharedPreferences.
 */
public class NoteRepository {
    private static final String PREFS_NAME = "notes_prefs";
    private static final String NOTES_KEY = "notes";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public NoteRepository(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // PUBLIC_INTERFACE
    public List<Note> getNotes() {
        String json = sharedPreferences.getString(NOTES_KEY, "");
        if (json.isEmpty()) return new ArrayList<>();
        Type type = new TypeToken<ArrayList<Note>>() {}.getType();
        List<Note> notes = gson.fromJson(json, type);
        Collections.sort(notes, (a, b) -> Long.compare(b.getTimestamp(), a.getTimestamp()));
        return notes;
    }

    // PUBLIC_INTERFACE
    public void saveNotes(List<Note> notes) {
        String json = gson.toJson(notes);
        sharedPreferences.edit().putString(NOTES_KEY, json).apply();
    }
}
