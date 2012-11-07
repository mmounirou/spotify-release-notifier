package com.mmounirou.spotify.datamodel.query;

import static com.mysema.query.types.PathMetadataFactory.*;


import com.mmounirou.spotify.datamodel.Albums;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAlbums is a Querydsl query type for Albums
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAlbums extends com.mysema.query.sql.RelationalPathBase<Albums> {

    private static final long serialVersionUID = -1961403478;

    public static final QAlbums tAlbums = new QAlbums("t_albums");

    public final StringPath albumartist = createString("albumartist");

    public final StringPath name = createString("name");

    public final StringPath uri = createString("uri");

    public QAlbums(String variable) {
        super(Albums.class, forVariable(variable), "null", "t_albums");
    }

    @SuppressWarnings("all")
    public QAlbums(Path<? extends Albums> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "t_albums");
    }

    public QAlbums(PathMetadata<?> metadata) {
        super(Albums.class, metadata, "null", "t_albums");
    }

}

