package com.pichincha.prueba.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pichincha.prueba.bo.IPersonaBO;
import com.pichincha.prueba.dto.PersonaDTO;
import com.pichincha.prueba.dto.ResponseOk;
import com.pichincha.prueba.exceptions.BOException;
import com.pichincha.prueba.exceptions.CustomExceptionHandler;
import com.pichincha.prueba.util.MensajesUtil;

public class PersonaApi {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(PersonaApi.class.getName());
	
	@Autowired
	private IPersonaBO objIPersonaBO;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> crearPersona(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage, 
			@RequestBody PersonaDTO objPersonaDTO
			) throws BOException {
		
		try {
			
			objIPersonaBO.crearPersona(objPersonaDTO);

			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("pru.response.usuarioCreado", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
}
