
import java.io.*;
import java.util.*;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Pt2 implements Serializable {
	private static HashMap<Integer, Persona> persones;

	/*
	 * Un HashMap es una col.lecio on cada node es del tipus (Objecte clau, Objecte
	 * Dades) funcionant l'objecte clau com un identificador. En aquest cas la clau
	 * es un String amb el codi del curs, mentre de que l'objecte dades es un del
	 * tipus Curs.
	 */
	public Pt2() {
		this.persones = new HashMap<Integer, Persona>();
	}

	public void carregarDades(File f) {
		persones.clear();
		try {
			if (f.exists()) {
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				persones = (HashMap<Integer, Persona>) ois.readObject(); // ERROR SI S'UTILITZA THIS
				System.out.println("==> LES DADES DEL FITXER HA ESTAT CARREGADES\n");
				ois.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int menuPrincipal() {
		Scanner teclat = new Scanner(System.in);
		int opcio = 0;
		System.out.println("");
		System.out.println("==============================================");
		System.out.println("1. AFEGIR PERSONA");
		System.out.println("2. GENERAR XML");
		System.out.println("3. LLEGIR XML");
		System.out.println("4. SORTIR");
		System.out.print("Escull una opcio:");
		opcio = teclat.nextInt();

		return opcio;
	}

	public static void main(String[] args) throws IOException {
		Pt2 x = new Pt2();
		String ruta = "persones.xml";
		String id = "";
		int o = 0;
		File cd = new File(ruta);
//		x.carregarDades(cd); // Carreguem les dades que han estat guardades anteriorment
		do {
			o = x.menuPrincipal();
			Scanner dades = new Scanner(System.in);
			switch (o) {
			case 1:
				System.out.println("digues el nom de la persona");
				id = dades.nextLine();
				System.out.println("digues el cognom de la persona");
				String cognom = dades.nextLine();
				System.out.println("digues la edat de la persona");
				int identificador = persones.size();
				identificador++;
				int edat;
				if (dades.hasNextInt()) {
					edat = dades.nextInt();
					x.persones.put(identificador, new Persona(id, cognom, edat));
					System.out.println("La persona" + id + " " + cognom + " ha estat creada");
					System.out.println("HIHAN " + x.persones.size() + " PERSONES");
				} else
					System.out.println("Error no s'ha creat, error de edat");
				break;
			case 2:
				try {
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.newDocument();
					// root element
					Element rootElement = doc.createElement("persones");
					doc.appendChild(rootElement);
					for (int i = 1; i <= persones.size(); i++) {
						Element departamento = doc.createElement("persona");
						rootElement.appendChild(departamento);
						// nombre
						Element nombre = doc.createElement("nombre");
						departamento.appendChild(nombre);
						nombre.appendChild(doc.createTextNode(persones.get(i).getNombre()));
						// apellido
						nombre = doc.createElement("apellido");
						departamento.appendChild(nombre);
						nombre.appendChild(doc.createTextNode(persones.get(i).getApellido()));
						// edad
						nombre = doc.createElement("edad");
						departamento.appendChild(nombre);
						nombre.appendChild(doc.createTextNode(String.valueOf(persones.get(i).getEdad())));
					}

					// write the content into xml file
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File("persones.xml"));
					transformer.transform(source, result);
					// Output to console for testing
					StreamResult consoleResult = new StreamResult(System.out);
					transformer.transform(source, consoleResult);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("guardat a persones.xml");
				break;
			case 3:
				try {
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(new File("persones.xml"));
					// root element
					System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					NodeList nList = doc.getElementsByTagName("persona");
					System.out.println("----------------------------");
					for (int temp = 0; temp < nList.getLength(); temp++) {
						Node nNode = nList.item(temp);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;
							System.out.println("\nCurrent Element :" + nNode.getNodeName());
							System.out.println("Nom de la persona : "
									+ eElement.getElementsByTagName("nombre").item(0).getTextContent());
							System.out.println("Cognom de la persona : "
									+ eElement.getElementsByTagName("apellido").item(0).getTextContent());
							System.out.println("Edat de la persona : "
									+ eElement.getElementsByTagName("edad").item(0).getTextContent());
							System.out.println("----------------------");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("Llegit a persones.xml");
				break;
			}
		} while (o < 5);
		System.out.println("S'HA ACABAT");
	}
}
