package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quiz.R;
import com.example.quiz.models.AuxiliarColores;
import com.example.quiz.models.Preferencias;

// Actividad de preferencias

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        setContentView(R.layout.activity_preferences);
    }
}