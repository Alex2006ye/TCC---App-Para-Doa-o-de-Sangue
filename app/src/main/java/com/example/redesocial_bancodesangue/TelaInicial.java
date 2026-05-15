package com.example.redesocial_bancodesangue;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.redesocial_bancodesangue.databinding.ActivityTelaInicialBinding;

public class TelaInicial extends AppCompatActivity {

    ActivityTelaInicialBinding biding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biding = ActivityTelaInicialBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(biding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int idDoador = getIntent().getIntExtra("idUsuario", -1);

        fragmentReplace(homeDoadorFragment.newInstance(idDoador));

        // Aqui ele vai verificar para qual fragment ele deve mudar
        biding.bottomNavigationViewDoador.setOnItemSelectedListener(menuItem -> {
            int menuItemId = menuItem.getItemId();

            if(menuItemId == R.id.homeDoador){
                fragmentReplace(homeDoadorFragment.newInstance(idDoador));
            }
            else if (menuItemId == R.id.agendamentosDoador){
                fragmentReplace(doadorAgendamentos.newInstance(idDoador));
            }

            return true;
        });
    }

    private void fragmentReplace(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameTelaInicial, fragment);
        transaction.commit();
    }
}