package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quiz.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class PartidaOnline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida_online);
    }
}