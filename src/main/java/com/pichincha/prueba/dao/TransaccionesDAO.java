package com.pichincha.prueba.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Service;

import com.pichincha.prueba.model.Transacciones;

import lombok.NonNull;

@Service
public class TransaccionesDAO extends BaseDAO<Transacciones,Integer>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected TransaccionesDAO() {
		super(Transacciones.class);
	}

	@Override
	public void persist(Transacciones t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(Transacciones t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<Transacciones> find(@NonNull Integer id) {
		return super.find(id);
	}
}
