package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.*;


public class Editor extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //
        DB db = new DB();
    
        PrintWriter out = resp.getWriter();

        String tipo = req.getParameter("tipo");

        String idEntrada = req.getParameter("identrada");
        /*
        String formulario = """
            <form action='editor' method='post'>
                Titulo: <br>
                <input type='text' name='titulo' value={titulo}><br/> 
                Texto: <br>
                <input type='text' name='texto' value={texto}><br/> 
                <input type='date' name='fecha'><br/> 
                <input type='submit' value='Guardar'> 
            </form>
        """;

        if(idEntrada!=null){
            List<String> entrada = db.buscarEntradaPorId(idEntrada);

            formulario = formulario.replace("{titulo}", entrada.get(1));//la 1 es la del titulo
            formulario = formulario.replace("{texto}", entrada.get(2));//la 2 es la del texto

            //fecha tambien?
        }else{
            formulario = formulario.replace("{titulo}", "");
            formulario = formulario.replace("{texto}", "");
        }
        */

        PlantillasHTML plantilla = new PlantillasHTML();
        String extra = "";

        if(idEntrada!=null){
            List<String> entrada = db.buscarEntradaPorId(idEntrada);
            extra = plantilla.editorHTML(entrada.get(1), entrada.get(2));//1titulo 2texto
        }else{
            extra = plantilla.editorHTML("", "");
        }
        
        String pagina = plantilla.baseHTML("Editar entrada", extra);

        out.println(pagina);

        String titulo = req.getParameter("titulo");
        String texto = req.getParameter("texto"); 
        String fecha = req.getParameter("fecha");

        if(!titulo.equals("")&&!texto.equals("")&&!fecha.equals("")){
            if(idEntrada!=null){
                db.actualizarEntrada(idEntrada, titulo, texto, fecha);
            }else{
                db.insertarEntrada(titulo, texto, fecha);
            }
            //redireccionar
            resp.sendRedirect(req.getContextPath() + "/blog");
        }else{
            out.println("Faltan datos");
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}