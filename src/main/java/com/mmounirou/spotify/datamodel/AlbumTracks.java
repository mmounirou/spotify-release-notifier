package com.mmounirou.spotify.datamodel;


/**
 * AlbumTracks is a Querydsl bean type
 */
public class AlbumTracks {

    private String albumsUri;

    private String tracksUri;

    public String getAlbumsUri() {
        return albumsUri;
    }

    public void setAlbumsUri(String albumsUri) {
        this.albumsUri = albumsUri;
    }

    public String getTracksUri() {
        return tracksUri;
    }

    public void setTracksUri(String tracksUri) {
        this.tracksUri = tracksUri;
    }

}

