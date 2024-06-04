package vista;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import vista.GramaticIn;

@SuppressWarnings("serial")
public class GramaticOut extends JDialog {
	/**
	 * relaciones
	 */
	private GramaticIn in;
	/*
	 * atributos
	 */
	private ArrayList gramatic;
	private JTextArea labelGramatic;
	
	/**
	 * constructor
	 * @param in
	 * @param gramatic
	 */
	public GramaticOut(GramaticIn in,ArrayList gramatic) {
		super(in, true);
		this.in = in;
		this.gramatic = gramatic;
		
		setTitle("Gramatica");
		setSize(500, 500);
		showGrammar();
		add(labelGramatic);
		pack();
	}
	
	/**
	 * agregar la gramatica en la interfaz
	 */
	public void showGrammar() {
		String grammar = "GRAMATICA:\n";
		for (int i = 0; i < gramatic.size(); i++) {
			grammar += ((ArrayList) gramatic.get(i)).get(0);
			grammar += "->";
			grammar += ((ArrayList) gramatic.get(i)).get(1);
			grammar += "\n";
		}
		labelGramatic = new JTextArea(grammar);
		//labelGramatic.setEditable(false);
	}
	
}
