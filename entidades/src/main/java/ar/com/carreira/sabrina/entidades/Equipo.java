package ar.com.carreira.sabrina.entidades;

public class Equipo {
	private String codigo = "";
	private String nombre = "";
	
	
	public Equipo(String codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public String getCodigo() {
		return this.codigo;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	

	@Override
	public String toString() {
		return "Equipo [codigo=" + codigo + ", nombre=" + nombre + "]";
	}
	
	
}
