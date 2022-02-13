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
        //lo quitpo porque parece que va pero cuando recarga la pagina vuelve otra vez a verse mal
        //y me da errores al guardarlo en sqlite
        //tambien he quitado la codificacion del html por eso
        //sin ponerlo no me da errores y se ve todo bien
        //resp.setCharacterEncoding("UTF-8");

        Instalador instalador = new Instalador();
        instalador.iniciar();
        
        doPost(req, resp);
    }
}