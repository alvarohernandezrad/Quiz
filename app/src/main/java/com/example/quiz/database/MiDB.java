package com.example.quiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.quiz.models.Turno;

import java.util.ArrayList;

public class MiDB extends SQLiteOpenHelper {
    public MiDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL("create table players('name' varchar(255) primary key not null)");
        sqLiteDatabase.execSQL("CREATE TABLE jugadores('nombre' VARCHAR(255) PRIMARY KEY NOT NULL, 'id' INTEGER NOT NULL, 'puntos' INTEGER not null)");
        sqLiteDatabase.execSQL("CREATE TABLE preguntas('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'hecha' INTEGER NOT NULL, 'pregunta' TEXT NOT NULL, 'respuesta1' VARCHAR(255) NOT NULL, 'respuesta2' VARCHAR(255) NOT NULL, 'respuesta3' VARCHAR(255) NOT NULL, 'respuesta4' VARCHAR(255) NOT NULL, 'tipo' VARCHAR(255) NOT NULL, 'correcta' INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Quien es mejor?','Messi', 'Ronaldo', 'Aduriz', 'Mbappe', 'Deportes',3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Quien es mejor?','Maria', 'Laura', 'Sara', 'Claudia', 'Deportes',0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // Obtener los nombres de los jugadores logrando un ArrayList
    public ArrayList<String> getNombresJugadores(){
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT nombre FROM jugadores", (String[]) null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(0));
        }
        cursor.close();
        return list;
    }

    // Obtener los nombres de los jugadores en un Array y por orden de id
    public String[] obtenerNombresJugadores(){
        String[] nombres = new String[this.numeroJugadores()];
        int i = 0;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT nombre FROM jugadores ORDER BY id", (String[]) null);
        while(cursor.moveToNext()){
            nombres[i] = cursor.getString(0);
            i++;
        }
        cursor.close();
        return nombres;
    }

    // Obtener las puntuaciones actuales de los jugadores y por orden de id
    public int[] obtenerPuntuacionesJugadores(){
        int[] puntuaciones = new int[this.numeroJugadores()];
        int i = 0;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT puntos FROM jugadores ORDER BY id", (String[]) null);
        while(cursor.moveToNext()){
            puntuaciones[i] = cursor.getInt(0);
            i++;
        }
        cursor.close();
        return puntuaciones;
    }

    public boolean existeJugador(String nickname){
       if(getReadableDatabase().rawQuery("SELECT nombre FROM jugadores WHERE nombre='" + nickname + "'", (String[]) null).getCount() == 0){
            return false;
       }else return true;
    }

    public void insertarJugador(int id, String nickname){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO jugadores VALUES(" + id + ", '"+ nickname + "', 0)");
        db.close();
    }

    public int numeroJugadores(){
       int numero = getReadableDatabase().rawQuery("SELECT * FROM jugadores", (String[]) null).getCount();
       return numero;
    }

    public int numeroPreguntas(){
        int numero = getReadableDatabase().rawQuery("SELECT * FROM preguntas", (String[]) null).getCount();
        return numero;
    }

    public void limpiarTablaJugadores(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM jugadores WHERE 1");
        db.close();
    }

    public void eliminarJugador(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM jugadores WHERE nombre='" + name + "'");
        db.close();
    }

    public Turno recibirPreguntaYMarcarComoLeida(){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, pregunta, respuesta1, respuesta2, respuesta3, respuesta4, tipo, correcta FROM preguntas WHERE hecha = 0 ORDER BY random() LIMIT 1", null);
        cursor.moveToFirst();
        // Obtenemos el identificador de la pregunta para poder marcarla como leída después.
        int id = cursor.getInt(0);
        // Obtenemos los elementos necesarios para construir un Turno
        String pregunta = cursor.getString(1);
        ArrayList<String> respuestas = new ArrayList<String>();
        for (int i = 2; i <= 5; i++) {
            respuestas.add(cursor.getString(i));
        }
        String tipo = cursor.getString(6);
        int correcta = cursor.getInt(7);
        Turno turno = new Turno(id, pregunta, respuestas, tipo, correcta);

        cursor.close();
        // Marcamos la pregunta como leída
        //db.execSQL("update preguntas set hecha = 1 where id = '"+ id +"'");
        db.close();
        return turno;
    }

    public void marcarPreguntasComoNoLeidas(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE preguntas SET hecha = 0");
        db.close();
    }

    public String nombreJugadorActual(int idJugadorActual){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT nombre FROM jugadores WHERE id = " + idJugadorActual + "", null);
        cursor.moveToFirst();
        // Obtenemos el nombre del jugador con el id
        String nombre = cursor.getString(0);

        cursor.close();
        db.close();
        return nombre;
    }

    public void sumarPunto(int idJugadorAcierto){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE jugadores SET puntos = puntos+1 WHERE id = "+idJugadorAcierto+"");
        db.close();
    }

    public boolean comprobarGanador(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(puntos) FROM jugadores", null);
        cursor.moveToFirst();
        // Obtenemos el nombre del jugador con el id
        int puntos = cursor.getInt(0);
        cursor.close();
        db.close();

        if(puntos == 7) { return true;}
        else{ return false;}
    }

    public int idUltimoJugador(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(id) FROM jugadores", null);
        cursor.moveToFirst();
        // Obtenemos el nombre del jugador con el id
        int id = cursor.getInt(0);
        cursor.close();
        db.close();
        return id;
    }

    public void reordenarIds(){
        // Obtenemos todos los ids en un arraylist
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM jugadores", null);
        ArrayList<Integer> listaIds = new ArrayList<Integer>();
        // Mientras haya más ids
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            listaIds.add(id);
        }
        // Una vez tenemos los IDs los vamos reordenando empezando por el 0
        for(int i = 0; i < listaIds.size(); i++){
            int idCambiar = listaIds.get(i);
            db.execSQL("UPDATE jugadores SET id = "+i+" WHERE id = "+idCambiar+"");
        }
        cursor.close();
        db.close();
    }

    // Obtener los nombres de los jugadores en un Array y por orden de puntos
    public String[] jugadoresPorPuntos(){
        String[] nombres = new String[this.numeroJugadores()];
        int i = 0;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT nombre FROM jugadores ORDER BY puntos DESC", (String[]) null);
        while(cursor.moveToNext()){
            nombres[i] = cursor.getString(0);
            i++;
        }
        cursor.close();
        return nombres;
    }
}