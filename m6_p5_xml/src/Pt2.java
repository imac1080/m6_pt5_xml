
import java.io.*;
import java.util.*;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Pt2 implements Serializable {
	private HashMap persona;

	/*
	 * Un HashMap es una col.lecio on cada node es del tipus (Objecte clau, Objecte
	 * Dades) funcionant l'objecte clau com un identificador. En aquest cas la clau
	 * es un String amb el codi del curs, mentre de que l'objecte dades es un del
	 * tipus Curs.
	 */
	public Pt2() {
		this.persona = new HashMap();
	}

	public void carregarDades(File f) {
		persones.clear();
		try {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			persones = (HashMap) ois.readObject(); // ERROR SI S'UTILITZA THIS
			System.out.println("==> LES DADES DEL FITXER HA ESTAT CARREGADES\n");
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void guardarDades(File f) {
//		try {
//			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
//			oos.writeObject(persones); // ERROR SI S'UTILITZA THIS
//			System.out.println("==> LES DADES HAN ESTAT GUARDADES AL FITXER\n");
//			oos.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			// root element
			Element rootElement = doc.createElement("persones");
			doc.appendChild(rootElement);

			for (int i = 0; i < persones.size(); i++) {
				Element departamento = doc.createElement("persona");
				rootElement.appendChild(departamento);
				departamento.appendChild(doc.createTextNode(persones);
				departamento.appendChild(doc.createTextNode("Rafa Aracil"));
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
	}

	public int menuPrincipal() {
		Scanner teclat = new Scanner(System.in);
		int opcio = 0;
		for (int i = 0; i < 50; i++)
			System.out.println("");
		System.out.println("==============================================");
		System.out.println("1. AFEGIR PERSONA");
		System.out.println("2. GENERAR XML");
		System.out.println("4. SORTIR");
		System.out.print("Escull una opcio:");
		opcio = teclat.nextInt();

		return opcio;
	}

	public static void main(String[] args) throws IOException {
		Pt2 x = new Pt2();
		String ruta = "myPeople.dat";
		String id = "";
		int o = 0;
		File cd = new File(ruta);
		x.carregarDades(cd); // Carreguem les dades que han estat guardades anteriorment
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
				if (dades.hasNextInt()) {
					o = dades.nextInt();
					x.persones.put(id + " " + cognom, new Persona(id, cognom, o));
					System.out.println("La persona" + id + " " + cognom + " ha estat creada");
					System.out.println("HIHAN " + x.persones.size() + " PERSONES");
				} else
					System.out.println("Error no s'ha creat, error de edat");
				break;
			case 2:

				break;
			}
		} while (o < 5);
		File gd = new File(ruta);
		x.guardarDades(gd); // Tornem a guardar el HashMap de cursos al fitxer
		System.out.println("S'HA ACABAT");
	}
}