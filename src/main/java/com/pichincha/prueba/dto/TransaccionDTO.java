package com.pichincha.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransaccionDTO {
	private Integer secuenciaTransaccion;
	private Double valorTotal;
	private String descripcion;
	private String fechaIngreso;
}
