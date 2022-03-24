package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.quiz.R;
import com.example.quiz.models.AuxiliarColores;

import java.util.Locale;

// Actividad. Está actividad esta creada para que la notificación local que llega tenga sentido.

public class PreRanking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        setContentView(R.layout.activity_pre_ranking);
        int idNotificacion = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            idNotificacion = extras.getInt("idNotificacion");
        }
        Button boton = findViewById(R.id.botonPreRanking);
        ImageView imagen = findViewById(R.id.imagenPreRanking);
        boton.setText(R.string.tituloRanking);
        imagen.setImageResource(R.drawable.quizimagen);
        Intent intentRanking = new Intent(this, Ranking.class);
        intentRanking.putExtra("idNotificacion", idNotificacion);
        boton.setOnClickListener(view -> {
            startActivity(intentRanking);
            finish();
        });
    }
}