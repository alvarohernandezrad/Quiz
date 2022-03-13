package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.R;
import com.example.quiz.adaptors.AdaptorListView;
import com.example.quiz.database.MiDB;
import com.example.quiz.dialogs.AceptarJugadoresDialog;

import java.util.ArrayList;

public class PrePartida extends AppCompatActivity implements AceptarJugadoresDialog.ListenerDelDialogo {
    MiDB database;
    TextView texto;
    static ListView jugadores;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_partida);

        this.texto = findViewById(R.id.textoAceptar);
        this.boton = findViewById(R.id.botonAceptar);
        this.database = new MiDB(this, "App", (SQLiteDatabase.CursorFactory) null, 1);

        this.texto.setText(R.string.listaJugadores);
        this.boton.setText(R.string.jugar);

        String[] nombres = obtenerJugadores();
        jugadores = (ListView)findViewById(R.id.lista);
        AdaptorListView adaptor = new AdaptorListView(getApplicationContext(), nombres);
        jugadores.setAdapter(adaptor);

        jugadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nombre = (String) adapterView.getItemAtPosition(i);
                Log.d("ayuda", nombre);
                database.eliminarJugador(nombre);
                // Datos actualizados
                adaptor.notifyDataSetChanged();
                String[] nombres = obtenerJugadores();
                AdaptorListView adaptor = new AdaptorListView(getApplicationContext(), nombres);
                jugadores.setAdapter(adaptor);
            }
        });
    }

    @Override
    public void clickarSiDialog() {
//Abrir intent jugar
    }

    @Override
    public void clickarNoDialog() {
        return;
    }

    private String[] obtenerJugadores(){
        ArrayList<String> jugadores = this.database.getNombresJugadores();
        String[] s = new String[jugadores.size()];
        int i = 0;
        for (String nombre : jugadores)
            s[i++] = nombre;
        return s;
    }

    public void mostrarDialog(View v){
        int numeroJugadores = this.database.numeroJugadores();
        Intent intentPrePartida = new Intent(this, PrePartida.class);
        if(numeroJugadores < 2) {
            Toast.makeText(this, R.string.faltanJugadores, Toast.LENGTH_SHORT).show();
            return;
        }else {
            DialogFragment dialogo = new AceptarJugadoresDialog();
            dialogo.show(getSupportFragmentManager(), "aceptar");
        }
    }


}