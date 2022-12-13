package com.example.proyectointegradorinnamorato.service;

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
class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologoTest() {
        Odontologo odontologoAGuardar = new Odontologo("sdh2523","Nombre","Apellido");
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologoAGuardar);

        Assertions.assertEquals(1L, odontologoGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarOdontologoPorIdTest() {
        Long idABuscar = 1L;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(idABuscar);

        Assertions.assertNotNull(odontologoBuscado.get());
    }

    @Test
    @Order(3)
    public void buscarOdontologosTest() {
        List<Odontologo> odontologosBuscados = odontologoService.listarOdontologos();
        Integer cantidadEsperada = 1;

        Assertions.assertEquals(cantidadEsperada, odontologosBuscados.size());
    }

    @Test
    @Order(4)
    public void actualizarOdontologoTest() {
        Odontologo odontologoActualizado= new Odontologo(1L,"sdh2523","Jose","Apellido");
        odontologoService.actualizarOdontologo(odontologoActualizado);
        Optional<Odontologo> buscarOdontologoActualizado = odontologoService.buscarOdontologo(odontologoActualizado.getId());

        Assertions.assertEquals("Jose", buscarOdontologoActualizado.get().getNombre());
    }

    @Test
    @Order(5)
    public void eliminarOdontologoTest() {
        Long idAEliminar = 1L;
        odontologoService.eliminarOdontologo(idAEliminar);
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(idAEliminar);

        Assertions.assertFalse(odontologoBuscado.isPresent());
    }

}