package com.pichincha.prueba.bo;

import com.pichincha.prueba.dto.PersonaDTO;
import com.pichincha.prueba.exceptions.BOException;

public interface IPersonaBO {

	/**
	 * Crea o actualiza una persona
	 * @author Bryan Zamora
	 * @param objPersonaDTO
	 */
	public void crearOActualizaPersona(PersonaDTO objPersonaDTO) throws BOException;

}
