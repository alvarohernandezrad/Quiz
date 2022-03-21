package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.example.quiz.R;
import com.example.quiz.database.MiDB;
import com.example.quiz.models.AuxiliarColores;


import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    final private MiDB database = new MiDB(this, "App", (SQLiteDatabase.CursorFactory) null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("paso", "pasoPorOnCreate");
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String color = prefs.getString("color", "normal");
        if(extras != null){
            color = extras.getString("color");
            if(color == null){
                color = prefs.getString("color", "normal");
            }
        }
        Log.d("paso", color);
        AuxiliarColores.setColor(color);
        AuxiliarColores.elegirColor(this);
        cargarPreferenciasDarkTheme();
        cargarPreferenciasIdioma();
        setContentView(R.layout.activity_main);

        ImageView imagen = findViewById(R.id.imagenInicio);
        Button botonMulti = findViewById(R.id.botonMultijugador);
        Button botonPreferencias = findViewById(R.id.botonPreferencias);
        Button botonGithub = findViewById(R.id.botonGithub);
        // Establecer texto en los botones
        botonMulti.setText(R.string.multijugador);
        botonPreferencias.setText(R.string.preferencias);
        botonGithub.setText(R.string.proyecto);

        // Settear la imagen de inicio desde drawable
        imagen.setImageResource(R.drawable.quizimagen);

        // Crear intents para diferentes botones
        // Boton multijugador (crear nueva partida)
        Intent intentMulti = new Intent(this, AddPlayers.class);
        Intent intentPreferences = new Intent(this, PreferencesActivity.class);
        Intent intentGithub = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/alvarohernandezrad"));
        Log.d("preguntas", String.valueOf(this.database.numeroPreguntas()));
        botonMulti.setOnClickListener(view -> {
            database.limpiarTablaJugadores();
            startActivity(intentMulti);
        });

        botonPreferencias.setOnClickListener(view -> startActivity(intentPreferences));

        botonGithub.setOnClickListener(view -> startActivity(intentGithub));
    }

    private void cargarPreferenciasDarkTheme() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean dark = prefs.getBoolean("temaOscuro", false);
        if (!dark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    private void cargarPreferenciasIdioma() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String actual = prefs.getString("idioma", "ES");
        String idioma = this.getResources().getConfiguration().locale.getDisplayLanguage();
        String ingles, castellano;
        boolean movilEnEspañol;
        if(idioma.equals("inglés") || idioma.equals("español")){
            movilEnEspañol = true;
        }else{
            movilEnEspañol = false;
        }
        if(movilEnEspañol){
            ingles = "inglés";
            castellano = "español";
        }else{
            ingles = "English";
            castellano = "Spanish";
        }
        Locale nuevaloc;
        if (actual.equals("ES") && !this.getResources().getConfiguration().locale.getDisplayLanguage().equals(castellano)){
            nuevaloc = new Locale(actual.toLowerCase());
        }else if(actual.equals("EN") && !this.getResources().getConfiguration().locale.getDisplayLanguage().equals(ingles)){
            nuevaloc = new Locale(actual.toLowerCase());
        }else{
            return;
        }
        Configuration configuration = this.getResources().getConfiguration();
        Locale.setDefault(nuevaloc);
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);
        Context context = this.createConfigurationContext(configuration);
        this.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        finish();
        startActivity(getIntent());
    }

    private void cargarPreferenciasColor(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String color = prefs.getString("color", "normal");
        Log.d("paso", color);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("color", color);
        startActivity(i);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        cargarPreferenciasColor();
        cargarPreferenciasIdioma();
    }


}