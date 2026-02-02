package com.cantero.gestiontareas.services.impl;

import com.cantero.gestiontareas.entities.Tarea;
import com.cantero.gestiontareas.entities.Usuario;
import com.cantero.gestiontareas.repositories.TareaRepository;
import com.cantero.gestiontareas.services.TareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;

    @Override
    public List<Tarea> getAllTareas(Usuario usuario) {
        return tareaRepository.findByUsuario(usuario);
    }

    @Override
    public Optional<Tarea> getTareaById(Long id, Usuario usuario) {
        return tareaRepository.findByIdAndUsuario(id, usuario);
    }

    @Override
    public Tarea createTarea(Tarea tarea, Usuario usuario) {
        tarea.setUsuario(usuario);
        return tareaRepository.save(tarea);
    }

    @Override
    public Tarea updateTarea(Long id, Tarea tarea, Usuario usuario) {
        return tareaRepository.findByIdAndUsuario(id, usuario)
                .map(tareaExistente -> {
                    tarea.setId(id);
                    tarea.setUsuario(usuario);
                    return tareaRepository.save(tarea);
                })
                .orElse(null);
    }

    @Override
    public void deleteTarea(Long id, Usuario usuario) {
        tareaRepository.findByIdAndUsuario(id, usuario)
                .ifPresent(tareaRepository::delete);
    }
}