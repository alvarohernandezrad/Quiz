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

public class AdaptorListViewBotonEliminar extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private String[] nombres;

    public AdaptorListViewBotonEliminar(Context pcontext, String[] pnombres){
        context = pcontext;
        nombres = pnombres;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        view = layoutInflater.inflate(R.layout.fila, null);

        TextView nombre = (TextView) view.findViewById(R.id.nombre);
        Button boton = (Button) view.findViewById(R.id.botonEliminar);

        nombre.setText(nombres[i]);
        boton.setText(R.string.eliminar);

        boton.setClickable(false);
        boton.setFocusable(false);
        return view;
    }
}
