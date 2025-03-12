package com.db.f1db.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.stereotype.Repository;

import com.db.f1db.model.Corrida;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class CorridaRepository extends CouchDbRepositorySupport<Corrida> {

    @Autowired
    public CorridaRepository(CouchDbConnector db) {
        super(Corrida.class, db);
    }

    public List<Corrida> listarCorridas() {
        return queryView("view-corridas");
    }
}
