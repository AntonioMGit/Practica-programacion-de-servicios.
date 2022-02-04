package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;


public class Panel extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        DB db = new DB();
        //poner oculta la contraseña
        String formulario = """
            <form action='panel' method='post'>
            Contraseña: <input type='text' name='contra1'><br/> 
            Repetir contraseña: <input type='text' name='contra2'><br/> 
            <input type='submit' value='Cambiar contraseña'> 
            </form>
            <br>
            <a href='/practica/editor'>Crear nueva entrada<a>
            <br>
        """;

        //Map<Integer, String> mapEntradas = new HashMap<Integer, String>();
        ArrayList<String> listaEntradas = db.buscarEntradas();

        String entradas = "";

        for(int i = 0; i < listaEntradas.size(); i++){
            entradas = entradas +   """
                                    <a href='{eEditar}'> Editar<a>
                                    <a href='{eBorrar}'> Borrar<a>
                                    {titulo}
                                    <br>
                                    """;

            String[] datos = listaEntradas.get(i).split(",");//separa la liena de string, que tiene todos los datos juntos
            
            String eEditar = "/practica/editor?accion=editar&identrada=" + datos[0]; //datos[0] es la id de la entrada
            String eBorrar = "/practica/editor?accion=borrar&identrada="+ datos[0]; //es con ?

            entradas = entradas.replace("{eEditar}", eEditar);
            entradas = entradas.replace("{eBorrar}", eBorrar);
            entradas = entradas.replace("{titulo}", datos[1]);//el 1 es el titulo
        }

        String extra = formulario + "<br>" + entradas;

        PlantillasHTML plantilla = new PlantillasHTML();
        String pagina = plantilla.baseHTML("Panel de control", extra);

        out.println(pagina);

        String pss1 = req.getParameter("contra1");
        String pss2 = req.getParameter("contra2"); 

        if(!pss1.equals("") && !pss2.equals("") && pss1.equals(pss2)){
            db.actualizarUsuario("admin", pss1);
            out.println("pasa");
        }
 
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}