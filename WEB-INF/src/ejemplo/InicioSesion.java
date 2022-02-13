package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import javax.servlet.http.Cookie;

public class InicioSesion extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        PlantillasHTML plantilla = new PlantillasHTML();
        String extra = plantilla.inicioSesionHTML();

        String pagina = plantilla.baseHTML("Inicio sesión", extra);

        out.println(pagina);

        String usr = req.getParameter("usuario");
        String pss = req.getParameter("contra"); 
        
        DB db = new DB();

        boolean comprobar = db.loguear(usr, pss);

        if(comprobar){   
            HttpSession sesion = req.getSession(true);
            sesion.setAttribute("usuario", usr);
            sesion.setMaxInactiveInterval(10 * 60); //la sesion durara 10 minutos
            
            //redireccionar
            resp.sendRedirect(req.getContextPath() + "/panel");
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion = req.getSession();
        sesion.invalidate(); //se desconecta de la sesion que ya tenga
        //resp.setCharacterEncoding("UTF-8");

        Instalador instalador = new Instalador();
        instalador.iniciar();
        
        doPost(req, resp);
    }
}