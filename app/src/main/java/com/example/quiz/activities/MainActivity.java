package com.example.quiz.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.example.quiz.R;
import com.example.quiz.database.MiDB;
import com.example.quiz.models.AuxiliarColores;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.Locale;

// Actividad principal, con la que se inicia la aplicación

public class MainActivity extends AppCompatActivity {

    final private MiDB database = new MiDB(this, "App", (SQLiteDatabase.CursorFactory) null, 1);

    private static String caca = "en";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        // Comprobamos que color hay guardado en las preferencias del jugador
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String color = prefs.getString("color", "normal");
        /* Si el jugador ha cambiado el color de preferencia en la ejecución actual de la aplicación se llamará a un
        intent de esta misma aplicación pasándole el color seleccionado */
        if(extras != null){
            color = extras.getString("color");
            if(color == null){
                color = prefs.getString("color", "normal");
            }
        }
        // Seteamos en la clase Auxiliar el color elegido y lo establecemos en la aplicación.
        AuxiliarColores.setColor(color);
        AuxiliarColores.elegirColor(this);
        // Cargamos también las preferencias de Tema Oscuro e Idioma
        cargarPreferenciasDarkTheme();
        cargarPreferenciasIdioma();
        setContentView(R.layout.activity_main);

        ImageView imagen = findViewById(R.id.imagenInicio);
        Button botonMulti = findViewById(R.id.botonMultijugador);
        Button botonPreferencias = findViewById(R.id.botonPreferencias);
        Button botonGithub = findViewById(R.id.botonGithub);
        Button botonOnline = findViewById(R.id.botonOnline);
        // Establecer texto en los botones
        botonMulti.setText(R.string.multijugador);
        botonPreferencias.setText(R.string.preferencias);
        botonGithub.setText(R.string.proyecto);
        botonOnline.setText(R.string.jugarOnline);

        // Settear la imagen de inicio desde drawable
        imagen.setImageResource(R.drawable.quizimagen);

        // Crear intents para diferentes botones
        // Boton multijugador (crear nueva partida)
        // Boton preferencias (elegir las preferencias del jugador)
        // Boton github (poder investigar el proyecto en github)
        Intent intentMulti = new Intent(this, AddPlayers.class);
        Intent intentPreferences = new Intent(this, PreferencesActivity.class);
        Intent intentGithub = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/alvarohernandezrad/Quiz"));
        Intent intentOnline = new Intent(this, LoginRegisterActivity.class);
        Intent intentOnlineCredenciales = new Intent(this, OnlineMenuActivity.class);


        botonMulti.setOnClickListener(view -> {
            database.limpiarTablaJugadores();
            startActivity(intentMulti);
        });

        botonPreferencias.setOnClickListener(view -> startActivity(intentPreferences));

        botonGithub.setOnClickListener(view -> startActivity(intentGithub));

        botonOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                String user = preferences.getString("username","");
                if(user.length() > 0){
                    intentOnlineCredenciales.putExtra("user", user);
                    startActivity(intentOnlineCredenciales);
                }
                else{
                    startActivity(intentOnline);
                }
            }
        });

        //botonOnline.setOnClickListener(view -> startActivity(intentOnline));

    }

    // Método para establecer el modo oscuro o no, dependiendo de lo que elija el jugador
    private void cargarPreferenciasDarkTheme() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // Como se trata de un checkbox, si está marcado devolverá True, sino False.
        boolean dark = prefs.getBoolean("temaOscuro", false);
        if (!dark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    /* Método para establecer el idioma del menú (español o inglés). Este método me ha dado problemas ya que
    el métogo .locale.getCountry() no me devolvía correctamente el cod del país, por lo que recurrí a utilizar el
    idioma del dispositivo. Pero dependiendo del país del dispositivo, devolvía el idioma en el que está el teléfono
    de distinta manera. Por ejemplo, un móvil del emulador para decir que el idioma está en español devolvía: Spanish, mientras
    que mi Android físico devolvía: español. He tenido que gestionar eso.     */
    private void cargarPreferenciasIdioma() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String actual = prefs.getString("idioma", "ES");
        String idioma = this.getResources().getConfiguration().locale.getDisplayLanguage();
        String ingles, castellano;
        boolean movilEnEspañol;
        // Comprobar si el dispositivo es "español" o "inglés"
        if(idioma.equals("inglés") || idioma.equals("español")){
            movilEnEspañol = true;
        }else{
            movilEnEspañol = false;
        }
        // Establecer los nombres para los idiomas dependiendo del idioma original del dispositivo
        if(movilEnEspañol){
            ingles = "inglés";
            castellano = "español";
        }else{
            ingles = "English";
            castellano = "Spanish";
        }
        Locale nuevaloc;
        // Si se ha cambiado la preferencia, cambiar la localización
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

    // Método para establecer el color favorito del jugador
    private void cargarPreferenciasColor(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String color = prefs.getString("color", "normal");
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