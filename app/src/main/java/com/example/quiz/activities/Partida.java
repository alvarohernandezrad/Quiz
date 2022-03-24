package com.example.quiz.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.R;
import com.example.quiz.adaptors.AdaptorListViewJugadorPuntos;
import com.example.quiz.database.MiDB;
import com.example.quiz.models.AuxiliarColores;
import com.example.quiz.models.Turno;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;

// Actividad en la que el jugador debe elegir la respuesta correcta para cada pregunta

public class Partida extends AppCompatActivity {

    MiDB database;
    ImageView imagen;
    TextView pregunta;
    ListView respuestas;
    TextView textoTurno;
    ListView jugadoresPuntos; // Solo en modo horizontal
    TextView cabeceraJugador; // Solo en modo horizontal
    TextView cabeceraPuntos; // Solo en modo horizontal
    Button botonLog;

    Turno turno;
    static int numeroTurno;
    static int numeroJugadores;
    static int jugadorAnterior;
    static int jugadorActual;
    static int correcta;
    static int idPregunta;

    static String respuestaJugadorString;
    static String respuestaCorrectaString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        idPregunta = 0;
        /* Por si se da el caso de girar la pantalla, queremos guardar el identificador de la pregunta actual
        con el objetivo de que se siga mostrando la misma pregunta y no una nueva. */
        if(savedInstanceState != null){
            idPregunta = savedInstanceState.getInt("idPregunta");
        }
        setContentView(R.layout.activity_partida);
        // por ahora voy a usar solo el logo. En el futuro tal vez agregue una imagen a cada pregunta.
        this.imagen = findViewById(R.id.imagenPartida);
        this.pregunta = findViewById(R.id.preguntaPartida);
        this.respuestas = findViewById(R.id.respuestasPartida);
        this.textoTurno = findViewById(R.id.turnoPartida);
        this.database = new MiDB(this, "App", (SQLiteDatabase.CursorFactory) null, 1);
        this.botonLog = findViewById(R.id.botonLogPartida);


        // Obtener número de jugadores
        numeroJugadores = this.database.numeroJugadores();
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            jugadorAnterior = extras.getInt("jugadorAnterior");
            numeroTurno = extras.getInt("numeroTurno")+1;
        }else{
            jugadorActual = 0;
            jugadorAnterior = numeroJugadores;
            numeroTurno = 1;
        }
        if(jugadorAnterior < numeroJugadores-1){ //añadimos el menos 1 porque el índice de los jugadores comieza en 0 y no en 1.
            jugadorActual = jugadorAnterior + 1;
        }else{
            jugadorActual = 0;
        }
        // Comprobar si quedan preguntas disponibles. En caso de que no, poner todas como no leídas y empezar un nuevo ciclo.
        if(!this.database.hayPreguntasDisponibles()){
            this.database.marcarPreguntasComoNoLeidas();
        }

        // Cargar la pregunta de la base de datos y marcarla como leída para que no se repita
        turno = this.database.recibirPreguntaYMarcarComoLeida(idPregunta);

        // Guardamos el id de la pregunta para por si rotamos el móvil, mantener la misma pregunta.
        idPregunta = turno.getId();
        /* En el futuro, intentaré que algunas preguntas contengan fotos. Eso supondrá cargar de la base de datos
        el nombre de la imagen. Si tiene alguna nombre para la foto, se carga esa foto en el icono. Si no tiene foto
        se carga la imagen default */

        this.imagen.setImageResource(R.drawable.quizimagen);
        this.botonLog.setText("Log");
        this.pregunta.setText(turno.getPregunta());
        String nombreJugadorActual = this.database.nombreJugadorActual(jugadorActual);
        this.textoTurno.setText(getResources().getString(R.string.turno) + " " + nombreJugadorActual);
        correcta = turno.getCorrecta();
        respuestaCorrectaString = turno.getRespuestaString(correcta);

        // Convertir ArrayList con las respuestas en Array para el adaptador
        String[] arrayRespuestas = new String[turno.getRespuestas().size()];
        arrayRespuestas = turno.getRespuestas().toArray(arrayRespuestas);
        ArrayAdapter adaptadorPreguntas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayRespuestas);
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

        respuestas.setOnItemClickListener((adapterView, view, i, l) -> {
            respuestaJugadorString = turno.getRespuestaString((int)l);
            // En caso de acierto
            if(correcta == l){
                acierto();
                adapterView.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            }else{ // En caso de error
                fallo();
                adapterView.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                adapterView.getChildAt(correcta).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            }
            registrarLog(nombreJugadorActual);
            pasarDeIntent();
        });
    }

    // Se ha respondido la pregunta correctamente
    private void acierto() {
        Toast.makeText(this, R.string.acierto, Toast.LENGTH_SHORT).show();
        // Sumar un punto a la persona que ha acertado pasándole su identificador
        this.database.sumarPunto(jugadorActual);
    }

    // Se ha respondido la pregunta erroneamente
    private void fallo() { // Se ha respondido la pregunta erroneamente
        Toast.makeText(this, R.string.fallo, Toast.LENGTH_SHORT).show();
    }

    private void pasarDeIntent(){
        // Comprobamos a que Intent debemos pasar
        // Puede ser que ya haya un ganador, o que todavía haya que seguir jugando
        if(this.database.comprobarGanador()){
            lanzarNotificacion();
            Intent intentGanar = new Intent(this, PreRanking.class);
            // Le pasamos el id de la notificación también, por si llegamos por aquí también se quite la notificación
            intentGanar.putExtra("idNotificacion", 1);
            startActivity(intentGanar);
            finish();
        }else {
            Intent intentSeguir = new Intent(this, Partida.class);
            intentSeguir.putExtra("jugadorAnterior", jugadorActual);
            intentSeguir.putExtra("numeroTurno", numeroTurno);
            startActivity(intentSeguir);
            finish();
        }
    }

    /* En caso de que haya un ganador avisamos de que ya hay un ganador mediante una notificación local
    y le damos la oportunidad de ir al ranking desde la propia notificación */
    private void lanzarNotificacion() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "IdCanal");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel("IdCanal", "NombreCanal", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(canal);
        }
        Intent intentNotificacion = new Intent(this, Ranking.class);
        intentNotificacion.putExtra("idNotificacion",1);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentNotificacion, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.quizimagen))
                .setSmallIcon(android.R.drawable.star_on)
                .setContentTitle("Ranking")
                .setContentText("¡¡Ya tenemos un ganador!! / There is a winner!!")
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setAutoCancel(true)
                .addAction(android.R.drawable.ic_input_add,"Ver el Ranking", pendingIntent);
        manager.notify(1, builder.build());
    }

    // Programamos el método onSaveInstanceState para que al rotar la pantalla se mantenga la misma pregunta
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("idPregunta", idPregunta);
    }

    // Método para ir guardando un registro de la partida en un fichero.
    private void registrarLog(String nombre){
        try{
            OutputStreamWriter fichero = new OutputStreamWriter(openFileOutput("log.txt", Context.MODE_APPEND));
            fichero.write("Turno "+numeroTurno+".- Pregunta: "+turno.getPregunta().toUpperCase()+"; Jugador: "+nombre.toUpperCase()+
                    "; Respuesta jugador: "+respuestaJugadorString.toUpperCase()+"; Respuesta correcta: "+respuestaCorrectaString.toUpperCase()+
                    ".\n");
            fichero.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickBotonLog(View view){
        Intent intentLog = new Intent(this, LogPartida.class);
        intentLog.putExtra("numeroTurnos", numeroTurno);
        startActivity(intentLog);
    }

}
