package com.example.app_financas;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_financas.CategoriaFormaPag.CatFormaPag;
import com.example.app_financas.CategoriaFormaPag.CatFormaPagDAO;
import com.example.app_financas.CategoriaGeral.CatGeral;
import com.example.app_financas.CategoriaGeral.CatGeralDAO;
import com.example.app_financas.CategoriaPagamento.CatPagDAO;
import com.example.app_financas.Transacao.Transacao;
import com.example.app_financas.Transacao.TransacaoAdapter;
import com.example.app_financas.Transacao.TransacaoDAO;

import java.util.ArrayList;
import java.util.List;

public class ConsultarFinanca extends AppCompatActivity {
    Spinner spnCatGeral, spnCatFormaPag;
    EditText dataInicial, dataFinal;
    RadioButton rbGanho, rbGasto;
    RadioGroup radioGroup;
    TextView somaTotal;
    RecyclerView recyclerViewValores;
    private List<Transacao> listaTransacoes = new ArrayList<>();
    private TransacaoAdapter transacaoAdapter;
    private TransacaoDAO transacaoDAO; // seu DAO para pegar as transações do banco
    private CatGeralDAO catgeralDAO;
    private CatPagDAO catpagDAO;
    private CatFormaPagDAO catformapagDAO;

    //usadas no método carregar filtro
    private int tipoSelecionadoId = -1;
    private String catGeralSelecionada = null;
    private String catFormaPagSelecionada = null;
    private String dataInicialStr = null;
    private String dataFinalStr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar_financa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ConsultarFinanca), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        spnCatGeral = findViewById(R.id.spinnerCategoriaGeral);
        spnCatFormaPag = findViewById(R.id.spinnerCategoriaFormaPag);
        dataInicial = findViewById(R.id.editTextDataInicial);
        dataFinal = findViewById(R.id.editTextDataFinal);
        rbGasto = findViewById(R.id.rbGastoConsultar);
        rbGanho = findViewById(R.id.rbGanhoConsultar);
        radioGroup = findViewById(R.id.radioGroupTipo);
        somaTotal = findViewById(R.id.somaTotal);
        recyclerViewValores = findViewById(R.id.recyclerViewValores);

        transacaoDAO = new TransacaoDAO(this);
        catgeralDAO = new CatGeralDAO(this);
        catpagDAO = new CatPagDAO(this);
        catformapagDAO = new CatFormaPagDAO(this);

        List<Transacao> lista = transacaoDAO.listar();
        for (Transacao t : lista) {
            if (t.getIdCategoriaGeral() != null) {
                String nome = catgeralDAO.buscarNomePorId(t.getIdCategoriaGeral());
                t.setNomeCategoriaGeral(nome);
            }

            if (t.getIdCategoriaPagamento() != null) {
                String nome = catpagDAO.buscarNomePorId(t.getIdCategoriaPagamento());
                t.setNomeCategoriaPagamento(nome);
            }

            if (t.getIdCategoriaFormaPagamento() != null) {
                String nome = catformapagDAO.buscarNomePorId(t.getIdCategoriaFormaPagamento());
                t.setNomeCategoriaFormaPagamento(nome);
            }
        }

        transacaoAdapter = new TransacaoAdapter(listaTransacoes, transacao -> {
        });

        recyclerViewValores.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewValores.setAdapter(transacaoAdapter);

        carregarCategoriasSpinners();
        carregarTransacoesFiltros();
        configurarFiltros();
    }
    public void telaInicio(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void telaConfigurar(View v){
        Intent i = new Intent(this, Configurar.class);
        startActivity(i);
    }
    public void carregarCategoriasSpinners() {
        catformapagDAO = new CatFormaPagDAO(this);
        List<CatFormaPag> listaCatFormaPag = catformapagDAO.listar();

        List<String> nomesCatFormaPag = new ArrayList<>();
        nomesCatFormaPag.add("--");

        for (CatFormaPag catformapag : listaCatFormaPag) {
            nomesCatFormaPag.add(catformapag.getNome_categoriaFormaPag());
        }
        ArrayAdapter<String> adapterCatFormaPag = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nomesCatFormaPag
        );
        adapterCatFormaPag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCatFormaPag.setAdapter(adapterCatFormaPag);

        //categoria geral
        catgeralDAO = new CatGeralDAO(this);
        List<CatGeral> listaCatGeral = catgeralDAO.listar();

        List<String> nomesCatGeral = new ArrayList<>();
        nomesCatGeral.add("--");

        for (CatGeral catgeral : listaCatGeral) {
            nomesCatGeral.add(catgeral.getNome_categoriaGeral());
        }
        ArrayAdapter<String> adapterCatGeral = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nomesCatGeral
        );
        adapterCatGeral.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCatGeral.setAdapter(adapterCatGeral);
    }
    private void capturarFiltros() {
        // Tipo (Gasto / Ganho)
        if (rbGasto.isChecked()) {
            tipoSelecionadoId = R.id.rbGasto;
        } else if (rbGanho.isChecked()) {
            tipoSelecionadoId = R.id.rbGanho;
        } else {
            tipoSelecionadoId = -1;
        }
        // Categoria Geral selecionada no spinner
        catGeralSelecionada = spnCatGeral.getSelectedItem() != null ?
                spnCatGeral.getSelectedItem().toString() : null;
        if ("--".equals(catGeralSelecionada)) {
            catGeralSelecionada = null; // não filtrar
        }
        // Categoria Forma Pag selecionada no spinner
        catFormaPagSelecionada = spnCatFormaPag.getSelectedItem() != null ?
                spnCatFormaPag.getSelectedItem().toString() : null;
        if ("--".equals(catFormaPagSelecionada)) {
            catFormaPagSelecionada = null; // não filtrar
        }

        // Datas
        dataInicialStr = dataInicial.getText().toString().trim();
        dataFinalStr = dataFinal.getText().toString().trim();

        if (dataInicialStr.isEmpty()) {
            dataInicialStr = null;
        }
        if (dataFinalStr.isEmpty()) {
            dataFinalStr = null;
        }
    }
    private void carregarTransacoesFiltros() {
        // Pega os filtros atuais da UI
        capturarFiltros();

        String tipo = null;
        if (tipoSelecionadoId == R.id.rbGasto) {
            tipo = "Gasto";
        } else if (tipoSelecionadoId == R.id.rbGanho) {
            tipo = "Ganho";
        }

        // Busca as transações com os filtros no DAO (que você deve implementar)
        listaTransacoes.clear();
        listaTransacoes.addAll(transacaoDAO.buscarTransacoesComFiltros(
                catGeralSelecionada,
                catFormaPagSelecionada,
                dataInicialStr,
                dataFinalStr,
                tipo));

        transacaoAdapter.notifyDataSetChanged();

        atualizarSomaTotal();
    }
    private void atualizarSomaTotal() {
        double soma = 0;
        for (Transacao t : listaTransacoes) {
            soma += t.getValor();
        }
        somaTotal.setText(String.format("Total: R$ %.2f", soma));
    }
    private void configurarFiltros() {
        // Filtro: Categoria Geral
        spnCatGeral.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean isFirstSelection = true;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isFirstSelection) {
                    carregarTransacoesFiltros();
                }
                isFirstSelection = false;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Filtro: Forma de Pagamento
        spnCatFormaPag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean isFirstSelection = true;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isFirstSelection) {
                    carregarTransacoesFiltros();
                }
                isFirstSelection = false;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            carregarTransacoesFiltros();
        });

        configurarFiltroData(dataInicial);
        configurarFiltroData(dataFinal);
    }
    private void configurarFiltroData(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                carregarTransacoesFiltros();
            }
        });
    }





}