package com.pichincha.prueba.util;

import org.apache.commons.lang3.ObjectUtils;

import com.pichincha.prueba.exceptions.BOException;

public class GenericUtil {

	/**
	 * Valida campo requerido 
	 * @param <T>
	 * @param objCampoRequerido
	 * @param strNombreCampo
	 * @throws BOException
	 */
	public static <T> void validarCampoRequeridoBO(T objCampoRequerido, String strNombreCampo) throws BOException {
	 	
		if (ObjectUtils.isEmpty(objCampoRequerido)) {
			throw new BOException("pru.warn.campoObligatorio", new Object[] { strNombreCampo });
		}
	}
}
