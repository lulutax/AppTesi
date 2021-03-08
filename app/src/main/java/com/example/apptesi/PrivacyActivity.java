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
        String text= "L'applicazione CrowdCampus rispetta la privacy dell'utente, infatti, utilizza il " +
                     "codice IMEI (International Mobile Equipe Identity) per identificare il dispositivo " +
                    "dell'utente all'interno del campus. La posizione e il relativo codice IMEI vengono " +
                    "salvate solo se il dispositivo risulta essere all'interno dell'Unical, e rimossa " +
                     "non appena la posizione non è all'interno del campus.";

        message.append(text);
    }
}