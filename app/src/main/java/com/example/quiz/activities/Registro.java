package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.R;
import com.example.quiz.conexionesBDWebServices.AñadirNuevoUsuarioWebService;
import com.example.quiz.conexionesBDWebServices.ComprobarExisteYaUsuarioWebService;
import com.example.quiz.models.AuxiliarColores;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.ExecutionException;

public class Registro extends AppCompatActivity {

    EditText user, password;
    TextView registro, warning;
    ImageView imagen;
    Button botonRegistro;
    static boolean existeUsuario;
    static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        setContentView(R.layout.activity_registro);

        user = findViewById(R.id.textUserRegistro);
        password = findViewById(R.id.textPasswordRegistro);
        imagen = findViewById(R.id.imagenRegistro);
        botonRegistro = findViewById(R.id.botonRegistro);
        registro = findViewById(R.id.tituloRegistro);
        warning = findViewById(R.id.textWarningRegistro);

        registro.setText(R.string.registrar);
        warning.setVisibility(View.INVISIBLE);
        botonRegistro.setText(R.string.paginaRegistro);
    }

    // Método para gestionar click en login
    public void onRegister(View v) {
        String username = this.user.getText().toString();
        String password = this.password.getText().toString();
        // Uso de Toast para evitar un nombre repetido, un nombre vacío, o un nombre genérico.
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, getString(R.string.ningunCampoVacio), Toast.LENGTH_SHORT).show();
            warning.setText(getString(R.string.ningunCampoVacio));
            warning.setVisibility(View.VISIBLE);
            warning.setTextColor(Color.RED);
            return;
        }
        else if(password.length() < 6) {
            Toast.makeText(this, getString(R.string.requisitosContraseña), Toast.LENGTH_SHORT).show();
            warning.setText(getString(R.string.ningunCampoVacio));
            warning.setVisibility(View.VISIBLE);
            warning.setTextColor(Color.RED);
        }
        else{
            jugadorExisteRegistro(username, password);
        }
    }


    private void jugadorExisteRegistro(String username, String password) {
        Data datos = new Data.Builder().putString("usuario", username).build();
        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(ComprobarExisteYaUsuarioWebService.class).setInputData(datos).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(req.getId()).observe(this, status -> {
            if(status != null && status.getState().isFinished()) {
                existeUsuario = status.getOutputData().getBoolean("existe", false);
                if(existeUsuario){
                    Toast.makeText(this, getString(R.string.usuarioYaExiste), Toast.LENGTH_SHORT).show();
                    warning.setText(getString(R.string.usuarioYaExiste));
                    warning.setVisibility(View.VISIBLE);
                    warning.setTextColor(Color.RED);
                    return;
                }
                else{
                    registrar(username, password);
                }
            }
        });
        WorkManager.getInstance(this).enqueue(req);
    }

    private void registrar(String username, String password) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
            }
        });

        Data datos = new Data.Builder().putString("usuario", username).putString("password", password).putString("token", token).build();
        Constraints restricciones = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(AñadirNuevoUsuarioWebService.class).setInputData(datos).setConstraints(restricciones).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(req.getId()).observe(this, status -> {
            if(status != null && status.getState().isFinished()) {
                Boolean okey = status.getOutputData().getBoolean("OK", false);
                if(okey){
                    Toast.makeText(this, getString(R.string.usuarioAñadidoConExito), Toast.LENGTH_SHORT).show();
                    Intent intentMenuOnline = new Intent(this, OnlineMenuActivity.class);
                    intentMenuOnline.putExtra("user", username);
                    setResult(RESULT_OK, intentMenuOnline);
                    startActivity(intentMenuOnline);
                    finish();
                }

            }
        });
        WorkManager.getInstance(this).enqueue(req);
    }
}