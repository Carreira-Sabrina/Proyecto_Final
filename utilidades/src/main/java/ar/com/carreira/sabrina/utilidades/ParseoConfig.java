package ar.com.carreira.sabrina.utilidades;

import java.util.List;
import java.util.HashMap;

public class ParseoConfig {
	//Clase que provee métodos estáticos para facilitar la conversión del archivo de configuración
	
	public static HashMap<String,String> parsearConfig(List<String>data){
		//Toma las lineas obtenidas del archivo de configuración y la almacena en un HashMap
		HashMap <String,String>mapaConfig = new HashMap<>();
		for(String linea: data) {
			//***Podrìa implementar la funcion de saltear una linea que empiece con cierto
			//caracter (posibilidad de incluir comentarios)
			//Cada linea se separa por el caracter =, lo que està a la izquierda es la key
			//lo que está a la derecha es el value
			String tempArray[] = linea.split("=");
			//El index 0 pasaría a contener la key y el 1 el value
			String tempKey = tempArray[0];
			String tempValue = tempArray[1];
			mapaConfig.put(tempKey, tempValue);
		}
		return mapaConfig;
	}

}
