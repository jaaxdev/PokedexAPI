package com.jaax.pokeapidex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    private PokemonAdapter pokemonAdapter;
    private RecyclerView recyclerView;
    private static final String TAG = "POKEDEX";
    private boolean chargeable;
    private int offset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        pokemonAdapter = new PokemonAdapter( this );
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( layoutManager );
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
                        offset += 15;
                        obtenerDatos(offset);
                    }
                }
            }
            }
        });
        recyclerView.setAdapter(pokemonAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        offset = 0;
        obtenerDatos(offset);
    }

    private void obtenerDatos(int offset){
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonRespuesta> respuestaCall = service.obtenerListaPokemon(15, offset);

        respuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(@NonNull Call<PokemonRespuesta> call, @NonNull Response<PokemonRespuesta> response) {
                chargeable = true;
                if( response.isSuccessful() ){
                    PokemonRespuesta respuesta = response.body();
                    ArrayList<Pokemon> listaPokemon = (ArrayList<Pokemon>) respuesta.getResults();
                    pokemonAdapter.addListaPokemon(listaPokemon);
                } else {
                    Log.e(TAG, " onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonRespuesta> call, @NonNull Throwable t) {
                chargeable = true;
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
