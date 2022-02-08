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
        /*
        usuarioC = new Cookie("usuario", usr);
        usuarioC.setMaxAge(60);
        usuarioC.setPath("/ejercicios");
        resp.addCookie(usuarioC);
        */
        PrintWriter out = resp.getWriter();
        DB db = new DB();

        ArrayList<String> listaEntradas = db.buscarEntradas();

        HttpSession sesion = req.getSession(false);
        //String u = (String) sesion.getAttribute("usuario");

        String entradas = "";

        for(int i = 0; i < listaEntradas.size(); i++){
            entradas = entradas +   """
                                    <h3>{titulo}</h3>
                                    {fecha}
                                    <br>
                                    {texto}
                                    <br>
                                    <a href='{eEditar}'> Editar<a>
                                    <br>
                                    <a href='{eBorrar}'> Borrar<a>
                                    <br>
                                    """;

            String[] datos = listaEntradas.get(i).split(",");//separa la liena de string, que tiene todos los datos juntos
            
            String eEditar = "/practica/editor?identrada=" + datos[0]; //datos[0] es la id de la entrada
            String eBorrar = "/practica/borrar?identrada="+ datos[0]; //es con ?

            entradas = entradas.replace("{eEditar}", eEditar);
            entradas = entradas.replace("{eBorrar}", eBorrar);
            entradas = entradas.replace("{titulo}", datos[1]);//el 1 es el titulo
            entradas = entradas.replace("{fecha}", datos[3]);//el 3 es la fecha
            entradas = entradas.replace("{texto}", datos[2]);//el 2 es el texto
        }

        String extra = entradas;

        PlantillasHTML plantilla = new PlantillasHTML();
        String pagina = plantilla.baseHTML("Panel de control", extra);

        out.println(pagina);
 
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}