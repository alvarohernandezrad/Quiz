package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quiz.R;
import com.example.quiz.conexionesBDWebServices.ImagenUsuarioInsertarBDWebService;
import com.example.quiz.conexionesBDWebServices.ImagenesUsuarioWebService;
import com.example.quiz.models.AuxiliarColores;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import de.hdodenhof.circleimageview.CircleImageView;

public class OnlineMenuActivity extends AppCompatActivity {
    static String username;
    Button botonFoto, botonGaleria;
    CircleImageView imagenCircular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        setContentView(R.layout.activity_online_menu);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            username = extras.getString("user");
        }

        botonFoto = findViewById(R.id.botonFoto);
        botonGaleria = findViewById(R.id.botonGaleria);
        imagenCircular = findViewById(R.id.imagenCircular);

        cargarImagenUsuarioBaseDatos(username);

        botonFoto.setOnClickListener(view -> {
            Intent intentFotoCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intentFotoCamara, 100);
        });

        botonGaleria.setOnClickListener(view -> {
            Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentGaleria, 200);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap foto = (Bitmap) extras.get("data");
            subirImagenBD(username, foto);
        }
        else if(requestCode == 200 && resultCode == RESULT_OK) {
            Uri imagenSeleccionada = data.getData();
            InputStream pictureInputStream = null;
            // Convertir URI a STREAM
            try {
                pictureInputStream = getContentResolver().openInputStream(imagenSeleccionada);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // Abrir la imagen como bitmap
            Bitmap foto = BitmapFactory.decodeStream(pictureInputStream);
            subirImagenBD(username, foto);
        }
    }

    private void subirImagenBD(String username, Bitmap foto){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] fotoTransformada = stream.toByteArray();
        String fotoen64 = Base64.encodeToString(fotoTransformada,Base64.DEFAULT);
        //Como no podemos pasarle al worker la imagen codificada en base64 porque data no permite tanto espacio
        // lo escribo en un fichero interno que el worker leerá
        try{
            OutputStreamWriter fichero = new OutputStreamWriter(openFileOutput("ficheroAyudaImagenGuardar.txt", Context.MODE_PRIVATE));
            fichero.write(fotoen64);
            fichero.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        }else{
            Toast.makeText(this, getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
        }
        Data datos = new Data.Builder().putString("username", username).build();
        Constraints restricciones = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(ImagenUsuarioInsertarBDWebService.class).setInputData(datos).setConstraints(restricciones).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(req.getId()).observe(this, status -> {
            if(status != null && status.getState().isFinished()) {
                if(status.getOutputData().getBoolean("OK", false)){
                    Intent intentRestartActivity = getIntent();
                    startActivity(intentRestartActivity);
                    finish();
                }
            }
        });
        WorkManager.getInstance(this).enqueue(req);
    }


    private void cargarImagenUsuarioBaseDatos(String username) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        }else{
            Toast.makeText(this, getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
        }
        Data datos = new Data.Builder().putString("username",username).build();
        Constraints restricciones = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(ImagenesUsuarioWebService.class).setInputData(datos).setConstraints(restricciones).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(req.getId()).observe(this, status -> {
            if(status != null && status.getState().isFinished()) {
                if(status.getOutputData().getBoolean("escrito", false)){
                    String imagen = "";
                    //leemos el contenido del fichero interno
                    try {
                        BufferedReader ficherointerno = new BufferedReader(new InputStreamReader(getApplicationContext().openFileInput("ficheroAyudaImagenRecoger.txt")));
                        String linea = ficherointerno.readLine();
                        while (linea != null) {
                            imagen += linea;
                            linea = ficherointerno.readLine();
                        }
                        ficherointerno.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //vaciamos el fichero interno para la próxima vez
                    try{
                        OutputStreamWriter fichero = new OutputStreamWriter(getApplicationContext().openFileOutput("ficheroAyudaImagenRecoger.txt", Context.MODE_PRIVATE));
                        fichero.write("");
                        fichero.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    byte[] decodedString = Base64.decode(imagen, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imagenCircular.setImageBitmap(decodedByte);
                }
            }
        });
        WorkManager.getInstance(this).enqueue(req);
    }
}