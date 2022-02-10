package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import java.util.*;


public class Panel extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        PrintWriter out = resp.getWriter();
        DB db = new DB();

        HttpSession sesion = req.getSession(false);
        String u = (String) sesion.getAttribute("usuario");
        ArrayList<String> listaEntradas = db.buscarEntradas();

        PlantillasHTML plantilla = new PlantillasHTML();
        String extra = plantilla.panelHTML(u,listaEntradas);
        String pagina = plantilla.baseHTML("Panel de control", extra);

        out.println(pagina);

        String pss1 = req.getParameter("contra1");
        String pss2 = req.getParameter("contra2"); 

        if(!pss1.equals("") && !pss2.equals("") && pss1.equals(pss2)){
            db.actualizarUsuario(u, pss1); //actualiza la contraseña del ususario que este iniciado la sesion
            out.println("pasa");
        }
 
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        HttpSession sesion = req.getSession(false);

        //lo pongo asi porque sino, con uno me da error cuando ya he iniciado una vez y con el otro 
        //cuando no he iniciado. Poniendo los dos no da error en ningun caso
        if(sesion != null && sesion.getAttribute("usuario") != null){
            doPost(req, resp);
        }else{
            resp.sendRedirect(req.getContextPath() + "/iniciosesion");
        }
    }
}