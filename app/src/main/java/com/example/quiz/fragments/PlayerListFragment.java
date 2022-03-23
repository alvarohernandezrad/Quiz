package com.example.quiz.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

// Fragment para utilizar en la actividad en horizontal de añadir jugadores

public class PlayerListFragment extends ListFragment {

    public interface listenerDelFragment{
        String[] cargarElementos();
        void eliminarElemento(int position);
    }

    private listenerDelFragment elListener;

    public void onAttach(@NonNull Context context) {
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
        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, datos));
    }

    @Override
    public void onListItemClick(@NonNull ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        elListener.eliminarElemento(position);

    }

    public void actualizarDatos(String[] datos){
        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, datos));
    }




}
