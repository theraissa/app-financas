package com.example.app_financas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConsultarFinanca extends AppCompatActivity {
    Spinner spnCatGeral, spnCatFormaPag;
    EditText dataInicial, dataFinal;
    CheckBox cbGasto, cbGanho;
    LinearLayout checkboxGroup;
    TextView somaTotal;
    Button btnLimparFiltro;
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
    Calendar dataSelecionada = Calendar.getInstance();

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
        cbGasto = findViewById(R.id.cbGastoConsultar);
        cbGanho = findViewById(R.id.cbGanhoConsultar);
        checkboxGroup = findViewById(R.id.linearLayoutTipo);
        somaTotal = findViewById(R.id.somaTotal);
        recyclerViewValores = findViewById(R.id.recyclerViewValores);

        btnLimparFiltro = findViewById(R.id.buttonLimparFiltros);
        btnLimparFiltro.setOnClickListener(v -> limparFiltros());

        transacaoDAO = new TransacaoDAO(this);
        catgeralDAO = new CatGeralDAO(this);
        catpagDAO = new CatPagDAO(this);
        catformapagDAO = new CatFormaPagDAO(this);

        listaTransacoes = transacaoDAO.listar(); // use diretamente a lista preenchida
        transacaoAdapter = new TransacaoAdapter(listaTransacoes, new TransacaoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Transacao transacao) {
            }
            @Override
            public void onExcluirClick(Transacao transacao){
                new AlertDialog.Builder(ConsultarFinanca.this)
                        .setTitle("Excluir Transação")
                        .setMessage("Tem certeza que deseja excluir esta transação?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            transacaoDAO.excluir(transacao.getId());
                            carregarTransacoesFiltros();
                            Toast.makeText(ConsultarFinanca.this, "Transação excluída!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }

        });

        recyclerViewValores.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewValores.setAdapter(transacaoAdapter);

        carregarCategoriasSpinners();
        carregarTransacoesFiltros();

        for (Transacao t : listaTransacoes) {
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

        // Desabilitar teclado, focar só no clique
        dataInicial.setInputType(InputType.TYPE_NULL);
        dataInicial.setFocusable(false);
        dataFinal.setInputType(InputType.TYPE_NULL);
        dataFinal.setFocusable(false);

        dataInicial.setOnClickListener(v -> abrirDatePicker(dataInicial));
        dataFinal.setOnClickListener(v -> abrirDatePicker(dataFinal));

        configurarFiltros();
    }
    public void telaConsultar(View v){
        if (!this.getClass().equals(ConsultarFinanca.class)) {
            Intent i = new Intent(this, ConsultarFinanca.class);
            startActivity(i);
        }
    }
    public void telaInicio(View v){
        if (!this.getClass().equals(MainActivity.class)) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
    public void telaConfigurar(View v){
        if (!this.getClass().equals(Configurar.class)) {
            Intent i = new Intent(this, Configurar.class);
            startActivity(i);
        }
    }

    private void abrirDatePicker(EditText editText) {
        Calendar calendar = Calendar.getInstance();

        // Se o campo já tem uma data, tenta usar ela como data inicial do DatePicker
        String dataStr = editText.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        try {
            if (!dataStr.isEmpty()) {
                Date data = sdf.parse(dataStr);
                calendar.setTime(data);
            }
        } catch (ParseException e) {
            // se erro, mantém a data atual
        }

        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    editText.setText(sdf.format(calendar.getTime()));
                }, ano, mes, dia);

        datePickerDialog.show();
    }

    private boolean validarDatas() {
        String dataIniStr = dataInicial.getText().toString().trim();
        String dataFimStr = dataFinal.getText().toString().trim();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        sdf.setLenient(false); // Para evitar datas inválidas tipo 32/01/2025

        Date dataIni = null;
        Date dataFim = null;

        // Se um campo estiver vazio, consideramos válido (filtro aberto)
        if (dataIniStr.isEmpty() && dataFimStr.isEmpty()) {
            return true;
        }

        try {
            if (!dataIniStr.isEmpty()) {
                dataIni = sdf.parse(dataIniStr);
            }
            if (!dataFimStr.isEmpty()) {
                dataFim = sdf.parse(dataFimStr);
            }
        } catch (ParseException e) {
            Toast.makeText(this, "Formato de data inválido. Use dd/MM/yyyy.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Se só uma data foi preenchida, ok
        if (dataIni == null || dataFim == null) {
            return true;
        }

        // Ambas preenchidas: dataInicial deve ser <= dataFinal
        if (dataIni.after(dataFim)) {
            Toast.makeText(this, "A data inicial não pode ser maior que a data final.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
        // Atualiza categorias e datas
        catGeralSelecionada = spnCatGeral.getSelectedItem() != null ?
                spnCatGeral.getSelectedItem().toString() : null;
        if ("--".equals(catGeralSelecionada)) catGeralSelecionada = null;

        catFormaPagSelecionada = spnCatFormaPag.getSelectedItem() != null ?
                spnCatFormaPag.getSelectedItem().toString() : null;
        if ("--".equals(catFormaPagSelecionada)) catFormaPagSelecionada = null;

        dataInicialStr = dataInicial.getText().toString().trim();
        dataFinalStr = dataFinal.getText().toString().trim();
        if (dataInicialStr.isEmpty()) dataInicialStr = null;
        if (dataFinalStr.isEmpty()) dataFinalStr = null;
    }
    // retorna lista com tipos selecionados
    private List<String> tiposSelecionados() {
        List<String> tipos = new ArrayList<>();
        if (cbGasto.isChecked()) tipos.add("Gasto");
        if (cbGanho.isChecked()) tipos.add("Ganho");
        return tipos;
    }
    private void carregarTransacoesFiltros() {
        // Não continua se datas inválidas
        if (!validarDatas()) {return;}

        // Pega os filtros atuais da UI
        capturarFiltros();

        // Busca as transações com os filtros no DAO (que você deve implementar)
        List<String> tipos = tiposSelecionados();

        listaTransacoes.clear();
        listaTransacoes.addAll(transacaoDAO.buscarTransacoesComFiltros(
                catGeralSelecionada,
                catFormaPagSelecionada,
                dataInicialStr,
                dataFinalStr,
                tipos));

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
        spnCatGeral.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean isFirstSelection = true;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isFirstSelection) carregarTransacoesFiltros();
                isFirstSelection = false;
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        spnCatFormaPag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean isFirstSelection = true;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isFirstSelection) carregarTransacoesFiltros();
                isFirstSelection = false;
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        cbGasto.setOnCheckedChangeListener((buttonView, isChecked) -> carregarTransacoesFiltros());
        cbGanho.setOnCheckedChangeListener((buttonView, isChecked) -> carregarTransacoesFiltros());

        configurarFiltroData(dataInicial);
        configurarFiltroData(dataFinal);
    }

    private void configurarFiltroData(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                carregarTransacoesFiltros();
            }
        });
    }

    private void limparFiltros() {
        dataInicial.setText("");
        dataFinal.setText("");
        spnCatGeral.setSelection(0);
        spnCatFormaPag.setSelection(0);
        cbGasto.setChecked(false);
        cbGanho.setChecked(false);

        // Limpa os filtros armazenados
        catGeralSelecionada = null;
        catFormaPagSelecionada = null;
        dataInicialStr = null;
        dataFinalStr = null;

        // Atualiza a lista com todas as transações
        listaTransacoes.clear();
        listaTransacoes.addAll(transacaoDAO.listar());
        transacaoAdapter.notifyDataSetChanged();

        atualizarSomaTotal();
    }
}