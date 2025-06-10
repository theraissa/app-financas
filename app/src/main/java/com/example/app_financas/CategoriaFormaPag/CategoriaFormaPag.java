package com.example.app_financas.CategoriaFormaPag;

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

public class CategoriaFormaPag extends AppCompatActivity {
    private RecyclerView recyclerCatFormaPag;
    private CatFormaPagDAO catformapagDAO;
    private CatFormaPagAdapter catformapagAdapter;
    private EditText editCatFormaPag;
    private Button btnConfirmarCatFormaPag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categoria_forma_pag);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_categoria_forma_pag), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerCatFormaPag = findViewById(R.id.recyclerViewCategoriaFormaPag);
        recyclerCatFormaPag.setLayoutManager(new LinearLayoutManager(this));

        catformapagDAO = new CatFormaPagDAO(this);

        editCatFormaPag = findViewById(R.id.editTextCategoriaFormaPag);
        btnConfirmarCatFormaPag = findViewById(R.id.btnConfirmarCategoriaFormaPag);

        btnConfirmarCatFormaPag.setOnClickListener(v -> {
            String nomeCategoria = editCatFormaPag.getText().toString().trim();
            if (!nomeCategoria.isEmpty()) {
                CatFormaPag nova = new CatFormaPag(0, nomeCategoria);
                catformapagDAO.inserir(nova);
                editCatFormaPag.setText("");
                carregarCategoriasFormaPag();
                Toast.makeText(this, "Categoria adicionada!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Digite uma categoria", Toast.LENGTH_SHORT).show();
            }
        });
        carregarCategoriasFormaPag();

    }
    @Override
    protected void onResume(){
        super.onResume();
        carregarCategoriasFormaPag();
    }
    private void carregarCategoriasFormaPag(){
        List<CatFormaPag> catformapags = catformapagDAO.listar();
        catformapagAdapter = new CatFormaPagAdapter(catformapags, new CatFormaPagAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CatFormaPag catformapag) {
                Toast.makeText(CategoriaFormaPag.this, "Selecionado: " + catformapag.getNome_categoriaFormaPag(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onEditarClick(CatFormaPag catformapag) {
                // Abrir nova tela para edição
                Intent intent = new Intent(CategoriaFormaPag.this, EditarCatFormaPag.class);
                intent.putExtra("categoria_id", catformapag.getId_categoriaFormaPag());
                intent.putExtra("categoria_nome", catformapag.getNome_categoriaFormaPag());
                startActivity(intent);
            }
            @Override
            public void onExcluirClick(CatFormaPag catformapag) {
                // Diálogo de confirmação
                new AlertDialog.Builder(CategoriaFormaPag.this)
                        .setTitle("Excluir Categoria")
                        .setMessage("Tem certeza que deseja excluir esta categoria?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            catformapagDAO.excluir(catformapag);
                            carregarCategoriasFormaPag();
                            Toast.makeText(CategoriaFormaPag.this, "Categoria excluída!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        recyclerCatFormaPag.setAdapter(catformapagAdapter);
    }



}