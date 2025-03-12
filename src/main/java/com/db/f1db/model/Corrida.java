package com.db.f1db.model;

import java.util.Date;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Corrida extends CouchDbDocument {

    private String nome;
    private String circuito;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date data;
    private Piloto vencedor;

    @TypeDiscriminator
    private final String type = "corrida";

    public Corrida(){
    };

    public Corrida(String nome, String circuito, Date data, Piloto vencedor) {
        this.nome = nome;
        this.circuito = circuito;
        this.data = data;
        this.vencedor = vencedor;
    }
}
