package com.db.f1db.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.stereotype.Repository;

import com.db.f1db.model.Equipe;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class EquipeRepository extends CouchDbRepositorySupport<Equipe> {

    @Autowired
    public EquipeRepository(CouchDbConnector db) {
        super(Equipe.class, db);
    }

    public List<Equipe> listarEquipes(){
        return queryView("view-equipes");
    }
}
