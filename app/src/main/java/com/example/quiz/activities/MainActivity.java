package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.quiz.R;
import com.example.quiz.database.MiDB;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private MiDB database = new MiDB(this, "App", (SQLiteDatabase.CursorFactory) null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //cargarPreguntas();
        setContentView(R.layout.activity_main);

        ImageView imagen = findViewById(R.id.imagenInicio);
        Button botonMulti = findViewById(R.id.botonMultijugador);
        Button botonPreferencias = findViewById(R.id.botonPreferencias);

        // Establecer texto en los botones
        botonMulti.setText(R.string.multijugador);
        botonPreferencias.setText(R.string.preferencias);

        // Settear la imagen de inicio desde drawable
        imagen.setImageResource(R.drawable.quizimagen);

        // Crear intents para diferentes botones
        // Boton multijugador (crear nueva partida)
        Intent intentMulti = new Intent(this, AddPlayers.class);
        Log.d("preguntas", String.valueOf(this.database.numeroPreguntas()));
        botonMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.limpiarTablaJugadores();
                startActivity(intentMulti);
            }
        });
    }

    /*private void cargarPreguntas(){
        InputStream fichero = getResources().openRawResource(R.raw.basedatos);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(fichero));
        try{
            String linea = buffer.readLine();
            while (linea != null){
                this.database.cargarPregunta(linea);
            }
            fichero.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }*/
}