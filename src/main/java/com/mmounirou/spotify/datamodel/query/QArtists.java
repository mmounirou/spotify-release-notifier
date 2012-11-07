package com.mmounirou.spotify.datamodel.query;

import static com.mysema.query.types.PathMetadataFactory.*;
import com.mmounirou.spotify.datamodel.Artists;


import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QArtists is a Querydsl query type for Artists
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QArtists extends com.mysema.query.sql.RelationalPathBase<Artists> {

    private static final long serialVersionUID = 1293968357;

    public static final QArtists tArtists = new QArtists("t_artists");

    public final StringPath name = createString("name");

    public final StringPath uri = createString("uri");

    public QArtists(String variable) {
        super(Artists.class, forVariable(variable), "null", "t_artists");
    }

    @SuppressWarnings("all")
    public QArtists(Path<? extends Artists> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "t_artists");
    }

    public QArtists(PathMetadata<?> metadata) {
        super(Artists.class, metadata, "null", "t_artists");
    }

}

