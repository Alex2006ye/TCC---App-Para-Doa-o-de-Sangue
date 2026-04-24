package com.example.redesocial_bancodesangue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaCadastro extends AppCompatActivity {

    private Button btnAvancarTelaCadastro;
    private TextView txtLinkLogin;
    private RadioButton radioUsuarioDoador, radioUsuarioHemocentro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAvancarTelaCadastro = findViewById(R.id.btnAvancarTipoUsuario);
        txtLinkLogin = findViewById(R.id.txtLinkLogin);
        radioUsuarioDoador = findViewById(R.id.radioUsuarioDoador);
        radioUsuarioHemocentro = findViewById(R.id.radioUsuarioHemocentro);

        txtLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnAvancarTelaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!radioUsuarioDoador.isChecked() && !radioUsuarioHemocentro.isChecked()){
                    Toast.makeText(getApplicationContext(), "Deve-se marcar pelo menos um dos dois para continuar", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(radioUsuarioDoador.isChecked()){
                    Intent intent = new Intent(getApplicationContext(), TelaCadastroDoadores.class);
                    startActivity(intent);
                    finish();
                }

                if(radioUsuarioHemocentro.isChecked()){
                    Intent intent = new Intent(getApplicationContext(), TelaCadastroHemocentro.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}