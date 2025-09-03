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

@WebServlet("/EmailSupports")
public class EmailSupports extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Credenciales del correo de origen
    private static final String CORREO_REMITENTE = "samirandres296@gmail.com";
    private static final String CONTRASENA = "vwbm iiml fcmo tgnz"; // Usa contraseña de aplicación

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
            System.out.println("✅ Correo enviado correctamente.");

            // Redirige al JSP con estado OK
            response.sendRedirect("CorreoSupports.jsp?status=ok");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error al enviar el correo.");

            // Redirige al JSP con estado de error
            response.sendRedirect("CorreoSupports.jsp?status=error");
        }
    }
}