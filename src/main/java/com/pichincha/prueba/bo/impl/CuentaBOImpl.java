package com.pichincha.prueba.bo.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.prueba.bo.ICuentaBO;
import com.pichincha.prueba.dao.CuentasDAO;
import com.pichincha.prueba.dto.CuentasDTO;
import com.pichincha.prueba.exceptions.BOException;
import com.pichincha.prueba.util.GenericUtil;

@Service
public class CuentaBOImpl implements ICuentaBO{

	@Autowired
	private CuentasDAO objCuentasDAO;
	
	@Override
	public List<CuentasDTO> consultarCuentas(String strEstado) throws BOException {
		
		//Valida que el primer nombre sea requerido
		GenericUtil.validarCampoRequeridoBO(strEstado, "pru.campos.estado");
				
		
		String[] strTiposEstado = { "ACTIVO", "INACTIVO", "TODOS" };
		// VALIDA PRESTACION
		if (!Arrays.stream(strTiposEstado).anyMatch(StringUtils.upperCase(strEstado)::equals)) {
			throw new BOException("pru.warn.paramNoValidEstado");
		}
		
		return objCuentasDAO.consultarCuentas(strEstado);
	}
	
}
