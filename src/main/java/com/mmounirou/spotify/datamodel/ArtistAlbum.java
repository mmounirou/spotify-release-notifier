package com.mmounirou.spotify.datamodel;


/**
 * ArtistAlbum is a Querydsl bean type
 */
public class ArtistAlbum {

    private String albumsUri;

    private String artistsUri;

    public String getAlbumsUri() {
        return albumsUri;
    }

    public void setAlbumsUri(String albumsUri) {
        this.albumsUri = albumsUri;
    }

    public String getArtistsUri() {
        return artistsUri;
    }

    public void setArtistsUri(String artistsUri) {
        this.artistsUri = artistsUri;
    }

}

