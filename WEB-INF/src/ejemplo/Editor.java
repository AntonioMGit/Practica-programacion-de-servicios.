package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;


public class Editor extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
    
        String formulario = """
            <form action='editor' method='post'>
                Titulo: <br>
                <input type='text' name='titulo'><br/> 
                Texto: <br>
                <input type='text' name='texto'><br/> 
                <input type='date' name='fecha'><br/>
                <input type='submit' value='Guardar'> 
            </form>
        """;
        PlantillasHTML plantilla = new PlantillasHTML();
        String pagina = plantilla.baseHTML("Editar entrada", formulario);

        out.println(pagina);

        String titulo = req.getParameter("titulo");
        String texto = req.getParameter("texto"); 
        String fecha = req.getParameter("fecha");

        if(!titulo.equals("")){
            DB db = new DB();

            db.insertarEntrada(titulo, texto, fecha);
            out.println("pasa");
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}