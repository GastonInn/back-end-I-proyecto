package com.example.proyectointegradorinnamorato.service;

import com.example.proyectointegradorinnamorato.entity.Domicilio;
import com.example.proyectointegradorinnamorato.entity.Paciente;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    public void guardarPacienteTest() {
        Paciente pacienteAGuardar = new Paciente("Nombre","Apellido","DNI", LocalDate.of(2022,10,05),"email@gmail.com", new Domicilio("Calle", 123, "Localidad", "Provincia"));
        Paciente pacienteGuardado = pacienteService.guardarPaciente(pacienteAGuardar);

        Assertions.assertEquals(1L, pacienteGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarPacientePorIdTest() {
        Long idABuscar = 1L;
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(idABuscar);

        Assertions.assertNotNull(pacienteBuscado.get());
    }

    @Test
    @Order(3)
    public void buscarPacientesTest() {
        List<Paciente> pacientesBuscados = pacienteService.listarPacientes();
        Integer cantidadEsperada = 1;

        Assertions.assertEquals(cantidadEsperada, pacientesBuscados.size());
    }

    @Test
    @Order(4)
    public void actualizarPacienteTest() {
        Paciente pacienteActualizado= new Paciente(1L,"Pedro","Sanchez","DNI", LocalDate.of(2022,10,05),"email@gmail.com", new Domicilio("Calle", 123, "Localidad", "Provincia"));
        pacienteService.actualizarPaciente(pacienteActualizado);
        Optional<Paciente> buscarPacienteActualizado = pacienteService.buscarPaciente(pacienteActualizado.getId());

        Assertions.assertEquals("Pedro", buscarPacienteActualizado.get().getNombre());
    }

    @Test
    @Order(5)
    public void buscarPacientePorEmailTest() {
        String emailABuscar = "email@gmail.com";
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorEmail(emailABuscar);

        Assertions.assertEquals("email@gmail.com",pacienteBuscado.get().getEmail());
    }

    @Test
    @Order(6)
    public void eliminarPacienteTest() {
        Long idAEliminar = 1L;
        pacienteService.eliminarPaciente(idAEliminar);
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(idAEliminar);

        Assertions.assertFalse(pacienteBuscado.isPresent());
    }

}