package ar.com.carreira.sabrina.utilidades;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LecturaArchivo {
	//Clase que provee métodos estáticos para facilitar la lectura de archivos
	
	public static List<String> leerArchivo(String ubicacionArchivo){
		//Ruta del archivo
		Path ruta = Paths.get(ubicacionArchivo);
		List<String> lineasArchivo = null;
		
		try {
			lineasArchivo = Files.readAllLines(ruta);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lineasArchivo;
	}

}
