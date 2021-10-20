package com.pichincha.prueba.enums;


public enum FormatoFecha {

	YYYY_MM_DD("yyyy-MM-dd"),
	YYYY_MM_DD_HH_MM_SS("YYYY-MM-DD hh:mm:ss");

	private String name;

	FormatoFecha(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
