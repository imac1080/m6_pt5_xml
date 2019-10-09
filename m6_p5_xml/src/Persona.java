//Clase Persona
import java.io.Serializable;

public class Persona implements Serializable {
	private String nombre;
	private int edad;

	public Persona() {
	}

	public Persona( String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
