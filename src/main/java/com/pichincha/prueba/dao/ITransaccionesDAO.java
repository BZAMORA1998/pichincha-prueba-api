package com.pichincha.prueba.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pichincha.prueba.model.Transacciones;

@Repository
public interface ITransaccionesDAO extends JpaRepository<Transacciones, Integer>{

}
