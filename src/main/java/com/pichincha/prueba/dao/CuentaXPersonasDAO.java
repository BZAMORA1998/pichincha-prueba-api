package com.pichincha.prueba.dao;

import java.util.List;
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
	
	/**
	 * Consulta las cuentas que tiene activa una persona
	 * 
	 * @author Bryan Zamora
	 * @param intSecuenciaPersona
	 * @return
	 */
	public List<CuentasDTO> consultarCuentasPersona(Integer intSecuenciaPersona) {
		
			try {
				StringBuilder strJPQLBase = new StringBuilder();
				strJPQLBase.append("select c.secuenciaCuenta as secuenciaCuenta, ");
				strJPQLBase.append(" 	   c.nombre as nombre, ");
				strJPQLBase.append(" 	   c.descripcion as descripcion, ");
				strJPQLBase.append(" 	   c.esActivo as estado ");
				strJPQLBase.append("from CuentaXPersonas cp ");
				strJPQLBase.append("	JOIN cp.cuentas c ");
				strJPQLBase.append("	JOIN cp.personas pe ");
				strJPQLBase.append("where pe.secuenciaPersona=:secuenciaPersona ");
				strJPQLBase.append("and cp.esActivo='S' ");	
				strJPQLBase.append("ORDER BY nombre ");
				TypedQuery<Tuple> query = (TypedQuery<Tuple>) em.createQuery(strJPQLBase.toString(), Tuple.class);
				query.setParameter("secuenciaPersona",intSecuenciaPersona);
				
				return query.getResultList().stream()
						.map(tuple -> CuentasDTO.builder()
						.secuenciaCuenta(tuple.get("secuenciaCuenta", Number.class).intValue())
						.nombre(tuple.get("nombre", String.class))
						.descripcion(tuple.get("descripcion", String.class))
						.estado(tuple.get("estado")!=null && "S".equalsIgnoreCase(tuple.get("estado",String.class))?true:false)
						.build())
				.collect(Collectors.toList());
			} catch (NoResultException e) {
				return null;
			}
	}
}
