package com.example.app_financas.CategoriaFormaPag;

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

import com.example.app_financas.CategoriaGeral.CatGeral;
import com.example.app_financas.CategoriaGeral.CatGeralDAO;
import com.example.app_financas.R;

public class EditarCatFormaPag extends AppCompatActivity {
    private EditText editTextNomeNovo;
    private TextView textViewNomeAntigo;
    private Button btnSalvar;
    private int idCategoria;
    private String nomeAntigo;
    private CatFormaPagDAO catFormaPagDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_cat_forma_pag);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNomeNovo = findViewById(R.id.editTextNomeCatFormaPagNovo);
        textViewNomeAntigo = findViewById(R.id.textViewNomeCatFormaPagAntigo);
        btnSalvar = findViewById(R.id.buttonSalvarCatFormaPag);

        // Recupera os dados da Intent
        Intent intent = getIntent();
        idCategoria = intent.getIntExtra("categoria_id", -1);
        nomeAntigo = intent.getStringExtra("categoria_nome");

        // Mostra o nome antigo
        textViewNomeAntigo.setText("Nome atual: " + nomeAntigo);

        // DAO para atualizar no banco
        catFormaPagDAO = new CatFormaPagDAO(this);

        btnSalvar.setOnClickListener(v -> {
            String novoNome = editTextNomeNovo.getText().toString().trim();
            if (!novoNome.isEmpty()) {
                CatFormaPag categoriaAtualizada = new CatFormaPag(idCategoria, novoNome);
                catFormaPagDAO.atualizar(categoriaAtualizada);
                Toast.makeText(this, "Categoria atualizada!", Toast.LENGTH_SHORT).show();
                finish(); // Fecha a tela e volta
            } else {
                Toast.makeText(this, "Digite um novo nome", Toast.LENGTH_SHORT).show();
            }
        });


    }
}