package com.example.proyectointegradorinnamorato.service;

import com.example.proyectointegradorinnamorato.dto.TurnoDTO;
import com.example.proyectointegradorinnamorato.entity.Odontologo;
import com.example.proyectointegradorinnamorato.entity.Paciente;
import com.example.proyectointegradorinnamorato.entity.Turno;
import com.example.proyectointegradorinnamorato.repository.TurnoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    private static final Logger LOGGER = Logger.getLogger(TurnoService.class);

    private TurnoRepository turnoRepository;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    public TurnoDTO guardarTurno(TurnoDTO turnoDTO) {
        LOGGER.info("Iniciando el proceso de guardar un turno con fecha " + turnoDTO.getFecha());
        Turno turnoAGuardar = turnoDTOaTurno(turnoDTO);
        Turno turnoGuardado = turnoRepository.save(turnoAGuardar);
        return turnoAturnoDTO(turnoGuardado);
    }

    public Optional<TurnoDTO> buscarTurno(Long id) {
        LOGGER.info("Iniciando el proceso de busqueda del turno con id = " + id);
        Optional<Turno> turnoBuscado = turnoRepository.findById(id);
        if (turnoBuscado.isPresent()) {
            return Optional.of(turnoAturnoDTO(turnoBuscado.get()));
        }
        else {
            return Optional.empty();
        }
    }

    public List<TurnoDTO> listarTurnos() {
        LOGGER.info("Iniciando el proceso de busqueda de todos los turnos");
        List<Turno> turnosEncontrados = turnoRepository.findAll();
        List<TurnoDTO> respuesta = new ArrayList<>();
        for (Turno t : turnosEncontrados) {
            respuesta.add((turnoAturnoDTO(t)));
        }
        return respuesta;
    }

    public void actualizarTurno(TurnoDTO turnoDTO) {
        LOGGER.info("Iniciando el proceso de actualización del turno con id = " + turnoDTO.getId());
        Turno turnoAActualizar = turnoDTOaTurno(turnoDTO);
        turnoRepository.save(turnoAActualizar);
    }

    public void eliminarTurno(Long id) {
        turnoRepository.deleteById(id);
        LOGGER.warn("Se realizó el proceso de eliminación del turno con id = " + id);
    }

    private TurnoDTO turnoAturnoDTO(Turno turno) {
        TurnoDTO respuesta = new TurnoDTO();
        respuesta.setId(turno.getId());
        respuesta.setFecha(turno.getFecha());
        respuesta.setPacienteId(turno.getPaciente().getId());
        respuesta.setOdontologoId(turno.getOdontologo().getId());
        return respuesta;
    }

    private Turno turnoDTOaTurno(TurnoDTO turnoDTO) {
        Turno turno = new Turno();
        Paciente paciente = pacienteService.buscarPaciente(turnoDTO.getPacienteId()).get();
        Odontologo odontologo = odontologoService.buscarOdontologo(turnoDTO.getOdontologoId()).get();

        turno.setId(turnoDTO.getId());
        turno.setFecha(turnoDTO.getFecha());
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);

        return turno;
    }

}