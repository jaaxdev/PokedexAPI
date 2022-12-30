package com.jaax.pokeapidex.models;

public class Pokemon {
    private final String name;
    private final String imageURL;

    public Pokemon(String name, String imageURL){
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getNumber() {
        String[] arrayNumImg = imageURL.split("/");
        return arrayNumImg[ arrayNumImg.length - 1 ];
    }

    public String getUrl() {
        return this.imageURL;
    }
    public String getName() {
        return name;
    }
}
