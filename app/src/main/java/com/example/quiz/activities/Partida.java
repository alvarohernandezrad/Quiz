package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
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
import com.example.quiz.adaptors.AdaptorListViewJugadorPuntos;
import com.example.quiz.database.MiDB;
import com.example.quiz.models.Turno;

import java.util.ArrayList;

public class Partida extends AppCompatActivity {

    private MiDB database;
    private ImageView imagen;
    private TextView pregunta;
    private ListView respuestas;
    private TextView textoTurno;
    private ListView jugadoresPuntos; // Solo en modo horizontal
    private TextView cabeceraJugador; // Solo en modo horizontal
    private TextView cabeceraPuntos; // Solo en modo horizontal

    private Turno turno;
    private static int numeroJugadores;
    private static int jugadorAnterior;
    private static int jugadorActual;
    private static int correcta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        // por ahora voy a usar solo el logo. En el futuro tal vez agregue una imagen a cada foto.
        this.imagen = findViewById(R.id.imagenPartida);
        this.pregunta = findViewById(R.id.preguntaPartida);
        this.respuestas = findViewById(R.id.respuestasPartida);
        this.textoTurno = findViewById(R.id.turnoPartida);
        this.database = new MiDB(this, "App", (SQLiteDatabase.CursorFactory) null, 1);

        // Obtener número de jugadores
        numeroJugadores = this.database.numeroJugadores();
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            jugadorAnterior = extras.getInt("jugadorAnterior");
        }else{
            jugadorActual = 0;
            jugadorAnterior = numeroJugadores;
        }

        // Ver a que jugador le toca. Si el anterior ha sido menor que el total +1. Si no 0 (índice empieza en 0)
        if(jugadorAnterior < numeroJugadores-1){ //añadimos el menos 1 porque el índice de los jugadores comieza en 0 y no en 1.
            jugadorActual = jugadorAnterior + 1;
        }else{
            jugadorActual = 0;
        }

        // Cargar la pregunta de la base de datos y marcarla como leída para que no se repita
        turno = this.database.recibirPreguntaYMarcarComoLeida();

        // En el futuro, intentaré que algunas preguntas contengan fotos. Eso supondrá cargar de la base de datos
        // el nombre de la imagen. Si tiene alguna nombre para la foto, se carga esa foto en el icono. Si no tiene foto
        // se carga la imagen default

        this.imagen.setImageResource(R.drawable.quizimagen);

        this.pregunta.setText(turno.getPregunta());
        String nombreJugadorActual = this.database.nombreJugadorActual(jugadorActual);
        this.textoTurno.setText(getResources().getString(R.string.turno) + " " + nombreJugadorActual);
        correcta = turno.getCorrecta();

        // Convertir ArrayList con las respuestas en Array para el adaptador
        String[] arrayRespuestas = new String[turno.getRespuestas().size()];
        arrayRespuestas = turno.getRespuestas().toArray(arrayRespuestas);
        ArrayAdapter adaptadorPreguntas = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayRespuestas);
        this.respuestas.setAdapter(adaptadorPreguntas);

        //Si estamos en orientación horizontal, añadimos lo necesario para añadir la ListView personalizada Jugador-Puntos
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.jugadoresPuntos = findViewById(R.id.listaJugadoresPuntosPartida);
            // Conseguimos array con nombres y puntuaciones, en el mismo orden
            String[] jugadores = this.database.obtenerNombresJugadores();
            int[] puntuaciones = this.database.obtenerPuntuacionesJugadores();
            AdaptorListViewJugadorPuntos adaptadorJugadorPuntos = new AdaptorListViewJugadorPuntos(getApplicationContext(), jugadores, puntuaciones);
            this.jugadoresPuntos.setAdapter(adaptadorJugadorPuntos);

            this.cabeceraJugador = findViewById(R.id.cabeceraJugador);
            this.cabeceraPuntos = findViewById(R.id.cabeceraPuntos);

            this.cabeceraJugador.setText(R.string.cabeceraJugador);
            this.cabeceraPuntos.setText(R.string.cabeceraPuntos);
        }

        respuestas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               if(correcta == l){
                   acierto();
                   adapterView.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
               }else{
                   fallo();
                   adapterView.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
               }
               pasarDeIntent();
           }
        });


        // Comprobar si existe algún jugador con 10 aciertos. Si es así saltar a un intent con listView personalizada con ranking
        // Y mostrar una notificación de que existe algún jugador ganador


        Intent i = new Intent(this, Partida.class);
        i.putExtra("jugadorAnterior", jugadorActual);
    }

    private void acierto() { // Se ha respondido la pregunta correctamente
        Toast.makeText(this, R.string.acierto, Toast.LENGTH_SHORT).show();
        // Sumar un punto a la persona que ha acertado pasándole su identificador
        this.database.sumarPunto(jugadorActual);
    }
    private void fallo() { // Se ha respondido la pregunta erroneamente
        Toast.makeText(this, R.string.fallo, Toast.LENGTH_SHORT).show();
    }

    private void pasarDeIntent(){
        // Comprobamos a que Intent debemos pasar
        // Puede ser que ya haya un ganador, o que todavía haya que seguir jugando
        if(this.database.comprobarGanador()){
            Log.d("ganador", "tenemos ganador");
            Intent intentGanar = new Intent(this, Ranking.class);
            startActivity(intentGanar);
            finish();
        }else {
            Intent intentSeguir = new Intent(this, Partida.class);
            intentSeguir.putExtra("jugadorAnterior", jugadorActual);
            startActivity(intentSeguir);
            //Al pasar de intent acabamos el activity actual
            finish();
        }
    }

}
