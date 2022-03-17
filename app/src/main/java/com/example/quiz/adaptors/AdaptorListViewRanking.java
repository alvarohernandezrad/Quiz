package com.example.quiz.adaptors;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quiz.R;
import com.example.quiz.activities.PrePartida;
import com.example.quiz.database.MiDB;

import java.util.Arrays;

public class AdaptorListViewRanking extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private String[] nombres;
    private int[] imagenes;

    public AdaptorListViewRanking(Context pcontext, String[] pnombres){
        this.context = pcontext;
        this.nombres = pnombres;
        this.layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount(){
        return nombres.length;
    }

    @Override
    public Object getItem(int i){
        return nombres[i];
    }

    @Override
    public long getItemId(int i){
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){

        view = layoutInflater.inflate(R.layout.ranking, null);
        this.imagenes = new int[this.getCount()];
        for(int j = 0; j < this.imagenes.length; j++){
            switch (i){
                case 0:
                    this.imagenes[i] = R.drawable.medalla1;
                case 1:
                    this.imagenes[i] = R.drawable.medalla2;
                case 2:
                    this.imagenes[i] = R.drawable.medalla3;
                default:
                    this.imagenes[i] = R.drawable.carafeliz;
            }
        }
        TextView nombre = (TextView) view.findViewById(R.id.nombreRanking);
        ImageView imagen = (ImageView) view.findViewById(R.id.imagenRanking);

        nombre.setText(nombres[i]);
        imagen.setImageResource(this.imagenes[i]);
        return view;
    }
}