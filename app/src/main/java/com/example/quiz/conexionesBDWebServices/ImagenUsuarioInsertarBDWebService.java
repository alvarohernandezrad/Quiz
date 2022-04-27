package com.example.quiz.conexionesBDWebServices;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImagenUsuarioInsertarBDWebService extends Worker  {
    public ImagenUsuarioInsertarBDWebService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String imagen = "";
        //leemos el contenido del fichero interno
        try {
            BufferedReader ficherointerno = new BufferedReader(new InputStreamReader(getApplicationContext().openFileInput("ficheroAyudaImagenGuardar.txt")));
            StringBuilder cadena = new StringBuilder();
            String linea = null;
            while((linea = ficherointerno.readLine()) != null){
                cadena.append(linea);
            }
            ficherointerno.close();
            imagen = cadena.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //vaciamos el fichero interno para la próxima vez
        try{
            OutputStreamWriter fichero = new OutputStreamWriter(getApplicationContext().openFileOutput("ficheroAyudaImagenGuardar.txt", Context.MODE_PRIVATE));
            fichero.write("");
            fichero.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        String username = getInputData().getString("username");
        String direccion = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/ahernandez141/WEB/insertarImagenUsuario.php";
        HttpURLConnection urlConnection = null;
        try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("imagen", imagen)
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
            return Result.failure();
        }
    }
}
