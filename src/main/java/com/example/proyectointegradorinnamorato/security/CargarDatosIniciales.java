package com.example.proyectointegradorinnamorato.security;

import com.example.proyectointegradorinnamorato.entity.Usuario;
import com.example.proyectointegradorinnamorato.entity.UsuarioRole;
import com.example.proyectointegradorinnamorato.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CargarDatosIniciales implements ApplicationRunner {
    private UsuarioRepository usuarioRepository;

    @Autowired
    public CargarDatosIniciales(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder cifrador = new BCryptPasswordEncoder();
        String passwordCifrada = cifrador.encode("12345");

        Usuario admin = new Usuario("Gaston", "Gaston", "admin@gmail.com", passwordCifrada, UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(admin);

        Usuario user = new Usuario("user", "user", "user@gmail.com", passwordCifrada, UsuarioRole.ROLE_USER);
        usuarioRepository.save(user);
    }
}
