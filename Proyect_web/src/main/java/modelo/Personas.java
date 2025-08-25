package modelo;

public class Personas {
	

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
