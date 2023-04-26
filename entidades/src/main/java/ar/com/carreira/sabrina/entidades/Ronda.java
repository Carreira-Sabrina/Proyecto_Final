package ar.com.carreira.sabrina.entidades;

import java.util.ArrayList;


public class Ronda {
	private String numero;
	private ArrayList<Partido> partidos= null;
	
	//Constructor 
	public Ronda(String numero) {
		this.numero = numero;
		this.partidos = new ArrayList<Partido>();
	}
	
	//Getters
	public String getNumero() {
		return this.numero;
	}
	
	public ArrayList<Partido> getPartidos(){
		return this.partidos;
	}
	
	public int getCantPartidos () {
		return this.partidos.size();	
	}
	
	//Este método es muy importante para agregar partidos en un loop
	public void agregarPartido(Partido partido) {
		this.partidos.add(partido);
	}
	

	//NO SE QUE HACER CON ESTE MÉTODO !!!
	public void puntos() {
		
	}

}
