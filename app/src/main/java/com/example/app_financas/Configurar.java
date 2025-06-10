package com.example.app_financas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_financas.CategoriaFormaPag.CategoriaFormaPag;
import com.example.app_financas.CategoriaGeral.CategoriaGeral;
import com.example.app_financas.CategoriaPagamento.CategoriaPagamento;
import com.example.app_financas.Perfil.CadastroPerfil;

public class Configurar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configurar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.configurar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void telaConsultar(View v){
        Intent i = new Intent(this, ConsultarFinanca.class);
        startActivity(i);
    }
    public void telaGrafico(View v){
        Intent i = new Intent(this, Grafico.class);
        startActivity(i);
    }
    public void telaCadastrarPerfil(View v){
        Intent i = new Intent(this, CadastroPerfil.class);
        startActivity(i);
    }
    public void telaConfigurarCategoriaGeral(View v){
        Intent i = new Intent(this, CategoriaGeral.class);
        startActivity(i);
    }
    public void telaConfigurarCategoriaPagamento(View v){
        Intent i = new Intent(this, CategoriaPagamento.class);
        startActivity(i);
    }
    public void telaConfigurarCategoriaFormaPagamento(View v){
        Intent i = new Intent(this, CategoriaFormaPag.class);
        startActivity(i);
    }


}