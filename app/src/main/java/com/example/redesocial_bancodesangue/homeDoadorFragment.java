package com.example.redesocial_bancodesangue;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.example.redesocial_bancodesangue.dto.UpdateLocationDTO;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

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

    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_PERMISSION_REQUEST = 100;
    private UsuarioApi api;

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

        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireActivity());

        RetrofitService retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(UsuarioApi.class);

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

        solicitarLocalizacao();

        carregarHemocentros();

        myMap.setOnMarkerClickListener(marker ->{
            Object tag = marker.getTag();

            if(tag instanceof Integer){
                Integer idHemocentro = (Integer) tag;
                abrirCampanhas(idHemocentro);
                return true;
            }

            return false;
        });
    }

    private void carregarHemocentros() {
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

        } catch (IOException e) {
            Log.e("MAPA", "Erro ao converter endereço", e);
        }
    }

    private void abrirCampanhas(Integer idHemocentro){
        ListaCampanhasHemocentroDoadorFragment fragment = ListaCampanhasHemocentroDoadorFragment.newInstance(idHemocentro, idDoador);

        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameTelaInicial, fragment).addToBackStack(null).commit();
    }

    private void solicitarLocalizacao(){
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST);

            return;
        }

        obterLocalizacao();
    }

    private void obterLocalizacao(){
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if(location != null){
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Log.d("LOCALIZAÇÃO", "LATITUDE: " + latitude + " LONGITUDE: " + longitude);

                UpdateLocationDTO updateLocationDTO = new UpdateLocationDTO();
                updateLocationDTO.setIdUsuario(idDoador);
                updateLocationDTO.setLatitude(latitude);
                updateLocationDTO.setLongitude(longitude);

                api.atualizarLocalizacao(updateLocationDTO).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful())
                            Toast.makeText(getActivity().getApplicationContext(), "Localização do usuário salvo com sucesso", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(getActivity().getApplicationContext(), "Localização do usuário não foi salva", Toast.LENGTH_SHORT).show();
                    }
                });

                posicionarMapa(latitude, longitude);
            }
        });
    }

    private void posicionarMapa(double latitude, double longitude) {
        LatLng localizacaoUsuario = new LatLng(latitude, longitude);

        myMap.addMarker(new MarkerOptions().position(localizacaoUsuario).title("Você está aqui"));

        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoUsuario, 13f));
    }
}