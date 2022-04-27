package com.example.quiz.conexionesBDWebServices;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class VidasJugadorBDWebService extends Worker {
    public VidasJugadorBDWebService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String username = getInputData().getString("username");
        String direccion = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/ahernandez141/WEB/vidasJugador.php";
        HttpURLConnection urlConnection = null;
        try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("usuario", username);
            String parametros = builder.build().getEncodedQuery();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametros);
            out.close();
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = "";
        try {
            int statusCode = urlConnection.getResponseCode();
            if(statusCode == 200){
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(inputStream, "UTF-8"));
                result = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Data datos = null;
        if(result.equals("1")){
            datos = new Data.Builder()
                    .putBoolean("vida", true)
                    .build();
        }else{
            datos = new Data.Builder()
                    .putBoolean("noVida", false)
                    .build();
        }
        return Result.success(datos);
    }
}
