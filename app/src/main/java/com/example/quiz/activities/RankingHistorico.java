package com.example.quiz.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quiz.R;
import com.example.quiz.adaptors.AdaptorListViewBotonEliminar;
import com.example.quiz.adaptors.AdaptorListViewRanking;
import com.example.quiz.adaptors.AdaptorListViewRankingOnline;
import com.example.quiz.conexionesBDWebServices.InsertarRankingHistoricoBDWebService;
import com.example.quiz.conexionesBDWebServices.LograrDatosRankingBDWebService;
import com.example.quiz.conexionesBDWebServices.VidasJugadorBDWebService;
import com.example.quiz.models.AuxiliarColores;

public class RankingHistorico extends AppCompatActivity {
    ListView ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        setContentView(R.layout.activity_ranking_historico);
        Button botonVolver = findViewById(R.id.botonVolverRankingOnline);
        botonVolver.setText(getString(R.string.volver));
        Button botonMapa = findViewById(R.id.botonGoogleMaps);
        botonMapa.setText(getString(R.string.verEnMapa));
        TextView titulo, username, puntuacion, localizacion;
        titulo = findViewById(R.id.tituloRankingOnline);
        username = findViewById(R.id.textoNombreRankingOnline);
        puntuacion = findViewById(R.id.textoPuntosRankingOnline);
        localizacion = findViewById(R.id.textoLocalizacionRankingOnline);
        username.setText(getString(R.string.nombre));
        titulo.setText(getString(R.string.rankingHistorico));
        puntuacion.setText(getString(R.string.puntos));
        localizacion.setText(getString(R.string.localizacion));
        botonVolver.setOnClickListener(view -> {
            Intent intentMenu = new Intent(this, OnlineMenuActivity.class);
            startActivity(intentMenu);
            finish();
        });
        botonMapa.setOnClickListener(view -> {
            Intent intentMapa = new Intent(this, GoogleMapsActivity.class);
            startActivity(intentMapa);
        });

        // Obtenemos los datos para cada una de las filas del ListView personalizado
        Constraints restricciones = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(LograrDatosRankingBDWebService.class).setConstraints(restricciones).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(req.getId()).observe(this, status -> {
            // De un worker logramos la informacion que nos ofrece el servidor
            if(status != null && status.getState().isFinished()) {
               String resultado = status.getOutputData().getString("resultado");
               resultado = resultado.replace("[", "").replace("]","").replace("\"","");
               String[] nombres = resultado.split(" ")[0].split(",");
               String[] puntos = resultado.split(" ")[1].split(",");
               String[] longitudes = resultado.split(" ")[2].split(",");
               String[] latitudes = resultado.split(" ")[3].split(",");

               ranking = (ListView) findViewById(R.id.listaRanking);
               AdaptorListViewRankingOnline adaptor = new AdaptorListViewRankingOnline(getApplicationContext(), nombres, puntos);
               ranking.setAdapter(adaptor);
               // Por si se pulsa el botón de ver localizacion
                ranking.setOnItemClickListener((adapterView, view, i, l) -> {
                // Miramos que las coordenadas no sean nulas
                    if(!longitudes[i].equals("null") && !latitudes[i].equals("null")) {
                            Double longitud = Double.valueOf(longitudes[i]);
                            Double latitud = Double.valueOf(latitudes[i]);
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle(getString(R.string.verLocalizacion));
                            builder.setMessage(getString(R.string.verMapa));
                            builder.setPositiveButton("OK", (dialogInterface, j) -> {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + latitud + "," + longitud + "?q=" + latitud + "," + longitud));
                                    startActivity(intent); //y abrimos Google Maps sobre la localizacion con un marcador
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                            builder.setNegativeButton("NO", (dialogInterface, j) -> {
                                return;
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle(getString(R.string.verLocalizacion));
                        builder.setMessage(getString(R.string.sinLocalizacion2));
                        builder.setPositiveButton("OK", (dialogInterface, j) -> {
                            return;
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }
        });
        WorkManager.getInstance(this).enqueue(req);

    }

}