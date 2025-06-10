package com.example.app_financas.CategoriaGeral;

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
    private void carregarCategoriasGeral(){
        List<CatGeral> catgerals = catgeralDAO.listar();
        catgeralAdapter = new CatGeralAdapter(catgerals, catgeral -> {
            Toast.makeText(this, "Selecionado: " + catgeral.getNome_categoriaGeral(), Toast.LENGTH_SHORT).show();
        });
        recyclerCatGeral.setAdapter(catgeralAdapter);

    }

}