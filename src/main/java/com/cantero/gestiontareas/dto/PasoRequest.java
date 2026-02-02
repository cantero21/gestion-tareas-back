package com.cantero.gestiontareas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasoRequest {

    @NotBlank(message = "La descripción del paso es obligatoria")
    private String descripcion;

    private boolean completado;
}