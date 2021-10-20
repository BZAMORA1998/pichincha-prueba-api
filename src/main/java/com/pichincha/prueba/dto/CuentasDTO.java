package com.pichincha.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CuentasDTO {
	private Integer secuenciaCuenta;
	private String nombre;
	private String descripcion;
	private Boolean estado;
}
