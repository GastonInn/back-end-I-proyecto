package com.example.proyectointegradorinnamorato.service;

import com.example.proyectointegradorinnamorato.dto.TurnoDTO;
import com.example.proyectointegradorinnamorato.entity.Domicilio;
import com.example.proyectointegradorinnamorato.entity.Odontologo;
import com.example.proyectointegradorinnamorato.entity.Paciente;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private PacienteService pacienteService;


    @Test
    @Order(1)
    public void guardarTurnoTest(){
        Odontologo odontologoAGuardar = odontologoService.guardarOdontologo(new Odontologo("SH548",
                "Gabriel", "Sanchez"));
        Paciente pacienteAGuardar = pacienteService.guardarPaciente(new Paciente("Alberto",
                "Herrera", "515682105", LocalDate.of(2022,11,30),
                "Alberto@gmail.com", new Domicilio("Calle", 123,"Las Piedras", "Canelones")));

        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(1L);
        turnoDTO.setFecha(LocalDate.of(2022,11,29));
        turnoDTO.setOdontologoId(1L);
        turnoDTO.setPacienteId(1L);

        TurnoDTO turnoDTOAGuardar = turnoService.guardarTurno(turnoDTO);

        assertEquals(1L, turnoDTOAGuardar.getId());
    }

    @Test
    @Order(2)
    public void buscarTurnoPorIdTest() {
        Long idABuscar = 1L;
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurno(idABuscar);

        assertNotNull(turnoBuscado.get());
    }

    @Test
    @Order(3)
    public void buscarTurnosTest() {
        List<TurnoDTO> turnosBuscados = turnoService.listarTurnos();
        Integer cantidadEsperada = 1;
        Assertions.assertEquals(cantidadEsperada, turnosBuscados.size());
    }

    @Test
    @Order(4)
    public void actualizarTurnoTest() {

        TurnoDTO turnoDTOActualizado = new TurnoDTO();
        turnoDTOActualizado.setId(1L);
        turnoDTOActualizado.setFecha(LocalDate.of(2023,11,30));
        turnoDTOActualizado.setOdontologoId(1L);
        turnoDTOActualizado.setPacienteId(1L);

        turnoService.actualizarTurno(turnoDTOActualizado);
        Optional<TurnoDTO> buscarTurnoActualizado = turnoService.buscarTurno(turnoDTOActualizado.getId());

        Assertions.assertEquals(LocalDate.of(2023,11,30), buscarTurnoActualizado.get().getFecha());
    }

    @Test
    @Order(5)
    public void eliminarTurnoTest() {
        Long idAEliminar = 1L;
        turnoService.eliminarTurno(idAEliminar);

        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurno(idAEliminar);

        Assertions.assertFalse(turnoBuscado.isPresent());
    }
}