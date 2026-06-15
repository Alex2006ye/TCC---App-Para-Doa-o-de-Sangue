package com.example.redesocial_bancodesangue;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.example.redesocial_bancodesangue.model.Usuario;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;
import com.example.redesocial_bancodesangue.retrofit.UsuarioApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeDoadorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeDoadorFragment extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GoogleMap myMap;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer idDoador;

    public homeDoadorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment homeDoadorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homeDoadorFragment newInstance(int idDoador) {
        homeDoadorFragment fragment = new homeDoadorFragment();
        Bundle args = new Bundle();
        args.putInt("idDoador", idDoador);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idDoador = getArguments().getInt("idDoador");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_home_doador,
                container,
                false);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        carregarHemocentros();
    }

    private void carregarHemocentros() {
        RetrofitService retrofitService = new RetrofitService();

        UsuarioApi api = retrofitService.getRetrofit().create(UsuarioApi.class);

        api.listarHemocentros().enqueue(new Callback<List<Usuario>>() {

                    @Override
                    public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {

                        if(response.isSuccessful() && response.body() != null){

                            for(Usuario hemo : response.body()){
                                localizarHemocentro(hemo);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Usuario>> call, Throwable t) {

                        Log.e(
                                "MAPA",
                                "Erro ao buscar hemocentros",
                                t
                        );
                    }
                });
    }

    private void localizarHemocentro(Usuario hemo) {

        try {
            Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());

            String enderecoCompleto = hemo.getRua() + ", "
                            + hemo.getNumero() + ", "
                            + hemo.getBairro() + ", "
                            + hemo.getCep();

            List<Address> resultado = geocoder.getFromLocationName(enderecoCompleto, 1);

            if(resultado != null && !resultado.isEmpty()) {

                Address endereco = resultado.get(0);

                LatLng posicao = new LatLng(endereco.getLatitude(), endereco.getLongitude());
                //Adiciona marcador no mapa
                Marker marker = myMap.addMarker(new MarkerOptions().position(posicao).title(hemo.getNome()));
                //Guarda ID dentro do marcador
                marker.setTag(hemo.getId());

                myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicao, 12f));
            }
            myMap.setOnMarkerClickListener(marker -> {
                Integer idHemocentro = (Integer) marker.getTag();
                abrirCampanhas(idHemocentro);
                return true;
            });

        } catch (IOException e) {
            Log.e("MAPA", "Erro ao converter endereço", e);
        }
    }

    private void abrirCampanhas(Integer idHemocentro){
        ListaCampanhasHemocentroDoadorFragment fragment = ListaCampanhasHemocentroDoadorFragment.newInstance(idHemocentro, idDoador);

        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameTelaInicial, fragment).addToBackStack(null).commit();
    }
}