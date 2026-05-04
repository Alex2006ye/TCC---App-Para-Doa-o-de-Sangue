package com.example.redesocial_bancodesangue;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.redesocial_bancodesangue.databinding.ActivityTelaInicialHemocentrosBinding;

public class TelaInicialHemocentros extends AppCompatActivity {

    ActivityTelaInicialHemocentrosBinding biding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biding = ActivityTelaInicialHemocentrosBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(biding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int idHemocentro = getIntent().getIntExtra("idUsuario", -1);

        fragmentReplace(HomeHemocentroFragment.newInstance(idHemocentro));

        // Aqui ele vai verificar para qual fragment ele deve mudar
        biding.bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            int menuItemId = menuItem.getItemId();

            if(menuItemId == R.id.homeHemo){
                fragmentReplace(HomeHemocentroFragment.newInstance(idHemocentro));
            }
            else if (menuItemId == R.id.campanhasHemo){
                fragmentReplace(CampanhasHemocentroFragment.newInstance(idHemocentro));
            }

            return true;
        });
    }

    //Esse é o método para mudar de fragment
    private void fragmentReplace(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameTelaInicialHemocentros, fragment);
        transaction.commit();
    }
}