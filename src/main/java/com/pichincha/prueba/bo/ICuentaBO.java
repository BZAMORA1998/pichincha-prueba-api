package com.pichincha.prueba.bo;

import java.util.List;
import java.util.Map;

import com.pichincha.prueba.dto.CuentasDTO;
import com.pichincha.prueba.exceptions.BOException;

public interface ICuentaBO {

	/**
	 * Consulta las cuentas en general.
	 * 
	 * @author Bryan Zamora
	 * @param strEstado
	 * @return
	 * @throws BOException
	 */
	public List<CuentasDTO> consultarCuentas(String strEstado) throws BOException;

	/**
	 * Agrega las cuentas a la persona
	 * 
	 * @author Bryan Zamora 
	 * @param intSecuenciaPersona
	 * @param lsSecuenciasCuenta
	 * @throws BOException 
	 */
	public void agregarCuentaPersona(Integer intSecuenciaPersona, List<Integer> lsSecuenciasCuenta) throws BOException;

	/**
	 * Crear nueva cuenta
	 * 
	 * @author Bryan Zamora
	 * @param objCuentasDTO
	 * @return
	 * @throws BOException
	 */
	public Map<String,Object> crearCuenta(CuentasDTO objCuentasDTO) throws BOException;

	/**
	 * 
	 * Actualiza la cuenta
	 * 
	 * @author Bryan Zamora
	 * @param intSecuenciaCuenta
	 * @param objCuentasDTO
	 * @return
	 * @throws BOException 
	 */
	public Map<String,Object> actualizaCuenta(Integer intSecuenciaCuenta, CuentasDTO objCuentasDTO) throws BOException;

}
