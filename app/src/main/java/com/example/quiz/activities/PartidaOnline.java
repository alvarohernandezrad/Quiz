package com.example.quiz.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.R;
import com.example.quiz.database.MiDB;
import com.example.quiz.models.AuxiliarColores;
import com.example.quiz.models.Turno;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class PartidaOnline extends AppCompatActivity {

    MiDB database;
    TextView pregunta;
    ListView respuestas;
    TextView textoTurno;
    Turno turno;
    static int correcta;
    static int numPregunta;
    static int idPregunta;
    static int puntos;
    static String respuestaJugadorString;
    static String respuestaCorrectaString;
    static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        setContentView(R.layout.activity_partida_online);
        numPregunta = 1;
        puntos = 0;
        idPregunta = 0;
        /* Por si se da el caso de girar la pantalla, queremos guardar el identificador de la pregunta actual
        con el objetivo de que se siga mostrando la misma pregunta y no una nueva. */
        if(savedInstanceState != null){
            idPregunta = savedInstanceState.getInt("idPregunta");
        }
        pregunta = findViewById(R.id.preguntaPartidaOnline);
        respuestas = findViewById(R.id.respuestasPartidaOnline);
        textoTurno = findViewById(R.id.numeroPregunta);
        database = new MiDB(this, "App", (SQLiteDatabase.CursorFactory) null, 1);

        // Obtener número de jugadores
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            username = extras.getString("username");
            numPregunta = extras.getInt("numPregunta");
            puntos = extras.getInt("puntos");
        }

        // Comprobar si quedan preguntas disponibles. En caso de que no, poner todas como no leídas y empezar un nuevo ciclo.
        if(!this.database.hayPreguntasDisponibles()){
            this.database.marcarPreguntasComoNoLeidas();
        }

        // Cargar la pregunta de la base de datos y marcarla como leída para que no se repita
        turno = this.database.recibirPreguntaYMarcarComoLeida(idPregunta);

        // Guardamos el id de la pregunta para por si rotamos el móvil, mantener la misma pregunta.
        idPregunta = turno.getId();

        pregunta.setText(turno.getPregunta());
        textoTurno.setText(getResources().getString(R.string.pregunta) + " " + String.valueOf(numPregunta));
        correcta = turno.getCorrecta();
        respuestaCorrectaString = turno.getRespuestaString(correcta);

        // Convertir ArrayList con las respuestas en Array para el adaptador
        String[] arrayRespuestas = new String[turno.getRespuestas().size()];
        arrayRespuestas = turno.getRespuestas().toArray(arrayRespuestas);
        ArrayAdapter adaptadorPreguntas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayRespuestas);
        respuestas.setAdapter(adaptadorPreguntas);

        respuestas.setOnItemClickListener((adapterView, view, i, l) -> {
            respuestaJugadorString = turno.getRespuestaString((int)l);
            // En caso de acierto
            if(correcta == l){
                acierto();
                adapterView.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                Intent intentSeguir = getIntent();
                intentSeguir.putExtra("puntos", puntos+1);
                intentSeguir.putExtra("numPregunta", numPregunta+1);
                startActivity(intentSeguir);
                finish();
            }else{ // En caso de error
                fallo();
                adapterView.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                adapterView.getChildAt(correcta).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                finish();
            }
        });
    }

    // Se ha respondido la pregunta correctamente
    private void acierto() {
        Toast.makeText(this, R.string.acierto, Toast.LENGTH_SHORT).show();
        // Sumar un punto a la persona que ha acertado pasándole su identificador
    }

    // Se ha respondido la pregunta erroneamente
    private void fallo() { // Se ha respondido la pregunta erroneamente
        Toast.makeText(this, R.string.fallo, Toast.LENGTH_SHORT).show();
        //php guardar historico
    }

    // Programamos el método onSaveInstanceState para que al rotar la pantalla se mantenga la misma pregunta
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("idPregunta", idPregunta);
    }
}