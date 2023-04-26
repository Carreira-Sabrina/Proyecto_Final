package ar.com.carreira.sabrina.utilidades;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import ar.com.carreira.sabrina.entidades.Equipo;
import ar.com.carreira.sabrina.entidades.Partido;
import ar.com.carreira.sabrina.entidades.Ronda;

//SE INTENTA QUE AHORA DEVUELVA DIRECTAMENTE UN ARRAYLIST DE RONDAS LAS CUALES CONTENDRAN SU PROPIA
//ARRAYLIST DE PARTIDOS GENERADA DINAMICAMENTE A PARTIR DE LAS LINEAS DEL ARCHIVO DE RESULTADOS

public class ParseoResultados {
	//Esta clase provee métodos estáticos para procesar el archivo de resultados
	
	public static ArrayList<Ronda> instanciarPartidosDeArchivo(String archivo,HashMap<String,Equipo> mapaEquipos) {
		//El segundo argumento es el HashMap con las instancias de los equipos
		
		//Se usa el método auxiliar para leer las lineas del archivo
		List<String> lineasArchivo = LecturaArchivo.leerArchivo(archivo);
		
		//La ArrayList de instancias de ronda que se van a devolver
		ArrayList<Ronda> rondas = new ArrayList<>();
		
		//Se debe llevar la cuenta de qué ronda se está procesando en el loop
		String numRondaActual = "1"; 
		//Se crea la primera instancia de ronda, se crean mas a medida que cambia el num. de ronda en el archivo
		Ronda rondaActual = new Ronda(numRondaActual);
		//Referencia a la instancia de Ronda actual, cambia cuando cambia el num. de ronda en el archivo
		Ronda referenciaRondaActual = rondaActual;
		
		//Guardo la primera instancia de la ronda en la ArrayList
		rondas.add(referenciaRondaActual);
		
	
		//Constantes para facilitar la lectura de la array temporal en la que se convierte cada linea
		final int INDEX_RONDA = 0;
		final int INDEX_EQ1 = 1;
		final int INDEX_GOLES1 = 2;
		final int INDEX_GOLES2 = 3;
		final int INDEX_EQ2 = 4;
		
		int indiceLoop = 1; //la primera linea se descarta porque es la cabecera de la tabla en el archivo
		
		while(indiceLoop < lineasArchivo.size()) {
			//La linea actual se transforma en una array
			String [] tempArray = lineasArchivo.get(indiceLoop).split(",");
			String numRonda = tempArray[INDEX_RONDA];
			String nombreEq1 = tempArray[INDEX_EQ1];
			String nombreEq2 = tempArray[INDEX_EQ2];
			int golesEq1 = Integer.parseInt(tempArray[INDEX_GOLES1]);
			int golesEq2 = Integer.parseInt(tempArray[INDEX_GOLES2]);
			//Recupero las instancias de equipo del HashMap buscando por nombre
			Equipo equipo1 = mapaEquipos.get(nombreEq1);
			Equipo equipo2 = mapaEquipos.get(nombreEq2);
			
			Partido partido = new Partido(equipo1, equipo2, golesEq1, golesEq2);
			
			if (!numRondaActual.equals(numRonda)) {
				//Cuando cambia el numero de ronda,se reinicia la referencia a la instancia de la ronda actual
				numRondaActual = numRonda;
				referenciaRondaActual = new Ronda(numRondaActual);
				rondas.add(referenciaRondaActual);
				
			}
			//Siempre guardo el partido actual en la ArrayList de partidos de la ronda
			
			referenciaRondaActual.agregarPartido(partido);
			
			indiceLoop = indiceLoop + 1;
			
		}
		//De hecho, un conjunto de rondas es una fase
		return rondas;	
		
	}//
	

}//end of class
