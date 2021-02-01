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
        String text= "Questa applicazione non usa alcuna informazione dell'utente che la usa, vengono salvati i dati riguardo la posizione attraverso l'imei del telefono.";

        message.append(text);
    }
}