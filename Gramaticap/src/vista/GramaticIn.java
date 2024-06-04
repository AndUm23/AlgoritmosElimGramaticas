package vista;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.Control;

@SuppressWarnings("serial")
public class GramaticIn extends JFrame implements ActionListener {
	
	//relaciones
	private Control controller;
	private GramaticOut gramaticOut;

	 //atributos
	JPanel aux = new JPanel(new BorderLayout());
	JTextField[][] productions;
	
	//constructor of the class
	public GramaticIn() {
		controller = new Control();
		int numProductions = 0;
		try {
			numProductions = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el numero de producciones", "Gramatica a FNC", JOptionPane.INFORMATION_MESSAGE));
			if (numProductions <= 0) {
				System.exit(0);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "¡¡¡Oh no, algo salio mal!!!");
			System.exit(0);
		}
		createView(numProductions);
		add(aux);
		setSize(500, 500);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	
	/**
	 * crear la vista del programa
	 * @param numProductions
	 */
	private void createView(int numProductions) {
		productions = new JTextField[numProductions][2];
		JPanel panelProductions = new JPanel(new GridLayout(numProductions, 3));
		for (int i = 0; i < numProductions; i++) {
			productions[i][0] = new JTextField();			
			panelProductions.add(productions[i][0]);
			JLabel arrow = new JLabel("-->");
			panelProductions.add(arrow);
			productions[i][1] = new JTextField();
			panelProductions.add(productions[i][1]);
		}
		JLabel labMessage = new JLabel("<html><big>Ingrese aqui las producciones,<br> se aplicaran los 4 algoritmos</big><br>use , en remplazo de | para separar las producciones<br>(para lambda ingrese _)</html>");
		JButton buttonAccept = new JButton("ACEPTAR");
		buttonAccept.setActionCommand("ACCEPT");
		buttonAccept.addActionListener(this);
		aux.add(labMessage, BorderLayout.NORTH);
		aux.add(panelProductions, BorderLayout.CENTER);
		aux.add(buttonAccept, BorderLayout.SOUTH);
	}
	
	/**
	 * tomar los datos de la gramática
	 * @return
	 */
	public ArrayList takeData() {
		ArrayList data = new ArrayList();
		for (int i = 0; i < productions.length; i++) {
			ArrayList dataAux = new ArrayList();
			dataAux.add(productions[i][0].getText());
			ArrayList productionsAux = new ArrayList();
			String[] aux = productions[i][1].getText().split(",");
			for (int j = 0; j < aux.length; j++) {
				productionsAux.add(aux[j]);
			}
			dataAux.add(productionsAux);
			data.add(dataAux);
		}
		return data;
	}
	
	/**
	 * enviar los datos a gramaticOut
	 * @param grammar
	 */
	private void showData(ArrayList grammar) {
		gramaticOut = new GramaticOut(this, grammar);
		gramaticOut.setLocationRelativeTo(this);
		gramaticOut.setVisible(true);
	}
	
	/**
	 * accion Boton
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("ACCEPT")) {
			showData(controller.accept(takeData()));
			showData(controller.acceptB(takeData()));
			showData(controller.acceptC(takeData()));
			showData(controller.acceptD(takeData()));
		}
	}
	
	/**
	 * main 
	 * @param args
	 */
	public static void main(String[] args) {
		GramaticIn g = new GramaticIn();
	}

}

