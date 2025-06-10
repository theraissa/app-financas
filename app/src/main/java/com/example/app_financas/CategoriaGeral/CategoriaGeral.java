package com.example.app_financas.CategoriaGeral;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_financas.R;

import java.util.List;

public class CategoriaGeral extends AppCompatActivity {
    private RecyclerView recyclerCatGeral;
    private CatGeralDAO catgeralDAO;
    private CatGeralAdapter catgeralAdapter;
    private EditText editCatGeral;
    private Button btnConfirmarCatGeral;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categoria_geral);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_categoria_geral), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerCatGeral = findViewById(R.id.recyclerViewCategoriaGeral);
        recyclerCatGeral.setLayoutManager(new LinearLayoutManager(this));

        catgeralDAO = new CatGeralDAO(this);

        editCatGeral = findViewById(R.id.editTextCategoriaGeral);
        btnConfirmarCatGeral = findViewById(R.id.btnConfirmarCategoriaGeral);

        btnConfirmarCatGeral.setOnClickListener(v -> {
            String nomeCategoria = editCatGeral.getText().toString().trim();
            if (!nomeCategoria.isEmpty()) {
                CatGeral nova = new CatGeral(0, nomeCategoria);
                catgeralDAO.inserir(nova);
                editCatGeral.setText("");
                carregarCategoriasGeral();
                Toast.makeText(this, "Categoria adicionada!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Digite uma categoria", Toast.LENGTH_SHORT).show();
            }
        });

        carregarCategoriasGeral();

    }
    @Override
    protected void onResume(){
        super.onResume();
        carregarCategoriasGeral();
    }
    private void carregarCategoriasGeral() {
        List<CatGeral> catgerals = catgeralDAO.listar();
        catgeralAdapter = new CatGeralAdapter(catgerals, new CatGeralAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CatGeral catgeral) {
                Toast.makeText(CategoriaGeral.this, "Selecionado: " + catgeral.getNome_categoriaGeral(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onEditarClick(CatGeral catgeral) {
                // Abrir nova tela para edição
                Intent intent = new Intent(CategoriaGeral.this, EditarCatGeral.class);
                intent.putExtra("categoria_id", catgeral.getId_categoriaGeral());
                intent.putExtra("categoria_nome", catgeral.getNome_categoriaGeral());
                startActivity(intent);
            }
            @Override
            public void onExcluirClick(CatGeral catgeral) {
                // Diálogo de confirmação
                new AlertDialog.Builder(CategoriaGeral.this)
                        .setTitle("Excluir Categoria")
                        .setMessage("Tem certeza que deseja excluir esta categoria?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            catgeralDAO.excluir(catgeral);
                            carregarCategoriasGeral();
                            Toast.makeText(CategoriaGeral.this, "Categoria excluída!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        recyclerCatGeral.setAdapter(catgeralAdapter);
    }

}