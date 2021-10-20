package com.pichincha.prueba.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.pichincha.prueba.enums.FormatoFecha;

public class FechasUtil {

	/**
	 * Valida si el formato de una fecha en String es correcto.
	 * 
	 * @author Bryan Zamora
	 * @param strDate      Fecha en String
	 * @param formatoFecha Tipo de formato de fecha a evaluar.
	 * @return
	 */
	public static boolean formatoFechaValido(String strDate, FormatoFecha formatoFecha) {
		if (strDate.trim().equals("")) {
			return false;
		}
		SimpleDateFormat sdfrmt = new SimpleDateFormat(formatoFecha.getName());
		sdfrmt.setLenient(false);
		try {
			sdfrmt.parse(strDate);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * Convierte una fecha string a tipo de dato java.util.Date
	 * 
	 * @author Bryan Zamora
	 * @param strFecha
	 * @return
	 */
	public static Date stringToDate(String strFecha, FormatoFecha formatoFecha) {
		if (!formatoFechaValido(strFecha, formatoFecha)) {
			throw new RuntimeException("La fecha no cumple con el formato indicado.");
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(formatoFecha.getName());
			return formatter.parse(strFecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Eliminar horas a la fecha
	 * 
	 * @author Bryan Zamora
	 * @param date
	 */

	public static Date limpiarHoraAFecha(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);

		return calendar.getTime();
	}

	/**
	 * Asignar la hora final del día a la fecha (23:59:59.0)
	 * 
	 * @author Bryan Zamora
	 * @param date
	 * @return
	 */
	public static Date asignarHoraFinDelDia(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.HOUR_OF_DAY, 23);

		return calendar.getTime();
	}
	
	/**
	 * Convierte una fecha Date a String
	 * 
	 * @author Bryan Zamora
	 * @param datFecha
	 * @param formatoFecha
	 * @return
	 */
	public static String dateToString(Date datFecha, FormatoFecha formatoFecha) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatoFecha.getName());
		return formatter.format(datFecha);
	}
	
}
