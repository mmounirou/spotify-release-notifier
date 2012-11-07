package com.mmounirou.spotify.datamodel.query;

import static com.mysema.query.types.PathMetadataFactory.*;
import com.mmounirou.spotify.datamodel.AlbumTracks;


import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAlbumTracks is a Querydsl query type for AlbumTracks
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAlbumTracks extends com.mysema.query.sql.RelationalPathBase<AlbumTracks> {

    private static final long serialVersionUID = -2131322576;

    public static final QAlbumTracks tAlbumTracks = new QAlbumTracks("t_album_tracks");

    public final StringPath albumsUri = createString("albums_uri");

    public final StringPath tracksUri = createString("tracks_uri");

    public QAlbumTracks(String variable) {
        super(AlbumTracks.class, forVariable(variable), "null", "t_album_tracks");
    }

    @SuppressWarnings("all")
    public QAlbumTracks(Path<? extends AlbumTracks> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "t_album_tracks");
    }

    public QAlbumTracks(PathMetadata<?> metadata) {
        super(AlbumTracks.class, metadata, "null", "t_album_tracks");
    }

}

