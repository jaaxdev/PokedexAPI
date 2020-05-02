package com.jaax.pokeapidex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.jaax.pokeapidex.models.Pokemon;
import com.jaax.pokeapidex.models.PokemonRespuesta;
import com.jaax.pokeapidex.pokeapi.PokeapiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";
    private RecyclerView recyclerView;
    private ListaPokemonAdapter pokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        pokemonAdapter = new ListaPokemonAdapter();
        recyclerView.setAdapter(pokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        obtenerDatos();
    }

    private void obtenerDatos(){
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonRespuesta> respuestaCall = service.obtenerListaPokemon();

        respuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                if(response.isSuccessful()){
                    PokemonRespuesta respuesta = response.body();
                    ArrayList<Pokemon> listaPokemon = respuesta.getResults();

                    pokemonAdapter.addListaPokemon(listaPokemon);
                } else {
                    Log.e(TAG, " onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
