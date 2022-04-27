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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImagenesUsuarioWebService extends Worker {
    public ImagenesUsuarioWebService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String username = getInputData().getString("username");
        String direccion = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/ahernandez141/WEB/extraerImagenUsuario.php";
        HttpURLConnection urlConnection = null;
        try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("username", username);
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
        int responseCode = 0;
        String result = "";
        Data datos = null;
        try {
            responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(inputStream, "UTF-8"));
                result = bufferedReader.readLine();
                if(result != null) {
                    try {
                        OutputStreamWriter fichero = new OutputStreamWriter(getApplicationContext().openFileOutput("ficheroAyudaImagenRecoger.txt", Context.MODE_PRIVATE));
                        fichero.write(result);
                        fichero.close();
                        datos = new Data.Builder().putBoolean("escrito", true).build();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    datos = new Data.Builder().putBoolean("escrito", true).build();
                }
                return Result.success(datos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.retry();
    }
}
