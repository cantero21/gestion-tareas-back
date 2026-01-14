package com.cantero.gestiontareas.controller;


import com.cantero.gestiontareas.entities.Tarea;
import com.cantero.gestiontareas.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "https://stalwart-paletas-d6b1a7.netlify.app")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @GetMapping
    public List<Tarea> listarTareas(){
        return tareaService.getAllTareas();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Tarea> listarTareaPorId(@PathVariable Long id){
        Optional<Tarea> tarea = tareaService.getTareaById(id);
        return tarea.map(value -> ResponseEntity.ok().body(value)).orElse(null);
    }



    @PostMapping
    public ResponseEntity<Tarea>guardarTarea(@RequestBody Tarea tarea) {
        Tarea tareaGuardada = tareaService.createTarea(tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaGuardada);


    }


    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tarea){
        Tarea tareaUpdate = tareaService.updateTarea(id,tarea);
        if (tareaUpdate != null){
            return ResponseEntity.ok().body(tareaUpdate);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id){
        tareaService.deleteTarea(id);
        return ResponseEntity.noContent().build();
    }






}
