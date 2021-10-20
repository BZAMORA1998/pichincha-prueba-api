package com.pichincha.prueba.api;

import java.util.List;

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

import com.pichincha.prueba.bo.ITransaccionBO;
import com.pichincha.prueba.dto.ResponseOk;
import com.pichincha.prueba.dto.TransaccionDTO;
import com.pichincha.prueba.exceptions.BOException;
import com.pichincha.prueba.exceptions.CustomExceptionHandler;
import com.pichincha.prueba.util.MensajesUtil;

@RestController
@RequestMapping("/transaccion")
public class TransaccionApi {
	
	@Autowired
	private ITransaccionBO objITransaccionBO;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(TransaccionApi.class.getName());
	
	
	/**
	 * http://localhost:8080/transaccion?secuenciaCuenta=1&secuenciaPersona=1
	 * 
	 * El api crea transacciones asociada a una cuenta y a una persona
	 * 
	 * JSONBODY
//	 [
//    {
//        "descripcion":"AGUA, COLA Y JUGO",(requerido)
//        "valorTotal":22.5 (requerido)
//    },
//    {
//        "descripcion":"HELADO",
//        "valorTotal":50
//    }
//	]
	 
	 * 5) Crea una o varias transacciones a una cuenta
	 * 
	 * @author Bryan Zamora
	 * @param strLanguage
	 * @param intSecuenciaPersona
	 * @param intSecuenciaCuenta
	 * @param lsTransaccionDTO
	 * @return
	 * @throws BOException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> crearTransaccion(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage, 
			@RequestParam(	value = "secuenciaPersona", required = false) Integer intSecuenciaPersona, 
			@RequestParam(	value = "secuenciaCuenta", required = false)Integer intSecuenciaCuenta ,
			@RequestBody List<TransaccionDTO> lsTransaccionDTO 
			) throws BOException {
		
		try {
			
			objITransaccionBO.crearTransaccion(intSecuenciaPersona,intSecuenciaCuenta,lsTransaccionDTO);

			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("pru.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
}
