package com.example.proyectointegradorinnamorato.controller;

import com.example.proyectointegradorinnamorato.entity.Odontologo;
import com.example.proyectointegradorinnamorato.entity.Paciente;
import com.example.proyectointegradorinnamorato.exception.ResourceNotFoundException;
import com.example.proyectointegradorinnamorato.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private OdontologoService odontologoService;

    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping
    public ResponseEntity<Odontologo> registrarNuevoOdontologo(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }


    @GetMapping
    public ResponseEntity<List<Odontologo>> listarOdontologos() {
        return ResponseEntity.ok(odontologoService.listarOdontologos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologo(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(id);

        if (odontologoBuscado.isPresent()) {
            return ResponseEntity.ok(odontologoBuscado.get());
        }
        throw new ResourceNotFoundException("No existe un odontologo con id = " + id + " en la base de datos.");
    }


    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(odontologo.getId());

        if (odontologoBuscado.isPresent()) {
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("Se actualizó al odontologo con id = " + odontologo.getId());
        }
        throw new ResourceNotFoundException("No existe un odontologo con id = " + odontologo.getId() + " en la base de datos.");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException{
        if (odontologoService.buscarOdontologo(id)!=null){
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok().body("Se eliminó al odontologo con id = " + id);
        }
        throw new ResourceNotFoundException("No existe un odontologo con id = " + id + " en la base de datos.");
    }

}
