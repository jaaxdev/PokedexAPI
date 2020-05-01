package com.jaax.pokeapidex.pokeapi;

import com.jaax.pokeapidex.models.PokemonRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeapiService {

    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon();
}
