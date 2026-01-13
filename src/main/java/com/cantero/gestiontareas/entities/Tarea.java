package com.cantero.gestiontareas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList; // Importante
import java.util.List;      // Importante

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    private boolean completado; // Estado general de la tarea



    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tarea_id") // Esto crea la columna foránea en la tabla 'pasos'
    private List<Paso> pasos = new ArrayList<>();
}