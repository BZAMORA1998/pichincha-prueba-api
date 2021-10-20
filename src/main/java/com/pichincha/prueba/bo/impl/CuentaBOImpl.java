package com.pichincha.prueba.bo.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pichincha.prueba.bo.ICuentaBO;
import com.pichincha.prueba.dao.CuentaXPersonasDAO;
import com.pichincha.prueba.dao.CuentasDAO;
import com.pichincha.prueba.dao.PersonasDAO;
import com.pichincha.prueba.dto.CuentasDTO;
import com.pichincha.prueba.exceptions.BOException;
import com.pichincha.prueba.model.CuentaXPersonas;
import com.pichincha.prueba.model.CuentaXPersonasCPK;
import com.pichincha.prueba.model.Cuentas;
import com.pichincha.prueba.model.Personas;
import com.pichincha.prueba.util.GenericUtil;

@Service
public class CuentaBOImpl implements ICuentaBO{

	@Autowired
	private CuentasDAO objCuentasDAO;
	@Autowired
	private PersonasDAO objPersonasDAO;
	@Autowired
	private CuentaXPersonasDAO objCuentaXPersonasDAO;
	
	@Override
	public List<CuentasDTO> consultarCuentas(String strEstado) throws BOException {
		
		//Valida que el primer nombre sea requerido
		GenericUtil.validarCampoRequeridoBO(strEstado, "pru.campos.estado");
				
		
		String[] strTiposEstado = { "ACTIVO", "INACTIVO", "TODOS" };
		// VALIDA PRESTACION
		if (!Arrays.stream(strTiposEstado).anyMatch(StringUtils.upperCase(strEstado)::equals)) {
			throw new BOException("pru.warn.paramNoValidEstado");
		}
		
		return objCuentasDAO.consultarCuentas(strEstado);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class})
	public void agregarCuentaPersona(Integer intSecuenciaPersona, List<Integer> lsSecuenciasCuenta) throws BOException {
		
		//Valida que el primer nombre sea requerido
		GenericUtil.validarCampoRequeridoBO(intSecuenciaPersona, "pru.campos.secuenciaPersona");
		
		//Valida que el primer nombre sea requerido
		GenericUtil.validarCampoRequeridoBO(lsSecuenciasCuenta, "pru.campos.secuenciasCuentas");
		
		//Busca la persona
		Optional<Personas> objPersonas=objPersonasDAO.find(intSecuenciaPersona);
		
		//Valida que el tipo de identificacion exista
		if(!objPersonas.isPresent()) 
			throw new BOException("pru.warn.campoNoExiste", new Object[] { "pru.campos.secuenciaPersona"});
		
		//Valida que el tipo de identificacion este activo
		if(!("S").equalsIgnoreCase(objPersonas.get().getEsActivo())) 
			throw new BOException("pru.warn.campoInactivo", new Object[] { "pru.campos.secuenciaPersona"});
		
		Optional<CuentaXPersonas> objCuentaXPersonas=null;
		Optional<Cuentas> objCuentas =null;
		Date datFechaActual=new Date();
		for(Integer intSecuenciasCuenta:lsSecuenciasCuenta) {
			
			//Busca la cuenta
			objCuentas=objCuentasDAO.find(intSecuenciasCuenta);
			
			//Valida que la cuenta exista
			if(!objCuentas.isPresent()) 
				throw new BOException("pru.warn.campoNoExiste", new Object[] { "pru.campos.secuenciaCuenta"});
			
			//Valida que la cuenta este activo
			if(!("S").equalsIgnoreCase(objCuentas.get().getEsActivo())) 
				throw new BOException("pru.warn.campoInactivo", new Object[] { "pru.campos.secuenciaCuenta"});
			
			objCuentaXPersonas=objCuentaXPersonasDAO.find(new CuentaXPersonasCPK(intSecuenciasCuenta,intSecuenciaPersona));
		
			//Si no existe se lo agrega
			if(!objCuentaXPersonas.isPresent()) {
				objCuentaXPersonas=Optional.of(new CuentaXPersonas());
				objCuentaXPersonas.get().setCuentaXPersonasCPK(new CuentaXPersonasCPK(intSecuenciasCuenta,intSecuenciaPersona));
				objCuentaXPersonas.get().setFechaIngreso(datFechaActual);
				objCuentaXPersonas.get().setEsActivo("S");
				objCuentaXPersonasDAO.persist(objCuentaXPersonas.get());
			}else {
				//Si esta inactivo se lo activa
				if("N".equalsIgnoreCase(objCuentaXPersonas.get().getEsActivo())) {
					objCuentaXPersonas.get().setFechaModificacion(datFechaActual);
					objCuentaXPersonas.get().setEsActivo("S");
					objCuentaXPersonasDAO.update(objCuentaXPersonas.get());
				}else
					//Si ya esta agregada salta la exepcion
					throw new BOException("pru.warn.cuentaYaIngresada", new Object[] {objCuentas.get().getNombre(),objPersonas.get().getPrimerNombre()+" "+objPersonas.get().getPrimerApellido()});
			}
		
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class})
	public Map<String, Object> crearCuenta(CuentasDTO objCuentasDTO) throws BOException {
		
		//Valida que el nombree sea requerido
		GenericUtil.validarCampoRequeridoBO(objCuentasDTO.getNombre(), "pru.campos.nombre");
		//Valida que la descripcion sea requerido
		GenericUtil.validarCampoRequeridoBO(objCuentasDTO.getDescripcion(), "pru.campos.descripcion");
		
		Long lonCountCuentas=objCuentasDAO.consultarPorNombre(objCuentasDTO.getNombre());
		
		if(lonCountCuentas.intValue()>0)
			throw new BOException("pru.warn.cuentaYaExisteConEseNombre");
		
		Cuentas objCuentas=new Cuentas();
		objCuentas.setEsActivo("S");
		objCuentas.setNombre(objCuentasDTO.getNombre().toUpperCase());
		objCuentas.setDescripcion(objCuentasDTO.getDescripcion());
		objCuentas.setFechaIngreso(new Date());
		objCuentasDAO.persist(objCuentas);
		
		Map<String, Object> mapRequest=new HashMap<String, Object>();
		mapRequest.put("secuenciaCuenta", objCuentas.getSecuenciaCuenta());
		return mapRequest;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class})
	public Map<String, Object> actualizaCuenta(Integer intSecuenciaCuenta, CuentasDTO objCuentasDTO) throws BOException {
		
		//Valida que el secuenciaCuenta sea requerido
		GenericUtil.validarCampoRequeridoBO(intSecuenciaCuenta, "pru.campos.secuenciaCuenta");
		//Valida que el nombree sea requerido
		GenericUtil.validarCampoRequeridoBO(objCuentasDTO.getNombre(), "pru.campos.nombre");
		//Valida que la descripcion sea requerido
		GenericUtil.validarCampoRequeridoBO(objCuentasDTO.getDescripcion(), "pru.campos.descripcion");
		//Valida que la estado sea requerido
		GenericUtil.validarCampoRequeridoBO(objCuentasDTO.getEstado(), "pru.campos.estado");
		
		Optional<Cuentas> objCuentas=objCuentasDAO.find(intSecuenciaCuenta);
		
		//Valida que la cuenta exista
		if(!objCuentas.isPresent()) 
			throw new BOException("pru.warn.campoNoExiste", new Object[] { "pru.campos.secuenciaCuenta"});
		
		if(!objCuentas.get().getNombre().toUpperCase().equalsIgnoreCase(objCuentasDTO.getNombre().toUpperCase())) {
			
			Long lonCountCuentas=objCuentasDAO.consultarPorNombre(objCuentasDTO.getNombre());
			
			if(lonCountCuentas.intValue()>0)
				throw new BOException("pru.warn.cuentaYaExisteConEseNombre");
			
		}
		
		objCuentas.get().setNombre(objCuentasDTO.getNombre());
		objCuentas.get().setDescripcion(objCuentasDTO.getDescripcion());
		objCuentas.get().setEsActivo(objCuentasDTO.getEstado()?"S":"N");
		objCuentas.get().setFechaIngreso(new Date());
		objCuentasDAO.persist(objCuentas.get());
		
		Map<String, Object> mapRequest=new HashMap<String, Object>();
		mapRequest.put("secuenciaCuenta", objCuentas.get().getSecuenciaCuenta());
		return mapRequest;
	}
	
}
