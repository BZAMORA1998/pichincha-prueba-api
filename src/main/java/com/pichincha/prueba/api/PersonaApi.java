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

import com.pichincha.prueba.bo.IPersonaBO;
import com.pichincha.prueba.dto.PersonaDTO;
import com.pichincha.prueba.dto.ResponseOk;
import com.pichincha.prueba.exceptions.BOException;
import com.pichincha.prueba.exceptions.CustomExceptionHandler;
import com.pichincha.prueba.util.MensajesUtil;

@RestController
@RequestMapping("/persona")
public class PersonaApi {
	
	@Autowired
	private IPersonaBO objIPersonaBO;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(PersonaApi.class.getName());
	
	/**
	 * Crear y actualizar personas
	 * @param strLanguage
	 * @param objPersonaDTO
	 * @return
	 * @throws BOException
	 */
	
	/*PUT http://localhost:8080/persona
	 JSON BODY
	  {
	    "secuenciaPersona":2,  --Solo se envia si se va actualizar
	    "primerNombre":"BRYAN",
	    "segundoNombre":"STEVEN",--opcional
	    "primerApellido":"ZAMORA",
	    "segundoApellido":"LITARDO",--opcional
	    "secuenciaTipoIdentificacion":1,
	    "numeroIdentificacion":"0928914464",
	    "secuenciaGenero":1,
	    "esActivo":true --solo se envia si se va actualizar
	}*/
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> crearOActualizaPersona(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage, 
			@RequestBody PersonaDTO objPersonaDTO
			) throws BOException {
		
		try {
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("pru.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					objIPersonaBO.crearOActualizaPersona(objPersonaDTO)), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> consultarDatosPersonas(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage, 
			@RequestParam(	value = "numeroIdentificacion", 	required = false) String strNumeroIdentificacion
			) throws BOException {
		
		try {
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("pru.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					objIPersonaBO.consultarDatosPersonas(strNumeroIdentificacion))
					, HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
}
