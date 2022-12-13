package com.example.proyectointegradorinnamorato.service;

import com.example.proyectointegradorinnamorato.entity.Odontologo;
import com.example.proyectointegradorinnamorato.repository.OdontologoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {

    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);
    private OdontologoRepository odontologoRepository;
    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo) {
        LOGGER.info("Iniciando el proceso de guardar un odontologo con apellido " + odontologo.getApellido());
        return odontologoRepository.save(odontologo);
        //Se usa para registrar y para actualizar
    }

    public Optional<Odontologo> buscarOdontologo(Long id) {
        LOGGER.info("Iniciando el proceso de busqueda del odontologo con id = " + id);
        return odontologoRepository.findById(id);
    }

    public List<Odontologo> listarOdontologos() {
        LOGGER.info("Iniciando el proceso de busqueda de todos los odontologos");
        return odontologoRepository.findAll();
    }

    public void actualizarOdontologo(Odontologo odontologo) {
        LOGGER.info("Iniciando el proceso de actualización del odontologo con id = " + odontologo.getId());
        odontologoRepository.save(odontologo);
    }

    public void eliminarOdontologo(Long id) {
        odontologoRepository.deleteById(id);
        LOGGER.warn("Se realizó el proceso de eliminación del odontologo con id = " + id);
    }


}
