package com.cantero.gestiontareas.services;

import com.cantero.gestiontareas.entities.Tarea;
import com.cantero.gestiontareas.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface TareaService {

    List<Tarea> getAllTareas(Usuario usuario);

    Optional<Tarea> getTareaById(Long id, Usuario usuario);

    Tarea createTarea(Tarea tarea, Usuario usuario);

    Tarea updateTarea(Long id, Tarea tarea, Usuario usuario);

    void deleteTarea(Long id, Usuario usuario);
}