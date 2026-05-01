package com.example.redesocial_bancodesangue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.redesocial_bancodesangue.dto.LoginDTO;
import com.example.redesocial_bancodesangue.model.TipoUsuario;
import com.example.redesocial_bancodesangue.model.Usuario;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;
import com.example.redesocial_bancodesangue.retrofit.UsuarioApi;

import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView linkCadastro;
    private EditText edtEmail, edtSenhaLogin;
    private Button btnEntrarLogin;

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

        linkCadastro = findViewById(R.id.txtLinkCadastro);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenhaLogin = findViewById(R.id.edtSenhaLogin);
        btnEntrarLogin = findViewById(R.id.btnEntrarLogin);

        RetrofitService retrofit = new RetrofitService();

        UsuarioApi api = retrofit.getRetrofit().create(UsuarioApi.class);

        linkCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TelaCadastro.class);
                startActivity(intent);
                finish();
            }
        });

        btnEntrarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtSenha = edtSenhaLogin.getText().toString();
                String txtEmail = edtEmail.getText().toString();

                if(txtSenha.equals("") || txtEmail.equals("")) {
                    Toast.makeText(MainActivity.this, "Digite todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginDTO dto = new LoginDTO();
                dto.setEmail(txtEmail);
                dto.setSenha(txtSenha);

                api.consultar(dto).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Usuario usuario = new Usuario();
                            usuario.setId(response.body().getId());
                            usuario.setTipoUsuario(response.body().getTipoUsuario());

                            Toast.makeText(getApplicationContext(), "Login realizado com sucesso", Toast.LENGTH_SHORT).show();
                            if(usuario.getTipoUsuario().equals(TipoUsuario.UsuarioDoador)){
                                Intent intent = new Intent(getApplicationContext(), TelaInicial.class);
                                intent.putExtra("idUsuario", usuario.getId());
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Intent intent = new Intent(getApplicationContext(), TelaInicialHemocentros.class);
                                intent.putExtra("idUsuario", usuario.getId());
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Login fracassou", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "Login Deu Erro", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}