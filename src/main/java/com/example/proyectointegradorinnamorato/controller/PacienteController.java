package com.example.proyectointegradorinnamorato.controller;

import com.example.proyectointegradorinnamorato.entity.Paciente;
import com.example.proyectointegradorinnamorato.exception.ResourceNotFoundException;
import com.example.proyectointegradorinnamorato.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    @PostMapping
    public ResponseEntity<Paciente> registrarNuevoPaciente(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }


    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(id);

        if (pacienteBuscado.isPresent()) {
            return ResponseEntity.ok(pacienteBuscado.get());
        }
        throw new ResourceNotFoundException("No existe un paciente con id = " + id + " en la base de datos.");
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<Paciente> buscarPacientePorEmail(@PathVariable String email) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorEmail(email);

        if (pacienteBuscado.isPresent()) {
            return ResponseEntity.ok(pacienteBuscado.get());
        }
        throw new ResourceNotFoundException("No existe un paciente con email: (" + email + ") en la base de datos.");
    }


    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(paciente.getId());

        if (pacienteBuscado.isPresent()) {
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("Se actualizó al paciente con id = " + paciente.getId());
        }
        throw new ResourceNotFoundException("No existe un paciente con id = " + paciente.getId() + " en la base de datos.");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException{
        if (pacienteService.buscarPaciente(id).isPresent()){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok().body("Se eliminó al paciente con id = " + id);
        }
        throw new ResourceNotFoundException("No existe un paciente con id = " + id + " en la base de datos.");
    }

}
