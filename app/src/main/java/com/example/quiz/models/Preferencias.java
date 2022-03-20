package com.example.quiz.models;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.quiz.R;

public class Preferencias extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s){
        addPreferencesFromResource(R.xml.pref_config);
    }
}
