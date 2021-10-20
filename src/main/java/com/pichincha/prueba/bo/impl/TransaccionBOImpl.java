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
import com.pichincha.prueba.dao.ITransaccionesDAO;
import com.pichincha.prueba.dao.PersonasDAO;
import com.pichincha.prueba.dao.TransaccionesDAO;
import com.pichincha.prueba.dto.TransaccionDTO;
import com.pichincha.prueba.enums.FormatoFecha;
import com.pichincha.prueba.exceptions.BOException;
import com.pichincha.prueba.model.CuentaXPersonas;
import com.pichincha.prueba.model.CuentaXPersonasCPK;
import com.pichincha.prueba.model.Cuentas;
import com.pichincha.prueba.model.Personas;
import com.pichincha.prueba.model.Transacciones;
import com.pichincha.prueba.util.FechasUtil;
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
	@Autowired
	private ITransaccionesDAO objITransaccionesDAO;

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

	@Override
	public List<TransaccionDTO> consultarTransacciones(String strFechaInicio, String strFechaFin,
			Integer intSecuenciaCuenta) throws BOException {
		
		//Valida que el intSecuenciaCuenta sea requerido
		GenericUtil.validarCampoRequeridoBO(intSecuenciaCuenta, "pru.campos.secuenciaCuenta");
		//Valida que el strFechaInicio sea requerido
		GenericUtil.validarCampoRequeridoBO(strFechaInicio, "pru.campos.fechaInicio");
		//Valida que el strFechaFin sea requerido
		GenericUtil.validarCampoRequeridoBO(strFechaFin, "pru.campos.fechaFin");
		
		//Valida si la fecha inicio esta en el formato correcto
		if(!FechasUtil.formatoFechaValido(strFechaInicio,FormatoFecha.YYYY_MM_DD))
			throw new BOException("pru.warn.fechaInvalida", new Object[] { "pru.campos.fechaInicio"});
		
		//Valida si la fecha fin esta en el formato correcto
		if(!FechasUtil.formatoFechaValido(strFechaInicio,FormatoFecha.YYYY_MM_DD))
			throw new BOException("pru.warn.fechaInvalida", new Object[] { "pru.campos.fechaFin"});
		
		Date datFechaInicio=FechasUtil.limpiarHoraAFecha(FechasUtil.stringToDate(strFechaInicio,FormatoFecha.YYYY_MM_DD));
		Date datFechaFin=FechasUtil.asignarHoraFinDelDia(FechasUtil.stringToDate(strFechaFin,FormatoFecha.YYYY_MM_DD));
				
		//Busca la cuenta
		Optional<Cuentas> objCuentas=objCuentasDAO.find(intSecuenciaCuenta);
		
		//Valida que la cuenta exista
		if(!objCuentas.isPresent()) 
			throw new BOException("pru.warn.campoNoExiste", new Object[] { "pru.campos.secuenciaCuenta"});
		
		//Valida que la cuenta este activo
		if(!("S").equalsIgnoreCase(objCuentas.get().getEsActivo())) 
			throw new BOException("pru.warn.campoInactivo", new Object[] { "pru.campos.secuenciaCuenta"});
	
		return objTransaccionesDAO.consultarTransacciones(datFechaInicio, datFechaFin,intSecuenciaCuenta);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class})
	public void eliminarTransaccion(Integer intSecuenciaTransaccion) throws BOException {
		
		//Valida que el intSecuenciaTransaccion sea requerido
		GenericUtil.validarCampoRequeridoBO(intSecuenciaTransaccion, "pru.campos.secuenciaTransaccion");
		
		//Busca la transaccion
		Optional<Transacciones> objTransacciones=objTransaccionesDAO.find(intSecuenciaTransaccion);
		
		//Valida que la cuenta exista
		if(!objTransacciones.isPresent()) 
			throw new BOException("pru.warn.campoNoExiste", new Object[] { "pru.campos.secuenciaTransaccion"});
		
		objITransaccionesDAO.delete(objTransacciones.get());
		
	}
	
}
