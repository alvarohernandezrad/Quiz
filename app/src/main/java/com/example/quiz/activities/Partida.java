package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.R;
import com.example.quiz.database.MiDB;
import com.example.quiz.models.Turno;

import java.util.ArrayList;

public class Partida extends AppCompatActivity {

    private MiDB database;
    private ImageView imagen;
    private TextView pregunta;
    private ListView respuestas;

    private Turno turno;
    private int numeroJugadores = 0;
    private int jugadorAnterior = 1;
    private int jugadorActual = 1;
    private int correcta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Estoy aqui", "aqui");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        this.imagen = findViewById(R.id.imagenPartida);
        this.pregunta = findViewById(R.id.preguntaPartida);
        this.respuestas = findViewById(R.id.respuestasPartida);
        this.database = new MiDB(this, "App", (SQLiteDatabase.CursorFactory) null, 1);

        // Obtener número de jugadores
        numeroJugadores = this.database.numeroJugadores();
        Log.d("numero", String.valueOf(numeroJugadores));
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            jugadorAnterior = extras.getInt("jugadorAnterior");
        }

        // Ver a que jugador le toca. Si el anterior ha sido menor que el total +1. Si no 1 (índice empieza en 1)
        if(jugadorAnterior < numeroJugadores){
            jugadorActual = jugadorAnterior + 1;
        }else{
            jugadorActual = 1;
        }

        ArrayList<String> lista = new ArrayList<String>();
        lista.add("PEsi");
        lista.add("PEsi");
        lista.add("PEsi");
        lista.add("PEsi");

        turno = this.database.recibirPregunta();
        //turno = new Turno("¿Quien es el mejor?" , lista, "Deportes", 2);
        pregunta.setText(turno.getPregunta());
        correcta = turno.getCorrecta();
        String[] arrayRespuestas = new String[turno.getRespuestas().size()];
        arrayRespuestas = turno.getRespuestas().toArray(arrayRespuestas);
        ArrayAdapter elAdaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayRespuestas);
        respuestas.setAdapter(elAdaptador);

       respuestas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               if(correcta == l){
                   mostrarAcierto();
               }else{
                   mostrarFallo();
               }
           }
       });


        /*Intent i = new Intent(this, Partida.class);
        i.putExtra("jugadorAnterior", jugadorActual);*/


    }

    private void mostrarAcierto() {
        Toast.makeText(this, R.string.acierto, Toast.LENGTH_SHORT).show();
    }
    private void mostrarFallo() {
        Toast.makeText(this, R.string.fallo, Toast.LENGTH_SHORT).show();
    }

}
