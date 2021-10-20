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

}
