package com.mmounirou.spotify.datamodel.query;

import static com.mysema.query.types.PathMetadataFactory.*;
import com.mmounirou.spotify.datamodel.ArtistAlbum;


import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QArtistAlbum is a Querydsl query type for ArtistAlbum
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QArtistAlbum extends com.mysema.query.sql.RelationalPathBase<ArtistAlbum> {

    private static final long serialVersionUID = 977529025;

    public static final QArtistAlbum tArtistAlbum = new QArtistAlbum("t_artist_album");

    public final StringPath albumsUri = createString("albums_uri");

    public final StringPath artistsUri = createString("artists_uri");

    public QArtistAlbum(String variable) {
        super(ArtistAlbum.class, forVariable(variable), "null", "t_artist_album");
    }

    @SuppressWarnings("all")
    public QArtistAlbum(Path<? extends ArtistAlbum> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "t_artist_album");
    }

    public QArtistAlbum(PathMetadata<?> metadata) {
        super(ArtistAlbum.class, metadata, "null", "t_artist_album");
    }

}

