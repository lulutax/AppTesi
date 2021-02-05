package com.example.apptesi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        TextView message = findViewById(R.id.textView2);
        StringBuilder stringBuilder = new StringBuilder();
        String text= "L'app Crowd Campus per tracciare il numero degli utenti all'interno del campus utilizza il codice IMEI, International Mobile Equipe Identity, " +
                "così da identificare il dispositivo che si trova in un dato momento e in un determinato luogo. Questo codice verrà rimosso dal nostro Database nel momento " +
                "in cui l'utente esce dal campus. ";

        message.append(text);
    }
}