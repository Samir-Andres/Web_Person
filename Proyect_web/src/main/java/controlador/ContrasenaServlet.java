package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;
import java.sql.Timestamp;

// Importaciones para el correo electrónico
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;


@WebServlet("/ContrasenaServlet")
public class ContrasenaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String CORREO_REMITENTE = "samirandres296@gmail.com";
    private static final String CONTRASENA = "vwbm iiml fcmo tgnz";

    public ContrasenaServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correoDestino = request.getParameter("correo");
        boolean correoExiste = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/bd_tiendamascotas";
            String user = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT COUNT(*) FROM tblusuarios WHERE correo = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, correoDestino);
            
            rs = stmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                correoExiste = true;
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Hubo un error en la base de datos. Por favor, inténtalo de nuevo más tarde.");
            request.getRequestDispatcher("RecuperarContrasena.jsp").forward(request, response);
            return;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
      
        if (correoExiste) {
            String token = UUID.randomUUID().toString();
            Connection connToken = null;
            PreparedStatement stmtToken = null;
            try {
                connToken = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_tiendamascotas", "root", "123456");
                String sqlUpdate = "UPDATE tblusuarios SET token_recuperacion = ?, fecha_expiracion_token = ? WHERE correo = ?";
                stmtToken = connToken.prepareStatement(sqlUpdate);
                stmtToken.setString(1, token);
                Timestamp expira = new Timestamp(System.currentTimeMillis() + 3600000); 
                stmtToken.setTimestamp(2, expira);
                stmtToken.setString(3, correoDestino);
                stmtToken.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("mensaje", "No se pudo generar el enlace de recuperación. Por favor, inténtalo de nuevo.");
                request.getRequestDispatcher("RecuperarContrasena.jsp").forward(request, response);
                return;
            } finally {
                try {
                    if (stmtToken != null) stmtToken.close();
                    if (connToken != null) connToken.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            String host = request.getContextPath();
            String enlaceRecuperacion = host + "/RestablecerContrasenaServlet?token=" + token;

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

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(CORREO_REMITENTE));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
                message.setSubject("Recuperación de Contraseña");
                
                String htmlContent = "<html><body>"
                        + "<h3>Recuperación de Contraseña</h3>"
                        + "<p>Hola, has solicitado la recuperación de tu contraseña.</p>"
                        + "<p>Por favor, haz clic en el siguiente enlace para restablecerla:</p>"
                        + "<p><a href='" + enlaceRecuperacion + "'>Restablecer mi Contraseña</a></p>"
                        + "<p>Si el enlace no funciona, copia y pega la siguiente URL en tu navegador:</p>"
                        + "<p>" + enlaceRecuperacion + "</p>"
                        + "</body></html>";
                
                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(htmlContent, "text/html");
                
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText("Hola, has solicitado la recuperación de tu contraseña. Por favor, haz clic en el siguiente enlace para restablecerla:\n\n" + enlaceRecuperacion);
                
                MimeMultipart multipart = new MimeMultipart("alternative");
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(htmlPart);
                
                message.setContent(multipart);

                Transport.send(message);
                request.setAttribute("mensaje", "Se ha enviado un correo con las instrucciones para restablecer tu contraseña.");

            } catch (MessagingException e) {
                e.printStackTrace();
                request.setAttribute("mensaje", "Hubo un error al enviar el correo. Por favor, inténtalo de nuevo.");
            }
        } else {
            request.setAttribute("mensaje", "El correo electrónico ingresado no se encuentra en nuestra base de datos.");
        }
        
        request.getRequestDispatcher("RecuperarContrasena.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}