package com.pichincha.prueba.util;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper de validaciones de cadenas.
 * 
 * @author Bryan Zamora
 *
 */
public class StringUtil {
	
	/**
	 * Valida si solamente existen letras y espacios en blanco en una cadena.
	 * 
	 * @author Bryan Zamora
	 * @param input
	 * @return
	 */
	public static boolean soloLetrasYEspacio(String input) {
		String regx = "^[\\p{L} ]+$";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}
	
	/**
	 * Valida si solamente existen letras y numeros en una cadena.
	 * 
	 * @author Bryan Zamora
	 * @param input
	 * @return
	 */
	public static boolean soloLetrasYNumeros(String input) {
		String regx = "^[a-zA-Z0-9]+$";
		return input.matches(regx);
	}
	
}
