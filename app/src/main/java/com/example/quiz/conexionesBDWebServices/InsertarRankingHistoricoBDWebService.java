package com.example.quiz.conexionesBDWebServices;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InsertarRankingHistoricoBDWebService extends Worker {
    public InsertarRankingHistoricoBDWebService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String direccion = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/ahernandez141/WEB/insertarRanking.php";
        HttpURLConnection urlConnection = null;
        String username = getInputData().getString("username");
        int puntos = getInputData().getInt("puntos", 0);
        double longitud = getInputData().getDouble("longitud", 0);
        double latitud = getInputData().getDouble("latitud", 0);
        // Flag para saber si el usuario quiere ofrecer la localizacion o no
        int flagLocalizacion;
        if(longitud == 0.0 && latitud == 0.0){
            flagLocalizacion = 0;
        }else{
            flagLocalizacion = 1;
        }
        try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("username", username)
                    .appendQueryParameter("puntos", String.valueOf(puntos))
                    .appendQueryParameter("longitud", String.valueOf(longitud))
                    .appendQueryParameter("latitud", String.valueOf(latitud))
                    .appendQueryParameter("flag", String.valueOf(flagLocalizacion));
            String parametros = builder.build().getEncodedQuery();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametros);
            out.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int statusCode = 0;
        try {
            statusCode = urlConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(statusCode == 200){
            Data datos = new Data.Builder().putBoolean("OK", true).build();
            return Result.success(datos);
        }else{
            return Result.retry();
        }

    }
}
