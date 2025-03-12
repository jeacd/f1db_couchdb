package com.db.f1db.controller;

import org.ektorp.DocumentNotFoundException;

import com.db.f1db.form.CorridaForm;
import com.db.f1db.model.Corrida;
import com.db.f1db.model.Piloto;
import com.db.f1db.repository.CorridaRepository;
import com.db.f1db.repository.PilotoRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/corridas")
public class CorridaController {

    @Autowired
    private CorridaRepository CorridaRepository;
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
    public Corrida salvarCorrida(@RequestBody CorridaForm CorridaData) {
        Piloto piloto = PilotoRepository.get(CorridaData.getVencedorId());
        Corrida corrida = new Corrida(CorridaData.getNome(), CorridaData.getCircuito(), CorridaData.getData(), piloto);

        CorridaRepository.add(corrida);
        return corrida;
    }

    @GetMapping
    public List<Corrida> listarTodosCorridas() {
        return CorridaRepository.listarCorridas();
    }

    @DeleteMapping("/{id}")
    public List<Corrida> deletarCorrida(@PathVariable String id) {
        try {
            CorridaRepository.remove(CorridaRepository.get(id));
            return CorridaRepository.listarCorridas();
        } catch (DocumentNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    @PutMapping("/{id}")
    public Corrida atualizarCorrida(@PathVariable String id, @RequestBody CorridaForm novaCorrida) {
        Corrida corridaExistente = CorridaRepository.get(id);
    
        if (corridaExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Corrida não encontrada");
        }
    
        BeanUtils.copyProperties(novaCorrida, corridaExistente, getNullPropertyNames(novaCorrida));
    
        CorridaRepository.update(corridaExistente);
        return corridaExistente;
    }
}
