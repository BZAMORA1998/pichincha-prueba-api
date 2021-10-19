package com.pichincha.prueba.enums;

public enum TipoIdentificacion {

	CEDULA(new Integer("1")),
	PASAPORTE(new Integer("2")),
	RUC(new Integer("2"));

	private Integer valor;

	TipoIdentificacion(Integer valor) {
        this.valor = valor;
    }

    public Integer getValor() {
        return valor;
    }
}
