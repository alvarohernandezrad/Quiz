package com.example.quiz.models;

import java.util.ArrayList;

public class Turno {
    private String pregunta;
    private ArrayList<String> respuestas = new ArrayList<String>();
    private String tipo;
    private int correcta;

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public ArrayList<String> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<String> respuestas) {
        this.respuestas = respuestas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCorrecta() {
        return correcta;
    }

    public void setCorrecta(int correcta) {
        this.correcta = correcta;
    }

    public Turno(String pregunta, ArrayList<String> respuestas, String tipo, int correcta){
        this.pregunta = pregunta;
        this.respuestas = respuestas;
        this.tipo = tipo;
        this.correcta = correcta;
    }


}
