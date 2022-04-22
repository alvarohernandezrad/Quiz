package com.example.quiz.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.R;
import com.example.quiz.conexionesBDWebServices.InsertarRankingHistoricoBDWebService;
import com.example.quiz.database.MiDB;
import com.example.quiz.models.AuxiliarColores;
import com.example.quiz.models.Turno;
import com.example.quiz.services.AlarmReceiver;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;

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
    static double latitud;
    static double longitud;
    private static final int CODIGO_PERMISOS = 99;


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
        if (savedInstanceState != null) {
            idPregunta = savedInstanceState.getInt("idPregunta");
        }
        pregunta = findViewById(R.id.preguntaPartidaOnline);
        respuestas = findViewById(R.id.respuestasPartidaOnline);
        textoTurno = findViewById(R.id.numeroPregunta);
        database = new MiDB(this, "App", (SQLiteDatabase.CursorFactory) null, 1);

        // Conseguimos la última ubicación disponible, que normalmente equivale a la actual
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            longitud = location.getLongitude();
                            latitud = location.getLatitude();
                        }
                        else{
                            longitud = 0.0;
                            latitud = 0.0;
                        }
                    });
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CODIGO_PERMISOS);
            Intent intentAjustes = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intentAjustes.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intentAjustes.setData(uri);
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.permisosLocalizacion));
                builder.setMessage(getString(R.string.registrarLocalizacion));
                builder.setPositiveButton(getString(R.string.darPermisos), (dialogInterface, i) -> startActivity(intentAjustes));
                builder.setNegativeButton(getString(R.string.meDaIgual), (dialogInterface, i) -> {
                    return;
                });
                builder.show();
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.permisosLocalizacion));
                builder.setMessage(getString(R.string.sinLocalizacion));
                builder.setPositiveButton(getString(R.string.meDaIgual), (dialogInterface, i) -> startActivity(intentAjustes));
                builder.setNegativeButton(getString(R.string.meDaIgual), (dialogInterface, i) -> {
                    return;
                });
                builder.show();

            }

        }

        // Obtener número de jugadores
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            numPregunta = extras.getInt("numPregunta");
            puntos = extras.getInt("puntos");
        }

        // Comprobar si quedan preguntas disponibles. En caso de que no, poner todas como no leídas y empezar un nuevo ciclo.
        if (!this.database.hayPreguntasDisponibles()) {
            this.database.marcarPreguntasComoNoLeidas();
        }

        // Cargar la pregunta de la base de datos y marcarla como leída para que no se repita
        turno = this.database.recibirPreguntaYMarcarComoLeida(idPregunta);

        // Guardamos el id de la pregunta para por si rotamos el móvil, mantener la misma pregunta.
        idPregunta = turno.getId();

        pregunta.setText(turno.getPregunta());
        textoTurno.setText(getResources().getString(R.string.pregunta) + " " + numPregunta);
        correcta = turno.getCorrecta();
        respuestaCorrectaString = turno.getRespuestaString(correcta);

        // Convertir ArrayList con las respuestas en Array para el adaptador
        String[] arrayRespuestas = new String[turno.getRespuestas().size()];
        arrayRespuestas = turno.getRespuestas().toArray(arrayRespuestas);
        ArrayAdapter adaptadorPreguntas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayRespuestas);
        respuestas.setAdapter(adaptadorPreguntas);

        respuestas.setOnItemClickListener((adapterView, view, i, l) -> {
            respuestaJugadorString = turno.getRespuestaString((int) l);
            // En caso de acierto
            if (correcta == l) {
                acierto();
                adapterView.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                Intent intentSeguir = getIntent();
                intentSeguir.putExtra("puntos", puntos + 1);
                intentSeguir.putExtra("numPregunta", numPregunta + 1);
                startActivity(intentSeguir);
                finish();
            } else { // En caso de error
                fallo();
                adapterView.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                adapterView.getChildAt(correcta).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));

                // Uso de alarma para avisar de que tenemos una nueva vida y sumarla 5 minutos después de perder
                Calendar calendario = Calendar.getInstance();
                calendario.set(Calendar.HOUR_OF_DAY, calendario.get(Calendar.HOUR_OF_DAY));
                calendario.set(Calendar.MINUTE, calendario.get(Calendar.MINUTE)+5);
                calendario.set(Calendar.SECOND, calendario.get(Calendar.SECOND));

                // Creamos el intent
                Intent intentAlarma = new Intent(this, AlarmReceiver.class);
                intentAlarma.putExtra("username",username);
                PendingIntent pendingIntentAlarma = PendingIntent.getBroadcast(this,9999,intentAlarma, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager gestor = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                gestor.setExact(AlarmManager.RTC_WAKEUP,calendario.getTimeInMillis(),pendingIntentAlarma);

                Intent intentMenuOnline = new Intent(this, OnlineMenuActivity.class);
                startActivity(intentMenuOnline);
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
        // Al final de la partida queremos guardar que jugador ha jugado, cuantos puntos ha logrado y la locación del jugador para ver
        // de donde son los mejores jugadores
        guardarEnHistorico(username, puntos, longitud, latitud);
    }

    // Añade los resultados de la partida a la base de datos
    private void guardarEnHistorico(String username, int puntos, double longitud, double latitud){
        Data datos = new Data.Builder().putString("username", username).putInt("puntos", puntos).putDouble("longitud", longitud).putDouble("latitud", latitud).build();
        Constraints restricciones = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(InsertarRankingHistoricoBDWebService.class).setInputData(datos).setConstraints(restricciones).build();
        WorkManager.getInstance(this).enqueue(req);
    }

    // Programamos el método onSaveInstanceState para que al rotar la pantalla se mantenga la misma pregunta
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("idPregunta", idPregunta);
    }


    // Gestiona la petición de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODIGO_PERMISOS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intentSeguir = getIntent();
                    intentSeguir.putExtra("puntos", puntos + 1);
                    intentSeguir.putExtra("numPregunta", numPregunta + 1);
                    startActivity(intentSeguir);
                    finish();
                } else {
                    latitud = 0.0;
                    longitud = 0.0;
                }
                break;
        }
    }
}