package com.example.quiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

public class MiDB extends SQLiteOpenHelper {
    public MiDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table players('name' varchar(255) primary key not null )");


        /*sqLiteDatabase.execSQL("create table users('id' integer primary key autoincrement not null, 'username' varchar(255) not null, 'email' varchar(255) not null, 'password' varchar(255) not null)");
        sqLiteDatabase.execSQL("create table restaurants('id' integer primary key autoincrement not null, 'name' varchar(255) not null, 'image_path' varchar(255) not null, 'city' varchar(255) not null)");
        sqLiteDatabase.execSQL("create table food('id' integer primary key autoincrement not null, 'name' varchar(255) not null, 'image_path' varchar(255) not null, 'price' real not null)");
        sqLiteDatabase.execSQL("create table restfood('id' integer primary key autoincrement not null, 'rest_id' integer not null, 'food_id' integer not null, foreign key(\"rest_id\") references restaurants(id), foreign key(\"food_id\") references food(id))");
        sqLiteDatabase.execSQL("create table orders('id' integer not null, 'user_id' integer not null, 'rest_id' integer not null, 'food_id' integer not null, 'price' real not null, foreign key(rest_id) references restaurants(id), foreign key(food_id) references food(id), foreign key(user_id) references users(id), primary key(id, rest_id, food_id, user_id))");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Tagliatella\", \"tagliatella\", \"bilbao\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Burger King\", \"burger\", \"bilbao\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Tagliatella\", \"tagliatella\", \"barcelona\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Burger King\", \"burger\", \"madrid\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Tagliatella\", \"tagliatella\", \"madrid\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Burger King\", \"burger\", \"sevilla\")");
        sqLiteDatabase.execSQL("insert into restaurants(\"name\", \"image_path\", \"city\") values(\"Tagliatella\", \"tagliatella\", \"sevilla\")");*/

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
    }

    public int numeroJugadores(){
       int numero = getReadableDatabase().rawQuery("select name from players", (String[]) null).getCount();
       return numero;
    }

    public void limpiarTablaJugadores(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from players where 1");
    }

    public void eliminarJugador(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from players where name='" + name + "'");
    }

}