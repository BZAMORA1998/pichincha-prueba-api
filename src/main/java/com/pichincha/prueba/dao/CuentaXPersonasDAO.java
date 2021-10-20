package com.pichincha.prueba.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Service;

import com.pichincha.prueba.model.CuentaXPersonas;
import com.pichincha.prueba.model.CuentaXPersonasCPK;

import lombok.NonNull;

@Service
public class CuentaXPersonasDAO extends BaseDAO<CuentaXPersonas,CuentaXPersonasCPK>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected CuentaXPersonasDAO() {
		super(CuentaXPersonas.class);
	}

	@Override
	public void persist(CuentaXPersonas t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(CuentaXPersonas t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<CuentaXPersonas> find(@NonNull CuentaXPersonasCPK id) {
		return super.find(id);
	}
}
