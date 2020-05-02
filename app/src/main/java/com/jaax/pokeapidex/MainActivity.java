package com.jaax.pokeapidex;

import androidx.annotation.NonNull;
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
    private int offset;
    private boolean chargeable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        pokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView.setAdapter(pokemonAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int itemTotalCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if(chargeable){
                        if((visibleItemCount + pastVisibleItems) >= itemTotalCount){
                            Log.i(TAG, " Final");
                            chargeable = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        offset = 0;
        obtenerDatos(offset);
    }

    private void obtenerDatos(int offset){
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonRespuesta> respuestaCall = service.obtenerListaPokemon(20, offset);

        respuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                chargeable = true;
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
                chargeable = true;
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
