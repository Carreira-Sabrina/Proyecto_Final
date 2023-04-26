package ar.com.carreira.sabrina.implementacion;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.com.carreira.sabrina.entidades.Equipo;
import ar.com.carreira.sabrina.entidades.Partido;
import ar.com.carreira.sabrina.entidades.Pronostico;

/**
 * Unit test for simple App.
 */
public class TestCalculoPuntajes 
{
    @Test
    public void puntajePronostico()
    {
    	Equipo eq1 = new Equipo("BRA", "Brasil");
    	Equipo eq2 = new Equipo("COS", "Corea del Sur");
    	
    	Partido part1 = new Partido(eq1, eq2, 10, 4);
    
    	Pronostico pr = new Pronostico("1", "Lily", part1);
    	//Setear el pronostico
    	pr.setAliasPartido("BRA-COS");
    	pr.setEquipo(eq2); //apostó por Corea
    	pr.setPartido(part1);
    	pr.setResultado();
    	
    	int puntos = pr.puntos(part1.resultado(pr.getEquipo()));
    	
    	int puntosEsperados = 0;
    	
    	assertEquals(puntos, puntosEsperados);
    	
    }
}
