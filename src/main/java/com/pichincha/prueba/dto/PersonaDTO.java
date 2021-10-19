package com.pichincha.prueba.dto;

import lombok.Data;

@Data
public class PersonaDTO {
	private Integer secuenciaPersona;
	private Integer secuenciaTipoIdentificacion;
	private String numeroIdentificacion;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String fechaNacimiento;
	private String fechaIngreso;
	private String fechaModificacion;
	private Integer secuenciaGenero;
	private String direccionDomicilio;
	private Boolean esActivo;
}
