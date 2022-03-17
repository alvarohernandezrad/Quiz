package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quiz.R;
import com.example.quiz.adaptors.AdaptorListViewRanking;
import com.example.quiz.database.MiDB;

public class Ranking extends AppCompatActivity {

    private TextView titulo;
    private ImageView imagen;
    private ListView lista;
    private Button boton;
    private MiDB database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        this.titulo = findViewById(R.id.tituloRanking);
        this.imagen = findViewById(R.id.imagenRanking);
        this.lista = findViewById(R.id.listaRanking);
        this.boton = findViewById(R.id.botonRanking);
        this.database = new MiDB(this, "App", (SQLiteDatabase.CursorFactory) null, 1);

        this.titulo.setText(R.string.tituloRanking);
        this.boton.setText(R.string.jugarOtraVez);
        this.imagen.setImageResource(R.drawable.quizimagen);

        String[] nombres = this.database.jugadoresPorPuntos();
        AdaptorListViewRanking adaptador = new AdaptorListViewRanking(getApplicationContext(), nombres);
        this.lista.setAdapter(adaptador);
    }
}