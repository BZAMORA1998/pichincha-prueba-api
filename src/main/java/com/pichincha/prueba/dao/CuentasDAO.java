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
import com.pichincha.prueba.model.Cuentas;

import lombok.NonNull;

@Service
public class CuentasDAO extends BaseDAO<Cuentas,Integer>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected CuentasDAO() {
		super(Cuentas.class);
	}

	@Override
	public void persist(Cuentas t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(Cuentas t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<Cuentas> find(@NonNull Integer id) {
		return super.find(id);
	}
	
	public List<CuentasDTO> consultarCuentas(String strEstado) {
	
		String[] strTiposEstado = { "ACTIVO", "INACTIVO", "TODOS" };
			
			try {
				StringBuilder strJPQLBase = new StringBuilder();
				strJPQLBase.append("select c.secuenciaCuenta as secuenciaCuenta, ");
				strJPQLBase.append(" 	   c.nombre as nombre, ");
				strJPQLBase.append(" 	   c.esActivo as estado ");
				strJPQLBase.append("from Cuentas c ");
				
				if(strTiposEstado[0].equalsIgnoreCase(strEstado))
					strJPQLBase.append("where c.esActivo='S' ");
				else if(strTiposEstado[1].equalsIgnoreCase(strEstado))
					strJPQLBase.append("where c.esActivo='N' ");
				
				strJPQLBase.append("ORDER BY nombre ");
				TypedQuery<Tuple> query = (TypedQuery<Tuple>) em.createQuery(strJPQLBase.toString(), Tuple.class);
	
				return query.getResultList().stream()
						.map(tuple -> CuentasDTO.builder()
						.secuenciaCuenta(tuple.get("secuenciaCuenta", Number.class).intValue())
						.nombre(tuple.get("nombre", String.class))
						.estado(tuple.get("estado")!=null && "S".equalsIgnoreCase(tuple.get("estado",String.class))?true:false)
						.build())
				.collect(Collectors.toList());
			} catch (NoResultException e) {
				return null;
			}
	}

	/**
	 * Consulta la cuenta por nombre
	 * @param nombre
	 * @return
	 */
	public Long consultarPorNombre(String nombre) {
		try {	
			return em.createQuery(
						"SELECT count(1) \n" +
						"  FROM Cuentas c \n" +
						"  WHERE upper(c.nombre)=upper(:nombre) ",Long.class)
						.setParameter("nombre",nombre)
						.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
