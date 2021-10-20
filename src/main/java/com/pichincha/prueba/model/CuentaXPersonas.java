package com.pichincha.prueba.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity implementation class for Entity: TiposIdentificacion
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tbl_cuenta_x_personas")
public class CuentaXPersonas implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@EqualsAndHashCode.Include
    private CuentaXPersonasCPK cuentaXPersonasCPK;
	
	@Column(name = "fecha_ingreso")
	private Date fechaIngreso;
	 
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;
	
	@Size(max=1)
	@Column(name = "es_activo")
    private String esActivo;
}
