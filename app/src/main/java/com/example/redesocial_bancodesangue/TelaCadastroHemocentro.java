package com.example.redesocial_bancodesangue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.redesocial_bancodesangue.model.Endereco;
import com.example.redesocial_bancodesangue.model.TipoUsuario;
import com.example.redesocial_bancodesangue.model.Usuario;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;
import com.example.redesocial_bancodesangue.retrofit.UsuarioApi;
import com.example.redesocial_bancodesangue.retrofit.ViaCepApi;
import com.example.redesocial_bancodesangue.retrofit.ViaCepRetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaCadastroHemocentro extends AppCompatActivity {

    private Button btnAvancarUsuarioHemocentro;
    private EditText edtNomeHemocentro, edtSenhaHemocentro, edtEmailHemocentro,
            edtCnpjHemocentro, edtCEPHemocentro, edtNumeroHemocentro;

    // Guarda o endereço retornado pelo ViaCEP
    private Endereco enderecoAtual = null;

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
        edtCnpjHemocentro           = findViewById(R.id.edtCnpjHemocentro);
        edtEmailHemocentro          = findViewById(R.id.edtEmailHemocentro);
        edtNomeHemocentro           = findViewById(R.id.edtNomeHemocentro);
        edtSenhaHemocentro          = findViewById(R.id.edtSenhaHemocentro);
        edtCEPHemocentro            = findViewById(R.id.edtCEPHemocentro);
        edtNumeroHemocentro         = findViewById(R.id.edtNumeroHemocentro); // campo de número

        // Clientes Retrofit
        RetrofitService retrofit = new RetrofitService();
        UsuarioApi api = retrofit.getRetrofit().create(UsuarioApi.class);

        ViaCepRetrofitService viaCepService = new ViaCepRetrofitService();
        ViaCepApi viaCepApi = viaCepService.getRetrofit().create(ViaCepApi.class);

        // Busca o CEP automaticamente quando 8 dígitos forem digitados
        edtCEPHemocentro.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override //verifica o campo quando for mudado
            public void afterTextChanged(Editable s) {
                String cepLimpo = s.toString().replaceAll("[^0-9]", "");

                if (cepLimpo.length() == 8) {
                    buscarCep(cepLimpo, viaCepApi);
                } else {
                    enderecoAtual = null; // limpa se o CEP for apagado
                }
            }
        });

        btnAvancarUsuarioHemocentro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome   = edtNomeHemocentro.getText().toString();
                String email  = edtEmailHemocentro.getText().toString();
                String senha  = edtSenhaHemocentro.getText().toString();
                String cnpj   = edtCnpjHemocentro.getText().toString();
                String cep    = edtCEPHemocentro.getText().toString();
                String numero = edtNumeroHemocentro.getText().toString();

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cnpj.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Digite todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cnpj.length() != 14) {
                    Toast.makeText(getApplicationContext(), "O CNPJ foi digitado de forma incorreta", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (enderecoAtual == null) {
                    Toast.makeText(getApplicationContext(), "Digite um CEP válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setSenha(senha);
                usuario.setNome(nome);
                usuario.setCnpj(cnpj);
                usuario.setTipoUsuario(TipoUsuario.UsuarioHemocentro);
                // Campos vindos do ViaCEP
                usuario.setRua(enderecoAtual.logradouro);
                usuario.setBairro(enderecoAtual.bairro);
                usuario.setNumero(Integer.parseInt(numero));
                usuario.setCep(cep);

                UsuarioHemocentroCreateDTO dto = new UsuarioHemocentroCreateDTO(
                        usuario.getNome(), usuario.getEmail(), usuario.getSenha(),
                        usuario.getCnpj(), usuario.getTipoUsuario(), enderecoAtual.logradouro,
                        enderecoAtual.bairro, usuario.getNumero(), usuario.getCep()
                        // adicione os campos de endereço no DTO se necessário
                );

                api.salvarUsuarioHemocentro(dto).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 409) {
                            Toast.makeText(getApplicationContext(), "Email ou CNPJ já cadastrado", Toast.LENGTH_SHORT).show();
                        }
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Log.e("RETROFIT_ERROR", "Mensagem: " + throwable.getMessage());
                        throwable.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Erro: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void buscarCep(String cep, ViaCepApi viaCepApi) {
        viaCepApi.buscarCep(cep).enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (Boolean.TRUE.equals(response.body().erro)) {
                        Toast.makeText(getApplicationContext(), "CEP não encontrado", Toast.LENGTH_SHORT).show();
                        enderecoAtual = null;
                        return;
                    }
                    enderecoAtual = response.body();
                    Toast.makeText(getApplicationContext(),
                            "Endereço encontrado: " + enderecoAtual.logradouro, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro ao buscar CEP", Toast.LENGTH_SHORT).show();
                Log.e("VIACEP_ERROR", t.getMessage());
            }
        });
    }
}