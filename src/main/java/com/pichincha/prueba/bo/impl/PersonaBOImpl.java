package com.pichincha.prueba.bo.impl;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pichincha.prueba.bo.IPersonaBO;
import com.pichincha.prueba.dao.GenerosDAO;
import com.pichincha.prueba.dao.PersonasDAO;
import com.pichincha.prueba.dao.TiposIdentificacionDAO;
import com.pichincha.prueba.dto.PersonaDTO;
import com.pichincha.prueba.enums.AlgoritmosIdentificacion;
import com.pichincha.prueba.enums.TipoIdentificacion;
import com.pichincha.prueba.exceptions.BOException;
import com.pichincha.prueba.model.Generos;
import com.pichincha.prueba.model.Personas;
import com.pichincha.prueba.model.TiposIdentificacion;
import com.pichincha.prueba.util.GenericUtil;
import com.pichincha.prueba.util.IdentificacionUtil;
import com.pichincha.prueba.util.StringUtil;

@Service
public class PersonaBOImpl implements IPersonaBO{
	
	@Autowired
	private TiposIdentificacionDAO objTiposIdentificacionDAO;
	@Autowired
	private GenerosDAO objGenerosDAO;
	@Autowired
	private PersonasDAO objPersonasDAO;
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class})
	public void crearOActualizaPersona(PersonaDTO objPersonaDTO) throws BOException {
		
		//Si la secuencia de la persona es null o vacio se procede a crear la persona 
		//caso contrario se asume que es una actualizacion
		Optional<Personas> objPersona=null;
		if(ObjectUtils.isEmpty(objPersonaDTO.getSecuenciaPersona())) {
			
			objPersona=Optional.of(new Personas());
			
			//Se registra las fecha que se ingresa la persona
			objPersona.get().setFechaIngreso(new Date());
			//La persona se activa por default
			objPersona.get().setEsActivo("S");
			
		}else {
			objPersona=objPersonasDAO.find(objPersonaDTO.getSecuenciaPersona());
			
			//Valida que el tipo de identificacion exista
			if(!objPersona.isPresent()) 
				throw new BOException("pru.warn.campoNoExiste", new Object[] { "pru.campos.secuenciaPersona"});
			
			//Valida que el campo esActivo sea requerido
			GenericUtil.validarCampoRequeridoBO(objPersonaDTO.getEsActivo(), "pru.campos.esActivo");
			
			//Se registra las fecha que se actualiza la persona
			objPersona.get().setFechaModificacion(new Date());
			
			objPersona.get().setEsActivo(objPersonaDTO.getEsActivo()?"S":"N");
		}
		
		//---------------Valida los nombres y apellidos------------------------------
		//Primer Nombre
		//Valida que el primer nombre sea requerido
		GenericUtil.validarCampoRequeridoBO(objPersonaDTO.getPrimerNombre(), "pru.campos.primerNombre");
		//Valida que el primer nombre solo tenga letras y espacios
		if(!StringUtil.soloLetrasYEspacio(objPersonaDTO.getPrimerNombre()))
			throw new BOException("pru.warn.campoSoloLetrasEspacios", new Object[] { "pru.campos.primerNombre"});
		
		objPersona.get().setPrimerNombre(objPersonaDTO.getPrimerNombre().toUpperCase());

		//Segundo nombre (opcional)
		//Valida que el segundo nombre solo tenga letras y espacios
		if(!ObjectUtils.isEmpty(objPersonaDTO.getSegundoNombre())) {
			
			if(!StringUtil.soloLetrasYEspacio(objPersonaDTO.getSegundoNombre()))
				throw new BOException("pru.warn.campoSoloLetrasEspacios", new Object[] { "pru.campos.segundoNombre"});
			
			objPersona.get().setSegundoNombre(objPersonaDTO.getSegundoNombre().toUpperCase());
			
		}else {
			objPersona.get().setSegundoNombre(null);
		}
			
		//Primer Apellido
		//Valida que el primer Apellido sea requerido
		GenericUtil.validarCampoRequeridoBO(objPersonaDTO.getPrimerApellido(), "pru.campos.primerApellido");
		//Valida que el primer apellido solo tenga letras y espacios
		if(!StringUtil.soloLetrasYEspacio(objPersonaDTO.getPrimerApellido()))
			throw new BOException("pru.warn.campoSoloLetrasEspacios", new Object[] { "pru.campos.primerApellido"});
		
		objPersona.get().setPrimerApellido(objPersonaDTO.getPrimerApellido().toUpperCase());
		
		
		//Segundo apellido (opcional)
		//Valida que el segundo nombre solo tenga letras y espacios
		if(!ObjectUtils.isEmpty(objPersonaDTO.getSegundoApellido())) {
			
			if(!StringUtil.soloLetrasYEspacio(objPersonaDTO.getSegundoApellido()))
				throw new BOException("pru.warn.campoSoloLetrasEspacios", new Object[] { "pru.campos.segundoApellido"});
			
			objPersona.get().setSegundoApellido(objPersonaDTO.getSegundoApellido().toUpperCase());
			
		}else {
			objPersona.get().setSegundoApellido(null);
		}
			
			
		//-------------------------Valida la identificacion------------------------------
		//Secuencia tipo identificacion
		//Valida que la secuancia tipo de identificacion sea requerido
		GenericUtil.validarCampoRequeridoBO(objPersonaDTO.getSecuenciaTipoIdentificacion(), "pru.campos.secuenciaTipoIdentificacion");
	
		//Busca el tipo de identificacion
		Optional<TiposIdentificacion> objTiposIdentificacion=objTiposIdentificacionDAO.find(objPersonaDTO.getSecuenciaTipoIdentificacion());
		
		//Valida que el tipo de identificacion exista
		if(!objTiposIdentificacion.isPresent()) 
			throw new BOException("pru.warn.campoNoExiste", new Object[] { "pru.campos.secuenciaTipoIdentificacion"});
		
		//Valida que el tipo de identificacion este activo
		if(!("S").equalsIgnoreCase(objTiposIdentificacion.get().getEsActivo())) 
			throw new BOException("pru.warn.campoInactivo", new Object[] { "pru.campos.secuenciaTipoIdentificacion"});
		
		//---------------------------Valida el numero de identificacion-----------------------------------------------------------
		
		// Valida que el numero de identificacion sea obligatoria
		GenericUtil.validarCampoRequeridoBO(objPersonaDTO.getNumeroIdentificacion(), "pru.campos.numeroIdentificacion");
		
		Boolean booNumeroIdentificacion=false;
		//Valida el tipo de indeificacion segun el formato
		if(TipoIdentificacion.CEDULA.getValor().equals(objPersonaDTO.getSecuenciaTipoIdentificacion())) 
			booNumeroIdentificacion=IdentificacionUtil.validaAlgoritmoIdentificacion(objPersonaDTO.getNumeroIdentificacion(), AlgoritmosIdentificacion.CEDULA_IDENTIDAD_EC.getName());
		else if(TipoIdentificacion.RUC.getValor().equals(objPersonaDTO.getSecuenciaTipoIdentificacion())) 
			booNumeroIdentificacion=IdentificacionUtil.validaAlgoritmoIdentificacion(objPersonaDTO.getNumeroIdentificacion(), AlgoritmosIdentificacion.REGISTRO_UNICO_CONTRIBUYENTE_EC.getName());
		else  if(TipoIdentificacion.PASAPORTE.getValor().equals(objPersonaDTO.getSecuenciaTipoIdentificacion())) 
			booNumeroIdentificacion=true;
		
		//Valida el tipo de indeificacion segun el formato
		if(!booNumeroIdentificacion) 
			throw new BOException("pru.warn.numeroIdentificacionInvalida");
		
		//Valida que el numero de identificacion no exista
		if(ObjectUtils.isEmpty(objPersonaDTO.getSecuenciaPersona()) 
				|| !objPersonaDTO.getNumeroIdentificacion().equalsIgnoreCase(objPersonaDTO.getNumeroIdentificacion())) {
			Long longCountId=objPersonasDAO.consultarPorIdentificacion(objPersonaDTO.getNumeroIdentificacion());
			
			if(longCountId.intValue()>0) 
				throw new BOException("pru.warn.numeroIdentificacionExiste");
			
		}
		
		objPersona.get().setNumeroIdentificacion(objPersonaDTO.getNumeroIdentificacion().toUpperCase());
		objPersona.get().setTiposIdentificacion(objTiposIdentificacion.get());
		
		//--------------------------Valida el genero-----------------------------------------------
				
		// Valida que la secuencia genero sea obligatorio
		GenericUtil.validarCampoRequeridoBO(objPersonaDTO.getSecuenciaGenero(), "pru.campos.secuenciaGenero");
		
		//Busca el genero
		Optional<Generos> objGenero=objGenerosDAO.find(objPersonaDTO.getSecuenciaGenero());
		
		//Valida que exista el genero
		if(!objGenero.isPresent()) 
			throw new BOException("pru.warn.campoNoExiste", new Object[] { "pru.campos.secuenciaGenero"});
		
		//Valida que este activo el genero
		if(!("S").equalsIgnoreCase(objGenero.get().getEsActivo())) 
			throw new BOException("pru.warn.campoInactivo", new Object[] { "pru.campos.secuenciaGenero"});
		
		objPersona.get().setGenero(objGenero.get());
		
		//------------------------------------------------------------------------------------------------

		//Transacion
		if(ObjectUtils.isEmpty(objPersonaDTO.getSecuenciaPersona())) {
			objPersonasDAO.persist(objPersona.get());
		}else {
			objPersonasDAO.update(objPersona.get());
		}

	}

}
