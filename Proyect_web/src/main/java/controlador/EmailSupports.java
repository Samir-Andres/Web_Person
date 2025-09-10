package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Properties;

//Servlet para el envío de correos electrónicos de soporte mientras que el serblet servlet maneja las solicitudes HTTP POST para enviar un correo
// electrónico a una dirección de destino con un asunto y mensaje específicos,utilizando las credenciales de una cuenta de Gmail.

@WebServlet("/EmailSupports")
public class EmailSupports extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Credenciales del correo de origen
    private static final String CORREO_REMITENTE = "samirandres296@gmail.com";
    private static final String CONTRASENA = "vwbm iiml fcmo tgnz"; // Usa contraseña de aplicación
//Maneja las solicitudes HTTP de tipo POST,  Este método se invoca cuando se envía un formulario HTML con el método POST
    //a la URL mapeada a este servlet (/EmailSupports).
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	// Obtiene los parámetros del formulario de la solicitud.
        String correoDestino = request.getParameter("correo");
        String asunto = request.getParameter("asunto");
        String mensaje = request.getParameter("mensaje");

        try {
        	 // Establece la versión del protocolo TLS para asegurar una conexión segura.
            System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");

            // Configura las propiedades de la sesión de correo para la conexión SMTP de Gmail.
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            
            // Crea una nueva sesión de correo con las propiedades y un autenticador.crea y configura una sesión de correo,
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(CORREO_REMITENTE, CONTRASENA);
                }
            });

         // Crea un mensaje de correo electrónico. 
            Message messageObj = new MimeMessage(session);
            messageObj.setFrom(new InternetAddress(CORREO_REMITENTE));
            messageObj.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
            messageObj.setSubject(asunto);
            messageObj.setText(mensaje);

            // Envía el correo electrónico.
            Transport.send(messageObj);
            System.out.println(" *_* Correo enviado correctamente*_*");

            // Redirige al JSP con estado OK
            response.sendRedirect("CorreoSupports.jsp?status=ok");

        } catch (MessagingException e) {
        	   // Maneja excepciones en caso de fallo del envío.
            e.printStackTrace();
            System.out.println("Error al enviar el correo.");

            // Redirige al JSP con estado de error
            response.sendRedirect("CorreoSupports.jsp?status=error");
        }
    }
}