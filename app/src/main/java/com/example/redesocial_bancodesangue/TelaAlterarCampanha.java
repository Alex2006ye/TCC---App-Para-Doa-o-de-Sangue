package com.example.redesocial_bancodesangue;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.redesocial_bancodesangue.model.Campanha;
import com.example.redesocial_bancodesangue.model.TipoSanguineo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TelaAlterarCampanha extends AppCompatActivity {

    private EditText edtNomeCampanhaAlterar, edtDataInicioAlterar, edtDataFimAlterar;
    private Button btnAlterarCampanha;
    private int campoAtual = 0;
    private ImageButton btnVoltar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_alterar_campanha);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtDataFimAlterar = findViewById(R.id.edtDataFimAlterar);
        edtDataInicioAlterar = findViewById(R.id.edtDataInicioAlterar);
        edtNomeCampanhaAlterar = findViewById(R.id.edtNomeCampanhaAlterar);
        btnAlterarCampanha = findViewById(R.id.btnAlterarCampanha);
        btnVoltar = findViewById(R.id.btnVoltarTelaAlterarCampanha);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TelaInicialHemocentros.class);
                startActivity(intent);
                finish();
            }
        });

        edtDataFimAlterar.setText(getIntent().getStringExtra("dataFim"));
        edtDataInicioAlterar.setText(getIntent().getStringExtra("dataInicio"));
        edtNomeCampanhaAlterar.setText(getIntent().getStringExtra("nomeCampanha"));

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
                    edtDataInicioAlterar.setText(dataEscolhida.format(formatter));
                if(campoAtual == 2)
                    edtDataFimAlterar.setText(dataEscolhida.format(formatter));
            }
            // Atualiza o objeto do seu modelo (ex: usuario.setDataNasc(dataEscolhida))
        };

        edtDataInicioAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campoAtual = 1;
                new DatePickerDialog(
                        TelaAlterarCampanha.this,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        edtDataFimAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campoAtual = 2;
                new DatePickerDialog(
                        TelaAlterarCampanha.this,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        btnAlterarCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeCampanha = edtNomeCampanhaAlterar.getText().toString();
                String dataInicio = edtDataInicioAlterar.getText().toString();
                String dataFim = edtDataFimAlterar.getText().toString();

                if(nomeCampanha.isEmpty() || dataInicio.isEmpty() || dataFim.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Campos vazios", Toast.LENGTH_SHORT).show();
                    return;
                }

                Campanha campanha = new Campanha();
                campanha.setIdCampanha(getIntent().getIntExtra("idCampanha", -1));
                campanha.setNomeCampanha(nomeCampanha);
                campanha.setDataFim(dataFim);
                campanha.setDataInicio(dataInicio);
                campanha.setTipoSanguineoVisado(TipoSanguineo.valueOf(getIntent().getStringExtra("tipoSanguineo")));

                Intent intent = new Intent(getApplicationContext(), TelaTipoSanguineoAlterar.class);
                intent.putExtra("nomeCampanha", campanha.getNomeCampanha());
                intent.putExtra("idCampanha", campanha.getIdCampanha());
                intent.putExtra("dataInicio", campanha.getDataInicio());
                intent.putExtra("dataFim", campanha.getDataFim());
                intent.putExtra("tipoSanguineo", campanha.getTipoSanguineoVisado().name());
                startActivity(intent);
                finish();
            }
        });
    }
}