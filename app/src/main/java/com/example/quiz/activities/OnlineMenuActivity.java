package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quiz.R;
import com.example.quiz.models.AuxiliarColores;

public class OnlineMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        setContentView(R.layout.activity_online_menu);
    }
}