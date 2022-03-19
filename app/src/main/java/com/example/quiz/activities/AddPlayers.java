package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.R;
import com.example.quiz.database.MiDB;
import com.example.quiz.fragments.PlayerListFragment;

import java.util.ArrayList;

public class AddPlayers extends AppCompatActivity implements PlayerListFragment.listenerDelFragment {

    private MiDB database;
    private ImageView imagen;
    private Button botonJugar, botonAdd;
    private EditText nombre;
    private TextView texto, textoLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        this.imagen = findViewById(R.id.imagenAddPlayers);
        this.texto = findViewById(R.id.textoAddPlayers);
        this.botonJugar = findViewById(R.id.botonJugarAddPlayers);
        this.botonAdd = findViewById(R.id.botonAddAddPlayers);
        this.nombre = (EditText) findViewById(R.id.nombreAddPlayers);
        this.database = new MiDB(this, "App", (SQLiteDatabase.CursorFactory) null, 1);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.textoLista = findViewById(R.id.textoLista);
            textoLista.setText(R.string.eliminarJugador);
            actualizarDatos();
        }
        // Asignar texto correspondiente a cada elemento
        imagen.setImageResource(R.drawable.quizimagen);
        texto.setText(R.string.textoAñadir);
        botonJugar.setText(R.string.jugar);
        botonAdd.setText(R.string.añadir);

        /*nombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                nombre.setText("");
            }
        });*/
    }


    // Métodos sobreescritos del listener del fragment
    public String[] cargarElementos() {
        ArrayList<String> jugadores = this.database.getNombresJugadores();
        String[] s = new String[jugadores.size()];
        int i = 0;
        for (String nombre : jugadores)
            s[i++] = nombre;
        return s;
    }

    public void eliminarElemento(int pos) {
        ArrayList<String> jugadores = this.database.getNombresJugadores();
        String[] s = new String[jugadores.size()];
        int i = 0;
        for (String nombre : jugadores)
            s[i++] = nombre;
        String eliminar = s[pos];
        this.database.eliminarJugador(eliminar);
        actualizarDatos();
    }

    // Método para gestionar click en añadir jugador
    public void onAddPlayer(View v){
        String nickname = this.nombre.getText().toString();
        // Uso de Toast para evitar un nombre repetido, un nombre vacío, o un nombre genérico.
        if(nickname.isEmpty()){
            Toast.makeText(this, getString(R.string.campoVacio), Toast.LENGTH_SHORT).show();
            return;
        }
        // El jugador ya exíste
        else if(this.database.existeJugador(nickname)){
            Toast.makeText(this, getString(R.string.nombreYaExiste), Toast.LENGTH_SHORT).show();
            return;
        }
        // Lista de jugadores completa
        else if(this.database.numeroJugadores() == 6){
            Toast.makeText(this, R.string.noMasJugadores, Toast.LENGTH_SHORT).show();
            return;
        }
        // Añadir un jugador nuevo
        else{
            // Se añadirá como id del nuevo jugador el siguiente número al id del último jugador.
            this.nombre.setText("");
            int id = this.database.idUltimoJugador()+1;
            this.database.insertarJugador(id, nickname);
            Toast.makeText(this, "Nuevo jugador añadido: "+nickname, Toast.LENGTH_SHORT).show();
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                actualizarDatos();
            }
        }
    }

    // Método para gestionar click en empezar partida
    public void onJugar(View v) {
        int numeroJugadores = this.database.numeroJugadores();
        Intent intentPrePartida = new Intent(this, PrePartida.class);
        if(numeroJugadores < 2) {
            Toast.makeText(this, R.string.faltanJugadores, Toast.LENGTH_SHORT).show();
            return;
        }else startActivity(intentPrePartida);
    }

    private void actualizarDatos(){
        PlayerListFragment listFragment = (PlayerListFragment) getSupportFragmentManager().findFragmentById(R.id.listFragment);
        String[] datos = cargarElementos();
        listFragment.actualizarDatos(datos);
    }


}