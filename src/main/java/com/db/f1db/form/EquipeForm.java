package com.db.f1db.form;

import java.util.List;

import lombok.Data;

@Data
public class EquipeForm {
    private String nome;
    private String pais;
    private int anoFundacao;
    private List<String> idPilotos;
}
