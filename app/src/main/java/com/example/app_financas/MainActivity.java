package com.example.app_financas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_financas.CategoriaFormaPag.CatFormaPag;
import com.example.app_financas.CategoriaFormaPag.CatFormaPagDAO;
import com.example.app_financas.CategoriaGeral.CatGeral;
import com.example.app_financas.CategoriaGeral.CatGeralDAO;
import com.example.app_financas.CategoriaPagamento.CatPag;
import com.example.app_financas.CategoriaPagamento.CatPagDAO;
import com.example.app_financas.Perfil.CadastroPerfil;
import com.example.app_financas.Perfil.Perfil;
import com.example.app_financas.Perfil.PerfilDAO;
import com.example.app_financas.Transacao.Transacao;
import com.example.app_financas.Transacao.TransacaoDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView olaPerfil;
    EditText dataTransacao, valorTransacao, descTransacao;
    Button btnConfirmar;
    RadioButton rbGanho, rbGasto;
    Spinner spnCatGeral, spnCatPag, spnCatFormaPag;
    private PerfilDAO perfilDAO;
    private TransacaoDAO transacaoDAO;
    private Perfil perfil;
    private CatGeralDAO catgeralDAO;
    private CatGeral catgeral;
    private CatPagDAO catpagDAO;
    private CatFormaPagDAO catformapagDAO;

    Calendar dataInformada = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        valorTransacao = findViewById(R.id.editTextValor);
        descTransacao = findViewById(R.id.editTextDescricao);
        dataTransacao = findViewById(R.id.editTextData);
        rbGanho = findViewById(R.id.rbGanho);
        rbGasto = findViewById(R.id.rbGasto);
        spnCatGeral = findViewById(R.id.spinnerCategoriaGeral);
        spnCatPag = findViewById(R.id.spinnerCategoriaPeriodoPag);
        spnCatFormaPag = findViewById(R.id.spinnerCategoriaFormaPag);
        btnConfirmar = findViewById(R.id.btnConfirmarTransacao);
        olaPerfil = findViewById(R.id.txtOlaUsuario);

        perfilDAO = new PerfilDAO(this);
        transacaoDAO = new TransacaoDAO(this);

        dataTransacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirJanela();
            }
        });
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarTransacao();
            }
        });

        CarregarPerfil();
        spinnerCategoriaGeral();
        spinnerCategoriaPagamento();
        spinnerCategoriaFormaPag();
        updateLabel(); // Preenche a data com a data atual
        dataTransacao.setFocusable(false);
        dataTransacao.setClickable(true);// impedir o usuário de digitar manualmente a data (e forçar o uso do calendário)
    }

    public void CarregarPerfil(){
        perfil = perfilDAO.selecionar();
        //olaPerfil.setText("Olá, " + perfil.getNome());

        if (perfil == null){
            Intent intent = new Intent(this, CadastroPerfil.class);
            startActivity(intent);
        } else {
            olaPerfil.setText("Olá, " + perfil.getNome());
        }
    }
    private void abrirJanela(){
        //capturar informações do usuário
        int ano = dataInformada.get(Calendar.YEAR);
        int mes = dataInformada.get(Calendar.MONTH);
        int dia = dataInformada.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override   //salvando as informações fornecida pelo usuario na dataInformada
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dataInformada.set(Calendar.YEAR, year);
                dataInformada.set(Calendar.MONTH, month);
                dataInformada.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        }, ano, mes, dia);
        datePickerDialog.show();
    }
    private void updateLabel(){
        String myformat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myformat, new Locale("pt", "BR"));
        dataTransacao.setText(sdf.format(dataInformada.getTime()));
    }
    public void telaConsultar(View v) {
        Intent i = new Intent(this, ConsultarFinanca.class);
        startActivity(i);
    }

    //SPINNER CATEGORIA GERAL
    public void spinnerCategoriaGeral() {
        catgeralDAO = new CatGeralDAO(this); // instanciar o DAO
        List<CatGeral> listaCatGeral = catgeralDAO.listar();

        List<String> nomesCategorias = new ArrayList<>();
        nomesCategorias.add("--");

        for (CatGeral catgeral : listaCatGeral) {
            nomesCategorias.add(catgeral.getNome_categoriaGeral());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nomesCategorias
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCatGeral.setAdapter(adapter);
    }
    //SPINNER CATEGORIA PAGAMENTO
    public void spinnerCategoriaPagamento() {
        catpagDAO = new CatPagDAO(this);
        List<CatPag> listaCatPag = catpagDAO.listar();

        List<String> nomesCategorias = new ArrayList<>();
        nomesCategorias.add("--");

        for (CatPag catpag : listaCatPag) {
            nomesCategorias.add(catpag.getNome_categoriaPag());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nomesCategorias
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCatPag.setAdapter(adapter);
    }
    //SPINNER CATEGORIA FORMA DE PAGAMENTO
    public void spinnerCategoriaFormaPag() {
        catformapagDAO = new CatFormaPagDAO(this);
        List<CatFormaPag> listaCatFormaPag = catformapagDAO.listar();

        List<String> nomesCategorias = new ArrayList<>();
        nomesCategorias.add("--");

        for (CatFormaPag catformapag : listaCatFormaPag) {
            nomesCategorias.add(catformapag.getNome_categoriaFormaPag());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nomesCategorias
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCatFormaPag.setAdapter(adapter);
    }

    public void confirmarTransacao() {
        String valorStr = valorTransacao.getText().toString();
        String descricao = descTransacao.getText().toString();
        String data = dataTransacao.getText().toString();
        String tipo = rbGasto.isChecked() ? "Gasto" : rbGanho.isChecked() ? "Ganho" : "";

        String nomeCatGeral = spnCatGeral.getSelectedItem().toString();
        String nomeCatPag = spnCatPag.getSelectedItem().toString();
        String nomeCatForma = spnCatFormaPag.getSelectedItem().toString();

        // Exemplo de validação simples
        if (valorStr.isEmpty() || descricao.isEmpty() || data.isEmpty() || tipo.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor;
        //Se o usuário digitar um valor inválido (ex: letras), vai dar exceção.
        try {
            valor = Double.parseDouble(valorStr.replace(",", "."));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Digite um valor numérico válido!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pegando IDs a partir dos nomes
        Integer idCatGeral = nomeCatGeral.equals("--") ? null : catgeralDAO.buscarIdPorNome(nomeCatGeral);
        Integer idCatPag = nomeCatPag.equals("--") ? null : catpagDAO.buscarIdPorNome(nomeCatPag);
        Integer idCatForma = nomeCatForma.equals("--") ? null : catformapagDAO.buscarIdPorNome(nomeCatForma);

        // Logs para depuração
        Log.d("TRANSACAO_DEBUG", "Valor: " + valor);
        Log.d("TRANSACAO_DEBUG", "Descricao: " + descricao);
        Log.d("TRANSACAO_DEBUG", "Data: " + data);
        Log.d("TRANSACAO_DEBUG", "Tipo: " + tipo);

        Log.d("TRANSACAO_DEBUG", "Categoria Geral - Nome: " + nomeCatGeral + ", ID: " + idCatGeral);
        Log.d("TRANSACAO_DEBUG", "Categoria Pagamento - Nome: " + nomeCatPag + ", ID: " + idCatPag);
        Log.d("TRANSACAO_DEBUG", "Categoria Forma Pagamento - Nome: " + nomeCatForma + ", ID: " + idCatForma);


        // Criando objeto da transação (exemplo)
        Transacao novaTransacao = new Transacao();
        novaTransacao.setValor(valor);
        novaTransacao.setDescricao(descricao);
        novaTransacao.setData(data);
        novaTransacao.setTipo(tipo);
        novaTransacao.setIdCategoriaGeral(idCatGeral);
        novaTransacao.setIdCategoriaPagamento(idCatPag);
        novaTransacao.setIdCategoriaFormaPagamento(idCatForma);

        // Inserindo no banco
        long resultado = transacaoDAO.inserir(novaTransacao); // método do seu DAO

        if (resultado > 0 ) {
            Toast.makeText(this, "Transação salva com sucesso!", Toast.LENGTH_SHORT).show();

            // Limpa os campos
            valorTransacao.setText("");
            descTransacao.setText("");
            dataTransacao.setText("");
            rbGasto.setChecked(false);
            rbGanho.setChecked(false);
            spnCatGeral.setSelection(0);
            spnCatPag.setSelection(0);
            spnCatFormaPag.setSelection(0);

        } else {
            Toast.makeText(this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
        }

        Log.d("CONFIRMA_IDs", "ID Geral: " + idCatGeral + ", ID Pag: " + idCatPag + ", ID Forma: " + idCatForma);

    }

}