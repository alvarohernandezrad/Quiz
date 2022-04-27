package com.example.quiz.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.quiz.R;


public class AdaptorListViewRankingOnline extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    String[] nombres;
    String[] puntos;

    public AdaptorListViewRankingOnline(Context pcontext, String[] pnombres, String[] ppuntos){
        context = pcontext;
        nombres = pnombres;
        puntos = ppuntos;
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

        view = layoutInflater.inflate(R.layout.fila_ranking_online, null);

        TextView nombre = view.findViewById(R.id.nombreRankingOnline);
        TextView puntos = view.findViewById(R.id.puntosRankingOnline);
        nombre.setText(this.nombres[i]);
        puntos.setText(this.puntos[i]);

        Button boton = (Button) view.findViewById(R.id.mapaRankingOnline);
        boton.setText(R.string.verLocalizacion);
        boton.setClickable(false);
        boton.setFocusable(false);
        return view;
    }

}