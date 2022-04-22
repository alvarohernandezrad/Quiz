package com.example.quiz.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.quiz.R;
import com.example.quiz.adaptors.AdaptorListViewRankingOnline;
import com.example.quiz.conexionesBDWebServices.AñadirVidaBDWebService;
import com.example.quiz.conexionesBDWebServices.LograrDatosRankingBDWebService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extra = intent.getExtras();
        String username = "";
        if(extra != null){
            username = extra.getString("username");
        }
        // Programamos la función de la alarma para que se sume una vida 5 minutos despues de perder
        // y a su vez firebase le mande una notificacion con que puede jugar

        Data datos = new Data.Builder().putString("username", username).build();
        Constraints restricciones = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(AñadirVidaBDWebService.class).setInputData(datos).setConstraints(restricciones).build();
        WorkManager.getInstance(context).enqueue(req);
    }
}
