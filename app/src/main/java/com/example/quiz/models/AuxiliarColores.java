package com.example.quiz.models;

import android.content.Context;

import com.example.quiz.R;

// Como necesitaba un método para utilizar en todas las actividades cree esta clase. Pone el color favorito del jugador como tema de la app

public class AuxiliarColores {

    private static String color;

    public static void setColor(String colorTema){
        AuxiliarColores.color = colorTema;
    }

    public static String getColor(){
        return color;
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
