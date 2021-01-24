package com.jaax.pokeapidex.models;

import java.util.List;

public class PokemonRespuesta {
    private List<Pokemon> results;

    public List<Pokemon> getResults() {
        return results;
    }

    public void setResults(List<Pokemon> results) {
        this.results = results;
    }
}
