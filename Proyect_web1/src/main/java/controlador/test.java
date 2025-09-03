package controlador;

import javax.swing.JOptionPane;

public class test {

	public  static void main (String [] args) {
		
		conexion test = new conexion();
		
		if (test.conectarBD() != null) {
			JOptionPane.showConfirmDialog(null, "Conectado a la BD");
		}else {
			JOptionPane.showConfirmDialog(null, "No se pudo conectar a la BD");
			
		}
		
	}

}

