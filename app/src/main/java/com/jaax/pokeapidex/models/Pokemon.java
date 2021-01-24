package com.jaax.pokeapidex.models;

public class Pokemon {
    private final String name;
    private final String url;

    public Pokemon( String name, String url ){
        this.name = name;
        this.url = url;
    }

    public int getNumber() {
        String[] arrayNumImg = url.split("/");
        return Integer.parseInt( arrayNumImg[ arrayNumImg.length - 1 ]) ;
    }
    public String getName() {
        return name;
    }
}
