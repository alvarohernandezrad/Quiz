package com.example.quiz.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quiz.R;

public class AdaptorListViewJugadorPuntos extends BaseAdapter {
    private Context contexto;
    private LayoutInflater inflater;
    private String[] nombres;
    private int[] puntos;

    public AdaptorListViewJugadorPuntos(Context pContext, String[] pNombres, int[] pPuntos){
        this.contexto = pContext;
        this.nombres = pNombres;
        this.puntos = pPuntos;
        this.inflater = (LayoutInflater) this.contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return nombres.length;
    }

    @Override
    public Object getItem(int i) {
        return nombres[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = this.inflater.inflate(R.layout.jugadores_puntos, null);
        TextView nombre = (TextView) view.findViewById(R.id.jugadorListViewPartida);
        TextView puntuacion = (TextView) view.findViewById(R.id.puntosListViewPartida);

        nombre.setText(this.nombres[i]);
        puntuacion.setText(String.valueOf(this.puntos[i]));
        return view;
    }
}
