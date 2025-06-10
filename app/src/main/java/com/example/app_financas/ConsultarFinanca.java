package com.example.app_financas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConsultarFinanca extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar_financa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ConsultarFinanca), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void telaGrafico(View v){
        Intent i = new Intent(this, Grafico.class);
        startActivity(i);
    }
    public void telaConfigurar(View v){
        Intent i = new Intent(this, Configurar.class);
        startActivity(i);
    }
}