package com.cantero.gestiontareas.controller;

import com.cantero.gestiontareas.entities.Tarea;
import com.cantero.gestiontareas.entities.Usuario;
import com.cantero.gestiontareas.repositories.UsuarioRepository;
import com.cantero.gestiontareas.services.TareaService;
import com.cantero.gestiontareas.dto.TareaRequest;
import com.cantero.gestiontareas.entities.Paso;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = {
        "https://stalwart-paletas-d6b1a7.netlify.app",
        "http://localhost:4200"
})
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Tarea> listarTareas(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = obtenerUsuario(userDetails);
        return tareaService.getAllTareas(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> listarTareaPorId(@PathVariable Long id,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = obtenerUsuario(userDetails);
        return tareaService.getTareaById(id, usuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarea> guardarTarea(@Valid @RequestBody TareaRequest request,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = obtenerUsuario(userDetails);
        Tarea tarea = convertirATarea(request);
        Tarea tareaGuardada = tareaService.createTarea(tarea, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id,
                                                 @Valid @RequestBody TareaRequest request,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = obtenerUsuario(userDetails);
        Tarea tarea = convertirATarea(request);
        Tarea tareaUpdate = tareaService.updateTarea(id, tarea, usuario);
        if (tareaUpdate != null) {
            return ResponseEntity.ok(tareaUpdate);
        }
        return ResponseEntity.notFound().build();
    }

    private Tarea convertirATarea(TareaRequest request) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(request.getTitulo());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setCompletado(request.isCompletado());

        List<Paso> pasos = request.getPasos().stream()
                .map(pasoRequest -> {
                    Paso paso = new Paso();
                    paso.setDescripcion(pasoRequest.getDescripcion());
                    paso.setCompletado(pasoRequest.isCompletado());
                    return paso;
                })
                .toList();

        tarea.setPasos(pasos);
        return tarea;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = obtenerUsuario(userDetails);
        tareaService.deleteTarea(id, usuario);
        return ResponseEntity.noContent().build();
    }

    private Usuario obtenerUsuario(UserDetails userDetails) {
        return usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}