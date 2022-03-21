package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quiz.R;
import com.example.quiz.adaptors.AdaptorListViewRanking;
import com.example.quiz.database.MiDB;
import com.example.quiz.models.AuxiliarColores;

public class Ranking extends AppCompatActivity {

    TextView titulo;
    ImageView imagen;
    ListView lista;
    Button boton;
    MiDB database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        setContentView(R.layout.activity_ranking);
        this.titulo = findViewById(R.id.tituloRanking);
        this.imagen = findViewById(R.id.imagenRanking);
        this.lista = findViewById(R.id.listaRanking);
        this.boton = findViewById(R.id.botonRanking);
        this.database = new MiDB(this, "App", (SQLiteDatabase.CursorFactory) null, 1);

        // Para cancelar la notificación
        // Recogemos el id que nos ha pasado la anterior actividad, y borramos la notificación
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int id = extras.getInt("id");
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(id);
        }

        this.titulo.setText(R.string.tituloRanking);
        this.boton.setText(R.string.jugarOtraVez);
        this.imagen.setImageResource(R.drawable.quizimagen);

        String[] nombres = this.database.jugadoresPorPuntos();

        AdaptorListViewRanking adaptador = new AdaptorListViewRanking(getApplicationContext(), nombres);
        this.lista.setAdapter(adaptador);
        Intent intentInicio = new Intent(getApplicationContext(), MainActivity.class);
        intentInicio.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.boton.setOnClickListener(view -> {
            startActivity(intentInicio);
            finish();
        });
    }
}