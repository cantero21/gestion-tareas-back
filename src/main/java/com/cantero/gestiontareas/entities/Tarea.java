package com.cantero.gestiontareas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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



    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true ,  fetch = FetchType.EAGER)
    @JoinColumn(name = "tarea_id") // Esto crea la columna foránea en la tabla 'pasos'
    private List<Paso> pasos = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore // Para no crear bucle infinito en el JSON
    private Usuario usuario;
}