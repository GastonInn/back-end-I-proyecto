package com.example.proyectointegradorinnamorato.service;

import com.example.proyectointegradorinnamorato.entity.Paciente;
import com.example.proyectointegradorinnamorato.repository.PacienteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private static final Logger LOGGER = Logger.getLogger(PacienteService.class);
    private PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente guardarPaciente (Paciente paciente) {
        LOGGER.info("Iniciando el proceso de guardar un paciente con apellido " + paciente.getApellido());
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPaciente(Long id){
        LOGGER.info("Iniciando el proceso de busqueda del paciente con id = " + id);
        return pacienteRepository.findById(id);
    }

    public List<Paciente> listarPacientes(){
        LOGGER.info("Iniciando el proceso de busqueda de todos los pacientes");
        return pacienteRepository.findAll();
    }

    public void actualizarPaciente(Paciente paciente){
        LOGGER.info("Iniciando el proceso de actualización del paciente con id = " + paciente.getId());
        pacienteRepository.save(paciente);
    }

    public void eliminarPaciente(Long id){
        pacienteRepository.deleteById(id);
        LOGGER.warn("Se realizó el proceso de eliminación del paciente con id = " + id);
    }

    public Optional<Paciente> buscarPacientePorEmail(String email) {
        LOGGER.info("Iniciando el proceso de busqueda del paciente con email = " + email);
        return pacienteRepository.findByEmailEquals(email);
    }

}
