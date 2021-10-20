package com.pichincha.prueba.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pichincha.prueba.bo.ICuentaBO;
import com.pichincha.prueba.bo.IPersonaBO;
import com.pichincha.prueba.dto.PersonaDTO;
import com.pichincha.prueba.dto.ResponseOk;
import com.pichincha.prueba.exceptions.BOException;
import com.pichincha.prueba.exceptions.CustomExceptionHandler;
import com.pichincha.prueba.util.MensajesUtil;

@RestController
@RequestMapping("/cuenta")
public class CuentaApi {
	
	@Autowired
	private ICuentaBO objICuentaBO;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CuentaApi.class.getName());
	

	/**
	 * http://localhost:8080/cuenta?estado=todos
	 * 
	 * El api recibe el parametro estado requerido los valores pueden ser : 
	 * "ACTIVO", "INACTIVO", "TODOS"
	 * 
	 * 2) Recuperar todas las cuentas existentes
	 * @author Bryan Zamora
	 * @param strLanguage
	 * @param objPersonaDTO
	 * @return
	 * @throws BOException
	 */
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> consultarCuentas(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@RequestParam(value = "estado", required = false) String strEstado) throws BOException {
		
		try {
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("pru.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					objICuentaBO.consultarCuentas(strEstado)), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
}
