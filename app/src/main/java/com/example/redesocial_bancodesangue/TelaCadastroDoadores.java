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

import com.example.redesocial_bancodesangue.model.TipoUsuario;
import com.example.redesocial_bancodesangue.model.Usuario;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

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

        Calendar calendar = Calendar.getInstance();

        // Define o listener para o DatePickerDialog
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
                edtIdadeDoadorCadastro.setText(dataEscolhida.format(formatter));
            }
            // Atualiza o objeto do seu modelo (ex: usuario.setDataNasc(dataEscolhida))
        };

// Define o clique no EditText para abrir o calendário
        edtIdadeDoadorCadastro.setOnClickListener(v -> {
            new DatePickerDialog(
                    this,
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        btnAvancarCadastroDoador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = edtNomeDoadorCadastro.getText().toString();
                String email = edtEmailDoadorCadastro.getText().toString();
                String senha = edtSenhaDoadorCadastro.getText().toString();
                String idade = edtIdadeDoadorCadastro.getText().toString();
                String cpf = edtCpfDoadorCadastro.getText().toString();
                String peso = edtPesoDoadorCadastro.getText().toString();

                Usuario usuario = new Usuario();

                // verifica se os campos estão preenchidos ou não
                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty() || idade.isEmpty() || cpf.isEmpty() || peso.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Digite todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                //basicamente, ele verifica se a pessoa está apta para fazer a doação de sangue, verificando o peso e a idade
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate dataNascimento = LocalDate.parse(idade, formatter);

                    int anos = Period.between(dataNascimento, LocalDate.now()).getYears();

                    if (Double.parseDouble(peso) < 50 || anos < 18 || anos > 69) {
                        Toast.makeText(getApplicationContext(), "Você não está apto para doar sangue", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    usuario.setDataNasc(String.valueOf(dataNascimento));
                }
                usuario.setCpf(cpf);
                usuario.setEmail(email);
                usuario.setNome(nome);
                usuario.setPeso(Double.parseDouble(peso));
                usuario.setSenha(senha);
                usuario.setTipoUsuario(TipoUsuario.UsuarioDoador);

                Intent intent = new Intent(getApplicationContext(), TelaCadastroTipoSanguineo.class);
                intent.putExtra("nome_usuario", usuario.getNome());
                intent.putExtra("senha_usuario", usuario.getSenha());
                intent.putExtra("email_usuario", usuario.getEmail());
                intent.putExtra("cpf_usuario", usuario.getCpf());
                intent.putExtra("peso_usuario", usuario.getPeso());
                intent.putExtra("dataNasc_usuario", usuario.getDataNasc());
                intent.putExtra("tipoUsuario_usuario", usuario.getTipoUsuario().name());

                startActivity(intent);
                finish();
            }
            });
        }
    }