package com.pichincha.prueba.bo;

import java.util.List;

import com.pichincha.prueba.dto.CuentasDTO;
import com.pichincha.prueba.exceptions.BOException;

public interface ICuentaBO {

	/**
	 * @author Bryan Zamora
	 * @param strEstado
	 * @return
	 * @throws BOException
	 */
	public List<CuentasDTO> consultarCuentas(String strEstado) throws BOException;

}
