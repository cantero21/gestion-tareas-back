package com.cantero.gestiontareas.controller;

import com.cantero.gestiontareas.dto.AuthResponse;
import com.cantero.gestiontareas.dto.LoginRequest;
import com.cantero.gestiontareas.dto.RegisterRequest;
import com.cantero.gestiontareas.entities.Usuario;
import com.cantero.gestiontareas.repositories.UsuarioRepository;
import com.cantero.gestiontareas.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://stalwart-paletas-d6b1a7.netlify.app")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse("El usuario ya existe", null, null));
        }

        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        usuarioRepository.save(usuario);

        // Generar token para el nuevo usuario
        UserDetails userDetails = new User(usuario.getUsername(), usuario.getPassword(), List.of());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse("Usuario registrado exitosamente", usuario.getUsername(), token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow();

        UserDetails userDetails = new User(usuario.getUsername(), usuario.getPassword(), List.of());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse("Login exitoso", usuario.getUsername(), token));
    }
}