package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;


public class Usuarios extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        DB db = new DB();
        //poner oculta la contraseña
        String formulario = """
            <form action='usuarios' method='post'>
            Usuario: <input type='text' name='usr'><br/> 
            Contraseña: <input type='text' name='contra'><br/> 
            <input type='submit' value='Insertar usuario'> 
            </form>
            <br>
            <a href='/practica/usuarios'>Crear nuevo usuario<a>
            <br>
            <a href='/practica/editor'>Crear nueva entrada<a>
            <br>
        """;

        //Map<Integer, String> mapEntradas = new HashMap<Integer, String>();
        ArrayList<String> listaUsuarios = db.buscarUsuarios();

        String entradas = "";

        for(int i = 0; i < listaUsuarios.size(); i++){
            entradas = entradas +   """
                                    <a href='{eEditar}'> Editar<a>
                                    <a href='{eBorrar}'> Borrar<a>
                                    {usr}
                                    <br>
                                    """;
            
            //String eEditar = "/practica/editor?identrada=" + datos[0]; //datos[0] es la id de la entrada
            String eBorrar = "/practica/borrar?idusr="+ listaUsuarios.get(i); 

            //entradas = entradas.replace("{eEditar}", eEditar);
            //entradas = entradas.replace("{eBorrar}", eBorrar);
            entradas = entradas.replace("{usr}", listaUsuarios.get(i));
        }

        String extra = formulario + "<br>" + entradas;

        PlantillasHTML plantilla = new PlantillasHTML();
        String pagina = plantilla.baseHTML("Panel de control", extra);

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
        doPost(req, resp);
    }
}