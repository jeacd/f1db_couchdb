package com.db.f1db.model;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Piloto extends CouchDbDocument {

    private String nome;
    private String nacionalidade;
    private int numero;

    @TypeDiscriminator
    private final String type = "piloto";

    public Piloto(){
    };

    public Piloto(String nome, String nacionalidade, int numero) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.numero = numero;
    }
}
