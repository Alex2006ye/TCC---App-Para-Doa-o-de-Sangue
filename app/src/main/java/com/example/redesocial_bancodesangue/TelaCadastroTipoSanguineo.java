package com.example.redesocial_bancodesangue;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.redesocial_bancodesangue.dto.UsuarioDoadorCreateDTO;
import com.example.redesocial_bancodesangue.model.TipoSanguineo;
import com.example.redesocial_bancodesangue.model.TipoUsuario;
import com.example.redesocial_bancodesangue.model.Usuario;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;
import com.example.redesocial_bancodesangue.retrofit.UsuarioApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaCadastroTipoSanguineo extends AppCompatActivity {

    private Button btnAvancarTipoSanguineo;
    private RadioButton Apositivo, Anegativo, Bpositivo, Bnegativo, ABpositivo, ABnegativo, Opositivo, Onegativo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro_tipo_sanguineo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAvancarTipoSanguineo = findViewById(R.id.btnAvancarTipoSanguineo);
        Apositivo = findViewById(R.id.Apositivo);
        Anegativo = findViewById(R.id.Anegativo);
        Bpositivo = findViewById(R.id.Bpositivo);
        Bnegativo = findViewById(R.id.Bnegativo);
        ABpositivo = findViewById(R.id.ABpositivo);
        ABnegativo = findViewById(R.id.ABnegativo);
        Opositivo = findViewById(R.id.Opositivo);
        Onegativo = findViewById(R.id.Onegativo);

        RetrofitService retrofit = new RetrofitService();

        UsuarioApi api = retrofit.getRetrofit().create(UsuarioApi.class);

        btnAvancarTipoSanguineo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario usuario = new Usuario();
                usuario.setNome(getIntent().getStringExtra("nome_usuario"));
                usuario.setTipoUsuario(TipoUsuario.valueOf(getIntent().getStringExtra("tipoUsuario_usuario")));
                usuario.setSenha(getIntent().getStringExtra("senha_usuario"));
                usuario.setPeso(getIntent().getDoubleExtra("peso_usuario", 0));
                usuario.setEmail(getIntent().getStringExtra("email_usuario"));
                usuario.setCpf(getIntent().getStringExtra("cpf_usuario"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    usuario.setDataNasc(getIntent().getStringExtra("dataNasc_usuario"));
                }

                if(!Apositivo.isChecked() && !Anegativo.isChecked() && !Bnegativo.isChecked() && !Bpositivo.isChecked() && !ABnegativo.isChecked() && !ABpositivo.isChecked() && !Onegativo.isChecked() && !Opositivo.isChecked()){
                    Toast.makeText(getApplicationContext(), "É obrigatório escolher um tipo Sanguíneo para prosseguir", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Apositivo.isChecked()){
                    usuario.setTipoSanguineo(TipoSanguineo.APositivo);
                }

                if(Anegativo.isChecked()){
                    usuario.setTipoSanguineo(TipoSanguineo.ANegativo);
                }

                if(Bpositivo.isChecked()){
                    usuario.setTipoSanguineo(TipoSanguineo.BPositivo);
                }

                if(Bnegativo.isChecked()){
                    usuario.setTipoSanguineo(TipoSanguineo.BNegativo);
                }

                if(ABpositivo.isChecked()){
                    usuario.setTipoSanguineo(TipoSanguineo.ABPositivo);
                }

                if(ABnegativo.isChecked()){
                    usuario.setTipoSanguineo(TipoSanguineo.ABNegativo);
                }

                if(Opositivo.isChecked()){
                    usuario.setTipoSanguineo(TipoSanguineo.OPositivo);
                }

                if(Onegativo.isChecked()){
                    usuario.setTipoSanguineo(TipoSanguineo.ONegativo);
                }

                UsuarioDoadorCreateDTO usuarioDoadorCreateDTO = new UsuarioDoadorCreateDTO(usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getCpf(), usuario.getPeso(), usuario.getDataNasc(), usuario.getTipoUsuario(), usuario.getTipoSanguineo());

                api.salvarUsuarioDoador(usuarioDoadorCreateDTO).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 409){
                            Toast.makeText(getApplicationContext(), "Email ou CPF já cadastrado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), TelaCadastroDoadores.class);
                            startActivity(intent);
                            finish();
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
                        throwable.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Cadastro deu erro, não conseguiu se comunicar com servidor", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}