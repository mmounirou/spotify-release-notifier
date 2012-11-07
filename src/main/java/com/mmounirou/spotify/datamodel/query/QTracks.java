package com.mmounirou.spotify.datamodel.query;

import static com.mysema.query.types.PathMetadataFactory.*;
import com.mmounirou.spotify.datamodel.Tracks;


import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTracks is a Querydsl query type for Tracks
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTracks extends com.mysema.query.sql.RelationalPathBase<Tracks> {

    private static final long serialVersionUID = -523255921;

    public static final QTracks tTracks = new QTracks("t_tracks");

    public final StringPath name = createString("name");

    public final StringPath uri = createString("uri");

    public QTracks(String variable) {
        super(Tracks.class, forVariable(variable), "null", "t_tracks");
    }

    @SuppressWarnings("all")
    public QTracks(Path<? extends Tracks> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "t_tracks");
    }

    public QTracks(PathMetadata<?> metadata) {
        super(Tracks.class, metadata, "null", "t_tracks");
    }

}

