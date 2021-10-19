package com.pichincha.prueba.util;

import org.apache.commons.lang3.ObjectUtils;

import com.pichincha.prueba.exceptions.BOException;

public class GenericUtil {

	private GenericUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Valida campo requerido 
	 * @param <T>
	 * @param objCampoRequerido
	 * @param strNombreCampo
	 * @throws BOException
	 */
	public static <T> void validarCampoRequeridoBO(T objCampoRequerido, String strNombreCampo) throws BOException {
	 	
		if (ObjectUtils.isEmpty(objCampoRequerido)) {
			throw new BOException("pr.warn.campoObligatorio", new Object[] { objCampoRequerido });
		}
	}
	
	/**
	 * Valida canal requerido 
	 * @param <T>
	 * @param objCampoRequerido
	 * @param strNombreCampo
	 * @throws BOException
	 */
	public static <T> void validarCanalRequeridoBO(T objCampoRequerido, String strNombreCampo) throws BOException {
	 	
		if (ObjectUtils.isEmpty(strNombreCampo)) {
			throw new BOException("seg.warn.headerObligatorio", new Object[] { objCampoRequerido });
		}
	}
}
