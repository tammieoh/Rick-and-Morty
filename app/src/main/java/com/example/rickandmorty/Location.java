package com.example.rickandmorty;

public class Location {
    String name;
    String type;
    String dimension;

    public Location(String name, String type, String dimension) {
        this.name = name;
        this.type = type;
        this.dimension = dimension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String description) {
        this.type = type;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String image_url) {
        this.dimension = dimension;
    }

}
