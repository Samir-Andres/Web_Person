package modelo;

public class Personas {
	
	/*En esta clase (Personas) se de declaran las variables de la base de datos 
	 * En esta clase se ´puede encontrar los metodos Getter y Setter que sirve para controlar 
	 * el acceso alas variables donde se permite leer y modificar.
	 * 
	 * */
	
	private int id;
	private String nombre;
	private String apellido;
	private int edad;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	
    public String toString() {
        return  " nombre=" + nombre
               + ", apellido=" + apellido + ", edad =" + edad + "]" ;
   }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	} 
	
	 

}
