package com.db.f1db.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.stereotype.Repository;

import com.db.f1db.model.Piloto;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class PilotoRepository extends CouchDbRepositorySupport<Piloto> {

    @Autowired
    public PilotoRepository(CouchDbConnector db) {
        super(Piloto.class, db);
    }

    public List<Piloto> listarPilotos() {
        return queryView("view-pilotos");
    }
}
