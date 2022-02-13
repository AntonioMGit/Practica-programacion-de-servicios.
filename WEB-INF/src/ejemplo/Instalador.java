package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import ejemplo.DB;


public class Instalador extends HttpServlet {

    public void iniciar(){
        DB db = new DB();

        db.borrarUsuario("admin");//por si acaso
        db.iniciar();
        db.insertarUsuario("admin", "admin");

    }
    
}