package com.example.proyectointegradorinnamorato.controller;

import com.example.proyectointegradorinnamorato.dto.TurnoDTO;
import com.example.proyectointegradorinnamorato.entity.Odontologo;
import com.example.proyectointegradorinnamorato.entity.Paciente;
import com.example.proyectointegradorinnamorato.exception.BadRequestException;
import com.example.proyectointegradorinnamorato.exception.ResourceNotFoundException;
import com.example.proyectointegradorinnamorato.service.OdontologoService;
import com.example.proyectointegradorinnamorato.service.PacienteService;
import com.example.proyectointegradorinnamorato.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private TurnoService turnoService;
    private OdontologoService odontologoService;
    private PacienteService pacienteService;

    @Autowired
    public TurnoController(TurnoService turnoService, OdontologoService odontologoService, PacienteService pacienteService) {
        this.turnoService = turnoService;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
    }


    @PostMapping
    public ResponseEntity<TurnoDTO> registrarTurno(@RequestBody TurnoDTO turnoDTO) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(turnoDTO.getPacienteId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(turnoDTO.getOdontologoId());

        if (pacienteBuscado.isPresent() && odontologoBuscado.isPresent()) {
            return ResponseEntity.ok(turnoService.guardarTurno(turnoDTO));
        }
        throw new ResourceNotFoundException("No existe un turno con id = " + turnoDTO.getId() + " en la base de datos.");
    }


    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listarLosTurnos() {
        return ResponseEntity.ok(turnoService.listarTurnos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurno(id);

        if (turnoBuscado.isPresent()) {
            return ResponseEntity.ok(turnoBuscado.get());
        }
        throw new ResourceNotFoundException("No existe un turno con id = " + id + " en la base de datos.");
    }


    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody TurnoDTO turnoDTO) throws BadRequestException, ResourceNotFoundException {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurno(turnoDTO.getId());

        if (turnoBuscado.isPresent()) {
            if (pacienteService.buscarPaciente(turnoDTO.getPacienteId()).isPresent() &&
                    odontologoService.buscarOdontologo(turnoDTO.getOdontologoId()).isPresent()) {
                        turnoService.actualizarTurno(turnoDTO);
                        return ResponseEntity.ok("Se actualizó el turno con id = " + turnoDTO.getId());
            }
            else {
                throw new BadRequestException("Error al actualizar, verificar si el paciente y/o el" + "odontologo existen en la base de datos.");
            }
        }
        throw new ResourceNotFoundException("No existe un turno con id = " + turnoDTO.getId() + " en la base de datos.");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurno(id);

        if (turnoBuscado.isPresent()) {
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok().body("Se eliminó el turno con id = " + id);
        }
        throw new ResourceNotFoundException("No existe un turno con id = " + id + " en la base de datos.");
    }

}
