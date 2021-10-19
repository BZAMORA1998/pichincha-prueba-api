package com.pichincha.prueba.bo;

import com.pichincha.prueba.dto.PersonaDTO;
import com.pichincha.prueba.exceptions.BOException;

public interface IPersonaBO {

	/**
	 * Crea una persona
	 * @author Bryan Zamora
	 * @param objPersonaDTO
	 */
	public void crearPersona(PersonaDTO objPersonaDTO) throws BOException;

}
