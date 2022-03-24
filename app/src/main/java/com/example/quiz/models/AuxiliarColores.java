package com.example.quiz.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;


import androidx.preference.PreferenceManager;

import com.example.quiz.R;

import java.util.Locale;


// Como necesitaba un método para utilizar en todas las actividades cree esta clase. Pone el color favorito del jugador como tema de la app

public class AuxiliarColores {

    private static String color;
    private static String idioma;

    public static void setColor(String colorTema){
        AuxiliarColores.color = colorTema;
    }
    public static void setIdioma(String pIdioma){
        AuxiliarColores.idioma = pIdioma;
    }

    public static String getColor(){
        return color;
    }
    public static String getIdioma(){
        return idioma;
    }

    public static void elegirColor(Context context) {
        switch (color) {
            case "normal":
                context.setTheme(R.style.Theme_Quiz);
                break;
            case "rojo":
                context.setTheme(R.style.TemaRojo);
                break;
            case "azul":
                context.setTheme(R.style.TemaAzul);
                break;
            case "verde":
                context.setTheme(R.style.TemaVerde);
                break;
            case "amarillo":
                context.setTheme(R.style.TemaAmarillo);
                break;
            case "morado":
                context.setTheme(R.style.TemaMorado);
                break;
            case "rosa":
                context.setTheme(R.style.TemaRosa);
                break;
            case "naranja":
                context.setTheme(R.style.TemaNaranja);
                break;
            default:
                context.setTheme(R.style.Theme_Quiz);
                break;
        }
    }
}
