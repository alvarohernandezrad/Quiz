package com.example.quiz.services;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ServicioFirebase extends FirebaseMessagingService {
    public ServicioFirebase(){}

    public void onMessageReceived(RemoteMessage remoteMessage){
        if(remoteMessage.getData().size() > 0){
            // Viene con datos
        }
        if(remoteMessage.getNotification() != null){
            // Recibe notificación
        }
    }

    public void onNewToken(String s){
        super.onNewToken(s);
    }
}
