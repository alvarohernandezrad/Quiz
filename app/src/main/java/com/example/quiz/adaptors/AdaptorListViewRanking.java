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
    private int[] imagenes = {R.drawable.medalla1, R.drawable.medalla2, R.drawable.medalla3, R.drawable.carafeliz, R.drawable.carafeliz, R.drawable.carafeliz};

    public AdaptorListViewRanking(Context pcontext, String[] pnombres){
        context = pcontext;
        nombres = pnombres;
        layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        TextView nombre = view.findViewById(R.id.nombreRanking);
        ImageView imagen = view.findViewById(R.id.fotoRanking);

        Log.d("longitud",String.valueOf(this.getCount()));
        nombre.setText(this.nombres[i]);
        imagen.setImageResource(this.imagenes[i]);
        return view;
    }
}