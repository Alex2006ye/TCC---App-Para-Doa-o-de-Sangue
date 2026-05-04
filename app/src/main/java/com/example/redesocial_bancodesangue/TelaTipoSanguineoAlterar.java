package com.example.redesocial_bancodesangue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.redesocial_bancodesangue.dto.CampanhaUpdateDTO;
import com.example.redesocial_bancodesangue.model.Campanha;
import com.example.redesocial_bancodesangue.model.TipoSanguineo;
import com.example.redesocial_bancodesangue.retrofit.CampanhaApi;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaTipoSanguineoAlterar extends AppCompatActivity {

    private Button btnAlteracaoCampanha;
    private RadioButton radioApositivoAlterar, radioAnegativoAlterar, radioBpositivoAlterar, radioBnegativoAlterar, radioABpositivoAlterar,
            radioABnegativoAlterar, radioOpositivoAlterar, radioOnegativoAlterar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_tipo_sanguineo_alterar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAlteracaoCampanha = findViewById(R.id.btnAlteracaoCampanha);
        radioApositivoAlterar = findViewById(R.id.radioApositivoAlterar);
        radioAnegativoAlterar = findViewById(R.id.radioAnegativoAlterar);
        radioBpositivoAlterar = findViewById(R.id.radioBpositivoAlterar);
        radioBnegativoAlterar = findViewById(R.id.radioBnegativoAlterar);
        radioABpositivoAlterar = findViewById(R.id.radioABpositivoAlterar);
        radioABnegativoAlterar = findViewById(R.id.radioABnegativoAlterar);
        radioOpositivoAlterar = findViewById(R.id.radioOpositivoAlterar);
        radioOnegativoAlterar = findViewById(R.id.radioOnegativoAlterar);

        TipoSanguineo tipinho = TipoSanguineo.valueOf(getIntent().getStringExtra("tipoSanguineo"));

        if(tipinho == TipoSanguineo.ABNegativo)
            radioABnegativoAlterar.isChecked();
        if(tipinho == TipoSanguineo.ANegativo)
            radioAnegativoAlterar.isChecked();
        if(tipinho == TipoSanguineo.ABPositivo)
            radioABpositivoAlterar.isChecked();
        if(tipinho == TipoSanguineo.APositivo)
            radioApositivoAlterar.isChecked();
        if(tipinho == TipoSanguineo.BNegativo)
            radioBnegativoAlterar.isChecked();
        if(tipinho == TipoSanguineo.BPositivo)
            radioBpositivoAlterar.isChecked();
        if(tipinho == TipoSanguineo.ONegativo)
            radioOnegativoAlterar.isChecked();
        if(tipinho == TipoSanguineo.OPositivo)
            radioOpositivoAlterar.isChecked();

        RetrofitService retrofit = new RetrofitService();

        CampanhaApi api = retrofit.getRetrofit().create(CampanhaApi.class);

        btnAlteracaoCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Campanha campanha = new Campanha();
                campanha.setNomeCampanha(getIntent().getStringExtra("nomeCampanha"));
                campanha.setDataInicio(getIntent().getStringExtra("dataInicio"));
                campanha.setDataFim(getIntent().getStringExtra("dataFim"));
                campanha.setIdCampanha(getIntent().getIntExtra("idCampanha", -1));

                if(!radioApositivoAlterar.isChecked() && !radioAnegativoAlterar.isChecked() && !radioBpositivoAlterar.isChecked() && !radioBnegativoAlterar.isChecked() && !radioABpositivoAlterar.isChecked() && !radioABnegativoAlterar.isChecked() && !radioOpositivoAlterar.isChecked() && !radioOnegativoAlterar.isChecked()){
                    Toast.makeText(getApplicationContext(), "É obrigatório selecionar um tipo sanguíneo como foco principal da campanha", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(radioApositivoAlterar.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.APositivo);
                if(radioAnegativoAlterar.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.ANegativo);
                if(radioBpositivoAlterar.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.BPositivo);
                if(radioBnegativoAlterar.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.BNegativo);
                if(radioABpositivoAlterar.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.ABPositivo);
                if(radioABnegativoAlterar.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.ABNegativo);
                if(radioOpositivoAlterar.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.OPositivo);
                if(radioOnegativoAlterar.isChecked())
                    campanha.setTipoSanguineoVisado(TipoSanguineo.ONegativo);

                CampanhaUpdateDTO dto = new CampanhaUpdateDTO();
                dto.setDataFim(campanha.getDataFim());
                dto.setDataInicio(campanha.getDataInicio());
                dto.setNomeCampanha(campanha.getNomeCampanha());
                dto.setTipoSanguineoVisado(campanha.getTipoSanguineoVisado());
                dto.setIdCampanha(campanha.getIdCampanha());

                api.alterarCampanha(dto).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getApplicationContext(), "Campanha alterada com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "Alteração da campanha falhou " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}