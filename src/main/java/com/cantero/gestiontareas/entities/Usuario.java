package com.cantero.gestiontareas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private  String password;

    @Column(nullable = false)
    private String role;
}
