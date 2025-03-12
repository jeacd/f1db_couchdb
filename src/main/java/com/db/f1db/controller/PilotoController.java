package com.db.f1db.controller;

import org.ektorp.DocumentNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.db.f1db.form.PilotoForm;
import com.db.f1db.model.Piloto;
import com.db.f1db.repository.PilotoRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/pilotos")
public class PilotoController {

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
    public Piloto salvarPiloto(@RequestBody PilotoForm PilotoData) {
        Piloto piloto = new Piloto(PilotoData.getNome(), PilotoData.getNacionalidade(), PilotoData.getNumero());

        PilotoRepository.add(piloto);
        return piloto;
    }

    @GetMapping
    public List<Piloto> listarTodosPilotos() {
        return PilotoRepository.listarPilotos();
    }
    
    @DeleteMapping("/{id}")
    public List<Piloto> deletarPiloto(@PathVariable String id) {
        try {
            PilotoRepository.remove(PilotoRepository.get(id));
            return PilotoRepository.listarPilotos();
        } catch (DocumentNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public Piloto atualizarPiloto(@PathVariable String id, @RequestBody Piloto novoPiloto) {
        Piloto pilotoExistente = PilotoRepository.get(id);
    
        if (pilotoExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Piloto não encontrado");
        }
    
        BeanUtils.copyProperties(novoPiloto, pilotoExistente, getNullPropertyNames(novoPiloto));
    
        PilotoRepository.update(pilotoExistente);
        return pilotoExistente;
    }
}
