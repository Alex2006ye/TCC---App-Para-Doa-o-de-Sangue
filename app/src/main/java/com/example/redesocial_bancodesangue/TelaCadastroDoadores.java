package com.example.redesocial_bancodesangue;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaCadastroDoadores extends AppCompatActivity {

    private Button btnAvancarCadastroDoador;
    private EditText edtNomeDoadorCadastro, edtSenhaDoadorCadastro, edtEmailDoadorCadastro, edtIdadeDoadorCadastro, edtCpfDoadorCadastro, edtPesoDoadorCadastro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro_doadores);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAvancarCadastroDoador = findViewById(R.id.btnAvancarCadastroDoador);
        edtNomeDoadorCadastro = findViewById(R.id.edtNomeDoadorCadastro);
        edtEmailDoadorCadastro = findViewById(R.id.edtEmailDoadorCadastro);
        edtSenhaDoadorCadastro = findViewById(R.id.edtSenhaDoadorCadastro);
        edtIdadeDoadorCadastro = findViewById(R.id.edtIdadeDoadorCadastro);
        edtPesoDoadorCadastro = findViewById(R.id.edtPesoDoadorCadastro);
        edtCpfDoadorCadastro = findViewById(R.id.edtCpfDoadorCadastro);

        btnAvancarCadastroDoador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}