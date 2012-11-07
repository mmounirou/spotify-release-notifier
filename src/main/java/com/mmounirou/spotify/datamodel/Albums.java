package com.mmounirou.spotify.datamodel;


/**
 * Albums is a Querydsl bean type
 */
public class Albums {

    private String name;

    private String uri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}

