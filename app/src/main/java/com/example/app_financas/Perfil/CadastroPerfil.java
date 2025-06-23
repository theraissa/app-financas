package com.example.app_financas.Perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_financas.MainActivity;
import com.example.app_financas.R;

public class CadastroPerfil extends AppCompatActivity {
    EditText nomePerfil;
    private PerfilDAO perfilDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nomePerfil = findViewById(R.id.editTextNomePerfil);
        perfilDAO = new PerfilDAO(this);

        Perfil perfil = perfilDAO.selecionar();
        if (perfil != null) {
            nomePerfil.setText(perfil.getNome());
        }
    }

    public void CadastrarPerfil(View v){
        String nome = nomePerfil.getText().toString().trim();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um nome.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nome.contains(" ")) {
            Toast.makeText(this, "Digite apenas o primeiro nome (sem espaços).", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nome.length() > 10) {
            Toast.makeText(this, "O nome deve ter no máximo 10 caracteres.", Toast.LENGTH_SHORT).show();
            return;
        }

        Perfil perfilExistente = perfilDAO.selecionar();

        if (perfilExistente != null) {
            // Atualiza o nome do perfil existente
            perfilExistente.setNome(nome);
            perfilDAO.atualizar(perfilExistente);
            Toast.makeText(this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            // Cria um novo perfil
            Perfil novoPerfil = new Perfil(nome);
            perfilDAO.inserir(novoPerfil);
            Toast.makeText(this, "Perfil cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
        }

        nomePerfil.setText("");
        startActivity(new Intent(this, MainActivity.class));
    }



}