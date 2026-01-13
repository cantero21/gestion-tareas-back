package com.cantero.gestiontareas.services.impl;


import com.cantero.gestiontareas.entities.Tarea;
import com.cantero.gestiontareas.repositories.TareaRepository;
import com.cantero.gestiontareas.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaServiceImpl implements TareaService {

    @Autowired
    private TareaRepository tareaRepository;


    @Override
    public List<Tarea> getAllTareas() {
        return tareaRepository.findAll();
    }

    @Override
    public Optional<Tarea> getTareaById(Long id) {
        return tareaRepository.findById(id);
    }

    @Override
    public Tarea createTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Override
    public Tarea updateTarea(Long id, Tarea tarea) {
        if(tareaRepository.existsById(id)){
            return tareaRepository.save(tarea);
        }
        return null;
    }

    @Override
    public void deleteTarea(Long id) {
        tareaRepository.deleteById(id);

    }
}
