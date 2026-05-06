package com.example.redesocial_bancodesangue;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.example.redesocial_bancodesangue.dto.UsuarioHemocentroCreateDTO;
import com.example.redesocial_bancodesangue.model.TipoUsuario;
import com.example.redesocial_bancodesangue.model.Usuario;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;
import com.example.redesocial_bancodesangue.retrofit.UsuarioApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaCadastroHemocentro extends AppCompatActivity {

    private Button btnAvancarUsuarioHemocentro;
    private EditText edtNomeHemocentro, edtSenhaHemocentro, edtEmailHemocentro, edtCnpjHemocentro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro_hemocentro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAvancarUsuarioHemocentro = findViewById(R.id.btnAvancarCadastroHemocentro);
        edtCnpjHemocentro = findViewById(R.id.edtCnpjHemocentro);
        edtEmailHemocentro = findViewById(R.id.edtEmailHemocentro);
        edtNomeHemocentro = findViewById(R.id.edtNomeHemocentro);
        edtSenhaHemocentro = findViewById(R.id.edtSenhaHemocentro);

        RetrofitService retrofit = new RetrofitService();

        UsuarioApi api = retrofit.getRetrofit().create(UsuarioApi.class);

        btnAvancarUsuarioHemocentro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = edtNomeHemocentro.getText().toString();
                String email = edtEmailHemocentro.getText().toString();
                String senha = edtSenhaHemocentro.getText().toString();
                String cnpj = edtCnpjHemocentro.getText().toString();

                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cnpj.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Digite todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(cnpj.length() > 14 || cnpj.length() < 14){
                    Toast.makeText(getApplicationContext(), "O CNPJ foi digitado de forma incorreta", Toast.LENGTH_SHORT).show();
                    return;
                }

                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setSenha(senha);
                usuario.setNome(nome);
                usuario.setCnpj(cnpj);
                usuario.setTipoUsuario(TipoUsuario.UsuarioHemocentro);

                UsuarioHemocentroCreateDTO usuarioHemocentroCreateDTO = new UsuarioHemocentroCreateDTO(usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getCnpj(), usuario.getTipoUsuario());

                api.salvarUsuarioHemocentro(usuarioHemocentroCreateDTO).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 409){
                            Toast.makeText(getApplicationContext(), "Email ou CNPJ já cadastrado", Toast.LENGTH_SHORT).show();
                        }
                        if(response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "Cadastro deu erro", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}