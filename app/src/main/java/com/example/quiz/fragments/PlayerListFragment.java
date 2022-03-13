package com.example.quiz.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.quiz.R;
import com.example.quiz.activities.AddPlayers;
import com.example.quiz.database.MiDB;

import java.util.ArrayList;

public class PlayerListFragment extends ListFragment {

    public interface listenerDelFragment{
        String[] cargarElementos();
        void eliminarElemento(int position);
    }

    private listenerDelFragment elListener;

    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            elListener=(listenerDelFragment) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException("La clase " + context.toString()
                    + "debe implementar listenerDelFragment");
        }
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] datos = elListener.cargarElementos();
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datos));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        elListener.eliminarElemento(position);

    }

    public void actualizarDatos(String[] datos){
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datos));
    }




}
