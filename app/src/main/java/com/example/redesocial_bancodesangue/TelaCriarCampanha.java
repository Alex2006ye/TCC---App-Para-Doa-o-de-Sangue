package com.example.redesocial_bancodesangue;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.redesocial_bancodesangue.model.Campanha;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TelaCriarCampanha extends AppCompatActivity {

    private EditText txtNomeCampanha, txtDataInicio, txtDataFim;
    private Button btnAvancarCampanha;
    private int campoAtual = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_criar_campanha);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtNomeCampanha = findViewById(R.id.edtNomeCampanha);
        txtDataFim = findViewById(R.id.edtDataFim);
        txtDataInicio = findViewById(R.id.edtDataInicio);
        btnAvancarCampanha = findViewById(R.id.btnAvancarCampanha);

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            // Cria um LocalDate com os valores escolhidos
            LocalDate dataEscolhida = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dataEscolhida = LocalDate.of(year, month + 1, dayOfMonth);
            }
            // Formata e exibe a data no EditText
            DateTimeFormatter formatter = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(campoAtual == 1)
                    txtDataInicio.setText(dataEscolhida.format(formatter));
                if(campoAtual == 2)
                    txtDataFim.setText(dataEscolhida.format(formatter));
            }
            // Atualiza o objeto do seu modelo (ex: usuario.setDataNasc(dataEscolhida))
        };

        txtDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campoAtual = 1;
                new DatePickerDialog(
                        TelaCriarCampanha.this,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        txtDataFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campoAtual = 2;
                new DatePickerDialog(
                        TelaCriarCampanha.this,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        btnAvancarCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer idHemocentro = getIntent().getIntExtra("idHemocentro", -1);
                String nome = txtNomeCampanha.getText().toString();
                String dataFim = txtDataFim.getText().toString();
                String dataInicio = txtDataInicio.getText().toString();

                if(nome.isEmpty() || dataFim.isEmpty() || dataInicio.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Digite todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if(LocalDate.parse(dataFim).isBefore(LocalDate.parse(dataInicio))){
                        Toast.makeText(getApplicationContext(), "Digite uma data de término válida", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(LocalDate.parse(dataInicio).isBefore(LocalDate.now())){
                        Toast.makeText(getApplicationContext(), "Digite uma data de inicio válida", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Campanha campanha = new Campanha();
                campanha.setNomeCampanha(nome);
                campanha.setDataFim(dataFim);
                campanha.setDataInicio(dataInicio);
                campanha.setIdUsuarioHemocentro(idHemocentro);

                Intent intent = new Intent(getApplicationContext(), TelaCriarCampanhaTipoSanguineo.class);
                intent.putExtra("nomeCampanha", campanha.getNomeCampanha());
                intent.putExtra("dataFim", campanha.getDataFim());
                intent.putExtra("dataInicio", campanha.getDataInicio());
                intent.putExtra("idHemocentro", campanha.getIdUsuarioHemocentro());
                startActivity(intent);
                finish();
            }
        });
    }
}