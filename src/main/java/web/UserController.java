package web;

import datos.UsuarioDaoJDBC;
import dominio.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UserController")
public class UserController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        //recuperamos los valores usuario y password del formulario
        String username = request.getParameter("username");
        String password = request.getParameter("password");
            
        Usuario usuario = new UsuarioDaoJDBC().acceso(new Usuario(username, password));
       
           
        if (usuario != null) {
             request.setAttribute("user", usuario);
            request.getRequestDispatcher("principal.jsp").forward(request, response);

        } else {

            request.getRequestDispatcher("index.jsp").forward(request, response);
           
        }
    }

}
