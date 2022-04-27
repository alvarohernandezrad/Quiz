package com.example.quiz.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;

import com.example.quiz.R;
import com.example.quiz.conexionesBDWebServices.LograrDatosRankingBDWebService;
import com.example.quiz.models.AuxiliarColores;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        setContentView(R.layout.activity_google_maps);
        SupportMapFragment fragmento = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentoMapa);
        fragmento.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map){
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        // Obtenemos los datos para cada una de las filas del ListView personalizado
        Constraints restricciones = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(LograrDatosRankingBDWebService.class).setConstraints(restricciones).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(req.getId()).observe(this, status -> {
            // De un worker logramos la informacion que nos ofrece el servidor
            if(status != null && status.getState().isFinished()) {
                String resultado = status.getOutputData().getString("resultado");
                resultado = resultado.replace("[", "").replace("]", "").replace("\"", "");
                String[] nombres = resultado.split(" ")[0].split(",");
                String[] puntos = resultado.split(" ")[1].split(",");
                String[] longitudes = resultado.split(" ")[2].split(",");
                String[] latitudes = resultado.split(" ")[3].split(",");

                for (int i = 0; i < longitudes.length; i++) {
                    // Miramos que las coordenadas no sean nulas
                    if (!longitudes[i].equals("null") && !latitudes[i].equals("null")) {
                        map.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(latitudes[i]), Double.valueOf(longitudes[i])))
                                .title(nombres[i] + ": " + puntos[i] + " " + getString(R.string.puntosMinuscula)));
                    }
                }
            }
        });
        WorkManager.getInstance(this).enqueue(req);
    }
}