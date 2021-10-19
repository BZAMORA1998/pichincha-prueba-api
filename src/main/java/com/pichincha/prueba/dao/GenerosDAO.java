package com.pichincha.prueba.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Service;

import com.pichincha.prueba.model.Generos;

import lombok.NonNull;

@Service
public class GenerosDAO extends BaseDAO<Generos,Integer>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected GenerosDAO() {
		super(Generos.class);
	}

	@Override
	public void persist(Generos t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(Generos t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<Generos> find(@NonNull Integer id) {
		return super.find(id);
	}
}
