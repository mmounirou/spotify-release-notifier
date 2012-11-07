package fr.cantor.test.datamodel;


/**
 * Tracks is a Querydsl bean type
 */
public class Tracks {

    private String name;

    private String trackalbum;

    private String trackartist;

    private String uri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrackalbum() {
        return trackalbum;
    }

    public void setTrackalbum(String trackalbum) {
        this.trackalbum = trackalbum;
    }

    public String getTrackartist() {
        return trackartist;
    }

    public void setTrackartist(String trackartist) {
        this.trackartist = trackartist;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}

