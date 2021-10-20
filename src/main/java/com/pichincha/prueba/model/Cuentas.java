package com.pichincha.prueba.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "tbl_cuentas")
public class Cuentas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "secuencia_cuenta")
    private Integer secuenciaCuenta;
	
	@Size(max=100)
	@Column(name = "nombre")
    private String nombre;
	
	@Column(name = "fecha_ingreso")
	private Date fechaIngreso;
	 
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;
	
	@Size(max=1)
	@Column(name = "es_activo")
    private String esActivo;
}
