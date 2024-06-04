package controlador;


import java.util.ArrayList;

import modelo.Modelo;

/**
 *el programa genera una gramatica (aplicando los 4 algs) a una g dada
 */
public class Control {
	
	private Modelo model;
	
	/**
	 * button accept
	 * @param data
	 */
	public ArrayList accept(ArrayList data) {
		model = new Modelo(data);
		return model.algT();
	}
	public ArrayList acceptB(ArrayList data) {
		model = new Modelo(data);
		return model.algAl();
	}
	public ArrayList acceptC(ArrayList data) {
		model = new Modelo(data);
		return model.algAn();
	}
	public ArrayList acceptD(ArrayList data) {
		model = new Modelo(data);
		return model.algU();
	}
	
}
