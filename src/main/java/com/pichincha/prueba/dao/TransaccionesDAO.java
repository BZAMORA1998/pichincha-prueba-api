package com.pichincha.prueba.dao;

import java.util.Date;
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

import com.pichincha.prueba.dto.TransaccionDTO;
import com.pichincha.prueba.enums.FormatoFecha;
import com.pichincha.prueba.model.Transacciones;
import com.pichincha.prueba.util.FechasUtil;

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
	
	@Override
	public void remove(Transacciones id) {
		super.remove(id);
	}

	/**
	 * Consulta las transacciones en un rango de fecha 
	 * 
	 * @param datFechaInicio
	 * @param datFechaFin
	 * @param intSecuenciaCuenta
	 * @return
	 */
	public List<TransaccionDTO> consultarTransacciones(Date datFechaInicio, Date datFechaFin,
			Integer intSecuenciaCuenta) {
		try {
			StringBuilder strJPQLBase = new StringBuilder();
			strJPQLBase.append("select t.secuenciaTransaccion as secuenciaTransaccion, ");
			strJPQLBase.append(" 	   t.descripcion as descripcion, ");
			strJPQLBase.append(" 	   t.valorTotal as valorTotal, ");
			strJPQLBase.append(" 	   t.fechaIngreso as fechaIngreso, ");
			strJPQLBase.append(" 	   t.esActivo as esActivo ");
			strJPQLBase.append("from Transacciones t ");
			strJPQLBase.append("	JOIN t.cuentaXPersonas cp ");
			strJPQLBase.append("	JOIN cp.cuentas c ");
			strJPQLBase.append("where c.secuenciaCuenta=:secuenciaCuenta ");
			strJPQLBase.append("and   t.fechaIngreso between :fechaInicio and :fechaFin ");
			strJPQLBase.append("and   t.esActivo='S' ");
			strJPQLBase.append("and   cp.esActivo='S' ");
			strJPQLBase.append("ORDER BY descripcion ");
			TypedQuery<Tuple> query = (TypedQuery<Tuple>) em.createQuery(strJPQLBase.toString(), Tuple.class);
			query.setParameter("secuenciaCuenta",intSecuenciaCuenta);
			query.setParameter("fechaInicio",datFechaInicio);
			query.setParameter("fechaFin",datFechaFin);
			
			return query.getResultList().stream()
					.map(tuple -> TransaccionDTO.builder()
					.secuenciaTransaccion(tuple.get("secuenciaTransaccion", Number.class).intValue())
					.descripcion(tuple.get("descripcion", String.class))
					.valorTotal(tuple.get("valorTotal", Number.class).doubleValue())
					.fechaIngreso(FechasUtil.dateToString( tuple.get("fechaIngreso",Date.class),FormatoFecha.YYYY_MM_DD_HH_MM_SS))
					.build())
			.collect(Collectors.toList());
		} catch (NoResultException e) {
			return null;
		}
	}
}
