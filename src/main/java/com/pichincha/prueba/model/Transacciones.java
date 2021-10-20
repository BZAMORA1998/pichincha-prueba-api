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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity implementation class for Entity: Cuentas
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tbl_transacciones")
public class Transacciones implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "secuencia_transaccion")
    private Integer secuenciaTransaccion;
	
	@Size(max=500)
	@Column(name = "descripcion")
    private String descripcion;
	
	@Column(name = "valor_total")
	private Double valorTotal;
	
	@Column(name = "fecha_ingreso")
	private Date fechaIngreso;
	 
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;
	
	@Size(max=1)
	@Column(name = "es_activo")
    private String esActivo;
	
	@JoinColumns({
		@JoinColumn(name = "secuencia_cuenta", referencedColumnName = "secuencia_cuenta"),
		@JoinColumn(name = "secuencia_persona", referencedColumnName = "secuencia_persona") })
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private CuentaXPersonas cuentaXPersonas;
}
