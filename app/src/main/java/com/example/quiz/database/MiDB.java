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
        sqLiteDatabase.execSQL("create table players('name' varchar(255) primary key not null )");
        sqLiteDatabase.execSQL("create table preguntas('id' int primary key not null, 'hecha' int not null, 'pregunta' text not null, 'respuesta1' varchar(255) not null, 'respuesta2' varchar(255) not null, 'respuesta3' varchar(255) not null, 'respuesta4' varchar(255) not null, 'tipo' varchar(255) not null, 'correcta' int not null)");
        sqLiteDatabase.execSQL("insert into preguntas values (1, 0, '¿Quien es mejor?','Messi', 'Ronaldo', 'Aduriz', 'Mbappe', 'Deportes',3)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<String> getNombresJugadores(){
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("select name from players", (String[]) null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(0));
        }
        cursor.close();
        return list;
    }

    public boolean existeJugador(String nickname){
       if(getReadableDatabase().rawQuery("select name from players where name='" + nickname + "'", (String[]) null).getCount() == 0){
            return false;
       }else return true;
    }

    public void insertarJugador(String nickname){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into players values('"+ nickname+"')");
        db.close();
    }

    public int numeroJugadores(){
       int numero = getReadableDatabase().rawQuery("select name from players", (String[]) null).getCount();
       return numero;
    }

    public void limpiarTablaJugadores(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from players where 1");
        db.close();
    }

    public void eliminarJugador(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from players where name='" + name + "'");
        db.close();
    }

    public Turno recibirPregunta(){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("select id, pregunta, respuesta1, respuesta2, respuesta3, respuesta4, tipo, correcta from preguntas where hecha < 1", null);
        cursor.moveToFirst();
        String pregunta = cursor.getString(1);
        ArrayList<String> respuestas = new ArrayList<String>();
        for (int i = 2; i <= 5; i++) {
            respuestas.add(cursor.getString(i));
        }
        int id = cursor.getInt(0);
        String tipo = cursor.getString(6);
        int correcta = cursor.getInt(7);
        Turno turno = new Turno(pregunta, respuestas, tipo, correcta);
        return turno;
    }



}