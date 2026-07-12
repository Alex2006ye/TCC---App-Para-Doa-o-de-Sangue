package com.example.redesocial_bancodesangue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.redesocial_bancodesangue.dto.CampanhaCreateDTO;
import com.example.redesocial_bancodesangue.model.Campanha;
import com.example.redesocial_bancodesangue.model.TipoSanguineo;
import com.example.redesocial_bancodesangue.retrofit.CampanhaApi;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;
import com.example.redesocial_bancodesangue.retrofit.UsuarioApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaCriarCampanhaTipoSanguineo extends AppCompatActivity {

    private Button btnCriacaoCampanha;
    private RadioButton radioApositivo, radioAnegativo, radioBpositivo, radioBnegativo, radioABpositivo,
    radioABnegativo, radioOpositivo, radioOnegativo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_criar_campanha_tipo_sanguineo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCriacaoCampanha = findViewById(R.id.btnFinalizarCriacaoCampanha);
        radioApositivo = findViewById(R.id.radioApositivo);
        radioAnegativo = findViewById(R.id.radioAnegativo);
        radioBpositivo = findViewById(R.id.radioBpositivo);
        radioBnegativo = findViewById(R.id.radioBnegativo);
        radioABpositivo = findViewById(R.id.radioABpositivo);
        radioABnegativo = findViewById(R.id.radioABnegativo);
        radioOpositivo = findViewById(R.id.radioOpositivo);
        radioOnegativo = findViewById(R.id.radioOnegativo);

        RetrofitService retrofit = new RetrofitService();

        CampanhaApi api = retrofit.getRetrofit().create(CampanhaApi.class);

        btnCriacaoCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Campanha campanha = new Campanha();
                campanha.setNomeCampanha(getIntent().getStringExtra("nomeCampanha"));
                campanha.setDataInicio(getIntent().getStringExtra("dataInicio"));
                campanha.setDataFim(getIntent().getStringExtra("dataFim"));
                campanha.setIdUsuarioHemocentro(getIntent().getIntExtra("idHemocentro", -1));

                if(!radioApositivo.isChecked() && !radioAnegativo.isChecked() && !radioBpositivo.isChecked() && !radioBnegativo.isChecked() && !radioABpositivo.isChecked() && !radioABnegativo.isChecked() && !radioOpositivo.isChecked() && !radioOnegativo.isChecked()){
                    Toast.makeText(getApplicationContext(), "É obrigatório selecionar um tipo sanguíneo como foco principal da campanha", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(radioApositivo.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.APositivo);
                if(radioAnegativo.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.ANegativo);
                if(radioBpositivo.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.BPositivo);
                if(radioBnegativo.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.BNegativo);
                if(radioABpositivo.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.ABPositivo);
                if(radioABnegativo.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.ABNegativo);
                if(radioOpositivo.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.OPositivo);
                if(radioOnegativo.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.ONegativo);

                CampanhaCreateDTO dto = new CampanhaCreateDTO(campanha.getNomeCampanha(), campanha.getDataInicio(),
                        campanha.getDataFim(), campanha.getTipoSanguineoVisado(),
                        campanha.getIdUsuarioHemocentro());

                api.criarCampanha(dto).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Campanha criada com sucesso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), TelaInicialHemocentros.class);
                            intent.putExtra("idUsuario", campanha.getIdUsuarioHemocentro());
                            startActivity(intent);
                            finish();
                        } else{
                            try {
                                if (response.errorBody() != null) {
                                    Log.e("ERRO", response.errorBody().string());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "Erro no salvamento da campanha " + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.e("RETROFIT", "Erro", throwable);

                        Toast.makeText(getApplicationContext(),
                                throwable.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}