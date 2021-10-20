package com.pichincha.prueba.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pichincha.prueba.bo.ICuentaBO;
import com.pichincha.prueba.dto.CuentasDTO;
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
	 * 
	 * PUT http://localhost:8080/cuenta/agregarCuenta/1?secuenciasCuenta=3
	 * 
	 * Se debe mandar el pathParam secuenciaPersona (Obligatorio) 
	 * Se debe mandar el parametro secuenciasCuenta con arreglo de numeros ejemplo: 0,1,2,3,4 (Obligatorio)
	 * 
	 * 
	 * 2) Relaciona una o varias cuentas a una persona
	 * 
	 * @author Bryan Zamora
	 * @param strLanguage
	 * @param intSecuenciaPersona
	 * @param lsSecuenciasCuenta
	 * @return
	 * @throws BOException
	 */
	@RequestMapping(value="/agregarCuenta/{secuenciaPersona}",method = RequestMethod.PUT)
	public ResponseEntity<?> agregarCuentaPersona(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage, 
			@PathVariable(	value = "secuenciaPersona", required = false) Integer intSecuenciaPersona, 
			@RequestParam(	value = "secuenciasCuenta", required = false) List<Integer> lsSecuenciasCuenta 
			) throws BOException {
		
		try {
			
			objICuentaBO.agregarCuentaPersona(intSecuenciaPersona,lsSecuenciasCuenta);

			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("pru.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	/**
	 * POST http://localhost:8080/cuenta
	 * JSON BODY
//		{
//		    "nombre":"CUENTA 5",(requerido)
//		    "descripcion":"CUENTA 4"(requerido)
//		}
	 * 
	 * El api puede crear nuevas cuentas siempre y cuando no esten repetidas
	 * 
	 * 2)Crea una nueva cuenta
	 * @author Bryan Zamora
	 * @param strLanguage
	 * @param objPersonaDTO
	 * @return
	 * @throws BOException
	 */
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> crearCuenta(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@RequestBody CuentasDTO objCuentasDTO) throws BOException {
		
		try {
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("pru.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					objICuentaBO.crearCuenta(objCuentasDTO)), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	

	/**
	 * GET http://localhost:8080/cuenta?estado=todos
	 * 
	 * El api recibe el parametro estado requerido los valores pueden ser : 
	 * "ACTIVO", "INACTIVO", "TODOS"
	 * 
	 * 3) Recuperar todas las cuentas existentes
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
	
	/**
	 * PUT http://localhost:8080/cuenta/1
	 * JSON BODY
	//{
	//    "nombre":"CUENTA 1",(requerido)
	//    "descripcion":"CUENTA 1",(requerido)
	//    "estado":true (requerido)
	//}
	 * 
	 * El api puede actualizar una cuenta
	 * 
	 * 2)Actualizar información de la cuenta (un atributo y/o todos los atributos)
	 * @author Bryan Zamora
	 * @param strLanguage
	 * @param objPersonaDTO
	 * @return
	 * @throws BOException
	 */
	
	
	@RequestMapping(value="/{secuenciaCuenta}",method = RequestMethod.PUT)
	public ResponseEntity<?> actualizaCuenta(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@PathVariable(	value = "secuenciaCuenta", required = false) Integer intSecuenciaCuenta, 
			@RequestBody CuentasDTO objCuentasDTO) throws BOException {
		
		try {
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("pru.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					objICuentaBO.actualizaCuenta(intSecuenciaCuenta,objCuentasDTO)), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	/**
	 * GET http://localhost:8080/cuenta/1
	 * 
	 * Consulta las cuentas quw tiene una persona
	 * @param strLanguage
	 * @param intSecuenciaPersona
	 * @return
	 * @throws BOException
	 */
	@RequestMapping(value="/{secuenciaPersona}",method = RequestMethod.GET)
	public ResponseEntity<?> consultarCuentasPersona(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@PathVariable(	value = "secuenciaPersona", required = false) Integer intSecuenciaPersona
			) throws BOException {
		
		try {
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("pru.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					objICuentaBO.consultarCuentasPersona(intSecuenciaPersona)), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
}
