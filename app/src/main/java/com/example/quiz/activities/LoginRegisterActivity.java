package com.example.quiz.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.quiz.R;
import com.example.quiz.conexionesBDWebServices.ComprobarUsuarioWebService;
import com.example.quiz.models.AuxiliarColores;
import com.google.firebase.iid.FirebaseInstanceId;


public class LoginRegisterActivity extends AppCompatActivity {

    EditText user, password;
    TextView registro, warning;
    ImageView imagen;
    Button botonLogin;
    static boolean existeUsuario;
    static String token;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && resultCode == RESULT_OK){
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        setContentView(R.layout.activity_login_register);

        user = findViewById(R.id.textUser);
        password = findViewById(R.id.textPassword);
        imagen = findViewById(R.id.imagenLogin);
        botonLogin = findViewById(R.id.botonLogin);
        registro = findViewById(R.id.textRegistro);
        warning = findViewById(R.id.textWarning);

        registro.setText(R.string.registrar);
        warning.setVisibility(View.INVISIBLE);
        botonLogin.setText(R.string.iniciarSesion);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> token = instanceIdResult.getToken());
    }



    // Método para gestionar click en login
    public void onLogin(View v) {
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
        // El jugador no exíste
        else{
            jugadorExiste(username, password);
        }
    }

    public void onRegister(View v){
        Intent intentRegistro = new Intent(this, Registro.class);
        startActivityForResult(intentRegistro, 10);
    }



    private void jugadorExiste(String username, String password) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        }else{
            Toast.makeText(this, getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
        }
        // Pasamos los datos al worker para que haga el registro
        Data datos = new Data.Builder().putString("usuario", username).putString("password", password).putString("token", token).build();
        Constraints restricciones = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(ComprobarUsuarioWebService.class).setInputData(datos).setConstraints(restricciones).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(req.getId()).observe(this, status -> {
            if(status != null && status.getState().isFinished()) {
                existeUsuario = status.getOutputData().getBoolean("existe", false);
                if(!existeUsuario){
                    Toast.makeText(this, getString(R.string.jugadorNoExisteInvalidPassword), Toast.LENGTH_SHORT).show();
                    warning.setText(getString(R.string.jugadorNoExisteInvalidPassword));
                    warning.setVisibility(View.VISIBLE);
                    warning.setTextColor(Color.RED);
                    return;
                }
                else{
                    SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username);
                    editor.commit();
                    Intent intentOnline = new Intent(this, OnlineMenuActivity.class);
                    intentOnline.putExtra("user", username);
                    startActivity(intentOnline);
                    finish();
                }
            }
        });
        WorkManager.getInstance(this).enqueue(req);
    }
}