package com.pichincha.prueba.bo;

import java.util.List;

import com.pichincha.prueba.dto.TransaccionDTO;
import com.pichincha.prueba.exceptions.BOException;

public interface ITransaccionBO {

	/**
	 * Crea una nueva transaccion
	 * 
	 * @author Bryan Zamora
	 * @param intSecuenciaPersona
	 * @param intSecuenciasCuenta
	 * @param lsTransaccionDTO
	 */
	public void crearTransaccion(Integer intSecuenciaPersona, Integer intSecuenciasCuenta,
			List<TransaccionDTO> lsTransaccionDTO)throws BOException;

	/**
	 * Consulta las transacciones por cuenta
	 * 
	 * @author Bryan Zamora
	 * @param strFechaInicio
	 * @param strFechaFin
	 * @param intSecuenciaCuenta
	 */
	public List<TransaccionDTO> consultarTransacciones(String strFechaInicio, String strFechaFin, Integer intSecuenciaCuenta)throws BOException;

	/**
	 * Elimina una transaccion existente
	 * 
	 * @author Bryan Zamora
	 * @param intSecuenciaTransaccion
	 * @return
	 */
	public void eliminarTransaccion(Integer intSecuenciaTransaccion)throws BOException;

}
