package com.example.quiz.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.quiz.R;

public class AceptarJugadoresDialog extends DialogFragment {
    ListenerDelDialogo miListener;

    public interface  ListenerDelDialogo{
        void clickarSiDialog();
        void clickarNoDialog();
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        miListener = (ListenerDelDialogo) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.estasSeguro);
        builder.setPositiveButton(R.string.siSeguro, (dialogInterface, i) -> miListener.clickarSiDialog());
        builder.setNegativeButton("No", (dialogInterface, i) -> miListener.clickarNoDialog());
        return builder.create();
    }
}
