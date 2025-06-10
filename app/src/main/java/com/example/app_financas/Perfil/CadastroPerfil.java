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

    }

    public void CadastrarPerfil(View v){
        String nome = nomePerfil.getText().toString().trim(); //trim tira os espaçamentos antes e depois

        if(!nome.isEmpty()){
            Perfil perfil = new Perfil(nome);
            perfilDAO.remover();
            long id = perfilDAO.inserir(perfil);
            Toast.makeText(this, "Usuário cadastrado", Toast.LENGTH_SHORT).show(); //show para visualizar
            nomePerfil.setText(""); //limpando a entrada
            //trocar de tela
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


        }

    }



}