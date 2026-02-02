package com.cantero.gestiontareas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TareaRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede tener más de 100 caracteres")
    private String titulo;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    private String descripcion;

    private boolean completado;

    private List<PasoRequest> pasos = new ArrayList<>();
}