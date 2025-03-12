package com.db.f1db.controller;

import org.ektorp.DocumentNotFoundException;

import com.db.f1db.form.EquipeForm;
import com.db.f1db.model.Equipe;
import com.db.f1db.model.Piloto;
import com.db.f1db.repository.EquipeRepository;
import com.db.f1db.repository.PilotoRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/equipes")
public class EquipeController {

    @Autowired
    private EquipeRepository EquipeRepository;
    @Autowired
    private PilotoRepository PilotoRepository;

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> emptyNames = new HashSet<>();

        for (var propertyDescriptor : src.getPropertyDescriptors()) {
            Object value = src.getPropertyValue(propertyDescriptor.getName());
            if (value == null) {
                emptyNames.add(propertyDescriptor.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }

    @PostMapping
    public Equipe salvarEquipe(@RequestBody EquipeForm EquipeData) {
        List<Piloto> pilotos = new ArrayList<>();

        for (String pilotoId : EquipeData.getIdPilotos()) {
            Piloto piloto = PilotoRepository.get(pilotoId);
            if (piloto != null) {
                pilotos.add(piloto);
            }
        }

        Equipe equipe = new Equipe(EquipeData.getNome(), EquipeData.getPais(), EquipeData.getAnoFundacao(), pilotos);

        EquipeRepository.add(equipe);
        return equipe;
    }

    @GetMapping
    public List<Equipe> listarTodasEquipes() {
        return EquipeRepository.listarEquipes();
    }

    @DeleteMapping("/{id}")
    public List<Equipe> deletarEquipe(@PathVariable String id) {
        try {
            EquipeRepository.remove(EquipeRepository.get(id));
            return EquipeRepository.listarEquipes();
        } catch (DocumentNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public Equipe atualizarEquipe(@PathVariable String id, @RequestBody Equipe novaEquipe) {
        Equipe equipeExistente = EquipeRepository.get(id);
    
        if (equipeExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipe não encontrada");
        }
    
        BeanUtils.copyProperties(novaEquipe, equipeExistente, getNullPropertyNames(novaEquipe));
    
        EquipeRepository.update(equipeExistente);
        return equipeExistente;
    }
}
