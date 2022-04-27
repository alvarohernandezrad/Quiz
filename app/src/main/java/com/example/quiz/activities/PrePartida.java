package com.example.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.R;
import com.example.quiz.adaptors.AdaptorListViewBotonEliminar;
import com.example.quiz.database.MiDB;
import com.example.quiz.dialogs.AceptarJugadoresDialog;
import com.example.quiz.models.AuxiliarColores;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

// Actividad justo anterior al comienzo de la partida. Última oportunidad para decidir la lista definitiva de jugadores

public class PrePartida extends AppCompatActivity implements AceptarJugadoresDialog.ListenerDelDialogo {
    MiDB database;
    TextView texto;
    ListView jugadores;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuxiliarColores.elegirColor(this);
        setContentView(R.layout.activity_pre_partida);

        this.texto = findViewById(R.id.textoAceptar);
        this.boton = findViewById(R.id.botonAceptar);
        this.database = new MiDB(this, "App", (SQLiteDatabase.CursorFactory) null, 1);

        this.texto.setText(R.string.listaJugadores);
        this.boton.setText(R.string.jugar);

        String[] nombres = obtenerJugadores();
        jugadores = (ListView)findViewById(R.id.lista);
        AdaptorListViewBotonEliminar adaptor = new AdaptorListViewBotonEliminar(getApplicationContext(), nombres);
        jugadores.setAdapter(adaptor);

        jugadores.setOnItemClickListener((adapterView, view, i, l) -> {
            String nombre = (String) adapterView.getItemAtPosition(i);
            database.eliminarJugador(nombre);
            // Datos actualizados
            adaptor.notifyDataSetChanged();
            String[] nombres1 = obtenerJugadores();
            AdaptorListViewBotonEliminar adaptor1 = new AdaptorListViewBotonEliminar(getApplicationContext(), nombres1);
            jugadores.setAdapter(adaptor1);
        });
    }

    @Override
    public void clickarSiDialog() {
        /* Queremos tener los ids ordenados del 0 al 5. Sin embargo, en el proceso de añadir y borrar los IDs, hemos dejado
        números desordenadados, dejándo así los número de ID mal, por ejemplo, para 6 jugadores {0, 2, 3, 5, 6, 7},
        cuando en realidad queremos tener la secuencia {0, 1, 2, 3, 4, 5}.
        Para eso existe el método reordenar Ids. Ahora quedará así ->
        El primero de los id de los jugadores empezará en 0. Si no hay jugadores añadidos anteriormente el primer jugador tendrá
        el id = 0 -> número de jugadores hasta ahora (0). Para los demás, +1 del anterior. Así, aunque se borren jugadores en el proceso,
        al final los ID-s irán del 0 al 5 */
        this.database.reordenarIds();
        limpiarFicheroLog();
        Intent intentJugar = new Intent(this, Partida.class);
        startActivity(intentJugar);
    }

    @Override
    public void clickarNoDialog() {
    }

    // Crear el array que le vamos a pasar al ListView a partir de los datos de la base de datos.
    private String[] obtenerJugadores(){
        ArrayList<String> jugadores = this.database.getNombresJugadores();
        String[] s = new String[jugadores.size()];
        int i = 0;
        for (String nombre : jugadores)
            s[i++] = nombre;
        return s;
    }

    public void mostrarDialog(View v){
        int numeroJugadores = this.database.numeroJugadores();
        if(numeroJugadores < 2) {
            Toast.makeText(this, R.string.faltanJugadores, Toast.LENGTH_SHORT).show();
        }else {
            DialogFragment dialogo = new AceptarJugadoresDialog();
            dialogo.show(getSupportFragmentManager(), "aceptar");
        }
    }

    private void limpiarFicheroLog(){
        try{
            OutputStreamWriter fichero = new OutputStreamWriter(openFileOutput("log.txt", Context.MODE_PRIVATE));
            fichero.write("");
            fichero.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}