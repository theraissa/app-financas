package com.example.app_financas.CategoriaPagamento;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_financas.R;

public class EditarCatPag extends AppCompatActivity {
    private EditText editTextNomeNovo;
    private TextView textViewNomeAntigo;
    private Button btnSalvar;
    private int idCategoria;
    private String nomeAntigo;
    private CatPagDAO catpagDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_cat_pag);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNomeNovo = findViewById(R.id.editTextNomeCatPagNovo);
        textViewNomeAntigo = findViewById(R.id.textViewNomeCatPagAntigo);
        btnSalvar = findViewById(R.id.buttonSalvarCatPag);

        // Recupera os dados da Intent
        Intent intent = getIntent();
        idCategoria = intent.getIntExtra("categoria_id", -1);
        nomeAntigo = intent.getStringExtra("categoria_nome");

        // Mostra o nome antigo
        textViewNomeAntigo.setText("Nome atual: " + nomeAntigo);

        // DAO para atualizar no banco
        catpagDAO = new CatPagDAO(this);

        btnSalvar.setOnClickListener(v -> {
            String novoNome = editTextNomeNovo.getText().toString().trim();
            if (!novoNome.isEmpty()) {
                CatPag categoriaAtualizada = new CatPag(idCategoria, novoNome);
                catpagDAO.atualizar(categoriaAtualizada);
                Toast.makeText(this, "Categoria atualizada!", Toast.LENGTH_SHORT).show();
                finish(); // Fecha a tela e volta
            } else {
                Toast.makeText(this, "Digite um novo nome", Toast.LENGTH_SHORT).show();
            }
        });


    }



}