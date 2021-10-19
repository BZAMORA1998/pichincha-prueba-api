package com.pichincha.prueba.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tbl_personas")
public class Personas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "secuencia_persona")
    private Integer secuenciaPersona;
	
	@Size(max=50)
	@Column(name = "numero_identificacion")
	private String numeroIdentificacion;
	
	@Size(max=50)
    @Column(name = "primer_nombre")
    private String primerNombre;
	
	@Size(max=50)
    @Column(name = "segundo_nombre")
    private String segundoNombre;
	
	@Size(max=50)
    @Column(name = "primer_apellido")
    private String primerApellido;
	
	@Size(max=50)
    @Column(name = "segundo_apellido")
    private String segundoApellido;
	
	@Size(max=1)
	@Column(name = "es_activo")
	private String esActivo;
	
	@Column(name = "fecha_ingreso")
	private Date fechaIngreso;
	 
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;
	
	 
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "secuencia_tipo_identificacion", referencedColumnName = "secuencia_tipo_identificacion", insertable = true, updatable = true)
	private TiposIdentificacion tiposIdentificacion;
    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "secuencia_genero", referencedColumnName = "secuencia_genero", insertable = true, updatable = true)
	private Generos genero;
    
    
}

