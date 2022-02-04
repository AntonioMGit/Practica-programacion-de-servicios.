package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.*;


public class Borrar extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //
        DB db = new DB();
    
        PrintWriter out = resp.getWriter();

        String idEntrada = req.getParameter("identrada");

        db.borrarEntrada(idEntrada);  

        PlantillasHTML plantilla = new PlantillasHTML();
        String texto = "Entrada con id: " + idEntrada + " eliminada correctamente. " +
            "<br>" +
            "<a href='/practica/panel'>Volver al panel de control<a>";

        String pagina = plantilla.baseHTML("Entrada borrada", texto);

        out.println(pagina);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}