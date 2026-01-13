package com.cantero.gestiontareas.repositories;

import com.cantero.gestiontareas.entities.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository <Tarea , Long>{
}
