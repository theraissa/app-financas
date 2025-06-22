package com.example.app_financas.CategoriaPagamento;

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

public class CategoriaPagamento extends AppCompatActivity {
    private RecyclerView recyclerCatPag;
    private CatPagDAO catpagDAO;
    private CatPagAdapter catpagAdapter;
    private EditText editCatPag;
    private Button btnConfirmarCatPag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categoria_pagamento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_categoria_pagamento), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerCatPag = findViewById(R.id.recyclerViewCategoriaPagamento);
        recyclerCatPag.setLayoutManager(new LinearLayoutManager(this));

        catpagDAO = new CatPagDAO(this);

        editCatPag = findViewById(R.id.editTextCategoriaPagamento);
        btnConfirmarCatPag = findViewById(R.id.btnConfirmarCategoriaPagamento);

        btnConfirmarCatPag.setOnClickListener(v -> {
            String nomeCategoria = editCatPag.getText().toString().trim();
            if (!nomeCategoria.isEmpty()) {
                CatPag nova = new CatPag(0, nomeCategoria);
                catpagDAO.inserir(nova);
                editCatPag.setText("");
                carregarCategoriasPagamento();
                Toast.makeText(this, "Categoria adicionada!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Digite uma categoria", Toast.LENGTH_SHORT).show();
            }
        });

        carregarCategoriasPagamento();
    }

    @Override
    protected void onResume(){
        super.onResume();
        carregarCategoriasPagamento();
    }
    private void carregarCategoriasPagamento(){
        List<CatPag> catpags = catpagDAO.listar();
        catpagAdapter = new CatPagAdapter(catpags, new CatPagAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CatPag catpag) {
                Toast.makeText(CategoriaPagamento.this, "Selecionado: " + catpag.getNome_categoriaPag(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onEditarClick(CatPag catpag) {
                // Abrir nova tela para edição
                Intent intent = new Intent(CategoriaPagamento.this, EditarCatPag.class);
                intent.putExtra("categoria_id", catpag.getId_categoriaPag());
                intent.putExtra("categoria_nome", catpag.getNome_categoriaPag());
                startActivity(intent);
            }
            @Override
            public void onExcluirClick(CatPag catpag) {
                if (catpagDAO.catPagTransacoesRelacionadas(catpag.getId_categoriaPag())) {
                    Toast.makeText(CategoriaPagamento.this, "Não é possível excluir: há transações vinculadas a esta categoria!", Toast.LENGTH_LONG).show();
                    return;
                }

                new AlertDialog.Builder(CategoriaPagamento.this)
                        .setTitle("Excluir Categoria")
                        .setMessage("Tem certeza que deseja excluir esta categoria?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            catpagDAO.excluir(catpag);
                            carregarCategoriasPagamento();
                            Toast.makeText(CategoriaPagamento.this, "Categoria excluída!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        recyclerCatPag.setAdapter(catpagAdapter);
    }

}