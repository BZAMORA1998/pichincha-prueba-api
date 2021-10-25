package com.pichincha.prueba.bo;

import java.util.Map;

import com.pichincha.prueba.dto.PersonaDTO;
import com.pichincha.prueba.exceptions.BOException;

public interface IPersonaBO {

	/**
	 * Crea o actualiza una persona
	 * @author Bryan Zamora
	 * @param objPersonaDTO
	 * @return 
	 */
	public Map<String, Object> crearOActualizaPersona(PersonaDTO objPersonaDTO) throws BOException;

	/**
	 * Retonara los datos de la persona por su numero de cedula
	 * 
	 * @author Bryan Zamora
	 * 
	 * @param strNumeroIdentificacion
	 * @return
	 */
	public PersonaDTO consultarDatosPersonas(String strNumeroIdentificacion) throws BOException;


}
