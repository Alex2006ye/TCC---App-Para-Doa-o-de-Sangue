package com.example.redesocial_bancodesangue;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.example.redesocial_bancodesangue.dto.UpdateTokenDTO;
import com.example.redesocial_bancodesangue.model.TipoUsuario;
import com.example.redesocial_bancodesangue.model.Usuario;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;
import com.example.redesocial_bancodesangue.retrofit.UsuarioApi;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView linkCadastro;
    private EditText edtEmail, edtSenhaLogin;
    private Button btnEntrarLogin;

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Essa parte do Firebase estou testando o FCM que é um serviço de mensageria do Firebase

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {

                    if (!task.isSuccessful()) {
                        Log.e("FCM", "Erro ao obter o token", task.getException());
                        return;
                    }

                    token = task.getResult();

                    Log.d("FCM", "Token: " + token);
                });

        criarCanalDeNotificacao();

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

                if (txtSenha.equals("") || txtEmail.equals("")) {
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

                            UpdateTokenDTO dto1 = new UpdateTokenDTO();
                            dto1.setIdUsuario(usuario.getId());
                            dto1.setToken(token);

                            api.atualizarTokenFcm(dto1).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {

                                    Toast.makeText(getApplicationContext(),
                                            "Login realizado com sucesso",
                                            Toast.LENGTH_SHORT).show();

                                    if (usuario.getTipoUsuario().equals(TipoUsuario.UsuarioDoador)) {
                                        Intent intent = new Intent(getApplicationContext(), TelaInicial.class);
                                        intent.putExtra("idUsuario", usuario.getId());
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(getApplicationContext(), TelaInicialHemocentros.class);
                                        intent.putExtra("idUsuario", usuario.getId());
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable throwable) {
                                    Toast.makeText(getApplicationContext(),
                                            "Login Deu Erro, Não se conectou ao banco de dados ou problema no Token"
                                                    + throwable.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Login fracassou",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable throwable) {
                        Toast.makeText(getApplicationContext(),
                                "Login Deu Erro, Não se conectou ao banco de dados "
                                        + throwable.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Método para criação do canal de notificações do app
    private void criarCanalDeNotificacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel("canal",
                    "Notificação no App",
                    NotificationManager.IMPORTANCE_DEFAULT);

            canal.setDescription("Canal de Notificações");
            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(canal);
        }
    }
}