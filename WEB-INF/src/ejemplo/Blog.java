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

import java.util.*;

public class Blog extends HttpServlet {

    static Cookie usuarioC = new Cookie("usuario", "");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        DB db = new DB();

        ArrayList<String> listaEntradas = db.buscarEntradas();

        HttpSession sesion = req.getSession(false);
        boolean conectado = false;
        if(sesion != null && sesion.getAttribute("usuario") != null){
            conectado = true;
        }

        PlantillasHTML plantilla = new PlantillasHTML();
        String extra = plantilla.blogHTML(listaEntradas, conectado);
        String pagina = plantilla.baseHTML("Blog", extra);
        out.println(pagina);
 
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}