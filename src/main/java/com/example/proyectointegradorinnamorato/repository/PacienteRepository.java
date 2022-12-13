package com.example.proyectointegradorinnamorato.repository;

import com.example.proyectointegradorinnamorato.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByEmailEquals(String email);
}
