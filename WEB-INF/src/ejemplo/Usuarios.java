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


public class Usuarios extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        DB db = new DB();
        ArrayList<String> listaUsuarios = db.buscarUsuarios();

        PlantillasHTML plantilla = new PlantillasHTML();    
        String extra = plantilla.usuariosHTML(listaUsuarios);
        String pagina = plantilla.baseHTML("Control usuarios", extra);

        out.println(pagina);

        String pss1 = req.getParameter("usr");
        String pss2 = req.getParameter("contra"); 

        if(!pss1.equals("") && !pss2.equals("") && !listaUsuarios.contains(pss1)){
            db.insertarUsuario(pss1, pss2);
            out.println("pasa");
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.setCharacterEncoding("UTF-8");
        
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