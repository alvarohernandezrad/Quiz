package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.quiz.R;
import com.example.quiz.models.AuxiliarColores;
import com.example.quiz.models.Preferencias;

import java.util.Locale;

// Actividad de preferencias

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        setContentView(R.layout.activity_preferences);
    }
}