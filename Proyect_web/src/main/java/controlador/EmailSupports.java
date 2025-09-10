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

//Servlet para el envío de correos electrónicos de soporte mientras que el servlet maneja las solicitudes HTTP POST para enviar un correo
// electrónico a una dirección de destino con un asunto y mensaje específicos,utilizando las credenciales de una cuenta de Gmail.
/*En el metodo dopost se crean los parametros que son enviados de un formulario de un jsp definida con sus variables, tambien
 *Se genera un try-catch  donde se configura las credenciales de gmail de propiedade con un objeto Properties, depues se cra un sesion 
 *donde se verifica se la contraseña y el correo remitente son validos, al final de esto se crea el mensaje y se envia el correo redirigiedo 
 *a la vista CorreoSupports.js enviando un mensaje de el correo fue enviado de lo contrario si no se envia el correo se muestra un mensaje en
 *pantalla del que mensaje no se envio.
 * 
 * 
 * */

@WebServlet("/EmailSupports")
public class EmailSupports extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String CORREO_REMITENTE = "samirandres296@gmail.com";
    private static final String CONTRASENA = "vwbm iiml fcmo tgnz"; 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correoDestino = request.getParameter("correo");
        String asunto = request.getParameter("asunto");
        String mensaje = request.getParameter("mensaje");

        try {
            System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(CORREO_REMITENTE, CONTRASENA);
                }
            });

            Message messageObj = new MimeMessage(session);
            messageObj.setFrom(new InternetAddress(CORREO_REMITENTE));
            messageObj.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
            messageObj.setSubject(asunto);
            messageObj.setText(mensaje);

            Transport.send(messageObj);
            System.out.println(" *_* Correo enviado correctamente*_*");

            response.sendRedirect("CorreoSupports.jsp?status=ok");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error al enviar el correo.");

            response.sendRedirect("CorreoSupports.jsp?status=error");
        }
    }
}