package controlador;

import javax.swing.JOptionPane;

public class test {
	
	/*En esta clase test se crea el metodo main que se utiliza para ejecutar la clase 
	 * En esta clase de crea un objeto de la clase conexion llamado texto para poder llamar el metodo 
	 * conectarBD, despues de  esto se valida si la conexion es exitosa y se imprime por consola si fue conectada
	 * a la base de datos sino sale el error por  el else sino se pudo conectar a la base de datos.
	 * 
	 * 
	 */

	public  static void main (String [] args) {
		
		conexion test = new conexion();
		
		if (test.conectarBD() != null) {
			JOptionPane.showConfirmDialog(null, "Conectado a la BD");
		}else {
			JOptionPane.showConfirmDialog(null, "No se pudo conectar a la BD");
			
		}
		
	}

}

