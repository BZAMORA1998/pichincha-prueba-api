package com.pichincha.prueba.dao;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import com.pichincha.prueba.dto.CuentasDTO;
import com.pichincha.prueba.model.Personas;

import lombok.NonNull;

@Service
public class PersonasDAO extends BaseDAO<Personas,Integer>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected PersonasDAO() {
		super(Personas.class);
	}

	@Override
	public void persist(Personas t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(Personas t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<Personas> find(@NonNull Integer id) {
		return super.find(id);
	}
	
	/**
	 * Consulta por numero de identificacion
	 * 
	 * @Author: Bryan Zamora
	 * @Param numeroIdentificacion
	 * @Return
	 */
	public Long consultarPorIdentificacion(String numeroIdentificacion) {
		try {	
			return em.createQuery(
						"SELECT count(1) \n" +
						"  FROM Personas pe \n" +
						"  WHERE upper(pe.numeroIdentificacion)=upper(:numeroIdentificacion) ",Long.class)
						.setParameter("numeroIdentificacion",numeroIdentificacion)
						.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	/**
	 * @author Bryan Zamora
	 * Busca la persona por la cedula
	 * @param strCedula
	 * @return
	 */
	public Personas findByCedula(String strCedula) {
		try {	
			return em.createQuery(
						"SELECT p \n" +
						"  FROM Personas p \n" +
						"  WHERE upper(p.numeroIdentificacion)=upper(:cedula)",Personas.class)
						.setParameter("cedula",strCedula)
						.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
