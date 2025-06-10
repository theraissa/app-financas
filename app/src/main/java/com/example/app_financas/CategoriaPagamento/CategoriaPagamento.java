package com.example.app_financas.CategoriaPagamento;

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

import com.example.app_financas.CategoriaGeral.CatGeral;
import com.example.app_financas.CategoriaGeral.CatGeralAdapter;
import com.example.app_financas.CategoriaGeral.CatGeralDAO;
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
        catpagAdapter = new CatPagAdapter(catpags, catpag -> {
            Toast.makeText(this, "Selecionado: " + catpag.getNome_categoriaPag(), Toast.LENGTH_SHORT).show();
        });
        recyclerCatPag.setAdapter(catpagAdapter);
    }

}