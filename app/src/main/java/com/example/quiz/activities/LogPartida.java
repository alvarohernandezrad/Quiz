package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.example.quiz.R;
import com.example.quiz.models.AuxiliarColores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

// Actividad que se mostrará tras pulsar el botón LOG en la actividad de la partida y hace uso de ficheros

public class LogPartida extends AppCompatActivity {

    ListView lista;
    Button boton;
    private static int numeroTurnos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        setContentView(R.layout.activity_log);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            numeroTurnos = extras.getInt("numeroTurnos")-1;
        }
        this.lista = findViewById(R.id.listaLog);
        this.boton = findViewById(R.id.botonLog);
        this.boton.setText(R.string.seguirJugando);
        String[] log = leerLog();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, log);
        this.lista.setAdapter(adapter);
        this.boton.setOnClickListener(view -> finish());
    }

    /* Habrá un fichero llamado "log.txt" que guarda las respuestas dadas en cada turno de la partida, y mediante este
    método leemos cada línea, y la guardamos en un array. El array serán los elementos que muestra el ListView. */
    private String[] leerLog() {
        String[] log = new String[numeroTurnos];
        try {
            BufferedReader ficherointerno = new BufferedReader(new InputStreamReader(openFileInput("log.txt")));
            String linea = ficherointerno.readLine();
            int i = 0;
            while (linea != null) {
                log[i] = linea;
                i++;
                linea = ficherointerno.readLine();
            }
            ficherointerno.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return log;
    }
}