package com.db.f1db.model;

import java.util.List;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Equipe extends CouchDbDocument {

    private String nome;
    private String pais;
    private int anoFundacao;
    private List<Piloto> pilotos;

    @TypeDiscriminator
    private final String type = "equipe";

    public Equipe() {
    }

    public Equipe(String nome, String pais, int anoFundacao, List<Piloto> pilotos) {
        this.nome = nome;
        this.pais = pais;
        this.anoFundacao = anoFundacao;
        this.pilotos = pilotos;
    }
}
