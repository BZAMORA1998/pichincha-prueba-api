package com.pichincha.prueba.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CuentaXPersonasCPK implements Serializable {

	private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "secuencia_cuenta")
    private Integer secuenciaCuenta;
	
    @NotNull
    @Column(name = "secuencia_persona")
    private Integer secuenciaPersona;
	
}
