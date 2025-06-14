package com.example.app_financas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Grafico extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grafico);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Grafico), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void telaConsultar(View v){
        Intent i = new Intent(this, ConsultarFinanca.class);
        startActivity(i);
    }
    public void telaConfigurar(View v){
        Intent i = new Intent(this, Configurar.class);
        startActivity(i);
    }
}