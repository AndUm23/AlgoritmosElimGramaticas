package modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Modelo {

	// Atributos
	ArrayList gramatic;

	/**
	 * constructor
	 * 
	 * @param data
	 */
	public Modelo(ArrayList data) {
		gramatic = data;
	}

	/**
	 * método que llama los métodos para hacer la gramática final
	 * 
	 * @return gramatica editada
	 */
	public ArrayList algT() {
		vacias();
		terminales();
		// alcanzables();
		// anulables();
		// unitarias();
		return gramatic;
	}
	public ArrayList algAl() {
		vacias();
		terminales();
		alcanzables();
		// anulables();
		// unitarias();
		return gramatic;
	}
	public ArrayList algAn() {
		vacias();
		terminales();
		alcanzables();
		anulables();
		// unitarias();
		return gramatic;
	}
	public ArrayList algU() {
		vacias();
		terminales();
		alcanzables();
		anulables();
		unitarias();
		return gramatic;
	}
	

	// método que elimina variables vacias
	public void vacias() {
		for (int i = 0; i < gramatic.size(); i++) {
			if (((ArrayList) gramatic.get(i)).get(1).equals("") || ((ArrayList) gramatic.get(i)).get(0).equals("")) {
				gramatic.remove(i);
				i = -1;
			}
		}
	}

	/**
	 * hacer una copia de la gramática
	 * 
	 * @return copia de la gramática
	 */
	private ArrayList makeACopy(ArrayList grammarToCopy) {
		ArrayList copy = new ArrayList();
		for (int i = 0; i < grammarToCopy.size(); i++) {
			ArrayList aux1 = new ArrayList();
			String auxP = (String) ((ArrayList) grammarToCopy.get(i)).get(0);
			aux1.add(auxP);
			ArrayList copyProductions = new ArrayList();
			for (int k = 0; k < ((ArrayList) ((ArrayList) grammarToCopy.get(i)).get(1)).size(); k++) {
				String productions = (String) ((ArrayList) ((ArrayList) grammarToCopy.get(i)).get(1)).get(k);
				copyProductions.add(productions);
			}
			aux1.add(copyProductions);
			copy.add(aux1);
		}
		return copy;
	}

	/**
	 * dice si dos gramáticas son iguales
	 * 
	 * @return true si son iguals
	 */
	private boolean areGrammarEquals(ArrayList grammar1, ArrayList grammar2) {
		boolean equals = true;
		if (grammar1.size() != grammar2.size()) {
			return false;
		}
		for (int i = 0; i < grammar1.size(); i++) {
			if (!((String) ((ArrayList) grammar1.get(i)).get(0))
					.equals((String) ((ArrayList) grammar2.get(i)).get(0))) {
				return equals;
			}
			for (int j = 0; j < ((ArrayList) ((ArrayList) grammar1.get(i)).get(1)).size(); j++) {
				if (!((String) ((ArrayList) ((ArrayList) grammar1.get(i)).get(1)).get(j))
						.equals((String) ((ArrayList) ((ArrayList) grammar2.get(i)).get(1)).get(j))) {
					return equals;
				}
			}
		}
		return equals;
	}

	public void eliminarNoTerminales(ArrayList<ArrayList<String>> gramatica) {
		ArrayList<ArrayList<String>> produccionesTerminales = new ArrayList<>();
		ArrayList<ArrayList<String>> iDK = new ArrayList<>();

		// Identificar producciones con solo símbolos terminales
		for (ArrayList<String> produccion : gramatica) {
			boolean esTerminal = true;
			for (String simbolo : produccion.get(1).split("\\|")) {
				if (!simbolo.matches("[a-z]+")) {
					esTerminal = false;
					break;
				}
			}
			if (esTerminal) {
				produccionesTerminales.add(produccion);
			} else {
				iDK.add(produccion);
			}
		}

		// Encontrar más símbolos terminales
		boolean huboCambios;
		do {
			ArrayList<ArrayList<String>> copiaProduccionesTerminales = new ArrayList<>(produccionesTerminales);
			for (ArrayList<String> produccion : iDK) {
				boolean esTerminal = true;
				for (String simbolo : produccion.get(1).split("\\|")) {
					if (!simbolo.matches("[a-z]+")) {
						boolean encontrado = false;
						for (ArrayList<String> terminales : produccionesTerminales) {
							if (terminales.get(0).equals(simbolo)) {
								encontrado = true;
								break;
							}
						}
						if (!encontrado) {
							esTerminal = false;
							break;
						}
					}
				}
				if (esTerminal) {
					produccionesTerminales.add(produccion);
					iDK.remove(produccion);
				}
			}
			huboCambios = !produccionesTerminales.equals(copiaProduccionesTerminales);
		} while (huboCambios);

		// Eliminar variables no terminales
		gramatica.removeIf(
				produccion -> iDK.stream().anyMatch(noTerminal -> noTerminal.get(0).equals(produccion.get(0))));

		// Eliminar producciones que contienen símbolos no terminales
		for (ArrayList<String> produccion : gramatica) {
			produccion.get(1).replaceAll("\\|", " ");
			for (ArrayList<String> noTerminal : iDK) {
				produccion.set(1, produccion.get(1).replaceAll(noTerminal.get(0), ""));
			}
			produccion.set(1, produccion.get(1).replaceAll("  ", "|").trim());
		}
	}

	// remover variables no terminales
	@SuppressWarnings({ "unlikely-arg-type", "unused" })
	public void terminales() {
		ArrayList productionsTerminals = new ArrayList();
		ArrayList iDK = new ArrayList();

		// terminales 1
		for (int i = 0; i < gramatic.size(); i++) {
			boolean terminal = false;
			for (int j = 0; j < ((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).size(); j++) {
				String production = (String) ((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).get(j);
				if (production.equals(production.toLowerCase())) {
					productionsTerminals.add(gramatic.get(i));
					terminal = true;
					break;
				}
			}
			if (!terminal) {
				iDK.add(gramatic.get(i));
			}
		}

		// terminales 2-n
		String[] variables = new String[productionsTerminals.size()];
		for (int i = 0; i < variables.length; i++) {
			variables[i] = (String) ((ArrayList) productionsTerminals.get(i)).get(0);
		}
		boolean equals = true;
		boolean changed = false;
		while (equals) {
			ArrayList aux = makeACopy(productionsTerminals);
			for (int i = 0; i < iDK.size(); i++) {
				int pos = -1;
				for (int j = 0; j < ((ArrayList) ((ArrayList) iDK.get(i)).get(1)).size(); j++) {
					String production = (String) ((ArrayList) ((ArrayList) iDK.get(i)).get(1)).get(j);
					for (int k = 0; k < production.length(); k++) {
						if (Character.isUpperCase(production.charAt(k))) {
							for (int l = 0; l < variables.length; l++) {
								if (variables[l].equals(production.charAt(k))) {
									pos = l;
									break;
								} else {
									pos = -1;
									break;
								}
							}
							if (pos == -1) {
								break;
							}
							if (pos != -1) {
								productionsTerminals.add(iDK.remove(i));
								changed = true; // Update changed if a production is added
							}
						}
					}
				}
				if (pos != -1) {
					productionsTerminals.add(iDK.remove(i));
					i = -1;
				}
			}
			if (!changed) {
				break; // Break out of the loop if no changes were made
			}
			equals = !areGrammarEquals(aux, productionsTerminals);
		}

		// eliminar variables no terminales
		for (int i = 0; i < gramatic.size(); i++) {
			for (int j = 0; j < iDK.size(); j++) {
				if (((ArrayList) gramatic.get(i)).get(0).equals(((ArrayList) iDK.get(j)).get(0))) {
					gramatic.remove(i);
					i = -1;
					break;
				}
			}
		}

		// eliminar producciones sin terminales
		for (int i = 0; i < gramatic.size(); i++) {
			for (int j = 0; j < ((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).size(); j++) {
				String production = (String) ((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).get(j);
				for (int k = 0; k < iDK.size(); k++) {
					if (production.contains((CharSequence) ((ArrayList) iDK.get(k)).get(0))) {
						((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).remove(j);
						j = -1;
					}
				}
			}
		}
	}

	/**
	 * eliminar variables no alcanzables de la gramática
	 */
	public void alcanzables() {
		String[] variables = new String[gramatic.size()];
		for (int i = 0; i < variables.length; i++) {
			variables[i] = (String) ((ArrayList) gramatic.get(i)).get(0);
		}
		Queue queue = new LinkedList();
		boolean[] isAttainable = new boolean[gramatic.size()];
		for (int i = 0; i < isAttainable.length; i++) {
			isAttainable[i] = false;
		}
		queue.add(gramatic.get(0));
		isAttainable[0] = true;
		while (!queue.isEmpty()) {
			ArrayList elementQueue = (ArrayList) queue.poll();
			for (int i = 0; i < ((ArrayList) elementQueue.get(1)).size(); i++) {
				String production = (String) ((ArrayList) elementQueue.get(1)).get(i);
				for (int j = 1; j < variables.length; j++) {
					if (isAttainable[j] != true && production.contains(variables[j])) {
						isAttainable[j] = true;
						queue.add(gramatic.get(j));
					}
				}
			}
		}
		for (int i = gramatic.size() - 1; i > 0; i--) {
			if (isAttainable[i] == false) {
				gramatic.remove(i);
			}
		}
	}

	/**
	 * eliminar producciones anulables de las producciones
	 */
	public void anulables() {

		// anulable 1
		ArrayList voidable = new ArrayList();
		for (int i = 0; i < gramatic.size(); i++) {
			for (int j = 0; j < ((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).size(); j++) {
				String production = (String) ((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).get(j);
				if (production.equals("_")) {
					voidable.add(gramatic.get(i));
					((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).remove(j);
				}
			}
		}

		// anulable 2-n
		boolean equals = true;
		while (equals) {
			ArrayList aux = makeACopy(voidable);
			for (int i = 0; i < gramatic.size(); i++) {
				for (int j = 0; j < ((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).size(); j++) {
					String production = (String) ((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).get(j);
					if (production.length() == 1) {
						for (int l = 0; l < voidable.size(); l++) {
							if ((!voidable.contains(gramatic.get(i))) && production
									.charAt(0) == ((String) ((ArrayList) voidable.get(l)).get(0)).charAt(0)) {
								voidable.add(gramatic.get(i));
							}
						}
					}
				}
			}
			equals = !areGrammarEquals(aux, voidable);
		}
	}

	/**
	 * hacer gramática unitaria
	 */
	public void unitarias() {
		String[] variables = new String[gramatic.size()];
		for (int i = 0; i < variables.length; i++) {
			variables[i] = (String) ((ArrayList) gramatic.get(i)).get(0);
		}
		boolean[] visited = new boolean[variables.length];
		for (int i = 0; i < gramatic.size(); i++) {
			Queue queue = new LinkedList();
			queue.add(gramatic.get(i));
			while (!queue.isEmpty()) {
				ArrayList grammar = (ArrayList) queue.poll();
				for (int j = 0; j < ((ArrayList) grammar.get(1)).size(); j++) {
					String productions = (String) ((ArrayList) grammar.get(1)).get(j);
					if (productions.length() == 1 && productions.toUpperCase().equals(productions)) {
						int pos = -1;
						for (int k = 0; k < variables.length; k++) {
							if (productions.equals(variables[k])) {
								pos = k;
								break;
							}
						}
						if (visited[pos]) {
							break;
						} else {
							visited[pos] = true;
						}
						queue.add(gramatic.get(pos));
						ArrayList aux = ((ArrayList) ((ArrayList) gramatic.get(pos)).get(1));
						for (int k = 0; k < aux.size(); k++) {
							if (((((String) aux.get(k)).length() != 1) || (((String) aux.get(k))
									.charAt(0) == ((String) aux.get(k)).toLowerCase().charAt(0)))
									&& !(((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).contains(aux.get(k)))) {
								((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).add(aux.get(k));
							}
						}
					}
				}
			}
		}

		for (int i = 0; i < gramatic.size(); i++) {
			for (int j = 0; j < ((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).size(); j++) {
				if (((String) ((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).get(j)).length() == 1
						&& ((String) ((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).get(j)).toUpperCase()
								.equals(((String) ((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).get(j)))) {
					((ArrayList) ((ArrayList) gramatic.get(i)).get(1)).remove(j);
					j = -1;
				}
			}
		}
	}

}
