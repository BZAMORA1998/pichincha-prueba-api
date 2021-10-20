package com.pichincha.prueba.bo.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pichincha.prueba.bo.ITransaccionBO;
import com.pichincha.prueba.dao.CuentaXPersonasDAO;
import com.pichincha.prueba.dao.CuentasDAO;
import com.pichincha.prueba.dao.PersonasDAO;
import com.pichincha.prueba.dao.TransaccionesDAO;
import com.pichincha.prueba.dto.TransaccionDTO;
import com.pichincha.prueba.exceptions.BOException;
import com.pichincha.prueba.model.CuentaXPersonas;
import com.pichincha.prueba.model.CuentaXPersonasCPK;
import com.pichincha.prueba.model.Cuentas;
import com.pichincha.prueba.model.Personas;
import com.pichincha.prueba.model.Transacciones;
import com.pichincha.prueba.util.GenericUtil;

@Service
public class TransaccionBOImpl implements ITransaccionBO{

	@Autowired
	private CuentasDAO objCuentasDAO;
	@Autowired
	private PersonasDAO objPersonasDAO;
	@Autowired
	private CuentaXPersonasDAO objCuentaXPersonasDAO;
	@Autowired
	private TransaccionesDAO objTransaccionesDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class})
	public void crearTransaccion(Integer intSecuenciaPersona, Integer intSecuenciaCuenta,
			List<TransaccionDTO> lsTransaccionDTO) throws BOException {
		
		//Valida que el secuenciaCuenta sea requerido
		GenericUtil.validarCampoRequeridoBO(intSecuenciaCuenta, "pru.campos.secuenciaCuenta");
		//Valida que la secuencia persona sea requerido
		GenericUtil.validarCampoRequeridoBO(intSecuenciaPersona, "pru.campos.secuenciaPersona");
		
		//Busca la persona
		Optional<Personas> objPersonas=objPersonasDAO.find(intSecuenciaPersona);
		
		//Valida que el tipo de identificacion exista
		if(!objPersonas.isPresent()) 
			throw new BOException("pru.warn.campoNoExiste", new Object[] { "pru.campos.secuenciaPersona"});
		
		//Valida que el tipo de identificacion este activo
		if(!("S").equalsIgnoreCase(objPersonas.get().getEsActivo())) 
			throw new BOException("pru.warn.campoInactivo", new Object[] { "pru.campos.secuenciaPersona"});
		
		//Busca la cuenta
		Optional<Cuentas> objCuentas=objCuentasDAO.find(intSecuenciaCuenta);
		
		//Valida que la cuenta exista
		if(!objCuentas.isPresent()) 
			throw new BOException("pru.warn.campoNoExiste", new Object[] { "pru.campos.secuenciaCuenta"});
		
		//Valida que la cuenta este activo
		if(!("S").equalsIgnoreCase(objCuentas.get().getEsActivo())) 
			throw new BOException("pru.warn.campoInactivo", new Object[] { "pru.campos.secuenciaCuenta"});
		
		Optional<CuentaXPersonas> objCuentaXPersonas=objCuentaXPersonasDAO.find(new CuentaXPersonasCPK(intSecuenciaCuenta,intSecuenciaPersona));
		
		//Valida que la cuenta exista
		if(!objCuentaXPersonas.isPresent() || !("S").equalsIgnoreCase(objCuentaXPersonas.get().getEsActivo())) 
			throw new BOException("pru.warn.cuentaNoAsignada", new Object[] {objPersonas.get().getPrimerNombre()+" "+objPersonas.get().getPrimerApellido() ,objCuentas.get().getNombre()});
		
		
		Transacciones objTransacciones=null;
		Date dateFechaActual=new Date();
		for(TransaccionDTO objTransaccionDTO:lsTransaccionDTO) {
			
			//Valida que el descripcion sea requerido
			GenericUtil.validarCampoRequeridoBO(objTransaccionDTO.getDescripcion(), "pru.campos.descripcion");
			//Valida que el valor total sea requerido
			GenericUtil.validarCampoRequeridoBO(objTransaccionDTO.getValorTotal(), "pru.campos.valorTotal");
			
			objTransacciones=new Transacciones();
			objTransacciones.setCuentaXPersonas(objCuentaXPersonas.get());
			objTransacciones.setEsActivo("S");
			objTransacciones.setFechaIngreso(dateFechaActual);
			objTransacciones.setDescripcion(objTransaccionDTO.getDescripcion());
			objTransacciones.setValorTotal(objTransaccionDTO.getValorTotal());
			objTransaccionesDAO.persist(objTransacciones);
		}
	
	}
	
}
