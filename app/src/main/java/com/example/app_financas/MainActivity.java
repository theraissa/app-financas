package com.example.app_financas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_financas.Perfil.CadastroPerfil;
import com.example.app_financas.Perfil.Perfil;
import com.example.app_financas.Perfil.PerfilDAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText data;
    TextView olaPerfil;
    private PerfilDAO perfilDAO;
    private Perfil perfil;
    Calendar dataInformada = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        data = findViewById(R.id.editTextData);
        olaPerfil = findViewById(R.id.txtOlaUsuario);
        perfilDAO = new PerfilDAO(this);

        CarregarPerfil();
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirJanela();
            }
        });

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
        data.setText(sdf.format(dataInformada.getTime()));
    }
    public void telaConsultar(View v) {
        Intent i = new Intent(this, ConsultarFinanca.class);
        startActivity(i);
    }

}